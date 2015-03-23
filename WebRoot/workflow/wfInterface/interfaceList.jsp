<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
<%@ include file="/common/core/taglibs.jsp"%>   
 
<script src="${ctx}/workflow/wfInterface/js/interface.js"></script> 
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	

<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/main_popup.css" />
<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/popup.css" />


    <title><eoms:lable name="wf_interface_title1"/></title>

		<script language="javascript">
window.onresize = function() 
	{
	  setCenter(0,56);
	}
window.onload = function() 
	{
	  setCenter(0,56);changeRow_color("tab");
	}
	
/**
*删除流程接口方法
*liuchuanzu 20100607
*id	流程自定义模型id
*/
function delInterface() {
	var objs = document.getElementsByName("checkid");
	var wfString = "";
			for (var i = 0; i < objs.length; i++) {
				if (objs[i].checked == true) {
					wfString += "," + objs[i].value;
				}
			}
	wfString = wfString.substring(1);
			
			
	if(wfString == ''){
		alert('<eoms:lable name="com_unchecked"/>');
	}else{
		if (confirm('<eoms:lable name="com_btn_confirm_del"/>')) {
			$("#wfString")[0].value = wfString;
			$("#form")[0].action = "${ctx}/wfinterface/delInterface.action";
			$("#form")[0].submit();
		}
	}
}
	</script>
</head>
  

<body>   
<dg:datagrid  var="WfInterface" items="${interfaceList}" title="${nodePath}">
  		<dg:lefttoolbar>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"/><eoms:lable name="wf_model_reflash"></eoms:lable></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="addInterface();"/><eoms:lable name="wf_model_add1"></eoms:lable></li>
	      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delInterface();"/><eoms:lable name="wf_model_delete"></eoms:lable></li>
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"/><eoms:lable name="wf_model_help"></eoms:lable></li>
  		</dg:lefttoolbar>
  		
    	<dg:gridtitle>
				<tr>
					<th width="50">
						<input type="checkbox" onclick="checkAll('checkid')"></input>
					</th>
					<th>
						<!-- 接口名称 -->
						<eoms:lable name="wf_interface_name" />
					</th>
					<th>
						<!-- 接口标识 -->
						<eoms:lable name="wf_interface_code" />
					</th>
					<th>
						<!-- 接口类型  -->
						<eoms:lable name="wf_interface_type" />
					</th>
					<th>
						<!-- 接口路径 -->
						<eoms:lable name="wf_interface_path" />
					</th>
				</tr>
			</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${WfInterface_row}">
				<td><input name="checkid" id="checkid" type="checkbox" value='${WfInterface.code}'></input></td>
		        <td width="20%"><a href="javascript:;" onclick="editInterface('${WfInterface.code}');">${WfInterface.name}</a></td>
		        <td width="20%"><a href="javascript:;" onclick="editInterface('${WfInterface.code}');">${WfInterface.code}</a></td>
		        <td width="20%"><a href="javascript:;" onclick="editInterface('${WfInterface.code}');">
		        <script language="javascript">
		        	var type =  ${WfInterface.type};
		        	var test = '';
					if(type == '1'){
						test = '<eoms:lable name="wf_interface_mapinterface"/>';
					}else if(type == '2'){
						test = '<eoms:lable name="wf_interface_freeinterface"/>';
					}else if(type == '3'){
						test = '<eoms:lable name="wf_interface_conditioninterface"/>';
					}else if(type =='4'){
						test = '<eoms:lable name="wf_interface_manainterface"/>';
					}
					document.write(test);
		        </script>
		       
		        
		        </a></td>
				<td width="40%"><a href="javascript:;"  onclick="editInterface('${WfInterface.code}');" >${WfInterface.path}</a></td>
		    </tr>
		</dg:gridrow>
</dg:datagrid>

<form id="form" target="_self">
	<input type="hidden" name="wfString" id="wfString" />
</form>
  </body>
</html>
