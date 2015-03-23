<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ultrapower.eoms.workflow.design.control.*"%>
<%@ page import="com.ultrapower.eoms.workflow.design.model.*"%>
<%@page import="com.ultrapower.workflow.configuration.version.model.WfVersion"%>
<%
String mode = request.getParameter("mode");//new:新建模板；edit:编辑模板
String basecode = request.getParameter("basecode");//类型code：WF:App_Base_bak
String basename = new   String((request.getParameter("basename")!=null?request.getParameter("basename"):"").getBytes( "ISO-8859-1"), "utf-8"); //类型名称：故障工单
String modeltype = request.getParameter("modeltype");//模板大类型
String isSubflow = request.getParameter("isSubflow");//判断子流程显示
if(isSubflow==null)isSubflow = "";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="history/history.css" />
<title>固定流程模板定制</title>
<script src="AC_OETags.js" language="javascript"></script>
<script src="history/history.js" language="javascript"></script>
<style>
body { margin: 0px; overflow:hidden }
</style>
<script language="JavaScript" type="text/javascript">

var requiredMajorVersion = 9;

var requiredMinorVersion = 0;

var requiredRevision = 124;


function save(basestring,wfstring,designstring,roleArray)
{
document.getElementById("wfxml").value = wfstring;
document.getElementById("wfbase").value = basestring;
document.getElementById("wfdesignxml").value = designstring;
document.getElementById("roleCodes").value = roleArray[0];
document.getElementById("roleNames").value = roleArray[1];
document.getElementById("submitForm").submit();
}
</script>
</head>
<body scroll="no" >
<script language="JavaScript" type="text/javascript">

var hasProductInstall = DetectFlashVer(6, 0, 65);
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = window.location;
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
		"width", "100%",
		"height", "100%",
		"align", "middle",
		"id", "WFDesigner",
		"quality", "high",
		"bgcolor", "#869ca7",
		"name", "WFDesigner",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else if (hasRequestedVersion) {
	AC_FL_RunContent(
			"src", "WFDesigner",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "WFDesigner",
			"quality", "high",
			"bgcolor", "#869ca7",
			"name", "WFDesigner",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  } else {  
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
  }
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="WFDesigner" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="WFDesigner.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#869ca7" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="WFDesigner.swf" quality="high" bgcolor="#869ca7"
				width="100%" height="100%" name="WFDesigner" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</noscript>
<input id="custommodestring" name="custommodestring" type="hidden" value='' />
<input id="basecode" name="basecode" type="hidden" value='<%=basecode%>' />
<form id="submitForm" name="submitForm" action="${pageContext.request.contextPath}/wfdesign/saveWfDesign.action" style="display:none;" target="_self" method="post">
    <input id="wfxml" name="wfxml" type="hidden" value="" />
    <input id="wfdesignxml" name="wfdesignxml" type="hidden" value="" />
    <input id="wfbase" name="wfbase" type="hidden" value="" />
    <input id="mode" name="mode" type="hidden" value="<%=mode%>" />
    <input id="roleCodes" name="roleCodes" type="hidden" />
    <input id="roleNames" name="roleNames" type="hidden" />
</form>
</body>
</html>
<script type="text/javascript">
function init(value){
document.getElementById("WFDesigner").flowmapInit('','','','','<%=isSubflow%>');
}
function subFlowMap(flowids,pid)
{
    var url = 'subFlowMapsShow.jsp?baseid=&baseschema=&flowids='+flowids;
	window.open(url, '_blank', 'height=700px,width=900px,toolbar=no,status=no,location=no,directories=no');
}

</script>
