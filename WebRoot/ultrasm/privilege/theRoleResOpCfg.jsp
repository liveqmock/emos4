<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='sm_lb_roleResOpCfg' /></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0,56);
			}
			window.onload = function() 
			{
			  setCenter(0,56);
			  changeRow_color("tab");
			}
			
			function openwin()
			{
				window.open("${ctx}/common/demo/editByValidation.jsp");
			}
			
			function clearPrivilege(rroId)
			{
				if(confirm("<eoms:lable name='sm_msg_clearOffPrivilege' />？"))
				{
					//document.forms[0].action = '${ctx}/roleResOpManager/clearPrivilege.action?rroId=' + rroId;
					//document.forms[0].submit();
					$.get('${ctx}/roleResOpManager/clearPrivilege.action',{rroId:rroId},function(result){
						if(result=='true')
						{
							alert("<eoms:lable name='sm_msg_rolePriClearSuccess' />！");
						}
						else
						{
							alert("<eoms:lable name='sm_msg_rolePriClearErr' />！");
						}
					});
				}
			}
			
			function setOpPrivilege(rroId,allowCfg)
			{
				openwindow('${ctx}/roleResOpManager/getOpPrivilege.action?rroId=' + rroId+'&allowCfg='+allowCfg,'',800,500);
			}
						
			function roleResOpDel()
			{
				getCheckValue("checkid");
				var ids = document.getElementsByName('var_selectvalues').value;
				document.forms[0].var_selectvalues.value = ids;
				if(ids=='')
				{
					alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
					return;
				}
				if(confirm("<eoms:lable name='com_btn_confirm_del' />"))
				{
					document.forms[0].action = '${ctx}/roleManager/delTheRoleResOp.action?theroleid=${theroleid}&parentid=${parentid}';
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="rroShow" sqlname="SQL_SM_RoleResOpList.theRole_query" ititle="sm_lb_roleResOpCfg" action="">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
				<c:choose>
		  			<c:when test="${session.userSession.isAdmin=='1' || !fn:contains(session.userSession.roleId,param.theroleid)}">
						<eoms:operate cssclass="page_add_button" id="com_btn_add"
							onmouseover="this.className='page_add_button_hover'"
							onmouseout="this.className='page_add_button'" 
							onclick="showModalDialog('${ctx}/ultrasm/privilege/theRoleResOpAdd.jsp?theroleid=${theroleid}&parentid=${parentid}&isRadio=1&isSelectChild=0&isSelectType=2',window,'help:no;center:true;scroll:no;status:no;dialogWidth:650px;dialogHeight:450px');"
							text="com_btn_add" operate="com_add_op" />
						<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
						    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
						    id="com_btn_del" onclick="roleResOpDel();"/>
					</c:when>
				</c:choose>
			</dg:lefttoolbar>
			<dg:condition>
				<table class="serachdivTable" style="width:60%" align="center">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" name="button" id="button" value="<eoms:lable name='com_btn_lookUp' />"
								class="searchButton"
								onmouseover="this.className='searchButton_hover'"
								onmouseout="this.className='searchButton'" onclick="" />
							<input type="submit" name="button2" id="button2" value="<eoms:lable name='com_btn_reset' />"
								class="ResetButton"
								onmouseover="this.className='ResetButton_hover'"
								onmouseout="this.className='ResetButton'" />
						</td>
					</tr>
				</table>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th width="25"><input name="checkid" type="checkbox" onclick="checkAll('checkid')" /></th>
					<th width="20%"><eoms:lable name='sm_lb_roleName' /></th>
					<th><eoms:lable name='sm_lb_resourceName' /></th>
					<th width="20%"><eoms:lable name='sm_lb_operateName' /></th>
					<th width="15%"><eoms:lable name='sm_lb_cfgDataPrivilege' /></th>
					<th width="15%"><eoms:lable name='sm_lb_clearDataPrivilege' /></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${rroShow_row}">
					<td><input name="checkid" type="checkbox" value='${pid}' /></td>
					<td>${rolename}</td>
					<td>${resname}</td>
					<td>${opname}</td>
					<c:choose>
						<c:when test="${session.userSession.isAdmin=='1' || !fn:contains(session.userSession.roleId,param.theroleid)}">
							<td><a href="javascript:;" onclick="setOpPrivilege('${pid}','true')" style="color:blue"><u><eoms:lable name='sm_lb_clickToCfg' /></u></a></td>
							<td><a href="javascript:;" onclick="clearPrivilege('${pid}','true')" style="color:blue"><u><eoms:lable name='sm_lb_clearPrivilege' /></u></a></td>
						</c:when>
						<c:otherwise>
							<td><a href="javascript:;" onclick="setOpPrivilege('${pid}','false');" style="color:blue"><u><eoms:lable name='sm_lb_clickToCheck' /></u></a></td>
							<td><a href="javascript:;" style="color:gray"><u><eoms:lable name='sm_lb_clearPrivilege' /></u></a></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
