package com.ultrapower.eoms.ultrabpp.model.component.displayfield.labelfield;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayField;

@Entity
@Table(name = "BS_T_BPP_F_LABEL")
public class LabelField extends DisplayField
{
    private Integer colspan;

    private Integer rowspan;
    
    private String cssName;

    public Integer getColspan()
    {
	return colspan;
    }

    public void setColspan(Integer colspan)
    {
	this.colspan = colspan;
    }

    public Integer getRowspan()
    {
	return rowspan;
    }

    public void setRowspan(Integer rowspan)
    {
	this.rowspan = rowspan;
    } 

    public String getCssName()
    {
        return cssName;
    }

    public void setCssName(String cssName)
    {
        this.cssName = cssName;
    }

}
