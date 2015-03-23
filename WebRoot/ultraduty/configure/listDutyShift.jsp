<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<%@taglib uri="/WEB-INF/datagrid" prefix="dg"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
    <title><eoms:lable name="duty_title_shift"/></title>
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
			window.open("enterDutyShiftAdd.action?orgId=${orgId}","","width=600,height=400,top=200,left=300,Location=no,Toolbar=no,Resizable=no,scrollbars=no");
		} else {
			window.open("enterDutyShiftEdit.action?pid="+pid,"","width=600,height=400,top=200,left=300,Location=no,Toolbar=no,Resizable=no,scrollbars=no");
		}
	}
	
	function delShifts(){
    	var str="";
    	$("input:checked[name='checkid']").each(function(){
    		str+=$(this).val().split(',')[0]+",";
    	})
    	if (str == "") {
    		alert("请选择要删除的班次记录！");
    		return false;
    	} else {
    		if(!window.confirm("确认要删除吗?")){
				return false;
			}else{
				window.location="delDutyShift.action?pid="+str+"&orgId=${orgId}";
			}
    	}
    }
    
    function formSubmit()
	{
		var ids = '';
		var values = "";
		//""
		$("input:checked[name='checkid']").each(function(){
    		ids+=$(this).val().split(',')[0]+",";
    		values+=$(this).val().split(',')[1]+",";
    	})
    	if (ids.length >= 1) {
    		values = values.substring(0, values.length - 1);
    	}
		opener.document.getElementById("shiftIds").value = ids;
		opener.document.getElementById("shiftNames").value = values;
		window.close();
	}
	</script>
  </head>
  <body>
  	<dg:datagrid var="dutyShift" items="${dutyShiftList}" title="班次管理">
  		<dg:lefttoolbar>
  		  <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="formSubmit();"><eoms:lable name='com_btn_save'/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin('')"><eoms:lable name='com_btn_add'/></li>
	      <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delShifts();"><eoms:lable name='com_btn_delete'/></li>
  		</dg:lefttoolbar>
    	<dg:gridtitle>
	    	<tr>
	    		<th width="6%"><input name="checkidd" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th>班次名称</th>
	    		<th>日期类型</th>
	    		<th>开始时间</th>
	    		<th>结束时间</th>
	    		<th>班次顺序</th>
	    		<!-- 
	    		<th>值班人数</th>
	    		<th>是否参与交接班</th>
	    		 -->
	    		<th>是否跨天</th>
	    		<th>是否启用</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${dutyShift_row}">
				<td width="5"><input name="checkid" type="checkbox" value="${dutyShift.pid},${dutyShift.shiftname}"/></td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">
		        	${dutyShift.shiftname}
		        	<input type="hidden" name="name" value="${dutyShift.shiftname}" />
		        </td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')"><eoms:dic dictype='dateType' value='${dutyShift.datetype }' isfullname='true'/></td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">${dutyShift.starttime}</td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">${dutyShift.endtime}</td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">${dutyShift.orderno }</td>
		        <!-- 
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">
			        <c:if test="${dutyShift.workflag == 0}">否</c:if>
			        <c:if test="${dutyShift.workflag == 1}">是</c:if>	
		        </td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">${dutyShift.pernum }</td>
		        	 -->
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">${dutyShift.isNextDayName }</td>
		        <td width="10%" onclick="openwin('${dutyShift.pid}')">${dutyShift.hideFlagName }</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
