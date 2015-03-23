<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
	<script type="text/javascript" src="${ctx}/common/javascript/util.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/BaseQuery.js"></script>
	<script language="javascript">
		var llDivTitle1 = '<eoms:lable name="wf_sheet_query_quicklog_time" />';
		var llDivTitle2 = '<eoms:lable name="wf_sheet_query_quicklog_log" />';
		window.onresize = function() 
		{
		  setCenter(0,55);
		}
		window.onload = function() 
		{
		  setCenter(0,55);
		  changeRow_color_custom("tab",1);
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
		  	window.open('${ctx}/workflow/sheet/query/sheetCreator.jsp','_blank', 'height=500px,width=315px,toolbar=no,status=no,location=no,directories=no');
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

			function doDelete()
			{
				var chkObjs = document.getElementsByName('chkDeleteBase');
				var delIDs = '';
				for(var i = 0; i < chkObjs.length; i++)
				{
					if(chkObjs[i].checked)
					{
						delIDs += (',' + chkObjs[i].value);
					}
				}
				delIDs = delIDs.substr(1);
				document.getElementById('baseIDs').value = delIDs;

				document.getElementById('deleteform').submit();
			}

			function selectall()
			{
				var chkall = document.getElementById('chk_all').checked;
				var chks = document.getElementsByName('chkDeleteBase');
				for(var i = 0; i < chks.length; i++)
				{
					if(chkall)
					{
						chks[i].checked = true;
					}
					else
					{
						chks[i].checked = false;
					}
				}
			}
	</script>
  </head>
  <body>
	  	<dg:datagrid  var="waitingdealsheet"   title="工单删除" action="" sqlname="${queryname}" tquery="workSheetTitle" cachemode="sql">
	  		<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
	  		  	<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		  	<eoms:operate cssclass="page_del_button" id="com_btn_delete" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="doDelete()" text="com_btn_delete" />
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
					<th width="50"><input type="checkbox" id="chk_all" onclick="selectall()" /></th>
					<th width="120"><eoms:lable name="wf_sheet_wfbaseid"/></th>
		    		<th width="150"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th><eoms:lable name="wf_sheet_basesummary"/></th>
		    		<th width="100"><eoms:lable name="wf_sheet_creator"/></th>
		    		<th width="120"><eoms:lable name="wf_sheet_createtime"/></th>
		    		<th width="100"><eoms:lable name="wf_sheet_currentstatus"/></th>
					<th width="120"><eoms:lable name="wf_sheet_basedealouttime"/></th>
					<th width="80"><eoms:lable name="wf_sheet_basepriority"/></th>
		    	</tr>
	    </dg:gridtitle>
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
				<td><input type="checkbox" id="chk_${baseid}" name="chkDeleteBase" value="${baseid};${baseschema}" /></td>
				<td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${baseid}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basesn}</a></td>
		        <td>
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
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basecreatorfullname}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')"><eoms:date value="${basecreatedate}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basestatus}</a></td>
				<td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')"><eoms:date value="${basedealouttime}"/></a></td>
				<td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${taskid}','${processtype}')">${basepriority}</a></td>
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
		<form id="deleteform" action="${ctx}/formDelete/multiDelete.action" target="_self">
			<input type="hidden" name="baseSchema" id="schema" value="${baseschema}" />
			<input type="hidden" name="baseIDs" id="baseIDs" />
		</form>
		<div id="logList" style="position:absolute; float:left; top:10px; left:10px; width:400px; height:140px; display:none;">
			<div id="logListContent"><div></div></div>
			<div id="logListPoint"><div class="logListPointTop"></div><div class="logListPointBottom"></div></div>
		</div>
  </body>
</html>
