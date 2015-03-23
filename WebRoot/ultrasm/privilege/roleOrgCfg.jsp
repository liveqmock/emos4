<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='sm_t_roleOrg'/></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		function roleCompare(roleIds)
		{
			var current_roleId = '${sessionScope.userSession.roleId}';
			var arr_role = roleIds.split(',');
			for(var i=0;i<arr_role.length;i++)
			{
				if(current_roleId.lastIndexOf(arr_role[i])!=-1)
				{//包含
					return true;
				}
			}
			return false;//不包含
		}
		function openTree(formname,input_id,input_name,input_parentid)
		{
			showModalDialog('${ctx}/roleManager/getSelfRoleTree.action?form_name='+formname+'&input_name='+input_name+'&input_id='+input_id+'&enableCbx=1',window,'help:no;scroll:yes;status:no;dialogWidth:300px;dialogHeight:400px');
			if("${sessionScope.userSession.isAdmin}"=="0" && roleCompare(document.getElementById(input_id).value))
			{//不能给自己配权限
				document.getElementById('submitButton').style.visibility = 'hidden';
				alert("<eoms:lable name='sm_msg_selfRoleAssignConstraint' />！");
				return;
			}
			else
			{
				document.getElementById('submitButton').style.visibility = 'visible';
			}
		}
		
		/*
		 获得所选择的部门和人员的ID
		*/
		function getItemInfo()
		{
			var str_temp = document.getElementById('tree').contentWindow.getCheckedInfo();
			var arr_temp = str_temp.split(';');
			var depids = arr_temp[0];
			var userids = arr_temp[2];
			document.getElementById('depids').value = depids;
			document.getElementById('userids').value = userids;
		}
		function formSubmit()
		{
			getItemInfo();
			var roleids = document.getElementById('roleids').value;
			var depids = document.getElementById('depids').value;
			var userids = document.getElementById('userids').value;
			if(depids=='' && userids=='')
			{
				alert('<eoms:lable name="sm_msg_chooseDepOrUser" />！');
				return;
			}
			if(roleids=='')
			{
				alert('<eoms:lable name="sm_msg_chooseRole" />！');
				return;
			}
			if(roleids!='')
			{
				document.roleOrgForm.submit();
			}
		}
	</script>
	</head>

	<body>
	  <form action="${ctx}/roleOrgManager/roleOrgSave.action" name="roleOrgForm" method="post">
		<input type="hidden" name="roleids" id="roleids" value=""/>
		<input type="hidden" name="depids" id="depids" value=""/>
		<input type="hidden" name="userids" id="userids" value=""/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name='sm_t_roleOrg'/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<table style="width:100%;height:100%">
					<tr valign="top" style="height:92%">
						<td style="width:50%">
							<fieldset style="border:1px #d2e5fe solid;padding:2px;height:100%">
								<legend><font style="font-weight:bold"><eoms:lable name='com_btn_chooseRole'/></font></legend>
								<div class="blank_tr"></div>
								<div class="tabContent_top" style="height:80%;">
									<table width="98%">
										<tr>
											<td>
												<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_choose'/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'" 
													onclick="openTree('roleOrgForm','roleids','roleNames')" />
											</td>
										</tr>
										<tr>
											<td>
												<textarea id="roleNames" name="roleNames" rows="18" style="width:98%" readonly></textarea>
											</td>
										</tr>
									</table>
								</div>
								<div class="blank_tr"></div>
							</fieldset>
						</td>
						<td style="width:50%">
							<fieldset style="border:1px #d2e5fe solid;padding:2px;height:100%">
								<legend><font style="font-weight:bold"><eoms:lable name='sm_lb_chooseOrg'/></font></legend>
								<div class="blank_tr"></div>
								<div class="tabContent_top" style="height:80%;overflow:hidden">
									<iframe src="${ctx}/common/tools/depListTree.jsp?isRadio=${param.isRadio}&isSelectType=${param.isSelectType}" id='tree' scrolling="no" frameborder="0" width="100%" height="320px"></iframe>
								</div>
								<div class="blank_tr"></div>
							</fieldset>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="formSubmit();" id="submitButton" style="visibility:hidden;"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
