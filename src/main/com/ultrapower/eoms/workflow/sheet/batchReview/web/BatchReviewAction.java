package com.ultrapower.eoms.workflow.sheet.batchReview.web;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ProcessService;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.workflow.sheet.batchReview.service.BatchReviewService;

/**
 * 工单批量审批
 * @author 
 *
 */

public class BatchReviewAction extends BaseAction {
	
	private PortalManagerService portalManagerService; 
	private BatchReviewService batchReviewService;
	private AttachmentManagerService attachmentManagerService;
	private ProcessService processService; 
	protected String baseIds; //需要批量删除的工单  
	protected String assignString; //处理字符串  
	protected String baseSchema;  //工单Schema
	protected String actionID; //动作标识
	protected String actionText; //动作描述
	protected String actionType; //动作类型
	protected String fieldsStr; //表单字段,存储表单相关信息
	protected String attaGroupId; // 附件关联ID
	
	
	/**
	 * 批量审批提交
	 * @return 
	 */
	
	public void bacthDealReview(){
		String returnStr= "error：";
		String returnText = "";
		String baseIdArray[]=baseIds.split(",");   
		for(String baseId:baseIdArray) {
			String sheetRelationAttId = batchReviewService.getAttIdByBaseId(baseSchema,baseId); //得到工单关联的附件ID
			batchReviewService.cloneDataRecord(attaGroupId,sheetRelationAttId,baseId,baseSchema); //将批量审批工单分发到各工单
			returnText= doAction(baseSchema,baseId,assignString);
			if(!"".equals(returnText)){
//				returnText=baseSN+returnText;
			}
		}
		returnStr+=returnText; //如果有异常发生,将错误单号回传给前台,否则返回成功信息
		if(returnStr.equals("error：")){
			returnStr ="批量审批成功";
		}
		this.renderText(returnStr);
	}
	
	/**
	 * 批量审批方法页面跳转
	 * @return
	 */
	public String batchReview(){
		return this.findForward("batchReview");
	}
	
	
	/**
	 * 执行批量审批操作
	 * @param baseSchema
	 * @param baseId
	 * @param assignString
	 * @return
	 */
	public String  doAction(String baseSchema,String baseId,String assignString){
		String fieldsStr = this.fieldsStr;
		String filedStrArray[]=fieldsStr.split(";");   
		String returnText = "";
		WorksheetCommitContext commitCxt = new WorksheetCommitContext();
		
		//放入表单相关信息
		for(String stemp:filedStrArray){  
			commitCxt.getFieldMap().put(stemp.substring(0, stemp.indexOf(":")), stemp.substring(stemp.indexOf(":")+1,stemp.length()) );
		}
		
		commitCxt.setBaseID(baseId);
		commitCxt.setBaseSchema(baseSchema);
		commitCxt.setActionID(actionID);
		commitCxt.setActionType(actionType);
		commitCxt.setActionText(actionText);
		commitCxt.setAssignString(assignString);
		commitCxt.setUserInfo(portalManagerService.getUserSession(getUserSession().getLoginName()));
		
		try
		{
			returnText = processService.save(commitCxt);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			returnText = e.getMessage();
			commitCxt.setCloseAfterCommit(0);
		}
		return returnText;
	}

	
	
	public PortalManagerService getPortalManagerService() {
		return portalManagerService;
	}

	public void setPortalManagerService(PortalManagerService portalManagerService) {
		this.portalManagerService = portalManagerService;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public String getBaseIds() {
		return baseIds;
	}

	public void setBaseIds(String baseIds) {
		this.baseIds = baseIds;
	}
	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getAssignString() {
		return assignString;
	}

	public void setAssignString(String assignString) {
		this.assignString = assignString;
	}

	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

	public String getActionText() {
		return actionText;
	}

	public void setActionText(String actionText) {
		this.actionText = actionText;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public AttachmentManagerService getAttachmentManagerService() {
		return attachmentManagerService;
	}

	public void setAttachmentManagerService(
			AttachmentManagerService attachmentManagerService) {
		this.attachmentManagerService = attachmentManagerService;
	}

	public String getFieldsStr() {
		return fieldsStr;
	}

	public BatchReviewService getBatchReviewService() {
		return batchReviewService;
	}

	public void setBatchReviewService(BatchReviewService batchReviewService) {
		this.batchReviewService = batchReviewService;
	}

	public void setFieldsStr(String fieldsStr) {
		this.fieldsStr = fieldsStr;
	}

	public String getAttaGroupId() {
		return attaGroupId;
	}

	public void setAttaGroupId(String attaGroupId) {
		this.attaGroupId = attaGroupId;
	}

	
}

