package org.alvin.gui.action.shape;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.alvin.gui.action.edit.BaseEditAction;
import org.alvin.resource.ResourceUtil;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.Language;
import org.alvin.util.DialogUtil;

public class PropertyAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public PropertyAction() {
		super(ResourceUtil.property_icon, "Properties");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
	}

	public void actionPerformed(ActionEvent e) {
		String text = "";
		text = DialogUtil.inputDialog(Language.getValue("Text"), null,
				Application.currentSelectShape.getText());
		if (text == null)
			return;
		Application.currentSelectShape.setText(text);
		Application.repaint();
		Application.saveed = false;
		ActionManager.firePropertyChangeEditAction();
	}

	public boolean isEnabled() {
		return Application.currentSelectShape != null
				&& Application.currentSelectShape.isEditable();
	}
}
