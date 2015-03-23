<%@ page language="java" import="java.util.*,com.ultrapower.eoms.common.core.util.StringUtils,com.ultrapower.eoms.common.constants.PropertiesUtils,com.ultrapower.eoms.common.core.web.ActionContext" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow: auto;">
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
<script language="javascript">
window.onresize = function()
{
	setCenter(0,30);
}
window.onload = function()
{
	setCenter(0,30);
}
function reserchpege(){
		showsearch()	
	}
function editSort(id){
	if(id == "")
	{
		alert("<eoms:lable name='sm_msg_seletefirst'/>!");
		return false;
	}
	parent.window.open("${ctx}/serverQuest/addserverQuestInfo.action?id="+id+"&levertype=","rightFrame2");
}	
	
	</script>
	
  </head>
  <body >

 	<dg:datagrid sqlname="SQL_SERVERQUESTLIST.query" var="seq" inmap="${sqlwhere}">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'"  onclick="reserchpege()" text='com_btn_search' />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		</dg:lefttoolbar>
  		<dg:condition>
	  		<div align="center">
		      <table  class="serachdivTable" style="width:80%;display:'none';">
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
	    		<th width="20%">服务请求名称</th>
	    		<th width="10%">是否vip</th>
	    		<th width="10%">服务处理责任处室</th>
	    		<th width="15%">承办处管理负责人(行员）</th>
	    		<th width="10%">承办处处长</th>
	    		<th width="15%">运行中心副主任</th>
	    		
	    	</tr> 
    	</dg:gridtitle>
		<dg:gridrow>	
			<tr class="${ywxt_row}">
		        <td><a href="javascript:;" onclick="editSort('${pid}');">${serverquestname}</a></td>
		        <td><a href="javascript:;" onclick="editSort('${pid}');"><c:if test="${vipflog eq '1' }">是</c:if><c:if test="${vipflog ne '1' }">否</c:if></a></td>
		        <td><a href="javascript:;" onclick="editSort('${pid}');">${audit_0_name}</a></td>
	        	<td><a href="javascript:;" onclick="editSort('${pid}');">${audit_1_name}</a></td>
	        	<td><a href="javascript:;" onclick="editSort('${pid}');">${audit_2_name}</a></td>	        		        	        
		        <td><a href="javascript:;" onclick="editSort('${pid}');">${audit_3_name}</a></td>
		     
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
	</html>