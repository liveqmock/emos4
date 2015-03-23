package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DicItem entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="BS_T_SM_DICITEM")
public class DicItem  implements java.io.Serializable {

    // Fields    

     private String pid;
     private String dtid;
     private String dtcode;
     private String diname;
     private String divalue;
     private String dicdn;
     private String dicdns;
     private String dicfullname;
     private String isdefault;
     private Long status;
     private Long ordernum;
     private String parentid;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String remark;

    // Constructors

    /** default constructor */
    public DicItem() {
    }

	/** minimal constructor */
    public DicItem(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public DicItem(String pid, String dtid, String dtcode, String diname, String divalue, String dicdn, String dicdns, String dicfullname, String isdefault, Long status, Long ordernum, String parentid, String creater, Long createtime, String lastmodifier, Long lastmodifytime, String remark) {
        this.pid = pid;
        this.dtid = dtid;
        this.dtcode = dtcode;
        this.diname = diname;
        this.divalue = divalue;
        this.dicdn = dicdn;
        this.dicdns = dicdns;
        this.dicfullname = dicfullname;
        this.isdefault = isdefault;
        this.status = status;
        this.ordernum = ordernum;
        this.parentid = parentid;
        this.creater = creater;
        this.createtime = createtime;
        this.lastmodifier = lastmodifier;
        this.lastmodifytime = lastmodifytime;
        this.remark = remark;
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
    
    @Column(name="DTID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getDtid() {
        return this.dtid;
    }
    
    public void setDtid(String dtid) {
        this.dtid = dtid;
    }
    
    @Column(name="DTCODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getDtcode() {
        return this.dtcode;
    }
    
    public void setDtcode(String dtcode) {
        this.dtcode = dtcode;
    }
    
    @Column(name="DINAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)
    public String getDiname() {
        return this.diname;
    }
    
    public void setDiname(String diname) {
        this.diname = diname;
    }
    
    @Column(name="DIVALUE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    public String getDivalue() {
        return this.divalue;
    }
    
    public void setDivalue(String divalue) {
        this.divalue = divalue;
    }
    
    @Column(name="DICDN", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    public String getDicdn() {
		return dicdn;
	}

	public void setDicdn(String dicdn) {
		this.dicdn = dicdn;
	}

	@Column(name="DICDNS", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getDicdns() {
		return dicdns;
	}

	public void setDicdns(String dicdns) {
		this.dicdns = dicdns;
	}

	@Column(name="DICFULLNAME", unique=false, nullable=true, insertable=true, updatable=true, length=500)
	public String getDicfullname() {
		return dicfullname;
	}

	public void setDicfullname(String dicfullname) {
		this.dicfullname = dicfullname;
	}

	@Column(name="ISDEFAULT", unique=false, nullable=true, insertable=true, updatable=true, length=30)
    public String getIsdefault() {
        return this.isdefault;
    }
    
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }
    @Column(name="STATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    @Column(name="ORDERNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=5, scale=0)
    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="PARENTID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getParentid() {
        return this.parentid;
    }
    
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    
    @Column(name="CREATER", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getCreater() {
        return this.creater;
    }
    
    public void setCreater(String creater) {
        this.creater = creater;
    }
    
    @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="LASTMODIFIER", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    public String getLastmodifier() {
        return this.lastmodifier;
    }
    
    public void setLastmodifier(String lastmodifier) {
        this.lastmodifier = lastmodifier;
    }
    
    @Column(name="LASTMODIFYTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
    public Long getLastmodifytime() {
        return this.lastmodifytime;
    }
    
    public void setLastmodifytime(Long lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }
    
    @Column(name="REMARK", unique=false, nullable=true, insertable=true, updatable=true, length=500)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}