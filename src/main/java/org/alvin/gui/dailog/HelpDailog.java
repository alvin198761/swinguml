package org.alvin.gui.dailog;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTextPane;

import org.alvin.sys.Application;
import org.alvin.sys.Language;

public class HelpDailog extends BaseDailog {

	private static final long serialVersionUID = 1L;

	public HelpDailog() {
		setSize(600, 400);
		setTitle(Language.getValue("Help"));
		mainPanel.setLayout(new BorderLayout(10, 0));
		JTextPane text = new JTextPane();
		text.setText(Language.getValue("Help me"));
		text.setEditable(false);
		mainPanel.add(text);
		setLocationRelativeTo(Application.mainFrame);
		buttonPanel.add(new JButton(closeAction));
	}
}
