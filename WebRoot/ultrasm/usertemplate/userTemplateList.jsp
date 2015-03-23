<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head> 
  	<base target="_self"/>
	<%@ include file="/common/core/header_list.jsp"%>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0, LAYOUT_LIST_RIGHT);
			}
			
			window.onload = function() 
			{
				setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
			}
			function userTemplateDelete()
			{
				getCheckValue("checkid");
				var ids = document.getElementsByName('var_selectvalues').value;
				document.forms[0].var_selectvalues.value = ids;
				if(ids=='')
				{
					alert("<eoms:lable name='sm_msg_chooseOpObject'/>!");
					return;
				}
				if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
				{
					document.forms[0].action = "${ctx}/userTemplate/userTemplateDelete.action";
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="template" sqlname="SQL_SM_UserTemplate.query" title='${nodePath}' cachemode="sql">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
	  		  	<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
	  				onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
				<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
	  				onmouseout="this.className='page_add_button'" 
	  				onclick="openwindow('${ctx}/userTemplate/userTemplateInfo.action','',800,550)"
	  				text="com_btn_add" operate="com_add"/>
				<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
					onmouseout="this.className='page_del_button'" onclick="userTemplateDelete();" text="com_btn_delete" operate="com_delete"/>
			</dg:lefttoolbar>
			<dg:condition>
				<div align="center">
				  <table  class="serachdivTable">
					<tr>
					  <td colspan="6" align="center">
					  	<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
						<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
					  </td>
					</tr>
				  </table>
				</div>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th width="25"><input id="checkidAll" name="checkidAll" type="checkbox" onclick="checkAll('checkid')"/></th>
					<th width="25%"><eoms:lable name="sm_lb_templateName"/></th>
					<th width="15%"><eoms:lable name="sm_lb_isShare"/></th>
					<th width="15%"><eoms:lable name="com_lb_status"/></th>
					<th><eoms:lable name="com_lb_desc"/></th>
				</tr>
			</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${template_row}">
					<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/userTemplate/userTemplateInfo.action?utid=${pid}','',800,550)">${templatename}</a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/userTemplate/userTemplateInfo.action?utid=${pid}','',800,550)"><eoms:dic value="${isshare}" dictype="isdefault"/></a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/userTemplate/userTemplateInfo.action?utid=${pid}','',800,550)"><eoms:dic value="${status}" dictype="status"/></a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/userTemplate/userTemplateInfo.action?utid=${pid}','',800,550)">${remark}</a></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
