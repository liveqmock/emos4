/**
 * 通用方法和继承所用到的类
 * 版本：V1.0
 * @作者：李昊原
 * @创建时间：2008年8月22日
 * @修改记录：
 * ---------------------------------
 *   修改时间  | 修改人 | 备注
 *  - - - - - - - - - - - - - - - - 
 *  2008-08-22 | 李昊原 | 创建文件
 * ---------------------------------
*/
function Util() {}
//浏览器的判断，true为IE，false为Firefox
Util.ie = navigator.appName.indexOf('Microsoft') != -1 ? true : false;

Util.$ = function(objid) {return document.getElementById(objid);}

Util.windowWidth = 0;

//relate：0为不关联，1为异步关联，2为同步关联
//copydata：1为复制原工单数据，0为不复制原工单数据，如果relate为0，则该参数不起作用
Util.newWorksheet = function(baseSchema, relate, copydata ,copyconfig)
{
	var url = "";
	if(relate == 0)
	{
		url = $ctx + "/ultrabpp/view.action?mode=NEW&baseSchema="+baseSchema;
	}
	else if(relate == 1)
	{
		url = $ctx + "/ultrabpp/view.action?mode=NEW&baseSchema="+baseSchema+"&relateType=0&fromBaseSchema="+ClientContext.baseSchema+"&fromBaseID="+ClientContext.baseID+"&fromBaseSn="+ClientContext.baseSN+"&fromTaskID="+ClientContext.taskID+"&cRelateData="+copydata+"&cRelateByConfig="+copyconfig;
	}
	else
	{
		url = $ctx + "/ultrabpp/view.action?mode=NEW&baseSchema="+baseSchema+"&relateType=1&fromBaseSchema="+ClientContext.baseSchema+"&fromBaseID="+ClientContext.baseID+"&fromBaseSn="+ClientContext.baseSN+"&fromTaskID="+ClientContext.taskID+"&cRelateData="+copydata+"&cRelateByConfig="+copyconfig;
	}
	window.open(url, "_blank");
}

Util.openWorksheet = function(baseID, baseSchema)
{
	var url = $ctx + "/ultrabpp/view.action?mode=MODIFY&baseID="+baseID+"baseSchema="+baseSchema;
	window.open(url, "_blank");
}

Util.keyFieldReplace = function(resString)
{
	var formatString = resString;
	var finalString = "";
	while(formatString.indexOf("@!{") > -1)
	{
		finalString += formatString.substring(0, formatString.indexOf("@!{"));
		formatString = formatString.substring(formatString.indexOf("@!{") + 3);
		var fid = formatString.substring(0, formatString.indexOf("}"));
		var fieldObj = F(fid);
		if(fieldObj != null)
		{
			finalString += fieldObj.G();
			formatString = formatString.substring(formatString.indexOf("}") + 1);
		}
		else
		{
			formatString = "#!{" + formatString;
		}
		
	}
	finalString += formatString;
	
	while(finalString.indexOf("#!{") > -1)
	{
		finalString = finalString.replace("#!{", "@!{");
	}
	
	return finalString;
}

/**
 * 处理人字符串操作类
 * 版本：V1.0
 * @作者：李昊原
 * @创建时间：2013年4月7日
 * @修改记录：
*/
function AssignStrModel()
{
	/**
	 * 处理人类型，U（用户），G（组），R（角色细分）。默认为U（用户）
	 */
	this.actorType = "U";
	/**
	 * 登录名，组id，角色细分id
	 */
	this.actorID = "";
	/**
	 * 动作类型，固定环节为NEXT，其他的为ASSIGN或其他
	 */
	this.actionCode = "";
	/**
	 * 组处理模式，[数字型属性]，1（共享）,2（独占）,3（管理者管理）。默认为2（独占）
	 */
	this.dealType = 2;
	/**
	 * 受理时限，[数字型属性]，10位秒格式。默认为0
	 */
	this.acceptOutTime = 0;
	/**
	 * 派发时限，[数字型属性]，10位秒格式。默认为0
	 */
	this.assignOutTime = 0;
	/**
	 * 处理时限，[数字型属性]，10位秒格式。默认为0
	 */
	this.dealOutTime = 0;
	/**
	 * 驳回、追回等回退类动作需要指定的stepId
	 */
	this.rollbackStepID = "";
	/**
	 * 环节号，只有固定流程并行分支时才需指定，其余情况可以空着或为”dp0”
	 */
	this.targetPhaseNo = "";
	/**
	 * 子流程版本号，派发到多个固定子流程时需要指定具体的版本号，派发到单个固定子流程可以为空
	 */
	this.subflowVersionID = "";
	/**
	 * 派发说明
	 */
	this.desc = "";
}
AssignStrModel.buildAssignStrModel = function(assignStr)
{
	if(assignStr == null || assignStr == "") return null;
	var str = assignStr.substr(0, assignStr.length - 2);
	
	var strArr = str.split("#:");
	if(strArr.length != 11) return null;
	
	var model = new AssignStrModel();
	
	model.actorType = strArr[0];
	model.actorID = strArr[1];
	model.actionCode = strArr[2];
	model.dealType = strArr[3];
	model.acceptOutTime = strArr[4];
	model.assignOutTime = strArr[5];
	model.dealOutTime = strArr[6];
	model.rollbackStepID = strArr[7];
	model.targetPhaseNo = strArr[8];
	model.subflowVersionID = strArr[9];
	model.desc = strArr[10];
	
	return model;
}
AssignStrModel.prototype =
{
	getAssignString : function()
	{
		var asStr = this.actorType+"#:"+this.actorID+"#:"+this.actionCode+"#:"+this.dealType+"#:"+this.acceptOutTime+"#:"+this.assignOutTime+"#:"+this.dealOutTime+"#:"+this.rollbackStepID+"#:"+this.targetPhaseNo+"#:"+this.subflowVersionID+"#:"+this.desc+"#;";
		return asStr;
	}
}




//流程引擎存储的流程类型是1和0，流程图用到的是tpl和free，为了保持兼容性在此转换
function transFlowType(flowType) {
	if(flowType == '1') {
		return 'tpl';
	} else {
		return 'free';
	}
}

//设置iframe的url地址
function setIframeUrl(iframe_id,url){
 	$("#" + iframe_id).attr("src",url); 
}

//打开流程图
function openFlowMap(baseId, baseSchema, baseWorkflowFlag, tplId, entryId) {
	window.open($ctx +'/workflow/design/v4/WFDesigner.jsp?mode=map&baseid='+baseId+'&baseschema='+baseSchema+'&type='+transFlowType(baseWorkflowFlag)+'&entryid='+entryId+'&tplid='+tplId+'&modeltype=EOMS');
}

//工单浏览
function sheetView(baseId, baseSchema,  tplId) {
	window.open($ctx + '/workflow/sheet/query/BaseInfoViewBpp.jsp?baseschema='+baseSchema+'&baseid='+baseId+'&flagnewwindow=1&tplid='+tplId);
}

 
String.prototype.getBytes = function(){
	var cArr = this.match(/[^\x00-\xff]/ig);
	return this.length + (cArr ==null?0:cArr.length*2);
}
