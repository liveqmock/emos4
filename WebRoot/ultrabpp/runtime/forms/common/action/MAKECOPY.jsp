<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="AssignTree3" label="抄送给" cell="4" single="0" action="MAKECOPY" next="1" />

<script language="javascript">
ClientContext.submit("action", "MAKECOPY", function()
{
	var AssignTree3 = F("AssignTree3").G();
	if(AssignTree3 == null || AssignTree3 == "")
	{
		Message.tip(Message.require, "AssignTree3", "不能为空！");
		return false;
	}
	return true;
});
</script>