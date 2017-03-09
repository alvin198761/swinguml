package org.alvin.gui.dailog;

import javax.swing.JProgressBar;

/**
 * µÈ´ý¿ò
 * 
 * @author Administrator
 * 
 */
public class ProgressDialog extends BaseDailog {
	private static final long serialVersionUID = 1L;
	private JProgressBar progress = new JProgressBar();

	public ProgressDialog() {
		mainPanel.add(progress);
		progress.setMaximum(100);
		progress.setMinimum(0);
	}

	public void start(Runnable run) {
		new Thread(run).start();
	}

	public void setValue(int value) {
		progress.setValue(value);
	}

}
