<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src='style/js/jquery-1.8.0.min.js'></script>
	<%
	String forwho = request.getParameter("forwho");
	request.setAttribute("forwho", forwho);
	%>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0,0);
		}
		window.onload = function() 
		{
		  setCenter(0,0);
		}
	</script>
  </head>
  
  <body style="margin:0px;">
	<div id="center" style="width: 100%;overflow: hidden;"></div>
		<script type="text/javascript">
			tree=new dhtmlXTreeObject("center","100%","100%",0);
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
			tree.enableCheckBoxes(2);
			tree.enableTreeLines(true);
			var url = "${ctx}/serverQuest/getServiceCategoryTreeXML.action?id=0&time_="+new Date();
			if("${forwho}" != ""){
				url = url + "&forwho=${forwho}";
			}
			tree.loadXML(url);
			tree.setOnClickHandler(doHandler)
			var dilever="";
			function doHandler(key)
			{
				var name = tree.getSelectedItemText();
		    	dilever= tree.getUserData(key,"dilever");
		    	if(dilever=='3'){
		    		var newSheetUrl = "${ctx}/ultrabpp/view.action?mode=NEW&baseSchema=CDB_SERVICEREQUEST&cp.serviceCategory="+encodeURI(encodeURI(tree.getUserData(key,"serverquestfullname")));
		    		if("${forwho}" != ""){//目前只有自助才会是对外的，forwho才有值
		    			newSheetUrl += "&customizedPage=mainSelfHelp&formjsp=formSelfHelp";
					}
		    		window.open(newSheetUrl);
		    	}
			}
			$("#serverQuestTreeBox").children("div").first().css("overflow","hidden");//dhtmlXTree自己生成的div
			
		</script>
  </body>
</html>
