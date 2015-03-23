package com.ultrapower.eoms.common.core.component.sla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SlaRule entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SLARULE")
public class SlaRule implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -2727925414684845327L;
	private String pid;
	private String rulename;
	private String ruleidentifier;
	private String actionid;
	private Long status;
	private Long ordernum;
	private Long pri;
	private String describe;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public SlaRule() {
	}

	/** minimal constructor */
	public SlaRule(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SlaRule(String pid, String rulename, String ruleidentifier, String actionid, Long status, Long ordernum, Long pri,
			String describe, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime) {
		this.pid = pid;
		this.rulename = rulename;
		this.ruleidentifier = ruleidentifier;
		this.actionid = actionid;
		this.status = status;
		this.ordernum = ordernum;
		this.pri = pri;
		this.describe = describe;
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

	@Column(name = "RULENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	
	@Column(name = "RULEIDENTIFIER", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getRuleidentifier() {
		return ruleidentifier;
	}

	public void setRuleidentifier(String ruleidentifier) {
		this.ruleidentifier = ruleidentifier;
	}

	@Column(name = "ACTIONID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getActionid() {
		return this.actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}
	
	@Column(name = "PRI", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getPri() {
		return this.pri;
	}

	public void setPri(Long pri) {
		this.pri = pri;
	}

	@Column(name = "DESCRIBE", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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