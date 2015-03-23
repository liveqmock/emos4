package com.ultrapower.eoms.ftrmaintain.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * FieldSpanInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_FTR_FIELDSPANINFO")

public class FieldSpanInfo  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String modelpid;
     private String indexfieldid;
     private Long cols;
     private Long ordernum;
     private Long fieldposition;
     private Long displayzone;

    // Constructors

    /** default constructor */
    public FieldSpanInfo() {
    }

	/** minimal constructor */
    public FieldSpanInfo(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public FieldSpanInfo(String pid, String modelpid, String indexfieldid, 
    		Long cols,Long ordernum,Long fieldposition,Long displayzone) {
        this.pid = pid;
        this.modelpid = modelpid;
        this.indexfieldid = indexfieldid;
        this.cols = cols;
        this.ordernum = ordernum;
        this.fieldposition = fieldposition;
        this.displayzone = displayzone;
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
    
    @Column(name="MODELPID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getModelpid() {
        return this.modelpid;
    }
    
    public void setModelpid(String modelpid) {
        this.modelpid = modelpid;
    }
    
    @Column(name="INDEXFIELDID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getIndexfieldid() {
        return this.indexfieldid;
    }
    
    public void setIndexfieldid(String indexfieldid) {
        this.indexfieldid = indexfieldid;
    }
    
    @Column(name="COLS", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)

    public Long getCols() {
        return this.cols;
    }
    
    public void setCols(Long cols) {
        this.cols = cols;
    }

    @Column(name="ORDERNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)
    
	public Long getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}
	
	@Column(name="FIELDPOSITION", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)
	
	public Long getFieldposition() {
		return fieldposition;
	}

	public void setFieldposition(Long fieldposition) {
		this.fieldposition = fieldposition;
	}

    @Column(name="DISPLAYZONE", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)
    
	public Long getDisplayzone() {
		return displayzone;
	}

	public void setDisplayzone(Long displayzone) {
		this.displayzone = displayzone;
	}
}