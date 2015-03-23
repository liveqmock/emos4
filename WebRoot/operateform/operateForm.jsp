<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/operateform/js/easydialog.min.js"></script>
	 <title>
		     <eoms:lable name="wf_sort_wfsort" />
	</title>
	
    <script type="text/javascript">
    
	window.onresize = function() 
	{
	  setCenter(0,62);
	}
	$(window).load(function()
	{
	  setCenter(0,62);
	  changeRow_color_custom("tab",1);
	});
    
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
    
	function saveForm(){
		if(!confirm("是否确定要删除该工单？")){
			return false;
		}
		var baseID = document.getElementById("baseID");
		if("" == baseID.value){
			alert("baseID不能为空！");
			baseID.focus();
			return;
		}
		var baseSchema = document.getElementById("baseSchema");
		if("" == baseSchema.value){
			alert("baseSchema不能为空！");
			baseSchema.focus();
			return;
		}
        
		addForm.submit();
	}
	
	function del(baseID,baseSchema){
		if(!confirm("删除后无法恢复，是否确定要删除该工单？")){
			return false;
		}
        loading();
        $.ajax({
		  type: "GET",
		  url: "operateform/doDeleteForm.action",
		  data: "baseID="+baseID+"&baseSchema="+baseSchema,
		  success: function(msg){
		  	if(msg == 'true'){
		    	alert("删除成功！");
		    }else{
		    	alert("删除失败！");
		    }
		    document.forms[0].submit();
		  }
		});
	}
	
	
	function cancel(baseID,baseSchema){
		if(!confirm("作废后无法恢复，是否确定要作废该工单？")){
			return false;
		}
		loading();
        $.ajax({
		  type: "GET",
		  url: "operateform/doCancelForm.action?r="+Math.random(),
		  data: "baseID="+baseID+"&baseSchema="+baseSchema,
		  success: function(msg){
		  	if(msg == 'true'){
		    	alert("作废成功！");
		    }else{
		    	alert("作废失败！");
		    }
		    document.forms[0].submit();
		  }
		});
	}
	
	function loading(){
		easyDialog.open({
			container : 'imgBox',
			fixed : false
		});
	};
	
	function modify(baseID,baseSchema){
		window.open($ctx +'/operateform/modifyTicketPage.action?baseID='+baseID+'&baseSchema='+baseSchema, '工单修改', 'height=400px, width=800px, top=100, left=300, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
	}
	
    </script>
</head>
<body>
<dg:datagrid  var="waitingdealsheet"   title="工单操作" action="" sqlname="SQL_WF_OPERATEFORM.query" tquery="workSheetTitle">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
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
		    		<th width="15%" field="basesn"><eoms:lable name="wf_sheet_serialnum"/></th>
		    		<th width="30%" field="basesummary">工单主题</th>
		    		<th width="10%" field="basecreatorfullname"><eoms:lable name="wf_sheet_creator"/></th>
		    		<th width="15%" field="basecreatedate"><eoms:lable name="wf_sheet_createtime"/></th>
					<!--<th width="15%"><eoms:lable name="wf_sheet_closetime"/></th>-->
		    		<th width="10%"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    		<th width="10%">操作</th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basesn}</a></td>
		         <td>
		         	<a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">
		         		<c:if test="${fn:length(basesummary) > 40}">
			        		${fn:substring(basesummary, 0, 40)}...
			        	</c:if> <c:if test="${fn:length(basesummary) <= 40}">
			        		${basesummary}
			        	</c:if> 
			        </a>
		         </td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basecreatorfullname}</a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')"><eoms:date value="${basecreatedate}"/></a></td>
		        <td><a href="javascript:;"  onclick="openSheet('${baseschema}','${baseid}','${processtype}')">${basestatus}</a></td>
		        <td>
		        	<input type="button" name="deleteForm" value="作废" class="button" onclick="cancel('${baseid}','${baseschema}');"/>
		        	<input type="button" name="cancelForm" value="删除" class="button" onclick="del('${baseid}','${baseschema}');"/>
		        	<input type="button" name="cancelForm" value="修改" class="button" onclick="modify('${baseid}','${baseschema}');"/>
		        </td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	<div id="imgBox" style="display: none;"><img src='${ctx}/common/demo/dm/loading.gif' />处理中，请稍候。。。</div>
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