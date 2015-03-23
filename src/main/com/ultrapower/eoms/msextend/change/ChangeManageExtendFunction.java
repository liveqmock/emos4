package com.ultrapower.eoms.msextend.change;

import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.BASE_STATUS_SENDAUDIT;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.addWfRecord;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.leaveActions;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.workflow.WorkFLowUtil;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.eoms.ultrasm.model.ChgBatchInfo;


public class ChangeManageExtendFunction implements ExtendFunction {
	private static final Logger log = LoggerFactory.getLogger(ChangeManageExtendFunction.class);

	public void beginOpen(WorksheetDisplayContext displayCxt) {
		
		
	}
	
	public void endOpen(WorksheetDisplayContext displayCxt)
	{
		//增加受理按钮
		this.setActions(displayCxt);
		changeAuditActions(displayCxt);
		displayCxt.getAttributeMap().put("GROUPID",displayCxt.getUserInfo().getGroupId());
		displayCxt.getAttributeMap().put("NOWTIME",TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		displayCxt.getAttributeMap().put("LOGINNAME",displayCxt.getUserInfo().getLoginName());
		displayCxt.getAttributeMap().put("FULLNAME",displayCxt.getUserInfo().getFullName());
		changeSendAuditActions(displayCxt);
	}
	
	//只保留追回按钮
	private void changeSendAuditActions(WorksheetDisplayContext displayCxt) {
		ActionModel actionModel = WorkFLowUtil.getAction(displayCxt, "CHASE");//在改变按钮之前取到追回按钮
		if(actionModel!=null){//追回按钮不为null，则显示追回
			displayCxt.getFixActionList().add(actionModel);
		}
		displayCxt.getFreeActionList().clear();//移除所有自由按钮
	}

	public void commitPhase1(WorksheetCommitContext commitCxt) {
		//获取受理时间
		setAcceptTimes(commitCxt);
		//获取各环节的处理时间
		setDealTimes(commitCxt);
		//升级的时候，修改处理人的派发动作
		setAuditAgain(commitCxt);
	}

	public void commitPhase2(WorksheetCommitContext commitCxt) {
		// TODO Auto-generated method stub
	}

	public void commitPhase3(WorksheetCommitContext commitCxt) {
		commitCxt.setReturnMsg("");
		seetRecord(commitCxt);
	}
	
	
	
	//升级的时候，修改处理人的派发动作
	private void setAuditAgain(WorksheetCommitContext commitCxt) {
		String status = commitCxt.getFieldMap().get("BASESTATUS");
		String assignStr = commitCxt.getAssignString();
		if(status != null && status.equals("审批中") && assignStr != null && assignStr.indexOf("RENEXT") != -1){
			commitCxt.setActionType("RENEXT");
			commitCxt.setAssignString(commitCxt.getAssignString().replace("auditAgainUser",""));
		}
	}
	
	
	
	
	/**
	 * 每一个环节，先受理再显示其他按钮
	 */
public void setActions(WorksheetDisplayContext displayCxt){
	String status = displayCxt.getFieldMap().get("BASESTATUS");
	List<ActionModel> list = new ArrayList<ActionModel>();
	if(status.equals("草稿") && StringUtils.isBlank(displayCxt.getFieldMap().get("BASESN"))){
		for(ActionModel action:displayCxt.getFixActionList()){
			if(action.getActionName().equals("SAVE")){
				list.add(action);
			}
		}
		displayCxt.getFixActionList().clear();
		displayCxt.getFixActionList().addAll(list);
	}
	
	if(displayCxt.getFixActionList().size() > 0 && displayCxt.getFreeActionList().size() > 0){	
			if(status != null && !status.equals("")){
				if((status.equals("修订中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("reviseAcceptTime")) || displayCxt.getFieldMap().get("reviseAcceptTime").equals("0"))) ||
					(status.equals("方案验证中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("caseAcceptTime")) || displayCxt.getFieldMap().get("caseAcceptTime").equals("0"))) ||
//					(status.equals("审批中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("auditAcceptTime")) || displayCxt.getFieldMap().get("auditAcceptTime").equals("0"))) ||
//					(status.equals("评审中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("organizeAcceptTime")) || displayCxt.getFieldMap().get("organizeAcceptTime").equals("0"))) ||
					(status.equals("实施中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("realAcceptTime")) || displayCxt.getFieldMap().get("realAcceptTime").equals("0"))) ||
					(status.equals("验证中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("testAcceptTime")) || displayCxt.getFieldMap().get("testAcceptTime").equals("0"))) ||
					(status.equals("回顾中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("reviewAcceptTime")) || displayCxt.getFieldMap().get("reviewAcceptTime").equals("0")))
					){
					displayCxt.getFreeActionList().clear();
					displayCxt.getFixActionList().clear();
	
					ActionModel acceptAction = new ActionModel();
					acceptAction.setId("cdb_accept");
					acceptAction.setActionName("ACCEPT");
					acceptAction.setActionType("ACCEPT");
					acceptAction.setLabel("受 理");
					acceptAction.setDescription("");
					acceptAction.setHasNext(0);
					acceptAction.setIsClose(0);
					acceptAction.setIsFree(1);
					displayCxt.getFixActionList().add(acceptAction);
				}else{
					displayCxt.getFreeActionList().clear();
					if(status.equals("审批中")){
						String auditTimes = displayCxt.getFieldMap().get("auditTimes");
						String chgType = displayCxt.getFieldMap().get("chgType");
//						if(!"一般变更".equals(chgType) && StringUtils.isNotBlank(auditTimes) && Integer.parseInt(auditTimes) < 2){
//							for(ActionModel action:displayCxt.getFixActionList()){
//								if(action.getActionName().equals("auditPassed")){
//									action.setActionType("RENEXT");
//								}
//							}
//						}
					}
				}
			}			
		}
	}
	
	/**
	 * 保存每一个环节的受理时间
	 */
	public void setAcceptTimes(WorksheetCommitContext commitCxt){
		String actionId = commitCxt.getActionID();
		if(actionId != null && actionId.equals("ACCEPT")){
			String status = commitCxt.getFieldMap().get("BASESTATUS");
			if(status != null && !status.equals("")){
				if(status.equals("修订中")){
					commitCxt.getFieldMap().put("reviseAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}else if(status.equals("方案验证中")){
					commitCxt.getFieldMap().put("caseAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}else if(status.equals("审批中")){
					commitCxt.getFieldMap().put("auditAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}else if(status.equals("评审中")){
					commitCxt.getFieldMap().put("organizeAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}else if(status.equals("实施中")){
					commitCxt.getFieldMap().put("realAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}else if(status.equals("验证中")){
					commitCxt.getFieldMap().put("testAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}else if(status.equals("回顾中")){
					commitCxt.getFieldMap().put("reviewAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				}
				commitCxt.setReturnMsg("");
			}
			WorkFLowUtil.addWfRecord(commitCxt,0,"","",null,"");
			
			
		}
	}
	
	/**
	 * 保存每一个环节的处理时间
	 */
	public void setDealTimes(WorksheetCommitContext commitCxt){
		String actionId = commitCxt.getActionID();
		if(actionId != null && !actionId.equals("")){
			
			
			if(!actionId.equals("ACCEPT")){
				//清空掉所有的受理时间
				commitCxt.getFieldMap().put("reviseAcceptTime","");
				commitCxt.getFieldMap().put("caseAcceptTime","");
				commitCxt.getFieldMap().put("auditAcceptTime","");
				commitCxt.getFieldMap().put("organizeAcceptTime","");
				commitCxt.getFieldMap().put("realAcceptTime","");
				commitCxt.getFieldMap().put("testAcceptTime","");
				commitCxt.getFieldMap().put("reviewAcceptTime","");
			}
			
			if(actionId.equals("caseCheck")){//修订
				commitCxt.getFieldMap().put("reviseTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			}else if(actionId.equals("check2audit")){//方案验证
				commitCxt.getFieldMap().put("caseCheckTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			}else if(actionId.equals("auditPassed")){//审批通过
				commitCxt.getFieldMap().put("auditTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				commitCxt.getFieldMap().put("auditAcceptTime","");//审批通过后将审批受理时间设空，保证下一个审批人进来之后还是受理按钮
				String auditTimes = commitCxt.getFieldMap().get("auditTimes");
				if(StringUtils.isBlank(auditTimes)){
					auditTimes = "0";
				}
				commitCxt.getFieldMap().put("auditTimes",(Integer.parseInt(auditTimes) + 1) + "");
				commitCxt.setAssignString(commitCxt.getAssignString().replaceAll("auditAgainUser",""));				
			}else if(actionId.equals("auditUnPass")){//审批不通过
				commitCxt.getFieldMap().put("auditTime","");
				commitCxt.getFieldMap().put("auditTimes","0");	
			}else if(actionId.equals("organizeAuditPass")){//评审
				commitCxt.getFieldMap().put("organizeTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			}else if(actionId.equals("finished")){//实施
				commitCxt.getFieldMap().put("BASEFINISHDATE",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
				//commitCxt.getFieldMap().put("realFinishTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			}else if(actionId.equals("checked")){//验证
				commitCxt.getFieldMap().put("testFinishedTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			}else if(actionId.equals("close")){//回顾
				commitCxt.getFieldMap().put("reviewTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			}
//			commitCxt.setReturnMsg("提交成功！");
			
			
		}
	}
	
	/**
	 * 记录各个处理过程
	 */
	public void seetRecord(WorksheetCommitContext commitCxt){
		String actionType = commitCxt.getActionType();
		if(StringUtils.isNotBlank(actionType) && (actionType.equals("NEXT") || actionType.equals("RENEXT"))){
			String mailContent=""; //邮件内容
			mailContent = WorkFLowUtil.getMailContentByType("CDB_CHANGE_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify","dealDesc",mailContent,"");
		}
	}
	
	//审批环节如果最晚参与评审时间小于当前时间，则只保留审批不通过按钮
		private void changeAuditActions(WorksheetDisplayContext displayCxt){
			String batch_info_id = displayCxt.getFieldMap().get("batch_info_id");
			if(batch_info_id != null && !"".equals(batch_info_id)){
				if("审批中".equals(displayCxt.getFieldMap().get("BASESTATUS"))){
					ChgBatchInfo chgBatchInfo = ChangeUtil.queryChgBatchInfo(batch_info_id);
					String exec_flag = chgBatchInfo.getExec_flag();
					if("停止受理".equals(exec_flag)){ //开关，是否执行规则
						String nowTime = TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss");
						String latestreviewtime = chgBatchInfo.getLatestreviewtime();
						int result = nowTime.compareTo(latestreviewtime);  
						if(result>0){//result>0 表示当前时间大于最晚参与评审时间
							leaveActions(displayCxt,"auditUnPass");
						}
					}
				}
			}	
		}
}
