/**
 该函数完成索引系统主页面(search.jsp页面)的普通查询功能
*/
function planeSearch() {
	var keyWords = document.getElementById("txtKeyword").value;
	if (keyWords == "") {
		window.location.reload();
		return;
	}
	document.getElementById("form_keyWords").value = keyWords;
	var categoryId = document.getElementById("form_categoryId").value;
	if (categoryId == "") {
		document.getElementById("form_categoryId").value = document.getElementById("allcategoryId").value;
	}
	document.getElementById("form_page").value = "1";
	document.getElementById("form_op").value = "1";
	document.getElementById("searchLevel").value = "plane";
	document.searchForm.submit();
}
/**
 * 建议性搜索
 * @param suggestionValue --搜索建议词
 */
function suggestionSearch(suggestionValue){
	if(suggestionValue==null||suggestionValue=="undefined"||suggestionValue=="")
		return;
	document.getElementById("form_keyWords").value = suggestionValue;
	document.getElementById("form_page").value = "1";
	document.getElementById("form_op").value = "1";
	document.searchForm.submit();
}
/**
 * 该函数完成检索结果页面(resultList.jsp页面)的普通查询和高级查询的翻页查询。
 * @param pageNum --查询页码
 * @param op --查询类型，在后台规定的两种类型为”新建查询“和“缓存翻页查询“两种
*/
function startSearch(pageNum, op) {
	if (op > 1 && document.getElementById("searchLevel").value == "advanced") {//高级搜索的翻页
		document.getElementById("form_categoryId").value = document.getElementById("advancedCategoryId").value;
		document.getElementById("form_page").value = pageNum;
		document.getElementById("form_op").value = op;
	} else {//本页面内的普通搜索或普通搜索的翻页搜索
		var keyWords = document.getElementById("txtKeyword").value;
		if (keyWords == "") {
			return;
		}
		document.getElementById("form_fieldAndValue").value = "";
		document.getElementById("form_keyWords").value = keyWords;
		var categoryId = document.getElementById("form_categoryId").value;
		if (categoryId == "") {
			document.getElementById("form_categoryId").value = document.getElementById("allcategoryId").value;
		}
		document.getElementById("form_page").value = pageNum;
		document.getElementById("form_op").value = op;
		document.getElementById("searchLevel").value = "plane";
	}
	document.searchForm.submit();
}
/*
 完成打开URL链接的函数
*/
function openDoc(url) {
	window.open(url);
}
/*
 该函数完成按照给定的宽度和高度打开URL链接
*/
function openwindow(url,name,iWidth,iHeight)
{
	var url; 
	var name; 
	var iWidth; 
	var iHeight; 
	var iTop = (window.screen.availHeight-30-iHeight)/2; 
	var iLeft = (window.screen.availWidth-10-iWidth)/2; 
	window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes');
}
/*-----------------------高级搜索_开始----------------------------*/
/*
 该函数完成高级搜索功能
 @param ctx --项目路径
*/
function advancedSearch(ctx) {
	var categoryId = document.getElementById("form_categoryId").value;
	if (categoryId == "" && document.getElementById("allcategoryId") != null) {
		categoryId = document.getElementById("allcategoryId").value;
	}
	if (categoryId != "" && categoryId != null) {
		categoryId = categoryId.replace(/&comm;/g, ",");
		//showPopWin(ctx+"/fulltext/search/advancedSearch.jsp?categoryId=" + categoryId, 442, 269, null);
		openwindow(ctx+"/fulltext/search/advancedSearch.jsp?categoryId=" + categoryId,"",1000,500);
		//openDoc(ctx+"/fulltext/search/advancedSearch.jsp?categoryId=" + categoryId);
	}
}
/*-------------------------高级搜索_结束---------------------------*/

