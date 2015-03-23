<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0,0);
		}
		window.onload = function() 
		{
		  setCenter(0,0);
		  changeRow_color_custom("tab",1);
		}
		
		/**
		 *打开待办工单
		 */
		 function openSheet(baseSchema,baseId,taskid,processType){
		 	document.getElementById('baseSchemas').value = baseSchema ;
		 	document.getElementById('taskid').value = taskid ;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('processType').value = processType;
		 	document.getElementById('sheetform').submit();
		 }
		 
		 /**
		  *打开选择“建单人”树
		  */
		  function toSelectActor(){
		  	window.open('${ctx}/workflow/sheet/query/sheetCreator.jsp','_blank', 'height=500px,width=300px,toolbar=no,status=no,location=no,directories=no');
		  }
		  	   
		 /**
		  *新建工单
		  */
		  function createNewSheet(){
		  	var baseSchema = '${baseSchema}';
		 	document.getElementById('baseSchemas').value = baseSchema ;
		  	document.getElementById('sheetform').action = '${ctx}/sheet/createNewSheet.action';
		  	document.getElementById('sheetform').submit();
		  }
	</script>
  </head>
  
  <body style="background-color:white;">
  
	  	<dg:datagrid  var="waitingdealsheet"   title="" action="" sqlname="SQL_WF_WaitingDealSheet.query" pagesize='20' currentpage='1'>
	  	<dg:condition>
		  	<div align="center">
		      <table  class="serachdivTable" style="width:100%">
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
		    		<th style="font-weight:normal;line-height:18px; height:18px;"><eoms:lable name="wf_sheet_title"/></th>
		    		<th width="150" style="font-weight:normal;line-height:18px; height:18px;"><eoms:lable name="wf_sheet_dealtime"/></th>
		    		<th width="100" style="font-weight:normal;line-height:18px; height:18px;"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="45" style="font-weight:normal;line-height:18px; height:18px;"><eoms:lable name="wf_sheet_sign"/></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td style="line-height:18px; height:18px;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">
		        	${basename}：
		        	<c:if test="${fn:length(worksheettitle) > 30}">
		        		${fn:substring(worksheettitle, 0, 30)}...
		        	</c:if>
		        	<c:if test="${fn:length(worksheettitle) <= 30}">
		        		${worksheettitle}
		        	</c:if><c:if test="${duedate > 0 && currenttime > duedate}"><!-- 未完成已超时 -->
				     	<img src="${ctx}/workflow/sheet/images/overtime.gif" title="<eoms:lable name="wf_sheet_unfinished_timeout" />" />
				     </c:if>
		        </a>
		    	</td>
		        <td style="line-height:18px; height:18px;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');"><eoms:date value="${duedate}"/></a></td>
		        <td style="line-height:18px; height:18px;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${basestatus}</a></td>
		        <td style="line-height:18px; height:18px;">
				   	<c:if test="${currenttime <= duedate || duedate == 0}"><!-- 未完成未超时 -->
			        	<img src="${ctx}/workflow/sheet/images/gray.png" title="<eoms:lable name="wf_sheet_unfinished_untimeout" />" />
				     </c:if>
				     <c:if test="${duedate > 0 && currenttime > duedate}"><!-- 未完成已超时 -->
				     	<img src="${ctx}/workflow/sheet/images/yellow.png" title="<eoms:lable name="wf_sheet_unfinished_timeout" />" />
				     </c:if>
				</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank" style="display:none;">
			<input type="hidden" name="baseSchema" id="baseSchemas" />
			<input type="hidden" name="taskId" id="taskid" />
			<input type="hidden" name="baseId" id="baseId" />
			<input type="hidden" name="processType" id="processType" />
		</form>
  </body>
</html>
