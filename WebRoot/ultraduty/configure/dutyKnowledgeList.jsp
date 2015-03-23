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
    <title>常识列表</title>
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
		if (pid == '') {
			window.open("enterDutyKnowledgeAdd.action","new","width=500,height=330,top=200,left=300,Location=no,Toolbar=no,Resizable=no,scrollbars=no");
		} else {
			window.open("enterDutyKnowledgeEdit.action?pid="+pid,pid,"width=500,height=330,top=200,left=300,Location=no,Toolbar=no,Resizable=no,scrollbars=no");
		}
	}
	
	function delKnowledges(){
    	var str="";
    	$("input:checked[name='checkid']").each(function(){
    		str+=$(this).val()+",";
    	})
    	if (str == "") {
    		alert("请选择要删除的常识！");
    		return false;
    	} else {
    		if(!window.confirm("确认要删除吗?")){
				return false;
			}else{
				window.location.href="delKnowledge.action?pid="+str+"&id=${id}";
			}
    	}
    }
	</script>
  </head>
  <body>
  	<dg:datagrid var="dgk" items="${dgkList}" title="${nodePath }">
  		<dg:lefttoolbar>
	      <li class="page_search_button"  onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()"><eoms:lable name='com_btn_search'/></li>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"><eoms:lable name='com_btn_refresh'/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin('')"><eoms:lable name='com_btn_add'/></li>
	      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delKnowledges();"><eoms:lable name='com_btn_delete'/></li>
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"><eoms:lable name='com_btn_help'/></li>
  		</dg:lefttoolbar>
  		<dg:condition>
	      <table  class="serachdivTable">
	        <tr>
				<td class="serachdivTableTd">常识标题：</td>
				<td>
					<input type="text" id="title" name="dgkInfo.title" value="${dgkInfo.title }"/>
				</td>
				<td class="serachdivTableTd">常识内容：</td>
				<td>
					<input type="text" id="contents" name="dgkInfo.contents" value="${dgkInfo.contents }"/>
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
	    	<tr align="center">
	    		<th><input name="checkidd" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th>常识标题</th>
	    		<th>常识内容</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${dgk_row}">
				<td width="5"><input type="checkbox" name="checkid" value="${dgk.pid}"/></td>
				<td width="30%" onclick="openwin('${dgk.pid}')">${dgk.title}</td>
				<td width="60%" onclick="openwin('${dgk.pid}')">
					<c:if test="${fn:length(dgk.contents) > 20}">${fn:substring(dgk.contents, 0, 20)}……</c:if>
					<c:if test="${fn:length(dgk.contents) <= 20}">${dgk.contents}</c:if>
      			</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
