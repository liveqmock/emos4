package com.ultrapower.eoms.common.core.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.ultrapower.eoms.common.core.web.ActionContext;

/**
 * 用于国际化转译用的类
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-5-26
 */
public class Internation {
	
	/**
	 * 根据对应的key值来去对应的国际化字符
	 * @param key
	 * @return
	 */
	public static String language(String key) {
		String value="";
		if(key==null || key.equals(""))
			return "";

		try{
			String laguage=getHl();
			Locale locale = null;
			if (laguage.equals("zh-cn"))
				locale = new Locale("zh", "CN");
			else if (laguage.equals("zh-tw"))
				locale = new Locale("zh", "TW");
			else if (laguage.equals("en"))
				locale = new Locale("en", "US");			
			ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
			value = rb.getString(key);
		}catch(Exception ex)
		{
			System.out.print("Internation.language  ");
			System.out.println(ex.getMessage());
			//ex.printStackTrace();
		}
		
		return value;
	} 

	public static String getHl() {
		String hl =""; 
		HttpServletRequest request=ActionContext.getRequest();
		try
		{
			if(request!=null)
			{
				hl = request.getParameter("hl");
				if (hl == null)
					hl = request.getHeader("Accept-Language");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		if (hl == null) {
//			Cookie[] cookies = request.getCookies();
//			if ((cookies == null) || (cookies.length == 0))
//				hl = null;
//			else {
//				for (int i = 0; i < cookies.length; i++) {
//					if ("hl".equals(cookies[i].getName()))
//						hl = cookies[i].getValue();
//				}
//			}
//		}
		if (hl == null)
			return "zh-cn";
		hl = hl.toLowerCase();
		if ("zh-cn".equals(hl))
			return hl;
		else if (hl.startsWith("en"))
			return "en";
		else if ("zh-tw".equals(hl) || "zh-hk".equals(hl) || "zh-sg".equals(hl)
				|| "zh-mo".equals(hl))
			return "zh-tw";
		else
			return "zh-cn";
	}  
}
