package com.ultrapower.eoms.ultrabpp.model.component.specialfield.assigntreefield;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ultrapower.eoms.ultrabpp.model.component.specialfield.SpecialField;

@Entity
@Table(name = "BS_T_BPP_F_ASSIGNTREE")
public class AssignTreeField extends SpecialField
{

    /**
     * 是否多选
     */
    private Integer singleSelect; 

    /**
     * 占行
     */
    private Integer rowspan;
    /**
     * 占列
     */
    private Integer colspan;

    private String action;
    
    private String actionName;
    
    private String stepno;

    private String next;
    
    private String selectType;

    public Integer getSingleSelect()
    {
	return singleSelect;
    }

    public void setSingleSelect(Integer singleSelect)
    {
	this.singleSelect = singleSelect;
    }

    public Integer getRowspan()
    {
	return rowspan;
    }

    public void setRowspan(Integer rowspan)
    {
	this.rowspan = rowspan;
    }

    public Integer getColspan()
    {
	return colspan;
    }

    public void setColspan(Integer colspan)
    {
	this.colspan = colspan;
    }

    public String getAction()
    {
	return action;
    }

    public void setAction(String action)
    {
	this.action = action;
    }

    public String getNext()
    {
	return next;
    }

    public void setNext(String next)
    {
	this.next = next;
    }
    
    @Transient
	public String getStepno() {
		return stepno;
	}

	public void setStepno(String stepno) {
		this.stepno = stepno;
	}
	
	@Transient
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	@Transient
	public String getSelectType()
	{
		return selectType;
	}
	public void setSelectType(String selectType)
	{
		this.selectType = selectType;
	}
	
	

}
