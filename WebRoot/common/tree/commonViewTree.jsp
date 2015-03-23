<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src='style/js/jquery-1.8.0.min.js'></script>
	<script type="text/javascript" src="${ctx}/common/tree/js/commonViewTree.js"></script>
	<link rel="stylesheet" href="${ctx}/serverQuest/css/peopleTree.css" type="text/css"></link>
	<script type="text/javascript">
	var tree2;
	$(function(){
		tree2 = new CommonViewTree("commonViewTree","100%","100%",0,${optJson});
	});
	</script>
  </head>
  
  <body>
  <div class="" id="commonViewTree" style="height:600px"></div>
  <ul id="checkedItems" class="checkedItem"></ul>
  <input type="button" onclick="tree2.clickOK()" value="确定">
  <input type="button" onclick="tree2.clear()" value="清空">
  </body>
</html>
