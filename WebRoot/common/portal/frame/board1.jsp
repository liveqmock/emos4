<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/portal.css" />
<script src="../../javascript/main.js"></script>
<title>board</title>
</head>
<body>
<div class="content">
	<div class="title_right">
		<div class="title_left">
			<span class="title_bg"><span><img src="../../style/blue/images/title/tag7.png"  align="absmiddle" />最新案例</span></span>
			<span class="title_xieline"></span>
			<span class="title_more" onclick="window.open('${ctx}/ultrarepository/newRepositoryList.action')"></span>
		</div>
	</div>
	<div class="scroll_div" id="center">
		<div id="tab21" style="height:200px;overflow:hidden;">
			<iframe src="${ctx}/ultrarepository/newRepository.action" height="100%" width="100%" scrolling="no" frameborder="0" style="margin:0px;"></iframe>
		</div>
	</div>
</div>
   </body>
   </html>