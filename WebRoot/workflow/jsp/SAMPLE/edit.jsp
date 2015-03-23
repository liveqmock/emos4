<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>
<head>
	<c:choose>
		<c:when test="${fn:length(sheetFeildsMap.baseId)>0}">
			<title>${sheetFeildsMap.baseName}[${sheetFeildsMap.baseSn}]</title>
		</c:when>
		<c:otherwise>
			<title><fmt:message key="common.new" bundle="${i18Bundle}"/>${sheetFeildsMap.baseName}</title>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/jsp/common/js/util.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/jsp/common/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/selectTag.js"></script>
	<script type="text/javascript" src="${ctx}/common/ck/ckeditor/ckeditor.js"></script>
	<script>
		window.onload = function() {
			getPageMenu('baseInfor_tab','baseInfor');PageMenuActive('baseInfor_tab','baseInfor');
			CKEDITOR.replace('editor1');
		}
		
	</script>
</head>
<body onresize="resizeDiv()" class="newbodystyle">
	<!--表单标题-->
	<div class="lefttopbg"></div>
	<div class="righttopbg"></div>
	<div class="gd_title">
		<span class="gd_titlespanleft">${sheetFeildsMap.baseStatus}</span>
		<span class="gd_titlespanright"></span>
		<span class="gd_titlefont">${sheetFeildsMap.baseName}&nbsp;&nbsp;${sheetFeildsMap.baseSn}</span>
		<div id="flowMapDiv" style="display:none;">
			<span class="gd_sheetview">
				<a href="javascript:" onclick="sheetView('${baseId}', '${baseSchema}', '${sheetFeildsMap.baseTplId}');"><fmt:message key="common.sheetview" bundle="${i18Bundle}"/></a>
			</span>
			<span class="gd_viso">
				<a href="javascript:" onclick="openFlowMap('${baseId}', '${baseSchema}', '${sheetFeildsMap.baseWorkflowFlag}', '${sheetFeildsMap.baseTplId}', '${sheetFeildsMap.baseEntryId}');"><fmt:message key="common.flowmap" bundle="${i18Bundle}"/></a>
			</span>
		</div>
	</div>
	<!-- 横线  -->
	<form name="fieldForm" id="fieldForm" action="save.action" method="post" onsubmit="return checkForm();">
		<!-- 用户信息 -->
		<s:hidden name="sheetFeildsMap.baseCreatorLoginName"/>
		<s:hidden name="sheetFeildsMap.baseCreatorDepId"/>
		<s:hidden name="sheetFeildsMap.baseCreatorCorpId"/>
		<s:hidden name="sheetFeildsMap.baseCreatorDN"/>
		<!-- 工单类型信息 -->
		<s:hidden name="baseSchema" id="baseSchema"/>
		<s:hidden name="sheetFeildsMap.baseCatagoryId"/>
		<s:hidden name="sheetFeildsMap.baseWorkflowFlag"/>
		<s:hidden name="sheetFeildsMap.baseSchema"/>
		<s:hidden name="sheetFeildsMap.baseName"/>
		<s:hidden name="sheetFeildsMap.baseTplId" id="sheetFeildsMap.baseTplId"/>
		<s:hidden name="sheetFeildsMap.baseId" id="sheetFeildsMap.baseId"/>
		<s:hidden name="sheetFeildsMap.baseAttachGUID"/>
		<!-- 工单用到的维度字段、工单用到的用于条件判断的字段 -->
		<s:hidden name="dimAndCon" id="dimAndCon"/>
		<!-- 提交动作信息 -->
		<s:hidden name="wfAction.actionName" id="wfAction.actionName"/>
		<s:hidden name="wfAction.actionType" id="wfAction.actionType"/>
		<s:hidden name="wfAction.page" id="wfAction.page"/>
		<s:hidden name="wfAction.needActor" id="wfAction.needActor"/>
		<s:hidden name="wfAction.radio" id="wfAction.radio"/>
		<!-- 任务信息 -->
		<s:hidden name="task.id" id="task.id"/>
		<s:hidden name="task.flagActive" id="task.flagActive"/>
		<s:hidden name="task.entryId" id="task.entryId"/>
		<s:hidden name="task.stepCode" id="task.stepCode"/>
		<!-- 业务信息 -->
		<s:hidden name="sheetFeildsMap.INC_Finish_Phase" id="sheetFeildsMap.INC_Finish_Phase"/>
		<s:hidden name="sheetFeildsMap.INC_Result" id="sheetFeildsMap.INC_Result"/>
		<s:hidden name="sheetFeildsMap.INC_CloseAuditingDealResult" id="sheetFeildsMap.INC_CloseAuditingDealResult"/>
		<!-- 验证信息 -->
		<div id="validateDiv">
		  <validation id="sheetFeildsMap.baseSummaryV" dataType="Require" msg="<fmt:message key='validate.baseSummary' bundle='${i18Bundle}'/>" />
		  <validation id="sheetFeildsMap.netTypeV" dataType="Require" msg="<fmt:message key='validate.netType' bundle='${i18Bundle}'/>" />
		  <validation id="sheetFeildsMap.alarmLevelV" dataType="Require" msg="<fmt:message key='validate.alarmLevel' bundle='${i18Bundle}'/>" />
		  <validation id="sheetFeildsMap.taskDescV" dataType="Require" msg="<fmt:message key='validate.taskDesc' bundle='${i18Bundle}'/>" />
		  <validation id="sheetFeildsMap.isPreDealV" dataType="Require" msg="<fmt:message key='validate.isPreDeal' bundle='${i18Bundle}'/>" />
		</div>
		
		<div class="gd_tab">
		  	<!-- 标签页 -->
			<div class="gd_tablabel">
				<div id="baseInfor_tab" class="tab_show" onclick="PageMenuActive('baseInfor_tab','baseInfor')"><span><fmt:message key="common.baseinfor" bundle="${i18Bundle}"/></span></div>
				<div id="dealInfor_tab" class="tab_hide" style="display:none" onclick="PageMenuActive('dealInfor_tab','dealInfor');setIframeUrl('dealInfor_frame','${ctx}/workflow/sheet/query/BaseInfoView.jsp?baseschema=${baseSchema}&baseid=${baseId}&tplid=${sheetFeildsMap.baseTplId}')"><span><fmt:message key="common.dealinfor" bundle="${i18Bundle}"/></span></div>
			</div>
		</div>
		
		<!-- 工单信息 -->
		<div id="baseInfor" class="baseInforclass">
		    <!-- 工单通用信息 -->
		    <div class="form_title"><span>&nbsp;</span></div>
		    <table class="gd_baseinfor">
		      <tr>
		        <td class="require"><fmt:message key="common.creator" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="sheetFeildsMap.baseCreatorFullName" cssClass="inputnostyle" readonly="true"/></td>
		        <td class="require"><fmt:message key="common.creatorPhone" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="sheetFeildsMap.baseCreatorConnectWay" cssClass="inputnostyle" readonly="true"/></td>
		        <td class="require"><fmt:message key="common.creatorDep" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="sheetFeildsMap.baseCreatorDep" cssClass="inputnostyle" readonly="true"/></td>
		        <td class="require"><fmt:message key="common.creatorCorp" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="sheetFeildsMap.baseCreatorCorp" cssClass="inputnostyle" readonly="true"/></td>
		      </tr>
		      <tr>
		        <td class="require"><fmt:message key="common.createDate" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><eoms:dateTime name="sheetFeildsMap.baseCreateDate" value="${sheetFeildsMap.baseCreateDate}" cssClass="inputnostyle" type="display"/></td>
		        <td class="require"><fmt:message key="common.closeDate" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><eoms:dateTime name="sheetFeildsMap.baseCloseDate" value="${sheetFeildsMap.baseCloseDate}" cssClass="inputnostyle" type="display"/></td>
		        <td class="require"><fmt:message key="common.status" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="sheetFeildsMap.baseStatus" cssClass="inputnostyle" readonly="true"/></td>
		        <td class="require"><fmt:message key="common.baseSn" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="sheetFeildsMap.baseSn" cssClass="inputnostyle" readonly="true"/></td>
		      </tr>
		      <tr>
		      	<td class="require"><fmt:message key="common.stepAdvice" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="task.taskName" cssClass="inputnostyle" readonly="true"/></td>
		        <td class="require"><fmt:message key="common.stepStatus" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><s:textfield name="task.processState" cssClass="inputnostyle" readonly="true"/></td>
		        <td class="require"><fmt:message key="common.stepAcceptDate" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><eoms:dateTime name="sheetFeildsMap.baseCreateDate" value="${task.acceptDate}" cssClass="inputnostyle" type="display"/></td>
		        <td class="require"><fmt:message key="common.stepDueDate" bundle="${i18Bundle}"/>：</td>
		        <td class="leftalign2"><eoms:dateTime name="sheetFeildsMap.baseCreateDate" value="${task.dueDate}" cssClass="inputnostyle" type="display"/></td>
		      </tr>
		    </table>
		    
		    <!-- 工单信息1 -->
		    <div class="form_title"><span><fmt:message key="base.sheetInfo" bundle="${i18Bundle}"/></span></div>
		    <table class="gd_table">
		      <tr>
		        <td class="require"><fmt:message key="base.baseSummary" bundle="${i18Bundle}"/></td>
		        <td colspan="7" class="leftalign mustedit"><s:textfield name="sheetFeildsMap.baseSummary" cssClass="gd_input2" maxlength="50"/></td>
		      </tr>
		      <tr>
		        <td class="require"><fmt:message key="base.netType" bundle="${i18Bundle}"/></td>
		        <td class="leftalign mustedit"><eoms:select style="gd_select" name="sheetFeildsMap.netType" dataDicTypeCode="netTypeCode" value="${sheetFeildsMap.netType}"/></td>
		        <td class="require"><fmt:message key="base.alarmLevel" bundle="${i18Bundle}"/></td>
		        <td class="leftalign mustedit"><eoms:select style="gd_select" name="sheetFeildsMap.alarmLevel" dataDicTypeCode="alarmTypeCode" value="${sheetFeildsMap.alarmLevel}"/></td>
		        <td class="require"><fmt:message key="base.affectType" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><eoms:select style="gd_select" name="sheetFeildsMap.bizType" dataDicTypeCode="bizType" value="${sheetFeildsMap.bizType}"/></td>
		        <td class="require"><fmt:message key="base.province" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><eoms:select style="gd_select" name="sheetFeildsMap.province" dataDicTypeCode="provinceType" childrenname="sheetFeildsMap.city" value="${sheetFeildsMap.province}"/></td>
		      </tr>
		      <tr>
		        <td class="require"><fmt:message key="base.city" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><eoms:select style="gd_select" name="sheetFeildsMap.city" value="${sheetFeildsMap.city}"/></td>
		        <td class="require"><fmt:message key="base.netName" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><s:textfield type="text" name="sheetFeildsMap.netDevice" cssClass="gd_input" maxlength="50"/></td>
		        <td class="require"><fmt:message key="base.dealTime" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><eoms:dateTime name="sheetFeildsMap.baseDealOutTime" value="${sheetFeildsMap.baseDealOutTime}" cssClass="gd_input"/></td>
		        <td class="require"><fmt:message key="base.acceptTime" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><eoms:dateTime name="sheetFeildsMap.baseAcceptOutTime" value="${sheetFeildsMap.baseAcceptOutTime}" cssClass="gd_input"/></td>
		      </tr>
		      <tr>
		        <td class="rightalign"><fmt:message key="base.isPreDeal" bundle="${i18Bundle}"/></td>
		        <td class="leftalign mustedit"><eoms:select style="gd_select" name="sheetFeildsMap.isPreDeal" dataDicTypeCode="isdefault" value="${sheetFeildsMap.isPreDeal}"/></td>
		        <td class="require"><fmt:message key="base.person" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><s:textfield name="sheetFeildsMap.contactPerson" cssClass="gd_input" maxlength="50"/></td>
		        <td class="require"><fmt:message key="base.connectWay" bundle="${i18Bundle}"/></td>
		        <td class="leftalign"><s:textfield name="sheetFeildsMap.contactWay" cssClass="gd_input" maxlength="50"/></td>
		        <td></td>
		      </tr>
		    </table>
		    
		    <!-- 工单信息2 -->
		    <div class="form_title"><span><fmt:message key="base.extInfo" bundle="${i18Bundle}"/></span></div>
		    <table class="gd_table">
		      <tr>
		        <td class="rightalign"><fmt:message key="base.taskDesc" bundle="${i18Bundle}"/></td>
		        <td colspan=5 class="leftalign mustedit"><div>
		            <s:textarea cssClass='gd_textare' name="sheetFeildsMap.taskDesc" rows="3" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"/>
		          </div></td>
		      </tr>
		      <tr>
		        <td class="rightalign"><fmt:message key="base.terminate" bundle="${i18Bundle}"/></td>
		        <td class="leftalign" colspan=5><div>
		            <s:textarea cssClass='gd_textare' name="sheetFeildsMap.termDesc" rows="3" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"/>
		          </div></td>
		      </tr>
		      <tr>
		        <td class="rightalign"><fmt:message key="base.attach" bundle="${i18Bundle}"/></td>
		        <td class="leftalign" colspan=5>
		        	<div class="fujainstyele">
		        		<atta:fileupload id="myfileupload${sheetFeildsMap.baseAttachGUID}" 
							fileTypes="*.*" 
							progressIsVisible="true"
							attchmentGroupId="${sheetFeildsMap.baseAttachGUID}"
							uploadTableVisible="true" 
							isMultiDownload="true"
							isAutoUpload="true" 
							uploadBtnIsVisible="true"
							downTableVisible="true"
							operationParams="0,${task.flagActive},1"
							isMultiUpload="true" 
							uploadDestination="${path}"
							flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf" ></atta:fileupload>
					</div>
				</td>
		      </tr>
		    </table>
		    
		    <!-- 工单信息3 -->
		    <div class="form_title"><span><fmt:message key="base.attaInfo" bundle="${i18Bundle}"/></span></div>
		    <table class="gd_table">
		      <tr>
		        <td class="rightalign"><fmt:message key="base.detailDesc" bundle="${i18Bundle}"/></td>
		        <td class="leftalign1"><div class="newmaioshu">
		            <s:textarea id="editor1" cssClass='editor1class' name="sheetFeildsMap.richText" readonly="true" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></s:textarea>
		          </div></td>
		      </tr>
		    </table>  
		</div>
		  
		<div class="baseInforclass1">
		    <!-- 处理信息 -->
		    <div id="dealInfor" class="dealInforclass" style="display:none;">
		    	<iframe id="dealInfor_frame" class="dealInfor_frameclass" src="" MARGINWIDTH="0" MARGINHEIGHT="0" HSPACE="0" VSPACE="0" FRAMEBORDER="0" Border="0" noresize scrolling="auto"></iframe>
		    </div>
		    
		    <!-- 业务操作按钮 -->
		    <div id="operDiv" class="button_bar1">
				<div class="newleftbutton">
					<c:forEach items="${leftWfActions}" var="wfAction">
					        <script>
								recActionType('${wfAction.actionType}');
							</script>
					        <c:choose>
					          <c:when test="${fn:length(wfAction.page)>0}">
					            <input type="button" id="btn_${wfAction.actionId}" class="gd_button" value="${wfAction.actionName}" onclick="showStep('${wfAction.actionType}', '${wfAction.actionName}', '${wfAction.page}', '${wfAction.needActor}', '${wfAction.radio}')"/>
					          </c:when>
					          <c:otherwise>
					            <input type="submit" id="btn_${wfAction.actionId}" class="gd_button" value="${wfAction.actionName}" onclick="setAction('${wfAction.actionType}', '${wfAction.actionName}', '${wfAction.page}', '${wfAction.needActor}', '${wfAction.radio}');"/>
					          </c:otherwise>
					        </c:choose>
				     </c:forEach>
				</div>
				<div class="newrightbutton">
					<c:if test="${fn:length(rightWfActions) > 0}">
						<div class="chromestyle" id="chromemenu">
						    <ul>
						      <li onmouseover="showDiv('dropmenu1');this.className='menu_onfocus';" onmouseout="this.className='menu_onblur';"><a href="#" rel="dropmenu1"><fmt:message key="common.otherActions" bundle="${i18Bundle}"/></a></li>
						    </ul>
						</div>
						<div id="dropmenu1" class="dropmenudiv" style="display:none;">
			   				<ul>
								<c:forEach items="${rightWfActions}" var="wfAction">
									<script>
										recActionType('${wfAction.actionType}');
									</script>
									<li><a href="#" onclick="showStep('${wfAction.actionType}', '${wfAction.actionName}', '${wfAction.page}', '${wfAction.needActor}', '${wfAction.radio}')"><span class="menu_font">${wfAction.actionName}</span></a></li>
						      </c:forEach>
					       </ul>
				  		</div>
			  		</c:if>
			    </div>
		    </div>
		    
		    <!-- 环节页面div -->
		    <div id="stepDiv" style="display:none" class="centeralign"></div>
		</div>
	</form>
</body>
</html>
<script>
	<c:if test="${fn:length(sheetFeildsMap.baseId)>0}">
		showDiv("dealInfor_tab");
		showDiv("flowMapDiv");
	</c:if>
	
	try{
		cssdropdown.startchrome("chromemenu");
	}catch(e){
	
	}
</script>
