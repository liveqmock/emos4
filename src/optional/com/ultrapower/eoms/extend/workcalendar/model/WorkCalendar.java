package com.ultrapower.eoms.extend.workcalendar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BS_T_COM_WORKCALENDAR")

public class WorkCalendar  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String calendartype;
     private String datestring;
     private Long dateseconds;
     private Long isholiday;
     private String holidaytype;
     private Long createtime;
     private Long updatetime;


    // Constructors

    /** default constructor */
    public WorkCalendar() {
    }

	/** minimal constructor */
    public WorkCalendar(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public WorkCalendar(String pid, String calendartype, String datestring, Long dateseconds, Long isholiday, String holidaytype, Long createtime, Long updatetime) {
        this.pid = pid;
        this.calendartype = calendartype;
        this.datestring = datestring;
        this.dateseconds = dateseconds;
        this.isholiday = isholiday;
        this.holidaytype = holidaytype;
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
    
    @Column(name="CALENDARTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getCalendartype() {
        return this.calendartype;
    }
    
    public void setCalendartype(String calendartype) {
        this.calendartype = calendartype;
    }
    
    @Column(name="DATESTRING", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getDatestring() {
        return this.datestring;
    }
    
    public void setDatestring(String datestring) {
        this.datestring = datestring;
    }
    
    @Column(name="DATESECONDS", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getDateseconds() {
        return this.dateseconds;
    }
    
    public void setDateseconds(Long dateseconds) {
        this.dateseconds = dateseconds;
    }
    
    @Column(name="ISHOLIDAY", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)

    public Long getIsholiday() {
        return this.isholiday;
    }
    
    public void setIsholiday(Long isholiday) {
        this.isholiday = isholiday;
    }
    
    @Column(name="HOLIDAYTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getHolidaytype() {
        return this.holidaytype;
    }
    
    public void setHolidaytype(String holidaytype) {
        this.holidaytype = holidaytype;
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