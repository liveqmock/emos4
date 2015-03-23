<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);changeRow_color("tab");
			}
			function comfirmDelRelation()
			{
				getCheckValue("checkid");
				var ids = document.getElementsByName('var_selectvalues').value;
				if(ids=='')
				{
					alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
					return;
				}
				if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
				{
					window.location.href('${ctx}/depUserRelation/delDepUser.action?depid=${depid}&userid='+ids);
				}
			}
			function userDel()
			{
				getCheckValue("checkid");
				var temp = document.getElementsByName('var_selectvalues').value;
				if(temp=='')
				{
					alert("<eoms:lable name='sm_msg_chooseOpObject'/>!");
					return;
				}
				if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
				{
					document.forms[0].ids.value = temp;
					document.forms[0].action = "${ctx}/userManager/userDel.action?src=userDepRelation_right";
					document.forms[0].submit();
				}
			}
		</script>

	</head>
	<body>
	  <dg:datagrid  var="userInfo" sqlname="SQL_SM_UserList.query_UDR" ititle="sm_subt_userDep">
	  		<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
				<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'"
					  onclick="openwindow('${ctx}/depUserRelation/addDepUser.action?depid=${depid}','',600,500);" text="sm_btn_addrelation" operate="com_add"/>
				<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'"
					  onclick="comfirmDelRelation();" text="sm_btn_delrelation"/>
				<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'"
					  onclick="openwindow('${ctx}/userManager/addGroupUser.action?depid=${depid}','',1000,600);" text="sm_btn_createuser" operate="com_add"/>
				<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'"
					  onclick="userDel();" text="sm_btn_deleteuser"/>
	  		</dg:lefttoolbar>
	  		<dg:condition>
	  			<input type="hidden" name="ids" id="userID" value=""/>
	  			<input type="hidden" name="depid" id="depid" value="${depid}"/>
	  			<table class="add_user">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" 
								onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
							<input type="reset" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton"
									onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
						</td>
					</tr>
				</table>
				<div class="blank_tr"></div>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
					<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
					<th width="35%"><eoms:lable name="sm_lb_userName"/></th>
					<th width="35%"><eoms:lable name="sm_lb_fullName"/></th>
					<th><eoms:lable name="com_lb_status"/></th>
		        </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${userInfo_row}">
					<td><input name="checkid" type="checkbox" value="${pid}"></input></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/userManager/userLoad.action?userID=${pid}&systemVar=2','',1000,600);">${loginname}</a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/userManager/userLoad.action?userID=${pid}&systemVar=2','',1000,600);">${fullname}</a></td>
					<td><eoms:dic value="${status}" dictype="status"/></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
