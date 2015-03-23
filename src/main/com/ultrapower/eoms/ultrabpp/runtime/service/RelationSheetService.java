package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.ultrabpp.runtime.model.RelationSheetModel;

public interface RelationSheetService
{
	//根据baseID得到与当前工单关联的工单
	public List<RelationSheetModel> getRelationList(String baseID);
	//接触工单关联关系
	public void removeRelationById(String baseID,String relationBaseID);
	//添加关联关系（异步）
	public String addRelation(String baseID,String relationBaseID,String realtionTaskID);
	//关联建单，根据实现类定制关联工单的表单数据
	public HashMap<String, String> buildRelateData(HashMap<String, String> relateDataMap,String relateBaseID,String relateBaseSchema,String baseSchema);
}
