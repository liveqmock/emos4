<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		//资源删除
		function transferStatus(){
			var resid = document.getElementsByName('checkid');
			var resids = '';
			for(var i=0;i<resid.length;i++){
				if(resid[i].checked){
					resids += resid[i].value + ',' ;
				}
			}
			if(resids==''){
				alert('<eoms:lable name="sm_msg_chooseOpObject"/>'+'!');
			}else{
				if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
				{
					var temp = resids.lastIndexOf(',');
					resids = resids.substring(0,temp);
					window.location.href = '${ctx}/resManager/transferStatus.action?resids='+resids;
				}
			}
		}
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="resource"  title="${nodePath}" sqlname="SQL_SM_Resource.queryList" cachemode="sql">
	  		<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  			onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  		<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  		  			onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  		<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
  		  			onmouseout="this.className='page_add_button'" 
  		  			onclick="openwindow('${ctx}/resManager/getResourceInfo.action','',800,500);"
  		  			text="com_btn_add" operate="com_add"/>
  		 		<eoms:operate cssclass="page_del_button" id="com_btn_delete" onmouseover="this.className='page_del_button_hover'" 
  		 			onmouseout="this.className='page_del_button'" onclick="transferStatus()"  text="com_btn_delete" operate="com_delete"/>
	     		<eoms:operate cssclass="page_import_button" id="com_btn_import" onmouseover="this.className='page_import_button_hover'" 
	     			onmouseout="this.className='page_import_button'" text="com_btn_import"  onclick="getCheckValue('checkid')" operate="005"/>
	      		<eoms:operate cssclass="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" 
      				onmouseout="this.className='page_output_button'" text="com_btn_export" onclick="exportExcel('${ctx}','SQL_SM_Resource.queryList','EXP_SM_Resource');"/>
	      		<eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" 
	      			onmouseout="this.className='page_help_button'" text="com_btn_help"/>
	  		</dg:lefttoolbar>
	  		<dg:condition>
		  		<div align="center">
			      <table  class="serachdivTable" style="width:80%">
			        <tr>
			          <td colspan="6" align="center">
			          	<input type="submit" name="searchResource" id="searchResource" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
			        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
			          </td>
			        </tr>
			      </table>
			    </div>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="25"><input id="checkidAll" name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
		    		<th width="25%"><eoms:lable name="sm_lb_resourceSystemType"/></th>
		    		<th width="25%"><eoms:lable name="sm_lb_resourceDefineType"/></th>
		    		<th width="25%"><eoms:lable name="sm_lb_resourceName"/></th>
		    		<th><eoms:lable name="com_lb_status"/></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${resource_row}">
					<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resManager/getResourceInfo.action?resourceId=${pid}','',800,500);">${systemtype}</a></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resManager/getResourceInfo.action?resourceId=${pid}','',800,500);">${definetype}</a></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resManager/getResourceInfo.action?resourceId=${pid}','',800,500);">${resname}</a></td>
			   		<td><a href="javascript:;" onclick="openwindow('${ctx}/resManager/getResourceInfo.action?resourceId=${pid}','',800,500);"><eoms:dic dictype="status" value="${status}"/></a>
			        </td>
			    </tr>
			</dg:gridrow>
		</dg:datagrid>
  </body>
</html>
