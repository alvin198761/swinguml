package org.alvin.gui.drawpane;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.alvin.sys.Application;

public class DrawPaneHelper implements MouseListener, MouseMotionListener,
		MouseWheelListener, DropTargetListener {

	protected DrawPaneHelper(DrawPane panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
		new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, this);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		Application.drawPanelBiz.mouseWheelMoved(e);
	}

	public void mouseDragged(MouseEvent e) {
		Application.drawPanelBiz.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		Application.drawPanelBiz.mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		Application.drawPanelBiz.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		Application.drawPanelBiz.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		Application.drawPanelBiz.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {
		Application.drawPanelBiz.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		Application.drawPanelBiz.mouseExited(e);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		Application.drawPanelBiz.dragEnter(dtde);
	}

	public void dragOver(DropTargetDragEvent dtde) {
		Application.drawPanelBiz.dragOver(dtde);
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		Application.drawPanelBiz.dropActionChanged(dtde);
	}

	public void dragExit(DropTargetEvent dtde) {
		Application.drawPanelBiz.dragExit(dtde);
	}

	public void drop(DropTargetDropEvent dtde) {
		Application.drawPanelBiz.drop(dtde);
	}

}