/*--------------选择索引类别时，设置突显和非突显_开始-------------------*/
/*
 该函数完成当选择某个索引类别时，该索引类别突显，其他未选中的类别非突显，并且将选中的类别的ID设置到form的input中
 @param id --选中的索引类别id
 @param source --被触发事件的对象
 @param type --从哪里调用的该方法
*/
function setCategory(id, source, type) {
	var categoryArr = document.getElementsByTagName("span");
	for (var i = 0; i < categoryArr.length; i++) {
		if (categoryArr[i].className == "category_name_selected") {
			categoryArr[i].className = "category_name";
		}
	}
	source.className = "category_name_selected";
	document.getElementById("form_categoryId").value = id;
	//如果调用该方法是从topLevel节点调用的，则删除“_topLevelMenuId”和“_selectedMenuText”的input，不将其传送到resultList.jsp页面
	if(type!="_sysLevel")
	{
		var inpId = document.getElementById("_topLevelMenuId");
		var inpName = document.getElementById("_selectedMenuText");
		//alert(document.getElementById("_topLevelMenuId"));
		if(inpId!=null)
			$("#_topLevelMenuId").remove();
		if(inpName!=null)
			$("#_selectedMenuText").remove();
		//alert(document.getElementById("_topLevelMenuId"));
	}
}
/*----------------选择索引类别时，设置突显和非突显_结束-----------------*/

/*----------------回车提交查询，历史关键字后台提取及其上下左右的选择_开始-------------------*/
/*
 该函数完成回车提交查询、上下左右键选择历史关键的功能
 @param event --事件对象
 @param method --如果是回车提交查询，该参数表示到底选择哪个查询方法
 @param type --如果type为2的话，表示为检索结果页面的subInput中的提交的查询
*/
function checkSubmit(event,method,type) {
	var srcCode = event.keyCode;
	if (srcCode == undefined) {
		srcCode = event.which;
	}
	if (srcCode == 13) {
		if (type == 2) {
			document.getElementById("txtKeyword").value = document.getElementById("subInput").value;
		}
		var aArr = $("#lstCondition a");
		var len = aArr.length;
		for(var i=0;i<len;i++)
		{
			if(aArr[i].style.background=='#e5f2f4')
			{
				aArr[i].click();
				return;
			}
		}
		if(method == 1)
			planeSearch();
		else if(method == 2)
			startSearch(1, 1);
	}
	if(srcCode==37 || srcCode==38)//左和上
	{
		var aArr = $("#lstCondition a");
		if(aArr.length==0)
			return;
		var len = aArr.length;
		var tag = false;
		for(var i=0;i<len;i++)
		{
			//alert(aArr[i].style.background);
			if(aArr[i].style.background=='#e5f2f4')
			{
				if(i==0)
				{
					aArr[len-1].style.background='#e5f2f4';
					aArr[i].style.background='#ffffff';
					tag = true;
					break;
				}
				else
				{
					aArr[i-1].style.background='#e5f2f4';
					aArr[i].style.background='#ffffff';
					tag = true;
					break;
				}
			}
		}
		if(!tag)
		{
			aArr[len-1].style.background='#e5f2f4';
		}
	}
	if(srcCode==39 || srcCode==40)//右和下
	{
		var aArr = $("#lstCondition a");
		if(aArr.length==0)
			return;
		var len = aArr.length;
		var tag = false;
		for(var i=0;i<len;i++)
		{
			//alert(aArr[i].style.background);
			if(aArr[i].style.background=='#e5f2f4')
			{
				if(i!=len-1)
				{
					aArr[i+1].style.background='#e5f2f4';
					aArr[i].style.background='#ffffff';
					tag = true;
					break;
				}
				else
				{
					aArr[0].style.background='#e5f2f4';
					aArr[i].style.background='#ffffff';
					tag = true;
					break;
				}
			}
		}
		if(!tag)
		{
			aArr[0].style.background='#e5f2f4';
		}
	}
}

