<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
	String idStr = StringUtils.checkNullString(request.getParameter("idStr"));
	String idType = StringUtils.checkNullString(request.getParameter("idType"));
%>
<head>
	<%@ include file="/common/core/taglibs_new_simple.jsp"%>
	<script src="${ctx }/common/plugin/jquery/jquery.js"></script>
	<script language="javascript">
		window.onload = function()
		{
			var idStr = '<%=idStr%>';
			var idType = '<%=idType%>';
			$.get("${ctx}/userTemplate/getMergerData.action", {idStr:idStr,idType:idType}, function (actorStr)
			{
				window.returnValue = actorStr;
				window.close();
			});
		}
	</script>
</head>
<body>
</body>
</html>
