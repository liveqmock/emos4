<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
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
			
			function clearPrivilege(rroId)
			{
				document.forms[0].action = '${ctx}/roleResOpManager/clearPrivilege.action?rroId=' + rroId;
				document.forms[0].submit();
			}
			
			function openwin()
			{
				window.open("${ctx}/common/demo/editByValidation.jsp");
			}
			
			function clearPrivilege(rroId)
			{
				if(confirm("<eoms:lable name='sm_msg_clearOffPrivilege' />？"))
				{
					document.forms[0].action = '${ctx}/roleResOpManager/clearPrivilege.action?rroId=' + rroId;
					document.forms[0].submit();
				}
			}
			
			function setOpPrivilege(rroId)
			{
				window.open('${ctx}/roleResOpManager/getOpPrivilege.action?rroId=' + rroId,'','location=no,toolbar=no,resizable=yes,scrollbars=no,width=800,height=500,top=100,left=200');
			}
						
			function roleResOpDel()
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
					document.forms[0].action = '${ctx}/roleResOpManager/delRoleResOp.action';
					document.forms[0].submit();
				}
			}
			function openwindow(url,name,iWidth,iHeight)
			{
				var url; 
				var name; 
				var iWidth; 
				var iHeight; 
				var iTop = (window.screen.availHeight-30-iHeight)/2; 
				var iLeft = (window.screen.availWidth-10-iWidth)/2; 
				window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="dictype" sqlname="SQL_SM_DicTypeList.query" title="${nodePath}" action="">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
				<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="location.reload();" text="com_btn_refresh" />
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
					<th width="20%"><eoms:lable name='sm_lb_dtname' /></th>
					<th width="20%"><eoms:lable name='sm_lb_dtcode' /></th>
					<th width="10%"><eoms:lable name='sm_lb_dictype' /></th>
					<th width="10%"><eoms:lable name='com_lb_status' /></th>
					<th width="30%"><eoms:lable name='com_lb_remark' /></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${dictype_row}">
					<td><a href="javascript:;" onclick="openwindow('${ctx}/dicManager/dicInfo.action?cfgpage=0&type=1&dicId=${pid}:${dtcode}','',800,500);">${dtname}</a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/dicManager/dicInfo.action?cfgpage=0&type=1&dicId=${pid}:${dtcode}','',800,500);">${dtcode}</a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/dicManager/dicInfo.action?cfgpage=0&type=1&dicId=${pid}:${dtcode}','',800,500);"><eoms:dic dictype="dictype" value="${dictype}"/></a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/dicManager/dicInfo.action?cfgpage=0&type=1&dicId=${pid}:${dtcode}','',800,500);"><eoms:dic dictype="status" value="${status}"/></a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/dicManager/dicInfo.action?cfgpage=0&type=1&dicId=${pid}:${dtcode}','',800,500);">${remark}</a></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
