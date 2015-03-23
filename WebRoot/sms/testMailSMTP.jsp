<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="javax.mail.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.mail.internet.*"%>
<%@ page import="com.ultrapower.eoms.common.core.component.sms.manager.MailProxyPara"%>
<%@ page import="com.ultrapower.eoms.common.core.component.sms.manager.TestMailAuthenticator" %>
<%
String smtphost = null==request.getParameter("smtphost")?MailProxyPara.MAIL_SMTP_HOST:(String)request.getParameter("smtphost");
String smtpport = null==request.getParameter("smtpport")?MailProxyPara.MAIL_SMTP_PORT:(String)request.getParameter("smtpport");
String user = null==request.getParameter("user")?MailProxyPara.MAIL_FROM_USER:(String)request.getParameter("user");
String pass = null==request.getParameter("pass")?MailProxyPara.MAIL_FROM_PASS:(String)request.getParameter("pass");
String addtitle = null==request.getParameter("addtitle")?MailProxyPara.MAIL_FROM_ADDTITLE:(String)request.getParameter("addtitle");
String add = null==request.getParameter("add")?MailProxyPara.MAIL_FROM_ADD:(String)request.getParameter("add");
String mailtitle = null==request.getParameter("mailtitle")?"":(String)request.getParameter("mailtitle");
String mailcontent = null==request.getParameter("mailcontent")?"":(String)request.getParameter("mailcontent");
String tomail = null==request.getParameter("tomail")?"":(String)request.getParameter("tomail");
String sendflag = null==request.getParameter("tomail")?"":(String)request.getParameter("sendflag");
String result = "";
if("1".equals(sendflag)){
	String[] mail = tomail.split(";");
	try {
		// 由邮件会话新建一个消息对象
		Properties pro = new Properties();
		pro.put("mail.smtp.host", smtphost);
		pro.put("mail.smtp.port", smtpport);
		pro.put("mail.smtp.auth", "true");
		Authenticator auth = new TestMailAuthenticator(user,pass);
		Session sendMailSession = Session.getInstance(pro, auth);
	
		MimeMessage mailMessage = new MimeMessage(sendMailSession);
		InternetAddress from;
		try {
			String emailfromAddTitle = addtitle;
			if (emailfromAddTitle == null) {
				emailfromAddTitle = "IT服务台";
			}
			from = new InternetAddress(MimeUtility
					.encodeText(emailfromAddTitle)
					+ "<" + add + ">");
			mailMessage.setFrom(from);
			mailMessage.setSentDate(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 设置收件人邮箱
		Address[] to = new InternetAddress[mail.length];
		for(int i=0;i<mail.length;i++){
			 to[i] = new InternetAddress(mail[i]);
		}
		mailMessage.setRecipients(Message.RecipientType.TO,to);
		
		// 设置主题 
		mailMessage.setSubject(mailtitle, "GB2312");
		
	    // 设置内容及ContentType和编码方式
		mailMessage.setContent(mailcontent, "text/html;charset=GB2312");
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

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			var result = "<%=result%>";
			if(""!=result){
				alert(result);
			}
		</script>
	</head>

	<body>
		<form method="post">
		<input type="hidden" name="sendflag" value="1"/>
		<input type="submit" value="发送"/><br/>
		SMTP地址：<input name="smtphost" value="<%=smtphost %>"/><br/>
		SMTP端口：<input name="smtpport" value="<%=smtpport %>"	/><br/>
		发送人：<input name="user" value="<%=user %>" style="width:1000px"/><br/>
		发送人密码：<input name="pass" value="<%=pass %>" style="width:1000px"/><br/>
		发送人邮件地址：<input name="add" value="<%=add %>" style="width:1000px"/><br/>
		发送人显示名：<input name="addtitle" value="<%=addtitle %>" style="width:1000px"/><br/>
		收件人(;分隔)：<input name="tomail" value="<%=tomail %>" style="width:1000px"/><br/>
		发送标题：<input name="mailtitle" value="<%=mailtitle %>" style="width:1000px"/><br/>
		发送内容：
		<textarea rows="20" cols="130" name="mailcontent"><%=mailcontent %></textarea>
		</form>
	</body>
</html>
