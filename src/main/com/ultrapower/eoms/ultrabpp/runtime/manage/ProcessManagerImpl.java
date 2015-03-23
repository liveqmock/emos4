package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.cache.service.ThemeCacheService;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.EntryAttributeService;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.eoms.ultrabpp.runtime.service.ProcessService;
import com.ultrapower.eoms.ultrabpp.runtime.service.RelationSheetService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkSheetService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowRoleService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.utils.UUIDGenerator;

@Transactional
public class ProcessManagerImpl implements ProcessService {
	
	private WorkSheetService workSheetService;
	private WorkflowService workflowService;
	private WorkflowRoleService workflowRoleService;
	private IWfSortManager ver;
	private ExtendFunctionCacheService extendFuncCacheService;
	private EntryAttributeService entryAttributeService;
	private ThemeCacheService themeCacheService;
	private MetaCacheService metaCacheService;
	private RelationSheetService relationSheetService;

	public Map<String, EditableFieldModel> checkAction(String baseID, String baseSchema,
			String taskID, UserSession userInfo, String actionID, String actionType) {
		ProcessTask currentTask = workflowService.getCurrentProcess(baseID, baseSchema, userInfo, taskID);
		Map<String, EditableFieldModel> fields = null;
		if(currentTask.getFlagPreDefined() == 1)
		{
			fields = workflowService.getEditableFieldsByAction(baseSchema, currentTask, actionID);
		}
		else
		{
			fields = workflowService.getEditableFieldsByAction(baseSchema, currentTask, actionType);
		}
		return fields;
	}

	public String save(WorksheetCommitContext commitCxt) {
		String msg = "";
		UserSession userInfo = commitCxt.getUserInfo();
		long currentTime = TimeUtils.getCurrentTime();
		boolean addNew = false;
		String baseID = commitCxt.getBaseID();
		String baseSchema = commitCxt.getBaseSchema();
		String taskID = commitCxt.getTaskID();
		String actionID = commitCxt.getActionID();
		String actionType = commitCxt.getActionType();
		String actionText = commitCxt.getActionText();
		ProcessTask task = workflowService.getCurrentProcess(baseID, baseSchema, userInfo, taskID);
		commitCxt.setCurrentTask(task);
		commitCxt.setFlagActive(task.getFlagActive());
		
		List<ExtendFunction> extendFuncList = extendFuncCacheService.getFormCommitFuncList(baseSchema);
		commitCxt.setExtendFuncList(extendFuncList);
		
		Map<String, String> fieldMap = commitCxt.getFieldMap();
		if (StringUtils.isBlank(baseID)) {
			baseID = UUIDGenerator.getId();
			commitCxt.setBaseID(baseID);
			addNew = true;
		}
		Map<String, String> attributeMap = entryAttributeService.buildAttributeMap(baseID,baseSchema, fieldMap);
		commitCxt.setAttributeMap(attributeMap);
		Map<String, String> dbDataMap = null;
		if (!addNew) {
			dbDataMap = workSheetService.getDataMap(baseID, baseSchema);
			dbDataMap.putAll(fieldMap);
			fieldMap = dbDataMap;
			commitCxt.setFieldMap(fieldMap);
		}
		
		//提交第一阶段  BEGIN
		if (CollectionUtils.isNotEmpty(extendFuncList)) {
			for (int i = 0; i < extendFuncList.size(); i++) {
				ExtendFunction extendFunction = extendFuncList.get(i);
				extendFunction.commitPhase1(commitCxt);
			}
		}
		baseID = commitCxt.getBaseID();
		baseSchema = commitCxt.getBaseSchema();
		taskID = commitCxt.getTaskID();
		actionID = commitCxt.getActionID();
		actionType = commitCxt.getActionType();
		actionText = commitCxt.getActionText();
		task = commitCxt.getCurrentTask();
		commitCxt.getFieldMap().put("bpp_Sys_AssignString", commitCxt.getAssignString());
		
		//提交第一阶段 END
		
		fieldMap.putAll(attributeMap);
		
		
		
		EngineModel engineModel = workflowService.doAction(baseID, baseSchema, userInfo, task, actionID, actionType, actionText, fieldMap);
		
		commitCxt.setOldTasks(engineModel.getOldTasks());
		commitCxt.setNewTasks(engineModel.getNewTasks());
		commitCxt.getFieldMap().put("BASESTATUS", engineModel.getEntryState());
		commitCxt.getFieldMap().put("BASESN", engineModel.getBaseSn());
		commitCxt.getFieldMap().put("BASECREATEDATE", String.valueOf(engineModel.getBaseCreateTime()));
		commitCxt.getFieldMap().put("BASECLOSEDATE", String.valueOf(engineModel.getBaseCloseTime()));
		commitCxt.setEngineModel(engineModel);
		
		//提交第二阶段  BEGIN
		if (CollectionUtils.isNotEmpty(extendFuncList)) {
			for (int i = 0; i < extendFuncList.size(); i++) {
				ExtendFunction extendFunction = extendFuncList.get(i);
				extendFunction.commitPhase2(commitCxt);
			}
		}
		baseID = commitCxt.getBaseID();
		baseSchema = commitCxt.getBaseSchema();
		taskID = commitCxt.getTaskID();
		actionID = commitCxt.getActionID();
		actionType = commitCxt.getActionType();
		actionText = commitCxt.getActionText();
		task = commitCxt.getCurrentTask();
		
		fieldMap = commitCxt.getFieldMap();
		//提交第二阶段 END
		
		workSheetService.save(baseID, baseSchema, addNew, fieldMap, task, actionID, userInfo, engineModel);
		workflowService.saveFieldModifyLog(baseID, baseSchema, addNew, task, userInfo, actionID, actionType, actionText, fieldMap, engineModel);
		entryAttributeService.saveAttributes(baseID, baseSchema, addNew, attributeMap);
		if(engineModel.getEntryStateFlag().equals("2"))
		{
			entryAttributeService.clearAttributes(baseID, baseSchema);
		}
		
		msg = engineModel.getMailContent();
		commitCxt.setReturnMsg(msg);
		
		//提交第三阶段  BEGIN
		if (CollectionUtils.isNotEmpty(extendFuncList)) {
			for (int i = 0; i < extendFuncList.size(); i++) {
				ExtendFunction extendFunction = extendFuncList.get(i);
				extendFunction.commitPhase3(commitCxt);
			}
		}
		msg = commitCxt.getReturnMsg();
		//提交第三阶段 END
		
		return msg;
	}

