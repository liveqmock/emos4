package com.ultrapower.eoms.ultrasla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * EventHandlerComp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_USLA_EVTHDERCOMP")

public class EventHandlerComp  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String componentname;
     private String componenttype;
     private Long compexetype;
     private String compexeparam;
     private Long status;
     private Long createtime;
     private Long updatetime;


    // Constructors

    /** default constructor */
    public EventHandlerComp() {
    }

	/** minimal constructor */
    public EventHandlerComp(String pid, String componentname, Long compexetype, String compexeparam, Long status) {
        this.pid = pid;
        this.componentname = componentname;
        this.compexetype = compexetype;
        this.compexeparam = compexeparam;
        this.status = status;
    }
    
    /** full constructor */
    public EventHandlerComp(String pid, String componentname, String componenttype, Long compexetype, String compexeparam, Long status, Long createtime, Long updatetime) {
        this.pid = pid;
        this.componentname = componentname;
        this.componenttype = componenttype;
        this.compexetype = compexetype;
        this.compexeparam = compexeparam;
        this.status = status;
        this.createtime = createtime;
        this.updatetime = updatetime;
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
    
    @Column(name="COMPONENTNAME", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getComponentname() {
        return this.componentname;
    }
    
    public void setComponentname(String componentname) {
        this.componentname = componentname;
    }
    
    @Column(name="COMPONENTTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getComponenttype() {
        return this.componenttype;
    }
    
    public void setComponenttype(String componenttype) {
        this.componenttype = componenttype;
    }
    
    @Column(name="COMPEXETYPE", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)

    public Long getCompexetype() {
        return this.compexetype;
    }
    
    public void setCompexetype(Long compexetype) {
        this.compexetype = compexetype;
    }
    
    @Column(name="COMPEXEPARAM", unique=false, nullable=false, insertable=true, updatable=true, length=300)

    public String getCompexeparam() {
        return this.compexeparam;
    }
    
    public void setCompexeparam(String compexeparam) {
        this.compexeparam = compexeparam;
    }
    
    @Column(name="STATUS", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
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