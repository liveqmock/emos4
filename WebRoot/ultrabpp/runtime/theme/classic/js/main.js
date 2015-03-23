
//组织机构树    id:字段ID，checks:1:单选(默认)；2：多选;defaultData:默认值
//user 选择组后,联动的人员下拉框组件ID
//action  执行动作 如NEXT,RENEXT,自由流的ASSIGN,AUDIT,REAUDIT,AUDITINGPASS等默认NEXT
function groupTree(id,checks,defaultValue,user,action){
	var group = F(id);//组(隐藏控件)
	var viewGroup = F('view_' + id);//组
	var $viewGroup = $('#view_' + id);//组jquery对象
	
	$("#view_"+id+"_extend").hide();//隐藏掉处理组后面的小框框
	$viewGroup.css("width",216)//调整处理组控件长度
	
	//点击提交按钮前,再设置一次处理规则串
	$("#bpp_ActPanel_BtnPanel").children(":input[value='提　交']").click(function(){
		setAssignString();
	});
	//点击取消,清空处理规则串
	$("#bpp_ActPanel_BtnPanel").children(":input[value='取　消']").click(function(){
		ClientContext.setAssignString("");
	});
	
	if(action == '' || action == null){
		action = 'NEXT';
	}
	
	//可编辑状态下才出现组织机构树
	if($viewGroup.attr("readonly") != "readonly"){
		$viewGroup.click(function(){
			initDivs(this,'/dict/dictTree.action?id=' + id + '&&dictType=&&checkboxes=1&&levelFlag=-1&&checkLevelFlag=-1&&valueOf=value&&dictFlag=&&pid=0&&sqlStr=null&&sqlName=SQL_DICT_GROUPQUERY.query&&pflied=&&defaultValue=' + defaultValue);
		});
	}
	
	$viewGroup.keyup(function(){
 		toQuery();
	});
	
	/**人员下拉列表中的值变动时
	 * 如果是清空操作,则设置处理规则串为当前组
	 * 如果是选择人员,则设置处理规则串为当前人*/
	var userDropDwon = F("view_"+user);
	userDropDwon.change("",function(){
		setAssignString();
	});
	
	//组变动时,根据组ID填充人员列表,设置处理规则串为当前组
	group.change("",function(){
		if(getGroupId() == ""){
			ClientContext.setAssignString("");//清空处理规则串
			viewGroup.S("");//清空组显示框
			userDropDwon.resource = "";
			userDropDwon.S("");
		}else{
			getUserListByDepIds(getGroupId(),user);//获取选择的部门
			ClientContext.setAssignString("G#:"+getGroupId()+"#:" + action + "#:2#:0#:0#:0#:#:#:#:#;");
		}
	});
	
	//获取部门id
	var getGroupId = function(){
		return group.G();
	}
	
	//设置处理规则串
	var setAssignString = function(){
		var value = userDropDwon.G();
		if(value == ""){
			ClientContext.setAssignString("G#:"+getGroupId()+"#:" + action + "#:2#:0#:0#:0#:#:#:#:#;");
		}else{
			value = value.match(/\[.*\]/)[0].replace("[","").replace("]","");//取得中括号中的值
			ClientContext.setAssignString("U#:"+value+"#:" + action + "#:2#:0#:0#:0#:#:#:#:#;");
		}
		F(user).S(value);
	}
}

