package com.ultrapower.eoms.common.external.web;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class DataSynchAction extends BaseAction
{
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	
	public String synchToPasm()
	{
		return SUCCESS;
	}
	
	public String synchToPasm_User()
	{
		if(userManagerService.synchUserToPasm())
			System.out.println("用户数据同步PASM成功！");
		else
			System.out.println("用户数据同步PASM失败！请查看错误日志.");
		return this.findForward("synchToPasm");
	}
	
	public String synchToPasm_Dep()
	{
		if(depManagerService.synchDepToPasm())
			System.out.println("部门数据同步PASM成功！");
		else
			System.out.println("部门数据同步PASM失败！请查看错误日志.");
		return this.findForward("synchToPasm");
	}
	
	public String synchToPasm_UserDep()
	{
		if(depManagerService.synchUserDepToPasm())
			System.out.println("组成员关系数据同步PASM成功！");
		else
			System.out.println("组成员关系数据同步PASM失败！请查看错误日志.");
		return this.findForward("synchToPasm");
	}
	
	public String synchToPasm_Role()
	{
		if(roleManagerService.synchRoleToPasm())
			System.out.println("角色数据同步PASM成功！");
		else
			System.out.println("角色数据同步PASM失败！请查看错误日志.");
		return this.findForward("synchToPasm");
	}
	
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
}
