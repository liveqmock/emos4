package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * RoleOrg entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_ROLEORG")
public class RoleOrg implements java.io.Serializable {

	// Fields    

	private String pid;
	private String roleid;
	private String orgid;
	private Long orgtype;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public RoleOrg() {
	}

	/** minimal constructor */
	public RoleOrg(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public RoleOrg(String pid, String roleid, String orgid, Long orgtype,
			String creater, Long createtime, String lastmodifier,
			Long lastmodifytime) {
		this.pid = pid;
		this.roleid = roleid;
		this.orgid = orgid;
		this.orgtype = orgtype;
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

	@Column(name = "ROLEID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Column(name = "ORGID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	@Column(name = "ORGTYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getOrgtype() {
		return this.orgtype;
	}

	public void setOrgtype(Long orgtype) {
		this.orgtype = orgtype;
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