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
					var parentid = '';//操作成功以后刷新父ID
					if('${dicitem.parentid}' == '0')
						parentid = '${dicitem.dtid}' + ':' + '${dicitem.dtcode}';//刷新字典类型时字典类型ID的格式
					else
						parentid = '${dicitem.parentid}';
					parent.document.getElementById('leftFrame2').contentWindow.refresh(parentid);//刷新左侧树结构
				}
			}

			function dicInfoDel(type)
			{
				if(document.getElementById('dicitem.pid').value == '')
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>");
					return;
				}
				if(confirm("<eoms:lable name='sm_msg_delDicItem'/>？"))
				{
					window.location.href = 'dicInfoDel.action?type=2&dicId='+document.getElementById('dicitem.pid').value;
				}
			}
			
			function dicInfoSave()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					if(document.getElementById('dicitem.pid').value != '')
					{
						document.getElementById('dicitem.divalue').disabled = '';
						document.forms[0].submit();
					}
					else
					{
						var src = document.getElementById('dicitem.divalue');
						$.get('checkUnique.action?type=2',{divalue:src.value,dtcode:document.getElementById('dicitem.dtcode').value},function(result)
						{
							if(result=='false')
							{
								src.style.color = 'red';
								alert('<eoms:lable name="sm_msg_uniqueDivalue"/>！');
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
				if(document.getElementById('dicitem.pid').value == '')
				{
					alert("<eoms:lable name='sm_msg_savefirst'/>");
					return;
				}
				if(level == 'lower')
				{
					document.getElementById('dicitem.parentid').value = document.getElementById('dicitem.pid').value;
					document.getElementById('parentname').value = document.getElementById('dicitem.diname').value;
				}
				document.getElementById('dicitem.pid').value = '';
				document.getElementById('dicitem.divalue').value = '';
				document.getElementById('dicitem.divalue').disabled = '';
				document.getElementById('dicitem.diname').value = '';
				//document.getElementById('dicitem.isdefault').value = '0';
				document.getElementById('dicitem.status').value = '1';
				document.getElementById('dicitem.ordernum').value = '';
				document.getElementById('dicitem.remark').value = '';
			}
			
			function updateFullName(src)
			{
				var fullName = document.getElementById('dicitem.dicfullname');
				if(fullName.value.indexOf('.') < 0)
				{
					fullName.value = src.value;
				}
				else
				{
					if(fullName.value==''|| fullName.value.length==0)
					{
						return;
					}
					else
					{
						fullName.value = fullName.value.substring(0,fullName.value.lastIndexOf('.')+1) + src.value;
					}
				}
			}
		</script>
	</head>
	<body>
		<form action="dicInfoSave.action" method="post">
		<input type="hidden" id="type" name="type" value="${type}"/>
		<input type="hidden" id="cfgpage" name="cfgpage" value="${cfgpage}"/>
		<input type="hidden" id="dicitem.dicdn" name="dicitem.dicdn" value="${dicitem.dicdn}"/>
		<input type="hidden" id="dicitem.dicdns" name="dicitem.dicdns" value="${dicitem.dicdns}"/>
		<input type="hidden" id="dicitem.dicfullname" name="dicitem.dicfullname" value="${dicitem.dicfullname}"/>
		<div class="content">
		<!-- cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮 -->
			<c:if test="${cfgpage=='1'}">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="dicInfoSave()" text="com_btn_save" operate="com_save" />
					<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="dicInfoDel()" text="com_btn_delete" operate="com_delete" />
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_currentadd" operate="com_add" />
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_loweradd" operate="com_add" />
	  	 		</div>
			</div>
			</c:if>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_t_dicitem"/></legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:20%"><eoms:lable name="sm_lb_dtname" />：</td>
							<td style="width:60%">
								<input type="hidden" name="dicitem.dtid" id="dicitem.dtid" class="textInput" value="${dicitem.dtid}"/>
								<input type="hidden" name="dicitem.dtcode" id="dicitem.dtcode" class="textInput" value="${dicitem.dtcode}"/>
								<input type="text" name="dtname" id="dtname" class="textInput" value="${dtname}" disabled/>
							</td>
							<td style="width:20%"></td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_parentdicitem" />：</td>
							<td>
								<input type="hidden" name="dicitem.parentid" id="dicitem.parentid" value="${dicitem.parentid}"/>
								<input type="text" name="parentname" id="parentname" class="textInput" value="${parentname}" disabled/>
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_diname" />：<span class="must">*</span></td>
							<td>
								<input type="hidden" name="dicitem.pid" id="dicitem.pid" value="${dicitem.pid}"/>
								<input type="text" name="dicitem.diname" id="dicitem.diname" class="textInput" value="${dicitem.diname}" onblur="updateFullName(this)"/>
								<validation id="dicitem.dinameV" dataType="Limit" Min="1" Max="30" msg="<eoms:lable name='sm_msg_dinameConstraint'/>" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_divalue" />：<span class="must">*</span></td>
							<td>
								<input type="text" name="dicitem.divalue" id="dicitem.divalue" class="textInput" value="${dicitem.divalue}" <c:if test="${dicitem.pid!=''}">disabled</c:if>/>
								<validation id="dicitem.divalueV" require="true" dataType="Custom" regexp="^\w{1,20}$" msg="<eoms:lable name='sm_msg_divalueConstraint'/>！" />
							</td>
						</tr>
						<!-- 
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_isdefault" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="dicitem.isdefault" style="select" dataDicTypeCode="isdefault" value="${dicitem.isdefault}" isnull="false"/>
								<validation id="dicitem.isdefaultV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_isdefaultConstraint'/>！" />
							</td>
						</tr>
						 -->
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_status" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="dicitem.status" style="select" dataDicTypeCode="status" value="${dicitem.status}" isnull="false"/>
								<validation id="dicitem.statusV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_orderNum" />：<span class="must">*</span></td>
							<td>
								<input type="text" name="dicitem.ordernum" id="dicitem.ordernum" class="textInput" value="${dicitem.ordernum}" />
								<validation id="dicitem.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top"><eoms:lable name="com_lb_remark"/>：</td>
							<td colspan="3">
								<textarea name="dicitem.remark" id="textarea" rows="6" class="textInput">${dicitem.remark}</textarea>
								<validation id="dicitem.remarkV" require="false" dataType="Limit" max="160" msg="<eoms:lable name='sm_msg_remarkConstraint'/>！" />
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
