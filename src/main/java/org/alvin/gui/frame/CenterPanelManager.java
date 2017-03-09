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
 * 中间的操作去面板管理
 * 
 * @author 唐植超
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
		// 左边是控件栏
		JScrollPane scrollPane = new JScrollPane(Application.shapePanel);
		scrollPane.setBorder(null);
		leftTabPane.addTab(Language.getValue("Shape"), scrollPane);
		mainSplitPanel.setLeftComponent(leftTabPane);
		// 中间是画图区
		scrollPane = new JScrollPane(Application.drawPanel);
		scrollPane.setBorder(null);
		mainSplitPanel.setRightComponent(scrollPane);
		// 右边没有
		// 上面是工具条
		panel.add(Application.toolbarBox, BorderLayout.NORTH);
		// 下面是状态条
		panel.add(Application.statusBox, BorderLayout.SOUTH);
		// 中间是分割栏
		panel.add(defaultPanel, BorderLayout.CENTER);
		Application.contentPane = panel;
		mainSplitPanel.setEnabled(false);
		mainSplitPanel.setOneTouchExpandable(true);
		Application.mainSplitPanel = mainSplitPanel;
		Application.defaultPanel = defaultPanel;
	}

}
