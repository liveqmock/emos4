<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_lb_cfgUsers"/></title>
		<c:if test="${param.depid==null || param.depid==''}">
			<c:set var="sqlCon" value="SQL_SM_UserList.defUserDepRelation_nullDepid_query" scope="page"/>
		</c:if>
		<c:if test="${param.depid!=null && param.depid!=''}">
			<c:set var="sqlCon" value="SQL_SM_UserList.defUserDepRelation_query" scope="page"/>
		</c:if>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0, LAYOUT_LIST_OPEN);
			}
			window.onload = function() 
			{
			  setCenter(0, LAYOUT_LIST_OPEN);changeRow_color("tab");
			}
			
			function addUser()
			{
				var i = 0;
				var data = "";
				while(document.getElementById("checkbox"+i) != null)
				{
					if(document.getElementById("checkbox"+i).checked)
					{
						data += "&semi;" + document.getElementById("userid"+i).value + "&comm;" + document.getElementById("loginname"+i).value + "&comm;" + document.getElementById("fullname"+i).value + "&comm;" + document.getElementById("depname"+i).value;
					} 
					i++;
				}
				window.opener.addrowdata(data,'user');
				window.close();
			}
			function selectAll()
			{
				var status = document.getElementById('chkAll').checked;
				var chkArr = document.getElementsByName('cbx');
				for(var i=0;i<chkArr.length;i++)
				{
					chkArr[i].checked = status;
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid  var="userinfo" sqlname="${pageScope.sqlCon}" ititle="sm_lb_cfgUsers">
	  		<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
	  			<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" 
		  			  onmouseout="this.className='page_ok_button'"  onclick="addUser();" text='com_btn_confirm' />
		  		<eoms:operate cssclass="page_cancelchanges_button" id="com_btn_search" onmouseover="this.className='page_cancelchanges_button_hover'" 
		  			  onmouseout="this.className='page_cancelchanges_button'"  onclick="window.close();" text='com_btn_cancel' />
	  		</dg:lefttoolbar>
	  		<dg:condition>
	  			<table class="add_user" width="100%">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" 
								onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
							<input type="reset" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton"
									onmouseover="this.className='ResetButton_hover'"
									onmouseout="this.className='ResetButton'"/>
						</td>
					</tr>
				</table>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
				  	<th width="25"><input type="checkbox" name="chkAll" id="chkAll"  onclick="checkAll('checkid')"/></th>
					<th width="30%"><eoms:lable name="sm_lb_userName"/></th>
					<th width="30%"><eoms:lable name="sm_lb_userFullName"/></th>
					<th><eoms:lable name="sm_lb_department"/></th>
			    </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${userinfo_row}">
				  	<td><input type="hidden" id="userid${userinfo_index}" name="userid${userinfo_index}" value="${pid}"/><input type="checkbox" name="checkid" id="checkbox${userinfo_index}"/></td>
					<td><input type="hidden" id="loginname${userinfo_index}" name="loginname${userinfo_index}" value="${loginname}"/>${loginname}</td>
					<td><input type="hidden" id="fullname${userinfo_index}" name="fullname${userinfo_index}" value="${fullname}"/>${fullname}</td>
					<td><input type="hidden" id="depname${userinfo_index}" name="depname${userinfo_index}" value="${depname}"/>${depname}</td>
			    </tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
