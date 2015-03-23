<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
</head>
<body>
	<form action="${ctx}/formImport/formImportAction/importBase.action" method="post" enctype="multipart/form-data" target="_self">
		工单导入：
		<input id="excel" name="excel" type="file" />
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