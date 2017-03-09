package org.alvin.gui.comp;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.alvin.gui.drawpane.DrawPane;
import org.alvin.util.DialogUtil;

public class SMMFileChooser extends JFileChooser {

	private static final long serialVersionUID = 1L;

	// private static JScrollPane panel;
	// private static ImagePreview preview;

	// static {
	// preview = new ImagePreview(fileChooserDialog);
	// panel = new JScrollPane();
	// panel.setViewportView(preview);
	// panel.getHorizontalScrollBar().addAdjustmentListener(
	// new AdjustmentListener() {
	//
	//
	// public void adjustmentValueChanged(AdjustmentEvent e) {
	// preview.repaint();
	// }
	// });
	// panel.getVerticalScrollBar().addAdjustmentListener(
	// new AdjustmentListener() {
	//
	//
	// public void adjustmentValueChanged(AdjustmentEvent e) {
	// preview.repaint();
	// }
	// });
	// panel.setPreferredSize(new Dimension(300, 300));
	// }

	public SMMFileChooser() {
		setFileSelectionMode(FILES_ONLY);
		setMultiSelectionEnabled(false);
		setSelectedFile(null);
		updateUI();
	}

	static class ImagePreview extends DrawPane implements
			PropertyChangeListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		FileFilter filter = new FileNameExtensionFilter("VDK file", "vkd",
				"vsd");
		private JFileChooser jfc;

		public ImagePreview(SMMFileChooser fileChooserDialog) {
			this.setEnabled(false);
			this.jfc = fileChooserDialog;
			jfc.setFileFilter(filter);
			jfc.addPropertyChangeListener(this);
			// scale = 0.4;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			try {
				File file = jfc.getSelectedFile();
				updateImage(file);
			} catch (IOException ex) {
				System.out.println(ex.getMessage() + "文件已经处在打开状态");
			}
		}

		public void updateImage(File file) throws IOException {

			if (file == null) {
				// this.setAllShapes(new ArrayList<IShape>());
			} else {
				String path = file.getAbsolutePath();
				if (path.endsWith("vsd")) {
					// if (map.containsKey(path)) {
					// List<IShape> list = map.get(path);
					// this.setAllShapes(list);
					// } else {
					// List<IShape> list = printerBiz.analyseVisioFile(file);
					// this.setAllShapes(list);
					// map.put(file.getAbsolutePath(), list);
					// }
					// this.setAllShapes(new ArrayList<IShape>());
				} else if (path.endsWith("vkd")) {
					// List<IShape> list = printerBiz.loadVKDShapesImage(file);
					// this.setAllShapes(list);
				}
			}
			repaint();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// if (getAllShapes() == null || getAllShapes().size() <= 0) {
			// g.setColor(Color.black);
			// Rectangle visibleRect = getVisibleRect();
			// g.drawString("没有预览",
			// visibleRect.x + visibleRect.width / 2 - 20,
			// visibleRect.y + visibleRect.height / 2);
			// }
		}
	}

	public void approveSelection() {
		if (getDialogType() == SAVE_DIALOG) {
			File fi = getSelectedFile();
			if (fi == null) {
				super.approveSelection();
				return;
			}
			if (fi.exists()) {
				int reuslt = DialogUtil.confirmDialog("文件\"" + fi.getName()
						+ "\"已经存在!是否替换原文件?", this);
				if (reuslt != DialogUtil.SELECT_YES) {
					return;
				}
			}
		} else if (getDialogType() == OPEN_DIALOG) {
			File fi = getSelectedFile();
			if (fi == null || (fi != null && !fi.exists())) {
				DialogUtil.promptMessage("文件不存在!");
				return;
			}
		}
		super.approveSelection();
	}
}