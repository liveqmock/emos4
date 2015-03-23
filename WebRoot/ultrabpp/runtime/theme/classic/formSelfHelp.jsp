<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp" %>
<%@include file="../header.jsp" %>
<%@include file="style.jsp" %>
<%@include file="script.jsp" %>
<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
<script type="text/javascript">
ClientContext.cp = eval('(${customParameters})');//将自定义参数转化成json对象
var donotShowKM = "1";//不显示km悬浮窗
var selfHelp = "1";//代表此页面是自助服务页面
window.onresize=function()
{
	if(document.getElementById("kmsearchdiv")!=null&&document.getElementById("kmsearchdiv")!=""){
	var mod1 = document.getElementById("bpp_FormContainer");
	var mod2 = document.getElementById("kmsearchdiv");
	
	mod2.style.left =document.body.clientWidth/2+ mod1.offsetWidth/2+10+ "px";
	}
}
</script>
<title>
	<c:choose>
		<c:when test="${displayCxt.editType=='MODIFY'}">${displayCxt.baseName}[${displayCxt.fieldMap['BASESN']}]</c:when>
		<c:otherwise>新建${displayCxt.baseName}</c:otherwise>
	</c:choose>
</title>
<c:forEach items="${displayCxt.clientFuncList}" var="clientFunc">
	<script type="text/javascript" src="${ctx}/ultrabpp/runtime/forms/${displayCxt.formFolder}/script/${clientFunc}.js"></script>
