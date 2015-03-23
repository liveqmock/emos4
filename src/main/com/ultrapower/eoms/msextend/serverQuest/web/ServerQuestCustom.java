package com.ultrapower.eoms.msextend.serverQuest.web;

import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.BASE_STATUS_ACCEPT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.BASE_STATUS_AUDIT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.BASE_STATUS_CLOSE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.BASE_STATUS_DEAL;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.BASE_STATUS_SENDAUDIT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.CURRENT_AUDIT_SEQUENCE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.HAS_ACCEPT_PERSON;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.SAVE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_ACCEPT_ACCEPT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_ACCEPT_AUDIT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_ACCEPT_CLOSE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_ACCEPT_DEAL;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_AUDIT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_BACK_FROM_CLOSE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_BACK_TO_ACCEPT_FROM_DEAL;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_BACK_TO_REQUEST;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_DEALFINISH_FROM_ACCEPT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_DEALFINISH_FROM_DEAL;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_DEAL_FROM_ACCEPT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_NOPASS;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_PASS;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_PASS_REQUEST_AUDIT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_RENEXT_TO_ACCEPT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_RENEXT_TO_CLOSE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_RENEXT_TO_DEAL;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_REQUEST;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_SAVE_ACCEPT;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_SAVE_DEAL;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_SEND_AUDIT;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.addWfRecord;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.getAttribute;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.getFieldValue;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.isNew;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.leaveActions;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.removeAction;

import org.apache.commons.lang3.StringUtils;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.msextend.serverQuest.service.ServerQuestWorkFlowService;
import com.ultrapower.eoms.msextend.sla.SlaExtUtil;
import com.ultrapower.eoms.msextend.util.DataValidateUtil;
import com.ultrapower.eoms.msextend.workflow.WorkFLowUtil;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;

/**
 * 服务请求客户化类
 * @author hhy
 *
 */
public class ServerQuestCustom implements ExtendFunction {
	ServerQuestWorkFlowService flowService = (ServerQuestWorkFlowService) WebApplicationManager.getBean("serverQuestWorkFlow");
	public void beginOpen(WorksheetDisplayContext displayCxt) {
		
	}
	
	public void endOpen(WorksheetDisplayContext displayCxt) {
		String baseStatus = getFieldValue(displayCxt, "BASESTATUS");
		changeNewActions(displayCxt);
		changeSendAuditActions(displayCxt,baseStatus);
		changeAcceptActions(displayCxt,baseStatus);
		changeDealActions(displayCxt,baseStatus);
		changeAuditActions(displayCxt,baseStatus);
		changeCloseActions(displayCxt,baseStatus);
		displayCxt.getFreeActionList().clear();//移除所有自由按钮
	}
	
	//改变送审环节按钮
	private void changeSendAuditActions(WorksheetDisplayContext displayCxt, String baseStatus) {
		if(!baseStatus.equals(BASE_STATUS_SENDAUDIT))
			return;
		ActionModel actionModel = WorkFLowUtil.getAction(displayCxt, "CHASE");//在改变按钮之前取到追回按钮
		if(actionModel!=null){//追回按钮不为null，则显示追回
			displayCxt.getFixActionList().add(actionModel);
		}
	}

	/**<pre>
	 * 新建环节
	 * 未保存
	 *   只显示"保存"按钮
	 * 以保存
	 *   添加"转派"按钮
	 </pre>*/
	private void changeNewActions(WorksheetDisplayContext displayCxt){
		if(isNew(displayCxt))
			leaveActions(displayCxt,SAVE);
	}
	
