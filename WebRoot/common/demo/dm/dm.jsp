<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/core/jspbase.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/core/meta.jsp"%>
<title>Docs Manager Demo</title>
<%@ include file="/common/core/cssbase.jsp"%>
<style>
.w90p #header, .w90p #content, .w90p #footer { width:90%;}
.w90p #header { height:30px}
.btnbar:after, .listheader:after { content:'\20';display:block;clear:both;height:0; }
.btnbar, .listheader { *zoom:1}

.breadcrumbs { font-size:14px}
span.navlink { color:#336699;cursor:pointer}

.btnbar { border-bottom:1px solid #333;}
.btns { text-align:right;}
#btn-view-list { width:20px;height:20px;background:url(view_list.png) no-repeat;float:right;cursor:pointer;}
#btn-view-icon { width:20px;height:20px;background:url(view_icon.png) no-repeat;float:right;cursor:pointer;}

.box .even { }
.box p { margin:10px;width:32px;height:32px;background:url(loading.gif) no-repeat;}
.box div.over,
.box .view-icon div.over { background-color:#f1f7fe}
.box div.selected,
.box .view-icon div.selected { background-color:#cde2fc}

.listheader { padding:3px 0 3px 20px;position:relative}
.listheader div { color:#4c607a;position:absolute}
.listheader span { float:left;margin-left:-20px}

.doc { position:relative;}
.doc span { float:left}
.doc div { position:absolute}
.doc .size, .listheader .size { left:200px;}
.doc .date, .listheader .date  { left:300px;}
.doc .author, .listheader .author  {left:400px;}

.doc { height:20px;padding:2px 0 2px 20px; background:url(icons-small.png) no-repeat;-moz-user-select:none;cursor:pointer;}
.dir { background-position:0 5px;}
.txt { background-position:0 -26px;}
.img { background-position:0 -56px;}

.view-icon .listheader { display:none}
.view-icon .doc { display:inline;width:100px;float:left;border:0;background:url(icons-big.png) no-repeat;padding-left:50px;height:45px;}
.view-icon .doc div { display:none}
.view-icon .dir { background-position:0 -45px;}
.view-icon .txt { background-position:0 -130px;}
.view-icon .img { background-position:0 -237px;}

.ctxmenu { display:none;border:1px solid #ccc;position:absolute;background:#fff;-moz-box-shadow:0 4px 6px #ccc;}
.ctx-item { white-space:nowrap;cursor:pointer;padding:2px 5px}
.ctxmenu .over { background-color:#f1f7fe}
</style>
<%@ include file="/common/core/jsbase.jsp"%>
</head>
<body class="w90p">
<div id="header"></div>
<div id="content">
	<div id="explorer"></div>
</div>
<div id="footer"></div>
<script>
KISSY.use('docmanager', function(S){
	docs = new S.DocManager({
		container : '#explorer',
		rootId : '01',
		rootName : '根文件夹',
		dataUrl : 'data-json.jsp'
	});
	
	docs.on('doc.before.dblclick',function(ev, data){
		S.log(ev.el);
	})
});
</script>
</body>
</html>