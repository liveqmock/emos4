package com.ultrapower.eoms.ultrasla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * EventAction entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_USLA_EVENTACTION")
public class EventAction implements java.io.Serializable {

	// Fields

	private String pid;
	private String eventid; // 事件id
	private String ruleid; // 规则id
	private Long duetime; // 业务时限
	private Long noticetime; // 通知时间
	private Long noticedtimes; // 通知次数
	private Long nextnoticetime; // 下一次通知时间
	private Long status; //新建销毁状态 1：新建 2：销毁
	private Long actionstatus; //动作通知状态 1：活动；2：已发送；3：补发活动；4：补发销毁
	private String defaulthanlderid; // 默认通知对象id
	private Long createtime; // 创建时间

	// Constructors

	/** default constructor */
	public EventAction() {
	}

	/** minimal constructor */
	public EventAction(String pid, String eventid, String ruleid, Long duetime,
			Long noticetime) {
		this.pid = pid;
		this.eventid = eventid;
		this.ruleid = ruleid;
		this.duetime = duetime;
		this.noticetime = noticetime;
	}

	/** full constructor */
	public EventAction(String pid, String eventid, String ruleid, Long duetime,
			Long noticetime, Long noticedtimes, Long nextnoticetime,
			Long status, Long actionstatus, String defaulthanlderid, Long createtime) {
		this.pid = pid;
		this.eventid = eventid;
		this.ruleid = ruleid;
		this.duetime = duetime;
		this.noticetime = noticetime;
		this.noticedtimes = noticedtimes;
		this.nextnoticetime = nextnoticetime;
		this.status = status;
		this.actionstatus = actionstatus;
		this.defaulthanlderid = defaulthanlderid;
		this.createtime = createtime;
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

	@Column(name = "EVENTID", unique = false, nullable = false, insertable = true, updatable = true, length = 300)
	public String getEventid() {
		return this.eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	@Column(name = "RULEID", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	@Column(name = "DUETIME", unique = false, nullable = false, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getDuetime() {
		return this.duetime;
	}

	public void setDuetime(Long duetime) {
		this.duetime = duetime;
	}

	@Column(name = "NOTICETIME", unique = false, nullable = false, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getNoticetime() {
		return this.noticetime;
	}

	public void setNoticetime(Long noticetime) {
		this.noticetime = noticetime;
	}

	@Column(name = "NOTICEDTIMES", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getNoticedtimes() {
		return this.noticedtimes;
	}

	public void setNoticedtimes(Long noticedtimes) {
		this.noticedtimes = noticedtimes;
	}

	@Column(name = "NEXTNOTICETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getNextnoticetime() {
		return this.nextnoticetime;
	}

	public void setNextnoticetime(Long nextnoticetime) {
		this.nextnoticetime = nextnoticetime;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ACTIONSTATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	
	public Long getActionstatus() {
		return actionstatus;
	}

	public void setActionstatus(Long actionstatus) {
		this.actionstatus = actionstatus;
	}

	@Column(name = "DEFAULTHANLDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 700)
	public String getDefaulthanlderid() {
		return this.defaulthanlderid;
	}

	public void setDefaulthanlderid(String defaulthanlderid) {
		this.defaulthanlderid = defaulthanlderid;
	}

	@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
}