//实施人回填
function fillBackUserInfo(data,doc_id) {
	if (data != '') {
		var dataArray = data.split(';');
		var depArray = dataArray[0].split(':');
		if (depArray[2] != '') {
			$("#"+doc_id+"Id").val(depArray[1]);
			var index = depArray[2].indexOf("[");
			if(index!=-1){
			var res = depArray[2].substr(0,index);
			$("#"+doc_id).val(res);
			}else{
			$("#"+doc_id).val(depArray[2]);
			}
			
		}
	}
}

function bpp_init() 
{
	F("connBaseId").readonly(true);
	F("connBaseId").required(false);
	
	if(ClientContext.baseStatus == '草稿' || ClientContext.baseStatus == '新建'){
		$("#impl_contact").click(function(){
	        showModalDialog($ctx+'/common/tools/orgtree/organizaTree.jsp?doc_id='+this.id+'&back_fill="incident"&isSelectType=1&idtype=1&isRadio=1',window,'help:no;center:true;scroll:no;status:no;dialogWidth:590px;dialogHeight:490px');
	    });
		$("#affect_system").click(function(){
	        systemTree({"nameElementId":"affect_system","idElementId":"affect_system_id"});
	    });
		
		F("affect_udstream_system").change("affect_udstream_system_change",function(){
			if(F("affect_udstream_system").G() == "是"){
				F("affect_system").required(true);
			}else{
				F("affect_system").required(false);
			}
		});
		F("affect_user_end").change("affect_user_end_change",function(){
			if(F("affect_user_end").G() == "是"){
				F("affect_user_end_desc").required(true);
			}else{
				F("affect_user_end_desc").required(false);
			}
		});
		F("is_sync_backup").change("is_sync_backup_change",function(){
			if(F("is_sync_backup").G() == "是"){
				F("backup_env_chg_time").required(true);
			}else{
				F("backup_env_chg_time").required(false);
			}
		});
	}
	
	//当选择应用系统时对应用系统ID进行赋值
	F("connBusSystem").change("connBusSystem",function(){
		if(F("connBusSystem").G()!=""&&F("connBusSystem").G()!=null){
			setFieldIdByName("connBusSystemID",F("connBusSystem").G(),"busSystem","PROPFIELD_03");
		}
	});
	
	
	//建单提交
	ClientContext.init("action","submit",function(){
		getDefaultDep('EDITUSERID','EDITUSER');
		groupTree('dealGroup','','',"dealUser");//选择部门
		return true;
	})
	
	ClientContext.submit("action","submit",function(){
		if ( $("#last_chg_time").val() != "" ) {
			//上次变更时间应小于当前时间
			var validate_res = validateTime();
			if(validate_res == 0){
				return false;
			}
		}
		return true;
	})
	 
	
	//退回，发布部署方案非必填
	ClientContext.init("action","ToBack",function(){
		F("buildCase").required(false);
		return true;
	})
	//退回,取消，发布部署方案必填
	ClientContext.back('ToBack',function(){
		F("buildCase").required(true);
		return true;
	});
	
	//变更对象值变化时，设置变更类型
	if(!F('connBusSystem').isReadonly){
		F("connBusSystem").change("",function()
		{
			var connBusSystem = F("connBusSystem").G();
			if(connBusSystem != null && connBusSystem.indexOf('.') != -1){
				connBusSystem = connBusSystem.substring(connBusSystem.lastIndexOf('.') + 1);
			}
			var sqlName = "select AUDITUSERS as times1 , AUDITUSERS_2 as times2 from BS_T_WF_RELEASEDEALUSERCONFIG where bussystem = \'" + connBusSystem +"'";
			var url = ctx + "/business/getJsonBySQL.action";
			$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","sqlName":sqlName},function(data){
				var dt = eval(data);
				var time = 0;
				$.each(dt,function(i,item){
					if(item == null) return true;
					if(item.TIMES1 != null && item.TIMES1 != ''){
						time++;
					}
					if(item.TIMES2 != null && item.TIMES2 != ''){
						time++;
					}
				});
				if(time == 0){
					F('allAuditTime').S('1');
				}else{
					F('allAuditTime').S(time);
				}
			});
		});
	}

	//修订完成 提交验证操作
	ClientContext.init("action","caseCheck",function(){
    	getDefaultDep('CONFIRMID','CONFIRMNAME');
    	groupTree('dealGroup','','',"dealUser");//选择部门
	})
	ClientContext.submit("action","caseCheck",function(){
		if ( $("#last_chg_time").val() != "" ) {
			//上次变更时间应小于当前时间
			var validate_res = validateTime();
			if(validate_res == 0){
				return false;
			}
		}
		getAssStr('dealGroup','dealUser','');
		return true;
	})
	
	//方案验证完成 提交审批操作
	ClientContext.init("action","check2audit",function(){
    	getDefaultDep('AUDITUSERIDS','AUDITUSERS');
    	groupTree('dealGroup','','',"dealUser");//选择部门
	})
	
	ClientContext.submit("action","check2audit",function(){
    	getAssStr('dealGroup','dealUser','');
    	return true;
	})
	
	if(ClientContext.baseStatus == '验证中' || ClientContext.baseStatus == '审批中'){
		F('cmdbReload').readonly(true);
		F('baseResult').readonly(true);
		F('closeUser').readonly(true);
	}
	
	//审批通过
	ClientContext.init("action","auditPassed",function(){
		var auditTimes = F('auditTimes').G();
		if(auditTimes == null || auditTimes == ''){
			auditTimes = '1';
		}
		var allAuditTime = F('allAuditTime').G();
		if(allAuditTime != '' && auditTimes != '' && parseInt(allAuditTime)  > parseInt(auditTimes)){//需要再次进行审批
	    	getDefaultDep('AUDITUSERIDS_2','AUDITUSERS_2','RENEXT');
	    	groupTree('dealGroup','','',"dealUser",'RENEXT');//选择部门
		}else{
			//不需要二次审批，但可审批升级
	    	getDefaultDep('ORGANIZEAUDITID','ORGANIZEAUDITNAME');
	    	groupTree('dealGroup','','',"dealUser");//选择部门    
	    	setOrganizeAuditActor();
			F("auditAgain").visiable(true);	    	
	    	F("auditAgain").change("",function(){//升级时选择处理人
	    		isAuditAgain();
	    	})
	    	
	    	F("auditAgainUser").change("",function()
			{
				var actor = F("auditAgainUser").G();
				ClientContext.setAssignString(actor);
			});
		}
	})
	
	//判断是否进行审批升级
	function isAuditAgain(){
		var auditAgain = F("auditAgain").G();
   		if(auditAgain == ''){
   			F('view_dealGroup').visiable(true);
   			F('view_dealUser').visiable(true);
   			F('view_dealGroup').required(true);
   			F('view_dealUser').required(false);
   			F('auditAgainUser').visiable(false);
   			F('auditAgainUser').required(false);
   			F('view_dealGroup').S('');
   			F('view_dealUser').S('');
   			ClientContext.setAssignString('');   				
   		}else{
   			F('view_dealGroup').visiable(false);
   			F('view_dealUser').visiable(false);
   			F('view_dealGroup').required(false);
   			F('view_dealUser').required(false);
   			F('auditAgainUser').visiable(true);
   			F('auditAgainUser').required(true);
   			F('view_dealGroup').S('');
   			F('auditAgainUser').S('');
   			F('view_dealUser').S('');
   			ClientContext.setAssignString('');
			setOrganizeAuditActor();
   		}
	}
	//审批通过
	ClientContext.submit("action","auditPassed",function(){
		var auditTimes = F('auditTimes').G();
		var allAuditTime = F('allAuditTime').G();
		if(allAuditTime != '' && auditTimes != '' && parseInt(allAuditTime) > parseInt(auditTimes)){//需要再次进行审批
   			getAssStr('dealGroup','dealUser','RENEXT');
		}else{
			var auditAgain = F("auditAgain").G();
			if(auditAgain == ''){//不升级
				getAssStr('dealGroup','dealUser','');
			}else{
				setOrganizeAuditActor();
				var actor = F('auditAgainUser').G();
				if(actor == null || actor == ''){
					alert('升级审批人不能为空');
					return false;
				}
				getAssStr('','dealUser','RENEXT');
			}
		}
    	return true;
	})
	
	//审批不通过
	ClientContext.init("action","auditUnPass",function(){
		F('step_sug').S('否决');
		reviewInfo();
		F("step_sug").change("",function(){
			reviewInfo();
		})
	})
	
	//审批不通过 提交
	ClientContext.submit("action","auditUnPass",function(){
		return setReviewInfo();	
	})
	
	//评审通过  提交
	ClientContext.init("action","organizeAuditPass",function(){
    	getDefaultDep('EXCUTEUSERID','EXCUTEUSER');
    	groupTree('dealGroup','','',"dealUser");//选择部门		
    	groupUserTree({"viewid":"audit_huican_person","beforeInit":initDirector});
    	F("review_time").S(ClientContext.getAttr("NOWTIME"));
	})
	ClientContext.submit("action","organizeAuditPass",function(){
    	getAssStr('dealGroup','dealUser','');
    	return true;
	})
	
	//评审不通过  提交
	ClientContext.submit("action","organizeAuditUnpass",function(){
		F('step_cmdbupload').S('否');
		F('step_baseresult').S('取消');
		return setReviewInfo();	
	})
	//验证完成 提交
	ClientContext.init("action","checked",function(){
    	F('step_cmdbupload').S('否');
    	F('step_baseresult').S('成功');
	})
	ClientContext.submit("action","checked",function(){
    	getAssStr('dealGroup','dealUser','');
		return setReviewInfo();
	})
	
	if(ClientContext.baseStatus == '实施中' || ClientContext.baseStatus == '审批中' || ClientContext.baseStatus == '评审中'){
		if(F('cmdbReload') != null){
			F('cmdbReload').readonly(true);
		}
		if(F('baseResult') != null){
			F('baseResult').readonly(true);
		}
		if(F('closeUser') != null){
			F('closeUser').readonly(true);
		}
	}
	
	
	//实施完成 提交验证操作
	ClientContext.init("action","finished",function(){
		getDefaultDep('TESTUSERID','TESTUSER');
    	groupTree('dealGroup','','',"dealUser");//选择部门
	})
	ClientContext.submit("action","finished",function(){
    	getAssStr('dealGroup','dealUser','');
    	return true;
	})
	
	
	
	


	//自动获取当前人处室的业务系统
