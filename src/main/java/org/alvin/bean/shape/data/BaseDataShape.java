package org.alvin.bean.shape.data;

import static org.alvin.bean.shape.ShapeHelper.CTRL_SHAPE_SIZE;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.alvin.bean.shape.BaseShape;
import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlLineShape;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;

public abstract class BaseDataShape extends BaseShape {

	private static final long serialVersionUID = 1L;
	public static final String MODE_PREVIEW = "preview";
	public static final String MODE_DRAW = "draw";
	public static final String MODE_DRAG = "drag";
	public static final String MODE_MENU = "menu";

	protected boolean editable = false;

	protected boolean ctrlable = true;

	protected boolean conectable = true;
	protected boolean subSystem = false;
	protected boolean lineHover = false;
	protected String text;

	// 有几种模式 画图模式 预览模式 拖动模式 菜单模式
	protected String model = MODE_DRAW;

	protected Map<String, CtrlLineShape> ctrlLineMap;

	protected Map<String, CtrlResizeShape> resizeMap;

	public BaseDataShape() {
		init();
		createBox();
	}

	public void addBeginLine(BaseLineShape line, String way) {
		ctrlLineMap.get(way).addBeginLine(line);
	}

	public void addEndLine(BaseLineShape line, String way) {
		ctrlLineMap.get(way).addEndLine(line);
	}

	public boolean canCopy() {
		return select;
	}

	public void containsLineHover(Point2D p) {
		setLineHover(contains(p));
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			entry.getValue().setLineHover(entry.getValue().contains(p));
		}
	}

	public CtrlResizeShape contansCtrlBox(Point2D p) {
		if (resizeMap == null) {
			return null;
		}
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			if (entry.getValue().contains(p)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public void contrlLines() {
		if (!conectable) {
			return;
		}
		List<BaseLineShape> lines = getAllLines();
		for (BaseLineShape line : lines) {
			line.changePoint();
		}
		// 移动线条的位置
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			entry.getValue().contrlLine();
		}
	}

	protected void createBox() {
		return;
	}

	
	public final void draw(Graphics2D g) {
		// 画图形
		drawShape(g);
		if (select) {
			// 画一个淡绿色的框框，表示好看
			drawSelectBox(g);
			// 控制拖动
			drawResize(g);
		}
		do {
			if (model.equals(MODE_MENU))
				break;
			if (!conectable)
				break;
			// 控制线
			drawCtrl(g);
		} while (false);
		do {
			if (!editable)
				break;
			if (model.equals(MODE_MENU))
				break;
			if (text == null)
				break;
			if (text.trim().equals(""))
				break;
			drawText(g);
		} while (false);
		drawLineHoverBox(g);
	}

	protected void drawCtrl(Graphics2D g) {
		for (java.util.Map.Entry<String, CtrlLineShape> entry : ctrlLineMap
				.entrySet()) {
			entry.getValue().draw(g);
		}
	}

	protected void drawLineHoverBox(Graphics2D g) {
		if (!conectable) {
			return;
		}
		if (!lineHover) {
			return;
		}
		g.setColor(Color.red);
		g.draw(getBounds2D());
	}

	protected void drawResize(Graphics2D g) {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().draw(g);
		}
	}

	protected abstract void drawSelectBox(Graphics2D g);

	protected abstract void drawShape(Graphics2D g);

	protected void drawText(Graphics2D g) {
		g.setColor(Color.black);
		Rectangle2D rect = this.getBounds2D();
		FontMetrics fm = g.getFontMetrics();
		int fontW = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fontH = fm.getAscent() - 20;
		double x = rect.getCenterX() - (fontW >> 1);
		double y = rect.getCenterY() - (fontH >> 1);
		g.drawString(text, (float) x, (float) y);
	}

	protected void endInit() {
		return;
	}

	public List<BaseLineShape> getAllLines() {
		if (!conectable) {
			return new LinkedList<BaseLineShape>();
		}
		List<BaseLineShape> lines = new LinkedList<BaseLineShape>();
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			lines.addAll(entry.getValue().getLines());
		}
		return lines;
	}

	public String getModel() {
		return model;
	}

	public Point2D getPoisationByWay(String way) {
		Rectangle2D rect = this.getBounds2D();
		double hx = rect.getX() + rect.getWidth() / 2;
		double hy = rect.getY() + rect.getHeight() / 2;

		double endX = rect.getX() + rect.getWidth();
		double endY = rect.getY() + rect.getHeight();
		int chw = CTRL_SHAPE_SIZE >> 1;
		if (ShapeHelper.WAY_TOP.equals(way)) {
			return new Point2D.Double(hx - chw, y - chw);
		}
		if (ShapeHelper.WAY_TOP_LEFT.equals(way)) {
			return new Point2D.Double(x - chw, y - chw);
		}
		if (ShapeHelper.WAY_TOP_RIGHT.equals(way)) {
			return new Point2D.Double(endX - chw, y - chw);
		}
		if (ShapeHelper.WAY_LEFT.equals(way)) {
			return new Point2D.Double(x - chw, hy - chw);
		}
		if (ShapeHelper.WAY_RIGHT.equals(way)) {
			return new Point2D.Double(endX - chw, hy - chw);
		}
		if (ShapeHelper.WAY_BOTTOM.equals(way)) {
			return new Point2D.Double(hx - chw, endY - chw);
		}
		if (ShapeHelper.WAY_BOTTOM_LEFT.equals(way)) {
			return new Point2D.Double(x - chw, endY - chw);
		}
		if (ShapeHelper.WAY_BOTTOM_RIGHT.equals(way)) {
			return new Point2D.Double(endX - chw, endY - chw);
		}
		return null;
	}

	public String getText() {
		return text;
	}

	public String getWay(BaseLineShape line) {
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			if (entry.getValue().getLines().contains(line)) {
				return entry.getKey();
			}
		}
		return "0";
	}

	protected void init() {
		return;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isLineHover() {
		return lineHover;
	}

	public boolean isSubSystem() {
		return subSystem;
	}

	
	public void setH(double h) {
		if (h < 5) {
			this.h = 5;
			return;
		}
		this.h = h;
	}

	public void setLineHover(boolean lineHover) {
		if (!conectable) {
			this.lineHover = false;
			return;
		}
		if (this.lineHover == lineHover) {
			return;
		}
		this.lineHover = lineHover;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public void setW(double w) {
		if (w < 5) {
			w = 5;
			return;
		}
		this.w = w;
	}

	public String toData() {
		return "<shape id=\"" + id + "\" type=\"" + type + "\" x=\"" + x
				+ "\" y=\"" + y + "\" w=\"" + w + "\" h=\"" + h
				+ "\"><![CDATA[" + text + "]]></shape>";
	}

	
	public String toString() {
		return text;
	}

	public void removeLine(BaseDataShape line) {
		for (String way : ShapeHelper.getWay4()) {
			CtrlLineShape ctrl = this.ctrlLineMap.get(way);
			if (ctrl == null) {
				continue;
			}
			if (ctrl.removeLine(line)) {
				break;
			}
		}
	}

	public boolean isConectable() {
		return conectable;
	}

}
