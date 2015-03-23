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
		function typedata(dictype)
		{
			document.getElementById('center').src = '${ctx}/ultrasm/dictionary/dicManage.jsp?datadictype='+dictype;
		}
	</script>
  </head>
  <body>
  	<div class="content">
  		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">${nodePath}</span></span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="page_div_bg">
			<div class="page_div">
			<c:forEach items="${dicitsmList}" var="di">
				<li class='page_sameadd_button' id='page_sameadd_button' onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="typedata('${di.divalue}');">${di.diname}</li>
			</c:forEach>
			<li class='page_sameadd_button' id='page_sameadd_button' onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="typedata('0');">所有类型</li>
			</div>
		</div>
		<iframe src="${ctx}/ultrasm/dictionary/dicManage.jsp?datadictype=1" frameborder="0" id="center" name="center"></iframe>
  	</div>
  </body>
</html>
