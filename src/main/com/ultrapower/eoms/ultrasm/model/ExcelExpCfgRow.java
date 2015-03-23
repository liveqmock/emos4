package com.ultrapower.eoms.ultrasm.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * ExcelExpCfgRow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_EXCELEXPCFGROW")

public class ExcelExpCfgRow  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String eecid;
     private Long rownumber;
     private String datarow;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;


    // Constructors

    /** default constructor */
    public ExcelExpCfgRow() {
    }

	/** minimal constructor */
    public ExcelExpCfgRow(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public ExcelExpCfgRow(String pid, String eecid, Long rownumber, String datarow, String creater, Long createtime, String lastmodifier, Long lastmodifytime) {
        this.pid = pid;
        this.eecid = eecid;
        this.rownumber = rownumber;
        this.datarow = datarow;
        this.creater = creater;
        this.createtime = createtime;
        this.lastmodifier = lastmodifier;
        this.lastmodifytime = lastmodifytime;
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
    
    @Column(name="EECID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getEecid() {
        return this.eecid;
    }
    
    public void setEecid(String eecid) {
        this.eecid = eecid;
    }
    
    @Column(name="ROWNUMBER", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getRownumber() {
        return this.rownumber;
    }
    
    public void setRownumber(Long rownumber) {
        this.rownumber = rownumber;
    }
    
    @Column(name="DATAROW", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getDatarow() {
        return this.datarow;
    }
    
    public void setDatarow(String datarow) {
        this.datarow = datarow;
    }
    
    @Column(name="CREATER", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getCreater() {
        return this.creater;
    }
    
    public void setCreater(String creater) {
        this.creater = creater;
    }
    
    @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="LASTMODIFIER", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getLastmodifier() {
        return this.lastmodifier;
    }
    
    public void setLastmodifier(String lastmodifier) {
        this.lastmodifier = lastmodifier;
    }
    
    @Column(name="LASTMODIFYTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getLastmodifytime() {
        return this.lastmodifytime;
    }
    
    public void setLastmodifytime(Long lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }
   








}