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
		<div id="bpp_BaseName">${displayCxt.baseName}</div>
		<div id="bpp_BaseSN"><c:choose><c:when test="${displayCxt.editType=='MODIFY'}">[${displayCxt.fieldMap['BASESN']}]</c:when><c:otherwise></c:otherwise></c:choose></div>
		<div id="bpp_ExtendLinks">
			<c:if test="${displayCxt.editType=='MODIFY'}">
				<span id="bpp_FlowmapLink"><a href="javascript:void(0);" onclick="openFlowMap('${displayCxt.baseID}', '${displayCxt.baseSchema}', '${displayCxt.fieldMap['BASEWORKFLOWFLAG']}', '${displayCxt.defCode}', '${displayCxt.fieldMap['BASEENTRYID']}');">流程图</a></span>
				<span id="bpp_PrintLink"><a href="javascript:void(0);" onclick="sheetView('${displayCxt.baseID}', '${displayCxt.baseSchema}', '${displayCxt.defCode}');">打印预览</a></span>
			</c:if>
		</div>
		<div id="bpp_BasicInfor"><label class="per20"><em>建单人：</em><span id="bpp_CreateUser">${displayCxt.fieldMap['BASECREATORFULLNAME']}</span></label>
			<label class="per20"><em>建单人部门：</em><span id="bpp_CreateDep" title="${displayCxt.fieldMap['BASECREATORDEP']}">
				<c:if test="${fn:length(displayCxt.fieldMap['BASECREATORDEP'])>8}">${fn:substring(displayCxt.fieldMap['BASECREATORDEP'],0,8)}...</c:if>
	   　　   		<c:if test="${fn:length(displayCxt.fieldMap['BASECREATORDEP'])<=8}">${displayCxt.fieldMap['BASECREATORDEP']}</c:if>
			</span></label>
			<label class="per20"><em>建单时间：</em><span id="bpp_CreateTime"><eoms:date value="${displayCxt.fieldMap['BASECREATEDATE']}"></eoms:date></span></label>
			<!-- <label class="per20"><em>完成时间：</em><span id="bpp_FinishTime"><eoms:date value="${displayCxt.fieldMap['BASEFINISHDATE']}"></eoms:date></span></label> -->
			<label class="per20"><em>关闭时间：</em><span id="bpp_CloseTime"><eoms:date value="${displayCxt.fieldMap['BASECLOSEDATE']}"></eoms:date></span></label>
		</div>
	</div>
	<div id="bpp_TaskInfor">
		<div>
			<label class="per20"><em>任务状态：</em><span id="bpp_TaskStatus">${displayCxt.currentTask.processState}</span></label>
			<label class="per20"><em>开始时间：</em><span id="bpp_TaskCreateTime"><eoms:date value="${displayCxt.currentTask.createTime}"></eoms:date></span></label>
			<label class="per20" style="display: none;"><em>受理时限：</em><span id="bpp_TaskAcceptDeadLine"><eoms:date value="${displayCxt.currentTask.acceptDate}"></eoms:date></span></label>
			<label class="per20" style="display: none;"><em>处理时限：</em><span id="bpp_TaskDealDeadLine"><eoms:date value="${displayCxt.currentTask.dueDate}"></eoms:date></span></label>
            <label class="per20"><em>处理描述：</em><span id="bpp_TaskName">${displayCxt.currentTask.taskName}</span></label>
		</div>
	</div>
	<div id="bpp_Body">
		<jsp:include page="/ultrabpp/runtime/forms/${displayCxt.formFolder}/main/${displayCxt.mainPage}.jsp" />
	</div>
	<div id="bpp_BtnPanel" <c:if test="${empty displayCxt.fixActionList && empty displayCxt.freeActionList}">style="display:none;"</c:if>>
		<div id="bpp_BtnTip" class="bpp_BtnTip">小提示：鼠标移动到按钮上此处会有对应的操作说明。</div>
		<ul id="bpp_FixBtn" <c:if test="${displayCxt.currentTask.flagPreDefined == 0 || fn:length(displayCxt.fixActionList) == 0}">style="display:none;"</c:if>>
		<c:if test="${displayCxt.currentTask.flagPreDefined == 1}">
			<c:forEach items="${displayCxt.fixActionList}" var="actionModel">
				<li><input id="bpp_Btn_${actionModel.actionName}" type="button" value=" ${actionModel.label} " /></li>
				<script type="text/javascript">
					ClientContext.addFixAction("${actionModel.actionName}", "${actionModel.actionType}", "${actionModel.label}", ${actionModel.isFree}, ${actionModel.hasNext}, ${actionModel.isClose}, "${actionModel.description}");
				</script>
			</c:forEach>
			<li><div <c:if test="${fn:length(displayCxt.freeActionList) == 0}">style="display:none;"</c:if>><a href="javascript:void(0)" onclick="document.getElementById('bpp_FixBtn').style.display='none';document.getElementById('bpp_FreeBtn').style.display='block';">显示内部操作按钮</a></div></li>
		</c:if>
		</ul>
		<ul id="bpp_FreeBtn" <c:if test="${displayCxt.currentTask.flagPreDefined == 1 && fn:length(displayCxt.fixActionList) > 0}">style="display:none;"</c:if>>
			<c:forEach items="${displayCxt.freeActionList}" var="actionModel">
				<li><input id="bpp_Btn_${actionModel.actionName}" type="button" value=" ${actionModel.label} " /></li>
				<script type="text/javascript">
					ClientContext.addFreeAction("${actionModel.actionName}", "${actionModel.actionType}", "${actionModel.label}", ${actionModel.isFree}, ${actionModel.hasNext}, ${actionModel.isClose}, "${actionModel.description}");
				</script>
			</c:forEach>
			<li><div <c:if test="${displayCxt.currentTask.flagPreDefined == 0 || fn:length(displayCxt.fixActionList) == 0}">style="display:none;"</c:if>><a href="javascript:void(0)" onclick="document.getElementById('bpp_FreeBtn').style.display='none';document.getElementById('bpp_FixBtn').style.display='block';">隐藏内部操作按钮</a></div></li>
		</ul>
	</div>
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