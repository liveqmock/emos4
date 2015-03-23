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
	    check();
	    
	    initVisiable();
	    initNeedSave();
	    
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('timeField.id').value;
	    var baseSchema =  document.getElementById('timeField.baseSchema').value;
	    var fieldName =  document.getElementById('timeField.fieldName').value;
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
	
	function checkboxs(checked){
	    if(checked) {
	      document.getElementById('timeField.visiable').value = '1';
	    } else {
	      document.getElementById('timeField.visiable').value = '0';
	    }
	    
	}
	function checkboxsave(checked){
	    if(checked) {
	      document.getElementById('timeField.needSave').value = '1';
	    } else {
	      document.getElementById('timeField.needSave').value = '0';
	    }
	}
	
	function initVisiable(){
		var visiable = document.getElementById('visiable');
		if('${timeField.id}'==''){
			visiable.checked = true;
			document.getElementById('timeField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('timeField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('timeField.visiable').value = visiable.checked?'1':'0';
	}
	function initNeedSave(){
		var needSave = document.getElementById('needSave');
		if('${timeField.id}'==''){
			needSave.checked = true;
			document.getElementById('timeField.needSave').value='1';
		}else{
			needSave.checked = document.getElementById('timeField.needSave').value=='1'?true:false;
		}
	}
	function changeNeedSave(){
		var needSave = document.getElementById('needSave');
		document.getElementById('timeField.needSave').value = needSave.checked?'1':'0';
	}
	
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_datetext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="fieldType" name="fieldType" value="${fieldType}"/>
		          <input type="hidden" id="timeField.hasDeploy" name="timeField.hasDeploy" value="${timeField.hasDeploy}"/>
		          <input type="hidden" id="timeField.operate" name="timeField.operate" value="${timeField.operate}"/>
		          <input type="hidden" id="timeField.id" name="timeField.id"  class="textInput" value="${timeField.id}"/><!--主键ID-->
		          <input type="hidden" id="timeField.baseSchema" name="timeField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		  		  <input type="hidden" name="timeField.length" id="timeField.length" class="textInput" value="15" maxlength="10"/>
									
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="timeField.label" id="timeField.label" class="textInput" value="${timeField.label}" maxlength="50"/>
									<validation id="timeField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${timeField.hasDeploy == '0'&& timeField.operate == 'add'}">
								         <input type="text" name="timeField.fieldName" id="timeField.fieldName" class="textInput" value="${timeField.fieldName}" maxlength="50"/>
								         <validation id="timeField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
								   </c:if>
								   <c:if test="${timeField.hasDeploy != '0'|| timeField.operate != 'add'}">
								       ${timeField.fieldName}
								        <input type="hidden" id="timeField.fieldName" name="timeField.fieldName" value="${timeField.fieldName}"/>
								    </c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>
										<input type="hidden" id="timeField.visiable" name="timeField.visiable" value="${timeField.visiable}"/>
										<input type="checkbox" id="visiable" name="visiable"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="timeField.orderNum" id="timeField.orderNum" class="textInput" value="${timeField.orderNum}" maxlength="10"/>
									<validation id="timeField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_additional_container"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${timeField.parentID == 'action'}">
									<input type="hidden" id="timeField.parentID" name="timeField.parentID" value="${timeField.parentID}"/>																																				
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${timeField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="timeField.parentID" id="timeField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										 <eoms:lable name="bpp_develop_worksheet_additional_nocontainer"/>
										<validation id="timeField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="timeField.parentID" id="timeField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_needsave"/>：<span class="must">*</span>
									</td>
									<td>
								    <input type="hidden" id="timeField.needSave" name="timeField.needSave" value="${timeField.needSave}"/>
									<input type="checkbox" id="needSave" name="needSave"  onclick="changeNeedSave()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_defaultvalue"/>：
									</td>
									<td>
									<input type="text" name="timeField.defaultValue" id="timeField.defaultValue" class="textInput" value="${timeField.defaultValue}" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="timeField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="timeField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_timeformat"/>：<span class="must">*</span>
									</td>
									<td>
									
									<input type="text" name="timeField.timeFormat" id="timeField.timeFormat" class="textInput" value="<c:if test="${timeField.timeFormat!=''}">${timeField.timeFormat}</c:if><c:if test="${timeField.timeFormat==null}">yyyy-MM-dd HH:mm:ss</c:if>"  maxlength="50"/>
									<validation id="timeField.timeFormatV" require="true" dataType="Require" Max="100" msg="日期格式不能为空，长度不能超过50" />
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