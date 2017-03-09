package org.alvin.bean.shape.data.seri.line;

import java.awt.geom.Point2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.data.BaseLineShape;

public abstract class BaseSeriLineShape extends BaseLineShape {

	private static final long serialVersionUID = 1L;

	public BaseSeriLineShape() {
		editable = true;
	}

	public void changePoint() {
		// 实现自动连线的代码
		double len1;
		double len2;
		double len3;
		Point2D p;
		if (beginShape != null) {
			// 看看尾端是在左边还是右边
			p = new Point2D.Double(this.x, this.y);
			if (this.x2 < beginShape.getX()) {
				Point2D tempP = null;
				tempP = beginShape.getPoisationByWay(ShapeHelper.WAY_LEFT);
				len1 = p.distance(tempP);
				tempP = beginShape.getPoisationByWay(ShapeHelper.WAY_TOP_LEFT);
				len2 = p.distance(tempP);
				tempP = beginShape.getPoisationByWay(ShapeHelper.WAY_TOP_RIGHT);
				len3 = p.distance(tempP);
				beginShape.removeLine(this);
				if (len1 < len2 && len1 < len3) {
					beginShape.addBeginLine(this, ShapeHelper.WAY_LEFT);
				} else if (len2 < len3) {
					beginShape.addBeginLine(this, ShapeHelper.WAY_TOP_LEFT);
				} else {
					beginShape.addBeginLine(this, ShapeHelper.WAY_TOP_RIGHT);
				}
			} else {
				Point2D tempP = null;
				tempP = beginShape.getPoisationByWay(ShapeHelper.WAY_RIGHT);
				len1 = p.distance(tempP);
				tempP = beginShape
						.getPoisationByWay(ShapeHelper.WAY_BOTTOM_LEFT);
				len2 = p.distance(tempP);
				tempP = beginShape
						.getPoisationByWay(ShapeHelper.WAY_BOTTOM_RIGHT);
				len3 = p.distance(tempP);
				beginShape.removeLine(this);
				if (len1 < len2 && len1 < len3) {
					beginShape.addBeginLine(this, ShapeHelper.WAY_RIGHT);
				} else if (len2 < len3) {
					beginShape.addBeginLine(this, ShapeHelper.WAY_BOTTOM_LEFT);
				} else {
					beginShape.addBeginLine(this, ShapeHelper.WAY_BOTTOM_RIGHT);
				}
			}
		}
		if (endShape != null) {
			// 看看头端是在左边还是右边
			p = new Point2D.Double(this.x2, this.y2);
			if (this.x < endShape.getX()) {
				Point2D tempP = null;
				tempP = endShape.getPoisationByWay(ShapeHelper.WAY_LEFT);
				len1 = p.distance(tempP);
				tempP = endShape.getPoisationByWay(ShapeHelper.WAY_TOP_LEFT);
				len2 = p.distance(tempP);
				tempP = endShape.getPoisationByWay(ShapeHelper.WAY_TOP_RIGHT);
				len3 = p.distance(tempP);
				endShape.removeLine(this);
				if (len1 < len2 && len1 < len3) {
					endShape.addEndLine(this, ShapeHelper.WAY_LEFT);
				} else if (len2 < len3) {
					endShape.addEndLine(this, ShapeHelper.WAY_TOP_LEFT);
				} else {
					endShape.addEndLine(this, ShapeHelper.WAY_TOP_RIGHT);
				}
			} else {
				Point2D tempP = null;
				tempP = endShape.getPoisationByWay(ShapeHelper.WAY_RIGHT);
				len1 = p.distance(tempP);
				tempP = endShape.getPoisationByWay(ShapeHelper.WAY_BOTTOM_LEFT);
				len2 = p.distance(tempP);
				tempP = endShape
						.getPoisationByWay(ShapeHelper.WAY_BOTTOM_RIGHT);
				len3 = p.distance(tempP);
				endShape.removeLine(this);
				if (len1 < len2 && len1 < len3) {
					endShape.addEndLine(this, ShapeHelper.WAY_RIGHT);
				} else if (len2 < len3) {
					endShape.addEndLine(this, ShapeHelper.WAY_BOTTOM_LEFT);
				} else {
					endShape.addEndLine(this, ShapeHelper.WAY_BOTTOM_RIGHT);
				}
			}
		}
	}

	public String toData() {
		String beginWay = "-1";
		String endWay = "-1";
		String startId = this.x + "," + this.getY();
		String endId = this.x2 + "," + this.getY2();
		if (this.beginShape != null) {
			beginWay = this.beginShape.getWay(this);
			startId = this.beginShape.getId();
		}
		if (this.endShape != null) {
			endWay = this.endShape.getWay(this);
			endId = this.endShape.getId();
		}
		return "<shape id=\"" + id + "\" type=\"" + type + "\" bid=\""
				+ startId + "\" bway=\"" + beginWay + "\" eid=\"" + endId
				+ "\" eway=\"" + endWay + "\"><![CDATA[" + text + "]]></shape>";
	}
}
