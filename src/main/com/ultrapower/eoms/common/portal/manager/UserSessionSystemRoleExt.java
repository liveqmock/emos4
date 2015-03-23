package com.ultrapower.eoms.common.portal.manager;

import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.UserSessionExtService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

/**
 * UserSession设置系统角色扩展类
 * @author SunHailong
 */
public class UserSessionSystemRoleExt implements UserSessionExtService
{
	private RoleManagerService roleManagerService;
	
	public UserSession buildExtendUserSession(UserSession userSession)
	{
		if(userSession == null)
		{
			return userSession;
		}
		//获取用户角色ID、名称、角色DNS 以;分割 如：001,002;角色1,角色2;001.001,001.002
		String roleInfo = StringUtils.checkNullString(roleManagerService.getRoleIdAndNameByUserId(userSession.getPid()));
		String roleDns = "";
		if(!roleInfo.equals(""))
		{
			String[] roles = roleInfo.split(";");
			if(roles.length > 2)
			{
				roleDns = roles[2];
				List dnsList = UltraSmUtil.arrayToList(roleDns.split(","));
				if(dnsList.indexOf("001") >= 0)
					userSession.setIsAdmin("1");
				else
					userSession.setIsAdmin("0");
				userSession.setRoleId(roles[0]);
				userSession.setRoleName(roles[1]);
				userSession.setRoleDns(roles[2]);
			}
		}
		if("".equals(roleDns))
			userSession.setIsAdmin("0");
		return userSession;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
}
