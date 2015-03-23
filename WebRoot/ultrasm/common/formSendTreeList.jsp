<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0,56);
			}
			window.onload = function() 
			{
			  setCenter(0,56);changeRow_color("tab");
			}
			
			//删除
			function del()
			{
				var pid = document.getElementsByName('checkid');
				var pids = '';
				for(var i=0;i<pid.length;i++){
					if(pid[i].checked){
						pids += pid[i].value + ',' ;
					}
				}
				if(pids==''){
					alert('<eoms:lable name="sm_msg_chooseOpObject"/>'+'!');
				}else{
					if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
					{
						var temp = pids.lastIndexOf(',');
						pids = pids.substring(0,temp);
						window.location.href = '${ctx}/formCustSendTree/del.action?pids='+pids;
					}
				}
			}
			
			//状态修改
			function transStatus(type)
			{
				var pid = document.getElementsByName('checkid');
				var pids = '';
				for(var i=0;i<pid.length;i++){
					if(pid[i].checked){
						pids += pid[i].value + ',' ;
					}
				}
				if(pids==''){
					alert('<eoms:lable name="sm_msg_chooseOpObject"/>'+'!');
				}else{
					if(confirm("<eoms:lable name="sm_msg_confirmStatus"/>？"))
					{
						var temp = pids.lastIndexOf(',');
						pids = pids.substring(0,temp);
						window.location.href = '${ctx}/formCustSendTree/transStatus.action?type='+type+'&pids='+pids;
					}
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="formSender" sqlname="SQL_SM_FormCustSendTreeList.query" title="sm_subt_formCustSenderTree" action="">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
				<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="location.reload();" text="com_btn_refresh" />
				<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
	  				onmouseout="this.className='page_add_button'" 
	  				onclick="openwindow('#','',1000,600);"
	  				text="com_btn_add" operate="com_add"/>
	  			<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'"
					onmouseout="this.className='page_del_button'" onclick="del();" text="com_btn_delete" operate="com_delete"/>
				<eoms:operate cssclass="page_enabled_button" id="" onmouseover="this.className='page_enabled_button_hover'" 
	 				onmouseout="this.className='page_enabled_button'" onclick="transStatus('inuse')"  text="com_btn_inuse" operate="com_inuse"/>
	 			<eoms:operate cssclass="page_stop_button" id="" onmouseover="this.className='page_stop_button_hover'" 
	 				onmouseout="this.className='page_stop_button'" onclick="transStatus('outuse')"  text="com_btn_outuse" operate="com_stop"/>
				<eoms:operate cssclass="page_help_button" id="com_btn_help"
					onmouseover="this.className='page_help_button_hover'"
					onmouseout="this.className='page_help_button'" text="com_btn_help" 
					/>
			</dg:lefttoolbar>
			<dg:condition>
				<table class="serachdivTable" style="width:60%" align="center">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" name="button" id="button" value="<eoms:lable name='com_btn_lookUp' />"
								class="searchButton"
								onmouseover="this.className='searchButton_hover'"
								onmouseout="this.className='searchButton'" onclick="" />
							<input type="submit" name="button2" id="button2" value="<eoms:lable name='com_btn_reset' />"
								class="ResetButton"
								onmouseover="this.className='ResetButton_hover'"
								onmouseout="this.className='ResetButton'" />
						</td>
					</tr>
				</table>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
					<th width="30%"><eoms:lable name="sm_lb_smname"/></th>
					<th width="30%"><eoms:lable name="sm_lb_formTypeTree"/></th>
					<th width="20%"><eoms:lable name="com_lb_orderNum"/></th>
					<th width="20%"><eoms:lable name="com_lb_status"/></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${formSender_row}">
					<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
					<td>${treename}</td>
					<td>${baseschema}</td>
					<td>${ordernum}</td>
					<td><eoms:dic dictype="status" value="${status}"/></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
