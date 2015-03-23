<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<title>流水号重置</title>
</head>
<body>
<div class="title" align="center" id="titlePlan" class="titlePlan">
<form id="impForm" name="impForm" action="${ctx}/formImport/formImportAction/buildSerialNo.action" method="post">
<table width="350" align = "center" cellpadding="0" cellspacing="0" border="0">
    <tr>
	    <td align="left" width="80">工单类别：</td>
	    <td align="left"><input id="schema" type="text" name="schema" style="width:90%;"></td>    
    </tr>
    <tr>
	    <td align="left" width="80">工单类别ID：</td>
	    <td align="left"><input id="categoryID" type="text" name="categoryID" style="width:90%;"></td>    
    </tr>
    <tr>
	    <td align="left" width="80">开始时间：</td>
	    <td align="left"><input id="startTime" type="text" name="startTime" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});"></td>    
    </tr>
    <tr>
	    <td align="left" width="80">结束时间：</td>
	    <td align="left"><input id="endTime" type="text" name="endTime" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'});"></td>    
    </tr>
    <tr>
    	<td colspan="2" align="right"><input class="button" type="submit" value="开始" /></td>    
    </tr>
</table>
</form>
</body>
<script type="text/javascript">
 <%
 String result = request.getParameter("result");
 if(result != null && result.equals("1"))
 {
 %>
 alert('删除完成！');
 <%}%>
</script>
</html>