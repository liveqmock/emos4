<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:CharacterField name="Desc" label="作废说明" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "CANCEL", function()
{
	var Desc = F("Desc").G();
	
	if(Desc == null || Desc == "")
	{
		Message.tip(Message.require, "Desc", "不能为空！");
		return false;
	}
	return true;
});
</script>