<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="AuditTree1" label="组织会审" cell="4" single="0"next="1" />

<script language="javascript">
ClientContext.submit("action", "ORGANIZEAUDIT", function()
{
	var AuditTree1 = F("AuditTree1").G();
	if(AuditTree1 == null || AuditTree1 == "")
	{
		Message.tip(Message.require, "AuditTree1", "不能为空！");
		return false;
	}
	return true;
});
</script>