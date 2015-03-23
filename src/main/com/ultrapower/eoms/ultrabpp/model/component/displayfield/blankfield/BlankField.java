package com.ultrapower.eoms.ultrabpp.model.component.displayfield.blankfield;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayField;

@Entity
@Table(name="BS_T_BPP_F_BLANK")
public class BlankField extends DisplayField
{
	private Integer colspan;
	
	private Integer rowspan;

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
