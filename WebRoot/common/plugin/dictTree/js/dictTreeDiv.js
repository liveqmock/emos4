/**
 * 下拉框(弹出一个DIV,里面可以进行快速搜索)
 * liuchuanzu 2011-07-25
 */
 
var id = '';
var max = '';//多选时最多能选择
var dictType = '';//字典类型code
var checkboxes = '';//1：单选；其它的为多选
var levelFlag = '';//显示前几级
var valueOf = '';//主要用来获取字典表中的字段值，如value,name,fullname等等；默认的是value
var checkLevelFlag = -1;//第几级的值可以进行选择,-1:所有均可选择，0:根节点选择,-2:叶子节点
var dictFlag = '';//数据字典标识，与字典值中的标识对应
var u;		//传过来的URL
var inputObj;//原输入框对象
var valueObj;//原输入框存放value的输入框对象
var inputObjId;//原输入框对象ID，主要是用来做一个缓存，用来记录是否同一个input，如果是就不需要重新加载词典树，如果不是就需要重新加载
var divObj;//弹出的DIV对象
var divTop;//弹出的TOPDIV对象
var isView = false;//是否显示
var timeout = 100;//input组件失去焦点执行缓冲时间
var queryStr;//搜索时关键字
var pid;//树根节点ID，默认为0
var sqlStr;//SQL获取树节点数据
var sqlName;//XML配置SQL
var pflied;//父字段ID
var pfliedValue;//父字段ID值
var defaultValue;//默认值


//显示div方法
 function initDivs(obj,url){
 	isView = true;
 	if(queryStr != ''){
 		queryStr = '';
 		inputObjId = '';
 	}
 	inputObj = obj;
 	valueObj = document.getElementById(inputObj.id.substring(5));
 	if(valueObj == null){
 		valueObj = document.getElementsByName(inputObj.id.substring(5))[0];	
 	}
 	initUrl(url);
 	createDivs(obj,'dictdiv',240,250);
 	var divObj = document.getElementById('dictdiv');
 	initTree(divObj,dictType,checkboxes);
 }
 
 //显示div方法
 function initDivs2(obj){
 	isView = true;
 	if(queryStr != ''){
 		queryStr = '';
 		inputObjId = '';
 	}
 	inputObj = document.getElementById(obj.viewid);
 	if(obj.valueid){
 		valueObj = obj.valueid;
 	}else{
 		valueObj = document.getElementById(obj.viewid+"_id");
 	}
 	initUrl(obj.url);
 	createDivs(inputObj,'dictdiv',240,250);
 	var divObj = document.getElementById('dictdiv');
 	initTree(divObj,dictType,checkboxes);
 }
 
 function initUrl(url){
 	u = new Url(url);//获取本地址的后辍信息
	id = u.getvalue('id');
	dictType = u.getvalue('dictType');
	checkboxes = u.getvalue('checkboxes');
	levelFlag = u.getvalue('levelFlag');
	valueOf = u.getvalue('valueOf');
	checkLevelFlag = u.getvalue('checkLevelFlag');
	dictFlag = u.getvalue('dictFlag');
	pid = u.getvalue('pid');
	sqlStr = u.getvalue('sqlStr');
	sqlName = u.getvalue('sqlName');
	pflied = u.getvalue('pflied');
	max = u.getvalue('max');
	defaultValue = u.getvalue('defaultValue');
	if(max == null){
	  max = -1;
	}
	if(pflied != null){
		if(document.getElementsByName(pflied)[0] != null){
			pfliedValue = document.getElementsByName(pflied)[0].value;
		}else{
			pfliedValue = 'null';
		}
		 
		pfliedValue = pfliedValue.replace(/\+/g, '%2B');
		pfliedValue = encodeURI(encodeURI(pfliedValue));
	}
 }
 
 function getQueryString(url,name) { 
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");    
   var r = url.match(reg);    
   if (r != null) 
      return unescape(r[2]); 
   return null;    
 }
 
 var divTop_height = 30;
 function createDivs(obj,id,x,y){
 
 	if(document.getElementById(id) != null){
 		divTop = document.getElementById('divTop');
 	}else{
 		divTop = document.createElement('div');
 	}
 	divTop.id = 'divTop';
 	divTop.style.width = x;
 	divTop.style.height = divTop_height;
 	divTop.style.left = pageX(obj);
 	divTop.style.top = pageY(obj) + 23;
 	divTop.style.background = '#eee';
 	divTop.style.position = 'absolute';
 	divTop.classname = 'divClass';
 	if(checkboxes == 1){
		divTop.innerHTML='<p align="right" width="100%;" style="cursor:hand">[可输入搜索]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B width="15px" onclick="setValue();">[确认]</B><B width="15px" onclick="toClear();">[清空]</B><B align="right" width="15px" onclick="isView=false;divHidden(true);">[关闭]</B></p>' 		
 	}else{
 		divTop.innerHTML='<p align="right" width="100%;" style="cursor:hand">[可输入搜索]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B width="15px" onclick="setAll();">[全选]</B><B width="15px" onclick="setValue();">[确认]</B><B width="15px" onclick="toClear();">[清空]</B><B align="right" width="15px" onclick="isView=false;divHidden(true);">[关闭]</B></p>'
 	}
 	document.body.appendChild(divTop);
 	
 	if(document.getElementById(id) != null){
 		divObj = document.getElementById(id);
 	}else{
 		divObj = document.createElement('div');
 	}
 	if(divObj != null){
	 	divObj.id = id;
	 	divObj.classname = 'divClass';
	 	if(divObj.style != null){
		 	divObj.style.width = x;
		 	divObj.style.height = y;
		 	divObj.style.position = 'absolute';
		 	divObj.style.left = pageX(obj);
		 	divObj.style.top = pageY(obj) + 23 + divTop_height;
		 	divObj.style.background = '#eee';
	 	}
 		document.body.appendChild(divObj);
 		divShow();
 	}
 	/**
 	alert(
		  'scrollHeight:' + obj.scrollHeight + '   '
 		+ 'scrollLeft:' + obj.scrollLeft + '   '
 		+ 'scrollTop:' + obj.scrollTop + '   '
 		+ 'scrollWidth:' + obj.scrollWidth + '   '
 		+ 'offsetHeight:' + obj.offsetHeight + '   '
 		+ 'offsetLeft:' + obj.offsetLeft + '   '
 		+ 'offsetTop:' + obj.offsetTop + '   '
 		+ 'clientX:' + event.clientX + '   ' 
 		+ 'clientY:' + event.clientY + '   '
 		+ 'offsetX:' + event.offsetX + '   '
 		+ 'offsetY:' + event.offsetY + '   '
 		+ 'offsetY:' + event.offsetY + '   '
 		+ 'X:' + pageX(obj) + '   '
 		+ 'Y:' + pageY(obj) + '   '
 		);**/
 	//divObj.style = ':;width:500;height:100;z-index:2000;';
 	//divObj.innerHTML = 'ssssss<br/>safds<br/>fffff<br/>ssss';
 }
 
 function toClear(){
 	inputObj.value = '';
 	valueObj.value = '';
 	checks=tree.getAllChecked();
    items = checks.split(",");
    for (var i = 0; i < items.length; i++) {
       tree.setCheck(items[i],false);
    }
 	toQuery();
 }
 
 //获取input的绝对位置X
 function pageX(obj){
 	return obj.offsetParent?obj.offsetLeft + pageX(obj.offsetParent):obj.offsetLeft;
 }
 //获取input的绝对位置Y
 function pageY(obj){
 	return obj.offsetParent?obj.offsetTop + pageY(obj.offsetParent):obj.offsetTop;
 }
 
