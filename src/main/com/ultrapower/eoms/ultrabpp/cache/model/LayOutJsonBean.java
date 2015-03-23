package com.ultrapower.eoms.ultrabpp.cache.model;

import java.util.List;

public class LayOutJsonBean
{
	private String fieldName;
	private String parentName;
	private List childNameArray;
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public String getParentName()
	{
		return parentName;
	}
	public void setParentName(String parentName)
	{
		this.parentName = parentName;
	}
	public List getChildNameArray()
	{
		return childNameArray;
	}
	public void setChildNameArray(List childNameArray)
	{
		this.childNameArray = childNameArray;
	}
	
}
