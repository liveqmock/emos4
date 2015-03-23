<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);changeRow_color("tab");
			}
			
	
	function add(){
		src = '${ctx}/agency/add.action';
		window.open(src,'','width=600,height=430,top=150,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
		//修改流程分类
	function edit(id){
		var src = '${ctx}/agency/edit.action?id='+id;
		window.open(src,'','width=600,height=430,top=150,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	
	function del(){
		var objs = document.getElementsByName("checkid");
		var wfString = "";
				for (var i = 0; i < objs.length; i++) {
					if (objs[i].checked == true) {
						wfString += "," + objs[i].value;
					}
				}
		wfString = wfString.substring(1);
		if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && wfString != '') {
			var src = '${ctx}/agency/del.action?delIds='+wfString;
			window.open(src,'','width=400,height=290,top=50,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
	}
	
		</script>

	</head>
	<body>
	
	  <dg:datagrid  var="agency" items="${agencys}" title="${nodePath}">
	  		<dg:lefttoolbar>
		  			<eoms:operate cssclass="page_add_button" id="wf_type_add" onmouseover="this.className='page_add_button_hover'" 
		  			  	onmouseout="this.className='page_add_button'"    onclick="add();"  text="wf_type_add" />
		  			<eoms:operate cssclass="page_del_button" id="wf_type_add" onmouseover="this.className='page_del_button_hover'" 
					  onmouseout="this.className='page_del_button'"    onclick="del();"  text="wf_sort_del" />
	  		</dg:lefttoolbar>
	  		
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="50">
						<input type="checkbox" onclick="checkAll('checkid')"></input>
					</th>
					<th><eoms:lable name="wf_agency_dealname"/></th>
					<th><eoms:lable name="wf_agency_agentname"/></th>
					<th><eoms:lable name="wf_agency_bgdate"/></th>
					<th><eoms:lable name="wf_agency_eddate"/></th>
		        </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${agency_row}" style="cursor: hand">
					<td>
						<input name="checkid" id="checkid" type="checkbox" value='${agency.id}'></input>
					</td>
					<td onclick="edit('${agency.id}');">${agency.dealer}</td>
					<td onclick="edit('${agency.id}');">${agency.agent}</td>
					<td onclick="edit('${agency.id}');"><eoms:date value="${agency.bgDate}"/></td>
					<td onclick="edit('${agency.id}');"><eoms:date value="${agency.edDate}"/></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
		
	<s:form name="sortForm" id="sortForm">
		<input type="hidden" id="wfSort" />
		<input type="hidden" id="wfSortId" name="wfSortId"  value='${wfSortId}' />
	</s:form>
	</body>
</html>
