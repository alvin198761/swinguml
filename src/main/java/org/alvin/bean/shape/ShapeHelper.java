package org.alvin.bean.shape;

import java.awt.Component;
import java.awt.Cursor;
import java.util.LinkedList;
import java.util.List;

public class ShapeHelper {

	// 控制方位
	public static final String WAY_TOP = "8";
	public static final String WAY_TOP_RIGHT = "9";
	public static final String WAY_TOP_LEFT = "10";
	public static final String WAY_LEFT = "11";
	public static final String WAY_RIGHT = "12";
	public static final String WAY_BOTTOM = "13";
	public static final String WAY_BOTTOM_LEFT = "14";
	public static final String WAY_BOTTOM_RIGHT = "15";
	//
	public static final int CTRL_SHAPE_SIZE = 6;
	public static final int SUBSYSTEM_HEIGHT = 580;

	// 活动图
	public static final String ACTIVE_START = "1";
	public static final String ACTIVE_END = "2";
	public static final String ACTIVE_ACTION = "3";
	public static final String ACTIVE_CONDITION = "4";
	public static final String ACTIVE_INTEGERFACE = "5";
	public static final String ACTIVE_LANE = "6";
	public static final String ACTIVE_LINE = "7";
	// 序列图
	public static final String SERI_ACTIVE = "8";
	public static final String SERI_CONSTRAIN = "9";
	public static final String SERI_LIFE = "10";
	public static final String SERI_MESSAGE = "11";
	public static final String SERI_MESSAGE_CALL = "12";
	public static final String SERI_MESSAGE_RETURN = "13";
	public static final String SERI_MESSAGE_CALL_RETURN = "14";
	public static final String SERI_MESSAGE_ASY = "15";
	// 用例图
	public static final String USECASE_LINE = "16";
	public static final String USECASE_ACTOR = "17";
	public static final String USECASE_SYS_BORDER = "18";
	public static final String USECASE_CASE = "19";

	/**
	 * 活动图
	 * 
	 * @return
	 */
	public static List<String> getActiveTypeList() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(ACTIVE_START);
		list.add(ACTIVE_END);
		list.add(ACTIVE_ACTION);
		list.add(ACTIVE_CONDITION);
		list.add(ACTIVE_INTEGERFACE);
		list.add(ACTIVE_LANE);
		return list;
	}

	/**
	 * 序列图类型
	 * 
	 * @return
	 */
	public static List<String> getSeriTypeList() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(SERI_LIFE);
		list.add(SERI_ACTIVE);
		list.add(SERI_MESSAGE);
		list.add(SERI_MESSAGE_CALL);
		list.add(SERI_MESSAGE_RETURN);
		list.add(SERI_MESSAGE_CALL_RETURN);
		list.add(SERI_MESSAGE_ASY);
		list.add(SERI_CONSTRAIN);
		return list;
	}

	/**
	 * 用例类型
	 * 
	 * @return
	 */
	public static List<String> getUseCaseTypeList() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(USECASE_ACTOR);
		list.add(USECASE_CASE);
		list.add(USECASE_SYS_BORDER);
		return list;
	}

	/**
	 * 8方位
	 * 
	 * @return
	 */
	public static List<String> getWay8() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(WAY_TOP);
		list.add(WAY_LEFT);
		list.add(WAY_RIGHT);
		list.add(WAY_BOTTOM);

		list.add(WAY_TOP_RIGHT);
		list.add(WAY_TOP_LEFT);
		list.add(WAY_BOTTOM_LEFT);
		list.add(WAY_BOTTOM_RIGHT);
		return list;
	}

	/**
	 * 6方位
	 * 
	 * @return
	 */
	public static List<String> getWay6() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(WAY_LEFT);
		list.add(WAY_RIGHT);

		list.add(WAY_TOP_RIGHT);
		list.add(WAY_TOP_LEFT);
		list.add(WAY_BOTTOM_LEFT);
		list.add(WAY_BOTTOM_RIGHT);
		return list;
	}

	/**
	 * 4方位
	 * 
	 * @return
	 */
	public static List<String> getWay4() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(WAY_TOP);
		list.add(WAY_LEFT);
		list.add(WAY_RIGHT);
		list.add(WAY_BOTTOM);
		return list;
	}

	/**
	 * 鼠标图形的控制
	 * 
	 * @param c
	 * @param way
	 */
	public static void setCursor(Component c, String way) {
		if (way.equals(WAY_TOP)) {
			c.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_TOP_LEFT)) {
			c.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_TOP_RIGHT)) {
			c.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_LEFT)) {
			c.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_RIGHT)) {
			c.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_BOTTOM)) {
			c.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_BOTTOM_LEFT)) {
			c.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_BOTTOM_RIGHT)) {
			c.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
			return;
		}
	}

}
