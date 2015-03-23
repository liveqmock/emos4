<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='sm_t_addOrUpdateResUrl'/></title>
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
		function selectRes()
		{
			showModalDialog('${ctx}/resUrlManager/resourceList.action',window,'help:no;scroll:no;status:no;dialogWidth:700px;dialogHeight:450px');
		}
		function selectOp()
		{
			showModalDialog('${ctx}/resUrlManager/operateList.action',window,'help:no;scroll:no;status:no;dialogWidth:700px;dialogHeight:450px');
		}
		var init_url_value = '${resUrl.url}';
		function formSubmit()
		{
			var myform = document.forms[0];
			var new_url_value = document.getElementById('resUrl.url').value;
			if(Validator.Validate(myform,2))
			{
				var temp = '${resUrl.pid}';
				var src = document.getElementById('resUrl.url');
				if((temp=='') || (temp!='' && init_url_value!=new_url_value))
				{
					$.get("${ctx}/resUrlManager/chkUrlUnique.action",{urlstr:src.value},function(result)
					{
						if(result=='false')
						{
							alert('<eoms:lable name="sm_msg_resUrlUnique"/>！');
							src.focus();
						}
						else
						{
							myform.submit();
						}
					});
				}
				else
				{
					myform.submit();
				}
			}
		}
	</script>
	</head>

	<body>
	  <form action="${ctx}/resUrlManager/saveResUrl.action" method="post" name="ResUrlform">
	  <input type="hidden" id="resUrl.pid" name="resUrl.pid" value="${resUrl.pid}"/>
	  <input type="hidden" id="resUrl.createtime" name="resUrl.createtime" value="${resUrl.createtime }"/>
	  <input type="hidden" id="resUrl.creater" name="resUrl.creater" value="${resUrl.creater }" class="textInput" readonly/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name='sm_subt_addOrUpdateResUrl'/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name='sm_lb_resOpUrlInfo'/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
							<tr>
								<td class="texttd" style="width:15%">
									<eoms:lable name='sm_lb_chooseResource'/>：<span class="must">*</span>
								</td>
								<td style="width:35%">
									<input type="hidden" name="resUrl.resid" id="resUrl.resid" value="${resUrl.resid }"/>
									<input type="text" name="resUrl.resname" id="resUrl.resname" class="textInput" value="${resUrl.resname }" readonly/>
									<validation id="resUrl.resnameV" dataType="Require" msg="<eoms:lable name='sm_msg_chooseResourceConstraint'/>！" />
								</td>
								<td style="width:15%">
									<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="selectRes();" />
								</td><td style="width:35%"></td>
							</tr>
							<tr>
								<td class="texttd" style="width:15%">
									<eoms:lable name='sm_lb_chooseOperation'/>：<span class="must">*</span>
								</td>
								<td style="width:35%">
									<input type="hidden" name="resUrl.opid" id="resUrl.opid" value="${resUrl.opid }"/>
									<input type="text" name="resUrl.opname" id="resUrl.opname" value="${resUrl.opname }" class="textInput" readonly/>
									<validation id="resUrl.opnameV" dataType="Require" msg="<eoms:lable name='sm_msg_chooseOperateConstraint'/>！" />
								</td>
								<td>
									<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="selectOp();" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td class="texttd">
									<span class="texttd"><eoms:lable name='sm_lb_URL'/>：<span class="must">*</span></span>
								</td>
								<td>
									<input type="text" name="resUrl.url" id="resUrl.url" value="${resUrl.url }" class="textInput"/>
									<validation id="resUrl.urlV" dataType="Custom" regexp="^.{1,50}$" msg="<eoms:lable name='sm_msg_inputUrlConstraint'/>！" />
								</td>
								<td class="texttd">
									<span class="texttd"><eoms:lable name='com_lb_status'/>：<span class="must">*</span></span>
								</td>
								<td>
									<eoms:select name="resUrl.status" style="select" dataDicTypeCode="status" value="${resUrl.status}" isnull="false" />
									<validation id="resUrl.statusV" dataType="Require" msg="<eoms:lable name='sm_msg_chooseResUrlStatus'/>！" />
								</td>
							</tr>
							<tr>
								<td class="texttd" valign="top">
									<eoms:lable name="com_lb_remark"/>：
								</td>
								<td colspan="3">
									<textarea name="resUrl.remark" rows="5" style="width:98.7%">${resUrl.remark }</textarea>
									<validation id="resUrl.remarkV" require="false" dataType="Limit" max="50"
											msg="<eoms:lable name='sm_msg_resUrlRemarkConstraint'/>！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit()"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
