package com.ultrapower.eoms.ultrabpp.model;

import java.util.Map;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class BaseField implements Comparable<BaseField>
{

    /**
     * 主键ID
     */
    private String id;
    /**
     * 中文名称
     */
    private String label;
    /**
     * 英文名称
     */
    private String fieldName;
    /**
     * Field类型
     */
    private String fieldType;
    /**
     * 工单类型
     */
    private String baseSchema;
    /**
     * 是否可见
     */
    private Integer visiable;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 是否部署
     */
    private Integer hasDeploy;
    /**
     * 部署前操作
     */
    private String operate;
    /**
     * 上级容器ID，或者容器组ID
     * @return
     */
    private String parentID;
    
    
    @Id
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    
    
    public String getParentID()
    {
        return parentID;
    }

    public void setParentID(String parentID)
    {
        this.parentID = parentID;
    }

    @Transient
    public String getFieldType()
    {
        return fieldType;
    }

    public void setFieldType(String fieldType)
    {
        this.fieldType = fieldType;
    }

    public String getBaseSchema() {
	return baseSchema;
    }

    public void setBaseSchema(String baseSchema) {
	this.baseSchema = baseSchema;
    }
    
    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Integer getVisiable() {
        return visiable;
    }

    public void setVisiable(Integer visiable) {
        this.visiable = visiable;
    }

    public Integer getHasDeploy()
    {
        return hasDeploy;
    }

    public void setHasDeploy(Integer hasDeploy)
    {
        this.hasDeploy = hasDeploy;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }
    
    public int compareTo(BaseField o) {
    	return this.getOrderNum() - o.getOrderNum();
    }
    
    public abstract Object getDBValue(Map<String, String> fieldMap);

    public abstract String getDisplayValue(String value);
    
    public abstract Map<String, Object> getSaveSql(Map<String, String> fieldMap);
    
    @Transient
    public abstract String getAddColSql();
    
    @Transient
    public abstract String getDelColSql();
    
    @Transient
    public abstract String getModColSql();
}
