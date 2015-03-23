<%@page import="com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/BaseQuery.js"></script>
	<script language="javascript">
	var llDivTitle1 = '<eoms:lable name="wf_sheet_query_quicklog_time" />';
	var llDivTitle2 = '<eoms:lable name="wf_sheet_query_quicklog_log" />';
		window.onresize = function() 
		{
		  setCenter(0,74);
		}
		window.onload = function() 
		{
		  setCenter(0,74);
		  changeRow_color_custom("tab",1);
		  /** 按标题排序 */
		  initTitle('<%=GridConstants.HIDDEN_SORTFIELD%>','<%=GridConstants.HIDDEN_SORTTYPE%>');
		}
		
		/**
		 *打开待办工单
		 */
		 function openSheet(baseSchema,baseId,taskid,processType){
		 	document.getElementById('sheetform').action = '${ctx}/sheet/openWaittingSheet.action';
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
		  	window.open('${ctx}/workflow/sheet/query/sheetCreator.jsp','_blank', 'height=500px,width=330px,toolbar=no,status=no,location=no,directories=no');
		  }
		  	   
		 /**
		  *新建工单
		  */
		  function createNewSheet(){
		  	var baseSchema = '${baseSchema}';
		  	if(baseSchema.indexOf(',') > 0) {
		  		baseSchema = baseSchema.split(',')[0];
		  	}
		 	document.getElementById('baseSchemas').value = baseSchema ;
		  	document.getElementById('sheetform').action = '${ctx}/sheet/createNewSheet.action';
		  	document.getElementById('sheetform').submit();
		  }
		  
		  /**
		   *打开流程图
		   */
		   function openFlowMap(baseId,baseSchema,version, entryId){
			 	document.getElementById('baseSchemas').value = baseSchema;
		 		document.getElementById('baseId').value = baseId;
		 		document.getElementById('entryId').value = entryId ;
		 		document.getElementById('version').value = version ;
			  	document.getElementById('sheetform').action = '${ctx}/sheet/openFlowMap.action';
			  	document.getElementById('sheetform').submit();
		   }
		   
	   /**
	   * 导出EXCEL
	   */
	   function excelExport(){
	   	 document.forms[0].action="sheetExport.action?type=SQL_WF_WaitingDealSheet.query";
	   	 document.forms[0].submit();
	   }
		
	</script>
  </head>
  
  <body style="background-color:white;">
	  	<dg:datagrid  var="waitingdealsheet"   title="${nodePath}" action="" sqlname="SQL_WF_WaitingDealSheetServerQuest.query" tquery="true">
	  		<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		   <s:if test='%{baseSchema != null && baseSchema != ""}'>
	  		   	<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="createNewSheet();" text="com_btn_add" />
		     	</s:if>
		     <!--
		      <eoms:operate cssclass="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" onmouseout="this.className='page_output_button'" text="com_btn_export" operate="com_exp_op"/>
		      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" text="com_btn_help"/>
		      -->
			  <eoms:operate cssclass="page_excel_button" id="com_btn_excel" onmouseover="this.className='page_excel_button_hover'" onmouseout="this.className='page_excel_button'" onclick="excelExport()" text="com_btn_export"/>
  			</dg:lefttoolbar>
	  	<dg:condition>
		  	<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="ids" id="userID" value=""/>
					<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchUser" onclick="querysubmit()" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>
			</div>
	  	</dg:condition>
	    <dg:gridtitle>
		    	<tr>
		    		<th width="13%" field="serialnum"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th width="25%" field="worksheettitle">主题</th>
		    		<th width="10%" field="dealer"><eoms:lable name="wf_sheet_dearuser"/></th>
		    		<th width="15%" field="duedate"><eoms:lable name="wf_sheet_dealtime"/></th>
		    		<th width="15%" field="createtime"><eoms:lable name="wf_sheet_stdate"/></th>
		    		<th width="10%" field="entryState"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="9%">跟踪</th>
		    		<th width="3%"><eoms:lable name="wf_sheet_sign"/></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${viplevel eq '' ? '' : 'vip'}">
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${basesn}</a></td>
		        <td><a title="${worksheettitle}" href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${worksheettitle}</a></td>
		        <td><a title="${deal }" href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${deal }</a>
		    	</td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');"><eoms:date value="${duedate}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');"><eoms:date value="${stdate}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${basestatus}</a></td>
		        <td>
		    		<a href="javascript:;" onmouseover="getProcessLog('${baseid}','${baseschema}', this)" onmouseout="hideProcessLog()">
		    			[<eoms:lable name="wf_sheet_query_quicklog" />]
		    		</a>
		    		<a href="javascript:;" onclick="openFlowMap('${baseid}','${baseschema}','${defname}', '${entryid}');">
		    			[<eoms:lable name="wf_sheet_flowmap" />]
		    		</a>
		    	</td>
		        <td>
		        	<cdb:sign basedealouttime="${basedealouttime }" basestatus="${basestatus }" baseacceptouttime="${baseacceptouttime }" basefinishdate="${basefinishdate }" baseacceptdate="${baseacceptdate }" imgSrc="${ctx}/workflow/sheet/images/" />
				</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
			<input type="hidden" name="baseSchema" id="baseSchemas" />
			<input type="hidden" name="taskId" id="taskid" />
			<input type="hidden" name="baseId" id="baseId" />
			<input type="hidden" name="entryId" id="entryId" />
			<input type="hidden" name="version" id="version" />
			<input type="hidden" name="processType" id="processType" />
		</form>
		
		<div id="logList" style="position:absolute; float:left; top:10px; left:10px; width:400px; height:140px; display:none;">
			<div id="logListContent"><div></div></div>
			<div id="logListPoint"><div class="logListPointTop"></div><div class="logListPointBottom"></div></div>
		</div>
  </body>
</html>
<script language="javascript" type="text/javascript">
try
{
    document.body.attachEvent("onmousemove", hideProcessLog);
}
catch(e)
{
	document.body.addEventListener("mousemove", hideProcessLog, true);
}
</script>
