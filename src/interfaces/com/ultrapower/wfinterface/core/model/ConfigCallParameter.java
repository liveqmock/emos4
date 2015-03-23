package com.ultrapower.wfinterface.core.model;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.encoding.XMLType;

public class ConfigCallParameter
{
	private String name;
	private int dataType;
	private QName type;
	private String dicData;
	private ParameterMode mode;
	
	public ConfigCallParameter() {};
	
	public ConfigCallParameter(String name, int dataType, QName type, String dicData, ParameterMode mode)
	{
		this.name = name;
		this.dataType = dataType;
		this.type = type;
		this.dicData = dicData;
		this.mode = mode;
	}
	
	public static QName toType(String t)
	{
		if(t.equals("INT"))
		{
			return XMLType.XSD_INT;
		}
		else if(t.equals("DATETIME"))
		{
			return XMLType.XSD_DATETIME;
		}
		else
		{
			return XMLType.XSD_STRING;
		}
	}
	
	public static ParameterMode toMode(String m)
	{
		if(m.equals("INOUT"))
		{
			return ParameterMode.INOUT;
		}
		else if(m.equals("OUT"))
		{
			return ParameterMode.OUT;
		}
		else
		{
			return ParameterMode.IN;
		}
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public QName getType()
	{
		return type;
	}
	public void setType(QName type)
	{
		this.type = type;
	}
	public ParameterMode getMode()
	{
		return mode;
	}
	public void setMode(ParameterMode mode)
	{
		this.mode = mode;
	}

	public int getDataType()
	{
		return dataType;
	}

	public void setDataType(int dataType)
	{
		this.dataType = dataType;
	}

	public String getDicData()
	{
		return dicData;
	}

	public void setDicData(String dicData)
	{
		this.dicData = dicData;
	}
}
