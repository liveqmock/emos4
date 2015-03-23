package com.ultrapower.remedy4j.core;

/**
 * Remedy字段的数据类型
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
public class RemedyFieldType
{
	/**
	 * 空 NULL = 0
	 */
	public static final int NULL = 0;
	
	/**
	 * Remedy关键字 KEYWORD = 1
	 */
	public static final int KEYWORD = 1;
	
	/**
	 * 整形 INTEGER = 2
	 */
	public static final int INTEGER = 2;
	
	/**
	 * 	浮点型 REAL = 3
	 */
	public static final int REAL = 3;
	
	/**
	 * 字符型 CHAR = 4
	 */
	public static final int CHAR = 4;
	
	/**
	 * 枚举类型 ENUM = 6
	 */
	public static final int ENUM = 6;
	
	/**
	 * 时间型，传递秒 TIME = 7
	 */
	public static final int TIME = 7;
	
	/**
	 * 单精度型 REAL = 10
	 */
	public static final int DECIMAL = 10;
	
	/**
	 * 附件，传具体文件名，带路径 ATTACH = 11
	 */
	public static final int ATTACH = 11;
	
	/**
	 * 日期型 ATTACH = 11
	 */
	public static final int DATE = 13;
}
