<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/common/style/blue/css/detail.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<title>班次新增</title>
		<script src="${ctx}/common/javascript/main.js"></script>
		<script language="javascript">
			window.onresize = function() {
	         	setCenter(0,55);
            }
            window.onload = function() {
	          setCenter(0,55);
             }
            
            // 空值判断
            function checkNull(val, name) {
            	if (val == null || val == "") {
					alert(name+"不能为空！");
					return false;
				}
            }
            
           	function returnValues(){
           		var shiftValue = document.getElementById("shiftname").value;//班次名称
           		if (false == this.checkNull(shiftValue, "班次名称")) {
					return false;
				}
           		var dateType = document.getElementById("dutyShift.datetype").value;//日期类型
				var starttime = $("#starttime").val();//班次开始时间
				if (false == this.checkNull(starttime, "开始时间")) {
					return false;
				}
				
				var endtime = $("#endtime").val();//班次结束时间
				if (false == this.checkNull(endtime, "结束时间")) {
					return false;
				}
			    var isnextday = $("#isnextday").val();
			    if (isnextday==0 && starttime>=endtime){
			        alert("开始时间必须小于结束时间！");
			        return false;
			    }
				
				var orderno = $("#orderno").val();//班次顺序
				if (false == this.checkNull(orderno, "班次顺序")) {
					return false;
				}
				var orgId = $("#orgId").val();//岗位ID
				var shiftId = $("#shiftId").val();//班次ID
				// 校验是否被重复定义
				$.post("checkDutyShift.action", // 服务器页面地址
				{
					pid : orgId,
					shiftInfo : shiftId+","+shiftValue+","+dateType+","+orderno
				}, function(date) {
			      	if (date == '2') {
				       alert("同一日期类型下班次顺序号不能重复！");
				       return false;
			      	} else {
				        var form = $("#form");
						form.submit();
			       }
				});
			}
			//选择时间段
			function getTime(){
				var orgId = document.getElementById('orgId').value;
  				var dutytype  = document.getElementById("dutyShift.shiftValue").value;
  				var datetype = document.getElementById("dutyShift.datetype").value;
				showModalDialog('${ctx}/dutyConfigure/shiftTimeList.action?orgId='+orgId+'&st.dutytypeStr='+dutytype+'&st.datetypeStr='+datetype,window,'help:no;center:true;scroll:no;status:no;dialogWidth:600px;dialogHeight:400px');
			}
			
			function clearTime() {
				$("#starttime").val("");
				$("#endtime").val("");
				$("#isnextday").val("");
			}
		</script>
		<style>
			.localStyle {
				text-align:left;
				width:130px;
				padding-left:10px;
			}
		</style>
	</head>
	<body>
		<form id="form" action="saveDutyShift.action" method="post">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">班次新增</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div3" id="center">
				<fieldset class="fieldset_style">
					<legend>
						班次信息
					</legend>
					<table class="add_table">
						<tr>
							<td class="add_table_left">
								班次名称：
							</td>
							<td class="add_table_right">
								<input type="hidden" id="isOffConfirm" name="dutyShift.isOffConfirm" value="0"/>
								<input type="hidden" id="shiftId" name="dutyShift.pid" value="${dutyShift.pid }" />
								<input type="hidden" id="shiftTimeId" name="dutyShift.shiftTimeId"/>
								<input type="hidden" id="orgId" name="dutyShift.orgId" value="${orgId }"/>
								<input type="text" id="shiftname" name="dutyShift.shiftname"
										onblur="this.className='blur'" maxlength="6" value="${dutyShift.shiftname }"
									onfocus="this.className='focus'" class="blur" style="width: 130px;"/><span class="must">*</span>
							</td>
							<td class="add_table_left">
								日期类型：
							</td>
							<td class="add_table_right">
								<eoms:select name="dutyShift.datetype" style="localStyle" dataDicTypeCode="dateType" value="${dutyShift.datetype }" isnull="false" onChangeFun="clearTime();"/>
							</td>
						</tr>
						<tr>
							<td class="add_table_left">
								<span class="label">开始时间： </span>
							</td>
							<td class="add_table_right">
								<input type="text" id="starttime" name="dutyShift.starttime" class="blur" readonly="readonly"
									value="${dutyShift.starttime }"  onfocus="WdatePicker({dateFmt:'HH:mm:ss',autoPickDate:true});this.className='focus'" style="width: 130px;"/><span class="must">*</span>
							</td>
							<td class="add_table_left">
								<span class="label">结束时间： </span>
							</td>
							<td class="add_table_right">
								<input type="text" id="endtime" name="dutyShift.endtime" class="blur" readonly="readonly"
									value="${dutyShift.endtime }" onfocus="WdatePicker({dateFmt:'HH:mm:ss',autoPickDate:true});this.className='focus'" style="width: 130px;"/><span class="must">*</span>
							</td>
						</tr>
						<tr>
							<td class="add_table_left">
								<span class="label">是否跨天：</span>
							</td>
							<td class="add_table_right">
								<select id="isnextday" name="dutyShift.isnextday" style="width: 130px;">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							<td class="add_table_left">
								<span class="label">是否启用：</span>
							</td>
							<td class="add_table_right">
								<select id="hideflag" name="dutyShift.hideflag" style="width: 130px;">
									<option value="0">是</option>
									<option value="1">否</option>
								</select>
							</td>
						</tr>
						
						<tr style="display: none;">
							<td class="add_table_left">
								<span class="label">是否参与交接班：</span>
							</td>
							<td class="add_table_right">
								<select id="workflag" name="dutyShift.workflag" style="width: 130px;">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							<td class="add_table_left">
								<span class="label">人数：</span>
							</td>
							<td class="add_table_right">
								<input type="text" id="pernum" name="dutyShift.pernum" class="blur" value="0"
									onchange="this.value=this.value.replace(/[^\d]/g,'')" style="width: 130px;"/><span class="must">*</span>
							</td>
						</tr>
						<tr>
						<td class="add_table_left">
								<span class="label">是否交班审核：</span>
							</td>
							<td class="add_table_right">
								<select id="isapprove" name="dutyShift.isapprove" style="width: 130px;">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							<td class="add_table_left">
								<span class="label">班次顺序号：</span>
							</td>
							<td class="add_table_right">
								<input type="text" id="orderno" name="dutyShift.orderno" class="blur" value="0"
									onchange="this.value=this.value.replace(/[^\d]/g,'')" style="width: 130px;"/><span class="must">*</span>
							</td>
						</tr>
						<tr>
						<!-- 
							<td class="add_table_left">
								<span class="label">是否需要交班确认：</span>
							</td>
							<td class="add_table_right">
								<select id="isOffConfirm" name="dutyShift.isOffConfirm" style="width: 130px;">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							 -->
							<td class="add_table_left">
								<span class="label">是否审核后下一班才能接班：</span>
							</td>
							<td class="add_table_right">
								<select id="isApproveAccept" name="dutyShift.isApproveAccept" style="width: 130px;">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div class="add_bottom">
				<input type="button" name="save_button" id="save_button" value="<eoms:lable name='com_btn_save'/>"
					class="save_button"	onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="returnValues();" />
				<input type="button" name="cancel_button" id="cancel_button" value="<eoms:lable name='com_btn_close'/>"
					class="cancel_button" onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
		</form>
	</body>
</html>
