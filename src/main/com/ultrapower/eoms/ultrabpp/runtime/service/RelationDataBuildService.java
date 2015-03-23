package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.HashMap;

public interface RelationDataBuildService
{
	//客户化接口，需要实现该接口，然后按照规则在spring配置文件中命名bean即可，
	public HashMap<String, String> buildRelateData(HashMap<String, String> relateDataMap,String relateBaseID,String relateBaseSchema,String baseSchema);
}
