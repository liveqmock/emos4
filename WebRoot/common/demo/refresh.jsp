<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.ultrapower.eoms.common.core.component.rquery.startup.StartUp"%>
<%@page import="com.ultrapower.eoms.common.core.component.rquery.util.RConstants"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%@page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<%@ include file="/common/core/taglibs.jsp"%>   
    
    <title>sql refresh</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
	function allRefresh()
	{
		//alert(filename);
		//alert(document.getElementById("allfile").value);
		document.getElementById("allfile").value="1";
		//alert(document.getElementsByName("allfile").value);
		document.forms[0].submit();
	}
	
	function refresh(filename)
	{
		//alert(filename);
		document.getElementById("filename").value=filename;
		document.forms[0].submit();
	}
	</script>
  </head>
  
  <body>
  	<%
  	
  	String filename=StringUtils.checkNullString(request.getParameter("filename"));
  	String allfile=StringUtils.checkNullString(request.getParameter("allfile"));
  	System.out.println("----------->"+allfile);
  	if(allfile.equals("1"))
  	{
  		StartUp.loadFile("");//刷新全部文件
  	}
  	else if(!filename.equals(""))
  	{
  		StartUp.loadXml("",filename);
  	}	
  	/*
  	StartUp.loadFile("");
  	Iterator it = StartUp.sqlmapElement.entrySet().iterator();
  	while (it.hasNext())
  	{
  		Entry entry =  (Entry)it.next();
  		out.println("---->"+entry.getKey().toString());
  	}
  	*/
  	DataTable dt= StartUp.sqlDt;
  	int len=0;
  	if(dt!=null)
  		len=dt.length();
  	%>
  	<form  method="post">
  		<input type="button" value="全部刷新" onclick="allRefresh()" />
		<table class='tableborder'>
			<tr>
				<th style="width:55%">path</th>
				<th style="width:40%">sqlname</th>
				<!-- 
				<th style="width:50px"></th>
				 -->
			</tr>
			<%
				DataRow dr;
				String filepath="";
				for(int row=0;row<len;row++)
				{
					dr=dt.getDataRow(row);
					filepath=dr.getString("path");
					filepath=filepath+File.separator+dr.getString("filename");
					
			%>
				<tr>
					<td><%=dr.getString("path")%></td>
					<td><%=dr.getString("sqlname")%></td>
					<!-- 
					<td align="center"><a onclick="refresh('E:/WorkSpace2/eoms4/WebRoot/WEB-INF/classes/sqlconfig/ultrasm/organization')" href="#">刷新</a></td>
					 -->
				</tr>
			<%
				}
			%>
		</table>
		<div>
			<input name="allfile" type="hidden" value="2" >
			<input name="filename" type="hidden" >
		</div>
	</form>
  </body>
</html>
