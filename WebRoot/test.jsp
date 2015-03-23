<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.axis.client.Call"%>
<%@page import="com.ultrapower.wfinterface.clients.Webservices"%>
<%@page import="org.apache.axis.encoding.XMLType"%>
<%@page import="javax.xml.rpc.ParameterMode"%>
<%@page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发布接口测试</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  	<%
  	String detail = "";
	detail = "";
	String result="";
	try{
		//test
		String webServerAddress="http://127.0.0.1:8080/emos/services/ReleaseProcessSheet";
		
		Call m_Call =Webservices.initWebServerInfo(webServerAddress,"newWorkSheet");
		m_Call.addParameter("serSupplier",   XMLType.XSD_STRING,   ParameterMode.IN);
		m_Call.addParameter("serCaller",   XMLType.XSD_STRING,   ParameterMode.IN);
		m_Call.addParameter("callerPwd",   XMLType.XSD_STRING,   ParameterMode.IN);
		m_Call.addParameter("callTime",   XMLType.XSD_STRING,   ParameterMode.IN);
		m_Call.addParameter("opDetail",   XMLType.XSD_STRING,   ParameterMode.IN);
		detail = "<opdetail>" +
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>标题</fieldChName><fieldEnName>baseSummary</fieldEnName><fieldContent>标题测试fffffffff</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>建单人OA号</fieldChName><fieldEnName>requestOA</fieldEnName><fieldContent>C0113007</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>应用系统</fieldChName><fieldEnName>connBusSystem</fieldEnName><fieldContent>D011</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>关联变更单号</fieldChName><fieldEnName>connBaseid</fieldEnName><fieldContent>00001</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型1</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型2</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型3</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型4</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型4</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型4</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
							"<recordInfo>" + 
							"<fieldInfo><fieldChName>变更类型</fieldChName><fieldEnName>chgType</fieldEnName><fieldContent>类型4</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更名称</fieldChName><fieldEnName>chgName</fieldEnName><fieldContent>机房变更</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更个数</fieldChName><fieldEnName>chgNum</fieldEnName><fieldContent>10</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>变更内容说明</fieldChName><fieldEnName>chgContent</fieldEnName><fieldContent>测试一下</fieldContent></fieldInfo>" +
							"<fieldInfo><fieldChName>涉及模块</fieldChName><fieldEnName>chgModule</fieldEnName><fieldContent>模块一</fieldContent></fieldInfo>" +
							"</recordInfo>"+
							
					"</opdetail>";
		
		result = (String)m_Call.invoke(new Object[] {"serSupplier","serCaller","callerPwd",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"),detail});
		
	}catch(Exception ex)
	{
		ex.printStackTrace();
	}
	System.out.println("result:"+result);
  		
  	 %>
  	 <%=result %>
  </body>
</html>
