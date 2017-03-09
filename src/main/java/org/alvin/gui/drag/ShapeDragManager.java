package org.alvin.gui.drag;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.alvin.gui.drawpane.DrawPane;

/**
 * 实现图形拖拽的类
 * 
 * 参考swing hacks
 * 
 * @author 唐植超
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
	 *            参数frame为要实现拖拽的frame，frame中一定只能有一个DragManage对象 ， <br>
	 *            如果有多个，就会导致冲突。 <br>
	 *            如果该窗口要实现拖拽功能，必须有一个DragManage对象，<br>
	 * 
	 * @param target
	 *            拖拽目标
	 */
	public ShapeDragManager(JFrame frame, DrawPane target) {
		this.frame = frame;
		this.target = target;
		target.setLayout(null);
		this.frame.setGlassPane(this.glass = new ShapeGhostGlassPane());
		this.canDrag(target);
	}

	/**
	 * 指定能拖拽的组件
	 * 
	 * 
	 * @param souce
	 *            被拖拽的组件
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
	 * 返回拖拽源对象
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
