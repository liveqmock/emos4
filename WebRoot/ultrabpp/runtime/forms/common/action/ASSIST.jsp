<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="AssistTree2" label="协办给" cell="4" single="0" action="ASSIST" next="1" />


<script language="javascript">
ClientContext.submit("action", "ASSIST", function()
{
	var AssistTree2 = F("AssistTree2").G();
	
	if(AssistTree2 == null || AssistTree2 == "")
	{
		Message.tip(Message.require, "AssistTree2", "不能为空！");
		return false;
	}
	return true;
});
</script>