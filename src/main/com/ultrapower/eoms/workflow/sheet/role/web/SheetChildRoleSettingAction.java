package com.ultrapower.eoms.workflow.sheet.role.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;
import com.ultrapower.workflow.role.model.DimensionValue;
import com.ultrapower.workflow.role.model.RoleMatchDimension;
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
public class SheetChildRoleSettingAction extends BaseAction {
	
	/**
	 * 工单角色管理类
	 */
	private IRoleService roleService;
	
	//维度对象
	private Dimension dimension;
	
	//维度对象code
	private String dimensionCode;
	
	//维度集合
	private List<Dimension> demensionList;
	
	//角色维度关联
	private List<RoleMatchDimension> roleDimensionList;
	
	//维度值集合
	private List<DimensionValue> dimensionValueList;
	
	//维度值
	private String dimensionValues;
	
	//维度值层数，如:业务平台.流媒体.流媒体,三层
	private int level;
	
	private String childRoleId;
	
	private List<ChildRole> childRoleList;
	
	//角色细分code,name
	private String cRoleCode;
	private String cRoleName;
	
	//角色code
	private WorkflowRole wfRole;
	private String roleCode;
	
	
	//流程版本
	private String wfVersion;
	
	//流程环节号
	private String processNo;
	
	//用户名
	private String userName;
	
	/**
	 * 工单角色树对象
	 */
	private SheetRoleTreeImpl sheetRoleTreeImpl;
	

	/**
	 * 跳转至角色细分
	 */
	public String toChildRoleSetting(){
		this.wfRole = this.roleService.getWfRoleByCode(roleCode);
		this.roleDimensionList = this.roleService.getRoleMatchDemensionByRoleCode(roleCode);
		if(roleDimensionList == null || roleDimensionList.size() == 0){
			this.getRequest().setAttribute("message","本角色未设置维度，请先设置维度！");
			return "result";
		}
		return this.findForward("childRoleSettting");
	}
	
	/**
	 * 获取维度值
	 * @return
	 */
	public String getDimensionValue(){
		this.dimension = this.roleService.getDimensionByCode(this.dimensionCode);
		dimensionValueList = this.roleService.getDimensionValue(dimension);
		
		//遍历获取维度值的层数
		for(DimensionValue d:this.dimensionValueList){
			String[] values = d.getValue().split("\\.");
			if(values.length>level){
				level = values.length;
			}
		}
		return this.findForward("dimensionValue");
	}
	
	/**
	 * 批量添加角色细分
	 * @return
	 */
	public String saveDimensionBatch(){

		System.out.println(this.dimensionValues);
		Map<String,String> valuemap = new HashMap<String, String>();
		Map<String,String> namemap = new HashMap<String, String>();
		String[] values = this.dimensionValues.split("#");
		for(int i=0;i<values.length;i++){
			String st = values[i];
			String[] str = st.split(":_:");
			valuemap.put(str[0]+"_"+i,str[1]);
			if (StringUtils.isNotBlank(str[2])) {
				String[] s = str[2].split("::");
				if (s != null && s.length > 0) {
					namemap.put(s[0], s[1]);
				}
			}
		}

		this.roleDimensionList = this.roleService.getRoleMatchDemensionByRoleCode(roleCode);
//		String name = "";
//		for(RoleMatchDimension r:roleDimensionList){
//			Dimension d = this.wfRoleManager.getDimensionByCode(r.getDimensioncode());
//			name += ".[" + d.getDimensionname() + "]";
//		}
//		if(!"".equals(name)){
//			name = name.substring(1);
//		}
		
		int level = roleDimensionList.size()-1;
		
		Map<String, String> roleValueMap = new HashMap<String, String>();
		String keyDef = "#";//用来定义map中的key,如level = 4,key:4#1,4#2.....
		for(;level>=0;level--){
			Map tempMap = new HashMap<String,String>();
			String dCode = this.roleDimensionList.get(level).getDimensioncode();
			int tempInt = 1;
			for(String key:valuemap.keySet()){
				if(key.indexOf(dCode)!=-1){
					if(roleValueMap.size() == 0){
						tempMap.put(level+keyDef+tempInt, valuemap.get(key));
						tempInt++;
					}else{
						for(String k:roleValueMap.keySet()){
							tempMap.put(level+keyDef+tempInt, valuemap.get(key)+"%#"+roleValueMap.get(k));
							tempInt++;
						}
					}
				}
			}
			if(tempInt != 1){
				roleValueMap.clear();
				roleValueMap.putAll(tempMap);
			}
		}
		if(roleValueMap.keySet().size() == 1 && this.childRoleId!=null && !"".equals(this.childRoleId)){
			ChildRole r = this.roleService.getChildRoleById(id);
			if(r!=null){
				r.setDimensionValue(roleValueMap.get(roleValueMap.keySet().toArray()[0]));
			}
		}else{
			int i = 0;
			for(String value:roleValueMap.values()){
				ChildRole cr = new ChildRole();
				StringBuffer names = new StringBuffer();
				if(value!= null && !"".equals(value)){
					String[] vs = value.split("%#");
					for(String str:vs){
//						String[] strs = str.split("=");
//						if(strs.length == 2){
//							names.append("["+strs[1]+"]");
//						}
						names.append("[" + namemap.get(str) + "]");
					}
				}
				cr.setRoleCode(roleCode);
				cr.setMatchCount(Long.parseLong(value.split("%#").length+""));
				cr.setDimensionValue(value);
				cr.setChildRoleName(names.toString()+"["+wfRole.getRolename()+"]");
				this.roleService.saveChildRole(cr);
				i++;
			}
		}
		
		return "result";
	}

	
	/**
	 * remedy工单在运行时，根据流程版本号和环节ID配置相应的角色细分值
	 * @return
	 */
	public String toChildRoleValueMatch(){
		this.childRoleList = this.roleService.getChildRoleByValue(this.dimensionValues);
		return this.findForward("roleValueMatch2_remedy");
	}
	
	
	/**
	 * remedy工单运行时，根据流程版本号和环节ID，找到相应的维度对象
	 * @return
	 */
	public String getDimenValue(){
		this.wfRole = this.roleService.getWfRoleByVersonAndProcessId(this.wfVersion,this.processNo);
		dimensionValues = "";
		if(wfRole!=null){
			List<RoleMatchDimension> list = this.roleService.getRoleMatchDemensionByRoleCode(this.wfRole.getRolecode());
			for(RoleMatchDimension r:list){
				Dimension d = this.roleService.getDimensionByCode(r.getDimensioncode());
				this.dimensionValues +=  "#" + d.getDimensionname() + "=" + d.getDictfieldid();
			}
			if(!"".equals(this.dimensionValues)){
				this.dimensionValues = this.dimensionValues.substring(1);
			}
		}
		return this.findForward("roleValueMatch1_remedy");
	}
	
	
	/**
	 * 角色与角色细分树（1）
	 * 获取角色
	 * @return
	 */
	public void getWfRole_tree(){
		this.renderXML(sheetRoleTreeImpl.getWfRole_treeXML(this.wfVersion,"0"));
	}
	
