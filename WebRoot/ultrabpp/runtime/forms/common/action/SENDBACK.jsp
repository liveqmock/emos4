<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:RollbackListField name="Sendback_RollbackTable" label="退回给" radio="0"></ubf:RollbackListField>
<ubf:CharacterField name="Desc" label="退回说明" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "SENDBACK", function()
{
	var Sendback_RollbackTable = F("Sendback_RollbackTable").G();
	if(Sendback_RollbackTable == null || Sendback_RollbackTable == "")
	{
		Message.tip(Message.require, "Sendback_RollbackTable", "不能为空！");
		return false;
	}
	
	var Desc = F("Desc").G();
	if(Desc == null || Desc == "")
	{
		Message.tip(Message.require, "Desc", "不能为空！");
		return false;
	}
	
	return true;
});
</script>