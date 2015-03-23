<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<div id="RollbackTable1_ubfbox" class="bpp_Field bpp_RollbackListField" style="width:960px;">
	<table border="0" cellpadding="0" cellspacing="0" style="width:950px; height:30px;">
		<tr>
			<td valign="center" colspan="2"><div><span style="font-size:14px; font-weight:bold; color:green;">会审通过！</span></div></td>
		</tr>
	</table>
</div>
<ubf:CharacterField name="Desc" label="审批意见" row="2" cell="4" length="500"/>

<script language="javascript">
ClientContext.submit("action", "ORGANIZEAUDITINGPASS", function()
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