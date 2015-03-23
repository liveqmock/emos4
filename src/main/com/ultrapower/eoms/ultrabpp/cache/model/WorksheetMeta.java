package com.ultrapower.eoms.ultrabpp.cache.model;

import java.util.*;

import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class WorksheetMeta
{
    private List<StepMeta> steps;

    private WfType worksheetModel;

    private List<FreeActionFieldRelationModel> freeActionFields;
    
    private Map<String,BaseField> fields;
    private Map<String,BaseField> actionFields;
    
    private String layoutJSON;

    public List<StepMeta> getSteps()
    {
	return steps;
    }

    public void setSteps(List<StepMeta> steps)
    {
	this.steps = steps;
    }

    public WfType getWorksheetModel()
    {
	return worksheetModel;
    }

    public void setWorksheetModel(WfType worksheetModel)
    {
	this.worksheetModel = worksheetModel;
    }

    public List<FreeActionFieldRelationModel> getFreeActionFields()
    {
	return freeActionFields;
    }

    public void setFreeActionFields(
	    List<FreeActionFieldRelationModel> freeActionFields)
    {
	this.freeActionFields = freeActionFields;
    }

    public Map<String, BaseField> getFields()
    {
        return fields;
    }

    public void setFields(Map<String, BaseField> fields)
    {
        this.fields = fields;
    }

	public Map<String, BaseField> getActionFields()
	{
		return actionFields;
	}

	public void setActionFields(Map<String, BaseField> actionFields)
	{
		this.actionFields = actionFields;
	}

	public String getLayoutJSON()
	{
		return layoutJSON;
	}

	public void setLayoutJSON(String layoutJSON)
	{
		this.layoutJSON = layoutJSON;
	}



}