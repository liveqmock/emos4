<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
<%@ include file="/common/core/taglibs.jsp"%>   
 

<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/main_popup.css" />
<link type="text/css" rel="stylesheet" href="/eoms/common/style/blue/css/popup.css" />
<%@ include file="/common/plugin/jquery/jquery.jsp" %>
<script type="text/javascript" src="${ctx}/common/javascript/selectTag.js"></script>
  

    <title>查询列表</title>
		
	<script language="javascript">
	var refreshParentpage="yes";
	    if(refreshParentpage="${isfresh}")
	    {
	      if(window.opener!=null)
		     window.opener.location.reload();
		  window.close();
	    }
	    
	window.onresize = function() 
	{
	  setCenter(0,56);
	}
	window.onload = function() 
	{
	  setCenter(0,56);
	  changeRow_color("tab");
	}
	
	function openwin()
		{
			window.open("${ctx}/common/demo/editByValidation.jsp");
		}
	</script>

  </head>
  <body>
  	<dg:datagrid  var="book"  title="com_t_title" sqlname="SQL_SM_QuerySimple_001">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" text="com_btn_search" />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin()" text="com_btn_add" operate="com_add_op" />
	      <eoms:operate cssclass="page_import_button" id="com_btn_import" onmouseover="this.className='page_import_button_hover'" onmouseout="this.className='page_import_button'" text="com_btn_import"  onclick="getCheckValue('checkid')" operate="com_imp_op"/>
	      <eoms:operate cssclass="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" onmouseout="this.className='page_output_button'" text="com_btn_export" operate="com_exp_op"/>
	      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  		  
	      <!--  
	      <li class="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()" >搜索</li>
	      <li class="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();">刷新</li>
	      <li class="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin()">新增</li>
	      <li class="page_edit_button" id="com_btn_edit" onmouseover="this.className='page_edit_button_hover'" onmouseout="this.className='page_edit_button'" onclick="openwin()">编辑</li>
	      <li class="page_del_button" id="com_btn_delete" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'">删除</li>
	      <li class="page_import_button" id="com_btn_import" onmouseover="this.className='page_import_button_hover'" onmouseout="this.className='page_import_button'">导入</li>
	      <li class="page_output_button" id="com_btn_exp" onmouseover="this.className='page_output_button_hover'" onmouseout="this.className='page_output_button'">导出</li>
	      <li class="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'">帮助</li>
	       -->
  		</dg:lefttoolbar>
  		<dg:condition>
  		
	      <table  class="serachdivTable">
	      <!-- 
	          <td class="serachdivTableTd">人员姓名：</td>
	          <td><input type="text" name="fullname"   class="textInput" value="dd"/></td>
	          <td class="serachdivTableTd">资源名称：</td>
	          <td><input type="text" name="textfield3" id="textfield3"  class="textInput"/></td>
	          <td class="serachdivTableTd">全名：</td>
	          <td><input type="text" name="textfield2" id="textfield2" class="textInput"/></td>
	        </tr>
	        <tr>
	          <td class="serachdivTableTd">用户职位：</td>
	          <td><input type="text" name="textfield2" id="textfield2" class="textInput"/></td>
	          <td class="serachdivTableTd">配置角色资源：</td>
	          <td><input type="text" name="textfield2" id="textfield2" class="textInput"/></td>
	          <td class="serachdivTableTd"></td>
	          <td></td>
	        </tr>
	       -->
	        <tr>
	          <td colspan="6" align="center">
	          	<input type="submit" name="button" id="button" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="" />
	          	<input type="submit" name="button2" id="button2" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
	          </td>
	        </tr>
	      </table>
	       		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th><input name="checkid" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th>PID</th>
	    		<th>loginname</th>
	    		<th>fullname</th>
	    		<th>lastlogintime</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${book_row}" namea="${book_row}">
				<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
		        <td width="20%" ><a href="load.action?isbn=${book.isbn}">${pid}</a></td>
		        <td width="40%">${loginname}</td>
		        <td width="20%">${fullname}</td>
		        <td width="20%"><eoms:date value="${lastlogintime}"/></td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	
	<script>
	//style="display:none">
		//alert(document.getElementById("com_btn_search").style.display);
		//document.getElementById("com_btn_add").style.display="none";
	</script>
  </body>
</html>
