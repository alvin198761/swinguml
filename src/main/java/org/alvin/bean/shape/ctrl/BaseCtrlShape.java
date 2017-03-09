package org.alvin.bean.shape.ctrl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.alvin.bean.shape.BaseShape;
import org.alvin.bean.shape.data.BaseDataShape;

/**
 * 控制图形的基类，包括连线的控制盒图形大小的控制，或许以后还会有新的东西吧，
 * 
 * @author 唐植超
 * 
 */
public abstract class BaseCtrlShape extends BaseShape {

	private static final long serialVersionUID = 1L;
	//控制点的方位，比如，改变大小的控制点，就有上方的，负责向上改变大小
	protected String way;
	//被控制值的图形
	protected BaseDataShape ctrl;
	//是否启用控制
	protected boolean enable;
	//在划线的时候，有没有选中该控制点
	protected boolean lineHover;

	public BaseCtrlShape(BaseDataShape ctrl, String way) {
		this.ctrl = ctrl;
		this.way = way;
		bgColor = Color.green;
	}

	
	public boolean contains(Point2D p) {
		if (!enable) {
			return false;
		}
		return super.contains(p);
	}

	public BaseDataShape getCtrl() {
		return ctrl;
	}

	public String getWay() {
		return way;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isLineHover() {
		return lineHover;
	}

	public void setCtrl(BaseDataShape ctrl) {
		this.ctrl = ctrl;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setLineHover(boolean lineHover) {
		this.lineHover = lineHover;
	}

	public void setWay(String way) {
		this.way = way;
	}

	protected void drawLineHoverBox(Graphics2D g) {
		if (!lineHover) {
			return;
		}
		g.setColor(Color.red);
		g.draw(getBounds2D());
	}

	
	public String toString() {
		return way;
	}
}
