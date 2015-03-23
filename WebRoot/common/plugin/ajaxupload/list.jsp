<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<table border="0" width="95%" cellpadding="0" cellspacing="1" bgcolor="#C8C8C8" style="margin-top:5px;margin-bottom:5px">
	<tr bgcolor="#E7E7E7" style="color:#808080;" height=20>
		<td align=center>
		<s:text name="common.attachment"></s:text>
		</td>
		<td align=center>
		<s:text name="common.creator1"></s:text>
		</td>
		<td align=center>
		<s:text name="common.time"></s:text>
		</td>
		<s:if test="viewflag != 'true'">
			<td align=center>
			<s:text name="common.operation"></s:text>
			</td>
		</s:if>
	</tr>
	<s:iterator value="%{accessoriesList}">
		<tr bgcolor="#ffffff" height=20>
			<td>
				<a href="${ctx}/AccessoriesDownLoad.action?fileId=<s:property value="id"/>"><s:property
						value="fileName" /> </a>
			</td>
			<td>
				<s:property value="creator.userFullName" />
			</td>
			<td>
				<s:property value="newTime" />
			</td>
			<s:if test="viewflag != 'true'">
			<td>
				<a  onclick="upload_frame_${tagId}.removelist('<s:property value="ids"/>','<s:property value="id"/>','<s:property value="%{tagId}" />');upload_frame_${tagId}.getNums('-1');"
					href="#">删除</a>
			</td>
			</s:if>
		</tr>
	</s:iterator>
</table>