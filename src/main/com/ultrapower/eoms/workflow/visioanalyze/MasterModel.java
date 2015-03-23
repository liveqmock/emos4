package com.ultrapower.eoms.workflow.visioanalyze;

import java.util.*;

public class MasterModel
{
	protected String id;
	
	protected String nameU;
	
	protected String name;
	
	protected Map<String, PropModel> propModelMap;

	public Map<String, PropModel> getPropModelMap()
	{
		return propModelMap;
	}

	public void setPropModelMap(Map<String, PropModel> propModelMap)
	{
		this.propModelMap = propModelMap;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNameU()
	{
		return nameU;
	}

	public void setNameU(String nameU)
	{
		this.nameU = nameU;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
