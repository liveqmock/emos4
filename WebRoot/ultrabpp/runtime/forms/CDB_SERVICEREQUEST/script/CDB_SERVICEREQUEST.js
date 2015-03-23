
/**
 * 获取服务分类ID
 *  
*/
function setServiceCategoryID(service_category){
		$.post($ctx+"/serverQuest/getServerquestPID.action",{serverquestfullname:service_category},function(data) {
			if(data!=null && data!='null'){
				F("service_categoryID").S(data);
			}
		});
}

// 审批升级功能
function auditLevelUp(){
	F("is_audit_level_up").change("isAuditLevelUpChange",function(){
		if($("#is_audit_level_up_0").attr("checked") == "checked"){
			$("#dealPerson_ubfbox").show();//派发树
			F("dealPerson").require = 1;
			$("#view_deal_person_ubfbox").hide();//下拉框
			$("#view_deal_group_ubfbox").hide();//下拉框
			ClientContext.setAssignString("");//使用派发树时,清空原有的assignString
		}else{
			$("#dealPerson_ubfbox").hide();
			F("dealPerson").require = 0;
			F("dealPerson").S("","")
			$("#view_deal_person_ubfbox").show();
			$("#view_deal_group_ubfbox").show();
			ClientContext.setAssignString(assstr);//关闭派发树时,将assignString赋值为查询得到的值
		}
	});
}

/**
 * 修改下载模板按钮的可用状态
 */
var changeGetTepmlateStatus = function(){
	if(F("service_category").G() == ""){
		$("#download_template_getTemplate").attr("disabled","disabled");
	}else{
		$("#download_template_getTemplate").removeAttr("disabled","disabled");
	}
}


/**
 * 检查处理组和处理人
 */
function checkGroupPerson(){
	if(F("deal_group").G() == "" && F("view_deal_person").G() == ""){
		alert("处理人和处理组不能全部为空");
		return false;
	}
	return true;
}

/**
 * 如果是自助服务页面则自动提交
 */
function autoSubmit(){
	if(isSelfHelp()){
		F("notify").S("短信,邮件");
		if(confirm("是否提交工单")){
			if(!ActionPanel.submit()){
				ActionPanel.goBackView();
			}
		}else{
			ActionPanel.goBack()
		}
	}
}

/**
 * 
 */
function toCloseFromDealForSelfHelp(){
	if(isSelfHelp()){
		$("#bpp_ActPanel_warp").css("display","block");
		F("satisfaction").visiable(true);
		F("satisfaction").required(true);
		F("satisfaction").S("非常满意");
		$("#dealInfo_ubfbox span").html("满意度评价：");
		F("close_code").visiable(false);
		F("close_code").required(false);
		F("dealInfo").S("非常满意");
	}
}

function toCloseFromDealSubmitForSelfHelp(){
	if(isSelfHelp()){
		F("satisfaction_level").S(F("satisfaction").G())
		F("satisfaction_content").S(F("dealInfo").G());
	}
}

function isSelfHelp(){
	return (typeof selfHelp != "undefined");
}
//屏蔽新建和受理的转派按钮
function hideRenext(){
	$("#bpp_Btn_ToRenextToNew").parent().hide();
	$("#bpp_Btn_ToRenextToAccept").parent().hide();
}

//将秒格式的时间转换成时间格式	
function parseSecondToStr(sec) {
		var dateString = '';
		if(sec != null && sec != '' && sec != 'null' && sec != '0')
		{
			dateTime = new Date(sec*1000);
			dateString = dateTime.getFullYear();
			dateString += '-' + formatDateTimeLength((dateTime.getMonth()+1));
			dateString += '-' + formatDateTimeLength((dateTime.getDate()));
			dateString += ' ' + formatDateTimeLength(dateTime.getHours());
			dateString += ':' + formatDateTimeLength(dateTime.getMinutes());
			dateString += ':' + formatDateTimeLength(dateTime.getSeconds());
		}
		return dateString;
	}
	function formatDateTimeLength(str) {
	if ((str + '').length == 1) {
		return '0' + str;
	} else {
		return str;
	}
}

