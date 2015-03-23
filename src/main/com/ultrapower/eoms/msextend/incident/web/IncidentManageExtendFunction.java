package com.ultrapower.eoms.msextend.incident.web;

import org.apache.commons.lang3.StringUtils;
import org.h2.util.MathUtils;
import org.springframework.util.Assert;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;

import static com.ultrapower.eoms.msextend.incident.model.IncidentConstant.*;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.addWfRecord;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.getFieldValue;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.isNew;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.leaveActions;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.removeAction;

import com.ultrapower.eoms.msextend.sla.SlaExtUtil;
import com.ultrapower.eoms.msextend.util.DataValidateUtil;
import com.ultrapower.eoms.msextend.workflow.WorkFLowUtil;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;

public class IncidentManageExtendFunction implements ExtendFunction {

	public void beginOpen(WorksheetDisplayContext displayCxt) {

	}

	public void endOpen(WorksheetDisplayContext displayCxt) {
		String baseStatus = getFieldValue(displayCxt, "BASESTATUS");
		changeNewActions(displayCxt);
		changeAcceptActions(displayCxt, baseStatus);
		changeCloseActions(displayCxt, baseStatus);
		displayCxt.getFreeActionList().clear();// 移除所有自由按钮
	}

	/**
	 * 新建环节
	 * @param displayCxt
	 */
	private void changeNewActions(WorksheetDisplayContext displayCxt) {
		if (isNew(displayCxt))
			leaveActions(displayCxt, SAVE);
	}
	
	/**
	 * 工单提交时所伴随产生的动作,主要是针对一些后台规则的控制
	 * @param commitCxt
	 */
	private void actionSubmit(WorksheetCommitContext commitCxt){
	  String actionName = commitCxt.getActionID();
      String actionType = commitCxt.getActionType();
      if("ASSIGNStep01".equals(actionName)&&"NEXT".equals(actionType))
	  saveSubmitTime(commitCxt);
	}

	/**
	 * 处理环节,点击受理后隐藏受理按钮
	 */
	private void changeAcceptActions(WorksheetDisplayContext displayCxt,
			String baseStatus) {
		if (!baseStatus.equals(BASE_STATUS_PROCESS))
			return;
		ActionModel actionModel = WorkFLowUtil.getAction(displayCxt, "CHASE");
		if (StringUtils.isEmpty(getFieldValue(displayCxt, "acceptTime"))) {
			leaveActions(displayCxt, TO_ACCEPT_PROCESS);
		} else {
			removeAction(displayCxt, TO_ACCEPT_PROCESS);

		}
		if(actionModel!=null){
			displayCxt.getFixActionList().add(actionModel);
		}
	}

	/**
	 * 关单回顾环节,点击受理后隐藏受理按钮
	 */

	public void changeCloseActions(WorksheetDisplayContext displayCxt,
			String baseStatus) {
		if (!baseStatus.equals(BASE_STATUS_CLOSE))
			return;
		if (StringUtils.isEmpty(getFieldValue(displayCxt, "closeUserAcceptTime"))) {
			leaveActions(displayCxt, TO_ACCEPT_REVIEW);
		} else {
			removeAction(displayCxt, TO_ACCEPT_REVIEW);
		}
	}
	
