<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>告警建单维护</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		
	</script>
	</head>

	<body>
	 	<form name="euform" action="${ctx}/bsTCmbcAlarmnote/bsTCmbcAlarmnoteSave.action" method="post" onsubmit="return Validator.Validate(this,2);">
			<input type="hidden" id="bsTCmbcAlarmnote.pid" name="bsTCmbcAlarmnote.pid" value="${bsTCmbcAlarmnote.pid}" />
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2">告警建单维护查看</span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend>告警建单维护</legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td>
										  告警ID ：
									</td>
									<td colspan="3">
										<input type="text" name="bsTCmbcAlarmnote.alarmid" id="bsTCmbcAlarmnote.alarmid" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmid}"/>
									</td>
									<td style="width:15%">
										事件标题：
									</td>
									<td style="width:35%">
										<input type="text" name="bsTCmbcAlarmnote.alarmtitle" id="bsTCmbcAlarmnote.alarmtitle" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmtitle}"/>
									</td>
								</tr>
								<tr>
									<td>
										  应用系统分类 ：
									</td>
									<td colspan="3">
										<input type="text" name="bsTCmbcAlarmnote.alarmsystype" id="bsTCmbcAlarmnote.alarmsystype" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmsystype}"/>
									</td>
									<td style="width:15%">
										告警一级分类：
									</td>
									<td style="width:35%">
										<input type="text" name="bsTCmbcAlarmnote.alarmonetype" id="bsTCmbcAlarmnote.alarmonetype" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmonetype}"/>
									</td>
									
								</tr>
								<tr>
									<td>
										告警二级分类：
									</td>
									<td colspan="3">
										<input type="text" name="bsTCmbcAlarmnote.alarmtwotype" id="bsTCmbcAlarmnote.alarmtwotype" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmtwotype}"/>
									</td>
									<td style="width:15%">
										告警三级分类：
									</td>
									<td style="width:35%">
										<input type="text" name="bsTCmbcAlarmnote.alarmthreetype" id="bsTCmbcAlarmnote.alarmthreetype" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmthreetype}"/>
									</td>
									
								</tr>
								<tr>
									<td>
										地点：
									</td>
									<td colspan="3">
										<input type="text" name="bsTCmbcAlarmnote.alarmlocal" id="bsTCmbcAlarmnote.alarmlocal" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.alarmlocal}"/>
									</td>
									<td style="width:15%">
										发生时间：
									</td>
									<td style="width:35%">
										<input type="text" name="bsTCmbcAlarmnote.alarmhappentime" id="bsTCmbcAlarmnote.alarmhappentime" disabled="disabled" class="textInput" value="<eoms:date value='${bsTCmbcAlarmnote.alarmhappentime }'/>" readonly="true" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									</td>
									
								</tr>
								<tr>
									<td>
										部门：
									</td>
									<td colspan="3">
										<input type="text" name="bsTCmbcAlarmnote.dep" id="bsTCmbcAlarmnote.dep" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.dep}"/>
									</td>
									<td style="width:15%">
										实际开始时间：
									</td>
									<td style="width:35%">
										<input type="text" name="bsTCmbcAlarmnote.alarmstarttime" id="bsTCmbcAlarmnote.alarmstarttime" disabled="disabled" class="textInput" value="<eoms:date value='${bsTCmbcAlarmnote.alarmstarttime}'/>" readonly="true" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  是否建单成功：
									</td>
									<td colspan="3">
										<input type="text" name="bsTCmbcAlarmnote.iscreatesheet" id="bsTCmbcAlarmnote.iscreatesheet" disabled="disabled" class="textInput" style="width:98.5%;" value="${bsTCmbcAlarmnote.iscreatesheet}"/>
									</td>
									<td style="width:15%">
										入库时间：
									</td>
									<td style="width:35%">
										<input type="text" name="bsTCmbcAlarmnote.inputtime" id="bsTCmbcAlarmnote.inputtime" disabled="disabled" class="textInput" value="<eoms:date value='${bsTCmbcAlarmnote.inputtime}'/>" readonly="true" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									</td>
								</tr>
								<tr>
									<td >
										事件描述：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="bsTCmbcAlarmnote.alarmdesc" id="bsTCmbcAlarmnote.alarmdesc" disabled="disabled" rows="6" style="width:98.7%">${bsTCmbcAlarmnote.alarmdesc}</textarea>
									</td>
									<td >
										失败日志：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="bsTCmbcAlarmnote.faultlog" id="bsTCmbcAlarmnote.faultlog" disabled="disabled" rows="6" style="width:98.7%">${bsTCmbcAlarmnote.faultlog}</textarea>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
			</div>
		</form>
	</body>
</html>