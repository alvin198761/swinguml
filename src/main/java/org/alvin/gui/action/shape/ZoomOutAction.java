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

public class ZoomOutAction extends BaseEditAction {

	private static final long serialVersionUID = 1L;

	public ZoomOutAction() {
		super(ResourceUtil.zoomOut_icon, Language.getValue("ZoomOut"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_SUBTRACT);
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl SUBTRACT"));
	}

	public void actionPerformed(ActionEvent e) {
		double sacle = Application.drawPanel.getScale();
		Application.drawPanel.setScale(sacle - .1);
		Application.drawPanel.reDraw();
		Application.drawPanel.updateUI();
		firePropertyChange();
		ActionManager.getAction(ZoomInAction.class).firePropertyChange();
		ActionManager.getAction(ResetScaleAction.class).firePropertyChange();
	}

	public boolean isEnabled() {
		return Application.drawPanel.getScale() > .5
				&& Application.shapes.size() > 0;
	}

}
