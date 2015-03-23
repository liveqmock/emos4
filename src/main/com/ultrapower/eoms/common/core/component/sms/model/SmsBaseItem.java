package com.ultrapower.eoms.common.core.component.sms.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * SmsBaseItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_SMBASEITEM")

public class SmsBaseItem  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String loginname;
     private String baseschema;
     private String baseitem;


    // Constructors

    /** default constructor */
    public SmsBaseItem() {
    }

	/** minimal constructor */
    public SmsBaseItem(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public SmsBaseItem(String pid, String loginname, String baseschema, String baseitem) {
        this.pid = pid;
        this.loginname = loginname;
        this.baseschema = baseschema;
        this.baseitem = baseitem;
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
    
    @Column(name="LOGINNAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getLoginname() {
        return this.loginname;
    }
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    
    @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=150)

    public String getBaseschema() {
        return this.baseschema;
    }
    
    public void setBaseschema(String baseschema) {
        this.baseschema = baseschema;
    }
    
    @Column(name="BASEITEM", unique=false, nullable=true, insertable=true, updatable=true, length=200)

    public String getBaseitem() {
        return this.baseitem;
    }
    
    public void setBaseitem(String baseitem) {
        this.baseitem = baseitem;
    }
   








}