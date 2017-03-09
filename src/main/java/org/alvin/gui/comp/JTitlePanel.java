package org.alvin.gui.comp;

import java.awt.BorderLayout;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.factory.ActiveShapeFactory;
import org.alvin.factory.SeriShapeFactory;
import org.alvin.factory.UseCaseShapeFactory;
import org.alvin.sys.Application;
import org.alvin.sys.Language;

/**
 * @author ÌÆÖ²³¬
 * 
 */
public class JTitlePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel titleButton;
	private JPanel contentPanel;
	private String type;

	public JTitlePanel() {
		titleButton = new JLabel();
		titleButton.setHorizontalAlignment(JLabel.CENTER);
		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		setLayout(new BorderLayout(0, 5));
		add(BorderLayout.NORTH, titleButton);
		add(contentPanel);
	}

	public void changeType(String type) throws Exception {
		if (type.equals(this.type))
			return;
		this.type = type;
		contentPanel.removeAll();
		System.gc();
		Class<?> clzz = this.getClass();
		Method me = clzz.getDeclaredMethod(type);
		me.invoke(this);
		updateUI();
		Application.saveType = type;
	}

	public void changeToActivity() {
		titleButton.setText(Language.getValue("Activity"));
		Application.shapeFactory = new ActiveShapeFactory();
		changeShapeButtons(ShapeHelper.getActiveTypeList());
	}

	public void changeToSeri() {
		titleButton.setText(Language.getValue("Seri"));
		Application.shapeFactory = new SeriShapeFactory();
		changeShapeButtons(ShapeHelper.getSeriTypeList());
	}

	public void changeToUsercase() {
		titleButton.setText(Language.getValue("UseCase"));
		Application.shapeFactory = new UseCaseShapeFactory();
		changeShapeButtons(ShapeHelper.getUseCaseTypeList());
	}

	private void changeShapeButtons(List<String> list) {
		int x = 0;
		int y = 0;
		int size = 80;
		for (int i = 0; i < list.size(); i++) {
			x = i % 2 * size + 10;
			if (i % 2 == 1)
				x += 5;
			ShapeButton button = new ShapeButton(
					Application.shapeFactory.createMenuShapeByType(list.get(i)));
			button.setBounds(x, y, size, size);

			contentPanel.add(button);
			if (i % 2 == 1)
				y += size + 5;
			Application.dragManager.canDrag(button);
		}
	}

}
