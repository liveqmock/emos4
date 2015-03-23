<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() {
			setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
		}
		function edit(id){
			openwindow('${ctx}/eventRule/editEventRule.action?id='+id,'',1000,500);
        }
		function del(){
			var objs = document.getElementsByName("checkid");
			var wfString = "";
			for (var i = 0; i < objs.length; i++) {
				if (objs[i].checked == true) {
					wfString += "," + objs[i].value;
				}
			}
			wfString = wfString.substring(1);
			if(wfString == '') {
				alert("请选择要删除对象！！！");
				return false;
			}
			if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && wfString != '') {
				var src = '${ctx}/eventRule/delEventRule.action?delIds='+wfString;
				document.forms[0].action = src;
				document.forms[0].submit();
			}
		}	
	</script>
</head>
<body>
	<dg:datagrid var="eventDefine" sqlname="SQL_USLA_EventRule.query" title="${nodePath}">
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
				onclick="openwindow('${ctx}/eventRule/addEventRule.action','',1000,500);"
				text="com_btn_add" operate="com_add" />
			<eoms:operate onmouseout="this.className='page_del_button'"
				cssclass="page_del_button" text="com_btn_delete"
				onmouseover="this.className='page_del_button_hover'"
				id="com_btn_del" onclick="del()" operate="com_delete" />
		</dg:lefttoolbar>
		<dg:condition>
			<table class="serachdivTable">
				<tr>
					<td colspan="6" align="center">
						<input type="submit" name="button" id="submitButton"
							value="<eoms:lable name="com_btn_lookUp" />"
							class="searchButton"
							onmouseover="this.className='searchButton_hover'"
							onmouseout="this.className='searchButton'" />
						<input type="reset" name="button2" id="button2"
							value="<eoms:lable name="com_btn_reset" />" class="ResetButton"
							onmouseover="this.className='ResetButton_hover'"
							onmouseout="this.className='ResetButton'" />
					</td>
				</tr>
			</table>
		</dg:condition>
		<dg:gridtitle>
			<tr>
				<th width="30"><input type="checkbox" onclick="checkAll('checkid')"></input></th>
				<th>规则名称</th>
				<th>所属业务类别</th>
				<th>通知类型</th>
				<th>提前/超期时间(分钟)</th>
				<th>有效开始时间</th>
				<th>有效结束时间</th>
				<th>启用状态</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${agency_row}" style="cursor: hand">
				<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
				<td onclick="edit('${pid}');">${rulename}</td>
				<td onclick="edit('${pid}');"><eoms:dic dictype="ruletype" value="${ruletype}" /></td>
				<td onclick="edit('${pid}');"><eoms:dic dictype="noticetype" value="${noticetype}" /></td>
				<td onclick="edit('${pid}');">${timespan}</td>
				<td onclick="edit('${pid}');"><eoms:date value="${validstarttime}" /></td>
				<td onclick="edit('${pid}');"><eoms:date value="${validendtime}" /></td>
				<td onclick="edit('${pid}');"><eoms:dic dictype="startstatus" value="${status}" /></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
