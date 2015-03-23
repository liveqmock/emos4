<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/header_form.jsp"%>
		<title><eoms:lable name='sm_t_userTemplate'/></title>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setShare();
			setCenter(0, LAYOUT_FORM_OPEN);
			if('${typedata_name}' == '')
				document.getElementById('typedata_name').value = '所有工单';
		}
		function selectTemplateType()
		{
			var utType = document.getElementById('userTemplate.uttype').value;
			//var selectData = document.getElementById('typedata_mark').value;
			showModalDialog('${ctx}/userTemplate/userTemplateType.action?utType=' + utType + '&form_name=userTemplate&type_mark=typedata_mark&type_name=typedata_name',window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
		}
		function selectTemplateData()
		{
			var selectData = 'U:' + document.getElementById('userdata_id').value + ';D:' + document.getElementById('depdata_id').value;
			showModalDialog('${ctx}/common/tools/orgtree/organizaTree.jsp?isSelectType=2&user_name=userdata_name&user_id=userdata_id&dep_name=depdata_name&dep_id=depdata_id&targetDataArr='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
		}
		function selectTemplateShare()
		{
			var selectData = 'U:' + document.getElementById('usershare_id').value + ';D:' + document.getElementById('depshare_id').value;
			showModalDialog('${ctx}/common/tools/orgtree/organizaTree.jsp?isSelectType=2&user_name=usershare_name&user_id=usershare_id&dep_name=depshare_name&dep_id=depshare_id&targetDataArr='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
		}
		function clearTemplateType()
		{
			document.getElementById('typedata_mark').value = '';
			document.getElementById('typedata_name').value = '';
		}
		function clearTemplateData()
		{
			document.getElementById('userdata_id').value = '';
			document.getElementById('userdata_name').value = '';
			document.getElementById('depdata_id').value = '';
			document.getElementById('depdata_name').value = '';
		}
		function clearTemplateShare()
		{
			document.getElementById('usershare_id').value = '';
			document.getElementById('usershare_name').value = '';
			document.getElementById('depshare_id').value = '';
			document.getElementById('depshare_name').value = '';
		}
		function setShare()
		{
			if(document.getElementById('userTemplate.isshare').value == '1')
			{
				document.getElementById('cfgShare').disabled = '';
				document.getElementById('clearShare').disabled = '';
			}
			else
			{
				document.getElementById('cfgShare').disabled = 'disabled';
				document.getElementById('clearShare').disabled = 'disabled';
			}
		}
		function formSubmit()
		{
			var form = document.forms[0];
			if(Validator.Validate(form, 2))
			{
				if(document.getElementById('typedata_mark').value == '') // 如果没选工单，按所有类别算
				{
					document.getElementById('typedata_mark').value = 'ALL';
					document.getElementById('typedata_name').value = '所有工单';
					//alert('请选择工单类别！');
					//return;
				}
				if(document.getElementById('userdata_id').value == '' && document.getElementById('depdata_id').value == '')
				{
					alert('请配置模版所选人或组！');
					return;
				}
				document.getElementById('typedata').value = document.getElementById('typedata_mark').value + ';' + document.getElementById('typedata_name').value;;
				if(document.getElementById('typedata').value == document.getElementById('uttype').value)
				{
					document.getElementById('uttype').value = 'false';
				}
				//dataid：当dataid=false 时，则说明此尚未修改配置数据 不用进行操作配置数据表
				var dataid = document.getElementById('userdata_id').value + ':' + document.getElementById('depdata_id').value;
				if(document.getElementById('dataid').value == dataid)
				{
					document.getElementById('dataid').value = 'false';
				}
				//shareid：当shareid=false 时，则说明此尚未修改配置共享 不用进行操作配置共享表
				var shareid = document.getElementById('usershare_id').value + ':' + document.getElementById('depshare_id').value;
				if(document.getElementById('shareid').value == shareid)
				{
					document.getElementById('shareid').value = 'false';
				}
				if(Validator.Validate(userTemplate, 2))
				{
					form.submit();
				}
			}
		}
	</script>
	</head>
	<body>
	 	<form name="userTemplate" action="${ctx}/userTemplate/userTemplateSave.action" method="post" onsubmit="return Validator.Validate(this, 2);">
			<input type="hidden" id="userTemplate.pid" name="userTemplate.pid" value="${userTemplate.pid}"/>
			<input type="hidden" id="userTemplate.uttype" name="userTemplate.uttype" value="1"/><!-- 目前写死 1代表工单 -->
			<input type="hidden" id="userTemplate.creater" name="userTemplate.creater" value="${userTemplate.creater}"/>
			<input type="hidden" id="userTemplate.createtime" name="userTemplate.createtime" value="${userTemplate.createtime}"/>
			<input type="hidden" id="typedata" name="userTemplate.typedata" value="${userTemplate.typedata}"/>
			<input type="hidden" id="userdata_id" name="userTemplate.userdata" value="${userTemplate.userdata}"/>
			<input type="hidden" id="depdata_id" name="userTemplate.depdata" value="${userTemplate.depdata}"/>
			<input type="hidden" id="usershare_id" name="userTemplate.usershare" value="${userTemplate.usershare}"/>
			<input type="hidden" id="depshare_id" name="userTemplate.depshare" value="${userTemplate.depshare}"/>
			<input type="hidden" id="typedata_mark" name="typedata_mark" value="${typedata_mark}"/>
			<input type="hidden" id="uttype" name="uttype" value="${userTemplate.typedata}"/>
			<input type="hidden" id="dataid" name="dataid" value="${userTemplate.userdata}:${userTemplate.depdata}"/>
			<input type="hidden" id="shareid" name="shareid" value="${userTemplate.usershare}:${userTemplate.depshare}"/>
			<input type="hidden" id="utname" name="utname" value="${userTemplate.templatename}"/>
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"><span class="title_icon2"><eoms:lable name='sm_lb_userTemplateInfo'/></span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend><eoms:lable name='sm_lb_userTemplateInfo'/></legend>
						<div class="tabContent_top">
							<table class="tableborder">
								<tr>
									<td class="texttd" style="width: 15%">
										<eoms:lable name='sm_lb_templateName'/>：<span class="must">*</span>
									</td>
									<td style="width: 35%" colspan="2">
										<input type="text" id="userTemplate.templatename" name="userTemplate.templatename" class="textInput" value="${userTemplate.templatename}"/>
										<validation id="userTemplate.templatenameV" dataType="Limit" min="1" max="25" msg="<eoms:lable name='sm_msg_userTemplate'/>！" />
									</td>
									<td class="texttd" style="width: 15%">
										<eoms:lable name="com_lb_status"/>：<span class="must">*</span>
									</td>
									<td style="width: 35%" colspan="2">
										<eoms:select name="userTemplate.status" style="select" dataDicTypeCode="status"  value="${userTemplate.status}" isnull="false"/>
										<validation id="userTemplate.statusV" dataType="Require" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_orderNum"/>：<span class="must">*</span>
									</td>
									<td colspan="2">
										<input type="text" id="userTemplate.ordernum" name="userTemplate.ordernum" value="${userTemplate.ordernum }" class="textInput" />
										<validation id="userTemplate.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name='sm_lb_isShare'/>：<span class="must">*</span>
									</td>
									<td colspan="2">
										<eoms:select name="userTemplate.isshare" style="select" dataDicTypeCode="isdefault" onChangeFun="setShare();" value="${userTemplate.isshare}" isnull="false"/>
										<validation id="userTemplate.isshareV" dataType="Require" msg="<eoms:lable name='sm_msg_templateShare'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="com_lb_remark"/>：
									</td>
									<td colspan="5">
										<textarea name="userTemplate.remark" rows="3" style="width:98.6%">${userTemplate.remark}</textarea>
										<validation id="userTemplate.remarkV" require="false" dataType="Limit" max="150"
												msg="<eoms:lable name='sm_msg_operRemarkConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<th style="width: 15%"><eoms:lable name="sm_lb_textArea" /></th>
									<th style="width: 70%" colspan="4"><eoms:lable name="sm_lb_dataArea" /></th>
									<th style="width: 15%"><eoms:lable name="sm_lb_operateArea" /></th>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										工单类别：
									</td>
									<td colspan="4">
										<textarea name="typedata_name" rows="3" style="width:98%" readonly>${typedata_name}</textarea>
									</td>
									<td>
										<input type="button" class="button" value="选择工单" id="cfgType" name="cfgType" onclick="selectTemplateType();"/>
										<input type="button" class="button" value="清空工单" id="clearType" name="clearType" onclick="clearTemplateType();"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="sm_lb_templateDataUser"/>：<span class="must">*</span>
									</td>
									<td colspan="4">
										<textarea name="userdata_name" rows="3" style="width:98%" readonly>${userData}</textarea>
									</td>
									<td rowspan="2">
										<input type="button" class="button" value="<eoms:lable name='sm_lb_templateDataConfig'/>" id="cfgData" name="cfgData" onclick="selectTemplateData();"/>
										<input type="button" class="button" value="<eoms:lable name='sm_lb_templateDataClear'/>" id="clearData" name="clearData" onclick="clearTemplateData();"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="sm_lb_templateDataDep"/>：<span class="must">*</span>
									</td>
									<td colspan="4">
										<textarea name="depdata_name" rows="3" style="width:98%" readonly>${depData}</textarea>
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="sm_lb_templateShareUser"/>：
									</td>
									<td colspan="4">
										<textarea name="usershare_name" rows="3" style="width:98%" readonly>${userShare}</textarea>
									</td>
									<td rowspan="2">
										<input type="button" class="button" value="<eoms:lable name='sm_lb_templateShareConfig'/>" id="cfgShare" name="cfgShare" onclick="selectTemplateShare();"/>
										<input type="button" class="button" value="<eoms:lable name='sm_lb_templateShareClear'/>" id="clearShare" name="clearShare" onclick="clearTemplateShare();"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="sm_lb_templateShareDep"/>：
									</td>
									<td colspan="4">
										<textarea name="depshare_name" rows="3" style="width:98%" readonly>${depShare}</textarea>
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
