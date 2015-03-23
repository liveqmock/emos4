<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<FRAMESET ROWS="320,1,*" border="0" framespacing="0">
	<FRAME src="<%=request.getContextPath()%>/common/tools/orgtreev2/treeAreaOrganiza.jsp?isRadio=<%=request.getParameter("isRadio")%>&findType=<%=request.getParameter("findType")%>&sltType=<%=request.getParameter("sltType")%>&sltData=<%=request.getParameter("sltData")%>" id="leftTreeFrame" name="leftTreeFrame" scrolling="no" frameborder="0" noresize>
	<FRAME src="<%=request.getContextPath()%>/common/tools/orgtreev2/centerArea.jsp" scrolling="no" id="middleTreeFrame" name="middleTreeFrame" frameborder="0" noresize>
	<FRAME src="<%=request.getContextPath()%>/common/tools/orgtreev2/resultArea.jsp?isRadio=<%=request.getParameter("isRadio")%>" scrolling="auto" id="bottomTreeFrame" name="bottomTreeFrame" frameborder="0" noresize>
</FRAMESET>
<NOFRAMES>
	<BODY>Your browser dosen't support frames!</BODY>
</NOFRAMES>
</HTML>