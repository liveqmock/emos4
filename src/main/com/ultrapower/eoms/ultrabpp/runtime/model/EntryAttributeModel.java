package com.ultrapower.eoms.ultrabpp.runtime.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BS_T_BPP_ENTRYATTRIBUTE")
public class EntryAttributeModel
{
	private String attributeID;
	private String baseID;
	private String baseSchema;
	private String key;
	private String value;
	/**
	 * @return the attributeID
	 */
	@Id
	public String getAttributeID()
	{
		return attributeID;
	}
	/**
	 * @param attributeID the attributeID to set
	 */
	public void setAttributeID(String attributeID)
	{
		this.attributeID = attributeID;
	}
	/**
	 * @return the baseID
	 */
	public String getBaseID()
	{
		return baseID;
	}
	/**
	 * @param baseID the baseID to set
	 */
	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}
	/**
	 * @return the baseSchema
	 */
	public String getBaseSchema()
	{
		return baseSchema;
	}
	/**
	 * @param baseSchema the baseSchema to set
	 */
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}
	/**
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key)
	{
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
