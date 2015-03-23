<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

<script>
</script>
	
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="rightalign"><fmt:message key="step.relTime" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><eoms:dateTime name="modLogMap.relTime" cssClass="gd_input"/></td>
			<td class="rightalign"><fmt:message key="step.relNum" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><s:textfield name="modLogMap.relNum" cssClass="gd_input"></s:textfield></td>
			<td class="rightalign"><fmt:message key="step.spDesc" bundle="${i18Bundle}"/></td>
			<td class="leftalign" colspan="3"><s:textfield name="modLogMap.spDesc" cssClass="gd_input3"></s:textfield></td>
		</tr>
		<tr>
			<td class="rightalign"><fmt:message key="step.sendDesc" bundle="${i18Bundle}"/></td>
			<td class="leftalign mustedit" colspan="7"><textarea rows="3" name="modLogMap.assignDesc" class="gd_textare" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></textarea></td>
		</tr>
		<s:if test="wfAction.needActor">
			<tr>
				<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.dealActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}');"/></td>
				<td class="leftalign mustedit" colspan="7"><eoms:actor hiddenId="actorStr" textId="actorName" require="true" cssClass="gd_input2" resourceBundle='${i18Bundle}' msg="step.common.validate.dealActor"/></td>
			</tr>
			<tr id="auditTr" style="display: none">
				<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.auditActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}');"/></td>
				<td class="leftalign" colspan="7"><eoms:actor hiddenId="auditActorStr" textId="auditActorName" require="false" cssClass="gd_input2"/></td>
			</tr>
			<tr id="makeCopyTr" style="display: none">
				<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.copyActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}');"/></td>
				<td class="leftalign" colspan="7"><eoms:actor hiddenId="copyActorStr" textId="makeCopyActorName" require="false" cssClass="gd_input2"/></td>
			</tr>
		</s:if>
	</table>
	<p class="button_bar">
	<input type="submit" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.submit' bundle='${i18Bundle}'/>"/>
	<input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.back' bundle='${i18Bundle}'/>" onclick="sheetBack();"/>

<script>
	showCopyAudit();
</script>