var kwdTimer; //历史关键字定时器
var originalValue = ""; //记录上一次文本框中值
/*
 该函数完成从后台获取历史关键字
 @param ctx --项目根路径
*/
function getHisKWD(ctx) {
	kwdTimer = window.setInterval(function () {
		var temp = document.getElementById("txtKeyword").value;
		if (temp == originalValue) {
			return;
		}
		if (temp != "") {
			$.post(ctx+"/fulltext/search/getHisKWD.jsp?rnd="+(new Date()).getTime(), {kwd:temp}, function (data) {
				if (data != "") {
					data = data.replace(/(^\s*)|(\s*$)/g, "");
					if (data.length != 0) {
						var hiss = data.split(",");
						$("#lstCondition a").remove();
						var len = hiss.length;
						var ahtml;
						for (var i = 0; i < len; i++) {
							ahtml = "<a style=\"background:#ffffff\" onmouseover=\"this.style.background='#e5f2f4'\"";
							ahtml += "onmouseout=\"this.style.background='#ffffff'\"";
							ahtml += " href='javascript:selectpwd(\"";
							ahtml += hiss[i] + "\");'>" + hiss[i] + "</a>";
							$(ahtml).appendTo("#lstCondition");
							ahtml = "";
						}
						document.getElementById("lstCondition").style.display = "block";
					} else {
						document.getElementById("lstCondition").style.display = "none";
					}
					originalValue = temp;
				} else {
					document.getElementById("lstCondition").style.display = "none";
					originalValue = temp;
				}
			});
		} else {
			document.getElementById("lstCondition").style.display = "none";
			originalValue = temp;
		}
	}, 2000);
}
/*
 该函数清楚历史关键字定时器
*/
function clearTimer() {
	window.clearInterval(kwdTimer);
}
/*
 该函数完成从历史关键字下拉列表中选择历史关键字并提交检索
 @param pwd --所选择的历史关键字
*/
function selectpwd(pwd) {
	document.getElementById("txtKeyword").value = pwd;
	document.getElementById("lstCondition").style.display = "none";
	document.getElementById("btnSearch").click();
}
/*-------------------------回车提交查询，历史关键字后台提取及其上下左右的选择_结束------------------------*/

/*------------------------索引类别翻页_开始----------------------*/
/*
 该函数实现翻页功能。
 @param method 表示“上一页”还是“下一页”，固定值为“pre”或“next”，pre表示上一页，next表示下一页。
*/
function switchCtgSpan(method)
{
	if(method=='pre')
	{
		var curspan = parseInt(document.getElementById("curCtgSpan").value);
		document.getElementById("ctgSpan_"+curspan).style.display = "none";
		document.getElementById("ctgSpan_"+(curspan-1)).style.display = "block";
		if(curspan==2)
			document.getElementById("previous_cas").style.display = "none";
		document.getElementById("next_cas").style.display = "block";
		document.getElementById("curCtgSpan").value = curspan - 1;
	}
	else if(method=='next')
	{
		var allspan = parseInt(document.getElementById("allCtgSpan").value);
		var curspan = parseInt(document.getElementById("curCtgSpan").value);
		document.getElementById("ctgSpan_"+curspan).style.display = "none";
		document.getElementById("ctgSpan_"+(curspan+1)).style.display = "block";
		if(curspan==allspan-1)
			document.getElementById("next_cas").style.display = "none";
		document.getElementById("previous_cas").style.display = "block";
		document.getElementById("curCtgSpan").value = curspan + 1;
	}
}
/*
 该函数实现设置被选中的索引类别所在的那一页显示，其他页隐藏（用于检索结果页面）。
*/
function setSelectedSpan()
{
	var curspan_obj = document.getElementById("curCtgSpan");
	var allspan_obj = document.getElementById("allCtgSpan");
	if(curspan_obj==null || allspan_obj==null)
		return;
	var curspan = parseInt(curspan_obj.value);
	var allspan = parseInt(allspan_obj.value);
	if(curspan>1)
	{
		document.getElementById("previous_cas").style.display = "block";
		document.getElementById("ctgSpan_1").style.display = "none";
		document.getElementById("ctgSpan_"+curspan).style.display = "block";
		if(curspan==allspan)
		{
			document.getElementById("next_cas").style.display = 'none';
		}
	}
}
/*----------------------------索引类别翻页_结束------------------------*/

