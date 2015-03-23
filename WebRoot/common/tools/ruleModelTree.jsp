<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title>规则模板信息</title>
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
			var input_id = "${param.input_id}";
			var input_name = "${param.input_name}";
			var selId = tree.getSelectedItemId();
			var selText = tree.getSelectedItemText();
			if(window.opener!=null)
			{
				window.opener.document.getElementById(input_id).value = selId;
				window.opener.document.getElementById(input_name).value = selText;
				window.close();
			}
		}
		function loadTree()
		{
			tree=new dhtmlXTreeObject("center","100%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(false);//是否显示结点间的连线,默认false
			tree.loadXML("${ctx}/slaActionManager/getRuleModelTree.action");
			tree.setOnClickHandler(doHandler)
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2">规则模板树</span>
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