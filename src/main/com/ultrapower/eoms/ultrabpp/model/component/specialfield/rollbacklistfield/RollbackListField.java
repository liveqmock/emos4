package com.ultrapower.eoms.ultrabpp.model.component.specialfield.rollbacklistfield;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.component.specialfield.SpecialField;

@Entity
@Table(name = "BS_T_BPP_F_ROLLBACKLIST")
public class RollbackListField extends SpecialField
{
    private Integer singleSelect;

    private String type;

    /**
     * 占列
     */
    private Integer colspan;

    public Integer getColspan()
    {
	return colspan;
    }

    public void setColspan(Integer colspan)
    {
	this.colspan = colspan;
    }

   

    public Integer getSingleSelect()
    {
        return singleSelect;
    }

    public void setSingleSelect(Integer singleSelect)
    {
        this.singleSelect = singleSelect;
    }

    public String getType()
    {
	return type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

}
