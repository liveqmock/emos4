<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<FRAMESET ROWS="320,2,*" border="0" framespacing="0">
	<FRAMESET COLS="285,2,*" border="0" framespacing="0">
		<FRAME src="<%=request.getContextPath()%>/organizaTree/treeArea.action?treeType=<%=request.getParameter("treeType")%>&isOnly=<%=request.getParameter("isOnly")%>&isSelectType=<%=request.getParameter("isSelectType")%>&isRadio=<%=request.getParameter("isRadio")%>&depids=<%=request.getParameter("depids")%>&targetDataArr=<%=request.getParameter("targetDataArr")%>" id="leftTreeFrame" name="leftTreeFrame" scrolling="no" frameborder="0" noresize>
		<FRAME src="<%=request.getContextPath()%>/common/tools/orgtree/centerArea.jsp" name="centerTreeFrame" id="centerTreeFrame" scrolling="no" frameborder="0" noresize>
		<FRAME src="<%=request.getContextPath()%>/organizaTree/commonUserArea.action?returnPage=userArea&treeType=<%=request.getParameter("treeType")%>" scrolling="auto" id="rightTreeFrame" name="rightTreeFrame" frameborder="0" noresize>
	</FRAMESET>
	<FRAME src="<%=request.getContextPath()%>/common/tools/orgtree/centerArea.jsp" scrolling="no" id="middleTreeFrame" name="middleTreeFrame" frameborder="0" noresize>
	<FRAME src="<%=request.getContextPath()%>/common/tools/orgtree/resultArea.jsp?treeType=<%=request.getParameter("treeType")%>&isRadio=<%=request.getParameter("isRadio")%>" scrolling="auto" id="bottomTreeFrame" name="bottomTreeFrame" frameborder="0" noresize>
</FRAMESET>
<NOFRAMES>
	<BODY>Your browser dosen't support frames!</BODY>
</NOFRAMES>
</HTML>