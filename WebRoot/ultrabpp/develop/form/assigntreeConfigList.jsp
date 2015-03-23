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
			
	
	function addConfigTree()
	{
		var baseSchema = document.getElementById('baseSchema').value;
		var wftype = '${wftype}';
		var src = '${ctx}/ultrabpp/assigntree/configTreeEdit.action?baseSchema='+baseSchema+'&wftype='+wftype; 
		window.open(src,'','width=850px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	
	function editConfig(configid)
	{
		var wftype = '${wftype}';
		var src = '${ctx}/ultrabpp/assigntree/loadFromCustSendTree.action?configID='+configid;
		window.open(src,'','width=850px,height=400px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	function modifyConfig(configid){
		var src = '${ctx}/ultrabpp/assigntree/configInfoEdit.action?configID='+configid;
		window.open(src,'','width=380px,height=180px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}

	function delConfig(configid)
	{
	     if(window.confirm("您确定要删除吗？")){
           	document.getElementById('configID').value = configid;
           	var wftype = '${wftype}';
           	document.getElementById('configForm').action = '${ctx}/ultrabpp/assigntree/delConfigTree.action?wftype='+wftype;
			document.getElementById('configForm').submit();
         } 
	}
	

</script>

</head>
<body>
	<form id="configForm">
		<input type="hidden" id="configID" name="configID"/>
		<input type="hidden" id="baseSchema" name="baseSchema" value="${baseSchema}"/>
		<input type="hidden" id="wftype" name="wftype" value="${wftype}"/>
	</form>
  	<dg:datagrid  var="config" items="${configList}">
		<div class='page_div_bg'>
			<div class='page_div'>
				<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" onclick="addConfigTree();" text='dm_btn_add' />
			</div>
		</div>
    	<dg:gridtitle>
	    	<tr>
	    		<c:if test="${wftype==1}">
					<th>环节描述</th>
					<th>动作名称</th>
				</c:if>
				<th>派发树名称</th>
				<th>选择类型</th>
				<th>显示类型</th>
				<th>操作</th>
			</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr ><c:if test="${wftype==1}">
					<td>
						<a href="javascript:editConfig('${config.id}')">
						${config.stepDesc}
						</a>
					</td>
					<td>
						<a href="javascript:editConfig('${config.id}')">
						${config.actionLabel}
						</a>
					</td>
				</c:if>
				<td>
					<a href="javascript:editConfig('${config.id}')">
					${config.fieldLabel}
					</a>
				</td>
				<td>
					<a href="javascript:editConfig('${config.id}')">
						<c:if test="${config.selectType==0}">部门</c:if>
						<c:if test="${config.selectType==1}">人员</c:if>
						<c:if test="${config.selectType==2}">部门，人员</c:if>
					</a>
				</td>
				<td>
					<a href="javascript:editConfig('${config.id}')">
						<c:if test="${config.tabShow=='0'}">全部</c:if>
						<c:if test="${config.tabShow=='1'}">只显示组织机构</c:if>
						<c:if test="${config.tabShow=='2'}">只显示自定义派发数</c:if>
					</a>
				</td>
				<td>
					<a href="javascript:;"  style="color:blue;" onclick="modifyConfig('${config.id}');"><eoms:lable name="bpp_develop_fix_update"/></a>
                   	<a href="javascript:;"  style="color:blue;" onclick="delConfig('${config.id}');"><eoms:lable name="bpp_develop_fix_delete"/></a>
				</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
				