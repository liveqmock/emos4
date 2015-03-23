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
	    
	    //初始化
	    init();
	    //是否可见
	    initVisiable();
	    //单人派发
	    initSingleCheckBox();
	    //下步处理人
	    initDealingWithPeople();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	    
	    var bl = Validator.Validate(fieldForm,2,false);  
	    if(bl){
	    var fieldID =  document.getElementById('assignTreeField.id').value;
	    var baseSchema =  document.getElementById('assignTreeField.baseSchema').value;
	    var fieldName =  document.getElementById('assignTreeField.fieldName').value;
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
		if('${assignTreeField.id}'==''){
			document.getElementById('assignTreeField.rowspan').value='1';
		}
	}
	function initVisiable(){
		var visiable = document.getElementById('visiable');
		if('${assignTreeField.id}'==''){
			visiable.checked = true;
			document.getElementById('assignTreeField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('assignTreeField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('assignTreeField.visiable').value = visiable.checked?'1':'0';
	}
	
	
	function initSingleCheckBox(){
		var singleSelect = document.getElementById('singleSelect');
		if('${assignTreeField.id}'==''){
			singleSelect.checked= true;
			document.getElementById('assignTreeField.singleSelect').value= '1';
		}else{
			singleSelect.checked = '${assignTreeField.singleSelect}'=='1'?true:false;
		}
	}
	function singleCheckBox(){
		var singleSelect = document.getElementById('singleSelect');
		document.getElementById('assignTreeField.singleSelect').value = singleSelect.checked?'1':'0';
	}
	
	function initDealingWithPeople(){
	    if('${assignTreeField.id}'==''){
	       document.getElementById('dealingWithPeople').checked = true;
	       document.getElementById('assignTreeField.next').value = '1';
	    }else{
	       document.getElementById('dealingWithPeople').checked = '${assignTreeField.next}'=='1'?true:false;
	    }
	}
	
	function changeDealingWithPeople(){
		var dealingWithPeople = document.getElementById('dealingWithPeople');
		document.getElementById('assignTreeField.next').value = dealingWithPeople.checked?'1':'0';
	}
	
	
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> 
						<span class="title_icon2">
						    <eoms:lable name="bpp_develop_worksheet_additional_treetext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="assignTreeField.operate" name="assignTreeField.operate" value="${assignTreeField.operate}"/>
		          <input type="hidden" id="assignTreeField.hasDeploy" name="assignTreeField.hasDeploy" value="${assignTreeField.hasDeploy}"/>
		          <input type="hidden" id="assignTreeField.id" name="assignTreeField.id"  class="textInput" value="${assignTreeField.id}"/><!--主键ID-->
		          <input type="hidden" id="assignTreeField.baseSchema" name="assignTreeField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="assignTreeField.label" id="assignTreeField.label" class="textInput" value="${assignTreeField.label}" maxlength="50"/>
									<validation id="assignTreeField.labelV" require="true" dataType="Require" Max="100" msg="中文名称不能为空，长度不能超过50" />
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${assignTreeField.hasDeploy == '0'&& assignTreeField.operate == 'add'}">
									    <input type="text" name="assignTreeField.fieldName" id="assignTreeField.fieldName" class="textInput" value="${assignTreeField.fieldName}" maxlength="50"/>
									    <validation id="assignTreeField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
									</c:if>
									<c:if test="${assignTreeField.hasDeploy != '0'|| assignTreeField.operate != 'add'}">
									    ${assignTreeField.fieldName}
									        <input type="hidden" id="assignTreeField.fieldName" name="assignTreeField.fieldName" value="${assignTreeField.fieldName}"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：
									</td>
									<td>
										<input type="hidden" id="assignTreeField.visiable" name="assignTreeField.visiable" value="${assignTreeField.visiable}"/>
									    <input type="checkbox" id="visiable" name="visiable" maxlength="10"  onclick="changeVisiable()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="assignTreeField.orderNum" id="assignTreeField.orderNum" class="textInput" value="${assignTreeField.orderNum}" maxlength="50"/>
									<validation id="assignTreeField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${assignTreeField.parentID == 'action'}">
									<input type="hidden" id="assignTreeField.parentID" name="assignTreeField.parentID"  class="textInput" value="${assignTreeField.parentID}"/>
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${assignTreeField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="assignTreeField.parentID" id="assignTreeField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										 <eoms:lable name="bpp_develop_worksheet_additional_nocontainer"/>
										<validation id="assignTreeField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="assignTreeField.parentID" id="assignTreeField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								
								<tr>
									<td class="texttd">
									单人派发：
									</td>
									<td>
									<input type="hidden" id="assignTreeField.singleSelect" name="assignTreeField.singleSelect"  class="textInput" value="${assignTreeField.singleSelect}"/>
									<input type="checkbox" id="singleSelect" name="singleSelect"  onclick="singleCheckBox()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									自定义动作：
									</td>
									<td>									
									<input type="text" id="assignTreeField.action" name="assignTreeField.action"  class="textInput"   value="${assignTreeField.action}"/>								
									</td>
								</tr>
								<tr>
									<td class="texttd">
									下步处理人：
									</td>
									<td>									
									<input type="hidden" id="assignTreeField.next" name="assignTreeField.next" value="${assignTreeField.next}"/>
							        <input type="checkbox" id="dealingWithPeople" name="dealingWithPeople"  onclick="changeDealingWithPeople()"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>								
									<s:select name="assignTreeField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="assignTreeField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="assignTreeField.rowspan" id="assignTreeField.rowspan" class="textInput" value="${assignTreeField.rowspan}" maxlength="10"/>
									<validation id="assignTreeField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
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