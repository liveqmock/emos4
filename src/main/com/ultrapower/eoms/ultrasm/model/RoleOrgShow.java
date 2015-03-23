package com.ultrapower.eoms.ultrasm.model;

/**
 * 此类用来页面显示用
 * @author 孙海龙
 */
public class RoleOrgShow
{
	private String pid;			//角色组织ID
	private String roleid;		//角色ID
	private String rolename;	//角色名称
	private String orgid;		//组织ID
	private String orgname;		//组织名称
	private String orgtype;		//组织类型
	private String roletype;	//自定义类型
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
}
