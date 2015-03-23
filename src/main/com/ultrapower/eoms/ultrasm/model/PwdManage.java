package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * PwdManage entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_PWDMANAGE")
public class PwdManage implements java.io.Serializable {
	
	// Fields    

	private String pid;
	private String loginname;
	private String pwd;
	private String visiblepwd;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	
	// Constructors

	/** default constructor */
	public PwdManage() {
	}

	/** minimal constructor */
	public PwdManage(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public PwdManage(String pid, String loginname, String pwd, String visiblepwd,
			String creater, Long createtime,
			String lastmodifier, Long lastmodifytime) {
		this.pid = pid;
		this.loginname = loginname;
		this.pwd = pwd;
		this.visiblepwd = visiblepwd;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getPid() {
		return this.pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name = "LOGINNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getLoginname() {
		return this.loginname;
	}
	
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	@Column(name = "PWD", unique = false, nullable = true, insertable = true, updatable = true, length = 120)
	public String getPwd() {
		return this.pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Column(name = "VISIBLEPWD", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getVisiblepwd() {
		return visiblepwd;
	}

	public void setVisiblepwd(String visiblepwd) {
		this.visiblepwd = visiblepwd;
	}

	@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	@Column(name = "LASTMODIFIER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Column(name = "LASTMODIFYTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Long lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
}
