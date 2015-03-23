var actorMap;//处理人集合(key:id,value:JsActor)
var isselect;//单选或多选,1为多选，0为单选

//系统中是否开启协办功能
var isAssist = true;
//系统中是否开启派发与转派同时选择功能
var isAssignAndTransfer = true;

var depOrRole = '1';//判断当前是组织机构(1)选择还是角色选择(2)

//字符串规则：派发类型(U:人,G:组)#:派发角色ID(人:登录名,组:组ID)#:处理类型(固定流程：NEXT,自由流程：)#:组处理模式(1:共享,2:独占,3:管理者)#:受理时限#:派发时限#:处理时限#:环节号#:子流程定义名称#:派发说明#;
var actor_code = '';//字符串
var payout_code = '';//派发人字符串
var payout_name = '';//派发人_name
var transfer_code = '';//移交人_code
var transfer_name = '';//移交人_name
var audit_code = '';//审批人_code
var audit_name = '';//审批人_name
var copy_code = '';//抄送人_code
var copy_name = '';//抄送人_name
var assist_code = '';//协办人_code
var assist_name = '';//协办人_name
var childschema = '';//子流程定义名称

var model = '2';//组处理模式(1:共享,2:独占,3:管理者)
var action_sign = '';//动作
var actionName = '';//动作
var step_no = '';//当前环节号
var next_step = '';//下一环节
var next_radio = '';//单选或多选
var wfVersion = '';//版本号

var isApprove = '';//是否审批环节
var baseSchema = '';//工单类别
var companyId = '';//公司id
var phaseno = '';//环节号

var assignAndCopy = '';//派发与抄送同时存在标识 1:是 0:否
var assignAndAssign='';
var assignAndReassign='';
var assignAndAudit='';
var assignAndAssist='';
var baschema='';//工单类别

var url = null;
var treeField = "";

var payout_obj = null;
var copy_obj = null;
var audit_obj = null;
var transfer_obj = null;
var assist_obj = null;

var isSelectType = "2";
//初始化方法
function init(){
	url = new Url("");
	wfVersion = dialogArguments.ClientContext.defCode;//版本号
	childschema = "";//子流程定义名称
	// 环节号
	step_no = url.getvalue("step");
	action_sign = url.getvalue("act");
	actionName = url.getvalue("actN");
	isSelectType = url.getvalue("isSelectType");
	if(action_sign == null || action_sign == "") action_sign = dialogArguments.ClientContext.actionType;//动作
	//2010-12-22 fanying 当派发同时抄送
	assignAndCopy = action_sign == "MACKCOPY" ? 1 : 0;//派发与抄送同时存在标识
	//alert(assignAndCopy);
	//2011-1-13 fanying 动作人员分配整合 是否允许派发：650042018; 是否允许移交：650042019; 是否允许提交审批：650042020; 是否允许协办：650042021;
	assignAndReassign = action_sign == "REASSIGN" ? 1 : 0;//是否允许移交
	assignAndAudit = action_sign == "AUDIT" ? 1 : 0;//是否允许提交审批
	assignAndAssist = action_sign == "ASSIST" ? 1 : 0;//是否允许协办
	assignAndAssist = assignAndAssist == '' ? '0' : assignAndAssist;
	assignAndAssign = (assignAndReassign+assignAndAudit+assignAndAssist+assignAndCopy) == 0 ? 1 : 0;//是否允许派发
	baschema = dialogArguments.ClientContext.baseSchema;
	
	actorMap = new Map();
	initdata();	
	dealActorTableView();
	auditActorTableView();
	reassignActorTable();
	copyActorTableView();
	assistActorTable();
}

//tab页选择事件
function PageMenuOnclick(id1,id2,type){
	depOrRole = type;
	PageMenuActive(id1,id2);
}

