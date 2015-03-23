package com.ultrapower.eoms.ultrabpp.cache.model;

import java.util.List;
import java.util.Map;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class FieldConfig
{
    private String name;
    private String label;
    private String fullName;
    private String tableName;
    private String extendsName;
    private String extendsLabel;
    private String orderNum;
    private List<AttributeConfig> attrList;

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }


    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getTableName()
    {
	return tableName;
    }

    public void setTableName(String tableName)
    {
	this.tableName = tableName;
    }

    public String getExtendsName()
    {
	return extendsName;
    }

    public void setExtendsName(String extendsName)
    {
	this.extendsName = extendsName;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getExtendsLabel()
    {
        return extendsLabel;
    }

    public void setExtendsLabel(String extendsLabel)
    {
        this.extendsLabel = extendsLabel;
    }

	public List<AttributeConfig> getAttrList()
	{
		return attrList;
	}

	public void setAttrList(List<AttributeConfig> attrList)
	{
		this.attrList = attrList;
	}


}
