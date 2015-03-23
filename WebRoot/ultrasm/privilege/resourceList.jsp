<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<c:if test="${role_id_page==null}">
			<c:set var="myroleid" scope="page" value="SQL_SM_ResourceList.query"/>
		</c:if>
		<c:if test="${role_id_page!=null}">
			<c:set var="myroleid" scope="page" value="SQL_SM_ResourceList.query_ByRoleId"/>
		</c:if>
		<title><eoms:lable name="sm_t_resourceChoose" /></title>
		<base target="_self"/>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0, LAYOUT_LIST_OPEN);
			}
			window.onload = function() 
			{
			  setCenter(0, LAYOUT_LIST_OPEN);
			  changeRow_color("tab");
			}
			function radioSubmit()
			{
				var radioArr = document.getElementsByName("resradio");
				var temp = '';
				for(var i=0;i<radioArr.length;i++)
				{
					if(radioArr[i].checked)
					{
						temp = radioArr[i].value.split(';');
						break;
					}
				}
				if(temp=='')
				{
					window.close();
				}
				else
				{
					var callerWindowObj = dialogArguments;
					callerWindowObj.document.getElementById('resids').value = temp[0];
					callerWindowObj.document.getElementById('resNames').value = temp[1];
					window.close();
				}
	 		}
		</script>
	</head>
	<body>
		<dg:datagrid var="res" sqlname="${pageScope.myroleid}" ititle="sm_subt_resourceChoose">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
		  		<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" 
		  			  onmouseout="this.className='page_ok_button'"  onclick="radioSubmit();" text='com_btn_confirm' />
		  		<eoms:operate cssclass="page_cancelchanges_button" id="com_btn_canclechanges" onmouseover="this.className='page_cancelchanges_button_hover'" 
		  			  onmouseout="this.className='page_cancelchanges_button'"  onclick="window.close();" text='com_btn_cancel' />
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
					<th width="30"><eoms:lable name="com_btn_choose" /></th>
  					<th width="25%"><eoms:lable name="sm_lb_resourceSystemType" /></th>
  					<th width="25%"><eoms:lable name="sm_lb_resourceDefineType" /></th>
  					<th><eoms:lable name="sm_lb_resourceName" /></th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="res_row">
					<td><input type="radio" name="resradio" value="${pid};${resname}"/></td>
  					<td>${systemtype}</td>
  					<td>${definetype}</td>
  					<td>${resname}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
