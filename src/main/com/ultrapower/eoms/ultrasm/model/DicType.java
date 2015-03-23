package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DicType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_DICTYPE")
public class DicType  implements java.io.Serializable {

    // Fields    

     private String pid;
     private String dtname;
     private String dtcode;
     private String dictype;
     private Long status;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String remark;

    // Constructors

    /** default constructor */
    public DicType() {
    }

	/** minimal constructor */
    public DicType(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public DicType(String pid, String dtname, String dtcode, String dictype, Long status, String creater, Long createtime, String lastmodifier, Long lastmodifytime, String remark) {
        this.pid = pid;
        this.dtname = dtname;
        this.dtcode = dtcode;
        this.dictype = dictype;
        this.status = status;
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
    
    @Column(name="DTNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getDtname() {
        return this.dtname;
    }
    
    public void setDtname(String dtname) {
        this.dtname = dtname;
    }
    
    @Column(name="DTCODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getDtcode() {
        return this.dtcode;
    }
    
    public void setDtcode(String dtcode) {
        this.dtcode = dtcode;
    }
    
    @Column(name="DICTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    public String getDictype() {
        return this.dictype;
    }
    
    public void setDictype(String dictype) {
        this.dictype = dictype;
    }
    
    @Column(name="STATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
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