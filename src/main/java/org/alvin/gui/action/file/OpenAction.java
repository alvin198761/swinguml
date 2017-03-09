package org.alvin.gui.action.file;

import static org.alvin.sys.Application.OPER_NONE;
import static org.alvin.sys.Application.operStatus;
import static org.alvin.sys.Application.saveFile;
import static org.alvin.sys.Application.saveed;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.gui.action.BaseAction;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.Language;
import org.alvin.sys.SMMSystem;
import org.alvin.util.DialogUtil;
import org.alvin.util.LogUtil;
import org.alvin.util.SystemFileUtil;

public class OpenAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public OpenAction() {
		super(ResourceUtil.open_icon, "Open");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
	}

	public void actionPerformed(ActionEvent e) {
		do {
			if (saveed)
				break;
			int res = DialogUtil.chooserConfigDialog(
					Language.getValue("Save change"), Application.mainFrame);
			if (res == DialogUtil.SELECT_YES)
				// 调用保存方法
				ActionManager.getAction(SaveAction.class).actionPerformed(e);
			else if (res == DialogUtil.SELECT_CANCEL)
				return;
		} while (false);
		File f = SMMSystem.openFileChooser(Application.mainFrame);
		if (f == null)
			return;
		if (!f.exists()) {
			DialogUtil.promptWarning(Language.getValue("File not found"));
			return;
		}
		try {
			// 出事将默认界面移除掉，换成splitpanel
			Application.contentPane.remove(Application.defaultPanel);
			Application.contentPane.add(Application.mainSplitPanel);
			Application.contentPane.updateUI();
			Application.removeAllShapes();
			SystemFileUtil.analyzerFile(f);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<BaseDataShape> list = Application.shapes;
		if (list == null) {
			DialogUtil.promptError(Language.getValue("Analyzer Error"));
			return;
		}
		if (list.size() == 0) {
			DialogUtil.promptWarning(Language.getValue("File Content Empty"));
			return;
		}
		saveFile = f;
		saveed = true;
		SMMSystem.closeFileChooser();
		LogUtil.info("解析完毕");
		operStatus = OPER_NONE;
		SMMSystem.undoManager.reset();
		Application.currentSelectShape = null;
		ActionManager.firePropertyChangeAllAction();
		Application.drawPanel.setCursor(Cursor.getDefaultCursor());
		SMMSystem.chanageFrameTitle();
	}
}
