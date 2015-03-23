<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp" %>
<%@include file="../header.jsp" %>
<%@include file="style.jsp" %>
<%@include file="script.jsp" %>
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
<body onload="bpp_init()">
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
		<div id="bpp_BasicInfor">
			<span id="bpp_CreateUser">建单人：${displayCxt.fieldMap['BASECREATORFULLNAME']}</span>
			<span id="bpp_CreatePhone">建单人电话：${displayCxt.fieldMap['BASECREATORCONNECTWAY']}</span>
			<span id="bpp_CreateDep">建单人部门：${displayCxt.fieldMap['BASECREATORDEP']}</span>
			<span id="bpp_CreateTime">建单时间：<eoms:date value="${displayCxt.fieldMap['BASECREATEDATE']}"></eoms:date></span>
			<span id="bpp_CloseTime">关闭时间：<eoms:date value="${displayCxt.fieldMap['BASECLOSEDATE']}"></eoms:date></span>
		</div>
	</div>
	<div id="bpp_TaskInfor">
		<div id="bpp_TaskName"><span>处理描述：${displayCxt.currentTask.taskName}</span></div>
		<div>
			<span id="bpp_TaskStatus">任务状态：${displayCxt.currentTask.processState}</span>
			<span id="bpp_TaskCreateTime">开始时间：<eoms:date value="${displayCxt.currentTask.createTime}"></eoms:date></span>
			<span id="bpp_TaskAcceptDeadLine">受理时限：<eoms:date value="${displayCxt.currentTask.acceptDate}"></eoms:date></span>
			<span id="bpp_TaskDealDeadLine">处理时限：<eoms:date value="${displayCxt.currentTask.dueDate}"></eoms:date></span>
		</div>
	</div>
	<div id="bpp_Body">
		<jsp:include page="/ultrabpp/runtime/forms/${displayCxt.formFolder}/main/${displayCxt.mainPage}.jsp" />
	</div>
	<div id="bpp_BtnPanel" <c:if test="${empty displayCxt.fixActionList && empty displayCxt.freeActionList}">style="display:none;"</c:if>>
		<div style="height:5px;"></div>
		<div id="bpp_BtnPanelBody">
			<div id="bpp_BtnTip" class="bpp_BtnTip">请选择对工单的操作：</div>
			<ul>
				<c:if test="${displayCxt.currentTask.flagPreDefined == 1}">
					<c:forEach items="${displayCxt.fixActionList}" var="actionModel">
						<li><input id="bpp_Btn_${actionModel.actionName}" name="bpp_Btn_Group" type="radio" value="" />&nbsp;<label for="bpp_Btn_${actionModel.actionName}">${actionModel.label}：${actionModel.description}</label></li>
						<script type="text/javascript">
							ClientContext.addFixAction("${actionModel.actionName}", "${actionModel.actionType}", "${actionModel.label}", ${actionModel.isFree}, ${actionModel.hasNext}, ${actionModel.isClose}, "${actionModel.description}");
						</script>
					</c:forEach>
				</c:if>
				<c:forEach items="${displayCxt.freeActionList}" var="actionModel">
					<li><input id="bpp_Btn_${actionModel.actionName}" name="bpp_Btn_Group" type="radio" />&nbsp;<label for="bpp_Btn_${actionModel.actionName}">${actionModel.label}：${actionModel.description}</label></li>
					<script type="text/javascript">
						ClientContext.addFreeAction("${actionModel.actionName}", "${actionModel.actionType}", "${actionModel.label}", ${actionModel.isFree}, ${actionModel.hasNext}, ${actionModel.isClose}, "${actionModel.description}");
					</script>
				</c:forEach>
			</ul>
			<input type="hidden" id="bpp_BtnActionName" value="" />
			<input type="hidden" id="bpp_BtnType" value="" />
			<div id="bpp_BtnPanel_Action">
				<input type="button" id="bpp_BtnNext" value="下一步" onclick="ButtonPanel.next()" />
			</div>
		</div>
	</div>
	<div id="bpp_ActPanel">
		<div style="height:5px;"></div>
		<div id="bpp_ActBody">
			<div id="bpp_ActComment"></div>
			<div id="bpp_ActFields"></div>
			<div class="bpp_White_Block"></div>
			<div id="bpp_ActPanel_BtnPanel">
				<input type="button" value="上一步" onclick="ActionPanel.goBack()" />
				<input type="button" value="提　交" onclick="ActionPanel.submit()" />
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
</html>
<ubf:Preview></ubf:Preview>