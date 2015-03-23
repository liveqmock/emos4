package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * DepInfo entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_DEP")
public class DepInfo implements java.io.Serializable {

	// Fields    

	private String pid;
	private String depname;
	private String deptype;
	private String parentid;
	private String depfullname;
	private String depdns;
	private String depdn;
	private Long ordernum;
	private String depassginee;
	private String depphone;
	private String depfax;
	private String depemail;
	private Long status;
	private String depimage;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private String remark;
	
	private String parentShow;

	// Constructors

	/** default constructor */
	public DepInfo() {
	}

	/** minimal constructor */
	public DepInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public DepInfo(String pid, String depname, String deptype, String parentid,
			String depfullname, String depdns, String depdn, Long ordernum,
			String depassginee, String depphone, String depfax,
			String depemail, Long status, String depimage, String creater,
			Long createtime, String lastmodifier, Long lastmodifytime,
			String remark) {
		this.pid = pid;
		this.depname = depname;
		this.deptype = deptype;
		this.parentid = parentid;
		this.depfullname = depfullname;
		this.depdns = depdns;
		this.depdn = depdn;
		this.ordernum = ordernum;
		this.depassginee = depassginee;
		this.depphone = depphone;
		this.depfax = depfax;
		this.depemail = depemail;
		this.status = status;
		this.depimage = depimage;
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

	@Column(name = "DEPNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDepname() {
		return this.depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	@Column(name = "DEPTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getDeptype() {
		return this.deptype;
	}

	public void setDeptype(String deptype) {
		this.deptype = deptype;
	}

	@Column(name = "PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "DEPFULLNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getDepfullname() {
		return this.depfullname;
	}

	public void setDepfullname(String depfullname) {
		this.depfullname = depfullname;
	}

	@Column(name = "DEPDNS", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDepdns() {
		return this.depdns;
	}

	public void setDepdns(String depdns) {
		this.depdns = depdns;
	}

	@Column(name = "DEPDN", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getDepdn() {
		return this.depdn;
	}

	public void setDepdn(String depdn) {
		this.depdn = depdn;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "DEPASSGINEE", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getDepassginee() {
		return this.depassginee;
	}

	public void setDepassginee(String depassginee) {
		this.depassginee = depassginee;
	}

	@Column(name = "DEPPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDepphone() {
		return this.depphone;
	}

	public void setDepphone(String depphone) {
		this.depphone = depphone;
	}

	@Column(name = "DEPFAX", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDepfax() {
		return this.depfax;
	}

	public void setDepfax(String depfax) {
		this.depfax = depfax;
	}

	@Column(name = "DEPEMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDepemail() {
		return this.depemail;
	}

	public void setDepemail(String depemail) {
		this.depemail = depemail;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "DEPIMAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDepimage() {
		return this.depimage;
	}

	public void setDepimage(String depimage) {
		this.depimage = depimage;
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
	
	@Transient
	public String getParentShow()
	{
		return this.parentShow;
	}

	public void setParentShow(String parentShow)
	{
		this.parentShow = parentShow;
	}
}