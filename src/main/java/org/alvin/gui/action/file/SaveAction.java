package org.alvin.gui.action.file;

import static org.alvin.sys.Application.saveFile;
import static org.alvin.sys.Application.saveed;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.edit.BaseEditAction;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.SystemFileUtil;

public class SaveAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public SaveAction() {
		super(ResourceUtil.save_Icon, "Save");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
	}

	public void actionPerformed(ActionEvent e) {
		if (saveed)
			return;
		if (saveFile == null) {
			ActionManager.getAction(SaveAsAction.class).actionPerformed(e);
			return;
		}
		SystemFileUtil.saveFile(Application.shapes, saveFile);
		saveed = true;
		SMMSystem.chanageFrameTitle();
		ActionManager.firePropertyChangeEditAction();
	}

	public boolean isEnabled() {
		return !saveed;
	}

}
