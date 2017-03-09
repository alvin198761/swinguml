package org.alvin.gui.action.help;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.BaseAction;
import org.alvin.gui.dailog.AboutDialog;

public class AboutAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AboutDialog dialog = new AboutDialog();

	public AboutAction() {
		super("About");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift B"));
	}

	public void actionPerformed(ActionEvent e) {
		dialog.setVisible(true);
	}

}
