<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@ include file="/common/core/taglibs.jsp"%>
    <title>工作日历修改</title>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		var $CalWTCounter = ${fn:length(dayWorkTimes)};
		var $CalWTDatestring = "${workCalendar.datestring}";
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			$("#TimeTypeSelect").css("display","none");
			$(document.getElementById('tempField1')).attr("disabled","disabled");
			$(document.getElementById("workCalendar.isholiday")).unbind("change");
			$(document.getElementById("workCalendar.isholiday")).bind("change",function(){
				if(this.value=="1"){
					$("#workTimeArea").css("display","none");
				}else{
					$("#workTimeArea").css("display","block");
				}
			});
			$("#workTimeAddHref").click(function(){
				var index = $CalWTCounter++;
				var newR = "<tr>\n";
				newR    +=  "<td>\n";
				newR    +=   "<input type='hidden' name='dayWorkTimes["+index+"].datestring' value='"+$CalWTDatestring+"'/>\n";
				newR    +=   "【<input type='text' name='dayWorkTimes["+index+"].starttimeShow'"
				newR    +=                      " onclick='WdatePicker({dateFmt:\"HH:mm\",autoPickDate:true});'"
				newR    += 						" style='width:70px;border-width:1px;border-style:none;text-align:center'/>】\n";
				newR    +=   "&nbsp;到&nbsp;\n";
				newR    +=   "【<input type='text' name='dayWorkTimes["+index+"].endtimeShow'"
				newR    +=                      " onclick='WdatePicker({dateFmt:\"HH:mm\",autoPickDate:true});'"
				newR    += 						" style='width:70px;border-width:1px;border-style:none;text-align:center'/>】";
				newR    +=  "</td>\n";
				newR    +=  "<td>\n";
				newR    +=   "<select class='select' name='dayWorkTimes["+index+"].timetype'></select>";
				newR    +=  "</td>\n";
				newR    +=  "<td align='center'>\n";
				newR    +=   "<a href='javascript:;' style='color:blue;font-weight:normal' onclick='deleteWTRow(this)'>删除</a>";
				newR    +=  "</td>\n";
				newR    += "</tr>\n";
				$("#workTimeTable").append(newR);
				var tempSel = $("#workTimeTable select[name='dayWorkTimes["+index+"].timetype']")[0];
				var realSel = $("#TimeTypeSelect")[0];
				for(var i=0;i<realSel.options.length;i++){
					tempSel.options.add(new Option(realSel.options[i].text,realSel.options[i].value));
				}
			});
		}
		function deleteWTRow(source){
			document.getElementById("workTimeTable").deleteRow(source.parentNode.parentNode.rowIndex);
		}
		function saveCa(){
			document.forms[0].submit();
		}
	</script>
  </head>
  <body>
	  	<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">当前位置：工作日历修改
					</span></span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<eoms:select name="TimeTypeSelect" dataDicTypeCode="workTimeType"/>
				<form action="${ctx}/workCalendar/workCalendarSave.action" method="post">
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						<legend>工作日历</legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top" style="border-style:none">
							<input type="hidden" name="workCalendar.pid" value="${workCalendar.pid }"/>
							<input type="hidden" name="workCalendar.dateseconds" value="${workCalendar.dateseconds }"/>
							<input type="hidden" name="workCalendar.createtime" value="${workCalendar.createtime }"/>
							<input type="hidden" name="workCalendar.calendartype" value="${workCalendar.calendartype }"/>
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%"><eoms:lable name="opt_lb_workCalType"/>：</td>
									<td style="width:35%">
										<eoms:select name="tempField1" style="select" dataDicTypeCode="workCalendarType" 
											value="${workCalendar.calendartype}"/>
									</td>
									<td class="texttd" width="15%"><eoms:lable name="opt_lb_workDateStr" />：</td>
									<td width="35%">
									  	<input type="text" name="workCalendar.datestring" class="textInput" value="${workCalendar.datestring}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td class="texttd"><eoms:lable name="opt_lb_isHoliday"/>：</td>
									<td>
										<eoms:select name="workCalendar.isholiday" style="select" dataDicTypeCode="isdefault" 
											value="${workCalendar.isholiday}" isnull="false"/>
									</td>
									<td class="texttd"><eoms:lable name="opt_lb_holidayType" />：</td>
									<td>
									  	<eoms:select name="workCalendar.holidaytype" style="select" dataDicTypeCode="holidayType" 
											value="${workCalendar.holidaytype}"/>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style" id="workTimeArea" style="display:${workCalendar.isholiday==1?'none':'block'}">
						<legend>工作时间【<a href="javascript:;" id="workTimeAddHref" style="color:blue;font-weight:normal">增加</a>】</legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top" style="border-style:none">
							<table class="tableborder" id="workTimeTable">
								 <tr>
								 	<th style="font-weight:normal">工作时段</th>
								 	<th style="width:33.3%;font-weight:normal">时段类型</th>
								 	<th style="width:33.3%;font-weight:normal">操作</th>
								 </tr>
								 <c:forEach items="${dayWorkTimes}" var="wt" varStatus="sta">
								 	<tr>
								 		<td>
								 			<input type="hidden" name="dayWorkTimes[${sta.index}].pid" value="${wt.pid }"/>
								 			<input type="hidden" name="dayWorkTimes[${sta.index}].createtime" value="${wt.createtime }"/>
								 			<input type="hidden" name="dayWorkTimes[${sta.index}].datestring" value="${wt.datestring }"/>
								 			
											【<input type="text" name="dayWorkTimes[${sta.index}].starttimeShow" value="${wt.starttimeShow}"
												onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"
												style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
											&nbsp;到
											【<input type="text" name="dayWorkTimes[${sta.index}].endtimeShow" value="${wt.endtimeShow}"
												onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"
												style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										</td>
										<td>
											<eoms:select style="select" name="dayWorkTimes[${sta.index}].timetype" dataDicTypeCode="workTimeType" value="${wt.timetype}"/>
										</td>
										<td align="center">
											<a href="javascript:;" style="color:blue;font-weight:normal" onclick="deleteWTRow(this)">删除</a>
										</td>
								 	</tr>
								 </c:forEach>
							</table>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save" />" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="saveCa()"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" id="cancelButton"/>
		</div>
  </body>
</html>
