package com.ultrapower.eoms.common.core.component.tree.web;

import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.manager.RoleTreeImpl;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * 角色树action
 * @author RenChenglin
 */
public class RoleTreeAction extends BaseAction{

	protected String level = "";//查询级别
	private RoleManagerService roleManagerService;
	
	/**
	 * 没有传父角色ID，此时角色ID为0
	 */
	public void getRoleTree(){
		RoleTreeImpl roleTreeImpl = new RoleTreeImpl();
		String roleIds = StringUtils.checkNullString(this.getRequest().getParameter("roleIds"));
		List roleIdList = null;
		if(!"".equals(roleIds))
		{
			roleIdList = UltraSmUtil.arrayToList(roleIds.split(","));
		}
		String xmlstr = roleTreeImpl.getChildXml(id, roleIdList);
		this.renderXML(xmlstr);
	}
	
	/**
	 * 获取该节点以及该节点以下的角色（包含父节点在内，点击一次获取一次）
	 */
	public void getRoleChildrenTree(){
		RoleTreeImpl roleTreeImpl = new RoleTreeImpl();
		String xmlstr = roleTreeImpl.getChildXmlByChidren(id);
		this.renderXML(xmlstr);
	}
	
	/**
	 * 获取当前登录人员的角色树
	 */
	public void getSelfRoleTree()
	{
		RoleTreeImpl roleTreeImpl = new RoleTreeImpl();
		String roleIds = StringUtils.checkNullString(this.getRequest().getParameter("roleIds"));
		List roleIdList = null;
		if(!"".equals(roleIds))
		{
			roleIdList = UltraSmUtil.arrayToList(roleIds.split(","));
			this.renderXML(roleTreeImpl.getSelfXmlRoleTree(roleIdList));
		}
	}
	
	/**
	 * 获取角色菜单目录树
	 */
	public void getRoleMenuTree()
	{
		RoleTreeImpl roleTreeImpl = new RoleTreeImpl();
		List<MenuDtree> menuDtreeList = roleManagerService.getMenuByRoleID(this.getRequest().getParameter("roleid"));
		MenuDtree menuDtree = new MenuDtree();
		menuDtree.setId("0");
		menuDtree.setLevel(1);
		menuDtree.setText("菜单目录");
		menuDtree.setParentid("0");
		menuDtreeList.add(0, menuDtree);
		if(menuDtreeList.size()==0)
		{
			this.renderXML("<?xml version=\"1.0\" encoding=\"utf-8\"?><tree id=\"0\"></tree>");
		}
		else
		{
			this.renderXML(roleTreeImpl.getRoleMenuTreeXml(id,menuDtreeList,1));
		}
	}
	
	/*
	 * get/set方法区域
	 */
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
}
