<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<FRAMESET  COLS="25%,2,*" border="0" framespacing="0">
    <FRAME style="border-left:1px solid #d0d0d0;;" src="<%=request.getContextPath()%>/wfsort/viewLeftTree.action" name="wfSortLeft" id="wfSortLeft" scrolling="no" frameborder="0" noresize>
    <FRAME src="<%=request.getContextPath()%>/ultrasm/organization/userDepRelation_center.jsp" scrolling="auto" name="centerFrame2" frameborder="0" noresize>
    <FRAME scrolling="no" name="wfSortRight" frameborder="0">
</FRAMESET>
<noframes><body>Your browser dosen't support frames!</body></noframes>
</HTML>