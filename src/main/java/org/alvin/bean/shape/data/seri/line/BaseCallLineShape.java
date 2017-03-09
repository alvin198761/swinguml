package org.alvin.bean.shape.data.seri.line;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.HashMap;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.bean.shape.ctrl.LineResizeShape;
import org.alvin.bean.shape.data.BaseDataShape;

/**
 * 转弯的线
 * 
 * @author 唐植超
 * 
 */
public abstract class BaseCallLineShape extends BaseSeriLineShape {

	private static final long serialVersionUID = 1L;

	public BaseCallLineShape() {
	}

	
	public boolean contains(Point2D p) {
		QuadCurve2D qc = (QuadCurve2D) shape;
		QuadCurve2D max = new QuadCurve2D.Double(qc.getX1() - 4,
				qc.getY1() - 4, qc.getCtrlX() + 8, qc.getCtrlY(),
				qc.getX2() - 4, qc.getY2() + 4);
		QuadCurve2D min = new QuadCurve2D.Double(qc.getX1() + 4,
				qc.getY1() + 4, qc.getCtrlX() - 8, qc.getCtrlY(),
				qc.getX2() + 4, qc.getY2() - 4);
		return !min.contains(p) && max.contains(p);
	}

	protected void drawArrow(Graphics2D g) {
		drawArrow(g, x + w, y + h / 2, x2, y2);
	}

	
	protected void drawLine(Graphics2D g) {
		this.w = 40;
		this.h = this.y2 - this.y;
		shape = new QuadCurve2D.Double(x, y, x + w, y + h / 2, x2, y2);
		g.setColor(borderColor);
		g.draw(shape);
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

		resizeMap.get(ShapeHelper.WAY_BOTTOM).setEnable(true);
		resizeMap.get(ShapeHelper.WAY_TOP).setEnable(true);
		endInit();
	}

	public void setEndShape(BaseDataShape endShape) {
		this.endShape = endShape;
		changePoint();
	}

}
