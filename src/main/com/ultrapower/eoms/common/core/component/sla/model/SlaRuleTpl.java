package com.ultrapower.eoms.common.core.component.sla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SlaRuleTpl entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_RULETPL")
public class SlaRuleTpl implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -3825477554327013018L;
	private String pid;
	private String ruletemplatename;
	private String tplmark;
	private String implclass;
	private String systemtype;
	private String datasource;
	private Long status;
	private Long ordernum;
	private String describe;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public SlaRuleTpl() {
	}

	/** minimal constructor */
	public SlaRuleTpl(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SlaRuleTpl(String pid, String ruletemplatename, String tplmark, String implclass, String systemtype,
			String datasource, Long status, Long ordernum, String describe,
			String creater, Long createtime, String lastmodifier,
			Long lastmodifytime) {
		this.pid = pid;
		this.ruletemplatename = ruletemplatename;
		this.tplmark = tplmark;
		this.implclass = implclass;
		this.systemtype = systemtype;
		this.datasource = datasource;
		this.status = status;
		this.ordernum = ordernum;
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

	@Column(name = "RULETEMPLATENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getRuletemplatename() {
		return this.ruletemplatename;
	}

	public void setRuletemplatename(String ruletemplatename) {
		this.ruletemplatename = ruletemplatename;
	}
	
	
	@Column(name = "TPLMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getTplmark() {
		return tplmark;
	}

	public void setTplmark(String tplmark) {
		this.tplmark = tplmark;
	}
	
	@Column(name = "IMPLCLASS", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getImplclass() {
		return implclass;
	}

	public void setImplclass(String implclass) {
		this.implclass = implclass;
	}

	@Column(name = "SYSTEMTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getSystemtype() {
		return this.systemtype;
	}

	public void setSystemtype(String systemtype) {
		this.systemtype = systemtype;
	}

	@Column(name = "DATASOURCE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDatasource() {
		return this.datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
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