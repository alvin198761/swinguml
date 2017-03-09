package org.alvin.biz;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.active.line.ActiveLineShape;
import org.alvin.gui.drag.ShapeGhostGlassPane;
import org.alvin.gui.drawpane.DrawPane;
import org.alvin.gui.frame.BaseDrawComp;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Application;

/**
 * �϶�����ĸ���
 * 
 * @author Administrator
 * 
 */
public abstract class BaseDragBiz extends BaseBiz {
	protected DrawPane targetComp;
	protected ShapeGhostGlassPane glassPane;
	protected AlphaComposite composite;
	protected BaseDataShape shape;
	protected boolean flag;
	protected long startTime;
	protected BaseDataShape dragged = null;
	protected Point location = new Point(0, 0);
	private int state;

	public static final int STATE_ACCEPT = 1;
	public static final int STATE_UNACCEPT = 2;
	public static final int STATE_NOMAR = -1;

	public BaseDragBiz() {
		glassPane = Application.dragManager.getGlass();
		targetComp = Application.drawPanel;
		// ��͸��Ч��
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		startTime = System.currentTimeMillis();
		flag = false;
		if (Application.OPER_DRAWLINE.equals(Application.operStatus))
			return;
		shapeMouseDown(e);
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		if (Application.OPER_DRAWLINE.equals(Application.operStatus)) {
			return;
		}
		if (this.shape == null) {
			return;
		}
		if (this.shape instanceof ActiveLineShape) {
			return;
		}
		if (System.currentTimeMillis() - startTime < 80) {
			// �������»��ƵĴ�����
			return;
		}
		startTime = System.currentTimeMillis();
		flag = true;
		Component c = e.getComponent();
		if (c.equals(targetComp)
				&& Application.OPER_RESIZE.equals(Application.operStatus)) {
			this.setImage(null);
			glassPane.repaint();
			return;
		}
		Point p = (Point) e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p, c);
		Point eventPoint = (Point) p.clone();
		SwingUtilities.convertPointFromScreen(p, glassPane);