//数据初始化
function initdata(){
	treeField = url.getvalue("field");
	var tmpNS = url.getvalue("dp");
	if(tmpNS == null) tmpNS = treeField;
	next_step = tmpNS;//下一环节
	next_radio = url.getvalue("radio");//单选或多选
	
	baseSchema = dialogArguments.ClientContext.baseSchema;//工单类别
	
	var tmp_BaseAcceptTime = '';
	var tmp_BaseDealTime = '';
	if(dialogArguments.F("AcceptOuttime") != null)
	{
		tmp_BaseAcceptTime = dialogArguments.F("AcceptOuttime").G();//工单受理时限
	}
	if(dialogArguments.F("DealOuttime") != null)
	{
		tmp_BaseDealTime = dialogArguments.F("DealOuttime").G();//工单处理时限
	}
	
	if(tmp_BaseAcceptTime != '')
	{
		document.getElementById('limittime').value = tmp_BaseAcceptTime;
	}
	if(tmp_BaseDealTime != '')
	{
		document.getElementById('dealtime').value = tmp_BaseDealTime;
	}
	//var baseSchema = '';
	var preventTree = 'Position'; // 不显示职位选项卡
	var isRadio = '1';
	if(next_radio == '1')
		isRadio = '0';
	var commonTreeUrl = $ctx
			+ '/common/tools/orgtree/commonTreeFrame.jsp?useMode=frame&idtype=1&isRadio=' + isRadio 
			+ '&preventTree=' + preventTree + '&wfVersion=' + wfVersion + '&isApprove=' + isApprove 
			+ '&typeMark=' + baseSchema + '&companyId=' + companyId + '&phaseno=' + phaseno 
			+ '&step=' + step_no + '&act=' + actionName + '&field=' + treeField+'&isSelectType='+isSelectType;
	document.getElementById('commonTree').src = commonTreeUrl;
	isselect = next_radio;
	payout_code = dialogArguments.AssignTreeField.getAssignTagString(treeField);//派发人_code
	payout_obj = dialogArguments.$("#" + treeField);
	if(payout_obj.length > 0)
	{
		payout_name = payout_obj[0].value;//派发人_name
		payout_obj = payout_obj[0];
	} else {
		payout_obj = null;
	}
	
	
	var name = '';
	if(payout_name != ''){
		name += payout_name;
	}
	if(payout_code != ''){
		actor_code += payout_code;
	}
	
	if(copy_name != ''){
		name += ',' +  copy_name;
	}
	if(copy_code != ''){
		actor_code += copy_code;
	}
	
	if(audit_name != ''){
		name += ',' +  audit_name;
	}
	if(audit_code != ''){
		actor_code += audit_code;
	}
	
	if(transfer_name != ''){
		name += ',' + transfer_name;
	}
	
	if(transfer_code != ''){
		actor_code += transfer_code;
	}
	
	if(assist_name != ''){
		name += ',' + assist_name;
	}
	
	if(assist_code != ''){
		actor_code += assist_code;
	}
	
	if(name != '' && name.substring(0,1) == ','){
				name = name.substring(1);
		}
		
	titleInit();
	
	splitData(actor_code,name);
	
	
}



//初始化数据时，将字符串分解放到map中
function splitData(code,name){
	if(code == ''){
		return;
	}
	//U#:wangwumei#:ASSIGN#:2#:#:#:#:#:#:#;
	//U#:wangxiaodong#:MAKECOPY#:2#:#:#:#:#:#:#;

	var codeObjs = code.split('#;');
	var names = name.split(',');

	for(var i=0; i<codeObjs.length-1; i++){
		var codes = codeObjs[i].split('#:');
		var actor = new JsActor();
		actor.id = codes[1];
		actor.type = codes[0];
		actor.name = names[i];
		actor.dealtype = codes[2];
		actor.model = codes[3];
		actor.limittimes = parseSecondToStr(codes[4]);
		actor.payouttime = parseSecondToStr(codes[5]);
		actor.dealtimes = parseSecondToStr(codes[6]);
		actor.nextstepid = next_step;
		actor.childschema = childschema;
		actor.payoutdesc = codes[10];
		
		actorMap.put(actor.id,actor);
	}
}

function getDealModeStr(actorId, actorType, dealMode) {
	var isDisabled = '';
	if(actorType == 'U') {
		isDisabled = 'disabled';
	}
	var isShare = '';
	var isComplete = '';
	var isManage = '';
	if(dealMode == '1') {
		isShare = 'selected';
	} else if(dealMode == '2') {
		isComplete = 'selected';
	} else if(dealMode == '3') {
		isManage = 'selected';
	}
	var selectStr = '<select id=\''+actorId+'_select\' '+isDisabled+'><option value=\'1\' '+isShare+'>共享</option><option value=\'2\' '+isComplete+'>独占</option><option value=\'3\' '+isManage+'>管理员分派</option></select>';
	return selectStr;
}

