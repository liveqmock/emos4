package com.ultrapower.wfinterface.core.model;

public class WorkflowField
{
	private String fieldName;
	private String fieldText;
	private String dbColName;
	private int dataType;
	private String value;
	private boolean isCondition = false;
	
	public WorkflowField() {};
	
	public WorkflowField(String dbColName, String fieldName, String fieldText, int dataType, String value, boolean isCondition)
	{
		this.fieldName = fieldName;
		this.fieldText = fieldText;
		this.dbColName = dbColName;
		this.dataType = dataType;
		this.value = value;
		this.isCondition = isCondition;
	}
	
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public String getFieldText()
	{
		return fieldText;
	}
	public void setFieldText(String fieldText)
	{
		this.fieldText = fieldText;
	}
	public String getDbColName()
	{
		return dbColName;
	}
	public void setDbColName(String dbColName)
	{
		this.dbColName = dbColName;
	}
	public int getDataType()
	{
		return dataType;
	}
	public void setDataType(int dataType)
	{
		this.dataType = dataType;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}

	public boolean isCondition()
	{
		return isCondition;
	}

	public void setCondition(boolean isCondition)
	{
		this.isCondition = isCondition;
	}
	
}