	public void view(WorksheetDisplayContext displayCxt)
	{
		String baseID = displayCxt.getBaseID();
		String baseSchema = displayCxt.getBaseSchema();
		UserSession userInfo = displayCxt.getUserInfo();
		String taskID = displayCxt.getTaskID();
		String editType = displayCxt.getEditType();
		
		WfType wfType = null;
		//设置主题
		try
		{
			ver = WorkFlowServiceClient.clientInstance().getSortService();
			wfType = ver.getWfTypeByCode(baseSchema);
			String baseName = wfType.getName();
			String theme = wfType.getTheme();
			if(displayCxt.getTheme() != null)
			{
				theme = displayCxt.getTheme().getName();
			}
			ThemeModel themeModel = themeCacheService.getThemeModel(theme);
			displayCxt.setTheme(themeModel);
			displayCxt.setFormFolder(baseSchema.replace(':', '_'));
			displayCxt.setBaseName(baseName);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
			return;
		}
		
		// 设置工单打开逻辑
		List<ExtendFunction> extendFuncList = extendFuncCacheService.getFormOpenFuncList(baseSchema);
		displayCxt.setExtendFuncList(extendFuncList);
		// 设置客户端逻辑
		List<String> clientFuncList = extendFuncCacheService.getClientFuncList(baseSchema);
		List<String> cFuncList = new ArrayList<String>();
		cFuncList.addAll(clientFuncList);
		cFuncList.add(baseSchema);
		displayCxt.setClientFuncList(cFuncList);
		
		displayCxt.setAttributeMap(entryAttributeService.getAttributes(baseID, baseSchema));
			
		if(!editType.equals("PREVIEWMAIN"))
		{
			// begin
			for(ExtendFunction exFunc : extendFuncList)
			{
				exFunc.beginOpen(displayCxt);
			}
		}
		
		// 获取主表和BaseInfo数据
		String baseCloseDateStr = "";
		if(editType.equals("MODIFY"))
		{
			// get主表单数据
			Map<String, String> dataMap = workSheetService.getDataMap(baseID, baseSchema);
			displayCxt.setFieldMap(dataMap);
			displayCxt.setDefCode(dataMap.get("BASETPLID"));
			baseCloseDateStr = dataMap.get("BASECLOSEDATE");
		}
		else
		{
			displayCxt.getFieldMap().put("BASECREATEDATE", "");
			displayCxt.getFieldMap().put("BASESTATUS", "草稿");
			displayCxt.getFieldMap().put("BASECREATORLOGINNAME", userInfo.getLoginName());
			displayCxt.getFieldMap().put("BASECREATORFULLNAME", userInfo.getFullName());
			displayCxt.getFieldMap().put("BASECREATORCONNECTWAY", userInfo.getPhone());
			displayCxt.getFieldMap().put("BASECREATORDEPID", userInfo.getDepId());
			displayCxt.getFieldMap().put("BASECREATORDEP", userInfo.getDepName());
			displayCxt.getFieldMap().put("BASECREATORDN", userInfo.getDepFullName());
			displayCxt.getFieldMap().put("BASECREATORDNID", userInfo.getDepDns());
			displayCxt.getFieldMap().put("BASECREATORCORPID", userInfo.getCompanyId());
			displayCxt.getFieldMap().put("BASECREATORCORP", userInfo.getCompanyName());
			
			String relateBaseID = displayCxt.getRelateBaseID();
			String relateBaseSchema = displayCxt.getRelateBaseSchema();
			//关联建单参数 是否复制数据，1：是，2：否
			String copyRelateData = displayCxt.getCopyRelateData();
			//关联建单参数 是否根据特殊逻辑为数据赋值，1：是，2：否,只有在复制数据为1时，此参数才生效
			String copyRelateByConfig = displayCxt.getCopyRelateByConfig();
			
			if (StringUtils.isNotBlank(relateBaseID) && StringUtils.isNotBlank(relateBaseSchema) && "1".equals(copyRelateData)) {
				Map<String, String> relateDataMap = workSheetService.getDataMap(relateBaseID, relateBaseSchema);
				HashMap<String, String> newHashMap = Maps.newHashMap(relateDataMap);
				//fanying关联工单字段赋值扩展逻辑
				if("1".equals(copyRelateByConfig)){
					newHashMap = relationSheetService.buildRelateData(newHashMap, relateBaseID, relateBaseSchema, baseSchema);
				}
				newHashMap.remove("BASEID");
				newHashMap.remove("BASESCHEMA");
				newHashMap.remove("BASENAME");
				newHashMap.remove("BASESN");
				newHashMap.remove("BASEENTRYID");
				newHashMap.remove("BASECREATEDATE");
				newHashMap.remove("BASESENDDATE");
				newHashMap.remove("BASEACCEPTDATE");
				newHashMap.remove("BASEFINISHDATE");
				newHashMap.remove("BASECLOSEDATE");
				newHashMap.remove("BASESTATUS");
//				newHashMap.remove("BASESUMMARY");
//				newHashMap.remove("BASEITEMS");
//				newHashMap.remove("BASEPRIORITY");
				newHashMap.remove("BASEACCEPTOUTTIME");
				newHashMap.remove("BASEDEALOUTTIME");
				newHashMap.remove("BASEDESCRIPTION");
				newHashMap.remove("BASECLOSESATISFY");
				newHashMap.remove("BASETPLID");
				newHashMap.remove("BASEISARCHIVE");
				newHashMap.remove("BASEISTRUECLOSE");
				newHashMap.remove("BASEWORKFLOWFLAG");
				newHashMap.remove("BASECATAGORYNAME");
				newHashMap.remove("BASECATAGORYID");
				newHashMap.remove("BASECREATORFULLNAME");
				newHashMap.remove("BASECREATORLOGINNAME");
				newHashMap.remove("BASECREATORCONNECTWAY");
				newHashMap.remove("BASECREATORDEP");
				newHashMap.remove("BASECREATORDEPID");
				newHashMap.remove("BASECREATORCORP");
				newHashMap.remove("BASECREATORCORPID");
				newHashMap.remove("BASECREATORDN");
				newHashMap.remove("BASECREATORDNID");
				newHashMap.remove("BASEATTACHGUID");
				displayCxt.getFieldMap().putAll(newHashMap);
			}
		}
		// getCurrentProcess
		if(editType.equals("PREVIEWMAIN")) {
			return;
		}
			
		ProcessTask task = workflowService.getCurrentProcess(baseID, baseSchema, userInfo, taskID);
		if (task != null) {
			long flagActive = task.getFlagActive();
			displayCxt.setCurrentTask(task);
			displayCxt.setFlagActive(flagActive);
			displayCxt.setTaskID(task.getId());
			// editablefield
			if (flagActive == 1) {//工单已经关闭就不要取可编辑字段属性了。
				displayCxt.setEditableFieldMap(workflowService.getEditableFields(baseSchema, task));
				displayCxt.setLayoutJSON(metaCacheService.getLayOutJsonString(baseSchema));
			}else{
				displayCxt.setLayoutJSON("[]");
			}
			// availableActions
			List<ActionModel>[] actions = workflowService.getAvailableActions(baseID, baseSchema, task);
			for(ActionModel act : actions[0])
			{
				displayCxt.getFixActionList().add(act);
			}
			for(ActionModel act : actions[1])
			{
				displayCxt.getFreeActionList().add(act);
			}
		}else{
			displayCxt.setLayoutJSON("[]");
		}
		// end
		for(ExtendFunction exFunc : extendFuncList)
		{
			exFunc.endOpen(displayCxt);
		}
	}

