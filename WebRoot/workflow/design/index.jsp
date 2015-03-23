<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.workflow.bizconfig.version.IWfVersionManager"%>
<%@page import="com.ultrapower.workflow.client.WorkFlowServiceClient"%>
<%@page import="com.ultrapower.workflow.configuration.version.model.WfVersion"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<%@page import="com.ultrapower.eoms.workflow.visioanalyze.AnalyzeHandler"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>流程图操作页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>&nbsp; 
	自由流程图查看<br>
  <%
  AnalyzeHandler handler = new AnalyzeHandler();
  handler.testAnalyze();
  /**
  String tname = RemedySession.UtilInfor.getTableName("WF:App_Base_Infor");
  QueryAdapter queryAdapter  = new QueryAdapter();
  String sql = "select * from t"+tname+" where C700000041='0'";
  DataTable dt = queryAdapter.executeQuery(sql,null,0,0,0);
  for(int i=0;i<dt.length();i++){
  	DataRow dr = dt.getDataRow(i);
  	String baseid =  dr.getString("C700000000");
	String baseschema = dr.getString("C700000001");
	String summay = dr.getString("C700000011");
  %>
  <a href="workflow/flowmap/flowMap.jsp?baseid=<%=baseid%>&baseschema=<%=baseschema%>&type=free"><%=summay%></a><br>
  <%
  }
  %>
  	固定流程图查看<br>
  <%
  String tname1 = RemedySession.UtilInfor.getTableName("WF:App_Base_Infor");
  QueryAdapter queryAdapter1  = new QueryAdapter();
  String sql1 = "select * from t"+tname1+" where C700000041='1'";
  DataTable dt1 = queryAdapter1.executeQuery(sql1,null,0,0,0);
  for(int i=0;i<dt1.length();i++){
  	DataRow dr = dt1.getDataRow(i);
  	String baseid =  dr.getString("C700000000");
	String baseschema = dr.getString("C700000001");
	String tplid = dr.getString("C700000022");
	String summay = dr.getString("C700000011");
	
  %>
  <a href="workflow/flowmap/flowMap.jsp?baseid=<%=baseid%>&baseschema=<%=baseschema%>&tplid=<%=tplid%>&type=tpl"><%=baseid%></a><br>
  <%
  }
  **/
  %>
  </body>
</html>
