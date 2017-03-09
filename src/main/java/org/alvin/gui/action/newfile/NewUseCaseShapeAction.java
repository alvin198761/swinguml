package org.alvin.gui.action.newfile;

import java.awt.event.KeyEvent;

import javax.swing.Action;

public class NewUseCaseShapeAction extends BaseNewFileMenuAction {

	private static final long serialVersionUID = 1L;

	public NewUseCaseShapeAction() {
		super("Usecase Diagrams");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		type = "changeToUsercase";
	}

}
