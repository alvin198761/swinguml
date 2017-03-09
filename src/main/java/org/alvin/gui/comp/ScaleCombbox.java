package org.alvin.gui.comp;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.alvin.gui.action.shape.ResetScaleAction;
import org.alvin.gui.action.shape.ZoomInAction;
import org.alvin.gui.action.shape.ZoomOutAction;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;

public class ScaleCombbox extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ScaleCombbox() {
		setModel(new ScaleModel());
		Application.scaleCombox = this;
	}

	protected void selectedItemChanged() {
		String item = this.getSelectedItem().toString().replace('%', ' ')
				.trim();
		double scale = Double.parseDouble(item) / 100;
		Application.drawPanel.setScale(scale);
		Application.drawPanel.reDraw();
		Application.drawPanel.updateUI();

		ActionManager.getAction(ZoomInAction.class).firePropertyChange();
		ActionManager.getAction(ZoomOutAction.class).firePropertyChange();
		ActionManager.getAction(ResetScaleAction.class).firePropertyChange();
		firePropertyChange();
	}

	public boolean isEnabled() {
		return ActionManager.getAction(ZoomInAction.class).isEnabled()
				|| ActionManager.getAction(ZoomOutAction.class).isEnabled()
				|| ActionManager.getAction(ResetScaleAction.class).isEnabled();
	}

	public void firePropertyChange() {
		firePropertyChange("enabled", true, false);
	}
}

class ScaleModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;
	private static Object[] items = { "--none--", "50%", "75%", "100%", "150%",
			"200%" };

	public ScaleModel() {
		super(items);
	}

}
