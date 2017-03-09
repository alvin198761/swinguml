package org.alvin.gui.action.window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.BaseAction;

public class SettingAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public SettingAction() {
		super("Setting");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
	}

	public void actionPerformed(ActionEvent e) {
	}

	public boolean isEnabled() {
		return false;
	}

}
