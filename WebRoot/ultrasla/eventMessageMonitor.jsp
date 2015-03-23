<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, 30);
		}
		window.onload = function() {
			setCenter(0, 30);
			changeRow_color("tab");
		}
	</script>
</head>
<body>
	<dg:datagrid var="eventAction" sqlname="SQL_USLA_EventMessage.query">
		<dg:lefttoolbar>
			<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
				onmouseover="this.className='page_refresh_button_hover'"
				onmouseout="this.className='page_refresh_button'"
				onclick="location.reload();" text="com_btn_refresh" />
			<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
				onmouseover="this.className='page_refresh_button_hover'"
				onmouseout="this.className='page_refresh_button'"
				onclick="javascript:history.go(-1);" text="com_btn_goback" />
		</dg:lefttoolbar>
		<dg:condition>
			<table class="serachdivTable">
				<tr>
					<td colspan="6" align="center">
						<input type="submit" name="button" id="submitButton"
							value="<eoms:lable name="com_btn_lookUp" />"
							class="searchButton"
							onmouseover="this.className='searchButton_hover'"
							onmouseout="this.className='searchButton'" />
						<input type="reset" name="button2" id="button2"
							value="<eoms:lable name="com_btn_reset" />" class="ResetButton"
							onmouseover="this.className='ResetButton_hover'"
							onmouseout="this.className='ResetButton'" />
					</td>
				</tr>
			</table>
		</dg:condition>
		<dg:gridtitle>
			<tr>
				<th width="30">序号</th>
				<th>手机号码</th>
				<th>发送时间</th>
				<th>扫描状态</th>
				<th>发送状态</th>
				<th>发送内容</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${eventAction_row}">
				<td>${rowindex}</td>
				<td>${goal}</td>
				<td><eoms:date value="${presendtime}"/></td>
				<td>${scanstatus}</td>
				<td>${sendflag}</td>
				<td>
				<c:choose>
					<c:when test="${fn:length(content) > 22}">
						${fn:substring(content, 0, 20)}...
					</c:when>
					<c:otherwise>
						${content}
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
