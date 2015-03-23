package com.ultrapower.eoms.workflow.jsp.base.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.HibernateMapDaoImpl;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.workflow.jsp.base.service.IProcessService;
import com.ultrapower.eoms.workflow.jsp.util.ProcessUtil;
import com.ultrapower.eoms.workflow.sheet.ownfields.model.OwnFields;
import com.ultrapower.eoms.workflow.sheet.ownfields.service.OwnFieldsService;
import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.core.model.FieldDataType;
import com.ultrapower.workflow.engine.core.model.WfAction;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;
import com.ultrapower.workflow.role.service.IRoleService;
import com.ultrapower.workflow.utils.WfEngineConstants;

@Transactional
public class BaseProcessManagerImpl implements IProcessService {

	private IRoleService roleService;
	protected HibernateMapDaoImpl baseDao;
	protected IBizFacade bizFacade = WorkFlowServiceClient.clientInstance().getBizFacade();
	protected OwnFieldsService ownFieldsService;
	
	/**
	 * 工单保存
	 */
	public String save(String baseSchema, WfAction wfAction, ProcessTask task, UserSession user, String actorStr, String auditStr, Map inputs, Map modLogs) {
		String msg = "";
		long currentTime = TimeUtils.getCurrentTime();
		List<OwnFields> ownFields = ownFieldsService.getAll(baseSchema);
		String taskId = (task != null ? task.getId() : null);
		
		//保存工单通用信息
		saveBaseInfo(inputs);
		
		//调用流程引擎驱动流转
		EngineModel engineModel = doAction(wfAction, baseSchema, actorStr, auditStr, task, user, ownFields, inputs, modLogs);
		if (engineModel != null) {
			String topEntryId = engineModel.getTopEntryId();
			long baseCreateTime = engineModel.getBaseCreateTime();
			String baseSn = engineModel.getBaseSn();
			String entryState = engineModel.getEntryState();
			msg = engineModel.getMailContent();
			String entryStateFlag = engineModel.getEntryStateFlag();
			inputs.put("baseSn", baseSn);
			inputs.put("baseEntryId", topEntryId);
			inputs.put("baseStatus", entryState);
			inputs.put("baseCreateDate", baseCreateTime);
			if ("2".equals(entryStateFlag)) {//流程实例结束给关单时间赋值
				inputs.put("baseCloseDate", currentTime);
			}
		}
		
		//保存工单自有信息
		saveBaseSchemaInfo(baseSchema, inputs);
		
		//保存modlog信息
		if (StringUtils.isBlank(taskId)) {
			taskId = engineModel.getCreateTaskId();
		}
		saveModlog(baseSchema, user, inputs, modLogs, currentTime, taskId, wfAction);
		
		return msg;
	}

	/**
	 * 保存baseInfo信息
	 * @param inputs
	 */
	protected void saveBaseInfo(Map inputs) {
		String baseId = ProcessUtil.getStringFromMap("baseId", inputs);
		if (StringUtils.isBlank(baseId)) {
			inputs.put("baseId", null);
		}
		baseDao.setEntityName("BASEINFOR");
		baseDao.saveOrUpdate(inputs);
	}
	
