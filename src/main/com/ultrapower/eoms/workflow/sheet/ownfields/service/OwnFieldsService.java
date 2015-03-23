package com.ultrapower.eoms.workflow.sheet.ownfields.service;

import java.util.List;

import com.ultrapower.eoms.workflow.sheet.ownfields.model.OwnFields;

public interface OwnFieldsService {
	
	public void saveOrUpdate(OwnFields ownFields);
	
	public void deleteById(String id);
	
	public OwnFields getById(String id);
	
	public OwnFields getOwnFieldbyCode(String baseSchema, String fieldCode);
	
	public List<OwnFields> getAll(String baseSchema, String stepCode, String actionType);

	public List<OwnFields> getAll(String baseSchema);
}