		// ����Ƿ���Ŀ��ؼ����Ϸ�
		SwingUtilities.convertPointFromScreen(eventPoint, targetComp);
		int state = targetComp.getImageRect().contains(eventPoint) ? STATE_ACCEPT
				: STATE_UNACCEPT;
		setState(state);
		setPoint(p);
		glassPane.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if (Application.OPER_DRAWLINE.equals(Application.operStatus)) {
			return;
		}
		glassPane.setCursor(Cursor.getDefaultCursor());
		if (!flag) {
			setImage(null);
			glassPane.repaint();
			glassPane.setVisible(false);
			setImage(null);
			return;
		}
		Application.dragManager.setDragSource(null);
		System.gc();
		final Component c = e.getComponent();
		final Point p = (Point) e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p, c);
		final Point eventPoint = (Point) p.clone();
		final Rectangle2D rect = targetComp.getImageRect();
		SwingUtilities.convertPointFromScreen(p, glassPane);
		SwingUtilities.convertPointFromScreen(eventPoint, targetComp);
		setPoint(p);
		glassPane.repaint();
		/*
		 * �ƶ��ؼ���һ�¼������
		 * 
		 * 1���ؼ���ԭ����λ��֮�ϣ�û��
		 * 
		 * 2���ؼ���ԭ����λ���ƶ���Ŀ��ؼ���λ��
		 * 
		 * 3���ؼ��Ѿ���Ŀ���λ���ϣ�Ȼ���Զ���Ŀ���λ��
		 * 
		 * 4���ؼ���Ŀ���λ�����ƶ��������ؼ���
		 */
		// ��û�н���Ŀ�����
		if ((!c.equals(targetComp) && !targetComp.contains(eventPoint))
				|| (!c.equals(targetComp) && targetComp.contains(eventPoint))
				&& !targetComp.getImageRect().contains(eventPoint)) {
			new Thread(new Runnable() {

				
				public void run() {
					// û�н���
					setState(STATE_NOMAR);
					// �õ������ԭʼλ��
					Point cp = (Point) c.getLocationOnScreen().clone();
					// ת��glassλ��
					SwingUtilities.convertPointFromScreen(cp, glassPane);
					// ��ǰ�����glass�ϵ�λ��
					int x = cp.x;
					int y = cp.y;
					// �õ���ǰ��ʾ��ͼƬ��λ��
					int cx = p.x - (c.getWidth() / 2);
					int cy = p.y - (c.getHeight() / 2);

					int subX = (x - cx) / 10;
					int subY = (y - cy) / 10;

					x = p.x - (c.getWidth() / 2);
					y = p.y - (c.getHeight() / 2);

					for (int i = 0; i < 11; i++) {
						x += subX;
						y += subY;
						if (i == 10) {
							x = cp.x;
							y = cp.y;
						}
						cx = x + (c.getWidth() / 2);
						cy = y + (c.getHeight() / 2);
						setPoint(new Point(cx, cy));
						glassPane.repaint();
						try {
							Thread.sleep(20);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					glassPane.setVisible(false);
					setImage(null);
				}
			}).start();
			return;
		}
		// �ƶ���Ŀ���λ��
		if (!c.equals(targetComp)
				&& targetComp.getImageRect().contains(eventPoint)) {
			// �ֶ���һ�����
			// ������image��
			// ���Ƴ��µ����
			sourceToTarget(e, c, rect);
			Application.saveed = false;
			return;
		}
		// �ؼ��Ѿ���Ŀ���λ���ϣ�Ȼ���Զ���Ŀ���λ��
		if (c.equals(targetComp) && targetComp.contains(eventPoint)
				&& !Application.OPER_RESIZE.equals(Application.operStatus)) {
			targetToTarget(e, c, rect);
			Application.saveed = false;
			return;
		}
		// �ؼ���Ŀ���λ�����ƶ��������ؼ���
		if (c.equals(targetComp) && !targetComp.contains(eventPoint)) {
			// û�н���
			setState(STATE_NOMAR);
			// �����¼ͼ�ε�
			new Thread(new Runnable() {
				
				public void run() {
					BaseDataShape sourceShape = ((BaseDrawComp) c).getShape();
					if (sourceShape == null) {
						return;
					}
					// ��������Ļ�ϵ�λ��
					// shape��λ��
					Point cp = new Point((int) (sourceShape.getX()),
							(int) (sourceShape.getY()));
					// ת������Ļ
					SwingUtilities.convertPointToScreen(cp, c);
					// ת��glassλ��
					SwingUtilities.convertPointFromScreen(cp, glassPane);
					// �õ������λ��
					int x = cp.x;
					int y = cp.y;
					// �õ���ǰ����λ��
					SwingUtilities.convertPointToScreen(eventPoint, c);
					SwingUtilities
							.convertPointFromScreen(eventPoint, glassPane);
					int cx = eventPoint.x;
					int cy = eventPoint.y;

					int subX = (x - cx) / 10;
					int subY = (y - cy) / 10;

					x = eventPoint.x;
					y = eventPoint.y;

					for (int i = 0; i < 11; i++) {
						x += subX;
						y += subY;

						if (i == 10) {
							x = cp.x;
							y = cp.y;
						}

						cx = (int) (x + (shape.getW() / 2));
						cy = (int) (y + (shape.getH() / 2));
						setPoint(new Point(cx, cy));
						glassPane.repaint();
						try {
							Thread.sleep(20);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					glassPane.setVisible(false);
					setImage(null);
				}
			}).start();
			return;
		}
	}

	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * ��ԭ��������ϵ�Ŀ�����
	 * 
	 * @param e
	 * @param c
	 * @param rect
	 */
	protected abstract void sourceToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect);

	/**
	 * Ŀ������ϵ�Ŀ�����
	 * 
	 * @param e
	 * @param c
	 * @param rect
	 */
	protected abstract void targetToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect);

	/**
	 * ��갴�µĲ���
	 * 
	 * @param e
	 */
	protected abstract void shapeMouseDown(MouseEvent e);

	/**
	 * ��ͼ��ʵ��
	 * 
	 * @param g
	 */
	public void paintCompent(Graphics g) {
		if (dragged == null) {
			return;
		}
		paintShape(g);
		switch (state) {
		case STATE_ACCEPT:
			this.glassPane.setCursor(ResourceUtil.rightCursor);
			break;
		case STATE_UNACCEPT:
			this.glassPane.setCursor(ResourceUtil.wrongCursor);
			break;
		case STATE_NOMAR:
			this.glassPane.setCursor(Cursor.getDefaultCursor());
			break;
		}
	}

	/**
	 * ͼ�λ���
	 * 
	 * @param g
	 */
	protected abstract void paintShape(Graphics g);

	public void setState(int state) {
		this.state = state;
	}

	public void setImage(BaseDataShape dragged) {
		this.dragged = dragged;
	}

	public void setPoint(Point location) {
		this.location = location;
	}
}
