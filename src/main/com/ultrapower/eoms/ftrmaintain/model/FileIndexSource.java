package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * FileIndexSource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_FILEINDEXSOURCE")

public class FileIndexSource  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String indextypeid;
     private Long datafrom;
     private String sourceinfo;
     private Long isCustomSource;


    // Constructors

    /** default constructor */
    public FileIndexSource() {
    }

	/** minimal constructor */
    public FileIndexSource(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public FileIndexSource(String pid, String indextypeid, Long datafrom, String sourceinfo,Long isCustomSource) {
        this.pid = pid;
        this.indextypeid = indextypeid;
        this.datafrom = datafrom;
        this.sourceinfo = sourceinfo;
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
    
    @Column(name="DATAFROM", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getDatafrom() {
        return this.datafrom;
    }
    
    public void setDatafrom(Long datafrom) {
        this.datafrom = datafrom;
    }
    
    @Column(name="SOURCEINFO", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getSourceinfo() {
        return this.sourceinfo;
    }
    
    public void setSourceinfo(String sourceinfo) {
        this.sourceinfo = sourceinfo;
    }
   
    @Column(name="ISCUSTOMSOURCE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    
	public Long getIsCustomSource() {
		return isCustomSource;
	}

	public void setIsCustomSource(Long isCustomSource) {
		this.isCustomSource = isCustomSource;
	}
}