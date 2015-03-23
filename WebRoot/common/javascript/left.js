// JavaScript Document
function getElementsByClass (searchClass, node, tag) {
	var classElements = new Array();
 	if(node == null) {
		node = document;
	}
	if(tag == null) {
		tag = '*';
	}
	var els = node.getElementsByTagName(tag);
	var elsLen = els.length;
	var pattern = new RegExp("(^|\s)"+searchClass+"(\s|$)");
	for (i = 0, j = 0; i < elsLen; i++) {
		if(pattern.test(els[i].className)) {
			classElements[j] = els[i];
			j++;
		}
	}
	return classElements;
}
function showSubmenu (index) {  
	var submunuTbody = getElementsByClass("submunuTbody");
	for(var i = 0; i < submunuTbody.length; i++) { 
		
		if(i != index) {
			submunuTbody[i].style.display = "none";
		} else {
			submunuTbody[i].style.display = ""; 
		}  
	}   
}    
function changeColor(obj)   
  { 
  var submunuTbody = getElementsByClass("submunuTbody"); 
  for(var i=0;i<submunuTbody.length;i++)   
  {   
  if(i!=obj){
   document.getElementById("td"+i).className="tree_title_bg0";
  }  
  else   
   document.getElementById("td"+i).className="tree_title_bg1";
  }   
    
  } 

 
