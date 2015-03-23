function StatusFlowMap() {}

StatusFlowMap.addFreeStatusList = function(statusList)
{
	var sList = statusList.split(",");
	for(var i = 0; i < sList.length; i++)
	{
		ClientContext.allStatusList.add({name:sList[i]});
	}
}

StatusFlowMap.show = function()
{
	var serviceParaMap = new Map();
	serviceParaMap.put("baseSchema", ClientContext.baseSchema);
	serviceParaMap.put("defCode", ClientContext.defCode);
	DataTransfer.callService("bppFormClientCall", "getStatusFlowMap", serviceParaMap, function(data)
	{
		for(var i = 0; i < data.length; i++)
		{
			ClientContext.allStatusList.add(data[i]);
		}

		var serviceParaMap2 = new Map();
		serviceParaMap2.put("baseID", ClientContext.baseID);
		serviceParaMap2.put("baseSchema", ClientContext.baseSchema);
		DataTransfer.callService("bppFormClientCall", "getEndpointProcess", serviceParaMap2, function(data)
		{
			StatusFlowMap.showMap(ClientContext.allStatusList, data);
		});
	});
}
StatusFlowMap.showMap = function(statusList, endpointList)
{
	if(statusList.size() > 0)
	{
		$("#bpp_StatusFlowMap_imgs").append("<td style=\"height:30px;\">&nbsp;</td>");
		$("#bpp_StatusFlowMap_text").append("<td>&nbsp;</td>");
		var hasActive = false;
		for(var sit = statusList.iterator(); sit.hasNext();)
		{
			var st = sit.next();
			var classType = "";
			var classAppend = "";
			var appendText = "";
			if(sit.index == 0)
			{
				classType += "begin";
				if(st.name == ClientContext.baseStatus)
				{
					classAppend = "active";
					hasActive = true;
				}
				else classAppend = "pass";
			}
			else if(sit.index == statusList.size() - 1)
			{
				classType += "end";
				if(st.name == ClientContext.baseStatus)
				{
					classAppend = "pass";
					hasActive = true;
				}
				else classAppend = "wait";
			}
			else
			{
				classType += "mid";
				if(st.name == ClientContext.baseStatus || (ClientContext.baseStatus == "待处理" && st.name == "处理中"))
				{
					classAppend = "active";
					hasActive = true;

					var activeCount = 0;
					var allCount = 0;
					for(var i = 0; i < endpointList.length; i++)
					{
						if(endpointList[i].flagactive == 1) activeCount++;
						allCount++;
					}
					//appendText = "（" + activeCount + "/" + allCount + "）";
				}
				else if(hasActive) classAppend = "wait";
				else classAppend = "pass";
			}
			$("#bpp_StatusFlowMap_imgs").append("<td class=\"bpp_" + classType + "_" + classAppend + "\"></td>");
			$("#bpp_StatusFlowMap_text").append("<td align=\"center\" class=\"bpp_" + classAppend + "_text\">" + st.name + appendText + "</td>");
		}
		$("#bpp_StatusFlowMap_imgs").append("<td>&nbsp;</td>");
		$("#bpp_StatusFlowMap_text").append("<td>&nbsp;</td>");
		$("#bpp_StatusFlowMap").css("display", "block");
	}
}