package org.alvin.gui.action.toolbar;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.BaseAction;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Application;

public class SimpleLineAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public SimpleLineAction() {
		super(ResourceUtil.line_icon, "Line");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
	}

	public void actionPerformed(ActionEvent e) {
		Application.operStatus = Application.OPER_DRAWLINE;
		Application.mainFrame.setCursor(Cursor
				.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	public boolean isEnabled() {
		return Application.saveType != null;
	}
}
