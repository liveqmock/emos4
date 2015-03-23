<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>当前位置：创建工作日历模板</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/optional/extend/workcalendar/js/calendar.js"></script> 
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);
			}
			window.onload = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);
				initCalendarCreate();
			}
			function formSubmit()
			{
				if(Validator.Validate(document.forms[0],2)==true)
				{
					$.get("${ctx}/workCalendar/isCalendarExists.action?rnd="+(new Date()).getTime(),{calType:$("#calType").val(),calYear:$("#calYear").val()},function(result){
						if("1"==result)
						{
							if(window.confirm("系统中已存在所选模版类型和年份的日历，是否覆盖？"))
							{
								document.forms[0].submit();
							}
						}
						else
						{
							document.forms[0].submit();
						}
					});
				}
			}
		</script>
	</head>

	<body>
	  <form action="${ctx}/workCalendar/workCalendarCreate.action" method="post">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">当前位置：创建工作日历模板</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="overflow-y:scroll;overflow-x:hidden;">
				<fieldset class="fieldset_style" style="width:95%">
					<legend>基本信息</legend>
					<div class="blank_tr"></div>
					<div class="tabContent" style="border-style:none">
						<table class="add_user">
							<tr>
								<td style="width:146px"></td>
								<td></td>
							</tr>
							<tr>
								<td>周末选择：</td>
								<td>
									<div id="noTrigRuleDiv" style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:99.7%"
										onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
										<c:forEach items="${weekDays}" var="dayOfWeek" varStatus="sta">
											<c:choose>
												<c:when test="${dayOfWeek.divalue=='7'||dayOfWeek.divalue=='1'}">
													<input type="checkbox" name="weekend" value="${dayOfWeek.divalue}" checked="checked"/>${dayOfWeek.diname}
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="weekend" value="${dayOfWeek.divalue}"/>${dayOfWeek.diname}
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</div>
								</td>
							</tr>
							<tr>
								<td>模版类型：<span class="must">*</span></td>
								<td>
									<eoms:select name="calType" style="select" dataDicTypeCode="workCalendarType" value="${calType}" isnull="false"/>
									<validation id="calTypeV" dataType="Require" msg="模板类别不能为空！" />
								</td>
							</tr>
							<tr>
								<td>模版年份：<span class="must">*</span></td>
								<td>
									<input type="text" style="width:99%" id="calYear" name="calYear" readonly="readonly" 
										onclick="WdatePicker({dateFmt:'yyyy',autoPickDate:true});"/>
									<validation id="calYearV" dataType="Require" msg="请选择模板年份！" />
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style" style="width:95%">
					<legend>工作时间设置</legend>
					<div class="blank_tr"></div>
					<div id="workTimeSpanDiv" 
						style="position:absolute;width:291px;height:145px;background-color:white;border-style:solid;border-color:gray;border-width:1px;display:none">
						<div style="padding:5px;">
							<input id="workTimeInputFrom" type="text" value="00:00" style="width:69px;" readonly="readonly" 
									onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"/>
							<span>~</span>
							<input id="workTimeInputTo" type="text" value="23:59" style="width:69px;" readonly="readonly" 
									onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"/>
							<eoms:select name="workTimeType" dataDicTypeCode="workTimeType" isnull="false"></eoms:select>
							<input type="button" value="添加" id="addWorkTime"
									class="operate_button"
									onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'"/>
							<select id="workTimeSel" style="width:280px" size="5"></select>
						</div>
						<div style="padding:5px;" align="right">
							<input type="button" value="确定" id="confirmAddWorkTime"
									class="operate_button"
									onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'"/>
							<input type="button" value="取消" id="cancelWorkTime"
									class="operate_button"
									onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'"/>
						</div>
					</div>
					<div class="tabContent" style="border-style:none">
						<table class="tableborder" style="width:99.8%" id="workTimeTb" cellpadding="0">
							<tr>
								<!-- 
								<th style="font-weight:normal;width:120px">时间类型</th>
								 -->
								<th style="font-weight:normal;width:145px">开始日期<font color="red"> *</font></th>
								<th style="font-weight:normal;width:145px">结束日期<font color="red"> *</font></th>
								<th style="font-weight:normal">工作时间段<font color="red"> *</font></th>
								<th style="font-weight:normal;width:138px">
									操作（<a id="btn_AddWorkTime" href="javascript:;" style="text-decoration:underline">添加</a>）
								</th>
							</tr>
							<tr>
								<!-- 
								<td>
									<input name="workTimes[0].mark" type="text" style="width:92%" value="通用类型"/>
								</td>
								 -->
								<td>
									<input name="workTimes[0].startTime" type="text" style="width:94%" value="" readonly="readonly"/>
								</td>
								<td>
									<input name="workTimes[0].endTime" type="text" style="width:94%" value="" readonly="readonly"/>
								</td>
								<td>
									<input name="workTimes[0].timeSpanStr" type="text" style="width:97.5%" class="workTimeSpan" value="00:00-23:59 DAY" readonly="readonly"/>
								</td>
								<td align="center">
									<a href="javascript:;" style="text-decoration:underline" onclick="delWorkTimeRow(this);">删除</a>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style" style="width:95%">
					<legend>节假日设置</legend>
					<div class="blank_tr"></div>
					<div class="tabContent" style="border-style:none">
						<table class="tableborder" style="width:99.8%" id="workTimeTb" cellpadding="0">
							<tr>
								<th style="font-weight:normal;width:145px">假日类别</th>
								<th style="font-weight:normal;width:145px">开始日期</th>
								<th style="font-weight:normal;width:145px">结束日期</th>
								<th style="font-weight:normal;width:145px">调休工作日1</th>
								<th style="font-weight:normal;">调休工作日2</th>
							</tr>
							<c:forEach items="${holidays}" var="holiday" varStatus="sta">
								<tr>
									<td>
										<input type="hidden" name="holidaySets[${sta.index}].mark" value="${holiday.divalue}"/>
										${holiday.diname}
									</td>
									<td>
										<input name="holidaySets[${sta.index}].startTime" type="text" style="width:94%" value="" readonly="readonly"/>
									</td>
									<td>
										<input name="holidaySets[${sta.index}].endTime" type="text" style="width:94%" value="" readonly="readonly"/>
									</td>
									<td>
										<input name="holidaySets[${sta.index}].swapWorkDay1" type="text" style="width:94%" value="" readonly="readonly"/>
									</td>
									<td>
										<input name="holidaySets[${sta.index}].swapWorkDay2" type="text" style="width:94%" value="" readonly="readonly"/>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
