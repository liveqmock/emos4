//commonTreeFrameSpace框架中页面使用
var leftFrame = parent.document.getElementById('leftTreeFrame');
var rightFrame = parent.document.getElementById('rightTreeFrame');
var bottomFrame = parent.document.getElementById('bottomTreeFrame');

//初始化结果集页面数据
function initDataResult(sltData, idType) {
	if(sltData == null || sltData.length <= 2 || sltData == 'U:;D:') {
		return 0;
	}
	$.post("../../../organizaTreeV2/getUserTreeId.action", {sltData:sltData,idType:idType}, function (newData) {
		var dataArray = newData.split(';');
		for(var i=0 ; i<dataArray.length ; i++) {
			var subData = dataArray[i].split(':');
			var type = subData[0];
			var ids = subData[1];
			if(ids == null || ids.length == 0) {
				continue;
			}
			addMoreResult(ids, subData[2], type);
		}
	});
}
function addMoreResult(ids, texts, type) {
	var idArray = ids.split(',');
	var textArray = texts.split(',');
	for(var i=0 ; i<idArray.length ; i++) {
		addOneResult(type + '_' + idArray[i], textArray[i], type);
	}
}

//获取结果集页面数据
function getDataFromResult(dataFormat) {
	getDataFromResult(dataFormat, '2');
}
//解析树返回的数据格式：U:id1,id2,...:name1,name2,...;D:id1,id2,...:name1,name2,...
//获取结果集页面数据 idtype:获取用户的时候 0:取id-loginName 1:取loginName 2:取ID（非0,1）
function getDataFromResult(dataFormat, idtype) {
	var divArray = document.getElementById('inertData').getElementsByTagName('div');
	if(divArray.length > 0)
	{
		var user_id = '';	var user_name = '';	//用户数据
		var dep_id = '';	var dep_name = '';	//部门数据
		for(var i=0 ; i<divArray.length ; i++) {
			var _dic = divArray[i].id.indexOf('_');
			if(_dic > 0) {
				var type = divArray[i].id.substring(0, _dic);
				var id = divArray[i].id.substring(_dic + 1);
				if(type == 'U') {
					if(idtype != '0') {
						var subtmp = id.split('-');
						id = subtmp[0];
						if(idtype == '1' && subtmp.length > 1)
							id = subtmp[1];
					}
					user_id += ',' + id;
					user_name += ',' + divArray[i].idText;
				} else if(type == 'D') {
					dep_id += ',' + id;
					dep_name += ',' + divArray[i].idText;
				}
			}
		}
		if(user_id != '') {
			user_id = user_id.substring(1);
			user_name = user_name.substring(1);
		}
		if(dep_id != '') {
			dep_id = dep_id.substring(1);
			dep_name = dep_name.substring(1);
		}
		var result = '';
		if(dataFormat == '') {
			result = 'U:' + user_id + ':' + user_name + ';D:' + dep_id + ':' + dep_name;
		} else if(dataFormat == 'id') {
			result = 'U:' + user_id + ';D:' + dep_id;
		} else if(dataFormat == 'U_id') {
			result = user_id;
		} else if(dataFormat == 'D_id') {
			result = dep_id;
		} else if(dataFormat == 'U') {
			result = user_id + ':' + user_name;
		} else if(dataFormat == 'D') {
			result = dep_id + ':' + dep_name;
		} else {
			result = 'U:' + user_id + ':' + user_name + ';D:' + dep_id + ':' + dep_name;
		}
		return result;
	}
	else
	{
		return '';
	}
}
//解析树返回的数据格式：U:id1,name1;U:id2,name2;...;D:id1,name1;D:id2,name2;...
//idtype:获取用户的时候 0:取id-loginName 1:取loginName 2:取ID（非0,1）
function getResolveData(idtype)
{
	var divArray = document.getElementById('inertData').getElementsByTagName('div');
	var resolveData = '';
	if(divArray.length > 0)
	{
		for(var i=0 ; i<divArray.length ; i++)
		{
			var _dic = divArray[i].id.indexOf('_');
			if(_dic > 0)
			{
				var type = divArray[i].id.substring(0, _dic);
				var id = divArray[i].id.substring(_dic + 1);
				if(type == 'U' && idtype != '0')
				{
					var subtmp = id.split('-');
					id = subtmp[0];
					if(idtype == '1' && subtmp.length > 1)
						id = subtmp[1];
				}
				resolveData += ';' + type + ':' + id + ',' + divArray[i].idText;
			}
		}
	}
	return resolveData=='' ? '' : resolveData.substring(1);
}
