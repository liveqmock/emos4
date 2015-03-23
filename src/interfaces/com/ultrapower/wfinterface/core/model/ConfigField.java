package com.ultrapower.wfinterface.core.model;

public class ConfigField
{
	private String xmlColName;
	private String dbColName;
	private String fieldName;
	private String fieldText;
	private int dataType;
	private int dicType;
	private String dicData;
	private int length;
	private boolean isNull;
	private String defaultValue;
	
	public ConfigField() {}
	
	public ConfigField(String fieldName, String fieldText, String xmlColName, String dbColName, int dataType, int dicType, String dicData, int length, boolean isNull, String defaultValue)
	{
		this.xmlColName = xmlColName;
		this.dbColName = dbColName;
		this.fieldName = fieldName;
		this.fieldText = fieldText;
		this.dataType = dataType;
		this.dicType = dicType;
		this.dicData = dicData;
		this.length = length;
		this.isNull = isNull;
		this.defaultValue = defaultValue;
	}
	
	public String getXmlColName()
	{
		return xmlColName;
	}
	public void setXmlColName(String xmlColName)
	{
		this.xmlColName = xmlColName;
	}
	public String getDbColName()
	{
		return dbColName;
	}
	public void setDbColName(String dbColName)
	{
		this.dbColName = dbColName;
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
	public int getDataType()
	{
		return dataType;
	}
	public void setDataType(int dataType)
	{
		this.dataType = dataType;
	}
	public int getDicType()
	{
		return dicType;
	}
	public void setDicType(int dicType)
	{
		this.dicType = dicType;
	}
	public String getDicData()
	{
		return dicData;
	}
	public void setDicData(String dicData)
	{
		this.dicData = dicData;
	}
	public int getLength()
	{
		return length;
	}
	public void setLength(int length)
	{
		this.length = length;
	}
	public boolean isNull()
	{
		return isNull;
	}
	public void setNull(boolean isNull)
	{
		this.isNull = isNull;
	}
	public String getDefaultValue()
	{
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
}
