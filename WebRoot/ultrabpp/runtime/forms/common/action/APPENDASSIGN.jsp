<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="AssignTree1" label="追派给" cell="4" single="0" action="APPENDASSIGN" next="1" />

<script language="javascript">
ClientContext.submit("action", "APPENDASSIGN", function()
{
	var AssignTree1 = F("AssignTree1").G();
	
	if(AssignTree1 == null || AssignTree1 == "")
	{
		Message.tip(Message.require, "AssignTree1", "不能为空！");
		return false;
	}
	return true;
});
</script>