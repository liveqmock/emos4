package com.ultrapower.eoms.common.core.component.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.ultrapower.eoms.common.core.component.database.ConnectionPool;
import com.ultrapower.eoms.common.core.util.StringUtils;



public class QueryAdapter {

	private String poolName="";
	private String dbType="ORACLE";
	/**
		用于executeQuery方式时查询的总行数
	 */
	private int rowCount = 0;

	/**
	用于executeQuery方式时查询的当前页数
	 */
//	private int pageNumber = 0;

	/**
	用于executeQuery方式时查询的每页行数
	 */
	private int pageSize = 0;

	/**
	用于executeQuery方式时查询的总页数
	 */
	private int pageCount = 0;

	/**构造函数
	   @param poolName - 数据连接池名称，如果没有则取默认的连接池
	   @roseuid 4B56BE3002D4
	 */
	public int getPageCount()
	{
//		int result=0;
		if(this.rowCount>0 && pageSize>0)
		{
			pageCount=rowCount/pageSize;
			if((rowCount%pageSize)>0)
				pageCount++;
		}
		
		return pageCount;
	}
	public int getQueryResultSetCount()
	{
		return this.rowCount;
	}
	
	public QueryAdapter(String poolName,String dbType) 
	{
		this.poolName=StringUtils.checkNullString(poolName).trim();
		this.dbType=StringUtils.checkNullString(dbType).trim();
		if("".equals(this.dbType))
		{
			this.dbType="ORACLE";
		}
	}	
	public QueryAdapter(String poolName) 
	{
		this.poolName=StringUtils.checkNullString(poolName).trim();
	}

	/**
	 * 构造函数
	   @roseuid 4B56CB8800CF
	 */
	public QueryAdapter() 
	{
		
	}
	
	/**
	 * 
	 * @param p_sql
	 * @param values
	 * @return
	 */
	public DataTable executeQuery(String p_sql, final Object... values)
	{
		return executeQuery(p_sql,values,0,0,0);
	}
	public DataTable executeQuery(String p_sql,  Object[] values, int p_isCount)
	{
		return executeQuery(p_sql,values,0,0, p_isCount);
	}	
	/**
	   根据SQL查询数据,返回查询结果DataTable
	   @param p_sql 查询的sql
	   @param values sql中参数的值，用于变量绑定时传递的值，如果是非变量绑定则赋null
	   @param p_curpage 查询的页数
	   @param p_pageSize 每页的行数
	   @param p_isCount 是否查询总数，大于零则查询总数，当等于2时即查询总数又在后台打出查询的sql
	   @return com.ultrapower.system.data.DataTable
	   @roseuid 4B56B912000B
	 */
	/*
	public DataTable executeQuery(final String p_sql, final Object[] values,final int p_curpage, final int p_pageSize, final int p_isCount) 
	{
		
		this.pageSize=p_pageSize;
		String sql=p_sql;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		DataTable dataTable=null;
		Connection conn=null;
		try {
			conn=ConnectionPool.getConn(this.poolName);
			// conn = database.getConnection();
			int intStartRow=0;
			if(p_curpage==1)
				intStartRow=1;
			else if(p_curpage>1)
				intStartRow=(p_curpage-1)*p_pageSize+1;
			else
				intStartRow=0;
			int intRowCount=p_curpage*p_pageSize;
			
			int vLens=0;
			if(values!=null)
				vLens=values.length;
			int chartLen=p_sql.length();
			if(p_isCount==2)
			{
				StringBuffer sqlt=new StringBuffer();
//				String sqlT=p_sql;
//				for (int in = 0;  in < vLens;  in++)
//				{
//					sqlT=sqlT.replaceFirst("\\?", "'"+StringUtils.checkNullString(values[in])+"'");
//				}
				int in=0;
				for(int chartAt=0;chartAt<chartLen;chartAt++)
				{
					if(p_sql.charAt(chartAt)=='?')
					{
						sqlt.append("'"+StringUtils.checkNullString(values[in])+"'");
						in++;
					}
					else
						sqlt.append(p_sql.charAt(chartAt));
				}
				System.out.println("Query By SQL: "+sqlt.toString());
			}
			if(p_isCount>0)//查询数据总行数
				this.rowCount=getQueryRowCount(p_sql,values);
			
//			if(p_curpage>0 && p_pageSize>0)
//			{
//				if(vLens>0)//变量绑定的分页
//					sql=QueryControl.bindGetSqlStringForPagination(sql,"ORACLE",p_curpage,p_pageSize);
//				else
//					sql=QueryControl.getSqlStringForPagination(sql,"ORACLE",p_curpage,p_pageSize);
//			}
			
			
//			pstmt= conn.prepareStatement(sql);
			pstmt= conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if(p_curpage>0 && p_pageSize>0)
			{
				//最大查询到第几条记录
				pstmt.setMaxRows(intRowCount);
			}			
			
			int i;
			for (i = 0;  i < vLens;  i++) 
			{
				if(values[i]!=null)
				{
					pstmt.setObject( i + 1, values[ i]);
				}
			}	
		
//			if(vLens>0 && p_curpage>0 && p_pageSize>0)
//			{
//				i++;
//				pstmt.setObject( i, String.valueOf(intRowCount));
//				i++;
//				pstmt.setObject( i, String.valueOf(intStartRow));
//			}	
				
			esultSet=pstmt.executeQuery();
			
			if(p_curpage>0 && p_pageSize>0)
			{
				resultSet.first();
				resultSet.relative(intStartRow-2);
				
			}
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int cols = rsmd.getColumnCount();
			//去掉为分页加行数的数据列
//			if(p_curpage>0 && p_pageSize>0)
//			{
//				cols--;
//			}		
			dataTable=new DataTable("QTABLE");
			String colName;
			
			while(resultSet.next())
			{
				DataRow dtRow=new DataRow();
				dtRow.setOptype(4);
				for (i = 1; i <= cols; i++) 
				{
					colName = rsmd.getColumnName(i);
					Object value;
					if(resultSet.getObject(i)==null)
						value="";
					else	
					{
						if(rsmd.getColumnType(i)==java.sql.Types.CLOB)
						{
							Clob m_clob=resultSet.getClob(i);
							value=getClobString(m_clob);
						}
						else
							value=resultSet.getObject(i); // 作通用类型处理,这样row中的类型都是Object型的。
					}
					dtRow.put(colName, value);
				}
				dataTable.putDataRow(dtRow);
			}//while(resultSet.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(resultSet!=null)
				{
					resultSet.close();
				}
			}catch(Exception ex)
			{}			
			try{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{}
			ConnectionPool.free(conn);
		}
		return dataTable;
	}
	*/
	


