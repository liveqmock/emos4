<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
<%@ include file="/common/core/taglibs.jsp"%>  

<script src="${ctx}/workflow/WfModel/js/list.js"></script> 
 

<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/main_popup.css" />
<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/popup.css" />

<title>自定义模型列表</title>

<script language="javascript">
window.onresize = function() 
	{
	  setCenter(0,56);
	}
	window.onload = function() 
	{
	  setCenter(0,56);changeRow_color("tab");
	}
</script>
</head>

<body>   
<dg:datagrid  var="WfModel" items="${modelList}" title="当前位置：流程自定义模型">
  		<dg:lefttoolbar>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"/><eoms:lable name="wf_model_reflash"/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="addFlow()"/><eoms:lable name="wf_model_add1"></eoms:lable></li>
	      <li class="page_edit_button" onmouseover="this.className='page_edit_button_hover'" onmouseout="this.className='page_edit_button'" onclick="editFlow()"/><eoms:lable name="wf_model_edit1"></eoms:lable></li>
	     <!-- 
	      <li class="page_import_button" onmouseover="this.className='page_import_button_hover'" onmouseout="this.className='page_import_button'"/>导入</li>
	      <li class="page_output_button" onmouseover="this.className='page_output_button_hover'" onmouseout="this.className='page_output_button'"/>导出</li>
	      -->
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"/><eoms:lable name="wf_model_help"></eoms:lable></li>
  		</dg:lefttoolbar>
  		
    	<dg:gridtitle>
				<tr>
					<th>
						<!-- 是否启用 -->
						<eoms:lable name="Wf.isPublish" />
					</th>
					<th>
						<!-- 流程模型标识 -->
						<eoms:lable name="WfModel.code" />
					</th>
					<th>
						<!-- 流程模型名称 -->
						<eoms:lable name="WfModel.name" />
					</th>
					<th>
						<!-- 流程模型类型 -->
						<eoms:lable name="WfModel.type" />
					</th>
					<th width="50">
						<!-- 操作-->
						<eoms:lable name="wf.Control" />
					</th>
				</tr>
			</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${book_row}">
		        <td width="5%" >
		        <script>
						var ispublish = '${WfModel.isPublish}';
						var text;
						if(ispublish == '0'){
							text = '否';
						}
						else if(ispublish == '1'){
							text = '是';
						}
						document.write(text);
					</script>
		        </td>
		        <td width="15%">${WfModel.code}</td>
		        <td width="15%">${WfModel.name}</td>
		        <td width="15%" >${WfModel.type}</td>
		        <td width="50">
		        	<img src="../workflow/WfModel/images/del.png" title="<eoms:lable name='wf_model_delete'/>" name="outageButton" onclick="delFlow('${WfModel.entryCount}','${WfModel.id}')" />
				</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	
	<form id="filedform" target="_self">
		<s:hidden name="baseCode" />
		<input name="WfModelId" id="WfModelId" />
	</form>
  </body>
</html>
