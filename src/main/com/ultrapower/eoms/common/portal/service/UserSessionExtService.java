package com.ultrapower.eoms.common.portal.service;

import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 获取扩展的用户Session信息
 * @author SunHailong
 */
public interface UserSessionExtService
{
	/**
	 * 获取扩展的用户Session信息
	 * @param userSession
	 * @return
	 */
	public UserSession buildExtendUserSession(UserSession userSession);
}
