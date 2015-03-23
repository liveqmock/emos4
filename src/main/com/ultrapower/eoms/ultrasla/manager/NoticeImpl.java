package com.ultrapower.eoms.ultrasla.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.ultrapower.eoms.common.core.component.email.EmailBean;
import com.ultrapower.eoms.common.core.component.email.MailApi;
import com.ultrapower.eoms.common.core.component.sms.service.InsideSmsService;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.extend.notice.service.NoticeFilterService;
import com.ultrapower.eoms.ultrasla.exception.UnInitializationException;
import com.ultrapower.eoms.ultrasla.service.INoticeService;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;
import com.ultrapower.eoms.ultrasla.util.Receiver;
import com.ultrapower.eoms.ultrasla.util.UltraSLAMessage;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.RecType;

public class NoticeImpl implements INoticeService {
	private InsideSmsService smSendService;
	private MailApi emailSendService;
	private NoticeFilterService noticeFilterService;
	
	public NoticeImpl() {
		//
	}
	
	public void notice(Map<RecType, List<Receiver>> receiver,
			UltraSLAMessage message, long noticetype, Map<String,String> param) {
		if(receiver==null||message==null) {
			return;
		}
		if(noticetype==ConstantMark.SEND_TYPE_SM){
			//发送短信
			try {
				List<Receiver> tempre = receiver.get(RecType.NOTICE);//通知
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.DUPLICATE);//抄送
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.UPGRADE);//升级
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.DEFAULT);//默认
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.COMP);//组件
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.FIXED);//指定通知
				sendShortMessage(tempre,message,param);
			} catch (UnInitializationException e) {
				e.printStackTrace();
			}
		}else if(noticetype==ConstantMark.SEND_TYPE_EMAIL){
			//发送邮件
			try {
				List<Receiver> mailTo = new ArrayList<Receiver>();//收件人
				if(receiver.get(RecType.NOTICE)!=null){
					mailTo.addAll(receiver.get(RecType.NOTICE));
				}
				if(receiver.get(RecType.UPGRADE)!=null){
					mailTo.addAll(receiver.get(RecType.UPGRADE));
				}
				if(receiver.get(RecType.DEFAULT)!=null){
					mailTo.addAll(receiver.get(RecType.DEFAULT));
				}
				if(receiver.get(RecType.COMP)!=null){
					mailTo.addAll(receiver.get(RecType.COMP));
				}
				if(receiver.get(RecType.FIXED)!=null){
					mailTo.addAll(receiver.get(RecType.FIXED));
				}
				List<Receiver> ccTo = null; //抄送人
				if(receiver.get(RecType.DUPLICATE)!=null){
					ccTo = receiver.get(RecType.DUPLICATE);
				}
				sendEmail(mailTo, ccTo, message);
			} catch (UnInitializationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}else if(noticetype==ConstantMark.SEND_TYPE_SMANDEMAIL){
			//发送邮件和短信
			try {
				List<Receiver> tempre = receiver.get(RecType.NOTICE);//通知
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.DUPLICATE);//抄送
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.UPGRADE);//升级
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.DEFAULT);//默认
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.COMP);//组件
				sendShortMessage(tempre,message,param);
				tempre = receiver.get(RecType.FIXED);//指定通知
				sendShortMessage(tempre,message,param);

				List<Receiver> mailTo = new ArrayList<Receiver>();//收件人
				if(receiver.get(RecType.NOTICE)!=null){
					mailTo.addAll(receiver.get(RecType.NOTICE));
				}
				if(receiver.get(RecType.UPGRADE)!=null){
					mailTo.addAll(receiver.get(RecType.UPGRADE));
				}
				if(receiver.get(RecType.DEFAULT)!=null){
					mailTo.addAll(receiver.get(RecType.DEFAULT));
				}
				if(receiver.get(RecType.COMP)!=null){
					mailTo.addAll(receiver.get(RecType.COMP));
				}
				if(receiver.get(RecType.FIXED)!=null){
					mailTo.addAll(receiver.get(RecType.FIXED));
				}
				List<Receiver> ccTo = null; //抄送人
				if(receiver.get(RecType.DUPLICATE)!=null){
					ccTo = receiver.get(RecType.DUPLICATE);
				}
				sendEmail(mailTo, ccTo, message);
			} catch (UnInitializationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	//发送短信
	private void sendShortMessage(List<Receiver> receivers, UltraSLAMessage message, Map<String,String> param) throws UnInitializationException{
		if(receivers==null){
			return;
		}
		if(smSendService==null){
			throw new UnInitializationException("未初始化异常：'smSendService' in com.ultrapower.eoms.ultrasla.manager.NoticeImpl");
		}
		if(message.getContent()!=null){
			StringBuffer msg = new StringBuffer();
			//主题
			if(!"".equals(StringUtils.checkNullString(message.getTopic()))){
				msg.append("【");
				msg.append(message.getTopic());
				msg.append("】");
			}
			//内容
			msg.append(message.getContent());
			//描述
			if(!"".equals(StringUtils.checkNullString(message.getRemark()))) {
				msg.append("（");
				msg.append(message.getRemark());
				msg.append("）");
			}
			long delayTime;
			for(Receiver receiver:receivers){
				if(receiver.getMobile()!=null) {
					
					if(ConstantMark.OPEN_NOTICE_FILTER 
							&& noticeFilterService!=null
							&& param!=null
							&& receiver.getLoginName()!=null) {
						//需要进行通知过滤
						if(1==noticeFilterService.doNoticeFilter(param.get("BASESCHEMA"), receiver.getLoginName(), param)) {
							delayTime = noticeFilterService.getDelayTime(param.get("BASESCHEMA"), receiver.getLoginName(), param);
							//发短信通知
							smSendService.sendsm(receiver.getMobile(), msg.toString(), "UltraSLA", param.get("ACTIONID"), 0);
						}
					} else {
						//发短信通知
						smSendService.sendsm(receiver.getMobile(), msg.toString(), "UltraSLA", param.get("ACTIONID"), 0);
					}
				}
			}
		}
	}
	
	//发送EMAIL（参数：收件人 抄送人 消息）
	private void sendEmail(List<Receiver> mailTo, List<Receiver> ccTo, UltraSLAMessage message) throws UnInitializationException, IOException, MessagingException{
		if(mailTo==null||mailTo.size()<=0){
			return;
		}
		if(emailSendService==null){
			throw new UnInitializationException("未初始化异常：'emailSendService' in com.ultrapower.eoms.ultrasla.manager.NoticeImpl");
		}
		if(message.getContent()!=null) {
			EmailBean emailBean = new EmailBean();
			List<String> mailAddres = new ArrayList<String>(mailTo.size());//收件人地址
			for(Receiver rec:mailTo){
				if(rec.getEmail()!=null){
					mailAddres.add(rec.getEmail());
				}
			}
			emailBean.setMailTo(mailAddres);
			if(ccTo!=null){
				List<String> ccAddres = new ArrayList<String>(ccTo.size());//抄送人地址
				for(Receiver rec:ccTo){
					if(rec.getEmail()!=null){
						ccAddres.add(rec.getEmail());
					}
				}
				emailBean.setMailccTo(ccAddres);
			}
			emailBean.setSubject(StringUtils.checkNullString(message.getTopic()));
			StringBuffer content = new StringBuffer(message.getContent());
			if(!"".equals(StringUtils.checkNullString(message.getRemark()))) {
				content.append("（");
				content.append(message.getRemark());
				content.append("）");
			}
			emailBean.setMsgContent(message.getContent());
			emailSendService.setEmailBean(emailBean);
			emailSendService.sendMail("html");
		}
	}
	
	public void setSmSendService(InsideSmsService smSendService) {
		this.smSendService = smSendService;
	}

	public void setEmailSendService(MailApi emailSendService) {
		this.emailSendService = emailSendService;
	}

	public void setNoticeFilterService(NoticeFilterService noticeFilterService) {
		this.noticeFilterService = noticeFilterService;
	}

}
