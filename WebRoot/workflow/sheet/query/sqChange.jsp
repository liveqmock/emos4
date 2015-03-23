<%@page import="com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/BaseQuery.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/common/tree/js/commonViewTreePublic.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/operate.js"></script>
	<link href="${ctx}/common/plugin/autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/autocomplete/jquery.autocomplete.js" ></script>
	<script type="text/javascript" src="${ctx}/workflow/js/queryConditions.js" ></script>
	<script language="javascript">
	$(function() {
		
		$("#creator_name").autocomplete($ctx+"/userManager/autoSearchExOutHtml.action", {
			extraParams: 
	        {   
	             sqlQuery: "SQL_TEST_SearchUser.query"
	        },
			cellSeparator: "|",
			lineSeparator: "\n",
			minChars: 1,
			width: 350,
			maxItemsToShow: 10,
			formatItem: function(row) {
				return row[0] + "[" + row[1] + "]" + row[2];
			}

		}).result(function(event, row, formatted) {
			document.getElementsByName('creator_loginname')[0].value = row[1];
		});	
		
		$("#dealername").autocomplete($ctx+"/userManager/autoSearchExOutHtml.action", {
			extraParams: 
	        {   
	             sqlQuery: "SQL_TEST_SearchUser.query"
	        },
			cellSeparator: "|",
			lineSeparator: "\n",
			minChars: 1,
			width: 350,
			maxItemsToShow: 10,
			formatItem: function(row) {
				return row[0] + "[" + row[1] + "]" + row[2];
			}
		}).result(function(event, row, formatted) {
			document.getElementsByName('dealer_loginname')[0].value = row[1];
		});	
		
		$("#basecreatordep").autocomplete($ctx+"/depManager/autoSearchExOutHtml.action", {
			extraParams: 
	        {   
	             sqlQuery: "SQL_TEST_SearchDept.query"
	        },
			cellSeparator: "|",
			lineSeparator: "\n",
			minChars: 1,
			width: 350,
			maxItemsToShow: 10,
			formatItem: function(row) {
				return row[0] + "[" + row[2] + "]" ;
			}
		});	
		$("#basecreatorteam").autocomplete($ctx+"/depManager/autoSearchExOutHtml.action", {
			extraParams: 
	        {   
	             sqlQuery: "SQL_TEST_SearchTeam.query"
	        },
			cellSeparator: "|",
			lineSeparator: "\n",
			minChars: 1,
			width: 350,
			maxItemsToShow: 10,
			formatItem: function(row) {
				return row[0] + "[" + row[2] + "]" ;
			}
		}).result(function(event, row, formatted) {
		});	
		
		$("#creator_dep").autocomplete($ctx+"/depManager/autoSearchExOutHtml.action", {
			extraParams: 
	        {   
	             sqlQuery: "SQL_TEST_SearchDept.query"
	        },
			cellSeparator: "|",
			lineSeparator: "\n",
			minChars: 1,
			width: 350,
			maxItemsToShow: 10,
			formatItem: function(row) {
				
				return row[0] + "[" + row[2] + "]" ;
			}
		}).result(function(event, row, formatted) {
			document.getElementsByName('creator_dep_id')[0].value = row[1];
		});	
		
		$("#batch_info").autocomplete($ctx+"/business/autoSearchExOutHtml.action", {
			extraParams: 
	        {   
	             sqlQuery: "SQL_TEST_SearchBatchInfo.query"
	        },
			cellSeparator: "|",
			lineSeparator: "\n",
			minChars: 1,
			width: 350,
			maxItemsToShow: 10,
			formatItem: function(row) {
				return row[0];
			}
		}).result(function(event, row, formatted) {
			document.getElementsByName('batch_info')[0].value = row[0];
		});	
		
		
		
	}); 
		
		
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
		  initTitle('<%=GridConstants.HIDDEN_SORTFIELD%>','<%=GridConstants.HIDDEN_SORTTYPE%>');
		}
		
		/**
		 *打开待办工单
		 */
		 function openSheet(baseSchema,baseId,processType){
		 	document.getElementById('sheetform').action = '${ctx}/sheet/openWaittingSheet.action';
		 	document.getElementById('schema').value = baseSchema ;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('processType').value = processType;
		 	document.getElementById('sheetform').submit();
		 }
		 /**
		  *打开"工单状态树"
		  */
		  function selectStatus(){
		    	var url='queryExtend/selectStatus.action?baseSchema=${baseSchema}'; 
				var name='_blank'; 
				var iWidth=315; 
				var iHeight=500; 
				var iTop = (window.screen.availHeight-30-iHeight)/2; 
				var iLeft = (window.screen.availWidth-10-iWidth)/2; 
				window.open('${ctx}/'+url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
		  }
		 
		 /**
		  *新建工单
		  */
		  function createNewSheet(){
		  	var schema = '${baseSchema}';
		  	if(schema.indexOf(',') > 0) {
		  		schema = schema.split(',')[0];
		  	}
		  	document.getElementById('sheetform').action = '${ctx}/sheet/createNewSheet.action';
		  	document.getElementById("schema").value = schema;
		  	document.getElementById('sheetform').submit();
		  }
		 
		  
		  
		 /**
		  *打开选择“建单人”树
		  */
// 		  function toSelectActor(textElementId,idElementId){
// 			 var url = "${ctx}/workflow/sheet/query/sheetCreator.jsp";
// 			 if(textElementId){
// 				 url = url + "?textElementId="+textElementId+"&idElementId="+idElementId;
// 			 }
// 		  	window.open(url,'_blank', 'height=500px,width=315px,toolbar=no,status=no,location=no,directories=no');
// 		  }
		 

		   
	  function toSelectActor(){
			userAutoInput("creator_name","creator_loginname",function(user){
			    document.getElementsByName('creator_loginname')[0].value = user.loginname;
			});
	  }
		   /**
		   *打开流程图
		   */
		   function openFlowMap(baseId,baseSchema,version, baseentryId){
			 	document.getElementById('schema').value = baseSchema;
		 		document.getElementById('baseId').value = baseId ;
		 		document.getElementById('version').value = version ;
		 		document.getElementById('entryId').value = baseentryId ;
			  	document.getElementById('sheetform').action = '${ctx}/sheet/openFlowMap.action';
			  	document.getElementById('sheetform').submit();
		   }
		   
		   //变更分类
		   function openChgSortTree(enableCheckBoxes,nameElementId,idElementId){
				if(!idElementId) idElementId = "";
				var url = $ctx+"/commonViewTree/commonViewTree.action?opt.query=SQL_CDB_ChgSort.query&opt.nameElementId="+nameElementId+"&opt.enableCheckBoxes="+enableCheckBoxes+"&opt.idElementId="+idElementId+"&opt.parentid=0&opt.sameAsId=parentid";
				deptTreeWindow = window.open(url,'deptTree','height=800px,width=600px,toolbar=no,status=no,location=no,directories=no');
			}
		   
	</script>
  </head>
   
  <body style="background-color:white;">
	  	<dg:datagrid  var="waitingdealsheet"   title="${nodePath}" action="" sqlname="SQL_WF_SQChange.query" tquery="workSheetTitle">
	  		<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		  <eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'"  onclick="showsearchcondition('${ctx}','SQL_WF_SQChange.query')" text="com_btn_config" />
	  		  
	  		  <s:if test='%{baseSchema != null && baseSchema != ""}'>
	  		   <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="createNewSheet();" text="com_btn_add" />
		      </s:if>
  			  <eoms:operate cssclass="page_excel_button" id="com_btn_excel" onmouseover="this.className='page_excel_button_hover'" onmouseout="this.className='page_excel_button'" onclick="excelExport('SQL_WF_SQChange.query','${wfSortId}','${baseSchema}')" text="com_btn_export"/>
  			</dg:lefttoolbar>
	  	<dg:condition>
		  	<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="ids" id="userID" value=""/>
					<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="button" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'" onclick="z_reset()"/>
		        	<input type="button" name=saveCondition id="saveCondition"  class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="saveConditions('change')" value="收藏"/>
		        	<input type="button" name=defaultCondition id="defaultCondition"  class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="setConditions('change','${userSession.pid}')" value="提取 "/>
		          </td>
		        </tr>
		      </table>
			</div>
	  	</dg:condition>
	    <dg:gridtitle>
		    	<tr>
		    		<th width="12%"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th width="18%">主题</th>
		    		<th width="10%"><eoms:lable name="wf_sheet_creator"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_deal"/></th>
		    		<th width="13%"><eoms:lable name="wf_sheet_createtime"/></th>
		    		<th width="13%"><eoms:lable name="wf_sheet_closetime"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="10%">跟踪</th>
		    		<th width="4%"><eoms:lable name="wf_sheet_sign"/></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basesn}</a></td>
		        <td><a title="${basesummary}" href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basesummary}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basecreatorfullname}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${deal}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')"><eoms:date value="${basecreatedate}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')"><eoms:date value="${baseclosedate}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basestatus}</a></td>
		        <td>
		    		<a href="javascript:;" onmouseover="getProcessLog('${baseid}','${baseschema}', this)">
		    			[<eoms:lable name="wf_sheet_query_quicklog" />]
		    		</a>
		    		<a href="javascript:;" onclick="openFlowMap('${baseid}','${baseschema}','${basetplid}', '${baseentryid}');">
		    			[<eoms:lable name="wf_sheet_flowmap" />]
		    		</a>
		    	</td>
		        <td>
		        	<cdb:sign basedealouttime="${basedealouttime }" basestatus="${basestatus }" baseacceptouttime="${baseacceptouttime }" basefinishdate="${basefinishdate }"  baseacceptdate="${baseacceptdate }" imgSrc="${ctx}/workflow/sheet/images/" />
				</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		
		
		<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
			<input type="hidden" name="baseSchema" id="schema"/>			
			<input type="hidden" name="baseId" id="baseId" />
			<input type="hidden" name="entryId" id="entryId" />
			<input type="hidden" name="processType" id="processType" />
			<input type="hidden" name="version" id="version" />
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