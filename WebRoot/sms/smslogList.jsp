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
			  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="smsmonitor" sqlname="SQL_SM_SmslogList.query" title="当前位置：短信日志查询" action="">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
		  		<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="location.reload();" text="com_btn_refresh"/>
			</dg:lefttoolbar>
			<dg:condition>
				<table class="serachdivTable">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" name="button" id="submitButton" value="<eoms:lable name='com_btn_lookUp' />"
								class="searchButton"
								onmouseover="this.className='searchButton_hover'"
								onmouseout="this.className='searchButton'"/>
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
					<th width="5%"><eoms:lable name="com_lb_sequenceNumber"/></th>
					<th width="10%">日志ID</th>
					<th width="10%">WF_RECORD ID</th>
					<th width="5%"><eoms:lable name="sm_lb_smgoal"/></th>
					<th width="30%"><eoms:lable name="sm_lb_smcontent"/></th>
					<th width="30%">错误信息</th>
					<th width="8%"><eoms:lable name="sm_lb_smssengtime"/></th>
					<th width="5%"><eoms:lable name="sm_lb_smsenfflag"/></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="smsmonitor_row">
					<td>${rowindex}</td>
					<td>${pid}</td>
					<td>${recordpid}</td>
					<td>${sendobj}</td>
					<td>${smscontent}</td>
					<td>${errorinfo}</td>
					<td>${sendetime}</td>
					<td>${flag}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
