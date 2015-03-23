package com.ultrapower.eoms.ultrabpp.model.component.displayfield.buttonfield;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayField;

@Entity
@Table(name = "BS_T_BPP_F_BUTTON")
public class ButtonField extends DisplayField
{

    /**
     * 按钮标识
     */
    private String buttonCode;
 

    private Integer colspan;
    
    private Integer rowspan;


   
    public String getButtonCode()
    {
	return buttonCode;
    }

    public void setButtonCode(String buttonCode)
    {
	this.buttonCode = buttonCode;
    }

   
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


}
