package com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;

@Entity
@Table(name = "BS_T_BPP_F_SELECT")
public class SelectField extends DataField
{
	
	/**
	 * 类型
	 */
	private String type;
	
    /**
     * 资源
     */
    private String typeResource;
    
    /**
     * 级联
     */
    private String paras;
    
    

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeResource() {
		return typeResource;
	}

	public void setTypeResource(String typeResource) {
		this.typeResource = typeResource;
	}

	public String getParas() {
		return paras;
	}

	public void setParas(String paras) {
		this.paras = paras;
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
