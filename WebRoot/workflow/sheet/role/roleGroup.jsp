<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<script src="${ctx}/common/javascript/util.js"></script>
		<script src="${ctx}/workflow/sheet/role/js/roleUser.js"></script>

		<title><eoms:lable name="wf_sheet_dimensions_title"/></title>

	<script type="text/javascript">
		window.onresize = function() 
		{
		  setCenter(0,32);
		}
		function formsubmit(){
			var rstr =	document.getElementById('usertree').contentWindow.getSelectData('element', 'D', 1);
			if(rstr==''){
				document.getElementById("groupStr").value="";
			}else{
				document.getElementById("groupStr").value= rstr.substring(2);
			}
			document.getElementById("sheetform").submit();
		}
	</script>
	
	</head>
	<body>
		<div class="content">
			<div class="add_scroll_div_x" id="center">
					<table class="add_user" style="height: 95%;width: 100%" border="0">
						<tr>
							<td width="100%" valign="top" style="border:solid #666666 1px">
								<div id="div1" style="width:100%;height:100%">
									<iframe
										src="${ctx}/common/tools/orgtree/organizaTree.jsp?useMode=frame&isRadio=0&isSelectType=0&targetDataArr=${groupStr}"
										frameborder="0" id="usertree" name="center" style="width: 290px; height: 430px;"></iframe>
								</div>

							</td>
						</tr>
					</table>
			</div>
			
			
			<div class="add_bottom">	
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="formsubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
		</div>
		
		<form id="sheetform" action="${ctx}/sheetrole/addUser.action" method="post" target="_self">
			<s:hidden id="roleCode" name="roleCode" />
			<s:hidden id="childRoleId" name="childRoleId" />
			<s:hidden id="userName" name="userName" />
			<s:hidden id="charge" name="charge" />
			<s:hidden id="groupStr" name="groupStr"/>
		</form>
		
		<script type="text/javascript">
			window.onload = function(){
			
				init();
			}
		</script>
	</body>
</html>
