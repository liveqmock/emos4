// JavaScript Document
/**
 * 以下变量为公用常量，请勿随意改动！
 */
var LAYOUT_ZERO = 0;
var LAYOUT_LIST_RIGHT = 35 + 29;		//左树右列表的“列表”
var LAYOUT_FORM_RIGHT = 26 + 35;		//左树右信息页面的“信息页面”
var LAYOUT_LIST_OPEN = 26 + 29;		//工具条在“上端”所使用
var LAYOUT_FORM_OPEN = 45 + 35;		//工具条在“下端”所使用（保存、取消）
var LAYOUT_LIST_TAB = 26;		//列表页面最上端有tab页的情况
var LAYOUT_FRAME_RIGHT = 26;	//页面是左中右的总页面的配置
//由于按钮在上面，所以使用list的工具条的高度
function check_browser() {
   Opera = (navigator.userAgent.indexOf("Opera",0) != -1)?1:0;
   MSIE = (navigator.userAgent.indexOf("Microsoft",0) != -1)?1:0;
   FX = (navigator.userAgent.indexOf("Mozilla",0) != -1)?1:0;
   if ( Opera ) brow_type = "Opera";
   else if ( FX )brow_type = "Firefox";
   else if ( MSIE )brow_type = "MSIE";
   return brow_type;
}
function getWindowHeight() {
	var windowHeight = 0;
	if (typeof(window.innerHeight) == 'number') {
		windowHeight = window.innerHeight;
	}
	else {
		if (document.documentElement && document.documentElement.clientHeight) {
			windowHeight = document.documentElement.clientHeight;
		}
		else {
			if (document.body && document.body.clientHeight) {
				windowHeight = document.body.clientHeight;
			}
		}
	}
	return windowHeight;
}
function getWindowWidth() {
	var windowWidth = 0;
	if (typeof(window.innerWidth) == 'number') {
		windowWidth = window.innerWidth;
	}
	else {
		if (document.documentElement && document.documentElement.clientWidth) {
			windowWidth = document.documentElement.clientWidth;
		}
		else {
			if (document.body && document.body.clientWidth) {
				windowWidth = document.body.clientWidth;
			}
		}
	}
	return windowWidth;
}

function setCenter(x,y) {
	if (document.getElementById) {
		var windowHeight = getWindowHeight();
		var windowWidth = getWindowWidth();
		var brow_type = check_browser(); 
		if (windowHeight > 0) {
			var centerElement = document.getElementById('center');
			if(centerElement != null){
				var centerHeight  = centerElement.offsetHeight;
				var centerWidth  = centerElement.offsetWidth;
				if (windowHeight - (88) >= 0) {
					if (brow_type == "MSIE") {						
						centerElement.style.height = (windowHeight - y) + 'px';
						//centerElement.style.width = (1000 - x) + 'px';
					}
					else
					{
						centerElement.style.height = (windowHeight - y) + 'px';
						//centerElement.style.width = (1000 - x) + 'px';
					}
				}
			}
		}
	}
}
function changeRow_color(obj) {
var Ptr=document.getElementById(obj).getElementsByTagName("tr");
	
		for (var i=1;i<Ptr.length+1;i++) 
		{ 
		if(i%2>0)
		 { Ptr[i-1].className = "t2";}
		else
		 {Ptr[i-1].className = "t1";}
		}
	for(var i=0;i<Ptr.length;i++) {
		Ptr[i].onmouseover=function(){
		this.tmpClass=this.className;
		this.className = "t3";    
		};
		Ptr[i].onmouseout=function(){
		this.className=this.tmpClass;
		};
	}
}
function changeRow_color_custom(obj, group) {
	var Ptr=document.getElementById(obj).getElementsByTagName("tr");
		
	for (var i=1;i<Ptr.length;i++) 
	{
		if(Math.floor((i-1)/group)%2>0)
			{Ptr[i].className = Ptr[i].className == "" ? "t2" : Ptr[i].className;}
		else
			{Ptr[i].className = Ptr[i].className == "" ? "t1" : Ptr[i].className;}
	}
	for(var i=1;i<Ptr.length;i++)
	{
		var si = 0;
		if((i%group) > 0)
		{
			si = (i%group) - 1;
		}
		else
		{
			si = group - 1;
		}
		//var si = group-1-(i%group);
		//var trarr = '';
		var trarr = new Array();
		for(var j = i-si; j <= group; j++)
		{
			//trarr += ', Ptr[' + j + ']';
			trarr.push(Ptr[j]);
		}
		if(Ptr[i].className == "t1" || Ptr[i].className == "t2" || Ptr[i].className == "odd" || Ptr[i].className == "even"){//t1,t2为默认样式,odd,even为产品自带样式
			EventHandler.attachEvent(Ptr[i], EventType.mouseover, EventHandler.createEvent(changeRow_color_custom_mouseOverEvent, Ptr[i], si, group-si-1));
			EventHandler.attachEvent(Ptr[i], EventType.mouseout, EventHandler.createEvent(changeRow_color_custom_mouseOutEvent, Ptr[i], si, group-si-1));
		}
	}
}
function changeRow_color_custom_mouseOverEvent(trobj, s1, s2)
{
	trobj.tmpClass=trobj.className;
	trobj.className = "t3";
	var preObj = trobj.previousSibling;
	for(var i = 0; i < s1; i++)
	{
		preObj.tmpClass=preObj.className;
		preObj.className = "t3";
		preObj = preObj.previousSibling;
	}
	preObj = trobj.nextSibling;
	for(var i = 0; i < s2; i++)
	{
		preObj.tmpClass=preObj.className;
		preObj.className = "t3";
		preObj = preObj.nextSibling;
	}
}
function changeRow_color_custom_mouseOutEvent(trobj, s1, s2)
{
	trobj.className=trobj.tmpClass;
	var preObj = trobj.previousSibling;
	for(var i = 0; i < s1; i++)
	{
		preObj.className=preObj.tmpClass;
		preObj = preObj.previousSibling;
	}
	preObj = trobj.nextSibling;
	for(var i = 0; i < s2; i++)
	{
		preObj.className=preObj.tmpClass;
		preObj = preObj.nextSibling;
	}
}
function getPageMenu(menuName,divName)
{
	activePageMenu = menuName;
	activePageDiv = divName;
}

