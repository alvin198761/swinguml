package org.alvin.bean.shape.data.seri.line;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * ÏûÏ¢
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class SeriMessageLineShape extends BaseSeriLineShape {

	private static final long serialVersionUID = 1L;

	public SeriMessageLineShape() {
		type = ShapeHelper.SERI_MESSAGE;
		text = Language.getValue("SeriMsg");
	}

	
	protected void drawLine(Graphics2D g) {
		shape = new Line2D.Double(x, y, x2, y2);
		g.setColor(borderColor);
		g.draw(shape);
	}

	
}
