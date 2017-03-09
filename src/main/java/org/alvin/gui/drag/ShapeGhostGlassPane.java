package org.alvin.gui.drag;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.alvin.sys.Application;

/**
 * 用来画图形做跨组件的视觉效果的面板
 * 
 * @author 唐植超
 * 
 */
public class ShapeGhostGlassPane extends JPanel {

	private static final long serialVersionUID = 1L;

	public ShapeGhostGlassPane() {
		setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		Application.dragBiz.paintCompent(g);
	}

	public final void update(Graphics g) {
		this.paintComponent(g);
	}
}