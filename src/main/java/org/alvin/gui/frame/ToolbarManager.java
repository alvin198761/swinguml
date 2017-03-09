package org.alvin.gui.frame;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.alvin.gui.action.edit.CopyAction;
import org.alvin.gui.action.edit.CutAction;
import org.alvin.gui.action.edit.DeleteAction;
import org.alvin.gui.action.edit.PasteAction;
import org.alvin.gui.action.edit.RedoAction;
import org.alvin.gui.action.edit.SelectAllAction;
import org.alvin.gui.action.edit.UndoAction;
import org.alvin.gui.action.file.OpenAction;
import org.alvin.gui.action.file.SaveAction;
import org.alvin.gui.action.file.SaveAsAction;
import org.alvin.gui.action.shape.ResetScaleAction;
import org.alvin.gui.action.shape.ZoomInAction;
import org.alvin.gui.action.shape.ZoomOutAction;
import org.alvin.gui.action.toolbar.DefaultCursorAction;
import org.alvin.gui.action.toolbar.SimpleLineAction;
import org.alvin.gui.comp.ScaleCombbox;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;

public class ToolbarManager {
	private ToolbarManager() {
	}

	private static JToolBar createFileToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(ActionManager.getAction(OpenAction.class));
		toolbar.add(ActionManager.getAction(SaveAction.class));
		toolbar.add(ActionManager.getAction(SaveAsAction.class));
		return toolbar;
	}

	private static Component createEditToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(ActionManager.getAction(UndoAction.class));
		toolbar.add(ActionManager.getAction(RedoAction.class));
		toolbar.addSeparator();
		toolbar.add(ActionManager.getAction(CopyAction.class));
		toolbar.add(ActionManager.getAction(CutAction.class));
		toolbar.add(ActionManager.getAction(PasteAction.class));
		toolbar.addSeparator();
		toolbar.add(ActionManager.getAction(DeleteAction.class));
		toolbar.add(ActionManager.getAction(SelectAllAction.class));
		return toolbar;
	}

	private static Component createScaleToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(ActionManager.getAction(ZoomInAction.class));
		toolbar.add(ActionManager.getAction(ZoomOutAction.class));
		toolbar.add(ActionManager.getAction(ResetScaleAction.class));
		toolbar.addSeparator();
		toolbar.add(new ScaleCombbox());
		return toolbar;
	}

	private static Component createOperToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(ActionManager.getAction(DefaultCursorAction.class));
		toolbar.add(ActionManager.getAction(SimpleLineAction.class));
		return toolbar;
	}

	public static void createToolbar() {
		JPanel hbox = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		layout.setHgap(5);
		hbox.setLayout(layout);
		hbox.add(createFileToolbar());
		hbox.add(createOperToolbar());
		hbox.add(createEditToolbar());
		hbox.add(createScaleToolbar());
		Application.toolbarBox = hbox;
	}
}
