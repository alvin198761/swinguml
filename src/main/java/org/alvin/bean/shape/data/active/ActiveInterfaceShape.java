package org.alvin.bean.shape.data.active;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Map.Entry;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.sys.Language;

/**
 * 接口
 * 
 * @author 唐植超
 * 
 */
public class ActiveInterfaceShape extends BaseActiveShape {
	private static final int defaultWidth = 100;
	private static final long serialVersionUID = 1L;
	protected int titleHeight = 30;

	public int getTitleHeight() {
		return titleHeight;
	}

	public void setTitleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
	}

	public ActiveInterfaceShape() {
		this.type = ShapeHelper.ACTIVE_INTEGERFACE;
		this.setH(60);
		text = Language.getValue("Interface");
		editable = true;
		this.w = defaultWidth;
	}

	
	protected void drawShape(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		g.setColor(Color.white);
		g.fill(rect);
		g.setColor(borderColor);
		g.draw(rect);
		// 中间加一个线
		g.draw(new Line2D.Double(x, y + titleHeight, x + w, y + titleHeight));
		shape = new Rectangle2D.Double(x, y, w, titleHeight);
	}

	
	protected void drawText(Graphics2D g) {
		g.setColor(Color.black);
		Rectangle2D rect = this.shape.getBounds2D();
		FontMetrics fm = g.getFontMetrics();
		int fontW = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fontH = fm.getAscent() - 20;
		double x = rect.getCenterX() - (fontW >> 1);
		double y = rect.getCenterY() - (fontH >> 1);
		g.drawString(text, (float) x, (float) y);
	}

	
	protected void endInit() {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().setEnable(false);
		}
		resizeMap.get(ShapeHelper.WAY_LEFT).setEnable(true);
		resizeMap.get(ShapeHelper.WAY_RIGHT).setEnable(true);
	}

	
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(x, y, w, 60);
	}

	public void containsLineHover(Point p) {
		setLineHover(this.getBounds2D().contains(p));
	}

}
