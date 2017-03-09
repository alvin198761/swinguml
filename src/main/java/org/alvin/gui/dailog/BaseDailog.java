package org.alvin.gui.dailog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.alvin.gui.action.BaseAction;
import org.alvin.resource.ResourceUtil;

public class BaseDailog extends JDialog {

	private static final long serialVersionUID = 1L;
	protected JPanel mainPanel;
	protected JPanel buttonPanel;
	protected AboutCloseAction closeAction = new AboutCloseAction();

	public BaseDailog() {
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		mainPanel = new JPanel();
		getContentPane().add(BorderLayout.CENTER, mainPanel);
		buttonPanel = new JPanel();
		getContentPane().add(BorderLayout.SOUTH, buttonPanel);
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.RIGHT);
		buttonPanel.setLayout(layout);
		setIconImage(ResourceUtil.logo_img);
	}

	class AboutCloseAction extends BaseAction {

		private static final long serialVersionUID = 1L;

		public AboutCloseAction() {
			super("OK");
		}

		
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}

}
