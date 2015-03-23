<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

<script>
	function stepDeal() {
		getJQueryId("sheetFeildsMap.INC_CloseAuditingDealResult").val("归档工单");
	}
</script>
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="require"><fmt:message key="step.satisfy" bundle="${i18Bundle}"/></td>
			<td class="leftalign">
				<eoms:select style="gd_select" name="modLogMap.satisfyLevel" dataDicTypeCode="satisfyLevel"/>
			</td>
		</tr>
		<tr>
			<td class="rightalign"><fmt:message key="step.closeDesc" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><textarea rows="3" name="modLogMap.closeDesc" class="gd_textare" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></textarea></td>
		</tr>
	</table>
	<p class="button_bar">
	<input type="submit" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.submit' bundle='${i18Bundle}'/>" onclick="stepDeal();"/>
	<input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.back' bundle='${i18Bundle}'/>" onclick="sheetBack();"/>
