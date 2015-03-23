<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0,40);
			}
			window.onload = function()
			{
				setCenter(0,40);
				refreshNode();
			}
			
			function refreshNode()
			{
				var message = '${message}';
				if(message == 'success')
				{
					var parentid = '0';//操作成功以后刷新父ID
					parent.document.getElementById('leftFrame2').contentWindow.refresh(parentid);//刷新左侧树结构
				}
			}
			
			function dicInfoDel()
			{
				if(document.getElementById('dictype.pid').value == '')
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>");
					return;
				}
				if(confirm("<eoms:lable name='sm_msg_delDicType'/>？"))
				{
					window.location.href = 'dicInfoDel.action?type=1&dicId='+document.getElementById('dictype.pid').value;
				}
			}
			
			function dicInfoSave()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					if(document.getElementById('dictype.pid').value != '')
					{
							document.getElementById('dictype.dtcode').disabled = '';
							document.forms[0].submit();
					}
					else
					{
						var src = document.getElementById('dictype.dtcode');
						$.get('checkUnique.action?type=1',{dtcode:src.value},function(result)
						{
							if(result=='false')
							{
								src.style.color = 'red';
								alert('<eoms:lable name="sm_msg_uniqueDtcode"/>！');
								src.focus();
							}
							else
							{
								src.style.color = 'black';
								document.forms[0].submit();
							}
						});
					}
				}
			}
			
			function addnew(level)//level表示当前级别或是下级
			{
				if(document.getElementById('dictype.pid').value == '')
				{
					alert("<eoms:lable name='sm_msg_savefirst'/>");
					return;
				}
				if(level == 'current')
				{
					document.getElementById('dictype.pid').value = '';
					document.getElementById('dictype.dtname').value = '';
					document.getElementById('dictype.dtcode').value = '';
					document.getElementById('dictype.dtcode').disabled = '';
					//document.getElementById('dictype.dictype').value = '1';
					document.getElementById('dictype.status').value = '1';
					document.getElementById('dictype.remark').value = '';
				}
				else
				{
					document.getElementById('dictype.dtcode').disabled = '';
					document.forms[0].action = 'addItem.action';
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<form action="dicInfoSave.action" method="post">
		<input type="hidden" name="type" id="type" value="${type}"/>
		<input type="hidden" name="cfgpage" id="cfgpage" value="${cfgpage}"/>
		<div class="content">
		<!-- cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮 -->
			<c:if test="${cfgpage=='1'}">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="dicInfoSave()" text="com_btn_save" operate="com_save"/>
					<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="dicInfoDel()" text="com_btn_delete" operate="com_delete"/>
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_addtype" operate="com_add" />
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_additem" operate="com_add" />
	  	 		</div>
			</div>
			</c:if>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_t_dictype"/></legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:20%"><eoms:lable name="sm_lb_dtname" />：<span class="must">*</span></td>
							<td style="width:60%">
								<input type="hidden" name="dictype.pid" id="dictype.pid" class="textInput" value="${dictype.pid}"/>
								<input type="text" name="dictype.dtname" id="dictype.dtname" class="textInput" value="${dictype.dtname}"/>
								<validation id="dictype.dtnameV" dataType="Limit" Min="1" Max="15" msg="<eoms:lable name='sm_msg_dtnameConstraint'/>！" />
							</td>
							<td style="width:20%"></td>
						</tr>
						<tr>
							<td class="texttd" style="width:20%"><eoms:lable name="sm_lb_dtcode" />：<span class="must">*</span></td>
							<td>
								<input type="text" name="dictype.dtcode" id="dictype.dtcode" class="textInput" value="${dictype.dtcode}" disabled/>
								<validation id="dictype.dtcodeV" require="true" dataType="Custom" regexp="^\w{1,30}$" msg="<eoms:lable name='sm_msg_dtcodeConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" style="width:20%"><eoms:lable name="sm_lb_dictype" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="dictype.dictype" style="select" dataDicTypeCode="dictype" value="${dictype.dictype}" isnull="false"/>
								<validation id="dictype.dictypeV" dataType="Limit" Min="1" msg="<eoms:lable name='sm_msg_dictypeConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_status" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="dictype.status" style="select" dataDicTypeCode="status" value="${dictype.status}" isnull="false"/>
								<validation id="dictype.statusV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top"><eoms:lable name="com_lb_remark"/>：</td>
							<td colspan="2">
								<textarea name="dictype.remark" id="textarea" rows="6" class="textInput">${dictype.remark}</textarea>
								<validation id="dictype.remarkV" require="false" dataType="Limit" max="160" msg="<eoms:lable name='sm_msg_remarkConstraint'/>！" />
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
		<!-- cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮 -->
		<c:if test="${cfgpage=='0'}">
			<div class="add_bottom">
				<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_save"/>" class="save_button"
					onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="dicInfoSave();"/>
				<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
					onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</c:if>
		</form>
	</body>
</html>
