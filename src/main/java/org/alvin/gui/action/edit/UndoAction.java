package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.SMMSystem;

public class UndoAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public UndoAction() {
		super(ResourceUtil.undo_icon, "Undo");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
	}

	public void actionPerformed(ActionEvent e) {
		SMMSystem.undoManager.undo();
		canSave();
	}

	public boolean isEnabled() {
		return SMMSystem.undoManager.canUndo();
	}

}