	public WorkSheetService getWorkSheetService() {
		return workSheetService;
	}

	public void setWorkSheetService(WorkSheetService workSheetService) {
		this.workSheetService = workSheetService;
	}

	public WorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	public WorkflowRoleService getWorkflowRoleService() {
		return workflowRoleService;
	}

	public void setWorkflowRoleService(WorkflowRoleService workflowRoleService) {
		this.workflowRoleService = workflowRoleService;
	}

	/**
	 * @return the ver
	 */
	public IWfSortManager getVer()
	{
		return ver;
	}

	/**
	 * @param ver the ver to set
	 */
	public void setVer(IWfSortManager ver)
	{
		this.ver = ver;
	}

	/**
	 * @return the extendFuncCacheService
	 */
	public ExtendFunctionCacheService getExtendFuncCacheService()
	{
		return extendFuncCacheService;
	}

	/**
	 * @param extendFuncCacheService the extendFuncCacheService to set
	 */
	public void setExtendFuncCacheService(ExtendFunctionCacheService extendFuncCacheService)
	{
		this.extendFuncCacheService = extendFuncCacheService;
	}

	/**
	 * @return the entryAttributeService
	 */
	public EntryAttributeService getEntryAttributeService()
	{
		return entryAttributeService;
	}

	/**
	 * @param entryAttributeService the entryAttributeService to set
	 */
	public void setEntryAttributeService(EntryAttributeService entryAttributeService)
	{
		this.entryAttributeService = entryAttributeService;
	}

	/**
	 * @return the themeCacheService
	 */
	public ThemeCacheService getThemeCacheService()
	{
		return themeCacheService;
	}

	/**
	 * @param themeCacheService the themeCacheService to set
	 */
	public void setThemeCacheService(ThemeCacheService themeCacheService)
	{
		this.themeCacheService = themeCacheService;
	}

	public MetaCacheService getMetaCacheService()
	{
		return metaCacheService;
	}

	public void setMetaCacheService(MetaCacheService metaCacheService)
	{
		this.metaCacheService = metaCacheService;
	}

	public RelationSheetService getRelationSheetService()
	{
		return relationSheetService;
	}

	public void setRelationSheetService(RelationSheetService relationSheetService)
	{
		this.relationSheetService = relationSheetService;
	}

}
