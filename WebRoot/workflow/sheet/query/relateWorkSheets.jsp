<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<!-- <script src="/ultrabpp/runtime/theme/classic/js/main.js"></script>  -->
	<script language="javascript">
		/**
		 *打开待办工单
		 */
		 function openSheet(baseSchema,baseId){
		 	document.getElementById('baseSchema').value = baseSchema ;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('sheetform').submit();
		 }
	
		function syncCreate() {
			var baseSchema = document.getElementById('relateSchemaType').value;
			window.open("${ctx}/ultrabpp/view.action?mode=NEW&baseSchema="+baseSchema+"&relateType=1&fromBaseSchema=${baseSchema}&fromBaseID=${baseId}&fromBaseSn=${baseSn}&fromTaskID=${taskId}&copyRelateData=${copyRelateData}");
		}
		
		function asyncCreate() {
			var baseSchema = document.getElementById('relateSchemaType').value;
			window.open("${ctx}/ultrabpp/view.action?mode=NEW&baseSchema="+baseSchema+"&relateType=0&fromBaseSchema=${baseSchema}&fromBaseID=${baseId}&fromBaseSn=${baseSn}&fromTaskID=${taskId}&cRelateData=1&cRelateByConfig=1");
		}
		function rsheetCreate(){
			//alert('${baseId}');
			var relatebaseSchema = document.getElementById('relateSchemaType').value;
			window.open("${ctx}/rsheet/relateSheets.action?relationBaseID=${baseId}&taskId=${taskId}&relatebaseSchema="+relatebaseSchema);
		//window.open("${ctx}/rsheet/relateSheets.action?relationBaseID=${baseId}&taskId=${taskId}");
		 
		}
		
		function deleteSheet(rbaseid,baseid){
			$.ajax
		       ({	type: "get",
		            url: "${ctx}/rsheet/deleteSheet.action?relationBaseID="+rbaseid+"&baseid="+baseid,
		            success: function (mes)
		            { 
		             alert(mes);
		             location.reload(true);
		            }, 
					 error: function (mes)
		            { 
			             alert(mes);
			        }
		        });

		}
	
</script>
  </head>
   <body style="background-color:white;">
   	  	<dg:datagrid  var="waitingdealsheet"   title="${nodePath}" action="" sqlname="SQL_WF_RelateSheet.query" tquery="true">
	  	<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		    <c:if test="${baseId != '' && fn:length(relateSchemas)>0}"> 
						<li>
							<s:select cssClass="paddingHack" id="relateSchemaType" name="fieldType"  list="relateSchemas" listKey="code" listValue="name">
	  						</s:select>
						</li>
					 <!-- 	<c:if test="${fn:indexOf(relateType, '1') > -1 && '1' == flagActive}">
							<li>
								<input type="button" value="同步关联" onclick="syncCreate();" />
							</li>
						</c:if>
						<c:if test="${fn:indexOf(relateType, '0') > -1}">
							<li>
								<input type="button" value="异步关联" onclick="asyncCreate();" style="padding-left:1px;width:84px;margin-right:-8px"/>
							</li>
						</c:if>	 -->
					  </c:if>  
				 
					<li>
					<input type="button" value="关联" onclick="rsheetCreate();" style="padding-left:1px;width:84px;margin-right:-8px"/>
					</li>
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
		    		<th width="20%"><eoms:lable name="wf_relate_basename"/></th>
		    		<th width="20%"><eoms:lable name="wf_relate_serialno"/></th>
		    		<th width="20%"><eoms:lable name="wf_sheet_title"/></th>
		    		<th width="20%"><eoms:lable name="wf_relate_time"/></th>
		    		<th width="10%"><eoms:lable name="wf_relate_user"/></th>
		    		<th width="10%"><lable>删除关联</lable></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${basename}</a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${basesn}</a></td>
		      <!--  <td>
		        	<a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">
		        		<c:if test="${relatetype == '0'}">
		        			异步
		        		</c:if>
		        		<c:if test="${relatetype == '1'}">
		        			同步
		        		</c:if>
		        	</a> 
		        </td>-->
		         <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${basesummary}</a></td>
				<td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')"><eoms:date value="${relatetime}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${relateusername}</a></td>
		         <td><a href="javascript:;" onclick="deleteSheet('${baseId}','${baseid}')">删除关联</a></td>
		       <!--  <td><a href="javascript:;" class="sendRequest">删除关联</a></td>  -->
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
		<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
			<input type="hidden" name="baseSchema" id="baseSchema"/>			
			<input type="hidden" name="baseId" id="baseId" />
		</form>
  </body>
</html>
