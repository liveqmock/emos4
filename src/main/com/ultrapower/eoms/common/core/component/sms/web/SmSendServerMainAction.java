package com.ultrapower.eoms.common.core.component.sms.web;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.manager.Send;
import com.ultrapower.eoms.common.core.component.sms.manager.SmsProxyPara;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * 短信发送主函数入口
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-2 下午04:24:15
 */
public class SmSendServerMainAction extends BaseAction{
	
		public String status;//启动状态
		public static Send sendsm = null;
		private String startTime = SmsProxyPara.startTime;
		private String endTime = SmsProxyPara.endTime;
		
		/**
		 * 短信发送查询页面
		 * @return
		 */
		public String SmQueryList(){
			return SUCCESS;
		}
		
		/**
		 * 调用短信管理页面
		 * @return
		 */
		public String smsManager(){
			status = SmsProxyPara.sendStatus;
			return SUCCESS;
		}
		
		
		/**
		 * 启动
		 */
		public String startSm(){
			SmsProxyPara.sendStatus = "0";
			status = "0";//启动
			return SUCCESS;
		}
		
		/**
		 * 停止
		 */
		public String stopSm(){
			SmsProxyPara.sendStatus = "1";
			status = "1";//停止
			return SUCCESS;
		}
		
		/**
		 * 短信发送日志查询页面
		 * @return
		 */
		public String smslogList(){
			return SUCCESS;
		}
		
		//短信发送入口
		public void send(){
			if(null!=SmsProxyPara.sendStatus && "0".equals(SmsProxyPara.sendStatus)){
				Long now = System.currentTimeMillis()/1000;
				String date = TimeUtils.formatIntToDateString(now).split(" ")[0];
				Long smstart = TimeUtils.formatDateStringToInt(date+" "+SmsProxyPara.startTime,"yy-MM-dd HH:mm");
				Long smend = TimeUtils.formatDateStringToInt(date+" "+SmsProxyPara.endTime,"yy-MM-dd HH:mm");
				if(null==sendsm){
					sendsm = new Send();
				}
				if(smstart < now && smend > now){
					sendsm.sendSm();
				}else{
					RecordLog.printLog("SMS-SEND 短信未到发送时间："+date+" "+SmsProxyPara.startTime+"--"+date+" "+SmsProxyPara.endTime,RecordLog.LOG_LEVEL_INFO);
				}
			}else{
				RecordLog.printLog("SMS-SEND 短信发送已停止",RecordLog.LOG_LEVEL_INFO);
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
