ButtonPanel.registerBtnEvent = function(actionModel)
{
	$("#bpp_Btn_" + actionModel.actionName).click(function()
	{
		$("#bpp_BtnActionName")[0].value = actionModel.actionName;
		$("#bpp_BtnType")[0].value = actionModel.isFree;
		$("#bpp_BtnNext")[0].value = "下一步";
	});
	if(actionModel.hasNext == 0)
	{
		$("#bpp_BtnNext")[0].value = "提　交";
	}
}

ButtonPanel.next = function()
{
	var actionName = $("#bpp_BtnActionName")[0].value;
	var type = $("#bpp_BtnType")[0].value;
	var actionModel = null;
	
	if(actionName == null || actionName == "")
		return;
	
	if(type == "1")
	{
		actionModel = ClientContext.freeActions.get(actionName);
	}
	else
	{
		actionModel = ClientContext.fixActions.get(actionName);
	}
	
	if(actionModel.hasNext == 1)
	{
		ButtonPanel.initAction(actionModel);
		$("#bpp_ActComment").text(actionModel.actionDesc);
		$("#bpp_BtnPanel").fadeOut(20, function(){$("#bpp_ActPanel").fadeIn(20);});
	}
	else
	{
		ActionPanel.submit();
	}
}