<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.component.rquery.startup.StartUp"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<%@ include file="/common/core/taglibs.jsp"%>   
    <title>sql refresh</title>
	<script>
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		  changeRow_color("tab");
		}	
		
		function allRefresh()
		{
			document.getElementById("allfile").value="1";
			document.forms[0].submit();
		}
		
		function refresh(filename)
		{
			document.getElementById("filename").value=filename;
			document.forms[0].submit();
		}
	</script>
  </head>
  
  <body>
  	<%
  	String filename=StringUtils.checkNullString(request.getParameter("filename"));
  	String allfile=StringUtils.checkNullString(request.getParameter("allfile"));
  	System.out.println("----------->"+allfile);
  	if(allfile.equals("1"))
  	{
  		StartUp.loadFile("");//刷新全部文件
  	}
  	else if(!filename.equals(""))
  	{
  		StartUp.loadXml("",filename);
  	}	

  	DataTable dt= StartUp.sqlDt;
  	request.setAttribute("dt",dt);
  	%>
    	<dg:datagrid  var="userinfo" title="当前位置：自管理>>查询SQL刷新" datatable="${dt}" mempage="true">
	  		<dg:lefttoolbar>
	  		<!-- 
	  	  		<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  			onmouseout="this.className='page_search_button'"  onclick="" text='com_btn_search' />
  		  	 -->	
  		  		<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  		  			onmouseout="this.className='page_refresh_button'" onclick="allRefresh()" text="com_btn_refresh"/>
	      		<eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" 
	      			onmouseout="this.className='page_help_button'" text="com_btn_help"/>
	  		</dg:lefttoolbar>
	  		<dg:condition>
					<div>
						<input name="allfile" id="allfile" type="hidden" value="2" />
						<input name="filename" id="filename" type="hidden" />
					</div>	  		
		  		<div align="center">
			      <table  class="serachdivTable" style="width:80%">
			        <tr>
			          <td colspan="6" align="center">
			          	<input type="hidden" name="ids" id="userID" value=""/>
						<input type="hidden" name="transType" value=""/>
			          	<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
			        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
			          </td>
			        </tr>
			      </table>
			    </div>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="35"><eoms:lable name="com_lb_sequenceNumber"/></th>
		    		<th>SQL_Name</th>
		    		<th><eoms:lable name="com_lb_filename"/></th>
		    		<th width="35%"><eoms:lable name="com_lb_filepath"/></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${userinfo_row}">
			       <td>${rowindex}</td>
			       <td>${sqlname}</td>
			       <td>${filename}</td>
			       <td>${path}</td>
			    </tr>
			</dg:gridrow>
		</dg:datagrid>		
  </body>
</html>
