package com.ultrapower.eoms.ultrasm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BsTCmbcAlarmnote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_CMBC_ALARMNOTE", schema = "ULTRABPP")
public class BsTCmbcAlarmnote implements java.io.Serializable {

	// Fields

	private String pid;
	private String alarmid;
	private String alarmtitle;
	private String alarmdesc;
	private String alarmsystype;
	private String alarmonetype;
	private String alarmtwotype;
	private String alarmthreetype;
	private String alarmlocal;
	private Long alarmhappentime;
	private Long alarmstarttime;
	private String dep;
	private String iscreatesheet;
	private String faultlog;
	private String inputtime;

	// Constructors

	/** default constructor */
	public BsTCmbcAlarmnote() {
	}

	/** minimal constructor */
	public BsTCmbcAlarmnote(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public BsTCmbcAlarmnote(String pid, String alarmid, String alarmtitle,
			String alarmdesc, String alarmsystype, String alarmonetype,
			String alarmtwotype, String alarmthreetype, String alarmlocal,
			Long alarmhappentime, Long alarmstarttime, String dep,
			String iscreatesheet, String faultlog, String inputtime) {
		this.pid = pid;
		this.alarmid = alarmid;
		this.alarmtitle = alarmtitle;
		this.alarmdesc = alarmdesc;
		this.alarmsystype = alarmsystype;
		this.alarmonetype = alarmonetype;
		this.alarmtwotype = alarmtwotype;
		this.alarmthreetype = alarmthreetype;
		this.alarmlocal = alarmlocal;
		this.alarmhappentime = alarmhappentime;
		this.alarmstarttime = alarmstarttime;
		this.dep = dep;
		this.iscreatesheet = iscreatesheet;
		this.faultlog = faultlog;
		this.inputtime = inputtime;
	}

	// Property accessors
	@Id
	@Column(name = "PID", unique = true, nullable = false, length = 50)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "ALARMID", length = 100)
	public String getAlarmid() {
		return this.alarmid;
	}

	public void setAlarmid(String alarmid) {
		this.alarmid = alarmid;
	}

	@Column(name = "ALARMTITLE", length = 500)
	public String getAlarmtitle() {
		return this.alarmtitle;
	}

	public void setAlarmtitle(String alarmtitle) {
		this.alarmtitle = alarmtitle;
	}

	@Column(name = "ALARMDESC", length = 500)
	public String getAlarmdesc() {
		return this.alarmdesc;
	}

	public void setAlarmdesc(String alarmdesc) {
		this.alarmdesc = alarmdesc;
	}

	@Column(name = "ALARMSYSTYPE", length = 254)
	public String getAlarmsystype() {
		return this.alarmsystype;
	}

	public void setAlarmsystype(String alarmsystype) {
		this.alarmsystype = alarmsystype;
	}

	@Column(name = "ALARMONETYPE", length = 254)
	public String getAlarmonetype() {
		return this.alarmonetype;
	}

	public void setAlarmonetype(String alarmonetype) {
		this.alarmonetype = alarmonetype;
	}

	@Column(name = "ALARMTWOTYPE", length = 254)
	public String getAlarmtwotype() {
		return this.alarmtwotype;
	}

	public void setAlarmtwotype(String alarmtwotype) {
		this.alarmtwotype = alarmtwotype;
	}

	@Column(name = "ALARMTHREETYPE", length = 254)
	public String getAlarmthreetype() {
		return this.alarmthreetype;
	}

	public void setAlarmthreetype(String alarmthreetype) {
		this.alarmthreetype = alarmthreetype;
	}

	@Column(name = "ALARMLOCAL", length = 254)
	public String getAlarmlocal() {
		return this.alarmlocal;
	}

	public void setAlarmlocal(String alarmlocal) {
		this.alarmlocal = alarmlocal;
	}

	@Column(name = "ALARMHAPPENTIME", precision = 15, scale = 0)
	public Long getAlarmhappentime() {
		return this.alarmhappentime;
	}

	public void setAlarmhappentime(Long alarmhappentime) {
		this.alarmhappentime = alarmhappentime;
	}

	@Column(name = "ALARMSTARTTIME", precision = 15, scale = 0)
	public Long getAlarmstarttime() {
		return this.alarmstarttime;
	}

	public void setAlarmstarttime(Long alarmstarttime) {
		this.alarmstarttime = alarmstarttime;
	}

	@Column(name = "DEP", length = 254)
	public String getDep() {
		return this.dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	@Column(name = "ISCREATESHEET", length = 10)
	public String getIscreatesheet() {
		return this.iscreatesheet;
	}

	public void setIscreatesheet(String iscreatesheet) {
		this.iscreatesheet = iscreatesheet;
	}

	@Column(name = "FAULTLOG", length = 1000)
	public String getFaultlog() {
		return this.faultlog;
	}

	public void setFaultlog(String faultlog) {
		this.faultlog = faultlog;
	}

	@Column(name = "INPUTTIME", length = 10)
	public String getInputtime() {
		return this.inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

}