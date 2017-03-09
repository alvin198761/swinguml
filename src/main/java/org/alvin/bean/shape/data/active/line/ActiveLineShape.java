package org.alvin.bean.shape.data.active.line;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.sys.Language;
import org.alvin.util.LogUtil;

public class ActiveLineShape extends BaseLineShape {

	private static final long serialVersionUID = 1L;

	public ActiveLineShape() {
		type = ShapeHelper.ACTIVE_LINE;
		text = Language.getValue("Line");
	}

	
	protected void drawText(Graphics2D g) {

	}
	
	
	protected void drawResize(Graphics2D g) {
		return;
	}

	
	protected void init() {
		this.ctrlable = false;
	}

	
	protected void drawLine(Graphics2D g) {
		shape = new Line2D.Double(x, y, x2, y2);
		g.setColor(borderColor);
		g.draw(shape);
	}

	/**
	 * 根据图形的方位，自动调节位置
	 */
	public void changePoint() {
		if (beginShape == null || endShape == null) {
			return;
		}
		Point2D sP = new Point2D.Double(beginShape.getX() + beginShape.getW()
				/ 2, beginShape.getY() + beginShape.getH());
		Point2D eP = new Point2D.Double(endShape.getX() + endShape.getW() / 2,
				endShape.getY() + endShape.getH());
		if ((eP.getX() > sP.getX())
				&& (Math.abs(eP.getX() - sP.getX()) > Math.abs(eP.getY()
						- sP.getY()))) {
			// -45°< p < 45°
			beginShape.removeLine(this);
			beginShape.addBeginLine(this, ShapeHelper.WAY_RIGHT);

			endShape.removeLine(this);
			endShape.addEndLine(this, ShapeHelper.WAY_LEFT);

		} else if ((eP.getX() < sP.getX())
				&& (Math.abs(eP.getX() - sP.getX()) > Math.abs(eP.getY()
						- sP.getY()))) {
			// 135°< p <225°
			beginShape.removeLine(this);
			beginShape.addBeginLine(this, ShapeHelper.WAY_LEFT);

			endShape.removeLine(this);
			endShape.addEndLine(this, ShapeHelper.WAY_RIGHT);
		} else if ((eP.getY() > sP.getY())
				&& (Math.abs(eP.getX() - sP.getX()) < Math.abs(eP.getY()
						- sP.getY()))) {
			// 225°< p <315°
			beginShape.removeLine(this);
			beginShape.addBeginLine(this, ShapeHelper.WAY_BOTTOM);

			endShape.removeLine(this);
			endShape.addEndLine(this, ShapeHelper.WAY_TOP);
		} else if ((eP.getY() < sP.getY())
				&& (Math.abs(eP.getX() - sP.getX()) < Math.abs(eP.getY()
						- sP.getY()))) {
			// 45°< p <135°
			beginShape.removeLine(this);
			beginShape.addBeginLine(this, ShapeHelper.WAY_TOP);

			endShape.removeLine(this);
			endShape.addEndLine(this, ShapeHelper.WAY_BOTTOM);
		} else {
			LogUtil.info("没连上");
		}
	}

}