function groupUserTree(options){
	var viewobj = $("#"+options.viewid);
	var returnid = "";
	$("#"+options.viewid+"_extend").hide();//隐藏掉处理组后面的小框框
	viewobj.css("width",216)//调整处理组控件长度
	
	if(options.beforeInit){
		options.beforeInit(options);
	}
	if(!options.valueid){
		returnid = options.viewid+"_id";
	}
	returnid =returnid + "@" + options.viewid;
	viewobj.click(function(){
		window.open($ctx +"/serverQuest/peopleTree.jsp?isRadio=1&id='0'&first=true&isSelectType=&returnId="+returnid, '人员列表', 'height=400px, width=600px, top=100, left=300, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
	});
//	if(!options.defaultValue) options.defaultValue = "";
//	var url = '/dict/dictTree.action?id=id&&dictType=&&checkboxes=2&&levelFlag=-1&&checkLevelFlag=-1&&valueOf=value&&dictFlag=&&pid=0&&sqlStr=null&&sqlName=SQL_DICT_GROUPQUERY.query&&pflied=&&defaultValue=' + options.defaultValue;
//	viewobj.click(function(){
//		initDivs2({"viewid":options.viewid,"url":url});
//	});
}

function initDirector(options){
	var url = $ctx+"/dicManager/getDicItemByCodeAndValue.action";
	var url2 = "";
	$.post(url,{"dicitem.divalue":"directorFullname","dicitem.dtcode":"sheetVariable"},function(data){
		if(!data.remark) return;
		$.post($ctx+"/userManager/getUsersByGroupFullname.action",{groupFullname:data.remark},function(data){
			var viewstr = "";
			var valuestr = "";
			$.each(data,function(index,item){
				viewstr = viewstr + "," + item.fullname;
				valuestr = valuestr + "#;U#:" + item.loginname;
			});
			if(viewstr.length > 0){
				$("#"+options.viewid).val(viewstr.substring(1));
				if(options.valueid){
					$("#"+options.valueid).val(valuestr.substring(2));
				}else{
					$("#"+options.viewid+"_id").val(valuestr.substring(2));
				}
			}
		},"json");
	},"json");
}

//通过部门ID获取本部门所有用户信息   ids:部门ID  fieldID:下拉框字段ID
function getUserListByDepIds(ids,fieldId){
	var userList = F("view_"+fieldId);
	if(userList == null) return;
	
	userList.S("");//清空人员下拉列表
	var url = ctx + '/userOrDept/getUserListByDeptIds.action';
	userList.resource='';
	$.getJSON(url,{ids:ids},function(data){
		var resource = '';
		$.each(data,function(idx,item){
			if(item == null) return true;
			if(item.holidayState=='isHoliday'){
				resource += ',' + item.loginname + ':' + item.fullname + '[' + item.loginname + '](离岗)';	
			}else{
				resource += ',' + item.loginname + ':' + item.fullname + '[' + item.loginname + ']';
			}
		});
		if(resource != ''){
			resource = resource.substring(1);
		}
		userList.resource=resource;
	});
}






/****************** 给大字段的输入框追加内容  duly ************************/
//获得当前格式化日期
function getNowFormatDate(){
   var nowDate = new Date()
   var yy = nowDate.getFullYear();
   var mm = nowDate.getMonth() + 1; //lixichen 2013-11-12
   var dd = nowDate.getDate();
   var hh24 = nowDate.getHours();
   var nn = nowDate.getMinutes(); 
   var ss = nowDate.getSeconds();
   
   return (yy + "-" + mm + "-" + dd + " " + hh24 + ":" + nn + ":" + ss)
}

//添加“追加”按钮
var old_id = '';//记录上一次ID
function addBtn(id,text){
	if($('#' + id ).attr('readonly') != 'readonly' || $('#' + id ).attr('readonly') == false){
		if($('#add_' + id).length <= 0){
			var btnStr = '<input style="width:120px;height:24px;" id="add_' + id + '" type="button" value="' + text + '" onclick=toView("' + id + '"); />';
			var hei = parseInt($('#' + id ).css('height'));
			$('#' + id ).css('height',hei - 22);
			$('#' + id ).after(btnStr);
		}
			$('#' + id ).attr('readonly','true');
			if($('#' + id + '_extend') != null){
				$('#' + id + '_extend').css('display','none');
			}
	}
}

function removeBtn(id){
	if($('#add_' + id).length > 0){
		$('#add_' + id).remove();
		var hei = parseInt($('#' + id ).css('height'));
		$('#' + id ).css('height',hei + 22);
	}
}


//显示DIV
function toView(id){
	var top = $(document).scrollTop() + document.body.offsetHeight * 0.15;
	if($('#' + id + '_div').length <= 0){//对象不存在
		var str = '<div id="' + id + '_div" style="background:#aaaaaa;display:;z-index:1000;position:absolute;width:600px;height:225px; top:' + top + ';left:25%;">';
		str += '<textarea id="oldValue_' + id + '" style="display: none">' + $('#'+id).val() + '</textarea>';
		str += '<textarea id="newValue_' + id + '" style="height: 100%;width:99%" cols="15" rows="15"></textarea>';
		str += '<input style="width:140px;height:24px;" type="button" value="保存(ctrl + 回车)" onclick=toSave("' + id + '"); />';
		str += '<input style="width:70px;height:24px;" type="button" value="取消(Esc)" onclick=toCannel("' + id + '"); />';
		str += '</div>';
		$('#' + id ).after(str);
	}else{
		$('#' + id + '_div').css('top',top);
	}
	
	//控制同时只显示一个框
	if(old_id != ''){
		toCannel(old_id);
	}
	old_id = id;
	
	$('#' + id + '_div').css('display','');
	$('#newValue_' + id).focus();
	$(document).keydown(function(e){//ESC 27  ctrl:17 enter:13
		if(e.ctrlKey && 13==e.keyCode){
			toSave(id);
		}else if(e.which == 27){
			toCannel(id);
		}
	});
}

//获取值
function toSave(id){
	var values = $('#oldValue_' + id).val();
	if(values != ''){
		values += '\r\n';
	}
	if($('#newValue_' + id).val() != null && $('#newValue_' + id).val() != ''){
		values +='时间：' + getNowFormatDate() + '  人员：' + ClientContext.getAttr('FULLNAME') + '  添加信息：'  + $('#newValue_' + id).val();
	}
	$('#' + id).val(values);
	$('#' + id + '_div').css('display','none');
}


//取消
function toCannel(id){
	$('#' + id + '_div').css('display','none');
}
/****************** 给大字段的输入框追加内容  duly ************************/

/**
 * 自动输入用户信息
 * viewFieldName人员输入框
 * idFieldName人员id隐藏域
 * 选择人员后会对以上两个字段赋值
 */
var userAutoInput = function(viewFieldName,idFieldName,callback){
	//请求人自动搜索
	var user = $("#"+viewFieldName);
	var viewUser = $("<div>").attr("id","view_"+viewFieldName);//创建div供ListCombox使用
	user.after(viewUser);
	user.hide();//隐藏掉原有控件
	var combox =  new ListComboxEx({
		renderTo : viewUser.attr("id"),
		width:216,
		listWidth:350,
		tpl:'<tpl for="."><div ext:qtip="" class="x-combo-list-item">{value}[{key}] {depfullname}</div></tpl>',
		'AjaxUrl':$ctx+'/autosearch/autoSearchEx.action?sqlQuery=SQL_TEST_SearchUser.query',
		'queryParam':'paramKey',
		'IdField':idFieldName,
		'NameField':'fullname',
		'NameFieldName':'',
		"fields":["key","value","depfullname"],
		'listeners':{
	        select: function (field, newValue, oldValue) {
//	        	alert(newValue.json.value);
	        	F(viewFieldName).S(newValue.json.value);
	        	if(typeof callback != "undefined"){//回调并传递user
	        		$.post($ctx+"/userManager/getUserByLoginname.action",{userID:newValue.json.key},function(user){
	        			callback(user);
	        		},"json");
	        	}
	        },
	        scope: this
	    }
	});
	combox.setValue(F(viewFieldName).G());
}

/**
 * 显示知识库内容
 */
var addKmSearchDiv = function(){
	$(document.body).append('<div style="position:absolute;top:120px;right:15px;z-index:100;width:206px;" id="kmsearchdiv"></div>');
	var mod1 = document.getElementById("bpp_FormContainer");
	var mod2 = document.getElementById("kmsearchdiv");
	mod2.style.left =document.body.clientWidth/2+ mod1.offsetWidth/2+10+ "px";
	$.post($ctx+"/incident/requestKmContent.action",{},function(data){
		$("#kmsearchdiv").html(data);
		changeKm();
	});
}

/**
 * 增加按钮并指定相应处理
 */
var drawCreateXBt = function(btName,btCode,requstUrl){
	$("#bpp_FixBtn").append('<li style="float:right;margin-right:8px;"><input id="bppBtn_'+btCode+'"  type="button" value='+btName+'></li>');
	$("#bppBtn_"+btCode).click(function(){
	window.open (requstUrl, 'newwindow', 'height=500, width=500, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')
	});
}
//获取评审会参会人
function getAuditCanhuiPerson(){
	
}
//打开应用系统选择树
function systemTree(obj){
	var url = $ctx+"/commonViewTree/commonViewTree.action?opt.query=SQL_CDB_SYSTEM.query&opt.nameElementId="+obj.nameElementId+"&opt.enableCheckBoxes=true&opt.idElementId="+obj.idElementId;
	window.open(url,'systemTreeWindow','fullscreen=yes,type=fullWindow,scrollbars=yes');
}

/**
 * 获取分类ID
 * type: bs_t_sm_commonTree 表中的类型
 * fullname:bs_t_sm_commonTree 表中的fullname
 * item:bs_t_sm_commonTree 表中的数据列
 */

function setFieldIdByName(idName,IncidentPhenoClass,type,item){
//	if(!IncidentPhenoClass==""&&!IncidentPhenoClass==null){
		$.post($ctx+"/commonTree/getItemIdByName.action",{type:type,fullname:IncidentPhenoClass,item:item},function(data) {
			if(data!=null && data!='null'){
				if( typeof(F) != "undefined" ) {
					F(idName).S(data);
				}else{
					$("#"+idName).val(data);
				} 
			}
		});
//	}
}

