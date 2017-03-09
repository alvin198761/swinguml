package org.alvin.bean.shape.ctrl;

import java.awt.geom.Point2D;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;

/**
 * 直线的控制
 * 
 * @author 唐植超
 * 
 */
public class LineResizeShape extends CtrlResizeShape {

	private static final long serialVersionUID = 1L;

	public LineResizeShape(BaseDataShape ctrl, String way) {
		super(ctrl, way);
	}

	// 下面
	protected void contrl13(Point2D p) {
		BaseLineShape line = (BaseLineShape) this.ctrl;
		line.setX2(p.getX());
		line.setY2(p.getY());
	}

	// 控制上面
	protected void contrl8(Point2D p) {
		BaseLineShape line = (BaseLineShape) this.ctrl;
		line.setX(p.getX());
		line.setY(p.getY());
	}

}
