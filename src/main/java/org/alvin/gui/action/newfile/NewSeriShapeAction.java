package org.alvin.gui.action.newfile;

import java.awt.event.KeyEvent;

import javax.swing.Action;

public class NewSeriShapeAction extends BaseNewFileMenuAction {

	private static final long serialVersionUID = 1L;

	public NewSeriShapeAction() {
		super("Sequence Diagrams");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		type = "changeToSeri";
	}

}
