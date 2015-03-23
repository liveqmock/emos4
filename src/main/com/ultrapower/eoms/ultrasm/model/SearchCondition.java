package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BS_T_SM_SEARCHCONDITION")
public class SearchCondition
{
	private String pid;
    private String loginname;
    private String sqlname;
    private String conditions;
    
    @Id
    public String getPid()
	{
		return pid;
	}
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	public String getLoginname()
	{
		return loginname;
	}
	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}
	public String getSqlname()
	{
		return sqlname;
	}
	public void setSqlname(String sqlname)
	{
		this.sqlname = sqlname;
	}
	public String getConditions()
	{
		return conditions;
	}
	public void setConditions(String conditions)
	{
		this.conditions = conditions;
	}
    
    
}
