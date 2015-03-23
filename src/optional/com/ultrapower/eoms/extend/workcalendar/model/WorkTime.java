package com.ultrapower.eoms.extend.workcalendar.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ultrapower.eoms.common.core.util.StringUtils;

@Entity
@Table(name="BS_T_COM_WORKTIME")

public class WorkTime  implements java.io.Serializable {


    // Fields    

     private String pid;
     private String calendartype;
     private String datestring;
     private Long dateseconds;
     private String timetype;
     private Long starttime;
     private Long endtime;
     private Long createtime;
     private Long updatetime;
     private String starttimeShow;
     private String endtimeShow;


    // Constructors

    /** default constructor */
    public WorkTime() {
    }

	/** minimal constructor */
    public WorkTime(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public WorkTime(String pid, String calendartype, String datestring, Long dateseconds, String timetype, Long starttime, Long endtime, Long createtime, Long updatetime) {
        this.pid = pid;
        this.calendartype = calendartype;
        this.datestring = datestring;
        this.dateseconds = dateseconds;
        this.starttime = starttime;
        this.endtime = endtime;
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
    
    @Column(name="TIMETYPE", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getTimetype() {
        return this.timetype;
    }
    
    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }
    
    @Column(name="STARTTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(Long starttime) {
        this.starttime = starttime;
        if(starttime!=null)
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        	this.starttimeShow = sdf.format(new Date(starttime*1000));
        }
    }
    
    @Column(name="ENDTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(Long endtime) {
        this.endtime = endtime;
        if(endtime!=null)
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        	this.endtimeShow = sdf.format(new Date(endtime*1000));
        }
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

    @Transient
	public String getStarttimeShow() {
		return starttimeShow;
	}

	public void setStarttimeShow(String starttimeShow) {
		this.starttimeShow = starttimeShow;
		if(this.datestring!=null && !"".equals(StringUtils.checkNullString(starttimeShow)))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				this.starttime = sdf.parse(this.datestring+" "+starttimeShow).getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Transient
	public String getEndtimeShow() {
		return endtimeShow;
	}

	public void setEndtimeShow(String endtimeShow) {
		this.endtimeShow = endtimeShow;
		if(this.datestring!=null && !"".equals(StringUtils.checkNullString(endtimeShow)))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				this.endtime = sdf.parse(this.datestring+" "+endtimeShow).getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}