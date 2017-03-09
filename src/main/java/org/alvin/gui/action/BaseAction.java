package org.alvin.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import org.alvin.sys.Language;

public abstract class BaseAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public BaseAction(String text) {
		text = createByText(text);
		putValue(Action.NAME, text);
		putValue(Action.SHORT_DESCRIPTION, text);
	}

	public BaseAction(Icon icon, String text) {
		this(text);
		putValue(Action.LARGE_ICON_KEY, icon);
		putValue(Action.SMALL_ICON, icon);
	}

	private String createByText(String text) {
		// 这里根据配置设置快捷键，目前没有实现
		return Language.getValue(text);
	}

	public boolean isEnabled() {
		return true;
	}

	public abstract void actionPerformed(ActionEvent e);

	public final void firePropertyChange() {
		firePropertyChange("enabled", true, false);
	}

}