	public SheetRoleTreeImpl getSheetRoleTreeImpl() {
		return sheetRoleTreeImpl;
	}

	public void setSheetRoleTreeImpl(SheetRoleTreeImpl sheetRoleTreeImpl) {
		this.sheetRoleTreeImpl = sheetRoleTreeImpl;
	}

	/**
	 * 角色与角色细分树（1）
	 * 获取角色
	 * @return
	 */
	public void getWfRoleChild_tree(){
		this.renderXML(sheetRoleTreeImpl.getWfChildRole_treeXML(this.id,this.id));
	}
	
	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public List<Dimension> getDemensionList() {
		return demensionList;
	}

	public void setDemensionList(List<Dimension> demensionList) {
		this.demensionList = demensionList;
	}

	public List<RoleMatchDimension> getRoleDimensionList() {
		return roleDimensionList;
	}

	public void setRoleDimensionList(List<RoleMatchDimension> roleDimensionList) {
		this.roleDimensionList = roleDimensionList;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDimensionCode() {
		return dimensionCode;
	}

	public void setDimensionCode(String dimensionCode) {
		this.dimensionCode = dimensionCode;
	}

	public List<DimensionValue> getDimensionValueList() {
		return dimensionValueList;
	}

	public void setDimensionValueList(List<DimensionValue> dimensionValueList) {
		this.dimensionValueList = dimensionValueList;
	}

	public String getCRoleCode() {
		return cRoleCode;
	}
	public String getWfVersion() {
		return wfVersion;
	}

	public void setWfVersion(String wfVersion) {
		this.wfVersion = wfVersion;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public void setCRoleCode(String roleCode) {
		cRoleCode = roleCode;
	}

	public String getCRoleName() {
		return cRoleName;
	}

	public void setCRoleName(String roleName) {
		cRoleName = roleName;
	}

	public String getDimensionValues() {
		return dimensionValues;
	}

	public void setDimensionValues(String dimensionValues) {
		this.dimensionValues = dimensionValues;
	}

	public String getChildRoleId() {
		return childRoleId;
	}

	public void setChildRoleId(String childRoleId) {
		this.childRoleId = childRoleId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public WorkflowRole getWfRole() {
		return wfRole;
	}

	public void setWfRole(WorkflowRole wfRole) {
		this.wfRole = wfRole;
	}

	public List<ChildRole> getChildRoleList() {
		return childRoleList;
	}

	public void setChildRoleList(List<ChildRole> childRoleList) {
		this.childRoleList = childRoleList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}




}

