package com.ultrapower.eoms.msextend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidateUtil {

	/**
	 * 验证邮件是否合法
	 * @param emailStr
	 * @return
	 */
	public static boolean isEmail(String emailStr){
		if("".equals(emailStr)||emailStr==null){
			return false;
		}else{
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(emailStr);
		return matcher.matches();
		}
	}
}
