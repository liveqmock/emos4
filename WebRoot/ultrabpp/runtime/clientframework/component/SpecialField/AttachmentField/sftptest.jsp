<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.util.ftpclient.FtpClientSftp"%>
<%@page import="com.ultrapower.eoms.common.core.util.ftpclient.IFtpClientServices"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sftptest.jsp' starting page</title>
    
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
    	/*
    	//上传
    	IFtpClientServices upload = new FtpClientSftp();
    	try{
    		                                //ip 端口 用户名 密码
    		boolean b = upload.connectServer("192.168.101.13", 22, "hdpasm", "hdpasm"); 
    		if(b){
    			// 存储路径 存储名称 源文件路径 源文件名称
    			upload.uploadFile("path1/path2", "copy.txt", "d:\\", "source.txt");
    			upload.closeServer();
    			out.print("上传成功，请确认！");
    		}else{
    			out.println("未能成功连接sftp服务器！");
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    		out.println("产生异常，查看后台日志！");
    	}
    	*/
    	
    	//下载
    	IFtpClientServices download = new FtpClientSftp();
    	try{
    		                                //ip 端口 用户名 密码
    		boolean b = download.connectServer("192.168.101.13", 22, "hdpasm", "hdpasm"); 
    		if(b){
    			// 存储路径 存储名称 源文件路径 源文件名称
    			download.downloadFile("path1/path2", "copy.txt", "d:\\hello\\hello\\test", "source_download.txt");
    			download.closeServer();
    			out.print("下载成功，请确认！");
    		}else{
    			out.println("未能成功连接sftp服务器！");
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    		out.println("产生异常，查看后台日志！");
    	}
    	//java.io.File f = new java.io.File("/file.txt");
    	//out.println(f.getParent());
     %>
  </body>
</html>
