<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>

<%
String baseschema = StringUtils.checkNullString(request.getParameter("baseschema"));
String attachId = StringUtils.checkNullString(request.getParameter("attachId"));
Map map = new HashMap();
map.put("baseschema", baseschema);
request.setAttribute("valuemap",map);
request.setAttribute("attachId",attachId);
%>



<html>
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script src="${ctx}/common/javascript/AjaxBus.js"></script>
	<title><eoms:lable name="wf_sheet_template_manage_title1"/></title>
	
	<script language="javascript">
		function loadModel() {//加载模板
			 var ary = document.getElementsByName('isSelect');
			 if(ary.length > 0) {
			 	for(var i=0;i<ary.length;i++) {
			 		var modelId = ary[i].value;
			 		var checked = ary[i].checked;
					if(checked) {
						var url = "${ctx}/wfAttach/dealModelAttach.action?attachId=${attachId}&modelId="+modelId;
						var bus = new AjaxBus();
						bus.callBackGet(url);
						parent.window.F(650020001).mObj.fireEvent('onclick');
					}
			 	}
			 }
		}
		
		function cancel() {//返回
			parent.window.F(650020002).mObj.fireEvent('onclick');
		}
	</script>
</head>
<body style="overflow:hidden;">
			<div style="overflow:scroll;height:150; width:100%; background-color:white;">
				<dg:datagrid  var="modeltemplate"  action="" sqlname="SQL_ModelSelect.queryModelList">
					<dg:lefttoolbar>
						<!--
			  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
			  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
			  		   -->
			 		</dg:lefttoolbar>
					<dg:gridtitle>
					   	<tr>
					   		<th width="25%"><eoms:lable name="wf_sheet_template_manage_name"/></th>
					   		<th width="25%"><eoms:lable name="wf_sheet_template_manage_version"/></th>
					   		<th width="10%"><eoms:lable name="wf_sheet_template_manage_auther"/></th>
					   		<th width="15%"><eoms:lable name="wf_sheet_template_manage_createtime"/></th>
					   		<th width="15%"><eoms:lable name="wf_sheet_template_manage_action"/></th>
					   		<th width="10%"><eoms:lable name="wf_sheet_template_manage_select"/></th>
					   	</tr>
					   </dg:gridtitle>
					   <dg:gridrow>
						<tr class="${waitingdealsheet_row}">
					        <td onclick="showModelView('${modelid}')">${modelname}</td>
					        <td onclick="showModelView('${modelid}')">${modelversion }</td>
					        <td onclick="showModelView('${modelid}')">${modelauthor}</td>
					        <td onclick="showModelView('${modelid}')"><eoms:date value="${modelcreatedate}"/></td>
					        <td><a href='#' onclick="deleteModel('${modelid}', '${modelbaseschema}')"><eoms:lable name="wf_sheet_template_manage_delete"/></a></td>
					        <td><input type='radio' id='rad_${modelid}' onclick="selectModel('${modelid}')" name='isSelect' value='${modelid}' <c:if test="${index == 0} }">checked</c:if>></td>
					    </tr>
					</dg:gridrow>
				</dg:datagrid>
			</div>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="height:350; width:100%; background-color:white;">
	<tr>
		<td>
			<iframe src="" name="ModelView" width="100%" height="100%" scrolling="yes" frameborder="0"></iframe>	
		</td>
	</tr>
	<tr style="height:40; width:100%;">
		<td align="right">
			<input type="button" id="loadModel" value="<eoms:lable name="wf_sheet_template_manage_load"/>" class="operate_button" onmouseover="this.className='operate_button_hover'" onmouseout="this.className='operate_button'" onclick="loadModel();" />
			<input type="button" id="cancel" value="<eoms:lable name="wf_sheet_template_manage_cancel"/>" class="operate_button" onmouseover="this.className='operate_button_hover'" onmouseout="this.className='operate_button'" onclick="cancel();" />
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
function showModelView(id)
{
	document.getElementById('ModelView').src = "BaseModelView.jsp?modelID=" + id;
}

function deleteModel(id,schema)
{
	if(confirm('<eoms:lable name="wf_sheet_template_manage_meg"/>'))
	{
		window.open('BaseModelCtrl.jsp?modelID=' + id+'&schema='+schema, '_self');
	}
}

function selectModel(id)
{
	var radio=document.getElementById("rad_" + id);
	radio.checked = true;
	if(id != '')
	{
		parent.window.F(650000000).S((new parent.window.CharType(id)));
	}
	showModelView(id);
}
if(document.getElementsByName('isSelect').length > 0)
selectModel(document.getElementsByName('isSelect')[0].value);
</script>
</html>
