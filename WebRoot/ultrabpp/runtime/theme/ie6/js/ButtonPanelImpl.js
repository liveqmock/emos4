ButtonPanel.registerBtnEvent = function(actionModel)
{
	$("#bpp_Btn_" + actionModel.actionName).mouseover(function() {$("#bpp_BtnTip").text("小提示【" + actionModel.actionText + "】：" + actionModel.actionDesc);});
	$("#bpp_Btn_" + actionModel.actionName).mouseout(function() {$("#bpp_BtnTip").text("小提示：点击下列按钮进行工单操作，鼠标移到按钮上有对应的说明。");});
	if(actionModel.hasNext == 1)
	{
		$("#bpp_Btn_" + actionModel.actionName).click(function()
		{
			ButtonPanel.initAction(actionModel);
			$("#bpp_ActComment").text(actionModel.actionDesc);
			$("#bpp_BtnPanel").fadeOut(20, function(){$("#bpp_ActPanel").fadeIn(20);});
		});
	} else {
		$("#bpp_Btn_" + actionModel.actionName).click(function()
		{
			ActionPanel.submit();
		});
	}
}