package com.ultrapower.eoms.ultrabpp.model.component.datafield.characterfield;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;

@Entity
@Table(name = "BS_T_BPP_F_CHARACTER")
public class CharacterField extends DataField
{
    /**
     * 占行
     */
    private Integer rowspan; 
    /**
     * 大字段标识
     * @return
     */
    private Integer isClob;

    public Integer getRowspan()
    {
	return rowspan;
    }

    public void setRowspan(Integer rowspan)
    {
	this.rowspan = rowspan;
    }

    public Integer getIsClob()
	{
		return isClob;
	}

	public void setIsClob(Integer isClob)
	{
		this.isClob = isClob;
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
    	if (isClob != null && isClob == 1) {
    		return this.getFieldName() + " clob";
		}
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
    	if (isClob != null && isClob == 1) {
			return null;
		}
    	return this.getFieldName() + " varchar2(" + this.getLength() + ")";
    }
}
