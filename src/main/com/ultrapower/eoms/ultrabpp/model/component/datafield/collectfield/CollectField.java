package com.ultrapower.eoms.ultrabpp.model.component.datafield.collectfield;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

@Entity
@Table(name = "BS_T_BPP_F_COLLECT")
public class CollectField extends DataField
{
	/**
	 * 类型
	 */
	private String type;
    /**
     * 占行
     */
    private Integer rowspan;
	
    /**
     * 资源
     */
    private String typeResource;
    
    /**
     * 级联
     */
    private String paras;
	
    private String showType;

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

    public Integer getRowspan()
    {
    	return rowspan;
    }

    public void setRowspan(Integer rowspan)
    {
    	this.rowspan = rowspan;
    }

	/**
	 * @return the typeResource
	 */
	public String getTypeResource()
	{
		return typeResource;
	}

	/**
	 * @param typeResource the typeResource to set
	 */
	public void setTypeResource(String typeResource)
	{
		this.typeResource = typeResource;
	}

	/**
	 * @return the paras
	 */
	public String getParas()
	{
		return paras;
	}

	/**
	 * @param paras the paras to set
	 */
	public void setParas(String paras)
	{
		this.paras = paras;
	}

	/**
	 * @return the showType
	 */
	public String getShowType()
	{
		return showType;
	}

	/**
	 * @param showType the showType to set
	 */
	public void setShowType(String showType)
	{
		this.showType = showType;
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

	@Override
	public String getDisplayValue(String value)
	{
		if (value == null)
		    return "";
		else
		    return value;
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
	public Map<String, Object> getSaveSql(Map<String, String> fieldMap)
	{
		Map<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put(getFieldName(), getDBValue(fieldMap));
    	return rtnMap;
	}
	
	@Transient
	public static Map<String, String> getDicData(String resType, String resource, String para)
	{
		Map<String, String> dicValues = new LinkedHashMap<String, String>();
		
		if(resType.equals("collect"))
		{
			String[] items = resource.split(",");
			for(String item : items)
			{
				String[] itemarr = item.split(":");
				String key = itemarr[0];
				String value = itemarr[1];
				
				dicValues.put(key, value);
			}
		}
		else if(resType.equals("sysdic"))
		{
			DicManagerService dicManagerService = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
			List<DicItem> items = dicManagerService.getDicItemByFullName(resource, para);
			for(DicItem item : items)
			{
				//String key = item.getDivalue();
				String key = item.getDiname();
				String value = item.getDiname();
				
				dicValues.put(key, value);
			}
		}
		else if(resType.equals("table"))
		{
			String res = resource.split(":")[0];
			String keyName = resource.split(":")[1];
			String valName = resource.split(":")[2];
				
			String cells = "";
			if(keyName.equals(valName)) cells = valName;
			else cells = keyName + ", " + valName;
			StringBuilder querySql = new StringBuilder("select " + cells + " from " + res);
			if(para != null && !para.equals(""))
			{
				querySql.append(" where " + para);
			}
			querySql.append(" order by " + valName + " asc");
			QueryAdapter qa = new QueryAdapter();
			DataTable dicData = qa.executeQuery(querySql.toString());
			if(dicData != null)
			{
				List<DataRow> dicRows = dicData.getRowList();
				for(DataRow dicRow : dicRows)
				{
					String key = dicRow.getString(keyName);
					if(key != null && !key.equals(""))
					{
						String value = StringUtils.isEmpty(dicRow.getString(valName)) ? "" : dicRow.getString(valName);
						
						dicValues.put(key, value);
					}
				}
			}
		}
		
		return dicValues;
	}
}
