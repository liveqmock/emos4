package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * MaintainRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_INDEXMAINTAINRECORD")

public class MaintainRecord  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String indextypeid;
     private String datadate;
     private String starttime;
     private String endtime;


    // Constructors

    /** default constructor */
    public MaintainRecord() {
    }

	/** minimal constructor */
    public MaintainRecord(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public MaintainRecord(String pid, String indextypeid, String datadate, String starttime, String endtime) {
        this.pid = pid;
        this.indextypeid = indextypeid;
        this.datadate = datadate;
        this.starttime = starttime;
        this.endtime = endtime;
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
    
    @Column(name="INDEXTYPEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getIndextypeid() {
        return this.indextypeid;
    }
    
    public void setIndextypeid(String indextypeid) {
        this.indextypeid = indextypeid;
    }
    
    @Column(name="DATADATE", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getDatadate() {
        return this.datadate;
    }
    
    public void setDatadate(String datadate) {
        this.datadate = datadate;
    }
    
    @Column(name="STARTTIME", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    
    @Column(name="ENDTIME", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
   








}