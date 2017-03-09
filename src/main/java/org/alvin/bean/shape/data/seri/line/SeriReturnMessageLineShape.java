package org.alvin.bean.shape.data.seri.line;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * ÏûÏ¢ £¨·µ»Ø£©
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class SeriReturnMessageLineShape extends SeriMessageLineShape {

	private static final long serialVersionUID = 1L;

	public SeriReturnMessageLineShape() {
		type = ShapeHelper.SERI_MESSAGE_RETURN;
		text = Language.getValue("SeriMsgRETURN");
	}

	
	protected void drawLine(Graphics2D g) {
		BasicStroke dotted = new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 0, new float[] { 1, 5, 1, 5 }, 0);
		Stroke s = g.getStroke();
		g.setStroke(dotted);
		super.drawLine(g);
		g.setStroke(s);
	}

}
