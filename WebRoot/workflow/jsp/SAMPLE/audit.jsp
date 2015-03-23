<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

<script>
</script>
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="rightalign"><fmt:message key="step.auditInfo" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><textarea rows="3" name="modLogMap.auditDesc" class="gd_textare" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></textarea></td>
		</tr>
		<s:if test="wfAction.needActor">
			<tr>
				<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.auditActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}');"/></td>
				<td class="leftalign mustedit"><eoms:actor hiddenId="auditActorStr" textId="auditActorName" require="true" cssClass="gd_input2" resourceBundle="${i18Bundle}" msg="step.common.validate.auditActor"/></td>
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