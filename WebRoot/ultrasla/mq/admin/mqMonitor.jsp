<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>UltraSLA MQ监控信息</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0, 26);
			}
			window.onload = function() 
			{
				setCenter(0, 26);
			}
		</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">当前位置：SLA服务管理>>UltraSLA MQ监控信息</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<br/>
				<table bgcolor="gray" cellspacing="1" cellpadding="2" width="99%" align="center">
					<caption>MQ监控信息</caption>
					<tr bgcolor="white">
						<td width="10%">MQ代理ID</td>
						<td>${mqRuntimeInfo.brokerId }</td>
					</tr>
					<tr bgcolor="white">
						<td>MQ代理名称</td>
						<td>${mqRuntimeInfo.brokerName }</td>
					</tr>
					<tr bgcolor="white">
						<td>待处理消息数目</td>
						<td>${mqRuntimeInfo.remainMsgCount }</td>
					</tr>
				</table>
			</div>	
		</div>
	</body>
</html>