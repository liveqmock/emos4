<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			function changeContent(url)
			{
			 	var c = document.getElementById("content");
				c.src = url;
				//alert(url);
			}
		</script>
	</head>
	<FRAMESET COLS="230,5,*" border="0" framespacing="0" name="ContentFrm" id="mainFrm"><!--
		<FRAME src="${ctx}/common/portal/frame/frame_left.jsp" scrolling="no" name="centerFrame" frameborder="0" noresize>-->
		<FRAME style="border:1px solid #d0d0d0;border-top:none;" src="${ctx}/portal/left.action?menuid=${menuid}" name="leftFrame" scrolling="no" frameborder="0" noresize >
		<FRAME src="${ctx}/common/portal/frame/center.jsp" scrolling="no" name="centerFrame" frameborder="0" noresize>
		<c:choose>
			<c:when test="${menuUrl==''}">
				<FRAME style="border-top:none;" src="#" scrolling="auto" name="rightFrame" frameborder="0" noresize id="content">
			</c:when>
			<c:otherwise>
				<FRAME style="border-top:none;" src="${ctx}/${menuUrl}" scrolling="auto" name="rightFrame" frameborder="0" noresize id="content">
			</c:otherwise>
		</c:choose><!--
		<FRAME src="${ctx}/common/portal/frame/frame_right.jsp" scrolling="no" name="centerFrame" frameborder="0" noresize>
	</FRAMESET>-->
	<noframes>
		<body>
			您的浏览器不支持HTML框架，请更换浏览器
		</body>
	</noframes>
</html>
