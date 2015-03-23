<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/common/core/taglibs.jsp"%>
    <script language="javascript">
		window.onload = function()
		{
			setCenter(0, LAYOUT_FRAME_RIGHT);
		}
		window.onresize = function()
		{
			setCenter(0, LAYOUT_FRAME_RIGHT);
		}
	</script>
  </head>
  <body>
  	<div class="content">
  		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">公告查看</span></span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<iframe src="${ctx}/notice/noticeViewLogManage.jsp?datadictype=1" frameborder="0" id="center" name="center"></iframe>
  	</div>
  </body>
</html>