	/**<pre>
	 * 受理环节
	 * 未受理
	 *   显示"受理","退回"按钮<hr>
	 * 已受理
	 *   隐藏"受理"按钮
	 *   没有下一步审批人,隐藏"提交处理","处理完成"按钮
	</pre>*/
	private void changeAcceptActions(WorksheetDisplayContext displayCxt,String baseStatus){
		if(!baseStatus.equals(BASE_STATUS_ACCEPT))
			return;
		ActionModel actionModel = WorkFLowUtil.getAction(displayCxt, "CHASE");//在改变按钮之前取到追回按钮
		
		if(StringUtils.isEmpty(getFieldValue(displayCxt, "acceptTime"))){
			leaveActions(displayCxt, TO_ACCEPT_ACCEPT);
		}else{
			removeAction(displayCxt, TO_ACCEPT_ACCEPT);
			if(hasNextAssign(getFieldValue(displayCxt, "service_category"), getAttribute(displayCxt,CURRENT_AUDIT_SEQUENCE)))
				removeAction(displayCxt, TO_DEAL_FROM_ACCEPT, TO_DEALFINISH_FROM_ACCEPT);
		}
		
		if(actionModel!=null){//追回按钮不为null，则显示追回
			displayCxt.getFixActionList().add(actionModel);
		}
	}
	
	/**<pre>
	 * 处理环节
	 * 未受理
	 *   显示"受理","退回按钮"<hr>
	 * 已受理
	 *   隐藏"受理"按钮
	 </pre>*/
	private void changeDealActions(WorksheetDisplayContext displayCxt,String baseStatus){
		if(!baseStatus.equals(BASE_STATUS_DEAL))
			return;
		ActionModel actionModel = WorkFLowUtil.getAction(displayCxt, "CHASE");//在改变按钮之前取到追回按钮
		if(StringUtils.isEmpty(getFieldValue(displayCxt, "dealUserAcceptTime"))){
			leaveActions(displayCxt, TO_ACCEPT_DEAL);
		}else{
			removeAction(displayCxt, TO_ACCEPT_DEAL);
		}
		if(actionModel!=null){//追回按钮不为null，则显示追回
			displayCxt.getFixActionList().add(actionModel);
		}
	}
	
	/**<pre>
	 * 审批环节
	 * 未受理
	 *   显示"受理"<hr>
	 * 已受理
	 *   隐藏"受理"按钮
	 </pre>*/
	private void changeAuditActions(WorksheetDisplayContext displayCxt,String baseStatus){
		if(!baseStatus.equals(BASE_STATUS_AUDIT))
			return;
//		if(StringUtils.isEmpty(getFieldValue(displayCxt, "audit_accept_time"))){
//			leaveActions(displayCxt, TO_ACCEPT_AUDIT);
//		}else{
			removeAction(displayCxt, TO_ACCEPT_AUDIT);
//		}
	}
	
	/**<pre>
	 * 关单环节
	 * 未受理
	 *   显示"受理","退回按钮"<hr>
	 * 已受理
	 *   隐藏"受理"按钮
	 </pre>*/
	private void changeCloseActions(WorksheetDisplayContext displayCxt,String baseStatus){
		if(!baseStatus.equals(BASE_STATUS_CLOSE))
			return;
		if(StringUtils.isEmpty(getFieldValue(displayCxt, "closeUserAcceptTime"))){
			leaveActions(displayCxt, TO_ACCEPT_CLOSE);
		}else{
			removeAction(displayCxt, TO_ACCEPT_CLOSE);
		}
	}
	
//	/**
//	 * 设置客户端是否有下一审批人字段
//	 * @param displayCxt
//	 */
//	private void setClientAttrHasNextAudit(WorksheetDisplayContext displayCxt) {
//		Boolean hasNextAssign = hasNextAssign(displayCxt);
//		displayCxt.getAttributeMap().put("has_next_audit", hasNextAssign.toString());
//	}

