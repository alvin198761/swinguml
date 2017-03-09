package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.ActionManager;
import org.alvin.sys.SMMSystem;

public class CopyAction extends BaseEditAction {

	private static final long serialVersionUID = 1L;

	public CopyAction() {
		super(ResourceUtil.copy_icon, "Copy");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
	}

	public void actionPerformed(ActionEvent e) {
		SMMSystem.drawPaneClipBoard.copy();
		firePropertyChange();
		ActionManager.getAction(PasteAction.class).firePropertyChange();
	}

	public boolean isEnabled() {
		return SMMSystem.drawPaneClipBoard.canCopy();
	}

}
