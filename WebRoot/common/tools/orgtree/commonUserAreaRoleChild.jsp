<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/header_list.jsp"%>
	<script type="text/javascript" src="${ctx}/common/tools/orgtree/js/commonTree.js"></script>
	<script language="javascript">
	window.onresize = function() {
		setCenter(0, LAYOUT_LIST_OPEN);
	}
	window.onload = function() {
		setCenter(0, LAYOUT_LIST_OPEN);
		changeRow_color("tab");
	}
	function setResultArea(sourceObj, crId, crName) {
		if(sourceObj.checked) {
			bottomFrame.contentWindow.addOneResult(crId, crName, 'R');
		} else {
			bottomFrame.contentWindow.delOneResult(crId);
		}
	}
	function cancelSelect(crid) {
		var checkbox = document.getElementById(crid);
		if(checkbox != null)
			document.getElementById(crid).checked = '';
	}
</script>
</head>
<body>
	<dg:datagrid var="childRole" items="${crList}" pagetype="simple" title="角色细分选择">
		<dg:lefttoolbar>
			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"
				onclick="showsearch()" text='com_btn_search' />
		</dg:lefttoolbar>
		<dg:condition>
			<div align="center">
				<input type="hidden" id="rolecode" name="rolecode" value="${rolecode}"/>
				<table class="serachdivTable">
					<tr>
						<td class="add_table_left">
							角色细分名：
						</td>
						<td class="add_table_right" style="width: 22%">
							<input type="text" name="childRoleName" id="childRoleName" class="textInput" value="${childRoleName}" />
						</td>
						<td class="serachdivTableTd"></td>
					</tr>
					<tr>
						<td colspan="7" align="center">
							<input type="submit" name="button" id="button" value="<eoms:lable name='com_btn_lookUp'/>" class="searchButton" 
								onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="showsearch(0);" />
							<input type="reset" name="button2" id="button2" value="<eoms:lable name='com_btn_reset'/>" class="ResetButton"
								onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'" />
						</td>
					</tr>
				</table>
			</div>
		</dg:condition>
		<dg:gridtitle>
			<tr>
				<th width="25px"><input id="checkidAll" name="checkidAll" type="checkbox" onclick="checkAll('checkid')" disabled/></th>
				<th>角色细分名称</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${childRole_row}">
				<td><input id="R_${childRole.childRoleId}" name="checkid" type="checkbox" onclick="setResultArea(this, 'R_${childRole.childRoleId}', '${childRole.childRoleName}');"></input></td>
				<td>${childRole.childRoleName}</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
