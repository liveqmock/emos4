<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="ciInfosForm">
<table>
	<thead>
		<tr>
			<th></th>
			<th>配置项类型</th>
			<th>配置项名称</th>
			<th></th>
		</tr>
	</thead>
	<c:forEach items="${ciRelevances }" var="c">
		<tr class="t1">
			<td style="width: 30px;"><input type="checkbox" name="ciInfo" value='{"ciclass":"${c.ciClass.name }","ciId":"${c.ciId }","displayLabel":"${c.displayLabel}","name":"${c.name }"}'/></td>
			<td style="width: 30%;">${c.ciClass.displayName }</td>
			<td style="width: 30%;">${c.displayLabel}</td>
			<td><input type="button" value="查看" onclick="showDetail('${c.ciClass.name}','${c.ciId }')"/></td>
		</tr>
	</c:forEach>
</table>
</form>
