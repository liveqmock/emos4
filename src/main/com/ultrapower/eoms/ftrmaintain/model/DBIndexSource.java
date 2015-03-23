package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * DBIndexSource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_DBINDEXSOURCE")

public class DBIndexSource  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String indextypeid;
     private String sqlname;
     private Long isCustomSource;


    // Constructors

    /** default constructor */
    public DBIndexSource() {
    }

	/** minimal constructor */
    public DBIndexSource(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public DBIndexSource(String pid, String indextypeid, String sqlname,Long isCustomSource) {
        this.pid = pid;
        this.indextypeid = indextypeid;
        this.sqlname = sqlname;
        this.isCustomSource = isCustomSource;
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
    
    @Column(name="SQLNAME", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getSqlname() {
        return this.sqlname;
    }
    
    public void setSqlname(String sqlname) {
        this.sqlname = sqlname;
    }

    @Column(name="ISCUSTOMSOURCE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    
	public Long getIsCustomSource() {
		return isCustomSource;
	}

	public void setIsCustomSource(Long isCustomSource) {
		this.isCustomSource = isCustomSource;
	}

}