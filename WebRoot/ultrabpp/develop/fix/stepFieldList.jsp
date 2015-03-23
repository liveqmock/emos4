<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,0);
	}
	
	window.onload = function() 
	{
		setCenter(0,0);
		initRequiredCheckbox();
		initVisiableCheckbox();
	}
			
	
	 
	function addField(){
		var baseSchema = document.getElementById('base').value;
		var src = '${ctx}/ultrabpp/develop/editStep.action?baseSchema='+baseSchema;
		window.open(src,'','width=550px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	
	function editStepField(stepNo,baseSchema){
		var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+baseSchema+'&stepNo='+stepNo;
		window.open(src,'','width=550px,height=400px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	 	
	 }
	
	function deployField(stepNo,baseSchema){
	
		var src = '${ctx}/ultrabpp/develop/deployStep.action?baseSchema='+baseSchema+'&stepNo='+stepNo;
		window.location.href(src);
	}
	
	function delStepField(){
			if(window.confirm("您确定要删除吗？")){
           	document.getElementById('baseSchema').value = baseSchema;
           	document.getElementById('stepNo').value = stepNo;
           	document.getElementById('actionID').value = actionID;
			document.getElementById('stepFieldForm').submit();
         } 
	}
	
	function selectItemAllVisiable(value){
		if(value == true)
		{
			
			var visiable = document.getElementsByName('stepFieldRelation.visiable');
			var visiableCtx = document.getElementsByName('visiableCheckbox');
			for(var i = 0 ; i < visiable.length ; i++)
			{
	            var m = visiableCtx[i];
	            m.checked = true;
         	}
		}else
		{
			var visiable = document.getElementsByName('stepFieldRelation.visiable');
			var visiableCtx = document.getElementsByName('visiableCheckbox');
			for(var i = 0 ; i < visiable.length ; i++)
			{
	            var m = visiableCtx[i];
	            m.checked = false;
            }
		}
	}
	
	function selectItemAllRequired(value){
		if(value == true)
		{
			var required = document.getElementsByName('stepFieldRelation.required');
			var requiredCtx = document.getElementsByName('requiredCheckbox');
			for(var i = 0 ; i < required.length ; i++)
			{
	            var m = requiredCtx[i];
	            m.checked = true;
         	}
		}else
		{
			var required = document.getElementsByName('stepFieldRelation.required');
			var requiredCtx = document.getElementsByName('requiredCheckbox');
			for(var i = 0 ; i < required.length ; i++)
			{
	            var m = requiredCtx[i];
	            m.checked = false;
            }
		}
	}
	
	
	//确定是否可见的 全选按钮的选中状态
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
	
	//确定全选按钮的选中状态
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
	
	//初始化是否可见的全选按钮
	function initVisiableCheckbox()
	{
		var visiable = document.getElementsByName('stepFieldRelation.visiable');
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
       	//点击确定去选按钮状态
		checkTopVisiable();
	}
	
	//初始化如否必须的复选按钮的状态
	function initRequiredCheckbox()
	{
		var required = document.getElementsByName('stepFieldRelation.required');
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
       	//确定全选按钮选择状态
       	checkTopRequired();
	}
	
	//将修改提交到后台数据库
	function doPost(type,value,baseSchema,stepNo,fieldID,fieldType)
	{
		if(value==true)
		{
			value = 1;
		}else
		{
			value = 0;
		}
		$.get("${ctx}/ultrabpp/develop/saveStepField.action?stamp="+new Date().getTime(),{'baseSchema':baseSchema,'fieldID':fieldID,'stepNo':stepNo,'fieldType':fieldType,'checkvalue':value,'checktype':type},function(result)
		{
			
		}
		);
	}
	//全选，讲提交一个一个遍历，然后提交到数据库
	function doPostAll(value,type)
	{
		if(type == "visiable")
		{
			if(value == true)
			{
				var baseSchema = document.getElementsByName('stepFieldRelation.baseSchema');
				var stepNo = document.getElementsByName('stepFieldRelation.stepNo');
				var fieldID = document.getElementsByName('stepFieldRelation.fieldID');
				var fieldType = document.getElementsByName('stepFieldRelation.fieldType');
				
				for(var i = 0 ; i < baseSchema.length ; i++)
				{
		            doPost('visiable','1',baseSchema[i].value,stepNo[i].value,fieldID[i].value,fieldType[i].value)
	         	}
			}else
			{
				var baseSchema = document.getElementsByName('stepFieldRelation.baseSchema');
				var stepNo = document.getElementsByName('stepFieldRelation.stepNo');
				var fieldID = document.getElementsByName('stepFieldRelation.fieldID');
				var fieldType = document.getElementsByName('stepFieldRelation.fieldType');
				
				for(var i = 0 ; i < baseSchema.length ; i++)
				{
		            doPost('visiable','0',baseSchema[i].value,stepNo[i].value,fieldID[i].value,fieldType[i].value)
	         	}
			}
		}
		 if(type == "required")
		{
			if(value == true)
			{
				var baseSchema = document.getElementsByName('stepFieldRelation.baseSchema');
				var stepNo = document.getElementsByName('stepFieldRelation.stepNo');
				var fieldID = document.getElementsByName('stepFieldRelation.fieldID');
				var fieldType = document.getElementsByName('stepFieldRelation.fieldType');
				
				for(var i = 0 ; i < baseSchema.length ; i++)
				{
		            doPost("required","1",baseSchema[i].value,stepNo[i].value,fieldID[i].value,fieldType[i].value)
	         	}
			}else
			{
				var baseSchema = document.getElementsByName('stepFieldRelation.baseSchema');
				var stepNo = document.getElementsByName('stepFieldRelation.stepNo');
				var fieldID = document.getElementsByName('stepFieldRelation.fieldID');
				var fieldType = document.getElementsByName('stepFieldRelation.fieldType');
				
				for(var i = 0 ; i < baseSchema.length ; i++)
				{
		            doPost("required","0",baseSchema[i].value,stepNo[i].value,fieldID[i].value,fieldType[i].value)
	         	}
			}
		}
	}
		
</script>

</head>
<body>
 <div class="content">
  <dg:datagrid  var="stepFieldRelation" items="${stepFieldRelationList}">
	   	<dg:gridtitle>
	    	<tr>
	    		
				<th>
				<eoms:lable name="bpp_develop_fix_label"/>
				</th>
				<th>
				<eoms:lable name="bpp_develop_fix_actionName"/>
				</th>
				<th>
					<input type="checkbox"  id="checkboxV" name="checkboxV"
          			onclick="selectItemAllVisiable(this.checked);doPostAll(this.checked,'visiable');" />
          			<eoms:lable name="bpp_develop_fix_visiable"/>
				</th>
				<th>
					
					<input type="checkbox"  id="checkboxR" name="checkboxR"
          			onclick="selectItemAllRequired(this.checked);doPostAll(this.checked,'required');" />
          			<eoms:lable name="bpp_develop_fix_required"/>
				</th>
			</tr>
	   	</dg:gridtitle> 
		<dg:gridrow>
			<tr >
				
				<td style="display:none;">
					<input type="hidden" id="stepFieldRelation.baseSchema" name="stepFieldRelation.baseSchema" value="${stepFieldRelation.baseSchema}"/>
					${stepFieldRelation.baseSchema}
				</td>
				<td style="display:none;">
					<input type="hidden" id="stepFieldRelation.stepNo" name="stepFieldRelation.stepNo" value="${stepFieldRelation.stepNo}"/>
					${stepFieldRelation.stepNo}
				</td>
				<td style="display:none;">
					<input type="hidden" id="stepFieldRelation.fieldID" name="stepFieldRelation.fieldID" value="${stepFieldRelation.fieldID}"/>
					${stepFieldRelation.fieldID}
				</td>
				<td style="display:none;">
					<input type="hidden" id="stepFieldRelation.fieldType" name="stepFieldRelation.fieldType" value="${stepFieldRelation.fieldType}"/>
					${stepFieldRelation.fieldType}
				</td>
				
				<td>${stepFieldRelation.baseField.label}</td>
				<td>${stepFieldRelation.baseField.fieldName}</td>
				<td>
					<input type="hidden" id="stepFieldRelation.visiable" name="stepFieldRelation.visiable" value="${stepFieldRelation.visiable}"/>
					<input type="checkbox" name="visiableCheckbox" id="visiableCheckbox" onclick="checkTopVisiable();doPost('visiable',this.checked,'${stepFieldRelation.baseSchema}','${stepFieldRelation.stepNo}','${stepFieldRelation.fieldID}','${stepFieldRelation.fieldType}');"/>
				</td>
				<td>
					<input type="hidden" id="stepFieldRelation.required" name="stepFieldRelation.required" value="${stepFieldRelation.required}" />
					<input type="checkbox" name="requiredCheckbox" id="requiredCheckbox"  onclick="checkTopRequired();doPost('required',this.checked,'${stepFieldRelation.baseSchema}','${stepFieldRelation.stepNo}','${stepFieldRelation.fieldID}','${stepFieldRelation.fieldType}');"/>
				</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
	</div>
</body>
</html>
