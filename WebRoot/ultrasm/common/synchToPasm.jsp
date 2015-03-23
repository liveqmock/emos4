<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base target="_self" />
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
		}
		function synchToPasm(type)
		{
			if(type == '')
				return ;
			else if(type == 'user')
				document.forms[0].action = "${ctx}/dataSynch/synchToPasm_User.action";
			else if(type == 'dep')
				document.forms[0].action = "${ctx}/dataSynch/synchToPasm_Dep.action";
			else if(type == 'userdep')
				document.forms[0].action = "${ctx}/dataSynch/synchToPasm_UserDep.action";
			else if(type == 'role')
				document.forms[0].action = "${ctx}/dataSynch/synchToPasm_Role.action";
			document.forms[0].submit();
		}
	</script>
	</head>
	<body>
		<dg:datagrid var="operate" sqlname="" title='${nodePath}'
			cachemode="sql">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_sameadd_button" id="" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'"
					onclick="synchToPasm('user')" text="sm_btn_synchuser" operate="com_inuse" />
				<eoms:operate cssclass="page_sameadd_button" id="" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'"
					onclick="synchToPasm('dep')" text="sm_btn_synchdep" operate="com_inuse" />
				<eoms:operate cssclass="page_sameadd_button" id="" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'"
					onclick="synchToPasm('userdep')" text="sm_btn_synchuserdep" operate="com_inuse" />
			</dg:lefttoolbar>
			<dg:condition>
				<div align="center">
					<table class="serachdivTable">
						<tr>
							<td colspan="6" align="center">
								<input type="submit" name="searchUser" id="searchUser"
									value="<eoms:lable name="com_btn_search"/>"
									class="searchButton"
									onmouseover="this.className='searchButton_hover'"
									onmouseout="this.className='searchButton'" />
								<input type="reset" name="resetCondition" id="resetCondition"
									value="<eoms:lable name="com_btn_reset"/>" class="ResetButton"
									onmouseover="this.className='ResetButton_hover'"
									onmouseout="this.className='ResetButton'" />
							</td>
						</tr>
					</table>
				</div>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th width="15%">类别</th>
					<th width="85%">说明</th>
				</tr>
				<tr>
					<td>用户下行</td>
					<td>将本系统中的用户信息批量初始化同步到pasm系统</td>
				</tr>
				<tr>
					<td>部门下行</td>
					<td>将本系统中的部门信息批量初始化同步到pasm系统</td>
				</tr>
				<tr>
					<td>关系下行</td>
					<td>将本系统中的组用户信息批量初始化同步到pasm系统</td>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
