

function helper(returnId) {
	if(isRadioPara == "0") {
		fillInOpener(returnId);return;
	}
	// getDepAndUser();
	var checkedItems = $("#checkedItems li");
	if (checkedItems.length == 0) {
		alert("请选择人员/部门");
	} else {
		var id = "";
		var text = "";
		$.each(checkedItems,function(index,item){
			var tid = $(item).attr("data-tid");
			var type = $(item).attr("data-type");
			id = id + "#;" + type + "#:" + tid;
			text = text + "," + $(item).children("span").html();
		})
		id = id.substring(2);
		text = text.substring(1);
		if (returnId.indexOf("@") == -1) {
			window.opener.document.getElementById(returnId).value = text;
		} else {
			var rids = returnId.split('@');
			window.opener.document.getElementById(rids[0]).value = id;
			window.opener.document.getElementById(rids[1]).value = text;
		}
		window.close();
	}
}

function fillInOpener(returnId){
	var id = tree.getSelectedItemId();
	var text = tree.getSelectedItemText();
	if(id == ""){
		alert("请选择人员/部门");return;
	};
	if (returnId.indexOf("@") == -1) {
		window.opener.document.getElementById(returnId).value = text;
	} else {
		var rids = returnId.split('@');
		window.opener.document.getElementById(rids[0]).value = getUserType(id)+"#:"+id;
		window.opener.document.getElementById(rids[1]).value = text;
	}
	window.close();
}

/**
 * 清空父页面的控件内容
 */
function clear2(returnId){
	var rids=returnId.split('@');
	window.opener.document.getElementById(rids[0]).value="";
	window.opener.document.getElementById(rids[1]).value="";
	window.close();
}

/**
 * 选中已有的项
 */
function setChecked(returnid){
	$("#checkedItems li").each(function(index,item){
		tree.setCheck($(item).attr("data-tid"),true);
	})
}

//将opener中已存在的项添加到页面中
function addExistItemInOpener(returnid){
	var rids= returnid.split('@');
	var idsStr = window.opener.document.getElementById(rids[0]).value;
	if(idsStr == "") return;
	var ids = idsStr.split("#;");
	var texts = window.opener.document.getElementById(rids[1]).value.split(",");
	for(var i = 0; i < ids.length; i++) {
		var type_id = ids[i].split("#:");
		var type = type_id[0];
		var id = type_id[1];
		var text = texts[i];
		addSelectedLi(id,type,text)
	}
}

/**
 * 选中复选框的后续操作
 * state 0 取消选中，1 选中
 */
function checkPeopleTree(id, state){
	var text = tree.getItemText(id);
	if(state == 1){
		addSelectedLi(id,getUserType(id),text)
	}else{
		removeSelectedLi(id);
	}
}

// 添加选中的项的li
function addSelectedLi(id,type,text){
	var html = '<li id="'+getTreeItemId(id)+'" data-type="'+type+'" data-tid="'+id+'"><span>'+text+'</span><a href="#" onclick="removeSelectedLi(\''+id+'\');tree.setCheck(\''+id+'\',0);"></a></li>';
	$("#checkedItems").append(html);
}
// 移除选中的li
function removeSelectedLi(id){
	$("#"+getTreeItemId(id)).remove();
}
function getTreeItemId(id){
	return "treeItem_" + id;
}
function getUserType(id){
	return tree.getUserData(id,"type")=="U"?"U":"G";
}