<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.fulltext.dao.IndexCategoryDao;"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("ctx",basePath);
	//根据父索引类别ID取得其直接子索引类别
	String parentId = request.getParameter("parentId"); //父索引类别ID
	String level = request.getParameter("level");
	response.setContentType("text/plain;charset=UTF-8");
	IndexCategoryDao dao = new IndexCategoryDao();
	response.getWriter().write(dao.getDirectChildCategory(parentId,level));
%>
