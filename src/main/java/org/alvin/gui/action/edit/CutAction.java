package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.SMMSystem;

public class CutAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public CutAction() {
		super(ResourceUtil.cut_icon, "Cut");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
	}

	public void actionPerformed(ActionEvent e) {
		SMMSystem.drawPaneClipBoard.cut();
		canSave();
	}

	public boolean isEnabled() {
		return SMMSystem.drawPaneClipBoard.canCut();
	}

}
