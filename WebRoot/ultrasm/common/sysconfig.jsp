<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/common/core/header_list.jsp"%>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FRAME_RIGHT);
		}
		window.onload = function() {
			setCenter(0, LAYOUT_FRAME_RIGHT);changeRow_color("tab");
			setCheckCodeStatus();//读取当前系统初始状态
			updateLoginCode();//读取当前系统初始登录标识
		}
		//加载在线帮助配置文件
		function refreshHelpNode() {
			$.get('${ctx}/systemConfig/refreshCacheXml.action', {}, function(result){
				alert('重新加载完毕，详细信息请查看后台日志！');
			});
		}
		//设置登录手机验证启停
		function setCheckCodeStatus(type) {
			$.get('${ctx}/systemConfig/setCheckCodeStatus.action', {type:type}, function(result){
				if(result == 'true')
					$('#checkcode').val('停用');
				else if(result == 'false')
					$('#checkcode').val('启用');
				else
				{
					$('#checkcode').val('启用');
					document.getElementById('checkcode').disabled = 'disabled';
				}
				if(type == 'click')
					alert('系统登录手机验证启停状态设置完毕！');
			});
		}
		//修改后门登录标识
		function updateLoginCode() {
			var $logincode = $('#logincode').val();
			$.get('${ctx}/systemConfig/updateLoginCode.action', {logincode:$logincode}, function(result){
				if($logincode == '')
					$('#logincode').val(result);
				else
					alert('登录标识修改完毕！');
			});
		}
		//更新菜单目录树当前位置数据
		function updateNodepath() {
			$.get('${ctx}/systemConfig/updateNodepath.action', {}, function(result){
				alert('更新完毕，详细信息请查看后台日志！');
			});
		}
		//整体更新用户的拼音名称
		function updateUserPyName() {
			$.get('${ctx}/systemConfig/updateUserPyName.action', {}, function(result){
				alert('整体更新用户的拼音名称完毕！');
			});
		}
	</script>
</head>
<body>
	<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">当前位置：自管理>>系统配置列表</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class='scroll_div' id='center'>
			<table id='tab' class='tableborder'>
				<!-- 标题描述区_Start -->
				<tr>
					<th width="30px">序号</th>
					<th>描述区</th>
					<th width="150px">操作区</th>
					<th width="200px">输入区</th>
				</tr>
				<!-- 标题描述区_End -->
				
				<!-- 加载在线帮助配置文件_Start -->
				<tr>
					<td>1</td>
					<td>点击刷新重新加载在线帮助配置文件</td>
					<td><input type="button" value="刷新" class="button" onclick="refreshHelpNode();" disabled/></td>
					<td></td>
				</tr>
				<!-- 加载在线帮助配置文件_End -->
				
				<!-- 设置登录手机验证启停_Start -->
				<tr>
					<td>2</td>
					<td>设置系统登录手机验证启停</td>
					<td><input type="button" id="checkcode" name="checkcode" class="button" onclick="setCheckCodeStatus('click');"/></td>
					<td></td>
				</tr>
				<!-- 设置登录手机验证启停_End -->
				
				<!-- 修改简登录标识_Start -->
				<tr>
					<td>3</td>
					<td>输入修改简登录标识</td>
					<td><input type="button" value="刷新" class="button" onclick="updateLoginCode();" disabled/></td>
					<td><input type="text" id="logincode" name="logincode" class="textInput" value=""/></td>
				</tr>
				<!-- 修改简登录标识_End -->

				<!-- 更新菜单目录树当前位置数据_Start -->
				<tr>
					<td>4</td>
					<td>点击刷新更新菜单目录树当前位置数据</td>
					<td><input type="button" value="刷新" class="button" onclick="updateNodepath();"/></td>
					<td></td>
				</tr>
				<!-- 更新菜单目录树当前位置数据_End -->
				
				<!-- 整体更新用户的拼音名称_Start -->
				<tr>
					<td>5</td>
					<td>点击刷新整体更新用户的拼音名称</td>
					<td><input type="button" value="刷新" class="button" onclick="updateUserPyName();"/></td>
					<td></td>
				</tr>
				<!-- 整体更新用户的拼音名称_End -->
			</table>
		</div>
	</div>
</body>
</html>
