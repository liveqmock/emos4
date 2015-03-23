function DataTransfer() {}

DataTransfer.callService = function(_service, _method, _paras, func)
{
	var para = "";
	for(var keyIt = _paras.keySet().iterator(); keyIt.hasNext();)
	{
		var key = keyIt.next();
		para += "," + key + ":\"" + _paras.get(key) + "\"";
	}
	if(para != "") para = para.substring(1);
	para = "{" + para + "}";
	$.post($ctx + "/ultrabpp/clientOpt/callService.action", {service:_service, method:_method, paras:para}, func, "json");
}