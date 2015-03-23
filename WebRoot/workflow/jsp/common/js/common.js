//流程引擎存储的流程类型是1和0，流程图用到的是tpl和free，为了保持兼容性在此转换
function transFlowType(flowType) {
	if(flowType == '1') {
		return 'tpl';
	} else {
		return 'free';
	}
}

//加载环节页面
function showStep(actionType, actionName, page, needActor, radio) {
	createLoadDiv('appearDiv');
	hiddenDiv("operDiv");
	showDiv("stepDiv");
	if(cssdropdown.dropmenuobj) {
		cssdropdown.dropmenuobj.style.visibility='hidden';//隐藏下拉菜单
	}
	var baseId=getJQueryId("sheetFeildsMap.baseId").val();
	var baseSchema=getJQueryId("baseSchema").val();
	var dimAndCon=getJQueryId("dimAndCon").val();
	var entryId=getJQueryId("task.entryId").val();
	var stepCode=getJQueryId("task.stepCode").val();
//	var parentEntryId=getJQueryId("task.parentEntryId").val();
//	var taskId=getJQueryId("task.id").val();
//	var flagActive=getJQueryId("task.flagActive").val();
	var dimUrl = "";
	if(dimAndCon) {//传入角色细分用到的维度值
		var dims = dimAndCon.split(";");
		if(dims) {
			for(var i=0;i<dims.length;i++) {
				var dim = dims[i];
				if(dim && dim != "") {
					var id = "sheetFeildsMap." + dim;
					var valObj = getJQueryId(id);
					var val = "";
					if(valObj) {
						val = valObj.val();
					} else {
						var id2 = "sheetFeildsMap_" + dim;
						var valObj2 = getJQueryId(id2);
						if(valObj2) {
							val = valObj2.val();
						}
					}
					dimUrl += "&" + id + "=" + val;
				}
			}
		}
	}
	var taskUrl = "&task.entryId="+entryId+"&task.stepCode="+stepCode;
	setAction(actionType, actionName, page, needActor, radio);
	var url = $ctx + "/process/showStep.action?baseId="+baseId+"&baseSchema="+baseSchema+"&wfAction.actionType="+actionType+"&wfAction.actionName="+actionName+"&wfAction.page="+page+"&wfAction.needActor="+needActor+"&wfAction.radio="+radio+"&"+ dimUrl +taskUrl+"&a="+Math.random();
	getJQueryId("stepDiv").load(url, showResponse);
}

//给提交动作信息赋值
function setAction(actionType, actionName, page, needActor, radio) {
	getJQueryId("wfAction.actionType").val(actionType);
	getJQueryId("wfAction.actionName").val(actionName);
	getJQueryId("wfAction.page").val(page);
	getJQueryId("wfAction.needActor").val(needActor);
	getJQueryId("wfAction.radio").val(radio);
}

//显示按钮对应的环节页面
function showResponse(){
	removeDiv("appearDiv");
	scrollBottom();
}

//从操作页面返回到工单页面 
function sheetBack(){
	hiddenDiv('stepDiv');
	destoryAttach();
	emptyDiv("stepDiv");
	showDiv('operDiv');
	scrollBottom();
}

//清除附件实例
function destoryAttach() {
	var atta = getJQueryId("stepAttach");
	if(atta) {
		try{
			eval(atta.val() +".destory()");			
		} catch (ex) {
		}
	}
}

//打开派发树
function openActorTree(radio, actionType, actorActionType) {
	if(actorActionType && actorActionType != '') {
		actionType = actorActionType;
	}
	var tplId = getJQueryId("sheetFeildsMap.baseTplId").val();
	window.open($ctx + "/workflow/actor/dealActorJsp.jsp?tplId="+tplId+"&radio="+radio+"&actionType="+actionType);
}

//设置追回、催办、阶段建议的处理人
function setActor(actionType) {
	var tmpId = "";
	var cks = document.getElementsByName("ck_dpModels");
	if(cks != null) {
		for(var i=0;i<cks.length;i++) {
			if(cks[i].checked) {
				tmpId += cks[i].value + ";";
			}
		}
	}
	if(tmpId != "") {
		var actorId = "#:#:"+actionType+"#:2#:0#:0#:0#:"+tmpId+"#:#:#:#;";
		getJQueryId("actorStr").val(actorId);
	}
}

