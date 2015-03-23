package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * RoleInfo entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_ROLE")
public class RoleInfo implements java.io.Serializable {

	// Fields    

	private String pid;
	private String rolename;
	private String parentid;
	private String roledns;
	private String roledn;
	private String definetype;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private String remark;

	// Constructors

	/** default constructor */
	public RoleInfo() {
	}

	/** minimal constructor */
	public RoleInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public RoleInfo(String pid, String rolename, String parentid,
			String roledns, String roledn, String definetype, String creater,
			Long createtime, String lastmodifier, Long lastmodifytime,
			String remark) {
		this.pid = pid;
		this.rolename = rolename;
		this.parentid = parentid;
		this.roledns = roledns;
		this.roledn = roledn;
		this.definetype = definetype;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "ROLENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "ROLEDNS", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getRoledns() {
		return this.roledns;
	}

	public void setRoledns(String roledns) {
		this.roledns = roledns;
	}

	@Column(name = "ROLEDN", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getRoledn() {
		return this.roledn;
	}

	public void setRoledn(String roledn) {
		this.roledn = roledn;
	}

	@Column(name = "DEFINETYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDefinetype() {
		return this.definetype;
	}

	public void setDefinetype(String definetype) {
		this.definetype = definetype;
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

	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}