function insertSelectData(action,selectname,dicTypeCode,downselectname)//参数为数据字典的类型编码
{
	var selectValue = document.getElementById(selectname).value;//获取选择的下拉框的值 "/eoms/dicManager/getChildNode.action"
	$.get(action,{dictypecodePara:dicTypeCode,dicvaluePara:selectValue},function(result){
		if(result != null && result != ''){
			var ressplit = result.split(';');
			var obj = document.getElementById(downselectname);
			obj.options.length = 0;
			obj.options.add(new Option('',''));
			for(var node=0;node<ressplit.length;node++){
				var childStr = ressplit[node];
				var childArr = childStr.split(',');
				obj.options.add(new Option(childArr[1],childArr[0]));
			}
		 }else{
		 	var obj = document.getElementById(downselectname);
			obj.options.length = 0;
			obj.options.add(new Option('',''));
		 }
	 })
}