package com.ultrapower.eoms.common.core.component.rquery.datatransfer;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;

import com.ultrapower.eoms.common.core.util.StringUtils;

public class DataTransfer {

	
	public static SqlResult getFieldValues(final String p_prepend,final String p_operator,final String p_colname,final List list,final int datatype)
	{
		if(list!=null)
		{
			Object values[]=list.toArray();
			return  getFieldValues(p_prepend,p_operator,p_colname,values,datatype);
		}
		return null;
	}
	public static SqlResult getFieldValues(final String p_prepend,final String p_operator,final String p_colname,final String p_value,final int datatype)
	{
		String value=StringUtils.checkNullString(p_value).trim();
		if(!value.equals(""))
		{
			String values[]=value.split(RConstants.DATA_SPILT);
			return  getFieldValues(p_prepend,p_operator,p_colname,values,datatype);
		}
		return null;
	}
	/**
	 * 根据传入的参数拼接sql
	 * @param p_prepend 连接符 and or 
	 * @param p_operator 操作符 如： = 、<、>= 等
	 * @param p_colname 字段名
	 * @param p_value 值
	 * @param datatype 数据类型 （目前暂时没用 传 4）
	 * @return
	 */
	public static SqlResult getFieldValues(final String p_prepend,final String p_operator,final String p_colname,final Object[] p_value,final int datatype)
	{
		
		SqlResult sqlResult=new SqlResult();
		String operator=StringUtils.checkNullString(p_operator).trim().toLowerCase();
		Object values[]=p_value;
		int len=0;
		if(values!=null)
			len=values.length;
		int i;
		StringBuffer string=new StringBuffer();
		List list=new ArrayList();
		if(operator.equals(""))
		{
			operator="=";
		}
		String value;
		for(i=0;i<len;i++)
		{
			value=StringUtils.checkNullString(values[i]);
			if(!value.equals(""))
			{
				if(!string.toString().equals(""))
				{
					string.append(" or ");
				}
				string.append(p_colname);
				string.append(" ");
				if(operator.equals(RConstants.OPERATOR_LEFT_LIKE)||operator.equals(RConstants.OPERATOR_RIGHT_LIKE))
				{
					string.append("like");
				}
				else
				{
					string.append(operator);
				}
				string.append(" ");
				string.append("?");
				if(operator.equals(RConstants.OPERATOR_LIKE))
					list.add("%"+value+"%");
				else if(operator.equals(RConstants.OPERATOR_LEFT_LIKE))
					list.add(value+"%");
				else if(operator.equals(RConstants.OPERATOR_RIGHT_LIKE))
					list.add("%"+value);
				else
					list.add(value);
				
			}
		}
		String sql=string.toString();
		if(!sql.equals(""))
		{
			sqlResult.appendSql(" ");
			sqlResult.appendSql(p_prepend);
			sqlResult.appendSql(" ");
			if(len>1)
			{
				sqlResult.appendSql("(");
				sqlResult.appendSql(sql);
				sqlResult.appendSql(")");
			}
			else
			{
				sqlResult.appendSql(sql);
			}
			sqlResult.appendValues(list);
		}
		return sqlResult;
	}
	
	public static void main(String[] args)
	{
		List list=new ArrayList();
		list.add("aaaa");
		list.add("bbbb");
		
		Object ary[]=list.toArray();
		int len=ary.length;
		for(int i=0;i<len;i++)
		{
			System.out.println(ary[i]);
		}
	}
	
	
	
}
