<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,30);
	}
	
	window.onload = function() 
	{
		setCenter(0,30);changeRow_color("tab");
		var sign = '${sign}';
		if(sign == 'true'){
			var pid = '${wfSort.pid}'
			parent.document.getElementById('wfSortLeft').contentWindow.refresh(pid);
			window.close();
		}
	}
	
	function comfirmDelRelation(userid)
	{
		if(confirm("<eoms:lable name='com_btn_confirm_del'/>！"))
		{
			window.location.href('${ctx}/depUserRelation/delDepUser.action?depid=${depid}&userid='+userid);
		}
	}
			
	
	//添加流程分类或流程类型	
	function add(type,addType){
		var sortId = '${wfSortId}';
		var src = '';
		if(type == 'sort'){
			src = '${ctx}/wfsort/toEditOrAddWfSort.action?wfSortPid=' + sortId + '&type=' + addType;
			window.open(src,'','width=600px,height=230px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}else{
			src = '${ctx}/wfsort/toEditOrAddWfType.action?wfSortId=' + sortId;
			window.open(src,'','width=600px,height=520px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
	}
	
	
		//修改流程分类
	function editSort(){
		var sortId = '${wfSortId}';
		var src = '${ctx}/wfsort/toEditOrAddWfSort.action?wfSortId='+sortId;
		window.open(src,'','width=600,height=230,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	
		//修改流程类型
	function editType(wfTypeId){
		var src = '${ctx}/wfsort/toEditOrAddWfType.action?wfTypeId='+wfTypeId;
		window.open(src,'','width=600px,height=520px,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
		//删除流程分类
	function delSort(){
		var sortId = '${wfSortId}';
		if('000000000000011'==sortId)
		{
			alert('EOMS工单为类型根节点，不允许删除。');
		}else{
			//验证该流程分类下级是否有子节点，如果存在不允许删除流程分类
			$.get("${ctx}/wfsort/checkDelWfSort.action?stamp="+new Date().getTime(),{'wfSortId':sortId},function(result)
			{
				if(result == 'false'){
					alert('该流程分类下级存在子节点，请删除下级流程分类或者流程类型后，再进行删除操作。');
				}else{
					if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && sortId != '') 
					{
						var src = '${ctx}/wfsort/delWfSort.action?wfSortId='+sortId;
						window.open(src,'_self','width=400,height=290,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
					}
				}
			});
		}
		
	}
	
	
		//删除流程类型
	function delType(typeId){
		if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && typeId != '') {
			var wfSort = document.getElementById('wfSort');
			wfSort.name = 'wfTypeId';
			wfSort.value = typeId;
			document.getElementById('sortForm').action = '${ctx}/wfsort/delWfType.action';
			document.getElementById('sortForm').submit();
		}
	}
	
</script>

</head>
<body>

  <dg:datagrid  var="wfType" items="${wfTypeList}">
  		<dg:lefttoolbar>

		<c:if test="${wfSortId != '0'}"> 
			<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'"
				onmouseout="this.className='page_sameadd_button'" onclick="add('sort','current')" text="sm_btn_currentadd" />
		</c:if>
				
		<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'"
			onmouseout="this.className='page_loweradd_button'" onclick="add('sort','lower')" text="sm_btn_loweradd" />


	  	<c:if test="${wfSortId != '0'}">
	  			   
	  		<eoms:operate cssclass="page_edit_button" id="wf_type_add" onmouseover="this.className='page_edit_button_hover'" 
	  			  onmouseout="this.className='page_edit_button'"    onclick="editSort();"  text="com_btn_edit" />
					
	  		<eoms:operate cssclass="page_del_button" id="wf_sort_del" onmouseover="this.className='page_del_button_hover'" 
				  onmouseout="this.className='page_del_button'"    onclick="delSort();"  text="wf_sort_del" />
	  			  
		  	<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'"
					onmouseout="this.className='page_sameadd_button'" onclick="add('type','')" text="wf_type_add" />
	  	</c:if>
	  		 
  		</dg:lefttoolbar>
  		<dg:condition>
  		<div align="center">
  			<table class="serachdivTable" width="80%">
				<tr>
					<td class="texttd" width="20%"><eoms:lable name="sm_lb_userName"/>：</td>
					<td width="35%"><input type="text" name="" class="textInput" value="" /></td>
					<td class="texttd" width="10%"><eoms:lable name="sm_lb_fullName"/>：</td>
					<td width="35%"><input type="text" name="" class="textInput" value="" /></td>
					<td width="10%"><input type="button" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" 
					    onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
					</td>
				</tr>
			</table>
			</div>
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
				<th><eoms:lable name="wf_type_code"/></th>
				<th><eoms:lable name="wf_type_name"/></th>
				<th><eoms:lable name="wf_type_wftype"/></th>
				<th><eoms:lable name="wf_type_entrycount"/></th>
				<th><eoms:lable name="wf_type_todayentrycount"/></th>
				<th><eoms:lable name="wf_type_wfcounttype"/></th>
				<th><eoms:lable name="wf_type_wfdefaultversion"/></th>
				<th style="width:55px;"><eoms:lable name="wf_control"/></th>
	        </tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${wfType_row}">
				<td><a href="javascript:;" onclick="editType('${wfType.id}');">${wfType.code}</a></td>
				<td><a href="javascript:;"  onclick="editType('${wfType.id}');">${wfType.name}</a></td>
				<td><a href="javascript:;"  onclick="editType('${wfType.id}');">
					<c:if test="${wfType.wfType == '0'}">
						<eoms:lable name="wf_type_free"/>
					</c:if>
					<c:if test="${wfType.wfType == '1'}">
						<eoms:lable name="wf_type_fix"/>
					</c:if>
					</a>
				</td>
				<td><a href="javascript:;"  onclick="editType('${wfType.id}');">${wfType.entryCount}</a></td>
				<td><a href="javascript:;"  onclick="editType('${wfType.id}');">${wfType.todayEntryCount}</a></td>
				<td><a href="javascript:;" onclick="editType('${wfType.id}');">
					<c:if test="${wfType.wfCountType == 'Y'}">
						<eoms:lable name="wf_type_year"/>
					</c:if>
					<c:if test="${wfType.wfCountType == 'M'}">
						<eoms:lable name="wf_type_mon"/>
					</c:if>
					<c:if test="${wfType.wfCountType == 'D'}">
						<eoms:lable name="wf_type_day"/>
					</c:if>
					</a>
				</td>
				<td><a href="javascript:;"  onclick="editType('${wfType.id}');">${wfType.wfDefaultVersion}</a></td>
				<td><a href="javascript:;" onclick="delType('${wfType.id }');"><eoms:lable name="com_btn_delete"/></a></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
	
	<s:form name="sortForm" id="sortForm">
		<input type="hidden" id="wfSort" />
		<input type="hidden" id="wfSortId" name="wfSortId"  value='${wfSortId}' />
	</s:form>
	
</body>
</html>