function PageMenuActive(objName,divName)
{
	document.getElementById(activePageMenu).className = 'tab_hide';
	if(activePageDiv != '')
	{
		document.getElementById(activePageDiv).style.display = 'none';
	}
	document.getElementById(objName).className = 'tab_show';
	document.getElementById(divName).style.display = '';
	activePageMenu = objName;
	activePageDiv = divName;
}
function checkAll(){
			var chk = document.forms["form1"].elements["chkAll"];
			var inputObj =  document.forms["form1"].getElementsByTagName("input");
			for (i = 0; i < inputObj.length; i++) {
				var temp = inputObj[i];
				if(temp.type == "checkbox"){
					temp.checked = chk.checked;
				}
			}
		}
/*ban particular character in text field*/
function clearSpecialChar(event)
{
	//               [", <, >,  &, ']
	var specialArr = [34,60,62,38,39];//add the ascii value of particular character
	var srcCode = event.keyCode;
	if(srcCode==undefined)
	{
		srcCode = event.which;
	}
	var b = true;
	for(var i=0;i<specialArr.length;i++)
	{
		if(srcCode==specialArr[i])
		{
			b = false;
			alert('输入字符不能是下列字符之一：\n'+'\"  -英文双引号\n\'  -英文单引号\n<  -小于符号\n>  -大于符号\n&  -&符号');
			break;
		}
	}
	return b;
}
/*open window at the middle&center of screen*/
function openwindow(url,name,iWidth,iHeight)
{
	var url; 
	var name; 
	var iWidth; 
	var iHeight; 
	var iTop = (window.screen.availHeight-30-iHeight)/2; 
	var iLeft = (window.screen.availWidth-10-iWidth)/2; 
	window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=yes');
}
/*
 function: compare old_str and new_str, then output add_str and del_str
 example_1:	old_str=1,2,3,5;new_str=1,3,4,6;compareStr(old_str,new_str)=4,6;2,5
 notice: under any condition, ';' exists all the same!
*/
function compareStr(oldStr,newStr)
{
	if(oldStr==newStr)
	{
		return ';';
	}
	if(oldStr=='')
	{
		return newStr+';';
	}
	if(newStr=='')
	{
		return ';'+oldStr;
	}
	var oldArr = oldStr.split(',');
	var newArr = newStr.split(',');
	var dels = '';
	var adds = '';
	for(var i=0;i<oldArr.length;i++)
	{	
		var tag = 0;
		for(var j=0;j<newArr.length;j++)
		{
			if(oldArr[i] == newArr[j])
			{
				tag = 1;
				break;
			}
		}
		if(tag == 0)
		{
			dels = dels + oldArr[i] + ',';
		}
	}
	for(var i=0;i<newArr.length;i++)
	{	
		var tag = 0;
		for(var j=0;j<oldArr.length;j++)
		{
			if(newArr[i] == oldArr[j])
			{
				tag = 1;
				break;
			}
		}
		if(tag == 0)
		{
			adds = adds + newArr[i] + ',';
		}
	}
	if(dels=='' && adds!='')
	{
		return adds.substring(0,adds.lastIndexOf(',')) + ';';
	}
	else if(adds=='' && dels!='')
	{
		return ';' + dels.substring(0,dels.lastIndexOf(','));
	}
	else if(adds=='' && dels=='')
	{
		return ';';
	}
	else
	{
		return adds.substring(0,adds.lastIndexOf(',')) + ';' + dels.substring(0,dels.lastIndexOf(','));
	}
}
function setValueToInput(data_name, user_id, user_name, dep_id, dep_name)
{
	setValueToInputAll(data_name, user_id, user_name, dep_id, dep_name, '', '');
}
function setValueToInputAll(data_name, user_id, user_name, dep_id, dep_name, role_id, role_name)
{
	//datainfos：U:id1:name1;U:id2:name2;D:id1:name1;D:id2:name2;R:id1:name1;R:id2:name2...
	var datainfos = document.getElementById(data_name).value;
	var dataArray = datainfos.split(';');
	var u_id = '';
	var u_text = '';
	var d_id = '';
	var d_text = '';
	var r_id = '';
	var r_text = '';
	var type = '';
	for(var i=0 ; i<dataArray.length ; i++)
	{
		var subdata = dataArray[i].split(':');
		type = subdata[0];
		if(subdata.length < 3)
			continue;
		if(type == 'U')
		{
			u_id += ',' + subdata[1];
			u_text += ',' + subdata[2];
		}
		else if(type == 'D')
		{
			d_id += ',' + subdata[1];
			d_text += ',' + subdata[2];
		}
		else if(type == 'R')
		{
			r_id += ',' + subdata[1];
			r_text += ',' + subdata[2];
		}
	}
	if(user_id != '' && user_name != '')
	{
		if(u_id != '')
		{
			u_id = u_id.substring(1);
			u_text = u_text.substring(1);
		}
		document.getElementById(user_id).value = u_id;
		document.getElementById(user_name).value = u_text;
	}
	if(dep_id != '' && dep_name != '')
	{
		if(d_id != '')
		{
			d_id = d_id.substring(1);
			d_text = d_text.substring(1);
		}
		document.getElementById(dep_id).value = d_id;
		document.getElementById(dep_name).value = d_text;
	}
	if(role_id != '' && role_name != '')
	{
		if(r_id != '')
		{
			r_id = r_id.substring(1);
			r_text = r_text.substring(1);
		}
		document.getElementById(role_id).value = r_id;
		document.getElementById(role_name).value = r_text;
	}
}
/*JS高精度计算*/
//除法
function accDiv(arg1,arg2,arg3){ 
	var t1=0,t2=0,r1,r2; 
	try{t1=arg1.toString().split(".")[1].length}catch(e){} 
	try{t2=arg2.toString().split(".")[1].length}catch(e){} 
	with(Math){ 
		r1=Number(arg1.toString().replace(".","")) 
		r2=Number(arg2.toString().replace(".","")) 
		if(arg3==undefined)
			return (r1/r2)*pow(10,t2-t1); 
		else
			return accRound((r1/r2)*pow(10,t2-t1),arg3);
	} 
}
//乘法
function accMul(arg1,arg2,arg3) 
{ 
	var m=0,s1=arg1.toString(),s2=arg2.toString(); 
	try{m+=s1.split(".")[1].length}catch(e){} 
	try{m+=s2.split(".")[1].length}catch(e){} 
	if(arg3==undefined)
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m); 
	else
		return accRound(Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m),arg3);
}
//加法
function accAdd(arg1,arg2,arg3){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2))
	if(arg3==undefined)
		return (arg1*m+arg2*m)/m;
	else
		return accRound((arg1*m+arg2*m)/m,arg3);
}
//减法
function accSubtr(arg1,arg2,arg3){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     if(arg3==undefined)
     	return ((arg1*m-arg2*m)/m).toFixed(n);
     else 
     	return accRound(((arg1*m-arg2*m)/m).toFixed(n),arg3);
}
//保留小数位数，进行四舍五入
function accRound(arg1,arg2){
	try{
		var temp = arg1+"";
		var poi = temp.indexOf(".");
		if(poi==-1)
			return arg1;
		var left = temp.split(".")[0];
		var right = temp.split(".")[1];
		var len = right.length;
		if(len<=arg2)
			return arg1;
		var ritemp = '0.';
		var ritemp2 = '0.';
		var riarr = right.split("");
		for(var i=0;i<arg2;i++){
			ritemp += riarr[i];
			if(i<(arg2-1))
				ritemp2 += '0';
		}
		ritemp = parseFloat(ritemp);
		if(riarr[arg2]>=5){
			ritemp += parseFloat(ritemp2+'1');
		}
		return accAdd(parseFloat(left),ritemp);
	}catch(e){
		alert(e);
		return arg1;
	}
}
// 获取鼠标在浏览器中的坐标
function mousePos(e) {
	var x,y;
	var e = e||window.event;
	return {
		x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
		y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
	};
}

//window.open的扩展,支持post传参
openWindowEx = function(verb, url, data, target) {
	  var form = document.createElement("form");
	  form.action = url;
	  form.method = verb;
	  form.target = target || "_self";
	  if (data) {
	    for (var key in data) {
	      var input = document.createElement("textarea");
	      input.name = key;
	      input.value = typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
	      form.appendChild(input);
	    }
	  }
	  form.style.display = 'none';
	  document.body.appendChild(form);
	  form.submit();
};