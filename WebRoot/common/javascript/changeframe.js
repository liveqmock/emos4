// JavaScript Document

function changeFrameleft(){
	if(parent.document.getElementById("ContentFrm").getAttribute("cols")=='24,*'){
	   parent.document.getElementById("ContentFrm").cols='210,*';
	   document.getElementById('showtree').style.display="";
	   document.getElementById('notree').style.display="none";
	    parent.leftFrame.noResize = false;
		}
   else{
	   parent.document.getElementById("ContentFrm").cols='24,*';
	   document.getElementById('showtree').style.display="none"
	   document.getElementById('notree').style.display="";

	   parent.leftFrame.noResize = true;
	  
	   
	}
 }
 function changeFrametop(){
	
	if(parent.document.getElementById("ContentFrm2").getAttribute("rows")=='*,280'){
	   parent.document.getElementById("ContentFrm2").rows='*,26';
	   document.getElementById('bottomdown').style.display="";
	   document.getElementById('topup').style.display="none";
	   parent.content_fra_top.location.reload();
	   
		}
   else{
	   parent.document.getElementById("ContentFrm2").rows='*,280';
	   document.getElementById('bottomdown').style.display="none"
	   document.getElementById('topup').style.display="";
	   parent.content_fra_top.location.reload();

	  
	  
	   
	}
 }