var div_width = 100;
var div_height = 100;
function showDiv(obj,divId,dictType,checkBoxes,level){
	var div = document.getElementById(divId);
	div.style.display = '';
	initTree(div,dictType,checkBoxes);
}

var tree;
function initTree(divObj,dictType,checkBoxes){
	if(inputObjId == inputObj.id){
		//return;
	}else{
		inputObjId = inputObj.id;
	}
	divObj.innerHTML = '';
	tree = null;
	tree = new dhtmlXTreeObject(divObj.id,"98%","98%",0);
	tree.setImagePath(ctx + "/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
	if(checkBoxes == '1'){
		tree.attachEvent("onClick",setValue_1);
		tree.enableCheckBoxes(2);
	}else{
		tree.enableCheckBoxes(1);
	}
	
	//unix下用
	//queryStrTemp = (encodeURI(queryStr)).replace(/\+/g, '%2B');
	//alert('queryStrTemp:' + queryStrTemp);
	var loadUrl = ctx + "/dictTreeAction/loadxml.action?"
		+ "valueOf=" + valueOf
		+ "&checkLevelFlag=" + checkLevelFlag
		+ "&levelFlag=" + levelFlag
		+ "&checkboxes=" + checkboxes
		+ "&dictFlag=" + dictFlag
		+ "&dicttype=" + dictType
		+ "&sqlStr=" + sqlStr
		+ "&sqlName=" + sqlName
		+ "&pflied=" + pfliedValue
		+ "&name=" + valueObj.name;
	tree.setXMLAutoLoading(loadUrl);
	
	//tree.setOnClickHandler(setIsView);
	//alert(queryStrTemp);
	/****/
	queryStr = queryStr.replace(/\+/g, '%2B');
	queryStrTemp = encodeURI(queryStr);
	//queryStrTemp = queryStr;
	var treeUrl = ctx + "/dictTreeAction/loadxml.action?queryStr=" + queryStrTemp 
		+ "&valueOf=" + valueOf
		+ "&checkLevelFlag=" + checkLevelFlag
		+ "&levelFlag=" + levelFlag
		+ "&checkboxes=" + checkboxes
		+ "&dictFlag=" + dictFlag
		+ "&dicttype=" + dictType
		+ "&id=" + pid
		+ "&sqlStr=" + sqlStr
		+ "&sqlName=" + sqlName
		+ "&pflied=" + pfliedValue
		+ "&name=" + valueObj.name
		+ "&defaultValue=" +defaultValue;
	tree.loadXML(treeUrl);
	tree.attachEvent("onXLE", function(){
		setChecked()
	});
	
}

function setIsView(){
	isView = true;
}

/**
 * 全选
 */
function setAll(){
	tree.setSubChecked(pid,1);
	setValue();
}

/**
 * 恢复已经选中的节点
 */
function setChecked(){
	var items = valueObj.value.split(",");
	for(var i = 0; i < items.length; i++) {
		tree.setCheck(items[i],true);
	}
}

/**
 *单选时，点击节点，直接获取值
 */
function setValue_1(ids){
	if(checkLevelFlag == '-2' && tree.hasChildren(ids)){
		return;
	}
	
	if(checkboxes == '1'){
	   checks=tree.getAllChecked();
       items = checks.split(",");
       for (var i = 0; i < items.length; i++) {
          tree.setCheck(items[i],false);
       }
	  tree.setCheck(ids,"1"); 
	}
	
    var fullname = tree.getUserData(ids,'fullname');
	var dictvalue =  tree.getUserData(ids,'dictvalue');
	
	if(valueOf == 'fullname'){
		dictvalue = fullname;//页面显示全称
	}
	
	valueObj.value = dictvalue;
	inputObj.value = fullname;
	inputObj.title = fullname;
	
	//alert(valueObj.id + '   valueObj.value' + valueObj.value + '   fullname:'+fullname+ '   dictvalue:'+dictvalue  + '    onchange:'+ valueObj.onchange);
	
	try{
		if(valueObj.onchange != null && valueObj.onchange != ''){
			valueObj.onchange();
		}
		try{
			$('#' + valueObj.id).change();
		}catch(e){}
		
		if(valueObj.onclick != null && valueObj.onclick != ''){
			valueObj.onclick();
		}
		
		$('#' + valueObj.id).click();
	}catch(e){}
	isView = false;
	divHidden(true);
}


/**
 * DIV隐藏
 */
 var hide;
 function divHidden(hid){
 	hide = hid;
 	setTimeout("if(hide && !isView){divObj.style.display = 'none';divTop.style.display = 'none';}if(valueObj.value == null || valueObj.value == ''){inputObj.value = '';}",timeout);
 	
 }


/**;
 * DIV显示
 */
 function divShow(){
 	divTop.style.display = '';
 	divObj.style.display = '';
 }

function setValue(){
	       	var itemId = tree.getAllChecked();
	        var value ='';
	        var view ='';
	        if(itemId == ''){
	          view ='';
	          value ='';
	        }
	        else {
	        	items = itemId.split(",");
	        	for(var i = 0; i < items.length; i++){
	        		var fullname = tree.getUserData(items[i],'fullname');
	        		var dictvalue =  tree.getUserData(items[i],'dictvalue');
	        		if(dictvalue != ''){
						if(view == ''){
							view = fullname;
							value = dictvalue;
						}else{
							view += ',' + fullname;
							value += ',' + dictvalue;
						}
					}
			    }
	      	}
	      	if(max!=null&&max>1&&checkboxes!=1){
			     var items = itemId.split(",");
			      var length = items.length;
			      if(length>max){
			        alert("最多可选"+max+"项!");
			        return;
			      }
			 }
	      	
			if(valueOf == 'fullname'){
				value = view;//页面显示全称
			}
			valueObj.value = value;
			inputObj.value = view;
			inputObj.title = view;
			
			if(valueObj.onchange != null && valueObj.onchange != ''){
				valueObj.onchange();
			}
			if(valueObj.onclick != null && valueObj.onclick != ''){
				valueObj.onclick();
			}
			isView = false;
			divHidden(true);
}


/**
 * 搜索
 */
var runlength = 1000;
var run;
var isRun = true;
function toQuery(){
	valueObj.value = '';
	var evt = window.event;
	if(evt.keyCode == 27){//ESC键清空内容
		try{
			toClear();
		}catch(e){}
	}
	if(evt.keyCode == 13){//回车键选中内容
		setAll();
		inputObj.value = inputObj.value + ",";
	}else{
		divShow();
		var values = inputObj.value;
		if(queryStr != values){
			queryStr = values;
			runlength = 1000;
			if(isRun){
				run = setInterval('toRun();',200);
				isRun = false;
			}
		}
	}
}

function toRun(){
	runlength = runlength - 200;
	if(runlength <= 0){
		inputObjId = '';
		initTree(divObj,dictType,checkboxes);
		clearInterval(run);
		isRun = true;
	}
}

//页面关闭事件
window.onbeforeunload = function(){
	isView=false;
	divHidden(true);
}

function document.onkeydown(){
   if(divObj&&divObj!=null){
     if(divObj.style.display!='none'){
     if(event.keyCode==13){
    	setValue();
      }
     }
   }
 }

