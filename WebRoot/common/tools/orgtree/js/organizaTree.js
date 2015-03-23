//commonTreeFrameSpace框架中页面使用
var leftFrame = parent.document.getElementById('leftTreeFrame');
var rightFrame = parent.document.getElementById('rightTreeFrame');
var bottomFrame = parent.document.getElementById('bottomTreeFrame');

//初始化结果集页面数据
function initDataResult(selectData, idType)
{
	if(selectData == null || selectData.length <= 2 || selectData == 'U:;D:')
		return 0;
	$.post("../../../organizaTree/getUserTreeId.action", {selectData:selectData,idType:idType}, function (newData)
	{
		var dataArray = newData.split(';');
		for(var i=0 ; i<dataArray.length ; i++)
		{
			var subData = dataArray[i].split(':');
			var type = subData[0];
			var ids = subData[1];
			if(ids == null || ids.length == 0)
				continue;
			addMoreResult(ids, subData[2], type);
		}
	});
}
function addMoreResult(ids, texts, type)
{
	var idArray = ids.split(',');
	var textArray = texts.split(',');
	for(var i=0 ; i<idArray.length ; i++)
	{
		addOneResult(type + '_' + idArray[i], textArray[i], type);
	}
}

//获取结果集页面数据
function getDataFromResult(dataFormat)
{
	getDataFromResult(dataFormat, '2');
}
//解析树返回的数据格式：U:id1,id2,...:name1,name2,...;D:id1,id2,...:name1,name2,...;T:id1,id2,...:name1,name2,...;R:id1,id2,...:name1,name2,...
//获取结果集页面数据 idtype:获取用户的时候 0:取id-loginName 1:取loginName 2:取ID（非0,1）
function getDataFromResult(dataFormat, idtype)
{
	var divArray = document.getElementById('inertData').getElementsByTagName('div');
	if(divArray.length > 0)
	{
		var user_id = '';	var user_name = '';	//用户数据
		var dep_id = '';	var dep_name = '';	//部门数据
		var tem_id = '';	var tem_name = '';	//人员模版数据
		var role_id = '';	var role_name = '';	//角色细分数据
		for(var i=0 ; i<divArray.length ; i++)
		{
			var _dic = divArray[i].id.indexOf('_');
			if(_dic > 0)
			{
				var type = divArray[i].id.substring(0, _dic);
				var id = divArray[i].id.substring(_dic + 1);
				if(type == 'U')
				{
					if(idtype != '0')
					{
						var subtmp = id.split('-');
						id = subtmp[0];
						if(idtype == '1' && subtmp.length > 1)
							id = subtmp[1];
					}
					user_id += ',' + id;
					user_name += ',' + divArray[i].getAttribute('idText');
				}
				else if(type == 'D')
				{
					dep_id += ',' + id;
					dep_name += ',' + divArray[i].getAttribute('idText');
				}
				else if(type == 'T')
				{
					tem_id += ',' + id;
					tem_name += ',' + divArray[i].getAttribute('idText');
				}
				else if(type == 'R')
				{
					role_id += ',' + id;
					role_name += ',' + divArray[i].getAttribute('idText');
				}
			}
		}
		if(user_id != '')
		{
			user_id = user_id.substring(1);
			user_name = user_name.substring(1);
		}
		if(dep_id != '')
		{
			dep_id = dep_id.substring(1);
			dep_name = dep_name.substring(1);
		}
		if(tem_id != '')
		{
			tem_id = tem_id.substring(1);
			tem_name = tem_name.substring(1);
		}
		if(role_id != '')
		{
			role_id = role_id.substring(1);
			role_name = role_name.substring(1);
		}
		var result = '';
		if(dataFormat == '')
			result = 'U:' + user_id + ':' + user_name + ';D:' + dep_id + ':' + dep_name + ';T:' + tem_id + ':' + tem_name + ';R:' + role_id + ':' + role_name;
		else if(dataFormat == 'id')
			result = 'U:' + user_id + ';D:' + dep_id + ';T:' + tem_id + ';R:' + role_id;
		else if(dataFormat == 'U_id')
			result = user_id;
		else if(dataFormat == 'D_id')
			result = dep_id;
		else if(dataFormat == 'T_id')
			result = tem_id;
		else if(dataFormat == 'R_id')
			result = role_id;
		else if(dataFormat == 'U')
			result = user_id + ':' + user_name;
		else if(dataFormat == 'D')
			result = dep_id + ':' + dep_name;
		else if(dataFormat == 'T')
			result = tem_id + ':' + tem_name;
		else if(dataFormat == 'R')
			result = role_id + ':' + role_name;
		else
			result = 'U:' + user_id + ':' + user_name + ';D:' + dep_id + ':' + dep_name + ';T:' + tem_id + ':' + tem_name + ';R:' + role_id + ':' + role_name;
		return result;
	}
	else
	{
		return '';
	}
}
//解析树返回的数据格式：U:id1,name1;U:id2,name2;...;D:id1,name1;D:id2,name2;...;T:id1,name1;T:id2,name2;...;R:id1,name1;R:id2,name2;...
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
				resolveData += ';' + type + ':' + id + ',' + divArray[i].getAttribute('idText');
			}
		}
	}
	return resolveData=='' ? '' : resolveData.substring(1);
}
//删除结果集页面节点时同步到其他的框架中
function synchOtherFrameDel(id, type)
{
}

