<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:RollbackListField name="Suggest_RollbackTable" label="目标处理人" radio="0"></ubf:RollbackListField>
<ubf:CharacterField name="Desc" label="处理意见" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "SUGGEST", function()
{
	var suggestStr = F("Suggest_RollbackTable").G();
	if(suggestStr == null || suggestStr == "")
	{
		Message.tip(Message.require, "Suggest_RollbackTable", "不能为空！");
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