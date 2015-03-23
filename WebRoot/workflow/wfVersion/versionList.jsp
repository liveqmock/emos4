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
	  setCenter(0,56);
	}
	window.onload = function() 
	{
	  setCenter(0,56);changeRow_color("tab");
	}
</script>
</head>

<body>
<s:hidden name="baseCode" id="baseCode"></s:hidden>
<dg:datagrid  var="WfVersion" items="${versionList}" title="${nodePath}">
  		<dg:lefttoolbar>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"/><eoms:lable name="wf_model_reflash"/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="addFlow()"/><eoms:lable name="wf_model_add1"></eoms:lable></li>
	     <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"/><eoms:lable name="wf_model_help"></eoms:lable></li>
  		</dg:lefttoolbar>
  		
    	<dg:gridtitle>
				<tr>
					<th>
						<!-- 是否启用 -->
						<eoms:lable name="wf_ispublish" />
					</th>
					<th>
						<!-- 流程名称 -->
						<eoms:lable name="wf_version_baseName" />
					</th>
					<th>
						<!-- 流程标识 -->
						<eoms:lable name="wf_version_baseCode" />
					</th>
					<th>
						<!-- 版本标识 -->
						<eoms:lable name="wf_version_code" />
					</th>
					<th>
						<!-- 版本名称 -->
						<eoms:lable name="wf_version_name" />
					</th>
					<th>
						<!-- 实体数量 -->
						<eoms:lable name="wf_version_entryCount" />
					</th>
					<th>
						<!-- 创建时间 -->
						<eoms:lable name="wf_version_createTime" />
					</th>
					<th>
						<!-- 流程类型 -->
						<eoms:lable name="wf_version_subflag" />
					</th>
					<th width="70">
						<!-- 操作-->
						<eoms:lable name="wf_control" />
					</th>
				</tr>
			</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${WfVersion_row}" style="cursor:hand;">
		        <td width="7%" >
					 <c:choose>
				        <c:when test="${WfVersion.isPublish == '0'}">
				        	<eoms:lable name="wf_version_stop" />
				        </c:when>
				        <c:when test="${WfVersion.isPublish == '1'}">
				        	<eoms:lable name="wf_version_start" />
				        </c:when>
				  </c:choose>
		        </td>
		        <td width="10%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');">${WfVersion.baseName}</td>
		        <td width="12%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');">${WfVersion.baseCode}</td>
		        <td width="21%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');" >${WfVersion.code}</td>
		        <td width="14%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');">${WfVersion.name}</td>
		        <td width="5%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');">${WfVersion.entryCount}</td>
		        <td width="10%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');" ><eoms:date value="${WfVersion.createTime}"/></td>
		        <td width="7%" onclick="editFlow('${WfVersion.code}', '${WfVersion.entryCount}');">
		         <c:choose>
				        <c:when test="${WfVersion.subflag == '0'}">
				        	<eoms:lable name="wf_version_main" />
				        </c:when>
				        <c:when test="${WfVersion.subflag == '1'}">
				        	<eoms:lable name="wf_version_child" />
				        </c:when>
				  </c:choose>
		        </td>
		        <td width="7%">
			        <c:choose>
				        <c:when test="${WfVersion.isPublish == '0'}">
			        		<img src="../workflow/wfVersion/images/start.gif" name="enableButton"  title="<eoms:lable name='wf_version_start'/>" id="start${WfVersion.code}" onclick="startFlow('${WfVersion.id}')"/>
			        	</c:when>
			        	<c:when test="${WfVersion.isPublish == '1'}">
							<img src="../workflow/wfVersion/images/stop.gif" title="<eoms:lable name='wf_version_stop'/>" name="outageButton" id="stop${WfVersion.code}" onclick="stopFlow('${WfVersion.id}')" />
						</c:when>
					</c:choose>
					<img src="../workflow/wfVersion/images/del.png" title="<eoms:lable name='wf_version_delete'/>" name="outageButton" onclick="delFlow('${WfVersion.entryCount}','${WfVersion.id}','${WfVersion.isPublish}')" />
				</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	
	<form id="filedform" target="_self">
		<s:hidden name="baseCode" />
		<input type="hidden" name="wfVersionId" id="wfVersionId" />
	</form>
  </body>
</html>
