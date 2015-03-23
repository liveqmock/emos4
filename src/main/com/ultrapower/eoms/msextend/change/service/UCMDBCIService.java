package com.ultrapower.eoms.msextend.change.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.model.CIRelevance;

public interface UCMDBCIService {
	/**
	 * 根据CI类型名称获取属性名-属性显示名键值对
	 * @param id
	 * @return
	 */
	public Map<String,String> getAttributesByTypeName(String typeName);
	/**
	 * 根据CI的全局ID和类别名称获取属性显示名-属性值的键值对，可以指定要查询哪些属性
	 */
	public Map<String, String> getAttributeMapByCIGID(String typeName,String id);
	/**
	 * 根据CIType和CIName查询CI项
	 */
	public List<CIRelevance> getCIByTypeAndName(String cit,String name);
	/**
	 * 获取所有的CI类
	 */
	public List<CIClass> getAllClasses();
}
