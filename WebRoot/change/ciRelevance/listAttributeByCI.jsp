<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<title>配置项属性</title>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ include file="/common/core/basePath.jsp"%>
	<link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/icon.css">
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
</head>
<body>
	<table id ="tab" class="easyui-datagrid">
		<thead>
		<tr>
			<th data-options="field:'key'">配置项类型</th>
			<th data-options="field:'value'">配置项名称</th>
		</tr>
		</thead>
		<c:forEach items="${ciAttributes }" var="c">
			<tr class="t1">
				<td>${c.key }</td>
				<td>${c.value }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