//	if(!F('connBusSystem').isReadonly){
//		var groupId = ClientContext.getAttr('GROUPID');
//		if(groupId != null && groupId != ''){
//			var groupIds = groupId.split(',');
//			var str = '';
//			for(var i=0;i<groupIds.length;i++){
//				var id = groupIds[i];
//				if(str != ''){
//					str += ' or ';
//				}
//				str += " propfield_01 like '%" + id + "%' "
//			}
//			F('connBusSystem').parameters += ' and (' + str + ')';
//		}
//	}
		
	//获取申请人相关信息
	if(!F('requestUser').isReadonly){
		userAutoInput("requestUser","requestUserId",function(user)
		{
			F("requestGroup").S(user.depname); // 单位
			F("requestDept").S(user.depname); // 部门
			F("requestMobile").S(user.mobile); //手机
			F("requestEmail").S(user.email); //邮件
			F("requestOA").S(user.oaNumber);//oa号
			
		});
	}
	
	//隐藏 隐藏域
	if($('#hiddenArea_Item') != null){
		$('#hiddenArea_Item').css('display','none');
	}
	
	//填写回顾信息  审批不通过（否决时）、评审不通过、验证完成
	function reviewInfo(){
		var auditSug = F('step_sug').G();
		if(auditSug == '否决'){
			F('step_cmdbupload').S('否');
			F('step_baseresult').S('取消');
		}else{
			F('step_cmdbupload').S('');
			F('step_baseresult').S('');
		}
	}
	
	
	//保存回顾信息
	function setReviewInfo(){
		try{
			var step_cmdbupload = F('step_cmdbupload').G();
			if(step_cmdbupload != null && step_cmdbupload != ''){
				F('cmdbReload').S(F('step_cmdbupload').G());
				F('baseResult').S(F('step_baseresult').G());
				F('closeUser').S(ClientContext.getAttr('FULLNAME'));
				F('closeTime').S(ClientContext.getAttr('NOWTIME'));
				F('closeUserID').S(ClientContext.getAttr('LOGINNAME'));
			}
		}catch(e){}
		return true;
	}
	
	
	
	//提交时获取处理部门或组
	function getAssStr(depId,userId,action){
		if(action == ''){
			action = 'NEXT';
		}
		if(F(depId) != null){
			var dep = F(depId).G();
			if(dep != ''){
				ClientContext.setAssignString("G#:" + dep + "#:" + action + "#:2#:0#:0#:0#:#:#:#:#;");
			}
		}
		if(F(userId) != null){
			var user = F(userId).G();
			if(user != ''){
				ClientContext.setAssignString("U#:" + user + "#:" + action + "#:1#:0#:0#:0#:#:#:#:#;");
			}
		}
	}
	 
	 
	 
	 
	 	
