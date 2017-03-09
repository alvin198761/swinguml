package org.alvin.bean.shape.data.active;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Map.Entry;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.sys.Language;

/**
 * ½áÊø
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class ActiveEndShape extends BaseActiveShape {

	private static final long serialVersionUID = 1L;
	public static final int R = 20;
	public static final int PADDING = 3;

	public ActiveEndShape() {
		type = ShapeHelper.ACTIVE_END;
		text = Language.getValue("End");
		w = R;
		h = R;
	}

	
	protected void endInit() {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().setEnable(false);
		}
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Ellipse2D.Double(x, y, R, R);
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(borderColor);
		g.draw(shape);
		g.setColor(bgColor);
		g.fill(new Ellipse2D.Double(x + PADDING, y + PADDING, R
				- (PADDING << 1), R - (PADDING << 1)));
	}

	
	public void setW(double w) {
		super.setW(R);
	}

	
	public void setH(double h) {
		super.setH(R);
	}

}
