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
		function categoryDel()
		{
			getCheckValue("checkid");
			var temp = document.getElementsByName('var_selectvalues').value;
			document.getElementsByName('var_selectvalues')[0].value = temp;
			if(temp=='')
			{
				alert("<eoms:lable name='sm_msg_chooseOpObject'/>!");
				return;
			}
			if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
			{
				document.forms[0].action = "${ctx}/indexCategoryManager/delCategory.action";
				document.forms[0].submit();
			}
		}
	</script>
  </head>
  <body> 
  	<dg:datagrid  var="category" title="${nodePath}" action="" sqlname="SQL_FTR_IndexCategoryList.query">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
  		      onmouseout="this.className='page_add_button'" 
  		      onclick="openwindow('${ctx}/indexCategoryManager/categoryLoad.action','',850,500);"
  		      text="com_btn_add" operate="com_add"/>
  		  <eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
  		  	  onmouseout="this.className='page_del_button'" onclick="categoryDel();" text="com_btn_delete" operate="com_delete"/>
	      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" 
	          onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  		</dg:lefttoolbar>
  		<dg:condition>
	  		<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
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
	    		<th width="25%"><eoms:lable name="ftr_lb_indexCategoryName"/></th>
	    		<th width="15%"><eoms:lable name="ftr_lb_isPhysicalCategory"/></th>
	    		<th width="15%"><eoms:lable name="ftr_lb_isAddHLTText"/></th>
	    		<th><eoms:lable name="ftr_lb_HLTTextPath"/></th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${category_row}">
				<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx}/indexCategoryManager/categoryLoad.action?pid=${pid}','',850,500);">${displayname}</a></td>
		        <td>
			        <a href="javascript:;" onclick="openwindow('${ctx}/indexCategoryManager/categoryLoad.action?pid=${pid}','',850,500);">
			        	<eoms:dic value="${isphysical}" dictype="isdefault"></eoms:dic>
			        </a>
		        </td>
		        <td>
			        <a href="javascript:;" onclick="openwindow('${ctx}/indexCategoryManager/categoryLoad.action?pid=${pid}','',850,500);">
			        	<eoms:dic value="${isaddhlttext}" dictype="isdefault"></eoms:dic>
			        </a>
		        </td>
		        <td><a href="javascript:;" onclick="openwindow('${ctx}/indexCategoryManager/categoryLoad.action?pid=${pid}','',850,500);">${hlttextpath}</a></td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>