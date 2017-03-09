package org.alvin.gui.action.newfile;

import static org.alvin.sys.Application.OPER_NONE;
import static org.alvin.sys.Application.operStatus;
import static org.alvin.sys.Application.saveFile;
import static org.alvin.sys.Application.saveed;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.alvin.gui.action.BaseAction;
import org.alvin.gui.action.file.SaveAction;
import org.alvin.gui.drawpane.DrawPaneUndoRedoEdit;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.DialogUtil;

public abstract class BaseNewFileMenuAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	protected String type;

	public BaseNewFileMenuAction(Icon icon, String text) {
		super(icon, text);
	}

	public BaseNewFileMenuAction(String text) {
		super(text);
	}

	public final void actionPerformed(ActionEvent e) {
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
		Application.removeAllShapes();
		Application.repaint();
		saveed = false;
		saveFile = null;
		operStatus = OPER_NONE;
		SMMSystem.undoManager.reset();
		SMMSystem.chanageFrameTitle();
		Application.drawPanel.setCursor(Cursor.getDefaultCursor());
		// 换内容
		try {
			Application.shapePanel.changeType(type);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		changeGui();
		ActionManager.firePropertyChangeAllAction();
		SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(
				Application.drawPanel));
	}

	private void changeGui() {
		// 出事将默认界面移除掉，换成splitpanel
		Application.contentPane.remove(Application.defaultPanel);
		Application.contentPane.add(Application.mainSplitPanel);
		Application.contentPane.updateUI();
	}

}
