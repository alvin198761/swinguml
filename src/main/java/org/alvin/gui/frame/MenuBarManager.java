package org.alvin.gui.frame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.alvin.gui.action.edit.CopyAction;
import org.alvin.gui.action.edit.CutAction;
import org.alvin.gui.action.edit.DeleteAction;
import org.alvin.gui.action.edit.PasteAction;
import org.alvin.gui.action.edit.RedoAction;
import org.alvin.gui.action.edit.SelectAllAction;
import org.alvin.gui.action.edit.UndoAction;
import org.alvin.gui.action.file.ExitAction;
import org.alvin.gui.action.file.OpenAction;
import org.alvin.gui.action.file.SaveAction;
import org.alvin.gui.action.file.SaveAsAction;
import org.alvin.gui.action.help.AboutAction;
import org.alvin.gui.action.help.HelpAction;
import org.alvin.gui.action.newfile.NewActiveShapeAction;
import org.alvin.gui.action.newfile.NewSeriShapeAction;
import org.alvin.gui.action.newfile.NewUseCaseShapeAction;
import org.alvin.gui.action.shape.PropertyAction;
import org.alvin.gui.action.shape.ResetScaleAction;
import org.alvin.gui.action.shape.ZoomInAction;
import org.alvin.gui.action.shape.ZoomOutAction;
import org.alvin.gui.action.window.LookAndFeelAction;
import org.alvin.gui.action.window.SettingAction;
import org.alvin.gui.action.window.ShowStatusBarAction;
import org.alvin.gui.action.window.UpdateVersionAction;
import org.alvin.sys.ActionManager;
import org.alvin.sys.Application;
import org.alvin.sys.Language;
import org.alvin.sys.SMMSystem;

public class MenuBarManager {
	private MenuBarManager() {
	}

	public static void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		menubar.add(createFileMenu());
		menubar.add(createEditMenu());
		menubar.add(createShapeMenu());
		menubar.add(createWindowMenu());
		menubar.add(createHelpMenu());
		Application.menuBar = menubar;
	}

	private static JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu(Language.getValue("Help"));
		helpMenu.setMnemonic('H');
		helpMenu.add(ActionManager.getAction(AboutAction.class));
		helpMenu.add(ActionManager.getAction(HelpAction.class));
		return helpMenu;
	}

	private static JMenu createEditMenu() {
		JMenu editMenu = new JMenu(Language.getValue("Edit"));
		editMenu.setMnemonic('E');
		editMenu.add(ActionManager.getAction(UndoAction.class));
		editMenu.add(ActionManager.getAction(RedoAction.class));
		editMenu.addSeparator();
		editMenu.add(ActionManager.getAction(CopyAction.class));
		editMenu.add(ActionManager.getAction(CutAction.class));
		editMenu.add(ActionManager.getAction(PasteAction.class));
		editMenu.addSeparator();
		editMenu.add(ActionManager.getAction(SelectAllAction.class));
		editMenu.add(ActionManager.getAction(DeleteAction.class));
		return editMenu;
	}

	private static JMenu createFileMenu() {
		JMenu fileMenu = new JMenu(Language.getValue("File"));
		fileMenu.setMnemonic('F');
		// ÐÂ½¨
		JMenu newFileMenu = new JMenu(Language.getValue("New"));
		newFileMenu.setMnemonic('N');
		newFileMenu.add(ActionManager.getAction(NewUseCaseShapeAction.class));
		newFileMenu.add(ActionManager.getAction(NewActiveShapeAction.class));
		newFileMenu.add(ActionManager.getAction(NewSeriShapeAction.class));
		// --
		fileMenu.add(newFileMenu);
		fileMenu.add(ActionManager.getAction(OpenAction.class));
		fileMenu.addSeparator();
		fileMenu.add(ActionManager.getAction(SaveAction.class));
		fileMenu.add(ActionManager.getAction(SaveAsAction.class));
		fileMenu.addSeparator();
		fileMenu.add(ActionManager.getAction(ExitAction.class));
		return fileMenu;
	}

	private static JMenu createShapeMenu() {
		JMenu shapeMenu = new JMenu(Language.getValue("Shape"));
		shapeMenu.setMnemonic('S');
		shapeMenu.add(ActionManager.getAction(PropertyAction.class));
		shapeMenu.addSeparator();
		shapeMenu.add(ActionManager.getAction(ZoomInAction.class));
		shapeMenu.add(ActionManager.getAction(ZoomOutAction.class));
		shapeMenu.add(ActionManager.getAction(ResetScaleAction.class));
		return shapeMenu;
	}

	private static JMenu createWindowMenu() {
		JMenu windowMenu = new JMenu(Language.getValue("Window"));
		windowMenu.setMnemonic('W');
		windowMenu.add(ActionManager.getAction(ShowStatusBarAction.class));
		windowMenu.addSeparator();
		windowMenu.add(ActionManager.getAction(UpdateVersionAction.class));
		windowMenu.add(ActionManager.getAction(SettingAction.class));
		JMenu pluginMenu = new JMenu(Language.getValue("Plugin"));
		pluginMenu.setMnemonic('P');
		pluginMenu.setEnabled(false);
		windowMenu.add(pluginMenu);
		SMMSystem.pluginMenu = pluginMenu;
		// --
		JMenu lookAndFeelMenu = new JMenu(Language.getValue("LookAndFeel"));
		lookAndFeelMenu.add(new LookAndFeelAction("Windows",
				"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
		lookAndFeelMenu.add(new LookAndFeelAction("Swing",
				"javax.swing.plaf.metal.MetalLookAndFeel"));
		lookAndFeelMenu.add(new LookAndFeelAction("Other",
				"com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
		windowMenu.add(lookAndFeelMenu);
		return windowMenu;
	}
}
