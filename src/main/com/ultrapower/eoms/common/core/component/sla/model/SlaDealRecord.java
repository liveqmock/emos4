package com.ultrapower.eoms.common.core.component.sla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SlaDealRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SLADEALRECORD")
public class SlaDealRecord implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4507747145348850554L;
	private String pid;
	private String ruleid;
	private String taskid;
	private Long dealnum;
	private Long dealtime;

	// Constructors

	/** default constructor */
	public SlaDealRecord() {
	}

	/** minimal constructor */
	public SlaDealRecord(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SlaDealRecord(String pid, String ruleid, String taskid,
			Long dealnum, Long dealtime) {
		this.pid = pid;
		this.ruleid = ruleid;
		this.taskid = taskid;
		this.dealnum = dealnum;
		this.dealtime = dealtime;
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

	@Column(name = "RULEID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	@Column(name = "TASKID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	@Column(name = "DEALNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getDealnum() {
		return this.dealnum;
	}

	public void setDealnum(Long dealnum) {
		this.dealnum = dealnum;
	}

	@Column(name = "DEALTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getDealtime() {
		return this.dealtime;
	}

	public void setDealtime(Long dealtime) {
		this.dealtime = dealtime;
	}

}