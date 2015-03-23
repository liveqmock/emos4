package com.ultrapower.eoms.common.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音通用类
 * @author SunHailong
 */
public class PinYinUtils
{
	public static String getPinYin(String str)
	{
		return getPinYin(str, "1");
	}
	
	/**
	 * 获取汉字对应的拼音
	 * @param word
	 * @param type 1:全小写 2:全大写 3:首字母大写 4:前两个字首字母大写
	 * @return
	 */
	public static String getPinYin(String str, String type)
	{
		if(str == null)
			return str;
		if(type == null)
			type = "1";
		char[] strChar = str.toCharArray();
		String[] strArray = new String[strChar.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
		pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);//默认小写
		pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不翻译音调
		pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String pyStr = "";//转换后的拼音
		String tmp;
		int wordLen = strChar.length;
		try
		{
			for(int i=0;i<wordLen;i++)
			{
				// 判断能否为汉字字符
				//System.out.println(strChar[i]);
				if (Character.toString(strChar[i]).matches("[\\u4E00-\\u9FA5]+"))
				{
					strArray = PinyinHelper.toHanyuPinyinStringArray(strChar[i], pyFormat);// 将汉字的几种全拼都存到wordStr数组中
					// 取出该汉字全拼的第一种读音并连接到字符串pyStr后
					tmp = strArray[0];
					if("2".equals(type))
						pyStr += tmp.toUpperCase();
					else if("3".equals(type))
						pyStr += tmp.substring(0,1).toUpperCase() + tmp.substring(1);
					else if("4".equals(type))
					{
						if(i < 2)
							pyStr += tmp.substring(0,1).toUpperCase() + tmp.substring(1);
						else
							pyStr += tmp;
					}
					else
					{
						pyStr += tmp;
					}
				}
				else
				{
					// 如果不是汉字字符，间接取出字符并连接到字符串pyStr后
					pyStr += Character.toString(strChar[i]);
				}
			}
		}
		catch (BadHanyuPinyinOutputFormatCombination e)
		{
			e.printStackTrace();
		}
		return pyStr;
	}
	
	public static String getPinYinHeadChar(String str)
	{
		return getPinYinHeadChar(str, "1");
	}
	
	/**
	 * 获取汉字的首字母
	 * @param str
	 * @param type 1:小写 2:大写 3:第一个字母大写
	 * @return
	 */
	public static String getPinYinHeadChar(String str, String type)
	{
		String convert = "";
		for (int i=0;i<str.length();i++)
		{
			char word = str.charAt(i);
			// 提取汉字的首字母   
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null)
			{
				convert += pinyinArray[0].charAt(0);
			}
			else
			{
				convert += word;
			}
		}
		if(type == null)
			;
		else if("2".equals(type))
			convert = convert.toUpperCase();
		else if("3".equals(type))
			convert = convert.substring(0,1).toUpperCase() + convert.substring(1);
		return convert;
	}
	
	public static String getPinYinFristChar(String str)
	{
		return getPinYinFristChar(str, "2");
	}
	
	public static String getPinYinFristChar(String str, String type)
	{
		String convert = "";
		for (int i=0;i<str.length();i++)
		{
			char word = str.charAt(i);
			// 提取汉字的首字母   
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null)
			{
				convert += pinyinArray[0].charAt(0);
			}
			else
			{
				convert += word;
			}
			break;
		}
		if(type == null)
			;
		else if("2".equals(type))
			convert = convert.toUpperCase();
		return convert;
	}
	
	/**  
	 * 将字符串转换成ASCII码  
	 * @param cnStr  
	 * @return String  
	 */
	public static String getCnASCII(String cnStr)
	{
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列   
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++)
		{
			// 将每个字符转换成ASCII码   
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}
	
	public static void main(String[] args)
	{
		String cnStr = "我是一个兵";
		System.out.println(getPinYin(cnStr, "4"));
		System.out.println(getPinYinHeadChar(cnStr, "3"));
		System.out.println(getPinYinFristChar(cnStr, "2"));
		System.out.println(getCnASCII(cnStr));
	}
}
