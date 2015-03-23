<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>Error Page</title>
		<link href="${ctx}/css/css.css" rel="stylesheet" type="text/css" />
	</head>
	<%
		Throwable ex = null;
		if (exception != null)
			ex = exception;
		if (request.getAttribute("javax.servlet.error.exception") != null)
			ex = (Exception) request
					.getAttribute("javax.servlet.error.exception");
	%>
	<body>asdfasdfasdfasfasdf
	<div class="workarea">
		<br><div align="center">
			<s:fielderror cssStyle="color:red" ></s:fielderror>
			<%
				if (ex != null)
					out.println(ex.getMessage());
			%>
		</div>
		<div class="submit">
			<input type="button" class="btn2_mouseout"
				onclick="window.history.back()"
				value="<s:text name="button.back" />" />
		</div>
	</div>
	</body>
</html>