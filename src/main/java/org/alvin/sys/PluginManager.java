package org.alvin.sys;

import java.io.File;

public class PluginManager {

	private PluginManager() {

	}

	private static String pluginPath = SMMSystem.realPath.concat("plugin");

	/**
	 * ╪сть╡Е╪Ч
	 */
	public static void loadPlugin() {
		File f = new File(pluginPath);
		if (!f.exists()) {
			f.mkdirs();
			return;
		}
		
	}

}
