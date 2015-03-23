<%@ page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<%@ page import="com.ultrapower.eoms.common.constants.Constants"%>
<%@ page import="java.io.File"%>

<link href="${ctx}/common/style/${skintype}/css/newgongdan.css"  rel="stylesheet" type="text/css" />
<link href="${ctx}/common/style/${skintype}/lccommon/css/subModal.css"  rel="stylesheet" type="text/css" />
<link href="${ctx}/common/plugin/dropmenu/style/css/dropdown.css"  rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/common/plugin/dropmenu/js/dropmenu.js"></script>

<fmt:setBundle basename="i18n.${baseSchema}" var="i18Bundle"/>

<%
	Object obj = request.getParameter("baseSchema");
	if(obj != null) {
		String baseSchema = obj.toString().replace(":", "_");
		String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
		String uuid = TimeUtils.getCurrentTime() + "";
		String prefix = Constants.WORKSHEET_UPLOAD_PATH;
		String path = null;
		if(prefix != null && !prefix.trim().equals("")) {
			path = prefix + File.separator + baseSchema + File.separator + date;
		} else {
			path = "common" + File.separator + "workflow_attachment" + File.separator + baseSchema + File.separator + date;
		}
		request.setAttribute("uuid", uuid);
		request.setAttribute("path", path);
	}
%>