</c:forEach>
</head>
<body onload="bpp_init();Message.deactivateMask();">
<form id="bpp_WorksheetForm" method="post" action="${ctx}/ultrabpp/form/formAction/save.action">
<div id="bpp_FormContainer">
	<div id="bpp_Header">
		<div id="bpp_BaseStatus"><c:choose><c:when test="${displayCxt.editType=='MODIFY'}">${displayCxt.fieldMap['BASESTATUS']}</c:when><c:otherwise>草稿</c:otherwise></c:choose></div>
		<div id="bpp_BaseName">自助服务工单</div>
		<div id="bpp_BaseSN"><c:choose><c:when test="${displayCxt.editType=='MODIFY'}">[${displayCxt.fieldMap['BASESN']}]</c:when><c:otherwise></c:otherwise></c:choose></div>
		<div id="bpp_ExtendLinks">
			<c:if test="${displayCxt.editType=='MODIFY'}">
				<span id="bpp_FlowmapLink"><a href="javascript:void(0);" onclick="openFlowMap('${displayCxt.baseID}', '${displayCxt.baseSchema}', '${displayCxt.fieldMap['BASEWORKFLOWFLAG']}', '${displayCxt.defCode}', '${displayCxt.fieldMap['BASEENTRYID']}');">流程图</a></span>
				<span id="bpp_PrintLink"><a href="javascript:void(0);" onclick="sheetView('${displayCxt.baseID}', '${displayCxt.baseSchema}', '${displayCxt.defCode}');">打印预览</a></span>
			</c:if>
		</div>
		<div id="bpp_BasicInfor" style="height: 0px;padding-top: 0px;"></div>
	</div>
	<div id="bpp_Body">
		<jsp:include page="/ultrabpp/runtime/forms/${displayCxt.formFolder}/main/${displayCxt.mainPage}.jsp" />
	</div>
	<div id="bpp_BtnPanel" <c:if test="${empty displayCxt.fixActionList && empty displayCxt.freeActionList}">style="display:none;"</c:if>>
		<ul id="bpp_FixBtn" <c:if test="${displayCxt.currentTask.flagPreDefined == 0 || fn:length(displayCxt.fixActionList) == 0}">style="display:none;"</c:if>>
			<c:if test="${displayCxt.fieldMap['BASESTATUS'] == '新建' || displayCxt.fieldMap['BASESTATUS'] == '草稿'}">
				<LI><INPUT id=bpp_Btn_SAVE value="保存 " type="button"></LI>
				<SCRIPT type=text/javascript>
					ClientContext.addFixAction("SAVE", "SAVE", "保存", 0, 0, 0, "保存所填信息方可进行下一步操作");
				</SCRIPT>
			</c:if>
			<c:if test="${displayCxt.fieldMap['BASESTATUS'] == '新建' || displayCxt.fieldMap['BASESTATUS'] == '草稿'}">
				<LI><INPUT id=bpp_Btn_ToRequest value="提交" type="button"></LI>
				<SCRIPT type=text/javascript>
					ClientContext.addFixAction("ToRequest", "NEXT", "提交", 0, 1, 1, "将填写的服务请求信息提交给相应处理组");
				</SCRIPT>
				<LI><INPUT id=bpp_Btn_z_ToCancelFromRequest value="作废" type="button"></LI>
				<%--隐藏起来的作废按钮是真正执行作废功能的按钮，上面的作废按钮只是为了提示信息 --%>
				<LI style="display: none;"><INPUT id=bpp_Btn_ToCancelFromRequest value="作废 " type="button"></LI>
				<SCRIPT type=text/javascript>
					ClientContext.addFixAction("ToCancelFromRequest", "CANCEL", "作废", 0, 0, 1, "用户误建或不需要继续提交的工单，点击作废后，此工单不能在系统中流转");
				</SCRIPT>
			</c:if>
			<c:if test="${displayCxt.fieldMap['BASESTATUS'] == '待关闭'}">
				<LI><INPUT id=bpp_Btn_ToCloseFromDeal value="关单 " type="button"></LI>
				<SCRIPT type=text/javascript>
					ClientContext.addFixAction("ToCloseFromDeal", "NEXT", "关单", 0, 1, 1, "确认问题已经解决，点击关单后，此工单不能重新在系统中流转");
				</SCRIPT>
			</c:if>
		</ul>
	</div>
	<%--下面这个动作框要隐藏起来，不给用户看 --%>
	<div id="bpp_ActPanel_warp" style="display: none;">
	<div id="bpp_ActPanel">
		<div style="height:5px;"></div>
		<div id="bpp_ActComment"></div>
		<div id="bpp_ActBody">
			<div id="bpp_ActFields"></div>
			<div class="bpp_White_Block"></div>
			<div id="bpp_ActPanel_BtnPanel">
				<input type="button" value="提　交" onclick="ActionPanel.submit()" />
				<input type="button" value="取　消" onclick="ActionPanel.goBack()" />
			</div>
		</div>
	</div>
	</div>
	<div id="bpp_SystemPanel">
		<%@include file="../system.jsp" %>
	</div>
	<div id="bpp_HiddenPanel">
		<jsp:include page="/ultrabpp/runtime/forms/${displayCxt.formFolder}/main/hidden.jsp" />
	</div>
</div>
</form>
<form id="kmform" name="kmform" method="post" action="${ctx}/relatekm/kmRedirect.action"  target="_blank">
	 <input type="hidden" id="kmcaption" name="kmcaption" value=""/>
	 <input type="hidden" id="kmkeyword" name="kmkeyword" value=""/>
	 <input type="hidden" id="kmcategory" name="kmcategory" value=""/>
	 <input type="hidden" id="kmcontent" name="kmcontent" value=""/>
	 <input type="hidden" id="kmbaseSchema" name="kmbaseSchema" value="${displayCxt.baseSchema}"/>
	 <input type="hidden" id="kmbaseID" name="kmbaseID" value="${displayCxt.baseID}"/>
</form>
</body>
<script type="text/javascript">
	Message.activateMask();
	KMHandler.getTiketInfoToKm('${displayCxt.baseSchema}','${displayCxt.baseID}',function(data){
		$.each(data,function(i,item){
			$("#kmcaption").val(item.BASESUMMARY);
			
		});
	});
</script>
</html>
<ubf:Preview></ubf:Preview>