package com.ultrapower.eoms.ultrasm.model;

// default package

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * ResourceUrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_RESOURCEURL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class ResourceUrl  implements java.io.Serializable {

     private String pid;
     private String resid;
     private String opid;
     private String resname;
     private String opname;
     private Long status;
     private String url;
     private String remark;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String createTimeShow;


    // Constructors

	/** default constructor */
    public ResourceUrl() {
    }

	/** minimal constructor */
    public ResourceUrl(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public ResourceUrl(String resname,String opname,String pid, String resid, String opid, String url, String remark, String creater, Long createtime, String lastmodifier, Long lastmodifytime) {
        this.resname = resname;
        this.opname = opname;
    	this.pid = pid;
        this.resid = resid;
        this.opid = opid;
        this.url = url;
        this.remark = remark;
        this.creater = creater;
        this.createtime = createtime;
        this.lastmodifier = lastmodifier;
        this.lastmodifytime = lastmodifytime;
    }

   
    // Property accessors
    @Id 
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="PID", unique=true, nullable=false, length=50)
    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Column(name="RESID", length=50)

    public String getResid() {
        return this.resid;
    }
    
    public void setResid(String resid) {
        this.resid = resid;
    }
    
    @Column(name="OPID", length=50)

    public String getOpid() {
        return this.opid;
    }
    
    public void setOpid(String opid) {
        this.opid = opid;
    }
    
    @Column(name="URL", length=150)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="REMARK", length=150)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="CREATER", length=50)

    public String getCreater() {
        return this.creater;
    }
    
    public void setCreater(String creater) {
        this.creater = creater;
    }
    
    @Column(name="CREATETIME", precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
        setCreateTimeShow();
    }
    
    @Column(name="LASTMODIFIER", length=50)

    public String getLastmodifier() {
        return this.lastmodifier;
    }
    
    public void setLastmodifier(String lastmodifier) {
        this.lastmodifier = lastmodifier;
    }
    
    @Column(name="LASTMODIFYTIME", precision=15, scale=0)

    public Long getLastmodifytime() {
        return this.lastmodifytime;
    }
    
    public void setLastmodifytime(Long lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }
    
    @Column(name="RESNAME", length=80)
    
    public String getResname() {
		return resname;
	}

	public void setResname(String resname) {
		this.resname = resname;
	}

	@Column(name="OPNAME", length=50)
	
	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Transient
	
	public String getCreateTimeShow()
	{
		return createTimeShow;
	}
	
	public void setCreateTimeShow()
	{
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(this.createtime!=null)
		{
			this.createTimeShow = myFmt.format(new Date(this.createtime*1000));
		}
	}








}