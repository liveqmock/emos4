package com.ultrapower.eoms.workflow.sheet.role.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.WorkflowRole;
import com.ultrapower.workflow.role.service.IRoleService;

/**
 * 工单角色细分维度值配置（Web层action）
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-05-31 当前版本：v1.0 web
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class SheetRoleTreeImpl extends TreeManager {
	
	/**
	 * 工单角色管理类
	 */
	private IRoleService roleService;
	


	/**
	 * 角色与角色细分树（1）
	 * 获取角色
	 * @return
	 */
	public String getWfRole_treeXML(String wfVersion,String pid){
		List<WorkflowRole> list = this.roleService.getWfRoleBySchema(wfVersion);
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		for(WorkflowRole wf:list){
			DtreeBean dt = new DtreeBean();
			dt.setId(wf.getRolecode());
			dt.setText(wf.getRolename());
			dt.setNocheckbox("1");
			HashMap map = new HashMap();
			map.put("type","R");
			map.put("text",wf.getRolename());
			dt.setUserdata(map);
			dtreeList.add(dt);
		}
		
		DtreeBean dTree = new DtreeBean();
		dTree.setParentId(pid);
		dTree.setChildList(dtreeList);
		return this.createDhtmlxtreeXml(dtreeList, pid);
	}
	
	/**
	 * 角色与角色细分树（2）
	 * 获取角色细分
	 * @return
	 */
	public String getWfChildRole_treeXML(String roleCode,String pid){
		List<ChildRole> list = this.roleService.getChildRoleListByRoleCode(roleCode);
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		for(ChildRole wf:list){
			DtreeBean dt = new DtreeBean();
			dt.setId(wf.getChildRoleId());
			dt.setText(wf.getChildRoleName());
			HashMap map = new HashMap();
			map.put("type","crole");
			map.put("text",wf.getChildRoleName());
			dt.setUserdata(map);
			dt.setChild("");
			dtreeList.add(dt);
		}
		DtreeBean dTree = new DtreeBean();
		dTree.setParentId(pid);
		dTree.setChildList(dtreeList);
		
		return this.createDhtmlxtreeXml(dtreeList, pid);
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
	

}

