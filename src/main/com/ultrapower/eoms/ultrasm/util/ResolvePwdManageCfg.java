package com.ultrapower.eoms.ultrasm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class ResolvePwdManageCfg
{
	/**
	 * 获取密码管理配置表
	 */
	public static void getPwdManageCfg()
	{
		XmlParser xml = new XmlParser(ResolvePwdManageCfg.class.getResource("/").getPath() + "sysconfig/PwdManage_Config.xml");
		if(xml == null)
			return ;
		String recordCount = StringUtils.checkNullString(xml.getValue("RecordCount"));
		String timeValue = StringUtils.checkNullString(xml.getValue("MustUpdate"));
		String timeUnit = StringUtils.checkNullString(xml.getAttributeValue("MustUpdate", "unit"));
		String userInfo = StringUtils.checkNullString(xml.getValue("UserInfo"));
		String detail = StringUtils.checkNullString(xml.getAttributeValue("UserInfo", "detail"));
		String pwdLength = StringUtils.checkNullString(xml.getValue("PwdLength"));
		String sameWord = StringUtils.checkNullString(xml.getValue("SameWord"));
		String wordType = StringUtils.checkNullString(xml.getValue("WordType"));
		
		HashMap<String, Object> pwdManageCfg = new HashMap<String, Object> ();
		List<String> pwdRuleList = new ArrayList<String> ();

		//记录密码次数 
		recordCount = NumberUtils.isNumeric(recordCount) ? recordCount : "1";
		pwdManageCfg.put("recordCount", recordCount);
		pwdRuleList.add("不能与最近" + recordCount + "次修改过的密码相同");
		
		//修改密码时限 存入缓存中以秒为单位
		pwdManageCfg.put("updateTime", (getTimeDay(timeUnit, timeValue) * 24 * 60 * 60) + "");
		pwdRuleList.add("请务必在" + timeValue + getUnitStr(timeUnit) + "内修改密码");
		
		//人员基本信息的验证
		pwdManageCfg.put("userInfo", userInfo);
		if(!"".equals(userInfo))
		{
			pwdRuleList.add("请不要将密码修改成与个人信息相关的信息");
			if(!"".equals(detail))
			{
				String[] userDetail = detail.split(",");
				for(int i=0 ; i<userDetail.length ; i++)
				{
					pwdManageCfg.put(userDetail[i].toLowerCase(), "true");
				}
			}
			else
				pwdManageCfg.put("userDetail", "all");
		}
		
		//长度验证
		int splitSpace = pwdLength.indexOf("-");
		String min = "3";
		String max = "30";
		if(splitSpace > 0)
		{
			//最长最短值的处理
			min = pwdLength.substring(0, splitSpace);
			min = NumberUtils.formatToInt(min) < 3 ? "3" : min;
			max = pwdLength.substring(splitSpace + 1);
			max = NumberUtils.formatToInt(max) > 30 ? "30" : max;
			min = NumberUtils.formatToInt(min) > NumberUtils.formatToInt(max) ? "3" : min;
		}
		pwdManageCfg.put("length_Min", min);
		pwdManageCfg.put("length_Max", max);
		pwdRuleList.add("修改密码长度的规则[" + min + "-" + max + "]");
		
		//连续字符的验证
		pwdManageCfg.put("sameWord", "false".equals(sameWord) ? sameWord : "true");
		if(!"".equals(sameWord))
			pwdRuleList.add("请不要使用连续的字符作为密码");
		
		//字符类型
		if(!"".equals(wordType))
		{
			String[] words = wordType.split(",");
			String word_key;
			String wordRule = "";
			String mustWord = "";
			String mayWord = "";
			for(int i=0 ; i<words.length ; i++)
			{
				word_key = "word_" + words[i].replaceAll("\\p{P}", "");
				mayWord += "、" + getWordType(words[i]);
				if(words[i].indexOf("[") == 0 && words[i].indexOf("]") > 0)
					pwdManageCfg.put(word_key, "may"); // 此类型字符可有可无
				else
				{
					mustWord += "、" + getWordType(words[i]);
					pwdManageCfg.put(word_key, "must"); // 此类型字符必须包含
				}
			}
			if(words.length < 3)
			{
				wordRule = "您的密码只能包含" + mayWord.substring(1);
				if(!"".equals(mustWord))
					wordRule += "，并且必须包含" + mustWord.substring(1);
			}
			else
			{
				if(!"".equals(mustWord))
					wordRule = "您的密码必须包含" + mustWord.substring(1);
			}
			if(!"".equals(wordRule))
				pwdRuleList.add(wordRule);
		}
		else
			pwdManageCfg.put("wordType", "false");
		
		//提示信息设定
		pwdManageCfg.put("recordCount_Msg", "为了您的安全性，请不要使用最近" + recordCount + "次修改过的密码！");
		pwdManageCfg.put("updateTime_Msg", "为了您的安全性，您已经" + timeValue + getUnitStr(timeUnit) + "没有修改密码了，请进行修改！");
		pwdManageCfg.put("length_Msg", "为了您的安全性，请按照[" + min + "-" + max + "]长度范围输入！");
		pwdManageCfg.put("sameWord_Msg", "为了您的安全性，请不要使用连续的字符作为密码！");
		
		pwdManageCfg.put("pwdRuleList", pwdRuleList);
		
		//将密码配置放入缓存
		BaseCacheManager.put("pwdManageCfg", "pwdManageCfg", pwdManageCfg);
		System.out.println("==========已成功将密码配置放入缓存中==========");
	}
	
	/**
	 * 获取时限具体的天数
	 * @param timeUnit	时限单位
	 * @param timeValue	时限值
	 * @return
	 */
	private static int getTimeDay(String timeUnit, String timeValue)
	{
		int day = 0;
		int value = 0;
		if(NumberUtils.isNumeric(timeValue))
			value = NumberUtils.formatToInt(timeValue);
		if(value > 0)
		{
			if("day".equals(timeUnit))
				day = value;
			else if("week".equals(timeUnit))
				day = value * 7;
			else if("month".equals(timeUnit))
				day = value * 30;
			else if("year".equals(timeUnit))
				day = value * 365;
		}
		return day;
	}
	
	private static String getUnitStr(String timeUnit)
	{
		String unitStr = "天";
		if("day".equals(timeUnit))
			unitStr = "天";
		else if("week".equals(timeUnit))
			unitStr = "周";
		else if("month".equals(timeUnit))
			unitStr = "月";
		else if("year".equals(timeUnit))
			unitStr = "年";
		return unitStr;
	}
	
	private static String getWordType(String word)
	{
		String wordStr = "";
		if("".equals(StringUtils.checkNullString(word)))
			return wordStr;
		if(word.indexOf("word") >= 0)
			wordStr = "字母";
		else if(word.indexOf("number") >= 0)
			wordStr = "数字";
		else if(word.indexOf("symbol") >= 0)
			wordStr = "符号";
		return wordStr;
	}
	
	public static void main(String[] args)
	{
		
	}
}
