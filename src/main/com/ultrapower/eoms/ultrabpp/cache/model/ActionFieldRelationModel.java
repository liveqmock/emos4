package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="BS_T_BPP_ACTIONFIELDREL")
public class ActionFieldRelationModel extends EditableFieldModel
{
    private String id;
    private String baseSchema;
    private String stepNo;
    private String actionName;
    
    public String getBaseSchema()
    {
        return baseSchema;
    }

    public void setBaseSchema(String baseSchema)
    {
        this.baseSchema = baseSchema;
    }
    
    @Id
    public String getId()
    {
	return id; 
    }

    public void setId(String id)
    {
	this.id = id;
    }



    public String getStepNo()
    {
        return stepNo;
    }

    public void setStepNo(String stepNo)
    {
        this.stepNo = stepNo;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

}
