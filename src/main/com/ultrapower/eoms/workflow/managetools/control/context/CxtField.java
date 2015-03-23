package com.ultrapower.eoms.workflow.managetools.control.context;

import java.util.Map;

public class CxtField
{
	private String fieldID;
	
	private String fieldName;
	
	private int fieldType;
	
	private String fieldCode;
	
	private String fieldValue;
	
	private String sourceValue;
	
	private Map<String, String> fieldDic;

	/**
	 * @return the fieldID
	 */
	public String getFieldID()
	{
		return fieldID;
	}

	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(String fieldID)
	{
		this.fieldID = fieldID;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue()
	{
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue)
	{
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the fieldType
	 */
	public int getFieldType()
	{
		return fieldType;
	}

	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(int fieldType)
	{
		this.fieldType = fieldType;
	}

	/**
	 * @return the sourceValue
	 */
	public String getSourceValue()
	{
		return sourceValue;
	}

	/**
	 * @param sourceValue the sourceValue to set
	 */
	public void setSourceValue(String sourceValue)
	{
		this.sourceValue = sourceValue;
	}

	/**
	 * @return the fieldDic
	 */
	public Map<String, String> getFieldDic()
	{
		return fieldDic;
	}

	/**
	 * @param fieldDic the fieldDic to set
	 */
	public void setFieldDic(Map<String, String> fieldDic)
	{
		this.fieldDic = fieldDic;
	}

	/**
	 * @return the fieldCode
	 */
	public String getFieldCode()
	{
		return fieldCode;
	}

	/**
	 * @param fieldCode the fieldCode to set
	 */
	public void setFieldCode(String fieldCode)
	{
		this.fieldCode = fieldCode;
	}
}
