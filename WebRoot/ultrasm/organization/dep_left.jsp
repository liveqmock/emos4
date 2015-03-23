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
			setCenter(0,100);
		}
		window.onload = function() 
		{
			setCenter(0,100);
		}
		function transView()
		{
			window.parent.parent.location.href = '${ctx}/depManager/depList.action';
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
		<div class="page_div_bg">
			<div class="page_div">
      			<li class="page_listview_button"
					onmouseover="this.className='page_listview_button_hover'"
					onmouseout="this.className='page_listview_button'" id="show"
					onClick="transView();">
					<eoms:lable name="sm_btn_listView"/>
				</li>
    		</div>
		</div>
		<div class="" id="center" style="padding-left: 8px; padding-top: 8px;">
		
		</div>
  	</div>
 		<script type="text/javascript">
			tree=new dhtmlXTreeObject("center","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/depTree.action");
			tree.loadXML("${ctx}/common/depTree.action?id=0");
			tree.setOnClickHandler(doHandler);
			function doHandler(key){
				var name = tree.getSelectedItemText();
		    	var id = tree.getSelectedItemId();
				window.open("${ctx }/depManager/depLoad_right.action?depID="+id,"rightFrame2");
			}
			function refresh(itemid)
			{
				if(itemid.lastIndexOf(',')==-1)
				{
					tree.refreshItem(itemid);
				}
				else
				{
					var temp = itemid.split(',');
					for(var i=0;i<temp.length;i++)
					{
						tree.refreshItem(temp[i]);
						if(i==temp.length-1)
						{
							window.open("${ctx }/depManager/depLoad_right.action?depID="+temp[i],"rightFrame2");
						}
					}
				}
				
			}
		</script>
  </body>
</html>
