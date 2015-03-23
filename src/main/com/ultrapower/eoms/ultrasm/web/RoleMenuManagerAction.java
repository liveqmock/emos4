package com.ultrapower.eoms.ultrasm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleMenuShow;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class RoleMenuManagerAction extends BaseAction {
	private RoleManagerService roleManagerService;
	private List<RoleMenuShow> roleMenuShowList;
	
	/**
	 * 获取所有角色目录树列表
	 * @return
	 */
	public String roleMenuTreeList()
	{
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userId = "";
		if(userSession != null)
			userId = userSession.getPid();
		List<RoleInfo> lst = roleManagerService.getRoleByUserID(userId);
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
	 * 保存角色的菜单目录树
	 * @return
	 */
	public String saveRoleMenuTree()
	{
		String roleids = this.getRequest().getParameter("roleids");
		String menuids = this.getRequest().getParameter("menuids");
		this.getRequest().setAttribute("parafresh","true");
		if(roleManagerService.addRoleMenu(roleids, UltraSmUtil.arrayToList(menuids.split(","))))
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
	 * 删除角色菜单树
	 * @return
	 */
	public String delRoleMenuTree()
	{
		String delIds = this.getRequest().getParameter("var_selectvalues");
		if(delIds==null||"".equals(delIds))
		{
			return this.findRedirectPar("roleMenuTreeList.action?id="+this.getRequest().getParameter("id"));
		}
		String[] idArr = delIds.split(",");
		roleManagerService.deleteRoleMenuByID(UltraSmUtil.arrayToList(idArr));
		return this.findRedirectPar("roleMenuTreeList.action?id="+this.getRequest().getParameter("id"));
	}
	
	/*
	 * 以下为属性的get/set方法区域
	 */
	public void setRoleManagerService(RoleManagerService roleManagerService) 
	{
		this.roleManagerService = roleManagerService;
	}

	public List<RoleMenuShow> getRoleMenuShowList() 
	{
		return roleMenuShowList;
	}

	public void setRoleMenuShowList(List<RoleMenuShow> roleMenuShowList) 
	{
		this.roleMenuShowList = roleMenuShowList;
	}
	
}
