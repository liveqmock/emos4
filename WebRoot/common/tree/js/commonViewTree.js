/**opt 自定义参数
 * opt通用{
 * 	query : 使用的sqlxml
 *  idColumnName : 
 *  textColumnName :
 *  sameAsId : 指定的key的值应该为setXMLAutoLoading方法传递的id值
 *  pid : 根节点id
 *  其它 : 一般为查询参数
 * }
 */
var CommonViewTree = function(container,width,height,rootid,opt){
	initOpt(opt);
	this.opt = opt;
	this.idElement = window.opener.document.getElementById(opt.idElementId);
	this.nameElement = window.opener.document.getElementById(opt.nameElementId);
	if(opt.enableCheckBoxes == "true"){
		opt.enableCheckBoxes = true;
	}else{
		opt.enableCheckBoxes = false;
	}
	if(opt.enableCheckBoxes){
		addExistItem(opt.nameElementId,opt.idElementId);//多选情况下，将已选项添加到下方
	}
	
	tree = new dhtmlXTreeObject(container,width,height,0);
	tree.enableCheckBoxes(opt.enableCheckBoxes);
	tree.setImagePath($ctx+"/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
	var parametersStr = getParametersStr(opt);
	tree.setXMLAutoLoading($ctx+"/commonViewTree/loadXML.action?"+parametersStr);
	tree.enableTreeLines(true);//是否显示结点间的连线,默认false
	tree.loadXML($ctx+"/commonViewTree/loadXML.action?id="+rootid+parametersStr);
	this.tree = tree;
	
	tree.attachEvent("onCheck", function(id, state){
		checkCommonViewTree(id, state);
	});
	
	if(opt.enableCheckBoxes){//多选情况下在加载完目录树时，选中已有的项
		tree.attachEvent("onXLE", function(){
			//选中已有的项
			$("#checkedItems li").each(function(index,item){
				tree.setCheck($(item).attr("data-tid"),true);
			})
		});
	}
}

//初始化参数中的一些默认值
function initOpt(opt){
	if(!opt.idElementId) opt.idElementId = opt.nameElementId+"_id";
	if(!opt.idColumnName) opt.idColumnName = "id";//treeItemId的列名
	if(!opt.textColumnName) opt.textColumnName = "name";//treeItemText的列名
}

//将已存在的项添加到页面中
function addExistItem(nameElementId,idElementId){
	var idsElement = window.opener.document.getElementById(idElementId).value;
	if(idsElement == "") return;
	var ids = idsElement.split(",");
	var names = window.opener.document.getElementById(nameElementId).value.split(",");
	$.each(ids,function(index,item){
		addSelected(item,names[index]);
	});
}

/**
 * 选中复选框的后续操作
 * state 0 取消选中，1 选中
 */
function checkCommonViewTree(id, state){
	var text = tree.getItemText(id);
	if(state == 1){
		addSelected(id,text)
	}else{
		removeSelected(id);
	}
}

// 添加选中的项的li
function addSelected(id,text){
	var html = '<li id="'+getTreeItemId(id)+'" data-tid="'+id+'"><span>'+text+'</span><a href="#" onclick="removeSelected(\''+id+'\');tree.setCheck(\''+id+'\',0);"></a></li>';
	$("#checkedItems").append(html);
}
// 移除选中的li
function removeSelected(id){
	$("#"+getTreeItemId(id)).remove();
}
function getTreeItemId(id){
	return "treeItem_" + id;
}

function getParametersStr(opt){
	var str = "";
	for(i in opt){
		str = str + "&opt."+i+"="+opt[i];
	}
	return str;
}

CommonViewTree.prototype.clickOK = function(){
	var id = "",
		text = "";
	if(this.opt.clickOK){//指定其他处理返回值的方法
		window.opener["depTreeClick"](this);
	}else{
		if(this.opt.enableCheckBoxes){//多选 var checkedItems = $("#checkedItems li");
			var ids = new Array();
			var texts = new Array();
			$.each($("#checkedItems li"),function(index,item){
				texts.push($(item).children("span").html());
				ids.push($(item).attr("data-tid"));
			});
			if(ids.length != 0){
				text = texts.toString();
				id = ids.toString();
			}
		}else{//单选
			text = this.tree.getSelectedItemText();
			id = this.tree.getSelectedItemId();
		}
		this.nameElement.value = text;
		if(this.idElement)
			this.idElement.value = id;
		window.close();
	}
}

CommonViewTree.prototype.clear = function(){
	this.nameElement.value = "";
	if(this.idElement)
		this.idElement.value = "";
	window.close();
}
