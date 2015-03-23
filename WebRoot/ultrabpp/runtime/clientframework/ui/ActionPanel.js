function ActionPanel() {}
//添加提交标志，如果提交次数大于1，返回false 不再提交工单
var num=0;
ActionPanel.submit = function()
{
	Message.clearTips();
	var canSubmit = true;
	var funcActionList = ClientContext.submitFuncMap_Action.get(ClientContext.actionID);
	var funcStatusList = ClientContext.submitFuncMap_Status.get(ClientContext.baseStatus);
	if(funcActionList != null)
	{
		for(var itFuncA = funcActionList.iterator(); itFuncA.hasNext();)
		{
			var result = itFuncA.next()();
			if(!result) return false;
		}
	}
	if(funcStatusList != null)
	{
		for(var itFuncS = funcStatusList.iterator(); itFuncS.hasNext();)
		{
			var result = itFuncS.next()();
			if(!result) return false;
		}
	}
	
	for(var tagit = ClientContext.assignTags.iterator(); tagit.hasNext();)
	{
		var treeObj = $("#" + tagit.next() + "_AssignString");
		if(treeObj.length > 0)
		{
			ClientContext.setAssignString(ClientContext.assignString + treeObj[0].value);
		}
	}
	
	var emptyFields = new List();
	var overflowField = new List();
	var focusField = null;
	for(var fieldit = ClientContext.fields.iterator(); fieldit.hasNext();)
	{
		var field = fieldit.next();
		var tmp_val = field.G();
		//去掉字符串前后前后空格
		if(tmp_val !=null)
		{
			tmp_val = tmp_val.replace(/(^\s*)|(\s*$)/, ""); 
		}
		if(tmp_val != null && tmp_val == "" && field.require == 1)
		{
			emptyFields.add(field);
			if(focusField==null)
			{
				focusField = field;
			}
		}
		if(field.maxlen > 0 && tmp_val != null && tmp_val.getBytes() > field.maxlen)
		{
			overflowField.add(field);
			if(focusField==null)
			{
				focusField = field;
			}
		}
	}
	if(focusField!=null){
		var fieldMap = new Map();
		//将json数组对象封装为map对象，供后面逻辑调用
		for(var i=0;i<ClientContext.layoutJSon.length;i++){
			var fieldTemp  = ClientContext.layoutJSon[i];
			fieldMap.put(fieldTemp.fieldName,fieldTemp);
		}
		if(emptyFields.size() > 0 && ClientContext.actionType != "SAVE")
		{
			var text = "";
			for(var it = emptyFields.iterator(); it.hasNext();)
			{
				var field = it.next();
				//展开当前必填字段所在的容器组和容器
				Message.showParent(fieldMap,field.fieldID,0);
			}
		}
		if(overflowField.size() > 0)
		{
			var text = "";
			for(var it = overflowField.iterator(); it.hasNext();)
			{
				var field = it.next();
				//展开当前必填字段所在的容器组和容器
				Message.showParent(fieldMap,field.fieldID,0);
			}
		}
		//展开当前必填字段所在的容器组和容器，并且将所在tab页设为选中状态，其他同级tab页为不选中状态
		Message.showParent(fieldMap,focusField.fieldID,1);
		canSubmit = ActionPanel.showTip(emptyFields,overflowField);
	}
	
	if(!canSubmit)
	{
		ClientContext.setAssignString("");
		if(focusField!=null)
		{
			var objValue= document.activeElement.value;
//			if(	objValue!="提　交"){
				//document.getElementById("bpp_ActPanel_BtnPanel_Submit").blur();
				//=$("#"+focusField.fieldID);
//			}
		if(focusField.fieldType!="TimeField")
			$("#"+focusField.fieldID).focus();
		}
		return false;
	}
	num++;
	if(num>1)return false;
	$('#bpp_WorksheetForm')[0].submit();
	$('#bpp_ActPanel').css('display','none');
}
ActionPanel.goBack = function()
{
	ClientContext.setAssignString("");
	ClientContext.assignTags = new Map();
	Message.clearTips();
	var funcActionList = ClientContext.backFuncMap_Action.get(ClientContext.actionID);
	if(funcActionList != null)
	{
		for(var itFuncA = funcActionList.iterator(); itFuncA.hasNext();)
		{
			var result = itFuncA.next()();
			if(!result) return false;
		}
	}
	ActionPanel.goBackView();
}
ActionPanel.goBackView = function() {}
ActionPanel.showTip = function(emptyFields,overflowField)
{
	var canSubmit = true;
	if(emptyFields.size() > 0 && ClientContext.actionType != "SAVE" && ClientContext.actionType != "ACCEPT" && ClientContext.actionType != "CANCEL")
	{
		var text = "";
		for(var it = emptyFields.iterator(); it.hasNext();)
		{
			var field = it.next();
			text += ",“" + field.text + "”字段不能为空！"
			Message.tip(Message.require, field.fieldID, "不能为空！");
		}
		canSubmit = false;
	}
	if(overflowField.size() > 0)
	{
		var text = "";
		for(var it = overflowField.iterator(); it.hasNext();)
		{
			var field = it.next();
			text += ",“" + field.text + "”字段超长了！"
			Message.tip(Message.overLength, field.fieldID, "长度不能超过" + field.maxlen + "！");
		}
		canSubmit = false;
	}
	return canSubmit;
}
