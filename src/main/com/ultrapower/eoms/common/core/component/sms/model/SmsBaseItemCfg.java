package com.ultrapower.eoms.common.core.component.sms.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * SmsBaseItemCfg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_SMSBASEITEMCFG")

public class SmsBaseItemCfg  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String baseschema;
     private String itemsource;
     private Long status;


    // Constructors

    /** default constructor */
    public SmsBaseItemCfg() {
    }

	/** minimal constructor */
    public SmsBaseItemCfg(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public SmsBaseItemCfg(String pid, String baseschema, String itemsource, Long status) {
        this.pid = pid;
        this.baseschema = baseschema;
        this.itemsource = itemsource;
        this.status = status;
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
    
    @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=150)

    public String getBaseschema() {
        return this.baseschema;
    }
    
    public void setBaseschema(String baseschema) {
        this.baseschema = baseschema;
    }
    
    @Column(name="ITEMSOURCE", unique=false, nullable=true, insertable=true, updatable=true, length=1000)

    public String getItemsource() {
        return this.itemsource;
    }
    
    public void setItemsource(String itemsource) {
        this.itemsource = itemsource;
    }
    
    @Column(name="STATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
   








}