package com.ultrapower.wfinterface.core.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigMethod
{
	private String methodName;
	private String serviceName;
	private String baseSchema;
	private String implClassPath;
	private String operateCode;
	private String operateText;
	private String operateActonid;
	private String creator;
	
	private List<ConfigField> fields;
	
	public ConfigMethod()
	{
		fields = new ArrayList<ConfigField>();
	}
	
	public ConfigMethod(String methodName, String serviceName, String baseSchema, String implClassPath, String operateCode, String operateText,String operateActonid,String creator, List<ConfigField> fields)
	{
		this.methodName = methodName;
		this.serviceName = serviceName;
		this.baseSchema = baseSchema;
		this.implClassPath = implClassPath;
		this.operateCode = operateCode;
		this.operateText = operateText;
		this.operateActonid = operateActonid;
		this.creator = creator;  
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
	public String getServiceName()
	{
		return serviceName;
	}
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}
	public String getImplClassPath()
	{
		return implClassPath;
	}
	public void setImplClassPath(String implClassPath)
	{
		this.implClassPath = implClassPath;
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

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getOperateCode()
	{
		return operateCode;
	}

	public void setOperateCode(String operateCode)
	{
		this.operateCode = operateCode;
	}

	public String getOperateText()
	{
		return operateText;
	}

	public void setOperateText(String operateText)
	{
		this.operateText = operateText;
	}

	public String getOperateActonid() {
		return operateActonid;
	}

	public void setOperateActonid(String operateActonid) {
		this.operateActonid = operateActonid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
