function Message() {}
Message.require = "Require";
Message.overLength = "OverLength";
//当前页面是否有提示框
Message.hasTips = function(){
	return ($(".bpp_MessageTip_Require").length + $(".bpp_MessageTip_OverLength").length) > 0;
}
Message.alert = function(text)
{
	alert(text);
}
Message.clearTips = function()
{
	$(".bpp_MessageTip_Require").remove();
	$(".bpp_MessageTip_OverLength").remove();
}
Message.clearTip = function(fieldID)
{
	$("#" + fieldID + "_tip").remove();
}
Message.tip = function(type, fieldID, text)
{
	var tipid = fieldID + "_tip";
	$("#bpp_WorksheetForm").append("<div id=\"" + tipid + "\" class=\"bpp_MessageTip_" + type + "\"></div>");
	var targetTop = $("#" + fieldID + "_ubfbox").offset().top - 10;
	var inputWidth = $("#" + fieldID + "_ubfbox .bpp_Field_Label").width();
	var targetLeft = $("#" + fieldID + "_ubfbox").offset().left + inputWidth - 15;
	$("#" + tipid).offset({top : targetTop, left : targetLeft});

	F(fieldID).change("cheartip", function(e)
	{
		Message.clearTip(fieldID);
		$("#" + fieldID).unbind(this);
	});

	var evFunc = function()
	{
		var field = $("#" + fieldID);
		if(field.length > 0)
		{
			var targetTop = $("#" + fieldID + "_ubfbox").offset().top - 10;
			var inputWidth = $("#" + fieldID + "_ubfbox .bpp_Field_Label").width();
			var targetLeft = $("#" + fieldID + "_ubfbox").offset().left + inputWidth - 15;
			$("#" + tipid).offset({top : targetTop, left : targetLeft});
		}
	}
	$(window).resize(evFunc);
}
Message.activateMask = function()
{
	var maskStr = "<div id=\"bpp_MaskLayer\"></div>";
	$("#bpp_WorksheetForm").append(maskStr);
	Message.fillMask();
	$(window).resize(Message.fillMask);
}
Message.deactivateMask = function()
{
	var mask = $("#bpp_MaskLayer");
	if(mask.length > 0) mask[0].outerHTML = "";
}
Message.fillMask = function()
{
	var maxWidth = $(document).width();
	var maxHeight = $(document).height();
	$("#bpp_MaskLayer").width(maxWidth);
	$("#bpp_MaskLayer").height(maxHeight);
}
Message.showParent = function(fieldMap,fieldName,tagShowFlag)
{
		//遍历json数组对象,通过字段Name找到字段所有的父容器
		var parentArray = new Array();
		
		for(var i=0;i<ClientContext.layoutJSon.length;i++){
			var fieldTemp  = ClientContext.layoutJSon[i];
			if(fieldTemp.fieldName==fieldName)
			{
				 parentArray[parentArray.length] = 	fieldTemp.parentName;
				 findParent(fieldMap,parentArray,	fieldTemp.parentName);
				 break;
			}
		}
		
		for(var i=0;i<parentArray.length;i++)
		{
			var fieldObject = fieldMap.get(parentArray[i]);
			var display = $("#"+fieldObject.fieldName).css("display");
			if(display=="none")
			{
				PanelField.resumeBoxHeader(fieldObject.fieldName);
			}
			
			if(tagShowFlag==1)
			{
				var childNameArray = fieldObject.childNameArray;
				//如果子容器的数组大于0，说明当前组件为容器组，且容器组中有多个容器
				if(childNameArray.length>0&&i>0)
				{
					//需要提取之前的容器Name，做匹配，如果不是之前的Name，就将被隐藏显示
					var lastPanelName = parentArray[i-1];
						for(var j=0;j<childNameArray.length;j++){
							var childname = childNameArray[j];
							if(childname!=lastPanelName){
								$("#"+childname).css("display","none");
								$("#" + childname + "_Header").removeClass("bpp_TabPanelGroup_Header_Active");
							}else{
								$("#" + childname + "_Header").addClass("bpp_TabPanelGroup_Header_Active");
							}
						}
				}
			}
			
		}	
		
}

function findParent(fieldMap,parentArray,fieldName){
		var fieldTemp = fieldMap.get(fieldName);
		var parentName = fieldTemp.parentName;
		if(parentName!="main" && parentName!=""){
			parentArray[parentArray.length] = 	parentName;
			findParent( fieldMap,parentArray,parentName);
		}
}




