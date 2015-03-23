package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.BaseField;

@Entity
@Table(name = "BS_T_BPP_FREEACTIONFIELDREL")
public class FreeActionFieldRelationModel extends EditableFieldModel
{
    private String id;
    private String baseSchema;
    private String actionType;
    private String baseStatus;

    @Id
    public String getId()
    {
	return id;
    }

    public void setId(String id)
    {
	this.id = id;
    }

    public String getBaseSchema()
    {
	return baseSchema;
    }

    public void setBaseSchema(String baseSchema)
    {
	this.baseSchema = baseSchema;
    }

    public String getActionType()
    {
	return actionType;
    }

    public void setActionType(String actionType)
    {
	this.actionType = actionType;
    }

    public String getBaseStatus()
    {
	return baseStatus;
    }

    public void setBaseStatus(String baseStatus)
    {
	this.baseStatus = baseStatus;
    }

}