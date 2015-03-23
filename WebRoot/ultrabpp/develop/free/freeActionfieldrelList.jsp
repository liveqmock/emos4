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
		setCenter(0,30);
	}
			
	
	 
	function addStep(){
	}
	
	
	function addFreeActionFieldrel(value){
		var idAndType=value.split('*');
		var id=idAndType[0];
		var type=idAndType[1];
		var baseSchema = document.getElementById('base').value;
		var src = '${ctx}/ultrabpp/develop/editFreeActionfieldrel.action?baseSchema='+baseSchema+'&fieldType='+type+'&fieldID='+id;
		window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		document.getElementById('fieldTypeId').selectedIndex = '0';
	}
	
	
	function addActionFieldrel(value){
		var idAndType=value.split('*');
		var id=idAndType[0];
		var type=idAndType[1];
		var baseSchema = document.getElementById('base').value;
		var src = '${ctx}/ultrabpp/develop/editFreeActionfieldrel.action?baseSchema='+baseSchema+'&fieldType='+type+'&action=action'+'&fieldID='+id;
		window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		document.getElementById('actionTypeId').selectedIndex = '0';
	}
	
	function editFreeActionFieldrel(id,baseSchema,actionType)
	{
		if(actionType == "")
		{
			var src = '${ctx}/ultrabpp/develop/editFreeActionfieldrel.action?baseSchema='+baseSchema+'&id='+id;
			window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
		if(actionType != "")
		{
			var src = '${ctx}/ultrabpp/develop/editFreeActionfieldrel.action?baseSchema='+baseSchema+'&id='+id+'&action=action';
			window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
	}
	
	function delFreeActionFieldrel(Did,baseSchema)
	{
		 if(window.confirm("您确定要删除吗？")){
	     	document.getElementById('DbaseSchema').value = baseSchema;
           	document.getElementById('Did').value = Did;
           	document.getElementById('DferrActionForm').action = '${ctx}/ultrabpp/develop/delFreeActionfieldrel.action';
			document.getElementById('DferrActionForm').submit();
         } 
	}
	
	function rollBackFreeActionfieldrel(Rid,baseSchema)
	{
		 if(window.confirm("您确定要恢复吗？")){
	     	document.getElementById('RbaseSchema').value = baseSchema;
           	document.getElementById('Rid').value = Rid;
           	document.getElementById('RferrActionForm').action = '${ctx}/ultrabpp/develop/rollBackFreeActionfieldrel.action';
			document.getElementById('RferrActionForm').submit();
         } 
	}
	
	function deploy(baseSchema)
	{
	    var src = '${ctx}/ultrabpp/develop/deployFreeActionField.action?baseSchema='+baseSchema;	  
	    window.open(src);
	}
		
	
</script>
	</head>
	<body>
		<input type="hidden" id="base" value="${baseSchema}" />
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">${nodePath}</span> </span>
			</div>
		</div>
		<dg:datagrid var="freeActionFieldRelation" items="${freeActionfieldrelList}">
			<div class='page_div_bg'>
				<div class='page_div'>
					<li>
						<s:select id="fieldTypeId" cssClass="paddingHack" name="fieldType" list="baseFieldList" onchange="addFreeActionFieldrel(this.value);" listKey="id" listValue="label">
						</s:select>
					</li>
					<li>
						<s:select id="actionTypeId" cssClass="paddingHack" name="actionType" list="actionFieldFreeList" onchange="addActionFieldrel(this.value);" listKey="id" listValue="label">
						</s:select>
					</li>
					<li>
						<input type="button" value="部署" onclick="deploy('${baseSchema}')" />
					</li>
				</div>
			</div>
			<dg:gridtitle>
				<tr>
					<th>
						<eoms:lable name="bpp_develop_fix_baseSchema" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_isFree" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_status" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_visiable" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_required" />
					</th>
					<th>
						<eoms:lable name="plan_lb_type" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_label" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_actionName" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_oprate" />
					</th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr>
					<td style="display: none;">
						<input type="hidden" name="freeActionFieldRelation.id" id="freeActionFieldRelation.id" value="${freeActionFieldRelation.id}" />
					</td>
					<td>
						${freeActionFieldRelation.baseSchema}
					</td>
					<td>
						${freeActionFieldRelation.actionType}
					</td>
					<td>
						${freeActionFieldRelation.baseStatus}
					</td>
					<td>
						<c:if test="${freeActionFieldRelation.visiable == 1}">
						是
					</c:if>
						<c:if test="${freeActionFieldRelation.visiable == 0}">
						否
					</c:if>
					</td>
					<td>
						<c:if test="${freeActionFieldRelation.required == 1}">
						是
					</c:if>
						<c:if test="${freeActionFieldRelation.required == 0}">
						否
					</c:if>
					</td>
					<td>
						${freeActionFieldRelation.fieldType}
					</td>
					<td>
						${freeActionFieldRelation.baseField.label}
					</td>
					<td>
						${freeActionFieldRelation.baseField.fieldName}
					</td>
					<td>
						<a href="javascript:;" onclick="editFreeActionFieldrel('${freeActionFieldRelation.id}','${freeActionFieldRelation.baseSchema}','${freeActionFieldRelation.actionType}');"> <eoms:lable name="bpp_develop_fix_update" /> </a>
						<a href="javascript:;" onclick="delFreeActionFieldrel('${freeActionFieldRelation.id}','${freeActionFieldRelation.baseSchema}');"> <eoms:lable name="bpp_develop_fix_delete" /> </a>
					</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
		<s:form name="DferrActionForm" id="DferrActionForm">
			<input type="hidden" name="baseSchema" id="DbaseSchema" />
			<input type="hidden" name="id" id="Did" />
		</s:form>
		<s:form name="RferrActionForm" id="RferrActionForm">
			<input type="hidden" name="baseSchema" id="RbaseSchema" />
			<input type="hidden" name="id" id="Rid" />
		</s:form>
	</body>
</html>
