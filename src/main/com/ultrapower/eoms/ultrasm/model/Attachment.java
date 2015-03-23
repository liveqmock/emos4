package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.annotations.GenericGenerator;

/**
 * Attachment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_SM_ATTACHMENT")
public class Attachment  implements java.io.Serializable,Cloneable {

    // Fields    

     private String pid;
     private String name;
     private String realname;
     private String relationcode;
     private String type;
     private String path;
     private String attsize;
     private String creater;
     private Long createtime;
     private String lastmodifier;
     private Long lastmodifytime;
     private String remark;

    // Constructors

    /** default constructor */
    public Attachment() {
    }

	/** minimal constructor */
    public Attachment(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public Attachment(String pid, String name, String realname, String relationcode, String type, String path, String attsize, String creater, Long createtime, String lastmodifier, Long lastmodifytime, String remark) {
        this.pid = pid;
        this.name = name;
        this.realname = realname;
        this.relationcode = relationcode;
        this.type = type;
        this.path = path;
        this.attsize = attsize;
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
    
    @Column(name="NAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="REALNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getRealname() {
        return this.realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    @Column(name="RELATIONCODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getRelationcode() {
        return this.relationcode;
    }
    
    public void setRelationcode(String relationcode) {
        this.relationcode = relationcode;
    }
    
    @Column(name="TYPE", unique=false, nullable=true, insertable=true, updatable=true, length=50)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="PATH", unique=false, nullable=true, insertable=true, updatable=true, length=300)

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Column(name="ATTSIZE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
    
    public String getAttsize() {
		return attsize;
	}

	public void setAttsize(String attsize) {
		this.attsize = attsize;
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
    
	public Attachment clone() {
		Attachment result = null;
		try {
			result = (Attachment) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}
}