package com.ultrapower.eoms.common.core.component.data;

public class QueryControl {

	/**
	 * 根据数据库类型以及查询的Sql生成分页查询的Sql
	 * 
	 * @param strSql查询的Sql语句
	 * @param strDataType数据库的类型
	 * @param intStartRow开始的行数
	 * @param intStepRow要返回的多少行的数量
	 * @author xufaqiu
	 * @date 2006-11-15
	 * @return
	 */
	public static String getSqlStringForPagination(String strSql, String strDataBaseType,
			int p_PageNumber,int p_StepRow)
	{
		int intStartRow;	
		int intRowCount;
		if(p_PageNumber==1)
			intStartRow=1;
		else if(p_PageNumber>1)
			intStartRow=(p_PageNumber-1)*p_StepRow+1;
		else
			intStartRow=0;
		intRowCount=p_PageNumber*p_StepRow;
		//System.out.println("改之前："+strSql);
		StringBuffer strRe = new StringBuffer();
		if (strDataBaseType.toUpperCase().equals("ORACLE")) {
			// Oracle是通过Rownum限定行数
			strRe.append("select * from ( ");
			strRe.append("  select row_.*, rownum rownum_ from (  ");
			strRe.append(strSql);
			strRe.append(") row_ where rownum <="
					+ Integer.toString(intRowCount));
			strRe.append(") rsresult where rownum_>=" + Integer.toString(intStartRow));
		}else if (strDataBaseType.toUpperCase().equals("MYSQL"))
		{
				strRe.append(strSql);
				strRe.append(" limit ?,? ");
		}
		else if (strDataBaseType.toUpperCase().equals( "SQLSERVER"))
		{
				// SqlServer是通过Top来限定行数的
		}
		 //System.out.println("改之后："+strRe.toString());
		return strRe.toString();
	}	
	
	
	public static String bindGetSqlStringForPagination(String strSql, String strDataBaseType,
			int p_PageNumber,int p_StepRow)
	{

		//System.out.println("改之前："+strSql);
		StringBuffer strRe = new StringBuffer();
		if (strDataBaseType.toUpperCase().equals("ORACLE")) {
			// Oracle是通过Rownum限定行数
			strRe.append("select * from ( ");
			strRe.append("  select row_.*, rownum rownum_ from (  ");
			strRe.append(strSql);
			strRe.append(") row_ where rownum <=?");
			strRe.append(") rsresult where rownum_>=?");
		}
		else if (strDataBaseType.toUpperCase().equals("MYSQL"))
		{
			strRe.append(strSql);
			strRe.append(" limit ?,? ");
	 
		}
		else if (strDataBaseType.toUpperCase().equals("SQLSERVER"))
		{
			// SqlServer是通过Top来限定行数的
		}

		else 
		{
			strRe.append(strSql);
		}
		 //System.out.println("改之后："+strRe.toString());
		return strRe.toString();
	}	
	
	
}

