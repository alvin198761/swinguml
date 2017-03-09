package org.alvin.bean.shape.data.active;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Map.Entry;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.sys.Language;

/**
 * ¿ªÊ¼
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class ActiveStartShape extends BaseActiveShape {

	private static final long serialVersionUID = 1L;
	public static final int R = 20;

	public ActiveStartShape() {
		type = ShapeHelper.ACTIVE_START;
		text = Language.getValue("Begin");
		editable = false;
		w = R;
		h = R;
	}

	
	protected void endInit() {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().setEnable(false);
		}
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Ellipse2D.Double(x, y, w, h);
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(borderColor);
		g.draw(shape);
		g.setColor(bgColor);
		g.fill(shape);
	}

	
	public void setW(double w) {
		super.setW(R);
	}

	
	public void setH(double h) {
		super.setH(R);
	}

}
