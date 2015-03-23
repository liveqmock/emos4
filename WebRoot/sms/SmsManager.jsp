<%@ page language="java" import="java.text.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>  
		<script language="javascript">
			window.onresize = function() {
				setCenter(0, LAYOUT_LIST_RIGHT);
			}
			window.onload = function() {
				setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
			}
			
			function start()//启动
			{
				window.location.href('${ctx}/sms/startSm.action');
			}
			
			function stop()//停止
			{
				window.location.href('${ctx}/sms/stopSm.action');
			}
		</script>
	</head>
	<body>
       	<div class="title_right">
	      	<div class="title_left">
		        <span class="title_bg">
		          <span class="title_icon2">${nodePath}</span>
		        </span>
	        	<span class="title_xieline"></span>
	      	</div>
	 	 </div>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<li class="page_enabled_button" onmouseover="this.className='page_enabled_button_hover'" onmouseout="this.className='page_enabled_button'"  onclick="start();" /><eoms:lable name='sm_btn_start' /></li>
					<li class="page_stop_button" onmouseover="this.className='page_stop_button_hover'" onmouseout="this.className='page_stop_button'" onclick="stop();" /><eoms:lable name='sm_btm_stop' /></li>
				</div>
			</div>
		 <div class="scroll_div" id="center">                           
			  <table class="tableborder" id="tab">
					<tr>
						<th><eoms:lable name='sm_lb_smname' /></th>
						<th><eoms:lable name='sm_lb_smstatus' /></th>
					</tr>
					<tr>
						<td>发送时间</td>
						<td>${startTime }--${endTime }</td>
					</tr>
					<tr>
						<td><eoms:lable name='sm_lb_smsend' /></td>
						<c:choose>
						   <c:when test="${status=='0'}">
						   	 	<td><eoms:lable name='sm_lb_started' /></td>
						   </c:when>
						   <c:otherwise>
						   		<td><eoms:lable name='sm_lb_stoped' /></td>
						   </c:otherwise>
						</c:choose>
					</tr>
			</div>
		</div>		
	</body>
</html>