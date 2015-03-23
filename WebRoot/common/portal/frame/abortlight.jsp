<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/portal.css" />
<script src="../../javascript/main.js"></script>

        <script>
function changeTab2(num){
	var tabLength=document.getElementById("tabDiv02").getElementsByTagName("div").length;
	var tabObj=document.getElementById("tabDiv02").getElementsByTagName("div");
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
		document.getElementById("tab31").style.display="none";
		document.getElementById("tab32").style.display="none";
		}
	if(num<endnum){//判断不是最后一个
		tabObj.item(num).className="common_select";  
		document.getElementById("tab31").style.display="none";
		document.getElementById("tab32").style.display="";
		}
	else{
		tabObj.item(num).className="last_select";  
		document.getElementById("tab31").style.display="none";
		document.getElementById("tab32").style.display="";
		}
		
	if(num==0){
		tabObj.item(num).className="first_select";  
		document.getElementById("tab31").style.display="";
		document.getElementById("tab32").style.display="none";
		}
	}
	</script>

<title>abortlight</title>
</head>
<body> 
<div class="content">
	<div class="title_right">
		<div class="title_left">
			<span class="title_bg"><span><img src="../../style/blue/images/title/tag7.png"  align="absmiddle" />遗留问题</span></span>
			<span class="title_xieline"></span>
			<span class="title_more" onclick="window.open('${ctx}/sheet/baseInfoQuery.action?baseSchema=WF4:EL_TTM_LPH')"></span>
		</div>
	</div>
	<div class="scroll_div" id="center">
		<div id="tab21" style="height:324px;overflow:hidden;">
			<iframe src="${ctx}/sheet/lphShutcut.action" height="100%" width="100%" scrolling="no" frameborder="0" style="margin:0px;"></iframe>
		</div>
	</div>
</div>
</body>
</html>