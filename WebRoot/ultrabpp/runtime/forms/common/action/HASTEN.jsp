<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:RollbackListField name="Hasten_RollbackTable" label="催办列表" radio="0" type="HASTEN"></ubf:RollbackListField>
<ubf:CharacterField name="Desc" label="催办意见" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "HASTEN", function()
{
	var hastenStr = F("Hasten_RollbackTable").G();
	if(hastenStr == null || hastenStr == "")
	{
		Message.tip(Message.require, "Hasten_RollbackTable", "不能为空！");
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