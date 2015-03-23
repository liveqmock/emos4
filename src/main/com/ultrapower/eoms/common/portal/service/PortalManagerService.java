package com.ultrapower.eoms.common.portal.service;

import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 系统登录登出相关服务类
 * @author SunHailong
 */
public interface PortalManagerService {

	/**
	 * 系统登录返回Session
	 * @param loginName 用户登陆名
	 * @param pwd 用户密码
	 * @param isVerify 是否密码验证
	 * @return boolean
	 */
	public UserSession login(String loginName, String pwd, boolean isVerify);

	/**
	 * 系统简登录返回Session
	 * @param loginName 用户登录名
	 * @param pwd 用户密码
	 * @param isVerify 是否密码验证
	 * @return
	 */
	public UserSession loginSimple(String loginName, String pwd, boolean isVerify);
	
	/**
	 * 根据用户登录名获取Session信息
	 * @param loginName 用户登录名
	 * @param beanNames 获取Session信息的实现类实例名
	 * @return
	 */
	public UserSession getUserSession(String loginName, String... beanNames);

	/**
	 * 根据用户名和密码判断此用户是否可以登录
	 * @param loginName 用户登录名
	 * @param pwd 用户密码
	 * @return
	 */
	public boolean isValidateLoginInfo(String loginName, String pwd);

	/**
	 * 根据登录名将密码发到手机上
	 * @param loginName
	 * @return
	 */
	public String findpwd(String loginName);

	/**
	 * 验证码校验
	 * @param loginName
	 * @return
	 */
	public String getCheckCode(String loginName);

	/**
	 * 记录用户登陆信息
	 * @param loginName 用户登陆名
	 * @param type 登陆登出类型 login logout
	 * @return
	 */
	public boolean saveUserLoginInfo(String loginName, String type);
}
