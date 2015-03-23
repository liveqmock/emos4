<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

	<script>
	
	</script>
	
	<input type="hidden" name="modLogMap.attachField" id="modLogMap.attachField" value="${uuid}">
	<input type="hidden" name="stepAttach" id="stepAttach" value="myfileupload${uuid}">
	
	<div class="form_title"><span><fmt:message key="common.actionInfo" bundle="${i18Bundle}"/></span></div>
	<table class="gd_table">
		<tr>
			<td class="rightalign"><fmt:message key="step.isFatal" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><s:select name="modLogMap.isMergency" list="#{'0':'否','1':'是'}" emptyOption="false" cssClass="gd_select"></s:select></td>
			<td class="rightalign"><fmt:message key="step.alarmLevel" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><eoms:select style="gd_select" name="modLogMap.dictField" dataDicTypeCode="alarmTypeCode"/></td>
			<td class="rightalign"><fmt:message key="step.timeField" bundle="${i18Bundle}"/></td>
			<td class="leftalign"><s:textfield name="modLogMap.timeField" cssClass="gd_input" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield></td>
            <td class="rightalign">&nbsp;</td>
			<td class="leftalign" width="127">&nbsp;</td>
		</tr>
		<tr>
			<td class="rightalign"><fmt:message key="step.sendDesc" bundle="${i18Bundle}"/></td>
			<td class="leftalign" colspan="7"><textarea rows="3" name="modLogMap.assignDesc" class="gd_textare" onkeydown="return txtLimit(this, 200)" onkeyup="return txtLimit(this, 200)"></textarea></td>
		</tr>
		<tr>
			<td class="rightalign"><fmt:message key="step.attaField" bundle="${i18Bundle}"/></td>
			<td class="leftalign" colspan="7"><div class="fujainstyele1">
				<atta:fileupload id="myfileupload${uuid}" 
					fileTypes="*.*" 
					progressIsVisible="true"
					attchmentGroupId="${uuid}"
					uploadTableVisible="true" 
					isMultiDownload="true"
					isAutoUpload="true" 
					uploadBtnIsVisible="true"
					downTableVisible="true"
					operationParams="0,1,1"
					isEdit="1"
					isMultiUpload="true" 
					uploadDestination="${path}"
					flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf">
				</atta:fileupload></div>
			</td>
		</tr>
		<tr>
			<td class="rightalign"><input type="button" class="gd_button" onmouseover="this.className='gd_buttonhover'" onmouseout="this.className='gd_button'" value="<fmt:message key='step.common.dealActor' bundle='${i18Bundle}'/>" onclick="openActorTree('${wfAction.radio}', '${wfAction.actionType}');"/></td>
			<td class="leftalign mustedit" colspan="7"><eoms:actor hiddenId="actorStr" textId="actorName" hidDefValue="${actorStr}" textDefValue="${actorName}" require="true" cssClass="gd_input2" resourceBundle='${i18Bundle}' msg="step.common.validate.dealActor"/></td>
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
