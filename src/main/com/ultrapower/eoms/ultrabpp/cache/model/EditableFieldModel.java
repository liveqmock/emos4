package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.ultrapower.eoms.ultrabpp.model.BaseField;

/**
 * 
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
@MappedSuperclass
public abstract class EditableFieldModel
{
    private String fieldID;
    private int visiable;
    private int required;
    private String fieldType;
    private BaseField baseField;

    public String getFieldID()
    {
	return fieldID;
    }

    public void setFieldID(String fieldID)
    {
	this.fieldID = fieldID;
    }

    public int getVisiable()
    {
	return visiable;
    }

    public void setVisiable(int visiable)
    {
	this.visiable = visiable;
    }

    public int getRequired()
    {
	return required;
    }

    public void setRequired(int required)
    {
	this.required = required;
    }

    public String getFieldType()
    {
	return fieldType;
    }

    public void setFieldType(String fieldType)
    {
	this.fieldType = fieldType;
    }

    @Transient
    public BaseField getBaseField()
    {
	return baseField;
    }

    public void setBaseField(BaseField baseField)
    {
	this.baseField = baseField;
    }

}