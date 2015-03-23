package com.ultrapower.eoms.workflow.managetools.control.template;

import java.util.Map;

public class TemplateField
{
	private String fieldID;
	
	private String fieldName;
	
	private String fieldCode;
	
	private int fieldType;
	
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
