package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.SMMSystem;

public class PasteAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public PasteAction() {
		super(ResourceUtil.paste_icon, "Paste");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
	}

	public void actionPerformed(ActionEvent e) {
		SMMSystem.drawPaneClipBoard.paste();
		canSave();
	}

	public boolean isEnabled() {
		return SMMSystem.drawPaneClipBoard.canPaste();
	}

}
