package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.Map;

import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public interface ProcessService
{
	/**
	 * 工单打开逻辑
	 * @param displayCxt 工单打开上下文对象
	 */
	public void view(WorksheetDisplayContext displayCxt);
	
	/**
	 * 工单逻辑
	 * @param commitCxt
	 * @return
	 */
	public String save(WorksheetCommitContext commitCxt);
	
	public Map<String, EditableFieldModel> checkAction(String baseID, String baseSchema, String taskID, UserSession userInfo, String actionID, String actionType);
}
