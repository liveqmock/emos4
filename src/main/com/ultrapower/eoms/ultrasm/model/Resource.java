package com.ultrapower.eoms.ultrasm.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//import org.hibernate.annotations.Cache;
import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * Resource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_RESOURCE")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Resource  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String resname;
     private String parentid;
     private String systemtype;
     private String definetype;
     private Long status;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String remark;
     private String ordernum;


    // Constructors

    /** default constructor */
    public Resource() {
    }

	/** minimal constructor */
    public Resource(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public Resource(String pid, String resname, String parentid, String systemtype, String definetype, Long status, String creater, Long createtime, String lastmodifier, Long lastmodifytime, String remark,String ordernum) {
        this.pid = pid;
        this.resname = resname;
        this.parentid = parentid;
        this.systemtype = systemtype;
        this.definetype = definetype;
        this.status = status;
        this.creater = creater;
        this.createtime = createtime;
        this.lastmodifier = lastmodifier;
        this.lastmodifytime = lastmodifytime;
        this.remark = remark;
        this.ordernum = ordernum;
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
    
    @Column(name="RESNAME", unique=false, nullable=true, insertable=true, updatable=true, length=80)

    public String getResname() {
        return this.resname;
    }
    
    public void setResname(String resname) {
        this.resname = resname;
    }
    
    @Column(name="PARENTID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getParentid() {
        return this.parentid;
    }
    
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    
    @Column(name="SYSTEMTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getSystemtype() {
        return this.systemtype;
    }
    
    public void setSystemtype(String systemtype) {
        this.systemtype = systemtype;
    }
    
    @Column(name="DEFINETYPE", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getDefinetype() {
        return this.definetype;
    }
    
    public void setDefinetype(String definetype) {
        this.definetype = definetype;
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

    @Column(name="ORDERNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)
	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
   








}