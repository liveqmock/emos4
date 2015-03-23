<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="AssignTree4" label="提审给" cell="4" single="0" action="AUDIT" next="1" />

<script language="javascript">
ClientContext.submit("action", "AUDIT", function()
{
	var AssignTree4 = F("AssignTree4").G();
	
	if(AssignTree4 == null || AssignTree4 == "")
	{
		Message.tip(Message.require, "AssignTree4", "不能为空！");
		return false;
	}
	return true;
});
</script>