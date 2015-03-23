<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_lb_cfgSysRole"/></title>
		<c:if test="${param.orgid==null || param.orgid==''}">
			<c:set var="sqlCon" value="SQL_SM_RoleList.query" scope="page"/>
		</c:if>
		<c:if test="${param.orgid!=null && param.orgid!=''}">
			<c:set var="sqlCon" value="SQL_SM_RoleList.notNullOrgId_query" scope="page"/>
		</c:if>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0, LAYOUT_LIST_OPEN);
			}
			window.onload = function() 
			{
			  setCenter(0, LAYOUT_LIST_OPEN);changeRow_color("tab");
			}
			function addRole()
			{
				var i = 0;
				var data = "";
				while(document.getElementById("checkbox"+i) != null)
				{
					if(document.getElementById("checkbox"+i).checked)
					{
						data += "&semi;" + document.getElementById("roleid"+i).value +"&comm;"+document.getElementById("roletype"+i).value +"&comm;"+document.getElementById("rolename"+i).value;
					} 
					i++;
				}
				window.opener.addrowdata(data,'role');
				window.close();
			}
			
		</script>
	</head>
	<body>
		<dg:datagrid  var="roleinfo" sqlname="${pageScope.sqlCon}" ititle="sm_lb_cfgSysRole">
	  		<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
	  			<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" 
		  			  onmouseout="this.className='page_ok_button'"  onclick="addRole();" text='com_btn_confirm' />
		  		<eoms:operate cssclass="page_cancelchanges_button" id="com_btn_search" onmouseover="this.className='page_cancelchanges_button_hover'" 
		  			  onmouseout="this.className='page_cancelchanges_button'"  onclick="window.close();" text='com_btn_cancel' />
	  		</dg:lefttoolbar>
	  		<dg:condition>
	  			<table class="add_user">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" 
								onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
							<input type="reset" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton"
									onmouseover="this.className='ResetButton_hover'"
									onmouseout="this.className='ResetButton'"/>
						</td>
					</tr>
				</table>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="25"><input type="checkbox" name="chkAll" id="chkAll" onclick="checkAll('checkid')"/></th>
					<th width="45%"><eoms:lable name="sm_lb_roleType"/></th>
					<th><eoms:lable name="sm_lb_roleName"/></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${roleinfo_row}">
					<td><input type="hidden" id="roleid${roleinfo_index}" name="roleid${roleinfo_index}" value="${pid}"/><input type="checkbox" name="checkid" id="checkbox${roleinfo_index}"/></td>
					<td><input type="hidden" id="roletype${roleinfo_index}" name="roletype${roleinfo_index}" value="${definetype}"/>${definetype}</td>
					<td><input type="hidden" id="rolename${roleinfo_index}" name="rolename${roleinfo_index}" value="${rolename}"/>${rolename}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
