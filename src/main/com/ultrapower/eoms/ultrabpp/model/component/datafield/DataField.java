package com.ultrapower.eoms.ultrabpp.model.component.datafield;

import javax.persistence.MappedSuperclass;

import com.ultrapower.eoms.ultrabpp.model.BaseField;

@MappedSuperclass
public abstract class DataField extends BaseField
{

    /**
     * 需要保存
     */
    private Integer needSave;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 最大长度
     */
    private Integer length;
    /**
     * 占列
     */
    private Integer colspan;

    public Integer getNeedSave()
    {
        return needSave;
    }
    public void setNeedSave(Integer needSave)
    {
        this.needSave = needSave;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Integer getLength() {
        return length;
    }
    public void setLength(Integer length) {
        this.length = length;
    }
    public Integer getColspan() {
        return colspan;
    }
    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }
    
}
