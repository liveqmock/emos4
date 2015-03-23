package com.ultrapower.eoms.ultraduty.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ultrapower.eoms.common.constants.DutyConstants;

/**
 * DutyHoliday entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_HOLIDAY")
public class DutyHoliday implements java.io.Serializable {

	// Fields

	private String pid;//主键
	private String dateinfo;//日期
	private String holidayname;//节假日名称
	private String years;//年
	private String months;//月
	private String days;//日
	private String note;//备注
	private int hideflag;//是否隐藏，1：是，0：否
	private String hideFlagView;//显示是否隐藏
	private Long holidayFlag;//是否是节假日，0:节假日，1:非节假日
	
	private String datetype;//日期类型

	// Constructors

	/** default constructor */
	public DutyHoliday() {
	}

	/** minimal constructor */
	public DutyHoliday(String holidayid) {
		this.pid = holidayid;
	}

	/** full constructor */
	public DutyHoliday(String holidayid, String dateinfo, String holidayname,
			String years, String months, String days, String note,
			int hideflag) {
		this.pid = holidayid;
		this.dateinfo = dateinfo;
		this.holidayname = holidayname;
		this.years = years;
		this.months = months;
		this.days = days;
		this.note = note;
		this.hideflag = hideflag;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", unique = true, nullable = false, length = 30)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "DATEINFO",  length = 10)
	public String getDateinfo() {
		return this.dateinfo;
	}

	public void setDateinfo(String dateinfo) {
		this.dateinfo = dateinfo;
	}

	@Column(name = "HOLIDAYNAME", length = 50)
	public String getHolidayname() {
		return this.holidayname;
	}

	public void setHolidayname(String holidayname) {
		this.holidayname = holidayname;
	}

	@Column(name = "YEARS", length = 4)
	public String getYears() {
		return this.years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	@Column(name = "MONTHS", length = 2)
	public String getMonths() {
		return this.months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	@Column(name = "DAYS", length = 2)
	public String getDays() {
		return this.days;
	}

	public void setDays(String days) {
		this.days = days;
	}
	@Column(name = "DATETYPE", length = 10)
	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}

	@Column(name = "NOTE", length = 500)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "HIDEFLAG", precision = 22, scale = 0)
	public int getHideflag() {
		return this.hideflag;
	}

	public void setHideflag(int hideflag) {
		this.hideflag = hideflag;
	}
	
	@Transient
	public String getHideFlagView() {
		switch (this.getHideflag()) {
			case 0 : this.hideFlagView = DutyConstants.ENABLE; break;
			case 1 : this.hideFlagView = DutyConstants.DISABLE; break;
			default : this.hideFlagView = "";
		}
		return hideFlagView;
	}

	public void setHideFlagView(String hideFlagView) {
		this.hideFlagView = hideFlagView;
	}

	@Column(name = "HOLIDAYFLAG")
	public Long getHolidayFlag() {
		return holidayFlag;
	}

	public void setHolidayFlag(Long holidayFlag) {
		this.holidayFlag = holidayFlag;
	}


	
}