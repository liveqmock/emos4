<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms"%>
<%@ taglib uri="/WEB-INF/attachment.tld" prefix="atta"%>
<%@ taglib uri="/WEB-INF/datagrid" prefix="dg"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
<c:when test="${userSession==null}">
<c:set var="skintype" value="blue" />
</c:when>
<c:otherwise>
<c:set var="skintype" value="${userSession.skinType}" />
</c:otherwise>
</c:choose>

<fmt:setBundle basename="i18n.Messages" var="i18Bundle"/>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>

<link href="${ctx}/common/style/${skintype}/css/main.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/common/javascript/util.js"  defer="defer"></script>
<script src="${ctx }/common/javascript/main.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/date/WdatePicker.js"  defer="defer"></script>
<script type="text/javascript">
	var $ctx="${ctx}";
</script>
