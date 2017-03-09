package org.alvin.gui.drawpane;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataHandler;

import org.alvin.bean.shape.BaseShape;
import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.sys.Application;
import org.alvin.sys.SMMSystem;
import org.alvin.util.LogUtil;
import org.alvin.util.ObjectUtil;

public class DrawPaneClipboard {

	/**
	 * 画图板内置粘贴板
	 */
	private Clipboard board = new Clipboard("DrawPanel");
	/**
	 * 剪贴板支持的数据类型
	 */
	public static DataFlavor SHAPE_LIST_FLAVOR;

	static {
		try {
			SHAPE_LIST_FLAVOR = new DataFlavor(
					"application/x-java-serialized-object; class = java.util.LinkedList");
		} catch (Exception e) {
			System.out.print("粘贴板载入失败!");
		}
	}

	public DrawPaneClipboard() {
	}

	public void copy() {
		DataHandler data = new DataHandler(copyBaseDataShapeList(
				Application.getAllCopyShape(), 0),
				"application/x-java-serialized-object; class = java.util.ArrayList");
		board.setContents(data, null);
	}

	/**
	 * 深度拷贝
	 * 
	 * @param list
	 * @return
	 */
	private List<BaseDataShape> copyBaseDataShapeList(List<BaseDataShape> list,
			int trans) {
		List<BaseDataShape> result = new LinkedList<BaseDataShape>();
		for (BaseDataShape shape : list) {
			BaseDataShape copyShape;
			try {
				copyShape = (BaseDataShape) ObjectUtil.cloneObject(shape);
				copyShape.setX(shape.getX() + trans);
				result.add(copyShape);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public void paste() {
		Transferable value = board.getContents(SHAPE_LIST_FLAVOR);
		if (value == null) {
			return;
		}
		try {
			if (!(value.getTransferData(SHAPE_LIST_FLAVOR) instanceof LinkedList)) {
				return;
			}
			// 获取数据
			List<BaseDataShape> list = (LinkedList<BaseDataShape>) value
					.getTransferData(SHAPE_LIST_FLAVOR);
			list = (LinkedList<BaseDataShape>) copyBaseDataShapeList(list, 15);
			// undoredo
			SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(Application.drawPanel));
			// 删除原来是图形
			for (BaseShape shape : Application.shapes) {
				shape.setSelect(false);
			}
			// 添加新的 图形
			Application.clearSelect();
			Application.drawPanel.reDraw();
			Application.addDraw(list);
			copy();
		} catch (Exception e1) {
			e1.printStackTrace();
			LogUtil.info("粘贴板数据读取失败!");
		}
	}

	public void cut() {
		copy();
		SMMSystem.undoManager.post(new DrawPaneUndoRedoEdit(Application.drawPanel));
		Application.removeSelectShape();
		Application.repaint();
	}

	public boolean canCopy() {
		for (BaseDataShape shape : Application.shapes) {
			if (shape.canCopy()) {
				return true;
			}
		}
		return false;
	}

	public boolean canPaste() {
		Transferable value = board.getContents(SHAPE_LIST_FLAVOR);
		if (value != null) {
			try {
				return (value.getTransferData(SHAPE_LIST_FLAVOR) instanceof LinkedList);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public boolean canCut() {
		return canCopy();
	}

}
