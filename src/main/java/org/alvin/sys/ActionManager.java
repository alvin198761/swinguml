package org.alvin.sys;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPopupMenu;

import org.alvin.gui.action.BaseAction;
import org.alvin.gui.action.edit.CopyAction;
import org.alvin.gui.action.edit.CutAction;
import org.alvin.gui.action.edit.DeleteAction;
import org.alvin.gui.action.edit.PasteAction;
import org.alvin.gui.action.edit.RedoAction;
import org.alvin.gui.action.edit.SelectAllAction;
import org.alvin.gui.action.edit.UndoAction;
import org.alvin.gui.action.shape.PropertyAction;
import org.alvin.gui.action.shape.ResetScaleAction;
import org.alvin.gui.action.shape.ZoomInAction;
import org.alvin.gui.action.shape.ZoomOutAction;

/**
 * 用户操作控制和管理
 * 
 * @author 唐植超
 * 
 */
public class ActionManager {

	private ActionManager() {

	}

	private static Map<Class<? extends BaseAction>, BaseAction> actionMap = new HashMap<Class<? extends BaseAction>, BaseAction>();
	private static List<BaseAction> editActionList = new LinkedList<BaseAction>();
	public static JPopupMenu popup = new JPopupMenu();

	public static BaseAction getAction(Class<? extends BaseAction> clazz) {
		BaseAction action = actionMap.get(clazz);
		if (action != null) {
			return action;
		}
		try {
			action = clazz.newInstance();
			actionMap.put(clazz, action);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return actionMap.get(clazz);
	}

	public static void firePropertyChangeAllAction() {
		for (Entry<Class<? extends BaseAction>, BaseAction> entry : actionMap
				.entrySet()) {
			entry.getValue().firePropertyChange();
		}
	}

	public static void firePropertyChangeEditAction() {
		for (BaseAction action : editActionList) {
			action.firePropertyChange();
		}
		Application.scaleCombox.firePropertyChange();
	}

	public static void addEditList(BaseAction action) {
		editActionList.add(action);
	}

	public static void fireUndoRedoActionPropertyChange() {
		ActionManager.getAction(UndoAction.class).firePropertyChange();
		ActionManager.getAction(RedoAction.class).firePropertyChange();
	}

	public static void createPopup() {
		popup.add(ActionManager.getAction(UndoAction.class));
		popup.add(ActionManager.getAction(RedoAction.class));
		popup.addSeparator();
		popup.add(ActionManager.getAction(CopyAction.class));
		popup.add(ActionManager.getAction(CutAction.class));
		popup.add(ActionManager.getAction(PasteAction.class));
		popup.addSeparator();
		popup.add(ActionManager.getAction(DeleteAction.class));
		popup.add(ActionManager.getAction(SelectAllAction.class));
		popup.addSeparator();
		popup.add(ActionManager.getAction(PropertyAction.class));
		popup.addSeparator();
		popup.add(ActionManager.getAction(ZoomInAction.class));
		popup.add(ActionManager.getAction(ZoomOutAction.class));
		popup.add(ActionManager.getAction(ResetScaleAction.class));
	}
}
