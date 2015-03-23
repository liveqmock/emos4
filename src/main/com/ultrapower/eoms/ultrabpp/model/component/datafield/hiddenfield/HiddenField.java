package com.ultrapower.eoms.ultrabpp.model.component.datafield.hiddenfield;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;

@Entity
@Table(name = "BS_T_BPP_F_HIDDEN")
public class HiddenField extends DataField
{
    /**
     * 占行
     */
    private Integer rowspan;
    
    public Integer getRowspan()
    {
        return rowspan;
    }

    public void setRowspan(Integer rowspan)
    {
        this.rowspan = rowspan;
    }
    
    
    @Override
    public Object getDBValue(Map<String, String> fieldMap)
    {
    	String rtn = null;
    	if (fieldMap != null) {
    		rtn = fieldMap.get(getFieldName());
		}
    	return StringUtils.isBlank(rtn) ? "" : rtn;
    }

    @Override
    public String getDisplayValue(String value)
    {
	if (value == null)
	    return "";
	else
	    return value;
    }

    @Override
    public Map<String, Object> getSaveSql(Map<String, String> fieldMap)
    {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put(getFieldName(), getDBValue(fieldMap));
    	return rtnMap;
    }

    @Transient
    public String getAddColSql()
    {
	return this.getFieldName() + " varchar2(" + this.getLength() + ")";
    }

    @Transient
    public String getDelColSql()
    {
	return this.getFieldName();
    }

    @Transient
    public String getModColSql()
    {
	return this.getFieldName() + " varchar2(" + this.getLength() + ")";
    }


    
}
