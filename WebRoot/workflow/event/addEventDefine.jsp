<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<title>当前位置：流程事件定义</title>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
			setOpName('onload');
		}
		function defineSave() {
			var myform = document.eventDefineForm;
			if(Validator.Validate(myform,2)) {
				var type = document.getElementById('eventDefine.operationtype').value;
				if(type == 'SLA') {
					document.getElementById('eventDefine.operationname').value = document.getElementById('operationnameid').value;
				} else {
					document.getElementById('eventDefine.operationname').value = document.getElementById('operationname').value;
				}
				if(document.getElementById('eventDefine.operationname').value == '') {
					alert('触发逻辑名称不能为空！');
					return ;
				}
				myform.submit();
			}
		}
		function setOpName(source) {
			var type = document.getElementById('eventDefine.operationtype').value;
			if(source != 'onload') {
				document.getElementById('operationname').value = '';
				document.getElementById('operationnameid').value = '';
			}
			if(type == 'SLA') {
				document.getElementById('sltOpName').disabled = '';
				document.getElementById('operationname').readOnly = 'true';
			} else {
				document.getElementById('sltOpName').disabled = 'disabled';
				document.getElementById('operationname').readOnly = '';
			}
		}
		function selRule() {
			if(document.getElementById('eventDefine.operationtype').value=='SLA')
				window.showModalDialog('${ctx}/eventDefine/selectEventRule.action',window,'help:no;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
		}
	</script>
</head>
<body>
	<form action="${ctx}/eventDefine/saveEventDefine.action" name="eventDefineForm" id="eventDefineForm" method="post">
		<input type="hidden" id="eventDefine.eventdefid" name="eventDefine.eventdefid" value="${eventDefine.eventdefid}" />
		<input type="hidden" id="eventDefine.pid" name="eventDefine.pid" value="${eventDefine.pid}" />
		<input type="hidden" id="eventDefine.createtime" name="eventDefine.createtime" value="${eventDefine.createtime}" />
		<input type="hidden" id="eventDefine.operationname" name="eventDefine.operationname" value="${eventDefine.operationname}"/>
		<input type="hidden" id="eventDefine.eventconditionid" name="eventDefine.eventconditionid" value="${eventDefine.eventconditionid}"/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">添加/修改流程定义</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>流程事件定义</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
							<tr>
								<td style="width: 15%">工单类型：<span class="must">*</span></td>
								<td style="width: 75%">
									<eoms:select name="eventDefine.baseschema" value="${eventDefine.baseschema}" style="select"
										customSql="select code as baseSchema,name from bs_t_wf_type t"/>
									<validation id="eventDefine.baseschemaV" dataType="Limit" min="1" max="30" msg="工单类别不能为空，请选择工单类别！" />
								</td>
								<td style="width: 10%"></td>
							</tr>
							<tr>
								<td>事件类型：<span class="must">*</span></td>
								<td>
									<eoms:select name="eventDefine.eventtype" style="select" dataDicTypeCode="EventType" value="${eventDefine.eventtype}" isnull="false" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>处理类型组ID：</td>
								<td>
									<input type="text" id="eventDefine.handletypegroupid" name="eventDefine.handletypegroupid" class="textInput" style="width:97.8%" value="${eventDefine.handletypegroupid}"/>
									<validation id="eventDefine.handletypegroupidV" require="false" dataType="Limit" max="25" msg="事件出发条件不能多余25个字符！" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>事件主体：<span class="must">*</span></td>
								<td>
									<input type="text" id="eventDefine.eventsubject" name="eventDefine.eventsubject" class="textInput" style="width:97.8%" value="${eventDefine.eventsubject}"/>
									<validation id="eventDefine.eventsubjectV" dataType="Limit" min="1" max="10" msg="事件主体不能为空，在1到10个字之间！" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>事件动作：<span class="must">*</span></td>
								<td>
									<eoms:select name="eventDefine.eventaction" style="select" dataDicTypeCode="EventAction" value="${eventDefine.eventaction}" isnull="false" />
									<validation id="eventDefine.eventactionV" dataType="Require" msg="<eoms:lable name='sm_msg_operTypeConstraint'/>！" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>事件触发条件：</td>
								<td>
									<input type="text" id="eventDefine.eventcondition" name="eventDefine.eventcondition" class="textInput" style="width:97.8%" value="${eventDefine.eventcondition}"/>
									<validation id="eventDefine.eventconditionV" require="false" dataType="Limit" max="30" msg="事件出发条件不能多余30个字符！" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>触发类型：<span class="must">*</span></td>
								<td>
									<eoms:select name="eventDefine.handletype" style="select" dataDicTypeCode="HandleType"
										value="${eventDefine.handletype}" isnull="false"/>
									<validation id="eventDefine.handletypeV" dataType="Require"
										msg="<eoms:lable name='sm_msg_operTypeConstraint'/>！" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>触发逻辑类型：<span class="must">*</span></td>
								<td>
									<eoms:select name="eventDefine.operationtype" style="select" dataDicTypeCode="OperationType"
										value="${eventDefine.operationtype}" isnull="false" onChangeFun="setOpName('');"/>
									<validation id="eventDefine.operationtypeV" dataType="Require"
										msg="<eoms:lable name='sm_msg_operTypeConstraint'/>！" />
								</td>
								<td></td>
							</tr>
							
							<tr>
								<td>触发逻辑名称：<span class="must">*</span></td>
								<td>
									<input type="hidden" id="operationnameid" name="operationnameid" value="${operationnameid}"/>
									<input type="text" id="operationname" name="operationname" class="textInput" style="width:97.8%" value="${operationname}"/>
								    <validation id="operationnameV" dataType="Limit" min="1" max="100" msg="触发逻辑名称要在1到100个字之间！" />
								</td>
								<td>
									<input type="button" id="sltOpName" name="sltOpName" value="选择" class="operate_button" onclick="selRule();"/>
								</td>
							</tr>
							<tr>
								<td>时限字段：<span class="must">*</span></td>
								<td>
									<input type="text" id="eventDefine.duetimefield" name="eventDefine.duetimefield" class="textInput" 
										style="width:97.8%" value="${eventDefine.duetimefield}"/>
									<validation id="eventDefine.duetimefieldV" dataType="Require"
										msg="时限字段不能为空！" />
								</td>
								<td></td>
							</tr>							
						</table>
					</div>
				</fieldset>
			</div>
			<div class="add_bottom">
				<input type="button" value="<eoms:lable name="com_btn_save"/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="defineSave();" />
				<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
	</form>
</body>
</html>
