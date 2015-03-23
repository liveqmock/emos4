package com.ultrapower.eoms.common.core.component.sla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * SlaMailAction entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SLAMAILACTION")
public class SlaMailAction implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2596616596818791439L;
	private String pid;
	private String ruletplid;
	private String ruletplname;
	private String actionname;
	private String actiontype;
	private String actionmark;
	private Long dealmode;
	private Long status;
	private Long pri;
	private Long ordernum;
	private Long isholiday;
	private Long isbusinessinformer;
	private String noticeuserid;
	private String noticeusername;
	private String noticegroupid;
	private String noticegroupname;
	private String copyuserid;
	private String copyusername;
	private String copygroupid;
	private String copygroupname;
	private Long sendnum;
	private String sendseptime;
	private String mailsubject;
	private String mailcontent;
	private String remark;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private Long threadno;
	private String slarolemodel;
	private String noticeroleid;
	private String noticerolename;

	// Constructors

	/** default constructor */
	public SlaMailAction() {
	}

	/** minimal constructor */
	public SlaMailAction(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SlaMailAction(String pid, String ruletplid, String ruletplname, String actionname,
			String actiontype,String actionmark, Long dealmode, Long status, Long pri,
			Long ordernum, Long isholiday, Long isbusinessinformer,
			String noticeuserid, String noticeusername, String noticegroupid,
			String noticegroupname, String copyuserid, String copyusername,
			String copygroupid, String copygroupname, Long sendnum,
			String sendseptime, String mailsubject, String mailcontent,
			String remark, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime,Long threadno,String slarolemodel,String noticeroleid,String noticerolename) {
		this.pid = pid;
		this.ruletplid = ruletplid;
		this.ruletplname = ruletplname;
		this.actionname = actionname;
		this.actiontype = actiontype;
		this.actionmark = actionmark;
		this.dealmode = dealmode;
		this.status = status;
		this.pri = pri;
		this.ordernum = ordernum;
		this.isholiday = isholiday;
		this.isbusinessinformer = isbusinessinformer;
		this.noticeuserid = noticeuserid;
		this.noticeusername = noticeusername;
		this.noticegroupid = noticegroupid;
		this.noticegroupname = noticegroupname;
		this.copyuserid = copyuserid;
		this.copyusername = copyusername;
		this.copygroupid = copygroupid;
		this.copygroupname = copygroupname;
		this.sendnum = sendnum;
		this.sendseptime = sendseptime;
		this.mailsubject = mailsubject;
		this.mailcontent = mailcontent;
		this.remark = remark;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.threadno = threadno;
		this.slarolemodel = slarolemodel;
		this.noticeroleid = noticeroleid;
		this.noticerolename = noticerolename;
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

	@Column(name = "RULETPLID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRuletplid() {
		return this.ruletplid;
	}

	public void setRuletplid(String ruletplid) {
		this.ruletplid = ruletplid;
	}
	
	@Transient
	public String getRuletplname() {
		return ruletplname;
	}

	public void setRuletplname(String ruletplname) {
		this.ruletplname = ruletplname;
	}

	@Column(name = "ACTIONNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getActionname() {
		return this.actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	@Column(name = "ACTIONTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getActiontype() {
		return this.actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	
	
	@Column(name = "ACTIONMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getActionmark() {
		return actionmark;
	}

	public void setActionmark(String actionmark) {
		this.actionmark = actionmark;
	}

	@Column(name = "DEALMODE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getDealmode() {
		return dealmode;
	}

	public void setDealmode(Long dealmode) {
		this.dealmode = dealmode;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "PRI", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getPri() {
		return this.pri;
	}

	public void setPri(Long pri) {
		this.pri = pri;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "ISHOLIDAY", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsholiday() {
		return this.isholiday;
	}

	public void setIsholiday(Long isholiday) {
		this.isholiday = isholiday;
	}

	@Column(name = "ISBUSINESSINFORMER", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsbusinessinformer() {
		return this.isbusinessinformer;
	}

	public void setIsbusinessinformer(Long isbusinessinformer) {
		this.isbusinessinformer = isbusinessinformer;
	}

	@Column(name = "NOTICEUSERID", unique = false, nullable = true, insertable = true, updatable = true, length = 1500)
	public String getNoticeuserid() {
		return this.noticeuserid;
	}

	public void setNoticeuserid(String noticeuserid) {
		this.noticeuserid = noticeuserid;
	}

	@Column(name = "NOTICEUSERNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getNoticeusername() {
		return this.noticeusername;
	}

	public void setNoticeusername(String noticeusername) {
		this.noticeusername = noticeusername;
	}

	@Column(name = "NOTICEGROUPID", unique = false, nullable = true, insertable = true, updatable = true, length = 1500)
	public String getNoticegroupid() {
		return this.noticegroupid;
	}

	public void setNoticegroupid(String noticegroupid) {
		this.noticegroupid = noticegroupid;
	}

	@Column(name = "NOTICEGROUPNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getNoticegroupname() {
		return this.noticegroupname;
	}

	public void setNoticegroupname(String noticegroupname) {
		this.noticegroupname = noticegroupname;
	}

	@Column(name = "COPYUSERID", unique = false, nullable = true, insertable = true, updatable = true, length = 1500)
	public String getCopyuserid() {
		return this.copyuserid;
	}

	public void setCopyuserid(String copyuserid) {
		this.copyuserid = copyuserid;
	}

	@Column(name = "COPYUSERNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getCopyusername() {
		return this.copyusername;
	}

	public void setCopyusername(String copyusername) {
		this.copyusername = copyusername;
	}

	@Column(name = "COPYGROUPID", unique = false, nullable = true, insertable = true, updatable = true, length = 1500)
	public String getCopygroupid() {
		return this.copygroupid;
	}

	public void setCopygroupid(String copygroupid) {
		this.copygroupid = copygroupid;
	}

	@Column(name = "COPYGROUPNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getCopygroupname() {
		return this.copygroupname;
	}

	public void setCopygroupname(String copygroupname) {
		this.copygroupname = copygroupname;
	}

	@Column(name = "SENDNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getSendnum() {
		return this.sendnum;
	}

	public void setSendnum(Long sendnum) {
		this.sendnum = sendnum;
	}

	@Column(name = "SENDSEPTIME", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getSendseptime() {
		return this.sendseptime;
	}

	public void setSendseptime(String sendseptime) {
		this.sendseptime = sendseptime;
	}

	@Column(name = "MAILSUBJECT", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getMailsubject() {
		return this.mailsubject;
	}

	public void setMailsubject(String mailsubject) {
		this.mailsubject = mailsubject;
	}

	@Column(name = "MAILCONTENT", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public String getMailcontent() {
		return this.mailcontent;
	}

	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}

	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Column(name = "THREADNO", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getThreadno() {
		return threadno;
	}

	public void setThreadno(Long threadno) {
		this.threadno = threadno;
	}

	@Column(name = "SLAROLEMODEL", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getSlarolemodel() {
		return slarolemodel;
	}

	public void setSlarolemodel(String slarolemodel) {
		this.slarolemodel = slarolemodel;
	}
	
	@Column(name = "NOTICEROLEID", unique = false, nullable = true, insertable = true, updatable = true, length = 1500)
	public String getNoticeroleid() {
		return noticeroleid;
	}

	public void setNoticeroleid(String noticeroleid) {
		this.noticeroleid = noticeroleid;
	}

	@Column(name = "NOTICEROLENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getNoticerolename() {
		return noticerolename;
	}

	public void setNoticerolename(String noticerolename) {
		this.noticerolename = noticerolename;
	}
}