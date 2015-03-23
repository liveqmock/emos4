<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@ include file="/common/core/taglibs.jsp"%>
    <title><eoms:lable name="sm_subt_manualSmSend"/></title>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,61);
		}
		window.onload = function() 
		{
			setCenter(0,61);
			var message = "${message}";
			if(message!="")
			{
				alert(message);
			}
		}
		function sentTypeChange()
		{
			var type = document.getElementsByName("sendType")[0].value;
			if(type=="1")
			{
				document.getElementById("sendTime").disabled = true;
				document.getElementById("sendTime").value = "";
				document.getElementById("sendTypeSpan").style.visibility = "hidden";
			}
			if(type=="2")
			{
				document.getElementById("sendTime").disabled = false;
				document.getElementById("sendTypeSpan").style.visibility = "visible";
			}
		}
		function openTree(type)
		{
			if("1"==type)
			{//人员
				openwindow('${ctx}/common/tools/depOrUserSelect.jsp?input_name=smRecepter&input_id=smRecepterId&isRadio=1&isSelectType=1','',380,480);
			}
			if("2"==type)
			{//组织
				openwindow('${ctx}/common/tools/depOrUserSelect.jsp?input_name=smReceptGroup&input_id=smReceptGroupId&isRadio=1&isSelectType=0','',380,480);
			}
		}
		function formsubmit()
		{
			var recepter = document.getElementById("smRecepter").value;
			var receptGroup = document.getElementById("smReceptGroup").value;
			var sendType = document.getElementById("sendType").value;
			var sendTime = document.getElementById("sendTime").value;
			var smContent = document.getElementById("smContent").value;
			if(recepter=="" && receptGroup=="")
			{
				alert("<eoms:lable name='sm_msg_banNullRecepter'/>！");
				return;
			}
			if(sendType=="2" && sendTime=="")
			{
				alert("<eoms:lable name='sm_msg_chooseSendTime'/>！");
				return;
			}
			if(smContent=="")
			{
				alert("<eoms:lable name='sm_msg_nullSmContentTip'/>！");
				return;
			}
			else
			{
				document.forms[0].submit();
			}
		}
	</script>
  </head>
  <body>
	  	<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">${nodePath}
					</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_lb_smDataInfo"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<div id="div1" align="center">
							<form action="${ctx}/insidesms/manualSmSend.action" method="post">
								<table class="add_user" style="width:55%;">
									<tr align="left">
										<td class="texttd" width="15%"><eoms:lable name="sm_lb_smRecepter" />：</td>
										<td>
											<input type="text" readonly="readonly" name="smRecepter" id="smRecepter" class="textInput" style="width:70%"/>
											<input type="hidden" name="smRecepterId" id="smRecepterId"/>
											<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1');" />
											<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="document.getElementById('smRecepter').value='';"/>
										</td>
									</tr>
									<tr align="left">
										<td class="texttd"><eoms:lable name="sm_lb_smRecepGroup" />：</td>
										<td>
											<input type="text" readonly="readonly" name="smReceptGroup" id="smReceptGroup" class="textInput" style="width:70%"/>
											<input type="hidden" name="smReceptGroupId" id="smReceptGroupId"/>
											<input type="button" value="<eoms:lable name="com_btn_choose"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('2');" />
											<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onclick="document.getElementById('smReceptGroup').value='';"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"/>
										</td>
									</tr>
									<tr align="left">
										<td class="texttd"><eoms:lable name="sm_lb_smSendType" />：</td>
										<td>
											<select name="sendType" onchange="sentTypeChange();" style="width:41%">
												<option value="1"><eoms:lable name="sm_lb_smSendAtOnce"/></option>
												<option value="2"><eoms:lable name="sm_lb_smSendAtTime"/></option>
											</select>
										</td>
									</tr>
									<tr align="left">
										<td  class="texttd">
											<eoms:lable name="sm_lb_smssengtime" />：<span style="visibility:hidden" id="sendTypeSpan" class="must">*</span>
										</td>
										<td>
											<input readonly="readonly" disabled="disabled" type="text" name="sendTime" id="sendTime" class="textInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" style="width:40%"/>
											<span class="must">*<eoms:lable name="sm_lb_smSendAtTimeTip"/>。</span>
										</td>
									</tr>
									<tr align="left">
										<td class="texttd" valign="top" valign="top"><eoms:lable name="sm_lb_smcontent" />：<span class="must">*</span></td>
										<td>
											<textarea name="smContent" id="smContent" rows="6" class="textInput" style="width:93%"></textarea>
										</td>
									</tr>
								</table>
								<br/>
								<input type="button" value="<eoms:lable name="sm_lb_send" />" 
									class="save_button" onmouseover="this.className='save_button_hover'"
									onmouseout="this.className='save_button'" onclick="formsubmit();"/>
							</form>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom"></div>
  </body>
</html>
