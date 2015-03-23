<%-- 
	基于BS_T_SM_COMMONTREE 实现的小杂碎功能,此说明作为小杂碎功能的模板,模板说明如下:[使用字段：业务说明]:[PROPFIELD_02:负责人团队]
	PROPFIELD_02:负责人团队
	PROPFIELD_15:相关描述
	PROPFIELD_16:工作日响应时间
	PROPFIELD_17:非工作日响应时间
	PROPFIELD_18：解决时间上限（不更换硬件）
	PROPFIELD_19:解决时间上限（更换硬件）
	PROPFIELD_20:工作开始时间
	PROPFIELD_21:工作结束时间
--%>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<script src="${ctx}/common/javascript/date/WdatePicker.js"></script>
	<script src="${ctx}/ultrabpp/runtime/theme/classic/js/batchReview.js"></script>
<fieldset class="fieldset_style" sytle="">
	<legend>事件现象分类</legend>
	<table class="add_user">
	<s:hidden name="treeMap.PROPFIELD_01" id="picid"/>
		<tr>
			<tr>
				<td class="texttd" width="100px">负责人/团队:<span class="must">*</span></td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_02" id="phenoClassPicname" cssClass="textInput" maxlength="100" />
				</td>
			</tr>
			
			<tr>
				<td class="texttd" width="100px">工作日响应时间:</td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_16" id="wokingDayResponseTime" cssClass="textInput" maxlength="100" />
				</td>
				<td class="texttd" width="100px">非工作日响应时间:</td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_17" id="nonWokingDayResponseTime" cssClass="textInput" maxlength="100" />
				</td>
			</tr>
			
			<tr>
				<td class="texttd" width="100px">解决时间上限（不更换硬件）:</td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_18" id="resolveUlNonRepHw" cssClass="textInput" maxlength="100" />
				</td>
				<td class="texttd" width="10px">解决时间上限（更换硬件）:</td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_19" id="resolveUlRepHw" cssClass="textInput" maxlength="100" />
				</td>
			</tr>
			
			<tr>
				<td class="texttd" width="100px">工作开始时间:</td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_20" id="wokingStartTime" cssClass="textInput" maxlength="100" 
				onfocus="WdatePicker({dateFmt:'HH:mm:ss',autoPickDate:true})" onkeydown="return false" onpaste="return false" />
				</td>
				<td class="texttd" width="10px">工作结束时间:</td>
				<td colspan="3">
				<s:textfield name="treeMap.PROPFIELD_21" id="wokingEndTime" cssClass="textInput" maxlength="100" 
				onfocus="WdatePicker({dateFmt:'HH:mm:ss',autoPickDate:true})" onkeydown="return false" onpaste="return false"/>
				</td>
			</tr>
		</tr>
		    <tr>
				<td class="texttd" style="width:15%">相关描述：</td>
				<td colspan="3">
					<textarea id="PROPFIELD_15" name='treeMap.PROPFIELD_15' rows="3" cols="3" maxlength="2000" style="width: 97%;">${treeMap.PROPFIELD_15}</textarea>
				</td>
			</tr>
		</table>
</fieldset>

<script type="text/javascript">

//人,团队选择树
$("#phenoClassPicname").click(function(){
	showModalDialog($ctx+'/common/tools/orgtree/organizaTree.jsp?doc_id='+this.id+'&back_fill="incident"&isSelectType=2&idtype=1&isRadio=0',window,'help:no;center:true;scroll:no;status:no;dialogWidth:590px;dialogHeight:490px');
	});
	
//人,部门选择树
$("#phenoClassPicDept").click(function(){
	showModalDialog($ctx+'/common/tools/orgtree/organizaTree.jsp?doc_id='+this.id+'&back_fill="incident"&isSelectType=2&idtype=1&isRadio=0',window,'help:no;center:true;scroll:no;status:no;dialogWidth:590px;dialogHeight:490px');
	});
	
//数据回填
function fillBackUserInfo(data,doc_id) {
	if (data != '') {
		var userName = '';
		var userId = '';
		var groupId = '';
		var groupName = '';
		var dataArray = data.split(';');
		var userArray = dataArray[0].split(':');
		var depArray = dataArray[1].split(':');
		userId = userArray[0] + ":" + userArray[1];
		userName = "负责人" + ":" + userArray[2];
		groupId = depArray[0] + ":" + depArray[1];
		if(doc_id=='phenoClassPicname'){
		groupName = "负责团队" + ":" + depArray[2];
		}
		if (userArray[1] != '') {
			$("#picid").val(userId);
			$("#"+doc_id).val(userName);//全名
		}
		else if (depArray[1] != '') {
			$("#picid").val(groupId);
			$("#"+doc_id).val(groupName);//全名	
		}
	}
}

</script>