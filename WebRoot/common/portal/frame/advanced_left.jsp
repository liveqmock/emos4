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
	line-height:24px;height:24px;background:url(${ctx}/common/style/blue/images/portal/InfoImg.png) no-repeat left;margin-left:20px\0;padding-left:15px;}
</style>
	<body>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="left" id="showtree">
			<tr onClick="showSubmenu(0);">
				<td id="td0" class="tree_title_bg" valign="top">
					<div class="title_right">
						<div class="title_left">
							<span class="title_bg">
								<span class="title_icon">${menuName}</span>
							</span>
							<span class="title_xieline"></span>
						</div>
					</div>
				</td>
			</tr>
			<tr class=submunuTbody id=submunuTbody_1 style="height:150px">
				<td valign="top">
					<div id="knowledge_my" class="tree_content" >
						<div class="Infocontent">
						<c:if test="${nodemark == 'docsManager'}">
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/dmPortal/docsManagerPortal.action');">文档库首页</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/folderManager/dm.action?foldertype=1');">我的文档</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/folderManager/dm.action?foldertype=2');">部门文档</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/folderManager/dm.action?foldertype=5');">我收藏的文档</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/docsManager/fileSendMsgList.action');">我收到的文档</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/docsManager/recentFileList.action');">最近浏览的文档</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/docsManager/fileApproveMsgList.action');">待审文档</a></li>
						</c:if>
						<c:if test="${nodemark == 'repository'}">
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/repositoryPortal.action');">案例库首页</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/addRepository.action?urlFrom=addNew');">创建一条案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/myRepositoryList.action');">我创建的案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/packAwayRepositoryList.action');">我收藏的案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/readRepositoryList.action');">我订阅的案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/latelyLookRepository.action');">我最近浏览的案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/listRepositoryByManager.action');">经理审核案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/listRepositoryByExpert.action');">专家修订案例</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/listUpdRepositoryByExpert.action');">修订案例查询</a></li>
							<li><a href="javascript:;" onclick="openUrlHtml('${ctx}/ultrarepository/listReNewRepositoryByManager.action');">恢复案例</a></li>
						</c:if>
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
		var menuid = "${menuid}";
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
			menuId = id;
			do{
				url = tree.getUserData(id,"url");
				id=0;
			}while(id!="0")
			if(url!='' && url!=undefined)
			{
				if(url.indexOf("?") > 0)
				{
					url = url + '&id=' + menuId;
				}
				else
				{
					url = url + '?id=' + menuId;
				}
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
		}	
		
		function openUrlHtml(url)
		{
			window.open(url,"rightFrame");
		}
		
		function refresh(itemid)
		{
			window.open('${ctx}/folderManager/dm.action?parentid='+itemid+'&foldertype=0','rightFrame');
		}
	</script>
	</body>
</html>
