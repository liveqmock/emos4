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
	    initNeedSave();
	    initIsClob();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	
	function init(){
		if('${characterField.id}'==''){
			document.getElementById('characterField.rowspan').value='1';
		}
	}
	
	function formSubmit(){
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('characterField.id').value;
	    var baseSchema =  document.getElementById('characterField.baseSchema').value;
	    var fieldName =  document.getElementById('characterField.fieldName').value;
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


	function initVisiable(){
		var visiable = document.getElementById('visiable');
		if('${characterField.id}'==''){
			visiable.checked = true;
			document.getElementById('characterField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('characterField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('characterField.visiable').value = visiable.checked?'1':'0';
	}
	function initNeedSave(){
		var needSave = document.getElementById('needSave');
		if('${characterField.id}'==''){
			needSave.checked = true;
			document.getElementById('characterField.needSave').value='1';
		}else{
			needSave.checked = document.getElementById('characterField.needSave').value=='1'?true:false;
		}
	}
	function changeNeedSave(){
		var needSave = document.getElementById('needSave');
		document.getElementById('characterField.needSave').value = needSave.checked?'1':'0';
	}
	
	function initIsClob(){
		var isClob = document.getElementById('isClob');
		if('${characterField.id}'==''){
			isClob.checked = false;
			document.getElementById('characterField.isClob').value='0';
		}else{
			isClob.checked = document.getElementById('characterField.isClob').value=='1'?true:false;
		}
	}
	function changeIsClob(){
		var isClob = document.getElementById('isClob');
		document.getElementById('characterField.isClob').value = isClob.checked?'1':'0';
	}
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_charactertext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
                  <input type="hidden" id="characterField.operate" name="characterField.operate" value="${characterField.operate}"/>
		          <input type="hidden" id="characterField.hasDeploy" name="characterField.hasDeploy" value="${characterField.hasDeploy}"/>
		          <input type="hidden" id="characterField.id" name="characterField.id"  class="textInput" value="${characterField.id}"/><!--主键ID-->
		          <input type="hidden" id="characterField.baseSchema" name="characterField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		           <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="characterField.label" id="characterField.label" class="textInput" value="${characterField.label}" maxlength="50"/>
									<validation id="characterField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${characterField.hasDeploy == '0'&& characterField.operate == 'add'}">
									    <input type="text" name="characterField.fieldName" id="characterField.fieldName" class="textInput" value="${characterField.fieldName}" maxlength="50"/>
									    <validation id="characterField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
									</c:if>
									<c:if test="${characterField.hasDeploy != '0'|| characterField.operate != 'add'}">
									    ${characterField.fieldName}
									        <input type="hidden" id="characterField.fieldName" name="characterField.fieldName" value="${characterField.fieldName}"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>	
									<input type="hidden" id="characterField.visiable" name="characterField.visiable" value="${characterField.visiable}"/>
									<input type="checkbox" id="visiable" name="visiable"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="characterField.orderNum" id="characterField.orderNum" class="textInput" value="${characterField.orderNum}" maxlength="10"/>
									<validation id="characterField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${characterField.parentID == 'action'}">
									<input type="hidden" id="characterField.parentID" name="characterField.parentID" value="${characterField.parentID}"/>
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${characterField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="characterField.parentID" id="characterField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										<validation id="characterField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="characterField.parentID" id="characterField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_needsave"/>:<span class="must">*</span>
									</td>
									<td>
									<input type="hidden" id="characterField.needSave" name="characterField.needSave" value="${characterField.needSave}"/>
									<input type="checkbox" id="needSave" name="needSave"  onclick="changeNeedSave()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									CLOB:<span class="must">*</span>
									</td>
									<td>
									<input type="hidden" id="characterField.isClob" name="characterField.isClob" value="${characterField.isClob}"/>
									<input type="checkbox" id="isClob" name="isClob"  onclick="changeIsClob()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_length"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="characterField.length" id="characterField.length" class="textInput" value="${characterField.length}" maxlength="10"/>
									<validation id="characterField.lengthV" require="Custom" dataType="Integer" msg="最大长度为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="characterField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="characterField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="characterField.rowspan" id="characterField.rowspan" class="textInput" value="${characterField.rowspan}" maxlength="10"/>
									<validation id="characterField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_defaultvalue"/>：
									</td>
									<td>
									<input type="text" name="characterField.defaultValue" id="characterField.defaultValue" class="textInput" value="${characterField.defaultValue}" maxlength="50"/>
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