package org.alvin.bean.shape.data.seri;

import static org.alvin.bean.shape.ShapeHelper.CTRL_SHAPE_SIZE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlLineShape;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.sys.Language;

/**
 * ¼¤»î
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class SeriActiveShape extends BaseSeriShape {

	private static final long serialVersionUID = 1L;
	private SeriLifelineShape lifeShape;

	public SeriActiveShape() {
		setW(ShapeHelper.CTRL_SHAPE_SIZE);
		editable = false;
		conectable = true;
		text = Language.getValue("Active");
		type = ShapeHelper.SERI_ACTIVE;
		setH(100);
	}

	
	protected void createBox() {
		resizeMap = new HashMap<String, CtrlResizeShape>();
		resizeMap.put(ShapeHelper.WAY_BOTTOM, new CtrlResizeShape(this,
				ShapeHelper.WAY_BOTTOM));
		resizeMap.put(ShapeHelper.WAY_TOP, new CtrlResizeShape(this,
				ShapeHelper.WAY_TOP));
		resizeMap.get(ShapeHelper.WAY_BOTTOM).setEnable(true);
		resizeMap.get(ShapeHelper.WAY_TOP).setEnable(true);

		ctrlLineMap = new HashMap<String, CtrlLineShape>();
		for (String way : ShapeHelper.getWay6()) {
			ctrlLineMap.put(way, new CtrlLineShape(this, way));
		}
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Rectangle2D.Double(x, y, w, h);
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(borderColor);
		g.draw(shape);
	}

	
	public Point2D getPoisationByWay(String way) {
		if (ShapeHelper.WAY_TOP.equals(way)) {
			return super.getPoisationByWay(way);
		}
		if (ShapeHelper.WAY_BOTTOM.equals(way)) {
			return super.getPoisationByWay(way);
		}
		int chw = CTRL_SHAPE_SIZE >> 1;
		double subH = this.h / 4;
		double x1 = this.x - chw;
		double x2 = this.x + this.w - chw;
		Rectangle2D rect = this.getBounds2D();
		// ×ó±ß

		if (ShapeHelper.WAY_TOP_LEFT.equals(way)) {
			return new Point2D.Double(x1, rect.getY() + subH);
		}
		if (ShapeHelper.WAY_TOP_RIGHT.equals(way)) {
			return new Point2D.Double(x1, rect.getY() + subH * 2);
		}
		if (ShapeHelper.WAY_LEFT.equals(way)) {
			return new Point2D.Double(x1, rect.getY() + subH * 3);
		}
		// ÓÒ±ß
		if (ShapeHelper.WAY_RIGHT.equals(way)) {
			return new Point2D.Double(x2, rect.getY() + subH);
		}
		if (ShapeHelper.WAY_BOTTOM_LEFT.equals(way)) {
			return new Point2D.Double(x2, rect.getY() + subH * 2);
		}
		if (ShapeHelper.WAY_BOTTOM_RIGHT.equals(way)) {
			return new Point2D.Double(x2, rect.getY() + subH * 3);
		}
		return null;
	}

	public SeriLifelineShape getLifeShape() {
		return lifeShape;
	}

	public void setLifeShape(SeriLifelineShape lifeShape) {
		this.lifeShape = lifeShape;
	}

}
