package org.alvin.bean.shape.data.usecase;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.ctrl.CtrlLineShape;
import org.alvin.bean.shape.ctrl.CtrlResizeShape;
import org.alvin.bean.shape.data.BaseDataShape;

public abstract class BaseUseCaseShape extends BaseDataShape {
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

	protected void createBox() {
		if (!this.ctrlable) {
			return;
		}
		resizeMap = new HashMap<String, CtrlResizeShape>();
		ctrlLineMap = new HashMap<String, CtrlLineShape>();
		List<String> list = ShapeHelper.getWay8();
		for (String str : list) {
			resizeMap.put(str, new CtrlResizeShape(this, str));
		}
		list = ShapeHelper.getWay4();
		for (String str : list) {
			ctrlLineMap.put(str, new CtrlLineShape(this, str));
		}
		endInit();
	}

	
	protected void endInit() {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().setEnable(true);
		}
	}
}
