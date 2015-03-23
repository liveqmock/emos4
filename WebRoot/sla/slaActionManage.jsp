<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
  <head>
    <title>slaActionManagerFRAMESETpage</title>
    <%@ include file="/common/core/taglibs.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
<FRAMESET  COLS="25%,2,*" border="0" framespacing="0">
    <FRAME src="${ctx}/sla/slaActionManage_left.jsp" name="leftFrame2" scrolling="no" frameborder="0" noresize>
    <FRAME src="${ctx}/sla/slaActionManage_center.jsp" scrolling="auto" name="centerFrame2" frameborder="0" noresize>
    <FRAME src="${ctx}/sla/help.jsp" scrolling="auto" name="rightFrame2" frameborder="0">
</FRAMESET>
<noframes><body>Your browser dosen't support frames!</body></noframes>
</HTML>