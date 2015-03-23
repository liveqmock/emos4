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
	    
	    initVisiable();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	    
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('panelField.id').value;
	    var baseSchema =  document.getElementById('panelField.baseSchema').value;
	    var fieldName =  document.getElementById('panelField.fieldName').value;
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
		if('${panelField.id}'==''){
			visiable.checked = true;
			document.getElementById('panelField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('panelField.visiable').value=='1'?true:false;
		}
	}
	
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('panelField.visiable').value = visiable.checked?'1':'0';
	}
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_containertext"/><!--容器字段详细信息 -->
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="panelField.operate" name="panelField.operate" value="${panelField.operate}"/>
		          <input type="hidden" id="panelField.hasDeploy" name="panelField.hasDeploy" value="${panelField.hasDeploy}"/>
		          <input type="hidden" id="panelField.id" name="panelField.id"  class="textInput" value="${panelField.id}"/><!--主键ID-->
		          <input type="hidden" id="panelField.baseSchema" name="panelField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="panelField.label" id="panelField.label" class="textInput" value="${panelField.label}" maxlength="50"/>
									<validation id="panelField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${panelField.hasDeploy == '0'&& panelField.operate == 'add'}">
									    <input type="text" name="panelField.fieldName" id="panelField.fieldName" class="textInput" value="${panelField.fieldName}" maxlength="50"/>
									    <validation id="panelField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
									</c:if>
									<c:if test="${panelField.hasDeploy != '0'|| panelField.operate != 'add'}">
									    ${panelField.fieldName}
									        <input type="hidden" id="panelField.fieldName" name="panelField.fieldName" value="${panelField.fieldName}"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="hidden" id="panelField.visiable" name="panelField.visiable" value="${panelField.visiable}"/>
									<input type="checkbox" id="visiable" name="visiable"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="panelField.orderNum" id="panelField.orderNum" class="textInput" value="${panelField.orderNum}" maxlength="10"/>
									<validation id="panelField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器组：<span class="must">*</span>
									</td>
									<td>
										<s:select name="panelField.parentID" id="panelField.parentID" cssStyle="width:98%" cssClass="select" list="panelGroupFieldList"   listKey="id" listValue="label"	/>						
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