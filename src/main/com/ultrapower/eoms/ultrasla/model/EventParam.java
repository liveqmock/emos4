package com.ultrapower.eoms.ultrasla.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * EventParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_USLA_EVENTPARAM")

public class EventParam  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String eventid;
     private String actionid;
     private String actionparamxml;
     private Long createtime;


    // Constructors

    /** default constructor */
    public EventParam() {
    }

	/** minimal constructor */
    public EventParam(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public EventParam(String pid, String eventid, String actionid, String actionparamxml, Long createtime) {
        this.pid = pid;
        this.eventid = eventid;
        this.actionid = actionid;
        this.actionparamxml = actionparamxml;
        this.createtime = createtime;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=50)

    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Column(name="EVENTID", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getEventid() {
        return this.eventid;
    }
    
    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
    
    @Column(name="ACTIONID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    
    public String getActionid() {
		return actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	@Column(name="ACTIONPARAMXML", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

    public String getActionparamxml() {
        return this.actionparamxml;
    }
    
    public void setActionparamxml(String actionparamxml) {
        this.actionparamxml = actionparamxml;
    }
    
    @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
}