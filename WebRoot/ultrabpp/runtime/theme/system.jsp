<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input type="hidden" id="bpp_Sys_BaseID" name="bpp_Sys_BaseID" value="${displayCxt.baseID}" />
<input type="hidden" id="bpp_Sys_BaseSchema" name="bpp_Sys_BaseSchema" value="${displayCxt.baseSchema}" />
<input type="hidden" id="bpp_Sys_DefCode" name="bpp_Sys_DefCode" value="${displayCxt.defCode}" />
<input type="hidden" id="bpp_Sys_FlagActive" name="bpp_Sys_FlagActive" value="${displayCxt.flagActive}" />
<input type="hidden" id="bpp_Sys_TaskID" name="bpp_Sys_TaskID" value="${displayCxt.taskID}" />
<input type="hidden" id="bpp_Sys_ActionID" name="bpp_Sys_ActionID" value="" />
<input type="hidden" id="bpp_Sys_ActionType" name="bpp_Sys_ActionType" value="" />
<input type="hidden" id="bpp_Sys_ActionText" name="bpp_Sys_ActionText" value="" />
<input type="hidden" id="bpp_Sys_CloseAfterCommit" name="bpp_Sys_CloseAfterCommit" value="" />
<input type="hidden" id="bpp_Sys_AssignString" name="bpp_Sys_AssignString" value="" />
<input type="hidden" id="bpp_Sys_RelateType" name="bpp_Sys_RelateType" value="${displayCxt.relateType}" />
<input type="hidden" id="bpp_Sys_RelateBaseID" name="bpp_Sys_RelateBaseID" value="${displayCxt.relateBaseID}" />
<input type="hidden" id="bpp_Sys_RelateBaseSchema" name="bpp_Sys_RelateBaseSchema" value="${displayCxt.relateBaseSchema}" />
<input type="hidden" id="bpp_Sys_RelateBaseSN" name="bpp_Sys_RelateBaseSN" value="${displayCxt.relateBaseSN}" />
<input type="hidden" id="bpp_Sys_RelateTaskID" name="bpp_Sys_RelateTaskID" value="${displayCxt.relateTaskID}" />
<c:forEach items="${displayCxt.attributeMap}" var="attrItem">
	<input type="hidden" id="${attrItem.key}" name="${attrItem.key}" value="${attrItem.value}" />
	<script type="text/javascript">ClientContext.setAttr("${attrItem.key}", "${attrItem.value}");</script>
</c:forEach>