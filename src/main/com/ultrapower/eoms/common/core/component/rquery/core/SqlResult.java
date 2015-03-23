package com.ultrapower.eoms.common.core.component.rquery.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;

public class SqlResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4572818756192818493L;
	private StringBuffer sql;
	private List   values;
	
	public SqlResult()
	{
		sql=new StringBuffer();
		values=new ArrayList();
	}
	public String getSql() {
		return sql.toString();
	}
	/*public void setSql(String sql) {
		this.sql.append(sql);
	}*/
	public List getValues() {
		return values;
	}
/*	public void setValues(List values) {
		this.values = values;
	}
*/	
	
	public void setSql(String sql)
	{
		this.sql=new StringBuffer();
		this.sql.append(sql);
	}
	public void append(SqlResult sqlResult)
	{
		if(sqlResult!=null)
		{
			this.appendSql(sqlResult.getSql());
			this.appendValues(sqlResult.getValues());
		}
	}
	public void appendSql(String sql)
	{
		this.sql.append(sql);
	}
	public void appendValues(List p_values)
	{
		try {
			if(this.values.size()==0)
			{
				this.values=p_values;
			}
			else
			{
				this.values=ArrayTransferUtils.copyListSimple(this.values, p_values);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.values.addAll(values);
	}
	
}
