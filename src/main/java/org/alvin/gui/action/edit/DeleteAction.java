package org.alvin.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Application;

public class DeleteAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public DeleteAction() {
		super(ResourceUtil.del_icon, "Delete");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_DELETE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("DELETE"));
	}

	public void actionPerformed(ActionEvent e) {
		Application.removeSelectShape();
		canSave();
	}

	public boolean isEnabled() {
		for (BaseDataShape shape : Application.shapes) {
			if (shape.isSelect())
				return true;
		}
		return false;
	}

}
