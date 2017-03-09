package org.alvin.gui.action.edit;

import javax.swing.Icon;

import org.alvin.gui.action.BaseAction;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;

public abstract class BaseEditAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	public BaseEditAction(String text) {
		super(text);
		ActionManager.addEditList(this);
	}

	public BaseEditAction(Icon icon, String text) {
		super(icon, text);
		ActionManager.addEditList(this);
	}

	protected void canSave() {
		Application.saveed = false;
		ActionManager.firePropertyChangeEditAction();
	}
}
