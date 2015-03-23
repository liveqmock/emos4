<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
    <script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,30);
		}
		window.onload = function()
		{
			setCenter(0,30);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
		<div class="page_div_bg">
			<table style="height:100%"><tr valign="bottom"><td><eoms:lable name="sm_t_mymenutree"/></td></tr></table>
		</div>
		<div class="" id="center"></div>
  	</div>
		<script type="text/javascript">
			//菜单目录树
			tree=new dhtmlXTreeObject("center","97%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/myMenuTree.action");
			tree.loadXML("${ctx}/common/myMenuTree.action?id=0");
			//tree.enableHighlighting(1);
			tree.setOnClickHandler(doHandler);
			tree.enableDragAndDrop(1);//1:可以移动 0:不可以移动
			tree.setDragHandler(dragHandlerItem);
			
			function doHandler(key)
			{
				var name = tree.getSelectedItemText();
		    	var id = tree.getSelectedItemId();
		    	if(id!="nodata")
		    	{
		    		window.open("${ctx}/myMenuManager/myMenu.action?myMenuId="+id,"rightFrame2");
		    	}
			}
			
			function refresh(itemid)
			{
				tree.refreshItem(itemid);
				if(itemid!="0")
				{
					window.open("${ctx}/myMenuManager/myMenu.action?myMenuId="+itemid,"rightFrame2");
				}
			}
			
		   function dragHandlerItem(fromId,toId){
		   	    if(confirm('<eoms:lable name="sm_msg_mymenuismove"/>'))
		   	    {  //1:菜单夹 2:菜单
		   	       var fromidtype = tree.getUserData(fromId,"type");
		   	       var toidtype = tree.getUserData(toId,"type");
		   	       if(fromidtype==toidtype && fromidtype=='2'){
		   	       	  alert("<eoms:lable name="sm_msg_mymenumoveerrorone"/>");
		   	       }else if(fromidtype=='1' && toidtype=='2'){
		   	       	  alert("<eoms:lable name="sm_msg_mymenumoveerrortwo"/>");
		   	       }else{
		   	       	  $.post("${ctx}/common/changeItemParentidAction.action",{fromId:fromId,toId:toId},function(result)
				      {});
				      return true;
		   	       }
				}
           }
		</script>
  </body>
</html>
