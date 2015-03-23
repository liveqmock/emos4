package com.ultrapower.wfinterface.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;


/**
 * WfiTempAttachment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_WFI_TEMPATTACHMENT")

public class WfiTempAttachment  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String dataid;//关联数据ID
     private String attachname;//测试.doc
     private String localfilepath;//本地 路径+文件名
     private String attachurl;//  远程 路径+文件名
     private int status = 0;// 状态1启用0删除


    // Constructors

    /** default constructor */
    public WfiTempAttachment() {
		RandomN ran = new Random15();
		this.pid = ran.getRandom(System.currentTimeMillis());
    }

    
    /** full constructor */
    public WfiTempAttachment(String pid, String dataid, String attachname, String localfilepath, String attachurl) {
    	if (pid==null || "".equals(pid)){
    		RandomN ran = new Random15();
    		this.pid = ran.getRandom(System.currentTimeMillis());
    	} else {
    		this.pid = pid;
    	}
        this.dataid = dataid;
        this.attachname = attachname;
        this.localfilepath = localfilepath;
        this.attachurl = attachurl;
    }

   
    // Property accessors


    @Id 
    @Column(name="PID", unique=true, nullable=false, length=50)
    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name="DATAID", length=50)

    public String getDataid() {
        return this.dataid;
    }
    
    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    @Column(name="ATTACHNAME", length=80)

    public String getAttachname() {
        return this.attachname;
    }
    
    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    @Column(name="LOCALFILEPATH", length=80)

    public String getLocalfilepath() {
        return this.localfilepath;
    }
    
    public void setLocalfilepath(String localfilepath) {
        this.localfilepath = localfilepath;
    }

    @Column(name="ATTACHURL", length=150)

    public String getAttachurl() {
        return this.attachurl;
    }
    
    public void setAttachurl(String attachurl) {
        this.attachurl = attachurl;
    }
   
    @Column(name="STATUS", precision=2, scale=0)

    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
}