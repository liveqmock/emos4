package com.ultrapower.remedy4j.core;

/**
 * Remedy字段的对象
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
public class RemedyField
{
	/**
	 * 构造函数
	 * @param id 字段ID
	 * @param type 字段类型使用RemedyFieldType
	 */
	public RemedyField(String id, String name, int type)
	{
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	/**
	 * 构造函数
	 * @param id 字段ID
	 * @param type 字段类型使用RemedyFieldType
	 * @param value 字段的值
	 */
	public RemedyField(String id, String name, int type, String value)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 字段的ID
	 */
	private String id;
	
	/**
	 * 字段英文
	 */
	private String name;

	/**
	 * 字段的值
	 */
	private String value;
	
	/**
	 * 字段的数据类型，参看RemedyFieldType
	 */
	private int type;
	
	/**
	 * 获取字段的ID
	 * @return 字段的ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * 设置字段的ID
	 * @param id 字段的ID
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * 获取字段的英文
	 * @return 字段的英文
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * 设置字段的英文
	 * @param name 字段的英文
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * 获取字段的值
	 * @return字段的值
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * 设置字段的值
	 * @param value 字段的值
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * 获取字段的数据类型
	 * @return 字段的数据类型
	 */
	public int getType()
	{
		return type;
	}
	/**
	 * 设置字段的数据类型，使用RemedyFieldType赋值
	 * @param type 字段的数据类型，使用RemedyFieldType赋值
	 */
	public void setType(int type)
	{
		this.type = type;
	}
	
	
}
