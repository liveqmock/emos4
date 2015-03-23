var actorMap;//处理人集合(key:id,value:JsActor)
var isselect;//单选或多选,1为多选，0为单选

var depOrRole = '1';//判断当前是组织机构(1)选择还是角色选择(2)

//字符串规则：派发类型(U:人,G:组)#:派发角色ID(人:登录名,组:组ID)#:处理类型(固定流程：NEXT,自由流程：)#:组处理模式(1:共享,2:独占,3:管理者)#:受理时限#:派发时限#:处理时限#:环节号#:子流程定义名称#:派发说明#;
var actor_code = '';//字符串
var payout_code = '';//派发人字符串
var payout_name = '';//派发人_name
var audit_name = '';//审批人_name
var copy_name = '';//抄送人_name
var childschema = '';//子流程定义名称

var model = '1';//组处理模式(1:共享,2:独占,3:管理者)
var action_sign = '';//动作
var next_step = '';//下一环节
var wfVersion = '';//版本号

//初始化方法
function init(){
	actorMap = new Map();
	initdata();	
	dealActorTableView();
}

//tab页选择事件
function PageMenuOnclick(id1,id2,type){
	depOrRole = type;
	PageMenuActive(id1,id2);
}

//数据初始化
function initdata(){
	var tmp_BaseAcceptTime = '';//工单受理时限
	var tmp_BaseDealTime = '';//工单处理时限
	
	if(tmp_BaseAcceptTime != '') {
		document.getElementById('limittime').value = parseSecondToStr(parseInt(tmp_BaseAcceptTime));
	}
	if(tmp_BaseDealTime != '') {
		document.getElementById('dealtime').value = parseSecondToStr(parseInt(tmp_BaseDealTime));
	}
	
	if(isselect == '0'){
		document.getElementById('deptree').src = '../../common/tools/depListTree.jsp?isRadio=0';
		document.getElementById('roletree').src = '../../workflow/actor/sheetRoleTree.jsp?isRadio=0&wfVersion=' + wfVersion;
	}else{
		document.getElementById('deptree').src = '../../common/tools/depListTree.jsp?isRadio=1';
		document.getElementById('roletree').src = '../../workflow/actor/sheetRoleTree.jsp?isRadio=1&wfVersion=' + wfVersion;
	}
	
	var obj = opener.document.getElementById('actorStr');
	var auditObj = opener.document.getElementById('auditActorStr');
	var copyObj = opener.document.getElementById('copyActorStr');
	if(obj) {
		actor_code = obj.value;
	}
	if (auditObj) {
		actor_code += auditObj.value;
	}
	if (copyObj) {
		actor_code += copyObj.value;
	}
	
	var name = '';
	var obj1 = opener.document.getElementById('actorName');
	if(obj1) {
		payout_name = obj1.value;
		if(payout_name != ''){
			name += payout_name;
		}
	}
	
	var obj3 = opener.document.getElementById('auditActorName');
	if(obj3) {
		audit_name = obj3.value;
		if(audit_name != ''){
			name += ';' +  audit_name;
		}
	}
	
	var obj2 = opener.document.getElementById('makeCopyActorName');
	if(obj2) {
		copy_name = obj2.value;
		if(copy_name != ''){
			name += ';' +  copy_name;
		}
	}
	
	if(name != '' && name.substring(0,1) == ';'){
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

	var codeObjs = code.split('#;');
	var names = name.split(';');

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

/**
 *处理人列表显示(JsTable实现)
 */ 
 function dealActorTableView(actionType){
 	var actions = ['ASSIGN', 'REASSIGN', 'AUDIT', 'REAUDIT', 'MAKECOPY', 'NEXT', 'ORGANIZEAUDIT'];
 	for(var j=0;j<actions.length;j++) {
 		var actionType = actions[j];
 		var cList = new Array();
		for(var i=0;i<actorMap.keySet().size();i++){
			var actor = actorMap.get(actorMap.keySet().get(i));
			if(actor.dealtype == actionType){
				var data = [
				actor.id
				,'<center><img src="image/del.png" id="' + actor.id + '" align="center" onclick="delactor(this.id)" /></center>'
				,'<p  id="' + actor.id + '" align="center" >' + actor.name + ' </p>'
				,'<input  align="center" type="text" id="' + actor.id + '" class="textInput" onchange="setdesc(this.id,this.value);" value="' + actor.payoutdesc + '"/>'
				,'<center><img src="image/clock.png" id="' + actor.id + '" align="center" onclick="settime(this.id)" /></center>'
				];
				cList.push(data);
			}
		}
		
		var jt = new JsTable(actionType.toLocaleLowerCase() + 'ActorTables', true, 'tableborder', null, null, null, null, null, null,
        [
            new JsCell(false, 'id', '', [], 1, [], ''),
            new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="image/del.png" onclick="delactor(\'{@COL0}\')"/></center>'),
            new JsCell(true, 'name', '<b><center>处理人或组</center></b>', [], 2, [], ''),
            new JsCell(true, 'category', '<b><center>自定义说明</center></b>', [], 3, [], ''),
            new JsCell(true, 'dict', '<b><center>时限</center></b>', [], 4, [], '<center><img src="image/clock.png" onclick="settime(\'{@COL0}\')"/></center>')
        ],cList);
       jt.draw(document.getElementById(actionType.toLocaleLowerCase() + 'ActorTable'));
 	}
 }
 
 /**
  *添加派发人事件
  */
 function addDealActor(btype){
 	if(isselect == '0') {//单选
		actorMap.clear();
	}
	
 	 var actorStr = '';
	 if(depOrRole == '1'){
		document.getElementById('deptree').contentWindow.getDepAndUser();
	 	actorStr = document.getElementById('deptree').contentWindow.returnStr;
	 }else if(depOrRole == '2'){
	 	document.getElementById('roletree').contentWindow.getDepAndUser();
	 	actorStr = document.getElementById('roletree').contentWindow.returnStr;
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
 			document.getElementById('deptree').contentWindow.delItem(infos[0]);
 		}else if(type == 'U'){//用户
			actor.type = 'U';
 			actor.id = infos[2];
 			actor.model = '2';
 			document.getElementById('deptree').contentWindow.delItem(infos[3]);
 		}else if(type == 'R'){
 			actor.type = 'R';
 			actor.id = infos[0];
 			actor.model = '2';
 			document.getElementById('roletree').contentWindow.delItem(infos[0]);
 		}
		actor.name = infos[1];
		actor.dealtype = btype;
		actor.limittimes = '';
		actor.payouttime = '';
		actor.dealtimes = '';
		actor.nextstepid = next_step;
		actor.payoutdesc = '';
		actor.childschema = childschema;
		actorMap.put(actor.id, actor);
 	}
 	dealActorTableView();
 }
 
 //删除用户
 function delactor(id){
 	actorMap.remove(id);
	dealActorTableView();
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
 }
 
 //table中的自定义派发说明修改
 function setdesc(id,value){
 	var actor = actorMap.get(id);
 	actor.payoutdesc = value;
 	actorMap.put(id,actor);
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
	
	var auditcode = '';		//审批人_code
	var auditname = '';		//审批人_name
	
	var limittime = document.getElementById('limittime').value;
	var dealtime = document.getElementById('dealtime').value;
	var desc = document.getElementById('desc').value;
	for(var i=0;i<actorMap.keySet().size();i++){
		var tempstr = '';
		var actor = actorMap.get(actorMap.keySet().get(i));
			tempstr += actor.type + '#:';
			tempstr += actor.id + '#:';
			tempstr += actor.dealtype + '#:';
			tempstr += actor.model + '#:';
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
				tempstr += desc + ';' + actor.payoutdesc + '#;';	
			}else{
				tempstr += actor.payoutdesc + '#;';
			}
		
		if(actor.dealtype == 'MAKECOPY'){//抄送人
			copycode += tempstr;
			copyname += ';' + actor.name;
		}else if(actor.dealtype == 'AUDIT'){//审批人
			auditcode += tempstr;
			auditname += ';' + actor.name;
		}else{//其它均属于派发人
			payoutcode += tempstr;
			payoutname += ';' + actor.name;
		}
	}
	
	if(payoutname != ''){
		payoutname = payoutname.substring(1);
		opener.document.getElementById('actorName').value=payoutname;
		opener.document.getElementById('actorStr').value=payoutcode;
	} else {
		if(action_sign != 'MAKECOPY') {//下一步并内部抄送
			if(opener.document.getElementById('actorStr')) {
				opener.document.getElementById('actorStr').value="";
			}
			if(opener.document.getElementById('actorName')) {
				opener.document.getElementById('actorName').value="";
			}
		}
	}
		
	if(auditname != ''){
		auditname = auditname.substring(1);
		opener.document.getElementById('auditActorName').value=auditname;
		opener.document.getElementById('auditActorStr').value=auditcode;
	} else {
		if(opener.document.getElementById('auditActorStr')) {
			opener.document.getElementById('auditActorStr').value="";
		}
		if(opener.document.getElementById('auditActorName')) {
			opener.document.getElementById('auditActorName').value="";
		}
	}
	
	if(copyname != ''){
		copyname = copyname.substring(1);
		opener.document.getElementById('makeCopyActorName').value=copyname;
		opener.document.getElementById('copyActorStr').value=copycode;
	} else {
		if(action_sign != 'NEXT') {//下一步并内部抄送
			if(opener.document.getElementById('copyActorStr')) {
				opener.document.getElementById('copyActorStr').value="";
			}
			if(opener.document.getElementById('makeCopyActorName')) {
				opener.document.getElementById('makeCopyActorName').value="";
			}
		}
	}
		
 }
 
  