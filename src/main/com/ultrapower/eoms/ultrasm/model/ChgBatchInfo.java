package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_SM_BATCHCONF")
public class ChgBatchInfo {
	  private String pid;             
	  private String batch_no;         
	  private String chg_start_time;  
	  private String chg_end_time;     
	  private String reviewtime;      
	  private String latestreviewtime;
	  private String exec_flag;
	  private String z_log;
	  private String active;
	  private String lastaccepttime;
	  
    @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getChg_start_time() {
		return chg_start_time;
	}
	public void setChg_start_time(String chg_start_time) {
		this.chg_start_time = chg_start_time;
	}
	public String getChg_end_time() {
		return chg_end_time;
	}
	public void setChg_end_time(String chg_end_time) {
		this.chg_end_time = chg_end_time;
	}
	public String getReviewtime() {
		return reviewtime;
	}
	public void setReviewtime(String reviewtime) {
		this.reviewtime = reviewtime;
	}
	public String getLatestreviewtime() {
		return latestreviewtime;
	}
	public void setLatestreviewtime(String latestreviewtime) {
		this.latestreviewtime = latestreviewtime;
	}
	public String getExec_flag() {
		return exec_flag;
	}
	public void setExec_flag(String exec_flag) {
		this.exec_flag = exec_flag;
	}
	public String getZ_log() {
		return z_log;
	}
	public void setZ_log(String z_log) {
		this.z_log = z_log;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getLastaccepttime() {
		return lastaccepttime;
	}
	public void setLastaccepttime(String lastaccepttime) {
		this.lastaccepttime = lastaccepttime;
	}
	
	
	  
}
