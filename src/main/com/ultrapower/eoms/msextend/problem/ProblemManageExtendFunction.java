package com.ultrapower.eoms.msextend.problem;

import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.addWfRecord;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.getAttribute;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.getFieldValue;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.isNew;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.leaveActions;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.removeAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import  com.ultrapower.eoms.msextend.problem.model.ProblemConstant;
import com.ultrapower.eoms.msextend.sla.SlaExtUtil;
import com.ultrapower.eoms.msextend.workflow.WorkFLowUtil;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import com.ultrapower.workflow.engine.task.model.CurrentProcessTask;

public class ProblemManageExtendFunction implements ExtendFunction{


	@Override
	public void beginOpen(WorksheetDisplayContext displayCxt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endOpen(WorksheetDisplayContext displayCxt) {
		String baseStatus = getFieldValue(displayCxt, "BASESTATUS");
		// TODO Auto-generated method stub
		changeNewActions(displayCxt);
		changeCloseActions(displayCxt,baseStatus);
	
	}

	
	@Override
	public void commitPhase1(WorksheetCommitContext commitCxt) {
		commitCxt=SlaExtUtil.slaTimeDealLogic(commitCxt);
		changeAcceptTime(commitCxt,Long.toString(TimeUtils.getCurrentTime()));
		setDealTeam(commitCxt);
		
	}

	@Override
	public void commitPhase2(WorksheetCommitContext commitCxt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commitPhase3(WorksheetCommitContext commitCxt) {
		String  actionType = commitCxt.getActionType();
		String actionName = commitCxt.getActionID();
		if(actionName.equals("ToFinish02")||actionType.equals("ACCEPT")
				||actionType.equals("CANCEL")
				||actionType.equals("SAVE")){
			commitCxt.setReturnMsg("");
			return;
		}
		seetRecord(commitCxt);
//		setStatus(commitCxt);
	/*	
		String assignString= commitCxt.getAttributeMap().get("EVENT_MAKECOPY");
		if(!"".equals(assignString)&&null!=assignString){
			if("TurnSend".equals(commitCxt.getActionID())){
			   BaseAction action = new BaseAction();
			   String baseID = commitCxt.getBaseID();
			   String taskID = "";
			      String baseSchema = commitCxt.getBaseSchema();
			      List<BaseTask> taskList = commitCxt.getNewTasks();
			      for(BaseTask task:taskList){
			       CurrentProcessTask ctask = (CurrentProcessTask)task;
			       taskID = ctask.getId();
			       break;
			      }
			      String loginName = commitCxt.getUserInfo().getLoginName();
			      String actionID = "MAKECOPY";
			      String actionType = "MAKECOPY";
			      String actionText = "问题抄送";
			      String desc = "";
			      Map parameterMap = new HashMap();
			      parameterMap.putAll(commitCxt.getAttributeMap());
			      action.doAction(baseID, baseSchema, taskID, loginName, actionID, actionType, actionText, assignString, desc, parameterMap, null);
			}
		}*/
		
	}

	/**<pre>
	 * 改变受理时间
	 </pre>*/
	private void changeAcceptTime(WorksheetCommitContext commitCxt,String time) {
		String actionName = commitCxt.getActionID();
		//处理环节,受理时,记录受理时间,设置受理人
		if(actionName.equals(ProblemConstant.TO_ACCEPT_ACCEPT)){
			commitCxt.getFieldMap().put("acceptTime",time);
			commitCxt.getFieldMap().put("Processors",commitCxt.getUserInfo().getLoginName());
			commitCxt.getFieldMap().put("ProcessorsName",commitCxt.getUserInfo().getFullName());
			return;
		}
		if(actionName.equals(ProblemConstant.TOBACK)){
			commitCxt.getFieldMap().put("acceptTime","");
			return;
		}
	}
	
	
	/**
	 * 记录各个处理过程
	 */
	private void seetRecord(WorksheetCommitContext commitCxt){
		String actionType = commitCxt.getActionType();
		String actionId = commitCxt.getActionID();
		if(!(actionType.equals("SAVE") || actionType.equals("ACCEPT"))){
			String mailContent=""; //邮件内容
			mailContent = WorkFLowUtil.getMailContentByType("CBD_PROBLEM_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify","ProSuggest",mailContent,"");
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
			leaveActions(displayCxt,ProblemConstant.SAVE);
	}
	
	/**
	 * 没有受理只显示受理按钮,点击受理后隐藏受理按钮
	 */

	public void changeCloseActions(WorksheetDisplayContext displayCxt,
			String baseStatus) {
		if (!baseStatus.equals(ProblemConstant.BASE_STATUS_ACCEPT))
			return;
		String s=displayCxt.getFieldMap().get("acceptTime");
		if (StringUtils.isEmpty(s)) {
			leaveActions(displayCxt, ProblemConstant.TO_ACCEPT_ACCEPT);
		} else {
			removeAction(displayCxt, ProblemConstant.TO_ACCEPT_ACCEPT);

		}
	}
	
	/**
	 * 1.分派环节点击完成按钮记录分派人的团队,退回按钮清空处理人团队
	 * 2.点击受理时，增加处理人团队和处理人团队Id，退回按钮清空处理人团队 
	 */
	private void setDealTeam(WorksheetCommitContext commitCxt) {
		UserSession userinfo = commitCxt.getUserInfo();
		String cdbDealTeamName = userinfo.getCdbTeamName();
		String cdbDealTeamId = userinfo.getCdbTeamId();
		if("ToVerify".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType())||"ToAccept".equals(commitCxt.getActionID())){
			if(cdbDealTeamName==null){
				cdbDealTeamName="";
			}
			if(cdbDealTeamId==null) {
				cdbDealTeamId="";
			}
			commitCxt.getFieldMap().put("cdbDealTeamName", cdbDealTeamName);
			commitCxt.getFieldMap().put("cdbDealTeamId", cdbDealTeamId);
			return;
		}
		if("ToBack".equals(commitCxt.getActionID())&&"NEXT".equals(commitCxt.getActionType())||"ToBack01".equals(commitCxt.getActionID())){
			commitCxt.getFieldMap().put("cdbDealTeamName", "");
			commitCxt.getFieldMap().put("cdbDealTeamId", "");
			return;
		}
	}
	
}
