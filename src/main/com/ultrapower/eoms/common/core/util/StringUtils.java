package com.ultrapower.eoms.common.core.util;

import java.io.UnsupportedEncodingException;

/**
 * 字符串操作处理组件
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn) 
 * @version Apr 21, 2010 5:50:00 PM
 */
public class StringUtils {

	/**
	 * 对获取的字符串检查是否为空。
	 * @param p_String 一个String。
	 * @return String 如果为空则返回"",如果不为空则返回p_String本身。
	 */
	public static String checkNullString(String p_String){
		if (p_String == null)
			return "";
		else
			return p_String;
	}

	/**
	 * 返回 Object 参数的字符串表示形式。
	 * @param p_obj 一个Object。
	 * @return String 如果该对象为null,则返回"";否则返回p_obj.toString()的值。
	 */
	public static String checkNullString(Object p_obj){
		if (p_obj == null)
			return "";
		else
			return p_obj.toString();
	}

	/**
	 * 两个字符串内容是否一样。
	 * @param src 源字符串。
	 * @param dest 目标字符串。
	 * @return 如果相同则返回true,否则返回false。
	 */
	public static boolean compareEqualsString(String src, String dest){
		src = checkNullString(src).toLowerCase();
		dest = checkNullString(dest).toLowerCase();
		if (src.equals(dest))
			return true;
		else
			return false;
	}
	
	/**
	 * 按照指定的编码类型对对字符串编码
	 * @param str 需编码的字符串
	 * @param _codeType  编码类型 例如:UTF-8
	 * @return 返回编码后的字符串
	 */
	public static String encoder(String str,String _codeType) {
		if (str == null || str.length() < 1) {
			str = "";
		} else {
			try {
				str = new String(str.getBytes("iso-8859-1"), _codeType);
			} catch (UnsupportedEncodingException e) {
				return str;
			}
		}
		return str;
	}
	
	/**
	 * 转换指定编码的字符串
	 * @param str 源字符串
	 * @return 返回指定编码字符串
	 */
	public static String getEncoderStr(String str, String code) {
		if (str == null || str.length() < 1) {
			str = "";
		} else {
			try {
				str = (new String(str.getBytes(code)));
			} catch (Exception e) {
				e.printStackTrace();
				return str;
			}
		}
		return str;
	}
	
	/**
	 * 字符串连接方法，将一二三四级分类拼接为：一级分类.二级分类.三级分类.四级分类
	 * @param classStr1 一级分类
	 * @param classStr2 二级分类
	 * @param classStr3 三级分类
	 * @param classStr4 四级分类
	 * @return
	 */
	public static String joinClassStr(String classStr1, String classStr2, String classStr3, String classStr4) {
		String classStr = "";
		if (null != classStr1 && !"".equals(classStr1)) {
			classStr = classStr1;
			if (null != classStr2 && !"".equals(classStr2)) {
				classStr += "." + classStr2;
				if (null != classStr3 && !"".equals(classStr3)) {
					classStr += "." + classStr3;
					if (null != classStr4 && !"".equals(classStr4)) {
						classStr += "." + classStr4;
					}
				}
			}
		}
		return classStr;
	}

	public static boolean isNotBlank(String str) {
		return org.apache.commons.lang.StringUtils.isNotBlank(str);
	}

	public static boolean isBlank(String str) {
		return org.apache.commons.lang.StringUtils.isBlank(str);
	}
}
