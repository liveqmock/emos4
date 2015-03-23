<%-- 
	基于BS_T_SM_COMMONTREE 实现的小杂碎功能,此说明作为小杂碎功能的模板,模板说明如下:[使用字段：业务说明]:[PROPFIELD_02:负责人团队]
	PROPFIELD_01:变更(发布)对象
	PROPFIELD_02:变更类型
	PROPFIELD_03:修订人ID
	PROPFIELD_04：修订人
	PROPFIELD_05:方案验证人ID
	PROPFIELD_06:方案验证人
	PROPFIELD_07:验证人ID
	PROPFIELD_08:验证人
	PROPFIELD_09:实施人ID
	PROPFIELD_10:实施人
	PROPFIELD_11:一级审批人ID
	PROPFIELD_12:一级审批人
	PROPFIELD_13:二级审批人ID
	PROPFIELD_14:二级审批人
	PROPFIELD_15:评审人ID
	PROPFIELD_16:评审人
	PROPFIELD_15:描述
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<fieldset class="fieldset_style">
	<legend>变更分类信息</legend>
	<table class="add_user">
		<tr>
			<tr>
				<td class="texttd" width="100px">变更(发布)对象:<span class="must">*</span></td>
				<td colspan="3">
					<eoms:dictTree name="treeMap.PROPFIELD_01" value="${treeMap.PROPFIELD_01}" valueOf="fullname" checkboxes="2" style="width:97%;height:30px;" sqlStr="select id,id as value,name as text,name as fulltext,0 pid from busystem_view where tlevel = '3'"></eoms:dictTree>
					<validation id="treeMap.PROPFIELD_01V" dataType="Custom" regexp="^.{1,1000}$" msg="变更(发布)对象不能为空" />
				</td>
			</tr>
			<tr>
				<td class="texttd" style="width:15%">变更类型：<span class="must">*</span></td>
				<td style="width:35%">
					<eoms:dictTree name="treeMap.PROPFIELD_02" value="${treeMap.PROPFIELD_02}" onchange="setChangeType(this);" valueOf="fullname" checkboxes="1" style="width:97%;" dicttype="chg_changetype"></eoms:dictTree>
					<validation id="treeMap.PROPFIELD_02V" dataType="Custom" regexp="^.{1,1000}$" msg="变更类型不能为空" />
				</td>
				<td class="texttd" style="width:15%">修订人：</td>
				<td style="width:35%">
					<input type="hidden" id="PROPFIELD_03" name='treeMap.PROPFIELD_03' value="${treeMap.PROPFIELD_03}" />
					<input type="text" id="PROPFIELD_04" name='treeMap.PROPFIELD_04' value="${treeMap.PROPFIELD_04}" onclick="openpeopletree('PROPFIELD_03@PROPFIELD_04',1)" maxlength="2000" style="width: 96%;" />
				</td>
			</tr>
			<tr>
				<td class="texttd">方案验证人：</td>
				<td>
					<input type="hidden" id="PROPFIELD_05" name='treeMap.PROPFIELD_05' value="${treeMap.PROPFIELD_05}" />
					<input type="text" id="PROPFIELD_06" name='treeMap.PROPFIELD_06' value="${treeMap.PROPFIELD_06}" onclick="openpeopletree('PROPFIELD_05@PROPFIELD_06',1)" maxlength="2000" style="width: 96%;" />
				</td>
				<td class="texttd">评审人：</td>
				<td>
					<input type="hidden" id="PROPFIELD_15" name='treeMap.PROPFIELD_15' value="${treeMap.PROPFIELD_15}" />
					<input type="text" id="PROPFIELD_16" name='treeMap.PROPFIELD_16' value="${treeMap.PROPFIELD_16}" onclick="openpeopletree('PROPFIELD_15@PROPFIELD_16',1)" maxlength="2000" style="width: 96%;" />
				</td>
			</tr>
			<tr>
				<td class="texttd">一级审批人：</td>
				<td>
					<input type="hidden" id="PROPFIELD_11" name='treeMap.PROPFIELD_11' value="${treeMap.PROPFIELD_11}" />
					<input type="text" id="PROPFIELD_12" name='treeMap.PROPFIELD_12' value="${treeMap.PROPFIELD_12}" onclick="openpeopletree('PROPFIELD_11@PROPFIELD_12',1)" maxlength="2000" style="width: 96%;" />
				</td>
				<td class="texttd" id='audit_2'>二级审批人：</td>
				<td id='audit_2_value'>
					<input type="hidden" id="PROPFIELD_13" name='treeMap.PROPFIELD_13' value="${treeMap.PROPFIELD_13}" />
					<input type="text" id="PROPFIELD_14" name='treeMap.PROPFIELD_14' value="${treeMap.PROPFIELD_14}" onclick="openpeopletree('PROPFIELD_13@PROPFIELD_14',1)" maxlength="2000" style="width: 96%;" />
				</td>
			</tr>
			<tr>
				<td class="texttd">实施人：</td>
				<td>
					<input type="hidden" id="PROPFIELD_09" name='treeMap.PROPFIELD_09' value="${treeMap.PROPFIELD_09}" />
					<input type="text" id="PROPFIELD_10" name='treeMap.PROPFIELD_10' value="${treeMap.PROPFIELD_10}" onclick="openpeopletree('PROPFIELD_09@PROPFIELD_10',1)" maxlength="2000" style="width: 96%;" />
				</td>
				<td class="texttd">验证人：</td>
				<td>
					<input type="hidden" id="PROPFIELD_07" name='treeMap.PROPFIELD_07' value="${treeMap.PROPFIELD_07}" />
					<input type="text" id="PROPFIELD_08" name='treeMap.PROPFIELD_08' value="${treeMap.PROPFIELD_08}" onclick="openpeopletree('PROPFIELD_07@PROPFIELD_08',1)" maxlength="2000" style="width: 96%;" />
				</td>
			</tr>
			<tr>
				<td class="texttd">描述：</td>
				<td colspan="3">
					<textarea id="PROPFIELD_15" name='treeMap.PROPFIELD_15' rows="3" cols="3" maxlength="2000" style="width: 97%;">${treeMap.PROPFIELD_15}</textarea>
				</td>
			</tr>
		</table>
</fieldset>

<script type="text/javascript">
	function setChangeType(obj){
		var value = obj.value;
		if(value == '标准变更'){
			$('#audit_2').css('display','none');
			$('#audit_2_value').css('display','none');
			$('#PROPFIELD_13').val('');
			$('#PROPFIELD_14').val('');
		}else{
			$('#audit_2').css('display','');
			$('#audit_2_value').css('display','');
		}
	}
</script>