	/**
	 * 调用流程引擎驱动流转
	 * @param wfAction
	 * @param baseSchema
	 * @param actorStr
	 * @param task
	 * @param user
	 * @param ownFields
	 * @param inputs
	 * @param modLogs
	 * @return
	 */
	protected EngineModel doAction(WfAction wfAction, String baseSchema, String actorStr, String auditStr, ProcessTask task, UserSession user, List<OwnFields> ownFields, Map inputs, Map modLogs) {
		String baseId = ProcessUtil.getStringFromMap("baseId", inputs);
		String baseName = ProcessUtil.getStringFromMap("baseName", inputs);
		String baseSummary = ProcessUtil.getStringFromMap("baseSummary", inputs);
		EngineModel engineModel = null;
		IBizFacade bizFacade = WorkFlowServiceClient.clientInstance().getBizFacade();
		String defName = null;
		String entryType = null;
		String actorId = user.getLoginName();
		String actorType = WfEngineConstants.ACTOR_TYPE_USER;
		String entryId = null;
		String actionId = null;
		String actionType = wfAction.getActionType();
		String actionName = wfAction.getActionName();
		boolean isCurrent = true;
		String taskId = (task != null ? task.getId() : null);
		if (task != null && task.getFlagActive() != 1) {
			isCurrent = false;
		}
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		List<ActionInfo> actionInfoList = null;
		if (StringUtils.isNotBlank(auditStr)) {
			actionInfoList = ProcessUtil.formatActionInfo(auditStr);
			if (StringUtils.isNotBlank(actorStr)) {
				inputData.put(WfEngineConstants.PREASSIGN, new DataField(WfEngineConstants.PREASSIGN, FieldDataType.String, actorStr));
			}
		} else {
			actionInfoList = ProcessUtil.formatActionInfo(actorStr);
		}
		inputData.put(WfEngineConstants.INPUTS_BASEID, new DataField(WfEngineConstants.INPUTS_BASEID, FieldDataType.String, baseId));
		inputData.put(WfEngineConstants.INPUTS_BASESCHEMA, new DataField(WfEngineConstants.INPUTS_BASESCHEMA, FieldDataType.String, baseSchema));
		inputData.put(WfEngineConstants.INPUTS_BASENAME, new DataField(WfEngineConstants.INPUTS_BASENAME, FieldDataType.String, baseName));
		inputData.put(WfEngineConstants.INPUTS_BASESUMMARY, new DataField(WfEngineConstants.INPUTS_BASESUMMARY, FieldDataType.String, baseSummary));
		inputData.put(WfEngineConstants.PROCESS_ACTION_DESC , new DataField(WfEngineConstants.PROCESS_ACTION_DESC , FieldDataType.String, actionName));
		inputData.put(WfEngineConstants.PROCESS_DEAL_DESC, new DataField(WfEngineConstants.PROCESS_DEAL_DESC, FieldDataType.String, getDescText(modLogs, ownFields)));
		ProcessUtil.putAll(inputData, inputs);
		ProcessUtil.putAll(inputData, modLogs);
		
		try {
			engineModel = bizFacade.doAction(baseSchema, defName, entryType, actorId, actorType, entryId, actionId, taskId, actionType, isCurrent, actionInfoList, inputData, null);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return engineModel;
	}
	
	/**
	 * 保存modlog信息
	 * @param baseSchema
	 * @param user
	 * @param inputs
	 * @param modLogs
	 * @param currentTime
	 * @param taskId
	 */
	protected void saveModlog(String baseSchema, UserSession user, Map inputs,
			Map modLogs, long currentTime, String taskId, WfAction wfAction) {
		baseDao.setEntityName(baseSchema + "_MODLOG");
		if (modLogs != null) {
			Iterator iter = modLogs.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object obj = modLogs.get(key);
				Map modLogMap = ProcessUtil.cloneMap(inputs);
				modLogMap.put("modifyDate", currentTime);
				modLogMap.put("fieldCode", key);
				modLogMap.put("fieldValue", obj);
				modLogMap.put("dealerId", user.getLoginName());
				modLogMap.put("dealer", user.getFullName());
				modLogMap.put("taskId", taskId);
				modLogMap.put("actionType", wfAction.getActionType());
				modLogMap.put("actionName", wfAction.getActionName());
				baseDao.saveOrUpdate(modLogMap);
			}
		}
	}
	
	/**
	 * 保存工单自有信息
	 * @param baseSchema
	 * @param inputs
	 */
	protected void saveBaseSchemaInfo(String baseSchema, Map inputs) {
		Map cloneMap = ProcessUtil.cloneMap(inputs);
		baseDao.setEntityName(baseSchema);
		baseDao.merge(cloneMap);
	}
	
