package org.alvin.biz;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.gui.action.BaseAction;
import org.alvin.gui.action.shape.PropertyAction;
import org.alvin.gui.action.shape.ZoomInAction;
import org.alvin.gui.action.shape.ZoomOutAction;
import org.alvin.gui.drawpane.DrawPane;
import org.alvin.gui.drawpane.DrawPaneUndoRedoEdit;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.LogUtil;

/**
 * ��ͼ����ĸ���
 * 
 * @author Administrator
 * 
 */
public abstract class BaseDrawPanelBiz extends BaseBiz {
	protected DrawPane panel = Application.drawPanel;
	protected Point2D startPoint = new Point2D.Double();
	protected long startTime;
	protected Point2D mousePoint = new Point2D.Double();

	public void mouseEntered(MouseEvent e) {
		panel.requestFocus();
	}

	public void mouseExited(MouseEvent e) {
		panel.requestFocus(false);
	}

	protected BaseDataShape getLineHoverShape() {
		for (BaseDataShape shape : Application.shapes) {
			if (shape.isLineHover()) {
				return shape;
			}
		}
		return null;
	}

	public void mouseMoved(MouseEvent e) {
		if (Application.operStatus.equals(Application.OPER_DRAWLINE)) {
			for (BaseDataShape shape : Application.shapes) {
				shape.containsLineHover(e.getPoint());
			}
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		// ������꣬�Ŵ���С
		if (!e.isControlDown()) {
			return;
		}
		double scale = this.panel.getScale();
		if (scale <= .5 && scale >= 2) {
			return;
		}
		int value = e.getWheelRotation();
		if (value == 1) {
			BaseAction action = ActionManager.getAction(ZoomInAction.class);
			if (action.isEnabled()) {
				action.actionPerformed(null);
			}
		}
		if (value == -1) {
			BaseAction action = ActionManager.getAction(ZoomOutAction.class);
			if (action.isEnabled()) {
				ActionManager.getAction(ZoomOutAction.class).actionPerformed(
						null);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && Application.currentSelectShape != null
				&& Application.currentSelectShape.isEditable()) {
			ActionManager.getAction(PropertyAction.class).actionPerformed(null);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (System.currentTimeMillis() - startTime < 100) {
			// �������»��ƵĴ�����
			return;
		}
		startTime = System.currentTimeMillis();
		do {
			Rectangle2D rect = panel.getImageRect();
			if (Application.operStatus.equals(Application.OPER_DRAWLINE)) {
				drawLineDrag(e);
				break;
			}
			if (Application.operStatus.equals(Application.OPER_RESIZE)) {
				// �ı�ؼ���С
				Point2D p = new Point2D.Double(e.getPoint().getX()
						- rect.getX(), e.getPoint().getY() - rect.getY());
				Application.currentResizeShape.contrl(p);
				// Log.info("�ı�ؼ���С");
				break;
			}
			if (Application.operStatus.equals(Application.OPER_DRAWSELECTBOX)) {
				double x = Math.min(startPoint.getX(), e.getPoint().getX())
						- rect.getX();
				double y = Math.min(startPoint.getY(), e.getPoint().getY())
						- rect.getY();
				double w = Math.abs(startPoint.getX() - e.getPoint().getX());
				double h = Math.abs(startPoint.getY() - e.getPoint().getY());
				Application.selectBox.setRect(x, y, w, h);
				break;
			}
		} while (false);
		Application.repaint();
	}

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			return;
		}
		Rectangle2D rect = panel.getImageRect();
		mousePoint.setLocation(e.getPoint().getX() - rect.getX(), e.getPoint()
				.getY() - rect.getY());
		startTime = System.currentTimeMillis();
		// ��û�е������ǰ���Ƶ���
		do {
			if (Application.currentResizeShape == null)
				break;
			if (!Application.currentResizeShape.contains(mousePoint)) {
				Application.currentResizeShape = null;
				break;
			}
			// �ı�ؼ���С��״̬
			Application.operStatus = Application.OPER_RESIZE;
			LogUtil.info("�ı��С");
			return;
		} while (false);
		// ��û�е������ǰ��ѡ�е�ͼ����
		do {
			if (Application.currentSelectShape == null)
				break;
			Application.currentResizeShape = Application.currentSelectShape
					.contansCtrlBox(mousePoint);
			if (Application.currentResizeShape != null) {
				Application.operStatus = Application.OPER_RESIZE;
				return;
			}
			if (!Application.currentSelectShape.contains(mousePoint)) {
				Application.currentSelectShape = null;
				break;
			}
			LogUtil.info("ѡ��ͼ��");
			Application.operStatus = Application.OPER_DRAGSHAPE;
			ActionManager.firePropertyChangeEditAction();
			return;
		} while (false);
		if (Application.operStatus == Application.OPER_DRAWLINE) {
			LogUtil.info("�����ʼ����");
			drawLineStart(e);
			return;
		}
		Application.operStatus = Application.OPER_NONE;
		// Log.info("����ѡ��");
		// ��û��ѡ�� ����������е�ͼ��ѡ��״̬
		LinkedList<BaseDataShape> list = Application.shapes;
		Application.clearSelect();
		BaseDataShape shape = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			shape = list.get(i);
			shape.setSelect(shape.contains(mousePoint));
			if (!shape.isSelect()) {
				continue;
			}
			Application.currentSelectShape = shape;
			Application.repaint();
			ActionManager.firePropertyChangeEditAction();
			return;
		}
		// һ����ûѡ�� ��ʼ��ѡ���
		startPoint.setLocation(e.getPoint().getX(), e.getPoint().getY());
		Application.operStatus = Application.OPER_DRAWSELECTBOX;
		ActionManager.firePropertyChangeEditAction();
	}

