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
		<script type="text/javascript">
		window.onload = function(){
			init();
		}
		
		function init(){
			var roleValue = '';
			var roleName = '';
			<s:iterator value="%{childRoleList}" status="ChildRole">
				var id = "<s:property value='childRoleId' />";
				var name = "<s:property value='childRoleName' />";
				roleValue += ',' + id;
				roleName += ',' + name;
			</s:iterator>
				
			if(roleValue != ''){
				roleValue = roleValue.substring(1);
			}
			if(roleName != ''){
				roleName = roleName.substring(1);
			}
			
			 var values =  'R';
			
			var	action_sign = parent.window.F(650042003).G().toString();	//动作
			var	next_step = parent.window.F(650042004).G().toString();		//下一环节
			
			values += '@:' + roleValue;
			values += '@:' + action_sign;
			values += '@:' + '2'; // 抢单
			values += '@:' + '';
			values += '@:' + '';
			values += '@:' + '';
			values += '@:' + next_step + '@:';
			
			/**
				700060733：角色
				700060733：处理人信息字符串
				700040068：按钮
			**/
			
			parent.window.F(700060733).S(new parent.window.CharType(values));
			parent.window.F(700060733).S(new parent.window.CharType(roleName));
			parent.window.F(700040068).mObj.fireEvent('onclick')
			//var method = parent.window.F(700040068).onclick;
			//eval(method);
		}
		</script>
	</body>
</html>
