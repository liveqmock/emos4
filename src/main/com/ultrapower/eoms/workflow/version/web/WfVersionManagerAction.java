package com.ultrapower.eoms.workflow.version.web;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.workflow.util.PageLimitUtil;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.version.model.WfVersion;
import com.ultrapower.workflow.role.model.WorkflowRole;
import com.ultrapower.workflow.role.service.IRoleService;
/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author：liuchuanzu
 * @date 2010-05-31
 * 当前版本：v1.0
 * 流程版本管理 web
 */
public class WfVersionManagerAction extends BaseAction{
	
	// 流程版本list
	private List<WfVersion> versionList;
	
	//	流程类型标识
	private String baseCode;
	
	//	流程类型名称
	private String baseName;
	
	//	流程版本标识
	private String code;
	
	//	流程版本ID
	private String wfVersionId;
	
	/**
	 * 角色维度管理类
	 */
	private IRoleService roleService;

	// 流程版本控制service对象
	private IWfVersionManager ver;
	

	private IWfSortManager verSort;
	
	/**
	 * 目录树管理对象
	 */
	private MenuManagerService menuManagerService;
	private RoleManagerService roleManagerService;

	
	public RoleManagerService getRoleManagerService() {
		return roleManagerService;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}

	public MenuManagerService getMenuManagerService() {
		return menuManagerService;
	}

	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}

	/**
	 * 获取流程版本列表
	 * @return
	 */
	public String list(){
		try {
			ver = WorkFlowServiceClient.clientInstance().getVersionService();
			this.versionList = ver.getListByBaseCode(baseCode);
			Collections.sort(versionList,new Comparator<WfVersion>(){

				public int compare(WfVersion o1, WfVersion o2) {
					if (o1 != null && o2 != null) {
						long t1 = o1.getCreateTime();
						long t2 = o2.getCreateTime();
						if (t1 > t2) {
							return -1;
						} else if(t1 < t2) {
							return 1;
						}
					}
					return 0;
				}
			});
			versionList = PageLimitUtil.pageLimit(versionList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return this.findForward("versionList");
	}
	
	/**
	 * 启用流程版本
	 * @return
	 */
	public String startWf(){
		try {
			if(StringUtils.isNotBlank(wfVersionId)){
				ver = WorkFlowServiceClient.clientInstance().getVersionService();
				WfVersion wf = ver.getById(wfVersionId);
				List<WfVersion> list = ver.getListByBaseCode(wf.getBaseCode());
				for(WfVersion w:list){
					if(w.getId().equals(this.wfVersionId)){
						ver.startVersion(wfVersionId);
//						verSort = WorkFlowServiceClient.clientInstance().getSortService();
//						WfType wft = verSort.getWfTypeByCode(w.getBaseCode());
//						wft.setWfDefaultVersion(w.getCode());
//						verSort.saveWfType(wft);
					}else{
						if(w.getIsPublish() == 1){
							ver.stopVersion(w.getId());
						}
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "toList";
	}
	
	
	/**
	 * 停用流程版本
	 * @return
	 */
	public String stopWf(){
		try {
			if(wfVersionId!=null){
				ver = WorkFlowServiceClient.clientInstance().getVersionService();
				boolean bl = ver.stopVersion(wfVersionId);
				WfVersion wf = ver.getById(wfVersionId);
				verSort = WorkFlowServiceClient.clientInstance().getSortService();
				WfType wft = verSort.getWfTypeByCode(wf.getBaseCode());
				wft.setWfDefaultVersion("");
				verSort.saveWfType(wft);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "toList";
	}
	
	/**
	 * 停用流程版本
	 * @return
	 */
	public String delWf(){
		try {
			ver = WorkFlowServiceClient.clientInstance().getVersionService();
			WfVersion wf = ver.getById(wfVersionId);
			boolean bl = ver.removeById(wfVersionId);
			//删除流程版本时，删除相应信息：1、目录树上的相应节点；2、删除相应的工单角色；
			if(bl){
				this.delMenu(wf);
				this.delWfRole(wf);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "toList";
	}
	
	/**
	 * 删除目录树；
	 * 删除流程分类时，需要更新“工单管理”和“流程管理”中的相应目录
	 */
	public void delMenu(WfVersion wf){
		log.info("删除’"+wf.getBaseName()+"->配置工单角色‘下相应目录树上节点（开始）.....");
		String menuCode_version = "wf_sheetcrole_" + wf.getCode();
		menuManagerService.deleteMenuByMark(menuCode_version);
		log.info("删除’"+wf.getBaseName()+"->配置工单角色‘下相应目录树上节点（结束）");
	}
	
	/**
	 * 删除版本时，删除相应的角色及相关信息
	 */
	public void delWfRole(WfVersion wf){
		List<WorkflowRole> roleList = this.roleService.getRoleByVersionCode(wf.getCode());
		for(WorkflowRole wr:roleList){
			this.roleService.delWfRoleByRoleCode(wr.getRolecode(), wr.getBaseversion());
		}
	}
	
	/**
	 * 添加目录树（在“流程管理-》流程综合管理-》流程分类-》流程类型-》配置工单角色”添加"配置**工单角色“节点）
	 */
	public void addWfRoleMenu(WfVersion wfVersion){
		
		if(wfVersion!=null){
			List<String> menuIdList = new ArrayList<String>();
			MenuInfo oldMenu = this.menuManagerService.getMenuByMark("wf_sheetrole_"+wfVersion.getBaseCode());
			if(oldMenu!=null){
				//流程版本控制下目录树
				MenuInfo newMenu = new MenuInfo();
				newMenu.setNodename(wfVersion.getName()+"版本角色配置");
				newMenu.setNodetype(Long.parseLong("1"));
				newMenu.setNodeurl("sheetrole/getWfRole.action?baseSchema=" + wfVersion.getBaseCode() + "&baseVersion=" + wfVersion.getCode());
				newMenu.setNodemark("wf_sheetcrole_" + wfVersion.getCode());
				newMenu.setOpenway("1");
				newMenu.setStatus(Long.parseLong("1"));
				newMenu.setParentid(oldMenu.getPid());
				
				MenuInfo m = this.menuManagerService.getMenuByMark("wf_sheetcrole_" + wfVersion.getCode());
				if(m != null){
					newMenu.setPid(m.getPid());
					this.menuManagerService.updateMenuInfo(newMenu);
				}else{
					newMenu.setCreatetime(TimeUtils.getCurrentTime());
					this.menuManagerService.addMenuInfo(newMenu);
				}
				menuIdList.add(newMenu.getPid());
			}
			this.addRoleAndMenu(menuIdList);
		}
	}
	
	/**
	 * 给新增目录节点授权
	 * @param menuIdList
	 */
	public void addRoleAndMenu(List<String> menuIdList){
		List<String> roleIdList = roleManagerService.getRoleIdByUserID(this.getUserSession().getPid());
		this.roleManagerService.addRoleMenu(roleIdList, menuIdList);
	}
	public List<WfVersion> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<WfVersion> versionList) {
		this.versionList = versionList;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getWfVersionId() {
		return wfVersionId;
	}

	public void setWfVersionId(String wfVersionId) {
		this.wfVersionId = wfVersionId;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
