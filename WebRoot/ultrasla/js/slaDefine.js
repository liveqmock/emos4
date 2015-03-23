var ctx;//根路径
var formtimerulecols = 6;//工单时限规则列表的列数
var steptimerulecols = 7;//环节时限规则列表的列数
var formeventrulecols = 6;//工单事件规则列表的列数
var stepeventrulecols = 7;//环节事件规则列表的列数
var optype;//操作方式
var oprownum;//操作行
var baseSchema;//工单类型

//*********************SLA定义页面初始化开始***************************//
/**
 * 初始化slaDefine.jsp页面
 */
function init(){
	$("#btn_timeRule_form").click(function(){addRule('timeRule','form');});
	$("#btn_timeRule_step").click(function(){addRule('timeRule','step');});
	$("#btn_eventRule_form").click(function(){addRule('eventRule','form');});
	$("#btn_eventRule_step").click(function(){addRule('eventRule','step');});
	//读取SLA定义基本信息
	loadSlaHead(baseSchema);
	var slaId = $("#slaId").val();
	if(slaId!=""&&slaId!=undefined){
		//读取工单时限规则
		loadTimeRule($("#formTimeRuleID").val(),'form');
		//读取环节时限规则
		loadTimeRule($("#stepTimeRuleID").val(),'step');
		//读取工单事件规则
		loadEventRule($("#formEventRuleID").val(),'form');
		//读取环节事件规则
		loadEventRule($("#stepEventRuleID").val(),'step');
	}
	$("#com_btn_add").click(function(){saveSLA();});
}

/**
 * 添加规则链接
 * @param type 添加的规则类型
 * @param scope 添加规则范围（工单、环节）
 */
