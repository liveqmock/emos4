package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.manager.ScanSmsmonitor;
import com.ultrapower.eoms.common.core.component.sms.model.Maillog;
import com.ultrapower.eoms.common.core.component.sms.model.Mailmodel;
import com.ultrapower.eoms.common.core.component.sms.model.ReturnMsg;
import com.ultrapower.eoms.common.core.component.sms.model.Smslog;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmodel;
import com.ultrapower.eoms.common.core.component.sms.service.IMaillogService;
import com.ultrapower.eoms.common.core.component.sms.service.ISmslogService;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
/**
 * 查询wf_record表,调用WS接口进行信息发送
 */
public class Send{

	private SendSmByWS sendsm = new SendSmByWS();
	private ScanSmsmonitor scanSmsmonitor = new ScanSmsmonitor();
	public static List<Smsmodel> smsModels = new ArrayList<Smsmodel>();
	private static ISmslogService smslogService = (ISmslogService)WebApplicationManager.getBean("smslogService");
	private static SmsModelImpl smsModelService = (SmsModelImpl)WebApplicationManager.getBean("smsModelService");

	private SendMailBySMTP sendMail = new SendMailBySMTP();
	private ScanMailmonitor scanMailmonitor = new ScanMailmonitor();
	public static List<Mailmodel> mailModels = new ArrayList<Mailmodel>();
	private static IMaillogService maillogService = (IMaillogService)WebApplicationManager.getBean("maillogService");
	private static MailModelImpl mailModelService = (MailModelImpl)WebApplicationManager.getBean("mailModelService");
	/**
	 * 查询并调用WS进行短信发送
	 */
	public void sendSm(){
		if(null==smsModels || smsModels.isEmpty()){
			smsModels = smsModelService.getSmsModelAll();
		}
		List<HashMap<String,String>> smList = scanSmsmonitor.getWaitingSendSm();
		int smListLen = 0;
		if(smList!=null)
			smListLen = smList.size();
		RecordLog.printLog("SMS-SEND 扫描WF_RECORD表,获取到待发的短信条数:"+smListLen,RecordLog.LOG_LEVEL_INFO);
		ReturnMsg rmObj;
		String result;
		Smslog smslog;
		String returnXml;
		String sendContent;
		for(int i=0;i<smListLen;i++){
			HashMap<String,String> smsmonitor = smList.get(i);
			RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",发送---BEGIN------------------",RecordLog.LOG_LEVEL_INFO);
			result = "";
			returnXml = "";
			rmObj = null;
			smslog = null;
			sendContent = buildSmSendContent(smsmonitor);
			if(null==smsmonitor.get("ALLMOBILE")){
				RecordLog.printLog("SMS-SEND 短信数据:"+ StringUtils.checkNullString(smsmonitor.get("PID"))+",未发现手机号，略过", RecordLog.LOG_LEVEL_INFO);
				//5:取消发送(手机号为空)
				smsmonitor.put("SMSSENDFLAG", "5");
				scanSmsmonitor.updateSm(StringUtils.checkNullString(smsmonitor.get("PID")), smsmonitor.get("SMSSENDFLAG"));
				smslog = new Smslog(smsmonitor.get("PID"), TimeUtils
						.getCurrentDate("yyyy-MM-dd HH:mm:ss"), "", "",
						smsmonitor.get("errorinfo"), sendContent, "5", "", "",
						"");
			}else{
				smslog = new Smslog();
				smslog.setRecordpid(smsmonitor.get("PID"));
				//按组发送时，可能存在组内用户没有手机号情况，将此信息保存到日志中
				smslog.setErrorinfo(smsmonitor.get("errorinfo"));
				//记录WS调用时间
				smslog.setSendbtime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				//调用短信发送服务
				result = sendsm.SendSm(StringUtils.checkNullString(smsmonitor.get("PID")), 
						StringUtils.checkNullString(smsmonitor.get("ALLMOBILE")), 
						sendContent);
				//记录WS结束时间
				smslog.setSendetime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				smslog.setSendobj(smsmonitor.get("ALLMOBILE"));
				smslog.setSmscontent(sendContent);
				smslog.setSendxml(sendsm.getSendXml());
				
				//正常返回，MAINBODY标识XML，FLOWNO标识请求流水号
				if(result.indexOf("MAINBODY:")!=-1){
					returnXml = result.substring(9);
					rmObj = buildReturnMsg(returnXml);
					smslog.setReturnxml(returnXml);
					smslog.setFlowNo(rmObj.getSrvNo());
					
					//StdCode节点为返回的编码，000000为成功
					if("000000".equals(rmObj.getStdCode())){
						RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",发送成功",RecordLog.LOG_LEVEL_INFO);
						//1:已发送
						smslog.setFlag("1");
						smsmonitor.put("SMSSENDFLAG", "1");
					}else{
						RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",发送失败",RecordLog.LOG_LEVEL_INFO);
						//2:发送失败;
						smslog.setFlag("2");
						smslog.setErrorinfo(StringUtils.checkNullString(smslog.getErrorinfo())+" ReturnMsg----StdCode:"+rmObj.getStdCode()+" StdDesc:"+rmObj.getStdDesc());
						smsmonitor.put("SMSSENDFLAG", "2");
					}
					boolean updateflag = scanSmsmonitor.updateSm(StringUtils.checkNullString(smsmonitor.get("PID")), smsmonitor.get("SMSSENDFLAG"));
					
					if(updateflag){
						RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",更新WF_RECORD表状态---succeed", RecordLog.LOG_LEVEL_INFO);
					}else{
						RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",更新WF_RECORD表状态---failing", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{//异常返回，ERROR标识WS调用时异常
					RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",调用WS时发生异常",RecordLog.LOG_LEVEL_ERROR);
					smslog.setErrorinfo(StringUtils.checkNullString(smslog.getErrorinfo())+" throwException:"+result.substring(result.indexOf("ERROR:")+6));
					smslog.setFlag("4");
					//4:服务异常
					boolean updateflag = scanSmsmonitor.updateSm(StringUtils.checkNullString(smsmonitor.get("PID")), "4");
					if(updateflag){
						RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",更新WF_RECORD表状态---succeed", RecordLog.LOG_LEVEL_INFO);
					}else{
						RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",更新WF_RECORD表状态---failing", RecordLog.LOG_LEVEL_ERROR);
					}
				}
				
			}
			//日志保存
			smslogService.save(smslog);
			RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",更新SMSLOG表状态---succeed", RecordLog.LOG_LEVEL_INFO);
			RecordLog.printLog("SMS-SEND 短信数据:"+StringUtils.checkNullString(smsmonitor.get("PID"))+",发送---END--------------------",RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	/**
	 * 查询并调用SMTP进行邮件发送
	 */
	public void sendMail(){
		if(null==mailModels || mailModels.isEmpty()){
			mailModels = mailModelService.getMailModelAll();
		}
		List<HashMap<String,String>> mailList = scanMailmonitor.getWaitingSendMail();
		int mailListLen = 0;
		if(mailList!=null)
			mailListLen = mailList.size();
		RecordLog.printLog("MAIL-SEND 扫描WF_RECORD表,获取到待发的邮件条数:"+mailListLen,RecordLog.LOG_LEVEL_INFO);
		String result;
		Maillog maillog;
		String sendContent;
		String sendTitle;
		for(int i=0;i<mailListLen;i++){
			HashMap<String,String> mailmonitor = mailList.get(i);
			RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",发送---BEGIN------------------",RecordLog.LOG_LEVEL_INFO);
			result = "";
			maillog = null;
			sendContent = buildMailSendContent(mailmonitor);
			sendTitle = buildMailSendTitle(mailmonitor);
			if(null==mailmonitor.get("ALLEMAIL")){
				RecordLog.printLog("MAIL-SEND 邮件数据:"+ StringUtils.checkNullString(mailmonitor.get("PID"))+",未发现EMAIL，略过", RecordLog.LOG_LEVEL_INFO);
				//5:取消发送(EMAIL为空)
				mailmonitor.put("EMAILSENDFLAG", "5");
				scanMailmonitor.updateEmail(StringUtils.checkNullString(mailmonitor.get("PID")), mailmonitor.get("EMAILSENDFLAG"));
				maillog = new Maillog(mailmonitor.get("PID"), TimeUtils
						.getCurrentDate("yyyy-MM-dd HH:mm:ss"), "", "",
						mailmonitor.get("errorinfo"), sendContent, "5", "", "",
						"", sendTitle);
			}else{
				maillog = new Maillog();
				maillog.setRecordpid(mailmonitor.get("PID"));
				//按组发送时，可能存在组内用户没有手机号情况，将此信息保存到日志中
				maillog.setErrorinfo(mailmonitor.get("errorinfo"));
				//记录SMTP调用时间
				maillog.setSendbtime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				//调用邮件发送服务
				result = sendMail.send(StringUtils.checkNullString(mailmonitor.get("ALLEMAIL")),sendTitle,sendContent);
				//记录SMTP结束时间
				maillog.setSendetime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				maillog.setSendobj(mailmonitor.get("ALLEMAIL"));
				maillog.setMailTitle(sendTitle);
				maillog.setMailcontent(sendContent);
				
				//正常返回，MAINBODY标识
				if(result.indexOf("MAINBODY:")!=-1){
					RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",发送成功",RecordLog.LOG_LEVEL_INFO);
					//1:已发送
					maillog.setFlag("1");
					mailmonitor.put("EMAILSENDFLAG", "1");
					
					boolean updateflag = scanMailmonitor.updateEmail(StringUtils.checkNullString(mailmonitor.get("PID")), mailmonitor.get("EMAILSENDFLAG"));
					
					if(updateflag){
						RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",更新WF_RECORD表状态---succeed", RecordLog.LOG_LEVEL_INFO);
					}else{
						RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",更新WF_RECORD表状态---failing", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{//异常返回，ERROR标识SMTP调用时异常
					RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",调用SMTP时发生异常",RecordLog.LOG_LEVEL_ERROR);
					maillog.setErrorinfo(StringUtils.checkNullString(maillog.getErrorinfo())+" throwException:"+result.substring(result.indexOf("ERROR:")+6));
					maillog.setFlag("4");
					//4:服务异常
					boolean updateflag = scanMailmonitor.updateEmail(StringUtils.checkNullString(mailmonitor.get("PID")), "4");
					if(updateflag){
						RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",更新WF_RECORD表状态---succeed", RecordLog.LOG_LEVEL_INFO);
					}else{
						RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",更新WF_RECORD表状态---failing", RecordLog.LOG_LEVEL_ERROR);
					}
				}
				
			}
			//日志保存
			maillogService.save(maillog);
			RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",更新MAILLOG表状态---succeed", RecordLog.LOG_LEVEL_INFO);
			RecordLog.printLog("MAIL-SEND 邮件数据:"+StringUtils.checkNullString(mailmonitor.get("PID"))+",发送---END--------------------",RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	public ReturnMsg buildReturnMsg(String reutrnMsg){
		ReturnMsg rm = new ReturnMsg();
		try {
			Document doc = DocumentHelper.parseText(reutrnMsg);
			Element root = doc.getRootElement();
			
			Element sys_Header= root.element("Sys_Header");
			rm.setRTS(sys_Header.element("RTS").getText());
			rm.setSrvNo(sys_Header.element("SrvNo").getText());
			rm.setSrcSys(sys_Header.element("SrcSys").getText());
			rm.setDestSys(sys_Header.element("DestSys").getText());
			
			Element trunk= root.element("Trunk");
			rm.setMsg(trunk.element("Msg").getText());
			rm.setRetMsg(trunk.element("RetMsg").getText());
			rm.setBizType(trunk.element("BizType").getText());
			
			Element status= root.element("Status");
			rm.setStdCode(status.element("StdCode").getText());
			rm.setStdDesc(status.element("StdDesc").getText());
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rm;
	}
	
	public String buildSmSendContent(HashMap<String,String> monitor){
		//如果smsModel为空，则直接使用record表中的短信内容。(基本不可能)
		String content = StringUtils.checkNullString(monitor.get("SMSCONTENT"));
		Smsmodel defaultModel = null;
		Smsmodel tModel = null;
		for(Smsmodel smsModel : smsModels){
			if(null!=smsModel.getModelType() && smsModel.getModelType().equals(monitor.get("BASESCHEMA"))){
				tModel = smsModel;
				break;
			}
			//如果找不到对应模板则使用默认模板
			if("DEFAULT".equals(smsModel.getModelType())){
				defaultModel = smsModel;
			}
		}
		
		//如果未匹配到模板，并且默认模板存在，则使用默认模板
		if(null==tModel && null!=defaultModel){
			content = defaultModel.getContent();
			for(String key : monitor.keySet()){
				content = content.replaceAll("#"+key+"#", monitor.get(key));
			}
		}else if(null!=tModel){//如果匹配到模板，则使用此模板
			content = tModel.getContent();
			for(String key : monitor.keySet()){
				content = content.replaceAll("#"+key+"#", monitor.get(key));
			}
		}
		return content;
	}

	public String buildMailSendContent(HashMap<String,String> monitor){
		//如果smsModel为空，则直接使用record表中的短信内容。(基本不可能)
		String content = StringUtils.checkNullString(monitor.get("EMAILCONTENT"));
		Mailmodel defaultModel = null;
		Mailmodel tModel = null;
		for(Mailmodel mailModel : mailModels){
			//在扩展逻辑中已经使用模板进行了替换，无需再进行替换，直接使用EMAILCONTENT内容发送。
			//正常流转，规则为：_RECORDCONTENT以此结尾，以工单schema名开头
			if(null!=mailModel.getModelType() && mailModel.getModelType().equals(monitor.get("BASESCHEMA")+"_RECORDCONTENT")){
				tModel = null;
				defaultModel = null;
				break;
			}
			//特殊邮件发送
			//CBD_INCIDENT_CREATE_RECORDCONTENT CDB_SERVICEREQUEST_CREATE_RECORDCONTENT
			//CBD_INCIDENT_CLOSE_RECORDCONTENT CDB_SERVICEREQUEST_CLOSE_RECORDCONTENT
			if (mailModel.getModelType().startsWith(monitor.get("BASESCHEMA"))
					&& mailModel.getModelType().endsWith("_RECORDCONTENT")
					&& mailModel.getModelType().equals(
							monitor.get("BASESCHEMA"))) {
				tModel = null;
				defaultModel = null;
				break;
			}
			if(null!=mailModel.getModelType() && mailModel.getModelType().equals(monitor.get("BASESCHEMA")) && !mailModel.getModelType().endsWith("_RECORDCONTENT")){
				tModel = mailModel;
			}
			//如果找不到对应模板则使用默认模板
			if("DEFAULT".equals(mailModel.getModelType())){
				defaultModel = mailModel;
			}
		}
		
		//如果未匹配到模板，并且默认模板存在，则使用默认模板
		if(null==tModel && null!=defaultModel){
			content = defaultModel.getContent();
			for(String key : monitor.keySet()){
				content = content.replaceAll("#"+key+"#", monitor.get(key));
			}
		}else if(null!=tModel){//如果匹配到模板，则使用此模板
			content = tModel.getContent();
			for(String key : monitor.keySet()){
				content = content.replaceAll("#"+key+"#", monitor.get(key));
			}
		}
		return content;
	}
	
	public String buildMailSendTitle(HashMap<String,String> monitor){
		//如果smsModel为空，则直接使用record表中的短信内容。(基本不可能)
		String content = "您好，有一张工单["+monitor.get("BASESN")+"]主题："+monitor.get("BASESUMMARY")+",请您处理！";
		Mailmodel defaultModel = null;
		Mailmodel tModel = null;
		for(Mailmodel mailModel : mailModels){
			if(null!=mailModel.getModelType() && mailModel.getModelType().equals(monitor.get("BASESCHEMA")+"_RECORDCONTENT")){
				tModel = mailModel;
				break;
			}
			if(null!=mailModel.getModelType() && mailModel.getModelType().equals(monitor.get("BASESCHEMA"))){
				tModel = mailModel;
			}
			//如果找不到对应模板则使用默认模板
			if("DEFAULT".equals(mailModel.getModelType())){
				defaultModel = mailModel;
			}
		}
		
		//如果未匹配到模板，并且默认模板存在，则使用默认模板
		if(null==tModel && null!=defaultModel){
			content = defaultModel.getMailTitle();
			for(String key : monitor.keySet()){
				content = content.replaceAll("#"+key+"#", monitor.get(key));
			}
		}else if(null!=tModel){//如果匹配到模板，则使用此模板
			content = tModel.getMailTitle();
			for(String key : monitor.keySet()){
				content = content.replaceAll("#"+key+"#", monitor.get(key));
			}
		}
		return content;
	}
	
}