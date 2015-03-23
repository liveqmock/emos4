package com.ultrapower.eoms.ultrabpp.runtime.web;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class RelateProcessAction extends BaseAction {

	private String baseId;
	private String baseSchema;
	private String baseSn;
	private String taskId;
	private String flagActive;
	private String copyRelateData;
	
	private String whereSql;
	private String relateWhereSql;
	
	private ExtendFunctionCacheService extendFuncCacheService;
	private String relateType;
	private List<WfType> relateSchemas;
	
	
	public String list() {
		relateType = extendFuncCacheService.getRelateType(baseSchema);
		relateSchemas = extendFuncCacheService.getRelateSchemas(baseSchema);
		whereSql = " baseid='"+baseId+"' ";
		relateWhereSql = " relatebaseid='"+baseId+"' ";
		HashMap map = new HashMap();
		map.put("whereSql", whereSql);
		map.put("relateWhereSql", relateWhereSql);
		this.getRequest().setAttribute("valuemap",map);
		return findForward("relateWorkSheets.jsp");
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	public String getRelateWhereSql() {
		return relateWhereSql;
	}

	public void setRelateWhereSql(String relateWhereSql) {
		this.relateWhereSql = relateWhereSql;
	}

	public String getBaseSn() {
		return baseSn;
	}

	public void setBaseSn(String baseSn) {
		this.baseSn = baseSn;
	}

	public ExtendFunctionCacheService getExtendFuncCacheService() {
		return extendFuncCacheService;
	}

	public void setExtendFuncCacheService(
			ExtendFunctionCacheService extendFuncCacheService) {
		this.extendFuncCacheService = extendFuncCacheService;
	}

	public List<WfType> getRelateSchemas() {
		return relateSchemas;
	}

	public void setRelateSchemas(List<WfType> relateSchemas) {
		this.relateSchemas = relateSchemas;
	}

	public String getRelateType() {
		return relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}

	public String getFlagActive() {
		return flagActive;
	}

	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}

	public String getCopyRelateData() {
		return copyRelateData;
	}

	public void setCopyRelateData(String copyRelateData) {
		this.copyRelateData = copyRelateData;
	}

}
