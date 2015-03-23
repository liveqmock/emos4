<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
		<script language="javascript">
	var returnMessage = '${returnMessage}';
	if(returnMessage.length>0)
		alert(returnMessage);
	window.onresize = function() 
	{
		setCenter(0,200);
	}
	
	window.onload = function() 
	{
		setCenter(0,200);
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
		<fieldset class="fieldset_style">
			<legend>
				请选择需要导入的文件，并且点击加载按钮进行加载
			</legend>
			<div class="blank_tr"></div>
				<div id="div1">
					<form action="importWfTypeList.action" id="importForm" name="importForm" enctype="multipart/form-data"  method="post" target="_self">
						<input type="file" id="importFile" name="importFile"/>
						<input type="hidden" id="reLoad" name="reLoad" value="1"/>
						<input type="submit" id="load" name="load" value="加载"/>
					</form>
				</div>
		</fieldset>
	</body>
</html>
