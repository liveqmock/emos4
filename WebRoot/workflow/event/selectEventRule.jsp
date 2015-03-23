<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<title>事件定义规则选择页面</title>
	<base target="_self"/>
	<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_LIST_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_LIST_OPEN);changeRow_color("tab");
		}
		function returnRule(){
			getCheckValue("checkid");
			var datas = document.getElementsByName('var_selectvalues').value;
			if(datas == '')
				window.close();
			var dataArray = datas.split(',');
			var rule_id = '';
			var rule_name = '';
			for(var i=0 ; i<dataArray.length ; i++)
			{
				var subData = dataArray[i].split(';');
				if(subData.length < 2)
					continue;
				rule_id += ',' + subData[0];
				rule_name += ',' + subData[1];
			}
			if(rule_id != '')
			{
				rule_id = rule_id.substring(1);
				rule_name = rule_name.substring(1);
			}
			window.dialogArguments.document.getElementById("operationnameid").value = rule_id;
			window.dialogArguments.document.getElementById("operationname").value = rule_name;
			window.close();
		}
	</script>
</head>
<body>
	<dg:datagrid var="eventDefine" sqlname="SQL_WF_EventDefinedList.query_selRule" title="事件定义规则选择">
		<dg:lefttoolbar>
			<eoms:operate cssclass="page_search_button" id="com_btn_search"
				onmouseover="this.className='page_search_button_hover'"
				onmouseout="this.className='page_search_button'"
				onclick="showsearch()" text="com_btn_search" />
			<eoms:operate cssclass="page_add_button" id="com_btn_add"
				onmouseover="this.className='page_add_button_hover'"
				onmouseout="this.className='page_add_button'"
				onclick="returnRule()" text="确定" operate="com_add" />
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
				<th width="30"><input type="checkbox" onclick="checkAll('checkid')"/></th>
				<th>规则名称</th>
				<th>通知类型</th>
				<th>提前/超期时间(分)</th>
				<th>有效开始时间</th>
				<th>有效结束时间</th>
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${agency_row}" style="cursor: hand">
				<td><input name="checkid" type="checkbox" value='${pid};${rulename}'/></td>
				<td>${rulename}</td>
				<td><eoms:dic dictype="noticetype" value="${noticetype}"/></td>
				<td>${timespan}</td>
				<td><eoms:date value="${validstarttime}"/></td>
				<td><eoms:date value="${validendtime}"/></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>