<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/extjs/jsp/taglibs_simple.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>国家开发银行 - 自助服务页</title>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		request.setAttribute("basePath", basePath);
	%>
	<link rel="stylesheet" type="text/css" href="style/css/base.css">
    <link href="style/css/gkcss.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src='style/js/jquery-1.8.0.min.js'></script>
    <script type="text/javascript" src='style/js/index.js'></script>
    <script type="text/javascript" src='style/js/home.js'></script>
    <script type="text/javascript" src='${basePath }serverQuest/js/serverquest.js'></script>
    <script type="text/javascript">
    	var selfService = "selfService";
		var basePath = "${basePath}";
		window.onload = function(){
			searchCategory("div_serverquest","view_serverquest","${userSession.groupDepNames}");//初始化服务目录搜索
			setCenter(0,275);
		}
		window.onresize = function() 
		{
		  setCenter(0,275);
		}
	</script>
</head>
<body>
<body style="overflow-x: hidden;overflow-y: auto;">
<div class="gk_wrap gk_banner">
	<div class="gk_banner01">
        <h3>工作台</h3>
        <p><span>统一标准</span><span>统一展示</span><span>统一标准</span><span>统一展示</span></p>
    </div>
</div>
<div class="gk_wrap clearfix mt2" style="height:100%;overflow:hidden;">
	
	<div class="gk_left">
		<div class="gk_cnt">
			<div class="title_serverQuest">服务目录搜索</div>
			<div class="div_serverquest" id="div_serverquest"></div>
		</div>
        <div class="gk_cnt">
            <div>
            	<div class="title_serverQuest">服务目录</div>
            </div>
            <div class="" style="height:264px">
            	<iframe src="${basePath }home/serverQuestTree.jsp?forwho=1" FRAMEBORDER="0" style="width:100%;height:225px;border: 0;" id=""></iframe>
            </div>
        </div>
        <div class="gk_cnt mt10" style="height: 190px;">
        	<h2 class="gk_title">常用服务目录</h2>
            <ul class="gk_service clearfix" id="commonServiceCategory"></ul>
        </div>
    </div>
    <div class="gk_right" style="margin:0 0 0 270px;">
    	<div class="gk_cnt"  style="overflow: hidden">
    		<h2 class="gk_title">工单进度跟踪</h2>
    		<iframe FRAMEBORDER="0" style="width:100%;height:309px;border: 0;overflow: hidden;" src="${basePath }home/listWaitDeal.action"></iframe>
        </div>
    	<div class="gk_cnt"  style="overflow: hidden">
    		<h2 class="gk_title">历史工单查询</h2>
    		<iframe FRAMEBORDER="0" style="width:100%;height:150px;border: 0;overflow: hidden;" src="${basePath }home/listHistoryServerQuest.jsp"></iframe>
        </div>
    </div>
</div>
</body>
</html>
