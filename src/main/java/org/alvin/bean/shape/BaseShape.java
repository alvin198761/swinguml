package org.alvin.bean.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.BaseObject;
import org.alvin.sys.Application;

public abstract class BaseShape extends BaseObject {

	private static final long serialVersionUID = 1L;

	protected double x = 0;
	protected double y = 0;
	protected double w;
	protected double h;
	protected boolean select;
	protected String type;
	protected Color bgColor = Color.black;
	protected Color borderColor = Color.black;
	protected Shape shape;

	public BaseShape() {
		// ����Ψһ���
		id = Application.getTimeId();
	}

	/**
	 * �Ƿ����
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point2D p) {
		return shape.contains(p);
	}

	/**
	 * �Ƿ��ཻ
	 * 
	 * @param rect
	 * @return
	 */
	public boolean intersects(Rectangle2D rect) {
		return shape.intersects(rect);
	}

	/**
	 * ͼ�γ��ֵķ���
	 * 
	 * @param g
	 */
	public abstract void draw(Graphics2D g);

	public Rectangle2D getBounds2D() {
		return shape.getBounds2D();
	}

	public Point2D getCenterPoint() {
		return new Point2D.Double(shape.getBounds2D().getCenterX(), shape
				.getBounds2D().getCenterY());
	}

	public double getH() {
		return h;
	}

	public String getType() {
		return type;
	}

	public double getW() {
		return w;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isSelect() {
		return select;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setSelect(boolean select) {
		if (this.select == select) {
			return;
		}
		this.select = select;
	}

	public void setW(double w) {
		this.w = w;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
