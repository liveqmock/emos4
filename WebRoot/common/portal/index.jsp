<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@ include file="/common/core/taglibs.jsp"%> 
    <title>新一代电子运维管理平台</title>
	<script src="${ctx }/common/javascript/dropmenu.js" type="text/javascript"></script>
	<link rel="stylesheet" media="all" type="text/css" href="${ctx }/common/style/blue/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/common/style/blue/css/mymenu.css" />	
	<script type="text/javascript">
	//SuckerTree Vertical Menu (Aug 4th, 06)
	//By Dynamic Drive: http://www.dynamicdrive.com/style/
	var menuids=["suckertree1"] //Enter id(s) of SuckerTree UL menus, separated by commas
	function buildsubmenus()
	{
		for (var i=0; i<menuids.length; i++)
		{
		  var ultags=document.getElementById(menuids[i]).getElementsByTagName("ul")
		  for (var t=0; t<ultags.length; t++)
		  {
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
	window.addEventListener("load", buildsubmenus, false)
	else if (window.attachEvent)
	window.attachEvent("onload", buildsubmenus)
 
	/***************************公用代码************************************/
	function getPageMenu(menuName)
	{
		activePageMenu = menuName;
	}
	function PageMenuActive(objName)
	{
		document.getElementById(activePageMenu).className = 'menu_onblur';
		document.getElementById(objName).className = 'menu_onfocus';
		activePageMenu = objName;
	}
	window.onload = function()
	{
	    getPageMenu('td1');PageMenuActive('td1');
		setCenter(0,67);
	}
	window.onresize = function() 
	{
		setCenter(0,67)
	}
	</script>

  </head>
  
  <body >
	<div class="header">
	  <div class="banner">
	    <div class="banner_right" id="right">
	      <div class="logo">
	      	<div class="systemfuction">
	      		<span><a href="index.action" target="_top">常规风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span><a href="index_office.action" target="_top">Office风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span><a href="index_rightmenu.action" target="_top">右侧菜单风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="logon_user">欢迎您：${userSession.fullName}</span>
	      		<span class="help">系统帮助</span>
	      		<span class="exit"><a href="login.jsp" target="_top">退出系统</a></span>
	      	</div>
	      </div>
	    </div>
	  </div>
	  <div class="menu">
		  <ul>
			  <a href="${ctx}/common/portal/frame/portal.jsp" target="center"><li id="td1" class="menu_onfocus"  onclick="PageMenuActive('td1');"><span>首　　页</span></li></a>
			  <li class="menu_line"></li>
			  <a href="${ctx}/common/portal/frame/content_frame.jsp" target="center"><li id="td2" class="menu_onblur"  onclick="PageMenuActive('td2');"><span>值班管理</span></li></a>
			  <li class="menu_line"></li>
			  <a href="${ctx}/common/portal/frame/content_frame.jsp" target="center"><li id="td3" class="menu_onblur"  onclick="PageMenuActive('td3');"><span>作业计划</span></li></a>
			  <li class="menu_line"></li>
			  <a href="${ctx}/common/portal/frame/content_frame.jsp?sys=sm" target="center"><li id="td4" class="menu_onblur"  onclick="PageMenuActive('td4');"><span>系统管理</span></li></a>
			  <li class="menu_line"></li>
			  <a href="index_office.html" target="_top"><li id="td5" class="menu_onblur"  onclick="PageMenuActive('td5');"><span>Office风格</span></li></a>
		  </ul>
	  </div>	
	</div>
	
	<div style="position:absolute;z-index:100;top:55px;right:10px;">
	<div class="suckerdiv">
	<ul id="suckertree1">
	<li>
	  <div class="myMenu" onmouseover="this.className='myMenuHover'" onmouseout="this.className='myMenu'"></div>
	  <ul>
	  <div class="mymenu_title">
	  <span class="mymenutitle">我的菜单</span>
	  </div>
	  
	  <li class="first_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon"></span><span class="first_item_aSpan">文档管理</span></a></li>
	  <li class="first_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon"></span><span class="first_item_aSpan">文档管理</span></a></li>
	  <li class="first_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon"></span><span class="first_item_aSpan">文档管理</span></a></li>
	  <li class="first_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon"></span><span class="first_item_aSpan">文档管理</span></a></li>
	  <li class="first_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon"></span><span class="first_item_aSpan">用户基本设置</span></a>
	    <ul>
	    <li class="second_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon2"></span><span class="first_item_aSpan">文档管理</span></a></li>
	    <li class="second_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon2"></span><span class="first_item_aSpan">系统管理</span></a></li>
	    <li class="second_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon2"></span><span class="first_item_aSpan">资源管理</span></a></li>
	    <li class="second_item_li"><a href="#" class="first_item_a"><span class="mymenu_icon2"></span><span class="first_item_aSpan">新增人员</span></a></li>
	    </ul>
	  </li>
	  <div class="mymenu_bottom"></div>  
	  </ul>
	</li>
	</ul>
	</div>
	</div>
	<iframe src="${ctx}/common/portal/frame/portal.jsp"  frameborder="0" id="center" name="center"></iframe>		
  </body>
</html>
