package org.alvin.bean.shape.ctrl;

import static org.alvin.bean.shape.ShapeHelper.CTRL_SHAPE_SIZE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;

/**
 * 连线的管理和控制
 * 
 * @author 唐植超
 * 
 */
public class CtrlLineShape extends BaseCtrlShape {

	private static final long serialVersionUID = 1L;
	public static final String STATUS_BEGIN = "begin";
	public static final String STATUS_END = "end";
	// 图形某个方位的所有连线
	protected LinkedList<BaseLineShape> lines = new LinkedList<BaseLineShape>();
	// 每个连线的方向，[line.id,way],因为线是有箭头的
	protected Map<String, String> lineMap = new HashMap<String, String>();

	public Map<String, String> getLineMap() {
		return lineMap;
	}

	public CtrlLineShape(BaseDataShape ctrl, String way) {
		super(ctrl, way);
	}

	
	public void draw(Graphics2D g) {
		g.setColor(Color.blue);
		Point2D p = ctrl.getPoisationByWay(way);
		shape = new Rectangle2D.Double(p.getX(), p.getY(), CTRL_SHAPE_SIZE,
				CTRL_SHAPE_SIZE);
		g.draw(new Line2D.Double(p.getX(), p.getY(),
				p.getX() + CTRL_SHAPE_SIZE, p.getY() + CTRL_SHAPE_SIZE));
		g.draw(new Line2D.Double(p.getX(), p.getY() + CTRL_SHAPE_SIZE, p.getX()
				+ CTRL_SHAPE_SIZE, p.getY()));
		drawLineHoverBox(g);
	}

	/**
	 * 添加开始端在这个控制器上面的线
	 * 
	 * @param line
	 */
	public void addBeginLine(BaseLineShape line) {
		this.lines.add(line);
		lineMap.put(line.getId(), STATUS_BEGIN);
		contrllineShape(line);
	}

	/**
	 * 添加箭头端在这个控制器的还是那个的线
	 * 
	 * @param line
	 */
	public void addEndLine(BaseLineShape line) {
		this.lines.add(line);
		lineMap.put(line.getId(), STATUS_END);
		contrllineShape(line);
	}

	/**
	 * 解除和一条线的关系
	 * 
	 * @param line
	 */
	public boolean removeLine(BaseDataShape line) {
		return this.lines.remove(line);
	}

	/**
	 * 移动所有的线
	 */
	public void contrlLine() {
		for (BaseLineShape line : lines) {
			contrllineShape(line);
		}
	}

	public List<BaseLineShape> getLines() {
		return lines;
	}

	/**
	 * 移动某一条线
	 * 
	 * @param line
	 */
	private void contrllineShape(BaseLineShape line) {
		Rectangle2D rect = this.getBounds2D();
		if (lineMap.get(line.getId()).equals(STATUS_BEGIN)) {
			line.setX(rect.getCenterX());
			line.setY(rect.getCenterY());
		}
		if (lineMap.get(line.getId()).equals(STATUS_END)) {
			line.setX2(rect.getCenterX());
			line.setY2(rect.getCenterY());
		}
	}

}
