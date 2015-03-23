package com.ultrapower.eoms.ultrasla.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SLA工具类
 * @author SunHailong
 */
public class UltraSlaUtil {
	
	/**
	 * 根据类的全路径获取类的实例
	 * @param fullpath
	 * @return
	 */
	public static Object getClassInstance(String fullpath) {
		Object obj = null;
		try {
			obj = Class.forName(fullpath).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 获得日志头
	 * @return
	 */
	public static String constructLog(String msg,Integer errorCode,String kwdName,String kwd)
	{
		StringBuffer head = new StringBuffer();
		head.append("[UltraSLA ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		head.append(sdf.format(date));
		head.append("]");
		if(msg!=null)
		{
			head.append(msg);
			head.append("\t");
		}
		if(kwd!=null)
		{
			head.append(kwd);
			head.append(":");
			head.append(kwdName);
			head.append("\t");
		}
		if(errorCode!=null)
		{
			head.append("-");
			head.append(errorCode);
		}
		return head.toString();
	}
}