//记录允许的动作,用于设置派发树的按钮逻辑
var assign = 0;
var reassign = 0;
var audit = 0;
var reaudit = 0;
var orgaudit = 0;
var makecopy = 0;
function recActionType(actionType) {
	if('ASSIGN' == actionType) {
		assign = 1;
	}
	if('REASSIGN' == actionType) {
		reassign = 1;
	}
	if('AUDIT' == actionType) {
		audit = 1;
	}
	if('REAUDIT' == actionType) {
		reaudit = 1;
	}
	if('ORGANIZEAUDIT' == actionType) {
		orgaudit = 1;
	}
	if('MAKECOPY' == actionType) {
		makecopy = 1;
	}
}

//创建表单提交之后的loading div
function createLoadDiv(id){
	var o = createOverlay(id);
	var img = $j("<img id='loadimg' src='"+$ctx+"/workflow/jsp/common/images/loading.gif'></img>");
	o.append(img);
	img.css('position','absolute');
	img.css('top','47%');
	img.css('left','47%');
}
function createOverlay(id){
	var o = createDiv(id,getJQueryTag("body"));

    o.css('position','absolute');
	o.css('top','0');
	o.css('left','0');

	//o.css('width',document.body.scrollWidth>document.body.clientWidth?document.body.scrollWidth:document.body.clientWidth);
	//o.css('height',document.body.scrollHeight>document.body.clientHeight?document.body.scrollHeight:document.body.clientHeight);
	o.css('width',window.screen.width);
	o.css('height',document.body.scrollHeight>document.documentElement.clientHeight?document.body.scrollHeight:document.documentElement.clientHeight);
	//o.css('height',window.screen.height);
	//o.css('width',document.documentElement.clientWidth);
	//o.css('height',document.document.documentElement.clientHeight);
	o.css('background-color','#D4DFE5');
	o.animate({opacity:0.8},400);
	return o;
}
function resizeDiv() {
	var loadDiv = getJQueryId("appearDiv");
	if(loadDiv) {
		//loadDiv.css('width',document.body.scrollWidth>document.body.clientWidth?document.body.scrollWidth:document.body.clientWidth);
		//loadDiv.css('height',document.body.scrollHeight>document.body.clientHeight?document.body.scrollHeight:document.body.clientHeight);
		loadDiv.css('width',window.screen.width);
		loadDiv.css('height',document.body.scrollHeight>document.documentElement.clientHeight?document.body.scrollHeight:document.documentElement.clientHeight);
		//loadDiv.css('height',window.screen.height);
		//loadDiv.css('width',document.documentElement.clientWidth);
		//loadDiv.css('height',document.documentElement.clientHeight);
	}
}

//表单验证
function checkForm() {
	var isValid = Validator.Validate(document.getElementById("fieldForm"));
	if(isValid) {
		createLoadDiv('appearDiv');
	} else {
		return false;
	}
}

//判断抄送、审批处理人是否显示
function showCopyAudit() {
		if(audit == 1) {
			getJQueryId("auditTr").attr('style','display:');
		}
		if(makecopy == 1) {
			getJQueryId("makeCopyTr").attr('style','display:');
		}
	}
	
//打开流程图
function openFlowMap(baseId, baseSchema, baseWorkflowFlag, tplId, entryId) {
	window.open($ctx +'/workflow/design/v4/WFDesigner.jsp?mode=map&baseid='+baseId+'&baseschema='+baseSchema+'&type='+transFlowType(baseWorkflowFlag)+'&entryid='+entryId+'&tplid='+tplId+'&modeltype=EOMS');
}

//工单浏览
function sheetView(baseId, baseSchema,  tplId) {
	window.open($ctx + '/workflow/sheet/query/BaseInfoView.jsp?baseschema='+baseSchema+'&baseid='+baseId+'&flagnewwindow=1&tplid='+tplId);
}
