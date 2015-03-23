<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/datagrid" prefix="dg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include file="/common/core/taglibs.jsp"%>  
<script src="${ctx}/workflow/wfCustom/js/model.js"></script> 
<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	

<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/main_popup.css" />
<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/popup.css" />


    <title><eoms:lable name="wf_model_title"/></title>
		
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
	<dg:datagrid  var="WfModel" items="${modelList}" title="${nodePath}">
  		<dg:lefttoolbar>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"/>刷新</li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="addModel();"/>新增</li>
	      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delModel();"/>删除</li>
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"/><eoms:lable name="wf_model_help"/></li>
  		</dg:lefttoolbar>
  		
    	<dg:gridtitle>
				<tr>
					<th width="50">
						<input type="checkbox" onclick="checkAll('checkid')"></input>
					</th>
					<th>
						<!-- 流程模型名称 -->
						<eoms:lable name="wf_model_name" />
					</th>
					<th>
						<!-- 流程模型标识 -->
						<eoms:lable name="wf_model_code" />
					</th>
					<th>
						<!-- 流程模型类型 -->
						<eoms:lable name="wf_model_type" />
					</th>
					<th>
						<!-- 是否启用 -->
						<eoms:lable name="wf_ispublish" />
					</th>
				</tr>
		</dg:gridtitle>
		 
		<dg:gridrow>
			<tr class="${WfModel_row}">
				<td>
					<input name="checkid" id="checkid" type="checkbox" value='${WfModel.id}'></input>
				</td>
		        <td width="25%"><a href="javascript:;" onclick="editModel('${WfModel.id}');">${WfModel.name}</a></td>
		        <td width="25%"><a href="javascript:;" onclick="editModel('${WfModel.id}');">${WfModel.code}</a></td>
		        <td width="25%"><a href="javascript:;" onclick="editModel('${WfModel.id}');">${WfModel.type}</a></td>
				<td width="25%">
		        	<script>
						var ispublish = '${WfModel.isPublish}';
						var text;
						if(ispublish == '0'){
							text = '<eoms:lable name="wf_lb_design_no"/>';
						}
						else if(ispublish == '1'){
							text = '<eoms:lable name="wf_lb_design_yes"/>';
						}
						document.write(text);
					</script>
		        </td>
		    </tr>
		</dg:gridrow>
</dg:datagrid>

<form id="form" target="_self">
	<input type="hidden" name="propString" id="propString" />
</form>

  </body>
</html>
