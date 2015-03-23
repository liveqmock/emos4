package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BS_T_BPP_ASSINGTREEORGANIZE")
public class AssignTreeOrganizeModel
{
	private String id;
	private String configid;
	private Integer organizetype;
	private String organizeid;
	private String parentorgid;
	
	@Id
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getConfigid()
	{
		return configid;
	}
	public void setConfigid(String configid)
	{
		this.configid = configid;
	}
	public Integer getOrganizetype()
	{
		return organizetype;
	}
	public void setOrganizetype(Integer organizetype)
	{
		this.organizetype = organizetype;
	}
	public String getOrganizeid()
	{
		return organizeid;
	}
	public void setOrganizeid(String organizeid)
	{
		this.organizeid = organizeid;
	}
	public String getParentorgid()
	{
		return parentorgid;
	}
	public void setParentorgid(String parentorgid)
	{
		this.parentorgid = parentorgid;
	}
	
}
