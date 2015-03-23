<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
<%@ include file="/common/core/taglibs.jsp"%>  

<script src="${ctx}/workflow/wfVersion/js/versionList.js"></script> 
 

<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/main_popup.css" />
<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/popup.css" />

<title><eoms:lable name="wf_version_title"/></title>

<script language="javascript">
window.onresize = function() 
	{
	  setCenter(0,30);
	}
	window.onload = function() 
	{
	  setCenter(0,30);
	  initCheckBox();
	}
	var versionArray = new Array();
	function initCheckBox(){
		var selectVersion = '${selectVersion}';
		versionArray = selectVersion.split(',');
		for(var i=0;i<versionArray.length;i++){
			var elementID = versionArray[i];
			document.getElementById(elementID).checked = true;
		}
	}
	function saveSelectVersion(){
		var selectVersionString = '';
		var checkboxs = document.getElementsByName("wfVersionCheckBox");
		for(var i=0;i<checkboxs.length;i++){
			var checkbox = checkboxs[i];
			if(checkbox.checked){
				if(selectVersionString==''){
					selectVersionString = checkbox.id;
				}else{
					selectVersionString +=','+checkbox.id;
				}
			}
		}
		document.getElementById('selectVersion').value = selectVersionString;
		document.getElementById('actionForm').submit(); 
	}
	function changeAll(){
		var selectAll = document.getElementById("selectAll");
		checkBoxArray = document.getElementsByName("wfVersionCheckBox");
		if(selectAll.checked){
			for (var i=0; i<checkBoxArray.length; i++){
               checkBoxArray[i].checked = true;
        	}  
		}else{
			for (var i=0; i<checkBoxArray.length; i++){
               checkBoxArray[i].checked = false;
        	}  
		}
	}
</script>
</head>

<body>
<s:hidden name="baseCode" id="baseCode"></s:hidden>
<div class='scroll_div' id='center'>
	<table id='tab' class='tableborder'>

				<tr>
					<th>
						全选
						<input type="checkbox" name="selectAll" id="selectAll" onclick="changeAll()" />
					</th>
					<th>
						<!-- 版本标识 -->
						<eoms:lable name="wf_version_code" />
					</th>
					<th>
						<!-- 流程类型 -->
						<eoms:lable name="wf_version_subflag" />
					</th>
					<th>
						<!-- 是否启用 -->
						<eoms:lable name="wf_ispublish" />
					</th>
				</tr>
			<c:forEach var="WfVersion" items="${versionList}">
			<tr>
		        <td>
					<input type="checkbox" name="wfVersionCheckBox" id="${WfVersion.code}" />
				</td>
		        <td>${WfVersion.code}</td>
		        <td>
		         <c:choose>
				        <c:when test="${WfVersion.subflag == '0'}">
				        	<eoms:lable name="wf_version_main" />
				        </c:when>
				        <c:when test="${WfVersion.subflag == '1'}">
				        	<eoms:lable name="wf_version_child" />
				        </c:when>
				  </c:choose>
		        </td>
		        <td>
					 <c:choose>
				        <c:when test="${WfVersion.isPublish == '0'}">
				        	<eoms:lable name="wf_version_stop" />
				        </c:when>
				        <c:when test="${WfVersion.isPublish == '1'}">
				        	<eoms:lable name="wf_version_start" />
				        </c:when>
				  	</c:choose>
		        </td>
		    </tr>
		    </c:forEach>
	</table>
	</div>
	<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="saveSelectVersion()" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
	</div>
	<form action="saveSelectVersion.action" name="actionForm" method="post" id="actionForm">
		<input type="hidden" id = "selectVersion" name = "selectVersion"/>
		<input type="hidden" id = "baseSchema" name="baseSchema" value= "${baseSchema}"/>
	</form>	
</body>
</html>
