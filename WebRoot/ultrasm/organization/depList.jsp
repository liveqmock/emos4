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
		/*
		 更改用户的启用状态
		*/
		function transStatus(type)
		{
			getCheckValue("checkid");
			var temp = document.getElementsByName('var_selectvalues').value;
			if(temp=='')
			{
				alert('<eoms:lable name="sm_msg_chooseOpObject"/>!');
				return;
			}
			if(confirm("<eoms:lable name="sm_msg_confirmStatus"/>？"))
			{
				document.forms[0].depIds.value = temp;
				document.forms[0].transType.value = type;
				document.forms[0].action = "${ctx}/depManager/transferStatus.action";
				document.forms[0].submit();
			}
		}
		
		function depDel()
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
				document.forms[0].depIds.value = temp;
				document.forms[0].action = "${ctx}/depManager/delDep.action";
				document.forms[0].submit();
			}
		}
	</script>
  </head>
  <body> 
  	<dg:datagrid  var="depinfo" title="${nodePath}" action="" sqlname="SQL_SM_DepList.query" cachemode="sql">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
  		      onmouseout="this.className='page_add_button'" 
  		      onclick="openwindow('${ctx }/depManager/depLoad.action','',800,450);"
  		      text="com_btn_add" operate="com_add"/>
  		  <eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
  		  	  onmouseout="this.className='page_del_button'" onclick="depDel();" text="com_btn_delete" operate="com_delete"/>
  		  <eoms:operate cssclass="page_enabled_button" id="" onmouseover="this.className='page_enabled_button_hover'" 
  		 	  onmouseout="this.className='page_enabled_button'" onclick="transStatus('inuse')"  text="com_btn_inuse" operate="com_inuse"/>
 		  <eoms:operate cssclass="page_stop_button" id="" onmouseover="this.className='page_stop_button_hover'" 
 			  onmouseout="this.className='page_stop_button'" onclick="transStatus('outuse')"  text="com_btn_outuse" operate="com_stop"/>
   		  <eoms:operate cssclass="page_import_button" id="com_btn_import" onmouseover="this.className='page_import_button_hover'" 
   			  onmouseout="this.className='page_import_button'" text="com_btn_import"  onclick="importExcel('${ctx}','IMP_SM_Dep.xml');" operate="com_imp"/>
		  <eoms:operate cssclass="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" 
	      	  onmouseout="this.className='page_output_button'" text="com_btn_export" onclick="exportExcel('${ctx}','SQL_SM_DepList.query','EXP_SM_Dep');" operate="com_exp"/>
	      <eoms:operate cssclass="page_listview_button" id="com_btn_refresh" onmouseover="this.className='page_listview_button_hover'" 
  			  onmouseout="this.className='page_listview_button'" 
  			  onclick="window.location.href('${ctx }/ultrasm/organization/dep_Frame.jsp');" text="sm_btn_treeView"/>
	      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" 
	          onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  		</dg:lefttoolbar>
  		<dg:condition>
	  		<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="depIds" id="depIds" value=""/>
	  				<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchDep" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>  
	       </div>		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th width="15%"><eoms:lable name="sm_lb_department"/></th>
	    		<th><eoms:lable name="sm_lb_fullName"/></th>
	    		<th width="12%"><eoms:lable name="sm_lb_depType"/></th>
	    		<th width="12%"><eoms:lable name="sm_lb_assignee"/></th>
	    		<th width="12%"><eoms:lable name="com_lb_contactMethod"/></th>
	    		<th width="12%"><eoms:lable name="com_lb_status"/></th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${depinfo_row}">
				<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx }/depManager/depLoad.action?depID=${pid}','',800,450);">${depname}</a></td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx }/depManager/depLoad.action?depID=${pid}','',800,450);">${depfullname}</a></td>
		        <td>
		        <a href="javascript:;" onclick="openwindow('${ctx }/depManager/depLoad.action?depID=${pid}','',800,450);">
		        	<eoms:dic value="${deptype}" dictype="deptype"></eoms:dic>
		        </a>
		        </td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx }/depManager/depLoad.action?depID=${pid}','',800,450);">${depassginee}</a></td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx }/depManager/depLoad.action?depID=${pid}','',800,450);">${depphone}</a></td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx }/depManager/depLoad.action?depID=${pid}','',800,450);"><eoms:dic value="${status}" dictype="status"></eoms:dic></a></td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
