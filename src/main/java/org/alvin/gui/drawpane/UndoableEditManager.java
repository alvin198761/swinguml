package org.alvin.gui.drawpane;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import org.alvin.sys.ActionManager;

/**
 * undo管理
 * 
 * @author Administrator
 * 
 */
public class UndoableEditManager {
	/**
	 * Edit保存对象
	 */
	protected UndoableEditSupport undoSupport = new UndoableEditSupport();
	/**
	 * 撤销恢复管理器
	 */
	protected UndoManager undoManager = new UndoManager();

	public UndoableEditManager() {
		undoSupport.addUndoableEditListener(undoManager);
	}

	/**
	 * 删除所有Edit节点
	 */
	public void reset() {
		undoManager.discardAllEdits();
		ActionManager.fireUndoRedoActionPropertyChange();
	}

	/**
	 * 提交Undo
	 * 
	 * @param edit
	 */
	public void post(UndoableEdit edit) {
		undoSupport.postEdit(edit);
		ActionManager.fireUndoRedoActionPropertyChange();
	}

	public void undo() {
		undoManager.undo();
	}

	public void redo() {
		undoManager.redo();
	}

	public boolean canRedo() {
		return undoManager.canRedo();
	}

	public boolean canUndo() {
		return undoManager.canUndo();
	}
}