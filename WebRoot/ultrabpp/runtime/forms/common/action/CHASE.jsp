<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:RollbackListField name="Chase_RollbackTable" label="追回列表" radio="0" type="CHASE"></ubf:RollbackListField>
<ubf:CharacterField name="Desc" label="追回说明" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "CHASE", function()
{
	var chaseStr = F("Chase_RollbackTable").G();
	if(chaseStr == null || chaseStr == "")
	{
		Message.tip(Message.require, "Chase_RollbackTable", "不能为空！");
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