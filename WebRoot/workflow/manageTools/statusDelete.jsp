<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
</head>
<body>
	<form action="${ctx}/formDelete/formDeleteAction/statusDelete.action" method="post" target="_self">
		<h1 style="margin-top:10px;">工单删除：</h1>
		<h3 style="color:red;">请注意：删除之前要进行反复确认！</h3>
		工单Schema：<input id="baseSchema" name="baseSchema" type="text" value="" />
		工单状态：<input id="baseStatus" name="baseStatus" type="text" value="" />
		<input id="submit" name="submit" type="submit" value="Submit!" />
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