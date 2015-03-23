<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name="sm_t_importResult"/></title>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_FORM_OPEN);
		}
	</script>
  </head>
  <body>
	<form id="importform" method="post" enctype="multipart/form-data">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2"><eoms:lable name="sm_t_importResult"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="text-align: center; margin: 0 auto;">
				<table class="tableborder">
					<c:forEach items="${errorInfoList}" var="errorInfo" varStatus="errorStatus">
					<c:if test="${errorStatus.index==0}">
					<tr>
						<th width="30"><eoms:lable name="com_lb_sequenceNumber"/></th>
						<th><eoms:lable name="sm_lb_problemInfo"/>：</th>
					</tr>
					</c:if>
					<tr>
						<td>${errorStatus.index+1}</td>
						<td align="left">
							${errorInfo}
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<div class="add_bottom">
				<input type="button" value="<eoms:lable name='com_btn_goback'/>"
					class="cancel_button"
					id="cancelButton"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="javascript:history.go(-1);" />
				<input type="button" value="<eoms:lable name='com_btn_close'/>"
					class="cancel_button"
					id="cancelButton"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
	</form>
  </body>
</html>
