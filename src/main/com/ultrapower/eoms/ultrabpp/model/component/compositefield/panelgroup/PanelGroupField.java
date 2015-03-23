package com.ultrapower.eoms.ultrabpp.model.component.compositefield.panelgroup;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.component.compositefield.CompositeField;

@Entity
@Table(name = "BS_T_BPP_F_PANELGROUP")
public class PanelGroupField extends CompositeField
{

    /**
     * 标题可见
     */
    private Integer titleVisible;
    /**
     * 占列
     */
    private Integer colspan;
    /**
     * 类型
     */
    private String type;


    public Integer getTitleVisible()
    {
	return titleVisible;
    }

    public void setTitleVisible(Integer titleVisible)
    {
	this.titleVisible = titleVisible;
    }

    public Integer getColspan()
    {
	return colspan;
    }

    public void setColspan(Integer colspan)
    {
	this.colspan = colspan;
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
