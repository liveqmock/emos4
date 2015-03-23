<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'MyJsp.jsp' starting page</title>
    <%@ include file="/common/core/taglibs.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript">
	</script>
  </head>
  <body>
    <div class="content">
			<div align="center">
				<legend>
				</legend>
				<div class="blank_tr"></div>
				<table class="add_user">
					<tr>
						<td></td>
						<td>注意事项：</td>
					</tr>
					<tr>
						<td></td>
						<td>内置字段：记录标识(TASKID);通知人(NOTICEUSERID);通知组(NOTICEGROUPID);通知角色组(NOTICEROLEGROUPID);所属公司(BELONGCOMPANTID);所属部门(BELONGDEPID)</td>
					</tr>
					<tr>
						<td></td>
						<td>短信内容：例如: 你好,你用新工单需要去处理,工单号：#BASEID#,请尽快处理! (其中的变量全部采用大写的形式)</td>
					</tr>
					<tr>
						<td></td>
						<td>邮件主题：例如: 你好,你用新工单需要去处理,工单号：#BASEID#,请尽快处理! (其中的变量全部采用大写的形式)</td>
					</tr>
					<tr>
						<td></td>
						<td>邮件内容：例如: 你好,你用新工单需要去处理,工单号：#BASEID#,请尽快处理! (其中的变量全部采用大写的形式)</td>
					</tr>
						<td></td>	
						<td>规则配置条件与数据源替换标识(slaRuleCondiation)</td>				
					<tr>
				</table>
			</div>
    </div>
  </body>
</html>
