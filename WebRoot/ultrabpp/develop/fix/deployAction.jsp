<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,30);
		}
		
		window.onload = function() 
		{
			setCenter(0,30);
			initVisiableCheckbox();
			initRequiredCheckbox();
		}
		
	
        function deleteField(actionName,stepNo,baseSchema,fieldID,fieldType){
            if(window.confirm("您确定要删除？")){
            	document.getElementById('baseSchema').value = baseSchema;
            	document.getElementById('delActionName').value = actionName;
            	document.getElementById('delStepNo').value = stepNo;
            	document.getElementById('delFieldID').value = fieldID;
            	document.getElementById('delFieldType').value = fieldType;
            	document.getElementById('deployActionForm').action = '${ctx}/ultrabpp/develop/delActionField.action';
            	
				document.getElementById('deployActionForm').submit();
            } 
        }
        
        function configField(baseSchema,stepNo,actionName)
		{
			var src = '${ctx}/ultrabpp/develop/configActionField.action?baseSchema='+baseSchema+'&stepNo='+stepNo+'&actionName='+actionName; 
            window.open(src,'','width=600px,height=350px,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
		
        function rollBack()
        {
        	 if(window.confirm("您确定要恢复？")){
            	document.getElementById('baseSchemaR').value = baseSchema;
            	document.getElementById('delActionNameR').value = actionName;
            	document.getElementById('delStepNoR').value = stepNo;
            	document.getElementById('delFieldIDR').value = fieldID;
            	document.getElementById('delFieldTypeR').value = fieldType;
            	document.getElementById('deployActionFormR').action = '${ctx}/ultrabpp/develop/rollBackActionField.action';
				document.getElementById('deployActionFormR').submit();
            } 
        }
        
        //初始化是否可见复选按钮
        function initVisiableCheckbox()
		{
			var visiable = document.getElementsByName('actionFieldRelation.visiable');
			var visiableCtx = document.getElementsByName('visiableCheckbox');
			
			if(visiable.length < 1)
			{
				return;
			}
			
			for(var i = 0 ; i < visiable.length ; i++)
			{
	          var m = visiable[i].value;
	          if(m == "1")
	          {
	          	visiableCtx[i].checked = true;
	          }else
	          {
	          	visiableCtx[i].checked = false;
	          }
	       	}
	       	checkTopVisiable();
		}
		//初始化 是否必填的复选按钮
		function initRequiredCheckbox()
		{
			var required = document.getElementsByName('actionFieldRelation.required');
			var requiredCtx = document.getElementsByName('requiredCheckbox');
			
			if(required.length < 1)
			{
				return;
			}
			
			for(var i = 0 ; i < required.length ; i++)
			{
	          var m = required[i].value;
	          if(m == "1")
	          {
	          	requiredCtx[i].checked = true;
	          }else
	          {
	          	requiredCtx[i].checked = false;
	          }
	       	}
	       	checkTopRequired();
		}
		
		//每点一下复选按钮，确定头上的 全选按钮状态是不是点击全选状态
		function checkTopVisiable()
		{
			var visiableCtx = document.getElementsByName('visiableCheckbox');
			
			var temp1 = visiableCtx[0].checked;
			var temp = 1;
			for(var i = 0 ; i < visiableCtx.length ; i++)
			{
	            var m = visiableCtx[i];
	            if(m.checked == temp1) 
	            {
	            }else
	            {
	            	temp = temp + 1;
	            }
	         }
	         if(temp == 1)
	         {
	         	document.getElementById('checkboxV').checked = temp1;
	         }else
	         {
	         	document.getElementById('checkboxV').checked = false;
	         }
		}
        
        //每点一下复选按钮，确定头上的 全选按钮状态是不是点击全选状态
        function checkTopRequired()
		{
			var requiredCtx = document.getElementsByName('requiredCheckbox');
			
			var temp1 = requiredCtx[0].checked;
			var temp = 1;
			for(var i = 0 ; i < requiredCtx.length ; i++)
			{
	            var m = requiredCtx[i];
	            if(m.checked == temp1) 
	            {
	            }else
	            {
	            	temp = temp + 1;
	            }
	         }
	         if(temp == 1)
	         {
	         	document.getElementById('checkboxR').checked = temp1;
	         }else
	         {
	         	document.getElementById('checkboxR').checked = false;
	         }
		}
        
        //保存状态，更新数据库checktype =  value
        function doPost(value,baseSchema,actionName,stepNo,fieldID,fieldType,checktype)
		{
			if(value == true)
			{
				value = 1;
			}else
			{
				value = 0;
			}
			$.get("${ctx}/ultrabpp/develop/saveActionField.action?stamp="+new Date().getTime(),{'baseSchema':baseSchema,'actionName':actionName,'fieldID':fieldID,'stepNo':stepNo,'fieldType':fieldType,'checkvalue':value,'checktype':checktype},function(result)
			{
			}
			);
		}
		
		//出事换全选按钮
		function selectItemAllVisiable(value){
			if(value == true)
			{
				
				var visiable = document.getElementsByName('actionFieldRelation.visiable');
				var visiableCtx = document.getElementsByName('visiableCheckbox');
				
				if(visiable.length < 1)
				{
					return;
				}
				
				for(var i = 0 ; i < visiable.length ; i++)
				{
		            var m = visiableCtx[i];
		            m.checked = true;
	         	}
				}else
				{
					var visiable = document.getElementsByName('actionFieldRelation.visiable');
					var visiableCtx = document.getElementsByName('visiableCheckbox');
					for(var i = 0 ; i < visiable.length ; i++)
					{
			            var m = visiableCtx[i];
			            m.checked = false;
		            }
				}
		}
		//初始化全选按钮
		function selectItemAllRequired(value){
			if(value == true)
			{
				var required = document.getElementsByName('actionFieldRelation.required');
				var requiredCtx = document.getElementsByName('requiredCheckbox');
				
				if(required.length < 1)
				{
					return;
				}
				for(var i = 0 ; i < required.length ; i++)
				{
		            var m = requiredCtx[i];
		            m.checked = true;
	         	}
				}else
				{
					var required = document.getElementsByName('actionFieldRelation.required');
					var requiredCtx = document.getElementsByName('requiredCheckbox');
					for(var i = 0 ; i < required.length ; i++)
					{
			            var m = requiredCtx[i];
			            m.checked = false;
		            }
				}
		}
		//全选的提交，但是也是先遍历，然后一个一个的保存打数据库
		function doPostAll(value,checkType)
		{
			if(value == true)
			{
				var baseSchema = document.getElementsByName('actionFieldRelation.baseSchema');
				var actionName = document.getElementsByName('actionFieldRelation.actionName');
				var stepNo = document.getElementsByName('actionFieldRelation.stepNo');
				var fieldID = document.getElementsByName('actionFieldRelation.fieldID');
				var fieldType = document.getElementsByName('actionFieldRelation.fieldType');
				if( baseSchema.length < 1)
				{
					return;
				}
				
				for(var i = 0 ; i < baseSchema.length ; i++)
				{
		            doPost(true,baseSchema[i].value,actionName[i].value,stepNo[i].value,fieldID[i].value,fieldType[i].value,checkType);
	         	}
			}else
			{
				var baseSchema = document.getElementsByName('actionFieldRelation.baseSchema');
				var actionName = document.getElementsByName('actionFieldRelation.actionName');
				var stepNo = document.getElementsByName('actionFieldRelation.stepNo');
				var fieldID = document.getElementsByName('actionFieldRelation.fieldID');
				var fieldType = document.getElementsByName('actionFieldRelation.fieldType');
				if( baseSchema.length < 1)
				{
					return;
				}
				for(var i = 0 ; i < baseSchema.length ; i++)
				{
		            doPost(false,baseSchema[i].value,actionName[i].value,stepNo[i].value,fieldID[i].value,fieldType[i].value,checkType);
	         	}
			}
		}
		//预览
		function preview(baseSchema, stepNo, actionName)
		{
		    var src = '${ctx}/ultrabpp/previewAction.action?baseSchema='+baseSchema+'&stepNo='+stepNo+'&actionName='+actionName+'&mode=PREVIEWACTION';	  
		    window.open(src);
		}
		//部署
		function deploy(baseSchema, stepNo, actionName)
		{
		    var src = '${ctx}/ultrabpp/deployAction.action?baseSchema='+baseSchema+'&stepNo='+stepNo+'&actionName='+actionName;	  
		    window.open(src);
		}
		
		
</script>
	</head>
	<body>
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">动作字段列表</span> </span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<input type="hidden" id="base" name="base" value="${baseSchema}" />
		<input type="hidden" id="stepNo" name="stepNo" value="${stepNo}" />
		<input type="hidden" id="actionName" name="actionName" value="${actionName}" />
		<dg:datagrid var="actionFieldRelation" items="${actionFieldRelationList}">
			<div class='page_div_bg'>
				<div class='page_div'>
					<li>
						<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" onclick="configField('${baseSchema}','${stepNo}','${actionName}');" text='配置' />
					</li>
					<li>
						<input type="button" value="预览" onclick="preview('${baseSchema}', '${stepNo}', '${actionName}')" />
					</li>
					<li>
						<input type="button" value="动作部署" onclick="deploy('${baseSchema}', '${stepNo}', '${actionName}')" />
					</li>
				</div>
			</div>
			<dg:condition>
				<div align="center">
					<table class="serachdivTable" width="80%">
						<tr>
							<td class="texttd" width="20%">
								<eoms:lable name="sm_lb_userName" />
								：
							</td>
							<td width="35%">
								<input type="text" name="" class="textInput" value="" />
							</td>
							<td class="texttd" width="10%">
								<eoms:lable name="sm_lb_fullName" />
								：
							</td>
							<td width="35%">
								<input type="text" name="" class="textInput" value="" />
							</td>
							<td width="10%">
								<input type="button" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
							</td>
						</tr>
					</table>
				</div>
			</dg:condition>
			<dg:gridtitle>
				<tr>
					<th>
						<eoms:lable name="bpp_develop_fix_label" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_actionName" />
					</th>
					<th>
						<eoms:lable name="wf_componentType" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_listType" />
					</th>
					<th>
						<input type="checkbox" id="checkboxV" name="checkboxV" onclick="selectItemAllVisiable(this.checked);doPostAll(this.checked,'visiable');" />
						<eoms:lable name="bpp_develop_fix_visiable" />
					</th>
					<th>
						<input type="checkbox" id="checkboxR" name="checkboxR" onclick="selectItemAllRequired(this.checked);doPostAll(this.checked,'required');" />
						<eoms:lable name="bpp_develop_fix_required" />
					</th>
					<th>
						<eoms:lable name="bpp_develop_fix_oprate" />
					</th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr>
					<td style="display: none;">
						<input type="hidden" id="actionFieldRelation.baseSchema" name="actionFieldRelation.baseSchema" value="${actionFieldRelation.baseSchema}" />
						<input type="hidden" id="actionFieldRelation.actionName" name="actionFieldRelation.actionName" value="${actionFieldRelation.actionName}" />
						<input type="hidden" id="actionFieldRelation.stepNo" name="actionFieldRelation.stepNo" value="${actionFieldRelation.stepNo}" />
						<input type="hidden" id="actionFieldRelation.fieldID" name="actionFieldRelation.fieldID" value="${actionFieldRelation.fieldID}" />
						<input type="hidden" id="actionFieldRelation.fieldType" name="actionFieldRelation.fieldType" value="${actionFieldRelation.fieldType}" />
					</td>
					<td>
						${actionFieldRelation.baseField.label}
					</td>
					<td>
						${actionFieldRelation.baseField.fieldName}
					</td>
					<td>
						<c:choose>
							<c:when test="${actionFieldRelation.fieldType == 'CharacterField'}">字符型</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'ButtonField'}">按钮</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'BlankField'}">占位</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'LabelField'}">文本</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'SelectField'}">下拉框</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'ViewField'}">试图</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'AssignTreeField'}">派发树</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'TimeField'}">日期</c:when>
							<c:when test="${actionFieldRelation.fieldType == 'CollectField'}">选择框</c:when>
						</c:choose>
					</td>
					<td>
						${actionFieldRelation.baseSchema}
					</td>
					<td>
						<input type="hidden" id="actionFieldRelation.visiable" name="actionFieldRelation.visiable" value="${actionFieldRelation.visiable}" />
						<input type="checkbox" name="visiableCheckbox" id="visiableCheckbox"
							onclick="checkTopVisiable();doPost(this.checked,'${actionFieldRelation.baseSchema}','${actionFieldRelation.actionName}','${actionFieldRelation.stepNo}','${actionFieldRelation.fieldID}','${actionFieldRelation.fieldType}','visiable');" />
					</td>
					<td>
						<input type="hidden" id="actionFieldRelation.required" name="actionFieldRelation.required" value="${actionFieldRelation.required}" />
						<input type="checkbox" name="requiredCheckbox" id="requiredCheckbox"
							onclick="checkTopRequired();doPost(this.checked,'${actionFieldRelation.baseSchema}','${actionFieldRelation.actionName}','${actionFieldRelation.stepNo}','${actionFieldRelation.fieldID}','${actionFieldRelation.fieldType}','required');" />
					</td>
					<td>
							<a href="javascript:;" onclick="deleteField('${actionFieldRelation.actionName}','${actionFieldRelation.stepNo}','${actionFieldRelation.baseSchema}','${actionFieldRelation.fieldID}','${actionFieldRelation.fieldType}');"> <eoms:lable name="bpp_develop_fix_delete" />
							</a>
					</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
		<s:form name="deployActionForm" id="deployActionForm">
			<input type="hidden" name="baseSchema" id="baseSchema" />
			<input type="hidden" name="fieldID" id="delFieldID" />
			<input type="hidden" name="fieldType" id="delFieldType" />
			<input type="hidden" name="actionName" id="delActionName" />
			<input type="hidden" name="stepNo" id="delStepNo" />
		</s:form>
		<s:form name="deployActionFormR" id="deployActionFormR">
			<input type="hidden" name="baseSchema" id="baseSchemaR" />
			<input type="hidden" name="fieldID" id="delFieldIDR" />
			<input type="hidden" name="fieldType" id="delFieldTypeR" />
			<input type="hidden" name="actionName" id="delActionNameR" />
			<input type="hidden" name="stepNo" id="delStepNoR" />
		</s:form>
	</body>
</html>
