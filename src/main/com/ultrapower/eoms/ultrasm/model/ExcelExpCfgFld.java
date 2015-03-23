package com.ultrapower.eoms.ultrasm.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * ExcelExpCfgField entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_EXCELEXPCFGFLD")

public class ExcelExpCfgFld  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String eecrid;
     private String fieldname;
     private String displayname;
     private Long width;
     private Long rowspan;
     private Long colspan;
     private Long ordernum;
     private String enable;
     private String isgroup;
     private String datatype;
     private String datainfo;
     private Long datalength;
     private String operator;
     private String comparedata;
     private String displaycolor;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String colcolor;
     private String align;


    // Constructors

    /** default constructor */
    public ExcelExpCfgFld() {
    }

	/** minimal constructor */
    public ExcelExpCfgFld(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public ExcelExpCfgFld(String pid, String eecrid, String fieldname, String displayname, Long width, Long rowspan, Long colspan, Long ordernum, String enable, String isgroup, String datatype, String datainfo, Long datalength, String operator, String comparedata, String displaycolor, String creater, Long createtime, String lastmodifier, Long lastmodifytime, String align) {
        this.pid = pid;
        this.eecrid = eecrid;
        this.fieldname = fieldname;
        this.displayname = displayname;
        this.width = width;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.ordernum = ordernum;
        this.enable = enable;
        this.isgroup = isgroup;
        this.datatype = datatype;
        this.datainfo = datainfo;
        this.datalength = datalength;
        this.operator = operator;
        this.comparedata = comparedata;
        this.displaycolor = displaycolor;
        this.creater = creater;
        this.createtime = createtime;
        this.lastmodifier = lastmodifier;
        this.lastmodifytime = lastmodifytime;
        this.align = align;
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
    
    @Column(name="EECRID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getEecrid() {
        return this.eecrid;
    }
    
    public void setEecrid(String eecrid) {
        this.eecrid = eecrid;
    }
    
    @Column(name="FIELDNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getFieldname() {
        return this.fieldname;
    }
    
    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }
    
    @Column(name="DISPLAYNAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getDisplayname() {
        return this.displayname;
    }
    
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
    
    @Column(name="WIDTH", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)

    public Long getWidth() {
        return this.width;
    }
    
    public void setWidth(Long width) {
        this.width = width;
    }
    
    @Column(name="ROWSPAN", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getRowspan() {
        return this.rowspan;
    }
    
    public void setRowspan(Long rowspan) {
        this.rowspan = rowspan;
    }
    
    @Column(name="COLSPAN", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getColspan() {
        return this.colspan;
    }
    
    public void setColspan(Long colspan) {
        this.colspan = colspan;
    }
    
    @Column(name="ORDERNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="ENABLE", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getEnable() {
        return this.enable;
    }
    
    public void setEnable(String enable) {
        this.enable = enable;
    }
    
    @Column(name="ISGROUP", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getIsgroup() {
        return this.isgroup;
    }
    
    public void setIsgroup(String isgroup) {
        this.isgroup = isgroup;
    }
    
    @Column(name="DATATYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getDatatype() {
        return this.datatype;
    }
    
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    
    @Column(name="DATAINFO", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getDatainfo() {
        return this.datainfo;
    }
    
    public void setDatainfo(String datainfo) {
        this.datainfo = datainfo;
    }
    
    @Column(name="DATALENGTH", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getDatalength() {
        return this.datalength;
    }
    
    public void setDatalength(Long datalength) {
        this.datalength = datalength;
    }
    
    @Column(name="OPERATOR", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getOperator() {
        return this.operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    @Column(name="COMPAREDATA", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getComparedata() {
        return this.comparedata;
    }
    
    public void setComparedata(String comparedata) {
        this.comparedata = comparedata;
    }
    
    @Column(name="DISPLAYCOLOR", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getDisplaycolor() {
        return this.displaycolor;
    }
    
    public void setDisplaycolor(String displaycolor) {
        this.displaycolor = displaycolor;
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
    
    @Column(name="ALIGN", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getAlign() {
        return this.align;
    }
    
    public void setAlign(String align) {
        this.align = align;
    }

    @Column(name="COLCOLOR", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    
	public String getColcolor() {
		return colcolor;
	}

	public void setColcolor(String colcolor) {
		this.colcolor = colcolor;
	}
   








}