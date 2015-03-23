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
	    var fieldID =  document.getElementById('labelField.id').value;
	    var baseSchema =  document.getElementById('labelField.baseSchema').value;
	    var fieldName =  document.getElementById('labelField.fieldName').value;
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
		if('${hiddenField.id}'==''){
			document.getElementById('labelField.rowspan').value='1';
		}
	}
	
	function initVisiable(){
		var visiable = document.getElementById('visiable');
		if('${labelField.id}'==''){
			visiable.checked = true;
			document.getElementById('labelField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('labelField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('labelField.visiable').value = visiable.checked?'1':'0';
	}
	
	
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_labeltext"/><!--文本详细信息  -->
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="labelField.operate" name="labelField.operate" value="${labelField.operate}"/>
		          <input type="hidden" id="labelField.hasDeploy" name="labelField.hasDeploy" value="${labelField.hasDeploy}"/>
		          <input type="hidden" id="labelField.id" name="labelField.id"  class="textInput" value="${labelField.id}"/><!--主键ID-->
		          <input type="hidden" id="labelField.baseSchema" name="labelField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="labelField.label" id="labelField.label" class="textInput" value="${labelField.label}" maxlength="50"/>
									<validation id="labelField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${labelField.hasDeploy == '0'&& labelField.operate == 'add'}">
									    <input type="text" name="labelField.fieldName" id="labelField.fieldName" class="textInput" value="${labelField.fieldName}" maxlength="50"/>
									    <validation id="labelField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
									</c:if>
									<c:if test="${labelField.hasDeploy != '0'|| labelField.operate != 'add'}">
									    ${labelField.fieldName}
									        <input type="hidden" id="labelField.fieldName" name="labelField.fieldName" value="${labelField.fieldName}"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>
											<input type="hidden" id="labelField.visiable" name="labelField.visiable" value="${labelField.visiable}"/>
											<input type="checkbox" id="visiable" name="visiable"   onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${labelField.parentID == 'action'}">
									<input type="hidden" id="labelField.parentID" name="labelField.parentID" value="${labelField.parentID}"/>									
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${labelField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="labelField.parentID" id="labelField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										 <eoms:lable name="bpp_develop_worksheet_additional_nocontainer"/>
										<validation id="labelField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="labelField.parentID" id="labelField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="labelField.orderNum" id="labelField.orderNum" class="textInput" value="${labelField.orderNum}" maxlength="10"/>
									<validation id="labelField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="labelField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="labelField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="labelField.rowspan" id="labelField.rowspan" class="textInput" value="${labelField.rowspan}" maxlength="10"/>
									<validation id="labelField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									样式：
									</td>
									<td>
									<input type="text" name="labelField.cssName" id="labelField.cssName" class="textInput" value="${labelField.cssName}" maxlength="10"/>
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