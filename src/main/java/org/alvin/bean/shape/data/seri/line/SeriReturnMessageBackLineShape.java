package org.alvin.bean.shape.data.seri.line;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * ÏûÏ¢£¨·µ»Ø£©
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class SeriReturnMessageBackLineShape extends BaseCallLineShape {

	private static final long serialVersionUID = 1L;

	public SeriReturnMessageBackLineShape() {
		type = ShapeHelper.SERI_MESSAGE_CALL_RETURN;
		text = Language.getValue("SeriMsgCallReturn");
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
