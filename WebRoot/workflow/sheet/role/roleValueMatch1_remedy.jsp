<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<script src="${ctx}/common/javascript/util.js"></script>
		<script src="${ctx}/workflow/sheet/role/js/roleUser.js"></script>

		<title><eoms:lable name="wf_sheet_dimensions_title"/></title>

	<script type="text/javascript">
		window.onresize = function() 
		{
		  setCenter(0,38);
		}
	</script>
	
	</head>
	<body>
		
		<form id="sheetform" action="${ctx}/childrole/toChildRoleValueMatch.action" target="_self">
			<input type="hidden" id="dimensionValues" name="dimensionValues" />
		</form>
		
		<input type="text" id='C650000001' value='上海' />
		<input type="text" id='C650000004' value='潮州' />
		<input type="text" id='C650000006' value='业务平台' />
		
		
		<script type="text/javascript">
		window.onload = function(){
			init();
		}
		
		function init(){
			var dimensionValues = '';
			var values = '${dimensionValues}';
			alert(values);
			if(values != ''){
				var value =  values.split('#');
				for(var i=0;i < value.length;i ++ ){
					var key = value[i].split('=');
					dimensionValues += '#' + key[0] + '=' + parent.window.F(key[1].substring(1)).G().toString(); 
				}
			}
			if(dimensionValues != ''){
				dimensionValues = dimensionValues.substring(1);
			}
			document.getElementById('dimensionValues').value = dimensionValues;
			document.getElementById('sheetform').submit();
		}
		
		</script>
	</body>
</html>
