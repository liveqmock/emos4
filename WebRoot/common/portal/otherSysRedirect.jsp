<%@page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'kmRedirect.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <%
    List<Map> list = new ArrayList<Map>();
   	QueryAdapter query = new QueryAdapter();
   	DataTable dt = query.executeQuery("select remark from bs_t_sm_dicitem where dtcode='redirectConf' and divalue='"+request.getParameter("url")+"'");
   
    for(Object obj:dt.getRowList()){
		DataRow dr = (DataRow)obj;
		list.add(dr.getRowHashMap());
	}
    String redirectUrl = (String)list.get(0).get("REMARK");
	response.sendRedirect("http://"+request.getServerName()+redirectUrl);
	%>
  </body>
</html>
