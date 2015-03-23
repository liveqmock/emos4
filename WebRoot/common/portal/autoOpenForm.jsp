<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.constants.PropertiesUtils"%>
<%@ page import="com.ultrapower.eoms.common.portal.model.UserSession"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<%@ page import="com.ultrapower.eoms.common.core.util.CryptUtils"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	// 集成pasm的自动打开工单功能，访问url如下：
	// http://eoms4_ip:eoms4_port/eoms4/portal/login_v2.action?loginName=登录名&pwd=密码&url=/common/portal/autoOpenForm.jsp&param=baseId=工单号;baseSchema=工单类别;loginName=登录名;pwd=用户密码
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	Object obj = session.getAttribute("userSession");
	UserSession userSession = null;
	if(obj != null) {
		userSession = (UserSession) obj;
	}
	String loginName = userSession == null ? "" : StringUtils.checkNullString(userSession.getLoginName());
	String pwd = userSession == null ? "" : StringUtils.checkNullString(userSession.getPwd());
	CryptUtils crypt = CryptUtils.getInstance();
	if(userSession != null) {
		pwd = crypt.decode(pwd);
	}
	String baseId = StringUtils.checkNullString(request.getParameter("baseId"));
	String baseSchema = StringUtils.checkNullString(request.getParameter("baseSchema"));
	String pasm_ip = StringUtils.checkNullString(PropertiesUtils.getProperty("iam.server.ip"));
	String pasm_port = StringUtils.checkNullString(PropertiesUtils.getProperty("iam.http.port"));
%>
<html>
<head>
	<script type="text/javascript">
		window.onload = function () {
			document.forms[0].submit();
		}
	</script>
</head>
<body>
	<form action="http://<%=pasm_ip%>:<%=pasm_port%>/ucas/login" id="form">
		<input type="hidden" name="_username" id="_username" value="<%=loginName%>"/>
		<input type="hidden" name="_password" id="_password" value="<%=pwd%>"/>
		<input type="hidden" name="portal" id="portal" value="oa"/>
		<input type="hidden" name="service" id="service" value="<%=basePath%>/sheet/openWaittingSheet.action?baseId=<%=baseId%>&baseSchema=<%=baseSchema%>"/>
	</form>
</body>
</html>
