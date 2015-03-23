package com.ultrapower.eoms.workflow.util;

import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;


/**
 * 获取主键工具类
 * @author dulylau
 *
 */
public class KeyUtil {
	
	public static String getkey_15(){
		RandomN random = new Random15();
		return random.getRandom(System.currentTimeMillis());
	}

}
