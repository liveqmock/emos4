<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="com_t_title" /> </title>
		<script src="${ctx}/common/javascript/dropmenu.js" type="text/javascript"></script>
		<script src="${ctx}/common/style/office/js/index.js" type="text/javascript"></script>
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
	window.addEventListener("load", buildsubmenus, false)
	else if (window.attachEvent)
	window.attachEvent("onload", buildsubmenus)
 
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
	    getPageMenu('td0');PageMenuActive('td0');
		setCenter(0,67);
	}
	window.onresize = function() 
	{
		setCenter(0,67)
	}
	</script>

	</head>

	<body>
    
    <div class="headerOffice">
      <div class="bannerOffice">
        <div class="banner_rightOffice" id="right">
        
          <div class="logoOffice">
          	<div class="systemfuctionOffice">
                <span><a href="index.action" target="_top">常规风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span><a href="index_office.action" target="_top">Office风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span><a href="index_rightmenu.action" target="_top">右侧菜单风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="logon_user">欢迎您：${userSession.fullName}</span>
                <span class="help">系统帮助</span>
                <span class="exit"><a href="userLogout.action?logoutType=arsys" target="_top">安全退出</a></span>
            </div>
          </div>
          
        </div>
      </div>
      <div class="menubg">
      <div class="menu">
      <ul>
					<a href="${ctx}/common/portal/frame/portal.jsp" target="center">
						<li	id="td0" class="menu_onfocus" onclick="PageMenuActive('td0');"><span>首　　页</span></li>
					</a>
					<!--定义的Page头 开始-->
					<c:forEach items="${navigateList}" var="navigate" varStatus="status">						
						<c:set var="url" value="${ctx}/portal/content_frame.action?menuid=${navigate.id}"/>
						
						<%-- 知识库 --%>
						<c:if test="${'40288e0b2cbabe38012cbacb0af90012' == navigate.id}">
							<c:set var="url" value="${ctx}/common/portal/frame/knowledge_base.jsp?menuid=${navigate.id}"/>
						</c:if>
						
						<li class="menu_line"></li>
						<a href="${url}" target="center">
							<li id="td${status.count}" class="menu_onblur" onclick="PageMenuActive('td${status.count}');" onmouseover="showQuickBar('quickBar_${navigate.mark}')" onmouseout="hideQuickBar()"><span>${navigate.text}</span></li>
						</a>
					</c:forEach>
					<!--定义的Page头 结束-->
      
      </ul>
      </div>
            
    </div>
    </div>    

			
		<div style="position:absolute;z-index:100;top:36px;right:20px;">
			<div class="suckerdiv">
				<ul id="suckertree1">
					<li>
						<div class="myMenu" onmouseover="this.className='myMenuHover'"	onmouseout="this.className='myMenu'"></div>
						<ul style="background:url(${ctx}/common/style/blue/images/index/mymenu_first_bg.png);width:192px;">

							<div class="mymenu_title">
								<span class="mymenutitle">我的菜单</span>
							</div>
							${myMenuHtml}
							<div class="mymenu_bottom"></div>
						</ul>
					</li>
					
				</ul>
			</div>
		</div>
		<div id="skin_div" style="position:fixed;z-index:100;display:none">
			<ul>
				<li>菜单靠左风格</li>
				<li>菜单靠右风格</li>
				<li>Office风格</li>
			</ul>
		</div>
		<div id="quickBar_sheet" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_sheet')">
			<div class="quickBarItem" style="width:320px;">
				<div class="quickBarListBox">
					<div class="quickBarListTable">
						<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_TTM_TTH', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
							<img src="${ctx}/common/style/office/images/newForm.png" class="quickBarListItemImg" />
							<span>故障处理工单</span>
						</div>
						<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?schema=WF4:EL_CM_NC_IU', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
							<img src="${ctx}/common/style/office/images/newForm.png" class="quickBarListItemImg" />
							<span>网络综合调整工单</span>
						</div>
						<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?schema=WF4:EL_UVS_TSK', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
							<img src="${ctx}/common/style/office/images/newForm.png" class="quickBarListItemImg" />
							<span>通用任务工单</span>
						</div>
						<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?schema=WF4:EL_TTM_CCH', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
							<img src="${ctx}/common/style/office/images/newForm.png" class="quickBarListItemImg" />
							<span>个人投诉处理工单</span>
						</div>
						<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?schema=WF4:EL_CM_NC_DU', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
							<img src="${ctx}/common/style/office/images/newForm.png" class="quickBarListItemImg" />
							<span>网络数据管理工单</span>
						</div>
						<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?schema=WF4:EL_CM_NC_CC', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
							<img src="${ctx}/common/style/office/images/newForm.png" class="quickBarListItemImg" />
							<span>电路调度工单</span>
						</div>
					</div>
				</div>
				<div class="quickBarDesc">新建工单</div>
			</div>
			<div class="quickBarSplit"></div>
			<div class="quickBarItem" style="width:320px;">
				<div class="quickBarItemBtn" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">待办工单</div>
				</div>
				<div class="quickBarItemBtn" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">已办工单</div>
				</div>
				<div class="quickBarItemBtn" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">工单搜索</div>
				</div>
				<div class="quickBarDesc">常用查询列表</div>
			</div>
			<div class="quickBarSplit"></div>
		</div>
		<div id="quickBar_ultrasm" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_ultrasm')">
			<div class="quickBarItem" style="width:320px;">
				<div class="quickBarItemBtn" onclick="openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=depInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">部门信息</div>
				</div>
				<div class="quickBarItemBtn" onclick="openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=personInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">人员信息</div>
				</div>
				<div class="quickBarItemBtn" onclick="openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=menunode', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">菜单目录信息</div>
				</div>
				<div class="quickBarItemBtn" onclick="openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=roleInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
					<div class="quickBarItemBtnImgBox">
						<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
					</div>
					<div class="quickBarItemBtnText">角色信息</div>
				</div>
				<div class="quickBarDesc">常用查询列表</div>
			</div>
			<div class="quickBarSplit"></div>
		</div>
		
		<iframe src="${ctx}/common/portal/frame/portal.jsp" frameborder="0" width="100%" id="center" name="center" height="1px"></iframe>
		<div style="width: 100%;height: 100%; padding-top:200px;" align="center">
			<img src="${ctx}/common/style/blue/images/progress_nonmodal-circle.gif" width="32" height="32"/>
		</div>
	</body>
</html>
