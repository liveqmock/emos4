<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="${ctx}/common/js/date/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/common/js/common.js"></script>
		<script type="text/javascript" src="${ctx}/common/js/subModal.js"></script>
		<script type="text/javascript"
			src="${ctx}/common/javascript/validate.js"></script>
		<title>模板<c:if test="${dutyTemplate.pid == null}">新增</c:if><c:if test="${dutyTemplate.pid != null}">编辑</c:if></title>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,71);
			}
			window.onload = function() {
				setCenter(0,71);
			}
			function saveTempalte(){
				var msghtml = getFormData();
				if (msghtml == '') {
					alert("请填写模板内容!");
					return false;
				}
				
			   if(Validator.Validate(document.theForm,1)){
			      checkName();
			   }
			}
			function checkName(){
			   var name = $("#name").val();
			   if('${dutyTemplate.name}'!=''){
			       if(name != '${dutyTemplate.name}'){
			           $.post("checkName.action", {name:name},callBack);
			       }else{
			           document.forms[0].target="_self";
			           document.forms[0].action="saveDutyTemplate.action";
			           document.forms[0].submit();
			       }
			   }else{
			       $.post("checkName.action", {name:name},callBack);
			   }
			}
			
			function callBack(data){
			    if(data=='false'){
			       document.forms[0].target="_self";
			       document.forms[0].action="saveDutyTemplate.action";
			       document.forms[0].submit();
			    }else{
			       alert("模板名称已存在！");
			       return false;
			    }
			}
			
			function showDt(input_id,input_name){
			    var form_name = 'theForm';
			    showModalDialog('${ctx}/common/tools/selectDicTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id+"&dtcode=dutyTemplateType&isRadio=0",window,'help:no;center:true;scroll:no;status:no;dialogWidth:350px;dialogHeight:450px');
			}
			
		</script>
	</head>
	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">模板<c:if test="${dutyTemplate.pid == null}">新增</c:if><c:if test="${dutyTemplate.pid != null}">编辑</c:if></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div" id="center">
				<form name="theForm" action="saveDutyTemplate.action" method="post">
					<fieldset class="fieldset_style">
						<legend>
							模板信息
						</legend>
						<table id="tab" class="tableborder">
							<tr>
								<td class="title">
									模板名称：
								</td>
								<td class="content">
									<input type="text" name="dutyTemplate.name" id="name" class="textInput" value="${dutyTemplate.name}"/>
									<validation id="dutyTemplate.nameV" dataType="Require" msg="模板名称是必填项" />
								</td>
		
								
								<td class="title">
									启用停用：
								</td>
								<td class="content">
									<select name="dutyTemplate.actived" class="select" value="${dutyTemplate.actived}">
										<option value="1" <c:if test="${dutyTemplate.actived=='1'}">selected="select"</c:if>>
											启用
										</option>
										<option value="0" <c:if test="${dutyTemplate.actived=='0'}">selected="select"</c:if>>
											停用
										</option>
									</select>
								</td>
							</tr>
							<tr>
							    <td class="title">
									模板类型：
								</td>
								<td class="content">
									<input type="text" name="dutyTemplate.templateTypeName" id="templateTypeName" class="textInput" value="<eoms:dic dictype='dutyTemplateType' value='${dutyTemplate.templateType}' isfullname='true'/>" onclick="showDt('templateTypeId','templateTypeName');" readonly="readonly"/>
									<input type="hidden" name="dutyTemplate.templateType" id="templateTypeId" value="${dutyTemplate.templateType}"/>
									
								</td>
							</tr>
							<tr>
							<td colspan="4">
							<%@ include file="/ultraform/editor/form.jsp"%>
							</td></tr>
						</table>
					</fieldset>
					<input type="hidden" name="dutyTemplate.pid" value="${dutyTemplate.pid}"/>
					<input type="hidden" name="dutyTemplate.templateid" value="${dutyTemplate.templateid}"/>
				</form>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" name="button5" id="button4" value="<eoms:lable name='com_btn_save'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="saveTempalte();" />
			<input type="button" name="button5" id="button5" value="<eoms:lable name='com_btn_cancel'/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</body>
</html>
