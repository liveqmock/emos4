<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String sqlname = request.getParameter("sqlname");
	//String createtime = request.getParameter("createtime");
	//String endtime = request.getParameter("endtime");
	//String defname = request.getParameter("defname");
	//String stepcode = request.getParameter("stepcode");
 %>
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
		
		/**
		 *打开待办工单
		 */
		 function openSheet(baseSchema,baseId,taskid,processType){
		 	document.getElementById('sheetform').action = '${ctx}/sheet/openWaittingSheet.action';
		 	document.getElementById('schema').value = baseSchema ;
		 	document.getElementById('taskid').value = taskid ;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('processType').value = processType;
		 	document.getElementById('sheetform').submit();
		 }
		 
		 /**
		  *新建工单
		  */
		  function createNewSheet(){
		  	var schema = '${baseSchema}';
		  	document.getElementById('sheetform').action = '${ctx}/sheet/createNewSheet.action';
		  	document.getElementById("schema").value = schema;
		  	document.getElementById('sheetform').submit();
		  }
		 /**
		  *打开选择“建单人”树
		  */
		  function toSelectActor(){
		  	window.open('${ctx}/workflow/sheet/query/sheetCreator.jsp','_blank', 'height=500px,width=300px,toolbar=no,status=no,location=no,directories=no');
		  }
		  
		   
		   /**
		   *打开流程图
		   */
		   function openFlowMap(baseId,baseSchema,version){
			 	document.getElementById('schema').value = baseSchema;
		 		document.getElementById('baseId').value = baseId ;
		 		document.getElementById('version').value = version ;
			  	document.getElementById('sheetform').action = '${ctx}/sheet/openFlowMap.action';
			  	document.getElementById('sheetform').submit();
		   }
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="waitingdealsheet"   title="" action="" sqlname="<%=sqlname%>">
	  		<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  			</dg:lefttoolbar>
  			<dg:condition>
		  		<div align="center">
			      <table  class="serachdivTable" style="width:80%">
			        <tr>
			          <td colspan="6" align="center">
			          	<input type="submit" name="searchCondition" id="searchCondition" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
			        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
			          </td>
			        </tr>
			      </table>
			    </div>
	  		</dg:condition>
	    <dg:gridtitle>
		    	<tr>
		    		<th width="10%"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th width="20%"><eoms:lable name="wf_sheet_creator"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_createtime"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_closetime"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_sign"/></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basesn}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basecreatorfullname}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')"><eoms:date value="${basecreatedate}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')"><eoms:date value="${baseclosedate}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basestatus}</a></td>
		        <td>
				   	<c:if test="${baseclosedate <= 0 && ((currenttime <= basedealouttime && basedealouttime >0) || (basedealouttime == 0))}"><!-- 未完成未超时 -->
			        	<img src="${ctx}/workflow/sheet/images/gray.png" title="<eoms:lable name="wf_sheet_unfinished_untimeout" />" />
				     </c:if>
				     <c:if test="${baseclosedate <= 0 && (currenttime > basedealouttime && basedealouttime >0)}"><!-- 未完成已超时 -->
				     	<img src="${ctx}/workflow/sheet/images/yellow.png" title="<eoms:lable name="wf_sheet_unfinished_timeout" />" />
				     </c:if>
				     <c:if test="${baseclosedate >0 && (baseclosedate < basedealouttime || basedealouttime == 0)}"><!-- 已完成，未超时 -->
			        	<img src="${ctx}/workflow/sheet/images/green.png" title="<eoms:lable name="wf_sheet_finished_untimeout" />" />
				     </c:if>
				     <c:if test="${baseclosedate >0 && baseclosedate >= basedealouttime && basedealouttime > 0}"><!-- 已完成，已超时 -->
				     	<img src="${ctx}/workflow/sheet/images/red.png" title="<eoms:lable name="wf_sheet_finished_timeout" />" />
				     </c:if>
				</td>
		    </tr>
		    <tr class="${waitingdealsheet_row}">
		    	<td colspan="5" >
		    		<a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">
		    			${basename}：
			        	<c:if test="${fn:length(basesummary) > 40}">
			        		${fn:substring(basesummary, 0, 40)}...
			        	</c:if>
			        	<c:if test="${fn:length(basesummary) <= 40}">
			        		${basesummary}
			        	</c:if>
		    		</a>
		    		
		    	</td>
		    	<td>
		    		<a href="javascript:;" onclick="openFlowMap('${baseid}','${baseschema}','${basetplid}');"><eoms:lable name="wf_sheet_flowmap" /></a>
		    	</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
			<input type="hidden" name="baseSchema" id="schema"/>			
			<input type="hidden" name="taskId" id="taskid" />
			<input type="hidden" name="baseId" id="baseId" />
			<input type="hidden" name="processType" id="processType" />
			<input type="hidden" name="version" id="version" />
		</form>
  </body>
</html>
