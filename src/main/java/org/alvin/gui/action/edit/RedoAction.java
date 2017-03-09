package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.SMMSystem;

public class RedoAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public RedoAction() {
		super(ResourceUtil.redo_icon, "Redo");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Y"));
	}

	public void actionPerformed(ActionEvent e) {
		SMMSystem.undoManager.redo();
		canSave();
	}

	public boolean isEnabled() {
		return SMMSystem.undoManager.canRedo();
	}

}
