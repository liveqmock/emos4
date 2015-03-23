<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<%
	String str = "";
	String type = request.getParameter("treeType");
	String step_no = request.getParameter("step");
	String action_sign = request.getParameter("act");
	String fieldID = request.getParameter("field");
	if ("Config".equals(type)) {
		str = "&step=" + step_no + "&act=" + action_sign + "&field=" + fieldID;
	}
%>
<FRAMESET COLS="230,1,*" border="0" framespacing="0">
	<FRAME src="<%=request.getContextPath()%>/organizaTree/commonTreeArea.action?treeType=<%=request.getParameter("treeType")%><%=str %>&isSelectType=<%=request.getParameter("isSelectType")%>&approve=<%=request.getParameter("approve")%>&preventTree=<%=request.getParameter("preventTree")%>&utType=<%=request.getParameter("utType")%>&typeMark=<%=request.getParameter("typeMark")%>&depids=<%=request.getParameter("depids")%>&wfVersion=<%=request.getParameter("wfVersion")%>&targetDataArr=<%=request.getParameter("targetDataArr")%>" id="leftTreeFrame" name="leftTreeFrame" scrolling="no" frameborder="0" noresize>
	<FRAME src="<%=request.getContextPath()%>/common/tools/orgtree/centerArea.jsp" name="centerTreeFrame" id="centerTreeFrame" scrolling="no" frameborder="0" noresize>
	<FRAME src="<%=request.getContextPath()%>/organizaTree/commonUserArea.action?treeType=<%=request.getParameter("treeType")%>&approve=<%=request.getParameter("approve")%>" scrolling="auto" id="rightTreeFrame" name="rightTreeFrame" frameborder="0" noresize>
</FRAMESET>
<NOFRAMES>
	<BODY>Your browser dosen't support frames!</BODY>
</NOFRAMES>
</HTML>