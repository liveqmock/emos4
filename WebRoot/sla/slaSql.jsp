<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.component.rquery.*" %>
<%@ page import="com.ultrapower.eoms.common.core.component.sla.monitor.* " %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>SLA SQL preview</title>
	<%@ include file="/common/core/taglibs.jsp"%>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body><br>
    <% 
   // com.ultrapower.eoms.common.core.component.sla.monitor.SmActionRuleJob sm=new com.ultrapower.eoms.common.core.component.sla.monitor.SmActionRuleJob(); 
    //sm.execute(); 
    String tplid=request.getParameter("tplid");
    String actionid=request.getParameter("actionid");
    //String actionid="402809f930b5f13c0130b63cc41e0005"; 
    SmsActionDeal rule=new SmsActionDeal();  
    RQuery rQuery=rule.getRQuery(actionid,tplid,0);
     
     
     %>
     <div style=" align:center;width:90%;height:90%" >
		<textarea rows="" cols="" style="align:center;width:100%;height:100%">
	     		<%
	     		if(rQuery!=null)
	     		{
	     			out.print(rQuery.getSqlString());
	     		}
	     		
	     		
	     		%>
	     </textarea>     
     </div>

     
  </body>
</html>
