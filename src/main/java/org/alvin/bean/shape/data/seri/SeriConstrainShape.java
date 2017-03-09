package org.alvin.bean.shape.data.seri;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * Ô¼Êø
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class SeriConstrainShape extends BaseSeriShape {

	private static final long serialVersionUID = 1L;

	public SeriConstrainShape() {
		text = Language.getValue("Constrain");
		type = ShapeHelper.SERI_CONSTRAIN;
		conectable = false;
		editable = true;
		setW(100);
		setH(60);
	}

	
	protected void drawShape(Graphics2D g) {
		Polygon p = new Polygon();
		p.addPoint((int) x, (int) y);
		p.addPoint((int) (x + w - 10), (int) y);
		p.addPoint((int) (x + w), (int) y + 10);
		p.addPoint((int) (x + w), (int) (y + h));
		p.addPoint((int) x, (int) (y + h));
		GeneralPath path = new GeneralPath(p);
		shape = path;
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(borderColor);
		g.draw(shape);
		g.draw(new Line2D.Double((x + w - 10), y, (x + w - 10), y + 10));
		g.draw(new Line2D.Double((x + w), y + 10, (x + w - 10), y + 10));
	}

}
