package org.alvin.biz.seri;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.bean.shape.data.seri.line.BaseSeriLineShape;
import org.alvin.bean.shape.data.seri.line.SeriMessageLineShape;
import org.alvin.biz.BaseDrawPanelBiz;
import org.alvin.sys.Application;
import org.alvin.util.LogUtil;

/**
 * 序列图的画图
 * 
 * @author Administrator
 * 
 */
public class SeriDrawPanelBiz extends BaseDrawPanelBiz {

	
	protected void resizeShape(MouseEvent e) {
		if (!(Application.currentResizeShape.getCtrl() instanceof BaseSeriLineShape)) {
			Application.currentResizeShape.getCtrl().contrlLines();
			return;
		}
		LogUtil.info("自动连线");
		aotuConnection(e);
		Application.repaint();
	}

	
	protected void drawLineStart(MouseEvent e) {
		Application.clearSelect();
		LogUtil.info("确定锚点的位置");
		SeriMessageLineShape line = new SeriMessageLineShape();
		line.setSelect(true);
		line.setText("");
		line.setX(mousePoint.getX());
		line.setY(mousePoint.getY());
		Application.anchor.setLocation(line.getX(), line.getY());
		Application.repaint();
		Application.addShape(line);
		Application.currentSelectShape = line;
	}

	
	protected void drawLineFinish(MouseEvent e) {
		drawLineDrag(e);
		BaseLineShape line = (BaseLineShape) Application.currentSelectShape;
		if (Math.abs(line.getX2() - line.getX()) < 2
				&& Math.abs(line.getY2() - line.getY()) < 2) {
			Application.shapes.remove(Application.currentSelectShape);
			Application.currentSelectShape = null;
			Application.repaint();
			return;
		}
		Application.repaint();
	}

	
	protected void drawLineDrag(MouseEvent e) {
		Rectangle2D rect = panel.getImageRect();
		if (Application.currentSelectShape == null)
			return;
		BaseLineShape line = (BaseLineShape) Application.currentSelectShape;
		line.setX2(e.getPoint().getX() - rect.getX());
		line.setY2(e.getPoint().getY() - rect.getY());
	}
}
