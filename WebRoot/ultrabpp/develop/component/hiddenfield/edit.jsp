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
	    initNeedSave();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	
	function init(){
		if('${hiddenField.id}'==''){
			document.getElementById('hiddenField.rowspan').value='1';
		}
	}
	
	function formSubmit(){
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('hiddenField.id').value;
	    var baseSchema =  document.getElementById('hiddenField.baseSchema').value;
	    var fieldName =  document.getElementById('hiddenField.fieldName').value;
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

	function initNeedSave(){
		var needSave = document.getElementById('needSave');
		if('${hiddenField.id}'==''){
			needSave.checked = true;
			document.getElementById('hiddenField.needSave').value='1';
		}else{
			needSave.checked = document.getElementById('hiddenField.needSave').value=='1'?true:false;
		}
	}
	function changeNeedSave(){
		var needSave = document.getElementById('needSave');
		document.getElementById('hiddenField.needSave').value = needSave.checked?'1':'0';
	}
	
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							隐藏域
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
                  <input type="hidden" id="hiddenField.operate" name="hiddenField.operate" value="${hiddenField.operate}"/>
		          <input type="hidden" id="hiddenField.hasDeploy" name="hiddenField.hasDeploy" value="${hiddenField.hasDeploy}"/>
		          <input type="hidden" id="hiddenField.id" name="hiddenField.id"  class="textInput" value="${hiddenField.id}"/><!--主键ID-->
		          <input type="hidden" id="hiddenField.baseSchema" name="hiddenField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		       	  <input type="hidden" id="hiddenField.visiable" name="hiddenField.visiable" value="0"/>
									
		        	<div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="hiddenField.label" id="hiddenField.label" class="textInput" value="${hiddenField.label}" maxlength="50"/>
									<validation id="hiddenField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${hiddenField.hasDeploy == '0'&& hiddenField.operate == 'add'}">
									    <input type="text" name="hiddenField.fieldName" id="hiddenField.fieldName" class="textInput" value="${hiddenField.fieldName}" maxlength="50"/>
									    <validation id="hiddenField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
									</c:if>
									<c:if test="${hiddenField.hasDeploy != '0'|| hiddenField.operate != 'add'}">
									    ${hiddenField.fieldName}
									        <input type="hidden" id="hiddenField.fieldName" name="hiddenField.fieldName" value="${hiddenField.fieldName}"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="hiddenField.orderNum" id="hiddenField.orderNum" class="textInput" value="${hiddenField.orderNum}" maxlength="10"/>
									<validation id="hiddenField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${hiddenField.parentID == 'action'}">
									<input type="hidden" id="hiddenField.parentID" name="hiddenField.parentID" value="${hiddenField.parentID}"/>
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${hiddenField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="hiddenField.parentID" id="hiddenField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										<validation id="hiddenField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="hiddenField.parentID" id="hiddenField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="bpp_develop_worksheet_needsave"/>:
									</td>
									<td>
										<input type="hidden" id="hiddenField.needSave" name="hiddenField.needSave" value="${hiddenField.needSave}"/>
										<input type="checkbox" id="needSave" name="needSave"  onclick="changeNeedSave()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_length"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="hiddenField.length" id="hiddenField.length" class="textInput" value="${hiddenField.length}" maxlength="10"/>
									<validation id="hiddenField.lengthV" require="Custom" dataType="Integer" msg="最大长度为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="hiddenField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="hiddenField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="hiddenField.rowspan" id="hiddenField.rowspan" class="textInput" value="${hiddenField.rowspan}" maxlength="10"/>
									<validation id="hiddenField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_defaultvalue"/>：
									</td>
									<td>
									<input type="text" name="hiddenField.defaultValue" id="hiddenField.defaultValue" class="textInput" value="${hiddenField.defaultValue}" maxlength="50"/>
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