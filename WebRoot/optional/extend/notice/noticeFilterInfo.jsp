<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<title>当前位置：短信过滤</title>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FRAME_RIGHT);
		}
		
		window.onload = function() {
			setCenter(0, LAYOUT_FRAME_RIGHT);
			initInfo();
		}
		function initInfo() {
			var loginname = '${loginName}';
			if(loginname == ''){
				alert("登录名获取失败，以下的配置将无效！");
			}
			else{
				var saveResult = '${saveResult}';
				if(saveResult == 'true') {
					alert('保存成功!');
				} else if(saveResult == 'false') {
					alert('保存失败!');
				}
				if($('#workdaystarttime').val() == '') $('#workdaystarttime').val('00:00');
				if($('#workdayendtime').val() == '') $('#workdayendtime').val('23:59');
				if($('#holidaystarttime').val() == '') $('#holidaystarttime').val('00:00');
				if($('#holidayendtime').val() == '') $('#holidayendtime').val('23:59');
			}
			
		}
		function panduan(){
			var loginname = '${loginName}';			
			if(loginname==''){
				alert("登录名获取失败，以下的配置将无效！");
				return false;
			}else{
				var workst = document.getElementById('workdaystarttime').value;
				var worksed = document.getElementById('workdayendtime').value;
				var holidayst = document.getElementById('holidaystarttime').value;
				var holidayen = document.getElementById('holidayendtime').value;
				if(workst==''){
					alert("工作开始时间不能为空！");
					document.getElementById("workdaystarttime").focus();
					return false;
				}
				
				if(worksed == '') {
					alert("工作结束时间不能为空！");
					document.getElementById("workdayendtime").focus();
					return false;
				}
				if(holidayst == '') {
					alert("节假日开始时间不能为空！");
					document.getElementById("holidaystarttime").focus();
					return false;
				}
				if(holidayen == '') {
					alert("节假日结束时间不能为空！");
					document.getElementById("holidayendtime").focus();
					return false;
				}
				else{
					return true;
				}
			}
		}
		function formSubmit() {
			if(!panduan()){
				return false;
			}
			smsFilterForm.submit();		
		}
		
	</script>
</head>
<body>
	<form action="${ctx}/noticeFilter/noticeFilterSave.action" name="smsFilterForm" id="smsFilterForm" method="post">		
		<input type="hidden" id="noticeFilter.pid" name="noticeFilter.pid" value="${noticeFilter.pid}" />
		<input type="hidden" id="noticeFilter.businesstype"  name="noticeFilter.businesstype"  value="${noticeFilter.businesstype}"/>
		<input type="hidden" id="noticeFilter.createtime" name="noticeFilter.createtime" value="${noticeFilter.createtime}" />
		<input type="hidden" id="noticeFilter.businesscondition" name="noticeFilter.businesscondition" value="${noticeFilter.businesscondition}" />
		<input type="hidden" id="noticeFilter.checktype" name="noticeFilter.checktype" value="${noticeFilter.checktype}" />
		<input type="hidden" id="noticeFilter.checkparam" name="noticeFilter.checkparam" value="${noticeFilter.checkparam}" />
		<input type="hidden" id="noticeFilter.ordernum" name="noticeFilter.ordernum" value="${noticeFilter.ordernum}" />
		<input type="hidden" id="noticeFilter.usertype"   name="noticeFilter.usertype"  value="${noticeFilter.usertype}"/>
		<input type="hidden" name="noticeFilter.usermark" id="noticeFilter.usermark" value="${noticeFilter.usermark}"/>
		<input type="hidden" name="noticeFilter.businessmark" id="noticeFilter.businessmark"  value="${noticeFilter.businessmark}"/>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'"  onclick="formSubmit();" onmouseout="this.className='page_add_button'" text="com_btn_save"/>
		 	 	</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>短信接收配置</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
						<!--  <tr>
								<td style="width:20%">登录名：</td>
								<td style="width:80%">
									<input type="text" name="noticeFilter.usermark" id="usermark" style="width:266px" readonly="true" value="${noticeFilter.usermark}"/>									
								</td>
							</tr>
							<tr>
								<td>工单类型：</td>
								<td>
									<input type="text" name="noticeFilter.businessmark" id="noticeFilter.businessmark" style="width:266px" readonly="true" value="${noticeFilter.businessmark}"/>
								</td>
							</tr>
				  		-->	
							<tr>
								<td>工作日时间段：<span class="must">*</span></td>
								<td>
									<input type="text" name="noticeFilter.workdaystarttime" id="workdaystarttime" style="width:120px" readonly="true" value="${noticeFilter.workdaystarttime}" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									至
									<input type="text" name="noticeFilter.workdayendtime" id="workdayendtime" style="width:120px" readonly="true" value="${noticeFilter.workdayendtime}" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
								</td>
							</tr>
							<tr>
								<td>节假日是否接收：<span class="must">*</span></td>
								<td>
									<input type="radio" name="noticeFilter.holidaystatus" <c:if test="${noticeFilter.holidaystatus==1}">checked</c:if> value="1"/>是
									<input type="radio" name="noticeFilter.holidaystatus" <c:if test="${noticeFilter.holidaystatus!=1}">checked</c:if> value="0"/>否
								</td>
							</tr>	
							<tr>
								<td>节假日时间段：<span class="must">*</span></td>
								<td>
									<input type="text" name="noticeFilter.holidaystarttime" id="holidaystarttime" style="width:120px" readonly="true" value="${noticeFilter.holidaystarttime}" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									至
									<input type="text" name="noticeFilter.holidayendtime" id="holidayendtime" style="width:120px" readonly="true" value="${noticeFilter.holidayendtime}" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
								</td>
							</tr>
							<tr>
								<td>是否补发：<span class="must">*</span></td>
								<td>
									<input type="radio" name="noticeFilter.isresend" <c:if test="${noticeFilter.isresend!=0}">checked</c:if> value="1"/>是
									<input type="radio" name="noticeFilter.isresend" <c:if test="${noticeFilter.isresend==0}">checked</c:if> value="0"/>否									
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