function addRule(type,scope){
	if('form'==scope || 'step'==scope){
		if('timeRule'==type){
			showModalDialog(ctx+'/ultrasla/addDuetimeRule.jsp?scope='+scope+'&op=add&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
		}else if('eventRule'==type){
			showModalDialog(ctx+'/ultrasla/addEventRule.jsp?scope='+scope+'&op=add&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
		}
	}
}
/**
 * 读取SLA定义基本信息
 * @param baseSchema 工单类型
 */
function loadSlaHead(baseSchema){
	if(baseSchema==''||baseSchema==undefined){
		return;
	}
	$.ajax({
		type:"POST",
		async:false,
		url:ctx+"/slaDefine/loadSlaHead.action?rnd="+(new Date()).getTime(),
		data:{baseSchema:baseSchema},
		dataType:"xml",
		success:function(xmlObj){
			if(xmlObj){
				var $head = $(xmlObj).find("head");
				if($head.find("id").length==0){
					//没有找到SLA定义，为新建
					$("#slaId").val("");
				}else{
					$("#slaId").val($head.find("id").text());//id
					$("#slaName").val($head.find("name").text());//名称
					$("#slavalidstarttime").val($head.find("vldsttime").text());//有效开始时间
					$("#slavalidendtime").val($head.find("vldedtime").text());//有效结束时间
					$("#slastatus").val($head.find("status").text());//状态
					$("#slaRemark").val($head.find("remark").text());//描述
					$("#formTimeRuleID").val($head.find("ftrid").text());//工单时限规则ID
					$("#stepTimeRuleID").val($head.find("strid").text());//工单_环节时限规则ID
					$("#formEventRuleID").val($head.find("ferid").text());//工单事件规则ID
					$("#stepEventRuleID").val($head.find("serid").text());//工单_环节事件规则ID
				}
			}
		}
	});
}
/**
 * 读取时限规则
 */
function loadTimeRule(ids,scope){
	if(ids!="" && ids!=undefined){
		var stepArr;
		var idArr;
		var ruleTb;
		if("form"==scope){
			ruleTb = $("#formTimeRuleTb");
			idArr = ids.split(",");
		}else if("step"==scope){
			ruleTb = $("#stepTimeRuleTb");
			stepArr = new Array();
			idArr = new Array();
			var tempArr = ids.split(",");
			var tlen = tempArr.length;
			var ttArr;
			for(var i=0;i<tlen;i++){
				ttArr = tempArr[i].split(":");
				if(ttArr.length==2){
					stepArr[i] = ttArr[0];
					idArr[i] = ttArr[1];
				}
			}
		}
//		if(stepArr!=undefined)
//			alert(stepArr.length);
//		alert(idArr.length);
		var len = idArr.length;
		var snum = 1;
		if(len>0){
			ruleTb.find("tr").eq(1).remove();
		}
		for(var i=0;i<len;i++){
			var wfstep;
			if("step"==scope){
				wfstep = stepArr[i];
			}
			$.ajax({
				type:"POST",
				async:false,
				url:ctx+"/slaDefine/loadTimeRule.action?rnd="+(new Date()).getTime(),
				data:{id:idArr[i]},
				dataType:"xml",
				success:function(xmlObj){
					if(xmlObj){
						var $rule = $(xmlObj).find("rule");
						if($rule.find("id").length>0 && ruleTb){
							var html = "<tr ruleid='"+$rule.find("id").text()+"' align='left'>\n";
							html    +=		"<td field='snum'>"+snum+"</td>\n" //序号
							snum++;
							if('step'==scope){
								html += 	"<td field='wfstep'>"+wfstep+"</td>\n"; //环节
							}
							html    +=		"<td field='rulename' style='display:none'>"+$rule.find("name").text()+"</td>\n"; //规则名称
							html    +=		"<td field='ruletype' style='display:none'>"+$rule.find("type").text()+"</td>\n"; //规则类别
							html    +=		"<td field='condexpress'>"+$rule.find("express").text()+"</td>\n"; //条件表达式
							var rulePri = $rule.find("rulePri").text();
							var rulePriTxt = "";
							if(rulePri=="1"){
								rulePriTxt = "低";
							} else if (rulePri=="2") {
								rulePriTxt = "一般";
							} else if (rulePri=="3") {
								rulePriTxt = "中";
							} else if (rulePri=="4") {
								rulePriTxt = "高";
							} else if (rulePri=="5") {
								rulePriTxt = "紧急";
							} 
							html    +=		"<td field='rulePri' value='"+rulePri+"' style='display:none'>"+rulePriTxt+"</td>\n"; //优先级
							html    +=		"<td field='accAllMinute'>"+$rule.find("acctime").text()+"</td>\n"; //受理时限
							html    +=		"<td field='proAllMinute'>"+$rule.find("protime").text()+"</td>\n"; //处理时限
							html    += 		"<td field='op'>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"update\",this,\""+scope+"\")'>修改</a>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"preview\",this,\""+scope+"\")'>预览</a>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"delete\",this,\""+scope+"\")'>删除</a>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"copy\",this,\""+scope+"\")'>复制</a>\n"
										  + 	"</td>\n"; //操作
							html    +=	"</tr>\n";
							ruleTb.append(html);
						}
					}
				}
			});
		}
	}
}
/**
 * 读取事件规则
 */
function loadEventRule(ids,scope){
	if(ids!="" && ids!=undefined){
		var stepArr;
		var trigconArr;
		var idArr;
		var ruleTb;
		if("form"==scope){
			ruleTb = $("#formEventRuleTb");
			trigconArr = new Array();
			idArr = new Array();
			var tempArr = ids.split(",");
			var tlen = tempArr.length;
			var ttArr;
			for(var i=0;i<tlen;i++){
				ttArr = tempArr[i].split(":");
				if(ttArr.length==2){
					trigconArr[i] = ttArr[0];
					idArr[i] = ttArr[1];
				}else{
					trigconArr[i] ="";
					idArr[i] = "";
				}
			}
		}else if("step"==scope){
			ruleTb = $("#stepEventRuleTb");
			stepArr = new Array();
			idArr = new Array();
			var tempArr = ids.split(",");
			var tlen = tempArr.length;
			var ttArr;
			for(var i=0;i<tlen;i++){
				ttArr = tempArr[i].split(":");
				if(ttArr.length==2){
					stepArr[i] = ttArr[0];
					idArr[i] = ttArr[1];
				}else{
					stepArr[i] = "";
					idArr[i] = "";
				}
			}
			trigconArr = new Array();
			for(var i=0;i<tlen;i++){
				ttArr = stepArr[i].split("@");
				if(ttArr.length==2){
					stepArr[i] = ttArr[0];//环节号
					trigconArr[i] = ttArr[1];//触发规则ID
				}else{
					stepArr[i] = "";
					trigconArr[i] = "";
				}
			}
		}
//		if(stepArr!=undefined)
//			alert(stepArr);
//		alert(idArr);
		var len = idArr.length;
		var snum = 1;
		if(len>0){
			ruleTb.find("tr").eq(1).remove();
		}
		for(var i=0;i<len;i++){
			var wfstep = "";
			var trigcondition = "";
			if("step"==scope){
				wfstep = stepArr[i];
				trigcondition = trigconArr[i];
			}
			if("form"==scope){
				trigcondition = trigconArr[i];
			}
			$.ajax({
				type:"POST",
				async:false,
				url:ctx+"/slaDefine/loadEventRule.action?rnd="+(new Date()).getTime(),
				data:{id:idArr[i]},
				dataType:"xml",
				success:function(xmlObj){
					if(xmlObj){
						var $rule = $(xmlObj).find("rule");
						if($rule.find("id").length>0 && ruleTb){
							var html = "<tr ruleid='"+$rule.find("id").text()+"' align='left'>\n";
							html    +=		"<td field='snum'>"+snum+"</td>\n" //序号
							snum++;
							if('step'==scope){
								html += 	"<td field='wfstep'>"+wfstep+"</td>\n"; //环节
							}
							html    +=		"<td field='rulename'>"+$rule.find("name").text()+"</td>\n"; //规则名称
							html    +=		"<td field='ruletype' style='display:none'>"+$rule.find("type").text()+"</td>\n"; //规则类别
							html    +=		"<td field='ovdureRule' value='"+trigcondition+"'>"+$rule.find("ovRule").text()+"</td>\n"; //触发规则
							html    +=		"<td field='overAllMinute'>"+$rule.find("ovtime").text()+"</td>\n"; //逾期时间
							html    +=		"<td field='condexpress' style='display:none'>"+$rule.find("express").text()+"</td>\n"; //条件表达式
							var rulePri = $rule.find("rulePri").text();
							var rulePriTxt = "";
							if(rulePri=="1"){
								rulePriTxt = "低";
							} else if (rulePri=="2") {
								rulePriTxt = "一般";
							} else if (rulePri=="3") {
								rulePriTxt = "中";
							} else if (rulePri=="4") {
								rulePriTxt = "高";
							} else if (rulePri=="5") {
								rulePriTxt = "紧急";
							} 
							html    +=		"<td field='rulePri' value='"+rulePri+"' style='display:none'>"+rulePriTxt+"</td>\n"; //优先级
							html    +=		"<td field='noticedefault' style='display:none'>"+$rule.find("ntcdft").text()+"</td>\n"; //是否通知当前处理对象
							html    +=		"<td field='defaultchecktype' style='display:none'>"+$rule.find("dftchktyp").text()+"</td>\n"; //当前处理对象查询方式
							html    +=		"<td field='defaultcheckparam' style='display:none'>"+$rule.find("dftchkparam").text()+"</td>\n"; //当前处理对象查询参数
							html    +=		"<td field='noticeto_show' style='display:none'>"+$rule.find("ntc_show").text()+"</td>\n"; //通知到
							html    +=		"<td field='noticeto' style='display:none'>"+$rule.find("ntcto").text()+"</td>\n"; //通知到ID
							html    +=		"<td field='duplicateto_show' style='display:none'>"+$rule.find("dplct_show").text()+"</td>\n"; //抄送给
							html    +=		"<td field='duplicateto' style='display:none'>"+$rule.find("dplctto").text()+"</td>\n"; //抄送给ID
							html    +=		"<td field='upgradeto_show' style='display:none'>"+$rule.find("upgrd_show").text()+"</td>\n"; //升级到
							html    +=		"<td field='upgradeto' style='display:none'>"+$rule.find("upgrdto").text()+"</td>\n"; //升级ID
							var fixedhandler = $rule.find("fixedhandler").text(); // 格式 mobile:电话1,电话2,...;mail:邮箱1,邮箱2,... 或 mobile:电话1,电话2,... 或 mail:邮箱1,邮箱2,... 或 空
							var fixedmobile = '';
							var fixedmail = '';
							var _int_fh = fixedhandler.indexOf(';');
							if(_int_fh > 0) {
								fixedmobile = fixedhandler.substring(0, _int_fh);
								fixedmobile = fixedmobile.replace('mobile:', '');
								fixedmail = fixedhandler.substring(_int_fh + 1);
								fixedmail = fixedmail.replace('mail:', '');
							} else {
								if(_int_fh == 0) {
									fixedhandler = fixedhandler.replace(';', '');
								}
								if(fixedhandler.indexOf('mobile:') >= 0) {
									fixedmobile = fixedhandler.replace('mobile:', '');
								} else if (fixedhandler.indexOf('mail:') >= 0) {
									fixedmail = fixedhandler.replace('mail:', '');
								}
							}
							html    +=		"<td field='fixedmobile' style='display:none'>"+fixedmobile+"</td>\n"; //固定通知电话
							html    +=		"<td field='fixedmail' style='display:none'>"+fixedmail+"</td>\n"; //固定通知邮件
							html    +=		"<td field='selfDefineComp' style='display:none'>"+$rule.find("selfcomp").text()+"</td>\n"; //自定义通知组件ID
							var acttype = $rule.find("acttype").text();
							var acttypevalue = "";
							if("1"==acttype){
								acttypevalue = "短信通知";
							}else if("2"==acttype){
								acttypevalue = "邮件通知";
							}else if("3"==acttype){
								acttypevalue = "短信及邮件通知";
							}else if("9"==acttype){
								acttypevalue = "自定义处理";
							}
							html    +=		"<td field='actiontype' value='"+acttype+"'>"+acttypevalue+"</td>\n"; //动作类型
							html    +=		"<td field='actionparam' style='display:none'>"+$rule.find("actparam").text()+"</td>\n"; //动作参数
							html	+=		"<td field='calendartype' style='display:none'>"+$rule.find("calendartype").text()+"</td>\n";//节假日业务类别
							html    +=		"<td field='noticetimes' style='display:none'>"+$rule.find("ntctimes").text()+"</td>\n"; //通知次数
							html    +=		"<td field='noticeinterval' style='display:none'>"+$rule.find("ntcintval").text()+"</td>\n"; //事件间隔
							html    +=		"<td field='noticestarttime' style='display:none'>"+$rule.find("ntcsttime").text()+"</td>\n"; //通知开始时间
							html    +=		"<td field='noticeendtime' style='display:none'>"+$rule.find("ntcedtime").text()+"</td>\n"; //通知结束时间
							html    +=		"<td field='overduresend' style='display:none'>"+$rule.find("sendaft").text()+"</td>\n"; //是否补发
							html    +=		"<td field='paramprotype' style='display:none'>"+$rule.find("paramprotype").text()+"</td>\n"; //内容参数处理方式
							html    +=		"<td field='processparam' style='display:none'>"+$rule.find("processparam").text()+"</td>\n"; //内容参数处理参数
							html    +=		"<td field='topic' style='display:none'>"+$rule.find("topic").text()+"</td>\n"; //主题
							html    +=		"<td field='content' style='display:none'>"+$rule.find("content").text()+"</td>\n"; //内容
							html    += 		"<td field='op'>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"update\",this,\""+scope+"\")'>修改</a>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"preview\",this,\""+scope+"\")'>预览</a>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"delete\",this,\""+scope+"\")'>删除</a>\n"
										  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"copy\",this,\""+scope+"\")'>复制</a>\n"
										  + 	"</td>\n"; //操作
							html    +=	"</tr>\n";
							ruleTb.append(html);
						}
					}
				}
			});
		}
	}
}
//*********************SLA定义页面初始化结束***************************//

//*********************事件规则函数开始***************************//
/**
 * 初始化事件规则添加页面
 */
function initEventRule(scope){
	$("#allStep").click(function(){
		var preStep = $("#wfstep").val();
		if(preStep!='ALL'){
			$("#wfstep2").val(preStep);
		}
		if(this.checked==true){
			$("#wfstep").val(this.value);
		}else{
			$("#wfstep").val($("#wfstep2").val());
		}
	});
	$("#wfstep").bind("change",function(){
		if(this.value=='ALL'){
			$("#allStep").attr("checked",true);
		}else{
			$("#allStep").attr("checked",false);
		}
	});
	$("#condexpress").bind("keydown",function(event){
//		var srcCode = event.keyCode;
//		if (srcCode == undefined) {
//			srcCode = event.which;
//		}
//		if(srcCode==8					//退格键
//				||srcCode==37			//←键
//				||srcCode==38			//↑键
//				||srcCode==39			//→键
//				||srcCode==40){			//↓键
//			return true;
//		}else{
//			return false;
//		}
		return true;
	});
	$("#addcond").click(function(){
		var attname = $("#attname").val();
		var attvalue = $("#attvalue").val();
		var attrela = $("#attrela").val();
		if(attname==""||attvalue==""){
			return;
		}
		//$("#condexpress").text($("#condexpress").text()+"#"+attname+"#"+attrela+attvalue);
		insertText($("#condexpress")[0],"#"+attname+"#"+attrela+attvalue);
	});
	$("#opbtn :input").click(function(){
		//$("#condexpress").text($("#condexpress").text()+this.value);
		insertText($("#condexpress")[0],this.value);
	});
	//读取自定义通知组件
	$.ajax({
		type:"POST",
		async:false,
		url:ctx+"/slaDefine/readSelfNoticeComp.action?rnd="+(new Date()).getTime(),
		dataType:"json",
		success:function(jsonObj){
			if(jsonObj){
				var len = jsonObj.length;
				if(len>0){
					var compTb = $("#selfCompDiv table");
					var rowhtml = "";
					var counter = 0;
					var span = 3;
					$(jsonObj).each(function(i){
						counter += 1;
						if(counter==1||(counter-1)%span==0){
							rowhtml += "<tr>\n";
						}
						rowhtml     += 	"<td>";
						rowhtml     += 		"<input type='checkbox' name='selfDefineComp' value='"+this.pid+"'/>"+decodeURIComponent(this.name);
						rowhtml     += 	"</td>\n";
						if(counter==len||counter%span==0){
							rowhtml += "</tr>\n";
						}
					});
					compTb.append(rowhtml);
					$("#selfCompDiv").css("display","block");
					$("#noCompSpan").css("display","none");
				}
			}
		}
	});
	var schema = "";
	if(window.dialogArguments){
		schema = window.dialogArguments.baseSchema;
	}
//	alert(schema);
	//读取逾期规则
	$.ajax({
		type:"POST",
		async:false,
		url:ctx+"/slaDefine/getTrigRule.action?rnd="+(new Date()).getTime(),
		data:{baseSchema:schema,scope:scope},
		dataType:"json",
		success:function(jsonObj){//[{"id":"001","name":"name1"}]
			if(jsonObj){
				var len = jsonObj.length;
				if(len>0){
					var compTb = $("#trigRuleDiv table");
					var rowhtml = "";
					var counter = 0;
					var span = 4;
					$(jsonObj).each(function(i){
						counter += 1;
						if(counter==1||(counter-1)%span==0){
							rowhtml += "<tr>\n";
						}
						rowhtml     += 	"<td>";
						rowhtml     += 		"<input type='radio' name='ovdureRule' value='"+this.id+"'/><span>"+decodeURIComponent(this.name)+"</span>";
						rowhtml     += 	"</td>\n";
						if(counter==len||counter%span==0){
							rowhtml += "</tr>\n";
						}
					});
					compTb.append(rowhtml);
					$("#trigRuleDiv").css("display","block");
					$("#noTrigRuleDiv").css("display","none");
				}
			}
		}
	});
	
	$("#noticeBtn").unbind('click');
	$("#noticeBtn").click(function(){selectHanlders('noticeto','noticeto_show');});
	$("#duplicateBtn").unbind('click');
	$("#duplicateBtn").click(function(){selectHanlders('duplicateto','duplicateto_show');});
	$("#upgradeBtn").unbind('click');
	$("#upgradeBtn").click(function(){selectHanlders('upgradeto','upgradeto_show');});
	$("#fixedmobileBtn").unbind('click');
	$("#fixedmobileBtn").click(function(){clearHanlders('fixedmobile');});
	$("#fixedmailBtn").unbind('click');
	$("#fixedmailBtn").click(function(){clearHanlders('fixedmail');});
	$("#actiontype").unbind('change');
	$("#actiontype").bind("change",function(i){
		if(this.value=='9'){
			$("#actionparamRow").css("display","block");
		}else{
			$("#actionparam").val("");
			$("#actionparamRow").css("display","none");
		}
	});
	$("#actionparamRow").css("display","none");
	if(optype=='add'){
		$("#addRuleBtn").unbind('click');
		$("#addRuleBtn").click(function(){addEventRule(scope);});
		$("#cancelBtn").click(function(){window.close();});
	} else if(optype=='update'){
		if(oprownum<1){
			return;
		}
		$("#addRuleBtn").unbind('click');
		$("#addRuleBtn").bind('click',function(){updateEventRule(scope);});//执行修改
		$("#cancelBtn").click(function(){window.close();});
		readEventRuleRow(scope);
	} else if(optype=='preview'){
		$("#cancelBtn").val('关闭');
		$("#cancelBtn").click(function(){window.close();});
		$("#addRuleBtn").css("display","none");
		if(oprownum<1){
			return;
		}
		readEventRuleRow(scope);
	}
}

/**
 * 添加事件规则
 */
function addEventRule(scope){
	if(Validator.Validate(document.forms[0],2)==false){
		return;
	}
	if('form'!=scope && 'step'!=scope){
		alert('valid scope value!');
		return;
	}
	var parentWindow = window.dialogArguments;
	if(parentWindow==null){
		alert('parent window dosen\'t exist!');
		return;
	}
	var parentDocument = parentWindow.document;
	if(parentDocument==null){
		alert('parent document dosen\'t exist!');
		return;
	}
	var ruleTb = null;
	if('form'==scope){
		ruleTb = parentDocument.getElementById("formEventRuleTb");//工单事件规则列表
	}else if('step'==scope){
		ruleTb = parentDocument.getElementById("stepEventRuleTb");//环节事件规则列表
	}
	if(ruleTb){
		//计算序号
		var snum = 1;
		if(ruleTb.rows.length==2&&ruleTb.rows[1].getAttribute('rowtag')=='norule'){
			ruleTb.deleteRow(1);
		}else{
			snum = ruleTb.rows.length;
		}
		//计算总过逾期多少分钟
		var overday = $('#overday').val();//逾期天
		var overhour = $('#overhour').val();//逾期小时
		var overminute = $('#overminute').val();//逾期分钟
		var overAllMinute = 0;
		if(isNaN(parseInt(overday))==false){
			overAllMinute += parseInt(overday)*24*60;
		}
		if(isNaN(parseInt(overhour))==false){
			overAllMinute += parseInt(overhour)*60;
		}
		if(isNaN(parseInt(overminute))==false){
			overAllMinute += parseInt(overminute);
		}
		if(overAllMinute<=0){
			alert("请填写逾期时间！");
			return;
		}
		if($(":radio[name='ovdureRule']:checked").val()==undefined)
		{
			alert("请选择逾期规则！");
			return;
		}
		//查找自定义通知对象组件
		var selfCompId = "";
		$(":checkbox[name='selfDefineComp']:checked").each(function(i){
			if(selfCompId!=""){
				selfCompId += ",";
			}
			selfCompId += this.value;
		});
		//拼接规则行HTML
		var newrulerow = "<tr align='left'>\n";
		newrulerow    += 	"<td field='snum'>"+snum+"</td>\n" //序号
		if('step'==scope){
			newrulerow += 	"<td field='wfstep'>"+$("#wfstep").val()+"</td>\n"; //环节
		}
		var actionTypeSel = $("#actiontype").get(0);
		newrulerow    += 	"<td field='rulename'>"+$("#rulename").val()+"</td>\n"; //规则名称
		newrulerow    += 	"<td field='ruletype' style='display:none'>"+$("#ruletype").val()+"</td>\n"; //规则类别
		newrulerow    += 	"<td field='ovdureRule' value='"+$(":radio[name='ovdureRule']:checked").val()+"'>"+$(":radio[name='ovdureRule']:checked + span").text()+"</td>\n"; //触发规则
		newrulerow    += 	"<td field='overAllMinute'>"+overAllMinute+"</td>\n"; //逾期时间
		newrulerow    += 	"<td field='condexpress' style='display:none'>"+$("#condexpress").val()+"</td>\n"; //条件表达式
		var index = $("#rulePri")[0].selectedIndex;
		newrulerow    += 	"<td field='rulePri' value='"+$("#rulePri").val()+"' style='display:none'>"+$("#rulePri")[0].options[index].text+"</td>\n"; //优先级
		newrulerow    += 	"<td field='noticedefault' style='display:none'>"+$(":radio[name='noticedefault']:checked").val()+"</td>\n"; //是否通知当前处理对象
		newrulerow    += 	"<td field='defaultchecktype' style='display:none'>"+$("#defaultchecktype").val()+"</td>\n"; //当前处理对象查询方式
		newrulerow    += 	"<td field='defaultcheckparam' style='display:none'>"+$("#defaultcheckparam").val()+"</td>\n"; //当前处理对象查询参数
		newrulerow    += 	"<td field='noticeto_show' style='display:none'>"+$("#noticeto_show").val()+"</td>\n"; //通知到
		newrulerow    += 	"<td field='noticeto' style='display:none'>"+$("#noticeto").val()+"</td>\n"; //通知到ID
		newrulerow    += 	"<td field='duplicateto_show' style='display:none'>"+$("#duplicateto_show").val()+"</td>\n"; //抄送给
		newrulerow    += 	"<td field='duplicateto' style='display:none'>"+$("#duplicateto").val()+"</td>\n"; //抄送给ID
		newrulerow    += 	"<td field='upgradeto_show' style='display:none'>"+$("#upgradeto_show").val()+"</td>\n"; //升级到
		newrulerow    += 	"<td field='upgradeto' style='display:none'>"+$("#upgradeto").val()+"</td>\n"; //升级ID
		newrulerow    += 	"<td field='fixedmobile' style='display:none'>"+$("#fixedmobile").val()+"</td>\n"; //固定通知电话
		newrulerow    += 	"<td field='fixedmail' style='display:none'>"+$("#fixedmail").val()+"</td>\n"; //固定通知邮件
		newrulerow    += 	"<td field='selfDefineComp' style='display:none'>"+selfCompId+"</td>\n"; //自定义通知组件ID
		newrulerow    += 	"<td field='actiontype' value='"+actionTypeSel.value+"'>"+actionTypeSel.options[actionTypeSel.selectedIndex].text+"</td>\n"; //动作类型
		newrulerow    += 	"<td field='actionparam' style='display:none'>"+$("#actionparam").val()+"</td>\n"; //动作参数
		newrulerow    += 	"<td field='calendartype' style='display:none'>"+$("#calendartype").val()+"</td>\n";//节假日业务类别
		newrulerow    += 	"<td field='noticetimes' style='display:none'>"+$("#noticetimes").val()+"</td>\n"; //通知次数
		newrulerow    += 	"<td field='noticeinterval' style='display:none'>"+$("#noticeinterval").val()+"</td>\n"; //事件间隔
		newrulerow    += 	"<td field='noticestarttime' style='display:none'>"+$("#noticestarttime").val()+"</td>\n"; //通知开始时间
		newrulerow    += 	"<td field='noticeendtime' style='display:none'>"+$("#noticeendtime").val()+"</td>\n"; //通知结束时间
		newrulerow    += 	"<td field='overduresend' style='display:none'>"+$(":radio[name='overduresend']:checked").val()+"</td>\n"; //是否补发
		newrulerow    += 	"<td field='paramprotype' style='display:none'>"+$("#paramprotype").val()+"</td>\n"; //内容参数处理方式
		newrulerow    += 	"<td field='processparam' style='display:none'>"+$("#processparam").val()+"</td>\n"; //内容参数处理参数
		newrulerow    += 	"<td field='topic' style='display:none'>"+$("#topic").val()+"</td>\n"; //主题
		newrulerow    += 	"<td field='content' style='display:none'>"+$("#content").val()+"</td>\n"; //内容
		newrulerow    += 	"<td field='op'>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"update\",this,\""+scope+"\")'>修改</a>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"preview\",this,\""+scope+"\")'>预览</a>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"delete\",this,\""+scope+"\")'>删除</a>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opEventRuleRow(\"copy\",this,\""+scope+"\")'>复制</a>\n"
					  + 	"</td>\n"; //操作
		newrulerow    += "</tr>\n";
		//追加规则到表格
		$(ruleTb).append(newrulerow);
	}
	window.close();
}

/**
 * 操作事件规则行
 */
function opEventRuleRow(op,source,scope){
	var rownum = $(source).parent().parent().get(0).rowIndex;
	var ruleTb = null;
	if("form"==scope){
		ruleTb = $("#formEventRuleTb");//工单事件规则列表
	}else if("step"==scope){
		ruleTb = $("#stepEventRuleTb");//环节事件规则列表
	}
	if(ruleTb==null){
		return;
	}
	if('update'==op){
		showModalDialog(ctx+'/ultrasla/addEventRule.jsp?scope='+scope+'&op=update&rownum='+rownum+'&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
	}else if('preview'==op){
		showModalDialog(ctx+'/ultrasla/addEventRule.jsp?scope='+scope+'&op=preview&rownum='+rownum+'&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
	}else if('delete'==op){
		if(window.confirm('确定删除该规则吗？')==false){
			return;
		}
		var delrow = ruleTb.find("tr").eq(rownum);
		var ruleid = delrow.attr("ruleid");
		if(ruleid!=undefined){
			var delid = $("#deledEventRuleID").val();
			if(delid==""||delid==undefined){
				delid = ruleid;
			}else{
				delid += "," + ruleid;
			}
			$("#deledEventRuleID").val(delid);
		}
		delrow.remove();
		if(ruleTb.get(0).rows.length == 1){
			//添加无规则行
			var spanval = 0;
			if("form"==scope){
				spanval = formeventrulecols;
			}else if("step"==scope){
				spanval = stepeventrulecols;
			}
			ruleTb.append("<tr rowtag='norule'>\n<td colspan='"+spanval+"' align='center'>无规则！</td>\n</tr>");
		}else if(ruleTb.get(0).rows.length>1 && rownum<ruleTb.get(0).rows.length){
			//更新序号
			ruleTb.find("tr:gt(0)").each(function(index,curtd){
				$(curtd).find("> td:first").text(index+1);
			});
		}
	}else if(op=="copy"){
		var ruleRow = ruleTb.find("tr").eq(rownum);
		var newrulerow = ruleRow.clone(true);
		newrulerow.attr("ruleid","");
		newrulerow.find("td").css("color","blue");
		ruleRow.after(newrulerow);
		//更新序号
		ruleTb.find("tr:gt(0)").each(function(index,curtd){
			$(curtd).find("> td:first").text(index+1);
		});
	}
}

/**
 * 读取事件规则行
 */
function readEventRuleRow(scope){
	var $parentDoc = null;
	if(window.dialogArguments){
		$parentDoc = $(window.dialogArguments.document);
	}
	if($parentDoc==null){
		return;
	}
	var ruleTb = null;
	if("form"==scope){
		ruleTb = $parentDoc.find("#formEventRuleTb");
	}else if("step"==scope){
		ruleTb = $parentDoc.find("#stepEventRuleTb");
	}
	if(ruleTb){
		var ruleRow = ruleTb.find("tr").eq(oprownum);//注意 thead 和 tbody的存在
		if(ruleRow!=null){
			//计算所有超时天/小时/分钟
			var overAllMinute = parseInt(ruleRow.find("td[field='overAllMinute']").text());
			var overday = parseInt(overAllMinute/(24*60));
			var overhour = parseInt((overAllMinute-overday*24*60)/60);
			var overminute = overAllMinute-overday*24*60-overhour*60;
			if('step'==scope){
				var stepVal = ruleRow.find("td[field='wfstep']").text();
				$("#wfstep").val(stepVal);//环节
				if(stepVal=="ALL"){
					$("#allStep").attr("checked",true);
				}
			}
			$("#rulename").val(ruleRow.find("td[field='rulename']").text());//规则名称
			$("#ruletype").val(ruleRow.find("td[field='ruletype']").text());//规则类别
			$(":radio[name='ovdureRule'][value='"+ruleRow.find("td[field='ovdureRule']").attr("value")+"']").attr("checked","true");//规则
			$('#overday').val(overday);//逾期天
			$('#overhour').val(overhour);//逾期小时
			$('#overminute').val(overminute);//逾期分钟
			$("#condexpress").val(ruleRow.find("td[field='condexpress']").text());//条件表达式
			$("#rulePri").val(ruleRow.find("td[field='rulePri']").attr("value"));//优先级
			$(":radio[name='noticedefault'][value='"+ruleRow.find("td[field='noticedefault']").text()+"']").attr("checked","true");//是否通知当前处理对象
			$("#defaultchecktype").val(ruleRow.find("td[field='defaultchecktype']").text());//当前处理对象查询方式
			$("#defaultcheckparam").val(ruleRow.find("td[field='defaultcheckparam']").text());//当前处理对象查询参数
			$("#noticeto_show").val(ruleRow.find("td[field='noticeto_show']").text());//通知到
			$("#noticeto").val(ruleRow.find("td[field='noticeto']").text());//通知到ID
			$("#duplicateto_show").val(ruleRow.find("td[field='duplicateto_show']").text());//抄送给
			$("#duplicateto").val(ruleRow.find("td[field='duplicateto']").text());//抄送给ID
			$("#upgradeto_show").val(ruleRow.find("td[field='upgradeto_show']").text());//升级到
			$("#upgradeto").val(ruleRow.find("td[field='upgradeto']").text());//升级到ID
			$("#fixedmobile").val(ruleRow.find("td[field='fixedmobile']").text());//固定通知电话
			$("#fixedmail").val(ruleRow.find("td[field='fixedmail']").text());//固定通知邮件
			//分析自定义通知组件ID
			var selfDefineComp = ruleRow.find("td[field='selfDefineComp']").text();
			if(selfDefineComp!==null&&selfDefineComp!=undefined&&selfDefineComp!=""){
				var compArr = selfDefineComp.split(",");
				var len = compArr.length;
				for(var i=0;i<len;i++){
					$(":checkbox[name='selfDefineComp'][value='"+compArr[i]+"']").attr("checked","true");//自定义通知组件ID
				}
			}
			$("#actiontype").val(ruleRow.find("td[field='actiontype']").attr("value"));//动作类型
			if(ruleRow.find("td[field='actiontype']").attr("value")=="9"){
				$("#actionparamRow").css("display","block");
			}
			$("#actionparam").val(ruleRow.find("td[field='actionparam']").text());//动作类型参数
			$("#calendartype").val(ruleRow.find("td[field='calendartype']").text());//节假日业务类别
			$("#noticetimes").val(ruleRow.find("td[field='noticetimes']").text());//通知次数
			$("#noticeinterval").val(ruleRow.find("td[field='noticeinterval']").text());//时间间隔
			$("#noticestarttime").val(ruleRow.find("td[field='noticestarttime']").text());//通知开始时间
			$("#noticeendtime").val(ruleRow.find("td[field='noticeendtime']").text());//通知结束时间
			$(":radio[name='overduresend'][value='"+ruleRow.find("td[field='overduresend']").text()+"']").attr("checked","true");//是否补发
			$("#paramprotype").val(ruleRow.find("td[field='paramprotype']").text());//内容参数处理方式
			$("#processparam").val(ruleRow.find("td[field='processparam']").text());//内容参数处理参数
			$("#topic").val(ruleRow.find("td[field='topic']").text());//主题
			$("#content").val(ruleRow.find("td[field='content']").text());//内容
		}
	}
}
function setEventRuleData(ruleid){
	$.ajax({
		type:"POST",
		async:false,
		url:ctx+"/slaDefine/loadEventRule.action?rnd="+(new Date()).getTime(),
		data:{id:ruleid},
		dataType:"xml",
		success:function(xmlObj){
			var $rule = $(xmlObj).find("rule");
			var overAllMinute = parseInt($rule.find("ovtime").text());
			var overday = parseInt(overAllMinute/(24*60));
			var overhour = parseInt((overAllMinute-overday*24*60)/60);
			var overminute = overAllMinute-overday*24*60-overhour*60;
			/*
			if('step'==scope){
				var stepVal = ruleRow.find("td[field='wfstep']").text();
				$("#wfstep").val(stepVal);//环节
				if(stepVal=="ALL"){
					$("#allStep").attr("checked",true);
				}
			}*/
			$("#rulename").val($rule.find("name").text());//规则名称
			$("#ruletype").val($rule.find("type").text());//规则类别
			//$(":radio[name='ovdureRule'][value='"+ruleRow.find("td[field='ovdureRule']").attr("value")+"']").attr("checked","true");//规则
			$('#overday').val(overday);//逾期天
			$('#overhour').val(overhour);//逾期小时
			$('#overminute').val(overminute);//逾期分钟
			$("#condexpress").val($rule.find("express").text());//条件表达式
			$("#rulePri").val($rule.find("rulePri").text());//优先级
			$(":radio[name='noticedefault'][value='"+$rule.find("ntcdft").text()+"']").attr("checked","true");//是否通知当前处理对象
			$("#defaultchecktype").val($rule.find("dftchktyp").text());//当前处理对象查询方式
			$("#defaultcheckparam").val($rule.find("dftchkparam").text());//当前处理对象查询参数
			$("#noticeto_show").val($rule.find("ntc_show").text());//通知到
			$("#noticeto").val($rule.find("ntcto").text());//通知到ID
			$("#duplicateto_show").val($rule.find("dplct_show").text());//抄送给
			$("#duplicateto").val($rule.find("dplctto").text());//抄送给ID
			$("#upgradeto_show").val($rule.find("upgrd_show").text());//升级到
			$("#upgradeto").val($rule.find("upgrdto").text());//升级到ID
			var fixedhandler = $rule.find("fixedhandler").text(); // 格式 mobile:电话1,电话2,...;mail:邮箱1,邮箱2,... 或 mobile:电话1,电话2,... 或 mail:邮箱1,邮箱2,... 或 空
			var fixedmobile = '';
			var fixedmail = '';
			var _int_fh = fixedhandler.indexOf(';');
			if(_int_fh > 0) {
				fixedmobile = fixedhandler.substring(0, _int_fh);
				fixedmobile = fixedmobile.replace('mobile:', '');
				fixedmail = fixedhandler.substring(_int_fh + 1);
				fixedmail = fixedmail.replace('mail:', '');
			} else {
				if(_int_fh == 0) {
					fixedhandler = fixedhandler.replace(';', '');
				}
				if(fixedhandler.indexOf('mobile:') >= 0) {
					fixedmobile = fixedhandler.replace('mobile:', '');
				} else if (fixedhandler.indexOf('mail:') >= 0) {
					fixedmail = fixedhandler.replace('mail:', '');
				}
			}
			$("#fixedmobile").val(fixedmobile);//固定通知电话
			$("#fixedmail").val(fixedmail);//固定通知邮件
			//分析自定义通知组件ID
			var selfDefineComp = $rule.find("selfcomp").text();
			if(selfDefineComp!==null&&selfDefineComp!=undefined&&selfDefineComp!=""){
				var compArr = selfDefineComp.split(",");
				var len = compArr.length;
				for(var i=0;i<len;i++){
					$(":checkbox[name='selfDefineComp'][value='"+compArr[i]+"']").attr("checked","true");//自定义通知组件ID
				}
			}
			$("#actiontype").val($rule.find("acttype").text());//动作类型
			if($rule.find("acttype").text()=="9"){
				$("#actionparamRow").css("display","block");
			}
			$("#actionparam").val($rule.find("actparam").text());//动作类型参数
			$("#calendartype").val($rule.find("calendartype").text());//节假日业务类别
			$("#noticetimes").val($rule.find("ntctimes").text());//通知次数
			$("#noticeinterval").val($rule.find("ntcintval").text());//时间间隔
			$("#noticestarttime").val($rule.find("ntcsttime").text());//通知开始时间
			$("#noticeendtime").val($rule.find("ntcedtime").text());//通知结束时间
			$(":radio[name='overduresend'][value='"+$rule.find("sendaft").text()+"']").attr("checked","true");//是否补发
			$("#paramprotype").val($rule.find("paramprotype").text());//内容参数处理方式
			$("#processparam").val($rule.find("processparam").text());//内容参数处理参数
			$("#topic").val($rule.find("topic").text());//主题
			$("#content").val($rule.find("content").text());//内容
		}
	});
}
/**
 * 修改事件规则
 */
function updateEventRule(scope){
	if(Validator.Validate(document.forms[0],2)==false){
		return;
	}
	var $parentDoc = null;
	if(window.dialogArguments){
		$parentDoc = $(window.dialogArguments.document);
	}
	if($parentDoc==null){
		return;
	}
	var ruleTb = null;
	if("form"==scope){
		ruleTb = $parentDoc.find("#formEventRuleTb");
	}else if("step"==scope){
		ruleTb = $parentDoc.find("#stepEventRuleTb");
	}
	if(ruleTb){
		var ruleRow = ruleTb.find("tr").eq(oprownum);//注意 thead 和 tbody的存在
		if(ruleRow!=null){
			//计算总过逾期多少分钟
			var overday = $('#overday').val();//逾期天
			var overhour = $('#overhour').val();//逾期小时
			var overminute = $('#overminute').val();//逾期分钟
			var overAllMinute = 0;
			if(isNaN(parseInt(overday))==false){
				overAllMinute += parseInt(overday)*24*60;
			}
			if(isNaN(parseInt(overhour))==false){
				overAllMinute += parseInt(overhour)*60;
			}
			if(isNaN(parseInt(overminute))==false){
				overAllMinute += parseInt(overminute);
			}
			if(overAllMinute<=0){
				alert("请填写逾期时间！");
				return;
			}
			if($(":radio[name='ovdureRule']:checked").val()==undefined)
			{
				alert("请选择逾期规则！");
				return;
			}
			if('step'==scope){
				ruleRow.find("td[field='wfstep']").text($("#wfstep").val());//环节
			}
			ruleRow.find("td[field='rulename']").text($("#rulename").val());//规则名称
			ruleRow.find("td[field='ruletype']").text($("#ruletype").val());//规则类别
			ruleRow.find("td[field='ovdureRule']").text($(":radio[name='ovdureRule']:checked + span").text());//规则
			ruleRow.find("td[field='ovdureRule']").attr("value",$(":radio[name='ovdureRule']:checked").val());//规则值
			ruleRow.find("td[field='overAllMinute']").text(overAllMinute);//逾期时间（分）
			ruleRow.find("td[field='condexpress']").text($("#condexpress").val());//条件表达式
			var index = $("#rulePri")[0].selectedIndex;
			ruleRow.find("td[field='rulePri']").text($("#rulePri")[0].options[index].text);//优先级
			ruleRow.find("td[field='rulePri']").attr("value",$("#rulePri").val());//优先级值
			ruleRow.find("td[field='noticedefault']").text($(":radio[name='noticedefault']:checked").val());//是否通知当前处理对象
			ruleRow.find("td[field='defaultchecktype']").text($("#defaultchecktype").val());//当前处理对象查询方式
			ruleRow.find("td[field='defaultcheckparam']").text($("#defaultcheckparam").val());//当前处理对象查询参数
			ruleRow.find("td[field='noticeto_show']").text($("#noticeto_show").val());//通知到
			ruleRow.find("td[field='noticeto']").text($("#noticeto").val());//通知到ID
			ruleRow.find("td[field='duplicateto_show']").text($("#duplicateto_show").val());//抄送给
			ruleRow.find("td[field='duplicateto']").text($("#duplicateto").val());//抄送给ID
			ruleRow.find("td[field='upgradeto_show']").text($("#upgradeto_show").val());//升级到
			ruleRow.find("td[field='upgradeto']").text($("#upgradeto").val());//升级到ID
			ruleRow.find("td[field='fixedmobile']").text($("#fixedmobile").val());//固定通知电话
			ruleRow.find("td[field='fixedmail']").text($("#fixedmail").val());//固定通知邮件
			//查找自定义通知对象组件
			var selfCompId = "";
			$(":checkbox[name='selfDefineComp']:checked").each(function(i){
				if(selfCompId!=""){
					selfCompId += ",";
				}
				selfCompId += this.value;
			});
			ruleRow.find("td[field='selfDefineComp']").text(selfCompId);//自定义通知组件ID
			var actionTypeSel = $("#actiontype").get(0);
			ruleRow.find("td[field='actiontype']").text(actionTypeSel.options[actionTypeSel.selectedIndex].text);//动作类型显示名
			ruleRow.find("td[field='actiontype']").attr("value",actionTypeSel.value);//动作类型值
			ruleRow.find("td[field='actionparam']").text($("#actionparam").val());//动作类型参数
			ruleRow.find("td[field='calendartype']").text($("#calendartype").val());//节假日业务类别
			ruleRow.find("td[field='noticetimes']").text($("#noticetimes").val());//通知次数
			ruleRow.find("td[field='noticeinterval']").text($("#noticeinterval").val());//时间间隔
			ruleRow.find("td[field='noticestarttime']").text($("#noticestarttime").val());//通知开始时间
			ruleRow.find("td[field='noticeendtime']").text($("#noticeendtime").val());//通知结束时间
			ruleRow.find("td[field='overduresend']").text($(":radio[name='overduresend']:checked").val());//是否补发
			ruleRow.find("td[field='paramprotype']").text($("#paramprotype").val());//内容参数处理方式
			ruleRow.find("td[field='processparam']").text($("#processparam").val());//内容参数处理参数
			ruleRow.find("td[field='topic']").text($("#topic").val());//主题
			ruleRow.find("td[field='content']").text($("#content").val());//内容
		}
	}
	window.close();
}

/**
 * 选择通知对象
 */
function selectHanlders(inputid,inputname){
	$("#tempvalue").val("");
	showModalDialog(ctx+'/ultrasla/selectHandlers.jsp?rnd='+(new Date()).getTime()
				,window,'help:no;center:true;scroll:no;status:no;dialogWidth:587px;dialogHeight:525px');
	var pristr = $("#tempvalue").val();
	if(pristr!=""){
		var strArr = pristr.split(";");
		var len = strArr.length;
		var uid = "";//人员ID
		var uname = "";//人员name
		var did = "";//部门ID
		var dname = "";//部门name
		var temp;
		var element;
		var idname;
		for(var i=0;i<len;i++){
			temp = strArr[i];
			element = temp.split(":");
			if(element.length==2){
				if(element[0]=="U"){
					idname = element[1].split(",");
					if(idname.length==2 && idname[0]!="" && idname[1]!=""){
						uid += "," + idname[0];
						uname += "," + idname[1];
					}
				}else if(element[0]=="D"){
					idname = element[1].split(",");
					if(idname.length==2 && idname[0]!="" && idname[1]!=""){
						did += "," + idname[0];
						dname += "," + idname[1];
					}
				}
			}
		}
		if(uid!=""){
			uid = "U:"+uid.substring(1);
//			uname = "人员:"+uname.substring(1);
			uname = uname.substring(1);
		}
		if(did!=""){
			did = "D:"+did.substring(1);
//			dname = "部门:"+dname.substring(1);
			dname = dname.substring(1);
		}
		var resid = uid;
		var resname = uname;
		if(resid==""){
			resid = did;
			resname = dname;
		}else{
			if(did!=""){
				resid += ";"+did;
				resname += ";"+dname;
			}
		}
		$("#"+inputid).val(resid);
		$("#"+inputname).val(resname);
	}
}
function clearHanlders(clearid) {
	$('#'+clearid).val('');
}
//*********************事件规则函数结束***************************//

//*********************时限规则函数开始***************************//
/**
 * 初始化时限规则页面
 */
function initTimeRule(scope){
	$("#allStep").click(function(){
		var preStep = $("#wfstep").val();
		if(preStep!='ALL'){
			$("#wfstep2").val(preStep);
		}
		if(this.checked==true){
			$("#wfstep").val(this.value);
		}else{
			$("#wfstep").val($("#wfstep2").val());
		}
	});
	$("#wfstep").bind("change",function(){
		if(this.value=='ALL'){
			$("#allStep").attr("checked",true);
		}else{
			$("#allStep").attr("checked",false);
		}
	});
	$("#condexpress").bind("keydown",function(event){
//		var srcCode = event.keyCode;
//		if (srcCode == undefined) {
//			srcCode = event.which;
//		}
//		if(srcCode==8					//退格键
//				||srcCode==37			//←键
//				||srcCode==38			//↑键
//				||srcCode==39			//→键
//				||srcCode==40){			//↓键
//			return true;
//		}else{
//			return false;
//		}
		return true;
	});
	$("#addcond").click(function(){
		var attname = $("#attname").val();
		var attvalue = $("#attvalue").val();
		var attrela = $("#attrela").val();
		if(attname==""||attvalue==""){
			return;
		}
		//$("#condexpress").text($("#condexpress").text()+"#"+attname+"#"+attrela+attvalue);
		insertText($("#condexpress")[0],"#"+attname+"#"+attrela+attvalue);
	});
	$("#opbtn :input").click(function(){
		//$("#condexpress").text($("#condexpress").text()+this.value);
		insertText($("#condexpress")[0],this.value);
	});
	if(optype=='add'){
		$("#addRuleBtn").unbind('click');
		$("#addRuleBtn").click(function(){addTimeRule(scope);});
		$("#cancelBtn").click(function(){window.close();});
	} else if(optype=='update'){
		$("#addRuleBtn").unbind('click');
		$("#addRuleBtn").bind('click',function(){updateTimeRule(scope);});//执行修改
		$("#cancelBtn").click(function(){window.close();});
		if(oprownum<1){
			return;
		}
		readTimeRuleRow(scope);
	} else if(optype=='preview'){
		$("#addRuleBtn").css("display","none");
		$("#cancelBtn").val("取消");
		$("#cancelBtn").click(function(){window.close();});
		if(oprownum<1){
			return;
		}
		readTimeRuleRow(scope);
		$("#addRuleBtn").click(function(){window.close();});
	}
}
/**
 * 添加时限规则
 */
function addTimeRule(scope){
	if(Validator.Validate(document.forms[0],2)==false){
		return;
	}
	if('form'!=scope && 'step'!=scope){
		alert('valid scope value!');
		return;
	}
	var parentWindow = window.dialogArguments;
	if(parentWindow==null){
		alert('parent window dosen\'t exist!');
		return;
	}
	var parentDocument = parentWindow.document;
	if(parentDocument==null){
		alert('parent document dosen\'t exist!');
		return;
	}
	var ruleTb = null;
	if('form'==scope){
		ruleTb = parentDocument.getElementById("formTimeRuleTb");//工单事件规则列表
	}else if('step'==scope){
		ruleTb = parentDocument.getElementById("stepTimeRuleTb");//环节事件规则列表
	}
	if(ruleTb){
		//计算序号
		var snum = 1;
		if(ruleTb.rows.length==2&&ruleTb.rows[1].getAttribute('rowtag')=='norule'){
			ruleTb.deleteRow(1);
		}else{
			snum = ruleTb.rows.length;
		}
		//计算受理时限
		var accday = $('#accday').val();//逾期天
		var acchour = $('#acchour').val();//逾期小时
		var accminute = $('#accminute').val();//逾期分钟
		var accAllMinute = 0;
		if(isNaN(parseInt(accday))==false){
			accAllMinute += parseInt(accday)*24*60;
		}
		if(isNaN(parseInt(acchour))==false){
			accAllMinute += parseInt(acchour)*60;
		}
		if(isNaN(parseInt(accminute))==false){
			accAllMinute += parseInt(accminute);
		}
		if(accAllMinute<=0){
			alert("请填写受理时限！");
			return;
		}
		//计算处理时限
		var proday = $('#proday').val();//逾期天
		var prohour = $('#prohour').val();//逾期小时
		var prominute = $('#prominute').val();//逾期分钟
		var proAllMinute = 0;
		if(isNaN(parseInt(proday))==false){
			proAllMinute += parseInt(proday)*24*60;
		}
		if(isNaN(parseInt(prohour))==false){
			proAllMinute += parseInt(prohour)*60;
		}
		if(isNaN(parseInt(prominute))==false){
			proAllMinute += parseInt(prominute);
		}
		if(proAllMinute<=0){
			alert("请填写处理时限！");
			return;
		}
		//拼接规则行HTML
		var newrulerow = "<tr align='left'>\n";
		newrulerow    += 	"<td field='snum'>"+snum+"</td>\n" //序号
		if('step'==scope){
			newrulerow += 	"<td field='wfstep'>"+$("#wfstep").val()+"</td>\n"; //环节
		}
		newrulerow    += 	"<td field='rulename'>"+$("#rulename").val()+"</td>\n"; //规则名称
		newrulerow    += 	"<td field='ruletype' style='display:none'>"+$("#ruletype").val()+"</td>\n"; //规则类别
		newrulerow    += 	"<td field='condexpress' style='display:none'>"+$("#condexpress").val()+"</td>\n"; //条件表达式
		var index = $("#rulePri")[0].selectedIndex;
		newrulerow    += 	"<td field='rulePri' value='"+$("#rulePri").val()+"' style='display:none'>"+$("#rulePri")[0].options[index].text+"</td>\n"; //优先级
		newrulerow    += 	"<td field='accAllMinute'>"+accAllMinute+"</td>\n"; //受理时限
		newrulerow    += 	"<td field='proAllMinute'>"+proAllMinute+"</td>\n"; //处理时限
		newrulerow    += 	"<td field='op'>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"update\",this,\""+scope+"\")'>修改</a>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"preview\",this,\""+scope+"\")'>预览</a>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"delete\",this,\""+scope+"\")'>删除</a>\n"
					  + 		"<a style='text-decoration:underline' href='javascript:;' onclick='opTimeRuleRow(\"copy\",this,\""+scope+"\")'>复制</a>\n"
					  + 	"</td>\n"; //操作
		newrulerow    += "</tr>\n";
		//追加规则到表格
		$(ruleTb).append(newrulerow);
	}
	window.close();
}
/**
 * 操作时限规则行
 */
function opTimeRuleRow(op,source,scope){
	var rownum = $(source).parent().parent().get(0).rowIndex;
	var ruleTb = null;
	if("form"==scope){
		ruleTb = $("#formTimeRuleTb");//工单时限规则列表
	}else if("step"==scope){
		ruleTb = $("#stepTimeRuleTb");//环节时限规则列表
	}
	if(ruleTb==null){
		return;
	}
	if('update'==op){
		showModalDialog(ctx+'/ultrasla/addDuetimeRule.jsp?scope='+scope+'&op=update&rownum='+rownum+'&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
	}else if('preview'==op){
		showModalDialog(ctx+'/ultrasla/addDuetimeRule.jsp?scope='+scope+'&op=preview&rownum='+rownum+'&rnd='+(new Date()).getTime(),window,'help:no;center:true;scroll:no;status:no;dialogWidth:750px;dialogHeight:500px');
	}else if('delete'==op){
		if(window.confirm('确定删除该规则吗？')==false){
			return;
		}
		var delrow = ruleTb.find("tr").eq(rownum);
		var ruleid = delrow.attr("ruleid");
		if(ruleid!=undefined){
			var delid = $("#deledTimeRuleID").val();
			if(delid==""||delid==undefined){
				delid = ruleid;
			}else{
				delid += "," + ruleid;
			}
			$("#deledTimeRuleID").val(delid);
		}
		delrow.remove();
		if(ruleTb.get(0).rows.length == 1){
			//添加无规则行
			var spanval = 0;
			if("form"==scope){
				spanval = formtimerulecols;
			}else if("step"==scope){
				spanval = steptimerulecols;
			}
			ruleTb.append("<tr rowtag='norule'>\n<td colspan='"+spanval+"' align='center'>无规则！</td>\n</tr>");
		}else if(ruleTb.get(0).rows.length>1 && rownum<ruleTb.get(0).rows.length){
			//更新序号
			ruleTb.find("tr:gt(0)").each(function(index,curtd){
				$(curtd).find("> td:first").text(index+1);
			});
		}
	}else if("copy"==op){
		var ruleRow = ruleTb.find("tr").eq(rownum);
		var newrulerow = ruleRow.clone(true);
		newrulerow.attr("ruleid","");
		newrulerow.find("td").css("color","blue");
		ruleRow.after(newrulerow);
		//更新序号
		ruleTb.find("tr:gt(0)").each(function(index,curtd){
			$(curtd).find("> td:first").text(index+1);
		});
	}
}

/**
 * 读取时限规则
 */
function readTimeRuleRow(scope){
	var $parentDoc = null;
	if(window.dialogArguments){
		$parentDoc = $(window.dialogArguments.document);
	}
	if($parentDoc==null){
		return;
	}
	var ruleTb = null;
	if("form"==scope){
		ruleTb = $parentDoc.find("#formTimeRuleTb");
	}else if("step"==scope){
		ruleTb = $parentDoc.find("#stepTimeRuleTb");
	}
	if(ruleTb){
		var ruleRow = ruleTb.find("tr").eq(oprownum);//注意 thead 和 tbody的存在
		if(ruleRow!=null){
			//计算受理时限天/小时/分钟
			var accAllMinute = parseInt(ruleRow.find("td[field='accAllMinute']").text());
			var accday = parseInt(accAllMinute/(24*60));
			var acchour = parseInt((accAllMinute-accday*24*60)/60);
			var accminute = accAllMinute-accday*24*60-acchour*60;
			//计算处理时限天/小时/分钟
			var proAllMinute = parseInt(ruleRow.find("td[field='proAllMinute']").text());
			var proday = parseInt(proAllMinute/(24*60));
			var prohour = parseInt((proAllMinute-proday*24*60)/60);
			var prominute = proAllMinute-proday*24*60-prohour*60;
			if('step'==scope){
				var stepVal = ruleRow.find("td[field='wfstep']").text();
				$("#wfstep").val(stepVal);//环节
				if(stepVal=="ALL"){
					$("#allStep").attr("checked",true);
				}
			}
			$("#rulename").val(ruleRow.find("td[field='rulename']").text());//规则名称
			$("#ruletype").val(ruleRow.find("td[field='ruletype']").text());//规则类别
			$("#condexpress").val(ruleRow.find("td[field='condexpress']").text());//条件表达式
			$("#rulePri").val(ruleRow.find("td[field='rulePri']").attr("value"));//优先级
			$("#accday").val(accday);//受理天
			$("#acchour").val(acchour);//受理小时
			$("#accminute").val(accminute);//受理分钟
			$("#proday").val(proday);//处理天
			$("#prohour").val(prohour);//处理小时
			$("#prominute").val(prominute);//处理分钟
		}
	}
}

/**
 * 修改时限规则
 */
function updateTimeRule(scope){
	if(Validator.Validate(document.forms[0],2)==false){
		return;
	}
	var $parentDoc = null;
	if(window.dialogArguments){
		$parentDoc = $(window.dialogArguments.document);
	}
	if($parentDoc==null){
		return;
	}
	var ruleTb = null;
	if("form"==scope){
		ruleTb = $parentDoc.find("#formTimeRuleTb");
	}else if("step"==scope){
		ruleTb = $parentDoc.find("#stepTimeRuleTb");
	}
	if(ruleTb){
		var ruleRow = ruleTb.find("tr").eq(oprownum);//注意 thead 和 tbody的存在
		if(ruleRow!=null){
			//计算受理时限（分钟）
			var accday = $('#accday').val();//逾期天
			var acchour = $('#acchour').val();//逾期小时
			var accminute = $('#accminute').val();//逾期分钟
			var accAllMinute = 0;
			if(isNaN(parseInt(accday))==false){
				accAllMinute += parseInt(accday)*24*60;
			}
			if(isNaN(parseInt(acchour))==false){
				accAllMinute += parseInt(acchour)*60;
			}
			if(isNaN(parseInt(accminute))==false){
				accAllMinute += parseInt(accminute);
			}
			if(accAllMinute<=0){
				alert("请填写受理时限！");
				return;
			}
			//计算处理时限（分钟）
			var proday = $('#proday').val();//逾期天
			var prohour = $('#prohour').val();//逾期小时
			var prominute = $('#prominute').val();//逾期分钟
			var proAllMinute = 0;
			if(isNaN(parseInt(proday))==false){
				proAllMinute += parseInt(proday)*24*60;
			}
			if(isNaN(parseInt(prohour))==false){
				proAllMinute += parseInt(prohour)*60;
			}
			if(isNaN(parseInt(prominute))==false){
				proAllMinute += parseInt(prominute);
			}
			if(accAllMinute<=0){
				alert("请填写处理时限！");
				return;
			}
			if('step'==scope){
				ruleRow.find("td[field='wfstep']").text($("#wfstep").val());//环节
			}
			ruleRow.find("td[field='rulename']").text($("#rulename").val());//规则名称
			ruleRow.find("td[field='ruletype']").text($("#ruletype").val());//规则类别
			ruleRow.find("td[field='condexpress']").text($("#condexpress").val());//条件表达式
			var index = $("#rulePri")[0].selectedIndex;
			ruleRow.find("td[field='rulePri']").text($("#rulePri")[0].options[index].text);//优先级
			ruleRow.find("td[field='rulePri']").attr("value",$("#rulePri").val());//优先级值
			ruleRow.find("td[field='accAllMinute']").text(accAllMinute);//逾期时间（分）
			ruleRow.find("td[field='proAllMinute']").text(proAllMinute);//逾期时间（分）
		}
	}
	window.close();
}
//*********************事件规则函数结束***************************//

//*********************数据保存开始***************************//
/**
 * 保存SLA定义
 */
function saveSLA(){
	if(Validator.Validate(document.forms[0],2)==false){
		return;
	}
	$("#com_btn_add").attr("disabled","disabled");
	//保存工单事件规则
	saveTimeRule("form");
//	alert("工单时限规则："+$("#formTimeRuleID").val());
	//保存环节事件规则
	saveTimeRule("step");
//	alert("环节时限规则："+$("#stepTimeRuleID").val());
	//保存工单时限规则
	saveEventRule("form");
//	alert("工单事件规则："+$("#formEventRuleID").val());
	//保存环节时限规则
	saveEventRule("step");
//	alert("环节事件规则："+$("#stepEventRuleID").val());
	//保存SLA定义基本信息
	saveSlaHead();
}
/**
 * 保存事件规则
 */
function saveEventRule(scope){
	var ruleTb;
	if("form"==scope){
		ruleTb = $("#formEventRuleTb");
		$("#formEventRuleID").val("");
	}else if("step"==scope){
		ruleTb = $("#stepEventRuleTb");
		$("#stepEventRuleID").val("");
	}
	if(ruleTb){
		if(ruleTb.get(0).rows.length==2 && ruleTb.get(0).rows[1].getAttribute('rowtag')=='norule'){
			return;
		}
		var param;
		ruleTb.find("tr:gt(0)").each(function(index,row){
			var ruleid = $(row).attr("ruleid");
			if(ruleid==undefined){
				ruleid = "";
			}
			var step = "";//环节号
			if("step"==scope){
				step = $(row).find("td[field='wfstep']").text();
			}
			var trigRuleId = $(row).find("td[field='ovdureRule']").attr("value");
			param  = "<rule>\n";
			param += 	"<id>"+ruleid+"</id>\n";//规则ID
			param += 	"<name>"+$(row).find("td[field='rulename']").text()+"</name>\n";//规则名称
			param += 	"<type>"+$(row).find("td[field='ruletype']").text()+"</type>\n";//规则类别
			param += 	"<ovRule>"+$(row).find("td[field='ovdureRule']").attr("value")+"</ovRule>\n";//规则，如未处理已超时
			param += 	"<ovtime>"+$(row).find("td[field='overAllMinute']").text()+"</ovtime>\n";//逾期时间
			param += 	"<express>"+$(row).find("td[field='condexpress']").text()+"</express>\n";//规则表达式
			param += 	"<rulePri>"+$(row).find("td[field='rulePri']").attr("value")+"</rulePri>\n";//优先级
			param += 	"<ntcdft>"+$(row).find("td[field='noticedefault']").text()+"</ntcdft>\n";//是否通知当前处理人
			param += 	"<dftchktyp>"+$(row).find("td[field='defaultchecktype']").text()+"</dftchktyp>\n";//当前处理人查询方式
			param += 	"<dftchkparam>"+$(row).find("td[field='defaultcheckparam']").text()+"</dftchkparam>\n";//当前处理人查询参数
			param += 	"<ntc_show>"+$(row).find("td[field='noticeto_show']").text()+"</ntc_show>\n";//通知对象名
			param += 	"<ntcto>"+$(row).find("td[field='noticeto']").text()+"</ntcto>\n";//通知对象ID
			param += 	"<dplct_show>"+$(row).find("td[field='duplicateto_show']").text()+"</dplct_show>\n";//抄送对象名
			param += 	"<dplctto>"+$(row).find("td[field='duplicateto']").text()+"</dplctto>\n";//抄送对象ID
			param += 	"<upgrd_show>"+$(row).find("td[field='upgradeto_show']").text()+"</upgrd_show>\n";//升级对象名
			param += 	"<upgrdto>"+$(row).find("td[field='upgradeto']").text()+"</upgrdto>\n";//升级对象ID
			var fixedhandler = '';
			var fixedmobile = $(row).find("td[field='fixedmobile']").text();
			var fixedmail = $(row).find("td[field='fixedmail']").text();
			fixedmobile = fixedmobile=='' ? '' : 'mobile:' + fixedmobile;
			fixedmail = fixedmail=='' ? '' : 'mail:' + fixedmail;
			if(fixedmobile != '') fixedhandler = fixedmobile;
			if(fixedmail != '') fixedhandler += ';' + fixedmail;
			if(fixedhandler.indexOf(';')==0) fixedhandler = fixedhandler.substring(1);
			param += 	"<fixedhandler>"+fixedhandler+"</fixedhandler>\n";//固定通知电话
			param += 	"<selfcomp>"+$(row).find("td[field='selfDefineComp']").text()+"</selfcomp>\n";//自定义通知组件
			param += 	"<acttype>"+$(row).find("td[field='actiontype']").attr("value")+"</acttype>\n";//动作类型，如邮件通知
			param += 	"<actparam>"+$(row).find("td[field='actionparam']").text()+"</actparam>\n";//动作参数
			param += 	"<calendartype>"+$(row).find("td[field='calendartype']").text()+"</calendartype>\n";//节假日业务类别
			param += 	"<ntctimes>"+$(row).find("td[field='noticetimes']").text()+"</ntctimes>\n";//通知次数
			param += 	"<ntcintval>"+$(row).find("td[field='noticeinterval']").text()+"</ntcintval>\n";//间隔时间
			param += 	"<ntcsttime>"+$(row).find("td[field='noticestarttime']").text()+"</ntcsttime>\n";//通知开始时间
			param += 	"<ntcedtime>"+$(row).find("td[field='noticeendtime']").text()+"</ntcedtime>\n";//通知结束时间
			param += 	"<sendaft>"+$(row).find("td[field='overduresend']").text()+"</sendaft>\n";//是否补发
			param += 	"<paramprotype>"+$(row).find("td[field='paramprotype']").text()+"</paramprotype>\n";//内容参数查询方式
			param += 	"<processparam>"+$(row).find("td[field='processparam']").text()+"</processparam>\n";//内容参数查询参数
			param += 	"<topic>"+$(row).find("td[field='topic']").text()+"</topic>\n";//主题
			param += 	"<content>"+$(row).find("td[field='content']").text()+"</content>\n";//内容
			param += 	"<status>"+$("#slastatus").val()+"</status>\n";//状态
			param += 	"<vldsttime>"+$("#slavalidstarttime").val()+"</vldsttime>\n";//有效开始日期
			param += 	"<vldedtime>"+$("#slavalidendtime").val()+"</vldedtime>\n";//有效结束日期
			param += "</rule>\n";
			$.ajax({
				type:"POST",
				async:false,
				url:ctx+"/slaDefine/saveEventRule.action?rnd="+(new Date()).getTime(),
				data:{ruleXml:param,scope:scope},
				dataType:"text",
				success:function(id){
					if(id==undefined||id==""||id.length>32){
						return;
					}
					if("form"==scope){
						var temp = $("#formEventRuleID").val();
						if(temp==""){
							temp = trigRuleId + ":" + id;//触发规则ID:事件规则ID
						}else{
							temp += "," + trigRuleId + ":" + id;
						}
						$("#formEventRuleID").val(temp);
//						alert($("#formEventRuleID").val());
					}else if("step"==scope){
						var temp = $("#stepEventRuleID").val();
						if(temp==""){
							temp = step + "@" + trigRuleId + ":" + id;//环节号@触发规则ID:事件规则ID
						}else{
							temp += "," + step + "@" + trigRuleId + ":" + id;
						}
						$("#stepEventRuleID").val(temp);
//						alert( $("#stepEventRuleID").val());
					}
				}
			});
		});
	}
}

/**
 * 保存时限规则
 */
function saveTimeRule(scope){
	var ruleTb;
	if("form"==scope){
		ruleTb = $("#formTimeRuleTb");
		$("#formTimeRuleID").val("");
	}else if("step"==scope){
		ruleTb = $("#stepTimeRuleTb");
		$("#stepTimeRuleID").val("");
	}
	if(ruleTb){
		if(ruleTb.get(0).rows.length==2 && ruleTb.get(0).rows[1].getAttribute('rowtag')=='norule'){
			return;
		}
		var param;
		ruleTb.find("tr:gt(0)").each(function(index,row){
			var ruleid = $(row).attr("ruleid");
			if(ruleid==undefined){
				ruleid = "";
			}
			var step = "";//环节号
			if("step"==scope){
				step = $(row).find("td[field='wfstep']").text();
			}
			param  = "<rule>\n";
			param += 	"<id>"+ruleid+"</id>\n";//规则ID
			param += 	"<name>"+$(row).find("td[field='rulename']").text()+"</name>\n";//规则名称
			param += 	"<type>"+$(row).find("td[field='ruletype']").text()+"</type>\n";//规则类别
			param += 	"<express>"+$(row).find("td[field='condexpress']").text()+"</express>\n";//规则表达式
			param += 	"<rulePri>"+$(row).find("td[field='rulePri']").attr("value")+"</rulePri>\n";//优先级
			param += 	"<acctime>"+$(row).find("td[field='accAllMinute']").text()+"</acctime>\n";//受理时限
			param += 	"<protime>"+$(row).find("td[field='proAllMinute']").text()+"</protime>\n";//处理时限
			param += 	"<status>"+$("#slastatus").val()+"</status>\n";//状态
			param += 	"<vldsttime>"+$("#slavalidstarttime").val()+"</vldsttime>\n";//有效开始时间
			param += 	"<vldedtime>"+$("#slavalidendtime").val()+"</vldedtime>\n";//有效结束时间
			param += "</rule>\n";
			$.ajax({
				type:"POST",
				async:false,
				url:ctx+"/slaDefine/saveTimeRule.action?rnd="+(new Date()).getTime(),
				data:{ruleXml:param},
				dataType:"text",
				success:function(id){
					if(id==undefined||id==""||id.length>32){
						return;
					}
					if("form"==scope){
						var temp = $("#formTimeRuleID").val();
						if(temp==""){
							temp = id;
						}else{
							temp += "," + id;
						}
						$("#formTimeRuleID").val(temp);
//						alert($("#formTimeRuleID").val());
					}else if("step"==scope){
						var temp = $("#stepTimeRuleID").val();
						if(temp==""){
							temp = step + ":" + id;
						}else{
							temp += "," + step + ":" + id;
						}
						$("#stepTimeRuleID").val(temp);
//						alert($("#stepTimeRuleID").val());
					}
				}
			});
		});
	}
}

/**
 * 保存SLA定义基本信息
 */
function saveSlaHead(){
	var formTimeRuleID = $("#formTimeRuleID").val();//保存的工单时限规则ID
	var stepTimeRuleID = $("#stepTimeRuleID").val();//保存的工单_环节时限规则ID
	var formEventRuleID = $("#formEventRuleID").val();//保存的工单事件规则ID
	var stepEventRuleID = $("#stepEventRuleID").val();//保存的工单_环节事件规则ID
	if(formTimeRuleID=="" && stepTimeRuleID==""
		&& formEventRuleID=="" && stepEventRuleID==""){
		if(window.confirm("您的协议中没有触发规则，确定保存吗？")==false){
			return;
		}
	}
	var param = "<head>\n";
	param    += 	"<id>"+$("#slaId").val()+"</id>\n";
	param    += 	"<name>"+$("#slaName").val()+"</name>\n";
	param    += 	"<vldsttime>"+$("#slavalidstarttime").val()+"</vldsttime>\n";
	param    += 	"<vldedtime>"+$("#slavalidendtime").val()+"</vldedtime>\n";
	param    += 	"<status>"+$("#slastatus").val()+"</status>\n";
	param    += 	"<remark>"+$("#slaRemark").val()+"</remark>\n";
	param    += 	"<ftrid>"+formTimeRuleID+"</ftrid>\n";
	param    += 	"<strid>"+stepTimeRuleID+"</strid>\n";
	param    += 	"<ferid>"+formEventRuleID+"</ferid>\n";
	param    += 	"<serid>"+stepEventRuleID+"</serid>\n";
	param    += "</head>";
	$.post(ctx+"/slaDefine/saveSlaHead.action?rnd="+(new Date()).getTime()
			,{slaXml:param,baseSchema:baseSchema,deledTimeR:$("#deledTimeRuleID").val(),deledEventR:$("#deledEventRuleID").val()}
			,function(result){
		if("1"==result){
			alert("保存成功！");
			window.location.reload();
		}else{
			alert("保存失败！");
			$("#com_btn_add").removeAttr("disabled");
		}
	});
}
//*********************数据保存结束***************************//