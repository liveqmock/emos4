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
		src = '${ctx}/dealGroup/add.action';
		window.open(src,'','width=600,height=430,top=150,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
		//修改流程分类
	function edit(id){
		var src = '${ctx}/dealGroup/edit.action?id='+id;
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
			var src = '${ctx}/dealGroup/del.action?delIds='+wfString;
			window.open(src,'','width=400,height=290,top=50,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
	}
	
		</script>

	</head>
	<body>
	
	  <dg:datagrid  var="dealGroup" items="${dealGroups}" title="${nodePath}">
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
					<th><eoms:lable name="wf_dealgroup_name"/></th>
					<th><eoms:lable name="wf_dealgroup_groupid"/></th>
					<th><eoms:lable name="wf_dealgroup_groupname"/></th>
					<th><eoms:lable name="wf_dealgroup_grouptype"/></th>
					<th><eoms:lable name="wf_dealgroup_isenable"/></th>
					<th><eoms:lable name="com_lb_createTime"/></th>
		        </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${dealgroup_row}" style="cursor: hand">
					<td>
						<input name="checkid" id="checkid" type="checkbox" value='${dealGroup.id}'></input>
					</td>
					<td onclick="edit('${dealGroup.id}');">${dealGroup.name}</td>
					<td onclick="edit('${dealGroup.id}');">${dealGroup.groupId}</td>
					<td onclick="edit('${dealGroup.id}');">${dealGroup.groupName}</td>
					<td onclick="edit('${dealGroup.id}');"><eoms:dic value="${dealGroup.groupType}" dictype="actorType"></eoms:dic></td>
					<td onclick="edit('${dealGroup.id}');"><eoms:dic value="${dealGroup.isEnable}" dictype="status"></eoms:dic></td>
					<td onclick="edit('${dealGroup.id}');"><eoms:date value="${dealGroup.createTime}"/></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
		
	<s:form name="sortForm" id="sortForm">
	</s:form>
	</body>
</html>
