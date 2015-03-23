<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,60);
		}
		window.onload = function()
		{
			setCenter(0,60);
		}
		function submit()
		{
			var dtcode = document.getElementById("dtcode").value;
			if(dtcode==''){
				alert("没有选中任何字典类型。");
				return false;
			}
			if(opener.document.getElementById("selectField.typeResource")!=undefined){
				opener.document.getElementById("selectField.typeResource").value =  document.getElementById("dtcode").value;
				opener.document.getElementById("selectField.paras").value =  document.getElementById("dtfullname").value;
			}else{
				opener.document.getElementById("collectField.typeResource").value =  document.getElementById("dtcode").value;
				opener.document.getElementById("collectField.paras").value =  document.getElementById("dtfullname").value;
			}
			window.close();
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
			tree.loadXML("${ctx}/common/dicTree.action?id=0&dictype=4");
			//tree.enableHighlighting(1);
			tree.setOnClickHandler(doHandler)
			function doHandler(key)
			{
				var name = tree.getSelectedItemText();
		    	var id = tree.getSelectedItemId();
		    	 $.get("${ctx}/ultrabpp/select/getDicInfo.action?stamp="+new Date().getTime(),{'dicId':id},function(result)
					{
						var dic = result.split(":");
						document.getElementById("dtcode").value = dic[0];
						document.getElementById("dtfullname").value = dic[1];
					})
			}
		</script>
		<div class="add_bottom">
			 <input type="hidden" id="dtcode" name="dtcode" value=""/>
		     <input type="hidden" id="dtfullname" name="dtfullname" value=""/>
			<input type="button" value="确定" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
  </body>
</html>
