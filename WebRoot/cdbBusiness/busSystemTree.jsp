<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<fieldset class="fieldset_style">
	<legend>业务系统信息</legend>
	<table class="add_user">
		<tr>			
			<td class="texttd">责任处室：</td>
			<td style="width:35%">
				<input type="hidden" id="PROPFIELD_01" name='treeMap.PROPFIELD_01' value="${treeMap.PROPFIELD_01}" />
				<input type="text" id="PROPFIELD_02" name='treeMap.PROPFIELD_02' value="${treeMap.PROPFIELD_02}" onclick="openpeopletree('PROPFIELD_01@PROPFIELD_02',1)" maxlength="2000" style="width: 96%;" />
			</td>
			<td class="texttd" style="width:15%">系统编号：</td>
				<td style="width:35%">
				<input type="text" id="PROPFIELD_03" name='treeMap.PROPFIELD_03' value="${treeMap.PROPFIELD_03}"  style="width: 96%;" /><span id="isExist" style="color:red;display:none">应用系统编号已经存在请重新填写</span>
			</td>
		</tr>
	</table>
</fieldset>

<script type="text/javascript">
var systemCodeSource = $("#PROPFIELD_03").val();
$("#PROPFIELD_03").bind("blur",function(){
	var systemCode = $("#PROPFIELD_03").val();
	var item ="PROPFIELD_03";
	var type = "busSystem";
	if(systemCodeSource!=systemCode){
	$.post($ctx+"/commonTree/isExistItem.action",{item:item,type:type,itemValue:systemCode},function(data) {
		if(data=="exist"){
			$("#isExist").show();
			$("#page_save_button").hide();
		}else{
			$("#isExist").hide();
			$("#page_save_button").show();
		}
	})
	}
})

</script>