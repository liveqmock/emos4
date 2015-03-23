var $j = jQuery.noConflict();

/*****************************************************
通过ID获取jquery对象
*****************************************************/
function getJQueryId(id){
	if(id.indexOf('.')>0){
		return $j(document.getElementById(id));
	}else{
		return $j("#"+id);
	} 
}

/*****************************************************
通过class获取jquery对象
*****************************************************/
function getJQueryClass(cls){
		return $j("."+cls);
}

/*****************************************************
通过tag获取jquery对象
*****************************************************/
function getJQueryTag(tag){
		return $j(tag);
}

/*****************************************************
把html对象转换jquery对象
*****************************************************/
function getJQueryObj(obj){
		return $j(obj);
}

/*****************************************************
把jquery对象转换html对象
*****************************************************/
function getHtmlObj(obj){
		return obj[0];
}

/*****************************************************
获取客户端当前时间
*****************************************************/
function getCurrentTime(){
 	var now= new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
	return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
}

/*****************************************************
获取客户端当前日期
*****************************************************/
function getCurrentDate(){
 	var now= new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	return year+"-"+month+"-"+day;
}

/*****************************************************
替换所有字符串
*****************************************************/
function replaceAll(s,r){
	var re = eval("/"+a+"/g");
	str = str.replace(re, "");
	return str;
}

/*****************************************************
去掉空行
*****************************************************/
function trin(s){
	var re = /\n/g;
	 s = s.replace(re, "");
	 re = /\r/g;
	 s = s.replace(re, "");
	 return s;
}

/*****************************************************
设置下拉选择选中指定的值
*****************************************************/
function setSelected(selId,value){
	getJQueryId(selId).attr("value",value);
}

/*****************************************************
设置div显示
*****************************************************/
function showDiv(divid){
	getJQueryId(divid).show();
}

/*****************************************************
设置div隐藏
*****************************************************/
function hiddenDiv(divid){
	getJQueryId(divid).hide();
}


/*****************************************************
清空div
*****************************************************/
function emptyDiv(divId){
	getJQueryId(divId).empty();
}

/*****************************************************
添加样式
*****************************************************/
function addClass(ele,cls){
	getJQueryObj(ele).addClass(cls);
}

/*****************************************************
删除样式
*****************************************************/
function moveClass(ele,cls){
	getJQueryObj(ele).removeClass(cls);
}

/*****************************************************
创建DIV，放在tag后面
*****************************************************/
function createDiv(id,jobj){
	$j ("<div id='"+id+"'></div>").appendTo(jobj);
	return getJQueryId(id);
}

/*****************************************************
删除DIV
*****************************************************/
function removeDiv(id){
	var o = getJQueryId(id);
	o.remove();
}

//为元素添加事件
function addEvent(el, evname, func){
	if (el.attachEvent) { // IE
		el.attachEvent("on" + evname, func);
	} else if (el.addEventListener) { // Gecko / W3C
		el.addEventListener(evname, func, true);
	} else {
		el["on" + evname] = func;
	}
}

//为元素删除事件
function removeEvent(el, evname, func){
	if (el.detachEvent) { // IE
		el.detachEvent("on" + evname, func);
	} else if (el.removeEventListener) { // Gecko / W3C
		el.removeEventListener(evname, func, true);
	} else {
		el["on" + evname] = null;
	}
}


//动态添加验证
//function addValidate(id,dType,msg){
//	var validate = $j("<validation id='"+id+"V' dataType='"+dType+"' msg='"+msg+"' />");
//	getJQueryTag("body").append(validate);
//}

function addValidate(v){
	var validate = $j(v);
	getJQueryTag("body").append(validate);
}

//动态删除验证
function removeValidate(id){
	removeDiv(id+"V");
}

//设置字段只读
function setReadOnly(id){
	getJQueryId(id).attr('readonly','readonly');
}
//设置字段可写
function setCanWrite(id){
	getJQueryId(id).removeAttr('readonly');
}

//设置iframe的url地址
function setIframeUrl(iframe_id,url){
 	getJQueryId(iframe_id).attr("src",url); 
}

//创建div
function createDiv(id,jobj){
	$j("<div id='"+id+"'></div>").appendTo(jobj);
	return getJQueryId(id);
}

//滚动条滚动到页面底部
function scrollBottom(){
	var c = window.document.body.scrollHeight;
	window.scroll(0,c); 
}

//限制textarea长度
function txtLimit(obj, length) {
	if (obj.value.length>=length) {
		if(event.keyCode != 8 && event.keyCode != 127) {
			obj.value = obj.value.substring(0, length);
			return false;
		} 
	}
	return true;
}