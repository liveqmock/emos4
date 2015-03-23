package com.ultrapower.eoms.common.core.component.rquery.core;

public class NextVar 
{
	private String varname;//变量名称
	private int startpos;//变量开始位置
	private int endpos;//变量结束位置
	private int vartype;//变量类型
	public String getVarname() 
	{
		return varname;
	}
	public void setVarname(String varname)
	{
		this.varname = varname;
	}
	public int getStartpos()
	{
		return startpos;
	}
	public void setStartpos(int startpos) 
	{
		this.startpos = startpos;
	}
	public int getEndpos() 
	{
		return endpos;
	}
	public void setEndpos(int endpos)
	{
		this.endpos = endpos;
	}
	public int getVartype()
	{
		return vartype;
	}
	public void setVartype(int vartype) 
	{
		this.vartype = vartype;
	}
}
