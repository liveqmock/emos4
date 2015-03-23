<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<title>交班</title>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,30);
			}
			window.onload = function() {
				setCenter(0,30);
			}
		</script>
		<script type="text/javascript">
		   function offWork(){
		   		//校验交班人的工作是否已经全部完成
		   		//注释交接班时间控制
		   		$.ajax({
					url: 'isCanOffWork.action',
					type: 'POST',
					dataType: 'text',
					data:'calendarId=${calenBean.calendarId}',
					success: function(returnMessage){
						if (returnMessage == '') {
					    	$.ajax({
								url: 'offWorkCheck.action',
								type: 'POST',
								dataType: 'text',
								data:'calendarId=${calenBean.calendarId}&organizationId=${calenBean.organizationId}',
								success: function(returnMessage){
									if (returnMessage == '') {
								    	var form = $("#form");
										form.submit();
								    } else {
								    	alert(returnMessage);
								    	return false;
								    }
								}
							});
					    } else {
					    	alert(returnMessage);
					    	return false;
					    }
					}
				});
		   }
		</script>
	</head>
	<body>
		<form name="form" id="form" action="saveOffWork.action" method="post">
			<input type="hidden" id="calendarId" name="calenBean.calendarId" value="${calendarId}"/>
		    <input type="hidden" id="organizationId" name="calenBean.organizationId" value="${orgid}"/>
			<div class="content">
		    <div class="scroll_div" id="center">
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style">
					<legend>
						交班>>关注、遗留
					</legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div id='work_t_0' class="tab_hide">
							<span><a href="${ctx }/dutyLogMessage/getYlTaskList.action?calendarId=${calendarId}&orgId=${orgid}" target="taskInfo">遗留/关注任务</a></span>
						</div>
					</div>
					<div style="height: 2px;"></div>
						<iframe id="iframeCal" name="taskInfo" src="${ctx }/dutyLogMessage/getYlTaskList.action?calendarId=${calendarId}&orgId=${orgid}" 
							frameborder="0" scrolling="auto" width="100%" height="450px" allowTransparency="true">
						</iframe>
				</fieldset>
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style">
					<legend>
						交班信息
					</legend>
					<table class="tableborder">
						<tr>
							<td>交班人</td>
							<td>交班说明</td>
							<td>经理审核说明</td>
						</tr>
						<c:forEach var="work" items="${ducyCalendar.dutyWorks}">
							<tr>
								<td><eoms:user userId="${work.userid}"/></td>
								<td>${work.lastproblem }</td>
								<td>${work.comments }</td>
							</tr>
						</c:forEach>
					</table>
				</fieldset>
			</div>
			<div class="add_bottom">
				<input type="button" class="cancel_button" value="<eoms:lable name='com_btn_cancel'/>"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
            </div>
		</form>
	</body>
</html>