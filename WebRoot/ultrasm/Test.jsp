<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="com.ultrapower.eoms.common.core.util.Internation" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'Test.jsp' starting page</title>
    <%@ include file="/common/core/taglibs.jsp"%>
  </head>
  
  <body>
    <%=Internation.language("com_t_title")%>:<input name="title"/>
    <br>
    <eoms:lable name="com_t_title"/>:<input name="title"/>
  </body>
</html>
