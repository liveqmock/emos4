<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/theme/ie6/js/ActionPanelImpl.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/theme/ie6/js/ButtonPanelImpl.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/theme/ie6/js/CharacterFieldImpl.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/theme/ie6/js/ViewFieldImpl.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/theme/id6/js/main.js"></script>
<script type="text/javascript">
function resizeAttachField()
{
	var attachList = $("[ubftype=AttachField]");
	for(var i = 0; i < attachList.length; i++)
	{
		$(attachList[i]).width($(attachList[i]).width() - 3);
		attachList[i].firstChild.style.width = parseInt(attachList[i].firstChild.style.width) - 2;
	}
}
</script>
