<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String form_name = StringUtils.checkNullString(request.getParameter("form_name"));
	String type_mark = StringUtils.checkNullString(request.getParameter("type_mark"));
	String type_name = StringUtils.checkNullString(request.getParameter("type_name"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head> 
  	<base target="_self"/>
	<%@ include file="/common/core/header_list.jsp"%>
	<title>人员模版类别</title>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0, LAYOUT_LIST_RIGHT);
			}
			
			window.onload = function() 
			{
				setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
			}
			function utTypeSelect()
			{
				getCheckValue("checkid");
				var datas = document.getElementsByName('var_selectvalues').value;
				if(datas == '')
					window.close();
				var dataArray = datas.split(',');
				var type_mark = '';
				var type_name = '';
				for(var i=0 ; i<dataArray.length ; i++)
				{
					var subData = dataArray[i].split(';');
					if(subData.length < 2)
						continue;
					type_mark += ',' + subData[0];
					type_name += ',' + subData[1];
				}
				if(type_mark != '')
				{
					type_mark = type_mark.substring(1);
					type_name = type_name.substring(1);
				}
				var callerWindowObj = dialogArguments;
				<% 
					if(!"".equals(form_name))
					{
						if(!"".equals(type_mark))
						{
				%>
					    	callerWindowObj.document.<%=form_name%>.<%=type_mark%>.value = type_mark;
				<%
						}
						if(!"".equals(type_name))
						{
				%>
							callerWindowObj.document.<%=form_name%>.<%=type_name%>.value = type_name;
				<%
						}
					}
				%>
				window.close();
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="template" sqlname="${sqlquery}" title="人员模版类别选择">
			<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" 
		  			  onmouseout="this.className='page_ok_button'"  onclick="utTypeSelect();" text='com_btn_confirm' />
		  		<eoms:operate cssclass="page_cancelchanges_button" id="com_btn_search" onmouseover="this.className='page_cancelchanges_button_hover'" 
		  			  onmouseout="this.className='page_cancelchanges_button'"  onclick="window.close();" text='com_btn_cancel' />
			</dg:lefttoolbar>
			<dg:gridtitle>
				<tr>
					<th width="25"><input id="checkidAll" name="checkidAll" type="checkbox" onclick="checkAll('checkid')"/></th>
					<th width="40%">工单类别</th>
					<th>工单名称</th>
				</tr>
			</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${template_row}">
					<td><input name="checkid" type="checkbox" value='${baseschema};${name}'></input></td>
					<td>${baseschema}</td>
					<td>${name}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
