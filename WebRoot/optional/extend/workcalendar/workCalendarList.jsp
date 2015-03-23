<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<title>工作日历列表</title>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		  changeRow_color("tab");
		  $("input[name='datestring']").attr("readonly","readonly");
		  $("input[name='datestring']").click(function(){
		  	WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});
		  });
		}
	</script>
  </head>
  <body> 
  	<dg:datagrid  var="calday" title="${nodePath}" action="" sqlname="SQL_OPT_WorkCalendarList.query">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
  		      onmouseout="this.className='page_add_button'" 
  		      onclick="openwindow('${ctx}/workCalendar/workCalendarCreate.action','',800,450);"
  		      text="com_btn_add" operate="com_add"/>
	      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" 
	          onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  		</dg:lefttoolbar>
  		<dg:condition>
	  		<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="submit" name="searchDep" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>  
	       </div>		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th width="25%"><eoms:lable name="opt_lb_workCalType"/></th>
	    		<th width="25%"><eoms:lable name="opt_lb_workDateStr"/></th>
	    		<th><eoms:lable name="opt_lb_isHoliday"/></th>
	    		<th width="25%"><eoms:lable name="opt_lb_holidayType"/></th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${calday_row}">
		        <td>
		        	<a href="javascript:;" onclick="openwindow('${ctx }/workCalendar/workCalendarLoad.action?id=${pid}','',800,450);">
		        		<eoms:dic value="${calendartype}" dictype="workCalendarType"></eoms:dic>
		        	</a>
		        </td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx }/workCalendar/workCalendarLoad.action?id=${pid}','',800,450);">${datestring}</a></td>
		        <td>
			        <a href="javascript:;" onclick="openwindow('${ctx }/workCalendar/workCalendarLoad.action?id=${pid}','',800,450);">
			        	<eoms:dic value="${isholiday}" dictype="isdefault"></eoms:dic>
			        </a>
		        </td>
		        <td>
		        	<a href="javascript:;" onclick="openwindow('${ctx }/workCalendar/workCalendarLoad.action?id=${pid}','',800,450);">
		        		<eoms:dic value="${holidaytype}" dictype="holidayType"></eoms:dic>
		        	</a>
		        </td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
