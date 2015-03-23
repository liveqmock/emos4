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
		<title>节假日修改</title>
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
				var holidayName = $("#holidayname").val();
				if (holidayName == '') {
					alert("节假日名称不能为空！"); 
				  	return false;
				} 
				var form = $("#form");
				form.submit();
       		}
			function delHolidays(){
				var pid = document.getElementById("dutyHoliday.pid").value;
	    		if(!window.confirm("确认要删除吗?")){
					return false;
				}else{
					window.location.href="deleDutyHolidayById.action?pid="+pid;
				}
		    }
		</script>
	</head>
	<body>
		<form id="form" action="updateDutyHoliday.action" method="post">
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2">节假日修改</span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="scroll_div" id="center" style="text-align:center;">
				   <div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						<table class="add_table">
							<tr>
								<td class="add_table_left" style="width: 20%">
									节假日名称：
								</td>
								<td class="add_table_right" style="width: 70%">
									<input type="hidden" name="dutyHoliday.pid" value="${dutyHoliday.pid }" />
									<input type="hidden" id="oldDateinfo" value="${dutyHoliday.dateinfo }" />
									<input type="hidden" id="oldHolidayName" value="${dutyHoliday.holidayname }" />
									<input type="text" name="dutyHoliday.holidayname" maxlength="16"
										id="holidayname" class="textInput"
										value="${dutyHoliday.holidayname }"/>
								</td>
							</tr>
							<tr>
								<td class="add_table_left" style="width: 20%">
									日期：
								</td>
								<td class="add_table_right" style="width: 70%">
									<input type="text" id="dateinfo" name="dutyHoliday.dateinfo"
										class="textInput" readonly="true"
										value="${dutyHoliday.dateinfo }" />
								</td>
							</tr>
							<tr>
								<td class="add_table_left" style="width: 20%">
									启用停用：
								</td>
								<td class="add_table_right">
									<select name="dutyHoliday.hideflag" class="select" style="width: 96%">
										<c:forEach items="${enableOrDisableMap}" var="ed">
										   <option value="${ed.key}" <c:if test="${ed.key == dutyHoliday.hideflag}">selected="selected"</c:if>>
												${ed.value}
											</option>
									   </c:forEach>	
									</select>
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									是否是节假日：
								</td>
								<td class="add_table_right">
									<select name="dutyHoliday.holidayFlag" class="select" style="width: 96%">
										<option value="0" <c:if test="${0 == dutyHoliday.holidayFlag}">selected="selected"</c:if>>
											节假日
										</option>
										<option value="1" <c:if test="${1 == dutyHoliday.holidayFlag}">selected="selected"</c:if>>
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
									<eoms:select  name="dutyHoliday.datetype" style="select" dataDicTypeCode="datetype" value="${dutyHoliday.datetype }" isnull="false" />
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									<eoms:lable name="com_lb_remark"/>：
								</td>
								<td class="add_table_right">
									<textarea id="dutyHoliday.note" name="dutyHoliday.note" rows="4" cols="45" style="width: 96%"
									onchange="if(this.value.length>150){this.value=this.value.substring(0,150);alert('备注的最大长度为150位');}">${dutyHoliday.note }</textarea>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
			<div class="add_bottom">
				<input type="button" name="save_button" value="<eoms:lable name='com_btn_save'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="submitCheck();" />
				<input type="button" name="save_button" value="<eoms:lable name='com_btn_delete'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="delHolidays();" />
				<input type="reset" name="button5" id="button5" value="<eoms:lable name='com_btn_close'/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</form>
	</body>
</html>
