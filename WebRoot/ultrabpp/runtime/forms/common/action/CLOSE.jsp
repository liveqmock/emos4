<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:SelectField name="BaseCloseSatisfy" label="归档满意度" type="collect" resource="3:非常满意,2:满意,1:一般,0:不满意" defaultValue="满意" row="1" cell="1" />
<ubf:CharacterField name="Desc" label="归档描述" row="2" cell="4" length="500"/>


<script language="javascript">
ClientContext.submit("action", "CLOSE", function()
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