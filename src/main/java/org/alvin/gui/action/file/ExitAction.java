package org.alvin.gui.action.file;

import static org.alvin.sys.Application.saveed;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.BaseAction;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.DialogUtil;

public class ExitAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public ExitAction() {
		super("Exit");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
	}

	public void actionPerformed(ActionEvent e) {
		do {
			if (saveed)
				break;
			int res = DialogUtil.chooserConfigDialog("Save change",
					Application.mainFrame);
			if (res == DialogUtil.SELECT_YES)
				// 调用保存方法
				ActionManager.getAction(SaveAction.class).actionPerformed(e);
			else if (res == DialogUtil.SELECT_CANCEL)
				return;
		} while (false);
		// 询问是否要退出
		int res = DialogUtil.confirmDialog("Are you sure exit",
				Application.mainFrame);
		if (res != DialogUtil.SELECT_YES)
			return;
		// 调用保存方法
		SMMSystem.saveConfig();
		System.exit(0);
	}

}
