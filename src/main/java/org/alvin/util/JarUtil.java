package org.alvin.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtil {

	private JarUtil() {

	}

	/** * ����jar�е����� * * @param jarPathName * @throws Exception */
	public static void listJarFile(String jarPathName) throws Exception {
		JarFile jarFile = new JarFile(jarPathName);
		Enumeration<JarEntry> jarEntrys = jarFile.entries();
		while (jarEntrys.hasMoreElements()) {
			JarEntry jarEntry = jarEntrys.nextElement();
			System.out.println(jarEntry.getName());
		}
	}

	/** * ��ȡjar���е�ĳ����Ŀ * * @param jarPathName * @throws Exception */
	public static void getJarFileContent(String jarPathName) throws Exception {
		JarFile jarFile = new JarFile(jarPathName);
		JarEntry jarEntry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
		InputStream in = jarFile.getInputStream(jarEntry);
		int c = -1;
		while ((c = in.read()) != -1) {
			System.out.print((char) (c & 0XFF));
		}
		if (in != null) {
			in.close();
			in = null;
		}
		System.out.println();
	}

	public static void loadJar(String path) {

	}

	/**
	 * ��������
	 * 
	 * @author ��ֲ��
	 * 
	 */
	class URLLoader extends URLClassLoader {
		public URLLoader() {
			super(null);
		}

		public void addUrl(URL url) {
			super.addURL(url);
		}
	}
}
