<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.component.rquery.RQuery"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataAdapter"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@page import="com.ultrapower.remedy4j.core.RemedySession"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>

<%
String schema = request.getParameter("schema");

int count = 0;
if(schema != null && !schema.equals(""))
{
	QueryAdapter qa = new QueryAdapter();
	DataTable table = qa.executeQuery("select C1 from " + RemedySession.UtilInfor.getTableName(schema), null);
	RemedySession sess = new RemedySession("Demo", "");
	sess.login();
	if(table != null && table.length() > 0)
	{
		for(int i = 0; i < table.length(); i++)
		{
			DataRow row = table.getDataRow(i);
			sess.deleteEntry(schema, row.getString("C1"));
			count++;
		}
	}
	sess.logout();
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
</head>
<body>
	共删除<%=schema%>数据<%=count%>个
</body>
</html>