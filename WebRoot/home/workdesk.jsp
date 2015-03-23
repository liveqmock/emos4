<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.constants.PropertiesUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>国家开发银行 - 工作台</title>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		request.setAttribute("basePath", basePath);
	%>
	<script type="text/javascript">
		var basePath = "${basePath}";
	</script>
    <link href="style/css/gkcss.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src='style/js/jquery-1.8.0.min.js'></script>
    <script type="text/javascript" src='style/js/index.js'></script>
    <script type="text/javascript" src='style/js/home.js'></script>
</head>
<body>

<div class="gk_wrap clearfix mt2" style="overflow:hidden;">
	<div class="gk_left">
        <div class="gk_cnt" style="height:192px">
            <h2 class="gk_title">常用功能</h2>
            <ul class="gk_cygn" id="commonFunctionality"></ul>
        </div>
        <div class="gk_cnt mt10">
            <h2 class="gk_title">服务目录</h2>
            <div style="height:405px">
            	<iframe src="${basePath }home/serverQuestTree.jsp" FRAMEBORDER="0" style="width:100%;height:100%;border: 0;"></iframe>
            </div>
        </div>
    </div>
    <div class="gk_right clearfix" style="margin-left:270px;margin-right:260px;">
    	<div class="gk_rightleft" style="width:100%">
            <div class="gk_cnt">
                <h2 class="gk_title">待办工单列表</h2>
                <iframe FRAMEBORDER="3" scrolling="no" style="width:100%;height:340px;overflow:hidden;" src="${basePath}home/waitDeal.action"></iframe>
            </div>
            <div class="gk_cnt mt10" style="height:257px;">
            	<h2 class="gk_title">知识展示
                	<div class="gk_search" style="display: none;"><input type="text" value="服务目录搜索" name="" /><s class="gk_icon14_1"></s></div>
                </h2>
                <div class="gk_zszs" align="center">
                	<iframe frameborder="0" height="196px" width="100%" style="overflow: hidden;" src="<%="http://"+request.getServerName()+":39090/KMBasePlat" %>/kmNewHomePage.do?method=getKmCheckUpListNewHotMore&titleLength=32&hometype=new&num=5&typeID=39&workflow=cdb"></iframe>
                </div>
            </div>
        </div>
    </div>
    <div class="gk_rightlst" style="position:absolute;top:0;right:0;">
    	<div class="gk_cnt" id="gk_cnt" style="height:176px;"></div>
        <div class="gk_cnt mt10">
            <h2 class="gk_title">常用服务目录</h2>
            <ul class="gk_cygn gk_cyfw" style="height:413px" id="commonServiceCategory"></ul>
    	</div>
    </div>
</div>
</body>
</html>
