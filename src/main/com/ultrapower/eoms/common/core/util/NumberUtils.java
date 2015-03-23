package com.ultrapower.eoms.common.core.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供高精度的运算支持.
 * 所以函数以double为参数类型，兼容int与float.
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn)
 */
public class NumberUtils {

	/**
	 * 精确的加法运算。
	 * @param v1 被加数1
	 * @param v2 加数2
	 * @return 返回相加的结果
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 返回相减的结果
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 返回相乘的结果
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算，并对运算结果截位。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @param scale 运算结果小数后精确的位数
	 * @return 返回相乘的结果
	 */
	public static double multiply(double v1, double v2,int scale) {
		if (scale < 0)
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * 提供（相对）精确的除法运算。
	 * @param v1 被除数
	 * @param v2 除数
	 * @see #divide(double, double, int)
	 * @return 返回相除的结果
	 */
	public static double divide(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算.
	 * 由scale参数指定精度，以后的数字四舍五入.
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 * @return 返回相除的结果
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 返回四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 判断接收的字符串是否是数字
	 * @param str 一个String数据
	 * @return 如果是数字返回true,否则false
	 */
	public static boolean isNumeric(String str){
		try{
			if(str!=null&&!String.valueOf(str).equals("")){
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher isNum = pattern.matcher(str);
				if(!isNum.matches()){
					return false;
				}
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 将字符串转换成浮点型值
	 * @param p_String 一个字符串数据
	 * @return float 浮点型
	 */
	public static float formatToFloat(String p_String) {
		float intRe = 0;
		if (p_String != null) {
			if (!p_String.trim().equals("")) {
				try {
					intRe = Float.parseFloat(p_String);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		return intRe;
	}

	/**
	 * 将对象转换成浮点型值
	 * @param p_object 对象参数
	 * @return float 浮点型
	 */
	public static float formatToFloat(Object p_object) {
		String p_String = StringUtils.checkNullString(p_object);
		return formatToFloat(p_String);
	}

	/**
	 * 将字符串转换成整型值
	 * @param p_String 字符串参数
	 * @return int 整形
	 */
	public static int formatToInt(String p_String) {
		int intRe = 0;
		if (p_String != null) {
			if (!p_String.trim().equals("")) {
				try {
					intRe = Integer.parseInt(p_String);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return intRe;
	}

	/**
	 * 将对象转换成整型值
	 * @param p_object 对象参数
	 * @return int 整形
	 */
	public static int formatToInt(Object p_object) {
		int intRe = 0;
		String strobj = StringUtils.checkNullString(p_object).trim();
		if (!strobj.trim().equals("")) {
			try {
				intRe = Integer.parseInt(strobj);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		return intRe;
	}

	/**
	 * 将对象转换成整型值
	 * @param p_String 字符串参数
	 * @return long 长整形
	 */
	public static long formatToLong(String p_String) {
		long intRe = 0;
		if (p_String != null) {
			if (!p_String.trim().equals("")) {
				try {
					intRe = Integer.parseInt(p_String);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return intRe;
	}

	/**
	 * 将对象转换成长整型值
	 * @param p_object 对象参数
	 * @return long 长整形
	 */
	public static long formatToLong(Object p_object) {
		String p_String = StringUtils.checkNullString(p_object);
		long intRe = 0;
		if (!p_String.trim().equals("")) {
			try {
				intRe = Integer.parseInt(p_String);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		return intRe;
	}
}
