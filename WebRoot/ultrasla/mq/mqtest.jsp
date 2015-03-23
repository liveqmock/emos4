<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.ultrasla.mq.SLAMessageSender"%>
<%@page import="com.ultrapower.eoms.ultrasla.mq.test.TestMsgBody;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Message Test</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <%
    	org.springframework.web.context.WebApplicationContext webctx = 
    		org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(application);
    	Object obj = webctx.getBean("testSLAMessageSender");
    	if(obj!=null&&obj instanceof SLAMessageSender){
    		SLAMessageSender sender = (SLAMessageSender)obj;
    		for(int i=1;i<=100;i++){
    			TestMsgBody tmb = new TestMsgBody();
    			sender.sendMessage(tmb);
    		}
    		out.print("I have send 100 message, please check the console!");
    	} 
     %>
  </body>
</html>
