<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<fmt:setBundle basename="i18n.Messages" var="i18Bundle" />

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link type="text/css" href="${ctx }/common/extjs/css/ext-all.css"" rel="stylesheet" />
<link type="text/css" href="${ctx }/common/extjs/css/filemanager.css" rel="stylesheet"  />
<script type="text/javascript" src="${ctx}/common/extjs/js/ext-base.js" ></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/ext-all.js" ></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/ext-lang-zh_CN.js" ></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/json2.js" ></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/ext-lang-zh_CN.js" ></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/MsgPasswordBox.js" ></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/json2.js" ></script>
<script type="text/javascript" src="${ctx}/common/js/main.js" ></script>
<script type="text/javascript">
    var $ctx="${ctx}"
</script>
