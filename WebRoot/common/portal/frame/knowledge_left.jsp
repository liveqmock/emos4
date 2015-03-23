<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.net.URLDecoder"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="com_t_title" /></title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
		<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
		<script src="${ctx}/common/javascript/left.js"></script>
	</head>

<style>
.Infocontent { list-style:none;margin-top:5px;padding:0px;}
.Infocontent li{ clear:both;
	list-style:none;
	line-height:24px;height:24px;background:url(../../style/blue/images/portal/InfoImg.png) no-repeat left;margin-left:20px\0;padding-left:15px;}
</style>
	<body>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="left" id="showtree">
			<tr onClick="showSubmenu(0);">
				<td id="td0" class="tree_title_bg" valign="top">
					<div class="title_right">
						<div class="title_left">
							<span class="title_bg">
								<span class="title_icon">案 例 库</span>
							</span>
							<span class="title_xieline"></span>
					        <a href="javascript:void(0);" onclick="openwindow('${ctx}/common/portal/myMenuAdd.action','',600,300);">
					        	<span 	style="float:right;margin-right:5px;margin-top:2px;" 
					        			class="add_mymenu" 
					        			onmouseover="this.className='add_mymenu_over'" onmouseout="this.className='add_mymenu'">
					        	</span>
					        </a>
						</div>
					</div>
				</td>
			</tr>
			<tr class=submunuTbody id=submunuTbody_1 style="height:150px">
				<td valign="top">
					<div id="knowledge_my" class="tree_content" >
						 <div class="Infocontent">
							<li><a href="../../../ultrarepository/repositoryPortal.action">案例库首页</a></li>
							<li><a href="../../../ultrarepository/addRepository.action?urlFrom=addNew">创建一条案例</a></li>
							<li><a href="../../../ultrarepository/myRepositoryList.action?urlFrom=sNotDraft">我创建的案例</a></li>
							<li><a href="../../../ultrarepository/packAwayRepositoryList.action">我收藏的案例</a></li>
							<li><a href="../../../ultrarepository/readRepositoryList.action">我订阅的案例</a></li>
							<li><a href="../../../ultrarepository/latelyLookRepository.action">我最近浏览的案例</a></li>
							<li><a href="../../../ultrarepository/listRepositoryByManager.action">待审核案例</a></li>
							<li><a href="../../../ultrarepository/listRepositoryByExpert.action">专家复核案例</a></li>
							<li><a href="../../../ultrarepository/listUpdRepositoryByExpert.action">专家修改案例</a></li>
							<li><a href="../../../ultrarepository/listReNewRepositoryByManager.action">待恢复案例</a></li>
						</div>
					</div>
				</td>
			</tr>
			<tr class=submunuTbody style="height:1px">
				<td>
					<hr/>
				</td>
			</tr>
			<tr class=submunuTbody id=submunuTbody>
				<td valign="top">
					<div id="treeboxbox_tree" class="tree_content"></div>
				</td>
			</tr>
		</table>
		<script>
		var menuid = '40288e0b2cbabe38012cbacb0af90012';
		tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
		//tree.setSkin('csh_vista');
		tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
		tree.setXMLAutoLoading("${ctx}/common/leftTree.action");
		tree.setOnClickHandler(function(id){openPathDocs(id);});
		if(menuid!="")
		{
			tree.loadXML("${ctx}/common/leftTree.action?id="+menuid+"&openmenuid=${openmenuid}");
		}
		function openPathDocs(id)
		{
			var entUrl = "";
			var getFileFl = true;
			var suffix = "#"+id
			var url;
			do{
				url = tree.getUserData(id,"url");
				id=0;
			}while(id!="0")
			if(url!='' && url!=undefined)
			{
				//alert(url);
				if(url.indexOf("http:")<0)
				    url = "${ctx}/"+url;
			    if(url.indexOf("target=_blank") != -1)
				{
					window.open(url);
				}
				else
				{
					//alert(url);
					window.open(url,"rightFrame");
				}
			}
			//window.frames.contentFrame.location.href = entUrl
		}	
		
				//我的菜单 链接弹出 
	    function openwindow(url,name,iWidth,iHeight)
		{
			var id = tree.getSelectedItemId();
			if(id==''){
				alert('<eoms:lable name="sm_msg_selectfirstmymenu"/>');
			}else{
				var url; 
				var name; 
				var iWidth; 
				var iHeight; 
				var iTop = (window.screen.availHeight-30-iHeight)/2; 
				var iLeft = (window.screen.availWidth-10-iWidth)/2; 
				window.open(url+'?menuid='+id,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
			}
		}
	</script>
	</body>
</html>
