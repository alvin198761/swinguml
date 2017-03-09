package org.alvin.bean.shape.data.usecase;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * 参与者 （火柴人）
 * 
 * @author 唐植超
 * 
 */
public class UseCaseActorShape extends BaseUseCaseShape {

	private static final long serialVersionUID = 1L;

	public UseCaseActorShape() {
		type = ShapeHelper.USECASE_ACTOR;
		this.w = 60;
		this.h = 100;
		editable = true;
		conectable = true;
		text = Language.getValue("Actor");
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Rectangle2D.Double(x, y, w, h);
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(Color.black);
		double r = h / 5;
		double tx;
		double ty;
		tx = (w - r) / 2;
		ty = y;
		// 头
		g.draw(new Ellipse2D.Double(tx + x, ty, r, r));
		// 手
		g.draw(new Line2D.Double(x, y + r + 5, x + w, y + r + 5));
		tx = x + w / 2;
		ty = r + r * 2 + y;
		// 身子
		g.draw(new Line2D.Double(tx, r + y, tx, ty));
		// 两只脚
		g.draw(new Line2D.Double(tx, ty, x, y + h));
		g.draw(new Line2D.Double(tx, ty, x + w, y + h));
	}

	
	public Point2D getPoisationByWay(String way) {
		Point2D p = super.getPoisationByWay(way);
		if (ShapeHelper.WAY_LEFT.equals(way)
				|| ShapeHelper.WAY_RIGHT.equals(way)) {
			double r = h / 5;
			p.setLocation(p.getX(), r + y + 5 - ShapeHelper.CTRL_SHAPE_SIZE / 2);
		}
		return p;
	}

	
	protected void drawText(Graphics2D g) {
		g.setColor(Color.black);
		Rectangle2D rect = new Rectangle2D.Double(x, y + h, w, 25);
		FontMetrics fm = g.getFontMetrics();
		int fontW = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fontH = fm.getAscent() - 20;
		double x = rect.getCenterX() - (fontW >> 1);
		double y = rect.getCenterY() - (fontH >> 1);
		g.drawString(text, (float) x, (float) y);
	}
}
