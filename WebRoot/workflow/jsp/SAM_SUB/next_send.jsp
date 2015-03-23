<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

	<script>
	
	</script>
	
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.dealActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}', '${wfAction.actorActionType}');"/></td>
			<td class="leftalign mustedit" colspan="7"><eoms:actor hiddenId="actorStr" textId="actorName" require="true" cssClass="gd_input2" resourceBundle='${i18Bundle}' msg="step.common.validate.dealActor"/></td>
		</tr>
		<tr id="auditTr" style="display: none">
			<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.auditActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}');"/></td>
			<td class="leftalign" colspan="7"><eoms:actor hiddenId="auditActorStr" textId="auditActorName" require="false" cssClass="gd_input2"/></td>
		</tr>
		<tr id="makeCopyTr" style="display: none">
			<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.copyActor' bundle='${i18Bundle}'/>" onclick="openActorTree('1', 'MAKECOPY');"/></td>
			<td class="leftalign" colspan="7"><eoms:actor hiddenId="copyActorStr" textId="makeCopyActorName" require="false" cssClass="gd_input2"/></td>
		</tr>
	</table>
	<p class="button_bar">
	<input type="submit" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.submit' bundle='${i18Bundle}'/>"/>
	<input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.back' bundle='${i18Bundle}'/>" onclick="sheetBack();"/>
	
	<script>
		showCopyAudit();
	</script>
