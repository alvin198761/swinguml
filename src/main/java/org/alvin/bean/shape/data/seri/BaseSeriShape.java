package org.alvin.bean.shape.data.seri;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.List;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlLineShape;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.bean.shape.data.BaseDataShape;

/**
 * 序列图的基类
 * 
 * @author 唐植超
 * 
 */
public abstract class BaseSeriShape extends BaseDataShape {

	private static final long serialVersionUID = 1L;

	
	protected void drawSelectBox(Graphics2D g) {
		Stroke s = g.getStroke();
		BasicStroke bs = new BasicStroke(.8f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 0, new float[] { 6, 4, 6, 4 }, 0);
		g.setColor(Color.red);
		g.setStroke(bs);
		g.draw(getBounds2D());
		g.setStroke(s);
	}

	protected void drawCtrl(Graphics2D g) {
		if (!conectable) {
			return;
		}
		super.drawCtrl(g);
	}

	protected void init() {
		return;
	}

	protected void createBox() {
		resizeMap = new HashMap<String, CtrlResizeShape>();
		ctrlLineMap = new HashMap<String, CtrlLineShape>();
		List<String> list = ShapeHelper.getWay8();
		for (String str : list) {
			resizeMap.put(str, new CtrlResizeShape(this, str));
			resizeMap.get(str).setEnable(false);
		}
		list = ShapeHelper.getWay4();
		for (String str : list) {
			ctrlLineMap.put(str, new CtrlLineShape(this, str));
		}
		endInit();
	}

	protected void endInit() {
		return;
	}

}
