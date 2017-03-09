package org.alvin.bean.shape.data.seri;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * 生命线
 * 
 * @author 唐植超
 * 
 */
/**
 * @author Administrator
 * 
 */
public class SeriLifelineShape extends BaseSeriShape {

	private static final long serialVersionUID = 1L;
	private int titleHeight = 30;
	private Rectangle2D rect = new Rectangle2D.Double();
	private Line2D line = new Line2D.Double();
	private LinkedList<SeriActiveShape> activeList = new LinkedList<SeriActiveShape>();

	public SeriLifelineShape() {
		setH(ShapeHelper.SUBSYSTEM_HEIGHT);
		setW(200);
		setY(0);
		editable = true;
		conectable = false;
		text = Language.getValue("Object life");
		type = ShapeHelper.SERI_LIFE;
		subSystem = true;
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Rectangle2D.Double(x, y, w, titleHeight);
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(borderColor);
		g.draw(shape);
		Stroke s = g.getStroke();
		BasicStroke dotted = new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 0, new float[] { 5, 5, 5, 5 }, 0);
		g.setStroke(dotted);
		// 中间加一个线
		line.setLine(x + w / 2, y + titleHeight, x + w / 2, y + h);
		g.draw(line);
		g.setStroke(s);

		double x = line.getX1() - 2;
		double x2 = line.getX2() + 2;
		double y = line.getY1();
		double y2 = line.getY2();
		rect.setRect(x, y, x2 - x, y2 - y);
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

	
	public Rectangle2D getBounds2D() {
		return super.shape.getBounds2D();
	}

	public int getTitleHeight() {
		return titleHeight;
	}

	public void setTitleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
	}

	
	protected void endInit() {
		resizeMap.get(ShapeHelper.WAY_LEFT).setEnable(true);
		resizeMap.get(ShapeHelper.WAY_RIGHT).setEnable(true);
	}

	
	public void setW(double w) {
		if (w < 20) {
			w = 20;
			return;
		}
		this.w = w;
	}

	
	public boolean contains(Point2D p) {
		return super.contains(p) || rect.contains(p);
	}

	public void addActiveShape(SeriActiveShape shape) {
		if (shape.getLifeShape() != null) {
			return;
		}
		if (this.equals(shape.getLifeShape())) {
			return;
		}
		if (this.hasShape(shape)) {
			// 判断是否能够添加到
			shape.setLifeShape(this);
			this.activeList.add(shape);
		}
	}

	private boolean hasShape(SeriActiveShape shape) {
		return rect.intersects(shape.getBounds2D());
	}

	public List<SeriActiveShape> getShapes() {
		return this.activeList;
	}

	public void contrlLines() {
		super.contrlLines();
		double y = titleHeight;
		for (SeriActiveShape active : this.activeList) {
			// 改变父类图形位置
			y += 5;
			active.setX(line.getX1() - active.getW() / 2);
			active.setY(y);
			y += active.getH();
		}
	}

}
