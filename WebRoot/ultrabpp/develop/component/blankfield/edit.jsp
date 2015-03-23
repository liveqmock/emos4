<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	<title>
	</title>
	
<script language="javascript">
    window.onload = function(){
        setCenter(0,61); 
        
        init();
	    //初始化checkbox
	    initVisiable();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('blankField.id').value;
	    var baseSchema =  document.getElementById('blankField.baseSchema').value;
	    var fieldName =  document.getElementById('blankField.fieldName').value;
	     $.get("${ctx}/ultrabpp/develop/worksheetFieldNameCheckUnique.action?stamp="+new Date().getTime(),{'baseSchema':baseSchema,'fieldName':fieldName,'fieldID':fieldID},function(result)
					{
						if(result == 'false'){
							alert('组件英文名称重复已经存在！已经存在英文名称为：'+fieldName+'的组件了！');
						}else{
							var s=document.getElementById('fieldForm').submit(); 
	         			
						}
					})
	    }
	}

	function init(){
		if('${blankField.id}'==''){
			document.getElementById('blankField.rowspan').value='1';
		}
	}
	function initVisiable(){
		var visiable = document.getElementById('visiable');
		if('${blankField.id}'==''){
			visiable.checked = true;
			document.getElementById('blankField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('blankField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('blankField.visiable').value = visiable.checked?'1':'0';
	}
	
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_occupyingtext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="blankField.operate" name="blankField.operate" value="${blankField.operate}"/>
		          <input type="hidden" id="blankField.hasDeploy" name="blankField.hasDeploy" value="${blankField.hasDeploy}"/>
		          <input type="hidden" id="blankField.id" name="blankField.id"  class="textInput" value="${blankField.id}"/><!--主键ID-->
		          <input type="hidden" id="blankField.baseSchema" name="blankField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		          <input type="hidden" id="blankField.label" name="blankField.label"  class="textInput"  value="占位格"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${blankField.hasDeploy == '0'&& blankField.operate == 'add'}">
									    <input type="text" name="blankField.fieldName" id="blankField.fieldName" class="textInput" value="${blankField.fieldName}" maxlength="50"/>
									    <validation id="blankField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
									</c:if>
									<c:if test="${blankField.hasDeploy != '0'|| blankField.operate != 'add'}">
									    ${blankField.fieldName}
									        <input type="hidden" id="blankField.fieldName" name="blankField.fieldName" value="${blankField.fieldName}"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>	
									<input type="hidden" id="blankField.visiable" name="blankField.visiable" value="${blankField.visiable}"/>
									<input type="checkbox" id="visiable" name="visiable"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${blankField.parentID == 'action'}">
									<input type="hidden" id="blankField.parentID" name="blankField.parentID"  class="textInput" value="${blankField.parentID}"/>									
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${blankField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="blankField.parentID" id="blankField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										 <eoms:lable name="bpp_develop_worksheet_additional_nocontainer"/>
										<validation id="blankField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="blankField.parentID" id="blankField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="blankField.orderNum" id="blankField.orderNum" class="textInput" value="${blankField.orderNum}" maxlength="10"/>
									<validation id="blankField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="blankField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="blankField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="blankField.rowspan" id="blankField.rowspan" class="textInput" value="${blankField.rowspan}" maxlength="10"/>
									<validation id="blankField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
									</td>
								</tr>
							 </table>
						</div>
						   
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="formSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
		   </form>	
	</div>
</body>
</html>