package com.ultrapower.eoms.ultrasm.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * ExcelExpCfg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_EXCELEXPCFG")

public class ExcelExpCfg  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String cfgmark;
     private String cfgname;
     private String excelname;
     private Long titlerow;
     private Long titlecol;
     private String colmerge;
     private String interlacedcolor;
     private Long sheetrow;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String remark;


    // Constructors

    /** default constructor */
    public ExcelExpCfg() {
    }

	/** minimal constructor */
    public ExcelExpCfg(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public ExcelExpCfg(String pid, String cfgmark, String cfgname, String excelname, Long titlerow, Long titlecol, String colmerge, String interlacedcolor, Long sheetrow, String creater, Long createtime, String lastmodifier, Long lastmodifytime, String remark) {
        this.pid = pid;
        this.cfgmark = cfgmark;
        this.cfgname = cfgname;
        this.excelname = excelname;
        this.titlerow = titlerow;
        this.titlecol = titlecol;
        this.colmerge = colmerge;
        this.interlacedcolor = interlacedcolor;
        this.sheetrow = sheetrow;
        this.creater = creater;
        this.createtime = createtime;
        this.lastmodifier = lastmodifier;
        this.lastmodifytime = lastmodifytime;
        this.remark = remark;
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
    
    @Column(name="CFGMARK", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getCfgmark() {
        return this.cfgmark;
    }
    
    public void setCfgmark(String cfgmark) {
        this.cfgmark = cfgmark;
    }
    
    @Column(name="CFGNAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getCfgname() {
        return this.cfgname;
    }
    
    public void setCfgname(String cfgname) {
        this.cfgname = cfgname;
    }
    
    @Column(name="EXCELNAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getExcelname() {
        return this.excelname;
    }
    
    public void setExcelname(String excelname) {
        this.excelname = excelname;
    }
    
    @Column(name="TITLEROW", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getTitlerow() {
        return this.titlerow;
    }
    
    public void setTitlerow(Long titlerow) {
        this.titlerow = titlerow;
    }
    
    @Column(name="TITLECOL", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getTitlecol() {
        return this.titlecol;
    }
    
    public void setTitlecol(Long titlecol) {
        this.titlecol = titlecol;
    }
    
    @Column(name="COLMERGE", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getColmerge() {
        return this.colmerge;
    }
    
    public void setColmerge(String colmerge) {
        this.colmerge = colmerge;
    }
    
    @Column(name="INTERLACEDCOLOR", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getInterlacedcolor() {
        return this.interlacedcolor;
    }
    
    public void setInterlacedcolor(String interlacedcolor) {
        this.interlacedcolor = interlacedcolor;
    }
    
    @Column(name="SHEETROW", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)

    public Long getSheetrow() {
        return this.sheetrow;
    }
    
    public void setSheetrow(Long sheetrow) {
        this.sheetrow = sheetrow;
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
    
    @Column(name="REMARK", unique=false, nullable=true, insertable=true, updatable=true, length=500)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}