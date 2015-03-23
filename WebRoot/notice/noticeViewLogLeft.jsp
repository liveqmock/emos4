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
			setCenter(8,28);
		}
		window.onload = function() 
		{
			setCenter(8,28);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">级别管理</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="add_scroll_div_x" style="overflow:hidden;" id="center"></div>
  	</div>
		<script type="text/javascript">
			//菜单目录树
			var tree=new dhtmlXTreeObject("center","97%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/notice/getViewLogTree.action");
			tree.loadXML("${ctx}/notice/getViewLogTree.action?id=0&dtcode=noticeLevel");
			
			//tree.enableHighlighting(1);
			tree.setOnClickHandler(doHandler)
			function doHandler(key)
			{
				
				var name = tree.getSelectedItemText();
		    	var id = tree.getSelectedItemId();
		    	var src = "";
		    	// 03 未查看，01 按公告发布人，02 已删除的， 04 已查看的
		    	if(id == '03'){
		    		src = "${ctx}/notice/listNoticeViewLog.action?noticeStatus=1&isview=0";
		    		window.open(src,'rightFrame2');
		    	}else if(id == '04'){
		    		src = "${ctx}/notice/listNoticeViewLog.action?noticeStatus=1&isview=1";
		    		window.open(src,'rightFrame2');
		    	}else if(id == '01'){
		    		src = "${ctx}/notice/listNoticeViewLog.action?noticeStatus=1";
		    		window.open(src,'rightFrame2');
		    	}else if(id == '02'){
		    		src = "${ctx}/notice/listNoticeViewLog.action?noticeStatus=4";
		    		window.open(src,'rightFrame2');
		    	}else{
		    		src = "${ctx}/notice/listNoticeViewLog.action?noticeStatus=1&creatorid="+id;
		    		window.open(src,'rightFrame2');
		    	}
		    	//cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮
				//window.open("${ctx}/dicManager/dicInfo.action?cfgpage=1&dicId="+id,"rightFrame2");
				//alert(id);
			}
			window.open('${ctx}/notice/listNoticeViewLog.action?noticeStatus=1','rightFrame2');
		</script>
  </body>
</html>
