package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * DisplayModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_DISPLAYMODEL")

public class DisplayModel  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String modelname;
     private String docurl;
     private String indextypeid;
     private Long columns;
     private String attachlink;

    // Constructors

    /** default constructor */
    public DisplayModel() {
    }

	/** minimal constructor */
    public DisplayModel(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public DisplayModel(String pid, String modelname, String docurl, 
    		String indextypeid,Long columns) {
        this.pid = pid;
        this.modelname = modelname;
        this.docurl = docurl;
        this.indextypeid = indextypeid;
        this.columns = columns;
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
    
    @Column(name="MODELNAME", unique=false, nullable=true, insertable=true, updatable=true, length=150)

    public String getModelname() {
        return this.modelname;
    }
    
    public void setModelname(String modelname) {
        this.modelname = modelname;
    }
    
    @Column(name="DOCURL", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getDocurl() {
        return this.docurl;
    }
    
    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }
    
    @Column(name="INDEXTYPEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getIndextypeid() {
        return this.indextypeid;
    }
    
    public void setIndextypeid(String indextypeid) {
        this.indextypeid = indextypeid;
    }
   
    @Column(name="COLUMNS", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getColumns() {
        return this.columns;
    }
    
    public void setColumns(Long columns) {
        this.columns = columns;
    }
    
	@Column(name="ATTACHLINK", unique=false, nullable=true, insertable=true, updatable=true, length=300)
	
	public String getAttachlink() {
		return attachlink;
	}

	public void setAttachlink(String attachlink) {
		this.attachlink = attachlink;
	}
	
}