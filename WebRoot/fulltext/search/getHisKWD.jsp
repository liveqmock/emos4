<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.fulltext.dao.HistoryKWDDAO;"%>

<%
	request.setCharacterEncoding("UTF-8");
	String kwd = request.getParameter("kwd");
	List<String> results = null;
	if(kwd!=null && !"".equals(kwd))
	{
		results = HistoryKWDDAO.getHistory(kwd);
	}
	StringBuffer outstr = new StringBuffer();
	String returnvalue = "";
	if(results!=null)
	{
		int len = results.size();
		for(int i=0;i<len;i++)
		{
			outstr.append(",");
			outstr.append(results.get(i));
		}
		if(len>0)
			returnvalue = outstr.substring(1);
	}
	response.setContentType("text/plain;charset=utf-8");
	response.getWriter().print(returnvalue.trim());
%>
