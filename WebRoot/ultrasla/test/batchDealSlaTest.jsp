<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.component.data.*" %>
<%@ page import="com.ultrapower.eoms.ultrasla.service.*" %>
<%@ page import="com.ultrapower.eoms.ultrasla.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
<%
	String sql = "select * from bs_t_usla_eventtemp";
	QueryAdapter queryAdapter = new QueryAdapter();
	DataTable table = queryAdapter.executeQuery(sql, null);
	int tableLen = 0;
	if(table != null) {
		tableLen = table.length();
	}
	DataRow row;

	org.springframework.web.context.WebApplicationContext webctx = 
    		org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(application);
    IEventRuleTrigger eventRuleTrigger = (IEventRuleTrigger)webctx.getBean("eventRuleTrigger");
	for (int i=0 ; i<tableLen ; i++) {
		List<String> ruleList = new ArrayList<String> ();
	List<String> userList = new ArrayList<String> ();
	List<String> depList = new ArrayList<String> ();
	List<String> roleList = new ArrayList<String> ();
		row = table.getDataRow(i);
		String[] ruleid = row.getString("ruleids").split(",");
		for(int j=0 ; j<ruleid.length ; j++) {
			if(!"".equals(ruleid[j]))
				ruleList.add(ruleid[j]);
		}
		String[] userid = row.getString("defaultuser").split(",");
		for(int j=0 ; j<userid.length ; j++) {
			if(!"".equals(userid[j]))
				userList.add(userid[j]);
		}
		String[] depid = row.getString("defaultgroup").split(",");
		for(int j=0 ; j<depid.length ; j++) {
			if(!"".equals(depid[j]))
				depList.add(depid[j]);
		}
		String[] roleid = row.getString("defaultrole").split(",");
		for(int j=0 ; j<roleid.length ; j++) {
			if(!"".equals(roleid[j]))
				roleList.add(roleid[j]);
		}
		eventRuleTrigger.produce(row.getString("eventid"), ruleList, row.getLong("duetime"), userList, depList, roleList, ParamResolve.xmlThansToMap(row.getString("eventparamxml")));
	}
%>
</body>
</html>
