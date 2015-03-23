// 点击页面搜索按钮，显示和隐藏查询条件
function searchData(type) {
	if(type == 'user') {
		document.getElementById('userTable').style.display = '';
		document.getElementById('userDataTable').style.display = '';
		document.getElementById('groupTable').style.display = 'none';
		document.getElementById('groupDataTable').style.display = 'none';
	} else if(type == 'group') {
		document.getElementById('groupTable').style.display = '';
		document.getElementById('groupDataTable').style.display = '';
		document.getElementById('userTable').style.display = 'none';
		document.getElementById('userDataTable').style.display = 'none';
	}
}
// 响应查询，去后台获取数据
function findInfo(type) {
	var rearchData = '';
	var radioObj;
	if(type == 'user') {
		rearchData = combParam(rearchData, 'username');
		rearchData = combParam(rearchData, 'loginname');
		rearchData = combParam(rearchData, 'mobile');
		rearchData = combParam(rearchData, 'position');
		radioObj = document.getElementsByName('userdisptype');
	} else if(type == 'group') {
		rearchData = combParam(rearchData, 'company');
		rearchData = combParam(rearchData, 'dept');
		rearchData = combParam(rearchData, 'group');
		radioObj = document.getElementsByName('groupdisptype');
	}
	if(rearchData != '') {
		rearchData = rearchData + ';type=' + type;
	}
	document.getElementById('rearchData').value = rearchData;
	var disptype = '';
	for(var i=0 ; i<radioObj.length ; i++) {
		if(radioObj[i].checked) {
			disptype = radioObj[i].value;
			break;
		}
	}
	if(disptype == 'tree') {
		rearchUserOrDep();
	} else {
		rearchForList(rearchData, type);
	}
	showsearch();
}
// 合并参数
function combParam(rearchData, param) {
	var data = document.getElementById(param).value;
	if(data != '') {
		if(rearchData != '') {
			rearchData += ';';
		}
		rearchData += param + '=' + data;
	}
	return rearchData;
}
// 查询数据到列表
function rearchForList(rearchData, type) {
	rearchData	= encodeURI(rearchData);
	var selectId = bottomFrame.contentWindow.getDataFromResult('id', '0');
	$.post($ctx+'/organizaTreeV2/rearchForList.action', {rearchData:rearchData,selectId:selectId}, function (resultData) {
		document.getElementById('resultData').value = resultData;
		var table = type + 'DataTable';
		clearTableData(table);
		insertTableData(table, type);
	});
}
// 清除列表中数据
function clearTableData(table) {
	var tableObj = document.getElementById(table);
	for(var i=tableObj.rows.length-1 ; i>=1 ; i--) {
		tableObj.deleteRow(i);
	}
}
// 向列表插入数据
function insertTableData(table, type) {
	var resultData = document.getElementById('resultData').value;
	if(resultData == '') {
		return 0;
	}
	if (type == 'user') {
		type = 'U';
	} else if(type == 'group') {
		type = 'D';
	}
	var tableObj = document.getElementById(table);
	var rowArray = resultData.split(';');
	for(var i=0 ; i<rowArray.length ; i++) {
		if(rowArray[i] == '') {
			continue;
		}
		var rowObj = tableObj.insertRow(-1);
		var colArray = rowArray[i].split(',');
		if(colArray.length < 2) {
			continue;
		}
		for(var j=0 ; j<colArray.length ; j++) {
			if(colArray[j] == '') {
				if(j == 0) {
					break;
				} else {
					rowObj.insertCell(-1);
					continue;
				}
			}
			var colObj = rowObj.insertCell-1();
			if(j == 0) {
				var colSub = colArray[0].split(':');
				var id = colSub[0];
				var check = '';
				if(colSub.length > 1 && colSub[1] == 'true') {
					check = 'checked';
				}
				colObj.innerHTML = '<input type=\"checkbox\" id=\"' + id + '\" name=\"checkbox\" value="'+colArray[j]+'" ' + check + ' onclick=\"listSelectData(this, \'' + id + '\', \'' + colArray[1] + '\', \'' + type + '\');\"/>';
			} else {
				colObj.innerHTML = colArray[j];
			}
		}
	}
}
// 从列表选择数据，对结果集区域同步
function listSelectData(checkbox, id, text, type) {
	if(checkbox.checked) {
		bottomFrame.contentWindow.addOneResult(id, text, type);
	} else {
		bottomFrame.contentWindow.delOneResult(id, type);
	}
}
// 结果集区域选择数据同步到列表区域
function resultToListSelect(id) {
	if(document.getElementById(id)) {
		document.getElementById(id).checked = true;
	}
}
// 结果集区域取消选择同步到列表区域
function listCancelSelect(id) {
	if(document.getElementById(id)) {
		document.getElementById(id).checked = false;
	}
}
// 取消选择全部已选数据
function listCancelSelectAll() {
	var checkbox = document.getElementsByName('checkbox');
	for (var i=0 ; i<checkbox.length ; i++) {
		checkbox[i].checked = false;
	}
}
// 对于树型模式查询数据时，需将查询条件重新赋值到各个搜索区域
function initParam() {
	var rearchData = document.getElementById('rearchData').value;
	if(rearchData == '') {
		return 0;
	}
	var type = 'user';
	var dataArray = rearchData.split(';');
	for(var i=0 ; i<dataArray.length ; i++) {
		var subData = dataArray[i].split('=');
		if(subData.length < 2) {
			continue;
		}
		if(document.getElementById(subData[0])) {
			document.getElementById(subData[0]).value = subData[1];
		}
		if(subData[0] == 'type') {
			type = subData[1];
		}
	}
	insertTableData(type+'DataTable', type);
	defaultSelectList(type);
}
// 默认选中列表中数据
function defaultSelectList(type) {
	if(type == 'group') {
		type = 'D_';
	} else {
		type = 'U_';
	}
	var selectId = bottomFrame.contentWindow.getDataFromResult(type+'id', '0');
	var idArray = selectId.split(',');
	for(var i=0 ; i<idArray.length ; i++) {
		if(idArray[i] == '') {
			continue;
		}
		resultToListSelect(type + idArray[i]);
	}
}
//回车查询
function enterSearch(e, type) {
	// 响应回车
	var key = window.event ? e.keyCode : e.which;
	if (key == 13)
		findInfo(type);
}
