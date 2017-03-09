package org.alvin.gui.dailog;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Application;
import org.alvin.sys.Language;

/**
 * ¹ØÓÚ
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class AboutDialog extends BaseDailog {

	private static final long serialVersionUID = 1L;

	public AboutDialog() {
		setSize(400, 300);
		setTitle(Language.getValue("About"));
		mainPanel.setLayout(new BorderLayout(10, 0));
		mainPanel.add(BorderLayout.WEST, new JLabel(new ImageIcon(
				ResourceUtil.myface_img)));
		JTextPane text = new JTextPane();
		text.setText(Language.getValue("About me"));
		text.setEditable(false);
		mainPanel.add(text);
		setLocationRelativeTo(Application.mainFrame);
		buttonPanel.add(new JButton(closeAction));
	}

}
