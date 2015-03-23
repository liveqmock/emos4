package com.ultrapower.eoms.workflow.jsp.base.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.engine.core.model.WfAction;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public interface IProcessService {

	String save(String baseSchema, WfAction wfAction, ProcessTask task, UserSession user, String actorStr, String auditStr, Map inputs, Map modLogs);
	
	Map getSheetInfor(String baseId, String baseSchema);
	
	public List<DealProcessModel> getProcessList(String actionType, UserSession user, String baseId, String baseSchema);

	public List<WfAction> getAvailableActions(UserSession user, String baseSchema, ProcessTask task);

	public WfStep getCurrentWfStep(String baseSchema, String entryId, String stepCode);

	public DealProcessModel checkPerm(String baseId, String baseSchema, UserSession user);

	public String getDimensionStr(String baseSchema);

	public String[] matchRole(String baseSchema, String defName, String stepCode, Map inputs);

	public String getConditionStr(String baseSchema, String defName);

	public List<WfStep> getNextWfSteps(String baseSchema, String defName, String entryId,
			String stepCode, Map inputs);
	
	public ProcessTask getCurrentTaskById(String taskId);
	
	public ProcessTask getHistoryTaskById(String taskId);
}
