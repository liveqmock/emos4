<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/jsp/common/js/util.js"></script>
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
		var baseSchema = getJQueryId("baseSchema").val();
		var actionType = getJQueryId("actionType").val();
		var stepCode = getJQueryId("stepCode").val();
		src = "${ctx}/ownFields/add.action?baseSchema=" + baseSchema + "&stepCode="+stepCode+"&actionType="+actionType;
		window.open(src,'','width=900,height=630,top=150,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
		//修改流程分类
	function edit(id){
		var src = '${ctx}/ownFields/edit.action?id='+id;
		window.open(src,'','width=900,height=630,top=150,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
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
			var src = '${ctx}/ownFields/del.action?delIds='+wfString;
			window.open(src,'','width=400,height=290,top=50,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
	}
	
	function preview() {
		var baseSchema = getJQueryId("baseSchema").val();
		var actionType = getJQueryId("actionType").val();
		var stepCode = getJQueryId("stepCode").val();
		var src = '${ctx}/workflow/sheet/query/previewDealInfo.jsp?baseSchema='+baseSchema+ "&stepCode="+stepCode+"&actionType="+actionType;
		window.open(src);
	}
	
	function chgSelect() {
		var baseSchema = getJQueryId("baseSchema").val();
		var actionType = getJQueryId("actionType").val();
		var stepCode = getJQueryId("stepCode").val();
		var treeId = getJQueryId("treeId").val();
		var url = "${ctx}/ownFields/list.action?baseSchema=" + baseSchema + "&id=" +treeId + "&stepCode="+stepCode+"&actionType="+actionType;
		window.open(url,'_self');
	}
	
		</script>

<%
	HashMap map = new LinkedHashMap();
	map.put("sheet", "sheet");
	for(int i=0;i<50;i++) {
		String key = "dp_" + i;
		map.put(key, key);
	}
	request.setAttribute("map",map);
 %>
	</head>
	<body>
	
	  <dg:datagrid  var="ownFields" items="${ownFieldses}" title="${nodePath}">
	  		<dg:lefttoolbar>
		  			<eoms:operate cssclass="page_add_button" id="wf_type_add" onmouseover="this.className='page_add_button_hover'" 
		  			  	onmouseout="this.className='page_add_button'"    onclick="add();"  text="wf_type_add" />
		  			<eoms:operate cssclass="page_del_button" id="wf_type_add" onmouseover="this.className='page_del_button_hover'" 
					  onmouseout="this.className='page_del_button'"    onclick="del();"  text="wf_sort_del" />
					 <li><s:select onchange="chgSelect()" name="baseSchema" id="baseSchema" list="wftypes" listKey="code" listValue="name" emptyOption="false"></s:select></li>
					 <li><s:select onchange="chgSelect()" name="stepCode" list="#request.map" headerKey="" headerValue="请选择环节号"></s:select></li>
					 <li><s:select onchange="chgSelect()" name="actionType" id="actionType" list="dicItems" listKey="divalue" listValue="diname" headerKey="" headerValue="请选择流程动作"></s:select></li>
					 <eoms:operate cssclass="page_refresh_button" id="preview" onmouseover="this.className='page_refresh_button_hover'" 
					  onmouseout="this.className='page_refresh_button'"    onclick="preview()"  text="预览" />
	  		</dg:lefttoolbar>
	  		
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="50">
						<input type="checkbox" onclick="checkAll('checkid')"></input>
					</th>
					<th><eoms:lable name="wf_ownfield_name"/></th>
					<th><eoms:lable name="wf_ownfield_code"/></th>
					<th><eoms:lable name="wf_ownfield_type"/></th>
					<th><eoms:lable name="wf_ownfield_order"/></th>
					<th><eoms:lable name="wf_ownfield_printline"/></th>
					<th><eoms:lable name="wf_ownfield_isprint"/></th>
		        </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${ownFields_row}" style="cursor: hand">
					<td>
						<input name="checkid" id="checkid" type="checkbox" value='${ownFields.id}'></input>
					</td>
					<td onclick="edit('${ownFields.id}');">${ownFields.fieldName}</td>
					<td onclick="edit('${ownFields.id}');">${ownFields.fieldCode}</td>
					<td onclick="edit('${ownFields.id}');"><eoms:dic value="${ownFields.fieldType}" dictype="fieldType"></eoms:dic></td>
					<td onclick="edit('${ownFields.id}');">${ownFields.printOrder}</td>
					<td onclick="edit('${ownFields.id}');"><eoms:dic value="${ownFields.printOneLine}" dictype="isdefault"></eoms:dic></td>
					<td onclick="edit('${ownFields.id}');"><eoms:dic value="${ownFields.isPrint}" dictype="isdefault"></eoms:dic></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
		
	<s:form name="sortForm" id="sortForm">
	</s:form>
	</body>
</html>
