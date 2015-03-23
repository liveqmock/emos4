package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.Map;

import com.ultrapower.eoms.ultrabpp.runtime.model.EntryAttributeModel;

public interface EntryAttributeService
{
	public Map<String, String> getAttributes(String baseID, String baseSchema);
	
	public Map<String, EntryAttributeModel> getAttributeModels(String baseID, String baseSchema);
	
	public Map<String, String> buildAttributeMap(String baseID,String baseSchema, Map<String, String> fieldMap);
	
	public void saveAttributes(String baseID, String baseSchema, boolean isNew, Map<String, String> attrMap);
	
	public void saveAttributes(String baseID, String baseSchema, Map<String, EntryAttributeModel> attrMap);
	
	public void clearAttributes(String baseID, String baseSchema);
}