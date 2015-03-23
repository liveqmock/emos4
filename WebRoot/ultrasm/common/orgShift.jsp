<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name="sm_t_dataShift"/></title>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
	</script>
  </head>
  <body>
	<form id="importform" method="post" enctype="multipart/form-data">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2"><eoms:lable name="sm_t_dataShift"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="text-align: center; margin: 0 auto;">
				<table class="tableborder">
					<c:forEach items="${shiftInfoList}" var="shiftInfo" varStatus="status">
					<c:if test="${status.index==0}">
					<tr>
						<th width="30"><eoms:lable name="com_lb_sequenceNumber"/></th>
						<th><eoms:lable name="sm_lb_dataShiftInfo"/>：</th>
					</tr>
					</c:if>
					<tr>
						<td>${status.index+1}</td>
						<td align="left">
							${shiftInfo}
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<div class="add_bottom">
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
