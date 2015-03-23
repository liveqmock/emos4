//公用的树选择方法
var deptTreeWindow;
function openDeptTree(enableCheckBoxes,nameElementId,idElementId){
	if(!idElementId) idElementId = "";
	var url = $ctx+"/commonViewTree/commonViewTree.action?opt.query=SQL_SM_DepList.query&opt.nameElementId="+nameElementId+"&opt.enableCheckBoxes="+enableCheckBoxes+"&opt.idElementId="+idElementId+"&opt.idColumnName=pid&opt.textColumnName=depname&opt.parentid=0&opt.sameAsId=parentid";
//	if(!enableCheckBoxes){//单选 
//		url = url + "&opt.clickOK=depTreeClick";
//	}
	deptTreeWindow = window.open(url,'deptTree','height=800px,width=600px,toolbar=no,status=no,location=no,directories=no');
}
/*
function depTreeClick(obj){
	var id = "",
		text = "";
	var selectedItemId = obj.tree.getSelectedItemId();
	document.getElementById(obj.opt.nameElementId).value = obj.tree.getUserData(selectedItemId,"DEPFULLNAME");
	deptTreeWindow.close();
}
*/

//服务目录树
function openServiceCategoryTree(enableCheckBoxes,nameElementId,idElementId){
	if(!idElementId) idElementId = "";
	var url = $ctx+"/commonViewTree/commonViewTree.action?opt.query=SQL_CDB_SERVEICE_CATEGORY.query&opt.nameElementId="+nameElementId+"&opt.enableCheckBoxes="+enableCheckBoxes+"&opt.idElementId="+idElementId+"&opt.parentid=0&opt.sameAsId=parentid";
	deptTreeWindow = window.open(url,'deptTree','height=800px,width=600px,toolbar=no,status=no,location=no,directories=no');
}
//变更分类树
function openChgSortTree(enableCheckBoxes,nameElementId,idElementId){
	if(!idElementId) idElementId = "";
	var url = $ctx+"/commonViewTree/commonViewTree.action?opt.query=SQL_CDB_ChgSort.query&opt.nameElementId="+nameElementId+"&opt.enableCheckBoxes="+enableCheckBoxes+"&opt.idElementId="+idElementId+"&opt.parentid=0&opt.sameAsId=parentid";
	deptTreeWindow = window.open(url,'deptTree','height=800px,width=600px,toolbar=no,status=no,location=no,directories=no');
}