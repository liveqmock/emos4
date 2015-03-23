<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>   
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0,55);
		}
		window.onload = function() 
		{
		  setCenter(0,55);
		  changeRow_color_custom("tab",2);
		}

		function openSheet(baseSchema,baseId,taskid,processType){
		 	document.getElementById('sheetform').action = '${ctx}/sheet/openWaittingSheet.action';
		 	document.getElementById('schema').value = baseSchema ;
		 	document.getElementById('taskid').value = taskid ;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('processType').value = processType;
		 	document.getElementById('sheetform').submit();
		 }
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="waitingdealsheet"   title="${titleFlag}" action="" sqlname="SQL_WF_NetChangeView.query" pagesize='20' currentpage='1'>
	  		<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		  
		     <!-- 
	  		  <eoms:operate cssclass="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" onmouseout="this.className='page_output_button'" text="com_btn_export" operate="com_exp_op"/>
		      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  			 -->
  			</dg:lefttoolbar>
	  	<dg:condition>
		  	<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="ids" id="userID" value=""/>
					<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>
			</div>
	  	</dg:condition>
	    <dg:gridtitle>
		    	<tr>
		    		<th width="15%"><eoms:lable name="wf_netchange_change_start"/></th>
		    		<th width="15%"><eoms:lable name="wf_netchange_change_end"/></th>
		    		<th width="9%"><eoms:lable name="wf_netchange_changetype"/></th>
		    		<th width="10%"><eoms:lable name="wf_netchange_category"/></th>
		    		<th width="10%"><eoms:lable name="wf_netchange_place"/></th>
		    		<th width="10%"><eoms:lable name="wf_netchange_nc"/></th>
		    		<th width="32%"><eoms:lable name="wf_netchange_summary"/></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')"><eoms:date value="${plan_starttime}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')"><eoms:date value="${plan_endtime}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${changetype}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${baseitems}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${relate_province} ${relate_city}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${netname}</a></td>
		    	<td>
		    		<a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">
			        	<c:if test="${fn:length(basesummary) > 40}">
			        		${fn:substring(basesummary, 0, 40)}...
			        	</c:if>
			        	<c:if test="${fn:length(basesummary) <= 40}">
			        		${basesummary}
			        	</c:if>
		    		</a>
		    	</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		
		
		<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
			<input type="text" name="baseSchema" id="schema"/>			
			<input type="hidden" name="taskId" id="taskid" />
			<input type="hidden" name="baseId" id="baseId" />
			<input type="hidden" name="processType" id="processType" />
			<input type="hidden" name="version" id="version" />
		</form>
  </body>
</html>
