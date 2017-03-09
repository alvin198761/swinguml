package org.alvin.gui.action.newfile;

import java.awt.event.KeyEvent;

import javax.swing.Action;

public class NewActiveShapeAction extends BaseNewFileMenuAction {
	private static final long serialVersionUID = 1L;

	public NewActiveShapeAction() {
		super("Activity Diagrams");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		type = "changeToActivity";
	}

}
