package com.ultrapower.eoms.ultrasm.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * FieldInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_FIELD")
public class FieldInfo implements java.io.Serializable {

	// Fields

	private String pid;
	private String enname;
	private String field;
	private String fieldname;
	private String fieldtype;
	private Long length;
	private Long precision;
	private Long isrequired;
	private String defaultvalue;
	private String dtcode;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private String remark;

	// Constructors

	/** default constructor */
	public FieldInfo() {
	}

	/** minimal constructor */
	public FieldInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public FieldInfo(String pid, String enname, String field, String fieldname,
			String fieldtype, Long length, Long precision, Long isrequired,
			String defaultvalue, String dtcode, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime, String remark) {
		this.pid = pid;
		this.enname = enname;
		this.field = field;
		this.fieldname = fieldname;
		this.fieldtype = fieldtype;
		this.length = length;
		this.precision = precision;
		this.isrequired = isrequired;
		this.defaultvalue = defaultvalue;
		this.dtcode = dtcode;
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

	@Column(name = "ENNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getEnname() {
		return this.enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	@Column(name = "FIELD", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Column(name = "FIELDNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	@Column(name = "FIELDTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getFieldtype() {
		return this.fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	@Column(name = "LENGTH", unique = false, nullable = true, insertable = true, updatable = true, precision = 3, scale = 0)
	public Long getLength() {
		return this.length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	@Column(name = "PRECISION", unique = false, nullable = true, insertable = true, updatable = true, precision = 3, scale = 0)
	public Long getPrecision() {
		return this.precision;
	}

	public void setPrecision(Long precision) {
		this.precision = precision;
	}

	@Column(name = "ISREQUIRED", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsrequired() {
		return this.isrequired;
	}

	public void setIsrequired(Long isrequired) {
		this.isrequired = isrequired;
	}

	@Column(name = "DEFAULTVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	@Column(name = "DTCODE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDtcode() {
		return this.dtcode;
	}

	public void setDtcode(String dtcode) {
		this.dtcode = dtcode;
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