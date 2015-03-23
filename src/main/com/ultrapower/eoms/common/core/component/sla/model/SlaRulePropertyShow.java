package com.ultrapower.eoms.common.core.component.sla.model;

public class SlaRulePropertyShow 
{
	private String id;
	private String rpid;//规则属性ID
	private String fieldname;//规则属性名称
	private String inputtype;//规则属性输入类型（文本域、弹出列表、变量域）
	private String inputvaluetype;//输入值类型（文本、时间、数值）
	private String inputdatasourcetype;//规则属性数据源类型（系统变量、字典、树、脚本查询）
	private String indata;//规则属性数据值
	private SlaRuleProperty slaRuleProperty;//Sla规则属性
	private boolean isown = false;//sla规则属性是否拥有该属性
	
	public SlaRulePropertyShow() 
	{
		super();
	}
	public SlaRulePropertyShow(String id, String inputtype,
			String inputdatasourcetype, String indata, String fieldname,String rpid,
			SlaRuleProperty slaRuleProperty, boolean isown, String inputvaluetype) 
	{
		super();
		this.id = id;
		this.inputtype = inputtype;
		this.inputdatasourcetype = inputdatasourcetype;
		this.indata = indata;
		this.slaRuleProperty = slaRuleProperty;
		this.fieldname = fieldname;
		this.rpid = rpid;
		this.isown = isown;
		this.inputvaluetype = inputvaluetype;
	}
	public String getId() 
	{
		return id;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getInputtype() 
	{
		return inputtype;
	}
	public void setInputtype(String inputtype) 
	{
		this.inputtype = inputtype;
	}
	public String getInputdatasourcetype() 
	{
		return inputdatasourcetype;
	}
	public void setInputdatasourcetype(String inputdatasourcetype) 
	{
		this.inputdatasourcetype = inputdatasourcetype;
	}
	public String getIndata() 
	{
		return indata;
	}
	public void setIndata(String indata) 
	{
		this.indata = indata;
	}
	public SlaRuleProperty getSlaRuleProperty() 
	{
		return slaRuleProperty;
	}
	public void setSlaRuleProperty(SlaRuleProperty slaRuleProperty) 
	{
		this.slaRuleProperty = slaRuleProperty;
	}
	public String getFieldname() 
	{
		return fieldname;
	}
	public void setFieldname(String fieldname) 
	{
		this.fieldname = fieldname;
	}
	public String getRpid() 
	{
		return rpid;
	}
	public void setRpid(String rpid) 
	{
		this.rpid = rpid;
	}
	public boolean isIsown() 
	{
		return isown;
	}
	public void setIsown(boolean isown) 
	{
		this.isown = isown;
	}
	public String getInputvaluetype() {
		return inputvaluetype;
	}
	public void setInputvaluetype(String inputvaluetype) {
		this.inputvaluetype = inputvaluetype;
	}
	
}