	public void commitPhase1(WorksheetCommitContext commitCxt) {
		selfHelpCommit(commitCxt);
		commitCxt=SlaExtUtil.slaTimeDealLogic(commitCxt);
		//响应时间和受理时限格式转换
		String BASEACCEPTOUTTIME=commitCxt.getFieldMap().get("BASEACCEPTOUTTIME");
		String BASEDEALOUTTIME=commitCxt.getFieldMap().get("BASEDEALOUTTIME");
		if(BASEACCEPTOUTTIME!=null&&!BASEACCEPTOUTTIME.equals("")&&BASEACCEPTOUTTIME.length()!=10){
			BASEACCEPTOUTTIME=Long.toString(TimeUtils.formatDateStringToInt(BASEACCEPTOUTTIME));
			 commitCxt.getFieldMap().put("BASEACCEPTOUTTIME", BASEACCEPTOUTTIME);
		}
		if(BASEDEALOUTTIME!=null&&!BASEDEALOUTTIME.equals("")&&BASEDEALOUTTIME.length()!=10){
			BASEDEALOUTTIME=Long.toString(TimeUtils.formatDateStringToInt(BASEDEALOUTTIME));
			 commitCxt.getFieldMap().put("BASEDEALOUTTIME", BASEDEALOUTTIME);
		}
		actionSubmit(commitCxt);
		changeAcceptTime(commitCxt,Long.toString(TimeUtils.getCurrentTime()));
		//点击处理完成时，增加处理人团队和处理人团队Id 
		setDealTeam(commitCxt);
//		submitRequest(commitCxt);
		//事件工单/服务请求工单要求服务实施时间不填写的时候默认当前时间
		 if(("ToSendAudit".equals(commitCxt.getActionID())&&commitCxt.getActionType().equals("NEXT"))||("ToRequest".equals(commitCxt.getActionID())&&commitCxt.getActionType().equals("NEXT"))){
			 String requireDealTime = commitCxt.getFieldMap().get("requireDealTime");
			 if("".equals(requireDealTime)){
				 commitCxt.getFieldMap().put("requireDealTime",TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			 }
		 }
	}
	
	//自助服务的逻辑
	private void selfHelpCommit(WorksheetCommitContext commitCxt){
//		if(!StringUtils.equals(getFieldValue(commitCxt, "isSelfHelp"), "1")){
//			return;
//		}
//		String baseStatus = getFieldValue(commitCxt, "BASESTATUS");
//		if(baseStatus==null){//未保存直接提交
//			new BaseAction().doAction(null, "CDB_SERVICEREQUEST", 
//					null, commitCxt.getUserInfo().getLoginName(), 
//					TO_REQUEST, "SAVE", "提交请求", "", "", commitCxt.getFieldMap(), null);	
//		}
	}
	
	/**
	 * 修改当前受理,审批人的所在位置,全局变量current_audit_sequence
	 */
	private void actionSubmit(WorksheetCommitContext commitCxt){
		String dealInfoFiledName = "dealInfo";//处理信息的字段名
		String mailContent=""; //邮件内容
		String requstorId = commitCxt.getFieldMap().get(WorkFLowUtil.getRequstorId(commitCxt.getBaseSchema()));//不同流程请求人ID
		String actionName = commitCxt.getActionID();
		String actionType = commitCxt.getActionType();
		String baseStatus = getFieldValue(commitCxt, "BASESTATUS");
		if(actionName.equals(TO_AUDIT)){
			dealInfoFiledName = "audit_info";
		}
		if(!(actionType.equals("SAVE") || actionType.equals("ACCEPT"))){
			mailContent = WorkFLowUtil.getMailContentByType("CDB_SERVICEREQUEST_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify",dealInfoFiledName,mailContent,"");
		}
		
		//如果是在是建单环节提交,判断申请人和建单人是否是同一个人,如果不是同一个人发送邮件进行提醒
		if(TO_REQUEST.equals(commitCxt.getActionID())&&actionType.equals("NEXT")&&!requstorId.equals(commitCxt.getUserInfo().getLoginName())){
			mailContent =WorkFLowUtil.getMailContentByType("CDB_SERVICEREQUEST_CREATE_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify",dealInfoFiledName,mailContent,"createToRequestor");
		}
		
		/**
		 * 如果是关单环节提交,判断申请人和建单人是否同一个人,如果不是同一个人发送邮件调查,先发给申请人,然后发给建单人,
		 * 当申请人的邮箱为空或者不合法的时候,发给建单人,
		 * 当建单人的邮箱为空或者不合法时,满意度调查发给helpdesk@cdb.com.cn,
		 * 在流程记录中记录此操作
		 */
		
		 if("ToCloseFromDeal".equals(commitCxt.getActionID())&&actionType.equals("NEXT")&&!requstorId.equals(commitCxt.getUserInfo().getLoginName())){
			 
			 mailContent =WorkFLowUtil.getMailContentByType("CDB_SERVICEREQUEST_CLOSE_RECORDCONTENT");
			 //当申请人的邮箱和建单人邮箱验证无误,先发送邮件给申请人,然后建单人
			 String requestorEmailStr = WorkFLowUtil.getEmailByLoginName(requstorId);
			 if(DataValidateUtil.isEmail(requestorEmailStr)==true){
				 addWfRecord(commitCxt, 1, "notify",dealInfoFiledName,mailContent,"finishToRequestor");
			 }
			 //如果建单人的邮箱验证无误,发送邮件给建单人
			 String creatorEmailStr = WorkFLowUtil.getEmailByLoginName(commitCxt.getUserInfo().getLoginName());
			 if(DataValidateUtil.isEmail(creatorEmailStr)==true){
				 addWfRecord(commitCxt, 1, "notify",dealInfoFiledName,mailContent,"finishToCreator");
			 }
			 //如果申请人和建单人邮箱有误,发送给服务台
			 if(DataValidateUtil.isEmail(creatorEmailStr)==false&&DataValidateUtil.isEmail(requestorEmailStr)==false){
				 addWfRecord(commitCxt, 1, "notify",dealInfoFiledName,mailContent,"finishToHelpDesk");
			 }
		 }
		
		String currentAuditSequence = getAttribute(commitCxt, CURRENT_AUDIT_SEQUENCE);
		if(StringUtils.isEmpty(currentAuditSequence)) currentAuditSequence = "0";
		
		//追回到新建,记录下一审批人位置0
		if(StringUtils.isNotEmpty(baseStatus) && baseStatus.equals(BASE_STATUS_ACCEPT) && actionName.equals("CHASE")){
			commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "0");
			return;
		}
		
		//新建环节,提交请求时,记录受理人位置1,记录提交时间
		if(actionName.equals(TO_REQUEST)){
			saveSubmitTime(commitCxt);
			saveSubmitRequestPerson(commitCxt);
			return;
		}
		
		//新建环节，送审，记录提交请求人和名字
		if(actionName.equals(TO_SEND_AUDIT)){
			saveSubmitRequestPerson(commitCxt);
			return;
		}
		
		//申请审批环节，通过，审批升级动作改为转派，否则记录受理人位置1，记录通过（提交）时间
		if(actionName.equals(TO_PASS_REQUEST_AUDIT)){
			if(StringUtils.isNotEmpty(getFieldValue(commitCxt, "is_audit_level_up"))){
				commitCxt.setActionType("RENEXT");
				return;
			}
			saveSubmitTime(commitCxt);
			return;
		}
		
		//受理环节,提交审批时,记录下一审批人位置2,记录提交审批人
		if(actionName.equals(TO_AUDIT)){
			commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "2");
			return;
		}
		
