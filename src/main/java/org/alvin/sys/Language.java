package org.alvin.sys;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.alvin.util.LogUtil;
import org.alvin.util.XMLUtil;

/**
 * 语言配置管理类
 * 
 * @author 唐植超
 * 
 */
public class Language {

	private Language() {
	}

	private static Locale lc = Locale.getDefault();
	private static final String resouceName = "smm";
	private static Properties map;
	private static String filePath;
	private static boolean saveFlag = false;

	/**
	 * 国际化预留接口
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String value = map.getProperty(key);
		if (value == null) {
			map.put(key, key);
			value = key;
			saveFlag = true;
		}
		return value;
	}

	/**
	 * 加载国际化信息
	 */
	public static void loadLanguage() {
		String country = lc.getCountry();
		String language = lc.getLanguage();

		String fileName = SMMSystem.lang_dir.concat(resouceName).concat("_")
				.concat(language);
		filePath = fileName.concat("_").concat(country).concat(".xml");
		map = XMLUtil.loadLanguage(filePath);
	}

	public static void saveLanguage() {
		if (saveFlag) {
			XMLUtil.saveLanguage(filePath, map);
			LogUtil.info("更新国际化文件");
		}
	}

	public static void createLanguageFile() {
		LogUtil.info("创建一个默认的语言配置文件");
		String country = lc.getCountry();
		String language = lc.getLanguage();

		String fileName = SMMSystem.lang_dir.concat(resouceName).concat("_")
				.concat(language);
		filePath = fileName.concat("_").concat(country).concat(".xml");
		try {
			new File(fileName).createNewFile();
			XMLUtil.saveLanguage(filePath, new Properties());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
