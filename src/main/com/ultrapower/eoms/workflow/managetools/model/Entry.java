package com.ultrapower.eoms.workflow.managetools.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BS_T_WF_ENTRY")
public class Entry implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4962057010011307995L;
	// Fields    

     private String id;
     private String parententryid;
     private String topentryid;
     private String parentflowid;
     private String endcode;
     private String type;
     private String defname;
     private String state;
     private Long closetime;
     private Long createtime;
     private String topflowid;


    // Constructors

    /** default constructor */
    public Entry() {
    }

	/** minimal constructor */
    public Entry(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public Entry(String id, String parententryid, String topentryid, String parentflowid, String endcode, String type, String defname, String state, Long closetime, Long createtime, String topflowid) {
        this.id = id;
        this.parententryid = parententryid;
        this.topentryid = topentryid;
        this.parentflowid = parentflowid;
        this.endcode = endcode;
        this.type = type;
        this.defname = defname;
        this.state = state;
        this.closetime = closetime;
        this.createtime = createtime;
        this.topflowid = topflowid;
    }

   
    // Property accessors
    @Id
    @Column(name="ID", unique=true, nullable=false, insertable=true, updatable=true, length=15)
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="PARENTENTRYID", unique=false, nullable=true, insertable=true, updatable=true, length=15)

    public String getParententryid() {
        return this.parententryid;
    }
    
    public void setParententryid(String parententryid) {
        this.parententryid = parententryid;
    }
    
    @Column(name="TOPENTRYID", unique=false, nullable=true, insertable=true, updatable=true, length=15)

    public String getTopentryid() {
        return this.topentryid;
    }
    
    public void setTopentryid(String topentryid) {
        this.topentryid = topentryid;
    }
    
    @Column(name="PARENTFLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=15)

    public String getParentflowid() {
        return this.parentflowid;
    }
    
    public void setParentflowid(String parentflowid) {
        this.parentflowid = parentflowid;
    }
    
    @Column(name="ENDCODE", unique=false, nullable=true, insertable=true, updatable=true)

    public String getEndcode() {
        return this.endcode;
    }
    
    public void setEndcode(String endcode) {
        this.endcode = endcode;
    }
    
    @Column(name="TYPE", unique=false, nullable=true, insertable=true, updatable=true, length=15)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="DEFNAME", unique=false, nullable=true, insertable=true, updatable=true)

    public String getDefname() {
        return this.defname;
    }
    
    public void setDefname(String defname) {
        this.defname = defname;
    }
    
    @Column(name="STATE", unique=false, nullable=true, insertable=true, updatable=true)

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="CLOSETIME", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

    public Long getClosetime() {
        return this.closetime;
    }
    
    public void setClosetime(Long closetime) {
        this.closetime = closetime;
    }
    
    @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="TOPFLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=15)

    public String getTopflowid() {
        return this.topflowid;
    }
    
    public void setTopflowid(String topflowid) {
        this.topflowid = topflowid;
    }
}
