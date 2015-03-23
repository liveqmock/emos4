<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/portal.css" />
<script src="${ctx}/common/javascript/main.js"></script>

        <script>function changeTab(num){
	var tabLength=document.getElementById("tabDiv").getElementsByTagName("div").length;
	var tabObj=document.getElementById("tabDiv").getElementsByTagName("div");
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
		document.getElementById("tab11").style.display="none";
		document.getElementById("tab12").style.display="none";
		}
	if(num<endnum){//判断不是最后一个
		tabObj.item(num).className="common_select"; 
		document.getElementById("tab11").style.display="none";
		document.getElementById("tab12").style.display="";
		}
	else{
		tabObj.item(num).className="last_select";  
		document.getElementById("tab11").style.display="none";
		document.getElementById("tab12").style.display="";
		}
		
	if(num==0){
		tabObj.item(num).className="first_select";  
		document.getElementById("tab11").style.display="";
		document.getElementById("tab12").style.display="none";
		}
	}
	</script>
<title>order_draft</title>
</head>
<body>
<div class="title_right">
      <div class="title_left">
        <span class="title_bg">
          <span><img src="${ctx}/common/style/blue/images/portal/img_01.png" width="18" height="18" align="absmiddle" />工单起草</span>
        </span>
        <span class="title_xieline"></span>
      </div>
</div>

<table  class="third_content_table">
	<tr>
		<td class="Info1" valign="top">
			<div id="tab11" class="info_content" style="height:322px;">
			 <iframe src="${ctx}/common/portalShortcut.action?id=402894f5295d39ec01295e5724bf0002" height="100%" width="100%" frameborder="0" style="margin:0px;"></iframe>
			</div>
		</td>
	</tr>
</table>

</body>
</html>