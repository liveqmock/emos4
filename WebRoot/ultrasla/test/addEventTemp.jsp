<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@ include file="/common/core/taglibs.jsp"%>
    <title>UltraSLA测试—临时事件保存</title>
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
		function saveEventTemp(){
			document.forms[0].submit();
		}
	</script>
  </head>
  <body>
	  	<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">当前位置：UltraSLA测试—临时事件保存
					</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_lb_roleInfoManage"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<div id="div1">
							<form name="roleForm" action="${ctx}/roleManager/saveRole.action" method="post">
								<input type="hidden" id="eventTemp.pid" name="eventTemp.pid" value="${eventTemp.pid}"/>
								<table class="add_user">
									<tr>
										<td class="texttd" style="width:15%">规则ID：</td>
										<td style="width:35%">
											<input type="text" id="eventTemp.ruleids" name="eventTemp.ruleids" class="textInput" value="${eventTemp.ruleids}" />
										</td>
										<td class="texttd" width="15%">完成时限:</td>
										<td width="35%">
										  <input type="text" id="eventTemp.duetime" name="eventTemp.duetime" class="textInput" value="${eventTemp.duetime}" 
										  	onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',autoPickDate:true});"/>
										</td>
									</tr>
									<tr>
										<td class="texttd" style="width:15%">默认处理人：</td>
										<td style="width:35%">
											<input type="text" id="eventTemp.defaultuser" name="eventTemp.defaultuser" class="textInput" value="${eventTemp.defaultuser}"/>
										</td>
										<td class="texttd" width="15%">默认处理组:</td>
										<td width="35%">
										  <input type="text" id="eventTemp.defaultgroup" name="eventTemp.defaultgroup" class="textInput" value="${eventTemp.defaultgroup}"/>
										</td>
									</tr>
									<tr>
										<td class="texttd" style="width:15%">默认处理角色：</td>
										<td style="width:35%">
											<input type="text" id="eventTemp.defaultrole" name="eventTemp.defaultrole" class="textInput" value="${eventTemp.defaultrole}"/>
										</td>
										<td class="texttd" width="15%">状态:</td>
										<td width="35%">
											<eoms:select name="eventTemp.status" style="select" dataDicTypeCode="status" value="${eventTemp.status}" isnull="false"/>
										</td>
									</tr>
									<tr>
										<td class="texttd" valign="top" valign="top">事件参数XML：</td>
										<td colspan="3">
											<textarea name="eventTemp.eventparamxml" rows="3" class="textInput" style="width:98.7%">${eventTemp.eventparamxml}</textarea>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save" />" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="saveEventTemp();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" id="cancelButton"/>
		</div>
  </body>
</html>
