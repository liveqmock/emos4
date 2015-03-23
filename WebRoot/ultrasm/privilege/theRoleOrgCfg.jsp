<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='sm_subt_roleOrg' /></title>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0,56);
			}
			window.onload = function() 
			{
			  setCenter(0,56);changeRow_color("tab");
			}
			function roleOrgDel()
			{
				getCheckValue("checkid");
				var ids = document.getElementsByName('var_selectvalues').value;
				document.forms[0].var_selectvalues.value = ids;
				if(ids=='')
				{
					alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
					return;
				}
				if(confirm("<eoms:lable name="com_btn_confirm_del" />"))
				{
					document.forms[0].action = '${ctx}/roleManager/delTheRoleOrg.action?theroleid=${theroleid}';
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="roleOrg" sqlname="SQL_SM_RoleOrgList.theRole_query" ititle="sm_subt_roleOrg" action="">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
		  		<c:choose>
		  			<c:when test="${session.userSession.isAdmin=='1' || !fn:contains(session.userSession.roleId,param.theroleid)}">
				  		<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
				  			  onmouseout="this.className='page_add_button'"  
				  			  onclick="showModalDialog('${ctx}/ultrasm/privilege/theRoleOrgAdd.jsp?theroleid=${theroleid}&isRadio=1&isSelectChild=0&isSelectType=2',window,'help:no;center:true;scroll:no;status:no;dialogWidth:288px;dialogHeight:380px');"
				  			  text="com_btn_add" />
				  		<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
				  			  onmouseout="this.className='page_del_button'"  
				  			  onclick="roleOrgDel()" 
				  			  text="com_btn_delete" />
		  			</c:when>
		  		</c:choose>
			</dg:lefttoolbar>
			<dg:condition>
				<table class="serachdivTable">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" name="button" id="submitButton" value="<eoms:lable name='com_btn_lookUp' />"
								class="searchButton"
								onmouseover="this.className='searchButton_hover'"
								onmouseout="this.className='searchButton'"/>
							<input type="reset" name="button2" id="button2" value="<eoms:lable name='com_btn_reset' />"
								class="ResetButton"
								onmouseover="this.className='ResetButton_hover'"
								onmouseout="this.className='ResetButton'" />
						</td>
					</tr>
				</table>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
					<th width="30%"><eoms:lable name='sm_lb_roleName' /></th>
					<th width="30%"><eoms:lable name='sm_lb_userOrDep' /></th>
			 		<th><eoms:lable name='sm_lb_orgType' /></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="roleOrg_row">
					<td><input name="checkid" type="checkbox"  value='${pid}'></input></td>
					<td>${rolename }</td>
					<td>${orgname }</td>
					<td>
						<eoms:dic value="${orgtype}" dictype="orgtype"></eoms:dic>
					</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>