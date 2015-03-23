var processMap = new Map();//原始的处理信息
var processList = new List();
var processM = new JsProcess();
var proMap = new Map();//分析之后的处理信息

/**
 *环节对象
 *liuchuanzu
 *2010-07-20
 */
function JsProcess()
{
	this.id = '';		//主键
	this.processId = '',//环节id
	this.name = '';		//环节名
	this.lastNo = '';	//上一节点环节号
	this.pid = '';		//父节点
	this.childList = new List();	//派发多个节点
}


//初始化方法
function init()
{
	var count = processMap.keySet().size();
	getChild(processM);
	getTabContent(processM,'div_0');
}

var str = '';
function test(model){
	str += model.processId + ' ' + model.id;
	var list = model.childList;
	str +='	派发:'+list.size() + '分别为:';
	for(var i=0;i<list.size();i++){
		var obj = list.get(i);
		str+=" "+obj.processId+' '+obj.id;
	}
	str+='<br>';
	
	for(var i=0;i<list.size();i++){
		var obj = list.get(i);
		test(obj);
	}
}


//获取子环节
function getChild(model)
{
	//alert(id);
	var list = new List();
	var count = processMap.keySet().size();
	for(var i=0;i<count&&processMap.keySet().size()>0;i++)
	{
		var key = processMap.keySet().get(i);
		var valueObj = processMap.get(key);
		if(valueObj != null && valueObj.lastNo == model.processId)
		{
			valueObj.pid = model.id;
			list.add(valueObj);
			model.childList.add(valueObj);
			processMap.remove(key);
		}
	}
	proMap.put(model.id,model);
	
	//alert(model.processId + '+' + list.size());
	for(var j = 0;j<list.size();j++){
		var obj = list.get(j);
		var templist = this.getChild(obj);
	}
	return list;
}

/**
 * Tab页点击事件
 */
function tabOnlick(id,ids){
	var model = proMap.get(id);
	var divIds = ids.split('#');
	for(var i=0;i<divIds.length;i++){
		var divId = divIds[i];
		var obj = document.getElementById('content_'+divId);
		if(obj != null){
			obj.innerHTML = '';
			obj.style.display = 'none';
		}
		document.getElementById('tab_'+divId).className = 'tab_hide';
	}
	
	document.getElementById('tab_' + id ).className = 'tab_show';
	document.getElementById('content_' + id ).style.display = '';
	getTabContent(model,id);
}


/**
 *	获取Tab页下的内容
 */
function getTabContent(model,textid)
{
	var nextModel;
	var nextModelId;
	
	var infoview = document.getElementById(textid);
	
	//将本环节的信息放到div中
	var text = document.getElementById('table_' + model.id);
	var div_text = document.createElement('div');
		div_text.id = 'div_' + obj.id;
		div_text.innerHTML += text.outerHTML;
		infoview.appendChild(div_text);
		
	var childlist = model.childList;
	
	if(childlist != null && childlist.size() >= 1){
		nextModel = childlist.get(0);
		nextModelId = textid;
	}
	
	if(childlist != null && childlist.size() > 1){
		nextModel = childlist.get(0);
		nextModelId = 'main_' + nextModel.id;
	
		//	点击后，隐藏或展现的效果DIV（title)
		var id = 'level_' + model.id;
		var title = document.createElement('div');
		title.value = id;
		title.className = 'title';
		title.onclick = function(){expandcoll(this.value);};
		title.innerHTML += '<IMG id="level_' + model.id  + '_img" src="../../../common/style/blue/images/workflow/List_close.gif" border="0" valign="middle">&nbsp;<b>'+model.name+'(一派多）-></b>';
		
		//	点击后，隐藏或展现的效果DIV（content)
		var content = document.createElement('div');
		content.id = id;
		content.style.display = 'none';
			
		var bl = true;
		var tabIds = '';//主要用来tab页的点击事件的参数
		
		for(var i=0;i<childlist.size();i++)
		{
			var obj = childlist.get(i);
			tabIds += ',' + obj.id;
		}
		
		if(tabIds != ''){
			tabIds = tabIds.substring(1);
		}
		
		var tab_text = document.createElement('div');
			tab_text.className = 'tab_bg';
			
		//	循环添加标签页的tab显示
		for(var i=0;i<childlist.size();i++)
		{
			var obj = childlist.get(i);			
			var count = i + 1;
			title.innerHTML += '<b>' + count + '、' + obj.name + '</b>';
			
			var tab_obj = document.createElement('div');
			tab_obj.id = obj.id;
			tab_obj.value = tabIds;
			tab_obj.onclick = function(){tabOnlick(this.id,this.value);};
			if(bl)
			{
				tab_obj.className = 'tab_show';
				bl = false;
			}
			else
			{
				tab_obj.className = 'tab_hide';
			}
			
			tab_obj.innerHTML = '<span>' + obj.name + '</span>';
			tab_text.appendChild(tab_obj);
		}
		
		content.appendChild(tab_text);
		
		//循环添加标签页的tab内容显示
		bl = true;
		for(var i=0;i<childlist.size();i++)
		{
			var obj = childlist.get(i);
			var mainDiv = document.createElement('div');
			mainDiv.id = "main_" + obj.id;
			content.appendChild(mainDiv);
		}
		
		infoview.appendChild(title);
		infoview.appendChild(content);
	}
	
	//递归
	if(nextModel != null){
		setType(nextModel,nextModelId);
	}
}
