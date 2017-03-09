package org.alvin.gui.frame;

import javax.swing.Box;

import org.alvin.sys.Application;

public class StatusbarManager {

	private StatusbarManager() {
	}

	public static void createStatusBar() {
		Box box = Box.createHorizontalBox();
		Application.statusBox = box;
	}

}
