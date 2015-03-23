package com.ultrapower.eoms.ultrasla.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * SlaDefine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_USLA_SLADEFINE")

public class SlaDefine  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String baseschema;
     private String slaname;
     private Long validstarttime = 0L;
     private Long validendtime = 4102415998L;
     private Long status;
     private String remark;
     private String formtimeruleid;
     private String steptimeruleid;
     private String formeventruleid;
     private String stepeventruleid;
     private Long createtime;
     private Long updatetime;


    // Constructors

    /** default constructor */
    public SlaDefine() {
    }

	/** minimal constructor */
    public SlaDefine(String pid, String slaname) {
        this.pid = pid;
        this.slaname = slaname;
    }
    
    public SlaDefine(String pid, String baseschema, String slaname,
			Long validstarttime, Long validendtime, Long status, String remark,
			String formtimeruleid, String steptimeruleid,
			String formeventruleid, String stepeventruleid, Long createtime,
			Long updatetime) {
		super();
		this.pid = pid;
		this.baseschema = baseschema;
		this.slaname = slaname;
		this.validstarttime = validstarttime;
		this.validendtime = validendtime;
		this.status = status;
		this.remark = remark;
		this.formtimeruleid = formtimeruleid;
		this.steptimeruleid = steptimeruleid;
		this.formeventruleid = formeventruleid;
		this.stepeventruleid = stepeventruleid;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	// Property accessors
    @Id
    
    @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=50)

    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Column(name="BASESCHEMA", unique=false, nullable=false, insertable=true, updatable=true, length=100)
    
    public String getBaseschema() {
		return baseschema;
	}

	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}

	@Column(name="SLANAME", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getSlaname() {
        return this.slaname;
    }
    
    public void setSlaname(String slaname) {
        this.slaname = slaname;
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
    
    @Column(name="STATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    @Column(name="REMARK", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="FORMTIMERULEID", unique=false, nullable=true, insertable=true, updatable=true, length=600)

    public String getFormtimeruleid() {
        return this.formtimeruleid;
    }
    
    public void setFormtimeruleid(String formtimeruleid) {
        this.formtimeruleid = formtimeruleid;
    }
    
    @Column(name="STEPTIMERULEID", unique=false, nullable=true, insertable=true, updatable=true, length=600)

    public String getSteptimeruleid() {
        return this.steptimeruleid;
    }
    
    public void setSteptimeruleid(String steptimeruleid) {
        this.steptimeruleid = steptimeruleid;
    }
    
    @Column(name="FORMEVENTRULEID", unique=false, nullable=true, insertable=true, updatable=true, length=600)

    public String getFormeventruleid() {
        return this.formeventruleid;
    }
    
    public void setFormeventruleid(String formeventruleid) {
        this.formeventruleid = formeventruleid;
    }
    
    @Column(name="STEPEVENTRULEID", unique=false, nullable=true, insertable=true, updatable=true, length=600)

    public String getStepeventruleid() {
        return this.stepeventruleid;
    }
    
    public void setStepeventruleid(String stepeventruleid) {
        this.stepeventruleid = stepeventruleid;
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