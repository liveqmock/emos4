package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public interface WorkflowService
{
	public EngineModel doAction(String baseID, String baseSchema, UserSession userInfo, ProcessTask<?> currentTask, String actionID, String actionType, String actionText, Map<String, String> inputs);
	
	public void saveFieldModifyLog(String baseID, String baseSchema, boolean isAddNew, ProcessTask<?> currentTask, UserSession userInfo, String actionID, String actionType, String actionText, Map<String, String> fieldMap, EngineModel engineModel);
	
	public List<ProcessTask> getRollbackTasks(String baseID, String baseSchema, UserSession userInfo, String taskID, String actionType);
	
	public List<ActionModel>[] getAvailableActions(String baseID, String baseSchema, ProcessTask<?> currentTask);
	
	public ProcessTask<?> getCurrentProcess(String baseID, String baseSchema, UserSession userInfo, String taskID);
	
	public List<WfStep> getNextSteps(String entryId, String baseSchema, String defCode, String phaseNo, Map<String, String> args);
	
	public Map<String, EditableFieldModel> getEditableFields(String baseSchema, ProcessTask<?> currentTask);
	
	public Map<String, EditableFieldModel> getEditableFieldsByAction(String baseSchema, ProcessTask<?> currentTask, String actionNo);
	
	public String[] getConditions(String baseSchema, String defCode);
}
