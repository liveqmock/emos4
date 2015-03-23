<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<c:set var="myroleid" value="${session.userSession.roleId}" scope="page"/>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0, LAYOUT_LIST_RIGHT);
			}
			window.onload = function() 
			{
			  setCenter(0, LAYOUT_LIST_RIGHT);
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
					document.forms[0].action = '${ctx}/roleResOpManager/delRoleResOp.action';
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="rroShow" sqlname="SQL_SM_RoleResOpList.query" title="${nodePath}" action="">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
				<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="location.reload();" text="com_btn_refresh" />
				<eoms:operate cssclass="page_add_button" id="com_btn_add"
					onmouseover="this.className='page_add_button_hover'"
					onmouseout="this.className='page_add_button'" 
					onclick="openwindow('${ctx}/ultrasm/privilege/roleResOpCfg.jsp','',800,500);"
					text="com_btn_add" operate="com_add_op" />
				<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
				    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
				    id="com_btn_del" onclick="roleResOpDel();"/>
				<eoms:operate cssclass="page_help_button" id="com_btn_help"
					onmouseover="this.className='page_help_button_hover'"
					onmouseout="this.className='page_help_button'" text="com_btn_help" />
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
					<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')" /></th>
					<th width="20%"><eoms:lable name='sm_lb_roleName' /></th>
					<th><eoms:lable name='sm_lb_resourceName' /></th>
					<th width="20%"><eoms:lable name='sm_lb_operateName' /></th>
					<th width="15%"><eoms:lable name='sm_lb_cfgDataPrivilege' /></th>
					<th width="15%"><eoms:lable name='sm_lb_clearDataPrivilege' /></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${rroShow_row}">
					<td>
						<c:choose>
							<c:when test="${session.userSession.isAdmin=='0'}">
								<c:set var="mytag" value="no" scope="page"/>
								<c:forTokens var="var_id" items="${pageScope.myroleid}" delims=",">
									<c:if test="${rolepid==var_id}">
										<input name="checkid" type="checkbox" value='${pid}' disabled/>
										<c:set var="mytag" value="yes" scope="page"/>
									</c:if>
								</c:forTokens>
								<c:if test="${pageScope.mytag=='no'}">
									<input name="checkid" type="checkbox" value='${pid}'/>
								</c:if>
							</c:when>
							<c:otherwise>
								<input name="checkid" type="checkbox" value='${pid}'/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${rolename}</td>
					<td>${resname}</td>
					<td>${opname}</td>
					<c:choose>
						<c:when test="${session.userSession.isAdmin=='1' || !fn:contains(session.userSession.roleId,rolepid)}">
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
