package org.alvin.biz.seri;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.seri.SeriActiveShape;
import org.alvin.bean.shape.data.seri.SeriLifelineShape;
import org.alvin.bean.shape.data.seri.line.BaseCallLineShape;
import org.alvin.bean.shape.data.seri.line.BaseSeriLineShape;
import org.alvin.bean.shape.data.seri.line.SeriMessageLineShape;
import org.alvin.biz.BaseDragBiz;
import org.alvin.gui.drawpane.DrawPaneUndoRedoEdit;
import org.alvin.gui.frame.BaseDrawComp;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.ObjectUtil;

/**
 *
 * @author Administrator
 * 
 */
public class SeriDragPanelBiz extends BaseDragBiz {

	protected void sourceToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect) {
		try {
			// ��ӵ�Ŀ��λ��
			BaseDataShape copyShape = Application.shapeFactory
					.createShapeFactory(shape.getType());
			Application.addShape(copyShape);
			Point p2 = (Point) e.getPoint().clone();
			SwingUtilities.convertPointToScreen(p2, c);
			SwingUtilities.convertPointFromScreen(p2, targetComp);

			if (copyShape instanceof BaseCallLineShape) {
				double h = 60;
				copyShape.setX(p2.x - rect.getX() - 20);
				((BaseCallLineShape) copyShape).setX2(copyShape.getX());
				copyShape.setY(p2.y - rect.getY() - h / 2);
				((BaseCallLineShape) copyShape).setY2(copyShape.getY() + h);

				copyShape.draw(Application.g2d);
				Application.repaint();
				glassPane.setVisible(false);
				setImage(null);
				// �����ָ�
				SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
				Application.saveed = false;
				return;
			} else if (copyShape instanceof SeriLifelineShape) {
				copyShape.draw(Application.g2d);
				for (BaseDataShape shape : Application.shapes) {
					if (!(shape instanceof SeriActiveShape)) {
						continue;
					}
					((SeriLifelineShape) copyShape)
							.addActiveShape((SeriActiveShape) shape);
				}
			} else if (copyShape instanceof SeriActiveShape) {
				copyShape.draw(Application.g2d);
				for (BaseDataShape shape : Application.shapes) {
					if (!(shape instanceof SeriLifelineShape)) {
						continue;
					}
					((SeriLifelineShape) shape)
							.addActiveShape((SeriActiveShape) copyShape);
					((SeriLifelineShape) shape).contrlLines();
				}
			} else if (copyShape instanceof SeriMessageLineShape) {
				copyShape.setX(p2.x - rect.getX() - 40);
				((SeriMessageLineShape) copyShape).setX2(copyShape.getX() + 80);
				copyShape.setY(p2.y - rect.getY());
				((SeriMessageLineShape) copyShape).setY2(copyShape.getY());

				copyShape.draw(Application.g2d);
				Application.repaint();
				glassPane.setVisible(false);
				setImage(null);
				// �����ָ�
				SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
				Application.saveed = false;
				return;
			}
			double x = p2.x - copyShape.getW() / 2 - rect.getX();
			double y = p2.y - copyShape.getH() / 2 - rect.getY();
			copyShape.setX(x);
			copyShape.setY(y);
			if (copyShape.isSubSystem()) {
				copyShape.setY(0);
			}
			glassPane.setVisible(false);
			setImage(null);
			Application.repaint();
			// �����ָ�
			SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
			Application.saveed = false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected void targetToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect) {
		Point p2 = (Point) e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p2, c);
		SwingUtilities.convertPointFromScreen(p2, targetComp);

		BaseDataShape sourceShape = ((BaseDrawComp) c).getShape();
		if (sourceShape == null)
			return;

		if (sourceShape instanceof SeriLifelineShape) {
			sourceShape.draw(Application.g2d);
			for (BaseDataShape shape : Application.shapes) {
				if (!(shape instanceof SeriActiveShape))
					continue;

				((SeriLifelineShape) sourceShape)
						.addActiveShape((SeriActiveShape) shape);
				shape.contrlLines();
			}
		} else if (sourceShape instanceof SeriActiveShape) {
			sourceShape.draw(Application.g2d);
			for (BaseDataShape shape : Application.shapes) {
				if (!(shape instanceof SeriLifelineShape))
					continue;

				((SeriLifelineShape) shape)
						.addActiveShape((SeriActiveShape) sourceShape);
				sourceShape.contrlLines();
			}
		} else if (sourceShape instanceof BaseCallLineShape) {
			BaseCallLineShape line = (BaseCallLineShape) sourceShape;
			double w = 40;
			double h = line.getY2() - line.getY();
			line.setX(p2.getX() - rect.getX() - w / 2);
			line.setX2(line.getX());
			line.setY(p2.getY() - rect.getY() - h / 2);
			line.setY2(line.getY() + h);

			sourceShape.draw(Application.g2d);
			Application.repaint();
			glassPane.setVisible(false);
			setImage(null);
			// �����ָ�
			SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
			Application.saveed = false;
			return;
		} else if (sourceShape instanceof SeriMessageLineShape) {
			SeriMessageLineShape line = (SeriMessageLineShape) sourceShape;
			double w;
			double h;
			w = line.getX2() - line.getX();
			h = line.getY2() - line.getY();
			sourceShape.setX(p2.x - rect.getX() - w / 2);
			((SeriMessageLineShape) sourceShape).setX2(sourceShape.getX() + w);
			sourceShape.setY(p2.y - rect.getY() - h / 2);
			((SeriMessageLineShape) sourceShape).setY2(sourceShape.getY() + h);

			sourceShape.draw(Application.g2d);
			Application.repaint();
			glassPane.setVisible(false);
			setImage(null);
			// �����ָ�
			SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
			Application.saveed = false;
			return;
		}
		double x = p2.x - shape.getW() / 2;
		double y = p2.y - shape.getH() / 2;
		shape.setX(x - rect.getX());
		shape.setY(y - rect.getY());
		if (shape.isSubSystem())
			shape.setY(0);
		sourceShape.setX(shape.getX());
		sourceShape.setY(shape.getY());
		sourceShape.draw(Application.g2d);
		sourceShape.contrlLines();
		Application.repaint();
		glassPane.setVisible(false);
		setImage(null);
		// �����ָ�
		SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
		Application.saveed = false;
	}

	
	protected void shapeMouseDown(MouseEvent e) {
		Component c = e.getComponent();
		BaseDataShape sourceShape = ((BaseDrawComp) c).getShape();
		if (sourceShape == null)
			return;

		try {
			this.shape = (BaseDataShape) ObjectUtil.cloneObject(sourceShape);
			shape.setSelect(false);
			shape.setText("");
		} catch (Exception e1) {
			e1.printStackTrace();
			this.shape = null;
			return;
		}
		// ��ͼ
		BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		c.paint(g);
		glassPane.setVisible(true);
		Point p = (Point) e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p, c);
		SwingUtilities.convertPointFromScreen(p, glassPane);

		setPoint(p);
		setImage(shape);
		glassPane.repaint();
	}

	
	protected void paintShape(Graphics g) {
		int y = 0;
		int x = (int) (location.getX() - (dragged.getW() / 2));
		if (dragged.isSubSystem()) {
			Point p = (Point) targetComp.getLocationOnScreen().clone();
			SwingUtilities.convertPointFromScreen(p, this.glassPane);
			Rectangle2D rect = Application.drawPanel.getImageRect();
			y = (int) p.getY() + 1 + (int) rect.getY();
		} else if (dragged instanceof BaseCallLineShape) {
			BaseCallLineShape line = (BaseCallLineShape) dragged;
			if (line.getX2() == 0) {
				line.setX(location.getX() - 20);
				line.setX2(line.getX());
				line.setY(location.getY() - 30);
				line.setY2(line.getY() + 60);
			} else {
				line.setX(location.getX() - 20);
				line.setX2(line.getX());
				double h = line.getY2() - line.getY();
				line.setY(location.getY() - h / 2);
				line.setY2(line.getY() + h);
			}
			// ���ɰ��ϻ�ͼ
			Graphics2D g2 = (Graphics2D) g;
			// ����� ����
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setComposite(composite);
			dragged.draw(g2);
			return;
		} else if (dragged instanceof BaseSeriLineShape) {
			BaseSeriLineShape line = (BaseSeriLineShape) dragged;
			if (line.getX2() == 0) {
				line.setX(location.getX() - 40);
				line.setX2(line.getX() + 80);
				line.setY(location.getY());
				line.setY2(line.getY());
			} else {
				double w = line.getX2() - line.getX();
				line.setX(location.getX() - w / 2);
				line.setX2(line.getX() + w);
				double h = line.getY2() - line.getY();
				line.setY(location.getY() - h / 2);
				line.setY2(line.getY() + h);
			}
			// ���ɰ��ϻ�ͼ
			Graphics2D g2 = (Graphics2D) g;
			// ����� ����
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setComposite(composite);
			dragged.draw(g2);
			return;
		} else {
			y = (int) (location.getY() - (dragged.getH() / 2));
		}
		dragged.setX(x);
		dragged.setY(y);
		// ���ɰ��ϻ�ͼ
		Graphics2D g2 = (Graphics2D) g;
		// ����� ����
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(composite);
		dragged.draw(g2);
	}
}
