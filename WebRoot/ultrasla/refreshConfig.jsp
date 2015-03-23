<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript"
			src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() {
			setCenter(0, LAYOUT_LIST_RIGHT);
		}
		function refreshTrigConfig() {
			$.get("${ctx}/slaDefine/refreshTrigConfig.action?rnd="+(new Date()).getTime(),{},function(res){
				if("1"==res) {
					$("#refreshTag").text("刷新成功!");
				}else {
					$("#refreshTag").text("刷新失败!");
				}
			});
		}
	</script>
	</head>
	<body>
		<div class="content">

			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">当前位置：SLA服务管理>>触发配置刷新</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="page_div_bg">
				<div class="page_div">
					<li class="page_refresh_button"
						onmouseover="this.className='page_refresh_button_hover'"
						onmouseout="this.className='page_refresh_button'"
						onclick="refreshTrigConfig();" />
					刷新
					</li>
					<li class="page_help_button"
						onmouseover="this.className='page_help_button_hover'"
						onmouseout="this.className='page_help_button'" />
					帮助
					</li>
				</div>
			</div>

			<div class="scroll_div" id="center">
				<h4 id="refreshTag"></h4>
			</div>
		</div>
	</body>
</html>
