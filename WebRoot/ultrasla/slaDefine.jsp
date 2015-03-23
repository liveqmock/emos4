<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/ultrasla/js/slaDefine.js"></script>
		<script language="javascript">
			ctx = '${ctx}';
			baseSchema = '${baseSchema}';
			window.onload = function()
			{
				setCenter(0,30);
				$("#slastatus").css("width","99.7%");
				init();
			}
			window.onresize = function() 
			{
				setCenter(0,30);
			}
		</script>
	</head>
	<body>
	   <input type="hidden" value="" id="formTimeRuleID"/>  <%-- 工单时限规则ID --%>
	   <input type="hidden" value="" id="stepTimeRuleID"/>  <%-- 工单_环节时限规则ID --%>
	   <input type="hidden" value="" id="formEventRuleID"/> <%-- 工单事件规则ID --%>
	   <input type="hidden" value="" id="stepEventRuleID"/> <%-- 工单_环节事件规则ID --%>
	   <input type="hidden" value="" id="deledTimeRuleID"/> <%-- 将被删除的时限规则ID --%>
	   <input type="hidden" value="" id="deledEventRuleID"/><%-- 将被删除的事件规则ID --%>
	   <input type="hidden" value="" id="slaId"/>
	   <div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" text="com_btn_save"/>
		 	 	</div>
			</div>
			<div id="center" class="add_scroll_div_x" align="center" style="overflow-y:scroll;overflow-x:hidden;">
				<!-- 流程服务水平协议（SLA）描述_Start -->
				<fieldset class="fieldset_style" style="width:95%">
					<legend>流程服务水平协议（SLA）描述</legend>
					<div class="blank_tr"></div>
					<form>
						<table class="add_user" style="width:98%;">
							<tr>
								<td class="texttd" style="width:15%"></td>
								<td style="width:35%"></td>
								<td class="texttd" style="width:15%"></td>
								<td style="width:35%"></td>
							</tr>
							<tr>
								<td class="texttd">
									SLA名称：<span class="must">*</span>
								</td>
								<td colspan="3" align="left">
									<input type="text" class="textInput"  style="width:98.8%" id="slaName" name="slaName"/>
									<validation id="slaNameV" require="true" dataType="Custom" regexp="^[\w\u4e00-\u9fa5\-:]{1,30}$"
											msg="SLA名称不能为空，长度不超过30位，只能是字母、数字、下划线、减号和中文！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									有效时间从：<span class="must">*</span>
								</td>
								<td align="left">
									<input type="text" maxlength="8" readonly="readonly" class="textInput"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});" id="slavalidstarttime" name="slavalidstarttime"/>
									<validation id="slavalidstarttimeV" dataType="Require"
											msg="请选择SLA有效开始时间！" />
								</td>
								<td class="texttd">
									到：<span class="must">*</span>
								</td>
								<td>
									<input type="text" maxlength="8" readonly="readonly" class="textInput"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});" id="slavalidendtime" name="slavalidendtime"/>
									<validation id="slavalidendtimeV" dataType="Require"
											msg="请选择SLA有效结束时间！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									状态：
								</td>
								<td align="left" colspan="3">
									<eoms:select name="slastatus"style="select" dataDicTypeCode="startstatus" isnull="false"/>
								</td>
							</tr>
							<tr>
								<td class="texttd" valign="top">
									描述：
								</td>
								<td colspan="3" rowspan="2" align="left">
									<textarea rows="3" style="width:98.7%" id="slaRemark" name="slaRemark"></textarea>
									<validation id="slaRemarkV" dataType="Limit" max="100"
											msg="SLA描述长度不超过100位！" />
								</td>
							</tr>
						</table>
					</form>
					<div class="blank_tr"></div>
				</fieldset>
				<!-- 流程服务水平协议（SLA）描述_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程服务水平协议（SLA）规则_Start -->
				<fieldset class="fieldset_style" style="width:95%">
					<legend>流程服务水平协议（SLA）规则</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%" id="formTimeRuleTb">
						<tr>
							<th style="font-weight:normal;width:35px;">序号</th>
							<th style="font-weight:normal">规则名称</th>
							<!-- 
							<th style="font-weight:normal">触发规则</th>
							-->
							<th style="font-weight:normal;width:100px;">受理时限（分钟）</th>
							<th style="font-weight:normal;width:100px;">处理时限（分钟）</th>
							<th style="font-weight:normal;width:120px">
								操作（<a id="btn_timeRule_form" href="javascript:;" style="text-decoration:underline">添加</a>）
							</th>
						</tr>
						<tr rowtag='norule'>
							<td colspan="6" align="center">无规则！</td>
						</tr>
					</table>
				</fieldset>
				<!-- 流程服务水平协议（SLA）规则_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程环节（OLA）规则_Start -->
				<fieldset class="fieldset_style" style="width:95%">
					<legend>流程环节（OLA）规则</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%" id="stepTimeRuleTb">
						<tr>
							<th style="font-weight:normal;width:35px">序号</th>
							<th style="font-weight:normal;width:50px">环节号</th>
							<th style="font-weight:normal">规则名称</th>
							<!-- 
							<th style="font-weight:normal">触发规则</th>
							-->
							<th style="font-weight:normal;width:100px">受理时限（分钟）</th>
							<th style="font-weight:normal;width:100px">处理时限（分钟）</th>
							<th style="font-weight:normal;width:120px">
								操作（<a id="btn_timeRule_step" href="javascript:;" style="text-decoration:underline">添加</a>）
							</th>
						</tr>
						<tr rowtag='norule'>
							<td colspan="7" align="center">无规则！</td>
						</tr>
					</table>
				</fieldset>
				<!-- 流程环节（OLA）规则_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程SLA逾期通知或升级策略_Start -->
				<fieldset class="fieldset_style" style="width:95%">
					<legend>流程SLA逾期通知或升级策略</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%" id="formEventRuleTb">
						<tr>
							<th style="font-weight:normal;width:35px">序号</th>
							<th style="font-weight:normal">规则名称</th>
							<th style="font-weight:normal;width:130px">触发规则</th>
							<th style="font-weight:normal;width:100px">逾期时限（分钟）</th>
							<th style="font-weight:normal;width:100px">逾期策略</th>
							<th style="font-weight:normal;width:120px">
								操作（<a id="btn_eventRule_form" href="javascript:;" style="text-decoration:underline">添加</a>）
							</th>
						</tr>
						<tr rowtag='norule'>
							<td colspan="6" align="center">无规则！</td>
						</tr>
					</table>
				</fieldset>
				<!-- 流程SLA逾期通知或升级策略_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程环节OLA逾期通知或升级策略_Start -->
				<fieldset class="fieldset_style" style="width:95%">
					<legend>流程环节OLA逾期通知或升级策略</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%" id="stepEventRuleTb">
						<tr>
							<th style="font-weight:normal;width:35px;">序号</th>
							<th style="font-weight:normal;width:50px">环节号</th>
							<th style="font-weight:normal">规则名称</th>
							<th style="font-weight:normal;width:130px">触发规则</th>
							<th style="font-weight:normal;width:100px">逾期时限（分钟）</th>
							<th style="font-weight:normal;width:100px">逾期策略</th>
							<th style="font-weight:normal;width:120px">
								操作（<a id="btn_eventRule_step" href="javascript:;" style="text-decoration:underline">添加</a>）
							</th>
						</tr>
						<tr rowtag='norule'>
							<td colspan="7" align="center">无规则！</td>
						</tr>
					</table>
				</fieldset>
				<!-- 流程环节OLA逾期通知或升级策略_End -->
			</div>
	   </div>
	</body>
</html>
