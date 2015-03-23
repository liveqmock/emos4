<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>配置项关联</title>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/core/basePath.jsp"%>
	<link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/icon.css">
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script>
	function showDetail(){
		window.open("/change/ciRelevance/detailCI.jsp",'detail','height=800px,width=600px,toolbar=no,status=no,location=no,directories=no');
	}
	function releateCI(){
		window.open($basePath+"ciRelevance/searchCI.action?ciRelevance.baseschema=${ciRelevance.baseschema}&ciRelevance.baseid=${ciRelevance.baseid}");
	}
	</script>
</head>
<body style="">
<form action="" method="post">
	<input class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"  type="button" id="Confirm" value="关联配置项" onclick="releateCI()"/>
	</form>
	<table id ="tab" class="tableborder" style="table-layout: fixed;">
		<tr class="t2">
			<th width="30">序号</th>
			<th width="20%">配置项类别</th>
			<th width="20%">配置项名称</th>
			<th width="20%">关联时间</th>
			<th width="20%">关联人</th>
			<th width="20%">操作</th>
		</tr>
		<c:forEach items="${ciRelevances }" var="c" varStatus="s">
			<tr class="t1">
				<td>${s.index+1 }</td>
				<td>${t.ciClass.displayName }</td>
				<td>${t.displayLabel }</td>
				<td><eoms:date value="${t.createtime }"></eoms:date></td>
				<td>${t.creator.fullname }</td>
				<td><input type="button" value="查看" onclick="showDetail()"/></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
