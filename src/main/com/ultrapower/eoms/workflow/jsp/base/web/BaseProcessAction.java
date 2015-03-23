package com.ultrapower.eoms.workflow.jsp.base.web;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.workflow.jsp.base.service.IProcessService;
import com.ultrapower.eoms.workflow.jsp.util.ProcessUtil;
import com.ultrapower.eoms.workflow.util.WorkflowUtils;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.engine.core.model.WfAction;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.utils.WfEngineConstants;

public class BaseProcessAction extends BaseAction{
	
	private static Logger log = LoggerFactory.getLogger(BaseProcessAction.class);
	
	private IWfSortManager sortManager = WorkFlowServiceClient.clientInstance().getSortService();
	private String baseId;
	private String baseSchema;
	private String actorStr;
	private String actorName;
	private String auditActorStr;
	private String copyActorStr;//将审批和抄送的处理人分开主要是未处理连审带派的轻型
	private Map sheetFeildsMap = new HashMap();// 工单字段map
	private Map modLogMap = new HashMap();
	private UserSession user;
	private List<WfAction> wfActions;
	private List<WfAction> leftWfActions;//工单左侧按钮列表
	private List<WfAction> rightWfActions;//工单右侧按钮列表
	private WfAction wfAction;//当前提交动作
	private ProcessTask task;
	private List<DealProcessModel> dealProcessList;//驳回、催办、建议等用到的环节列表
	private String msg;//流程引擎返回值提示
	private String error;
	private String dimAndCon;//维度字符串 //流程用到的所有条件判断的字符串
	
	public BaseProcessAction() {
		user = getUserSession();
	}
	
