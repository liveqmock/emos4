<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>DIV拖动试验</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <table width="600" height="300" border="0" bgcolor="black" cellpadding="0" cellspacing="1">
    	<tr>
    		<td>
    			<div style="width:100%;height:100%;background:orange;">div1</div>
    		</td>
    		<td>
    			<div style="width:100%;height:100%;background:yellow;">div2</div>
    		</td>
    		<td>
    			<div style="width:100%;height:100%;background:white;">div3</div>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<div style="width:100%;height:100%;background:cyan;">div4</div>
    		</td>
    		<td>
    			<div style="width:100%;height:100%;background:blue;">div5</div>
    		</td>
    		<td>
    			<div style="width:100%;height:100%;background:pink;">div6</div>
    		</td>
    	</tr>
    </table>
    <hr>
    session:
    <%
    	out.print(session);
     %>
     <hr>
    	本网站cookie:<%=request.getHeader("cookie")%><br>
    	链接:<a href="http://192.168.14.111:8080/eoms/ultrasm/databasetable/test.jsp" target="_blank">eoms中的test页</a>
    	弹出:<a href="javascript:;" onclick="window.open('http://192.168.14.111:8080/eoms/ultrasm/databasetable/test.jsp');">eoms中的test页</a>
  </body>
</html>
