function bpp_init() 
{
	F("RepairDays").readonly(true);
	
	F("DaysResponseTime").readonly(true);
	F("NoWorkingDaysRespTime").readonly(true);
	F("WorkingTimeLimit").readonly(true);
	F("NoWorkingTimeLimit").readonly(true);
	F("BASEACCEPTOUTTIME").readonly(true);
	F("BASEDEALOUTTIME").readonly(true);
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
	
	//分钟转换小时
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
//	alert("受理时限的设定为:"+F("AcceptanceTimeLimit").G());
//	alert("处理时限的设定为:"+F("DealTimeLimit").G());
//	alert("处理时间为："+F("DealTime").G());
	
//	//判断用户是不是vip，是vip则变红
//	if($("#IsVIP").val() != ""){
//		//alert($("#IsVIP_ubfbox span").text());
//		//$("#IsVIP_ubfbox span").attr("style","color:red;");
//		$("#IsVIP_ubfbox span").addClass("bpp_Require");
//	}
	
	/**
	 *存入现象分类ID
	 */
	F("IncidentPhenoClass").change("IncidentPhenoClass",function(){
		if(F("IncidentPhenoClass").G()!=""&&F("IncidentPhenoClass").G()!=null){
			setFieldIdByName("IncidentPhenoClassID",F("IncidentPhenoClass").G(),"incPhenoClass","ID");
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
	if(F("isThirdpartSupport").G()=="是"){
		F("reqRepairStartTime").required (true);
		F("repairedEndTime").required (true);
	}
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
//			if(F("repairedEndTime").G()!=""||F("repairedEndTime").G()!=null){
				if(formatDateToTime(F("reqRepairStartTime").G())<=formatDateToTime(F("repairedEndTime").G())){
					fillRepairDays(F("reqRepairStartTime").G(),F("repairedEndTime").G());
				}else{
					F("RepairDays").S("");
					alert("报修开始时间不能大于维修结束时间");
					
				}
//			}else{
//				F("RepairDays").S("");
//			}
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
		
	
	/**
	 * 初始化相关用户信息
	 */
	
	$("#SatisfactionPanel_Item").hide(); //初始化时隐藏满意度容器,关单环节时显示
	if(ClientContext.baseStatus == "已关闭"||(ClientContext.baseStatus == "待关闭" && F("closeUserAcceptTime").G() != "")){
		$("#SatisfactionPanel_Item").show();
	}
	
	//处理环节处理意见赋默认值
	ClientContext.init("action","ToFinishProcessing",function(){ 
		var msg = $.trim($("#bpp_Btn_ToFinishProcessing").val());
		F("Description").S(msg);
	});
	//处理环节点击处理完成提交时,如果工单超时,强制填写超时原因
	ClientContext.submit("action","ToFinishProcessing",function(){
		if(formatDateToTime(F("DealTimeLimit").G())<new Date().getTime()){
			F("timeout_reason").required (true);
		}
		if(formatDateToTime(F("reqRepairStartTime").G())>formatDateToTime(F("repairedEndTime").G())){
			alert("报修开始时间不能大于维修结束时间");
			return false;
		}
		return true;
	})
		 
	//知识库内容
	addKmSearchDiv();
	
	//画创建按钮
//	drawCreateXBt("创建知识","KM","http://www.baidu.com");
//	drawCreateXBt("创建变更","CHG","http://www.hao123.com");
	
	F("ContactPersonSite").readonly(true);
	F("ContactDepartment").readonly(true);
	F("ContactOA").readonly(true);
//	F("ContactStation").readonly(true);
	F("ContactPosition").readonly(true);
	F("ContactPhone").readonly(true);
//	F("ContactTel").readonly(true);
	F("IsVIP").readonly(true);
	F("Priority").readonly(true);
	showVIP();
	
	//严重等级规则初始化
	var priorityMap = new Map();
	priorityMap = initpriorityMap(priorityMap);
	
	/**
	 *提交处理时通过现象分类填写处理人
	 */

	F("IncidentPhenoClass").change("changeSubmitDeal",function(){
		fillDealPerson('PROPFIELD_01','PROPFIELD_02',function(data) {
			// if(data.isDealPerson) hideDealGroupPerson();
			// else showDealGroupPerson();
		});
	});
	
	//新建环节,作废,处理说明赋默认值
	ClientContext.init("action","CANCEL",function(){ 
		var msg = $.trim($("#bpp_Btn_CANCEL").val());
		F("Description").S(msg);
	});
	//新建环节,作废,处理说明必填
	ClientContext.submit("action","CANCEL",function(){
		if(F("Description").G() == ""){
			alert("处理说明必填");$("#Description").focus();return;
		}
		return true;
	})
	
	
	/**
	 * 根据事件现象分类自动填充处理组和人
	 */
	var fillDealPerson = function(userId,userName,callback){
		var  effectDgreeFullName   = F("IncidentPhenoClass").G();//现象分类
		if(effectDgreeFullName == "") return;
		$.post(ctx + "/commonTree/getTreeData.action",{type:"incPhenoClass","conditionMap.fullname":effectDgreeFullName},function(data) {
			if(typeof callback != "undefined"){ 
				callback(data);
			}
			F("deal_person").S('');
			F("deal_group").S('');
			F("view_deal_person").S('');
			F("view_deal_group").S('');
			$.each(data,function(i,item){
				if(item == null) return true;
				for(var key in item){ 
			       if(userId == key){
			       		dealGroupId = item[key];
			       		if(dealGroupId.substring(0,1)=='U'){
			       			F("deal_person").S(dealGroupId.substring(2,dealGroupId.length));
			       			var deal_persion = dealGroupId.substring(2,dealGroupId.length);
			       		}
			       		if(dealGroupId.substring(0,1)=='D'){
			       			F("deal_group").S(dealGroupId.substring(2,dealGroupId.length));
			       		}
			       }
			       if(userName == key){
			       		dealGroupName = item[key];
			       		if(dealGroupName.substring(0,3)=='负责人'){
			       			F("view_deal_person").S(dealGroupName.substring(4,dealGroupName.length)+"["+deal_persion+"]");
			       		}
			       		if(dealGroupName.substring(0,4)=='负责团队'){
			       			F("view_deal_group").S(dealGroupName.substring(5,dealGroupName.length));
			       		}
			       }
			    }
			});
			
		},"json");
	}
	
	//判断处理人和处理组不能全部为空
	ClientContext.submit("action","ASSIGNStep01",function(){
		if(formatDateToTime(F("reqRepairStartTime").G())>formatDateToTime(F("repairedEndTime").G())){
			alert("报修开始时间不能大于维修结束时间");
			return false;
		}
		return checkGroupPerson();
	})
	
	//转派环节,处理说明赋默认值
	ClientContext.init("action","RENEXTStep01",function(){ 
		var msg = $.trim($("#bpp_Btn_RENEXTStep01").val());
		F("Description").S(msg);
	});
	
	//处理环节转派时，赋默认值
	ClientContext.init("action","RenextProcessing",function(){ 
		var msg = $.trim($("#bpp_Btn_RenextProcessing").val());
		F("Description").S(msg);
	});
	
	//处理环节转派时,处理过程内容不做检查
	ClientContext.submit("action","RenextProcessing",function(){
		F("IncidentSourceClass").required (false);
		F("RecoveryTime").required (false);
		F("ServiceInterruptionTime").required (false);
		F("IncidentPropertie").required (false);
		F("IncidentSolutionType").required (false);
		F("Solution").required (false);
		F("IncidentReason").required (false);
		F("ProcedureProcessing").required (false);
		F("isThirdpartSupport").required (false);
		F("reqRepairStartTime").required (false);
		F("repairedEndTime").required (false);
		F("timeout_reason").required (false);
		return true;
	})
	

	ClientContext.init("action", "SendBackProcessing", function() {
		var msg = $.trim($("#bpp_Btn_SendBackProcessing").val());
		F("Description").S(msg);
	});
	
	//处理环节转派时,处理过程内容不做检查
	ClientContext.submit("action","SendBackProcessing",function(){
		F("IncidentSourceClass").required (false);
		F("RecoveryTime").required (false);
		F("ServiceInterruptionTime").required (false);
		F("IncidentPropertie").required (false);
		F("IncidentSolutionType").required (false);
		F("Solution").required (false);
		F("IncidentReason").required (false);
		F("ProcedureProcessing").required (false);
		F("isThirdpartSupport").required (false);
		F("reqRepairStartTime").required (false);
		F("repairedEndTime").required (false);
		F("timeout_reason").required (false);
		return true;
	})
	
	
	F("Incident_EffectDgree").change("",function(){
		for(var i=0;i<priorityMap.size();i++)
		{
			if(F("Incident_EffectDgree").G().substring(0,1)+","+F("Incident_UrgentDgree").G().substring(0,1)==priorityMap.keySet().get(i))
			{
				F("Priority").S(priorityMap.values().get(i));
			}
		}
	});
	
	F("Incident_UrgentDgree").change("",function(){
		for(var i=0;i<priorityMap.size();i++)
		{
			if(F("Incident_EffectDgree").G().substring(0,1)+","+F("Incident_UrgentDgree").G().substring(0,1)==priorityMap.keySet().get(i))
			{
				F("Priority").S(priorityMap.values().get(i));
			}
		}
	});
	
	if(ClientContext.baseStatus == "草稿" || ClientContext.baseStatus == "受理建单"){
	userAutoInput("ContactPerson","ContactPersonId",function(user)
	{  
		F("ContactPersonSite").S(user.cdbUnitName);//单位
		F("ContactDepartment").S(user.cdbDepName);//部门
		F("ContactPosition").S(user.position); //职位
		F("ContactPhone").S(user.mobile); //手机
		F("ContactTel").S(user.phone); //电话
		F("ContactStation").S(user.station);//工位
		F("IsVIP").S(user.isVip);//是否vip
		F("ContactOA").S(user.oaNumber);//oa号
		showVIP();
	});
	}
	
	ClientContext.init("action","ASSIGNStep01",function(){ 
	    groupTree('deal_group','','',"deal_person");//选择部门 
		fillDealPerson('PROPFIELD_01','PROPFIELD_02',function(data) {
			// if(data.isDealPerson) hideDealGroupPerson();
			// else showDealGroupPerson();
		});
		//给处理意见赋默认值
		var msg = $.trim($("#bpp_Btn_ASSIGNStep01").val());
		F("Description").S(msg);
	})

	
	/**
	 * 建单环节关单，验证处理过程信息不能为空
	 */
	ClientContext.init("action","ToFinishStep01",function(){ 
		F("IncidentSourceClass").required (true);
		F("RecoveryTime").required (true);
		F("ServiceInterruptionTime").required (true);
		F("IncidentPropertie").required (true);
		F("IncidentSolutionType").required (true);
		F("Solution").required (true);
		F("IncidentReason").required (true);
		F("ProcedureProcessing").required (true);
		F("isThirdpartSupport").required (true);
		var msg = $.trim($("#bpp_Btn_ToFinishStep01").val());
		F("Description").S(msg);
	});
	
	/**
	 * 提交处理环节，未受理，处理过程不可填写
	 */
	if(ClientContext.baseStatus == "处理中" && F("acceptTime").G() == ""){
		F("IncidentSourceClass").readonly(true);
		F("RecoveryTime").readonly(true);
		F("ServiceInterruptionTime").readonly(true);
		F("IncidentPropertie").readonly(true);
		F("IncidentSolutionType").readonly(true);
		F("Solution").readonly(true);
		F("IncidentReason").readonly(true);
		F("ProcedureProcessing").readonly(true);
		F("isThirdpartSupport").readonly (true);
		F("reqRepairStartTime").readonly (true);
		F("repairedEndTime").readonly (true);
		F("timeout_reason").readonly (true);
	}
	
	/**
	 * 取消时，取消必填
	 */
	ClientContext.back("ToFinishStep01",function(){ 
		F("IncidentSourceClass").required (false);
		F("RecoveryTime").required (false);
		F("ServiceInterruptionTime").required (false);
		F("IncidentPropertie").required (false);
		F("IncidentSolutionType").required (false);
		F("Solution").required (false);
		F("IncidentReason").required (false);
		F("ProcedureProcessing").required (false);
		F("isThirdpartSupport").required (false);
		F("reqRepairStartTime").required (false);
		F("repairedEndTime").required (false);
		F("timeout_reason").required (false);
		return true;
	});
	
	/**
	 * 紧急程度与优先级配置关系map存放规则数据初始化：key:影响度,紧急程度，value：优先级
	 */
	function initpriorityMap(priorityMap)
	{
		priorityMap.put('1,1','1');
		priorityMap.put('2,1','2');
		priorityMap.put('3,1','3');
		priorityMap.put('1,2','2');
		priorityMap.put('2,2','2');
		priorityMap.put('3,2','3');
		priorityMap.put('1,3','3');
		priorityMap.put('2,3','3');
		priorityMap.put('3,3','4');
		return priorityMap;
	}
	/**
	 * 增加页面元素并绑定事件
	
	
	var elementId = 'Incident_EffectDgree,Incident_UrgentDgree'; //需要增加帮助信息的表单ID
	$.each(elementId.split(","), function() { 
		var docElement = this ;
		addElementHelp(docElement); //初始化页面，往页面增加帮助信息按钮，调整原布局
		$("#" + docElement + "fieldHelp").click(function(e) {
			var dialogParent = $("#" + docElement + "info").parent(); 
		    var dialogOwn = $("#" + docElement + "info").clone(); //规避因jqueryui dialog方法将div删除的问题
			addEleInfo(docElement);  
			$("#" + docElement + "info").dialog({
				open: function() {
//					elementPosX = parseInt(pageX(document.getElementById(docElement)));
//					elementPosY = parseInt(pageY(document.getElementById(docElement)));
					elementPosY= $("#"+docElement).offset().top;
					elementPosX= $("#"+docElement).offset().left;
					$(".ui-dialog").css({
						"top":elementPosY,
						"left":elementPosX
					});
					$(".ui-widget-content").css({
						"padding":"0"
					})
				},
				height:110,
				width:600,
				autoOpen: true, 
//				position:[elementPosX,elementPosY],
	            title: '通用定义',  
	            close: function () {   
	                dialogOwn.appendTo(dialogParent);  
	                $(this).dialog("destroy").remove();   
	                
	            }
			});
			
			$(".ui-dialog").css("height","110px");  
		});
	});
 */	
}



/**
 * 检查处理组和处理人
 */
function checkGroupPerson(){
	if(F("view_deal_group").G() == "" && F("view_deal_person").G() == ""){
		alert("处理人和处理组不能全部为空");
		return false;
	}
	return true;
}

/**
 * vip用户显示规则
 */
//function showVIP() 
//{
//	if (F("IsVIP").G() == "是" || F("IsVIP").G() == "VIP用户"|| F("IsVIP").G() == "1") {// 非vip用户直接隐藏
//		$("#IsVIP_ubfbox").show();
//		F("IsVIP").S("VIP用户")
//		$("#IsVIP_ubfbox span").css({"color":"#993300","font-weight":"900"});
//	} else {
//		$("#IsVIP_ubfbox").hide();
//	}
//
//}

function showVIP() 
{
	if (F("IsVIP").G() == "1" || F("IsVIP").G() == "一级VIP"){
		$("#IsVIP_ubfbox").show();
		$("#IsVIP").css({"color":"#993300","font-weight":"900"});
		F("IsVIP").S("一级VIP");
		F("viplevel").S("1");
		
	} else if(F("IsVIP").G() == "2"|| F("IsVIP").G() == "二级VIP") {
		$("#IsVIP_ubfbox").show();
		$("#IsVIP").css({"color":"#993300","font-weight":"900"});
		F("IsVIP").S("二级VIP");
		F("viplevel").S("2");
	}else{
		$("#IsVIP_ubfbox").hide();
	}

}



/**
 * 在所传入dom元素上增加元素具体帮助信息内容
 * @param elementId


function addEleInfo(elementId){
	var dtcode = elementId;
	$.post($ctx+"/incident/getDicItemByValue.action",{dtcode:dtcode},function(data) {
		if(data!=null && data!='null'){
		var jsondatas=eval("("+data+")");  
		$("#"+elementId+"info").empty();
		//$("#"+elementId+"info").append("<div style='border-bottom:1px solid #cccccc;height:30px;line-height:30px;background-color:#f2f2f2;'>"
		//								+"<div class='def_title' style='width:20%;border-right:1px solid #cccccc;float:left;font-weight:bold;text-align:center;height:30px;line-height:30px;'>级别</div> " 
		//								+"<div class='def_content' style='width:79%;float:left;text-align:center;font-weight:bold;height:30px;line-height:30px;'>通用定义</div>"
		//								+"</div>");
		$.each(jsondatas, function(i, item) {
			 $("#"+elementId+"info").append(
					 	"<div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;'>"
	                    +"<div style='width:50px;float:left;text-align:center;border-right:1px solid #cccccc'>" + item.dicfullname + "</div>" 
	                    + "<div style='width:75%;float:left;margin-left:2px;'>" + item.remark + "</div>"
	                    +"</div>");
        });
		}
	});
}
 */
/**
 * 在所传入dom元素后增加帮助信息按钮
 * @param elementId


function addElementHelp(elementId){	
//	cssPath =  $ctx+"/common/plugin/jquery/css/themes/custom-theme/jquery-ui-1.7.1.custom.css";
//	$("head").append('<link rel="stylesheet" type="text/css" href='+cssPath+' >');
	imagePath = $ctx+"/ultrabpp/runtime/forms/CBD_INCIDENT/style/help.gif";
	preEleWidth = $("#"+elementId).outerWidth(); //原宽度
	$("#"+elementId).width(preEleWidth-30); 
	$("#"+elementId+"_extend").after('<div id='+elementId+'fieldHelp><div style="position:relative;top:5px;"><img width=18px height=18px border=0px src="'+imagePath+'"></div></div>');
	$("#"+elementId+"fieldHelp").css({
		  "width":"22px",
		  "height":"22px",
		  "float":"right"
	}); 
	$("#"+elementId+"fieldHelp").after('<div id='+elementId+'info> </div>');
}
 */
//获取input的绝对位置X
function pageX(obj){
	return obj.offsetParent?obj.offsetLeft + pageX(obj.offsetParent):obj.offsetLeft;
}
//获取input的绝对位置Y
function pageY(obj){
	return obj.offsetParent?obj.offsetTop + pageY(obj.offsetParent):obj.offsetTop;
}
//截取事件影响度，事件紧急度级别
function getLevel(divalue){
	return divalue.substring(0,1);
}