var showWord = '';//人员选择窗口所存在的人名的首字母
//key为首字母的人名筛选显示结果
function setStatus(key)
{
	setShowOrHide(showWord);
	document.getElementById('th_'+key).style.display = '';
	document.getElementById('td_'+key).style.display = '';
	showWord = key;
}
//隐藏word指定的首字母区域
function setShowOrHide(word)
{
	var char = '';
	for(var i=0;i<word.length;i++)
	{
		char = word.substring(i,i+1);
		document.getElementById('th_'+char).style.display = 'none';
		document.getElementById('td_'+char).style.display = 'none';
	}
}
//将顶层所有字母颜色设置为空
function setSelectColor()
{
	var letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
	for(var i=0 ; i<letters.length ; i++)
	{
		document.getElementById('select_' + letters.substring(i,i+1)).style.color = '#000000';
	}
}
//初始化人员选择窗口数据
function initUserData(userData, selectId)
{
	if(userData == '')
		return ;
	var dataArray = userData.split(';');
	var js_table = document.getElementById('table_tree');
	var oldpy = '';
	var cellValue = '';
	var selectType = 'checkbox';
	if(isRadio == '0')
		selectType = 'radio';
	for(var i=0 ; i<dataArray.length ; i++)
	{
		var subData = dataArray[i].split(',');
		if(subData.length < 3)
			continue;
		if(i > 0 && oldpy != subData[0])
		{
			document.getElementById('th_'+oldpy).style.display = '';
			document.getElementById('td_'+oldpy).style.display = '';
			document.getElementById('select_'+oldpy).style.color = '#0000FF';
			var js_row = document.getElementById('td_'+oldpy);
			js_row.cells[0].innerHTML = cellValue;
			cellValue = '';
			showWord += oldpy;
		}
		oldpy = subData[0];
		var checked = '';
		if(subData[1].length >= 2 && selectId.indexOf(subData[1].substring(2)) >= 0)
		{
			checked = 'checked';
		}
		cellValue += '<nobr><input type=\"'+selectType+'\" name=\"'+selectType+'\" id=\"'+subData[1]+'\" '+checked+' onclick=\"setResultArea(this,\''+subData[1]+'\',\''+subData[2]+'\');\"/><u>' + subData[2] + '</u>；</nobr>';
		if(i == dataArray.length - 1)
		{
			document.getElementById('th_'+oldpy).style.display = '';
			document.getElementById('td_'+oldpy).style.display = '';
			document.getElementById('select_'+oldpy).style.color = '#0000FF';
			var js_row = document.getElementById('td_'+oldpy);
			js_row.cells[0].innerHTML = cellValue;
			cellValue = '';
			showWord += oldpy;
		}
	}
}
