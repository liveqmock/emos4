package com.ultrapower.eoms.ultrasm.model;

/**
 * 此类用来页面显示用
 * @author 孙海龙
 */
public class RoleMenuShow
{
	private String pid;			//角色目录树ID
	private String roleid;		//角色ID
	private String menuid;		//节点ID
	private String rolename;	//角色名称
	private String nodename;	//节点名称
	private String nodemark;	//节点标识
	private String nodeurl;		//节点连接
	
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
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getNodemark() {
		return nodemark;
	}
	public void setNodemark(String nodemark) {
		this.nodemark = nodemark;
	}
	public String getNodeurl() {
		return nodeurl;
	}
	public void setNodeurl(String nodeurl) {
		this.nodeurl = nodeurl;
	}
}
