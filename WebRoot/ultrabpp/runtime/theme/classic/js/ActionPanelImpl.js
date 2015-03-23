ActionPanel.goBackView = function()
{
	$("#bpp_ActPanel").fadeOut(50, function()
	{
		$("#bpp_BtnPanel").fadeIn(50, function()
		{
			$("#bpp_ActFields").text("");$("#bpp_ActComment").text("");
			$('#bpp_Btn_' + ClientContext.actionID).focus();
		});
	});
}