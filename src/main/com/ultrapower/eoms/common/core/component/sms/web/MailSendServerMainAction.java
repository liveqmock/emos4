package com.ultrapower.eoms.common.core.component.sms.web;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.manager.Send;
import com.ultrapower.eoms.common.core.component.sms.manager.MailProxyPara;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class MailSendServerMainAction extends BaseAction{
	public String status;//启动状态
	public static Send sendMail = null;
	private String startTime = MailProxyPara.startTime;
	private String endTime = MailProxyPara.endTime;
	
	/**
	 * 邮件发送查询页面
	 * @return
	 */
	public String mailQueryList(){
		return SUCCESS;
	}
	
	/**
	 * 调用邮件管理页面
	 * @return
	 */
	public String mailManager(){
		status = MailProxyPara.sendStatus;
		return SUCCESS;
	}
	
	
	/**
	 * 启动
	 */
	public String startMail(){
		MailProxyPara.sendStatus = "0";
		status = "0";//启动
		return SUCCESS;
	}
	
	/**
	 * 停止
	 */
	public String stopMail(){
		MailProxyPara.sendStatus = "1";
		status = "1";//停止
		return SUCCESS;
	}
	
	/**
	 * 邮件发送日志查询页面
	 * @return
	 */
	public String maillogList(){
		return SUCCESS;
	}
	
	//邮件发送入口
	public void send(){
		if(null!=MailProxyPara.sendStatus && "0".equals(MailProxyPara.sendStatus)){
			Long now = System.currentTimeMillis()/1000;
			String date = TimeUtils.formatIntToDateString(now).split(" ")[0];
			Long mailstart = TimeUtils.formatDateStringToInt(date+" "+MailProxyPara.startTime,"yy-MM-dd HH:mm");
			Long mailend = TimeUtils.formatDateStringToInt(date+" "+MailProxyPara.endTime,"yy-MM-dd HH:mm");
			if(null==sendMail){
				sendMail = new Send();
			}
			if(mailstart < now && mailend > now){
				sendMail.sendMail();
			}else{
				RecordLog.printLog("MAIL-SEND 邮件未到发送时间："+date+" "+MailProxyPara.startTime+"--"+date+" "+MailProxyPara.endTime,RecordLog.LOG_LEVEL_INFO);
			}
		}else{
			RecordLog.printLog("MAIL-SEND 邮件发送已停止",RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
