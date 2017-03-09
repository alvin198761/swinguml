package org.alvin.bean.shape.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.bean.shape.ctrl.LineResizeShape;

public abstract class BaseLineShape extends BaseDataShape {

	private static final long serialVersionUID = 1L;

	protected double x2;
	protected double y2;
	protected static final int arrowLen = 10;
	protected BaseDataShape beginShape;
	protected BaseDataShape endShape;

	public BaseLineShape() {
		conectable = false;
	}

	public boolean contains(Point2D p) {
		return Math.abs(p.getY() - ((y2 - y) / (x2 - x) * (p.getX() - x) + y)) <= 2;
	}

	public void containsLineHover(Point2D p) {
		return;
	}

	protected void drawArrow(Graphics2D g) {
		drawArrow(g, x, y, x2, y2);
	}

	protected void drawArrow(Graphics2D g, double x, double y, double x2,
			double y2) {
		double d = Math.abs(Point2D.distance(x2, y2, x, y));
		double xa = x2 + arrowLen * ((x - x2) + (y - y2) / 2) / d;
		double ya = y2 + arrowLen * ((y - y2) - (x - x2) / 2) / d;
		double xb = x2 + arrowLen * ((x - x2) - (y - y2) / 2) / d;
		double yb = y2 + arrowLen * ((y - y2) + (x - x2) / 2) / d;
		g.draw(new Line2D.Double(x2, y2, xa, ya));
		g.draw(new Line2D.Double(x2, y2, xb, yb));
	}

	protected void drawCtrl(Graphics2D g) {
		return;
	}

	protected void drawSelectBox(Graphics2D g) {

	}

	protected abstract void drawLine(Graphics2D g);

	public abstract void changePoint();

	protected final void drawShape(Graphics2D g) {
		drawLine(g);
		drawArrow(g);
	}

	public BaseDataShape getBeginShape() {
		return beginShape;
	}

	public BaseDataShape getEndShape() {
		return endShape;
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}

	public void setBeginShape(BaseDataShape beginShape) {
		this.beginShape = beginShape;
		if (beginShape == null) {
			return;
		}
		changePoint();
	}

	public void setEndShape(BaseDataShape endShape) {
		if (this.beginShape == endShape) {
			return;
		}
		this.endShape = endShape;
		if (endShape == null) {
			return;
		}
		changePoint();
	}

	public void setBeginShape(BaseDataShape beginShape, String way) {
		this.beginShape = beginShape;
		this.beginShape.addBeginLine(this, way);
		changePoint();
	}

	public void setEndShape(BaseDataShape endShape, String way) {
		this.endShape = endShape;
		this.endShape.addEndLine(this, way);
		changePoint();
	}

	public void setSelect(boolean select) {
		if (this.select == select) {
			return;
		}
		this.select = select;
		if (select) {
			borderColor = Color.red;
		} else {
			borderColor = Color.black;
		}
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public boolean canCopy() {
		return false;
	}

	public String toData() {
		String beginWay = this.beginShape.getWay(this);
		String endWay = this.endShape.getWay(this);
		return "<shape id=\"" + id + "\" type=\"" + type + "\" bid=\""
				+ beginShape.getId() + "\" bway=\"" + beginWay + "\" eid=\""
				+ endShape.getId() + "\" eway=\"" + endWay + "\"><![CDATA["
				+ text + "]]></shape>";
	}

	public Point2D getPoisationByWay(String way) {
		int chw = ShapeHelper.CTRL_SHAPE_SIZE >> 1;
		Point2D p = new Point2D.Double();
		if (way.equals(ShapeHelper.WAY_BOTTOM)) {
			p.setLocation(x2 - chw, y2 - chw);
		}
		if (way.equals(ShapeHelper.WAY_TOP)) {
			p.setLocation(x - chw, y - chw);
		}
		return p;
	}

	protected void createBox() {
		if (!this.ctrlable) {
			return;
		}
		resizeMap = new HashMap<String, CtrlResizeShape>();
		// 控制上下的位置
		resizeMap.put(ShapeHelper.WAY_TOP, new LineResizeShape(this,
				ShapeHelper.WAY_TOP));
		resizeMap.get(ShapeHelper.WAY_TOP).setEnable(true);
		resizeMap.put(ShapeHelper.WAY_BOTTOM, new LineResizeShape(this,
				ShapeHelper.WAY_BOTTOM));
		// 启用
		resizeMap.get(ShapeHelper.WAY_BOTTOM).setEnable(true);
		resizeMap.get(ShapeHelper.WAY_TOP).setEnable(true);
		endInit();
	}

}
