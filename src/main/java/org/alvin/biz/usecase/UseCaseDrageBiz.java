package org.alvin.biz.usecase;

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
import org.alvin.bean.shape.data.usecase.UseCaseSysBorderShape;
import org.alvin.bean.shape.data.usecase.line.UseCaseLineShape;
import org.alvin.biz.BaseDragBiz;
import org.alvin.gui.drawpane.DrawPaneUndoRedoEdit;
import org.alvin.gui.frame.BaseDrawComp;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.ObjectUtil;

/**
 * ÓÃÀýµÄÍÏ¶¯²Ù×÷
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class UseCaseDrageBiz extends BaseDragBiz {

	protected void sourceToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect) {
		try {
			// Ìí¼Óµ½Ä¿±êÎ»ÖÃ
			BaseDataShape copyShape = Application.shapeFactory
					.createShapeFactory(shape.getType());
			Application.addShape(copyShape);
			Point p2 = (Point) e.getPoint().clone();
			SwingUtilities.convertPointToScreen(p2, c);
			SwingUtilities.convertPointFromScreen(p2, targetComp);
			double x = p2.x - copyShape.getW() / 2 - rect.getX();
			double y = p2.y - copyShape.getH() / 2 - rect.getY();
			copyShape.setX(x);
			copyShape.setY(y);
			if (copyShape instanceof UseCaseSysBorderShape)
				copyShape.setY(p2.y - 12.5 - rect.getY());

			glassPane.setVisible(false);
			setImage(null);
			Application.repaint();
			// ³·Ïú»Ö¸´
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
		double x = p2.x - shape.getW() / 2;
		double y = p2.y - shape.getH() / 2;
		shape.setX(x - rect.getX());
		shape.setY(y - rect.getY());
		if (shape.isSubSystem())
			shape.setY(0);

		BaseDataShape sourceShape = ((BaseDrawComp) c).getShape();
		if (sourceShape == null)
			return;

		sourceShape.setX(shape.getX());
		sourceShape.setY(shape.getY());
		if (sourceShape instanceof UseCaseSysBorderShape)
			sourceShape.setY(p2.y - 12.5 - rect.getY());

		sourceShape.draw(Application.g2d);
		sourceShape.contrlLines();
		Application.repaint();
		glassPane.setVisible(false);
		setImage(null);
		// ³·Ïú»Ö¸´
		SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(targetComp));
		Application.saveed = false;
	}

	
	protected void shapeMouseDown(MouseEvent e) {
		Component c = e.getComponent();
		BaseDataShape sourceShape = ((BaseDrawComp) c).getShape();
		if (sourceShape == null)
			return;

		if (sourceShape instanceof UseCaseLineShape)
			return;

		try {
			this.shape = (BaseDataShape) ObjectUtil.cloneObject(sourceShape);
			shape.setSelect(false);
		} catch (Exception e1) {
			e1.printStackTrace();
			this.shape = null;
			return;
		}
		// »­Í¼
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
		} else if (dragged instanceof UseCaseSysBorderShape) {
			y = (int) (location.getY() - 12.5);
		} else {
			y = (int) (location.getY() - (dragged.getH() / 2));
		}
		// ÔÚÃÉ°æÉÏ»­Í¼
		Graphics2D g2 = (Graphics2D) g;
		// ¿¹¾â³Ý ¶¶¶¯
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(composite);
		dragged.setX(x);
		dragged.setY(y);
		dragged.draw(g2);
	}

}
