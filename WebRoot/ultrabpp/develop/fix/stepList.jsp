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
			
	function addStep()
	{
		
		var baseSchema = document.getElementById('base').value;
		var src = '${ctx}/ultrabpp/develop/editStep.action?baseSchema='+baseSchema+'&operate=add';
		window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	
	function editStep(stepNo,baseSchema)
	{
		var src = '${ctx}/ultrabpp/develop/editStep.action?baseSchema='+baseSchema+'&stepNo='+stepNo;
		window.open(src,'','width=550px,height=400px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	function deployStep(stepNo,baseSchema)
	{
		var src = '${ctx}/ultrabpp/develop/deployStep.action?baseSchema='+baseSchema+'&stepNo='+stepNo;
		window.location.href=src;
	}
	
	function delStep(stepNo,baseSchema)
	{
	     if(window.confirm("您确定要删除吗？")){
	     	document.getElementById('baseSchema').value = baseSchema;
           	document.getElementById('stepNo').value = stepNo;
           	document.getElementById('stepForm').action = '${ctx}/ultrabpp/develop/delStep.action';
			document.getElementById('stepForm').submit();
         } 
	}
	
	function delStepTop()
	{
		alert("请选择要删除的数据");
	}
	
	function rollBack(stepNo,baseSchema)
	{
		 if(window.confirm("您确定要恢复吗？")){
	     	document.getElementById('baseSchemaRoll').value = baseSchema;
           	document.getElementById('stepNoRoll').value = stepNo;
           	document.getElementById('stepFormRoll').action = '${ctx}/ultrabpp/develop/rebackStep.action';
			document.getElementById('stepFormRoll').submit();
         } 
	}
	
</script>

</head>
<body>
	<input type="hidden" id="base" value="${baseSchema}"/>
	
	<div class="title_right">
		<div class="title_left">
			<span class="title_bg"> <span class="title_icon2">${nodePath}</span> </span>
			<span class="title_xieline"></span>
		</div>
	</div>
  <dg:datagrid  var="step" items="${stepList}">
		<div class='page_div_bg'>
			<div class='page_div'>
				<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" onclick="addStep();" text='dm_btn_add' />
			</div>
		</div>
  		<dg:condition>
  		<div align="center">
  			
		</div>
  		</dg:condition>
  		
    	<dg:gridtitle>
	    	<tr>
				<th><eoms:lable name="bpp_develop_fix_stepNo"/> </th>
				<th><eoms:lable name="bpp_develop_fix_baseSchema"/> </th>
				<th><eoms:lable name="bpp_develop_fix_description"/> </th>
				<th><eoms:lable name="bpp_develop_fix_oprate"/> </th>
			</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr >
				<td>
					<c:if test="${step.operate  ==  'delete'}">
						${step.stepNo}
					</c:if>
					<c:if test="${step.operate!='delete'}">
						<a href="javascript:deployStep('${step.stepNo}','${step.baseSchema}')">
							${step.stepNo}
						</a>
					</c:if>
				</td>
				<td>
					<c:if test="${step.operate == 'delete'}">
						${step.baseSchema}
					</c:if>
					<c:if test="${step.operate!='delete'}">
						<a href="javascript:deployStep('${step.stepNo}','${step.baseSchema}')">
							${step.baseSchema}
						</a>
					</c:if>
				</td>
				<td>
					<c:if test="${step.operate == 'delete'}">
						${step.description}
					</c:if>
					<c:if test="${step.operate!='delete'}">
						<a href="javascript:deployStep('${step.stepNo}','${step.baseSchema}')">
							${step.description}
						</a>
					</c:if>
				</td>
				<!-- 
				//先暂时去掉 
				<td>
					<c:if test="${step.operate == 'delete'}">
						${step.acceptOver}
					</c:if>
					<c:if test="${step.operate!='delete'}">
						<a href="javascript:;" onclick="deployStep('${step.stepNo}','${step.baseSchema}')">
							${step.acceptOver}
						</a>
					</c:if>
				</td>
				<td>
					<c:if test="${step.operate == 'delete'}">
						${step.dealOver}
					</c:if>
					<c:if test="${step.operate!='delete'}">
						<a href="javascript:;" onclick="deployStep('${step.stepNo}','${step.baseSchema}')">
							${step.dealOver}
						</a>
					</c:if>
				</td>
				-->
				
				<td>
					<c:if test="${step.operate == 'delete'}">
						<a href="javascript:;"  onclick="rollBack('${step.stepNo}','${step.baseSchema}');"><eoms:lable name="bpp_develop_fix_rooback"/></a>
					</c:if>
					<c:if test="${step.operate!='delete'}">
						<a href="javascript:;"  onclick="editStep('${step.stepNo}','${step.baseSchema}');"><eoms:lable name="bpp_develop_fix_update"/></a>
                   		<a href="javascript:;"  onclick="delStep('${step.stepNo}','${step.baseSchema}');"><eoms:lable name="bpp_develop_fix_delete"/></a>
					</c:if>
				</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
	<s:form name="stepForm" id="stepForm">
		<input type="hidden" name="baseSchema" id="baseSchema"/>
		<input type="hidden" name="stepNo" id="stepNo"/>
	</s:form>
	<s:form name="stepFormRoll" id="stepFormRoll">
		<input type="hidden" name="baseSchema" id="baseSchemaRoll"/>
		<input type="hidden" name="stepNo" id="stepNoRoll"/>
	</s:form>
</body>
</html>
				