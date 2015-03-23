<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-y:auto;">
	<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<title>
    
    </title>
	<script src="${ctx}/common/javascript/dropmenu.js" type="text/javascript" defer="defer"></script>
	<script src="${ctx}/common/style/office/js/index.js" type="text/javascript" defer="defer"></script>
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>
	<script type="text/javascript"
			src="${ctx}/common/plugin/jquery/ui/jquery.timers-1.1.2.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/mymenu.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/office/css/index.css" />
	<script type="text/javascript">
	var menuids=["suckertree1"] //Enter id(s) of SuckerTree UL menus, separated by commas
	function buildsubmenus()
	{
		for (var i=0; i<menuids.length; i++)
		{
		  var ultags=document.getElementById(menuids[i]).getElementsByTagName("ul")
		  if( ultags==null)
		  {
		  	break;
		  }
		  for (var t=0; t<ultags.length; t++)
		  {
		 	
			if(ultags[t].parentNode.getElementsByTagName("a")[0]==null)
			{
				continue;
			}
		 	ultags[t].parentNode.getElementsByTagName("a")[0].className="subfolderstyle"
		 	
		    ultags[t].parentNode.onmouseover=function()
		    {
		    	this.getElementsByTagName("ul")[0].style.display="block"
		    }
		    ultags[t].parentNode.onmouseout=function()
		    {
		   		this.getElementsByTagName("ul")[0].style.display="none"
		    }
		  }//for (var t=0; t<ultags.length; t++)
		}
		
	}
	
	if (window.addEventListener)
	{
		window.addEventListener("load", buildsubmenus, false);
	}
	else if (window.attachEvent)
	{
		window.attachEvent("onload", buildsubmenus);
	}
 
    //我的菜单 链接弹出 
    function openwindow(url,name,iWidth,iHeight)
	{
		var url; 
		var name; 
		var iWidth; 
		var iHeight; 
		var iTop = (window.screen.availHeight-30-iHeight)/2; 
		var iLeft = (window.screen.availWidth-10-iWidth)/2; 
		window.open('${ctx}/'+url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
	}
		
	/***************************公用代码************************************/
	
	var topActivePageMenu = 'td0';
	function topPageMenuActive(objName)
	{
	
		document.getElementById(topActivePageMenu).className = 'menu_onblur';
		document.getElementById(objName).className = 'menu_onfocus';
		topActivePageMenu = objName;
	}
	window.onload = function()
	{
		//setCenter(0,111);
	}
	window.onresize = function() 
	{
		//setCenter(0,111)
	}
	/**首页弹出start**/
	function getTaskInfo(){
		jQuery.post("${ctx}/ultratask/getTaskInfo.action" ,   //服务器页面地址
            {},
            function(data) {
              if(data!=""){
                  //构建页面显示：
                  //jQuery("#viewPage").html(data);
                  //$("#viewPage").slideDown();
                  test(data);
                  var viewPage=$(document.getElementById("taskInfo"));
                  $(viewPage).css("cursor", "pointer").click(function() {updateListInfo();});
                  //然后10秒后关闭显示页面 一次时间
	              $(viewPage).oneTime(10000,function(){$(viewPage).slideUp();});
               }
             }
		);  
	}
	function updateListInfo(){
		 jQuery.post("${ctx}/ultratask/readTaskNote.action",   //服务器页面地址
         {},
         function(data) {
        	 $(document.getElementById("taskInfo")).slideUp();
         });
	}
	function test(data){
		createDiv("taskInfo",$(document.body));
		divObj = $(document.getElementById("taskInfo"));
		divObj.css('display','none');
		divObj.css('position','absolute');
		divObj.css('width','250px');
		divObj.css('height','80');
		divObj.css('z-index','32001');
		divObj.css('top',document.body.clientHeight-340);
		divObj.css('left',document.body.clientWidth-250);
		divObj.css('background','#CCFFFF');
		divObj.css('padding','10px');
		divObj.css('font-size','12px');
		divObj.css('color','#0000FF');
		divObj.css('filter','Alpha(opacity=80)') ;
		divObj.css('opacity','.60') ;
		divObj.css('border','1px solid #0000FF') ;
		divObj.css('border-top','4px solid #0000FF') ;
		divObj.html(data);
        divObj.slideDown();
	}
	function createDiv(id,jobj){
		$(jobj.children().get(0)).after("<div id='"+id+"'></div>");
	}
	/**首页弹出end**/
	
	function logout(){
		var href = "${ctx}/portal/userLogout.action?logoutType=";
		window.open(href,'_top');
	}
	</script>
	
	</head>

	<body>
<div id="viewPage"> </div>
<div id="taskInfo"> </div>
<div class="headerOffice" style="margin:-11px 50px 0 50px;">
    <!-- 
    <div class="headertip" style='display:none;' >
    	<div style="width:1200px; margin:0 auto; clear:both;">欢迎登录一站式服务管理平台！
          <div class="systemfuctionOffice"> <span><a href="index.action" target="_top">常规风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp; <span><a href="index_office.action" target="_top">Office风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp; <span><a href="index_rightmenu.action" target="_top">右侧菜单风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp; <span class="logon_user">欢迎您：${userSession.fullName}</span> <span class="help">系统帮助</span> <span class="exit"><a href="userLogout.action?logoutType=" target="_top">安全退出</a></span> </div>
        </div>
 	</div>
 	 -->
 	 
      <div class="bannerOffice" style="width:1200px; clear:both;">
      	<div style="width: 342px;height: 35px;cursor: pointer;position: absolute;z-index: 2000;" onclick="window.open('${ctx}/portal/index_office.action','_self');"></div>
    	<div class="banner_rightOffice" id="right">
    	<c:if test="${fn:indexOf(userSession.roleName,'IT运维') > -1}">    	
    		<div class="logoOffice2"><!-- IT运维管理平台 -->
    	</c:if>
    	<c:if test="${fn:indexOf(userSession.roleName,'IT运维') == -1}"> 	
          <div class="logoOffice"><!--IT统一服务管理平台 -->
    	</c:if>
        <!-- <div class="right_search">
              <li>
              <!-- 
            <input id="txtKeyword" type="text" value="服务目录搜索" onfocus="checkSearch1()" onblur="checkSearch2()" maxlength="100" style="width:180px;"/>
             -->
          </li>
          <!-- 
          <li>
                <span class="logon_user">欢迎您：${userSession.fullName}</span>
	      </li>
              <li>
            <select id="indexType">
                  <option>全部</option>
                </select>
          </li>
              <li>
            <input name="search" type="button" value="搜索" class="searchButton" 
	       		onmouseover="this.className='searchButton_hover'" 
	       		onmouseout="this.className='searchButton'" 
	       		onclick="planeSearch();"/>
          </li>
              <li>
            <input name="searchB" type="button" value="高级搜索" class="searchadv_button" 
	            onmouseover="this.className='searchadv_button_hover'" 
	            onmouseout="this.className='searchadv_button'" 
	            onclick="window.open('${ctx}/fulltext/search/search.jsp');" />
          </li></div>
           -->
      </div>
        </div>
  </div>
 
      	<map name="Map" id="Map"><area shape="rect" coords="100,200,10,100" style="cursor: pointer;" target="_blank" /></map>
 <div class="menubg" style="width:100%; margin:0 auto; clear:both;">
    <div class="menu">
          <ul>
       	<!-- 
       	 	<li	id="td0" class="menu_onfocus" onClick="topPageMenuActive('td0');"> <a href="${ctx}/common/portal/frame/portal.jsp" target="center"><span>首　　页</span></a> </li>
        -->
        <script>var page='';var pageUrl='';</script>
        <!--定义的Page头 开始-->
        <c:forEach items="${navigateList}" var="navigate" varStatus="status">
       		<c:if test="${fn:indexOf(userSession.roleName,'默认工作台') > -1 and navigate.text=='工作台'}"> 
       		<script>
       		page = 'td${navigate.mark}';pageUrl='${navigate.userdata.url}';
			</script>
       		</c:if>
	          <c:if test="${navigate.userdata.openway == '1'}"><!-- 目录树结构 -->
	           		<script>if(page == ''){page = 'td${navigate.mark}';pageUrl='${navigate.userdata.url}';}</script>
	           		<c:set var="url" value="${ctx}/portal/content_frame.action?menuid=${navigate.id}"/>
	            	<li id="td${navigate.mark}" class="menu_onblur" onClick="topPageMenuActive('td${navigate.mark}');" onMouseOver="showQuickBar('quickBar_${navigate.mark}')" onMouseOut="hideQuickBar()"><a href="${url}" target="center"><span>${navigate.text}</span> </a></li>
	          </c:if>
	          <c:if test="${navigate.userdata.openway == '2'}"><!-- 弹出打开 -->
	            <c:set var="openurl" value="${ctx}/${navigate.userdata.url}"/>
	            <c:if test="${fn:startsWith(navigate.userdata.url, 'http:') or fn:startsWith(navigate.userdata.url, 'https:')}">
	                  <c:set var="openurl" value="${navigate.userdata.url}"/>
	            </c:if>
	            <li id="td${navigate.mark}" class="menu_onblur" onClick="topPageMenuActive('td${navigate.mark}');" onMouseOver="showQuickBar('quickBar_${navigate.mark}')" onMouseOut="hideQuickBar()"><a href="${openurl}" target="_blank"><span>${navigate.text}</span></a></li>
	          </c:if>
	          <c:if test="${navigate.userdata.openway == '3'}"><!-- 首页式打开 -->
	           	<script>if(page == ''){page = 'td${navigate.mark}';pageUrl='${navigate.userdata.url}';}</script>
	            <c:set var="openurl" value="${ctx}/${navigate.userdata.url}"/>
	            <c:if test="${fn:startsWith(navigate.userdata.url, 'http:') or fn:startsWith(navigate.userdata.url, 'https:')}">
	                  <c:set var="openurl" value="${navigate.userdata.url}"/>
	            </c:if>
	            <li id="td${navigate.mark}" class="menu_onblur" onClick="topPageMenuActive('td${navigate.mark}');" onMouseOver="showQuickBar('quickBar_${navigate.mark}')" onMouseOut="hideQuickBar()"><a href="${openurl}" target="center"><span>${navigate.text}</span></a></li>
	          </c:if>  <li class="menu_line"></li>
        </c:forEach>
        <!--定义的Page头 结束-->
        
      </ul>
        </div><div style="position:absolute;z-index:100;top:5px;right:10px;">
      <div class="suckerdiv">
    <ul id="suckertree1" style="display: none;">
          <li>
        <div class="myMenu" onMouseOver="this.className='myMenuHover'"	onmouseout="this.className='myMenu'" ></div>
        <ul style="background:url(${ctx}/common/style/blue/images/index/mymenu_first_bg.png);width:192px;"  style="display: none;">
              <div class="mymenu_title"> <span class="mymenutitle">我的菜单</span> </div>
              ${myMenuHtml}
              <div class="mymenu_bottom"></div>
            </ul>
      </li>
        </ul>
  </div>
    </div>
  </div>
    </div>
<!-- 
<%@ include file="quickbar/quickBar_sheet.jsp"%>
<%@ include file="quickbar/quickBar_ultrasm.jsp"%>
<%@ include file="quickbar/quickBar_duty.jsp" %>
<%@ include file="quickbar/quickBar_plan.jsp" %>
<%@ include file="quickbar/quickbar_docsManager.jsp" %>
<%@ include file="quickbar/quickbar_taskbooking.jsp" %>
<%@ include file="quickbar/quickBar_workflow.jsp" %>
<%@ include file="quickbar/quickBar_repository.jsp" %>
 -->
	<div style="margin:0 50px 0 50px; clear:both;overflow-x:none;">
      <iframe src="" frameborder="0" width="100%" id="center" name="center" style="height:650px;margin:0 auto; clear:both;margin-top:8px;overflow-x:none;"></iframe>
    </div>
	<div style="width: 100%;height: 100%; padding-top:200px;display: none;" align="center"> <img src="${ctx}/common/style/blue/images/progress_nonmodal-circle.gif" width="32" height="32"/> </div>
	
	<div class="footer" style="width:1000px; margin:0 auto; clear:both; text-align:center;">
		<!-- <p><a href="###">网站地图</a> | <a href="###">联系我们</a></p> -->
        <p>&copy; 2014 国家开发银行版权所有<br/>
        	技术支持热线：张绍磊 13910275409（服务请求、事件流程）&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;施文   13811525206（变更流程、发布流程）
        </p></div>
</body>
</html>
<script type="text/javascript">
//setCenter(0,67);

if(pageUrl != ''){
	document.getElementById('center').src='${ctx}/' + pageUrl;
}
if(page != ''){
	topActivePageMenu = page;
	topPageMenuActive(page);
}
</script>