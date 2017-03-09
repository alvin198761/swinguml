package org.alvin.sys;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.alvin.util.LogUtil;
import org.alvin.util.XMLUtil;

/**
 * �������ù�����
 * 
 * @author ��ֲ��
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
	 * ���ʻ�Ԥ���ӿ�
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
	 * ���ع��ʻ���Ϣ
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
			LogUtil.info("���¹��ʻ��ļ�");
		}
	}

	public static void createLanguageFile() {
		LogUtil.info("����һ��Ĭ�ϵ����������ļ�");
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
