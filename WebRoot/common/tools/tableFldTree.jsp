<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name="sm_lb_tbFieldInfo" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,55);
		}
		window.onload = function() 
		{
			setCenter(0,55);
			loadTree();
		}
		var tree;
		function doHandler(key)//单选
		{
			if(tree.getLevel(tree.getSelectedItemId())!='1')
			{
				var pWindow = dialogArguments;
				var fldName = tree.getSelectedItemText();
				pWindow.document.getElementById('fieldname').value = fldName;
				window.close();
			}
		}
		function loadTree()
		{
			tree=new dhtmlXTreeObject("center","100%","100%",0);
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
			tree.enableTreeLines(true);
			tree.enableHighlighting(2);
			tree.loadXML("${ctx}/common/tableFldTree.action?id=0");
			tree.setOnClickHandler(doHandler);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_lb_tbFieldChoose" /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	   </div>
		<div id="center"></div>
	</div>
	<div class="add_bottom">
		<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
			onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
			onclick="window.close();"/>
	</div>
  </body>
</html>
