<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function()
		{
			setCenter(0,50);
			$('#editTreeNode').css('height',parseInt($('#center').css('height'))-28+"px");
			$('#tree').css('height',parseInt($('#center').css('height'))-28+"px");
		}
		window.onload = function()
		{
			setCenter(0,50);
			addIframe();
			init_open();
			$('#editTreeNode').css('height',parseInt($('#center').css('height'))-28+"px");
			$('#tree').css('height',parseInt($('#center').css('height'))-28+"px");
		}
		
		//第一次打开
		function init_open(){
			window.open("${ctx}/commonTree/editTreeNode.action?pageURL=${pageURL}&&type=${type}&&level=${level}&&id=",'editTreeNode');
		}
	</script>
  </head>
  
  <body>
  	<div class="content"  id='center'>
  		<div class="page_div_bg">业务树</div>
			<table border="0" cellpadding="0" cellspacing="0" width="100%;">
				<tr style="height: 100%;">
					<td width="300px" valign="top"><div class="" id="tree" style="height: 100%;"></div></td>
					<td valign="top" id='frame'></td>
				</tr>
			</table>
  	</div>
<script type="text/javascript">
			//菜单目录树
			var tree = new dhtmlXTreeObject("tree","97%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
				//tree.setSkin('dhx_skyblue');//样式名称
				tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
				tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
				tree.enableTreeLines(true);//是否显示结点间的连线,默认false
				tree.setXMLAutoLoading("${ctx}/commonTree/loadXML.action?type=${type}");
				tree.loadXML("${ctx}/commonTree/loadXML.action?type=${type}");
				//tree.enableHighlighting(1);
				tree.setOnClickHandler(doHandler);
			var id = "";
			var dilever="";
			function doHandler(key)
			{
				var name = tree.getSelectedItemText();
		    	id = tree.getSelectedItemId();
		    	dilever= tree.getUserData(id,"dilever");
		    	if(id != ''){
		    		window.open("${ctx}/commonTree/editTreeNode.action?pageURL=${pageURL}&&type=${type}&&level=${level}&&id=" + id,'editTreeNode');
		    	}
			}
			function refresh(itemid)
			{
				var text = tree.getItemText(itemid);
				if(text != '0'){
					tree.refreshItem(itemid);
				}else{
					tree.refreshItem('0');
				}
				window.open("${ctx}/commonTree/editTreeNode.action?pageURL=${pageURL}&&type=${type}&&level=${level}&&id=" + itemid,'editTreeNode');
			}
			
			function addIframe(){
				$('#frame').append("<iframe frameborder='0' name='editTreeNode' width='100%' id='editTreeNode' />");
			}
		</script>
  </body>
</html>