	public void drop(DropTargetDropEvent dtde) {
		LogUtil.info("�����ļ�");
		try {
			// �����ļ�������
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				List<?> list = (List<?>) (dtde.getTransferable()
						.getTransferData(DataFlavor.javaFileListFlavor));
				Iterator<?> iterator = list.iterator();
				while (iterator.hasNext()) {
					File f = (File) iterator.next();
					System.out.println(f.getAbsolutePath());
					// ���￪ʼ�����ļ�����ѡַ�������
				}
				dtde.dropComplete(true);
			} else {
				dtde.rejectDrop();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			ActionManager.popup.show(e.getComponent(), e.getX(), e.getY());
			return;
		}
		Application.anchor.setLocation(-1, -1);
		if (Application.operStatus.equals(Application.OPER_DRAWLINE)) {
			drawLineFinish(e);
			return;
		}
		if (Application.operStatus.equals(Application.OPER_RESIZE)) {
			resizeShape(e);
			Application.repaint();
			// �����ָ�
			SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(panel));
			return;
		}
		if (Application.operStatus.equals(Application.OPER_DRAWSELECTBOX)) {
			if (Application.selectBox.getWidth() > -1) {
				for (BaseDataShape shape : Application.shapes) {
					shape.setSelect(shape.intersects(Application.selectBox));
				}
			}
			Application.selectBox.setRect(-1, -1, -1, -1);
			Application.repaint();
			ActionManager.firePropertyChangeEditAction();
			return;
		}
	}

	/**
	 * �ı�ͼ�δ�С�Ĳ���
	 */
	protected abstract void resizeShape(MouseEvent e);

	/**
	 * ��ʼ����
	 */
	protected abstract void drawLineStart(MouseEvent e);

	/**
	 * ���߽���
	 */
	protected abstract void drawLineFinish(MouseEvent e);

	/**
	 * �϶�������
	 * 
	 * @param e
	 */
	protected abstract void drawLineDrag(MouseEvent e);
}
