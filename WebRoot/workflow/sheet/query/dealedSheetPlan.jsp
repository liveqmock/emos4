<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>   
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0,28);
		}
		window.onload = function() 
		{
		  setCenter(0,28);
		  changeRow_color_custom("tab",2);
		}
		
		/**
		 *打开待办工单
		 */
		 function openSheet(baseSchema,baseId){
		 	document.getElementById('sheetform').action='${ctx}/sheet/openDealedSheet.action';
		 	document.getElementById('baseSchemas').value = baseSchema;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('sheetform').submit();
		 }
		 
		 /**
		  *打开选择“建单人”树
		  */
		  function toSelectActor(){
		  	
		  }
		  
		   
		 /**
		  *新建工单
		  */
		  function createNewSheet(){
		  	document.getElementById('sheetform').action = '${ctx}/sheet/createNewSheet.action';
		  	document.getElementById('sheetform').submit();
		  }
		  
		   /**
		   *打开流程图
		   */
		   function openFlowMap(baseId,baseSchema,version){
			 	document.getElementById('baseSchemas').value = baseSchema;
		 		document.getElementById('baseId').value = baseId ;
		 		document.getElementById('version').value = version ;
			  	document.getElementById('sheetform').action = '${ctx}/sheet/openFlowMap.action';
			  	document.getElementById('sheetform').submit();
		   }
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="waitingdealsheet"  title="${titleFlag}" action="" sqlname="SQL_WF_DealedSheet_Plan.query">
	  		<dg:lefttoolbar>
	  		 <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		 <!-- 
	  		 <eoms:operate cssclass="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" onmouseout="this.className='page_output_button'" text="com_btn_export" operate="com_exp_op"/>
		     <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" text="com_btn_help"/>
		      -->
  		</dg:lefttoolbar>
	  	
	    <dg:gridtitle>
		    	<tr>
		    		<th width="10%"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th width="20%"><eoms:lable name="wf_sheet_creator"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_createtime"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_dealtime"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_sign"/></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}');">${basesn}</a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}');">${basecreatorfullname}</a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}');"><eoms:date value="${basecreatedate}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}');"><eoms:date value="${basedealouttime}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}');">${basestatus}</a></td>
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
		    	<td colspan="5">
		    		<a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}');">${basename}：${basesummary}</a>
		    	</td>
		    	<td>
		    		<a href="javascript:;" onclick="openFlowMap('${baseid}','${baseschema}','${basetpliD}');"><eoms:lable name="wf_sheet_flowmap" /></a>
		    	</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		
		<form id="sheetform" action="${ctx}/sheet/openDealedSheet.action" target="_blank">
			<input type="hidden" name="baseId" id="baseId" />
			<s:hidden name="baseSchema" id="baseSchemas"></s:hidden>
			<input type="hidden" name="version" id="version" />
		</form>
  </body>
</html>