//通过业务系统获取相应的处理人部门  EDITUSERID/EDITUSER 修订人   CONFIRMID/CONFIRMNAME 方案验证人 
//AUDITUSERIDSo/AUDITUSERS 一级审批人 AUDITUSERIDS_2/AUDITUSERS_2 二级审批人 ORGANIZEAUDITNAME/ORGANIZEAUDITID 评审人
//	EXCUTEUSERID/EXCUTEUSER 实施人 TESTUSERID/TESTUSER 验证人
	function getDefaultDep(f_id,f_name,action){
		if(action == '' ||  action == null){
			action = 'NEXT';
		}
		var connBusSystem = F('connBusSystem').G();
	   	if(connBusSystem != null && connBusSystem != ''){
	   		var dealGroupId = '';
	   		var dealGroupName = '';
	   		
			if(connBusSystem != null && connBusSystem.indexOf('.') != -1){
				connBusSystem = connBusSystem.substring(connBusSystem.lastIndexOf('.') + 1);
			}
	   		var sqlName = "select " + f_id + "," + f_name + " from bs_t_wf_releasedealuserconfig where BUSSYSTEM='" + connBusSystem + "'";
	    	var url = ctx + "/business/getJsonBySQL.action";
			$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","sqlName":sqlName},function(data){
				var dt = eval(data);
				$.each(dt,function(i,item){
					if(item == null) return true;
					for(var key in item){  
				       if(f_id == key){
				       		dealGroupId = item[key];
				       }
				       if(f_name == key){
				       		dealGroupName = item[key];
				       }
				    }
				});
				if(dealGroupId != '' && dealGroupName != ''){
					if(dealGroupId.indexOf('U#:') != -1){
						F('dealGroup').required(false);
						F('view_dealGroup').required(false);
						F("dealUser").S(dealGroupId.substring(3));//设定下一步处理人
						F("view_dealUser").S(dealGroupName);//设定下一步处理人
						ClientContext.setAssignString("U#:" + dealGroupId.substring(3) + "#:" + action + "#:1#:0#:0#:0#:#:#:#:#;");
					}else{
						dealGroupId = dealGroupId.substring(3);
						F('view_dealGroup').S(dealGroupName);
						F('dealGroup').S(dealGroupId);
						getUserListByDepIds(dealGroupId,'dealUser');
						ClientContext.setAssignString("G#:" + dealGroupId + "#:" + action + "#:2#:0#:0#:0#:#:#:#:#;");
					}					
				}
			});
		}
	}
	
	//设置变更的评审人
	function setOrganizeAuditActor(){
		F('dealGroup').S('40925cf428c64cd480ff6354648ff01b');
		F('view_dealGroup').S('评审会记录组');
		getUserListByDepIds('40925cf428c64cd480ff6354648ff01b','dealUser');
	}
	
	//得到日期数据
	function getLongDate(){
		var myDate = new Date().getTime()/1000;
		return myDate;
	}
	//时间验证函数
	//当前时间与选择时间比较。
	function cmpDate(str_time){
		var olddate=js_strto_time(str_time)+30;
		var myDate =getLongDate();
		return olddate<=myDate?true:false;
	}
	//根据日期字符串获取时间
	function js_strto_time(str_time){
		var new_str = str_time.replace(/:/g,'-');
		new_str = new_str.replace(/ /g,'-');
		var arr = new_str.split("-");
		var datum = new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4],arr[5]));
		return strtotime = datum.getTime()/1000;
	}
	//上次变更时间应小于当前时间
	function validateTime(){
		if(cmpDate(F('last_chg_time').G()) == false){
			alert("上次变更时间应小于当前时间");
			return 0;
		}else{
			return 1;
		}
	}
	
	//批次信息下拉框选择时，查询其对应的id，并赋值给隐藏域
	F("batch_info").change("",function()
	{
		var info = F("batch_info").G();
		var batch_no = info.substring(0,info.lastIndexOf('['));
		var reviewtime = info.substring(info.lastIndexOf('[')+1,info.length-1);
		var sqlName = "select pid,batch_no,chg_start_time,chg_end_time,reviewtime,latestreviewtime from BS_T_SM_BATCHCONF where batch_no = '" + batch_no +"' and reviewtime = \'" + reviewtime +"'"; 
		var url = ctx + "/business/getJsonBySQL.action";
		$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","sqlName":sqlName},function(data){
			var dt = eval(data);
			//var time = 0;
			$.each(dt,function(i,item){
				if(item == null) return true;
				F('batch_info_id').S(item.PID);
			});
 		});
	});
	
	
	
	//增加小尾巴
	/**
	 * 增加页面元素并绑定事件
	*/
	
	var elementId = 'batch_info'; //需要增加帮助信息的表单ID
	$.each(elementId.split(","), function() { 
		var docElement = this ;
		addElementHelp(docElement); //初始化页面，往页面增加帮助信息按钮，调整原布局
		$("#" + docElement + "fieldHelp").click(function(e) {
			var dialogParent = $("#" + docElement + "info").parent(); 
		    var dialogOwn = $("#" + docElement + "info").clone(); //规避因jqueryui dialog方法将div删除的问题
			addEleInfo(docElement);  
			$("#" + docElement + "info").dialog({
				open: function() {
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
				height:90,
				width:680,
				autoOpen: true, 
	            title: '详细信息',  
	            close: function () {   
	                dialogOwn.appendTo(dialogParent);  
	                $(this).dialog("destroy").remove();   
	                
	            }
			});
			
			$(".ui-dialog").css("height","110px");  
		});
	});
 
}


