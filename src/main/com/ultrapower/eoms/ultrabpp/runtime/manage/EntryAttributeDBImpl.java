package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService;
import com.ultrapower.eoms.ultrabpp.runtime.model.EntryAttributeModel;
import com.ultrapower.eoms.ultrabpp.runtime.service.EntryAttributeService;
import com.ultrapower.workflow.utils.UUIDGenerator;

public class EntryAttributeDBImpl implements EntryAttributeService
{
	private IDao<EntryAttributeModel> entryAttrModelDao;
	private ExtendFunctionCacheService extendFuncCacheService;
	
	public Map<String, String> getAttributes(String baseID, String baseSchema)
	{
		List<String> attrList = extendFuncCacheService.getAttributeList(baseSchema);
		Map<String, String> attrDataMap = new HashMap<String, String>();
		for(String attrDef : attrList)
		{
			attrDataMap.put(attrDef, "");
		}
		
		if(baseID != null && !baseID.equals(""))
		{
			List<EntryAttributeModel> modelList = entryAttrModelDao.find("from EntryAttributeModel where baseID=? and baseSchema=?", baseID, baseSchema);
			for(EntryAttributeModel attrData : modelList)
			{
				if(attrDataMap.containsKey(attrData.getKey()))
				{
					attrDataMap.put(attrData.getKey(), attrData.getValue());
				}
			}
		}
		
		return attrDataMap;
	}
	
	public Map<String, EntryAttributeModel> getAttributeModels(String baseID, String baseSchema)
	{
		List<String> attrList = extendFuncCacheService.getAttributeList(baseSchema);
		Map<String, EntryAttributeModel> attrDataMap = new HashMap<String, EntryAttributeModel>();
		for(String attrDef : attrList)
		{
			EntryAttributeModel model = new EntryAttributeModel();
			model.setAttributeID(UUIDGenerator.getId());
			model.setBaseID(baseID);
			model.setBaseSchema(baseSchema);
			model.setKey(attrDef);
			model.setValue("");
			attrDataMap.put(attrDef, model);
		}
		
		if(baseID != null && !baseID.equals(""))
		{
			List<EntryAttributeModel> modelList = entryAttrModelDao.find("from EntryAttributeModel where baseID=? and baseSchema=?", baseID, baseSchema);
			for(EntryAttributeModel attrData : modelList)
			{
				if(attrDataMap.containsKey(attrData.getKey()))
				{
					attrDataMap.put(attrData.getKey(), attrData);
				}
			}
		}
		
		return attrDataMap;
	}
	
	public Map<String, String> buildAttributeMap(String baseID,String baseSchema, Map<String, String> fieldMap)
	{
		//List<String> attrList = extendFuncCacheService.getAttributeList(baseSchema);
		Map<String,String> attrDataMap = this.getAttributes(baseID, baseSchema);
		for(String key : attrDataMap.keySet())
		{
			if(fieldMap.containsKey(key))
			{
				String attrData = fieldMap.get(key);
				attrDataMap.put(key, attrData); 
			}
		}
		return attrDataMap;
	}

	public void saveAttributes(String baseID, String baseSchema, boolean isNew, Map<String, String> attrMap)
	{
		Map<String, EntryAttributeModel> models = getAttributeModels(baseID, baseSchema);
		for(EntryAttributeModel model : models.values())
		{
			String attrData = "";
			if(attrMap.containsKey(model.getKey()))
			{
				attrData = attrMap.get(model.getKey());
			}
			if(isNew)
			{
				model.setAttributeID(UUIDGenerator.getId());
				model.setValue(attrData);
			}
			else
			{
				model.setValue(attrData);
			}
			entryAttrModelDao.saveOrUpdate(model);
		}
	}
	
	public void saveAttributes(String baseID, String baseSchema, Map<String, EntryAttributeModel> attrMap)
	{
		List<String> attrList = extendFuncCacheService.getAttributeList(baseSchema);
		for(String attrDef : attrList)
		{
			if(attrMap.containsKey(attrDef))
			{
				EntryAttributeModel model = attrMap.get(attrDef);
				entryAttrModelDao.saveOrUpdate(model);
			}
		}
	}
	
	public void clearAttributes(String baseID, String baseSchema)
	{
		entryAttrModelDao.executeUpdate("delete EntryAttributeModel where baseID=? and baseSchema=?", baseID, baseSchema);
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
	 * @return the entryAttrModelDao
	 */
	public IDao<EntryAttributeModel> getEntryAttrModelDao()
	{
		return entryAttrModelDao;
	}

	/**
	 * @param entryAttrModelDao the entryAttrModelDao to set
	 */
	public void setEntryAttrModelDao(IDao<EntryAttributeModel> entryAttrModelDao)
	{
		this.entryAttrModelDao = entryAttrModelDao;
	}

}
