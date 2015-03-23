package com.ultrapower.eoms.common.core.component.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Maillog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_MAILLOG")
public class Maillog implements java.io.Serializable {

	// Fields

	private String pid;
	private String recordpid;
	private String sendbtime;
	private String sendetime;
	private String sendobj;
	private String errorinfo;
	private String mailTitle;
	private String mailcontent;
	private String flag;
	private String sendxml;
	private String returnxml;
	private String flowNo;

	// Constructors

	/** default constructor */
	public Maillog() {
	}

	/** minimal constructor */
	public Maillog(String pid) {
		this.pid = pid;
	}

	public Maillog(String recordpid, String sendbtime, String sendetime,
			String sendobj, String errorinfo, String mailcontent, String flag,
			String sendxml, String returnxml, String flowNo, String mailTitle) {
		super();
		this.recordpid = recordpid;
		this.sendbtime = sendbtime;
		this.sendetime = sendetime;
		this.sendobj = sendobj;
		this.errorinfo = errorinfo;
		this.mailcontent = mailcontent;
		this.flag = flag;
		this.sendxml = sendxml;
		this.returnxml = returnxml;
		this.flowNo = flowNo;
		this.mailTitle = mailTitle;
	}

	/** full constructor */
	public Maillog(String pid, String recordpid, String sendbtime,
			String sendetime, String sendobj, String errorinfo,
			String mailcontent, String flag, String sendxml, String returnxml, String flowNo, String mailTitle) {
		this.pid = pid;
		this.recordpid = recordpid;
		this.sendbtime = sendbtime;
		this.sendetime = sendetime;
		this.sendobj = sendobj;
		this.errorinfo = errorinfo;
		this.mailcontent = mailcontent;
		this.flag = flag;
		this.sendxml = sendxml;
		this.returnxml = returnxml;
		this.mailTitle = mailTitle;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", nullable = false, length = 50)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "RECORDPID", length = 50)
	public String getRecordpid() {
		return this.recordpid;
	}

	public void setRecordpid(String recordpid) {
		this.recordpid = recordpid;
	}

	@Column(name = "SENDBTIME", length = 20)
	public String getSendbtime() {
		return this.sendbtime;
	}

	public void setSendbtime(String sendbtime) {
		this.sendbtime = sendbtime;
	}

	@Column(name = "SENDETIME", length = 20)
	public String getSendetime() {
		return this.sendetime;
	}

	public void setSendetime(String sendetime) {
		this.sendetime = sendetime;
	}

	@Column(name = "SENDOBJ", length = 4000)
	public String getSendobj() {
		return this.sendobj;
	}

	public void setSendobj(String sendobj) {
		this.sendobj = sendobj;
	}

	@Column(name = "ERRORINFO", length = 4000)
	public String getErrorinfo() {
		return this.errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	@Column(name = "MAILCONTENT", length = 4000)
	public String getMailcontent() {
		return this.mailcontent;
	}

	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}

	@Column(name = "FLAG", length = 10)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "SENDXML", length = 4000)
	public String getSendxml() {
		return this.sendxml;
	}

	public void setSendxml(String sendxml) {
		this.sendxml = sendxml;
	}

	@Column(name = "RETURNXML", length = 4000)
	public String getReturnxml() {
		return this.returnxml;
	}

	public void setReturnxml(String returnxml) {
		this.returnxml = returnxml;
	}

	@Column(name = "FLOWNO", length = 50)
	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	@Column(name = "MAILTITLE", length = 2000)
	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

}