/*------------------------索引类别树形菜单_开始-------------------------*/
var levelCounter = 0;//索引类别级别计数器，记录了当前生成到了第几级索引类别
var topLevelId = ""; //当前选择的索引的类别的顶级索引类别的ID
var paramsLoaderForm = "searchForm"; //用于传递参数到检索结果页面(resultList.jsp)的的form的id
/*
 该函数完成重置级别菜单的DIV，也就是删除从levelCounter到level级别的所有DIV。
 @param level --一直删除到哪个级别
 @param isInclude --表示是否表示level这个级别的DIV
*/
function resetLevelDiv(level,isInclude){
	var tempDiv;
	var k = level;
	if(isInclude==false)
		k = level+1;
	for(var i=levelCounter;i>=k;i--)
	{
		tempDiv = document.getElementById("levelDiv_"+i);
		if(tempDiv!=null)
			document.body.removeChild(tempDiv);
	}
	levelCounter = k-1;
}
/*
 该函数完成够造特定级别DIV并在这个DIV种显示当前索引类别下的所有直接子索引类别。
 @param parentId --父索引类别ID
 @param basePath --项目根路径
 @param level --构造的DIV的级别
 @param p_SelectImg --所点击表示”下一级“图标的span的ID
 @param p_self --所点击表示”下一级“图标的span对象本身
 @param p_selectText --用于显示当前选择索引类别的span的ID
*/
function showChildCategory(parentId,basePath,level,p_SelectImg,p_self,p_selectText)
{
	resetLevelDiv(parseInt(level),true);
	$.getJSON(basePath+"/fulltext/search/levelCategoryMenu.jsp?rnd="+new Date(),{parentId:parentId,level:level},function(returnValue){
		if(returnValue.length<=0)
			return;
		levelCounter = parseInt(level);
		if("1"==level)//如果是第一级别，就记录下topLevelId
			topLevelId = parentId;
		var levelDiv = document.createElement("<div id='levelDiv_"+level+"'/>");
		levelDiv.className = "selectDiv";
		levelDiv.onmouseout = function(){
			outSelectDiv(this.id,parseInt(level));
			hideChildDiv(parseInt(level));
		};
		levelDiv.onmouseover = function(){
			if("1"==level)
				setLevelDivPosition(this.id,p_SelectImg,p_self);
			else
				setLevelDivPosition2(levelDiv.id,p_SelectImg,p_self);
			persistParentDiv(parseInt(level));
		};
		document.body.appendChild(levelDiv);
		if("1"==level)
			setLevelDivPosition(levelDiv.id,p_SelectImg,p_self);
		else
		{
			setLevelDivPosition2(levelDiv.id,p_SelectImg,p_self);
		}
		addChild(levelDiv.id,returnValue,level,p_selectText,basePath);
	});
}
/*
 该函数完成将从后台取得子索引类别添加生成级别DIV中。
 @param showDiv --生成的级别DIV的ID
 @param child --从后台取得的所有子索引类别
 @param leve --当前级别DIV的级别数目
 @param p_selectText --用于显示当前选择索引类别的span的ID
 @param basePath --项目根路径
*/
function addChild(showDiv,child,level,p_selectText,basePath)
{
	var len = child.length;
	var conStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>\n";
	for(var i=0;i<len;i++)
	{
		conStr += "<tr><td id='td_"+level+i+"'";
		conStr += " onmouseover='over(\"td_"+level+i+"\",\""+level+"\")' onmouseout='out(\"td_"+level+i+"\")'";
		conStr += " onclick='sele(\""+showDiv+"\",\"td_"+level+i+"\",\""+p_selectText+"\",\""+child[i].pid+"\",\""+topLevelId+"\")' class='selectOptionTrNoSeled'>\n";
		conStr += child[i].displayname;
		conStr += "</td>";
		if("yes"==child[i].haschild)
		{
			conStr += "<td id='levelImg_"+level+i+"' class='selectOptionTrNoSeled_rightSel'";
			conStr += " onclick='showChildCategory(\""+child[i].pid+"\",\""+basePath+"\",\""+(levelCounter+1)+"\",\"levelImg_"+level+i+"\",this,\""+p_selectText+"\",event)'>";
			conStr += "<img src='"+basePath+"/fulltext/images/rightSelect.png'/>";
			conStr += "</td>";
		}
		else
			conStr += "<td class='selectOptionTrNoSeled_disabled'><img src='"+basePath+"/fulltext/images/rightSelect_disabled.png'/></td>";
		conStr += "</tr>\n";
	}
	conStr += "</table>\n";
	$(conStr).appendTo("#"+showDiv);
	//if(document.getElementById(showDiv).offsetHeight>162)
	//	document.getElementById(showDiv).style.height = "160px";
}
/*
 该函数完成计算给定对象的具体位置
 @param element --需要计算位置的对象
*/
function absolutePoint(element) {
	var result = { x: element.offsetLeft, y: element.offsetTop };
	element = element.offsetParent;
	while (element) {
		result.x += element.offsetLeft;
		result.y += element.offsetTop;
		element = element.offsetParent;
	}
	return result;
}
/*
 该函数完成从弹出的第一级别以后的DIV中选择下一级别的索引类别时，定位弹出的级别DIV的位置
 @param p_SelectArea --需要定位的级别DIV的ID
 @param p_SelectImg --所点击表示”下一级“图标的span的ID
*/
function setLevelDivPosition2(p_SelectArea,p_SelectImg){
	var obj_SelectArea = document.getElementById(p_SelectArea);
	var obj_SelectImg = document.getElementById(p_SelectImg);
	var mousePos = absolutePoint(obj_SelectImg); 
	var left = mousePos.x + 15;
	var top  = mousePos.y;
	obj_SelectArea.style.left	= left + "px";
	obj_SelectArea.style.top	= top + "px";
	if (obj_SelectArea.style.display=="none") {
		obj_SelectArea.style.display="block";
	}
}
/*
 该函数完成定位弹出的第一级别DIV的位置
 @param p_SelectArea --需要定位的级别DIV的ID
 @param p_SelectImg --所点击表示”下一级“图标的span的ID
 @param p_self --所点击表示”下一级“图标的span对象本身
*/
function setLevelDivPosition(p_SelectArea,p_SelectImg,p_self)
{
	var obj_SelectArea = document.getElementById(p_SelectArea);
	var obj_SelectImg = document.getElementById(p_SelectImg);
	var mousePos = absolutePoint(obj_SelectImg); 
	//var left = mousePos.x - 105;
	var left = mousePos.x;
	var top  = mousePos.y + 10;
	obj_SelectArea.style.left	= left + "px";
	obj_SelectArea.style.top	= top + "px";
	if (obj_SelectArea.style.display=="none") {
		obj_SelectArea.style.display="block";
	}
	//else if(p_self == obj_SelectImg){
	//	obj_SelectArea.style.display="none";   
	//}
 
}
/*
 该函数完成保持当前级别DIV以前的所有父级别DIV的display样式为block
 @param curLevel --当前级别DIV的级别数目
*/
function persistParentDiv(curLevel)
{
	for(var i=1;i<=curLevel;i++)
	{
		document.getElementById("levelDiv_"+i).style.display = 'block';
	}
}
/*
 该函数完成保持当前级别DIV以后的所有子级别DIV的display样式为none
 @param curLevel --当前级别DIV的级别数目
*/
function hideChildDiv(curLevel)
{
	for(var i=curLevel;i<=levelCounter;i++)
	{
		document.getElementById("levelDiv_"+i).style.display = 'none';
	}
}
/*
 该函数完成销毁所有的级别DIV
*/
function destoryAllDiv()
{
	for(var i=levelCounter;i>=1;i--)
	{
		var curDiv = document.getElementById("levelDiv_"+i);
		if(curDiv!=null)
			document.body.removeChild(curDiv);
	}
}
/*
 该函数完成记录选择索引类别的顶级父索引类别的ID和当前选择的索引类别的显示名称，用于传递到检索结果页面（resultList.jsp）用
*/
function recordIdAndName(id,name)
{
	var gotInp_id = document.getElementById("_topLevelMenuId");
	var loaderForm = document.getElementById(paramsLoaderForm);
	if(gotInp_id==null)
	{
		gotInp_id = document.createElement("input");
		gotInp_id.type = "hidden";
		gotInp_id.id = "_topLevelMenuId";
		gotInp_id.name = "_topLevelMenuId";
		if(loaderForm!=null)
			loaderForm.appendChild(gotInp_id);
	}
	document.getElementById("_topLevelMenuId").value = id;
	var gotInp_name = document.getElementById("_selectedMenuText");
	if(gotInp_name==null)
	{
		gotInp_name = document.createElement("input");
		gotInp_name.type = "hidden";
		gotInp_name.id = "_selectedMenuText";
		gotInp_name.name = "_selectedMenuText";
		if(loaderForm!=null)
		 loaderForm.appendChild(gotInp_name);
	}
	document.getElementById("_selectedMenuText").value = name;
	//alert(document.getElementById("_topLevelMenuId").value);
	//alert(document.getElementById("_selectedMenuText").value);
}
/*
 该函数完成选定索引类别
 @param p_SelectText --显示选中的索引类别的span的ID
 @param p_selectedId --被选中的索引类别的ID
*/
function seleValue(p_SelectText,p_selectedId)
{
	var obj_SelectText = document.getElementById(p_SelectText);
	//obj_SelectText.style.textDecoration="underline";
	//obj_SelectText.style.color="#000099";
	setCategory(p_selectedId, obj_SelectText,"_sysLevel");
	
}
/*
 该函数完成选择索引类别
 @param p_SelectArea --级别DIV的ID
 @param p_SelectTr --索引类别所在的行的id
 @param p_SelectText --显示选中的索引类别的span的ID
 @param p_selectedId --被选中的索引类别的ID
 @param p_topLevelId --被选中的索引类别的顶级父索引类别ID
*/
function sele(p_SelectArea,p_SelectTr,p_SelectText,p_selectedId,p_topLevelId)
{
	var obj_SelectArea 			 = document.getElementById(p_SelectArea);
	var obj_SelectTr 			 = document.getElementById(p_SelectTr);
	var obj_SelectText 			 = document.getElementById(p_SelectText);
	obj_SelectArea.style.display = 'none';
	obj_SelectTr.className		 = 'selectOptionTrNoSeled'
	obj_SelectText.innerText	 = obj_SelectTr.innerText.replace(/(\s*$)/g, "");
	seleValue(p_SelectText,p_selectedId);
	destoryAllDiv();
	recordIdAndName(p_topLevelId,obj_SelectText.innerText);
}
/*
 该函数完成鼠标在选择的行的效果
 @param p_SelectTr --行ID
 @param level --级别DIV的ID
*/
function over(p_SelectTr,level)
{
	var obj_SelectTr 	= document.getElementById(p_SelectTr);
	obj_SelectTr.className='selectOptionTrYesSeled';
	resetLevelDiv(parseInt(level),false);
}
/*
 该函数完成鼠标在选择的行外效果
 @param p_SelectTr --行ID
*/
function out(p_SelectTr)
{
	var obj_SelectTr 	= document.getElementById(p_SelectTr);
	obj_SelectTr.className='selectOptionTrNoSeled'
}
/*
 该函数完成鼠标在级别DIV外的效果
 @param p_SelectArea --级别DIV的ID
 @param level --级别DIV的级别数目
*/
function outSelectDiv(p_SelectArea,level)
{
	var obj_SelectArea 	= document.getElementById(p_SelectArea);
	obj_SelectArea.style.display='none';
}
/*-------------------------索引类别树形菜单_结束-----------------------*/
