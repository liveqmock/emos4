package com.ultrapower.eoms.ultrasm.model;

/**
 * 此类用来页面显示用
 * @author 孙海龙
 */
public class RoleResOpShow
{
    private String pid;			//角色资源操作PID
    private String roleid;		//角色ID
    private String rolename;	//角色名称
    private String resid;		//资源ID
    private String resname;		//资源名称
    private String opid;		//操作ID
    private String opname;		//操作名称
    private String status;		//启用状态
    
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
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public String getOpid() {
		return opid;
	}
	public void setOpid(String opid) {
		this.opid = opid;
	}
	public String getOpname() {
		return opname;
	}
	public void setOpname(String opname) {
		this.opname = opname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