		//受理环节,退回时,记录下一审批人位置0
		if(actionName.equals(TO_BACK_TO_REQUEST)){
			commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "0");
			return;
		}
		
		//受理环节,提交处理,指定处理人
		if(actionName.equals(TO_DEAL_FROM_ACCEPT)){
//			commitCxt.setAssignString(String.format("U#:%s#:NEXT#:2#:0#:0#:0#:#:#:#:#;", dealPersonId));
			return;
		}
		
		//受理环节,处理完成,设置关单人为提交请求人,记录处理完成时间,记录处理人
		if(actionName.equals(TO_DEALFINISH_FROM_ACCEPT)){
			commitCxt.getFieldMap().put("deal_finish_time", Long.toString(TimeUtils.getCurrentTime()));
			commitCxt.getFieldMap().put("dealUser", commitCxt.getUserInfo().getFullName());
			commitCxt.getFieldMap().put("dealuserloginname", commitCxt.getUserInfo().getLoginName());
			commitCxt.setAssignString(String.format("U#:%s#:NEXT#:2#:0#:0#:0#:#:#:#:#;", getFieldValue(commitCxt, "submit_request_person")));
			return;
		}
		
		//审批环节,通过时,如果assignString不为空(审批升级选择了派发树),将动作改为转派;否则记录下一审批人位置current_audit_sequence++;
		if(actionName.equals(TO_PASS)){
			if(StringUtils.isNotEmpty(getFieldValue(commitCxt, "is_audit_level_up"))){
				commitCxt.setActionType("RENEXT");
				return;
			}
			Integer auditSequence = Integer.parseInt(currentAuditSequence)+1;
			commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, auditSequence.toString());
			if(hasNextAssign(getFieldValue(commitCxt, "service_category"),currentAuditSequence))//有下一级审批人则继续审批环节
				commitCxt.setActionType("RENEXT");
			return;
		}
		
		//审批环节,不通过时,记录下一审批人位置1
		if(actionName.equals(TO_NOPASS)){
			commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "1");
			return;
		}
		
		//处理环节,退回时
		if(actionName.equals(TO_BACK_TO_ACCEPT_FROM_DEAL)){
			//有受理人,记录下一审批人位置1;没有受理人,记录下一审批人位置0
			if(commitCxt.getAttributeMap().get(HAS_ACCEPT_PERSON).equals(Boolean.toString(true)))
				commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "1");
			else
				commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "0");
			return;
		}
		
		//处理环节,处理完成时,设置关单人为提交请求人,记录处理完成时间,记录处理人
		if(actionName.equals(TO_DEALFINISH_FROM_DEAL)){
			commitCxt.getFieldMap().put("dealUser", commitCxt.getUserInfo().getFullName());
			commitCxt.getFieldMap().put("dealuserloginname", commitCxt.getUserInfo().getLoginName());
			commitCxt.getFieldMap().put("deal_finish_time", Long.toString(TimeUtils.getCurrentTime()));
			commitCxt.setAssignString(String.format("U#:%s#:NEXT#:2#:0#:0#:0#:#:#:#:#;", getFieldValue(commitCxt, "submit_request_person")));
			return;
		}
	}
	
	/**<pre>
	 * 改变受理时间,受理人
	 </pre>*/
	private void changeAcceptTime(WorksheetCommitContext commitCxt,String time) {
		String actionName = commitCxt.getActionID();
		//受理环节,受理时,记录受理时间和受理人
		if(actionName.equals(TO_ACCEPT_ACCEPT)){
			commitCxt.getFieldMap().put("acceptTime",time);
			commitCxt.getFieldMap().put("acceptUser",commitCxt.getUserInfo().getLoginName());
			return;
		}
		//受理环节,转派,清空受理时间和受理人
		if(actionName.equals(TO_RENEXT_TO_ACCEPT)){
			commitCxt.getFieldMap().put("acceptTime","");
			commitCxt.getFieldMap().put("acceptUser","");
			return;
		}
		//受理环节,退回时,清空受理时间和受理人
		if(actionName.equals(TO_BACK_TO_REQUEST)){
			commitCxt.getFieldMap().put("acceptTime","");
			commitCxt.getFieldMap().put("acceptUser","");
			return;
		}
		//处理环节,受理时,记录处理人受理时间
		if(actionName.equals(TO_ACCEPT_DEAL)){
			commitCxt.getFieldMap().put("dealUserAcceptTime",time);
			return;
		}
		//处理环节,转派时,清空处理人受理时间
		if(actionName.equals(TO_RENEXT_TO_DEAL)){
			commitCxt.getFieldMap().put("dealUserAcceptTime","");
			return;
		}
		//处理环节,退回时,清空处理人受理时间,清空受理时间和受理人
		if(actionName.equals(TO_BACK_TO_ACCEPT_FROM_DEAL)){
			commitCxt.getFieldMap().put("dealUserAcceptTime","");
			commitCxt.getFieldMap().put("acceptTime","");
			commitCxt.getFieldMap().put("acceptUser","");
			return;
		}
		//审批环节,受理时,记录审批人受理时间
		if(actionName.equals(TO_ACCEPT_AUDIT)){
			commitCxt.getFieldMap().put("audit_accept_time",time);
			return;
		}
		//审批环节,通过,不通过时,清空审批人受理时间,清空受理人受理时间
		if(actionName.equals(TO_NOPASS)||actionName.equals(TO_PASS)){
			commitCxt.getFieldMap().put("audit_accept_time","");
			commitCxt.getFieldMap().put("acceptTime","");
			return;
		}
		//关单环节,受理时,记录关单人(提交请求人)受理时间
		if(actionName.equals(TO_ACCEPT_CLOSE)){
			commitCxt.getFieldMap().put("closeUserAcceptTime",time);
			return;
		}
		//关单环节,转派,清空关单人受理时间
		if(actionName.equals(TO_RENEXT_TO_CLOSE)){
			commitCxt.getFieldMap().put("closeUserAcceptTime","");
			return;
		}
		//关单环节,退回时,清空关单人受理时间,清空处理人受理时间
		if(actionName.equals(TO_BACK_FROM_CLOSE)){
			commitCxt.getFieldMap().put("closeUserAcceptTime","");
			commitCxt.getFieldMap().put("dealUserAcceptTime","");
			return;
		}
	}
	
	public void commitPhase2(WorksheetCommitContext commitCxt) {
	}
	/**
	 * 保存和受理时不需要提示信息
	 */
	public void commitPhase3(WorksheetCommitContext commitCxt) {
		String actionName = commitCxt.getActionID();
		if(actionName.equals(SAVE)
				||actionName.equals(TO_ACCEPT_ACCEPT)
				||actionName.equals(TO_ACCEPT_DEAL)
				||actionName.equals(TO_ACCEPT_CLOSE)
				||actionName.equals(TO_SAVE_ACCEPT)
				||actionName.equals(TO_SAVE_DEAL)
				||actionName.equals(TO_ACCEPT_AUDIT)){
			commitCxt.setReturnMsg("");
			return;
		}
		//提交请求，并且是自助服务工单，则提示“处理成功”
		if(actionName.equals(TO_REQUEST) && StringUtils.equals(getFieldValue(commitCxt, "isSelfHelp"), "1")){
			commitCxt.setReturnMsg("处理成功");
		}
	}
	
	/**
	 * 是否有下一审批人
	 */
	private Boolean hasNextAssign(String serverQuestFullName,String auditSequence){
		return flowService.hasNextAudit(serverQuestFullName,auditSequence);
	}
	
	/**
	 *记录提交请求人的id和name 
	 */
	private void saveSubmitRequestPerson(WorksheetCommitContext commitCxt){
		commitCxt.getFieldMap().put("submit_request_person", commitCxt.getUserInfo().getLoginName());
		commitCxt.getFieldMap().put("submit_request_person_name", commitCxt.getUserInfo().getFullName());
	}
	
	/**
	 * 记录提交时间，是否有受理人
	 */
	private void saveSubmitTime(WorksheetCommitContext commitCxt){
		commitCxt.getFieldMap().put("submit_time", Long.toString(TimeUtils.getCurrentTime()));
		commitCxt.getAttributeMap().put(CURRENT_AUDIT_SEQUENCE, "1");
		//设置当前服务目录是否有受理人
		commitCxt.getAttributeMap().put(HAS_ACCEPT_PERSON, hasNextAssign(getFieldValue(commitCxt, "service_category"), "0").toString());
	}
	
	/**
	 *  点击处理完成时或直接关单，增加处理人团队和处理人团队Id 
	 */
	private void setDealTeam(WorksheetCommitContext commitCxt) {
		UserSession userinfo = commitCxt.getUserInfo();
		String cdbDealTeamName = userinfo.getCdbTeamName();
		String cdbDealTeamId = userinfo.getCdbTeamId();
		if(("ToDealFinishFromDeal".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType())) || ("ToCloseFromRequest".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType())) || ("ToDealFinishFromAccept".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType()))){
			commitCxt.getFieldMap().put("cdbDealTeamName", cdbDealTeamName);
			commitCxt.getFieldMap().put("cdbDealTeamId", cdbDealTeamId);
			return;
		}
	}

}
