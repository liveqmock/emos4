<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<title>短信模板新建</title>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0,61);
		}
		window.onload = function() {
			setCenter(0,61);
		}
		
		function getSelectText() {
	        var sel = document.getElementById('selectValue').value;
	        document.getElementById('smsModel.modelType').value = sel;
	        if(""!=sel){
		        ajaxGetSmsModel(sel);
	        }
		}
		
		function getSmsModelByModelType(){
			if(""!=$("#smsModel.modelType").val()){
				ajaxGetSmsModel($("#smsModel.modelType").val());
			}
		}
		
		function ajaxGetSmsModel(sel){
			$.ajax({
			    url: "${ctx}/smsModel/loadSmsModel.action?modelType="+sel,
			    type: 'POST',
			    dataType: 'json',
			    success: function(json){
						document.getElementById("smsModel.pid").value=json.pid;
						document.getElementById("smsModel.content").value=json.content;
			    }
			 });
		}
		
		function formSubmit()
		{
			if(Validator.Validate(document.forms[0],2))	document.forms[0].submit();
		}
	</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">短信模板新建</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>短信模板新建</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
							<table class="add_user">
							 <form action="${ctx}/smsModel/smsModelSave.action" method="post" name="smsModelSave" >
								<tr>
									<td class="texttd" width="15%">
										PID:
									</td>
									<td>
										<input id="smsModel.pid" name="smsModel.pid" value="${smsModel.pid}" readonly="true" style="width:400px"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										模板类别：
									</td>
									<td>
										<select id="selectValue" onchange="getSelectText()">
											<c:forEach items="${requestScope.schemaList}" var="modeltype">
												<option value="${modeltype}" <c:if test="${modeltype==smsModel.modelType}">selected</c:if>>${modeltype}</option>
											</c:forEach>
										</select>
										<input name="smsModel.modelType" id="smsModel.modelType" value="${smsModel.modelType}" onblur="getSmsModelByModelType()"/>
										<validation id="smsModel.modelTypeV" dataType="Require" msg="请选择或填写模板类别！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										模板内容：
									</td>
									<td>
										<input type="text" id="smsModel.content" name="smsModel.content" value="${smsModel.content}" class="textInput" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										&nbsp;
									</td>
								</tr>
							</table>
						</form>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  
	</body>
</html>
