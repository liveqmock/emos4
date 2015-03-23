<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../common/style/blue/css/portal.css" />
		<link type="text/css" rel="stylesheet"
			href="${ctx}/common/style/blue/css/main_popup.css" />
		<link type="text/css" rel="stylesheet"
			href="${ctx}/common/style/blue/css/popup.css" />
		<script src="../../javascript/main.js"></script>
		<script language="javascript">
		//打开任务详细信息
		function openExecBooking(bookingExecId,bookingtype,versionId,formId){
			if(bookingtype == 1){
				window.open("openExecBookingView.action?bookingExecId="+bookingExecId+"&isExecUser=false");
			}else if(bookingtype == 2){
				window.open("openCycleTaskBooking.action?bookingExecId="+bookingExecId+"&bookingtype="+bookingtype+"&versionId="+versionId+"&formId="+formId);
			}
		}
		</script>
		<title>foot_platform</title>
	</head>
	<body>
		<div class="title_right">
		      <div class="title_left">
		        <span class="title_bg">
		          <span class="title_icon_workplat">任务预约</span>
		        </span>
		        <span class="title_xieline"></span>
		        <span class="title_more" onclick="window.open('execCurrentTaskBookingList.action')"></span>
		      </div>
		</div>
		<div class="content">
			<div class="title_right">
				<div style="padding-top:4px;">
					${startTime}
					&nbsp;&nbsp;&nbsp;
					${nongLi}
				</div>
			</div>
			<div class="scroll_div" id="center">
				<div style="height:142px;">
					<table>
						<c:forEach items="${taskBookingExecList}" var="taskBooking">
							<tr style="cursor:hand">
								<td width="5%">
									<img src="${ctx}/common/style/blue/images/title/tag1.png"/>
								</td>
								<td onclick="openExecBooking('${taskBooking.pid}','${taskBooking.bookingtype}','${taskBooking.version}','${taskBooking.formId}');">
									[
									<c:if test="${taskBooking.informtype!=null}">
										<c:if test="${taskBooking.informtype==1}">首页弹出</c:if>
										<c:if test="${taskBooking.informtype==2}">公告</c:if>
										<c:if test="${taskBooking.informtype==3}">邮件</c:if>
										<c:if test="${taskBooking.informtype==4}">短信</c:if>
										<c:if test="${taskBooking.informtype==5}">通用任务工单</c:if>
									</c:if>
									]${taskBooking.title}
								</td>
								<td onclick="openExecBooking('${taskBooking.pid}','${taskBooking.bookingtype}','${taskBooking.version}','${taskBooking.formId}');">
									<c:if test="${taskBooking.bookingtype == 1}">
										单点预约
									</c:if>
									<c:if test="${taskBooking.bookingtype == 2}">
										周期预约
									</c:if>
								</td>
								<td onclick="openExecBooking('${taskBooking.pid}','${taskBooking.bookingtype}','${taskBooking.version}','${taskBooking.formId}');">
									<c:if test="${taskBooking.status == 1}">
										未完成
									</c:if>
									<c:if test="${taskBooking.status == 2}">
										已完成
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div style="height:20px;padding-left:10px;padding-bottom:12px;">
				<table>
				<tr style="cursor:hand">
					<td>
						<div class="operateArea_content3">
						    <span style="color:blue;cursor:hand" onclick="window.location.href('footPlatForm.action?isToday=BACK&currentDate=${currentDate}&tasksort=${tasksort}');"><u>前一天</u></span>
						    <span style="color:blue;cursor:hand" onclick="window.location.href('footPlatForm.action?isToday=TODAY&currentDate=${currentDate}&tasksort=${tasksort}');"><u>今天</u></span>
						    <span style="color:blue;cursor:hand" onclick="window.location.href('footPlatForm.action?isToday=NEXT&currentDate=${currentDate}&tasksort=${tasksort}');"><u>后一天</u></span>
						</div>
					</td>
					 <td width="145px" style="padding:0px; margin:0px;">
						<div class="page_div_right">
						</div>
					</td>
					<td style="padding-right:0px; margin:0px;">
						<div class="operateArea_content3">
						    <span style="color:blue;cursor:hand" onclick="window.open('addTask.action');"><u>新  建</u></span>
						</div>
					</td>
				</tr>
				</table>
			</div>
		</div>
	</body>
</html>
