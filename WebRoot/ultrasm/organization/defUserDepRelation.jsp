<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_lb_addMembers"/></title>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0,50);
			}
			window.onload = function() 
			{
			  setCenter(0,50);
			  changeRow_color("tab");
			}
			/*
			 保存所有选中的人员，提交表单到后台
			*/
			function saveDef()
			{
				getCheckValue('cbx');
				var userids = document.getElementsByName('var_selectvalues').value;
				if(userids=='')
				{
					alert('<eoms:lable name="sm_msg_chooseOpObject"/>！');
				}
				else
				{
					document.forms[0].var_selectvalues.value = userids;
					document.forms[0].action = "${ctx}/depUserRelation/saveDepUser.action?depid=${depid}";
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid  var="userInfo" sqlname="SQL_SM_UserList.defUserDepRelation_query" ititle="sm_lb_addMembers" action="">
	  		<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
		  		<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" 
		  			  onmouseout="this.className='page_ok_button'"  onclick="saveDef();" text='com_btn_confirm' />
		  		<eoms:operate cssclass="page_cancelchanges_button" id="com_btn_search" onmouseover="this.className='page_cancelchanges_button_hover'" 
		  			  onmouseout="this.className='page_cancelchanges_button'"  onclick="window.close();" text='com_btn_cancel' />
	  		</dg:lefttoolbar>
	  		<dg:condition>
	  			<table class="add_user">
					<tr>
						<td colspan="6" align="center">
							<input type="hidden" name="userids" id="userids" value=""/>
							<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        			<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
						</td>
					</tr>
				</table>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
					<th width="25"><input type="checkbox" name="chkAll" id="chkAll"  onclick="checkAll('cbx')"/></th>
					<th width="30%"><eoms:lable name="sm_lb_userName"/></th>
					<th width="30%"><eoms:lable name="sm_lb_userFullName"/></th>
					<th><eoms:lable name="sm_lb_department"/></th>
		        </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${userInfo_row}">
					<td><input type="checkbox" name="cbx" value="${pid}"/></td>
					<td>${loginname}</td>
					<td>${fullname}</td>
					<td>${depname}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>