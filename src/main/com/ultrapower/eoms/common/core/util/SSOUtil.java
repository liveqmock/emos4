package com.ultrapower.eoms.common.core.util;

public class SSOUtil {
	public final static String SSO_USER_PWD_SEPARATOR = "@semi@";
	public final static String EXTENT_LOGIN_ACTION = "/portal/extentLogin.action";
	public final static String EXTENT_LOGOUT_ACTION = "/portal/extentLogout.action";
	
	/**
	 * 根据如下参数返回跳转字符串（登录使用）
	 * @param loginName --用户名
	 * @param pwd --密码
	 * @param returnRedirectUrl --成功登录后跳转的URL，必须写全路径，如：http://localhost:8080/tjeoms
	 * @param remoteAppRootPath --远程应用系统根路径，根据实际部署而定，如:http://192.168.20.135:8088/eoms4
	 * @return
	 */
	public static String getRedirectStr(String loginName, String pwd, String returnRedirectUrl, String remoteAppRootPath)
	{
		String redirectStr = null;
		if(loginName!=null && pwd!=null && returnRedirectUrl!=null && remoteAppRootPath!=null)
		{
			CryptUtils crypt = CryptUtils.getInstance();
			String temp_passport = loginName+SSOUtil.SSO_USER_PWD_SEPARATOR+crypt.encode(pwd);
			temp_passport = temp_passport.replace("&", "&amp;");
			temp_passport = temp_passport.replace("#", "&jing;");
			redirectStr = remoteAppRootPath+SSOUtil.EXTENT_LOGIN_ACTION+"?data="+temp_passport+"&url="+returnRedirectUrl;
		}
		
		return redirectStr;
	}
	
	/**
	 * 根据如下参数返回跳转字符串（登出使用）
	 * @param returnRedirectUrl --成功登出后跳转的URL，必须写全路径，如：http://localhost:8080/tjeoms
	 * @param remoteAppRootPath --远程应用系统根路径，根据实际部署而定，如:http://192.168.20.135:8088/eoms4
	 * @return
	 */
	public static String getRedirectStr(String returnRedirectUrl, String remoteAppRootPath)
	{
		String redirectStr = null;
		if(returnRedirectUrl!=null && remoteAppRootPath!=null)
		{
			redirectStr = remoteAppRootPath+SSOUtil.EXTENT_LOGOUT_ACTION+"?url="+returnRedirectUrl;
		}
		return redirectStr;
	}
}
