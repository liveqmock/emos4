<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="/WEB-INF/datagrid" prefix="dg"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
    <title>节假日列表</title>
    <script language="javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
	<script language="javascript">
	window.onresize = function() 
	{
	  setCenter(0,56);
	}
	window.onload = function() 
	{
	  setCenter(0,56);changeRow_color("tab");
	}
	function openwin(pid)
	{
		if (pid == '') {
			window.open("enterNewDutyHoliday.action","","width=500,height=330,top=0,left=0,Location=no,Toolbar=no,Resizable=yes,scrollbars=yes");
		} else {
			window.open("editDutyHoliday.action?pid="+pid,"","width=500,height=330,top=0,left=0,Location=no,Toolbar=no,Resizable=yes,scrollbars=yes");
		}
	}
	
	function delHolidays(){
    	var str="";
    	$("input:checked[name='checkid']").each(function(){
    		str+=$(this).val()+",";
    	})
    	if (str == "") {
    		alert("请选择要删除的节假日记录！");
    		return false;
    	} else {
    		if(!window.confirm("确认要删除吗?")){
				return false;
			}else{
				window.location.href="deleDutyHoliday.action?pid="+str;
			}
		}
    }
	</script>
  </head>
  <body>
  	<dg:datagrid var="dutyHoliday" items="${dutyHolidayList}" title="${nodePath }">
  		<dg:lefttoolbar>
	      <li class="page_search_button"  onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()"><eoms:lable name='com_btn_search'/></li>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"><eoms:lable name='com_btn_refresh'/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin('')"><eoms:lable name='com_btn_add'/></li>
	      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delHolidays();"><eoms:lable name='com_btn_delete'/></li>
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"><eoms:lable name='com_btn_help'/></li>
  		</dg:lefttoolbar>
  		<dg:condition>
	      <table  class="serachdivTable">
	        <tr>
	          <td class="serachdivTableTd">节假日名称：</td>
	          <td><input type="text" id="holidayName" name="holidayName" value="${holidayName }"/></td>
	           <td class="serachdivTableTd">是否节假日：</td>
	          <td>
				<select name="holidayFlag" class="select" style="width:100px">
					<option value="0" <c:if test="${0 == dutyHoliday.holidayFlag}">selected="selected"</c:if>>
						节假日
					</option>
					<option value="1" <c:if test="${1 == dutyHoliday.holidayFlag}">selected="selected"</c:if>>
						工作日
					</option>
				</select>
			  </td>
	          <td class="serachdivTableTd">开始时间：</td>
	          <td><input type="text" id="startTime" name="startTime" value="${startTime }" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" readonly="readonly"/></td>
	          <td class="serachdivTableTd">结束时间：</td>
	          <td><input type="text" id="endTime" name="endTime" value="${endTime }" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" readonly="readonly"/></td>
	        </tr>
	        <tr>
				<td colspan="8" align="center">
					<input type="submit" name="button" id="button" value="<eoms:lable name='com_btn_lookUp'/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="showsearch(0)" />
					<input type="reset" name="button2" id="button2" value="<eoms:lable name='com_btn_reset'/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
				</td>
	        </tr>
	      </table>  		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr align="center">
	    		<th align="center"><input name="checkidd" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th align="center">节假日名称</th>
	    		<th align="center">日期</th>
	    		<th align="center">是否节假日</th>
	    		<th align="center">是否启用</th>
	    		
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${dutyHoliday_row}">
				<td width="5"><input type="checkbox" name="checkid" value="${dutyHoliday.pid}"/></td>
		        <td width="30%" onclick="openwin('${dutyHoliday.pid}');">${dutyHoliday.holidayname}</td>
		        <td width="30%" onclick="openwin('${dutyHoliday.pid}');">${dutyHoliday.dateinfo}</td>
		        <td width="30%" onclick="openwin('${dutyHoliday.pid}');">
			        <c:choose>
			        	<c:when test="${dutyHoliday.holidayFlag == 1}">工作日</c:when>
			        	<c:otherwise>节假日</c:otherwise>
			        </c:choose>
		        </td>
		        <td width="30%" onclick="openwin('${dutyHoliday.pid}');">${dutyHoliday.hideFlagView}</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
