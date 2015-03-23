package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SqlDataPrivilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_SQLDATAPRIVILEGE")
public class SqlDataPrivilege  implements java.io.Serializable {

    // Fields    

     private String pid;
     private String rroid;
     private String resid;
     private String opid;
     private String privilegedata;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;

    // Constructors

    /** default constructor */
    public SqlDataPrivilege() {
    }

	/** minimal constructor */
    public SqlDataPrivilege(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public SqlDataPrivilege(String pid, String rroid, String resid, String opid, String privilegedata, String creater, Long createtime, String lastmodifier, Long lastmodifytime) {
        this.pid = pid;
        this.rroid = rroid;
        this.resid = resid;
        this.opid = opid;
        this.privilegedata = privilegedata;
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
    
    @Column(name="RROID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getRroid() {
        return this.rroid;
    }
    
    public void setRroid(String rroid) {
        this.rroid = rroid;
    }
    
    @Column(name="RESID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getResid() {
        return this.resid;
    }
    
    public void setResid(String resid) {
        this.resid = resid;
    }
    
    @Column(name="OPID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getOpid() {
        return this.opid;
    }
    
    public void setOpid(String opid) {
        this.opid = opid;
    }
    
    @Column(name="PRIVILEGEDATA", unique=false, nullable=true, insertable=true, updatable=true, length=500)
    public String getPrivilegedata() {
        return this.privilegedata;
    }
    
    public void setPrivilegedata(String privilegedata) {
        this.privilegedata = privilegedata;
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