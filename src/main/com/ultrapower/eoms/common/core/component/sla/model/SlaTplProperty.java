package com.ultrapower.eoms.common.core.component.sla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SlaTplProperty entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_RULETPLPROPERTY")
public class SlaTplProperty implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 6197268405114555063L;
	private String pid;
	private String ruletplpid;
	private String fieldid;
	private String fieldname;
	private Long inputtype;
	private Long inputvaluetype;
	private Long inputdatasourcetype;
	private String indata;
	private Long status;
	private Long ordernum;
	private String describe;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public SlaTplProperty() {
	}

	/** minimal constructor */
	public SlaTplProperty(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SlaTplProperty(String pid, String ruletplpid, String fieldid,
			String fieldname, Long inputtype, Long inputvaluetype,
			Long inputdatasourcetype, String indata, Long status,
			Long ordernum, String describe, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime) {
		this.pid = pid;
		this.ruletplpid = ruletplpid;
		this.fieldid = fieldid;
		this.fieldname = fieldname;
		this.inputtype = inputtype;
		this.inputvaluetype = inputvaluetype;
		this.inputdatasourcetype = inputdatasourcetype;
		this.indata = indata;
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

	@Column(name = "RULETPLPID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRuletplpid() {
		return this.ruletplpid;
	}

	public void setRuletplpid(String ruletplpid) {
		this.ruletplpid = ruletplpid;
	}

	@Column(name = "FIELDID", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getFieldid() {
		return this.fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	@Column(name = "FIELDNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	@Column(name = "INPUTTYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getInputtype() {
		return this.inputtype;
	}

	public void setInputtype(Long inputtype) {
		this.inputtype = inputtype;
	}

	@Column(name = "INPUTVALUETYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getInputvaluetype() {
		return this.inputvaluetype;
	}

	public void setInputvaluetype(Long inputvaluetype) {
		this.inputvaluetype = inputvaluetype;
	}

	@Column(name = "INPUTDATASOURCETYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getInputdatasourcetype() {
		return this.inputdatasourcetype;
	}

	public void setInputdatasourcetype(Long inputdatasourcetype) {
		this.inputdatasourcetype = inputdatasourcetype;
	}

	@Column(name = "INDATA", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getIndata() {
		return this.indata;
	}

	public void setIndata(String indata) {
		this.indata = indata;
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