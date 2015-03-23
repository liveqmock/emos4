<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_self" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, 30);
		}
		window.onload = function() {
			setCenter(0, 30);
			changeRow_color("tab");
		}
		function lookupEventAction(eventId) {
			document.forms[0].action = "eventMonitor/eventActionMonitor.action?eventId=" + eventId;
			document.forms[0].submit();
		}
	</script>
</head>
<body>
	<dg:datagrid var="eventAction" sqlname="SQL_USLA_EventInstance.query">
		<dg:lefttoolbar>
			<eoms:operate cssclass="page_search_button" id="com_btn_search"
				onmouseover="this.className='page_search_button_hover'"
				onmouseout="this.className='page_search_button'"
				onclick="showsearch()" text="com_btn_search" />
			<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
				onmouseover="this.className='page_refresh_button_hover'"
				onmouseout="this.className='page_refresh_button'"
				onclick="location.reload();" text="com_btn_refresh" />
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
				<th>工单类别</th>
				<th>工单id</th>
				<th>事件类别</th>
				<th>事件类别值</th>
				<th>事件动作</th>
				<th>事件条件</th>
				<th>产生/销毁</th>
				<th>处理时限</th>
				<th>事件状态</th>
				<th>查看</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${eventAction_row}">
				<td>${rowindex}</td>
				<td>${baseschema}</td>
				<td>${eventbaseid}</td>
				<td>${eventtype}</td>
				<td>${eventsubject}</td>
				<td>${eventaction}</td>
				<td>${eventcondition}</td>
				<td>${handletype}</td>
				<td><eoms:date value="${duetime}"/></td>
				<td><eoms:dic dictype="actionStatus" value="${eventstatus}"/></td>
				<td><a href="javascript:;" onclick="lookupEventAction('${handlemark}');return false;" style="color:blue"><u>查看事件动作</u></a></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
