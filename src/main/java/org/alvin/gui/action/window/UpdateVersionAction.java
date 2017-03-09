package org.alvin.gui.action.window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.BaseAction;

public class UpdateVersionAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public UpdateVersionAction() {
		super("Update Version");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
	}

	public void actionPerformed(ActionEvent e) {
	}

	public boolean isEnabled() {
		return false;
	}

}
