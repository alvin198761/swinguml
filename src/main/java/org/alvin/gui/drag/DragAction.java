package org.alvin.gui.drag;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.alvin.gui.drawpane.DrawPane;
import org.alvin.sys.Application;

/**
 * ÍÏ×§¶¯×÷
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class DragAction implements MouseMotionListener, MouseListener {

	public DragAction(ShapeGhostGlassPane glassPane, DrawPane targetComp) {

	}

	public void mouseDragged(MouseEvent e) {
		Application.dragBiz.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		Application.dragBiz.mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		Application.dragBiz.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
		Application.dragBiz.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		Application.dragBiz.mouseExited(e);
	}

	public void mousePressed(MouseEvent e) {
		Application.dragBiz.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		Application.dragBiz.mouseReleased(e);
	}
}