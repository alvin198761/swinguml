package org.alvin.bean.shape.data.active;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * 甬道
 * 
 * @author 唐植超
 * 
 */
public class ActiveLaneShape extends ActiveInterfaceShape {

	private static final long serialVersionUID = 1L;

	public ActiveLaneShape() {
		this.type = ShapeHelper.ACTIVE_LANE;
		setH(ShapeHelper.SUBSYSTEM_HEIGHT);
		setW(200);
		setY(0);
		text = Language.getValue("SubSystem");
		conectable = false;
		subSystem = true;
	}

	
	public void setW(double w) {
		if (w < 20) {
			w = 20;
			return;
		}
		this.w = w;
	}

	
	public Rectangle2D getBounds2D() {
		return super.shape.getBounds2D();
	}

	
	protected void drawShape(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		g.setColor(borderColor);
		g.draw(rect);
		// 中间加一个线
		g.draw(new Line2D.Double(x, y + titleHeight, x + w, y + titleHeight));
		shape = new Rectangle2D.Double(x, y, w, titleHeight);
	}

	
	protected void drawText(Graphics2D g) {
		g.setColor(Color.black);
		Rectangle2D rect = this.getBounds2D();
		FontMetrics fm = g.getFontMetrics();
		int fontW = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fontH = fm.getAscent() - 20;
		double x = rect.getCenterX() - (fontW >> 1);
		double y = rect.getCenterY() - (fontH >> 1);
		g.drawString(text, (float) x, (float) y);
	}

}
