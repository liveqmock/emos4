<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ultrapower.eoms.workflow.util.PreviewUtils"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.control.BaseOwnFieldInfoManage"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel"%>

<html>
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../common/style/blue/css/baseinfoview.css">
<title><eoms:lable name="wf_sheet_template_manage_title"/></title>
</head>

<script type="text/javascript">
</script>

<body style="overflow:scroll;">
<TABLE cellSpacing=1 cellPadding=0 width="100%" align=center border=1 class="tableline">
<%
	String modelID = request.getParameter("modelID");
	List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList= (new BaseOwnFieldInfoManage()).getModelList(modelID);
	out.println(PreviewUtils.printMainHtml(m_BaseOwnFieldInfoList, null));
%>
</table>
</body>
</html>
