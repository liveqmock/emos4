<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<%@page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<%@page import="com.ultrapower.eoms.common.constants.Constants"%>
<%@page import="com.ultrapower.eoms.common.core.util.Internation"%>
<%@page import="java.io.File"%>
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<%
			
	String baseSchema = request.getParameter("baseSchema").replace(":", "_");
	String attachId = StringUtils.checkNullString(request.getParameter("attachId"));
	String processid = StringUtils.checkNullString(request.getParameter("processid"));
	String field = StringUtils.checkNullString(request.getParameter("fieldId"));
	String flagActive = StringUtils.checkNullString(request.getParameter("flagActive"));
	String queryBy = StringUtils.checkNullString(request.getParameter("queryBy"));
	String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
	String uuid = TimeUtils.getCurrentTime() + "";
	String prefix = Constants.WORKSHEET_UPLOAD_PATH;
	String path = null;
	if(prefix != null && !prefix.trim().equals("")) {
		path = prefix + File.separator + baseSchema + File.separator + date;
	} else {
		path = "common" + File.separator + "workflow_attachment" + File.separator + baseSchema + File.separator + date;
	}
	request.setAttribute("uuid", uuid);
	request.setAttribute("attachId", attachId + "_@!_" + processid + "_@!_" + field + "_@!_" + queryBy);
	request.setAttribute("path", path);
	request.setAttribute("flagActive", "1".equals(flagActive) ? "1" : "0");//控制操作
	request.setAttribute("flagActive2", "1".equals(flagActive) ? "true" : "false");//控制是否可以上传
	
	//为附件列表添加标题 RenChenglin
	String attachTitle = StringUtils.checkNullString(request.getParameter("title"));
	if(!"".equals(attachTitle))
		attachTitle = StringUtils.checkNullString(Internation.language(attachTitle));
%>
<html>
<head>
<script type="text/javascript">
window.onload = function() {
	
}

function myfileupload${uuid}deal(count) {
//	alert(count);
	<%
		if(!field.equals("") && !field.equals("<FIELD>")) {
	%>
			parent.window.F(<%=field%>).S(new parent.window.IntegerType(count));
	<%
		}
	%>
}
</script>
<style>
	.choose_files
	{
		background:#f2f5fd;
		height:33px;
		font-size:14px;
		font-weight:bold;
		color:#666;
		border-top:1px solid #E5ECEE;
		border-right:0px;
		border-left:0px;
		border-bottom:1px solid #E5ECEE;
	}
	.choose_files table
	{
		float:right;
		margin:5px;
	}
</style>
</head>
<body style="background-color:white; overflow-y:scroll;">
<atta:fileupload id="myfileupload${uuid}" 
	uploadAction="/wfAttach/upload.action"
	queryAction="/wfAttach/queryDownList.action" 
	deleteAction="/wfAttach/deleteAttachment.action"
	fileTypes="*.*" 
	progressIsVisible="true"
	attchmentGroupId="${attachId}"
	uploadTableVisible="false" 
	isMultiDownload="true"
	isAutoUpload="true" 
	operationParams="0,${flagActive},1" 
	uploadable="${flagActive2}"
	uploadBtnIsVisible="false"
	isEdit="${flagActive}"
	uploadDestination="${path}" 
	isMultiUpload="true" 
	flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf">
</atta:fileupload>

<script type="text/javascript">
	var timerObj;
	<%--设置附件标题--%>
	function setTitle(){
		if(eval('myfileupload${uuid}.initComplete')==true){
			var titlebox = document.createElement('div');
			titlebox.id = 'attachTitle';
			titlebox.style.marginLeft = 15;
			titlebox.style.marginTop = 9;
			var title = '<%=attachTitle%>';
			if(title=='')
				title = '附件列表'
			titlebox.innerText = title + "：";
			document.getElementById('myfileupload${uuid}UploaderComponent').appendChild(titlebox);
			if(timerObj)
				window.clearInterval(timerObj);
		}
	}
	timerObj = window.setInterval(setTitle,500);
</script>
</body>
</html>
