package com.ultrapower.eoms.common.core.component.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SmsOrderPlan entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SMSORDERPLAN")
public class SmsOrderPlan implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1303649427431104599L;
	private String pid;
	private String loginname;
	private String usermobile;
	private Long isholiday;
	private String starttime;
	private String endtime;
	private Long status;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public SmsOrderPlan() {
	}

	/** minimal constructor */
	public SmsOrderPlan(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SmsOrderPlan(String pid, String loginname, String usermobile,
			Long isholiday, String starttime, String endtime, Long status,
			String lastmodifier, Long lastmodifytime) {
		this.pid = pid;
		this.loginname = loginname;
		this.usermobile = usermobile;
		this.isholiday = isholiday;
		this.starttime = starttime;
		this.endtime = endtime;
		this.status = status;
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

	@Column(name = "LOGINNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "USERMOBILE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getUsermobile() {
		return this.usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	@Column(name = "ISHOLIDAY", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsholiday() {
		return this.isholiday;
	}

	public void setIsholiday(Long isholiday) {
		this.isholiday = isholiday;
	}

	@Column(name = "STARTTIME", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	@Column(name = "ENDTIME", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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