/**
 * 在所传入dom元素上增加元素具体帮助信息内容
 * @param elementId
*/

function addEleInfo(elementId){
	var dtcode = elementId;
	var info = F("batch_info").G();
	var batch_no = info.substring(0,info.lastIndexOf('['));
	var reviewtime = info.substring(info.lastIndexOf('[')+1,info.length-1);
	var sqlName = "select pid,batch_no,chg_start_time,chg_end_time,reviewtime,latestreviewtime from BS_T_SM_BATCHCONF where batch_no = '" + batch_no +"' and reviewtime = \'" + reviewtime +"'"; 
	var url = ctx + "/business/getJsonBySQL.action";
	$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","sqlName":sqlName},function(data){
		var dt = eval(data);
		$.each(dt,function(i,item){
			if(item == null) return true;
			 $("#"+elementId+"info").append(
					 	"<div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;'>"
					 	+"<table><th>批次号</th><th>变更开始时间</th><th>变更结束时间</th><th>评审会时间</th><th>最晚参与评审时间</th>"
					 	+"<tr><td style='border-right:1px solid #cccccc'>"+item.BATCH_NO+"</td><td style='border-right:1px solid #cccccc'>"+item.CHG_START_TIME+"</td><td style='border-right:1px solid #cccccc'>"+item.CHG_END_TIME+"</td><td style='border-right:1px solid #cccccc'>"+item.REVIEWTIME+"</td><td>"+item.LATESTREVIEWTIME+"</td></tr></table>"
//	                    +"<div style='width:50px;float:left;text-align:center;border-right:1px solid #cccccc'>" + item.BATCH_NO + "</div>" 
//	                    + "<div style='width:75%;float:left;margin-left:2px;'>" + item.CHG_START_TIME + "</div>"
	                    +"</div>");
					 
		});
	});
}
 
/**
 * 在所传入dom元素后增加帮助信息按钮
 * @param elementId
 */

function addElementHelp(elementId){	
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
