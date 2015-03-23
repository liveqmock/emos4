<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
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
						
			function myMenuDel()
			{
				getCheckValue("checkid");
				var ids = document.getElementsByName('var_selectvalues').value;
				document.forms[0].var_selectvalues.value = ids;
				if(ids=='')
				{
					alert('<eoms:lable name="sm_msg_rootCpy" />！');
					return;
				}
				if(confirm("<eoms:lable name='com_btn_confirm_del' />"))
				{
					document.forms[0].action = '${ctx}/menuManager/myMenuDel.action';
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="myMenu" sqlname="SQL_SM_MyMenu.query" title="sm_subt_myMenu" action="">
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
					onclick="openwindow('${ctx}/ultrasm/menuinfo/myMenuCfg.jsp','',350,450);"
					text="com_btn_add" operate="com_add_op" />
				<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
				    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
				    id="com_btn_del" onclick="myMenuDel();"/>
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
					<th><eoms:lable name='sm_lb_userFullName' /></th>
					<th><eoms:lable name='sm_lb_nodename' /></th>
					<th><eoms:lable name='sm_lb_nodemark' /></th>
					<th><eoms:lable name='sm_lb_nodeurl' /></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${myMenu_row}">
					<td><input name="checkid" type="checkbox" value='${pid}' /></td>
					<td>${fullname}</td>
					<td>${nodename}</td>
					<td>${nodemark}</td>
					<td>${nodeurl}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
