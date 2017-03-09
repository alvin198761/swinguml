package org.alvin.gui.action.window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.alvin.gui.action.BaseAction;

public class ShowStatusBarAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public ShowStatusBarAction() {
		super("Show Status");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
	}

	public void actionPerformed(ActionEvent e) {
	}

	public boolean isEnabled() {
		return false;
	}

}
