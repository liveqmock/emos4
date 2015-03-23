<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="/WEB-INF/datagrid" prefix="dg"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
    <title>岗位列表</title>
    <script language="javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
	<script language="javascript">
	window.onresize = function() 
	{
	  setCenter(0,56);
	}
	window.onload = function() 
	{
	  setCenter(0,56);changeRow_color("tab");
	}
	function openwin(pid)
	{
		if (pid != '') {
			window.open("enterDutyOrganization.action?pid="+pid,pid,"width=800,height=500,top=100,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
		} else {
			window.open("dutyOrganizationAdd.action","new","width=800,height=500,top=100,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
		}
	}
	
	function delOrganizations(){
    	var str="";
    	$("input:checked[name='checkid']").each(function(){
    		str+=$(this).val()+",";
    	})
    	if (str == "") {
    		alert("请选择要删除的岗位记录！");
    		return false;
    	} else {
    		if(!window.confirm("确认要删除吗?")){
				return false;
			}else{
				window.location="deleDutyOrganization.action?pid="+str+"&id=${id}";
			}
    	}
    }
	</script>
  </head>
  <body>
  	<dg:datagrid var="dutyOrganization" items="${dutyOrganizationList}" title="${nodePath }">
  		<dg:lefttoolbar>
	      <li class="page_search_button"  onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()"><eoms:lable name='com_btn_search'/></li>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"><eoms:lable name='com_btn_refresh'/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin('')"><eoms:lable name='com_btn_add'/></li>
	      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delOrganizations();"><eoms:lable name='com_btn_delete'/></li>
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"><eoms:lable name='com_btn_help'/></li>
  		</dg:lefttoolbar>
  		<dg:condition>
	      <table  class="serachdivTable">
	        <tr>
				<td class="serachdivTableTd">岗位：</td>
				<td>
					<select name="name" id="name">
						<option value="">--请选择岗位--</option>
						<c:forEach items="${orgList}" var="org">
						   <option value="${org.pid}" <c:if test="${name == org.pid}">selected="selected"</c:if>>
								${org.organizationname}
							</option>
					   </c:forEach>
					</select>
				</td>
				<td class="serachdivTableTd">是否启用：</td>
				<td width="20%">
					<select class="select" name="state">
						<option selected="selected" value="-1">--请选择--</option>
						<c:forEach items="${trueOrFalseMap}" var="map">
							<option value="${map.key }" <c:if test="${state == map.key}">selected="selected"</c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td class="serachdivTableTd">
				</td>
	        </tr>
	        <tr>
				<td colspan="6" align="center">
					<input type="submit" name="button" id="button" value="<eoms:lable name='com_btn_lookUp'/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="showsearch(0)" />
					<input type="reset" name="button2" id="button2" value="<eoms:lable name='com_btn_reset'/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
				</td>
	        </tr>
	      </table>  		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th><input name="checkidd" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th>岗位名称</th>
	    		<th>接班提前时间</th>
	    		<th>交班提前时间</th>
	    		<th>是否启用</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${dutyOrganization_row}" style="cursor: pointer;">
				<td width="5%"><input name="checkid" type="checkbox" value="${dutyOrganization.pid}"/></td>
		        <td width="20%" onclick="openwin('${dutyOrganization.pid}')">${dutyOrganization.organizationname}</td>
		        <td width="15%" onclick="openwin('${dutyOrganization.pid}')">${dutyOrganization.onTimeBef}分</td>
		        <td width="15%" onclick="openwin('${dutyOrganization.pid}')">${dutyOrganization.offTimeBef}分</td>
		        <td width="10%" onclick="openwin('${dutyOrganization.pid}')">${dutyOrganization.stateName }</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
