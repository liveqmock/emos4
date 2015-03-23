package com.ultrapower.eoms.common.core.component.email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件接口
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-16 上午11:29:40
 */
public class MailApi {

	private EmailBean emailBean;
	
	public MailApi(){};
	public MailApi(EmailBean p_emailBean) {
		emailBean = p_emailBean;
	}

	/**
	 * 文本邮件内容
	 * @param session
	 * @param msg
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void getMailTxtMsg(Session session, MimeMessage msg) throws IOException,
			MessagingException {
		if (EmailPara.MAIL_FROM != null && !EmailPara.MAIL_FROM.equals("")) {
			msg.setFrom(new InternetAddress(EmailPara.MAIL_FROM));
			System.out.println("发送人Email地址：" + EmailPara.MAIL_FROM);
		} else {
			System.out.println("没有指定发送人邮件地址,发送失败!");
			return;
		}
		// 收件人
		int mailtolen = 0;
		if (emailBean.getMailTo() != null)
			mailtolen = emailBean.getMailTo().size();
		if (mailtolen == 0) {
			System.out.println("没有指定收件人邮件地址！");
			return;
		}
		InternetAddress[] address = new InternetAddress[mailtolen];
		for (int i = 0; i < mailtolen; i++) {
			address[i] = new InternetAddress(emailBean.getMailTo().get(i));
			System.out.println("收件人Email地址：" + emailBean.getMailTo());
		}
		msg.setRecipients(Message.RecipientType.TO, address);

		// 抄送人
		int mailccLen = 0;
		if (emailBean.getMailccTo() != null)
			mailccLen = emailBean.getMailccTo().size();
		if(mailccLen>0){
			InternetAddress[] ccaddress = new InternetAddress[mailccLen];
			for (int i = 0; i < mailccLen; i++) {
				ccaddress[i] = new InternetAddress(emailBean.getMailccTo().get(i));
				System.out.println("抄送人Email地址：" + emailBean.getMailccTo());
			}
			msg.setRecipients(Message.RecipientType.CC, ccaddress);
		}

		// 暗送人
		int mailbcctoLen = 0;
		if (emailBean.getMailbccTo() != null)
			mailbcctoLen = emailBean.getMailbccTo().size();
		if(mailbcctoLen>0){
			InternetAddress[] bccaddress = new InternetAddress[mailbcctoLen];
			for (int i = 0; i < mailbcctoLen; i++) {
				bccaddress[i] = new InternetAddress(emailBean.getMailbccTo().get(i));
				System.out.println("暗送人Email地址：" + emailBean.getMailbccTo());
			}
			msg.setRecipients(Message.RecipientType.BCC, bccaddress);
		}

		msg.setSubject(emailBean.getSubject());// 设定主题
		msg.setSentDate(new Date());// 设定发送时间
		InternetAddress[] replyAddress = { new InternetAddress(
				EmailPara.MAIL_FROM) };
		msg.setReplyTo(replyAddress);
		MimeBodyPart mBodyContent = new MimeBodyPart();// 内容封装
		if (emailBean.getMsgContent() != null&& !emailBean.getMsgContent().equals(""))
			mBodyContent.setContent(emailBean.getMsgContent(), emailBean.getMessageContentMimeType());
		else
			mBodyContent.setContent("", emailBean.getMessageContentMimeType());
		Multipart mPart = new MimeMultipart();
		mPart.addBodyPart(mBodyContent);
		if (emailBean.getAttachedFileList() != null) {// 封装附件
			for (Enumeration fileList = emailBean.getAttachedFileList()
					.elements(); fileList.hasMoreElements();) {
				try {
					String attfile = (String) fileList.nextElement();
					File file = new File(attfile);
					if (!file.exists())
						continue;
					MimeBodyPart mBodyPart = new MimeBodyPart();// 附件封装
					FileDataSource fds = new FileDataSource(attfile);
					System.out.println("Mail发送的附件：" + attfile);
					mBodyPart.setDataHandler(new DataHandler(fds));
					String fileName = file.getName();
					fileName = new String(fileName.getBytes("gbk"),
							"ISO-8859-1");
					mBodyPart.setFileName(fileName);
					mPart.addBodyPart(mBodyPart);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		msg.setContent(mPart);
		msg.setSentDate(new Date());
	}
	
	/**
	 * html邮件内容
	 * @param session
	 * @param msg
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void getMailHtmlMsg(Session session, MimeMessage msg) throws IOException,
			MessagingException {
		if (EmailPara.MAIL_FROM != null && !EmailPara.MAIL_FROM.equals("")) {
			msg.setFrom(new InternetAddress(EmailPara.MAIL_FROM));
			System.out.println("发送人Email地址：" + EmailPara.MAIL_FROM);
		} else {
			System.out.println("没有指定发送人邮件地址,发送失败!");
			return;
		}
		// 收件人
		int mailtolen = 0;
		if (emailBean.getMailTo() != null)
			mailtolen = emailBean.getMailTo().size();
		if (mailtolen == 0) {
			System.out.println("没有指定收件人邮件地址！");
			return;
		}
		InternetAddress[] address = new InternetAddress[mailtolen];
		for (int i = 0; i < mailtolen; i++) {
			address[i] = new InternetAddress(emailBean.getMailTo().get(i));
			System.out.println("收件人Email地址：" + emailBean.getMailTo());
		}
		msg.setRecipients(Message.RecipientType.TO, address);

		// 抄送人
		int mailccLen = 0;
		if (emailBean.getMailccTo() != null)
			mailccLen = emailBean.getMailccTo().size();
		if(mailccLen>0){
			InternetAddress[] ccaddress = new InternetAddress[mailccLen];
			for (int i = 0; i < mailccLen; i++) {
				ccaddress[i] = new InternetAddress(emailBean.getMailccTo().get(i));
				System.out.println("抄送人Email地址：" + emailBean.getMailccTo());
			}
			msg.setRecipients(Message.RecipientType.CC, ccaddress);
		}

		// 暗送人
		int mailbcctoLen = 0;
		if (emailBean.getMailbccTo() != null)
			mailbcctoLen = emailBean.getMailbccTo().size();
		if(mailbcctoLen>0){
			InternetAddress[] bccaddress = new InternetAddress[mailbcctoLen];
			for (int i = 0; i < mailbcctoLen; i++) {
				bccaddress[i] = new InternetAddress(emailBean.getMailbccTo().get(i));
				System.out.println("暗送人Email地址：" + emailBean.getMailbccTo());
			}
			msg.setRecipients(Message.RecipientType.BCC, bccaddress);
		}

		msg.setSubject(emailBean.getSubject());// 设定主题
		msg.setSentDate(new Date());// 设定发送时间
		InternetAddress[] replyAddress = { new InternetAddress(EmailPara.MAIL_FROM) };
		msg.setReplyTo(replyAddress);
		
	    //MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
	    Multipart mainPart = new MimeMultipart();    
	    //创建一个包含HTML内容的MimeBodyPart    
	    BodyPart html = new MimeBodyPart();    
	    //设置HTML内容     
	    html.setContent(emailBean.getMsgContent(), "text/html; charset=utf-8");    
	    mainPart.addBodyPart(html); 
	    //将MiniMultipart对象设置为邮件内容 
	    msg.setContent(mainPart); 

		Multipart mPart = new MimeMultipart();
		mPart.addBodyPart(html);
		if (emailBean.getAttachedFileList() != null) {// 封装附件
			for (Enumeration fileList = emailBean.getAttachedFileList().elements(); fileList.hasMoreElements();) {
				try {
					String attfile = (String) fileList.nextElement();
					File file = new File(attfile);
					if (!file.exists())
						continue;
					MimeBodyPart mBodyPart = new MimeBodyPart();// 附件封装
					FileDataSource fds = new FileDataSource(attfile);
					System.out.println("Mail发送的附件：" + attfile);
					mBodyPart.setDataHandler(new DataHandler(fds));
					String fileName = file.getName();
					fileName = new String(fileName.getBytes("gbk"),
							"ISO-8859-1");
					mBodyPart.setFileName(fileName);
					mPart.addBodyPart(mBodyPart);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		msg.setContent(mPart);
		msg.setSentDate(new Date());
	}
    

	/**
	 * 发送e_mail，返回类型为int 当返回值为0时，说明邮件发送成功 当返回值为1时，说明邮件发送失败
	 * contentType html、txt
	 */
	public int sendMail(String contentType) throws IOException, MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", EmailPara.MAIL_SMTP_HOST);// 设置邮件smtp服务器地址
		props.put("mail.smtp.port", EmailPara.MAIL_SMTP_HOST_PORT);  
		props.put("mail.smtp.auth", EmailPara.MAIL_SMTP_AUTH);// 设置服务器smtp需要验证
		MailAuthenticator auth = new MailAuthenticator();
		//Session session = Session.getInstance(props, auth);
		Session session = Session.getDefaultInstance(props, auth); 
		session.setDebug(EmailPara.MAIL_DEBUG);
		MimeMessage msg = new MimeMessage(session);
		Transport trans = null;
		try {
			if(contentType.equals("html"))
				getMailHtmlMsg(session, msg);
			else
				getMailTxtMsg(session, msg);
			trans = session.getTransport("smtp");
			try {
				trans.connect(EmailPara.MAIL_SMTP_HOST, EmailPara.MAIL_USER, EmailPara.MAIL_PASSWORD);
			} catch (AuthenticationFailedException e) {
				e.printStackTrace();
				System.out.println("邮件服务器连接错误!" + e.getMessage());
				return 1;
			} catch (MessagingException e) {
				System.out.println("邮件服务器连接错误!" + e.getMessage());
				return 1;
			}
			trans.send(msg);
			trans.close();
		} catch (MessagingException mex) {
			System.out.println("邮件发送失败!" + mex.getMessage());
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				System.out.println(ex.toString());
				ex.printStackTrace();
			}
			return 1;
		} finally {
			try {
				if (trans != null && trans.isConnected())
					trans.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("邮件发送成功!");
		return 0;

	}

	/**
	 * 接收邮件
	 */
	public void reciverMail(){
		//创建一个有具体连接信息的Properties对象
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "pop3");
		props.setProperty("mail.pop3.host", EmailPara.MAIL_P0P3_HOST);
		//使用Properties对象获得Session对象
		Session session = Session.getInstance(props);
		session.setDebug(EmailPara.MAIL_DEBUG);
		//利用Session对象获得Store对象，并连接pop3服务器
		Store store = null;
		try{
			store = session.getStore();
			store.connect(EmailPara.MAIL_P0P3_HOST, EmailPara.MAIL_USER, EmailPara.MAIL_PASSWORD);
		}catch (NoSuchProviderException e) {
			e.printStackTrace();
		}catch (MessagingException e) {
			e.printStackTrace();
		}
		//获得邮箱内的邮件夹Folder对象，以"只读"打开
		Folder folder = null;
		//获得邮件夹Folder内的所有邮件Message对象
		Message[] messages = null;
		int mailCounts = 0;
		try {
			folder = store.getFolder("inbox");//inbox邮箱目录
			folder.open(Folder.READ_ONLY);
			messages = folder.getMessages();
			mailCounts = messages.length;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < mailCounts; i++) {
			String subject = "";
			String from = "";
			InternetAddress[] toAddress = null;
			InternetAddress[] ccAddress = null;
			InternetAddress[] bccAddress = null;
			try {
				subject = messages[i].getSubject();
				from = (messages[i].getFrom()[0]).toString();
				toAddress = (InternetAddress[]) messages[i].getRecipients(Message.RecipientType.TO);
				ccAddress = (InternetAddress[]) messages[i].getRecipients(Message.RecipientType.CC);
				bccAddress = (InternetAddress[]) messages[i].getRecipients(Message.RecipientType.BCC);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			System.out.println("第" + (i + 1) + "封邮件,主题：" + subject);
			System.out.println("发件人：" + from);
			
			if(toAddress!=null){
				String toEmail = "";//邮件接收者
				for(int a=0;a<toAddress.length;a++){
					toEmail += toAddress[a].getAddress() + ";";
				}
				if(!toEmail.equals(""))
					toEmail = toEmail.substring(0, toEmail.length()-1);
				System.out.println("邮件发送给："+toEmail);
			}
			
			if(ccAddress!=null){
				String ccEmail = "";//抄送者
				for(int c=0;c<ccAddress.length;c++){
					ccEmail += ccAddress[c].getAddress() + ";";
				}
				if(!ccEmail.equals(""))
					ccEmail = ccEmail.substring(0, ccEmail.length()-1);
				System.out.println("邮件抄送给："+ccEmail);
			}
			
			if(bccAddress!=null){
				String bccEmail = "";
				for(int b=0;b<bccAddress.length;b++){
					bccEmail += bccAddress[b].getAddress() + ";";
				}
				if(!bccEmail.equals(""))
					bccEmail = bccEmail.substring(0, bccEmail.length()-1);
				System.out.println("邮件暗送给："+bccEmail);
			}
			
//			System.out.println("是否打开该邮件(yes/no)?：");
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			String input;
//			try{
//				input = br.readLine();
//				if ("yes".equalsIgnoreCase(input))//直接输出到控制台中
//					messages[i].writeTo(System.out);
//			}catch (IOException e) {
//				e.printStackTrace();
//			}catch(MessagingException e){
//				e.printStackTrace();
//			}
		}
		try {
			folder.close(false);
		} catch (MessagingException e) {
			e.printStackTrace();
		}finally{
			try{
				store.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	class MailAuthenticator extends Authenticator{
		public PasswordAuthentication getPasswordAuthentication(){ 
	        return new PasswordAuthentication(EmailPara.MAIL_USER, EmailPara.MAIL_PASSWORD); 
	    } 
	}

	public EmailBean getEmailBean() {
		return emailBean;
	}
	
	public void setEmailBean(EmailBean emailBean) {
		this.emailBean = emailBean;
	}
	
	public static void main(String[] argv) throws Exception{
		//测试邮件发送
		EmailBean emailBean = new EmailBean();
		List to = new ArrayList();
		to.add("zhuzhaohui@ultrapower.com.cn");
		emailBean.setMailTo(to);
		emailBean.setSubject("测试邮件");
		emailBean.setMsgContent("系统管理员:<br/>&nbsp;您好<br/>&nbsp;您提出的数据查询请求查询单已经完成，工单号为：ID-04-20120717-00006。请与我部系统管理员(88888888)联系提取查询结果，谢谢。<br/><div align=\"right\">信息管理部信息统计室</div><br/>");
//		to.add("zhuzhaohui@ultrapower.com.cn");

		MailApi smMail = new MailApi(emailBean);
		smMail.sendMail("html");
		
		//测试邮件接收
//		MailApi reciver = new MailApi();
//		reciver.reciverMail();
	}
}
