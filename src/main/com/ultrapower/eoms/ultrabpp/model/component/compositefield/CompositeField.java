package com.ultrapower.eoms.ultrabpp.model.component.compositefield;

import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.ultrapower.eoms.ultrabpp.model.BaseField;

@MappedSuperclass
public abstract class CompositeField extends BaseField
{

	@Override
	public Object getDBValue(Map<String, String> fieldMap)
	{
		return null;
	}

	@Override
	public String getDisplayValue(String value)
	{
		return null;
	}

	@Override
	public Map<String, Object> getSaveSql(Map<String, String> fieldMap)
	{
		return null;
	}
	
	@Override
	@Transient
	public String getAddColSql() {
		return null;
	}
    
	@Override
	@Transient
    public String getDelColSql() {
    	return null;
    }
    
	@Override
	@Transient
    public String getModColSql() {
    	return null;
    }
}
