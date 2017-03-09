/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.alvin.util;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.alvin.sys.Language;

/**
 * 
 * @author Chris
 */
public class DialogUtil {

	public static final int SELECT_YES = JOptionPane.YES_OPTION;
	public static final int SELECT_NO = JOptionPane.NO_OPTION;
	public static final int SELECT_CANCEL = JOptionPane.CANCEL_OPTION;

	public static void promptMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, Language.getValue("Info"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void promptWarning(String msg) {
		JOptionPane.showMessageDialog(null, msg, Language.getValue("Warn"),
				JOptionPane.WARNING_MESSAGE);
	}

	public static void promptError(String msg) {
		JOptionPane.showMessageDialog(null, msg, Language.getValue("Error"),
				JOptionPane.ERROR_MESSAGE);
	}

	public static int confirmDialog(String msg) {
		int res = JOptionPane.showConfirmDialog(null, msg,
				Language.getValue("Confirm(y/n)"), JOptionPane.YES_NO_OPTION);
		return res;
	}

	public static int chooserConfigDialog(String msg) {
		int res = JOptionPane.showConfirmDialog(null, msg,
				Language.getValue("Confirm(y/n/c)"),
				JOptionPane.YES_NO_CANCEL_OPTION);
		return res;
	}

	public static void promptMessage(String msg, Component parent) {
		JOptionPane.showMessageDialog(null, msg, Language.getValue("Info"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void promptWarning(String msg, Component parent) {
		JOptionPane.showMessageDialog(null, msg, Language.getValue("Warn"),
				JOptionPane.WARNING_MESSAGE);
	}

	public static void promptError(String msg, Component parent) {
		JOptionPane.showMessageDialog(null, msg, Language.getValue("Error"),
				JOptionPane.ERROR_MESSAGE);
	}

	public static int confirmDialog(String msg, Component parent) {
		return JOptionPane.showConfirmDialog(null, msg,
				Language.getValue("Confirm(y/n)"), JOptionPane.YES_NO_OPTION);
	}

	public static int chooserConfigDialog(String msg, Component parent) {
		return JOptionPane.showConfirmDialog(parent, msg,
				Language.getValue("Confirm(y/n/c)"),
				JOptionPane.YES_NO_CANCEL_OPTION);
	}

	public static String inputDialog(String title, Component parent,
			String value) {
		return JOptionPane.showInputDialog(parent, title, value);
	}
}
