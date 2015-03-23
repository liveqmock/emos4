package com.ultrapower.eoms.workflow.visioanalyze;

import java.util.*;

public class ShapeModel
{
	protected String id;
	
	protected String code;
	
	protected String nameU;
	
	protected String name;
	
	protected String master;
	
	protected String x;
	
	protected String y;
	
	protected String width;
	
	protected String height;
	
	protected String format;
	
	protected Map<String, PropModel> propModelMap;
	
	protected String connectionPoint;

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

	public String getMaster()
	{
		return master;
	}

	public void setMaster(String master)
	{
		this.master = master;
	}

	public String getX()
	{
		return x;
	}

	public void setX(String x)
	{
		this.x = x;
	}

	public String getY()
	{
		return y;
	}

	public void setY(String y)
	{
		this.y = y;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public Map<String, PropModel> getPropModelMap()
	{
		return propModelMap;
	}

	public void setPropModelMap(Map<String, PropModel> propModelMap)
	{
		this.propModelMap = propModelMap;
	}

	public String getConnectionPoint()
	{
		return connectionPoint;
	}

	public void setConnectionPoint(String connectionPoint)
	{
		this.connectionPoint = connectionPoint;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}
