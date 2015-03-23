<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="ReassignTree1" label="转派给" cell="4" single="1" next="1" />

<script language="javascript">
ClientContext.submit("action", "REASSIGN", function()
{
	var ReassignTree1 = F("ReassignTree1").G();
	if(ReassignTree1 == null || ReassignTree1 == "")
	{
		Message.tip(Message.require, "ReassignTree1", "不能为空！");
		return false;
	}
	return true;
});
</script>