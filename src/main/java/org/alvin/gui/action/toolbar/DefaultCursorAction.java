package org.alvin.gui.action.toolbar;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.gui.action.BaseAction;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Application;

public class DefaultCursorAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public DefaultCursorAction() {
		super(ResourceUtil.defaultCurs_icon, "Defult cursor");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
	}

	public void actionPerformed(ActionEvent e) {
		Application.operStatus = Application.OPER_NONE;
		Application.mainFrame.setCursor(Cursor.getDefaultCursor());
		for (BaseDataShape shape : Application.shapes)
			shape.setLineHover(false);
	}

	public boolean isEnabled() {
		return Application.saveType != null;
	}

}
