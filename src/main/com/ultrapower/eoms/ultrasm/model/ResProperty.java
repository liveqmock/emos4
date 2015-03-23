package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ResProperty entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_RESPROPERTY")
public class ResProperty implements java.io.Serializable {

	// Fields

	private String pid;
	private String respid;
	private String fieldname;
	private String fielddisplayvalue;
	private Long intype;
	private Long invaluetype;
	private Long indatasourtype;
	private String indata;
	private Long ordernum;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private String remark;

	// Constructors

	/** default constructor */
	public ResProperty() {
	}

	/** minimal constructor */
	public ResProperty(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public ResProperty(String pid, String respid, String fieldname,
			String fielddisplayvalue, String operator, Long intype,
			Long invaluetype, Long indatasourtype, String indata,
			Long ordernum, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime, String remark) {
		this.pid = pid;
		this.respid = respid;
		this.fieldname = fieldname;
		this.fielddisplayvalue = fielddisplayvalue;
		this.intype = intype;
		this.invaluetype = invaluetype;
		this.indatasourtype = indatasourtype;
		this.indata = indata;
		this.ordernum = ordernum;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.remark = remark;
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

	@Column(name = "RESPID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRespid() {
		return this.respid;
	}

	public void setRespid(String respid) {
		this.respid = respid;
	}

	@Column(name = "FIELDNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	@Column(name = "FIELDDISPLAYVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getFielddisplayvalue() {
		return this.fielddisplayvalue;
	}

	public void setFielddisplayvalue(String fielddisplayvalue) {
		this.fielddisplayvalue = fielddisplayvalue;
	}

	@Column(name = "INTYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIntype() {
		return this.intype;
	}

	public void setIntype(Long intype) {
		this.intype = intype;
	}

	@Column(name = "INVALUETYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getInvaluetype() {
		return this.invaluetype;
	}

	public void setInvaluetype(Long invaluetype) {
		this.invaluetype = invaluetype;
	}

	@Column(name = "INDATASOURTYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIndatasourtype() {
		return this.indatasourtype;
	}

	public void setIndatasourtype(Long indatasourtype) {
		this.indatasourtype = indatasourtype;
	}

	@Column(name = "INDATA", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
	public String getIndata() {
		return this.indata;
	}

	public void setIndata(String indata) {
		this.indata = indata;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
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