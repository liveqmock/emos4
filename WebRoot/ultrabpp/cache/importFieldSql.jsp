<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,100);
		
	}
	
	window.onload = function() 
	{
		setCenter(0,100);

	}
	
	
</script>
	</head>
	<body class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">${nodePath}</span> </span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class='scroll_div' id='center' >
			<table id='tab' class='tableborder'>
				<tr>
					<th>
						流程标识
					</th>
					<th>sql执行语句</th>
				</tr>
				<c:forEach var="itemByBaseschema" items="${sqlMapByBaseschema}">
					<tr>
						<td>
							${itemByBaseschema.key}
						</td>
						<td>
							<c:forEach var="itemSql" items="${itemByBaseschema.value}">
							${itemSql};<br/>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</table> 
		</div>
		<fieldset class="fieldset_style">
			<legend>
				请手动更新数据库信息，更新操作成功后，点击导入按钮，开始表单配置信息导入操作！
			</legend>
			<div class="blank_tr"></div>
				<div id="div1">
					<form action="importConfigOther.action" id="importBaseSchameForm" name="importBaseSchameForm" method="post" target="_self">
						<input type="submit"  value="导入"/>
					    <input type="button" value="返回" onclick="window.history.back();"/>
					</form>
				</div>
		</fieldset>
	</body>
</html>
