package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * UserDep entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_USERDEP")
public class UserDep implements java.io.Serializable {

	// Fields    

	private String pid;
	private String userid;
	private String depid;
	private String relatetype;
	private String loginname;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public UserDep() {
	}

	/** minimal constructor */
	public UserDep(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public UserDep(String pid, String userid, String depid, String relatetype,
			String loginname, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime) {
		this.pid = pid;
		this.userid = userid;
		this.depid = depid;
		this.relatetype = relatetype;
		this.loginname = loginname;
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

	@Column(name = "USERID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "DEPID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDepid() {
		return this.depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	@Column(name = "RELATETYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRelatetype() {
		return this.relatetype;
	}

	public void setRelatetype(String relatetype) {
		this.relatetype = relatetype;
	}

	@Column(name = "LOGINNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
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