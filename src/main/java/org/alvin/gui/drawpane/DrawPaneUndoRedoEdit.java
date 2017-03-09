package org.alvin.gui.drawpane;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.undo.AbstractUndoableEdit;

import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.ObjectUtil;

/**
 * 画图板Edit,用于undo redo
 * 
 * @author 唐植超
 * 
 */
@SuppressWarnings("unchecked")
public class DrawPaneUndoRedoEdit extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;
	private static int index = 0;
	protected DrawPane panel;
	/**
	 * 当前数据
	 */
	protected String allShapes;
	/**
	 * 备份数据
	 */
	protected String backAllShapes;

	public DrawPaneUndoRedoEdit(DrawPane panel) {
		this.panel = panel;
		try {
			allShapes = writeShapes(Application.shapes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void undo() {
		super.undo();
		try {
			backAllShapes = writeShapes(Application.shapes);
			doExecute(readShapes(allShapes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void redo() {
		super.redo();
		doExecute(readShapes(backAllShapes));
	}

	private void doExecute(List<BaseDataShape> shapes) {
		Application.removeAllShapes();
		for (BaseDataShape shape : shapes) {
			Application.addShape(shape);
		}
		Application.repaint();
	}

	private String writeShapes(List<BaseDataShape> shapes) {
		String path = SMMSystem.tmp_dir.concat(index++ + "");
		File file = new File(path);
		try {
			ObjectUtil.writeObj(shapes, file);
			file.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	private List<BaseDataShape> readShapes(String path) {
		List<BaseDataShape> shapes = null;
		try {
			shapes = (List<BaseDataShape>) ObjectUtil.readObj(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			shapes = new LinkedList<BaseDataShape>();
		}
		return shapes;
	}
}
