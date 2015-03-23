package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * IndexPath entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_INDEXPATH")

public class IndexPath  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String indextypeid;
     private String indexpath;
     private Long iscurrentpath;


    // Constructors

    /** default constructor */
    public IndexPath() {
    }

	/** minimal constructor */
    public IndexPath(String indexpath,Long iscurrentpath) {
        this.indexpath = indexpath;
        this.iscurrentpath = iscurrentpath;
    }
    
    /** full constructor */
    public IndexPath(String pid, String indextypeid, String indexpath, Long iscurrentpath) {
        this.pid = pid;
        this.indextypeid = indextypeid;
        this.indexpath = indexpath;
        this.iscurrentpath = iscurrentpath;
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
    
    @Column(name="INDEXTYPEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getIndextypeid() {
        return this.indextypeid;
    }
    
    public void setIndextypeid(String indextypeid) {
        this.indextypeid = indextypeid;
    }
    
    @Column(name="INDEXPATH", unique=false, nullable=true, insertable=true, updatable=true, length=200)

    public String getIndexpath() {
        return this.indexpath;
    }
    
    public void setIndexpath(String indexpath) {
        this.indexpath = indexpath;
    }
    
    @Column(name="ISCURRENTPATH", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIscurrentpath() {
        return this.iscurrentpath;
    }
    
    public void setIscurrentpath(Long iscurrentpath) {
        this.iscurrentpath = iscurrentpath;
    }
   








}