function bpp_init() {
	F("CreatorLoginName").visiable(false);
	
	/**
	 * 提交处理环节，未受理，处理过程不可填写
	 */
	if(ClientContext.baseStatus == "处理" && F("acceptTime").G() == ""){
		F("ProReason").readonly(true);
		F("ResolutionTime").readonly(true);
		F("ProReasonAnalysis").readonly(true);
		F("ProSolutions").readonly(true);
		F("SolvingProcess").readonly(true);
		 
	}
	//严重等级规则初始化
	var priorityMap = new Map();
	priorityMap = initpriorityMap(priorityMap);
	
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
	
	F("ProEffectDgree").change("ProEffectDgree",function(){
		for(var i=0;i<priorityMap.size();i++)
		{
			if(F("ProEffectDgree").G().substring(0,1)+","+F("ProUrgentDgree").G().substring(0,1)==priorityMap.keySet().get(i))
			{
				F("Priority").S(priorityMap.values().get(i));
			}
		}
	});
	
	F("ProUrgentDgree").change("ProEffectDgree",function(){
		for(var i=0;i<priorityMap.size();i++)
		{
			if(F("ProEffectDgree").G().substring(0,1)+","+F("ProUrgentDgree").G().substring(0,1)==priorityMap.keySet().get(i))
			{
				F("Priority").S(priorityMap.values().get(i));
			}
		}
	});
	

	if(ClientContext.baseStatus == "草稿"|| ClientContext.baseStatus == "新建"){
		userAutoInput("ContactPerson","ContactPersonId",function(user)
		{  
			F("ContactPersonSite").S(user.cdbUnitName);//单位
			F("ContactDepartment").S(user.cdbDepName);//部门
			F("ContactPosition").S(user.position); //职位
			F("ContactPhone").S(user.mobile); //手机
			F("ContactTel").S(user.phone); //电话
			F("ContactStation").S(user.station);//工位
			F("ContactOA").S(user.oaNumber);//oa号
		});
		}
	
	F("ContactPersonSite").readonly(true);
	F("ContactDepartment").readonly(true);
	F("ContactOA").readonly(true);
	F("ContactPosition").readonly(true);
	F("ContactStation").readonly(true);
	F("Priority").readonly(true);
	
//	/**
//	 * 新建环节，提交问题按钮检初始化
//	 */
//	ClientContext.init("action","toSubmit",function(){ 
//    groupTree('deal_group','','',"deal_person");//选择部门 
//
//})
	/**
	 * 新建环节，提交问题按钮检查项
	 */
	ClientContext.submit("action", "toSubmit", function() {
		 var deal= $("#ProSolutionPerson_AssignString").val();
		 if(typeof(deal) != "undefined"&&deal!=null&&deal!=''){
			 if(deal.substr(0,1)!="U"){
				 alert("请选择具体的处理人！");
			 }else{
				 var OA=deal.split("#")[1].replace(":","");
				 F("Processors").S(OA);
				 F("TurnSendPersonID").S(OA); //保存派发人
				 return true;
			 }
		 }
//		if(F("view_deal_person").G()!=null&& F("view_deal_person").G()!=""){
//			F("TurnSendPerson").S(F("view_deal_person").G().substr(0,F("view_deal_person").G().indexOf("[")));
//			F("TurnSendPersonID").S(F("view_deal_person").G().substring(F("view_deal_person").G().indexOf("[")+1,F("view_deal_person").G().indexOf("]")));
//			F("ProcessorsName").S(F("view_deal_person").G().substr(0,F("view_deal_person").G().indexOf("[")));
//			F("Processors").S(F("view_deal_person").G().substring(F("view_deal_person").G().indexOf("[")+1,F("view_deal_person").G().indexOf("]")));
//			return true;
//		}
		return false;
	});
	
	/**
	 * 回顾环节完成直接关单，关闭代码值为已解决
	 */
	ClientContext.submit("action", "ToFinish02", function() {
		F("closedCode").S("已解决"); 
		return true;
	});
	
	
	/**
	 * 派发环节，分派按钮问题处理信息等不校验必填,记录分派的人为处理人
	 */
	
//	ClientContext.init("action","TurnSend",function(){ 
//	    groupTree('deal_group','','',"deal_person");//选择部门 
//
//	})
	ClientContext.submit("action", "TurnSend", function() {
		F("ProReason").required (false);
		F("ResolutionTime").required (false);
		F("ProReasonAnalysis").required (false);
		F("ProSolutions").required (false);
		F("SolvingProcess").required (false);
		//根据派发树选择的人，获取全名和登陆名称并保存
		var deal= $("#ProSolutionPerson_AssignString").val();
		 if(typeof(deal) != "undefined"&&deal!=null&&deal!=""){
			 if(deal.substr(0,1)!="U"){
				 alert("请选择具体的处理人！");
			 }else{
				 var POA=deal.split("#")[1].replace(":","");
				 F("Processors").S(POA);
				 return true;
			 }
		 }
		return false;
	});
	
	
	/**
	 * 派发环节，取消按钮问题处理信息等不校验必填
	 */
	ClientContext.submit("action", "ToFinish", function() {
		F("ProEffectDgree").required (false);
		F("ProUrgentDgree").required (false);
		F("ProPhenoClass").required (false);
		F("ForecastResolutionTime").required (false);
		
		F("ProReason").required (false);
		F("ResolutionTime").required (false);
		F("ProReasonAnalysis").required (false);
		F("ProSolutions").required (false);
		F("SolvingProcess").required (false);
		return true;
	});
	/**
	 * 派发环节，完成按钮检查项
	 */
	ClientContext.init("action", "ToVerify", function() {

		$("#CreatorFullName").attr("value",$("#bpp_CreateUser").text());
		return true;
	});
	ClientContext.submit("action", "ToVerify", function() {
		F("Processors").S(F("TurnSendPersonID").G());//派发环节完成，将派发人 即为处理人
		if($("#CreatorFullName_AssignString").val()==""){
		$("#CreatorFullName_AssignString").attr("value","U#:"+F("CreatorLoginName").G()+"#:NEXT#:2#:0#:0#:0#:#:CreatorFullName#:#:#;");
		}
	return true;
});
	
	/**
	 * 派发环节，退回按钮检查项
	 */
	ClientContext.submit("action", "ToBack", function() {
		F("ProEffectDgree").required(false);
		F("ProUrgentDgree").required(false);
		F("ProPhenoClass").required(false);
		F("ForecastResolutionTime").required(false);
		F("ProReason").required (false);
		F("ResolutionTime").required (false);
		F("ProReasonAnalysis").required (false);
		F("ProSolutions").required (false);
		F("SolvingProcess").required (false);
	return true;
	});
	
	/**
	 * 处理环节，退回按钮不检查
	 * 
	 */
	ClientContext.submit("action", "ToBack01", function() {
		F("ProReason").required (false);
		F("ResolutionTime").required (false);
		F("ProReasonAnalysis").required (false);
		F("ProSolutions").required (false);
		F("SolvingProcess").required (false);
	return true;
	});
	
	/**
	 * 验证环节，直接默认给处理人，可选
	 * 
	 */
	ClientContext.init("action", "ToPass", function() {
		fillDealUser(F('Processors').G());
//		alert(F("ProcessorsName").G());
//		F("ProSolutionPerson").S(F("ProcessorsName").G());
//		$("#ProSolutionPerson").attr("value",F("ProcessorsName").G());
		return true;
	});
	ClientContext.submit("action", "ToPass", function() {
		if($("#ProSolutionPerson_AssignString").val()==""){
		$("#ProSolutionPerson_AssignString").attr("value","U#:"+F("Processors").G()+"#:NEXT#:2#:0#:0#:0#:#:#:#:#;");
		}
	return true;
});
	
	
	/**
	 * 关单环节，关单按钮设置关闭代码
	 */
	 ClientContext.submit("action", "Closed", function() {
		 F("closedCode").S(F("BaseResult").G()); 
		return true;
	});

	 
	 /**
	  * 回顾环节，通过存的处理人登录名，查询全名，并显示
	  * 
	  */
	 var fillDealUser =function(OA){
		 var sqlName = "select fullname  from bs_t_sm_user  where loginname='"+OA+"'";
			var url = ctx + "/business/getJsonBySQL.action";
			$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","sqlName":sqlName},function(data){
				var dt = eval(data);
				var fullname='';
				$.each(dt,function(i,item){
					if(item == null) return true;
					fullname = item.FULLNAME;
					
				});
//				alert(fullname);
//				F('ProcessorsName').S(fullname);
				$("#ProSolutionPerson").attr("value",fullname);
			});
	 }
	/**
	 * 根据事件现象分类自动填充处理组和人
	 */
	var fillDealPerson = function(userId,userName,callback){
		var  effectDgreeFullName   = F("ProPhenoClass").G();//现象分类
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
	/*
	 * 处理环节,受理后问题处理信息可编辑
	 * */
	ClientContext.submit("action", "ToAccept", function() {
		F("ProReason").readonly(false);
		F("ProReason").required (false);
		F("ResolutionTime").required (false);
		F("ProReasonAnalysis").required (false);
		F("ProSolutions").required (false);
		F("SolvingProcess").required (false);
	return true;
});
	
	/*
	 * 处理环节完成到达验证环节，直接默认建单人
	 * */
	
	ClientContext.init("action", "ToFinist01", function() {
//		alert($("#bpp_CreateUser").text());
//		F("ProSolutionPerson").S(F("ProcessorsName").G());
//		F("CreatorFullName").S($("#bpp_CreateUser").text());
		$("#CreatorFullName").attr("value",$("#bpp_CreateUser").text());
		return true;
	});
	ClientContext.submit("action", "ToFinist01", function() {
		if($("#CreatorFullName_AssignString").val()==""){
//			$("#CreatorFullName_AssignString")
		$("#CreatorFullName_AssignString").attr("value","U#:"+F("CreatorLoginName").G()+"#:NEXT#:2#:0#:0#:0#:#:CreatorFullName#:#:#;");
		}
	return true;
});
}