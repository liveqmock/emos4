var str      = document.getElementById("itemsId").value;
var treeimgs =document.getElementById("treeimgs").value;
var url = '../lefttree/loadxml.action';
var firstLocation ='';
var dhxAccord = new dhtmlXAccordion("accordObj");


//parseStr passed itemsId
function parseStr(s){
	str = [];
	var a = eval(s);
	for(var i=0;i<a.length;i++){
		var temp = eval(a[i]);
		str.push(temp);
	}
}
//init menu items
function initItemDiv(){
	parseStr(str);
	for(var aa in str){
		if(str[aa]==str[0]){
			firstLocation = str[aa].id;
		}
		var itObj = dhxAccord.addItem(str[aa].id, str[aa].text);
		itObj.firstChild.setAttribute("nodeurl",str[aa].nodeurl);
		itObj.firstChild.setAttribute("title",str[aa].url);
		itObj.firstChild.style.cursor='pointer';
	}
	dhxAccord.attachEvent("onActive", function(itemId){
        var item = dhxAccord.cells(itemId);
        var itemId = item.getId();
        loadXML(itemId);
	});
}
//invoke an action with id
function  loadXML(itemId){
	var tree = dhxAccord.cells(itemId).attachTree();
	var nodeurl = dhxAccord.cells(itemId).firstChild.nodeurl;
	
	tree.setOnClickHandler(function(id){openPathDocs(id,tree);});
	//tree.enableCheckBoxes(false);
	if(nodeurl == '' || "undefined"==typeof(nodeurl)){
	tree.setImagePath(treeimgs+"/csh_bluebooks/");
	tree.setXMLAutoLoading(url);
	tree.loadXML(url+"?id="+itemId+"&&root=true");}
	else if(nodeurl == 'KM'){// 添加自定义的动态树
		tree.setImagePath(treeimgs+"/../../../system/cmdb/images/icon/templateIcons/");
		tree.setXMLAutoLoading("../cmdb/buildTree.action?treeName=KM");
		tree.loadXML("../cmdb/buildTree.action?id=0&treeName=KM");
	}else if(nodeurl == 'CM'){// 添加自定义的动态树CMDB管理
		tree.setImagePath(treeimgs+"/../../../system/cmdb/images/icon/templateIcons/");
		tree.setXMLAutoLoading("../cmdb/buildTree.action?treeName=CM&popedom=manage");
		tree.loadXML("../cmdb/buildTree.action?id=0&treeName=CM&popedom=manage");
	}else if(nodeurl == 'CMVIEW'){// 添加自定义的动态树CMDB查看
		tree.setImagePath(treeimgs+"/../../../system/cmdb/images/icon/templateIcons/");
		tree.setXMLAutoLoading("../cmdb/buildTree.action?treeName=CM&popedom=view");
		tree.loadXML("../cmdb/buildTree.action?id=0&treeName=CM&popedom=view");
	}else if(nodeurl == 'CMAPP'){// 添加自定义的动态树CMDB审批
		tree.setImagePath(treeimgs+"/../../../system/cmdb/images/icon/templateIcons/");
		tree.setXMLAutoLoading("../cmdb/buildTree.action?treeName=CM&popedom=approve");
		tree.loadXML("../cmdb/buildTree.action?id=0&treeName=CM&popedom=approve");
	}else {
	tree.setImagePath(treeimgs+"/csh_bluebooks/");
	tree.setXMLAutoLoading(nodeurl);
	tree.loadXML(nodeurl);
	}
	if(dhxAccord.cells(itemId).firstChild.title!=''&&dhxAccord.cells(itemId).firstChild.title!='#')
	parent.right.location.href = dhxAccord.cells(itemId).firstChild.title;
}
//open url to refresh right frame to show
function openPathDocs(id,tree){
	if(tree.getUserData(id,"url")!=null&&tree.getUserData(id,"url")!="#"){
	
	   var url = tree.getUserData(id,"url");
	
		if(tree.getUserData(id,"opencode")=="1"){
			window.open(url);
		}else{
			parent.right.location.href = url;
		}
		return;
	}
}
//open first item when leftree is first loaded default
function openFirstItem(){
	if(dhxAccord!=null&&dhxAccord.cells(firstLocation)!=null){
		dhxAccord.cells(firstLocation).open();
		loadXML(firstLocation);
	}
}

window.onload=function(){
	initItemDiv();
	openFirstItem();
}
