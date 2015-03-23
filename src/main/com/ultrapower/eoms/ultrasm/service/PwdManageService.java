package com.ultrapower.eoms.ultrasm.service;

/**
 * 萨班斯密码验证接口
 * @author SunHailong
 */
public interface PwdManageService
{
	/**
	 * 是否可以正常登陆
	 * 首次登陆系统或90天内未修改密码则不允许登陆系统
	 * @param loginName 用户登陆名
	 * @return 返回验证提示信息
	 */
	public String isCanLogin(String loginName);
	
	/**
	 * 萨班斯密码验证是否可用密码
	 * @param loginName 用户登陆名
	 * @param pwd 用户密码
	 * @return 返回违反萨班斯密码规则的信息 若为空则代表是可用密码
	 */
	public String isEnablePwd(String loginName, String pwd);
	
	/**
	 * 萨班斯密码管理 保存密码信息
	 * @param loginName 用户登陆名
	 * @param pwd 用户密码
	 * @return 返回true或false体现是否保存密码成功
	 */
	public boolean saveUserPwd(String loginName, String pwd);
}
