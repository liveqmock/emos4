<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>选择处理对象</title>
		<script type="text/javascript">
			window.onresize = function() {
				setCenter(0, LAYOUT_FORM_OPEN);
			}
			window.onload = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);
			}
			function submit(){
				var str = document.getElementById("tree").contentWindow.getSelectData('element');
				window.dialogArguments.document.getElementById("tempvalue").value = str;
				window.close();
			}
		</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">当前位置：选择处理对象</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<iframe id="tree" src="${ctx}/common/tools/orgtree/organizaTree.jsp?useMode=frame"
					scrolling="no" frameborder="0" width="580px" height="430px"></iframe>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="确定" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();"/>
			<input type="button" value="取消" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</body>
</html>
