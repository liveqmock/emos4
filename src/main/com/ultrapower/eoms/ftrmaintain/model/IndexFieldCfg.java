package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * IndexFieldCfg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_INDEXFIELDCFG")

public class IndexFieldCfg  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String indextypeid;
     private String sqlfieldname;
     private String indexfieldname;
     private String displayname;
     private Long fieldtype;
     private String fieldtypedata;
     private Long isstore;
     private Long isindex;
     private Long isanalyze;
     private Long isdisplay;
     private Long istitle;
     private Long iskeyfield;
     private Long ordernum;
     private Long sourcetype;
     private String numericfield;


    // Constructors

    /** default constructor */
    public IndexFieldCfg() {
    }

	/** minimal constructor */
    public IndexFieldCfg(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public IndexFieldCfg(String pid, String indextypeid, String sqlfieldname, 
    		String indexfieldname, String displayname, Long fieldtype, 
    		String fieldtypedata, Long isstore, Long isindex, Long isanalyze, 
    		Long isdisplay, Long istitle, 
    		Long iskeyfield, Long ordernum, Long sourcetype, String numericfield) {
        this.pid = pid;
        this.indextypeid = indextypeid;
        this.sqlfieldname = sqlfieldname;
        this.indexfieldname = indexfieldname;
        this.displayname = displayname;
        this.fieldtype = fieldtype;
        this.fieldtypedata = fieldtypedata;
        this.isstore = isstore;
        this.isindex = isindex;
        this.isanalyze = isanalyze;
        this.isdisplay = isdisplay;
        this.istitle = istitle;
        this.iskeyfield = iskeyfield;
        this.ordernum = ordernum;
        this.sourcetype = sourcetype;
        this.numericfield = numericfield;
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
    
    @Column(name="SQLFIELDNAME", unique=false, nullable=true, insertable=true, updatable=true, length=60)

    public String getSqlfieldname() {
        return this.sqlfieldname;
    }
    
    public void setSqlfieldname(String sqlfieldname) {
        this.sqlfieldname = sqlfieldname;
    }
    
    @Column(name="INDEXFIELDNAME", unique=false, nullable=true, insertable=true, updatable=true, length=60)

    public String getIndexfieldname() {
        return this.indexfieldname;
    }
    
    public void setIndexfieldname(String indexfieldname) {
        this.indexfieldname = indexfieldname;
    }
    
    @Column(name="DISPLAYNAME", unique=false, nullable=true, insertable=true, updatable=true, length=60)

    public String getDisplayname() {
        return this.displayname;
    }
    
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
    
    @Column(name="FIELDTYPE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getFieldtype() {
        return this.fieldtype;
    }
    
    public void setFieldtype(Long fieldtype) {
        this.fieldtype = fieldtype;
    }
    
    @Column(name="FIELDTYPEDATA", unique=false, nullable=true, insertable=true, updatable=true, length=200)

    public String getFieldtypedata() {
        return this.fieldtypedata;
    }
    
    public void setFieldtypedata(String fieldtypedata) {
        this.fieldtypedata = fieldtypedata;
    }
    
    @Column(name="ISSTORE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsstore() {
        return this.isstore;
    }
    
    public void setIsstore(Long isstore) {
        this.isstore = isstore;
    }
    
    @Column(name="ISINDEX", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsindex() {
        return this.isindex;
    }
    
    public void setIsindex(Long isindex) {
        this.isindex = isindex;
    }
    
    @Column(name="ISANALYZE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsanalyze() {
        return this.isanalyze;
    }
    
    public void setIsanalyze(Long isanalyze) {
        this.isanalyze = isanalyze;
    }
    
    @Column(name="ISDISPLAY", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsdisplay() {
        return this.isdisplay;
    }
    
    public void setIsdisplay(Long isdisplay) {
        this.isdisplay = isdisplay;
    }

	@Column(name="ISTITLE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIstitle() {
        return this.istitle;
    }
    
    public void setIstitle(Long istitle) {
        this.istitle = istitle;
    }
    
    @Column(name="ISKEYFIELD", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIskeyfield() {
        return this.iskeyfield;
    }
    
    public void setIskeyfield(Long iskeyfield) {
        this.iskeyfield = iskeyfield;
    }
    
    @Column(name="ORDERNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="SOURCETYPE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getSourcetype() {
        return this.sourcetype;
    }
    
    public void setSourcetype(Long sourcetype) {
        this.sourcetype = sourcetype;
    }

    @Column(name="NUMERICFIELD", unique=false, nullable=true, insertable=true, updatable=true, length=30)
    
	public String getNumericfield() {
		return numericfield;
	}

	public void setNumericfield(String numericfield) {
		this.numericfield = numericfield;
	}
    
    
}