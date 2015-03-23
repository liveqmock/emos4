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
			
			//function clearGC(){
			  //window.location.href="memory.jsp?clear=1";
			//}
		</script>
	</head>

	<%!
		private static final DecimalFormat mbFormat = new DecimalFormat("#0.00");
		private static final DecimalFormat percentFormat = new DecimalFormat("#0.0");
		private static final int NUM_BLOCKS = 100;
	%>
	<%
		//内存情况
		//if ("1".equals(request.getParameter("clear"))) {
		//	System.gc();
		//}
		
		Runtime runtime = Runtime.getRuntime();
		double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
		double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
		double usedMemory = totalMemory - freeMemory;
		double percentFree = ((double) freeMemory / (double) totalMemory) * 100.0;
		int used = 100 - (int) Math.round(percentFree);
	%>
	<body>
       	<div class="title_right">
	      	<div class="title_left">
		        <span class="title_bg">
		          <span class="title_icon2">当前位置：自管理>>内存管理</span>
		        </span>
	        	<span class="title_xieline"></span>
	      	</div>
	 	 </div>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
				    <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"/><eoms:lable name="com_btn_refresh"/></li>
					<li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"/><eoms:lable name="com_btn_help"/></li>
				</div>
			</div>
		 <div class="scroll_div" id="center">                           
			  <table class="tableborder" id="tab">
					<tr>
						<th><eoms:lable name="sm_lb_totalMemory"/></th>
						<th><eoms:lable name="sm_lb_usedMemory"/></th>
						<th><eoms:lable name="sm_lb_percentUsed"/></th>
						<th><eoms:lable name="sm_lb_percentFree"/></th>
					</tr>
					<tr>
						<td><%=mbFormat.format(totalMemory)%>MB</td>
						<td><%=mbFormat.format(usedMemory)%>MB</td>
						<td><%=percentFormat.format(100 - percentFree)%>%</td>
						<td><%=percentFormat.format(percentFree)%>%</td>
					</tr>
				</table>
				<table bgcolor="#000000" cellpadding="1" cellspacing="1" border="0" width="85%" align="center">
							<%
								for (int i = 0; i < NUM_BLOCKS; i++) {
									if ((i * (100 / NUM_BLOCKS)) < used) {
							%>
							<td bgcolor="#00ff00" width="<%=(100 / NUM_BLOCKS)%>%" height="20">
							</td>
							<%
								} else {
							%>
							<td bgcolor="#006600" width="<%=(100 / NUM_BLOCKS)%>%">
								
							</td>
							<%
								}
								}
							%>
				</table>
			</div>
		</div>		
	</body>
</html>
<%
	runtime = null;
%>
