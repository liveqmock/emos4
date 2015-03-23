<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ultrapower.eoms.workflow.design.control.*"%>
<%@ page import="com.ultrapower.eoms.workflow.design.model.*"%>
<%@page import="com.ultrapower.eoms.workflow.flowmap.model.BaseModel"%>
<%@page import="com.ultrapower.eoms.workflow.flowmap.control.BaseManage"%>
<%@page import="com.ultrapower.workflow.configuration.version.model.WfVersion"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%@page import="org.apache.struts2.components.Else"%> 
<%@page import="com.ultrapower.workflow.bizconfig.sort.IWfSortManager"%> 
<%@page import="com.ultrapower.workflow.client.WorkFlowServiceClient"%> 
<%@page import="com.ultrapower.eoms.common.core.util.StringUtils"%> 

<%
String mode = request.getParameter("mode");//new:新建模板；edit:编辑模板
String modeltype = request.getParameter("modeltype");//模板大类型
String maptype = request.getParameter("type");
String isSubflow = request.getParameter("isSubflow");//判断子流程显示
String tplid = request.getParameter("tplid");
String flowid = request.getParameter("flowid");
String baseid = request.getParameter("baseid");
String baseschema = request.getParameter("baseschema");
String entryid = request.getParameter("entryid");
String entrycount = request.getParameter("entryCount");//流程实例个数，用于流程图编辑保存时判断是否需要另存为新版本

if(entryid != null) {
	QueryAdapter con  = new QueryAdapter();
	String sql = "select topentryid from bs_t_wf_entry t where t.id='"+entryid+"'";
	DataTable rs = con.executeQuery(sql, null, 0);
	if(rs != null && rs.length() > 0) {
		DataRow dRow = rs.getDataRow(0);
		String topentryid = dRow.getString("topentryid");
		if(topentryid != null && topentryid.trim().length() > 0) {
			sql = "select id,defname from bs_t_wf_entry t where t.id='"+topentryid+"'";
			rs = con.executeQuery(sql, null, 0);
			if(rs != null && rs.length() > 0) {
				dRow = rs.getDataRow(0);
				entryid = dRow.getString("id");
				tplid = dRow.getString("defname");
			}
		}
	}
}

isSubflow = StringUtils.checkNullString(isSubflow);
mode = StringUtils.checkNullString(mode);
maptype = StringUtils.checkNullString(maptype);
tplid = StringUtils.checkNullString(tplid);
flowid = StringUtils.checkNullString(flowid);
entryid = StringUtils.checkNullString(entryid);

if(flowid.length() > 0 && maptype.equals("tpl")) {
	QueryAdapter con  = new QueryAdapter();
	String[] flowidAndEntryid = flowid.split("_");
	String sql = "select defname from bs_t_wf_entry t where t.id='"+flowidAndEntryid[1]+"'";
	DataTable rs = con.executeQuery(sql, null, 0);
	DataRow dRow = rs.getDataRow(0);
	tplid = dRow.getString("defname");
} 

//自定义模型
CustomModelManager cusManager = new CustomModelManager();

cusManager.setServletContext(this.getServletContext());

String customModelString = cusManager.getCustomModelString(modeltype,baseschema);

List<CustomModel> customList = cusManager.getModelList();
if(customModelString != null) {
	customModelString = customModelString.replaceAll("\r","").replaceAll("\n","");
}

//获取固定流程的流程定义和流程设计文件
String wfxml = "";
String designxml = "";
DesignManager designManager = null;
BaseMapModel baseMapModel = null;
if(!mode.equals("new") && !maptype.equals("free")) {
	designManager = new DesignManager(tplid,baseschema);
	designManager.init();
	baseMapModel = designManager.getBaseMapModel();
	wfxml = designManager.getWfVersion().getWorkflowXml().replaceAll("\r","").replaceAll("\n","").replace("'","&#039;");
	designxml = designManager.getWfVersion().getDesignXml().replaceAll("\r","").replaceAll("\n",""); 
	
	System.out.println("wfxml='"+wfxml+"';");
	System.out.println("designxml='"+designxml+"';");
}