/**
 *处理人列表显示(JsTable实现)
 */ 
 function dealActorTableView(){
	var cList = new Array();
	for(var i=0;i<actorMap.keySet().size();i++){
		var key = actorMap.keySet().get(i);
		var actor = actorMap.get(key);
		var actorType = actor.type;
		var dealMode = actor.model;
		if((actor.dealtype != 'MAKECOPY' && actor.dealtype == action_sign && action_sign!='ASSIGN')||(action_sign=='ASSIGN'&&actor.dealtype=='ASSIGN')){
			var data = [
			actor.id
			,'<center><img src="image/del.png" id="' + actor.id + '" align="center" onclick="delactor(this.id)" /></center>'
			,'<p  id="' + actor.id + '" align="center" >' + actor.name + ' </p>'
			,'<input  align="center" type="text" id="' + actor.id + '" class="textInput" onchange="setdesc(this.id,this.value);" value="' + actor.payoutdesc + '"/>'
			,getDealModeStr(key, actorType, dealMode)
			,'<center><img src="image/clock.png" id="' + actor.id + '" align="center" onclick="settime(this.id)" /></center>'
			//,'<input type="checkbox" id="' + actor.id + '" align="center" onclick="transfor(this.id)" value="转派" />'
			];
			cList.push(data);
		}
	}
	
	var jt = new JsTable('dealActorTables', true, 'tableborder', null, null, null, null, null, null,
    [
        new JsCell(false, 'id', '', [], 1, [], ''),
        new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="image/del.png" onclick="delactor(\'{@COL0}\')"/></center>'),
        new JsCell(true, 'name', '<b><center>处理人或组</center></b>', [], 2, [], ''),
        new JsCell(true, 'category', '<b><center>自定义说明</center></b>', [], 3, [], ''),
        new JsCell(true, 'dealMode', '<b><center>处理模式</center></b>', [], 4, [], ''),
        new JsCell(true, 'dict', '<b><center>时限</center></b>', [], 5, [], '<center><img src="image/clock.png" onclick="settime(\'{@COL0}\')"/></center>')
       // new JsCell(true, 'isnull', '<b><center>转派</center></b>', [], 5, [], '')
    ],cList);
   jt.draw(document.getElementById('dealActorTable'));
 }
  function auditActorTableView(){
	var cList = new Array();
	for(var i=0;i<actorMap.keySet().size();i++){
		var key = actorMap.keySet().get(i);
		var actor = actorMap.get(key);
		var actorType = actor.type;
		var dealMode = actor.model;
		if((actor.dealtype != 'MAKECOPY' && actor.dealtype == action_sign && action_sign!='ASSIGN')||(action_sign=='ASSIGN'&&actor.dealtype=='AUDIT')){
			var data = [
			actor.id
			,'<center><img src="image/del.png" id="' + actor.id + '" align="center" onclick="delactor(this.id)" /></center>'
			,'<p  id="' + actor.id + '" align="center" >' + actor.name + ' </p>'
			,'<input  align="center" type="text" id="' + actor.id + '" class="textInput" onchange="setdesc(this.id,this.value);" value="' + actor.payoutdesc + '"/>'
			,getDealModeStr(key, actorType, dealMode)
			,'<center><img src="image/clock.png" id="' + actor.id + '" align="center" onclick="settime(this.id)" /></center>'
			//,'<input type="checkbox" id="' + actor.id + '" align="center" onclick="transfor(this.id)" value="转派" />'
			];
			cList.push(data);
		}
	}
	
	var jt = new JsTable('auditActorTables', true, 'tableborder', null, null, null, null, null, null,
    [
        new JsCell(false, 'id', '', [], 1, [], ''),
        new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="image/del.png" onclick="delactor(\'{@COL0}\')"/></center>'),
        new JsCell(true, 'name', '<b><center>处理人或组</center></b>', [], 2, [], ''),
        new JsCell(true, 'category', '<b><center>自定义说明</center></b>', [], 3, [], ''),
        new JsCell(true, 'dealMode', '<b><center>处理模式</center></b>', [], 4, [], ''),
        new JsCell(true, 'dict', '<b><center>时限</center></b>', [], 5, [], '<center><img src="image/clock.png" onclick="settime(\'{@COL0}\')"/></center>')
       // new JsCell(true, 'isnull', '<b><center>转派</center></b>', [], 5, [], '')
    ],cList);
   jt.draw(document.getElementById('auditActorTable'));
 }
  function reassignActorTable(){
	var cList = new Array();
	for(var i=0;i<actorMap.keySet().size();i++){
		var key = actorMap.keySet().get(i);
		var actor = actorMap.get(key);
		var actorType = actor.type;
		var dealMode = actor.model;
		if(actor.dealtype=='REASSIGN'){
			var data = [
			actor.id
			,'<center><img src="image/del.png" id="' + actor.id + '" align="center" onclick="delactor(this.id)" /></center>'
			,'<p  id="' + actor.id + '" align="center" >' + actor.name + ' </p>'
			,'<input  align="center" type="text" id="' + actor.id + '" class="textInput" onchange="setdesc(this.id,this.value);" value="' + actor.payoutdesc + '"/>'
			,getDealModeStr(key, actorType, dealMode)
			,'<center><img src="image/clock.png" id="' + actor.id + '" align="center" onclick="settime(this.id)" /></center>'
			//,'<input type="checkbox" id="' + actor.id + '" align="center" onclick="transfor(this.id)" value="转派" />'
			];
			cList.push(data);
		}
	}
	
	var jt = new JsTable('reassignActorTables', true, 'tableborder', null, null, null, null, null, null,
    [
        new JsCell(false, 'id', '', [], 1, [], ''),
        new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="image/del.png" onclick="delactor(\'{@COL0}\')"/></center>'),
        new JsCell(true, 'name', '<b><center>处理人或组</center></b>', [], 2, [], ''),
        new JsCell(true, 'category', '<b><center>自定义说明</center></b>', [], 3, [], ''),
        new JsCell(true, 'dealMode', '<b><center>处理模式</center></b>', [], 4, [], ''),
        new JsCell(true, 'dict', '<b><center>时限</center></b>', [], 5, [], '<center><img src="image/clock.png" onclick="settime(\'{@COL0}\')"/></center>')
       // new JsCell(true, 'isnull', '<b><center>转派</center></b>', [], 5, [], '')
    ],cList);
   jt.draw(document.getElementById('reassignActorTable'));
}
 
 /**
 *处理人列表显示(JsTable实现)
 */ 
 function copyActorTableView(){
	var cList = new Array();
	for(var i=0;i<actorMap.keySet().size();i++){
		var key = actorMap.keySet().get(i);
		var actor = actorMap.get(key);
		var actorType = actor.type;
		var dealMode = actor.model;
		if(actor.dealtype == 'MAKECOPY'){
			var data = [
			actor.id
			,'<center><img src="image/del.png" id="' + actor.id + '" align="center" onclick="delactor(this.id)" /></center>'
			,'<p  id="' + actor.id + '" align="center" >' + actor.name + ' </p>'
			,'<input  align="center" type="text" id="' + actor.id + '" class="textInput" onchange="setdesc(this.id,this.value);" value="' + actor.payoutdesc + '"/>'
			];
			cList.push(data);
		}
	}
	
	var jt = new JsTable('copyActorTables', true, 'tableborder', null, null, null, null, null, null,
    [
        new JsCell(false, 'id', '', [], 1, [], ''),
         new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="image/del.png" onclick="delactor(\'{@COL0}\')"/></center>'),
        new JsCell(true, 'name', '<b><center>抄送人或组</center></b>', [], 2, [], ''),
        new JsCell(true, 'category', '<b><center>自定义说明</center></b>', [], 3, [], '')
    ],cList);
    
   jt.draw(document.getElementById('copyActorTable'));
}
 
 /**
  * 协办人列表显示
  */
 function assistActorTable(){
	var cList = new Array();
	for(var i=0;i<actorMap.keySet().size();i++){
		var key = actorMap.keySet().get(i);
		var actor = actorMap.get(key);
		var actorType = actor.type;
		var dealMode = actor.model;
		if(actor.dealtype=='ASSIST'){
			var data = [
			actor.id
			,'<center><img src="image/del.png" id="' + actor.id + '" align="center" onclick="delactor(this.id)" /></center>'
			,'<p  id="' + actor.id + '" align="center" >' + actor.name + ' </p>'
			,'<input  align="center" type="text" id="' + actor.id + '" class="textInput" onchange="setdesc(this.id,this.value);" value="' + actor.payoutdesc + '"/>'
			,getDealModeStr(key, actorType, dealMode)
			,'<center><img src="image/clock.png" id="' + actor.id + '" align="center" onclick="settime(this.id)" /></center>'
			//,'<input type="checkbox" id="' + actor.id + '" align="center" onclick="transfor(this.id)" value="转派" />'
			];
			cList.push(data);
		}
	}
	
	var jt = new JsTable('assistActorTables', true, 'tableborder', null, null, null, null, null, null,
    [
        new JsCell(false, 'id', '', [], 1, [], ''),
        new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="image/del.png" onclick="delactor(\'{@COL0}\')"/></center>'),
        new JsCell(true, 'name', '<b><center>处理人或组</center></b>', [], 2, [], ''),
        new JsCell(true, 'category', '<b><center>自定义说明</center></b>', [], 3, [], ''),
        new JsCell(true, 'dealMode', '<b><center>处理模式</center></b>', [], 4, [], ''),
        new JsCell(true, 'dict', '<b><center>时限</center></b>', [], 5, [], '<center><img src="image/clock.png" onclick="settime(\'{@COL0}\')"/></center>')
       // new JsCell(true, 'isnull', '<b><center>转派</center></b>', [], 5, [], '')
    ],cList);
   jt.draw(document.getElementById('assistActorTable'));
}
 
 
 /**
  *添加派发人事件
  */
 function addDealActorBak(btype){

 	var actorStr = '';
 	var treeobject;
	 if(depOrRole == '1'){
	 	treeobject=document.getElementById('deptree');
		treeobject.contentWindow.getDepAndUser();
	 	actorStr = treeobject.contentWindow.returnStr;
	 }else if(depOrRole == '2'){
	 	treeobject=document.getElementById('roletree');
	 	treeobject.contentWindow.getDepAndUser();
	 	actorStr = treeobject.contentWindow.returnStr;
	 }else if(depOrRole == '9'){
	    treeobject=document.getElementById('customertree');
	 	treeobject.contentWindow.getDepAndUser();
	 	actorStr = treeobject.contentWindow.returnStr;
	 }

 	var actors = actorStr.split(';'); 
 	for(var i=0;i<actors.length-1;i++){
 		var actor = new JsActor();
 		var type = actors[i].substring(0,1);
 		var infos = actors[i].substring(2).split(',');
 		if(type == 'D'){//部门
			actor.type = 'G';
 			actor.id = infos[0];
 			actor.model = model;
 			//document.getElementById('deptree').contentWindow.delItem(infos[0]);
 			treeobject.contentWindow.delItem(infos[0]);
 		}else if(type == 'U'){//用户
			actor.type = 'U';
 			actor.id = infos[2];
 			actor.model = '2';
 			//document.getElementById('deptree').contentWindow.delItem(infos[0]);
 			treeobject.contentWindow.delItem(infos[0]);
 		}else if(type == 'R'){
 			actor.type = 'R';
 			actor.id = infos[0];
 			actor.model = '2';
 			//document.getElementById('roletree').contentWindow.delItem(infos[0]);
 		}
		actor.name = infos[1];
		
		if(btype == 'ASSIGN'){
			actor.dealtype = action_sign;
		}else if(btype == 'MAKECOPY'){
			actor.dealtype = 'MAKECOPY';
		}else if(btype == 'AUDIT'){//2011-1-14 fanying 添加
			actor.dealtype = 'AUDIT';
		}else if(btype == 'REASSIGN'){
			actor.dealtype = 'REASSIGN';
		}else if(btype == 'ASSIST'){
			actor.dealtype = 'ASSIST';
		}
		
		actor.limittimes = '';
		actor.payouttime = '';
		actor.dealtimes = '';
		actor.nextstepid = next_step;
		actor.payoutdesc = '';
		actor.childschema = childschema;
		
		if(!isAssignAndTransfer) {
			if(btype == 'ASSIGN') {
				for(var itact = actorMap.iterator(); itact.hasNext();)
				{
					var act = itact.next();
					if(act.dealtype == 'REASSIGN'){
						actorMap.remove(act.id);
					}
				}
				/*
				for(var j=0;j<actorMap.keySet().size();j++){
				 	var act = actorMap.get(actorMap.keySet().get(j));
				 	if(act.dealtype == 'REASSIGN'){
						actorMap.remove(act.id);
					}
				}
				*/
			}
			else if(btype == 'REASSIGN') {
				for(var itact = actorMap.iterator(); itact.hasNext();)
				{
					var act = itact.next();
					if(act.dealtype == 'ASSIGN'){
						actorMap.remove(act.id);
					}
				}
				/*
				for(var j=0;j<actorMap.keySet().size();j++){
				 	var act = actorMap.get(actorMap.keySet().get(i));
				 	if(act.dealtype == 'ASSIGN'){
						actorMap.remove(act.id);
					}
				}
				*/
			}
		}
		
		if(btype == 'ASSIGN' && next_radio == '1'){//单选
			for(var itact = actorMap.iterator(); itact.hasNext();)
			{
				var act = itact.next();
				if(act.dealtype == action_sign){
					actorMap.remove(act.id);
				}
			}
			/*
 			for(var i=0;i<actorMap.keySet().size();i++){
		 		var act = actorMap.get(actorMap.keySet().get(i));
		 		if(act.dealtype == action_sign){
					actorMap.remove(act.id);
				}
			}
			*/
		}
		actorMap.put(actor.id,actor);
 	}
 	dealActorTableView();
	auditActorTableView();
	reassignActorTable();
	copyActorTableView();
	assistActorTable();
 	//formsubmit();
 }
 
 /**
  *添加派发人事件
  */
 function addDealActor(btype)
 {
	var idType = '1'; //代表取用户的时候取登录名 不取id
	var idStr = document.getElementById('commonTree').contentWindow.getSelectData('group', 'id', idType);
	//var actorStr = showModalDialog('../../common/tools/orgtree/getMergerData.jsp?idStr='+idStr+'&idType='+idType,window,'help:no;scroll:no;status:no;dialogWidth:0px;dialogHeight:0px');
	//actorStr格式： U:id1:name1;U:id2:name2;D:id1:name1;D:id2:name2;R:id1:name1;R:id2:name2...
	var actorStr;
	$.post($ctx+"/userTemplate/getMergerData.action", {idStr:idStr,idType:idType}, function (actorStr){setDealActor(actorStr, btype);});
 }
 
 function setDealActor(actorStr, btype)
 {
	if(!actorStr || actorStr=='') {
		return;
	}
 	var actors = actorStr.split(';');
 	for(var tt=0 ; tt<actors.length ; tt++)
 	{
 		var actor = new JsActor();
 		var infos = actors[tt].split(':');
 		var type = infos[0];
 		var id = infos[1];
 		var text = infos[2];
 		if(type == 'D'){//部门
			actor.type = 'G';
 			actor.id = id;
 			actor.model = model;
 		}else if(type == 'U'){//用户
			actor.type = 'U';
 			actor.id = id;
 			actor.model = '2';
 		}else if(type == 'R'){
 			actor.type = 'R';
 			actor.id = id;
 			actor.model = '2';
 		}
		actor.name = text;
		
		if(btype == 'ASSIGN'){
			actor.dealtype = action_sign;
		}else if(btype == 'MAKECOPY'){
			actor.dealtype = 'MAKECOPY';
		}else if(btype == 'AUDIT'){//2011-1-14 fanying 添加
			actor.dealtype = 'AUDIT';
		}else if(btype == 'REASSIGN'){
			actor.dealtype = 'REASSIGN';
		}else if(btype == 'ASSIST'){
			actor.dealtype = 'ASSIST';
		}
		
		actor.limittimes = '';
		actor.payouttime = '';
		actor.dealtimes = '';
		actor.nextstepid = next_step;
		actor.payoutdesc = '';
		actor.childschema = childschema;
		
		if(!isAssignAndTransfer) {
			if(btype == 'ASSIGN') {
				for(var itact = actorMap.iterator(); itact.hasNext();)
				{
					var act = itact.next();
					if(act.dealtype == 'REASSIGN'){
						actorMap.remove(act.id);
					}
				}
				/*
				for(var j=0;j<actorMap.keySet().size();j++){
				 	var act = actorMap.get(actorMap.keySet().get(j));
				 	if(act.dealtype == 'REASSIGN'){
						actorMap.remove(act.id);
					}
				}
				*/
			}
			else if(btype == 'REASSIGN') {
				for(var itact = actorMap.iterator(); itact.hasNext();)
				{
					var act = itact.next();
					if(act.dealtype == 'ASSIGN'){
						actorMap.remove(act.id);
					}
				}
				/*
				for(var j=0;j<actorMap.keySet().size();j++){
				 	var act = actorMap.get(actorMap.keySet().get(i));
				 	if(act.dealtype == 'ASSIGN'){
						actorMap.remove(act.id);
					}
				}
				*/
			}
		}
		
		if(btype == 'ASSIGN' && next_radio == '1'){//单选
			for(var itact = actorMap.iterator(); itact.hasNext();)
			{
				var act = itact.next();
				if(act.dealtype == action_sign){
					actorMap.remove(act.id);
				}
			}
			/*
 			for(var i=0;i<actorMap.keySet().size();i++){
		 		var act = actorMap.get(actorMap.keySet().get(i));
		 		if(act.dealtype == action_sign){
					actorMap.remove(act.id);
				}
			}
			*/
		}
		actorMap.put(actor.id,actor);
 	}
 	dealActorTableView();
	auditActorTableView();
	reassignActorTable();
	copyActorTableView();
	assistActorTable();
 	//formsubmit(); 
	document.getElementById('commonTree').contentWindow.document.getElementById('commonResultArea').contentWindow.clearAll();
 }
 
 //删除用户
 function delactor(id){
 	actorMap.remove(id);
	dealActorTableView();
	auditActorTableView();
	reassignActorTable();
	copyActorTableView();
	assistActorTable();
 	//formsubmit();
 }
 
 //列表中时限设设置方法
 function settime(id){
 	var obj = document.getElementById('time');
 	ev = window.event;
	var left = ev.clientX + document.body.scrollLeft - document.body.clientLeft;
	var top = ev.clientY + document.body.scrollTop  - document.body.clientTop;
	var widths = document.body.clientWidth;
	var heights = document.body.clientHeight;
	//obj.style.left = left;
	obj.style.right = widths - ev.clientX;
	obj.style.top = top;
	var actor = actorMap.get(id);
	document.getElementById('actor_id').value = id;
	document.getElementById('actor_limittime').value = actor.limittimes;
	document.getElementById('actor_dealtime').value = actor.dealtimes;
	obj.style.display = '';
 }
 
 //弹出DIV保存方法
 function timesetting(){
 	var id = document.getElementById('actor_id').value;
 	var actor = actorMap.get(id);
	actor.limittimes = document.getElementById('actor_limittime').value;
	actor.dealtimes = document.getElementById('actor_dealtime').value;
	actorMap.put(id,actor);
	var obj = document.getElementById('time');
	obj.style.display = 'none';
 	//formsubmit();
 }
 
 //table中的自定义派发说明修改
 function setdesc(id,value){
 	var actor = actorMap.get(id);
 	actor.payoutdesc = value;
 	actorMap.put(id,actor);
	//formsubmit();
 }
 
 
 /**
  *公用信息中处理时限和受理时限设置方法
  */
 function timesetting_basic(limittime,dealtime){
 	for(var i=0;i<actorMap.keySet().size();i++){
		var actor = actorMap.get(actorMap.keySet().get(i));
		if(limittime != '' && actor.limittimes == ''){
			actor.limittimes = limittime;
			//actor.payouttime = limittime;
		}
		
		if(dealtime != '' && actor.dealtimes == ''){
			actor.dealtimes = dealtime;
		}
		actorMap.put(actor.id,actor);
	}
	
	//formsubmit();
 }
 /**
  *数据提交，将MAP中的数据返回至父页面,拼字符串如下：
  *处理人：U#:huangwei#:ASSIGN#:2#:1274976000#:1274966000#:1274976000#:dp_3#:这里写派发说明。个性化派发说明#;...
  *处理人中文名：张三：李四：神州泰岳...
  */
 function formsubmit(){
 	var payoutcode = '';	//派发人_code
	var payoutname = '';	//派发人_name
	
	var copycode = '';		//抄送人_code
	var copyname = '';		//抄送人_name
	
	var auditcode = '';	//审批人_code
	var auditname = '';	//审批人_name
	
	var transfercode = '';	//移交人_code
	var transfername = '';	//移交人_name
	
	var assistcode = '';	//协办人_code
	var assistname = '';	//协办人_name
	
	var limittime = document.getElementById('limittime').value;
	var dealtime = document.getElementById('dealtime').value;
	var desc = document.getElementById('desc').value;
	for(var i=0;i<actorMap.keySet().size();i++){
		var tempstr = '';
		var key = actorMap.keySet().get(i);
		var actor = actorMap.get(key);
			tempstr += actor.type + '#:';
			tempstr += actor.id + '#:';
			tempstr += actor.dealtype + '#:';
			var modelValue = ''
			var objSelect = document.getElementById(key+'_select');
			if(objSelect) {
				modelValue = getSelectValue(objSelect);
			}
		    tempstr += modelValue + '#:';
			
			if(actor.limittimes != ''){//未设置‘处理时限、派发时限或受理时限’的用公用时间；
				tempstr += parseDateToSeconds(actor.limittimes) + '#:';
				tempstr += parseDateToSeconds(actor.payouttime) + '#:';
			}else{
				tempstr += parseDateToSeconds(limittime) + '#:';
				tempstr += parseDateToSeconds(limittime) + '#:';
			}
			if(actor.dealtimes != ''){
				tempstr += parseDateToSeconds(actor.dealtimes) + '#:';
			}else{
				tempstr += parseDateToSeconds(dealtime) + '#:';
			}
			tempstr += '#:';
			tempstr += actor.nextstepid + '#:';
			tempstr += actor.childschema + '#:';
			if(desc != ''){
				
				//2010-12-22 fanying 当派发和抄送在页面中同时存在时，将描述保存在派发说明中，抄送不保存描述
				if(assignAndCopy == '1' && actor.dealtype=='MAKECOPY')
					tempstr += actor.payoutdesc + '#;';
				else
					tempstr += desc + ';' + actor.payoutdesc + '#;';	
					
			}else{
				tempstr += actor.payoutdesc + '#;';
			}
		
		if(actor.dealtype == 'REASSIGN'){//转派人
			transfercode += tempstr;
			transfername += ',' + actor.name;
		}else if(actor.dealtype == 'MAKECOPY'){//抄送人
			copycode += tempstr;
			copyname += ',' + actor.name;
		}else if(actor.dealtype == 'AUDIT' || actor.dealtype == 'REAUDIT' || actor.dealtype == 'ORGANIZEAUDIT'){//审批人
			auditcode += tempstr;
			auditname += ',' + actor.name;
		}else if(actor.dealtype == 'ASSIST'){//协办人
			assistcode += tempstr;
			assistname += ',' + actor.name;
		}else{//其它均属于派发人
			payoutcode += tempstr;
			payoutname += ',' + actor.name;
		}
	}
	
	if(payoutname != '')
	{
		payoutname = payoutname.substring(1);
	}
		
	if(transfername != '')
	{
		payoutname = transfername.substring(1);
	}
		
	if(copyname != '')
	{
		payoutname = copyname.substring(1);
	}
		
	if(auditname != '')
	{
		payoutname = auditname.substring(1);
	}
		
	if(assistname != '')
	{
		payoutname = assistname.substring(1);
	}
	
	var assignCode = payoutcode + transfercode + auditcode + copycode + assistcode;
	dialogArguments.AssignTreeField.setAssignTagString(treeField, assignCode);
	
	if(payout_obj)
	{
		payout_obj.value = payoutname;
	}
	
	window.close();
	
	//parent.window.F(650042009).S(new parent.window.CharType(payoutcode + transfercode + copycode));
 }
 
function getSelectValue(objSelect) {
	for (var h = 0; h < objSelect.options.length; h++) {
        if (objSelect.options[h].selected) {        
            return objSelect.options[h].value;        
        }        
    }  
}