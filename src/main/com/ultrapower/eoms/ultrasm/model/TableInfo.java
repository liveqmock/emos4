package com.ultrapower.eoms.ultrasm.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TableInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_TABLE")
public class TableInfo implements java.io.Serializable {

	// Fields

	private String pid;
	private String enname;
	private String cnname;
	private String tabletype;
	private Long seqnum;
	private Long ordernum;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private String remark;

	// Constructors

	/** default constructor */
	public TableInfo() {
	}

	/** minimal constructor */
	public TableInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public TableInfo(String pid, String enname, String cnname,
			String tabletype, Long seqnum, Long ordernum, String creater,
			Long createtime, String lastmodifier, Long lastmodifytime,
			String remark) {
		this.pid = pid;
		this.enname = enname;
		this.cnname = cnname;
		this.tabletype = tabletype;
		this.seqnum = seqnum;
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

	@Column(name = "ENNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getEnname() {
		return this.enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	@Column(name = "CNNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getCnname() {
		return this.cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	@Column(name = "TABLETYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getTabletype() {
		return this.tabletype;
	}

	public void setTabletype(String tabletype) {
		this.tabletype = tabletype;
	}

	@Column(name = "SEQNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getSeqnum() {
		return this.seqnum;
	}

	public void setSeqnum(Long seqnum) {
		this.seqnum = seqnum;
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