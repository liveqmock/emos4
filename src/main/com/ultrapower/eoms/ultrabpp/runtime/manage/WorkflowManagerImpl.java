package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.utils.ProcessUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowService;
import com.ultrapower.pasm.exception.ExceedMaxLengthException;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.core.model.FieldDataType;
import com.ultrapower.workflow.engine.core.model.WfAction;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.def.model.StartDescriptor;
import com.ultrapower.workflow.engine.def.model.StepDescriptor;
import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
import com.ultrapower.workflow.engine.task.ITaskManager;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.utils.UUIDGenerator;
import com.ultrapower.workflow.utils.WfEngineConstants;

@Transactional
public class WorkflowManagerImpl implements WorkflowService {
	
	private static Logger log = LoggerFactory.getLogger(WorkflowManagerImpl.class);
	
	protected IBizFacade bizFacade = WorkFlowServiceClient.clientInstance().getBizFacade();
	protected ITaskManager taskManager;
	protected MetaCacheService metaCacheService;
	protected IDao jdbcDao;

	public EngineModel doAction(String baseID, String baseSchema,
			UserSession userInfor, ProcessTask<?> currentTask, String actionID, String actionType,
			String actionText, Map<String, String> inputs) {
		
		boolean checkAction = false;
		//taskManager.getCurrentTaskById(currentTask.getId());
		//ProcessTask<?> checkTask = getCurrentProcess(baseID, baseSchema, userInfor, null);
		if(currentTask.getId() == null || (
				currentTask.getFlagActive() == 0 && (actionType.equals("APPENDASSIGN") || actionType.equals("SUGGEST") || actionType.equals("CHASE")||actionType.equals("HASTEN"))
			  )
			  || currentTask.getFlagActive() > 0 && (!actionType.equals("APPENDASSIGN") || !actionType.equals("SUGGEST") || !actionType.equals("CHASE")||!actionType.equals("HASTEN"))
			)
			{
			checkAction = true;
			}
		
		if(!checkAction)
		{
			throw new RuntimeException("该任务已被处理！");
		}

		//fanying 增加受理动作的判断，当任务派给一个组时，如果组内有人已经受理了工单，其他人不能再对该张工单进行处理
		String dealer = currentTask.getDealerId();
		if("ACCEPT".equals(currentTask.getActionCode())&&currentTask.getDealer()!=null&&!userInfor.getLoginName().equals(dealer)){
			checkAction = false;
		}
		if(!checkAction){
			throw new RuntimeException("该任务已被受理！");
		}
		
		String taskID = null;
		boolean isCurrent = true;
		if (currentTask != null) {
			taskID = currentTask.getId();
			isCurrent = (currentTask.getFlagActive() == 1 || currentTask.getFlagActive() == 17);
		}
		String baseName = ProcessUtil.getStringFromMapIgnoreCase("BASENAME", inputs);
		String baseSummary = ProcessUtil.getStringFromMapIgnoreCase("BASESUMMARY", inputs);
		String assignString = ProcessUtil.getStringFromMapIgnoreCase("BPP_SYS_ASSIGNSTRING", inputs);
		String relateType = ProcessUtil.getStringFromMapIgnoreCase("BPP_SYS_RELATETYPE", inputs);
		String relateBaseID = ProcessUtil.getStringFromMapIgnoreCase("BPP_SYS_RELATEBASEID", inputs);
		String relateBaseSchema = ProcessUtil.getStringFromMapIgnoreCase("BPP_SYS_RELATEBASESCHEMA", inputs);
		String relateBaseName = "";
		String relateBaseSN = ProcessUtil.getStringFromMapIgnoreCase("BPP_SYS_RELATEBASESN", inputs);
		String relateTaskID = ProcessUtil.getStringFromMapIgnoreCase("BPP_SYS_RELATETASKID", inputs);
		log.info("assignString=" + assignString);
		Map<String, String> params = new HashMap<String, String>();
		params.put("relateType", relateType);
		params.put("relateBaseId", relateBaseID);
		params.put("relateBaseSchema", relateBaseSchema);
		if (StringUtils.isNotBlank(relateBaseSchema)) {
			IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
			try {
				WfType wfType = sortService.getWfTypeByCode(relateBaseSchema);
				relateBaseName = wfType.getName();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		params.put("relateBaseName", relateBaseName);
		params.put("relateBaseSn", relateBaseSN);
		params.put("relateTaskId", relateTaskID);
		
//		String relateType = params.get("relateType");
//		String relateBaseId = params.get("relateBaseId");
//		String relateBaseName = params.get("relateBaseName");
//		String relateBaseSchema = params.get("relateBaseSchema");
//		String relateTaskId = params.get("relateTaskId");
		
//		String assignString = "U#:Demo#:ASSIGN#:2#:0#:0#:0#:#:dp_0#:#:#;U#:admin#:ASSIST#:2#:0#:0#:0#:#:dp_0#:#:#;U#:Demo#:MAKECOPY#:2#:0#:0#:0#:#:dp_0#:#:#;U#:test#:AUDIT#:2#:0#:0#:0#:#:dp_0#:#:#;";
		String desc = ProcessUtil.getStringFromMapIgnoreCase("DESC", inputs);
		EngineModel engineModel = null;
		String defName = null;
		String entryType = null;
		String actorId = userInfor.getLoginName();
		String actorType = WfEngineConstants.ACTOR_TYPE_USER;
		String entryId = null;
		List<ActionInfo> actionInfo = null;
		if (StringUtils.isNotBlank(assignString)) {
			assignString = auditAndAssign(assignString, inputs);
			actionInfo = ProcessUtil.formatActionInfo(assignString);
		}
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		inputData.put(WfEngineConstants.INPUTS_BASEID, new DataField(WfEngineConstants.INPUTS_BASEID, FieldDataType.String, baseID));
		inputData.put(WfEngineConstants.INPUTS_BASESCHEMA, new DataField(WfEngineConstants.INPUTS_BASESCHEMA, FieldDataType.String, baseSchema));
		inputData.put(WfEngineConstants.INPUTS_BASENAME, new DataField(WfEngineConstants.INPUTS_BASENAME, FieldDataType.String, baseName));
		inputData.put(WfEngineConstants.INPUTS_BASESUMMARY, new DataField(WfEngineConstants.INPUTS_BASESUMMARY, FieldDataType.String, baseSummary));
		inputData.put(WfEngineConstants.PROCESS_ACTION_DESC , new DataField(WfEngineConstants.PROCESS_ACTION_DESC , FieldDataType.String, actionText));
		inputData.put(WfEngineConstants.PROCESS_DEAL_DESC, new DataField(WfEngineConstants.PROCESS_DEAL_DESC, FieldDataType.String, desc));
		ProcessUtil.putAll(inputData, inputs);
		try {
			engineModel = bizFacade.doAction(baseSchema, defName, entryType, actorId, actorType, entryId, actionID, taskID, actionType, isCurrent, actionInfo, inputData, params);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return engineModel;
	}
	
	public List<ActionModel>[] getAvailableActions(String baseID, String baseSchema,
			ProcessTask<?> currentTask) {
		Map<String, ActionModel> freeActionMap = metaCacheService.getFreeActionMap();
		Map<String, ActionModel> fixActionMap = null;
		List<ActionModel> freeActions = new ArrayList<ActionModel>();
		List<ActionModel> fixActions = new ArrayList<ActionModel>();
		if (currentTask != null) {
			String taskId = currentTask.getId();
			String stepNo = currentTask.getStepNo();
			if (StringUtils.isNotBlank(stepNo)) {
				fixActionMap = metaCacheService.getFixActionMap(baseSchema, stepNo);
			}
			try {
				List<WfAction> wfActions = bizFacade.getAvailableActions(baseSchema, taskId, null, null);
				if (CollectionUtils.isNotEmpty(wfActions)) {
					for (int i = 0; i < wfActions.size(); i++) {
						WfAction wfAction = wfActions.get(i);
						String actionNo = wfAction.getActionNo();
						String actionType = wfAction.getActionType();
						if (StringUtils.isBlank(actionNo)) {
							ActionModel freeAction = freeActionMap.get(actionType);
							if (freeAction != null) {
								freeActions.add(freeAction.copyModel());
							}
						} else {
							if (fixActionMap != null) {
								ActionModel fixAction = fixActionMap.get(actionNo);
								if (fixAction != null) {
									fixActions.add(fixAction.copyModel());
									fixActionMap.remove(actionNo);
								}
							}
						}
					}
				}
				if (fixActionMap != null && currentTask.getFlagActive() == 1) {
					for (Iterator iter = fixActionMap.keySet().iterator(); iter.hasNext();) {
						String key = (String) iter.next();
						ActionModel actionModel = fixActionMap.get(key);
						if (actionModel.getIsFree() == 1) {
							freeActions.add(actionModel);
						} else if(!actionModel.getActionType().equals("NEXT")) {
							fixActions.add(actionModel);
						}
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		List<ActionModel>[] arys = new ArrayList[2];
		arys[0] = fixActions;
		arys[1] = freeActions;
		return arys;
	}

	public String[] getConditions(String baseSchema, String defCode) {
		String[] rtn = null;
		try {
			List<String> conditions = bizFacade.getConditions(baseSchema, defCode);
			if (CollectionUtils.isNotEmpty(conditions)) {
				rtn = conditions.toArray(new String[conditions.size()]);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public ProcessTask<?> getCurrentProcess(String baseID, String baseSchema, 
			UserSession userInfo, String taskID) {
		ProcessTask task = null;
		if (StringUtils.isNotBlank(taskID)) {
			task = (ProcessTask) taskManager.getTaskById(taskID);
		} else {
			if (StringUtils.isBlank(baseID)) {
				IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
				try {
					WfType wfType = sortService.getWfTypeByCode(baseSchema);
					if (wfType != null) {
						long type = wfType.getWfType();
						if (1 == type) {
							StepDescriptor stDesc = getCreateStep(baseSchema);
							if (stDesc != null) {
								String stepNo = stDesc.getStepNo();
								String stepCode = stDesc.getId();
								task = new ProcessTask();
								task.setId(null);
								task.setStepNo(stepNo);
								task.setStepCode(stepCode);
								task.setEntryState("草稿");
								task.setFlagActive(1);
								task.setFlagPreDefined(1l);
							} 
						} else {
							task = new ProcessTask();
							task.setId(null);
							task.setEntryState("草稿");
							task.setFlagActive(1);
							task.setFlagPreDefined(0l);
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			} else {
				//反查
				try {
					task = bizFacade.checkTask(baseID, baseSchema, userInfo.getLoginName());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
		if (task != null) {
			return (ProcessTask<?>)task.clone();
		}
		return task;
	}

	public Map<String, EditableFieldModel> getEditableFields(String baseSchema, ProcessTask<?> currentTask) {
		
		Map<String, EditableFieldModel> rtn = null;
		IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
//		try {
			//WfType wfType = sortService.getWfTypeByCode(baseSchema);
			//if (wfType.getWfType() == 1) {
			if (currentTask.getFlagPreDefined() == 1) {
				//固定流程
				String stepNo = null;
				if (currentTask != null) {
					stepNo = currentTask.getStepNo();
				}
				rtn = metaCacheService.getStepFields(baseSchema, stepNo);
			} else {
				//自由流程
				String entryState = null;
				if (currentTask != null) {
					entryState = currentTask.getEntryState();
					if (StringUtils.isBlank(entryState)) {
						entryState = "草稿";
					}
				} 
				rtn = metaCacheService.getStatusFields(baseSchema, entryState);
			}
//		} catch (RemoteException e1) {
//			e1.printStackTrace();
//		}
		return rtn;
	}
	
	/**
	 * 获取建单环节的xml描述对象
	 * @param baseSchema
	 * @return
	 */
	public StepDescriptor getCreateStep(String baseSchema) {
		StepDescriptor stDesc = null;
		try {
			WorkflowDescriptor wfDesc = bizFacade.getWorkflowDescriptor(baseSchema, null);
			StartDescriptor startDesc = wfDesc.getStartDesc();
			String to = startDesc.getTo();
			stDesc = wfDesc.getStepDescriptor(to);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stDesc;
	}

	public List<WfStep> getNextSteps(String entryId, String baseSchema, String defCode,
			String phaseNo, Map<String, String> args) {
		List<WfStep> nextWfSteps = null;
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		try {
			nextWfSteps = bizFacade.getNextWfSteps(entryId, null, baseSchema, defCode, phaseNo, ProcessUtil.putAll(inputData, args));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return nextWfSteps;
	}

	public List<ProcessTask> getRollbackTasks(String baseID,
			String baseSchema, UserSession userInfo, String taskID, String actionType) {
		List<ProcessTask> rtn = null;
		try {
			ProcessTask ptask = getCurrentProcess(baseID, baseSchema, userInfo, taskID);
			if (WfEngineConstants.ACTION_CHASE.equals(actionType)) {//追回
				rtn = getChaseProcessTaskList(ptask);
			}
			if (WfEngineConstants.ACTION_REJECT.equals(actionType)) {//驳回
				rtn = getRejectProcessTaskList(ptask);
			}
			if (WfEngineConstants.ACTION_SUGGEST.equals(actionType)) {//阶段建议
				rtn = getSuggestProcessTaskList(ptask);
			}
			if (WfEngineConstants.ACTION_HASTEN.equals(actionType)) {//催办
				rtn = getHastenProcessTaskList(ptask);
			}
			if (WfEngineConstants.ACTION_SENDBACK.equals(actionType)) {//退回
				rtn = getSendBackProcessTaskList(ptask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public void saveFieldModifyLog(String baseID, String baseSchema, boolean isAddNew, ProcessTask<?> currentTask, UserSession userInfo, String actionID,
			String actionType, String actionText, Map<String, String> fieldMap, EngineModel engineModel) {
		String phaseNo = currentTask.getStepCode();
		String taskId = currentTask.getId();
		String stepNo = currentTask.getStepNo();
		String baseStatus = currentTask.getEntryState();
		long flagActive = currentTask.getFlagActive();
		if (isAddNew) {
			phaseNo = engineModel.getCreateStepCode();
			taskId = engineModel.getCreateTaskId();
		}
		long currentTime = TimeUtils.getCurrentTime();
		String tableName = "BS_F_" + baseSchema + "_FML";
		
		Map<String, EditableFieldModel> stepFields = null;
		Map<String, EditableFieldModel> actionFields = null;
		if (flagActive == 1) {
			if (StringUtils.isNotBlank(stepNo)) {
				stepFields = metaCacheService.getStepFields(baseSchema, stepNo);
				actionFields = metaCacheService.getActionFields(baseSchema,stepNo,actionID);
			} else {
				stepFields = metaCacheService.getStatusFields(baseSchema, baseStatus);
				actionFields = metaCacheService.getFreeActionFields(baseSchema, baseStatus, actionType);
			}
		}
		if (stepFields != null) {
			for (Iterator iterator = stepFields.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (StringUtils.isNotBlank(key)) {
					BaseField baseField = stepFields.get(key).getBaseField();
					String val = fieldMap.get(key);
					String label = baseField.getLabel();
					int orderNum = baseField.getOrderNum();
					String fieldType = baseField.getFieldType();
					if (val != null && baseField instanceof com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField) {
						com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField dataField = (com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField) baseField;
						int colspan = dataField.getColspan();
						Map<String, Object> saveMap = baseField.getSaveSql(fieldMap);
						if (saveMap != null) {
							String id = UUIDGenerator.getId();
							String sql = "insert into "
								+ tableName
								+ " (id,baseId,baseSchema,phaseNo,fieldId,fieldType,modifyDate,fieldCode,fieldValue,dealerId,dealer,taskId,actionType,actionName,fieldLabel,orderNum,colspan) "
								+ "values ('" + id + "','" + baseID + "','"
								+ baseSchema + "','" + phaseNo + "','"+key+"','"+fieldType+"','"
								+ currentTime + "','" + key + "',?,'"
								+ userInfo.getLoginName() + "','"
								+ userInfo.getFullName() + "','" + taskId + "','"
								+ actionType + "','" + actionText + "','"+ label+"','"+orderNum+"','"+colspan+"')";
							jdbcDao.executeUpdate(sql, new Object[] {val});
						}
					}
				}
			}
		}
		if (actionFields != null) {
			for (Iterator iterator = actionFields.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (StringUtils.isNotBlank(key)) {
					BaseField baseField = actionFields.get(key).getBaseField();
					String val = fieldMap.get(key);
					String label = baseField.getLabel();
					int orderNum = baseField.getOrderNum();
					String fieldType = baseField.getFieldType();
					if (val != null && baseField instanceof com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField) {
						com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField dataField = (com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField) baseField;
						int colspan = dataField.getColspan();
						Map<String, Object> saveMap = baseField.getSaveSql(fieldMap);
						if (saveMap != null) {
							String id = UUIDGenerator.getId();
							String sql = "insert into "
								+ tableName
								+ " (id,baseId,baseSchema,phaseNo,fieldId,fieldType,modifyDate,fieldCode,fieldValue,dealerId,dealer,taskId,actionType,actionName,fieldLabel,orderNum,colspan) "
								+ "values ('" + id + "','" + baseID + "','"
								+ baseSchema + "','" + phaseNo + "','"+key+"','"+fieldType+"','"
								+ currentTime + "','" + key + "',?,'"
								+ userInfo.getLoginName() + "','"
								+ userInfo.getFullName() + "','" + taskId + "','"
								+ actionType + "','" + actionText + "','"+ label+"','"+orderNum+"','"+colspan+"')";
							jdbcDao.executeUpdate(sql, new Object[] {val});
						}
					}
				}
			}
		}
	}
	
	/**
	 * 追回环节列表
	 * @param dp
	 * @return
	 */
	protected List<ProcessTask> getChaseProcessTaskList(ProcessTask task) {
		List<ProcessTask> rtn = null;
		if (task != null) {
			String baseId = task.getBaseId();
			String baseSchema = task.getBaseSchema();
			String entryId = task.getEntryId();
			String phaseNo = task.getStepCode();
			String hql = "from CurrentProcessTask t where t.entryId='"+entryId+"' and t.flagActive=1 and t.prePhaseNo='"+phaseNo+"' and t.acceptTime=0";
			rtn = taskManager.queryCurrentTask(hql);
		}
		return rtn;
	}
	
	/**
	 * 驳回环节列表
	 * @param dp
	 * @return
	 */
	public List<ProcessTask> getRejectProcessTaskList(ProcessTask task) {
		List<ProcessTask> rtn = null;
		if (task != null) {
			String baseId = task.getBaseId();
			String baseSchema = task.getBaseSchema();
			String entryId = task.getEntryId();
			String prePhaseNo = task.getPrePhaseNo();		//and t.flagActive !=1 抄送dp未处理继续转抄，转抄驳回flagActive=1
			String origPhaseNo = task.getOrigPhaseNo();
			String hql = "from HistoryProcessTask t where t.entryId='"+entryId+"' and t.stepCode='"+prePhaseNo+"' and t.flagEndDuplicated=1";
			//驳回到上一级，同样适用于转派和转审
//			if (StringUtils.isNotBlank(origPhaseNo)) {
//				hql = "from HistoryProcessTask t where t.entryId='"+entryId+"' and t.stepCode='"+origPhaseNo+"' and t.flagEndDuplicated=1";
//			}
			rtn = taskManager.queryCurrentTask(hql);
		}
		return rtn;
	}
	
	/**
	 * 阶段建议列表
	 * @param dp
	 * @return
	 */
	public List<ProcessTask> getSuggestProcessTaskList(ProcessTask task) {
		List<ProcessTask> rtn = null;
		if (task != null) {
			String baseId = task.getBaseId();
			String baseSchema = task.getBaseSchema();
			String entryId = task.getEntryId();
			String phaseNo = task.getStepCode();//此处必须用baseId，不能用entryId，如：固定next同时抄送，如果entryId则只能查到抄送的dp
			String hql = "from CurrentProcessTask t where t.baseId='"+baseId+"' and t.flagActive=1";
			rtn = taskManager.queryCurrentTask(hql);
		}
		return rtn;
	}
	
	/**
	 * 催办列表
	 * @param dp
	 * @return
	 */
	public List<ProcessTask> getHastenProcessTaskList(ProcessTask task) {
		List<ProcessTask> rtn = null;
		if (task != null) {
			String baseId = task.getBaseId();
			String baseSchema = task.getBaseSchema();
			String entryId = task.getEntryId();
			String phaseNo = task.getStepCode();//此处必须用baseId，不能用entryId，如：固定next同时抄送，如果entryId则只能查到抄送的dp
			String hql = "from CurrentProcessTask t where t.baseId='"+baseId+"' and t.flagActive=1";
			rtn = taskManager.queryCurrentTask(hql);
		}
		return rtn;
	}
	
	/**
	 * 退回列表
	 * @param dp
	 * @return
	 */
	public List<ProcessTask> getSendBackProcessTaskList(ProcessTask task) {
		List<ProcessTask> rtn = null;
		if (task != null) {
			String baseId = task.getBaseId();
			String baseSchema = task.getBaseSchema();
			String entryId = task.getEntryId();
			String phaseNo = task.getStepCode();
			String hql = "from HistoryProcessTask t where t.entryId='"+entryId+"' and t.flagEndDuplicated=1 and t.flagActive !=1 and (" +
					"(t.prePhaseNo='"+phaseNo+"' and t.edProcessAction !='转派' and t.edProcessAction !='驳回') " +
					"or " +
					"(t.origPhaseNo='"+phaseNo+"' and t.edProcessAction !='转派')" +
					")";
			rtn = taskManager.queryCurrentTask(hql);
		}
		return rtn;
	}
	
	public ITaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public MetaCacheService getMetaCacheService() {
		return metaCacheService;
	}

	public void setMetaCacheService(MetaCacheService metaCacheService) {
		this.metaCacheService = metaCacheService;
	}

	public IDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(IDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public Map<String, EditableFieldModel> getEditableFieldsByAction(String baseSchema, ProcessTask<?> currentTask, String actionNo) {
		Map<String, EditableFieldModel> rtn = null;
		try {
			if (currentTask.getFlagPreDefined() == 1) {
				rtn = metaCacheService.getActionFields(baseSchema,currentTask.getStepNo(),actionNo);
			} else {
				rtn = metaCacheService.getFreeActionFields(baseSchema, currentTask.getEntryState(), actionNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	/**
	 * 连审带派处理人需要特殊处理
	 * @param assignString
	 * @param inputs
	 * @return
	 */
	private String auditAndAssign(String assignString, Map<String, String> inputs) {
		String assignStr = "";
		String auditStr = "";
		if (assignString.indexOf("#:AUDIT#") > 0 && assignString.indexOf("#:ASSIGN#") > 0) {
			String[] assAry = assignString.split("#;");
			if (!ArrayUtils.isEmpty(assAry)) {
				for(String assign : assAry) {
					if (assign.indexOf("#:AUDIT#") > 0) {
						auditStr += assign + "#;";
					} else {
						assignStr += assign + "#;";
					}
				}
			}
			inputs.put(WfEngineConstants.PREASSIGN, assignStr);
			return auditStr;
		}
		return assignString;
	}
}
