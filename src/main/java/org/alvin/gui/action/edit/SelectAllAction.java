package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;

public class SelectAllAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public SelectAllAction() {
		super(ResourceUtil.selectAll_icon, "Select All");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
	}

	public void actionPerformed(ActionEvent e) {
		Application.selectAllShape();
		ActionManager.getAction(CopyAction.class).firePropertyChange();
		ActionManager.getAction(CutAction.class).firePropertyChange();
		ActionManager.getAction(DeleteAction.class).firePropertyChange();
		Application.operStatus = Application.OPER_SELECTALL;
	}

	public boolean isEnabled() {
		return !Application.isSelectAll();
	}

}
