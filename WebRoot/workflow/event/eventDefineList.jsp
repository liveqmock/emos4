<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
	    function editEventDefine(edid)
	    {  
			openwindow('${ctx}/eventDefine/editEventDefine.action?edid='+edid,'',800,500);
	    }
	    function delEventDefine(){
	    	getCheckValue("checkid");
			var delIds = document.getElementsByName('var_selectvalues').value;
			if(delIds=='') {
				alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
				return;
			}
			if (confirm('<eoms:lable name="com_btn_confirm_del"/>')) {
				var src = '${ctx}/eventDefine/delEventDefine.action?delIds='+delIds;
				document.forms[0].action = src;
				document.forms[0].submit();
			}
		}
	</script>
</head>
<body>
	<dg:datagrid var="eventDefine" sqlname="SQL_WF_EventDefinedList.query" title="${nodePath}">
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
				onclick="openwindow('${ctx}/eventDefine/addEventDefine.action','',800,500);"
				text="com_btn_add" operate="com_add" />
			<eoms:operate onmouseout="this.className='page_del_button'"
				cssclass="page_del_button" text="com_btn_delete"
				onmouseover="this.className='page_del_button_hover'"
				id="com_btn_del" onclick="delEventDefine();" operate="com_delete" />
		</dg:lefttoolbar>
		<dg:condition>
			<table class="serachdivTable">
				<tr>
					<td colspan="6" align="center">
						<input type="submit" name="searchUser" id="searchUser"
							value="<eoms:lable name="com_btn_lookUp" />"
							class="searchButton"
							onmouseover="this.className='searchButton_hover'"
							onmouseout="this.className='searchButton'" />
						<input type="reset" name="resetCondition" id="resetCondition"
							value="<eoms:lable name="com_btn_reset" />" class="ResetButton"
							onmouseover="this.className='ResetButton_hover'"
							onmouseout="this.className='ResetButton'" />
					</td>
				</tr>
			</table>
		</dg:condition>
		<dg:gridtitle>
			<tr>
				<th width="30"><input type="checkbox" onclick="checkAll('checkid')"/></th>
				<th>工单类型</th>
				<th>事件类型</th>
				<th>事件主体</th>
				<th>事件动作</th>
				<th>触发类型</th>
				<th>触发逻辑类型</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${agency_row}" style="cursor: hand">
				<td><input name="checkid" type="checkbox" value='${pid}'/></td>
				<td onclick="editEventDefine('${pid}');">${baseschema}</td>
				<td onclick="editEventDefine('${pid}');"><eoms:dic dictype="EventType" value="${eventtype}" /></td>
				<td onclick="editEventDefine('${pid}');">${eventsubject}</td>
				<td onclick="editEventDefine('${pid}');"><eoms:dic dictype="EventAction" value="${eventaction}" /></td>
				<td onclick="editEventDefine('${pid}');"><eoms:dic dictype="HandleType" value="${handletype}" /></td>
				<td onclick="editEventDefine('${pid}');"><eoms:dic dictype="OperationType" value="${operationtype}" /></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>