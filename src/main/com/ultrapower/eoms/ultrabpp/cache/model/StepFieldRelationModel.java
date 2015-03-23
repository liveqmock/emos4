package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ultrapower.eoms.ultrabpp.model.BaseField;

/**
 * 
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */

@Entity
@Table(name="BS_T_BPP_STEPFIELDREL")
public class StepFieldRelationModel extends EditableFieldModel
{
    private String id;
    private String stepNo;
    private String baseSchema;

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



}
