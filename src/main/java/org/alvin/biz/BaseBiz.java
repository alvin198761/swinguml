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
 * ��궯���Ĵ��������
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
		// ����ԭ��������ͼ��
		line.setBeginShape(null);
		line.setEndShape(null);
		// ��������� �ж��ǲ���������ĳ��ͼ����
		for (BaseDataShape shape : Application.shapes) {
			// �������ߵĲ����ж�
			if (!shape.isConectable())
				continue;

			// ͼ�β��Ϳ��Ƶ��ཻ�ľͲ���Ҫ�ж�
			if (!(shape.contains(new Point2D.Double(e.getPoint().x
					- rect.getX(), e.getPoint().y - rect.getY()))))
				continue;

			// ������Ƶ��ĳ��ͼ���ཻ�Ļ����жϿ��Ƶ�����������һ��
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
