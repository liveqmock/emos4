package com.ultrapower.eoms.ultrasla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * EventRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_USLA_EVENTRULE")

public class EventRule  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String rulename;
     private String ruletype;
     private Long noticetype;
     private String ruleexpress;
     private Long timespan;
     private Long priority;
     private Long isdefaultnotice;
     private Long defaultchecktype;
     private String defaultcheckparam;
     private String noticehandlerid;
     private String noticehandler;
     private String duplicatehandlerid;
     private String duplicatehandler;
     private String upgradehandlerid;
     private String upgradehandler;
     private String fixedhandler;
     private String custnoticecompid;
     private Long noticetimes;
     private Long noticeinterval;
     private String noticestarttime;
     private String noticeendtime;
     private Long isnoticeafterwards;
     private Long actiontype;
     private String actionparameter;
     private String calendartype;
     private String noticetopic;
     private String noticecontent;
     private Long dynamicconchecktype;
     private String dynamicconcheckparam;
     private Long validstarttime = 0L;
     private Long validendtime = 4102415998L;
     private Long status;
     private Long createtime;
     private Long updatetime;
     private String noticealias;


    // Constructors

    /** default constructor */
    public EventRule() {
    }

	/** minimal constructor */
    public EventRule(String pid, String rulename, Long noticetype, Long timespan, Long actiontype, Long status) {
        this.pid = pid;
        this.rulename = rulename;
        this.noticetype = noticetype;
        this.timespan = timespan;
        this.actiontype = actiontype;
        this.status = status;
    }
    
    /** full constructor */
    public EventRule(String pid, String rulename, String ruletype, Long noticetype, String ruleexpress
    		, Long timespan, Long priority, Long isdefaultnotice, Long defaultchecktype, String defaultcheckparam
    		, String noticehandlerid, String noticehandler, String duplicatehandlerid, String duplicatehandler
    		, String upgradehandlerid, String upgradehandler, String fixedhandler, String custnoticecompid
    		, Long noticetimes, Long noticeinterval, String noticestarttime, String noticeendtime
    		, Long isnoticeafterwards, Long actiontype, String actionparameter, String calendartype
    		, String noticetopic, String noticecontent , Long dynamicconchecktype, String dynamicconcheckparam
    		, Long validstarttime, Long validendtime, Long status, Long createtime, Long updatetime, String noticealias) {
        this.pid = pid;
        this.rulename = rulename;
        this.ruletype = ruletype;
        this.noticetype = noticetype;
        this.ruleexpress = ruleexpress;  
        this.timespan = timespan;
        this.priority = priority;
        this.isdefaultnotice = isdefaultnotice;
        this.defaultchecktype = defaultchecktype;
        this.defaultcheckparam = defaultcheckparam;
        this.noticehandlerid = noticehandlerid;
        this.noticehandler = noticehandler;
        this.duplicatehandlerid = duplicatehandlerid;
        this.duplicatehandler = duplicatehandler;
        this.upgradehandlerid = upgradehandlerid;
        this.upgradehandler = upgradehandler;
        this.fixedhandler = fixedhandler;
        this.custnoticecompid = custnoticecompid;
        this.noticetimes = noticetimes;
        this.noticeinterval = noticeinterval;
        this.noticestarttime = noticestarttime;
        this.noticeendtime = noticeendtime;
        this.isnoticeafterwards = isnoticeafterwards;
        this.actiontype = actiontype;
        this.actionparameter = actionparameter;
        this.calendartype = calendartype;
        this.noticetopic = noticetopic;
        this.noticecontent = noticecontent;
        this.dynamicconchecktype = dynamicconchecktype;
        this.dynamicconcheckparam = dynamicconcheckparam;
        this.validstarttime = validstarttime;
        this.validendtime = validendtime;
        this.status = status;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.noticealias = noticealias;
    }

   
    // Property accessors
    @Id
    
    @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=15)

    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Column(name="RULENAME", unique=false, nullable=false, insertable=true, updatable=true, length=150)

    public String getRulename() {
        return this.rulename;
    }
    
    public void setRulename(String rulename) {
        this.rulename = rulename;
    }
    
    @Column(name="RULETYPE", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getRuletype() {
        return this.ruletype;
    }
    
    public void setRuletype(String ruletype) {
        this.ruletype = ruletype;
    }
    
    @Column(name="NOTICETYPE", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)

    public Long getNoticetype() {
        return this.noticetype;
    }
    
    public void setNoticetype(Long noticetype) {
        this.noticetype = noticetype;
    }
    
    @Column(name="RULEEXPRESS", unique=false, nullable=true, insertable=true, updatable=true, length=1000)

    public String getRuleexpress() {
        return this.ruleexpress;
    }
    
    public void setRuleexpress(String ruleexpress) {
        this.ruleexpress = ruleexpress;
    }
    
    @Column(name="TIMESPAN", unique=false, nullable=false, insertable=true, updatable=true, precision=15, scale=0)

    public Long getTimespan() {
        return this.timespan;
    }
    
    public void setTimespan(Long timespan) {
        this.timespan = timespan;
    }
    
    @Column(name="PRIORITY", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    
    public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}
    
    @Column(name="ISDEFAULTNOTICE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsdefaultnotice() {
        return this.isdefaultnotice;
    }
    
    public void setIsdefaultnotice(Long isdefaultnotice) {
        this.isdefaultnotice = isdefaultnotice;
    }
    
    @Column(name="DEFAULTCHECKTYPE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getDefaultchecktype() {
        return this.defaultchecktype;
    }
    
    public void setDefaultchecktype(Long defaultchecktype) {
        this.defaultchecktype = defaultchecktype;
    }
    
    @Column(name="DEFAULTCHECKPARAM", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getDefaultcheckparam() {
        return this.defaultcheckparam;
    }
    
    public void setDefaultcheckparam(String defaultcheckparam) {
        this.defaultcheckparam = defaultcheckparam;
    }
    
    @Column(name="NOTICEHANDLERID", unique=false, nullable=true, insertable=true, updatable=true, length=700)

    public String getNoticehandlerid() {
        return this.noticehandlerid;
    }
    
    public void setNoticehandlerid(String noticehandlerid) {
        this.noticehandlerid = noticehandlerid;
    }
    
    @Column(name="NOTICEHANDLER", unique=false, nullable=true, insertable=true, updatable=true, length=700)

    public String getNoticehandler() {
        return this.noticehandler;
    }
    
    public void setNoticehandler(String noticehandler) {
        this.noticehandler = noticehandler;
    }
    
    @Column(name="DUPLICATEHANDLERID", unique=false, nullable=true, insertable=true, updatable=true, length=700)

    public String getDuplicatehandlerid() {
        return this.duplicatehandlerid;
    }
    
    public void setDuplicatehandlerid(String duplicatehandlerid) {
        this.duplicatehandlerid = duplicatehandlerid;
    }
    
    @Column(name="DUPLICATEHANDLER", unique=false, nullable=true, insertable=true, updatable=true, length=700)

    public String getDuplicatehandler() {
        return this.duplicatehandler;
    }
    
    public void setDuplicatehandler(String duplicatehandler) {
        this.duplicatehandler = duplicatehandler;
    }
    
    @Column(name="UPGRADEHANDLERID", unique=false, nullable=true, insertable=true, updatable=true, length=700)

    public String getUpgradehandlerid() {
        return this.upgradehandlerid;
    }
    
    public void setUpgradehandlerid(String upgradehandlerid) {
        this.upgradehandlerid = upgradehandlerid;
    }
    
    @Column(name="UPGRADEHANDLER", unique=false, nullable=true, insertable=true, updatable=true, length=700)

    public String getUpgradehandler() {
        return this.upgradehandler;
    }
    
    public void setUpgradehandler(String upgradehandler) {
        this.upgradehandler = upgradehandler;
    }
    
    @Column(name="FIXEDHANDLER", unique=false, nullable=true, insertable=true, updatable=true, length=1000)
    
    public String getFixedhandler() {
		return fixedhandler;
	}

	public void setFixedhandler(String fixedhandler) {
		this.fixedhandler = fixedhandler;
	}

	@Column(name="CUSTNOTICECOMPID", unique=false, nullable=true, insertable=true, updatable=true, length=1500)

    public String getCustnoticecompid() {
        return this.custnoticecompid;
    }
    
    public void setCustnoticecompid(String custnoticecompid) {
        this.custnoticecompid = custnoticecompid;
    }
    
    @Column(name="NOTICETIMES", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getNoticetimes() {
        return this.noticetimes;
    }
    
    public void setNoticetimes(Long noticetimes) {
        this.noticetimes = noticetimes;
    }
    
    @Column(name="NOTICEINTERVAL", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getNoticeinterval() {
        return this.noticeinterval;
    }
    
    public void setNoticeinterval(Long noticeinterval) {
        this.noticeinterval = noticeinterval;
    }
    
    @Column(name="NOTICESTARTTIME", unique=false, nullable=true, insertable=true, updatable=true, length=5)

    public String getNoticestarttime() {
        return this.noticestarttime;
    }
    
    public void setNoticestarttime(String noticestarttime) {
        this.noticestarttime = noticestarttime;
    }
    
    @Column(name="NOTICEENDTIME", unique=false, nullable=true, insertable=true, updatable=true, length=5)

    public String getNoticeendtime() {
        return this.noticeendtime;
    }
    
    public void setNoticeendtime(String noticeendtime) {
        this.noticeendtime = noticeendtime;
    }
    
    @Column(name="ISNOTICEAFTERWARDS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsnoticeafterwards() {
        return this.isnoticeafterwards;
    }
    
    public void setIsnoticeafterwards(Long isnoticeafterwards) {
        this.isnoticeafterwards = isnoticeafterwards;
    }
    
    @Column(name="ACTIONTYPE", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)

    public Long getActiontype() {
        return this.actiontype;
    }
    
    public void setActiontype(Long actiontype) {
        this.actiontype = actiontype;
    }
    
    @Column(name="ACTIONPARAMETER", unique=false, nullable=true, insertable=true, updatable=true, length=200)

    public String getActionparameter() {
        return this.actionparameter;
    }
    
    public void setActionparameter(String actionparameter) {
        this.actionparameter = actionparameter;
    }
    
    @Column(name="CALENDARTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getCalendartype() {
		return calendartype;
	}

	public void setCalendartype(String calendartype) {
		this.calendartype = calendartype;
	}
	
    @Column(name="NOTICETOPIC", unique=false, nullable=true, insertable=true, updatable=true, length=600)

    public String getNoticetopic() {
        return this.noticetopic;
    }
    
    public void setNoticetopic(String noticetopic) {
        this.noticetopic = noticetopic;
    }
    
    @Column(name="NOTICECONTENT", unique=false, nullable=true, insertable=true, updatable=true, length=1500)

    public String getNoticecontent() {
        return this.noticecontent;
    }
    
    public void setNoticecontent(String noticecontent) {
        this.noticecontent = noticecontent;
    }
    
    @Column(name="DYNAMICCONCHECKTYPE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getDynamicconchecktype() {
        return this.dynamicconchecktype;
    }
    
    public void setDynamicconchecktype(Long dynamicconchecktype) {
        this.dynamicconchecktype = dynamicconchecktype;
    }
    
    @Column(name="DYNAMICCONCHECKPARAM", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getDynamicconcheckparam() {
        return this.dynamicconcheckparam;
    }
    
    public void setDynamicconcheckparam(String dynamicconcheckparam) {
        this.dynamicconcheckparam = dynamicconcheckparam;
    }
    
    @Column(name="VALIDSTARTTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getValidstarttime() {
        return this.validstarttime;
    }
    
    public void setValidstarttime(Long validstarttime) {
        this.validstarttime = validstarttime;
    }
    
    @Column(name="VALIDENDTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getValidendtime() {
        return this.validendtime;
    }
    
    public void setValidendtime(Long validendtime) {
        this.validendtime = validendtime;
    }
    
    @Column(name="STATUS", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="UPDATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getUpdatetime() {
        return this.updatetime;
    }
    
    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }
    
    @Column(name="NOTICEALIAS", unique=false, nullable=true, insertable=true, updatable=true, length=150)

    public String getNoticealias() {
        return this.noticealias;
    }
    
    public void setNoticealias(String noticealias) {
        this.noticealias = noticealias;
    }

}