//获取子流程列表用于流程定义时进行选择
String basename="";
List<WfVersion> subFlowList = new ArrayList<WfVersion>();
if(mode.equals("edit")) {
    subFlowList = designManager.getWfList();
} else if(mode.equals("new")){
    IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
    basename = sortService.getWfTypeByCode(baseschema).getName();
	TplDesignManager tpldesign= new TplDesignManager(baseschema);
	subFlowList = tpldesign.getSubFlowWfListByBaseCode();
}

//环节状态列表
FlowMapConfig flowmapconfig = new FlowMapConfig();
String statusXmlString = flowmapconfig.getStatusXmlString();

System.out.println("statusXml='"+statusXmlString+"'");

//mode == 'map'
BaseModel baseinforModel = null;
String title = "";
String processinfoXmlString = "";
String drawLine  = "";
if(mode.equals("map")){
	DealProcessManager dealprocess = new DealProcessManager();
	List<ProcessInfo> processinfolist = dealprocess.buildProcessList(baseid,baseschema,flowid,maptype, tplid, entryid);		
	processinfoXmlString = dealprocess.buildProcessXML(processinfolist);
	processinfoXmlString = processinfoXmlString.replaceAll("\r","").replaceAll("\n","").replaceAll("'","&#039;");
	baseinforModel = new BaseManage().getOneModel(baseid, baseschema, "","");
	title = baseinforModel.getBaseName()+"["+baseinforModel.getBaseSN()+"]";
	System.out.println("processinfoXml='"+processinfoXmlString+"';");
	drawLine = dealprocess.drawProcessLine(baseid,baseschema,flowid);	
   	System.out.println("drawLine="+drawLine);
} else {
	title = "流程模板定制器";
} 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="history/history.css" />
<title><%=title%></title>
<script src="AC_OETags.js" language="javascript"></script>
<script src="history/history.js" language="javascript"></script>
<style>
body { margin: 0px; overflow:hidden }
</style>
<script language="JavaScript" type="text/javascript">

var requiredMajorVersion = 9;

var requiredMinorVersion = 0;

var requiredRevision = 124;


function save(basestring,wfstring,designstring,roleArray,asNew) {
	document.getElementById("wfxml").value = '<?xml version="1.0" encoding="UTF-8"?>'+wfstring;
	document.getElementById("wfbase").value = basestring;
	document.getElementById("wfdesignxml").value = '<?xml version="1.0" encoding="UTF-8"?>'+designstring;
	document.getElementById("roleCodes").value = roleArray[0];
	document.getElementById("roleNames").value = roleArray[1];
	document.getElementById("processNos").value = roleArray[2];
	document.getElementById("asNew").value = asNew;
	document.getElementById("submitForm").submit();
}

function mouseleave() {
	document.getElementById("WFDesigner");	
}
</script>
</head>
<body scroll="no" onmouseout="mouseleave()" >
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
<form id="submitForm" name="submitForm" action="${pageContext.request.contextPath}/wfdesign/saveWfDesign.action" style="display:none;" target="_self" method="post">
    <input id="wfxml" name="wfxml" type="hidden" value="" />
    <input id="wfdesignxml" name="wfdesignxml" type="hidden" value="" />
    <input id="wfbase" name="wfbase" type="hidden" value="" />
    <input id="mode" name="mode" type="hidden" value="<%=mode%>" />
    <input id="roleCodes" name="roleCodes" type="hidden" />
    <input id="roleNames" name="roleNames" type="hidden" />
    <input id="processNos" name="processNos" type="hidden" />
    <input id="asNew" name="asNew" type="hidden" />
