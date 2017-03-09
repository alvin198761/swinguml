package org.alvin.gui.action.window;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.alvin.gui.action.BaseAction;
import org.alvin.sys.Application;

public class LookAndFeelAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String lookAndFeel;

	public LookAndFeelAction(String text, String lookAndFeel) {
		super(text);
		this.lookAndFeel = lookAndFeel;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			UIManager.setLookAndFeel(lookAndFeel);
			SwingUtilities.updateComponentTreeUI(Application.mainFrame);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

}
