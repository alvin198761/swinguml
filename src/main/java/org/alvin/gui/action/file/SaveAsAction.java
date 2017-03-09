package org.alvin.gui.action.file;

import static org.alvin.sys.Application.saveFile;
import static org.alvin.sys.Application.saveed;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.edit.BaseEditAction;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.Language;
import org.alvin.sys.SMMSystem;
import org.alvin.util.DialogUtil;
import org.alvin.util.SystemFileUtil;

public class SaveAsAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public SaveAsAction() {
		super(ResourceUtil.saveAs_Icon, "Save As");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift A"));
	}

	public void actionPerformed(ActionEvent e) {
		File f = SMMSystem.saveFileChooser(Application.mainFrame);
		if (f == null) {
			saveed = true;
			return;
		}
		if (!SystemFileUtil.saveFile(Application.shapes, f)) {
			DialogUtil.promptMessage(Language.getValue("Save fail"));
			saveed = false;
			return;
		}
		saveFile = f;
		saveed = true;
		SMMSystem.closeFileChooser();
		SMMSystem.chanageFrameTitle();
		ActionManager.firePropertyChangeEditAction();
	}

	public boolean isEnabled() {
		return Application.saveType != null;
	}
}
