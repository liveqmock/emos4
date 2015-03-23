<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/portal.css" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/main.css" />
<script src="../../javascript/main.js"></script>

        <script>
function changeTab1(num){
	var tabLength=document.getElementById("tabDiv01").getElementsByTagName("div").length;
	var tabObj=document.getElementById("tabDiv01").getElementsByTagName("div");
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
		document.getElementById("tab21").style.display="none";
		document.getElementById("tab22").style.display="none";
		}
	if(num<endnum){//判断不是最后一个
		tabObj.item(num).className="common_select";  
		document.getElementById("tab21").style.display="none";
		document.getElementById("tab22").style.display="";
		}
	else{
		tabObj.item(num).className="last_select";  
		document.getElementById("tab21").style.display="none";
		document.getElementById("tab22").style.display="";
		}
		
	if(num==0){
		tabObj.item(num).className="first_select";  
		document.getElementById("tab21").style.display="";
		document.getElementById("tab22").style.display="none";
		}
	}
	window.onresize = function() 
		{
		  //setCenter(0,55);
		}
		window.onload = function() 
		{
		  //setCenter(0,55);
		}
	</script>
<title>wait_order</title>
</head>
<body style="overflow:hidden; margin:0px; padding:0px; background-color:white;">
<div class="content">
<div class="title_right">
  <div class="title_left">
		<span class="title_bg"><span><img src="../../style/blue/images/title/tag7.png"  align="absmiddle" />待办工单</span></span>
		<span class="title_xieline"></span>
		<span class="title_more" onclick="window.open('${ctx}/sheet/myWaitingDealSheetQuery.action')"></span>
	</div>
</div>
<div class="scroll_div" id="center" style="height:415px;">
	<div id="tab21" style="height:415px;">
		<iframe src="${ctx}/sheet/myWaitingDealSheetQueryShortcut.action" height="100%" width="100%" scrolling="no" frameborder="0" style="margin:0px;"></iframe>
	</div>
</div>
<div style="height:20px;background-color:#d6eaf7; padding-bottom:1px; padding-top:1px; padding-left:5px;">
	图例：
	<span class="colortdimg" style="margin-right:10px"><img src="${ctx}/workflow/sheet/images/green.png"  align="absmiddle" />  已完成，未超时</span>
	<span class="colortdimg" style="margin-right:10px"> <img src="${ctx}/workflow/sheet/images/red.png"  align="absmiddle" />  已完成，已超时 </span>
	<span class="colortdimg" style="margin-right:10px"> <img src="${ctx}/workflow/sheet/images/gray.png" align="absmiddle" /> 未完成，未超时</span>
	<span class="colortdimg" style="margin-right:10px"> <img src="${ctx}/workflow/sheet/images/yellow.png" align="absmiddle" /> 未完成，已超时</span>
</div>
</div>
   </body>
   </html>