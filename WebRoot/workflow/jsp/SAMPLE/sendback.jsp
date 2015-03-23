<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

<script>
</script>
	<input type="hidden" name="actorStr" id="actorStr">
	<validation id="actorStrV" dataType="Require" msg="<fmt:message key='step.common.validate.dealprocess' bundle='${i18Bundle}'/>" />
	
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="rightalign"><fmt:message key="step.common.dpList" bundle="${i18Bundle}"/></td>
			<td class="leftalign1 mustedit">
				<div class="onlywidthstyle"><dg:datagrid var="dp" title="" items="${dealProcessList}">
				    <dg:gridtitle>
					    	<tr>
					    		<th><fmt:message key="step.common.choice" bundle="${i18Bundle}"/></th>
					    		<th><fmt:message key="step.common.dealGroup" bundle="${i18Bundle}"/></th>
					    		<th><fmt:message key="step.common.dealer" bundle="${i18Bundle}"/></th>
					    		<th><fmt:message key="step.common.stepDesc" bundle="${i18Bundle}"/></th>
					    	</tr>
				    </dg:gridtitle> 
					<dg:gridrow>
						<tr class="${waitingdealsheet_row}">
					        <td><input type="checkbox" name="ck_dpModels" value="${dp.phaseNo}:${dp.stepId}" onclick=""></td>
					        <td>${dp.assignGroup}</td>
					        <td>${dp.assignee}</td>
					        <td>${dp.dpDesc}</td>
					    </tr>
					</dg:gridrow>
				</dg:datagrid></div>
			</td>
		</tr>
		<tr>
			<td class="rightalign"><fmt:message key="step.sendbackDesc" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><textarea rows="3" name="modLogMap.sendbackDesc" class="gd_textare" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></textarea></td>
		</tr>
	</table>
	<p class="button_bar">
	<input type="submit" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.submit' bundle='${i18Bundle}'/>" onclick="setActor('${wfAction.actionType}');"/>
	<input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.back' bundle='${i18Bundle}'/>" onclick="sheetBack();"/>
