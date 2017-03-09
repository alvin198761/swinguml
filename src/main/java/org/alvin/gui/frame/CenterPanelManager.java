package org.alvin.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.alvin.sys.Application;
import org.alvin.sys.Language;

/**
 * �м�Ĳ���ȥ������
 * 
 * @author ��ֲ��
 * 
 */
public class CenterPanelManager {

	private CenterPanelManager() {
	}

	public static void createContentPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		JPanel defaultPanel = new JPanel();
		defaultPanel.setBackground(Color.gray);

		JSplitPane mainSplitPanel = new JSplitPane();
		mainSplitPanel.setBorder(null);
		mainSplitPanel.setDividerLocation(200);
		JTabbedPane leftTabPane = new JTabbedPane();
		leftTabPane.setBorder(null);
		// ����ǿؼ���
		JScrollPane scrollPane = new JScrollPane(Application.shapePanel);
		scrollPane.setBorder(null);
		leftTabPane.addTab(Language.getValue("Shape"), scrollPane);
		mainSplitPanel.setLeftComponent(leftTabPane);
		// �м��ǻ�ͼ��
		scrollPane = new JScrollPane(Application.drawPanel);
		scrollPane.setBorder(null);
		mainSplitPanel.setRightComponent(scrollPane);
		// �ұ�û��
		// �����ǹ�����
		panel.add(Application.toolbarBox, BorderLayout.NORTH);
		// ������״̬��
		panel.add(Application.statusBox, BorderLayout.SOUTH);
		// �м��Ƿָ���
		panel.add(defaultPanel, BorderLayout.CENTER);
		Application.contentPane = panel;
		mainSplitPanel.setEnabled(false);
		mainSplitPanel.setOneTouchExpandable(true);
		Application.mainSplitPanel = mainSplitPanel;
		Application.defaultPanel = defaultPanel;
	}

}
