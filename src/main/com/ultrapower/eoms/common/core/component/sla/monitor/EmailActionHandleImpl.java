package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.io.IOException;
import javax.mail.MessagingException;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.email.EmailBean;
import com.ultrapower.eoms.common.core.component.email.MailApi;


/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-15 上午10:44:14
 * @descibe 
 */
public class EmailActionHandleImpl implements IActionHandle{

	public void execute(Object obj) {
		if(obj!=null){
			if(obj instanceof EmailBean){
				EmailBean emailBean = (EmailBean)obj;
				MailApi mailApi = new MailApi(emailBean);
				int result = 0;
				try {
					result = mailApi.sendMail("html");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				if(result==0){
					RecordLog.printLog("调用邮件接口,发送邮件。成功!", RecordLog.LOG_LEVEL_INFO);
				}else{
					RecordLog.printLog("调用邮件接口,发送邮件。失败!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("调用邮件接口,发送邮件。非法的邮件消息体对象", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
}
