<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_subt_addOrupdateDutyOrder"/></title>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0,61);
		}
		window.onload = function() {
			setCenter(0,61);
		}
		
		function getSelectText() {
	        sel = document.getElementById('smsOrderDuty.dutyroomid');
	        text = sel.options[sel.selectedIndex].text;
	        document.getElementById('smsOrderDuty.dutyroomname').value = text;
		}
		
		
		function formSubmit()
		{
			if(Validator.Validate(document.forms[0],2))
				document.forms[0].submit();
		}
	</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2"><eoms:lable name="sm_subt_addOrupdateDutyOrder"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_subt_dutyOrderInfo"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
							<table class="add_user">
							 <form action="${ctx}/smOrderDuty/addOrderDuty.action" method="post" name="addOrderDuty" >
							 	<input type="hidden" name="smsOrderDuty.dutyroomname" value="${smsOrderDuty.dutyroomname}"/>
								<c:if test="${smsOrderDuty.pid!=null}">
									<input type="hidden" id="smsOrderDuty.pid" name="smsOrderDuty.pid" value="${smsOrderDuty.pid}"/>
								</c:if>
								<tr>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_dutyRoom"/>：
									</td>
									<td width="35%">
										<eoms:select name="smsOrderDuty.dutyroomid" value="${smsOrderDuty.dutyroomid}" customSql="select pid, organizationname from bs_t_Duty_Organization where organizationname >'0' and state = '1'" onChangeFun="getSelectText();"></eoms:select>
										<validation id="smsOrderDuty.dutyroomidV" dataType="Require" msg="<eoms:lable name="sm_msg_dutyRoomNotNullConstraint"/>！" />
									</td>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_aheaddaynum"/>：
									</td>
									<td width="35%">
										<input type="text" id="smsOrderDuty.aheaddaynum" name="smsOrderDuty.aheaddaynum" value="${smsOrderDuty.aheaddaynum}" class="textInput" />
										<validation id="smsOrderDuty.aheaddaynumV" dataType="Range" min="0" max="999" msg="<eoms:lable name="sm_msg_aheaddaynumConstraint"/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_reciverstarttime"/>：
									</td>
									<td>
										<input type="text" name="smsOrderDuty.starttime" id="starttime" class="textInput" maxlength="8"
											onfocus="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});this.className='focus'"
											readonly="readonly" style='width:96%' value="${smsOrderDuty.starttime}" />
										<validation id="smsOrderDuty.starttimeV" dataType="Require" msg="<eoms:lable name="sm_msg_starttimeIsNullConstraint"/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="sm_lb_sendendtime"/>：
									</td>
									<td>
										<input type="text" name="smsOrderDuty.endtime" id="endtime" class="textInput" maxlength="8"
											onfocus="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});this.className='focus'"
											readonly="readonly" style='width:96%' value="${smsOrderDuty.endtime}" />
										<validation id="smsOrderDuty.endtimeV" dataType="Require" msg="<eoms:lable name="sm_msg_endtimeIsNullConstraint"/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_isholidayactive"/>：
									</td>
									<td>
										<select id="smsOrderDuty.isholiday" name="smsOrderDuty.isholiday" class="select">
											<c:choose>
												<c:when test="${smsOrderDuty.isholiday==1}">
														<option value="1" selected><eoms:lable name="wf_version_start"/></option>
														<option value="2"><eoms:lable name="wf_version_stop"/></option>
												</c:when>
												<c:when test="${smsOrderDuty.isholiday==0}">
														<option value="1"><eoms:lable name="wf_version_start"/></option>
														<option value="2" selected><eoms:lable name="wf_version_stop"/></option>
												</c:when>
												<c:otherwise>
														<option value="1"><eoms:lable name="wf_version_start"/></option>
														<option value="2" selected><eoms:lable name="wf_version_stop"/></option>
												</c:otherwise>
											    </c:choose>
										</select>
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_status"/>：
									</td>
									<td>
										<select id="smsOrderDuty.status" name="smsOrderDuty.status" class="select">
											<c:choose>
												<c:when test="${smsOrderDuty.status==1}">
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:when test="${smsOrderDuty.status==0}">
														<option value="1"><eoms:lable name="com_btn_inuse" /></option>
														<option value="0" selected><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:otherwise>
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:otherwise>
											    </c:choose>
										</select>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										&nbsp;
									</td>
								</tr>
							</table>
						</form>
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
	  
	</body>
</html>
