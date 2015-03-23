<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:RollbackListField name="Reject_RollbackTable" label="驳回给" radio="1"></ubf:RollbackListField>
<ubf:CharacterField name="Desc" label="驳回说明" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "REJECT", function()
{
	var RollbackTableStr = F("Reject_RollbackTable").G();
	if(RollbackTableStr == null || RollbackTableStr == "")
	{
		Message.tip(Message.require, "Reject_RollbackTable", "不能为空！");
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