	/**
	 * 处理需要记录在dealprocesslog上的字段
	 * @param modLogs
	 * @param ownFields
	 * @return
	 */
	private String getDescText(Map modLogs, List<OwnFields> ownFields) {
		String descText = "";
		if (modLogs != null && CollectionUtils.isNotEmpty(ownFields)) {
			Iterator iter = modLogs.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object obj = modLogs.get(key);
				for (int i = 0; i < ownFields.size(); i++) {
					OwnFields ownModel = ownFields.get(i);
					String fieldCode = ownModel.getFieldCode();
					long logPosition = ownModel.getLogPosition();
					if (key.equals(fieldCode) && 1 == logPosition) {
						if (StringUtils.isNotBlank(descText)) {
							descText += ";" + obj;
						} else {
							descText += obj;
						}
					}
				}
			}
		}
		return descText;
	}
	
	/**
	 * 根据动作类型获取环节列表
	 */
	public List<DealProcessModel> getProcessList(String actionType, UserSession user, String baseId, String baseSchema) {
		List<DealProcessModel> dealProcessList = null;
		try {
			if (WfEngineConstants.ACTION_CHASE.equals(actionType)) {//追回
				DealProcessModel dp = bizFacade.checkPerm(baseId, baseSchema, user.getLoginName());
				dealProcessList = getChaseDealProcessList(dp);
			}
			if (WfEngineConstants.ACTION_REJECT.equals(actionType)) {//驳回
				DealProcessModel dp = bizFacade.checkPerm(baseId, baseSchema, user.getLoginName());
				dealProcessList = getRejectDealProcessList(dp);
			}
			if (WfEngineConstants.ACTION_SUGGEST.equals(actionType)) {//阶段建议
				DealProcessModel dp = bizFacade.checkPerm(baseId, baseSchema, user.getLoginName());
				dealProcessList = getSuggestDealProcessList(dp);
			}
			if (WfEngineConstants.ACTION_HASTEN.equals(actionType)) {//催办
				DealProcessModel dp = bizFacade.checkPerm(baseId, baseSchema, user.getLoginName());
				dealProcessList = getHastenDealProcessList(dp);
			}
			if (WfEngineConstants.ACTION_SENDBACK.equals(actionType)) {//退回
				DealProcessModel dp = bizFacade.checkPerm(baseId, baseSchema, user.getLoginName());
				dealProcessList = getSendBackDealProcessList(dp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dealProcessList;
	}
	
	/**
	 * 追回环节列表
	 * @param dp
	 * @return
	 */
	protected List<DealProcessModel> getChaseDealProcessList(DealProcessModel dp) {
		List<DealProcessModel> rtn = null;
		if (dp != null) {
			String baseId = dp.getBaseId();
			String baseSchema = dp.getBaseSchema();
			String entryId = dp.getEntryId();
			String phaseNo = dp.getPhaseNo();
			String sql = "select * from BS_T_WF_DEALPROCESS t where t.entryId='"+entryId+"' and t.flagActive=1 and t.prePhaseNo='"+phaseNo+"' and t.bgDate=0";
			QueryAdapter con  = new QueryAdapter();
			DataTable rs = con.executeQuery(sql, null, 0);
			rtn = convert(rs);
		}
		return rtn;
	}
	
	/**
	 * 驳回环节列表
	 * @param dp
	 * @return
	 */
	public List<DealProcessModel> getRejectDealProcessList(DealProcessModel dp) {
		List<DealProcessModel> rtn = null;
		if (dp != null) {
			String baseId = dp.getBaseId();
			String baseSchema = dp.getBaseSchema();
			String entryId = dp.getEntryId();
			String prePhaseNo = dp.getPrePhaseNo();		//and t.flagActive !=1 抄送dp未处理继续转抄，转抄驳回flagActive=1
			String sql = "select * from BS_T_WF_DEALPROCESS t where t.entryId='"+entryId+"' and t.phaseNo='"+prePhaseNo+"' and t.flagEndDuplicated=1";
			QueryAdapter con  = new QueryAdapter();
			DataTable rs = con.executeQuery(sql, null, 0);
			rtn = convert(rs);
		}
		return rtn;
	}
	
	/**
	 * 阶段建议列表
	 * @param dp
	 * @return
	 */
	public List<DealProcessModel> getSuggestDealProcessList(DealProcessModel dp) {
		List<DealProcessModel> rtn = null;
		if (dp != null) {
			String baseId = dp.getBaseId();
			String baseSchema = dp.getBaseSchema();
			String entryId = dp.getEntryId();
			String phaseNo = dp.getPhaseNo();//此处必须用baseId，不能用entryId，如：固定next同时抄送，如果entryId则只能查到抄送的dp
			String sql = "select * from BS_T_WF_DEALPROCESS t where t.baseId='"+baseId+"' and t.flagActive=1";
			QueryAdapter con  = new QueryAdapter();
			DataTable rs = con.executeQuery(sql, null, 0);
			rtn = convert(rs);
		}
		return rtn;
	}
	
	/**
	 * 催办列表
	 * @param dp
	 * @return
	 */
	public List<DealProcessModel> getHastenDealProcessList(DealProcessModel dp) {
		List<DealProcessModel> rtn = null;
		if (dp != null) {
			String baseId = dp.getBaseId();
			String baseSchema = dp.getBaseSchema();
			String entryId = dp.getEntryId();
			String phaseNo = dp.getPhaseNo();//此处必须用baseId，不能用entryId，如：固定next同时抄送，如果entryId则只能查到抄送的dp
			String sql = "select * from BS_T_WF_DEALPROCESS t where t.baseId='"+baseId+"' and t.flagActive=1";
			QueryAdapter con  = new QueryAdapter();
			DataTable rs = con.executeQuery(sql, null, 0);
			rtn = convert(rs);
		}
		return rtn;
	}
	
	/**
	 * 退回列表
	 * @param dp
	 * @return
	 */
	public List<DealProcessModel> getSendBackDealProcessList(DealProcessModel dp) {
		List<DealProcessModel> rtn = null;
		if (dp != null) {
			String baseId = dp.getBaseId();
			String baseSchema = dp.getBaseSchema();
			String entryId = dp.getEntryId();
			String phaseNo = dp.getPhaseNo();
			String sql = "select * from BS_T_WF_DEALPROCESS t where t.entryId='"+entryId+"' and t.flagActive !=1 and t.prePhaseNo='"+phaseNo+"' and t.flagEndDuplicated=1";
			QueryAdapter con  = new QueryAdapter();
			DataTable rs = con.executeQuery(sql, null, 0);
			rtn = convert(rs);
		}
		return rtn;
	}
	
	/**
	 * 获取可执行动作列表
	 */
	public List<WfAction> getAvailableActions(UserSession user, String baseSchema, ProcessTask task) {
		List<WfAction> wfActions = null;
		String taskId = null;
		try {
			if (task != null) {
				taskId = task.getId();
			}
			wfActions = bizFacade.getAvailableActions(baseSchema, taskId, null, null);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return wfActions;
	}
	
	/**
	 * 删除指定动作
	 * @param actionList
	 * @param actionType
	 */
	public void removeWfAction(List<WfAction> actionList, String actionType) {
		if (StringUtils.isNotBlank(actionType)) {
			if (CollectionUtils.isNotEmpty(actionList)) {
				for (int i = 0; i < actionList.size(); i++) {
					WfAction wfAction = actionList.get(i);
					String acType = wfAction.getActionType();
					if (actionType.equals(acType)) {
						actionList.remove(wfAction);
					}
				}
			}
		}
	}
	
	/**
	 * 获取下一环节列表
	 */
	public List<WfStep> getNextWfSteps(String baseSchema, String defName, String entryId, String stepCode, Map inputs) {
		List<WfStep> nextWfSteps = null;
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		try {
			nextWfSteps = bizFacade.getNextWfSteps(entryId, null, baseSchema, defName, stepCode, ProcessUtil.putAll(inputData, inputs));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return nextWfSteps;
	}
	
	public WfStep getCurrentWfStep(String baseSchema, String entryId, String stepCode) {
		WfStep wfStep = null;
		try {
			wfStep = bizFacade.getCurrentWfStep(baseSchema, entryId, stepCode, null);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return wfStep;
	}
	
	public ProcessTask getCurrentTaskById(String taskId) {
		String sql = "select * from BS_T_WF_CURRENTTASK t where t.id='"+taskId+"'";
		QueryAdapter con  = new QueryAdapter();
		DataTable rs = con.executeQuery(sql, null, 0);
		return convertTask(rs);
	}
	
	public ProcessTask getHistoryTaskById(String taskId) {
		String sql = "select * from BS_T_WF_HISTORYTASK t where t.id='"+taskId+"'";
		QueryAdapter con  = new QueryAdapter();
		DataTable rs = con.executeQuery(sql, null, 0);
		return convertTask(rs);
	}
	
	/**
	 * 反查dealProcess
	 */
	public DealProcessModel checkPerm(String baseId, String baseSchema, UserSession user) {
		DealProcessModel dp = null;
		try {
			dp = bizFacade.checkPerm(baseId, baseSchema, user.getLoginName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return dp;
	}
	
	public Map getSheetInfor(String baseId, String baseSchema) {
		baseDao.setEntityName(baseSchema);
		return (Map) baseDao.get(baseId);
	}
	
	/**
	 * 获取该流程的所有维度字段
	 */
	public String getDimensionStr(String baseSchema) {
		StringBuffer sb = new StringBuffer();
		List<Dimension> dims = roleService.getDimensionBySchema(baseSchema);
		if (CollectionUtils.isNotEmpty(dims)) {
			for (int i = 0; i < dims.size(); i++) {
				Dimension dim = dims.get(i);
				String filedName = dim.getDimCode();
				String fieldid = dim.getFieldid();
				if (StringUtils.isNotBlank(fieldid)) {
					filedName = fieldid;
				}
				sb.append(filedName + ";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取该流程的所有分支条件上用到的字段
	 */
	public String getConditionStr(String baseSchema, String defName) {
		String rtn = "";
		try {
			List<String> conditions = bizFacade.getConditions(baseSchema, defName);
			if (CollectionUtils.isNotEmpty(conditions)) {
				for (int i = 0; i < conditions.size(); i++) {
					rtn += conditions.get(i);
					if (i != (conditions.size() - 1)) {
						rtn +=";";
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * 角色细分匹配
	 */
	public String[] matchRole(String baseSchema, String defName, String stepCode, Map inputs) {
		String[] chi = new String[2];
		StringBuffer chiName = new StringBuffer();
		StringBuffer chiCode = new StringBuffer();
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		ProcessUtil.putAll(inputData, inputs);
		List<ChildRole> chiRoles = null; 
		try {
			chiRoles = bizFacade.matchRole(baseSchema, defName, stepCode, inputData);
			if (CollectionUtils.isNotEmpty(chiRoles)) {
				for (int i = 0; i < chiRoles.size(); i++) {
					ChildRole chiRole = chiRoles.get(i);
					String code = "R#:"+chiRole.getChildRoleId()+"#:NEXT#:2#:#:#:#:#:"+stepCode+"#:#:#;";
					chiName.append(chiRole.getChildRoleName()+";");
					chiCode.append(code);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		chi[0] = chiName.toString();
		chi[1] = chiCode.toString();
		return chi;
	}

	public HibernateMapDaoImpl getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(HibernateMapDaoImpl baseDao) {
		this.baseDao = baseDao;
	}

	public List<DealProcessModel> convert(DataTable rs) {
		List<DealProcessModel> rtn = new ArrayList<DealProcessModel>();
		if (rs != null) {
			for (int i = 0; i < rs.length(); i++) {
				DealProcessModel dp = new DealProcessModel();
				DataRow dr = rs.getDataRow(i);
				String processId = dr.getString("processId");
				String taskId = dr.getString("taskId");
				String stepId = dr.getString("stepId");
				String phaseNo = dr.getString("phaseNo");
				String processType = dr.getString("processType");
				String actorType = dr.getString("actorType");
				String assigneeId = dr.getString("assigneeId");
				String assignee = dr.getString("assignee");
				String assignGroupId = dr.getString("assignGroupId");
				String assignGroup = dr.getString("assignGroup");
				String dpDesc = dr.getString("dpDesc");
				dp.setProcessId(processId);
				dp.setTaskId(taskId);
				dp.setStepId(stepId);
				dp.setPhaseNo(phaseNo);
				dp.setProcessType(processType);
				dp.setActorType(actorType);
				dp.setAssignee(assignee);
				dp.setAssigneeId(assigneeId);
				dp.setAssignGroup(assignGroup);
				dp.setAssignGroupId(assignGroupId);
				dp.setDpDesc(dpDesc);
				rtn.add(dp);
			}
		}
		return rtn;
	}
	
	private ProcessTask convertTask(DataTable rs) {
		ProcessTask task = null;
		if (rs != null && rs.length() > 0) {
			task = new ProcessTask();
			DataRow dr = rs.getDataRow(0);
			String id = dr.getString("id");
			int flagActive = dr.getInt("flagactive");
			String stepCode = dr.getString("stepCode");
			String entryId = dr.getString("entryid");
			String parentEntryId = dr.getString("parententryid");
			String processState = dr.getString("processstate");
			long acceptDate = dr.getLong("acceptdate");
			long dueDate = dr.getLong("duedate");
			String taskName = dr.getString("taskname");
			String defName = dr.getString("defname");
			task.setId(id);
			task.setFlagActive(flagActive);
			task.setStepCode(stepCode);
			task.setEntryId(entryId);
			task.setParentEntryId(parentEntryId);
			task.setProcessState(processState);
			task.setAcceptDate(acceptDate);
			task.setDueDate(dueDate);
			task.setTaskName(taskName);
			task.setDefName(defName);
		}
		return task;
	}

	public OwnFieldsService getOwnFieldsService() {
		return ownFieldsService;
	}

	public void setOwnFieldsService(OwnFieldsService ownFieldsService) {
		this.ownFieldsService = ownFieldsService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
