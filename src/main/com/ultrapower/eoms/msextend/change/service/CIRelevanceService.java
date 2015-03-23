package com.ultrapower.eoms.msextend.change.service;

import java.util.List;

import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.model.CIRelevance;

public interface CIRelevanceService {
	public List<CIRelevance> getCIsBySchemaAndBaseid(String schema,String baseid);
	public List<CIClass> getAllCIClasses();
	/**
	 * 加载所有的CI类别
	 */
	public List<CIClass> loadAllCIClasses();
	/**
	 * 获取工单关联的配置项
	 */
	public List<CIRelevance> getCIRelevanceBySchemaBaseid(String baseschema,String baseid);
	public void saveCIRelevance(List<CIRelevance> ciRelevances);
	public void deleteCIRelevance(String id);
}
