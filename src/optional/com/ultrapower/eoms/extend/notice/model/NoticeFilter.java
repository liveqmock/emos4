package com.ultrapower.eoms.extend.notice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BS_T_COM_NoticeFilter")
public class NoticeFilter  implements java.io.Serializable {

    // Fields    

     private String pid;
     private String businesstype;
     private String businessmark;
     private String businesscondition;
     private Long checktype;
     private String checkparam;
     private Long ordernum;
     private String usertype;
     private String usermark;
     private String workdaystarttime;
     private String workdayendtime;
     private Long holidaystatus;
     private String holidaystarttime;
     private String holidayendtime;
     private Long isresend;
     private Long createtime;
     private Long updatetime;

    // Constructors

    /** default constructor */
    public NoticeFilter() {
    }

	/** minimal constructor */
    public NoticeFilter(String pid, String usertype, String usermark) {
        this.pid = pid;
        this.usertype = usertype;
        this.usermark = usermark;
    }
    
    /** full constructor */
    public NoticeFilter(String pid, String businesstype, String businessmark, String businesscondition, Long checktype, String checkparam
    		, Long ordernum, String usertype, String usermark, String workdaystarttime, String workdayendtime
    		, Long holidaystatus, String holidaystarttime, String holidayendtime, Long isresend, Long createtime, Long updatetime) {
        this.pid = pid;
        this.businesstype = businesstype;
        this.businessmark = businessmark;
        this.businesscondition = businesscondition;
        this.checktype = checktype;
        this.checkparam = checkparam;
        this.ordernum = ordernum;
        this.usertype = usertype;
        this.usermark = usermark;
        this.workdaystarttime = workdaystarttime;
        this.workdayendtime = workdayendtime;
        this.holidaystatus = holidaystatus;
        this.holidaystarttime = holidaystarttime;
        this.holidayendtime = holidayendtime;
        this.isresend = isresend;
        this.createtime = createtime;
        this.updatetime = updatetime;
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
    
    @Column(name="BUSINESSTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getBusinesstype() {
        return this.businesstype;
    }
    
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }
    
    @Column(name="BUSINESSMARK", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getBusinessmark() {
        return this.businessmark;
    }
    
    public void setBusinessmark(String businessmark) {
        this.businessmark = businessmark;
    }
    
    @Column(name="BUSINESSCONDITION", unique=false, nullable=true, insertable=true, updatable=true, length=1000)

    public String getBusinesscondition() {
        return this.businesscondition;
    }
    
    public void setBusinesscondition(String businesscondition) {
        this.businesscondition = businesscondition;
    }
    
    @Column(name="CHECKTYPE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getChecktype() {
        return this.checktype;
    }
    
    public void setChecktype(Long checktype) {
        this.checktype = checktype;
    }
    
    @Column(name="CHECKPARAM", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getCheckparam() {
        return this.checkparam;
    }
    
    public void setCheckparam(String checkparam) {
        this.checkparam = checkparam;
    }
    
    @Column(name="ORDERNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=3, scale=0)

    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="USERTYPE", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getUsertype() {
        return this.usertype;
    }
    
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    
    @Column(name="USERMARK", unique=false, nullable=false, insertable=true, updatable=true, length=50)

    public String getUsermark() {
        return this.usermark;
    }
    
    public void setUsermark(String usermark) {
        this.usermark = usermark;
    }
    
    @Column(name="WORKDAYSTARTTIME", unique=false, nullable=true, insertable=true, updatable=true, length=5)
    
    public String getWorkdaystarttime() {
		return workdaystarttime;
	}

	public void setWorkdaystarttime(String workdaystarttime) {
		this.workdaystarttime = workdaystarttime;
	}

	@Column(name="WORKDAYENDTIME", unique=false, nullable=true, insertable=true, updatable=true, length=5)
	
	public String getWorkdayendtime() {
		return workdayendtime;
	}

	public void setWorkdayendtime(String workdayendtime) {
		this.workdayendtime = workdayendtime;
	}

	@Column(name="HOLIDAYSTATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getHolidaystatus() {
        return this.holidaystatus;
    }
    
    public void setHolidaystatus(Long holidaystatus) {
        this.holidaystatus = holidaystatus;
    }
    
    @Column(name="HOLIDAYSTARTTIME", unique=false, nullable=true, insertable=true, updatable=true, length=5)

    public String getHolidaystarttime() {
        return this.holidaystarttime;
    }
    
    public void setHolidaystarttime(String holidaystarttime) {
        this.holidaystarttime = holidaystarttime;
    }
    
    @Column(name="HOLIDAYENDTIME", unique=false, nullable=true, insertable=true, updatable=true, length=5)

    public String getHolidayendtime() {
        return this.holidayendtime;
    }
    
    public void setHolidayendtime(String holidayendtime) {
        this.holidayendtime = holidayendtime;
    }
    
    @Column(name="ISRESEND", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    
    public Long getIsresend() {
		return isresend;
	}

	public void setIsresend(Long isresend) {
		this.isresend = isresend;
	}

	@Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="UPDATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getUpdatetime() {
        return this.updatetime;
    }
    
    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }
}