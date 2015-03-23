function ClientContext() { }
ClientContext.loginName = "";
ClientContext.theme = "";
ClientContext.baseID = "";
ClientContext.baseSchema = "";
ClientContext.baseSN = "";
ClientContext.baseName = "";
ClientContext.defCode = "";
ClientContext.baseStatus = "";
ClientContext.allStatusList = new List();
ClientContext.taskID = "";
ClientContext.currentTask = null;
ClientContext.flagActive = 0;
ClientContext.editType = "";
ClientContext.formFolder = "";
ClientContext.attrs = new Map();
ClientContext.fixActions = new Map();
ClientContext.freeActions = new Map();
ClientContext.actionID = "";
ClientContext.actionType = "";
ClientContext.actionText = "";
ClientContext.closeAfterCommit = 1;
ClientContext.assignTags = new Map();
ClientContext.assignString = "";
ClientContext.fields = new Map();
ClientContext.initFuncMap_Action = new Map();
ClientContext.submitFuncMap_Action = new Map();
ClientContext.backFuncMap_Action = new Map();
ClientContext.initFuncMap_Status = new Map();
ClientContext.submitFuncMap_Status = new Map();
ClientContext.layoutJSon = null;
ClientContext.cp = {};//custom parameter自定义参数

ClientContext.addField = function(fieldObj)
{
	ClientContext.fields.put(fieldObj.fieldID, fieldObj);
}

ClientContext.setAttr = function(key, value)
{
	ClientContext.attrs.put(key, value);
	var attrModel = $("#" + key);
	if(attrModel.length > 0)
	{
		$("#" + key)[0].value = value;
	}
}

ClientContext.getAttr = function(key)
{
	return ClientContext.attrs.get(key);
}

ClientContext.setAssignString = function(assignStr)
{
	ClientContext.assignString = assignStr;
	$("#bpp_Sys_AssignString")[0].value = assignStr;
}

ClientContext.addAssignTags = function(tagID)
{
	ClientContext.assignTags.put(tagID, tagID);
}

ClientContext.addFixAction = function(actName, actType, actText, isFree, hasNext, isClose, actDesc)
{
	ClientContext.fixActions.put(actName, ButtonPanel.buildActionModel(actName, actType, actText, isFree, hasNext, isClose, actDesc));
}
ClientContext.addFreeAction = function(actName, actType, actText, isFree, hasNext, isClose, actDesc)
{
	ClientContext.freeActions.put(actName, ButtonPanel.buildActionModel(actName, actType, actText, isFree, hasNext, isClose, actDesc));
}

ClientContext.init = function(type, name, extendFunc)
{
	if(type == "action")
	{
		if(ClientContext.initFuncMap_Action.get(name) == null)
		{
			ClientContext.initFuncMap_Action.put(name, new List());
		}
		ClientContext.initFuncMap_Action.get(name).add(extendFunc);
	}
	else if(type == "status")
	{
		if(ClientContext.initFuncMap_Status.get(name) == null)
		{
			ClientContext.initFuncMap_Status.put(name, new List());
		}
		ClientContext.initFuncMap_Status.get(name).add(extendFunc);
	}
}

ClientContext.submit = function(type, name, extendFunc)
{
	if(type == "action")
	{
		if(ClientContext.submitFuncMap_Action.get(name) == null)
		{
			ClientContext.submitFuncMap_Action.put(name, new List());
		}
		ClientContext.submitFuncMap_Action.get(name).add(extendFunc);
	}
	else if(type == "status")
	{
		if(ClientContext.submitFuncMap_Status.get(name) == null)
		{
			ClientContext.submitFuncMap_Status.put(name, new List());
		}
		ClientContext.submitFuncMap_Status.get(name).add(extendFunc);
	}
}

ClientContext.back = function(name, extendFunc)
{
	if(ClientContext.backFuncMap_Action.get(name) == null)
	{
		ClientContext.backFuncMap_Action.put(name, new List());
	}
	ClientContext.backFuncMap_Action.get(name).add(extendFunc);
}







