<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
  	<base target="_self"/>
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name="sm_t_dicTypeChoose"/></title>   
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
		}
		function selConfirm()
		{
			var radioArr = document.getElementsByName('radio_res');
			var resname = '';
			for(var i=0;i<radioArr.length;i++)
			{
				if(radioArr[i].checked == true)
				{
					resname = radioArr[i].value;
					break;
				}
			}
			if(resname=='')
			{
				alert('<eoms:lable name="sm_msg_chooseDicType"/>！');
				return;
			}
			var callerWindowObj = dialogArguments;
			callerWindowObj.document.getElementById('field.dtcode').value = resname;
			window.close();
		}
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="dictype" sqlname="SQL_SM_DicTypeList.query" title="sm_t_dicTypeChoose">
	  		<dg:lefttoolbar>
		      <li class="page_search_button"  onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" ><eoms:lable name="com_btn_search"/></li>
		      <li class="page_ok_button" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" onclick="selConfirm();"><eoms:lable name="com_btn_confirm"/></li>
		      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="window.close()"><eoms:lable name="com_btn_cancel"/></li>
	  		</dg:lefttoolbar>
	  		<dg:condition>
			      <table  class="serachdivTable">
			        <tr>
			          <td colspan="6" align="center">
			          	<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
			        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
			          </td>
			        </tr>
			      </table>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="30"><eoms:lable name='com_btn_choose' /></th>
		    		<th width="25%"><eoms:lable name='sm_lb_dtname' /></th>
					<th width="25%"><eoms:lable name='sm_lb_dtcode' /></th>
					<th width="15%"><eoms:lable name='com_lb_status' /></th>
					<th><eoms:lable name='com_lb_remark' /></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${dictype_row}">
					<td><input type="radio" name="radio_res" value="${dtcode}"/></td>
					<td>${dtname}</td>
					<td>${dtcode}</td>
					<td><eoms:dic dictype="status" value="${status}"/></td>
					<td>${remark}</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
  </body>
</html>
