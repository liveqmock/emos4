<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="ReauditTree1" label="转审给" cell="4" action="REAUDIT" single="0" next="1" />

<script language="javascript">
ClientContext.submit("action", "REAUDIT", function()
{
	var ReauditTree1 = F("ReauditTree1").G();
	if(ReauditTree1 == null || ReauditTree1 == "")
	{
		Message.tip(Message.require, "ReauditTree1", "不能为空！");
		return false;
	}
	return true;
});
</script>