package com.ultrapower.eoms.ultrabpp.model.component.displayfield.viewfield;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayField;

@Entity
@Table(name = "BS_T_BPP_F_VIEW")
public class ViewField extends DisplayField
{
    /**
     * 链接地址
     */
    private String pageurl;

    private Integer colspan;

    private Integer rowspan;
    
    private String type;
    

    public String getPageurl()
    {
	return pageurl;
    }

    public void setPageurl(String pageurl)
    {
	this.pageurl = pageurl;
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
