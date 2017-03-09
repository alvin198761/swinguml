package org.alvin.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.alvin.resource.ResourceUtil;
import org.alvin.sys.Language;
import org.alvin.util.LogUtil;

import com.sun.awt.AWTUtilities;

/**
 *
 */
public class StartWindow {
	private JFrame frame = new JFrame();
	private JProgressBar progressBar;

	public StartWindow() {
		frame.setIconImage(ResourceUtil.logo_img);
		frame.setTitle(Language.getValue("SMM"));
	}

	private void prepareSplash() {
		int imgWidth = ResourceUtil.bg_icon.getIconWidth();
		int imgHeight = ResourceUtil.bg_icon.getIconHeight();
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(.3f, .3f, .3f, .5f));
		progressBar.setBorder(null);
		// ȥ�������ϵı����
		frame.setUndecorated(true);
		// ����һ��͸�������
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(panel);
		// ����������Ϊ͸��
		AWTUtilities.setWindowOpaque(frame, false);
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.05f));
		JLabel l = new JLabel(ResourceUtil.bg_icon);
		l.setOpaque(false);
		panel.add(BorderLayout.CENTER, l);
		panel.add(BorderLayout.SOUTH, progressBar);
		panel.setPreferredSize(new Dimension(imgWidth, imgHeight));
		progressBar.setOpaque(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * ���ý��Ȱٷֱȣ����ֵ100
	 * 
	 * @param percent
	 */
	public void setValue(int percent) {
		if (percent < 0 || percent > 100) {
			throw new RuntimeException(Language.getValue("percent is invalid."));
		}
		progressBar.setValue(percent);
	}

	/**
	 * �������
	 */
	public void startSplash() {
		prepareSplash();
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}

	/**
	 * ����
	 */
	public void stopSplash() {
		frame.setAlwaysOnTop(false);
		frame.dispose();
	}

	public void putProcessString(String value) {
		progressBar.setString(value);
		LogUtil.info(value);
	}
}