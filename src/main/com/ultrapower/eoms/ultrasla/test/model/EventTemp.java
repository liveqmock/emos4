package com.ultrapower.eoms.ultrasla.test.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;


/**
 * EventTemp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_USLA_EVENTTEMP")

public class EventTemp  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String eventid;
     private String ruleids;
     private Long duetime;
     private String defaultuser;
     private String defaultgroup;
     private String defaultrole;
     private String eventparamxml;
     private Long status;
     private Long createtime;
     private Long updatetime;


    // Constructors

    /** default constructor */
    public EventTemp() {
    	RandomN random = new Random15();
		this.pid = random.getRandom(System.currentTimeMillis());
    }

	/** minimal constructor */
    public EventTemp(String pid, String eventid, String ruleids, Long duetime) {
        this.pid = pid;
        this.eventid = eventid;
        this.ruleids = ruleids;
        this.duetime = duetime;
    }
    
    /** full constructor */
    public EventTemp(String pid, String eventid, String ruleids, Long duetime, String defaultuser, String defaultgroup, String defaultrole, String eventparamxml, Long status, Long createtime, Long updatetime) {
        this.pid = pid;
        this.eventid = eventid;
        this.ruleids = ruleids;
        this.duetime = duetime;
        this.defaultuser = defaultuser;
        this.defaultgroup = defaultgroup;
        this.defaultrole = defaultrole;
        this.eventparamxml = eventparamxml;
        this.status = status;
        this.createtime = createtime;
        this.updatetime = updatetime;
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
    
    @Column(name="EVENTID", unique=false, nullable=false, insertable=true, updatable=true, length=300)

    public String getEventid() {
        return this.eventid;
    }
    
    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
    
    @Column(name="RULEIDS", unique=false, nullable=false, insertable=true, updatable=true, length=300)

    public String getRuleids() {
        return this.ruleids;
    }
    
    public void setRuleids(String ruleids) {
        this.ruleids = ruleids;
    }
    
    @Column(name="DUETIME", unique=false, nullable=false, insertable=true, updatable=true, precision=15, scale=0)

    public Long getDuetime() {
        return this.duetime;
    }
    
    public void setDuetime(Long duetime) {
        this.duetime = duetime;
    }
    
    @Column(name="DEFAULTUSER", unique=false, nullable=true, insertable=true, updatable=true, length=1000)

    public String getDefaultuser() {
        return this.defaultuser;
    }
    
    public void setDefaultuser(String defaultuser) {
        this.defaultuser = defaultuser;
    }
    
    @Column(name="DEFAULTGROUP", unique=false, nullable=true, insertable=true, updatable=true, length=1000)

    public String getDefaultgroup() {
        return this.defaultgroup;
    }
    
    public void setDefaultgroup(String defaultgroup) {
        this.defaultgroup = defaultgroup;
    }
    
    @Column(name="DEFAULTROLE", unique=false, nullable=true, insertable=true, updatable=true, length=1000)

    public String getDefaultrole() {
        return this.defaultrole;
    }
    
    public void setDefaultrole(String defaultrole) {
        this.defaultrole = defaultrole;
    }
    
    @Column(name="EVENTPARAMXML", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

    public String getEventparamxml() {
        return this.eventparamxml;
    }
    
    public void setEventparamxml(String eventparamxml) {
        this.eventparamxml = eventparamxml;
    }
    
    @Column(name="STATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

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
   








}