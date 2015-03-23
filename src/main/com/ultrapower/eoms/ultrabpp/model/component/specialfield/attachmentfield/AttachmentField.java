package com.ultrapower.eoms.ultrabpp.model.component.specialfield.attachmentfield;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.model.component.specialfield.SpecialField;

@Entity
@Table(name = "BS_T_BPP_F_ATTACHMENT")
public class AttachmentField extends SpecialField
{

    private String type;

   

    public String getType()
    {
	return type;
    }

    public void setType(String type)
    {
	this.type = type;
    } 

    @Override
    public Object getDBValue(Map<String, String> fieldMap)
    {
	String rtn = null;
	if (fieldMap != null)
	{
	    rtn = fieldMap.get(getFieldName());
	}
	return StringUtils.isBlank(rtn) ? "" : rtn;
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
	return this.getFieldName() + " varchar2(50)";
    }

    @Transient
    public String getDelColSql()
    {
	return this.getFieldName();
    }
}
