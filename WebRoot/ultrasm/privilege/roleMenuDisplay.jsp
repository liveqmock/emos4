<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name="sm_t_roleMenuDisplay" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,26);
		}
		window.onload = function()
		{
			setCenter(0,26);
			loadTree();
		}
		function doHandler(key){}
		function loadTree()
		{
			var roleid = '${param.roleid}';
			//菜单目录树
			tree=new dhtmlXTreeObject("center","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			//tree.setXMLAutoLoading("${ctx}/common/roleMenuTree.action");
			tree.loadXML("${ctx}/common/roleMenuTree.action?id=0&roleid="+roleid);
			//tree.enableHighlighting(1);//是否有url链接
			//tree.enableThreeStateCheckboxes(1);
			tree.setOnClickHandler(doHandler);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
		<div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_subt_roleMenuDisplay" /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	   </div>
	   <div class="add_scroll_div_x" id="center"></div>
  	</div>
  </body>
</html>
