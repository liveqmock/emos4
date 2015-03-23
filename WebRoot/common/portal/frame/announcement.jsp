<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.component.rquery.RQuery"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%@page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/core/taglibs.jsp"%>   

<%
RQuery rq = new RQuery("query.BultView.query", new HashMap());
DataTable dt = rq.getDataTable();
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/portal.css" />
<link rel="stylesheet" type="text/css" href="../../style/blue/css/main.css" />
<script src="../../javascript/main.js"></script>
<script language="javascript">
		 function openSheet(baseSchema,baseId,taskid,processType){
		 	document.getElementById('sheetform').action = '${ctx}/sheet/openWaittingSheet.action';
		 	document.getElementById('schema').value = baseSchema ;
		 	document.getElementById('taskid').value = taskid ;
		 	document.getElementById('baseId').value = baseId ;
		 	document.getElementById('processType').value = processType;
		 	document.getElementById('sheetform').submit();
		 }
</script>
<title>announcement</title>
</head>
<body style="overflow:hidden; margin:0px; padding:0px; background-color:white;">
<div class="title_right">
      <div class="title_left">
        <span class="title_bg">
          <span class="title_icon_broadcast">公告</span> 
        </span>
        <span class="title_xieline"></span>
        <span class="title_more"></span>
      </div>
     </div>
<marquee width="100%" height="197" direction="up" scrollamount="2" scrolldelay="90" onmouseover="this.stop();" onmouseout="this.start();" style="background-color:white;">
	<table>
		<tr>
			<td class="notice">
				<div class="info_content">
<%
for(int i = 0; i < dt.length(); i++)
{
	DataRow row = dt.getDataRow(i);
%>
					<div<%if(i > 0){%>style="border-top:1px #4d7ed3 dotted;"<%}%>><a href="javascript:;"  onclick="openSheet('<%=row.getString("baseschema")%>','<%=row.getString("baseid")%>','','')">
						<%=row.getString("BaseSummary")%>
						[<%=TimeUtils.formatIntToDateString(row.getLong("BaseCreateDate"))%>]
					</a></div>
<%
}
%>
				</div>
			</td>
		</tr>
	</table>
</marquee>
<form id="sheetform" action="${ctx}/sheet/openWaittingSheet.action" target="_blank">
	<input type="hidden" name="baseSchema" id="schema"/>			
	<input type="hidden" name="taskId" id="taskid" />
	<input type="hidden" name="baseId" id="baseId" />
	<input type="hidden" name="processType" id="processType" />
	<input type="hidden" name="version" id="version" />
</form>
</body>
</html>