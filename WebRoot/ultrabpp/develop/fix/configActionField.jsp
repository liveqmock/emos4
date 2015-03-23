<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,61);
	}
	
	window.onload = function() 
	{
		setCenter(0,61);
	}
	//增加
	function addField()
	{
		var selObjWorksheet = document.getElementById("baseFieldSl");
		var selObjAction = document.getElementById("actionFieldSl");
		var selectedOption = new Array();
		for(var i=0;i<selObjWorksheet.options.length;i++)
		{
		 	var option = selObjWorksheet.options[i];
			if(option.selected)
			{
				selectedOption[selectedOption.length] = option;
			}
		}
		
		for(var i=0;i<selectedOption.length;i++)
		{
			var option = selectedOption[i];
			var idFieldType = option.value;//返回下拉列表中被选选项的文本
			var label = option.text;//返回下拉列表中被选选项的值
			selObjWorksheet.remove(option.index);
			selObjAction.options.add(new Option(label,idFieldType));//环节配置字段列表 添加
		}
	}
	//删除
	function removeField()
	{
		var selObjAction = document.getElementById("actionFieldSl");
		var selObjWorksheet = document.getElementById("baseFieldSl");
		var removeOption = new Array();
		
		for(var i=0;i<selObjAction.options.length;i++)
		{
			var option = selObjAction.options[i];
			if(option.selected)
			{
				removeOption[removeOption.length] = option;
			}
		}
		
		for(var i=0;i<removeOption.length;i++)
		{
			var option = removeOption[i];
			var idFieldType = option.value;//返回下拉列表中被选选项的文本
			var label = option.text;//返回下拉列表中被选选项的值
			selObjAction.remove(option.index);
			selObjWorksheet.options.add(new Option(label,idFieldType));
		}
	}
	//增加
	function addAllField()
	{
		var selObjWorksheet = document.getElementById("baseFieldSl");
		var selObjStep = document.getElementById("actionFieldSl");
		
		for(var i=0;i<selObjWorksheet.options.length;i++)
		{
			var idFieldType = selObjWorksheet.options[i].value;//返回下拉列表中被选选项的文本
			var label = selObjWorksheet.options[i].text;//返回下拉列表中被选选项的值
			selObjStep.options.add(new Option(label,idFieldType));
		}
		selObjWorksheet.length=0;
	}
	
	function removeAllField()
	{
		var selObjWorksheet = document.getElementById("baseFieldSl");
		var selObjStep = document.getElementById("actionFieldSl");
		
		for(var i=0;i<selObjStep.options.length;i++)
		{
			var idFieldType = selObjStep.options[i].value;//返回下拉列表中被选选项的文本
			var label = selObjStep.options[i].text;//返回下拉列表中被选选项的值
			selObjWorksheet.options.add(new Option(label,idFieldType));
		}
		selObjStep.length=0;	
	}
	
	function saveConfigField()
	{
		var selObjStep = document.getElementById("actionFieldSl");
		var idString="";
		for(var i=0;i<selObjStep.options.length;i++)
		{
			var idFieldType = selObjStep.options[i].value;//返回下拉列表中被选选项的文本
			var label = selObjStep.options[i].text;//返回下拉列表中被选选项的值
			if(i==0)
			{
				idString= idFieldType;
			}else{
				idString+=","+idFieldType;
			}
		}
		document.getElementById("idString").value=idString;
		document.getElementById('fieldForm').submit(); 
	}

</script>
	</head>
	<body>
	 	<s:form action="saveConfigActionField.action" theme="simple" name="fieldForm" id="fieldForm" method="post" target="_self">
	 		<input type="hidden" name="idString" id="idString"/>
	 		<input type="hidden" name="baseSchema" id="baseSchema" value="${baseSchema}"/>
	 		<input type="hidden" name="stepNo" id="stepNo" value="${stepNo}"/>
	 		<input type="hidden" name="actionName" id="actionName" value="${actionName}"/>
	 		
	 	</s:form>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">配置动作字段信息</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<table style="width: 100%; heigth: 280px">
					<tr>
						<th width="40%">
							表单动作字段
						</th>
						<th width="50">
							<eoms:lable name="bpp_develop_fix_oprate"/>
						</th>
						<th width="50%">
							动作字段
						</th>
					</tr>
					<tr>
						<td style="width: 40%; height: 250px">
							<s:select name="baseFieldSl" id="baseFieldSl" list="actionFieldList" cssStyle="width:98%;height:250px" cssClass="select" listKey="id" listValue="label" multiple="true"></s:select>
						</td>
						<td width="50">
							<a href="javascript:;" onclick="addAllField();">
							<eoms:lable name="bpp_develop_fix_selectAll"/>
							</a>
							<br />
							<a href="javascript:;" onclick="addField();">
							<eoms:lable name="bpp_develop_fix_select"/>
							</a>
							<br />
							<a href="javascript:;" onclick="removeField();">
							<eoms:lable name="bpp_develop_fix_remove"/>
							</a>
							<br />
							<a href="javascript:;" onclick="removeAllField();">
							<eoms:lable name="bpp_develop_fix_removeAll"/>
							</a>
						</td>
						<td width="50%">
							<s:select name="actionFieldSl" id="actionFieldSl" list="actionFieldRelationList" cssStyle="width:98%;height:250px" cssClass="select" listKey="baseField.id" listValue="baseField.label" multiple="true"></s:select>
						</td>
					</tr>
				</table>
			</div>
			<div class="add_bottom">
				<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="saveConfigField();" />
				<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
		</div>
	</body>
</html>
