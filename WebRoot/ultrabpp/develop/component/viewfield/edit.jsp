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
	    var fieldID =  document.getElementById('viewField.id').value;
	    var baseSchema =  document.getElementById('viewField.baseSchema').value;
	    var fieldName =  document.getElementById('viewField.fieldName').value;
	     $.get("${ctx}/ultrabpp/develop/worksheetFieldNameCheckUnique.action?stamp="+new Date().getTime(),{'baseSchema':baseSchema,'fieldName':fieldName,'fieldID':fieldID},function(result)
					{
					  if(result == 'false'){
							alert('组件英文名称重复已经存在！已经存在英文名称为：'+fieldName+'的组件了！');
						}else{
							document.getElementById("viewField.pageurl").value = document.getElementById("pageurl").value;
							var s=document.getElementById('fieldForm').submit(); 
	         			
						}
					})
	    }
	}
	function init(){
		if('${viewField.id}'==''){
			document.getElementById('viewField.rowspan').value='1';
		}
	}
	
	function initVisiable(){
		var visiable = document.getElementById('visiable');
		if('${viewField.id}'==''){
			visiable.checked = true;
			document.getElementById('viewField.visiable').value='1';
		}else{
			visiable.checked = document.getElementById('viewField.visiable').value=='1'?true:false;
		}
	}
	function changeVisiable(){
		var visiable = document.getElementById('visiable');
		document.getElementById('viewField.visiable').value = visiable.checked?'1':'0';
	}
	
	
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_viewtext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveField.action" name="fieldForm" method="post" id="fieldForm">
		          <input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
		          <input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		          <input type="hidden" id="viewField.operate" name="viewField.operate" value="${viewField.operate}"/>
		          <input type="hidden" id="viewField.hasDeploy" name="viewField.hasDeploy" value="${viewField.hasDeploy}"/>
		          <input type="hidden" id="viewField.id" name="viewField.id"  class="textInput" value="${viewField.id}"/><!--主键ID-->
		          <input type="hidden" id="viewField.baseSchema" name="viewField.baseSchema"  class="textInput"  value="${baseSchema}"/><!--工单类型-->
		          <input type="hidden" id="fieldType" name="fieldType"  class="textInput"  value="${fieldType}"/>
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_label"/>：
									</td>
									<td>
									<input type="text" name="viewField.label" id="viewField.label" class="textInput" value="${viewField.label}" maxlength="50"/>
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_fieldname"/>：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${viewField.hasDeploy == '0'&& viewField.operate == 'add'}">
								         <input type="text" name="viewField.fieldName" id="viewField.fieldName" class="textInput" value="${viewField.fieldName}" maxlength="50"/>
								         <validation id="viewField.fieldNameV" require="true" dataType="Require" Max="100" msg="英文名称不能为空，长度不能超过50" />
								   </c:if>
								   <c:if test="${viewField.hasDeploy != '0'|| viewField.operate != 'add'}">
								       ${viewField.fieldName}
								        <input type="hidden" id="viewField.fieldName" name="viewField.fieldName" value="${viewField.fieldName}"/>
								    </c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_visiable"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="hidden" id="viewField.visiable" name="viewField.visiable" value="${viewField.visiable}"/>
									<input type="checkbox" id="visiable" name="visiable" onclick="changeVisiable()"/>
									</td>									
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_ordernum"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="viewField.orderNum" id="viewField.orderNum" class="textInput" value="${viewField.orderNum}" maxlength="10"/>
									<validation id="viewField.orderNumV" require="Custom" dataType="Integer" msg="排序为数字" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									所属容器：<span class="must">*</span>
									</td>
									<td>
									<c:if test="${viewField.parentID == 'action'}">
									<input type="hidden" id="viewField.parentID" name="viewField.parentID" value="${viewField.parentID}"/>																																													
									<eoms:lable name="bpp_develop_worksheet_additional_actionelement"/>
									</c:if>
									<c:if test="${viewField.parentID != 'action'}">
										<c:if test="${ empty panelFieldList}">
										<s:select name="viewField.parentID" id="viewField.parentID" cssStyle="width:98%;display:none" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>
										 <eoms:lable name="bpp_develop_worksheet_additional_nocontainer"/>
										<validation id="viewField.parentIDV" require="true" dataType="Require" msg="容器不能为空" />
										</c:if>
										<c:if test="${!empty panelFieldList}">
									 		<s:select name="viewField.parentID" id="viewField.parentID" cssStyle="width:98%" cssClass="select" list="panelFieldList"   listKey="id" listValue="label"	/>						
										</c:if>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									加载类型:<span class="must">*</span>
									</td>
									<td>
									<s:select name="viewField.type" id="viewField.type" cssStyle="width:98%" cssClass="select" list="#{'include':'include','frame':'frame'}"   listKey="key" listValue="value"	/>						
									</td>
								</tr>
								
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_pageurl"/>:
									</td>
									<td>
									<textarea name="pageurl" id="pageurl" rows="3" class="textInput">${viewField.pageurl}</textarea>
									<input type="hidden" name="viewField.pageurl" id="viewField.pageurl" class="textInput" value="${viewField.pageurl}"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_colspan"/>：<span class="must">*</span>
									</td>
									<td>									
									<s:select name="viewField.colspan" cssStyle="width:98%" cssClass="select" list="#{'1':'1','2':'2','3':'3','4':'4'}"   listKey="key" listValue="value"	/>								
									<validation id="viewField.colspanV" require="Custom" dataType="Integer" msg="占列" />									
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_worksheet_rowspan"/>：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="viewField.rowspan" id="viewField.rowspan" class="textInput" value="${viewField.rowspan}" maxlength="10"/>
									<validation id="viewField.rowspanV" require="Custom" dataType="Integer" msg="占行为数字" />
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