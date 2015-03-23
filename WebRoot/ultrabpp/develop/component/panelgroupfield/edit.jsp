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
	    initTitleVisible();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	    
	
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('panelGroupField.id').value;
	    var baseSchema =  document.getElementById('panelGroupField.baseSchema').value;
	    var fieldName =  document.getElementById('panelGroupField.fieldName').value;
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
		if('${panelGroupField.id}'==''){
			visiable.checked = true;
			document.getElementById('panelGroupField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('panelGroupField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('panelGroupField.visiable').value = visiable.checked?'1':'0';
	}
	function initTitleVisible(){
		var titleVisible = document.getElementById('titleVisible');
		if('${panelGroupField.id}'==''){
			titleVisible.checked = true;
			document.getElementById('panelGroupField.titleVisible').value='1';
		}else{
			titleVisible.checked = document.getElementById('panelGroupField.titleVisible').value=='1'?true:false;
		}
	}
	function changeTitleVisible(){
		var titleVisible = document.getElementById('titleVisible');
		document.getElementById('panelGroupField.titleVisible').value = titleVisible.checked?'1':'0';
	}
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_containergrouptext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="panelGroupField.operate" name="panelGroupField.operate" value="${panelGroupField.operate}"/>
		          <input type="hidden" id="panelGroupField.hasDeploy" name="panelGroupField.hasDeploy" value="${panelGroupField.hasDeploy}"/>
		          <input type="hidden" id="panelGroupField.id" name="panelGroupField.id"  class="textInput" value="${panelGroupField.id}"/><!--主键ID-->
		          <input type="hidden" id="panelGroupField.baseSchema" name="panelGroupField.baseSchema"  class="textInput"  value="${panelGroupField.baseSchema}"/><!--工单类型-->
		          		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="panelGroupField.label" id="panelGroupField.label" class="textInput" value="${panelGroupField.label}" maxlength="50"/>
									<validation id="panelGroupField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${panelGroupField.hasDeploy == '0'&& panelGroupField.operate == 'add'}">
								         <input type="text" name="panelGroupField.fieldName" id="panelGroupField.fieldName" class="textInput" value="${panelGroupField.fieldName}" maxlength="50"/>
								         <validation id="panelGroupField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
	                                </c:if>
								    <c:if test="${panelGroupField.hasDeploy != '0'|| panelGroupField.operate != 'add'}">
								       ${panelGroupField.fieldName}
								        <input type="hidden" id="panelGroupField.fieldName" name="panelGroupField.fieldName" value="${panelGroupField.fieldName}"/>
								    </c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>
										<input type="hidden" id="panelGroupField.visiable" name="panelGroupField.visiable" value="${panelGroupField.visiable}"/>
										<input type="checkbox" id="visiable" name="visiable"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_titlevisible"/>：<span class="must">*</span>
									</td>
									<td>
										<input type="hidden" id="panelGroupField.titleVisible" name="panelGroupField.titleVisible" value="${panelGroupField.titleVisible}"/>
										<input type="checkbox" id="titleVisible" name="titleVisible"  onclick="changeTitleVisible()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="panelGroupField.orderNum" id="panelGroupField.orderNum" class="textInput" value="${panelGroupField.orderNum}" maxlength="10"/>
									<validation id="panelGroupField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									    <c:if test="${panelGroupField.parentID == 'main'}">
										   <eoms:lable name="bpp_develop_worksheet_additional_notupdate"/>
										</c:if>
									    <c:if test="${panelGroupField.parentID != 'main'}">
										    <c:if test="${ empty panelFieldList}">
												<s:select name="panelGroupField.parentID" id="panelGroupField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
												 <eoms:lable name="bpp_develop_worksheet_additional_nocontainer"/>
												<validation id="panelGroupField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
											</c:if>
											<c:if test="${!empty panelFieldList}">
											 	<s:select name="panelGroupField.parentID" id="panelGroupField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
											</c:if>
									    </c:if>
									</td>
								</tr>
								<tr>  
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>:<span class="must">*</span>
									</td>
									<td>	
									<s:select name="panelGroupField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									容器类型：<span class="must">*</span>
									</td>
									<td>	
									<s:select name="panelGroupField.type" cssStyle="width:98%" cssClass="select" list="#{'tab':'tab','panel':'panel'}"   listKey="key" listValue="value"	/>
									</td>
								</tr>
							 </table>
						</div>
						   
				</div>
				
				<div class="add_bottom">
				        <c:if test="${panelGroupField.parentID != 'main'}">
						    <input type="button" id="buttons" name="buttons" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="formSubmit()" />
					    </c:if>  
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
		   </form>	
	</div>
</body>
</html>