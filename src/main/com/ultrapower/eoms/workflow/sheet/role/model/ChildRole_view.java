package com.ultrapower.eoms.workflow.sheet.role.model;

/**
 * 本对象只用与页面显示
 * 
 * @author dulylau
 */
public class ChildRole_view {

	private String childRoleId;
	private String childRoleCode;
	private String childRoleName;
	private String roleCode;
	private Long matchCount;
	private String childRoleDesc;
	private String loginNames;
	private String fullNames;
	private String dimensionValue;
	private String depName;
	

	public String getChildRoleId() {
		return childRoleId;
	}

	public void setChildRoleId(String childRoleId) {
		this.childRoleId = childRoleId;
	}

	public String getChildRoleCode() {
		return childRoleCode;
	}

	public void setChildRoleCode(String childRoleCode) {
		this.childRoleCode = childRoleCode;
	}

	public String getChildRoleName() {
		return childRoleName;
	}

	public void setChildRoleName(String childRoleName) {
		this.childRoleName = childRoleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Long getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(Long matchCount) {
		this.matchCount = matchCount;
	}

	public String getChildRoleDesc() {
		return childRoleDesc;
	}

	public void setChildRoleDesc(String childRoleDesc) {
		this.childRoleDesc = childRoleDesc;
	}


	public String getLoginNames() {
		return loginNames;
	}

	public void setLoginNames(String loginNames) {
		this.loginNames = loginNames;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getDimensionValue() {
		return dimensionValue;
	}

	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}

	public String getDepName()
	{
		return depName;
	}

	public void setDepName(String depName)
	{
		this.depName = depName;
	}



}