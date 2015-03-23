package com.ultrapower.eoms.workflow.sheet.role.web;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.workflow.sheet.role.model.ChildRole_view;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.version.model.WfVersion;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;
import com.ultrapower.workflow.role.model.RoleMatchDimension;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.model.WorkflowRole;
import com.ultrapower.workflow.role.service.IRoleService;
import com.ultrapower.workflow.utils.UUIDGenerator;

/**
 * 工单角色配置（Web层action）
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-05-31 当前版本：v1.0 web
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class SheetRoleAction extends BaseAction {
	
	/**
	 * 角色
	 */
	private WorkflowRole wfRole;
	private WorkflowRole commonWfRole;
	private List<RoleMatchDimension> roleMdemensionList;
	
	private IWfSortManager ver;
	/**
	 * 工单类型
	 */
	private String baseSchema;
	
	/**
	 * 工单类型
	 */
	private String baseVersion;
	
	/**
	 * 工单名称
	 */
	private String baseName;
	
	private String ids;
	
	/**
	 * 角色细分
	 */
	private ChildRole childRole;

	private String childRoleId;
	
	private String childRoleIds;
	
	/**
	 * 角色code
	 */
	private String roleCode;
	
	private String userName;
	/**
	 * 角色细分组
	 */
	private String groupStr;
	
	//角色细分负责人
	private String charge;
	
	
	/**
	 * 角色维度管理类
	 */
	private IRoleService roleService;
	
	private List<ChildRole_view> childRoleViewList;
	
	private Dimension dimension;
	private List<Dimension> demensionList;
	private String dimensionCodes;
	private String dimensionOrderby;
	
	private String roleId;
	/**
	 * 工单维度查询
	 */
	public String sheetDimensionQuery(){
		HashMap map = new HashMap();
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		this.getRequest().setAttribute("valuemap",map);		
		return this.findForward("dimensionList");
	}
	/**
	 * 工单角色保存、删除与修改
	 */
	public void editWfRole(String roleCodes,String roleNames,String processNos,WfVersion version){	
		String[] rcodes = roleCodes.split("\\#");
		String[] rnames = roleNames.split("\\#");
		String[] rprocesss = processNos.split("\\#");
		CaseInsensitiveMap map = new CaseInsensitiveMap();
		for(int i=0; i <rcodes.length;i++){
			String code = rcodes[i];
			String name = rnames[i];
			String processNo = rprocesss[i];
			WorkflowRole role = (WorkflowRole) map.get(code);
			if(role == null){
				role = new WorkflowRole();
				role.setRolecode(code);
				role.setRolename(name);
				role.setBaseschema(version.getBaseCode());
				role.setBasename(version.getBaseName());
				role.setBaseversion(version.getCode());
				role.setPhaseno("#"+processNo+"#");
			}else{
				role.setPhaseno(role.getPhaseno()+processNo+"#");
			}
			map.put(code,role);
		}
		
		List<WorkflowRole> wrList = this.roleService.getRoleByVersionCode(version.getCode());
		CaseInsensitiveMap oldMap = new CaseInsensitiveMap();
		for(WorkflowRole wr:wrList){
			oldMap.put(wr.getRolecode(),wr);
		}
		
		for(Object key:map.keySet()){
			WorkflowRole newRole = (WorkflowRole) map.get(key.toString());
			String code = newRole.getRolecode();
			String name = newRole.getRolename();
			String processNo = newRole.getPhaseno();
		
			if(code!=null && !"".equals(code)){
				WorkflowRole role = (WorkflowRole) oldMap.get(code);
				if (role == null) {
					role = new WorkflowRole();
					role.setRolecode(code);
					role.setRolename(name);
					role.setBaseschema(version.getBaseCode());
					role.setBasename(version.getBaseName());
					role.setBaseversion(version.getCode());
					role.setPhaseno(processNo);
				}else{
					role.setRolename(name);
					role.setPhaseno(processNo);
					oldMap.remove(code);
				}
				this.roleService.saveOrUpdateRole(role);
			}
		}
		
		if (oldMap != null) {
			Set keySet = oldMap.keySet();
			Iterator iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				WorkflowRole r = (WorkflowRole)oldMap.get(key);
				this.roleService.delWfRoleByRoleCode(r.getRolecode(), r.getBaseversion());
			}
		}
	}
	
	/**
	 * 保存维度查询
	 * @return
	 */
	public String saveDimension(){
		this.baseSchema = this.dimension.getBaseschema();
		this.dimension.setDimensioncode(this.dimension.getDimCode()+"_"+this.dimension.getBaseschema());
		boolean bl = this.roleService.saveOrUpdateDimension(dimension);
		if(!bl){
			this.getRequest().setAttribute("message","维度保存失败!");
		}
		return "result";
	}
	
	public String saveWorkflowRole(){
		if (StringUtils.isBlank(commonWfRole.getRoleid())) {
			commonWfRole.setRoleid(UUIDGenerator.getId());
		}
		commonWfRole.setPhaseno("#"+commonWfRole.getRolecode()+"#");
		commonWfRole.setBaseversion(commonWfRole.getBaseschema());
		this.roleService.saveOrUpdateRole(commonWfRole);
		return "result";
	}
	
	public String addWorkflowRole(){
		return this.findForward("addWorkflowRole");
	}
	
	public String editWorkflowRole() {
		commonWfRole = roleService.getWorkflowRoleById(roleId);
		return this.findForward("addWorkflowRole");
	}
	
	public String removeWorkflowRole() {
		if(ids!=null){
			String[] idvalue = ids.split(",");
			for(String value:idvalue){
				this.roleService.deleteWorkflowRoleById(value);
			}
		}
		return getWfRole();
	}
	
	/**
	 * 跳转至新建或修改查询
	 * @return
	 */
	public String toNewOrUpdate(){
		if (dimension.getDimensionid() != null && !"".equals(dimension.getDimensionid())) {
			dimension = this.roleService.getDimensionById(dimension.getDimensionid());
		} else {
			if (StringUtils.isNotBlank(baseSchema)) {
				WfType wf = null;
				try {
					ver = WorkFlowServiceClient.clientInstance().getSortService();
					wf = ver.getWfTypeByCode(this.baseSchema);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (wf != null) {
					dimension = new Dimension();
					dimension.setBaseschema(baseSchema);
					dimension.setBasename(wf.getName());
				} else {
					this.getRequest().setAttribute("message", "工单类型不存在！");
					return "result";
				}
			}
		}
		return this.findForward("saveDimension");
	}
	
	/**
	 * 删除角维度
	 * @return
	 */
	public String deleteDimensions(){
		if(ids!=null){
			String[] idvalue = ids.split(",");
			for(String value:idvalue){
				this.roleService.deleteDimensionById(value);
			}
		}
		return "getDimensionList";
	}
	
	/**
	 * 角色细分查询(获取用户)
	 * @return
	 */
	public String getChildRoleByRoleCode(){
		
		childRoleViewList = new ArrayList<ChildRole_view>(); 
		List<ChildRole> croleList = roleService.getChildRoleListByRoleCode(roleCode);
		for(ChildRole role:croleList){
			ChildRole_view view = new ChildRole_view();
			try {
				BeanUtils.copyProperties(view,role);
				List<RoleUser> users = roleService.getRoleUserByCroleID(role.getChildRoleId());
				String depName = new String();
				String userName = new String();
				for (RoleUser u : users) {
					//角色细分关联到组
					String depNameTemp = u.getDepName();
					//原角色细分关联到人
					String fullNameTemp = u.getFullName();
					if(fullNameTemp!=null && !"".equals(fullNameTemp)){
						userName += ","+fullNameTemp;
					}else{
						depName += ","+depNameTemp;
					}
				}
				if(depName.length()>0){
					depName = depName.substring(1);
				}
				if(userName.length()>0){
					userName = userName.substring(1);
				}
				view.setChildRoleId(role.getChildRoleId());
				view.setChildRoleName(role.getChildRoleName());
				view.setDimensionValue(role.getDimensionValue().replace("%#", "<br />"));
				view.setMatchCount(role.getMatchCount());
				view.setDepName(depName);
				view.setFullNames(userName);
				childRoleViewList.add(view);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return this.findForward("wfChildRoleList");
	}
	
	/**
	 * 获取工单类型所对应的角色
	 * @return
	 */
	public String getWfRole(){
		HashMap map = new HashMap();
		if (baseVersion != null && !"".equals(baseVersion)) {
			map.put("baseVersion", baseVersion);
		}
		this.getRequest().setAttribute("valuemap", map);
		return this.findForward("wfRoleList");
	}
	
	/**
	 * 跳转角色用户添加页面
	 * @return
	 */
	public String toSetRoleUser(){
			List<RoleUser> userList = this.roleService.getRoleUserByCroleID(this.childRoleId);
			StringBuffer names = new StringBuffer();
			for(RoleUser ru:userList){
				names.append("," + ru.getLoginName() + ":" + ru.getFullName());
			}
			if(!names.toString().equals("")){
				this.userName = names.toString().substring(1);
			}
			ChildRole chiRole = roleService.getChildRoleById(childRoleId);
			if (chiRole != null) {
				this.charge = chiRole.getCharge();
			}
		return this.findForward("roleUser");
	}
	
	/**
	 * 跳转角色用户添加页面
	 * @return
	 */
	public String toSetRoleGroup(){
			List<RoleUser> userList = this.roleService.getRoleUserByCroleID(this.childRoleId);
			StringBuffer names = new StringBuffer();
			for(RoleUser ru:userList){
				String fullName = ru.getFullName();
				if(fullName!=null||"".equals(fullName)){
					names = new StringBuffer();
					break;
				}
				else{
					names.append(",D:" + ru.getDepID());
				}
			}
			if(!names.toString().equals("")){
				this.groupStr = names.toString().substring(1);
			}
			ChildRole chiRole = roleService.getChildRoleById(childRoleId);
			if (chiRole != null) {
				this.charge = chiRole.getCharge();
			}
		return this.findForward("roleGroup");
	}
	
	
	
	/**
	 * 添加角色细分用户
	 * @return
	 */
	public String addUser(){
		if(roleCode!=null && !"".equals(roleCode) && childRoleId!=null && !"".equals(childRoleId)){
			String[] roleIds = childRoleId.split(",");
			for(String cRoleId:roleIds){// 批量
				ChildRole chiRole = roleService.getChildRoleById(cRoleId);
				if (StringUtils.isNotBlank(charge)) {
					chiRole.setCharge(charge);
					roleService.saveChildRole(chiRole);
				}
				List<RoleUser> userList = this.roleService.getRoleUserByCroleID(cRoleId);
				Map<String,RoleUser> oldUserMap = new HashMap<String, RoleUser>();
				for(RoleUser r:userList){
					oldUserMap.put(r.getRoleUserId(),r);
				}
				if(groupStr!=null && !"".equals(groupStr)){
					String[] group = this.groupStr.split(",");
						if(oldUserMap.get(group[0])==null){//新增
							RoleUser role = new RoleUser();
							role.setRoleUserId(UUIDGenerator.getId());
							role.setChildRoleId(cRoleId);
							role.setDepID(group[0]);
							role.setRoleCode(roleCode);
							role.setDepName(group[1]);
							this.roleService.saveOrUpdate(role);
						}else{
							oldUserMap.remove(group[0]);
						}
				}
				if(userName!=null && !"".equals(userName)){
					String[] user = this.userName.split(",");
					for(String s:user){
						String[] users = s.split(":");
						if(oldUserMap.get(users[0])==null){//新增
							RoleUser role = new RoleUser();
							role.setChildRoleId(cRoleId);
							role.setLoginName(users[0]);
							role.setFullName(users[1]);
							role.setRoleCode(roleCode);
							this.roleService.saveOrUpdate(role);
						}else{
							oldUserMap.remove(s);
						}
					}
				}
				
				//删除
				for(String u:oldUserMap.keySet()){
					this.roleService.deleteRoleUserById(oldUserMap.get(u).getRoleUserId());
				}
			}
		}
		return "result";
	}
	
	/**
	 * 跳转至添加角色细分页面
	 * @return
	 */
	public String toAddOrEditChildRole(){
		if(childRoleId == null){
			childRole = new ChildRole();
			childRole.setRoleCode(roleCode);
		}else{
			childRole = this.roleService.getChildRoleById(childRoleId);
		}
		return this.findForward("childRoleSave");
	}
	
	/**
	 * 保存角色细分页面
	 * @return
	 */
	public String saveChildRole(){
		if(childRole!=null && childRole.getDimensionValue()!=null && !"".equals(childRole.getDimensionValue())){
			childRole.setMatchCount(Long.parseLong(childRole.getDimensionValue().split("\\#").length + ""));
		}
		this.roleService.saveChildRole(childRole);
		return "result";
	}
	
	/**
	 * 删除角色细分，具删除配置用户
	 * @return
	 */
	public String removeChildRole(){
		if (childRoleIds != null && !"".equals(childRoleIds)) {
			String[] childRoleId = childRoleIds.split(",");
			for (String str : childRoleId) {
				this.roleService.removeChildRoleById(str);
			}
		}
		return "queryChildRoleViewList";
	}
	
	/**
	 * 跳转维度配置
	 * @return
	 */
	public String toEidtDemension(){
		wfRole = this.roleService.getWfRoleByCode(roleCode);
		roleMdemensionList = this.roleService.getRoleMatchDemensionByRoleCode(roleCode);
		demensionList = this.roleService.getDimensionBySchema(baseSchema);
		return this.findForward("roleDimension");
	}
	
	/**
	 * 角色与维度关联保存
	 * @return
	 */
	public String saveRoleMatchDimension(){
		if(roleCode!=null && !"".equals(roleCode)){
			this.roleService.deleteAllRoleMatchConditionByRoleCode(roleCode);
			if(this.dimensionCodes != null && !"".equals(this.dimensionCodes)){
				String[] codes = dimensionCodes.split(",");
				String[] orderby = this.dimensionOrderby.split(",");
				for(int i=0;i<codes.length;i++){
					String code = codes[i];
					long order = Long.parseLong(orderby[i]);
					RoleMatchDimension r = new RoleMatchDimension();
					r.setRolecode(roleCode);
					r.setDimensioncode(code);
					r.setOrderby(order);
					this.roleService.saveRoleMatchCondition(r);
				}
			}
			
		}
		return "result";
	}

	public List<RoleMatchDimension> getRoleMdemensionList() {
		return roleMdemensionList;
	}

	public void setRoleMdemensionList(List<RoleMatchDimension> roleMdemensionList) {
		this.roleMdemensionList = roleMdemensionList;
	}

	public List<Dimension> getDemensionList() {
		return demensionList;
	}

	public void setDemensionList(List<Dimension> demensionList) {
		this.demensionList = demensionList;
	}

	public void setWfRole(WorkflowRole wfRole) {
		this.wfRole = wfRole;
	}

	public String getBaseSchema() {
		return baseSchema;
	}
	
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public List<ChildRole_view> getChildRoleViewList() {
		return childRoleViewList;
	}

	public void setChildRoleViewList(List<ChildRole_view> childRoleViewList) {
		this.childRoleViewList = childRoleViewList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChildRoleId() {
		return childRoleId;
	}

	public void setChildRoleId(String childRoleId) {
		this.childRoleId = childRoleId;
	}



	public String getChildRoleIds() {
		return childRoleIds;
	}

	public void setChildRoleIds(String childRoleIds) {
		this.childRoleIds = childRoleIds;
	}

	public ChildRole getChildRole() {
		return childRole;
	}

	public void setChildRole(ChildRole childRole) {
		this.childRole = childRole;
	}

	

	public String getBaseVersion() {
		return baseVersion;
	}

	public void setBaseVersion(String baseVersion) {
		this.baseVersion = baseVersion;
	}

	public String getDimensionCodes() {
		return dimensionCodes;
	}

	public void setDimensionCodes(String dimensionCodes) {
		this.dimensionCodes = dimensionCodes;
	}

	public String getDimensionOrderby() {
		return dimensionOrderby;
	}

	public void setDimensionOrderby(String dimensionOrderby) {
		this.dimensionOrderby = dimensionOrderby;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public IRoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public String getGroupStr()
	{
		return groupStr;
	}
	public void setGroupStr(String groupStr)
	{
		this.groupStr = groupStr;
	}
	public WorkflowRole getCommonWfRole() {
		return commonWfRole;
	}
	public void setCommonWfRole(WorkflowRole commonWfRole) {
		this.commonWfRole = commonWfRole;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}




	
}

