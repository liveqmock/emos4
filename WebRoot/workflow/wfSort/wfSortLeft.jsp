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
				<table style="height: 100%">
					<tr valign="bottom">
						<td>
							<eoms:lable name="sm_sort_tree" />
						</td>
					</tr>
				</table>
			</div>
			<div  class="add_scroll_div_x" style="overflow:hidden;" id="center"></div>
  	</div>
  	
  	<script type="text/javascript">
			//部门组织树
			tree=new dhtmlXTreeObject("center","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/wfsort/getWfSortTree.action");
			tree.loadXML("${ctx}/wfsort/getWfSortTree.action?id=0");
			tree.enableHighlighting(1);
			tree.setOnClickHandler(doHandler)
			
			//流程分类树上节点点击事件
			function doHandler(key){
				var name = tree.getSelectedItemText();
		    	var id =tree.getSelectedItemId();
				var type = tree.getUserData(id, "sign");
				if(type == 'sort'){
					var src = '${ctx}/wfsort/wfTypeList.action?wfSortId='+id;
				}else if(type == 'type'){
					var src = '${ctx}/wfsort/toEditOrAddWfType.action?wfTypeId='+id;
				}
				//parent.document.getElementById('wfSortRight').src = src;
				window.open(src,'wfSortRight');
			}
			
			
			
			function refresh(itemid)
			{
				tree.refreshItem(itemid);
				//window.open("${ctx}/menuManager/menuInfo.action?menuId="+itemid,"rightFrame2");
			}
			
			//parent.document.getElementById('wfSortRight').src = '<%=request.getContextPath()%>/wfsort/wfTypeList.action?wfSortId=0';
			window.open('<%=request.getContextPath()%>/wfsort/wfTypeList.action?wfSortId=000000000000011','wfSortRight');
		</script>
  </body>
</html>
