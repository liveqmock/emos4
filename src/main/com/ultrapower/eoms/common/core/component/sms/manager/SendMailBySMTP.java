package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class SendMailBySMTP {
	
	public String send(String sendMails,String subject,String contents) {
		String result = "";
		if(sendMails!= null){
			String[] mail = sendMails.split(";");
			
			try {
				// 由邮件会话新建一个消息对象
				MimeMessage mailMessage = buildMailMsg();
				
				// 设置收件人邮箱
				Address[] to = new InternetAddress[mail.length];
				for(int i=0;i<mail.length;i++){
					 to[i] = new InternetAddress(mail[i]);
				}
				mailMessage.setRecipients(Message.RecipientType.TO,to);
				
				// 设置主题 
				mailMessage.setSubject(subject, "GB2312");
				
		        // 设置内容及ContentType和编码方式
				mailMessage.setContent(contents, "text/html;charset=GB2312");
				//mailMessage.setText(contents, "GB2312");
				
				//设置发送时间
				mailMessage.setSentDate(new Date());
				
		        //存储信件信息
				mailMessage.saveChanges();  
				Transport.send(mailMessage);
				result = "MAINBODY:true";
			} catch (AddressException e) {
				e.printStackTrace();
				result = "ERROR:AddressException;";
			} catch (MessagingException e) {
				e.printStackTrace();
				result = "ERROR:MessagingException;";
			} catch (Exception e){
				e.printStackTrace();
				result = "ERROR:"+e.getMessage()+";";
			}
		}
		return result;
	}
	
	public static MimeMessage buildMailMsg() {
		Properties pro = new Properties();
		pro.put("mail.smtp.host", MailProxyPara.MAIL_SMTP_HOST);
		pro.put("mail.smtp.port", MailProxyPara.MAIL_SMTP_PORT);
		pro.put("mail.smtp.auth", MailProxyPara.MAIL_SMTP_AUTH);

		Session sendMailSession = Session.getInstance(pro, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailProxyPara.MAIL_FROM_USER, MailProxyPara.MAIL_FROM_PASS);
			}
		});

		MimeMessage mailMessage = new MimeMessage(sendMailSession);
		InternetAddress from;
		try {
			String emailfromAddTitle = MailProxyPara.MAIL_FROM_ADDTITLE;
			if (emailfromAddTitle == null) {
				emailfromAddTitle = "IT服务台";
			}
			from = new InternetAddress(MimeUtility
					.encodeText(emailfromAddTitle)
					+ "<" + MailProxyPara.MAIL_FROM_ADD + ">");
			mailMessage.setFrom(from);
			mailMessage.setSentDate(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailMessage;
	}
}
