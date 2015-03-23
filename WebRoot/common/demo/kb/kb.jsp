<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/core/jspbase.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/core/meta.jsp"%>
<base target="pageFrame"/>
<title>Knowledge Base Demo</title>
<%@ include file="/common/core/cssbase.jsp"%>
<style>
html, body { overflow:hidden;}
body { background:#fff;}
a { color:#336699}
.grid-s220m0 .col-sub { margin-left: -100%; width: 235px;}
div.max .col-sub {width:12px}
.grid-s220m0 .main-wrap { margin-left:239px;height:100%}
div.max .main-wrap {margin-left:12px;}
.max #common,
.max #treenav,
.max #topbar h2 { display:none;}
.max #nav { overflow:hidden;background:#fff}

.col-main { background:#85b8ff}
#pageFrame { width:100%;height:100%;border:0;}
#header { background:#85b8ff;padding:5px 10px}
#header h1 { font-size:14px; color:#555;float:left;line-height:15px;margin-right:20px;}
#nav { overflow:auto;background:#EFF8FF}
#nav h3 { margin:3px 0;color:#555}
#common,
#treenav { margin-bottom:10px;padding: 6px 0 0 15px}
#header ul,
#common ul,
#tree { padding-left:10px;margin-bottom:10px;}
#common li { margin:3px 0}
#header li { float:left;height:20px;margin:0 5px;}
#topbar { background:url(hbg.gif) repeat-x;height:26px}
#topbar h2 { float:left;font-size:12px; color:#555;font-weight:normal;padding:2px 10px;*padding-top:4px;}
.toolbar { float:right;}
.toolbar .btn-switch { background:url(switch.gif) no-repeat;width:16px;height:16px;cursor:pointer;margin:3px;}
.max .toolbar .btn-switch { background-position:-3px -16px;width:12px;margin:3px 0;}
</style>
<%@ include file="/common/core/jsbase.jsp"%>
</head>
<body>
<div id="header" class="ks-clear ks-hidden">
	<h1>知识库</h1>			
	<ul>
		<li><a href="#">知识库首页</a></li>
		<li><a href="#">我的收藏</a></li>
		<li><a href="#">我的订阅</a></li>
		<li><a href="#">最近浏览</a></li>
	</ul>
</div>
<div id="content" class="grid-s220m0">
	<div class="col-main">
		<div class="main-wrap">
			<iframe src="about:blank" height="100%" name="pageFrame" id="pageFrame" scroll="auto" frameborder="0"></iframe>
		</div>
	</div>
	<div id="nav" class="col-sub">
		<div id="topbar" class="ks-clear">
			<h2>知识库导航</h2>
			<div class="toolbar">
				<div id="switch" class="btn-switch"></div>
			</div>
		</div>
		<div id="common">
			<h3>知识管理</h3>
			<ul>
				<li><a href="kb-index.jsp">知识库首页</a></li>
				<li><a href="kb-new.jsp">创建一条知识</a></li>
				<li>我创建的 <a href="#">知识</a> | <a href="#">草稿</a></li>
				<li>我的 <a href="#">收藏</a> | <a href="#">订阅</a></li>
				<li><a href="#">最近浏览</a></li>
				<li><a href="#">待审核的知识</a></li>
				<li></li>
			</ul>
		</div>
		<div id="treenav">
			<h3>知识库</h3>
			<div id="tree"></div>
		</div>
	</div>
</div>
<div id="footer"></div>
<script>
KISSY.use('core,tree', function(S){
	var Event = S.Event, DOM = S.DOM;
	Event.on(window, 'load resize', function(){
		var h = DOM.viewportHeight();
		DOM.height('#nav', h);
		DOM.height('#pageFrame', h);
		DOM.get('#pageFrame').src = 'kb-index.jsp';
	});
	
	Event.on('#switch', 'click', function(){
		S.one('#content').toggleClass('max');
	});
	
	var menuid = '402894a6297e3e0101297e457d0d0001';
	var tree = new dhtmlXTreeObject("tree", null, null, 0);
	tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
	tree.setXMLAutoLoading("${ctx}/common/leftTree.action");
	tree.setOnClickHandler(openPathDocs);	
	tree.loadXML("${ctx}/common/leftTree.action?id="+menuid+"&openmenuid="+menuid);
		
		function openPathDocs(id){

			var url;
			if(id != "0"){
				url = tree.getUserData(id,"url");
			}
			
			if(url!='' && url!=undefined)
			{
				if(url.indexOf("http:")<0){
				    url = "${ctx}/"+url;
				}
			    if(url.indexOf("target=_blank") != -1)
				{
					window.open(url);
				}
				else
				{
					window.open(url,"pageFrame");
				}
			}
		}
});
</script>
</body>
</html>