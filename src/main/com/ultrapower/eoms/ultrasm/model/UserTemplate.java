package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_SM_USERTEMPLATE")
public class UserTemplate {

	// Fields

	private String pid;
	private String templatename;
	private String uttype;
	private Long isshare;
	private Long status;
	private Long ordernum;
	private String creater;
	private Long createtime;
	private Long lastmodifytime;
	private String remark;
	private String typedata;
	private String userdata;
	private String depdata;
	private String usershare;
	private String depshare;

	// Constructors

	/** default constructor */
	public UserTemplate() {
		
	}

	/** minimal constructor */
	public UserTemplate(String pid) {
		this.pid = pid;
	}
	
	/** full constructor */
	public UserTemplate(String pid, String templatename, String uttype, Long isshare, Long status, Long ordernum, String creater,
			Long createtime, Long lastmodifytime, String remark, String typedata, String userdata, String depdata, String usershare, String depshare) {
		this.pid = pid;
		this.templatename = templatename;
		this.uttype = uttype;
		this.isshare = isshare;
		this.status = status;
		this.ordernum = ordernum;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifytime = lastmodifytime;
		this.remark = remark;
		this.typedata = typedata;
		this.userdata = userdata;
		this.depdata = depdata;
		this.usershare = usershare;
		this.depshare = depshare;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name = "TEMPLATENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getTemplatename() {
		return templatename;
	}
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	
	@Column(name = "UTTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getUttype() {
		return uttype;
	}

	public void setUttype(String uttype) {
		this.uttype = uttype;
	}

	@Column(name = "ISSHARE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsshare() {
		return isshare;
	}
	public void setIsshare(Long isshare) {
		this.isshare = isshare;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "LASTMODIFYTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getLastmodifytime() {
		return lastmodifytime;
	}
	public void setLastmodifytime(Long lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
	
	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "TYPEDATA", unique = false, nullable = true, insertable = true, updatable = true, length = 1500)
	public String getTypedata() {
		return typedata;
	}

	public void setTypedata(String typedata) {
		this.typedata = typedata;
	}

	@Column(name = "USERDATA", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public String getUserdata() {
		return userdata;
	}
	public void setUserdata(String userdata) {
		this.userdata = userdata;
	}
	
	@Column(name = "DEPDATA", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public String getDepdata() {
		return depdata;
	}
	public void setDepdata(String depdata) {
		this.depdata = depdata;
	}
	
	@Column(name = "USERSHARE", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public String getUsershare() {
		return usershare;
	}
	public void setUsershare(String usershare) {
		this.usershare = usershare;
	}
	
	@Column(name = "DEPSHARE", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public String getDepshare() {
		return depshare;
	}
	public void setDepshare(String depshare) {
		this.depshare = depshare;
	}
}
