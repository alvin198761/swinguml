package org.alvin.bean.shape.data.active;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Map.Entry;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.sys.Language;

/**
 * ×´Ì¬
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class ActiveActionShape extends BaseActiveShape {

	private static final long serialVersionUID = 1L;

	public ActiveActionShape() {
		type = ShapeHelper.ACTIVE_ACTION;
		text = Language.getValue("Action");
		editable = true;
		select = false;
		this.w = 100;
		this.h = 50;
	}

	
	protected void drawShape(Graphics2D g) {
		shape = new Ellipse2D.Double(x, y, w, h);
		g.setColor(Color.white);
		g.fill(shape);
		g.setColor(borderColor);
		g.draw(shape);
	}

	
	protected void endInit() {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().setEnable(true);
		}
	}

}
