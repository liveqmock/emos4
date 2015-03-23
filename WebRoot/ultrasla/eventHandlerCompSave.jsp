<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>事件主体组件增加/修改</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		function formSubmit() {
			var myform = document.forms[0];
			if(Validator.Validate(myform, 2)) {				
				myform.submit();
			}
		}
	</script>
	</head>
	<body>
	 	<form name="euform" action="${ctx}/eventHandlerComp/saveEventHandlerComp.action" method="post" onsubmit="return Validator.Validate(this,2);">
			<input type="hidden" id="eventHandlerComp.pid" name="eventHandlerComp.pid" value="${eventHandlerComp.pid}" />	
			<input type="hidden" id="eventHandlerComp.createtime" name="eventHandlerComp.createtime" value="${eventHandlerComp.createtime}" />				
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2">事件主体组件增加/修改</span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend>事件主体组件信息维护</legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top">
							<table class="add_user">
								
								<tr>
									<td style="width:15%">
										组件名称：<span class="must">*</span>
									</td>
									<td style="width:75%">
										<input type="text" name="eventHandlerComp.componentname" id="eventHandlerComp.componentname" class="textInput"  style="width:97.8%" value="${eventHandlerComp.componentname}"/>
									    <validation id="eventHandlerComp.componentnameV" dataType="Limit" min="1" max="50" msg="组件名称不能为空且小于50个字！" />
									</td>
									<td style="width:10%"></td>
								</tr>
								<tr>								
								    <td>
										组件类型：
									</td>
									<td>
										<input type="text" name="eventHandlerComp.componenttype" id="eventHandlerComp.componenttype"  style="width:97.8%" class="textInput" value="${eventHandlerComp.componenttype}"/>
								        <validation id="eventHandlerComp.componenttypeV" dataType="Limit" min="0" max="50" msg="组件类型不能超过50个字！" />
								    </td>
								    <td></td>
								<tr>
									<td>
										组件执行类型：<span class="must">*</span>
									</td>
									<td>
										<eoms:select name="eventHandlerComp.compexetype" dataDicTypeCode="dynamicconchecktype"  value="${eventHandlerComp.compexetype}" isnull="false"/>
									    <validation id="eventHandlerComp.compexetypeV" dataType="Limit"  msg="组件执行类型为空！" />
									</td>
									<td></td>
								</tr>
								<tr>
									<td>
										执行类型参数：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="eventHandlerComp.compexeparam" id="eventHandlerComp.compexeparam" style="width:97.8%" class="textInput" value="${eventHandlerComp.compexeparam}"/>
									    <validation id="eventHandlerComp.compexeparamV" dataType="Limit" min="1" max="150"  msg="执行类型参数不能为空且小于150个字！" />
									</td>
									<td></td>
								<tr>
									<td>
										状态：<span class="must">*</span>
									</td>
									<td>
										<eoms:select name="eventHandlerComp.status"  style="select" dataDicTypeCode="status"  value="${eventHandlerComp.status}" isnull="false"/>
										<validation id="eventHandlerComp.statusV" dataType="Require" msg="状态不能为空！" />
									</td>
									<td></td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="add_bottom">
				<input type="button" value="<eoms:lable name="com_btn_save"/>" 
					class="save_button" onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="formSubmit();"/>
				<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</form>
	</body>
</html>