package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * CustomOrganize entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_CUSTOMORGANIZE")
public class CustomOrganize implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4901343730768158743L;
	private String pid;
	private String custominfopid;
	private Long organizetype;
	private String organizepid;

	// Constructors

	/** default constructor */
	public CustomOrganize() {
	}

	/** minimal constructor */
	public CustomOrganize(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public CustomOrganize(String pid, String custominfopid, Long organizetype,
			String organizepid) {
		this.pid = pid;
		this.custominfopid = custominfopid;
		this.organizetype = organizetype;
		this.organizepid = organizepid;
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

	@Column(name = "CUSTOMINFOPID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCustominfopid() {
		return this.custominfopid;
	}

	public void setCustominfopid(String custominfopid) {
		this.custominfopid = custominfopid;
	}

	@Column(name = "ORGANIZETYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getOrganizetype() {
		return this.organizetype;
	}

	public void setOrganizetype(Long organizetype) {
		this.organizetype = organizetype;
	}

	@Column(name = "ORGANIZEPID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getOrganizepid() {
		return this.organizepid;
	}

	public void setOrganizepid(String organizepid) {
		this.organizepid = organizepid;
	}

}