	/**
	 * 新建工单
	 * @return
	 */
	public String add() {
		try {
			//用户信息
			sheetFeildsMap.put("baseCreatorLoginName", user.getLoginName());
			sheetFeildsMap.put("baseCreatorDepId", user.getDepId());
			sheetFeildsMap.put("baseCreatorCorpId", user.getCompanyId());
			sheetFeildsMap.put("baseCreatorDN", user.getDepDns());
			sheetFeildsMap.put("baseCreatorFullName", user.getFullName());
			sheetFeildsMap.put("baseCreatorConnectWay", user.getPhone());
			sheetFeildsMap.put("baseCreatorDep", user.getDepName());
			sheetFeildsMap.put("baseCreatorCorp", user.getCompanyName());
			//工单类型信息
			WfType wfType = sortManager.getWfTypeByCode(baseSchema);
			sheetFeildsMap.put("baseCatagoryId", wfType.getSortId());
			sheetFeildsMap.put("baseWorkflowFlag", wfType.getWfType());
			sheetFeildsMap.put("baseSchema", wfType.getCode());
			sheetFeildsMap.put("baseName", wfType.getName());
			sheetFeildsMap.put("baseTplId", wfType.getWfDefaultVersion());
			//工单附件
			sheetFeildsMap.put("baseAttachGUID", TimeUtils.getCurrentTime() + "");
			
			task = new ProcessTask();
			task.setId(null);
			task.setFlagActive(1);
			
			IProcessService processService = getProcessService(baseSchema);
			wfActions = processService.getAvailableActions(user, baseSchema, task);
			layoutWfAction(wfActions);
			dimAndCon = dimAndCon(processService, baseSchema, null);
		} catch (RemoteException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return findForward("edit");
	}
	
	/**
	 * 查看工单
	 * @return
	 */
	public String view() {
		String page = "view";
		try {
			IProcessService processService = getProcessService(baseSchema);
			sheetFeildsMap = processService.getSheetInfor(baseId, baseSchema);
			
			String prePhaseNo = "";
			long flagActive = 0;
			String taskId = null;
			if (task == null || StringUtils.isBlank(task.getId())) {//从工单列表打开taskId为空，需要调用流程引擎反查taskId
				DealProcessModel dp = processService.checkPerm(baseId, baseSchema, user);
				if (dp != null) {
					prePhaseNo = dp.getPrePhaseNo();
					flagActive = dp.getFlagActive();
					if (1 == flagActive) {
						task = processService.getCurrentTaskById(dp.getTaskId());
					} else {
						task = processService.getHistoryTaskById(dp.getTaskId());
					}
				}
			} else {
				taskId = task.getId();
				task = processService.getCurrentTaskById(taskId);
				flagActive = task.getFlagActive();
			}
			
			if (task != null) {
				taskId = task.getId();
				if (StringUtils.isNotBlank(task.getId())) {
					dimAndCon = dimAndCon(processService, baseSchema, task.getDefName());
					wfActions = processService.getAvailableActions(user, baseSchema, task);
					layoutWfAction(wfActions);
					String entryId = task.getEntryId();
					String stepCode = task.getStepCode();
					Object obj = sheetFeildsMap.get("baseStatus");
					if (obj != null) {
						if (obj.equals("草稿")) {
							if (1 == flagActive && (WfEngineConstants.STEP_START_CODE.equals(prePhaseNo) || StringUtils.isBlank(prePhaseNo)) && StringUtils.isBlank(task.getParentEntryId())) {
								page = "edit";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return findForward(page);
	}
	
	/**
	 * 加载环节页面
	 * @return
	 */
	public String showStep() {
		IProcessService processService = getProcessService(baseSchema);
		String page = wfAction.getPage();
		String actionType = wfAction.getActionType();
		String decode = null;
		try {
			decode = java.net.URLDecoder.decode(wfAction.getActionName(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(baseSchema)) {
			//驳回、追回等动作需要获取dealprocess列表
			dealProcessList = processService.getProcessList(actionType, user, baseId, baseSchema);
		}
		if (WfEngineConstants.ACTION_NEXT.equals(actionType)) {//只有下一步动作才进行角色细分匹配（自由流动作不匹配）
			Map convertMap = ProcessUtil.convertMapFormat(baseSchema, sheetFeildsMap);
			List<WfStep> nextWfSteps = processService.getNextWfSteps(baseSchema, null, task.getEntryId(), task.getStepCode(), convertMap);
			if (CollectionUtils.isNotEmpty(nextWfSteps)) {
				WfStep wfStep = nextWfSteps.get(0);
				boolean auto = wfStep.isAuto();
				String stepCode = wfStep.getId();
				String subName = wfStep.getSubName();
				String type = wfStep.getType();
				String[] matchRole = processService.matchRole(baseSchema, null, stepCode, convertMap);
				if (matchRole != null) {
					if (StringUtils.isNotBlank(matchRole[0])) {
						actorName = matchRole[0];
						actorStr = matchRole[1];
					}
				}
				if (auto) {
					if (StringUtils.isNotBlank(subName)) {//固定子流程
						
					} else {//自由子流程
						if (WfEngineConstants.STEP_TYPE_DEAL.equals(type)) {
							wfAction.setActorActionType(WfEngineConstants.ACTION_ASSIGN);
						} else if (WfEngineConstants.STEP_TYPE_AUDITING.equals(type)) {
							wfAction.setActorActionType(WfEngineConstants.ACTION_AUDIT);
						} else if (WfEngineConstants.STEP_TYPE_ASSIST.equals(type)) {
							wfAction.setActorActionType(WfEngineConstants.ACTION_ASSIST);
						} else if (WfEngineConstants.STEP_TYPE_COPY.equals(type)) {
							wfAction.setActorActionType(WfEngineConstants.ACTION_MAKECOPY);
						}
					}
					wfAction.setRadio("1");
				}
			}
		}
		return findForward(page);
	}
	
	/**
	 * 工单保存
	 * @return
	 */
	public String save() {
		try {
			IProcessService processService = getProcessService(baseSchema);
			String actor = "";//处理连审带派
			if (StringUtils.isNotBlank(auditActorStr) && StringUtils.isNotBlank(copyActorStr)) {
				auditActorStr += copyActorStr;
				actor = actorStr;
			} else {
				if (StringUtils.isNotBlank(actorStr)) {
					actor += actorStr;
				}
				if (StringUtils.isNotBlank(copyActorStr)) {
					actor += copyActorStr;
				}
			}
			msg = processService.save(baseSchema, wfAction, task, user, actor, auditActorStr, 
					ProcessUtil.convertMapFormat(baseSchema, sheetFeildsMap), 
					ProcessUtil.convertMapFormat(baseSchema, modLogMap));
			if (StringUtils.isNotBlank(msg)) {
				msg = msg.replace("\n", "<br>");
			}
		} catch (Exception e) {
			error = e.getMessage();
			log.error(error, e);
			e.printStackTrace();
		}
		return "close";
	}

	/**
	 * 根据流程标识baseSchema获取processService
	 * @param baseSchema
	 * @return
	 */
	public IProcessService getProcessService(String baseSchema) {
		IProcessService processService = null;
		try {
			processService = (IProcessService) WebApplicationManager.getBean(baseSchema.toLowerCase() + "ProcessService");
		} catch (Exception e) {
			processService = (IProcessService) WebApplicationManager.getBean("baseProcessService");
		}
		return processService;
	}
	
	/**
	 * 对按钮进行布局
	 * @param wfActions
	 */
	protected void layoutWfAction(List<WfAction> wfActions) {
		if (CollectionUtils.isNotEmpty(wfActions)) {
			String[] fixRightActions = null;
			String[] freeRightActions = null;
			try {
				WfType wfType = sortManager.getWfTypeByCode(baseSchema);
				String fixStr = wfType.getBaseCategoryBtnFixIds();
				String freeStr = wfType.getBaseCategoryBtnFreeIds();
				if (StringUtils.isNotBlank(fixStr)) {
					fixRightActions = fixStr.split(";");
				}
				if (StringUtils.isNotBlank(freeStr)) {
					freeRightActions = freeStr.split(";");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			leftWfActions = new ArrayList<WfAction>();
			rightWfActions = new ArrayList<WfAction>();
			boolean isHasFix = false;
			for (int i = 0; i < wfActions.size(); i++) {
				WfAction action = wfActions.get(i);
				String actionType = action.getActionType();
				if (WfEngineConstants.ACTION_NEXT.equals(actionType)) {
					isHasFix = true;
					break;
				}
			}
			if (isHasFix) {
				leftRightActions(fixRightActions, wfActions);
			} else {
				leftRightActions(freeRightActions, wfActions);
			}
		}
	}
	
	private void leftRightActions(String[] actions, List<WfAction> wfActions) {
		WfAction saveAction = null;
		WfAction acceptAction = null;
		WfAction noticeAction = null;
		for (int i = 0; i < wfActions.size(); i++) {
			WfAction wfAction = wfActions.get(i);
			String actionType = wfAction.getActionType();
			if (WfEngineConstants.ACTION_NEXT.equals(actionType)) {
				leftWfActions.add(wfAction);
				continue;
			} else if (WfEngineConstants.ACTION_SAVE.equals(actionType)) {
				saveAction = wfAction;
				continue;
			}  else if (WfEngineConstants.ACTION_ACCEPT.equals(actionType)) {
				acceptAction = wfAction;
				continue;
			} else if (WfEngineConstants.ACTION_NOTICE.equals(actionType)) {
				noticeAction = wfAction;
				continue;
			} else {
				if (!ArrayUtils.isEmpty(actions)) {
					boolean isExist = false;
					for (int j = 0; j < actions.length; j++) {
						if (actionType.equalsIgnoreCase(actions[j])) {
							isExist = true;
							break;
						}
					}
					if (isExist) {
						rightWfActions.add(wfAction);
					} else {
						leftWfActions.add(wfAction);
					}
				} else {
					leftWfActions.add(wfAction);
				}
			}
		}
		if (noticeAction != null) {
			leftWfActions.add(0, noticeAction);
		}
		if (acceptAction != null) {
			leftWfActions.add(0, acceptAction);
		}
		if (saveAction != null) {
			leftWfActions.add(0, saveAction);
		}
	}
	
	/**
	 * 处理维度和分支条件字符串
	 * @param processService
	 * @param baseSchema
	 * @param defName
	 * @return
	 */
	private String dimAndCon(IProcessService processService, String baseSchema, String defName) {
		String dimensionStr = processService.getDimensionStr(baseSchema);
		String conditionStr = processService.getConditionStr(baseSchema, defName);
		return WorkflowUtils.unRepeat(dimensionStr, conditionStr);
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public Map getSheetFeildsMap() {
		return sheetFeildsMap;
	}

	public void setSheetFeildsMap(Map sheetFeildsMap) {
		this.sheetFeildsMap = sheetFeildsMap;
	}

	public UserSession getUser() {
		return user;
	}

	public void setUser(UserSession user) {
		this.user = user;
	}

	public Map getModLogMap() {
		return modLogMap;
	}

	public void setModLogMap(Map modLogMap) {
		this.modLogMap = modLogMap;
	}

	public WfAction getWfAction() {
		return wfAction;
	}

	public void setWfAction(WfAction wfAction) {
		this.wfAction = wfAction;
	}

	public String getActorStr() {
		return actorStr;
	}

	public void setActorStr(String actorStr) {
		this.actorStr = actorStr;
	}

	public ProcessTask getTask() {
		return task;
	}

	public void setTask(ProcessTask task) {
		this.task = task;
	}

	public List<DealProcessModel> getDealProcessList() {
		return dealProcessList;
	}

	public void setDealProcessList(List<DealProcessModel> dealProcessList) {
		this.dealProcessList = dealProcessList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getDimAndCon() {
		return dimAndCon;
	}

	public void setDimAndCon(String dimAndCon) {
		this.dimAndCon = dimAndCon;
	}

	public List<WfAction> getLeftWfActions() {
		return leftWfActions;
	}

	public void setLeftWfActions(List<WfAction> leftWfActions) {
		this.leftWfActions = leftWfActions;
	}

	public List<WfAction> getRightWfActions() {
		return rightWfActions;
	}

	public void setRightWfActions(List<WfAction> rightWfActions) {
		this.rightWfActions = rightWfActions;
	}

	public String getAuditActorStr() {
		return auditActorStr;
	}

	public void setAuditActorStr(String auditActorStr) {
		this.auditActorStr = auditActorStr;
	}

	public String getCopyActorStr() {
		return copyActorStr;
	}

	public void setCopyActorStr(String copyActorStr) {
		this.copyActorStr = copyActorStr;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
