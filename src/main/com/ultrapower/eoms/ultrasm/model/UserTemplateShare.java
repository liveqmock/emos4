package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_SM_USERTEMPLATESHARE")
public class UserTemplateShare {

	// Fields    
	
	private String pid;
	private String utid;
	private String utname;
	private String orgid;
	private Long orgtype;
	private String depdns;

	// Constructors

	/** default constructor */
	public UserTemplateShare() {
		
	}

	/** minimal constructor */
	public UserTemplateShare(String pid) {
		this.pid = pid;
	}
	
	/** full constructor */
	public UserTemplateShare(String pid, String utid, String utname, String orgid, Long orgtype, String depdns) {
		this.pid = pid;
		this.utid = utid;
		this.utname = utname;
		this.orgid = orgid;
		this.orgtype = orgtype;
		this.depdns = depdns;
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
	
	@Column(name = "UTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUtid() {
		return utid;
	}
	public void setUtid(String utid) {
		this.utid = utid;
	}
	
	@Column(name = "UTNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getUtname() {
		return utname;
	}
	public void setUtname(String utname) {
		this.utname = utname;
	}
	
	@Column(name = "ORGID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@Column(name = "ORGTYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(Long orgtype) {
		this.orgtype = orgtype;
	}
	
	@Column(name = "DEPDNS", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDepdns() {
		return depdns;
	}
	public void setDepdns(String depdns) {
		this.depdns = depdns;
	}
}
