<%@ page language="java" import="java.util.*,com.ultrapower.eoms.ultrasm.service.MenuManagerService,org.springframework.web.context.WebApplicationContext" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String reportType = request.getParameter("reportType");
	String menuId = request.getParameter("menuId");
	WebApplicationContext  wac=(WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	
	MenuManagerService menuManagerService = (MenuManagerService)wac.getBean("menuManagerService");
	
	String nodePath = menuManagerService.getNodePahtById(menuId);
 %>
<html>
  <head> 
		<%@ include file="/common/core/taglibs.jsp"%> 
		<title><eoms:lable name="wf_report_title"/></title>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,56);
			}
			window.onload = function() {
				setCenter(0,56);changeRow_color("tab");
			}
			
			function btn_submit()
			{
				var periodData = document.getElementById("periodData").value;
				var basecode = document.getElementById("basecode").value;
				var baseName = document.getElementById('basecode').options[document.getElementById('basecode').selectedIndex].text;
				var startTime = document.getElementById("startTime").value;    		
	    		var endTime = document.getElementById("endTime").value;
	    		
	    		var startTime1 = document.getElementById("startTime").value;    		
	    		var endTime1 = document.getElementById("endTime").value;
	    		
	    		var reporttype = "<%=reportType%>";
	    		
	    		startTime = startTime.replace('-','/');
	    		startTime = startTime.replace('-','/');
	    		
	    		endTime = endTime.replace('-','/');
	    		endTime = endTime.replace('-','/');
	    		
	    		var d2=new Date(startTime);
				var d1=new Date(endTime);
				var d3=d1-d2;
				if(d3<0)
				{
					alert('<eoms:lable name="wf_report_msg"/>!');
					return false;
				}
				if(periodData == "" )
	    		{
	    			alert('<eoms:lable name="wf_report_msg1"/>!');
	    			return false;
	    		}
	    		
				if(startTime == "" || endTime == "")
	    		{
	    			alert('<eoms:lable name="wf_report_msg2"/>!');
	    			return false;
	    		}

	    		if(basecode=="" 
	    			&& reporttype != 'eoms4_workflow_versionUsedCount' 
	    			&& reporttype != 'eoms4_workflow_entryDealTime')
	    		{
	    			//eoms4_workflow_versionUsedCount  || eoms4_workflow_entryDealTime
	    			if(reporttype!="eoms4_workflow_versionUsedCount" || reporttype!="eoms4_workflow_entryDealTime"){
	    				alert('<eoms:lable name="wf_report_msg3"/>!');
	    				return false;
	    			}
	    				
	    		}
	    		var reportfilename = "";
	    		if(reporttype!="eoms4_workflow_entryDealPromptness"){
	    		    reportfilename = reporttype+"_"+ periodData;
	    		 }else{
	    		 	if(periodData=="year")
	    		 		reportfilename = reporttype+"_yearAndMap";
	    		 	if(periodData=="months")
	    		 		reportfilename = reporttype+"_monthsAndMap";
	    		 	if(periodData=="week")
	    		 		reportfilename = reporttype+"_weekAndMap";
	    		 }
	    		//basecode 流程定义名称  &timetype=custom&datefromto=2010-12-01~2010-12-31
	    		//var url ="http://192.168.106.195:8090/report/bizman/common/result.jsp?defname="+basecode+"&defname_cn="+baseName+"&datefromto="+startTime1+"~"+endTime1+"&timename="+reportfilename+"&customizetype=sql&timetype=custom";
	    		//var url ="http://192.168.106.195:8090/report/bizman/common/result.jsp?defname="+basecode+"&defname_cn="+baseName+"&StartTime="+(d2.getTime()/1000)+"&EndTime="+(d1.getTime()/1000)+"&timename="+reportfilename+"&customizetype=sql";
	    		var url ="http://192.168.106.195:8090/report/bizman/common/result.jsp?defname="+basecode+"&defname_cn="+baseName+"&datefromto="+startTime1+"~"+endTime1+"&timename="+reportfilename+"&customizetype=sql&timetype=custom";
	    		document.QueryForm.action = url;
	    		document.QueryForm.submit();    
			}
		</script>
	</head>
	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2"><%=nodePath%></span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="page_div_bg">
				<div class="page_div">
					<li class="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'" onclick="showsearch()" ><eoms:lable name='com_btn_search'/></li>
					<li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" ><eoms:lable name='com_btn_refresh'/></li>
					<li class="page_help_button" onmouseover="this.className='page_help_button_hover'" 
	          onmouseout="this.className='page_help_button'"><eoms:lable name='com_btn_help'/></li>
				</div>
				<span class="pagenumber">
				</span>
			</div>
		</div>
		<div class="scroll_div" id="center">
			<form name="QueryForm" method="post" style="width:100%">
			<div id="serachdiv" class="serachdiv" style="display: none;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="serachdivTable">
					<tr>
						<td class="serachdivTableTd"><eoms:lable name="wf_report_type"></eoms:lable>：</td>
						<td><eoms:select name="periodData" style="width:100px;" dataDicTypeCode="period" ></eoms:select>
						<td class="serachdivTableTd"><eoms:lable name="wf_report_name"></eoms:lable>：</td>
						<td><eoms:select name="basecode" style="width:300px;" customSql="select code,name from bs_t_wf_version"></eoms:select>
						<td class="serachdivTableTd">
							<eoms:lable name="wf_report_createTimeBig"></eoms:lable>：</td>
						<td>
							<input type="text" name="startTime" id="startTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								readonly="true" />
						</td>
						<td class="serachdivTableTd"><eoms:lable name="wf_report_createTimeSmall"></eoms:lable>：</td>
						<td>
							<input type="text" name="endTime" id="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								readonly="true" />
						</td>
					</tr>
					<tr>
						<td colspan="8" align="center">
							<input type="submit" name="searchResource" id="searchResource" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="return btn_submit()"/>
			        		<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
						</td>
					</tr>
				</table>
			</div>
			</form>
		</div>
	</body>
</html>
