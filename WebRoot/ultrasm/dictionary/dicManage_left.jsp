<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,60);
		}
		window.onload = function()
		{
			setCenter(0,60);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
		<div class="page_div_bg">
			<table style="height:100%"><tr valign="bottom"><td>${diname}</td></tr></table>
		</div>
		<div class="" id="center"></div>
  	</div>
		<script type="text/javascript">
			//菜单目录树
			var dictype = "${datadictype}";
			tree=new dhtmlXTreeObject("center","97%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/dicTree.action");
			tree.loadXML("${ctx}/common/dicTree.action?id=0&dictype="+dictype);
			//tree.enableHighlighting(1);
			tree.setOnClickHandler(doHandler)
			function doHandler(key)
			{
				var name = tree.getSelectedItemText();
		    	var id = tree.getSelectedItemId();
		    	//cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮
				window.open("${ctx}/dicManager/dicInfo.action?cfgpage=1&dicId="+id,"rightFrame2");
			}
			function refresh(itemid)
			{
				if(itemid == '0')
				{
					tree.deleteChildItems(itemid);
					tree.loadXML("${ctx}/common/dicTree.action?id=0&dictype="+dictype);
				}
				else
					tree.refreshItem(itemid);
				var type = '';
				if(itemid == '0' || itemid.indexOf(':')>0)//当刷新的节点ID等于0或存在分隔符":"时,说明是类型
					type = '1';
				else//否则为信息项
					type = '2';
				//cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮
				window.open('${ctx}/dicManager/dicInfo.action?cfgpage=1&type='+type+'&dicId='+itemid,'rightFrame2');
			}
		</script>
  </body>
</html>
