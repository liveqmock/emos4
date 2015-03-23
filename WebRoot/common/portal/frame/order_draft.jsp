<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/portal.css" />
<script src="${ctx}/common/javascript/main.js"></script>
<script type="text/javascript">
function changeView(id)
{
	if(document.getElementById(id).style.display == 'none')
	{
		document.getElementById(id).style.display = '';
	}
	else
	{
		document.getElementById(id).style.display = 'none';
	}
}
</script>
<style type="text/css">
.contract_menu_top{
	border-top:1px #4d7ed3 dotted;
	border-bottom:0px;
	height:22px;
	line-height:26px;
	padding-left:20px;
	margin-bottom:0px;
}
.menu1{
	border-top:0px;
	border-bottom:0px;
	height:22px;
	line-height:26px;
	padding-left:20px;
	margin-bottom:0px;
}
.contract_menu li{
	list-style:none;
	height:22px;
	padding-left:31px;
}
</style>
<title>order_draft</title>
</head>
<body style="background-color:white; overflow-y:auto; height:322px;">
${shortcutHtml}
</body>
</html>