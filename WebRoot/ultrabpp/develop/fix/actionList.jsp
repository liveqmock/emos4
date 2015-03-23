<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,0);
	}
	
	window.onload = function() 
	{
		setCenter(0,0);changeRow_color("tab");
	}
	//action的增加
	function addAction(){
		var baseSchema = document.getElementById('base').value;
		var src = '${ctx}/ultrabpp/develop/editAction.action?baseSchema='+baseSchema;
		window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	//修改
	function editAction(stepNo,baseSchema,actionID){
		var src = '${ctx}/ultrabpp/develop/editAction.action?baseSchema='+baseSchema+'&stepNo='+stepNo+'&actionID='+actionID;
		window.open(src,'','width=550px,height=350px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	 }
	 //配置
	 function deployAction(baseSchema,actionName,stepNo){
		var src = '${ctx}/ultrabpp/develop/deployAction.action?baseSchema='+baseSchema+'&stepNo='+stepNo+'&actionName='+actionName;
		window.open(src,'','width=850px,height=650px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	 }
	//action对应的step
	function deployStep(stepNo,baseSchema){
	
		var src = '${ctx}/ultrabpp/develop/deployStep.action?baseSchema='+baseSchema+'&stepNo='+stepNo;
		window.open(src,'','width=550px,height=600px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		window.location.href(src);
	}
	//删除
	function delAction(stepNo,baseSchema,actionName){
	     if(window.confirm("您确定要删除？")){
           	document.getElementById('baseSchema').value = baseSchema;
           	document.getElementById('stepNo').value = stepNo;
           	document.getElementById('actionName').value = actionName;
           	document.getElementById('actionForm').action = '${ctx}/ultrabpp/develop/delAction.action';
			document.getElementById('actionForm').submit();
         } 
	} 
	//回复
	function rollBackAction(stepNo,baseSchema,actionName){
	     if(window.confirm("您确定要恢复？")){
           	document.getElementById('baseSchemaRoll').value = baseSchema;
           	document.getElementById('stepNoRoll').value = stepNo;
           	document.getElementById('actionNameRoll').value = actionName;
           	document.getElementById('actionFormRoll').action = '${ctx}/ultrabpp/develop/rebackAction.action';
			document.getElementById('actionFormRoll').submit();
         } 
	}
	
</script>

</head>
<body>
	
  <dg:datagrid  var="actionModel" items="${actionList}">
 
    	<dg:gridtitle>
	    	<tr>
				<th><eoms:lable name="bpp_develop_fix_label"/></th>
				<th><eoms:lable name="bpp_develop_fix_actionName"/></th>
				<th><eoms:lable name="bpp_develop_fix_fix"/>/<eoms:lable name="bpp_develop_fix_free"/></th>
				<th><eoms:lable name="bpp_develop_fix_dongzuoType"/></th>
				<th><eoms:lable name="bpp_develop_fix_hasNext"/></th>
				<th><eoms:lable name="bpp_develop_fix_describ"/></th>
				<th style="display:none;"><eoms:lable name="bpp_develop_fix_order"/></th>
				<th><eoms:lable name="bpp_develop_fix_oprate"/></th>
			</tr>
    	</dg:gridtitle> 
  
		<dg:gridrow>
			<tr >
				<td style="display:none;">${actionModel.id}</td>
				<td>
					<c:if test="${actionModel.operate!='delete'}">
						<a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}')">
							${actionModel.label}
						</a>
					</c:if>
					<c:if test="${actionModel.operate == 'delete'}">
							${actionModel.label}
					</c:if>
				</td>
				
				<td>
					<c:if test="${actionModel.operate!='delete'}">
						<a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}')">
							${actionModel.actionName}
						</a>
					</c:if>
					<c:if test="${actionModel.operate == 'delete'}">
							${actionModel.actionName}
					</c:if>
				</td>
				
			
				
			
				
				<td style="display:none;">${actionModel.actionType}</td>
				<td>
				<c:choose>
				   <c:when test="${actionModel.operate!='delete'}">
					<c:if test="${actionModel.isFree  == 0}">
			         <a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}','${actionModel.id}')">
			           	 <eoms:lable name="bpp_develop_fix_fix"/>
			         </a>
			         </c:if>
			      
			          <c:if test="${actionModel.isFree  == 1}">
			           	<a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}','${actionModel.id}')">
			           	 <eoms:lable name="bpp_develop_fix_free"/>
			           	 </a>
			         </c:if>
			       </c:when>
			       <c:when test="${actionModel.operate == 'delete'}">
					<c:if test="${actionModel.isFree  == 0}">
			           	<eoms:lable name="bpp_develop_fix_fix"/>
			         </c:if>
			      
			          <c:if test="${actionModel.isFree  == 1}">
			           	  <eoms:lable name="bpp_develop_fix_free"/>
			         </c:if>
			       </c:when>
			    </c:choose>
				</td>
				
				<td>
					<c:if test="${actionModel.operate!='delete'}">
						<a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}')">
							${actionModel.actionType}
						</a>
					</c:if>
					<c:if test="${actionModel.operate == 'delete'}">
							${actionModel.actionType}
					</c:if>
				</td>
				
				<td>
				<c:choose>
				   <c:when test="${actionModel.operate!='delete'}">
						<c:if test="${actionModel.hasNext  == 1}">
						<a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}','${actionModel.id}')">
				          	 <eoms:lable name="bpp_develop_fix_yes"/>
				         </a>
				         </c:if>
				          <c:if test="${actionModel.hasNext  == 0}">
				       	  <a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}','${actionModel.id}')">
				       	    <eoms:lable name="bpp_develop_fix_no"/>
				       	  </a>
				         </c:if>
					</c:when>
					<c:when test="${actionModel.operate == 'delete'}">
						<c:if test="${actionModel.hasNext  == 1}">
				          	 <eoms:lable name="bpp_develop_fix_yes"/>
				         </c:if>
				          <c:if test="${actionModel.hasNext  == 0}">
				       	   <eoms:lable name="bpp_develop_fix_no"/>
				         </c:if>
					</c:when>
				</c:choose>
				</td>
				
				<td>
					<c:if test="${actionModel.operate!='delete'}">
						<a href="javascript:;"  onclick="deployAction('${actionModel.baseSchema}','${actionModel.actionName}','${actionModel.stepNo}','${actionModel.id}')">
							${actionModel.description}
						</a>
					</c:if>
					<c:if test="${actionModel.operate == 'delete'}">
							${actionModel.description}
					</c:if>
				</td>
				
				<td>
					<a href="javascript:;"  onclick="editAction('${actionModel.stepNo}','${actionModel.baseSchema}','${actionModel.id}');">
						<eoms:lable name="bpp_develop_fix_update"/>
					</a>
                    <a href="javascript:;"  onclick="delAction('${actionModel.stepNo}','${actionModel.baseSchema}','${actionModel.actionName}');">
                    	<eoms:lable name="bpp_develop_fix_delete"/>
                    </a>
                </td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
	<s:form name="actionForm" id="actionForm">
		<input type="hidden" name="baseSchema" id="baseSchema"/>
		<input type="hidden" name="stepNo" id="stepNo"/>
		<input type="hidden" name="actionName" id="actionName"/>
	</s:form>
	<s:form name="actionForm" id="actionFormRoll">
		<input type="hidden" name="baseSchema" id="baseSchemaRoll"/>
		<input type="hidden" name="stepNo" id="stepNoRoll"/>
		<input type="hidden" name="actionName" id="actionNameRoll"/>
	</s:form>
</body>
</html>
