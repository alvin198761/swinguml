package org.alvin.biz.usecase;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.bean.shape.data.usecase.line.UseCaseLineShape;
import org.alvin.biz.BaseDrawPanelBiz;
import org.alvin.gui.drawpane.DrawPaneUndoRedoEdit;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.LogUtil;

/**
 * 用例的画图操作
 * 
 * @author 唐植超
 * 
 */
public class UseCaseDrawPanelBiz extends BaseDrawPanelBiz {

	
	protected void resizeShape(MouseEvent e) {
		Application.currentResizeShape.getCtrl().contrlLines();
	}

	
	protected void drawLineStart(MouseEvent e) {
		Application.clearSelect();
		BaseDataShape lineHoverShape;
		for (BaseDataShape shape : Application.shapes)
			shape.containsLineHover(mousePoint);

		lineHoverShape = getLineHoverShape();
		if (lineHoverShape == null) {
			ActionManager.firePropertyChangeEditAction();
			return;
		}
		LogUtil.info("确定锚点的位置");
		UseCaseLineShape line = new UseCaseLineShape();
		line.setSelect(true);
		line.setX(lineHoverShape.getBounds2D().getCenterX());
		line.setY(lineHoverShape.getBounds2D().getCenterY());
		line.setBeginShape(lineHoverShape);
		Application.anchor.setLocation(line.getX(), line.getY());
		Application.repaint();
		Application.addShape(line);
		Application.currentSelectShape = line;
	}

	
	protected void drawLineFinish(MouseEvent e) {
		if (Application.currentSelectShape == null)
			return;

		// 看看鼠标的位置有没有图形被选中
		BaseDataShape shape = getLineHoverShape();
		if (shape == null) {
			Application.shapes.remove(Application.currentSelectShape);
			Application.repaint();
			Application.currentSelectShape = null;
			return;
		} else {
			BaseLineShape line = (BaseLineShape) Application.currentSelectShape;
			line.setEndShape(shape);
			if (line.getEndShape() == null) {
				Application.shapes.remove(Application.currentSelectShape);
				Application.repaint();
				Application.currentSelectShape = null;
				return;
			}
			Application.repaint();
			// 撤销恢复
			SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(panel));
		}
	}

	
	protected void drawLineDrag(MouseEvent e) {
		Rectangle2D rect = panel.getImageRect();
		Point2D p = new Point2D.Double(e.getPoint().getX() - rect.getX(), e
				.getPoint().getY() - rect.getY());
		BaseLineShape line = (BaseLineShape) Application.currentSelectShape;
		if (line == null) {
			return;
		}
		line.setX2(e.getPoint().getX() - rect.getX());
		line.setY2(e.getPoint().getY() - rect.getY());
		for (BaseDataShape shape : Application.shapes) {
			shape.containsLineHover(p);
		}
	}

}
