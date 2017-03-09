package org.alvin.util;

public class LogUtil {

	private LogUtil() {

	}

	private static void p(String text) {
		System.out.println(text);
	}

	public static void debug(String text) {
		p(text);
	}

	public static void info(String text) {
		p(text);
	}

	public static void warn(String text) {
		p(text);
	}

	public static void error(String text) {
		p(text);
	}
}