</form>
</body>
</html>
<script type="text/javascript">
function init(value){

	<%
		if(mode.equals("new")){
	%>		
	
			var subArray = getSubArray();
			var modelArray = getModelArray();
			var statusBarArray = [{statusid:"s_1",content:"草稿",index:0,length:300,resize:true}
				      			 ,{statusid:"s_2",content:"处理中",index:1,length:400,resize:true}
				      			 ,{statusid:"s_3",content:"审批中",index:2,length:300,resize:true}
				      			 ,{statusid:"s_5",content:"已完成",  index:3,length:300,resize:true}
				      			 ,{statusid:"s_4",content:"已关闭",index:4,length:300,resize:true}];
			document.getElementById("WFDesigner").newInit(modelArray,statusBarArray,'<%=customModelString%>','<%=baseschema%>','<%=basename%>',subArray);
			
	<%
		} else if(mode.equals("edit")) {
	%>
	
			var subArray = getSubArray();
			var modelArray = getModelArray();
			var baseDataModel = [{id:'<%=baseMapModel.getId()%>',basecode:'<%=baseMapModel.getBasecode()%>',basename :'<%=baseMapModel.getBasename()%>',code:'<%=baseMapModel.getCode()%>',name:'<%=baseMapModel.getName()%>',active:'<%=baseMapModel.getActive()%>',startdate:'<%=baseMapModel.getStartdate()%>',createdate:'<%=baseMapModel.getCreatedate()%>',desc:'<%=baseMapModel.getDesc()%>',subflow:'<%=baseMapModel.getSubflow()%>'}];
			document.getElementById("WFDesigner").editInit(modelArray,'<%=customModelString%>',baseDataModel,subArray,'<%=wfxml%>','<%=designxml%>','<%=entrycount%>');
			
	<%
		} else if(mode.equals("map") && !maptype.equals("free")) {
	%>
			document.getElementById("WFDesigner").mapInit('<%=designxml%>','<%=processinfoXmlString%>','<%=statusXmlString%>','<%=baseinforModel.getBaseName()%>','<%=baseinforModel.getBaseSN()%>','<%=drawLine%>','<%=wfxml%>');
	
	<%
		} else {
	%>
			document.getElementById("WFDesigner").flowmapInit('<%=statusXmlString%>','<%=processinfoXmlString%>','<%=baseinforModel.getBaseName()%>','<%=baseinforModel.getBaseSN()%>','<%=isSubflow%>');
			
	<%
		} 
	%>
}

function getModelArray() {
	var modelArray = [{label:"开始",icon:"design/images/start.png",type:"BEGIN",modelkey:""}];
	<%
	for(int i=0; i < customList.size(); i++){
		CustomModel customModel = customList.get(i);
	%>
		modelArray.push({label:"环节",icon:"design/images/step.png",type:"STEP",modelkey:"<%=customModel.getModelkey()%>"});
	<%
	}
	%>
	modelArray.push({label:"分支",icon:"design/images/split.png",type:"SPLIT",modelkey:""},
			 		{label:"合并",icon:"design/images/join.png",type:"JOIN",modelkey:""},
			 		{label:"结束",icon:"design/images/end.png",type:"END",modelkey:""});
	return modelArray;
}

function getSubArray() {
	var subwf = new Array();
	<%//封装允许选择的子流程列表
	for(int i=0;i<subFlowList.size();i++){
		WfVersion subwf = subFlowList.get(i);
	%>
		subwf.push({label:'<%=subwf.getName()%>',data:'<%=subwf.getCode()%>'});
	<%}%>
	return subwf;
}

function subFlowMap(flowids,pid) {
    var url = 'subFlowMapsShow.jsp?baseid=<%=baseid%>&baseschema=<%=baseschema%>&flowids='+flowids;
	window.open(url, '_blank', 'height=700px,width=900px,toolbar=no,status=no,location=no,directories=no');
}


if(parent.hideLoadingDiv) {
	parent.hideLoadingDiv();
}
</script>
