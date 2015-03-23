package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BS_T_BPP_ASSINGTREECONFIG")
public class AssignTreeConfigModel
{
	private String id;
	private String baseSchema;
    private String stepNo;
    private String stepDesc;
    private String actionName;
    private String actionLabel;
    private String fieldName;
    private String fieldLabel;
    private String selectType;
    private String tabShow;
    
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
	public String getStepNo()
	{
		return stepNo;
	}
	public void setStepNo(String stepNo)
	{
		this.stepNo = stepNo;
	}
	public String getStepDesc()
	{
		return stepDesc;
	}
	public void setStepDesc(String stepDesc)
	{
		this.stepDesc = stepDesc;
	}
	public String getActionName()
	{
		return actionName;
	}
	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}
	public String getActionLabel()
	{
		return actionLabel;
	}
	public void setActionLabel(String actionLabel)
	{
		this.actionLabel = actionLabel;
	}
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public String getFieldLabel()
	{
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel)
	{
		this.fieldLabel = fieldLabel;
	}
	public String getSelectType()
	{
		return selectType;
	}
	public void setSelectType(String selectType)
	{
		this.selectType = selectType;
	}
	public String getTabShow()
	{
		return tabShow;
	}
	public void setTabShow(String tabShow)
	{
		this.tabShow = tabShow;
	}
    

}
