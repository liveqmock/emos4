package com.ultrapower.eoms.ultrabpp.model.component.datafield.timefield;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;

@Entity
@Table(name = "BS_T_BPP_F_TIME")
public class TimeField extends DataField
{
    /**
     * 日期格式
     */
    private String timeFormat;

    public String getTimeFormat()
    {
	return timeFormat;
    }

    public void setTimeFormat(String timeFormat)
    {
	this.timeFormat = timeFormat;
    }

    @Override
    public Object getDBValue(Map<String, String> fieldMap)
    {
		long rtn = 0;
		if (fieldMap != null) {
			String longStr = fieldMap.get(getFieldName());
			if (StringUtils.isNotBlank(longStr)) {
				rtn = TimeUtils.formatDateStringToInt(longStr, timeFormat);
			}
		}
		return rtn;
    }

    @Override
    public String getDisplayValue(String value)
    {
	String rtn = "";
	if (StringUtils.isNotBlank(value))
	{
	    rtn = TimeUtils.formatIntToDateString(timeFormat, Long.valueOf(value));
	}
	return rtn;
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
	return this.getFieldName() + " number(" + this.getLength() + ")";
    }

    @Transient
    public String getDelColSql()
    {
	return this.getFieldName();
    }

    @Transient
    public String getModColSql()
    {
	return this.getFieldName() + " number(" + this.getLength() + ")";
    }
}
