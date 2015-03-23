<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/portal.css" />
<script src="../../javascript/main.js"></script>

        <script>
			function changeTab3(num){
	var tabLength=document.getElementById("tabDiv03").getElementsByTagName("div").length;
	var tabObj=document.getElementById("tabDiv03").getElementsByTagName("div");
	var endnum=tabLength-1;
	for(i=0;i<tabLength;i++)//判断是否是最后一个
	    {
	    if(i==endnum){
			tabObj.item(i).className="last_unselect";
			}
		else if(i==0){
			tabObj.item(i).className="first_unselect";
			}
		else{
			tabObj.item(i).className="common_unselect";
			}
		document.getElementById("tab41").style.display="none";
		document.getElementById("tab42").style.display="none";
		}
	if(num<endnum){//判断不是最后一个
		tabObj.item(num).className="common_select";  
		document.getElementById("tab41").style.display="none";
		document.getElementById("tab42").style.display="";
		}
	else{
		tabObj.item(num).className="last_select";  
		document.getElementById("tab41").style.display="none";
		document.getElementById("tab42").style.display="";
		}
		
	if(num==0){
		tabObj.item(num).className="first_select";  
		document.getElementById("tab41").style.display="";
		document.getElementById("tab42").style.display="none";
		}
	}
	
			</script>
<title>new_posts</title>
</head>
<body style="background-color:white;">
<div class="title_right">
      <div class="title_left">
        <span class="title_bg">
          <span><img src="${ctx}/common/style/blue/images/portal/img_02.png" width="15" height="14" align="absmiddle" />常用链接</span>
        </span>
        <span class="title_xieline"></span>
      </div>
</div>
<table  class="bbs_content_table">
   <tr>
   <td class="Info" valign="top">
   <div id="tab41">
     <div class="Infocontent">
       <li>话务网管Web URL</li>
       <li>话务网管Web URL</li>
       <li>话务网管Web URL</li>
       <li>话务网管Web URL</li>
       <li>话务网管Web URL</li>
       <li>话务网管Web URL</li>
     </div>
   </div>
</td>
</tr>
</table>
</body>
</html>