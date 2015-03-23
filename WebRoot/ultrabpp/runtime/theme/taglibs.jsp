<%@ page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<%@ page import="com.ultrapower.eoms.common.constants.Constants"%>
<%@ page import="java.io.File"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms"%>
<%@ taglib uri="/WEB-INF/ultrabpp.tld" prefix="ubf"%>
<%@ taglib uri="/WEB-INF/attachment.tld" prefix="atta"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<fmt:setBundle basename="i18n.Messages" var="i18Bundle"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>

<%
	Object obj = request.getParameter("baseSchema");
	if(obj != null) {
		String baseSchema = obj.toString().replace(":", "_");
		String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
		String prefix = Constants.WORKSHEET_UPLOAD_PATH;
		String path = null;
		if(prefix != null && !prefix.trim().equals("")) {
			path = prefix + File.separator + baseSchema + File.separator + date;
		} else {
			path = "common" + File.separator + "workflow_attachment" + File.separator + baseSchema + File.separator + date;
		}
		request.setAttribute("path", path);
	}
%>
<%@ include file="/common/extjs/jsp/taglibs_simple.jsp"%>