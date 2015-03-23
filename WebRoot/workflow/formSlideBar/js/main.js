// JavaScript Document
function check_browser() {
   Opera = (navigator.userAgent.indexOf("Opera",0) != -1)?1:0;
   MSIE = (navigator.userAgent.indexOf("MSIE",0) != -1)?1:0;
   FX = (navigator.userAgent.indexOf("Firefox",0) != -1)?1:0;
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
						centerElement.style.width = (windowWidth - x) + 'px';
					}
					else
					{
						centerElement.style.height = (windowHeight - y) + 'px';
						centerElement.style.width = (windowWidth - x) + 'px';
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
function getPageMenu(menuName,divName)
{
	activePageMenu = menuName;
	activePageDiv = divName;
}

function PageMenuActive(objName,divName)
{
	document.getElementById(activePageMenu).className = 'tab_hide';
	document.getElementById(activePageDiv).style.display = 'none';
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
		
		