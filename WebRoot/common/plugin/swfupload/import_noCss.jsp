<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms"%>
<%@ taglib uri="/WEB-INF/attachment.tld" prefix="atta"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
<c:when test="${userSession==null}">
<c:set var="skintype" value="blue" />
</c:when>
<c:otherwise>
<c:set var="skintype" value="${userSession.skinType}" />
</c:otherwise>
</c:choose>
<!-- css -->
<link href="${ctx}/common/plugin/swfupload/css/ultraupload.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/common/plugin/swfupload/css/upload.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/common/plugin/jquery/ui/ui.core.js"></script>
<script type="text/javascript" src="${ctx}/common/plugin/jquery/ui/ui.dialog.js"></script>
<script language="JavaScript" src="${ctx}/common/plugin/swfupload/js/swfupload.js" type="text/javascript"></script>
<script language="JavaScript" src='${ctx}/common/plugin/swfupload/js/handlers.js' type='text/javascript'></script>
<script language="JavaScript" src='${ctx}/common/plugin/swfupload/js/ultraswfupload.js' type='text/javascript'></script>
<script src="${ctx}/common/javascript/util.js"  defer="defer"></script>
<script src="${ctx}/common/javascript/main.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/validate.js"  defer="defer"></script>
<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"  defer="defer"></script>
<script type="text/javascript" language="javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>

