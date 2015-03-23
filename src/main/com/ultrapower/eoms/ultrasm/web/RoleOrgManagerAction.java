package com.ultrapower.eoms.ultrasm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleOrgShow;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class RoleOrgManagerAction extends BaseAction {
	private RoleManagerService roleManagerService;
	private List<RoleOrgShow> roShowList;
	
	/**
	 * 获得角色的组织列表（人或者部门）
	 * @return
	 */
	public String roleOrgList()
	{
		//roShowList = roleManagerService.getRoleOrg(null);
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userId = "";
		if(userSession != null)
			userId = userSession.getPid();
		List<String> roleIDList = roleManagerService.getAllRoleIdByUserID(userId);
		List<RoleInfo> lst = roleManagerService.getRoleByID(roleIDList);
		StringBuffer roleids = new StringBuffer();
		Map map = new HashMap();
		for(RoleInfo role:lst)
		{
			roleids.append(role.getRoledns()+",");
		}
		if(roleids.lastIndexOf(",")!=-1)
		{
			map.put("roledns", roleids);
			this.getRequest().setAttribute("valuemap", map);
		}
		return SUCCESS;
	}
	
	/**
	 * 保存角色组织信息（人或者部门）
	 * @return
	 */
	public String roleOrgSave()
	{
		String roleids = this.getRequest().getParameter("roleids");
		String depids = this.getRequest().getParameter("depids");
		String userids = this.getRequest().getParameter("userids");
		List roleIdList;
		List userIdList;
		List depIdList;
		if(roleids==null||"".equals(roleids))
		{
			roleIdList = null;
		}
		else
		{
			roleIdList = UltraSmUtil.arrayToList(roleids.split(","));
		}
		if(depids==null||"".equals(depids))
		{
			depIdList = null;
		}
		else
		{
			depIdList = UltraSmUtil.arrayToList(depids.split(","));
		}
		if(userids==null||"".equals(userids))
		{
			userIdList = null;
		}
		else
		{
			userIdList = UltraSmUtil.arrayToList(userids.split(","));
		}
		this.getRequest().setAttribute("parafresh","true");
		if(roleManagerService.addRoleOrg(roleIdList, userIdList, depIdList))
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveSuccess")+"!");
		}
		else
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveErr")+"!");
		}
		return "refresh";
	}
	
	/**
	 * 删除角色组织
	 * @return
	 */
	public String delRoleOrg()
	{
		String ids = this.getRequest().getParameter("var_selectvalues"); 
		if(ids==null || "".equals(ids))
		{
			return this.findRedirectPar("roleOrgList.action?id="+this.getRequest().getParameter("id"));
		}
		roleManagerService.deleteRoleOrgByID(UltraSmUtil.arrayToList(ids.split(",")));
		return this.findRedirectPar("roleOrgList.action?id="+this.getRequest().getParameter("id"));
	}
	
	/*
	 * 以下方法为属性的get/set方法
	 */
	public List<RoleOrgShow> getRoShowList() 
	{
		return roShowList;
	}
	public void setRoShowList(List<RoleOrgShow> roShowList) 
	{
		this.roShowList = roShowList;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) 
	{
		this.roleManagerService = roleManagerService;
	}
	
	
}
