package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.Map;

import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public interface WorkSheetService
{
//	public void saveBaseInfo(String baseID, String baseSchema, boolean isAddNew, Map<String, String> fieldMap, UserInfo userInfo, EngineModel engineModel);
	
	public void save(String baseID, String baseSchema, boolean isAddNew, Map<String, String> fieldMap, ProcessTask task, String actoinId, UserSession userInfo, EngineModel engineModel);
	
	public Map<String, String> getDataMap(String baseID, String baseSchema);
	
}
