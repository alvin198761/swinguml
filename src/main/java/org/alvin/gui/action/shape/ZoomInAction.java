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

public class ZoomInAction extends BaseEditAction {

	private static final long serialVersionUID = 1L;

	public ZoomInAction() {
		super(ResourceUtil.zoomIn_icon, Language.getValue("ZoomIn"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ADD);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl ADD"));
	}

	public void actionPerformed(ActionEvent e) {
		double sacle = Application.drawPanel.getScale();
		Application.drawPanel.setScale(sacle + .2);
		Application.drawPanel.reDraw();
		Application.drawPanel.updateUI();
		firePropertyChange();
		ActionManager.getAction(ZoomOutAction.class).firePropertyChange();
		ActionManager.getAction(ResetScaleAction.class).firePropertyChange();
	}

	public boolean isEnabled() {
		return Application.drawPanel.getScale() < 4
				&& Application.shapes.size() > 0;
	}

}
