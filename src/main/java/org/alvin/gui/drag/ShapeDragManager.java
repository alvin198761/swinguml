package org.alvin.gui.drag;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.alvin.gui.drawpane.DrawPane;

/**
 * ʵ��ͼ����ק����
 * 
 * �ο�swing hacks
 * 
 * @author ��ֲ��
 * 
 */
public class ShapeDragManager {
	private JFrame frame;
	private DrawPane target;
	private ShapeGhostGlassPane glass = null;
	private Component dragSource;
	private Map<Component, DragAction> dragMap = new HashMap<Component, DragAction>();

	public void setDragSource(Component dragSource) {
		this.dragSource = dragSource;
	}

	/**
	 * @param frame
	 *            ����frameΪҪʵ����ק��frame��frame��һ��ֻ����һ��DragManage���� �� <br>
	 *            ����ж�����ͻᵼ�³�ͻ�� <br>
	 *            ����ô���Ҫʵ����ק���ܣ�������һ��DragManage����<br>
	 * 
	 * @param target
	 *            ��קĿ��
	 */
	public ShapeDragManager(JFrame frame, DrawPane target) {
		this.frame = frame;
		this.target = target;
		target.setLayout(null);
		this.frame.setGlassPane(this.glass = new ShapeGhostGlassPane());
		this.canDrag(target);
	}

	/**
	 * ָ������ק�����
	 * 
	 * 
	 * @param souce
	 *            ����ק�����
	 */
	public void canDrag(Component source) {
		DragAction action = null;
		action = new DragAction(glass, target);
		source.addMouseListener(action);
		source.addMouseMotionListener(action);
		dragSource = source;
		dragMap.put(source, action);
	}

	public void removeDrag(Component source) {
		DragAction action = dragMap.get(source);
		if (action != null) {
			source.removeMouseListener(action);
			source.removeMouseMotionListener(action);
			action = null;
			System.gc();
		}
	}

	/**
	 * ������קԴ����
	 * 
	 * @return
	 */
	public Component getDragSource() {
		return dragSource;
	}

	public ShapeGhostGlassPane getGlass() {
		return glass;
	}

}
