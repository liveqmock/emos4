<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="AssignTree1" label="派发给" cell="4" single="0" next="1" />
<ubf:AssignTreeField name="AssignTree2" label="协办给" cell="4" single="0" action="ASSIST" next="1" />
<ubf:AssignTreeField name="AssignTree3" label="抄送给" cell="4" single="0" action="MAKECOPY" next="1" />
<ubf:AssignTreeField name="AssignTree4" label="提审给" cell="4" single="0" action="AUDIT" next="1" />


<script language="javascript">
ClientContext.submit("action", "ASSIGN", function()
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