<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String sqlname = request.getParameter("sqlname");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		function getUrlParam(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		}
		
	 	$(document).ready(function(){
			$("#bu").click(function() {
				var list = "";
				var temp = "";
				var a = document.getElementsByName("selected");
				for (var i = 0; i < a.length; i++) {
					if (a[i].checked) {
						temp = a[i].value;
						list = list + temp + ";";
					}
				}
				if(list==""){
					alert("请选择需要关联的工单");
				}else{
				var relationBaseID = getUrlParam("relationBaseID");
				var taskId = getUrlParam("taskId");
				//alert(relationBaseID);
				//alert(taskId);
				//alert(list);
				//alert('${relatebaseSchema}');
			$.ajax({
					type: "get",
					url : "${ctx}/rsheet/addRsheet.action?relatebaseSchema=${relatebaseSchema}&relationBaseID="+relationBaseID+"&taskId="+taskId+"&baseIdlist="+list,
					success : function(mes) {
					//	alert(mes);
						window.close();
						window.opener.location.href=window.opener.location.href;
						window.close();
						window.parent.location.reload(true);
						
					},
					error: function (mes) {
						alert(mes);
					}
				});
				}
			});
	});



		function openSheet(baseSchema, baseId) {
		//	alert(baseSchema);
		//	alert(baseId);

			document.getElementById('baseSchema').value = baseSchema;
			document.getElementById('baseId').value = baseId;
			document.getElementById('sheetform').submit();
		}

		function checkItem(str) {
			var e = window.event.srcElement;
			var all = eval("document.all." + str);
			if (e.checked) {
				var a = document.getElementsByName(e.name);
				all.checked = true;
				for (var i = 0; i < a.length; i++) {
					if (!a[i].checked) {
						all.checked = false;
						break;
					}
				}
			} else
				all.checked = false;
		}
		
		  
	/*	function chooseRsheet() {
			alert(11111);
			var obj=document.getElementById("selectd");
			var relatebaseSchema=obj.options[obj.selectedIndex].text;//获取文本
			        for(i=0;i<obj.length;i++) {//下拉框的长度就是他的选项数
			           if(obj[i].selected==true) {
			            var relatebaseSchema=obj[i].text;//获取文本
			        }
			}
			var relationBaseID = getUrlParam("relationBaseID");
			alert(relatebaseSchema);
			alert('relationBaseID='+relationBaseID);
			alert('${taskId}');
			window.location.href="${ctx}/rsheet/chooseSheets.action?taskId=${taskId}&relatebaseSchema="+relatebaseSchema+"&relationBaseID="+relationBaseID;
		}  */

	</script>
	 </head>
   <body style="background-color:white;overflow-x:hidden;overflow-y:auto;" >
	<dg:datagrid  var="waitingDealSheet"   title="${nodePath}" action="" sqlname="${sqlname}" tquery="true">
			<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
  			<!-- 更改开始
  					<select id="selectd">
      		  <option value="">关联工单类型</option>
 			   <option value="1">CDB_CHANGE</option>
 		 	  <option value="2">CDB_RELEASE</option>
  			  <option value="3">CDB_SERVICEREQUEST</option>
  			   <option value="4">CBD_INCIDENT</option>
					</select>
  					 <li>
					<input type="button" value="测试" id="ce" onclick="chooseRsheet();" style="padding-left:1px;width:84px;margin-right:-8px"/>
					</li>  	
					 更改结束 -->
  					<li>
					<input type="button" value="关联" id="bu" style="padding-left:1px;width:84px;margin-right:-8px"/>
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
		    	<th width="5%"><input type='checkbox' value="全选" onclick="checkAll('selected')"/>全选</th>
		    		<th width="10%"><eoms:lable name="wf_relate_basename"/></th>
		    		<th width="10%"><eoms:lable name="wf_relate_serialno"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_title"/></th>
		    		<th width="10%"><eoms:lable name="wf_sheet_dearuser"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_createtime"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_closetime"/></th>
		    		<th width="15%"><eoms:lable name="wf_sheet_currentstatus"/></th>
		    	</tr>
	    </dg:gridtitle> 
	   
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
				<td><input type='checkbox' value="${baseid}" name="selected" /></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')" >${basename}</a></td> 
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${basesn}</a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${basesummary}</a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${requestuser}</a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')"><eoms:date value="${basecreatedate}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')"><eoms:date value="${baseclosedate}"/></a></td>
		        <td><a href="javascript:;" onclick="openSheet('${baseschema}','${baseid}')">${basestatus}</a></td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>

			<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
			<input type="hidden" name="baseSchema" id="baseSchema"/>			
			<input type="hidden" name="baseId" id="baseId" />
		</form>
  </body>
</html>