	/**
	 * 将带参数的SQL转换为普通的SQL
	 */
	public String getSqlString(final String p_sql, final Object[] values)
	{
//		String sqltStr = "";
		StringBuffer sqlt=new StringBuffer();
		int chartLen=0;
		if(p_sql!=null)
		{
			chartLen=p_sql.length();
		}
		
		int in=0;
		for(int chartAt=0;chartAt<chartLen;chartAt++)
		{
			if(p_sql.charAt(chartAt)=='?')
			{
				sqlt.append("'"+StringUtils.checkNullString(values[in])+"'");
				in++;
			}
			else
				sqlt.append(p_sql.charAt(chartAt));
		}
		
//		if(sqlt.indexOf("baseSummary")!=-1){
//			sqltStr = sqlt.toString().replaceAll("baseSummary", "upper(baseSummary)");
//		}
//		sqlt = new StringBuffer(sqltStr);
		
		//System.out.println("Query By SQL: "+sqlt.toString());
		return sqlt.toString();
		
	}
	
	public DataTable executeQuery(final String p_sql, final Object[] values,final int p_curpage, final int p_pageSize, final int p_isCount) 
	{
		this.pageSize=p_pageSize;
		String sql=p_sql;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		DataTable dataTable=null;
		Connection conn=null;
		try {
			conn=ConnectionPool.getConn(this.poolName);
			int intStartRow=0;
			if(p_curpage==1)
				intStartRow=1;
			else if(p_curpage>1)
				intStartRow=(p_curpage-1)*p_pageSize+1;
			else
				intStartRow=0;
			int intRowCount=p_curpage*p_pageSize;
			
			int vLens=0;
			if(values!=null)
				vLens=values.length;
			if(p_isCount==2)
			{
				System.out.print("Query By SQL: ");
				System.out.print(getSqlString(p_sql,values));
			}
			if(p_isCount>0)//查询数据总行数
				this.rowCount=getQueryRowCount(p_sql,values);
			
			if(p_curpage>0 && p_pageSize>0)
			{
				//if(vLens>0)//变量绑定的分页
					sql=QueryControl.bindGetSqlStringForPagination(sql,dbType,p_curpage,p_pageSize);
/*				else
					sql=QueryControl.getSqlStringForPagination(sql,dbType,p_curpage,p_pageSize);*/
			}

			pstmt= conn.prepareStatement(sql);
			
			int i;
			for (i = 0;  i < vLens;  i++) 
			{
				if(values[i]!=null)
				{
					pstmt.setObject( i + 1, values[ i]);
				}
			}	
			
			if( p_curpage>0 && p_pageSize>0)
			{
				if(this.dbType.equals("ORACLE"))
				{
					i++;
					pstmt.setObject( i, String.valueOf(intRowCount));
					i++;
					pstmt.setObject( i, String.valueOf(intStartRow));					
				}	
				else if(this.dbType.equals("MYSQL"))
				{ 
					i++;
//					pstmt.setObject( i, String.valueOf(intStartRow));
					pstmt.setInt(i,  intStartRow-1);
					i++;
//					pstmt.setObject( i, String.valueOf(p_pageSize));
					pstmt.setInt(i,  p_pageSize);
				}
				else if(this.dbType.equals("SQLSERVER"))
				{
					
				}				
	 
			}	
				
			resultSet=pstmt.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int cols = rsmd.getColumnCount();
			//去掉为分页加行数的数据列
			if(dbType.equals("ORACLE") && p_curpage>0 && p_pageSize>0)
			{
				cols--;
			}			
			dataTable=new DataTable("QTABLE");
			String colName;
			int[] columnType = new int[cols];
			for(int c=0;c<cols;c++)
			{
				columnType[c] = rsmd.getColumnType(c + 1);
			}
			dataTable.setColumnType(columnType);
			Blob m_Blob;
			Clob m_clob;
			while(resultSet.next())
			{
				DataRow dtRow=new DataRow();
				dtRow.setOptype(4);
				for (i = 1; i <= cols; i++) 
				{
					colName = rsmd.getColumnName(i);
					Object value;
					if(resultSet.getObject(i)==null)
						value="";
					else
					{
						if(rsmd.getColumnType(i)==java.sql.Types.CLOB)
						{
							m_clob=resultSet.getClob(i);
							value=getClobString(m_clob);
						}
						else if (rsmd.getColumnType(i) == java.sql.Types.BLOB
								|| rsmd.getColumnType(i) == java.sql.Types.LONGVARBINARY)
						{
							m_Blob=resultSet.getBlob(i);
							value=getBlobString(m_Blob);
						}
						else
							value=resultSet.getObject(i); // 作通用类型处理,这样row中的类型都是Object型的。
					}
					dtRow.put(colName, value);
				}
				dataTable.putDataRow(dtRow);
			}//while(resultSet.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{

			try{
				if(resultSet!=null)
				{
					resultSet.close();
				}
			}catch(Exception ex)
			{}			
			try{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{}
			ConnectionPool.free(conn);
		}
		return dataTable;
	}
	
	
	/**
	   根据SQL查询数据,返回第一行数据DataRow
	   @param p_sql
	   @param p_values
	   @param p_pageNum
	   @param p_pageSize
	   @param p_isCount
	   @return com.ultrapower.system.data.DataRow
	   @roseuid 4B56CC4A0266
	 */
/*	public DataRow executeQueryDataRow(String p_sql, Object[] p_values, int p_pageNum, int p_pageSize, int p_isCount) 
	{
		return null;
	}*/
	
	
	private int getQueryRowCount(String p_sql,Object[] p_values)
	{
		int result=0;
		String sql=getReBuildCountSQL(p_sql);
		if("".equals(sql))
			return result;
		int vLens=0;
		if(p_values!=null)
			vLens=p_values.length;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		Connection conn=null;
		try{
			conn=ConnectionPool.getConn(this.poolName);
			pstmt=conn.prepareStatement(sql);
			int i;
			for (i = 0;  i < vLens;  i++) {
				if(p_values[i]!=null)
				{
					pstmt.setObject( i + 1, p_values[ i]);
				}
			}	
			resultSet=pstmt.executeQuery();
			while(resultSet.next())
			{
				result=resultSet.getInt(1);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				if(resultSet!=null)
				{
					resultSet.close();
				}
			}catch(Exception ex)
			{}				
			try{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{}
			ConnectionPool.free(conn);
		}
		return result;
	}
	
	private String getReBuildCountSQL(String sql) {
		String regstr = "/*COUNT*/";
		String strSelect = sql.toString();// "select /*COUNT*/ base.c1
													// /*COUNT*/ from ";//
		int startlen = strSelect.indexOf(regstr);
		// strSelect=strSelect.replaceFirst("/\\*COUNT\\*/","");
		int endlen = strSelect.lastIndexOf(regstr);
		if (startlen < 0 || endlen < 0)
			return "";
		strSelect = strSelect.substring(0, startlen) + " count(*) as ROWCOUNT "+ strSelect.substring(endlen + 9);
		return strSelect;
	}	
	
	/**
	 * 获取大字段(Clob)中的字符串值
	 * @param p_clob
	 * @return
	 */
	private String getClobString(Clob p_clob)
	{
		String strValue="";
	
		if (p_clob!=null) 
		{
			try{
			if(p_clob.length()>0)
			{
				strValue=p_clob.getSubString((long)1,(int)p_clob.length());
			}
			}catch(Exception ex)
			{
				strValue="";
			}
		}
		return strValue;
	}
	/**
	 * 读取Blob字符串
	 * @param p_blob
	 * @return
	 */
	private String getBlobString(Blob p_blob)
	{
		InputStream is;
	    StringBuffer buf = new StringBuffer();
	    try 
	    {
			is = p_blob.getBinaryStream();
			InputStreamReader r = new InputStreamReader(is);
			int c;
			for (;;) 
			{
				c = r.read();
				if (c <= 0)
					break;
				buf.append((char) c);
			}
		}
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
		return buf.toString();
	}
	
}

