function KMHandler() {}

KMHandler.createKM = function(kmObj)
{
	$("#kmcaption")[0].value = Util.keyFieldReplace(kmObj.caption);
	$("#kmkeyword")[0].value = Util.keyFieldReplace(kmObj.keyword);
	$("#kmcategory")[0].value = Util.keyFieldReplace(kmObj.category);
	$("#kmcontent")[0].value = Util.keyFieldReplace(kmObj.content);
	$('#kmform').submit();
}


//获取同步至知识库的工单信息
KMHandler.getTiketInfoToKm = function(baseSchema,baseID,callback)
{
	$.post($ctx+'/relatekm/getTicketInfoToKm.action',{kmbaseSchema:baseSchema,kmbaseID:baseID},function(data) {
		if(data!=null && data!='null'){
			callback(data);
		}
	},"json");
	
	
}