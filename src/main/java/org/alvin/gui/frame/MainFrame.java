package org.alvin.gui.frame;

import javax.swing.JFrame;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Application;
import org.alvin.sys.Language;
import org.alvin.sys.SMMSystem;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 750);
		setLocationRelativeTo(null);
	}

	public void initGui() {
		setIconImage(ResourceUtil.logo_img);
		setTitle(Language.getValue("SMM") + "--" + SMMSystem.version);
		// ²Ëµ¥
		setJMenuBar(Application.menuBar);
		setContentPane(Application.contentPane);
	}

}
