package com.ultrapower.eoms.common.core.component.tree.web;

import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.manager.MenuTreeImpl;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;

/**
 * 左侧菜单树
 * @author 孙海龙
 * @version 2010-6-8 10:00:00 AM
 */
public class MenuTreeAction extends BaseAction{
	private RoleManagerService roleManagerService;
	
	public void getMenuTree() {
		String roleid = this.getRequest().getParameter("roleid");
		MenuTreeImpl menuTreeImpl = new MenuTreeImpl();
		this.renderXML(menuTreeImpl.getChildXml(id,roleid));
	}
	
	public void getAllMenuTree()
	{
		MenuTreeImpl menuTreeImpl = new MenuTreeImpl();
		UserSession userSession = this.getUserSession();
		String userId = userSession.getPid();
		List roleIdList = roleManagerService.getRoleIdByUserID(userId);
		List<MenuDtree> menuDtreeList = roleManagerService.getMenuByRoleID(roleIdList);
		String xmlstr = menuTreeImpl.getMenuTreeXml(menuDtreeList);
		this.renderXML(xmlstr);
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
}
