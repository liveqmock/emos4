<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>事件规则添加修改</title>
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
		function openTree(type, input_id, input_name)
		{
			if("1"==type)
			{//通知人员树
				var selectData = 'U:' + document.getElementById(input_id).value;
				showModalDialog('${ctx}/common/tools/orgtree/organizaTree.jsp?sltType=1&form_name=euform&user_name='+input_name+'&user_id='+input_id+'&dep_name=&dep_id=&sltData='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
			}
			if("0"==type)
			{//通知部门树
				var selectData = 'D:' + document.getElementById(input_id).value;
				showModalDialog('${ctx}/common/tools/orgtree/organizaTree.jsp?sltType=0&form_name=euform&user_name=&user_id=&dep_name='+input_name+'&dep_id='+input_id+'&sltData='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:285px;dialogHeight:490px');
			}
		}
		//打开组件选择列表页面
		function openComponentList(){		
			window.showModalDialog('${ctx}/eventHandlerComp/selEventHandlerComp.action',window,'help:no;scroll:no;status:no;dialogWidth:600px;dialogHeight:400px');
		}
		//清空文本框里的内容
		function clearContext(clearIds){
			alert(clearIds);
			alert(document.getElementById(clearIds).value);
			document.getElementById(clearIds).value = '';
			document.getElementById(clearIds + 'id').value = '';
		}
		function setHandler() {
			// 通知对象
			var nuserId = $('#notuserid').val();
			var ndepId = $('#notdepid').val();
			var nroleId = $('#notroleid').val();
			var notid1 = '';
			var notid2 ='';
			var notid3 ='';
			if(nuserId!=''){
					notid1 = 'U:' + $('#notuserid').val()+';';
			}else{
			    	notid1 = '';
			}
			if(ndepId!=''){
					notid2 = 'D:' + $('#notdepid').val()+';'
			}else{
					notid2 ='';
			}
			if(nroleId!=''){
					notid3 = 'R:' + $('#notroleid').val()+';';			
			}else{
					notid3 ='';
			}
			var notid = notid1 + notid2 + notid3;
		
			var notUser = $('#notuser').val();
			var notDep = $('#notdep').val();
			var notRole = $('#notRole').val();
			var notUser1 = '';
			var notDep1 = '';
			var notRole1 ='';
			if(notUser!=''){
				notUser1 = $('#notuser').val()+';';
			}else{
			    notUser1 = '';
			}
			if(notDep!=''){
				notDep1 = $('#notdep').val()+';'
			}else{
				notDep1 ='';
			}
			if(nroleId!=''){
				notRole1 = $('#notRole').val()+';';			
			}else{
				notRole1 ='';
			}
			
			var notname = notUser1+notDep1+notRole1;
		    $('#noticehandlerid').val(notid);
			$('#noticehandler').val(notname);
			
			//抄送对象
			
			
			var copyserId = $('#copyuserid').val();
			var copydepId = $('#copydepid').val();
			var copyroleId = $('#copyroleid').val()
			var copyuserid1 = '';
			var copydepid1 ='';
			var copyroleid1 ='';
			if(copyserId!=''){
				copyuserid1 = 'U:' + $('#copyuserid').val()+';';
			}else{
			    copyuserid1 = '';
			}
			if(copydepId!=''){
				copydepid1 = 'D:' + $('#copydepid').val()+';'
			}else{
				copydepid1 ='';
			}
			if(copyroleId!=''){
				copyroleid1 = 'R:' + $('#copyroleid').val()+';';			
			}else{
				copyroleid1 ='';
			}
			var copyid = copyuserid1 + copydepid1 + copyroleid1;
			
			$('#duplicatehandlerid').val(copyid);
			
			var copyser = $('#copyuser').val();
			var copydep = $('#copydep').val();
			var copyrole = $('#copyrole').val()
			var copyuser1 = '';
			var copydep1 ='';
			var copyrole1 ='';
			if(copyser!=''){
				copyuser1 = $('#copyuser').val()+';';
			}else{
			    copyuser1 = '';
			}
			if(copydepId!=''){
				copydep1 = $('#copydep').val()+';'
			}else{
				copydep1 ='';
			}
			if(copyroleId!=''){
				copyrole1 =  $('#copyrole').val()+';';			
			}else{
				copyrole1 ='';
			}
			var copyname = copyuser1 + copydep1 + copyrole1;
			
			$('#duplicatehandler').val(copyname);			
			//升级对象
			
			var upgradeUserId = $('#upgradeuserid').val();
			var upgradeDepId = $('#upgradedepid').val();
			var upgradeRoleId = $('#upgraderoleid').val();
			var upgradeuserid1 = '';
			var upgradedepid1 ='';
			var upgraderoleid1 ='';
			if(upgradeUserId!=''){
				upgradeuserid1 = 'U:' + $('#upgradeuserid').val()+';';
			}else{
			    upgradeuserid1 = '';
			}
			if(upgradeDepId!=''){
				upgradedepid1 = 'D:' + $('#upgradedepid').val()+';'
			}else{
				upgradedepid1 ='';
			}
			if(upgradeRoleId!=''){
				upgraderoleid1 = 'R:' + $('#upgraderoleid').val()+';';			
			}else{
				upgraderoleid1 ='';
			}
			
			var upgradeid = upgradeuserid1 + upgradedepid1 + upgraderoleid1;
			
			$('#upgradehandlerid').val(upgradeid);
			
	
			var upgradeUser = $('#upgradeuser').val();
			var upgradeDep = $('#upgradedep').val();
			var upgradeRole = $('#upgraderole').val();
			var upgradeuser1 = '';
			var upgradedep1 = '';
			var upgraderole1 = '';
			if(upgradeUser!=''){
				upgradeuser1 = $('#upgradeuser').val()+';';
			}else{
			    upgradeuser1 = '';
			}
			if(upgradeDep!=''){
				upgradedep1 = $('#upgradedep').val()+';'
			}else{
				upgradedep1 ='';
			}
			if(upgradeRole!=''){
				upgraderole1 = $('#copyrole').val()+';';			
			}else{
				upgraderole1 ='';
			}
			var upgradename = upgradeuser1 + upgradedep1 + upgraderole1;
			$('#upgradehandler').val(upgradename);
			
		}
		//判断默认通知参数是否为空
		function judgeNull(){
			var defaultcheck = document.getElementById('eventRule.defaultchecktype').value; 
				if(defaultcheck != '')//判断默认通知类型是否为空，为空时，则默认通知参数不可以为空，反之可以。
				{
					if(document.getElementById('eventRule.defaultcheckparam').value=='')
					{
						alert(" 默认通知参数  此时不能为空！！！");
						return false;
						}else{
							return true;
						}
				    }else{
				         return true;
				    }	
		
		}
		function dynamicTypeNull(){
		    var dynamic = document.getElementById('eventRule.dynamicconchecktype').value;
		    if(dynamic != ''){
		        if(document.getElementById('eventRule.dynamicconcheckparam').value == '')
		              {
		               alert(" 动态查询参数  此时不能为空");
		               return false;
		        } else{
		               return true;
		       }
		    }else{
				         return true;
				    }
		}
		//判断开始时间和是否为空
		function startTime(){
		    var startTimes = document.getElementById('stime').value;  		    
		    if (startTimes != ''){		    
		        $('#noticestarttime').val(startTimes);
		   	}else{
		        alert("开始时间不能为空，默认设置为00:00");
		        var statime = "00:00";
		        $('#noticestarttime').val(statime +'');
		        $('#stime').val(statime +'');		       
		        return false;		    
		    }		
		}
		//判断结束时间是否为空
		function endTime(){		
			var endTimes = document.getElementById('etime').value;		
		    if(endTimes != ''){		    
		        $('#noticeendtime').val(endTimes);		       
		     }else{
		        alert("结束时间不能为空，默认设置为23:59");
		        var endtime = "23:59" ;
		        $('#noticeendtime').val(endtime);
		        $('#etime').val(endtime);		       
		        return false;		    
		    }		
		}
		
		//判读通知次数是否为空
		function notices(){
		var notice = document.getElementById('noticet').value;
			if(notice != ''){
		      $('#noticetimes').val(notice);
			}else{
				alert("通知次数不能为空，默认设置为1");
		        var times = "1" ;
		        $('#noticetimes').val(times);
		        $('#noticet').val(times);		       
		        return false;
		  }}
		 //判断通知次数是否大于1，若大于1，则通知间隔要大于0
		 function noticeinTerval(){
			var notice = document.getElementById('noticet').value;
			var noticeinTervals = document.getElementById('noticeinters').value;		 	
		 	if(notice > 1){
				if(noticeinTervals > 0){
					notTers = noticeinTervals*60;
					$('#noticeinterval').val(notTers);
					return true;
				}else{
					alert("当通知次数大于1时，通知间隔要大于0！！！");
					return false;
		 	    }		    	
			}
			else if(notice == 1){
		 		$('#noticeinterval').val(noticeinTervals);
		 		return true;
		 	}
			else{			 
		        return true;
		     }
		 }
		
		//判断开始有效时间是否小于结束有戏时间
		function panduanDateTime(){
			var stDate = document.getElementById('validstarttime').value;
			var endDate = document.getElementById('validendtime').value;
			if(stDate<endDate)
			{
				return true;
			}else{
				alert("有效开始时间不能大于有效结束时间！");
				return false;
				}
		}
	
		function formSubmit()
		{   		    
			var myform = document.forms[0];				
			notices();
			if(!noticeinTerval()){
				document.getElementById("noticeinters").focus();	
				return false;
			}
			startTime();
			endTime();
			if(!judgeNull()){
				document.getElementById("eventRule.defaultcheckparam").focus();
				return false;
			}
			if(!dynamicTypeNull()){
			    document.getElementById("eventRule.dynamicconcheckparam").focus();
				return false;
			}
			if(!panduanDateTime()){
				document.getElementById("validstarttime").focus();			
				return false;
			}
			if(Validator.Validate(myform, 2)) {
				setHandler(); //设置接收对象
				//计算超时延期时间
				var day = $('#span_day').val();
				var hour = $('#span_hour').val();
				var minute = $('#span_minute').val();
				var timecount = day*24*60*60 + hour*60*60 + minute *60;
				$('#timespan').val(timecount);		    
			    if(timecount==0){
			      alert("提醒超时时间不能为空！")
			      return false;			     
			     }else{
			      myform.submit();
			    }
			}
		}
	</script>
	</head>

	<body>
	 	<form name="euform" action="${ctx}/eventRule/eventRuleSave.action" method="post" onsubmit="return Validator.Validate(this,2);">
			<input type="hidden" id="eventRule.pid" name="eventRule.pid" value="${eventRule.pid}" />
			<input type="hidden" id="noticestarttime" name="eventRule.noticestarttime" value="${eventRule.noticestarttime }" />
			<input type="hidden" id="noticeendtime" name="eventRule.noticeendtime" value="${eventRule.noticeendtime }" />
			<input type="hidden" id="noticetimes" name="eventRule.noticetimes" value="${eventRule.noticetimes }" />
			<input type="hidden" id="eventRule.createtime" name="eventRule.createtime" value="${eventRule.createtime}" />
			<input type="hidden" id="noticehandlerid" name="eventRule.noticehandlerid" value="${eventRule.noticehandlerid}" />
			<input type="hidden" id="noticehandler" name="eventRule.noticehandler" value="${eventRule.noticehandler}" />
			<input type="hidden" id="duplicatehandlerid" name="eventRule.duplicatehandlerid" value="${eventRule.duplicatehandlerid}" />
			<input type="hidden" id="duplicatehandler" name="eventRule.duplicatehandler" value="${eventRule.duplicatehandler}" />
			<input type="hidden" id="upgradehandlerid" name="eventRule.upgradehandlerid" value="${eventRule.upgradehandlerid}" />
			<input type="hidden" id="upgradehandler" name="eventRule.upgradehandler" value="${eventRule.upgradehandler}" />
			<input type="hidden" id="noticealias" name="eventRule.noticealias" value="${eventRule.noticealias}" />
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2">事件规则添加修改</span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend>事件规则信息维护</legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td>
										  规则名称 ：<span class="must">*</span>
									</td>
									<td colspan="3">
										<input type="text" name="eventRule.rulename" id="eventRule.rulename" class="textInput" style="width:98.5%;" value="${eventRule.rulename }"/>
									    <validation id="eventRule.rulenameV" dataType="Limit" min="1" max="75" msg="规则名称 不能为空且不可以超过75个字！" />
									</td>
								</tr>
								<tr>
									<td style="width:15%">
										所属业务类别：
									</td>
									<td style="width:35%">
										<eoms:select name="eventRule.ruletype" style="select" dataDicTypeCode="ruletype"  value="${eventRule.ruletype }" isnull="false"/>
									</td>
									<td style="width:15%">
										通知类型：
									</td>
									<td style="width:35%">
										<eoms:select name="eventRule.noticetype" style="select" dataDicTypeCode="noticetype"  value="${eventRule.noticetype}" isnull="false"/>
										<validation id="eventRule.noticetypeV" dataType="Require" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td>
										通知默认人/组：
									</td>
									<td>
										<eoms:select name="eventRule.isdefaultnotice" style="select" dataDicTypeCode="isdefaultnotice"  value="${eventRule.isdefaultnotice }" isnull="false"/>
									</td>
									<td>
										提前/超期时间：
									</td>
									<td>
										<input type="hidden" name="eventRule.timespan" id="timespan" class="textInput" value="${eventRule.timespan }"/>
										<input type="text" name="span_day" id="span_day" style="width:20%;text-align:right" value="${span_day }" /> 天<span class="must">*</span> <input type="text" name="span_hour" id="span_hour" value="${span_hour }" style="width:20%;text-align:right" /> 时<span class="must">*</span> <input type="text" name="span_minute" id="span_minute" value="${span_minute }" style="width:20%;text-align:right" /> 分<span class="must">*</span>
									    <validation id="span_dayV" dataType="Number" msg="天    不能为空且是数字！" />
									    <validation id="span_hourV" dataType="Number" msg="小时  不能为空且是数字！" />
									    <validation id="span_minuteV" dataType="Number" msg="分    不能为空且是数字！" />
									</td>
								</tr>
								<tr>
									<td>
										默认通知类型：
									</td>
									<td>
										<eoms:select name="eventRule.defaultchecktype"  style="select" dataDicTypeCode="dynamicconchecktype"  value="${eventRule.defaultchecktype }"/>
										
									</td>
									<td>
										默认通知参数：
									</td>
									<td>
										<input type="text" name="eventRule.defaultcheckparam" id="eventRule.defaultcheckparam" class="textInput" value="${eventRule.defaultcheckparam }"/>
									    <validation id="eventRule.defaultcheckparamV" dataType="Limit"  max="150" msg="默认通知参数 不可以超过150个字！" />
									</td>
								</tr>
								<tr>
									<td>
										  通知人员 ：
									</td>
									<td colspan="3">
										<input type="text" name="notuser" id="notuser" class="textInput" readonly="true" style="width:83%" value="${notuser}"/>
										<input type="hidden" name="notuserid" id="notuserid" value="${notuserid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1', 'notuserid', 'notuser');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('notuser');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  通知部门 ：
									</td>
									<td colspan="3">
										<input type="text" name="notdep" id="notdep" class="textInput"  readonly="true" style="width:83%" value="${notdep}"/>
										<input type="hidden" name="notdepid" id="notdepid" value="${notdepid}"/>
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('0', 'notdepid', 'notdep');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('notdep');"/>
									</td>
									
								</tr>
							    <tr style="display:none;">
									<td>
										  通知角色 ：
									</td>
									<td colspan="3">
										<input type="text" name="notrole" id="notrole" class="textInput"  readonly="true" style="width:83%" value="${notrole}"/>
										<input type="hidden" name="notroleid" id="notroleid" value="${notroleid}"/>
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('notrole');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  抄送人员 ：
									</td>
									<td colspan="3">
										<input type="text" name="copyuser" id="copyuser" class="textInput"  readonly="true" style="width:83%" value="${copyuser}"/>
										<input type="hidden" name="copyuserid" id="copyuserid" value="${copyuserid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1', 'copyuserid', 'copyuser');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('copyuser');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  抄送部门 ：
									</td>
									<td colspan="3">
										<input type="text" name="copydep" id="copydep" class="textInput" readonly="true" style="width:83%" value="${copydep}"/>
										<input type="hidden" name="copydepid" id="copydepid" value="${copydepid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('0', 'copydepid', 'copydep');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('copydep');"/>
									</td>
									
								</tr>
							    <tr style="display:none;">
									<td>
										  抄送角色 ：
									</td>
									<td colspan="3">
										<input type="text" name="copyrole" id="copyrole" class="textInput" readonly="true" style="width:83%" value="${copyrole}"/>
										<input type="hidden" name="copyroleid" id="copyroleid" value="${copyroleid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('copyrole');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  升级人员 ：
									</td>
									<td colspan="3">
										<input type="text" name="upgradeuser" id="upgradeuser" class="textInput" readonly="true" style="width:83%" value="${upgradeuser }"/>
										<input type="hidden" name="upgradeuserid" id="upgradeuserid" value="${upgradeuserid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1', 'upgradeuserid', 'upgradeuser');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('upgradeuser');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  升级部门 ：
									</td>
									<td colspan="3">
										<input type="text" name="upgradedep" id="upgradedep" class="textInput" readonly="true" style="width:83%" value="${upgradedep }"/>
										<input type="hidden" name="upgradedepid" id="upgradedepid" value="${upgradedepid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('0', 'upgradedepid', 'upgradedep');" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('upgradedep');"/>
									</td>
									
								</tr>
							    <tr style="display:none;">
									<td>
										  升级角色 ：
									</td>
									<td colspan="3">
										<input type="text" name="upgraderole" id="upgraderole" class="textInput" readonly="true" style="width:83%" value="${upgraderole }"/>
										<input type="hidden" name="upgraderoleid" id="upgraderoleid" value="${upgraderoleid}" />
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1');" /> 
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('upgraderole');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										  自定义通知组件 ：
									</td>
									<td colspan="3">
										<input type="text" name="custnoticecomp" id="custnoticecomp" class="textInput" readonly="true" style="width:83%" value="${custnoticecomp }"/>
										<input type="hidden" name="eventRule.custnoticecompid" id="custnoticecompid" value="${eventRule.custnoticecompid}" />									
										<input type="button" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openComponentList();" />
										<input type="button" value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="clearContext('custnoticecomp');"/>
									</td>
									
								</tr>
								<tr>
									<td>
										通知次数：
									</td>
									<td>
										<input type="text" name="noticet" id="noticet" class="textInput" value="${noticet }"/>
										<validation id="noticetV" dataType="Integer" msg="通知次数必须为数字，默认设置成1！" />
									</td>
									<td>
										开始时间：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="stime" id="stime" class="textInput" value="${stime}" readonly="true"  onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									
									</td>
								</tr>
								<tr>
									<td>
										通知间隔(分钟)：
									</td>
									<td>
										<input type="text" name="noticeinters" id="noticeinters" class="textInput" value="${noticeinters }"/>
										<input type="hidden" name="eventRule.noticeinterval" id="noticeinterval" value="${eventRule.noticeinterval }"/>
									</td>
									<td>
										结束时间：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="etime"" id="etime" class="textInput" value="${etime }" readonly="true" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
									</td>
								</tr>
								<tr>
									<td>
										是否补发：
									</td>
									<td>
										<eoms:select name="eventRule.isnoticeafterwards" style="select" dataDicTypeCode="isnoticeafterwards"  value="${eventRule.isnoticeafterwards }" isnull="false"/>
									</td>
								    <td>
										启用状态：
									</td>
									<td>
										<eoms:select name="eventRule.status" style="select" dataDicTypeCode="startstatus"  value="${eventRule.status }" isnull="false"/>
									</td>
								</tr>
								<tr>
									<td>
										动作类型：
									</td>
									<td>
										<eoms:select name="eventRule.actiontype" style="select" dataDicTypeCode="actiontype"  value="${eventRule.actiontype }" isnull="false"/>
										
									</td>
									<td>
										动作参数：
									</td>
									<td>
										<input type="text" name="eventRule.actionparameter" id="eventRule.actionparameter" class="textInput" value="${eventRule.actionparameter }"/>
										<validation id="eventRule.actionparameterV" dataType="Limit"  max="100" msg="动作参数 不能为超过100个字！" />
									</td>
								</tr>
								<tr>
									<td>
										  通知主题 ：
									</td>
									<td colspan="3">
										<input type="text" name="eventRule.noticetopic" id="eventRule.noticetopic" class="textInput" style="width:98.5%" value="${eventRule.noticetopic }"/>
									    <validation id="eventRule.noticetopicV" dataType="Limit"  max="300" msg="通知主题 不能为超过300个字！" />
									</td>
								</tr>
								<tr>
									<td valign="top">
										  通知内容 ：
									</td>
									<td colspan="3">
										<textarea name="eventRule.noticecontent" id="eventRule.noticecontent" rows="6" class="textInput" style="width:98.5%" >${eventRule.noticecontent }</textarea>
										<validation id="eventRule.noticecontentV" dataType="Limit"  max="750" msg="通知内容 不能为超过750个字！" />
									</td>
								</tr>
								<tr>
									<td>
										动态查询方式：
									</td>
									<td>
										<eoms:select name="eventRule.dynamicconchecktype" style="select" dataDicTypeCode="dynamicconchecktype"  value="${eventRule.dynamicconchecktype }" />
										
									</td>
									<td>
										动态查询参数：
									</td>
									<td>
										<input type="text" name="eventRule.dynamicconcheckparam" id="eventRule.dynamicconcheckparam" class="textInput" value="${eventRule.dynamicconcheckparam }"/>
										<validation id="eventRule.dynamicconcheckparamV" dataType="Limit"  max="150" msg="动态查询参数 不能为超过150个字！" />
									</td>
								</tr>
								<tr>
									<td>
										有效开始时间：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="validstarttime" id="validstarttime" class="textInput" readonly="true"  value="<eoms:date value='${eventRule.validstarttime }'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>	
									<validation id="validstarttimeV" dataType="Require" msg="有效开始时间    不能为空！" />
									</td>
									<td>
										有效结束时间：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="validendtime" id="validendtime" class="textInput" readonly="true" value="<eoms:date value='${eventRule.validendtime }'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
									 <validation id="validendtimeV" dataType="Require" msg="有效结束时间    不能为空！" />
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="add_bottom">
				<input type="button" value="<eoms:lable name="com_btn_save"/>" 
					class="save_button" onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="formSubmit();"/>
				<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</form>
	</body>
</html>