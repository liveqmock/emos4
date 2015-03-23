<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='sm_t_addOrUpdateOper'/></title>
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
		function formSubmit()
		{
			var opid = '${operate.pid}';
			var myform = document.forms[0];
			if(Validator.Validate(myform,2))
			{
				if(opid=='')
				{
					var src = document.getElementById('operate.pid');
					$.get("${ctx}/operateManager/checkUnique.action",{opId:src.value},function(result)
					{
						if(result=='false')
						{
							alert('<eoms:lable name="sm_msg_operateIdUnique"/>！');
							src.focus();
						}
						else
						{
							document.getElementById('saveType').value = 'add';
							myform.submit();
						}
					});
				}
				else
				{
					document.getElementById('saveType').value = 'update';
					myform.submit();
				}
			}
		}
	</script>
	</head>

	<body>
	 	<form action="${ctx}/operateManager/saveOperate.action" method="post" onsubmit="return Validator.Validate(this,2);">
			<input type="hidden" id="saveType" name="saveType" value=""/>
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2"><eoms:lable name='sm_subt_operationSave'/></span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend><eoms:lable name='sm_lb_operateInfo'/></legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name='sm_lb_operateId'/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<c:if test="${operate.pid==null}">
											<input type="text" name="operate.pid" id="operate.pid" class="textInput" value="${operate.pid }"/>
										</c:if>
										<c:if test="${operate.pid!=null}">
											<input type="text" name="operate.pid" id="operate.pid" class="textInput" value="${operate.pid }" readonly/>
										</c:if>
										<validation id="operate.pidV" dataType="Custom" regexp="^[\w]{1,15}$" msg="<eoms:lable name='sm_msg_operIdConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name='sm_lb_operateName'/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<input type="text" id="operate.opname" name="operate.opname" value="${operate.opname }" class="textInput" onkeypress="return clearSpecialChar(event);"/>
										<validation id="operate.opnameV" dataType="Limit" min="1" max="15" msg="<eoms:lable name='sm_msg_operNameConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name='sm_lb_operateType'/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<eoms:select name="operate.optype" style="select" dataDicTypeCode="operatetype"  value="${operate.optype }" isnull="false"/>
										<validation id="operate.optypeV" dataType="Require" msg="<eoms:lable name='sm_msg_operTypeConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name='com_lb_status'/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<eoms:select name="operate.status" style="select" dataDicTypeCode="status"  value="${operate.status}" isnull="false"/>
										<validation id="operate.statusV" dataType="Require" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="com_lb_remark"/>：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="operate.remark" rows="5" style="width:98.7%">${operate.remark }</textarea>
										<validation id="operate.remarkV" require="false" dataType="Limit" max="150"
												msg="<eoms:lable name='sm_msg_operRemarkConstraint'/>！" />
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
