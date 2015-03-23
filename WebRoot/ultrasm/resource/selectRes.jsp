<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
  	<base target="_self"/>
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name='sm_lb_chooseResource' /></title>   
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_OPEN);
		}
		
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_OPEN);changeRow_color("tab");
		}
		function selConfirm()
		{
			var radioArr = document.getElementsByName('radio_res');
			var resid = '';
			var resname = '';
			for(var i=0;i<radioArr.length;i++)
			{
				if(radioArr[i].checked == true)
				{
					var temp = radioArr[i].value.split(';');
					resid = temp[0];
					resname = temp[1];
					break;
				}
			}
			if(resid==''||resname=='')
			{
				alert('<eoms:lable name="sm_msg_chooseResourceConstraint" />！');
				return;
			}
			var callerWindowObj = dialogArguments;
			callerWindowObj.document.getElementById('resUrl.resid').value = resid;
			callerWindowObj.document.getElementById('resUrl.resname').value = resname;
			window.close();
		}
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="resource" sqlname="SQL_SM_Resource.queryList" ititle="sm_subt_chooseResource">
	  		<dg:lefttoolbar>
		      <li class="page_search_button"  onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" ><eoms:lable name="com_btn_search"/></li>
		      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"><eoms:lable name="com_btn_refresh"/></li>
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
		    		<th width="30"><eoms:lable name="com_btn_choose"/></th>
		    		<th width="28%"><eoms:lable name="sm_lb_resourceSystemType"/></th>
		    		<th width="28%"><eoms:lable name="sm_lb_resourceDefineType"/></th>
		    		<th width="28%"><eoms:lable name="sm_lb_resourceName"/></th>
		    		<th><eoms:lable name="com_lb_status"/></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${resource_row}">
					<td><input name="radio_res" type="radio" value='${pid};${resname}'></input></td>
			        <td>${systemtype}</td>
			        <td>${definetype}</td>
			        <td>${resname}</td>
			   		<td>    
				   		<c:choose>
				        	<c:when test="${status==1}">
				        		<eoms:lable name="com_btn_inuse"/>
				        	</c:when>
				        	<c:otherwise>
				        		<eoms:lable name="com_btn_outuse"/>
				        	</c:otherwise>
				        </c:choose>
			        </td>
			    </tr>
			</dg:gridrow>
		</dg:datagrid>
  </body>
</html>
