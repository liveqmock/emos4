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
        
	    //初始化checkbox
	    initVisiable();
	    initNeedSave();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('collectField.id').value;
	    var baseSchema =  document.getElementById('collectField.baseSchema').value;
	    var fieldName =  document.getElementById('collectField.fieldName').value;
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
		if('${collectField.id}'==''){
			visiable.checked = true;
			document.getElementById('collectField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('collectField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('collectField.visiable').value = visiable.checked?'1':'0';
	}
	function initNeedSave(){
		var needSave = document.getElementById('needSave');
		if('${collectField.id}'==''){
			needSave.checked = true;
			document.getElementById('collectField.needSave').value='1';
		}else{
			needSave.checked = document.getElementById('collectField.needSave').value=='1'?true:false;
		}
	}
	function changeNeedSave(){
		var needSave = document.getElementById('needSave');
		document.getElementById('collectField.needSave').value = needSave.checked?'1':'0';
	}
	//点击数据源文本框弹出页面
	function openResourcePage(){
		var obj = document.getElementById('collectField_type');
		var value = obj.value;
		if(value=='sysdic'){
			var src = '${ctx}/ultrabpp/develop/component/selectfield/dictree.jsp';
		    window.open(src,'','width=400px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}else if(value == 'table'){
			var resValue =  document.getElementById("collectField.typeResource").value;
			var src = '${ctx}/ultrabpp/develop/component/selectfield/tableinfo.jsp?resValue='+resValue;
		    window.open(src,'','width=300px,height=150px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
	}
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							选择框详细信息
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="collectField.operate" name="collectField.operate" value="${collectField.operate}"/>
		          <input type="hidden" id="collectField.hasDeploy" name="collectField.hasDeploy" value="${collectField.hasDeploy}"/>
		          <input type="hidden" id="collectField.id" name="collectField.id"  class="textInput" value="${collectField.id}"/>
		          <input type="hidden" id="collectField.baseSchema" name="collectField.baseSchema"  class="textInput"  value="${baseSchema}"/>
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="collectField.label" id="collectField.label" class="textInput" value="${collectField.label}" maxlength="50"/>
									<validation id="collectField.labelV" require="true" dataType="Require" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${collectField.hasDeploy == '0'&& collectField.operate == 'add'}">
								         <input type="text" name="collectField.fieldName" id="collectField.fieldName" class="textInput" value="${collectField.fieldName}" maxlength="50"/>
								         <validation id="collectField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
								   	</c:if>
								   	<c:if test="${collectField.hasDeploy != '0'|| collectField.operate != 'add'}">
								       ${collectField.fieldName}
								        <input type="hidden" id="collectField.fieldName" name="collectField.fieldName" value="${collectField.fieldName}"/>
								    </c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="hidden" id="collectField.visiable" name="collectField.visiable" value="${collectField.visiable}"/>
									<input type="checkbox" id="visiable" name="visiable"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="collectField.orderNum" id="collectField.orderNum" class="textInput" value="${collectField.orderNum}" maxlength="10"/>
									<validation id="collectField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字,不能为空" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${collectField.parentID == 'action'}">
									<input type="hidden" id="collectField.parentID" name="collectField.parentID" value="${collectField.parentID}"/>																											
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${collectField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="collectField.parentID" id="collectField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										<validation id="collectField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="collectField.parentID" id="collectField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_needsave"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="hidden" id="collectField.needSave" name="collectField.needSave" value="${collectField.needSave}" />
									<input type="checkbox" id="needSave" name="needSave"  onclick="changeNeedSave()" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_defaultvalue"/>：
									</td>
									<td>
									<input type="text" name="collectField.defaultValue" id="collectField.defaultValue" class="textInput" value="${collectField.defaultValue}" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_length"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="collectField.length" id="collectField.length" class="textInput" value="${collectField.length}" maxlength="10"/>
									<validation id="collectField.lengthV" require="Custom" dataType="Integer" msg="最大长度为数字,不能为空" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="collectField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="collectField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="collectField.rowspan" id="collectField.rowspan" class="textInput" value="${collectField.rowspan}" maxlength="10"/>
									<validation id="collectField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									数据源类型：<span class="must">*</span>
									</td>
									<td>
									<s:select name="collectField.type" cssClass="select" cssStyle="width:98%" list="#{'sysdic':'sysdic','collect':'collect','table':'table'}"   listKey="key" listValue="value"	/>
									<validation id="collectField.typeV" require="true" dataType="Require" Max="100" msg="数据源类型不能为空，长度不能超过50" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									数据源：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="collectField.typeResource" id="collectField.typeResource" class="textInput" onclick="openResourcePage()" value="${collectField.typeResource}"/>
									<validation id="collectField.typeResourceV" require="true" dataType="Require" Max="100" msg="数据源不能为空，长度不能超过50" />
									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									查询参数：
									</td>
									<td>
									<input type="text" name="collectField.paras" id="collectField.paras" class="textInput" value="${collectField.paras}" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									显示类型：<span class="must">*</span>
									</td>
									<td>
									<s:select name="collectField.showType" id="collectField.showType" cssClass="select" cssStyle="width:98%" list="#{'radio':'单选框','checkbox':'复选框'}"   listKey="key" listValue="value"	/>
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