package org.alvin.bean.shape.data.usecase;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * ÏµÍ³±ß½ç
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class UseCaseSysBorderShape extends BaseUseCaseShape {

	private static final long serialVersionUID = 1L;
	private static final int titleHeight = 25;

	public UseCaseSysBorderShape() {
		type = ShapeHelper.USECASE_SYS_BORDER;
		conectable = false;
		editable = true;
		this.w = 300;
		this.h = 300;
		text = Language.getValue("System");
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Rectangle2D.Double(x, y, w, h);
		g.setColor(borderColor);
		g.draw(shape);
	}

	
	public boolean contains(Point2D p) {
		return new Rectangle2D.Double(x, y, w, titleHeight).contains(p);
	}

	
	public boolean intersects(Rectangle2D rect) {
		return new Rectangle2D.Double(x, y, w, titleHeight).intersects(rect);
	}

	
	protected void drawText(Graphics2D g) {
		g.setColor(Color.black);
		Rectangle2D rect = new Rectangle2D.Double(x, y, w, titleHeight);
		FontMetrics fm = g.getFontMetrics();
		int fontW = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fontH = fm.getAscent() - 20;
		double x = rect.getCenterX() - (fontW >> 1);
		double y = rect.getCenterY() - (fontH >> 1);
		g.drawString(text, (float) x, (float) y);
	}
}
