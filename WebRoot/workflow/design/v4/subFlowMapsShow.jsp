<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%@page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>

<%
String baseid = request.getParameter("baseid");
String baseschema = request.getParameter("baseschema");
String flowids = request.getParameter("flowids");
String[] idArr = flowids.split(",");
String entryidsString="";
List<String> dpDescList = new ArrayList<String>();

for(int i=0; i < idArr.length; i++){
	String flowid = idArr[i];
	QueryAdapter qa = new QueryAdapter();
	String sql1 = "select stdate,dpdesc from bs_t_wf_dealprocess where flowid='"+flowid+"'";
	DataTable rst = qa.executeQuery(sql1, null, 0);
	DataRow drt = rst.getDataRow(0);
	long stdate = drt.getLong("stdate");
	String dpdesc = drt.getString("dpdesc");
	String sql = "select entryid,flagpredefined from bs_t_wf_dealprocess t where baseid='"+baseid+"' and baseschema = '"+baseschema+"' and parentflowid='"+flowid+"' order by stdate desc";
	DataTable rs = qa.executeQuery(sql, null, 0);
	for(int j=0;j<rs.length();j++){
		DataRow dr = rs.getDataRow(j);
		String entryid = dr.getString("entryid");
		long flagpredefined = dr.getLong("flagpredefined");
	    String type = (flagpredefined == 1 ? "tpl" : "free");
		if(entryidsString.length() == 0) {
			entryidsString = flowid + "_" + entryid + "-" + type + "-" + stdate;
			dpDescList.add(dpdesc);
		} else if(!entryidsString.contains(entryid)) {
			entryidsString += "," + flowid + "_" + entryid + "-" + type + "-" + stdate;
			dpDescList.add(dpdesc);
		}
	}
}
String[] entryids = entryidsString.split(",");

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分发流程列表</title>
<style type="text/css">
.titlestyle
{
	height:30px;
	padding:5px 0px 5px 10px;
	font-size:12px;
	font-weight:bold;
	border:solid #EEEEEE 1px;
	color:#FFFFFF;
	background-color:#339cde;
	cursor:pointer;
	filter:progid:DXImageTransform.Microsoft.Gradient(startColorStr='#339cde', endColorStr='#0a6ead', gradientType='0');
}
</style>
</head>
<script type="text/javascript">
var activeID = '';
function clickItem(id)
{
	if(activeID != '')
	{
		document.getElementById('c' + activeID).style.display = 'none';
		document.getElementById('p' + activeID).src = 'images/List_close.gif';
	}
	if(activeID != id)
	{
		document.getElementById('c' + id).style.display = 'block';
		document.getElementById('p' + id).src = 'images/List_open.gif';
		activeID = id;	
		var srcString = document.getElementById('i' + id).src;
		if(document.getElementById('i' + id).src == '' || srcString.indexOf('subFlowMapsShow.jsp')>-1)
		{
			showLoadingDiv();
			var idarray = id.split("-");
			document.getElementById('i' + id).src = "WFDesigner.jsp?mode=map&baseid=<%=baseid%>&baseschema=<%=baseschema%>&flowid=" + idarray[0]+"&type="+idarray[1]+"&isSubflow=true";
		}
		setIframHeightFree(id, 500);
	}
	else
	{
		activeID = '';
	}
}

function setIframHeightFree(id, size)
{   
    var height = size + 21;
    document.getElementById('c' + id).style.height = height;
	document.getElementById('i' + id).style.height = height;
}
function showLoadingDiv()
{
	document.getElementById('loadingDIV').style.top = document.body.scrollTop;
	document.getElementById('loadingDIV').style.display = 'block';
}
function hideLoadingDiv()
{
	document.getElementById('loadingDIV').style.display = 'none';
}
</script>
<body style="margin:0px; padding:0px;" scroll="yes">
<div id="loadingDIV" style="display:none; position:absolute; top:1px; left:1px; height:20px; width:120px; font-size:12px; padding:5px; background-color:#ffffaa; border:#666666 solid 0px; z-index:200;">
	 <img src="images/progress_loading.gif" style="margin-bottom:-4px;"></img>数据读取中……
</div>
<%
String dpTable = "";//rdbo.GetRemedyTableName("WF:App_DealProcess");
String apTable = "";//rdbo.GetRemedyTableName("WF:App_DealAssistantProcess");
for(int i = 0; i < entryids.length; i++) {
	String desc = "";
	long closeTime = 0l;
	boolean isFirst = true;
	boolean isClose = false;
	String[] infos = entryids[i].split("-");
	desc += "&nbsp;<font style=\"color:#FF0000;\">"+entryids[i]+"</font>";
	if(isClose) {
		//desc += "&nbsp;<font style=\"color:#00FF00;\">（已完成，完成时间：" + "" + "）</font>";//FormatTime.formatIntToDateString(closeTime) + "）</font>";
	} else {
		//desc += "&nbsp;<font style=\"color:#FF0000;\">（未完成）</font>";
	}
%>
	<div id="t<%=entryids[i]%>" class="titlestyle">
		<img id="p<%=entryids[i]%>" src="images/List_close.gif" style="margin-right:3px;" />
		<span onclick="clickItem('<%=entryids[i]%>')" onmouseover="this.parentElement.style.paddingTop=6" onmouseout="this.parentElement.style.paddingTop=5"><%=(dpDescList.get(i)+"["+TimeUtils.formatIntToDateString(Long.parseLong(infos[2]))+"]")%></span>
	</div>
	<div id="c<%=entryids[i]%>" style="display:none;">
		<iframe id="i<%=entryids[i]%>" width="100%" style="background-color:#EEEEEE; height:0px;" src=""></iframe>
	</div>
<%
}
%>
</body>
<script type="text/javascript">
	clickItem('<%=entryids[0]%>');
</script>
</html>