	/**
	 * 增加受理时间和日志记录
	 */
	public void commitPhase1(WorksheetCommitContext commitCxt) {
		String mailContent=""; //邮件内容
		String requstorId = commitCxt.getFieldMap().get(WorkFLowUtil.getRequstorId(commitCxt.getBaseSchema()));//不同流程请求人ID
		commitCxt=SlaExtUtil.slaTimeDealLogic(commitCxt);
		String actionType = commitCxt.getActionType();
		
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
		
		if(!(actionType.equals("SAVE") || actionType.equals("ACCEPT"))){
			mailContent = WorkFLowUtil.getMailContentByType("CBD_INCIDENT_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify","Description",mailContent,"");
		}
		
		//如果是在是建单环节提交,判断申请人和建单人是否同一个人,如果不是同一个人发送邮件进行提醒
		if(TO_ASSIGN.equals(commitCxt.getActionID())&&actionType.equals("NEXT")&&!requstorId.equals(commitCxt.getUserInfo().getLoginName())){
			mailContent =WorkFLowUtil.getMailContentByType("CBD_INCIDENT_CREATE_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify","Description",mailContent,"createToRequestor");
		}
		
		setDealTeam(commitCxt);
		
		/**
		 * 如果是关单环节提交,判断申请人和建单人是否同一个人,如果不是同一个人发送邮件调查,先发给申请人,然后发给建单人,
		 * 当申请人的邮箱为空或者不合法的时候,发给建单人,
		 * 当建单人的邮箱为空或者不合法时,满意度调查发给helpdesk@cdb.com.cn,
		 * 在流程记录中记录此操作
		 */
		
		 if(TO_CLOSE.equals(commitCxt.getActionID())&&actionType.equals("NEXT")&&!requstorId.equals(commitCxt.getUserInfo().getLoginName())){
			 
			 mailContent =WorkFLowUtil.getMailContentByType("CBD_INCIDENT_CLOSE_RECORDCONTENT");
			 //当申请人的邮箱和建单人邮箱验证无误,先发送邮件给申请人,然后建单人
			 String requestorEmailStr = WorkFLowUtil.getEmailByLoginName(requstorId);
			 if(DataValidateUtil.isEmail(requestorEmailStr)==true){
				 addWfRecord(commitCxt, 1, "notify","Description",mailContent,"finishToRequestor");
			 }
			 //如果建单人的邮箱验证无误,发送邮件给建单人
			 String creatorEmailStr = WorkFLowUtil.getEmailByLoginName(commitCxt.getUserInfo().getLoginName());
			 if(DataValidateUtil.isEmail(requestorEmailStr)==false&&DataValidateUtil.isEmail(creatorEmailStr)==true){
				 addWfRecord(commitCxt, 1, "notify","Description",mailContent,"finishToCreator");
			 }
			 //如果申请人和建单人邮箱有误,发送给服务台
			 if(DataValidateUtil.isEmail(creatorEmailStr)==false&&DataValidateUtil.isEmail(requestorEmailStr)==false){
				 addWfRecord(commitCxt, 1, "notify","Description",mailContent,"finishToHelpDesk");
			 }
		 }
		
		changeAcceptTime(commitCxt, Long.toString(TimeUtils.getCurrentTime()));
		
		//事件工单/服务请求工单要求服务实施时间不填写的时候默认当前时间
		 if("ASSIGNStep01".equals(commitCxt.getActionID())&&actionType.equals("NEXT")){
			 String requireDealTime = commitCxt.getFieldMap().get("requireDealTime");
			 if("".equals(requireDealTime)){
				 commitCxt.getFieldMap().put("requireDealTime",TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			 }
		 }
	}

	/**
	 * 改变受理时间,受理人
	 * 处理环节,受理时,记录处理人受理时间
	 * 处理环节,退回时,清空处理人受理时间
	 * 关单环节,受理时,记录关单人(提交请求人)受理时间
	 * 关单环节,退回时,清空关单人受理时间
	 * 处理环节,转派时，清空受理时间
	 * 关单环节,转派时,清空受理时间
	 */
	private void changeAcceptTime(WorksheetCommitContext commitCxt, String time) {
		String actionName = commitCxt.getActionID();
		if (actionName.equals(TO_ACCEPT_PROCESS)) {
			commitCxt.getFieldMap().put("acceptTime", time);
			return;
		}
		if (actionName.equals(TO_BACK_PROCESS)||actionName.equals(TO_RENEXT_PROCESS)) {
			commitCxt.getFieldMap().put("acceptTime", "");
			return;
		}
		
		if (actionName.equals(TO_ACCEPT_REVIEW)) {
			commitCxt.getFieldMap().put("closeUserAcceptTime", time);
			return;
		}
		if (actionName.equals(TO_BACK_REVIEW)||actionName.equals(TO_RENEXT_REVIEW)) {
			commitCxt.getFieldMap().put("closeUserAcceptTime", "");
			return;
		}
	}

	public void commitPhase2(WorksheetCommitContext commitCxt) {
	    actionSubmit(commitCxt);
	}

	/**
	 * 提交和受理环节取消弹出提示
	 */
	public void commitPhase3(WorksheetCommitContext commitCxt) {
		String actionName = commitCxt.getActionID();
		if (actionName.equals(SAVE) || actionName.equals(TO_ACCEPT_PROCESS)|| actionName.equals(TO_ACCEPT_REVIEW)) {
			commitCxt.setReturnMsg("");
			return;
		}
	}
	/**
     * 记录提交时间(一线派单时间)
     */
    private void saveSubmitTime(WorksheetCommitContext commitCxt){
        commitCxt.getFieldMap().put("submit_time", Long.toString(TimeUtils.getCurrentTime()));
    }
	
	/**
	 *  点击处理完成时，或直接关单增加处理人团队和处理人团队Id 
	 */
	private void setDealTeam(WorksheetCommitContext commitCxt) {
		UserSession userinfo = commitCxt.getUserInfo();
		String cdbDealTeamName = userinfo.getCdbTeamName();
		String cdbDealTeamId = userinfo.getCdbTeamId();
		if(("ToFinishProcessing".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType())) || ("ToFinishStep01".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType()))){
			commitCxt.getFieldMap().put("cdbDealTeamName", cdbDealTeamName);
			commitCxt.getFieldMap().put("cdbDealTeamId", cdbDealTeamId);
			return;
		}
	}

	//根据事件紧急度和影响度取得优先级
	public static String getPriority(String urgentDgree,String effectDgree){
		Assert.notNull(urgentDgree,"紧急度不能为空");
		Assert.notNull(effectDgree,"影响度不能为空");
		if(urgentDgree.equals("3") && effectDgree.equals("3")){
			return "4";
		}else{
			return Integer.valueOf(Math.max(Integer.parseInt(urgentDgree), Integer.parseInt(effectDgree))).toString();
		}
	}

}
