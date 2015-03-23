<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name="sm_t_menuInfo" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,71);
		}
		window.onload = function()
		{
			setCenter(0,71);
			loadTree();
		}
		var tree;
		function doHandler(key){}
		function loadTree()
		{
			var roleid = '${param.roleid}';
			//菜单目录树
			tree=new dhtmlXTreeObject("center","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(1);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			//tree.setXMLAutoLoading("${ctx}/common/roleMenuTree.action");
			tree.loadXML("${ctx}/common/roleMenuTree.action?id=0&roleid="+roleid);
			//tree.enableHighlighting(1);//是否有url链接
			//tree.enableThreeStateCheckboxes(1);
			tree.setOnClickHandler(doHandler)
		}
		function submit()
		{
			var ids = tree.getAllChecked();
			if(ids=='')
			{
				window.close();
			}
			document.getElementById('menuids').value = ids;
			document.menuForm.submit();
			window.close();
		}
	</script>
  </head>
  
  <body>
  	<form name="menuForm" action="${ctx}/roleManager/saveRoleMenu.action" method="post">
		<input type="hidden" name="menuids" id="menuids" value=""/>
		<input type="hidden" name="roleid" value="${param.id}"/>
	</form>
  	<div class="content">
		<div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_subt_menuChoose" /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	   </div>
	   <div class="add_scroll_div_x" id="center"></div>
  	</div>
	<div class="add_bottom">
		<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_save"/>" class="save_button"
			onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="submit();"/>
		<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
		    onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
		    onclick="window.close();" />
	</div>
  </body>
</html>
