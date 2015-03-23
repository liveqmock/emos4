package com.ultrapower.eoms.common.core.util;

import java.util.UUID;

/**
 * jdk uuid
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 9, 2010 6:34:57 PM
 */
public class UUIDGenerator {

	/**
	 * 去除"-"间隔符
	 * @return
	 */
	public static String getUUIDoffSpace() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String s = UUID.randomUUID().toString();
		return s;
	}
	
	/**
	 * 根据参数number 获取number个UUID
	 * @param number
	 * @return
	 */
	public static String[] getUUID(int number) {
		if (number < 1) 
			return null;
		String[] ss = new String[number];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = getUUIDoffSpace();
		}
		return ss;
	}
}
