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
		
		function transView()
		{
			window.parent.parent.location.href('${ctx}/slaActionManager/slaActionOperationList.action');
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
						<eoms:lable name="sm_btn_listLook"/>
					</li>
	    		</div>
		</div>
		<div class="scroll_div" style="overflow:hidden;" id="center"></div>
  	</div>
  		<script type="text/javascript">
			/*
				节点第一层为动作分类：返回节点ID为数据库PID+@TYPE@，如001@TYPE@
				节点第二层为业务：返回节点ID为数据库PID+@TRANS@，如002@TRANS@
				节点第三层为规则：返回节点ID为数据库PID+@RULE@，如003@RULE@
			*/
			tree=new dhtmlXTreeObject("center","100%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/slaActionManager/getActionTree.action");
			tree.setOnClickHandler(doHandler);
			tree.loadXML("${ctx}/slaActionManager/getActionTree.action?id=0");
			
			//获得数据库中的PID
			function getRealId(srcId)
			{
				var regType = /^.{1,}@TYPE@$/;
				var regTrans = /^.{1,}@TRANS@$/;
				var regRule = /^.{1,}@RULE@$/;
				var resultArr = new Array();
				if(regType.test(srcId))
				{
					return (srcId.substring(0,srcId.length-"@TYPE@".length))+"&comm;@TYPE@";
				}
				if(regTrans.test(srcId))
				{
					return (srcId.substring(0,srcId.length-"@TRANS@".length))+"&comm;@TRANS@";
				}
				if(regRule.test(srcId))
				{
					return (srcId.substring(0,srcId.length-"@RULE@".length))+"&comm;@RULE@";
				}
			}
			function doHandler(key)
			{
		    	var result = getRealId(tree.getSelectedItemId());
		    	var id = result.split("&comm;")[0];
		    	var branch = result.split("&comm;")[1];
		    	if("@TYPE@"==branch)
		    	{//调用添加业务界面
		    		if(id=="smNotice")
		    		{//调用添加短信业务界面
		    			window.open("${ctx}/slaActionManager/smTransLoad.action?nodeId="+id,"rightFrame2");
		    		}
		    		if(id=="emailNotice")
		    		{//调用添加邮件业务界面
		    			window.open("${ctx }/slaMail/emailTransSave.action?nodeId="+id,"rightFrame2");
		    		}
		    	}
		    	if("@TRANS@"==branch)
		    	{//调用业务界面
		    		var parentId = tree.getParentId(tree.getSelectedItemId());
		    		var presult = getRealId(parentId);
		    		var pid = presult.split("&comm;")[0];
		    		if(pid=="smNotice")
		    		{//调用短信业务界面
		    			window.open("${ctx}/slaActionManager/smTransLoad.action?id="+id+"&nodeId="+pid,"rightFrame2");
		    		}
		    		if(pid=="emailNotice")
		    		{//调用邮件业务界面
		    			window.open("${ctx }/slaMail/emailTransSave.action?id="+id+"&nodeId="+pid,"rightFrame2");
		    		}
		    	}
		    	if("@RULE@"==branch)
		    	{//调用规则界面
		    		var modelId = tree.getUserData(tree.getSelectedItemId(),"modelId");//模板ID
		    		var actionType = tree.getUserData(tree.getSelectedItemId(),"actionType");//动作分类类型（邮件或短信...）
		    		if(modelId != undefined && actionType != undefined)
		    		{
		    			window.open("${ctx }/slaActionManager/loadRule.action?ruleId="+id+"&modelId="+modelId+"&actionType="+actionType,"rightFrame2");
		    		}
		    	}
			}
			function refreshTrans(refreshId)
	    	{//刷新业务
	    		try
	    		{
	    			if(refreshId=="0")
		    		{
		    			tree.refreshItem(refreshId);	
		    		}
		    		else
		    		{
		    			var itemid = refreshId + "@TYPE@";
		    			tree.refreshItem("0");
		    			//alert(itemid);
		    			tree.refreshItem(itemid);
		    		}
	    		}
	    		catch(err)
	    		{
	    			window.location.reload();
	    		}
	    	}
	    	function refreshRule(refreshId, actionType)
	    	{//刷新规则
	    		try
	    		{
	    			if(refreshId=="0")
		    		{
		    			tree.refreshItem(refreshId);
		    		}
		    		else
		    		{
		    			var itemid = refreshId + "@TRANS@";
		    			tree.refreshItem("0");
		    			//alert(actionType+"@TYPE@");
		    			tree.refreshItem(actionType+"@TYPE@");
		    			//alert(itemid);
		    			tree.refreshItem(itemid);
		    		}
	    		}catch(err)
	    		{
	    			window.location.reload();
	    		}
	    	}
		</script>
  </body>
</html>
