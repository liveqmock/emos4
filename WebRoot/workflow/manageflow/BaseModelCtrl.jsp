<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.control.BaseOwnFieldInfoManage"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel"%>

<html>
<head>
</head>

<%
	String schema = StringUtils.checkNullString(request.getParameter("schema"));
	String modelID = StringUtils.checkNullString(request.getParameter("modelID"));

	BaseOwnFieldInfoManage ctrl = new BaseOwnFieldInfoManage();
	ctrl.delModelById(modelID);
	response.sendRedirect("BaseModelList.jsp?baseschema=" + schema);
%>

<body>
</body>
</html>
