<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@ include file="/common/core/taglibs.jsp"%>
    <title><eoms:lable name="sm_t_role" /></title>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			var roleid = '${roleInfo.pid}';
			var parentroleid = '${roleInfo.parentid}';
			if(roleid!='' && parentroleid=='0')
			{
				document.getElementById('roleInfoParentname').value = '<eoms:lable name="sm_lb_rootNoParent" />！';
			}
			if(roleid!='')
			{
				if("${sessionScope.userSession.isAdmin}"=="0" && "${sessionScope.userSession.roleId}".lastIndexOf(roleid)!=-1)
				{//不能修改自己所属角色的信息
					$("input:not(#cancelButton)").attr("disabled","disabled");
					$("select").attr("disabled","disabled");
					$("textarea").attr("disabled","disabled");
				}
			}
		}
		function openRoleTree(formname,input_id,input_name,type)
		{
			if(formname!='undefined' && input_id!='undefined' && type=='parent')
			{
				showModalDialog('${ctx}/roleManager/getSelfRoleTree.action?form_name='+formname+'&input_name='+input_name+'&input_id='+input_id,window,'help:no;scroll:yes;status:no;dialogWidth:300px;dialogHeight:400px');
			}
			if(formname!='undefined' && input_id!='undefined' && type=='copy')
			{
				var parentid = document.getElementById('roleInfoParentid').value;
				showModalDialog('${ctx}/ultrasm/privilege/roleOpenRadioTree.jsp?form_name='+formname+"&input_name="+input_name+"&input_id="+input_id+"&parentid="+parentid,window,'help:no;scroll:yes;status:no;dialogWidth:300px;dialogHeight:400px');
			}
		}
		function selectRole(type)
		{
			if(type=='parent')
			{
				openRoleTree('roleForm','roleInfoParentid','roleInfoParentname',type);
				document.getElementById('copyPrivilege').value = '';
				document.getElementById('copyPrivilege_show').value = '';
			}
			if(type=='copy')
			{
				if(document.getElementById('roleInfoParentname').value=='')
				{
					alert('<eoms:lable name="sm_msg_rootCpy" />！');
					return;
				}
				openRoleTree('roleForm','copyPrivilege','copyPrivilege_show',type);
			}
		}
		function openMenuTree()
		{
			var parentid = '${roleInfo.parentid}';
			if(parentid=='0')
			{
				alert('<eoms:lable name="sm_msg_rootBanMenu" />！');
				return;
			}
			var id = '${roleInfo.pid}';
			showModalDialog('${ctx}/ultrasm/privilege/menuTree.jsp?roleid='+parentid+'&id='+id,window,'help:no;scroll:yes;status:no;dialogWidth:400px;dialogHeight:400px');
		}
		function basicInfoSubmit(type)
		{
			document.getElementById('saveType').value = type;
			if(Validator.Validate(document.forms[0],2))
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
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="sm_t_role"/>
					</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_lb_roleInfoManage"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<div id="div1"><!-- 角色基本信息 -->
							<form name="roleForm" action="${ctx}/roleManager/saveRole.action" method="post">
								<input type="hidden" id="roleInfo.pid" name="roleInfo.pid" value="${roleInfo.pid}"/>
							  	<input type="hidden" id="roleInfo.roledns" name="roleInfo.roledns" value="${roleInfo.roledns}"/>
							  	<input type="hidden" id="roleInfo.roledn" name="roleInfo.roledn" value="${roleInfo.roledn}"/>
							  	<input type="hidden" id="roleInfo.creater" name="roleInfo.creater" value="${roleInfo.creater}"/>
							  	<input type="hidden" id="roleInfo.createtime" name="roleInfo.createtime" value="${roleInfo.createtime}"/>
							  	<input type="hidden" id="roleInfo.lastmodifier" name="roleInfo.lastmodifier" value="${roleInfo.lastmodifier}"/>
							  	<input type="hidden" id="roleInfo.lastmodifytime" name="roleInfo.lastmodifytime" value="${roleInfo.lastmodifytime}"/>
								<input type="hidden" name="saveType" id="saveType" value=""/>
								<table class="add_user">
									<tr>
										<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_roleName" />：<span class="must">*</span></td>
										<td style="width:35%">
											<input type="text" id="roleInfo.rolename" name="roleInfo.rolename" class="textInput" value="${roleInfo.rolename}" />
											<validation id="roleInfo.rolenameV" dataType="Custom" regexp="^.{1,20}$" msg="<eoms:lable name='sm_msg_roleNameConstraint' />！" />
										</td>
										<td class="texttd" width="15%"><eoms:lable name="sm_lb_resourceDefineType" />：<span class="must">*</span></td>
										<td width="35%">
										  <input type="text" id="roleInfo.definetype" name="roleInfo.definetype" class="textInput" value="${roleInfo.definetype}" />
											<validation id="roleInfo.definetypeV" dataType="Custom" regexp="^.{1,20}$" msg="<eoms:lable name='sm_msg_roleDefTypeConstraint' />！" />
										</td>
									</tr>
									<tr>
										<td class="texttd"><eoms:lable name="sm_lb_parentRole" />：<span class="must">*</span></td>
										<td>
											<c:choose>
												<c:when test="${roleInfo.pid==null}">
													<input  type="hidden" name="roleInfo.parentid" id="roleInfoParentid" class="textInput" value="0" />
													<input  readonly type="text" name="roleInfoParentname" id="roleInfoParentname" class="textInput" value=""/>
												</c:when>
												<c:otherwise>
													<input readonly type="hidden" id="roleInfo.parentid" name="roleInfo.parentid" class="textInput" value="${roleInfo.parentid}"/>
													<input readonly type="text" name="roleInfoParentname" id="roleInfoParentname" class="textInput" value="${parentRoleName}" />
												</c:otherwise>
											</c:choose>
											<validation id="roleInfoParentnameV" dataType="Require" msg="<eoms:lable name='sm_msg_roleParentConstraint' />！" />
										</td>
										<td>
											<c:if test="${roleInfo.pid==null}">
												<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
														class="operate_button"
														onmouseover="this.className='operate_button_hover'"
														onmouseout="this.className='operate_button'" 
														onclick="selectRole('parent');"/>
											</c:if>
										</td>
										<td></td>
									</tr>
									<tr>
										<c:if test="${roleInfo.pid==null}">
											<td class="texttd"><eoms:lable name="sm_lb_copyRole" />：</td>
											<td>
												<input type="hidden"  readonly name="copyPrivilege" id="copyPrivilege" class="textInput" value=""/>
												<input type="text"  readonly name="copyPrivilege_show" id="copyPrivilege_show" class="textInput" value="" 
													   title="<eoms:lable name='sm_msg_dblclickClearTextField' />！" ondblclick="this.value=''"/>
											</td>
											<td>
												<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
														class="operate_button"
														onmouseover="this.className='operate_button_hover'"
														onmouseout="this.className='operate_button'" 
														 onclick="selectRole('copy')"/>
											</td>
											<td></td>
										</c:if>
									</tr>
									<tr>
										<td class="texttd" valign="top" valign="top"><eoms:lable name="com_lb_desc" />：</td>
										<td colspan="3">
											<textarea name="roleInfo.remark" rows="3" class="textInput" style="width:98.7%">${roleInfo.remark }</textarea>
											<validation id="roleInfo.remarkV" dataType="Limit" max="150" msg="<eoms:lable name='sm_msg_roleRemarkConstraint' />！" />
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
				onmouseout="this.className='save_button'" onclick="basicInfoSubmit('close');"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" id="cancelButton"/>
		</div>
  </body>
</html>
