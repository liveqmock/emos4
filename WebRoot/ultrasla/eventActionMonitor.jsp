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
		function lookupMessage(actionid) {
			document.forms[0].action = "eventMonitor/eventMessageMonitor.action?actionId=" + actionid;
			document.forms[0].submit();
		}
		function lookupEventRule(ruleid) {
			var scope = 'form';
			var rownum = 0;
			//openwindow('${ctx}/eventRule/editEventRule.action?id=' + ruleid,'',1000,550);
			showModalDialog('${ctx}/ultrasla/addEventRule.jsp?ruleid='+ruleid+'&scope='+scope+'&op=preview&rownum='+rownum+'&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
		}
	</script>
</head>
<body>
	<dg:datagrid var="eventAction" sqlname="SQL_USLA_EventAction.query">
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
				<th>事件id</th>
				<th>处理时限</th>
				<th>通知时间</th>
				<th>已通知次数</th>
				<th>通知状态</th>
				<th>动作状态</th>
				<th>查看</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${eventAction_row}">
				<td>${rowindex}</td>
				<td>${eventid}</td>
				<td><eoms:date value="${duetime}"/></td>
				<td><eoms:date value="${nextnoticetime}"/></td>
				<td>${noticedtimes}</td>
				<td><eoms:dic dictype="noticeStatus" value="${actionstatus}"/></td>
				<td><eoms:dic dictype="actionStatus" value="${status}"/></td>
				<td>
					<a href="javascript:;" onclick="lookupMessage('${pid}');return false;" style="color:blue"><u>查看接收号</u></a>
					<a href="javascript:;" onclick="lookupEventRule('${ruleid}');" style="color:blue"><u>查看规则</u></a>
				</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
