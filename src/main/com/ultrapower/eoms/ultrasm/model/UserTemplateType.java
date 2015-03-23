package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_SM_USERTEMPLATETYPE")
public class UserTemplateType {
	
	// Fields
	
	private String pid;
	private String utid;
	private String uttype;
	private String typemark;
	private String typename;
	
	// Constructors
	
	/** default constructor */
	public UserTemplateType() {
		
	}
	
	/** minimal constructor */
	public UserTemplateType(String pid) {
		this.pid = pid;
	}
	
	/** full constructor */
	public UserTemplateType(String pid, String utid, String uttype, String typemark, String typename) {
		this.pid = pid;
		this.utid = utid;
		this.uttype = uttype;
		this.typemark = typemark;
		this.typename = typename;
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

	@Column(name = "UTTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getUttype() {
		return uttype;
	}

	public void setUttype(String uttype) {
		this.uttype = uttype;
	}

	@Column(name = "TYPEMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getTypemark() {
		return typemark;
	}

	public void setTypemark(String typemark) {
		this.typemark = typemark;
	}

	@Column(name = "TYPENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 150)
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
}