function  changeSec(second){
		if(second<60){
			second=second+'(分钟)';
		}else if(second>=60&&second<1440){
			second=Math.ceil(second/60);
			second=second+'(小时)';
		}else{
			second=Math.ceil(second/60/24);
			second=second+'(天)';
		}
		return second;
}

function bpp_init() {
	hideRenext();//屏蔽新建和受理的转派按钮
	
	//区分自助服务与非自助服务
	if(F("DaysResponseTime")!=null){
	F("DaysResponseTime").readonly(true);
	F("NoWorkingDaysRespTime").readonly(true);
	F("WorkingTimeLimit").readonly(true);
	F("NoWorkingTimeLimit").readonly(true);
	if(F("DaysResponseTime").G()==''||F("DaysResponseTime").G()==null){
		$("#BASEACCEPTOUTTIME").css("visibility","hidden");
	}
	if(F("WorkingTimeLimit").G()==''||F("WorkingTimeLimit").G()==null){
		$("#BASEDEALOUTTIME").css("visibility","hidden");
	}
	//分钟超过60转换为小时
	if(F("DaysResponseTime").G().indexOf("(")<0&&F("DaysResponseTime").G()!=''&&F("DaysResponseTime").G()!=''){
		F("DaysResponseTime").S(changeSec(F("DaysResponseTime").G()));
	}
	if(F("NoWorkingDaysRespTime").G().indexOf("(")<0&&F("NoWorkingDaysRespTime").G()!=''&&F("NoWorkingDaysRespTime").G()!=''){
		F("NoWorkingDaysRespTime").S(changeSec(F("NoWorkingDaysRespTime").G()));
	}
	if(F("WorkingTimeLimit").G().indexOf("(")<0&&F("WorkingTimeLimit").G()!=''&&F("WorkingTimeLimit").G()!=''){
		F("WorkingTimeLimit").S(changeSec(F("WorkingTimeLimit").G()));
	}
	if(F("NoWorkingTimeLimit").G().indexOf("(")<0&&F("NoWorkingTimeLimit").G()!=''&&F("NoWorkingTimeLimit").G()!=''){
		F("NoWorkingTimeLimit").S(changeSec(F("NoWorkingTimeLimit").G()));
	}
	
	F("RepairDays").readonly(true);//报修天数不可编辑
	//维修开始时间改变后给报修时间赋值
	F("reqRepairStartTime").change("reqRepairStartTime",function(){
		if(F("reqRepairStartTime").G()==""||F("reqRepairStartTime").G()==null){
			F("RepairDays").S("");
		}else{
			if(!F("repairedEndTime").G()==""||!F("repairedEndTime").G()==null){
				if(formatDateToTime(F("reqRepairStartTime").G())<=formatDateToTime(F("repairedEndTime").G())){
					fillRepairDays(F("reqRepairStartTime").G(),F("repairedEndTime").G());
				}else{
					F("RepairDays").S("");
					alert("报修开始时间不能大于维修结束时间");
				}
			}
		}
	});
	
	//维修结束时间判断，改变后给报修时间赋值
	F("repairedEndTime").change("repairedEndTime",function(){
		if(F("reqRepairStartTime").G()==""||F("reqRepairStartTime").G()==null||F("repairedEndTime").G()==""||F("repairedEndTime").G()==null){
			F("RepairDays").S("");
		}else{
				if(formatDateToTime(F("reqRepairStartTime").G())<=formatDateToTime(F("repairedEndTime").G())){
					fillRepairDays(F("reqRepairStartTime").G(),F("repairedEndTime").G());
				}else{
					F("RepairDays").S("");
					alert("报修开始时间不能大于维修结束时间");
					
				}
		}
	});
	//计算报修天数
	 var fillRepairDays =function(strTime,endTime){
			$.post(ctx + "/incident/getRepairDays.action?strTime="+strTime+"&endTime="+endTime,function(data) {
				if(typeof callback != "undefined"){ 
					callback(data);
				}
				$("#RepairDays").attr("value",data);
				});
	 }
	
	}

	F("BASEACCEPTOUTTIME").readonly(true);
	F("BASEDEALOUTTIME").readonly(true);

	if(isSelfHelp()){//自助服务要求服务时间非必填
		F("requireDealTime").required(false);
	}
	
 	/**
 	 * 获取服务分类ID
 	 *  
 	*/
 	F("service_category").change("service_category",function(){
 		if(F("service_category").G()!=""&&F("service_category").G()!=null){
 			setServiceCategoryID(F("service_category").G());
 		}
 	});
	
 	
	//响应时限格式转换
	if(F("BASEACCEPTOUTTIME").G()!=null&&F("BASEACCEPTOUTTIME").G()!=""&&$("#BASEACCEPTOUTTIME").val().length==10){
		$("#BASEACCEPTOUTTIME").val(parseSecondToStr(F("BASEACCEPTOUTTIME").G()))
	}
	//处理时限格式转换
	if(F("BASEDEALOUTTIME").G()!=null||F("BASEDEALOUTTIME").G()!=""&&$("#BASEACCEPTOUTTIME").val().length==10){
		$("#BASEDEALOUTTIME").val(parseSecondToStr(F("BASEDEALOUTTIME").G()))
	}
	
 	//是否第三方支持为是，时间必填
 	if(F("isThirdpartSupport")!=null)
 	F("isThirdpartSupport").change("isThirdpartSupport",function(){
		if(F("isThirdpartSupport").G()=="是"){
			F("reqRepairStartTime").required (true);
			F("repairedEndTime").required (true);
		}else{
			F("reqRepairStartTime").required (false);
			F("repairedEndTime").required (false);
		}
	});
 	
 	//如果是第三方支持，时间显示为必填
 	if(F("isThirdpartSupport")!=null)
	if(F("isThirdpartSupport").G()=="是"){
		F("reqRepairStartTime").required (true);
		F("repairedEndTime").required (true);
	}
 	
	$("#SatisfactionPanel_Item").hide(); //初始化时隐藏满意度容器,关单环节时显示
 	if(ClientContext.baseStatus == "已关闭"||(ClientContext.baseStatus == "待关闭" && F("closeUserAcceptTime")!=null && F("closeUserAcceptTime").G() != "")){
		$("#SatisfactionPanel_Item").show();
	}
	
 	//处理环节点击处理完成提交时,如果工单超时,强制填写超时原因
	ClientContext.submit("action","ToDealFinishFromDeal",function(){
		if(formatDateToTime(F("DealTimeLimit").G())<new Date().getTime()){
			F("timeout_reason").required (true);
		}
		return true;
	})
	
	//送审环节，若是第三方支持，时间判断
	if(F("reqRepairStartTime")!=null)
	ClientContext.submit("action","ToSendAudit",function(){
		if(formatDateToTime(F("reqRepairStartTime").G())>formatDateToTime(F("repairedEndTime").G())){
			alert("报修开始时间不能大于维修结束时间");
			return false;
		}
		return true;
	})
	
	//提交审批环节，若是第三方支持，时间判断
	if(F("reqRepairStartTime")!=null)
	ClientContext.submit("action","ToAudit",function(){
		if(formatDateToTime(F("reqRepairStartTime").G())>formatDateToTime(F("repairedEndTime").G())){
			alert("报修开始时间不能大于维修结束时间");
			return false;
		}
		return true;
	})
	 
	//处理完成环节,若是第三方支持，时间判断
	if(F("reqRepairStartTime")!=null)
	ClientContext.submit("action","ToDealFinishFromDeal",function(){
		if(formatDateToTime(F("reqRepairStartTime").G())>formatDateToTime(F("repairedEndTime").G())){
			alert("报修开始时间不能大于维修结束时间");
			return false;
		}
		return true;
	})
	
	if(ClientContext.baseStatus == "草稿"){
		var serviceCategory = ClientContext.cp.serviceCategory;
		if(serviceCategory != undefined)
		F("service_category").S(ClientContext.cp.serviceCategory);
	}
	
	if(typeof donotShowKM == "undefined")
	setTimeout(addKmSearchDiv,500);//知识悬浮窗
	
	$("#requestUser_extend").hide();//隐藏请求人后面的扩展按钮
	if(ClientContext.baseStatus == "草稿" || ClientContext.baseStatus == "新建"){
		changeGetTepmlateStatus();
		userAutoInput("requestUser","requestUserID",function(user){
			if(F("requestUserDept") != null) F("requestUserDept").S(user.cdbUnitName);//单位
			if(F("requestUserGroup") != null) F("requestUserGroup").S(user.cdbDepName);//部门
			if(F("requestUserPhone") != null) F("requestUserPhone").S(user.phone);//电话
			if(F("requestUserMobile") != null) F("requestUserMobile").S(user.mobile);//手机
			if(F("requestUserSite") != null) F("requestUserSite").S(user.station);//工位
			if(F("requestUserOA") != null) F("requestUserOA").S(user.oaNumber);//OA号
			if(F("requestUserPosition") != null) F("requestUserPosition").S(user.position);//职位
			if(F("ISVIP") != null) F("ISVIP").S(user.isVip == "0" ? "" : user.isVip == "1" ? "一级VIP" : "二级VIP");
			if(F("viplevel") != null) F("viplevel").S(user.isVip == "0" ? "" : user.isVip);
			showVIP();
		});
	}
	
	if(F("requestUserDept") != null) F("requestUserDept").readonly(true);
	if(F("requestUserGroup") != null) F("requestUserGroup").readonly(true);
	if(F("requestUserOA") != null) F("requestUserOA").readonly(true);
	if(F("requestUserPosition") != null) F("requestUserPosition").readonly(true);
	if(F("requestUserMobile") != null) F("requestUserMobile").readonly(true);
	if(F("ISVIP") != null) F("ISVIP").readonly(true);
	if(F("requestLevel") != null) F("requestLevel").readonly(true);
	
	//受理环节,未受理,处理过程和解决方案只读 
	if(ClientContext.baseStatus == "受理" && F("acceptTime")!=null && F("acceptTime").G() == ""){
		F("deal_process_solution").readonly(true);
		F("isThirdpartSupport").readonly(true);
		if(F("reqRepairStartTime")!=null) F("reqRepairStartTime").readonly (true);
		if(F("repairedEndTime")!=null) F("repairedEndTime").readonly (true);
		F("timeout_reason").readonly(true);
	}
	//处理环节,未受理,处理过程和解决方案只读 
	if(ClientContext.baseStatus == "处理" && F("dealUserAcceptTime")!=null && F("dealUserAcceptTime").G() == ""){
		F("deal_process_solution").required(false);
		F("deal_process_solution").readonly(true);
		F("isThirdpartSupport").readonly(true);
		if(F("reqRepairStartTime")!=null) F("reqRepairStartTime").readonly (true);
		if(F("repairedEndTime")!=null) F("repairedEndTime").readonly (true);
		F("timeout_reason").readonly(true);
	}
	
	//展示vip信息
	var showVIP = function(){
		if(F("ISVIP") == null) return;
		if(F("ISVIP").G() == ""){//非vip用户直接隐藏
			$("#ISVIP_ubfbox").children().hide();
			$("#requestLevel_ubfbox").children().hide();
		}else{
			$("#ISVIP_ubfbox").children().show();
			$("#requestLevel_ubfbox").children().show();
			F("requestLevel").S("VIP服务");
			$("#ISVIP").css({"color":"#993300","font-weight":"900"});
			if (F("ISVIP").G() == "1" || F("ISVIP").G() == "一级VIP"){
				F("viplevel").S("1")
			}
			if(F("ISVIP").G() == "2"|| F("ISVIP").G() == "二级VIP") {
				F("viplevel").S("2")
			}
		}
	};
	showVIP();
	
	/**
	 * 点击"获取模板",下载服务目录关联附件
	 */
	$("#download_template_getTemplate").click(function(){
		
		$.post($ctx+"/serverQuest/getServerQuestByFullname.action",{fullname:F("service_category").G()},function(data){
			$.post($ctx+"/attachment/getAttachmentByRelation.action",{attachmentGroupId:data.attamentid},function(data2){
				if(data2.length < 1){
					alert("此服务目录不包含附件");
				}else{
					var downloadIds = "";
					$.each(data2,function(index,item){
						if(index > 0) downloadIds = downloadIds + ",";
						downloadIds = downloadIds + item.pid;
					});
					window.open($ctx+"/attachment/download.action?downloadIds="+downloadIds);
				}
			},"json");
			
		},"json");
	});
	
	/**
	 * 展示处理组,处理人下拉框
	 */
	var showDealGroupPerson = function(){
		$("#view_deal_group_ubfbox").show();
		$("#view_deal_person_ubfbox").show();
	}
	
	/**
	 * 填写处理人
	 */
	var fillDealPerson = function(callback){
		F("deal_group").S("");//清空处理组
		F("view_deal_person").S("");//清空处理人
		var serverQuestFullName = F("service_category").G();//服务目录
		if(serverQuestFullName == "") return;
		
		var auditSequence = ClientContext.getAttr("current_audit_sequence");//审批序列，1受理人，2...审批人 
		
		var serviceParaMap = new Map();
		serviceParaMap.put('auditSequence',auditSequence);
		serviceParaMap.put('serverQuestFullName',serverQuestFullName);
		DataTransfer.callService('serverQuestWorkFlow','getNextAssign',serviceParaMap,function(data){
			if(data.assignType == "U"){
				var viewDealPerson = data.assignName + "[" + data.assignId + "]";
//				F("view_deal_person").resource = data.assignId + ":" +viewDealPerson;//设置处理人下拉列表值
				F("view_deal_person").resource = "清空";//设置处理人下拉列表值
				F("view_deal_person").S(viewDealPerson);//选中处理人
			}else if(data.assignType == "G"){
				F("deal_group").S(data.assignId);//设置处理组
				F("view_deal_group").S(data.assignName);//显示处理组
			}
			if(typeof callback != "undefined"){ 
				callback(data);
			}
		});
	}
	
	/**
	 * 根据组id查询组内人员,并拼处理人下拉框的串
	 */
	var fillDealUsers = function(groupId){
		$.post($ctx+"/userManager/getUsersByGroupId.action",
				{groupIds:groupId},
				function(data){
					F("deal_person").resource = "";//清空处理人下拉列表
					if(data == "") return;
					var users;
					$.each(data,function(index,item){//拼处理人串
						if(index > 0)
							users = users + ",";
						users = users + item.loginname + ":" + item.fullname;
					});
					F("deal_person").resource = users;//设置处理人下拉列表值
				},"json");
	}
	
	
	/**
	 * 更改服务目录时，修改默认的处理组和处理人
	 * 服务目录不为空时,获取模板按钮可用
	 */
	F("service_category").change("changeSubmitDeal",function(){
		var serviceCategory = F("service_category").G();
		if(serviceCategory == ""){
			F("service_category_1").S("");
			F("service_category_2").S("");
			F("service_category_3").S("");
		}else{
			var serviceCategorys = serviceCategory.split(".");
			F("service_category_1").S(serviceCategorys[0]);
			F("service_category_2").S(serviceCategorys[1]);
			F("service_category_3").S(serviceCategorys[2]);
		}
		changeGetTepmlateStatus();
		if(F("deal_person") == null) return;
		fillDealPerson();
	});
	//新建环节,作废,处理说明赋默认值
	ClientContext.init("action","ToCancelFromRequest",function(){ 
		var msg = $.trim($("#bpp_Btn_ToCancelFromRequest").val());
		F("dealInfo").S(msg);
	});
	
	//新建环节,作废,处理说明必填
	ClientContext.submit("action","ToCancelFromRequest",function(){
		if(F("dealInfo")!=null && F("dealInfo").G() == ""){
			alert("处理说明必填");$("#dealInfo").focus();return;
		}
		return true;
	});
	
	//自助服务，新建环节,作废，给出提示
	$("#bpp_Btn_z_ToCancelFromRequest").click(function(){
		if(confirm("是否作废此工单")){
			$("#bpp_Btn_ToCancelFromRequest").click();
		}
	});
	
	
	ClientContext.init("action","ToRequest",function(){ 
		var msg = $.trim($("#bpp_Btn_ToRequest").val());
		F("dealInfo").S(msg);
	    groupTree('deal_group','','',"deal_person");//弹出组选择树
	    fillDealPerson(autoSubmit);
	});
	
	ClientContext.submit("action","ToRequest",function(){
		if(isSendAudit()){
			alert("此工单必须先送审");
			return false;
		}
		if(F("reqRepairStartTime")!=null)
		if(formatDateToTime(F("reqRepairStartTime").G())>formatDateToTime(F("repairedEndTime").G())){
			alert("报修开始时间不能大于维修结束时间");
			return false;
		}
		return checkGroupPerson();
	});
	
	//新建环节,转派,处理说明赋值
	ClientContext.init("action","ToRenextToNew",function(){ 
		var msg = $.trim($("#bpp_Btn_ToRenextToNew").val());
		F("dealInfo").S(msg);
	});
	
	//提交审批环节中的通过和新建环节中的提交请求是一样的逻辑
	ClientContext.init("action","ToPassRequestAudit",function(){ 
		groupTree('deal_group','','',"deal_person");
		fillDealPerson();
		auditLevelUp();
	})
	
	ClientContext.submit("action","ToPassRequestAudit",function(){
		return checkGroupPerson();
	})
	
	/**
	 * 新建环节,关单,默认选择"处理取消"处理信息必填
	 */
	ClientContext.init('action','ToCloseFromRequest',function(){
		F("close_code").S("处理取消");
		var msg = $.trim($("#bpp_Btn_ToCloseFromRequest").val());
		F("dealInfo").S(msg);
		F("deal_process_solution").required(true);
	});
	
	/**
	 * 新建环节,关单,取消,处理信息不必填
	 */
	ClientContext.back('ToCloseFromRequest',function(){
		F("deal_process_solution").required(false);
		if(F("reqRepairStartTime")!=null) F("reqRepairStartTime").required (false);
		if(F("reqRepairStartTime")!=null) F("repairedEndTime").required (false);
		return true;
	});
	
	/**
	 * 受理环节,提交处理,下一步处理人不可编辑
	 */
	ClientContext.init('action','ToDealFromAccept',function(){
		groupTree('deal_group','','',"deal_person");
		fillDealPerson();
		F("deal_person").readonly(true);
	});
	
	ClientContext.submit("action","ToDealFromAccept",function(){
		return checkGroupPerson();
	})
	
	/**
	 * 受理环节,处理完成,展示下一步处理人(提交请求人),不可编辑,处理信息必填
	 */
	ClientContext.init('action','ToDealFinishFromAccept',function(){
		F("deal_process_solution").required(true);
		F("deal_person").readonly(true);
		F("deal_person").S(F("submit_request_person_name").G());
	});
	
	/**
	 * 受理环节,处理完成,取消,处理信息非必填
	 */
	ClientContext.back('ToDealFinishFromAccept',function(){
		F("deal_process_solution").required(false);
		return true;
	});
	//受理环节，退回处理意见赋值
	ClientContext.init("action","ToBackToRequestFromAccept",function(){ 
		var msg = $.trim($("#bpp_Btn_ToBackToRequestFromAccept").val());
		F("dealInfo").S(msg);
	});
	//受理环节，转派处理意见赋值
	ClientContext.init("action","ToRenextToAccept",function(){ 
		var msg = $.trim($("#bpp_Btn_ToRenextToAccept").val());
		F("dealInfo").S(msg);
	});
	/**
	 * 提交审批
	 * 如果下一步是处理人,则隐藏"处理组","处理人"下拉框,非必填;"处理人"派发树必填
	 * 如果下一步是审批人,则隐藏"处理人"派发树
	 */
	ClientContext.init('action','ToAudit',function(){
		var msg = $.trim($("#bpp_Btn_ToAudit").val());
		F("audit_info").S(msg);
		groupTree('deal_group','','',"deal_person");//选择部门 
		fillDealPerson(function(data){
			if(data.isDealPerson){//只留下派发树，并将groupTree方法中设置的assignString清空
				$("#view_deal_group_ubfbox").hide();
				$("#view_deal_person_ubfbox").hide();
				ClientContext.setAssignString("");
			}else{
				$("#dealPerson_ubfbox").hide();//隐藏处理人派发树
			}
		});
	});
	
	/**
	 * 审批通过,设定处理人
	 * 隐藏"处理人"派发树
	 * 如果下一步是审批人,隐藏"是否审批升级"
	 * 如果下一步是处理人,选中"是否审批升级",展示"处理人"派发树,必填;隐藏"处理人"下拉框;隐藏"处理组"
	 *               取消"是否审批升级",展示"处理人"下拉框,展示"处理组",隐藏"处理人"派发树,非必填,清空assignString
	 */
	ClientContext.init('action','ToPass',function(){
		var msg = $.trim($("#bpp_Btn_ToPass").val());
		F("dealInfo").S(msg);
		$("#dealPerson_ubfbox").hide();
//		F("view_deal_person").readonly(true);
//		F("view_deal_group").readonly(true);
		fillDealPerson(function(data){
			var assstr = data.assignType+"#:"+data.assignId+"#:NEXT#:2#:0#:0#:0#:#:#:#:#;";
			ClientContext.setAssignString(assstr);
			if(data.isDealPerson){
				auditLevelUp();
			}else{
				$("#is_audit_level_up_ubfbox").hide();
			}
			//如果是处理组,则"处理人"下拉框可用
			if(data.assignType == "G"){
//				F("view_deal_person").readonly(false);
				groupTree('deal_group','','',"deal_person");
//				getUserListByDepIds(data.assignId,"deal_person");
//				bindPersonChange();//绑定人员下拉列表change事件,选择人员时,拼处理规则串
//				bindPersonChange(id)
			}
		});
	});
	
	ClientContext.submit("action","ToPass",function(){
		return checkGroupPerson();
	})
	//退回
	ClientContext.submit("action","ToDealFinishFromAccept",function(){
		ClientContext.setAttr("reback_flag","0");
		return true;
	});
	ClientContext.submit("action","ToDealFinishFromDeal",function(){
		ClientContext.setAttr("reback_flag","1");
		return true;
	});
	
	//审批不通过，处理意见赋默认值
	ClientContext.init("action","ToNoPass",function(){ 
		var msg = $.trim($("#bpp_Btn_ToNoPass").val());
		F("dealInfo").S(msg);
	});
	/**
	 * 处理环节,处理完成,下一步处理人不可编辑
	 */
	ClientContext.init('action','ToDealFinishFromDeal',function(){
		var msg = $.trim($("#bpp_Btn_ToDealFinishFromDeal").val());
		F("dealInfo").S(msg);
		F("deal_person").readonly(true);
		F("deal_person").S(F("submit_request_person_name").G());
	});
	
	/**
	 * 处理环节,退回,处理信息非必填
	 */
	ClientContext.init('action','ToBackToAcceptFromDeal',function(){
		var msg = $.trim($("#bpp_Btn_ToBackToAcceptFromDeal").val());
		F("dealInfo").S(msg);
		F("deal_process_solution").required(false);
	});
	
	/**
	 * 处理环节,退回,取消,处理信息必填
	 */
	ClientContext.back('ToBackToAcceptFromDeal',function(){
		F("deal_process_solution").required(true);
		return true;
	});
	
	//处理环节,转派,处理信息赋值
	ClientContext.init("action","ToRenextToDeal",function(){ 
		var msg = $.trim($("#bpp_Btn_ToRenextToDeal ").val());
		F("dealInfo").S(msg);
	});

	/**
	 * 关单环节,关单,默认选择"已解决","非常满意"
	 */
	ClientContext.init('action','ToCloseFromDeal',function(){
		var msg = $.trim($("#bpp_Btn_ToCloseFromDeal").val());
		F("dealInfo").S(msg);
		toCloseFromDealForSelfHelp();//上面两句话给出了默认的处理意见，自助服务要再改一次默认处理意见
		F("close_code").S("已解决");
//		F("satisfaction").S("非常满意");
	});
	
	ClientContext.submit("action","ToCloseFromDeal",function(){
		toCloseFromDealSubmitForSelfHelp();
		return true;
	});
	
	//关单环节,退回,处理意见赋值
	ClientContext.init("action","ToBackFromClose",function(){ 
		var msg = $.trim($("#bpp_Btn_ToBackFromClose").val());
		F("dealInfo").S(msg);
	});
	//关单环节,转派,处理意见赋值
	ClientContext.init("action","ToRenextToClose",function(){ 
		var msg = $.trim($("#bpp_Btn_ToRenextToClose").val());
		F("dealInfo").S(msg);
	});
	
}

// 是否需要送审
function isSendAudit(){
	var isSendAudit = $.ajax({
		type:"POST",
		url:$ctx+"/serverQuest/isSendAudit.action",
		data:{
			"fullname":F("service_category").G()
		},
		async:false
	}).responseText;
	return isSendAudit == "1";
}