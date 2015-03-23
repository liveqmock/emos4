<%@page import="com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/BaseQuery.js"></script>
	<script language="javascript">
	var llDivTitle1 = '<eoms:lable name="wf_sheet_query_quicklog_time" />';
	var llDivTitle2 = '<eoms:lable name="wf_sheet_query_quicklog_log" />';
		window.onload = function() 
		{
		  setCenter(0,33);
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
		   
	</script>
  </head>
  
  <body style="background-color:white;">  
	  	<dg:datagrid  var="waitingdealsheet"   title="${nodePath}" action="" sqlname="SQL_WF_WaitingDealSheetChange.query" tquery="true">
	  		<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
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
		    		<th width="6%" field="serialnum"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th width="15%" field="serialnum">主题</th>
		    		<th width="4%" field="dealer"><eoms:lable name="wf_sheet_dearuser"/></th>
		    		<th width="4%">变更分类</th>
		    		<th width="4%">变更类型</th>
		    		<th width="4%">变更状态</th>
		    		<th width="6%" field="createtime"><eoms:lable name="wf_sheet_stdate"/></th>
		    		<th width="4%" field="entryState"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="6%">要求服务时间</th>
		    		<th width="4%">跟踪</th>
		    		<th width="2%"><eoms:lable name="wf_sheet_sign"/></th>
		    		
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${chgtype eq '重大变更' || chgstate eq '紧急' ? 'vip' : ''}">
		        <td style="text-align:center;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${basesn}</a></td>
		        <td><a title="${worksheettitle}" href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${worksheettitle}</a></td>
		        <td style="text-align:center;"><a title="${deal }" href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${deal }</a>
		    	</td>
		    	 <td style="text-align:center;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${chgsort}</a></td>
		    	  <td style="text-align:center;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${chgtype }</a></td>
		    	   <td style="text-align:center;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${chgstate }</a></td>
		        <td style="text-align:center;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');"><eoms:date value="${stdate}"/></a></td>
		        <td style="text-align:center;"><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}');">${basestatus}</a></td>
		        <td style="text-align:center;"><eoms:date value="${requiredealtime}"/>${requireDealTime}</td>
		        <td style="text-align:center;">
		        	<a href="javascript:;" onmouseover="getProcessLog('${baseid}','${baseschema}', this)" onmouseout="hideProcessLog()">
		    			[<eoms:lable name="wf_sheet_query_quicklog" />]
		    		</a>
		    		<a href="javascript:;" onclick="openFlowMap('${baseid}','${baseschema}','${defname}', '${entryid}');">
		    			[<eoms:lable name="wf_sheet_flowmap" />]
		    		</a>
		        </td>
		        <td style="width: 30px;text-align:center;">
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
