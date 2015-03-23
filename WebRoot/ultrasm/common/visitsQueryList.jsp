<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>visitsQuery</title>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script>
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		  changeRow_color("tab");
		}
	</script>
	</head>
	<body>
		<dg:datagrid var="visitsQuery" sqlname="SQL_SM_VisitsQueryList.query" title="当前位置：自管理>>功能访问量查询">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'" onclick="showsearch()"
					text='com_btn_search' />
				<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="location.reload();" text="com_btn_refresh" />
				<eoms:operate cssclass="page_help_button" id="com_btn_help"
					onmouseover="this.className='page_help_button_hover'"
					onmouseout="this.className='page_help_button'" text="com_btn_help" />
			</dg:lefttoolbar>
			<dg:condition>
				<div align="center">
					<table class="serachdivTable" style="width: 80%">
						<tr>
							<td colspan="6" align="center">
								<input type="submit" name="searchUser" id="searchUser"
									value="<eoms:lable name="com_btn_search"/>"
									class="searchButton"
									onmouseover="this.className='searchButton_hover'"
									onmouseout="this.className='searchButton'" />
								<input type="reset" name="resetCondition" id="resetCondition"
									value="<eoms:lable name="com_btn_reset"/>" class="ResetButton"
									onmouseover="this.className='ResetButton_hover'"
									onmouseout="this.className='ResetButton'" />
							</td>
						</tr>
					</table>
				</div>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th width="35"><eoms:lable name="com_lb_sequenceNumber"/></th>
					<th width="15%"><eoms:lable name="sm_lb_visitTime"/></th>
					<th width="15%"><eoms:lable name="sm_lb_visitIP"/></th>
					<th width="15%"><eoms:lable name="sm_lb_visitUser"/></th>
					<th><eoms:lable name="sm_lb_visitUrl"/></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${visitsQuery_row}">
					<td>${rowindex}</td>
					<td><eoms:date value="${time}"/></td>
					<td>${ip}</td>
					<td>${loginname}</td>
					<td>${url}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
