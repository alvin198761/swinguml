package org.alvin.bean.shape.ctrl;

import static org.alvin.bean.shape.ShapeHelper.CTRL_SHAPE_SIZE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Method;

import org.alvin.bean.shape.data.BaseDataShape;

/**
 * 用来改变图形大小的控制器
 * 
 * @author 唐植超
 * 
 */
public class CtrlResizeShape extends BaseCtrlShape {

	private static final long serialVersionUID = 1L;

	public CtrlResizeShape(BaseDataShape ctrl, String way) {
		super(ctrl, way);
	}

	/**
	 * 根据控制点所在的方位控制某个方向的大小变化
	 * 
	 * @param e
	 */
	public void contrl(Point2D p) {
		if (!enable) {
			return;
		}
		try {
			Method me = this.getClass().getDeclaredMethod("contrl" + way,
					Point2D.class);
			boolean flag = me.isAccessible();
			me.invoke(this, p);
			me.setAccessible(flag);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 控制上面
	protected void contrl8(Point2D p) {
		double y = ctrl.getY();
		double sub = y - p.getY();
		ctrl.setY(y - sub);
		ctrl.setH(ctrl.getH() + sub);
	}

	// 上右
	protected void contrl9(Point2D p) {
		contrl8(p);
		contrl12(p);
	}

	// 上左
	protected void contrl10(Point2D p) {
		contrl8(p);
		contrl11(p);
	}

	// 左
	protected void contrl11(Point2D p) {
		double x = ctrl.getX();
		double sub = x - p.getX();
		ctrl.setX(x - sub);
		ctrl.setW(ctrl.getW() + sub);
	}

	// 右
	protected void contrl12(Point2D p) {
		double x = ctrl.getX() + ctrl.getW();
		double sub = p.getX() - x;
		ctrl.setW(ctrl.getW() + sub);
	}

	// 下面
	protected void contrl13(Point2D p) {
		double y = ctrl.getY() + ctrl.getH();
		double sub = p.getY() - y;
		ctrl.setH(ctrl.getH() + sub);
	}

	// 左下方
	protected void contrl14(Point2D p) {
		contrl13(p);
		contrl11(p);
	}

	// 右下方
	protected void contrl15(Point2D p) {
		contrl13(p);
		contrl12(p);
	}

	
	public void draw(Graphics2D g) {
		g.setColor(enable ? Color.green : Color.gray);
		Point2D p = ctrl.getPoisationByWay(way);
		shape = new Rectangle2D.Double(p.getX(), p.getY(), CTRL_SHAPE_SIZE,
				CTRL_SHAPE_SIZE);
		g.fill(shape);
		g.setColor(Color.black);
		g.draw(shape);
	}

}
