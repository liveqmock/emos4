/**
* 导出EXCEL
*/
function excelExport(type,wfSortId,baseSchema){
	 var isAll = "current";
	 if(confirm("是否导出全部记录?")){
	 	isAll = "all";
	 }
	 document.forms[0].action="sheetExport.action?type="+type+"&isAll="+isAll+"&wfSortId="+wfSortId+"&baseSchema="+baseSchema;
	 document.forms[0].submit();
	 document.forms[0].action="";
}