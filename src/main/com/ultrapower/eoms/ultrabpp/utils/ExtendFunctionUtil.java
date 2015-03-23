package com.ultrapower.eoms.ultrabpp.utils;

import java.util.Map;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;

public class ExtendFunctionUtil
{
	public static ActionModel buildSpecialFreeAction(String actionName, String type, String label, int hasNext)
	{
		ActionModel model = new ActionModel();
		model.setActionName(actionName);
		model.setActionType(type);
		model.setLabel(label);
		model.setIsFree(1);
		model.setHasNext(hasNext);
		return model;
	}
	
	public static ActionModel getStandardFreeAction(String type)
	{
		MetaCacheService metaCacheService = (MetaCacheService)WebApplicationManager.getBean("metaCacheService");
		Map<String, ActionModel> actionMap = metaCacheService.getFreeActionMap();
		return actionMap.get(type);
	}
}
