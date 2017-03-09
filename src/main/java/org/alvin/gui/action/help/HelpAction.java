package org.alvin.gui.action.help;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.BaseAction;
import org.alvin.gui.dailog.HelpDailog;
import org.alvin.resource.ResourceUtil;

public class HelpAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private HelpDailog helpDialog = new HelpDailog();

	public HelpAction() {
		super(ResourceUtil.help_icon, "Help");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
	}

	public void actionPerformed(ActionEvent e) {
		helpDialog.setVisible(true);
	}

}
