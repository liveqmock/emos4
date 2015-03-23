package com.ultrapower.wfinterface.core.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigClient
{
	private String methodName;
	private String clientName;
	private String implClassPath;
	private String serviceAddress;
	private String serviceOperateName;
	private String serviceNameSpace;
	private String querySql;
	private String updateSql;
	private String exceptionSql;
	private List<ConfigCallParameter> parameters;
	private List<ConfigField> fields;
	
	public ConfigClient()
	{
		fields = new ArrayList<ConfigField>();
	}
	
	public ConfigClient(String methodName, String clientName, String implClassPath, String serviceAddress, String serviceOperateName, String serviceNameSpace, String querySql, String updateSql, String exceptionSql, List<ConfigCallParameter> parameters, List<ConfigField> fields)
	{
		this.methodName = methodName;
		this.clientName = clientName;
		this.implClassPath = implClassPath;
		this.serviceAddress = serviceAddress;
		this.serviceOperateName = serviceOperateName;
		this.serviceNameSpace = serviceNameSpace;
		this.querySql = querySql;
		this.updateSql = updateSql;
		this.exceptionSql = exceptionSql;
		this.parameters = parameters;
		this.fields = fields;
	}
	
	public String getMethodName()
	{
		return methodName;
	}
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	public String getClientName()
	{
		return clientName;
	}
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	public String getImplClassPath()
	{
		return implClassPath;
	}
	public void setImplClassPath(String implClassPath)
	{
		this.implClassPath = implClassPath;
	}
	public String getQuerySql()
	{
		return querySql;
	}
	public void setQuerySql(String querySql)
	{
		this.querySql = querySql;
	}
	public String getUpdateSql()
	{
		return updateSql;
	}
	public void setUpdateSql(String updateSql)
	{
		this.updateSql = updateSql;
	}

	public String getServiceAddress()
	{
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress)
	{
		this.serviceAddress = serviceAddress;
	}

	public String getServiceOperateName()
	{
		return serviceOperateName;
	}

	public void setServiceOperateName(String serviceOperateName)
	{
		this.serviceOperateName = serviceOperateName;
	}

	public List<ConfigField> getFields()
	{
		return fields;
	}

	public void setFields(List<ConfigField> fields)
	{
		this.fields = fields;
	}
	
	public ConfigField getField(String fieldName)
	{
		for(ConfigField field : fields)
		{
			if(field.getFieldName().equals(fieldName))
			{
				return field;
			}
		}
		return null;
	}

	public List<ConfigCallParameter> getParameters()
	{
		return parameters;
	}

	public void setParameters(List<ConfigCallParameter> parameters)
	{
		this.parameters = parameters;
	}

	public String getExceptionSql()
	{
		return exceptionSql;
	}

	public void setExceptionSql(String exceptionSql)
	{
		this.exceptionSql = exceptionSql;
	}

	public String getServiceNameSpace()
	{
		return serviceNameSpace;
	}

	public void setServiceNameSpace(String serviceNameSpace)
	{
		this.serviceNameSpace = serviceNameSpace;
	}
}
