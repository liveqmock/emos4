<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/common/style/blue/css/detail.css" rel="stylesheet"
			type="text/css" />
		<title>节假日新增</title>
		<script src="${ctx}/common/javascript/main.js"></script>
		<script language="javascript"
			src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,55);
			}
			window.onload = function() {
				setCenter(0,55);
			}
			function submitCheck(){
				// 节假日名称
				var holidayName = $("#holidayname").val();
				if (holidayName == '') {
					alert("节假日名称不能为空！"); 
				  	return false;
				} 
				var startTime=$("#startTime").val();
				var endTime=$("#endTime").val();
				// 判断日期是否是有效
				if (startTime == '') {
				  alert("节假日开始时间不能为空！"); 
				  return false;
				}
				if (endTime == '') {
				  alert("节假日结束时间不能为空！"); 
				  return false;
				}
				if(endTime < startTime){
				  alert("节假日开始时间不能小于结束时间！"); 
				  return false;
				}
				
				// 校验同一天不能被重复定义为同一节日
				$.post("checkHoliday.action", // 服务器页面地址
				{
					holidayName : holidayName,
					startTime : startTime,
					endTime : endTime
				}, function(date) {
			       if (date == 'false') {
				       if(startTime == endTime){
				    	   alert(startTime+"已有节假日被定义！");
					   }else{
				       		alert(startTime+"到"+endTime+"已有节假日被定义！");
					   }
				       return false;
			       } else {
			        var form = $("#form");
					form.submit();
			       }
				});
			}
		</script>
	</head>
	<body>
		
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2">节假日新增</span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="scroll_div" id="center" style="text-align:center;">
				  <div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						<table class="add_table">
						<form id="form" action="saveDutyHoliday.action" method="post">
							<tr>
								<td class="add_table_left">
									<span class="must">*</span> 节假日名称：
								</td>
								<td class="add_table_right">
									<input type="text" name="dutyHoliday.holidayname"
										id="holidayname" class="textInput" maxlength="16"/>
								</td>
								<td class="add_table_left">
									<span class="must">*</span> 日期：
								</td>
								<td class="add_table_right">
									<input type="text" id="startTime" name="startTime" value="${startTime}" 
										class="textInput" readonly="true" />
									<input type="hidden" name="endTime" id="endTime" value="${startTime}"/>
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									启用停用：
								</td>
								<td class="add_table_right">
									<select name="dutyHoliday.hideflag" class="select" style="width: 96%">
										<c:forEach items="${enableOrDisableMap}" var="ed">
										   <option value="${ed.key}">
												${ed.value}
											</option>
									   </c:forEach>	
									</select>
								</td>
								<td class="add_table_left">
									是否是节假日：
								</td>
								<td class="add_table_right">
									<select name="dutyHoliday.holidayFlag" class="select" style="width: 96%">
										<option value="0">
											节假日
										</option>
										<option value="1">
											工作日
										</option>
									</select>
								</td>
							</tr>
							<tr>
							<td class="add_table_left">
									日期类型：
								</td>
								<td class="add_table_right">
									<eoms:select  name="dutyHoliday.datetype" style="select" dataDicTypeCode="datetype"  isnull="false" />
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									<eoms:lable name="com_lb_remark"/>：
								</td>
								<td class="add_table_right" colspan="3">
									<textarea id="dutyHoliday.note" name="dutyHoliday.note" rows="4" cols="45" style="width: 98%"
									onchange="if(this.value.length>150){this.value=this.value.substring(0,150);alert('备注的最大长度为150位');}"></textarea>
								</td>
							</tr>
							</form>
						</table>
					</fieldset>
				</div>
			
			<div class="add_bottom">
				<input type="button" name="button5" id="button5" value="<eoms:lable name='com_btn_save'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="submitCheck();" />
				<input type="reset" name="button5" id="button5" value="<eoms:lable name='com_btn_close'/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
	</body>
</html>
