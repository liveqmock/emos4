<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<script language="javascript">
			$(function() {
				$("#reviewtime").bind("propertychange", function() { 
					addEleInfo(); 
				}); 
			});
		
			window.onresize = function()
			{
				setCenter(0,30);
			}
			window.onload = function()
			{
				setCenter(0,30);
				
				var result = '<%=request.getAttribute("result")%>';
				if(result == 'success'){
					window.opener.document.getElementById('form1').submit();
					window.close();
				}
			}
			
		
		//保存配置信息
		function configSave(){
			var vali =  Validator.Validate(document.getElementById('configForm'),1);
			if(vali){
				var ids = '${ids}';
				$('#configForm').submit();
			}
		}
		
		function getCurrentTime(returnType){
		 	var now= new Date();
			var year=now.getYear();
			var month=now.getMonth()+1;
			var day=now.getDate();
			var hour=now.getHours();
			var minute=now.getMinutes();
			if(month<10){
				month='0'+month;
			}
			if(minute<10){
				minute='0'+minute;
			}
			var second=now.getSeconds();
			if(returnType == 1){
				return year+""+month+""+day;
			}else{
				return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
			}
			
		}
		//dom全部加载完，给批次号字段赋值
// 		$(function() {
// 			var now = getCurrentTime(1);
// 			var batch_no = "PC_"+now;
// 			if($("#batch_no").val() == ""){
// 				$("#batch_no").val(batch_no);
// 			}
// 		});
		
		function addEleInfo(){
			var latestreviewtime=$("#reviewtime").val().split(' ')[0]+" 08:30:00";
			$("#latestreviewtime").val(latestreviewtime);
			var intoStepTimeL = (new Date($("#reviewtime").val().replace(new RegExp("-","gm"),"/"))).getTime()/1000; //得到秒数
			var url = ctx + "/business/getWorkDayJson.action";
			$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","intoStepTimeL":intoStepTimeL},function(data){
				var dt = eval(data);
				$.each(dt,function(i,item){
					var fill_res = item.res+" 08:30:00";
					$("#lastaccepttime").val(fill_res);
				});
			});
		}
		</script>
</head>
<body>
<form action="${ctx}/business/saveChgBatchConfig.action"  id="configForm" method="post">
	<input type="hidden" name="ids" value="${batchConMap.PID}" />
	<div class="content">
		<div class="page_div_bg">
			<div class="page_div">
				<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="configSave()" text="com_btn_save" operate="com_save"/>
  	 		</div>
		</div>
		<div class="add_scroll_div_x" id="center">
			<fieldset class="fieldset_style">
				<legend>批次信息</legend>
				<div class="blank_tr"></div>
				<table class="add_user">
					<tr>
						<td class="texttd" style="width:15%">批次号：<span class="must">*</span></td>
						<td style="width:35%">
							<input type="text" id="batch_no" name='batchConMap.batch_no' value="${batchConMap.BATCH_NO}"  maxlength="254" style="width: 96%;" />
							<validation id="batchConMap.batch_noV" dataType="Custom" regexp="^.{1,1000}$" msg="批次号不能为空" />
						</td>
						<td style="width:15%"></td>
						<td style="width:35%"></td>
					</tr>
					<tr>
						<td class="texttd">变更开始时间：<span class="must">*</span></td>
						<td>
							<input type="text" id="chg_start_time" name='batchConMap.chg_start_time' value="${batchConMap.CHG_START_TIME}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" maxlength="2000" style="width: 96%;" />
							<validation id="batchConMap.chg_start_timeV" dataType="Custom" regexp="^.{1,1000}$" msg="变更开始时间不能为空" />
						</td>
						<td class="texttd">变更结束时间：<span class="must">*</span></td>
						<td>
							<input type="text" id="chg_end_time" name='batchConMap.chg_end_time' value="${batchConMap.CHG_END_TIME}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" maxlength="2000" style="width: 96%;" />
							<validation id="batchConMap.chg_end_timeV" dataType="Custom" regexp="^.{1,1000}$" msg="变更结束时间不能为空" />
						</td>
					</tr>
					<tr>
						<td class="texttd">评审会时间：<span class="must">*</span></td>
						<td>
							<input type="text" id="reviewtime" name='batchConMap.reviewtime' value="${batchConMap.REVIEWTIME}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" maxlength="2000" style="width: 96%;" />
							<validation id="batchConMap.reviewtimeV" dataType="Custom" regexp="^.{1,1000}$" msg="评审会时间不能为空" />
						</td>
						<td class="texttd">最晚参与评审时间：<span class="must">*</span></td>
						<td>
							<input type="text" id="latestreviewtime" name='batchConMap.latestreviewtime' value="${batchConMap.LATESTREVIEWTIME}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" maxlength="2000" style="width: 96%;" />
							<validation id="batchConMap.latestreviewtimeV" dataType="Custom" regexp="^.{1,1000}$" msg="最晚参与评审时间不能为空" />						
						</td>
					</tr>
					<tr>
						<td class="texttd">最晚受理时间：<span class="must">*</span></td>
						<td>
							<input type="text" id="lastaccepttime" name='batchConMap.lastaccepttime' value="${batchConMap.LASTACCEPTTIME}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  maxlength="2000" style="width: 96%;" />
							<validation id="batchConMap.lastaccepttimeV" dataType="Custom" regexp="^.{1,1000}$" msg="最晚受理时间不能为空" />						
						</td>
						<td class="texttd">规则开关：<span class="must">*</span></td>
						<td>
							<select id="exec_flag" name="batchConMap.exec_flag">  
							  <option value ="开启受理" ${batchConMap.EXEC_FLAG eq "开启受理" ? "selected" : ""}>开启受理</option>  
							  <option value ="停止受理" ${batchConMap.EXEC_FLAG eq "停止受理" ? "selected" : ""}>停止受理</option>
							</select> 
							<validation id="batchConMap.exec_flagV" dataType="Custom" regexp="^.{1,1000}$" msg="规则开关不能为空" />	
						</td>
						
					</tr>
					<tr>
						
						<td class="texttd">是否活动：<span class="must">*</span></td>
						<td>
							<select id="active" name="batchConMap.active">  
							  <option value ="1" ${batchConMap.ACTIVE eq "1" ? "selected" : ""}>活动</option>  
							  <option value ="0" ${batchConMap.ACTIVE eq "0" ? "selected" : ""}>非活动</option>
							</select> 
							<validation id="batchConMap.active" dataType="Custom" regexp="^.{1,1000}$" msg="是否活动不能为空" />	
						</td>
						<td></td><td></td>
					</tr>
					<tr>
						<td class="texttd">规则操作日志：</td>
						<td colspan="3">
							<textarea rows="8" cols="3" maxlength="4000" style="width: 97%;" disabled="disabled">${batchConMap.Z_LOG}</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</form>
</body>
</html>
