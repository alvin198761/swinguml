package org.alvin.biz;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.sys.Application;

/**
 * 鼠标动作的处理类基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseBiz implements MouseListener, MouseWheelListener,
		MouseMotionListener, DropTargetListener {

	public void dragEnter(DropTargetDragEvent dtde) {

	}

	public void dragOver(DropTargetDragEvent dtde) {

	}

	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	public void dragExit(DropTargetEvent dte) {

	}

	public void drop(DropTargetDropEvent dtde) {

	}

	protected void aotuConnection(MouseEvent e) {
		Rectangle2D rect = Application.drawPanel.getImageRect();
		BaseLineShape line;
		line = (BaseLineShape) Application.currentResizeShape.getCtrl();
		if (line == null)
			return;
		// 放弃原来的链接图形
		line.setBeginShape(null);
		line.setEndShape(null);
		// 如果是连线 判断是不是拖入了某个图形中
		for (BaseDataShape shape : Application.shapes) {
			// 不能连线的不用判断
			if (!shape.isConectable())
				continue;

			// 图形不和控制点相交的就不需要判断
			if (!(shape.contains(new Point2D.Double(e.getPoint().x
					- rect.getX(), e.getPoint().y - rect.getY()))))
				continue;

			// 如果控制点和某个图形相交的话，判断控制的是线条的哪一端
			if (Application.currentResizeShape.getWay().equals(
					ShapeHelper.WAY_TOP)) {
				line.setBeginShape(shape);
				break;
			}
			if (Application.currentResizeShape.getWay().equals(
					ShapeHelper.WAY_BOTTOM)) {
				line.setEndShape(shape);
				break;
			}
		}
	}
}
