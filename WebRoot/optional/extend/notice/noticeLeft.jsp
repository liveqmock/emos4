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
		<div class="page_div_bg">
			<div style="position:relative;top:5px;left:5px"><eoms:lable name="sm_lb_formTypeTree"/></div>
		</div>
		<div class="scroll_div" style="overflow:hidden;" id="center"></div>
  	</div>
  		<script type="text/javascript">
			tree=new dhtmlXTreeObject("center","97%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(false);//是否显示结点间的连线,默认false
			tree.loadXML("${ctx}/smOrderForm/getOrderTypeTree.action");
			tree.setOnClickHandler(doHandler)
			function doHandler(key){
		    	var id = tree.getSelectedItemId();
		    	var wftype=tree.getUserData(id,"wftype");
				if(wftype==undefined){
					return;
				}
				window.open("${ctx}/noticeFilter/noticeFilterInfo.action?loginName=<%=request.getParameter("loginName")%>&baseSchema="+id+"&rnd="+Math.random(),"rightFrame2");
			}
		</script>
  </body>
</html>