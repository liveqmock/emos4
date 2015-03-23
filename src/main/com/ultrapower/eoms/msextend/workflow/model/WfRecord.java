package com.ultrapower.eoms.msextend.workflow.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ultrapower.eoms.ultrasm.model.UserInfo;

@Table(name="BS_T_WF_RECORD")
@Entity
public class WfRecord implements Serializable {

	private static final long serialVersionUID = 1274016425779283539L;
	private String pid;
	private String baseSchema;	//流程标识
	private String baseId; //	流程关键字
	private String currentUser;	//当前处理人
	private String currentUserLoginName;//	当前处理人（登陆名）
	private UserInfo currentUserInfo;//当前处理人信息
	private String dealTime;//	处理时间		本动作的执行时间，也是本记录的生成时间
	private String dealDesc;//	处理说明		主要用于页面显示
	private String dealAction;//	处理动作		业务动作，如提交审批、审批通过、审批不通过等
	private String isView;//	是否显示		0：否；1：是（工单页面的业务处理记录显示）
	private String nextDealUser;//	下一步处理人		
	private String nextDealUserLoginName;//	下一步处理人登陆名		格式：U:loginName;U:loginName;如：U:w00011;U:w00012;
	private String isSms;//	是否短信提醒		0：否；1：是（可根据页面字段控制）
	private String smsContent;//	短信提醒内容		定义默认格式，如为空则采用本格格式
	private String smsSendFlag;	//短信是否发送成功		
	private String isEmail;//	是否邮件提醒		0：否；1：是（可根据页面字段控制）
	private String emailSendFlag;//	邮件是否发送成功
	private String emailContent;//	邮件提醒内容		定义默认格式，如为空则采用本格格式
	
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="pid", unique=true, nullable=false, insertable=true, updatable=true)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getCurrentUserLoginName() {
		return currentUserLoginName;
	}
	public void setCurrentUserLoginName(String currentUserLoginName) {
		this.currentUserLoginName = currentUserLoginName;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getDealDesc() {
		return dealDesc;
	}
	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}
	public String getDealAction() {
		return dealAction;
	}
	public void setDealAction(String dealAction) {
		this.dealAction = dealAction;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	public String getNextDealUser() {
		return nextDealUser;
	}
	public void setNextDealUser(String nextDealUser) {
		this.nextDealUser = nextDealUser;
	}
	public String getNextDealUserLoginName() {
		return nextDealUserLoginName;
	}
	public void setNextDealUserLoginName(String nextDealUserLoginName) {
		this.nextDealUserLoginName = nextDealUserLoginName;
	}
	public String getIsSms() {
		return isSms;
	}
	public void setIsSms(String isSms) {
		this.isSms = isSms;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getSmsSendFlag() {
		return smsSendFlag;
	}
	public void setSmsSendFlag(String smsSendFlag) {
		this.smsSendFlag = smsSendFlag;
	}
	public String getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}
	public String getEmailSendFlag() {
		return emailSendFlag;
	}
	public void setEmailSendFlag(String emailSendFlag) {
		this.emailSendFlag = emailSendFlag;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
	@ManyToOne
	@JoinColumn(name="currentUserLoginName",referencedColumnName="loginname",insertable=false,updatable=false)
	public UserInfo getCurrentUserInfo() {
		return currentUserInfo;
	}
	public void setCurrentUserInfo(UserInfo currentUserInfo) {
		this.currentUserInfo = currentUserInfo;
	}
	
	
}
