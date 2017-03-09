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

public class ResetScaleAction extends BaseEditAction {

	private static final long serialVersionUID = 1L;

	public ResetScaleAction() {
		super(ResourceUtil.zoomReset_icon, Language.getValue("Reset"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
	}

	
	public void actionPerformed(ActionEvent e) {
		Application.drawPanel.setScale(1);
		Application.drawPanel.reDraw();
		Application.drawPanel.updateUI();
		firePropertyChange();
		ActionManager.getAction(ZoomInAction.class).firePropertyChange();
		ActionManager.getAction(ZoomOutAction.class).firePropertyChange();
	}

	
	public boolean isEnabled() {
		return Application.drawPanel.getScale() != 1;
	}

}
