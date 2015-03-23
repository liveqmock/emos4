<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.util.CryptUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'tranpwd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function setvalue(type)
	{
		document.getElementById("type").value=type;
		document.forms.sform.submit();
	}
	</script>
  </head>
  
  <body>
  	<%
  		String type=request.getParameter("type");
  		CryptUtils crypt = CryptUtils.getInstance();
  		String srpwd=request.getParameter("srpwd");
  		String trpwd=request.getParameter("trpwd");
  		
  		
  		
  		if("1".equals(type) && srpwd!=null)
  		{
  				trpwd=crypt.decode(srpwd);
  		}
  		if("2".equals(type) && srpwd!=null)
  		{
  				trpwd=crypt.encode(srpwd);
  		}
  		
  	 %>
  	 <form id="sform">	
      <table align="center">
      
      	<tr>
      		<td>源数据</td>
      		<td>
      		<input id="srpwd" name="srpwd" value="<%=srpwd %>" width="150"/>
      		</td>
      		<td>目标数据</td>
      		<td>
      		<input id="trpwd" name="trpwd" value="<%=trpwd %>" width="150"/>
      		</td>
      	</tr>
      	<tr>
      		<td colspan="4" align="center"><input type="submit" value="解密" onclick="setvalue(1)" /> &nbsp;&nbsp;
      		 <input type="submit" value="加密" onclick="setvalue(2)" /></td>
      	</tr>
      	<input id="type" name="type" type="hidden" value="<%=type %>" />
      </table>
      </form>
  </body>
</html>
