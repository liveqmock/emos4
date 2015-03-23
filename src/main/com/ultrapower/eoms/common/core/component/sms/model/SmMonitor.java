package com.ultrapower.eoms.common.core.component.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SMMONITOR")
public class SmMonitor {

	private String content; // 发送内容
	private String tablename; // 短信来源表
	private String smtype; // 短信类型,指的短信来源的业务类型
	private String goal; // 发送目标,接收短信的手机号码,多个号码使用英文逗号分割
	private String copyto; // 抄送的手机号码
	private String scanstatus; // 线程扫描状态(0:未扫描1:已扫描)
	private String sendflag; // 记录发送状态(0: 未发送1: 成功 2:失败)
	private Long inputtime; // 短信记录入库时间
	private Long presendtime; // 预定发送时间
	private Long sendtime; // 发送时间
	private String relateid; // 业务关联ID，针对工单则为工单ID(C1字段)
	private String baseschema; // 工单SCHEMA
	private String markflag; // 是否具有回复功能(0：无 1：有)
	private String proxystatus; // 短信网关反馈的短信发送状况信息(0：发送成功 1：发送失败 2：异常)
	private String remark; // 备注
	private String remark1; // 备注1
	private Long remark2; // 备注2
	private Long remark3; // 备注3
	private Long sendnum; // 发送次数
	private Long sp; // sp号
	
	public SmMonitor() {
		
	}
	
	public SmMonitor(String goal, String scanstatus, String sendflag, Long sendtime) {
		this.goal = goal;
		this.scanstatus = scanstatus;
		this.sendflag = sendflag;
		this.sendtime = sendtime;
	}
	
	public SmMonitor(String content, String tablename, String smtype, String goal, String copyto, String scanstatus, String sendflag
						, Long inputtime, Long presendtime, Long sendtime, String relateid, String baseschema, String markflag
						, String proxystatus, String remark, String remark1, Long remark2, Long remark3, Long sendnum, Long sp) {
		this.content = content;
		this.tablename = tablename;
		this.smtype = smtype;
		this.goal = goal;
		this.copyto = copyto;
		this.scanstatus = scanstatus;
		this.sendflag = sendflag;
		this.inputtime = inputtime;
		this.presendtime = presendtime;
		this.sendtime = sendtime;
		this.relateid = relateid;
		this.baseschema = baseschema;
		this.markflag = markflag;
		this.proxystatus = proxystatus;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.sendnum = sendnum;
		this.sp = sp;
	}
	
	@Column(name="CONTENT", unique=false, nullable=true, insertable=true, updatable=true, length=500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="TABLENAME", unique=false, nullable=true, insertable=true, updatable=true, length=255)
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	@Column(name="SMTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=255)
	public String getSmtype() {
		return smtype;
	}
	public void setSmtype(String smtype) {
		this.smtype = smtype;
	}
	@Column(name="GOAL", unique=false, nullable=false, insertable=true, updatable=true, length=500)
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	@Column(name="COPYTO", unique=false, nullable=true, insertable=true, updatable=true, length=500)
	public String getCopyto() {
		return copyto;
	}
	public void setCopyto(String copyto) {
		this.copyto = copyto;
	}
	@Column(name="SCANSTATUS", unique=false, nullable=false, insertable=true, updatable=true, length=50)
	public String getScanstatus() {
		return scanstatus;
	}
	public void setScanstatus(String scanstatus) {
		this.scanstatus = scanstatus;
	}
	@Column(name="SENDFLAG", unique=false, nullable=false, insertable=true, updatable=true, length=50)
	public String getSendflag() {
		return sendflag;
	}
	public void setSendflag(String sendflag) {
		this.sendflag = sendflag;
	}
	@Id
	@Column(name="INPUTTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Long getInputtime() {
		return inputtime;
	}
	public void setInputtime(Long inputtime) {
		this.inputtime = inputtime;
	}
	@Column(name="PRESENDTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Long getPresendtime() {
		return presendtime;
	}
	public void setPresendtime(Long presendtime) {
		this.presendtime = presendtime;
	}
	@Column(name="SENDTIME", unique=false, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
	public Long getSendtime() {
		return sendtime;
	}
	public void setSendtime(Long sendtime) {
		this.sendtime = sendtime;
	}
	@Column(name="RELATEID", unique=false, nullable=true, insertable=true, updatable=true, length=255)
	public String getRelateid() {
		return relateid;
	}
	public void setRelateid(String relateid) {
		this.relateid = relateid;
	}
	@Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=255)
	public String getBaseschema() {
		return baseschema;
	}
	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}
	@Column(name="MARKFLAG", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getMarkflag() {
		return markflag;
	}
	public void setMarkflag(String markflag) {
		this.markflag = markflag;
	}
	@Column(name="PROXYSTATUS", unique=false, nullable=true, insertable=true, updatable=true, length=50)
	public String getProxystatus() {
		return proxystatus;
	}
	public void setProxystatus(String proxystatus) {
		this.proxystatus = proxystatus;
	}
	@Column(name="REMARK", unique=false, nullable=true, insertable=true, updatable=true, length=1000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="REMARK1", unique=false, nullable=true, insertable=true, updatable=true, length=500)
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	@Column(name="REMARK2", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Long getRemark2() {
		return remark2;
	}
	public void setRemark2(Long remark2) {
		this.remark2 = remark2;
	}
	@Column(name="REMARK3", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Long getRemark3() {
		return remark3;
	}
	public void setRemark3(Long remark3) {
		this.remark3 = remark3;
	}
	@Column(name="SENDNUM", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Long getSendnum() {
		return sendnum;
	}
	public void setSendnum(Long sendnum) {
		this.sendnum = sendnum;
	}
	@Column(name="SP", unique=false, nullable=true, insertable=true, updatable=true, precision=10, scale=0)
	public Long getSp() {
		return sp;
	}
	public void setSp(Long sp) {
		this.sp = sp;
	}
}
