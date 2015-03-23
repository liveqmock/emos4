package com.ultrapower.eoms.common.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BS_T_SM_USERLOGININFO")
public class UserLoginInfo {
	private String pid;
	private String loginname;
	private String logindate;
	private String firstlogintime;
	private String lastlogouttime;
	private String firstloginip;
	private String lastlogoutip;
	private String logintype;
	
	public UserLoginInfo()
	{
		
	}
	
	public UserLoginInfo(String pid)
	{
		this.pid = pid;
	}
	
	public UserLoginInfo(String pid, String loginname, String logindate, String firstlogintime, String lastlogouttime, String firstloginip, String lastlogoutip, String logintype)
	{
		this.pid = pid;
		this.loginname = loginname;
		this.logindate = logindate;
		this.firstlogintime = firstlogintime;
		this.lastlogouttime = lastlogouttime;
		this.firstloginip = firstloginip;
		this.lastlogoutip = lastlogoutip;
		this.logintype = logintype;
	}
	
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=50)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Column(name="LOGINNAME", unique=false, nullable=true, insertable=true, updatable=true, length=60)
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	@Column(name="LOGINDATE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getLogindate() {
		return logindate;
	}
	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}
	@Column(name="FIRSTLOGINTIME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getFirstlogintime() {
		return firstlogintime;
	}
	public void setFirstlogintime(String firstlogintime) {
		this.firstlogintime = firstlogintime;
	}
	@Column(name="LASTLOGOUTTIME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getLastlogouttime() {
		return lastlogouttime;
	}
	public void setLastlogouttime(String lastlogouttime) {
		this.lastlogouttime = lastlogouttime;
	}
	@Column(name="FIRSTLOGINIP", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getFirstloginip() {
		return firstloginip;
	}
	public void setFirstloginip(String firstloginip) {
		this.firstloginip = firstloginip;
	}
	@Column(name="LASTLOGOUTIP", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getLastlogoutip() {
		return lastlogoutip;
	}
	public void setLastlogoutip(String lastlogoutip) {
		this.lastlogoutip = lastlogoutip;
	}
	@Column(name="LOGINTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=10)
	public String getLogintype() {
		return logintype;
	}
	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}
}
