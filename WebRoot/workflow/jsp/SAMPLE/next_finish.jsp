<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

<script>
	function stepDeal() {
		getJQueryId("sheetFeildsMap.INC_Finish_Phase").val(getJQueryId("task.stepCode").val());
		getJQueryId("sheetFeildsMap.INC_Result").val("完成处理");
	}
</script>
	<validation id="modLogMap.finishDescV" dataType="Require" msg="<fmt:message key='step.validate.finishDesc' bundle='${i18Bundle}'/>" />
	
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="require"><fmt:message key="step.failOverTime" bundle="${i18Bundle}"/></td>
             <td class="leftalign">
                 <s:textfield name="modLogMap.excetptionFinishTime" cssClass="gd_input" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield>
			</td>
		</tr>
		<tr>
			<td class="require"><fmt:message key="step.recoverTime" bundle="${i18Bundle}"/></td>
             <td class="leftalign">
                 <s:textfield name="modLogMap.recoverTime" cssClass="gd_input" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield>
			</td>
		</tr>
		<tr>
			<td class="rightalign"><fmt:message key="step.finishDesc" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><textarea rows="3" name="modLogMap.finishDesc" class="gd_textare" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></textarea><span class="redcolor">*</span></td>
		</tr>
	</table>
	<p class="button_bar">
	<input type="submit" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.submit' bundle='${i18Bundle}'/>" onclick="stepDeal();"/>
	<input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.back' bundle='${i18Bundle}'/>" onclick="sheetBack();"/>
