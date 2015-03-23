package com.ultrapower.eoms.msextend.release;

import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.SAVE;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.addWfRecord;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.isNew;
import static com.ultrapower.eoms.msextend.workflow.WorkFLowUtil.leaveActions;

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

public class ReleaseManageExtendFunction implements ExtendFunction {
	private static final Logger log = LoggerFactory.getLogger(ReleaseManageExtendFunction.class);

	public void beginOpen(WorksheetDisplayContext displayCxt) {
		
		
	}
	
	public void endOpen(WorksheetDisplayContext displayCxt)
	{
		changeNewActions(displayCxt);
		changeAuditActions(displayCxt);
		//增加受理按钮
		this.setActions(displayCxt);
		displayCxt.getAttributeMap().put("GROUPID",displayCxt.getUserInfo().getGroupId());
		displayCxt.getAttributeMap().put("NOWTIME",TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		displayCxt.getAttributeMap().put("LOGINNAME",displayCxt.getUserInfo().getLoginName());
		displayCxt.getAttributeMap().put("FULLNAME",displayCxt.getUserInfo().getFullName());
	}
	
	//新建环节,如果是新建工单则只显示保存按钮
	private void changeNewActions(WorksheetDisplayContext displayCxt){
		if(isNew(displayCxt))
			leaveActions(displayCxt,"save");
	}
	
	//审批环节如果最晚参与评审时间小于当前时间，则只保留审批不通过按钮
	private void changeAuditActions(WorksheetDisplayContext displayCxt){
		String batch_info_id = displayCxt.getFieldMap().get("batch_info_id");
		if("审批中".equals(displayCxt.getFieldMap().get("BASESTATUS"))){
			ChgBatchInfo chgBatchInfo = ReleaseUtil.queryChgBatchInfo(batch_info_id);
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
//	if(status.equals("草稿") && StringUtils.isBlank(displayCxt.getBaseID())){
//		for(ActionModel action:displayCxt.getFixActionList()){
//			if(action.getActionName().equals("SAVE")){
//				list.add(action);
//			}
//		}
//		displayCxt.getFixActionList().addAll(list);
//		displayCxt.getFixActionList().clear();
//	}
	
	displayCxt.getFreeActionList().clear();
	if(displayCxt.getFixActionList().size() > 0 ){	
			if(status != null && !status.equals("")){
				if((status.equals("方案验证中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("caseAcceptTime")) || displayCxt.getFieldMap().get("caseAcceptTime").equals("0"))) ||
//					(status.equals("审批中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("auditAcceptTime")) || displayCxt.getFieldMap().get("auditAcceptTime").equals("0"))) ||
//					(status.equals("评审中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("organizeAcceptTime")) || displayCxt.getFieldMap().get("organizeAcceptTime").equals("0"))) ||
					(status.equals("实施中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("realAcceptTime")) || displayCxt.getFieldMap().get("realAcceptTime").equals("0"))) ||
					(status.equals("验证中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("testAcceptTime")) || displayCxt.getFieldMap().get("testAcceptTime").equals("0"))) ||
					(status.equals("方案编写中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("editAcceptTime")) || displayCxt.getFieldMap().get("editAcceptTime").equals("0"))) ||
					(status.equals("回顾中") && (StringUtils.isBlank(displayCxt.getFieldMap().get("reviewAcceptTime")) || displayCxt.getFieldMap().get("reviewAcceptTime").equals("0")))
					){
					displayCxt.getFreeActionList().clear();
					displayCxt.getFixActionList().clear();
	
					ActionModel acceptAction = new ActionModel();
					acceptAction.setId("cdb_accept");
					acceptAction.setActionName("ACCEPT");
					acceptAction.setActionType("ACCEPT");
					acceptAction.setLabel("受    理");
					acceptAction.setDescription("");
					acceptAction.setHasNext(0);
					acceptAction.setIsClose(0);
					acceptAction.setIsFree(1);
					displayCxt.getFixActionList().add(acceptAction);
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
				if(status.equals("方案验证中")){
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
				}else if(status.equals("方案编写中")){
					commitCxt.getFieldMap().put("editAcceptTime",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
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
				commitCxt.getFieldMap().put("editAcceptTime","");
			}
			
			if(actionId.equals("check2audit")){//方案验证
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
			}
		}
	}
	
	/**
	 * 记录各个处理过程
	 */
	public void seetRecord(WorksheetCommitContext commitCxt){
		String actionType = commitCxt.getActionType();
		String actionId = commitCxt.getActionID();
		if(StringUtils.isNotBlank(actionId) && !actionId.equals("ToModify") 
				&& StringUtils.isNotBlank(actionType) && (actionType.equals("NEXT") || actionType.equals("RENEXT"))){
			String mailContent=""; //邮件内容
			mailContent = WorkFLowUtil.getMailContentByType("CDB_RELEASE_RECORDCONTENT");
			addWfRecord(commitCxt, 1, "notify","dealDesc",mailContent,"");
		}
	}

}
