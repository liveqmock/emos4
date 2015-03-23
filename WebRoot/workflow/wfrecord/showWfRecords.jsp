<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:scroll;overflow-x:hidden;">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script>
	$(function(){
	//return;
		var viewbox = window.parent.$("#${recordViewFieldName}_ubfbox");
		var height = 150;
		var iframeHeight = document.body.scrollHeight;
		if(iframeHeight < height){
			height = iframeHeight;
		}
		viewbox.height(height)//调整父窗口处理记录的高度
		viewbox.children("table").height(height)
		window.parent.$("#${recordViewFieldName}").height(height)
	});
	</script>
</head>
<body style="">
	<table class="tableborder" style="table-layout: fixed;">
		<tr class="t2">
			<th width="30">序号</th>
			<th width="40%">处理人</th>
			<th width="10%">处理动作</th>
			<th width="20%">处理时间</th>
			<th width="40%">处理意见</th>
		</tr>
		<c:forEach items="${wfRecords }" var="w" varStatus="s">
			<tr class="t1">
				<td>${s.index+1 }</td>
				<td>${w.currentUser }[${w.currentUserInfo.depname},${w.currentUserInfo.phone}]</td>
				<td>${w.dealAction }</td>
				<td><eoms:date value="${w.dealTime }"/></td>
				<td style="white-space: nowrap; overflow: hidden;">${w.dealDesc }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
