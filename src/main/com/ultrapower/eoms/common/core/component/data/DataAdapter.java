package com.ultrapower.eoms.common.core.component.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ultrapower.eoms.common.core.component.database.ConnectionPool;
import com.ultrapower.eoms.common.core.exception.BaseException;
import com.ultrapower.eoms.common.core.util.StringUtils;


/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-1-21
 */

public class DataAdapter 
{
	private static ThreadLocal connLocal = new ThreadLocal();
	private PreparedStatement pstmt;
   /**
   数据连接，只有使用事务时才会用该变量
    */
//   private Connection conn;
   
   /**
   是否使用事务 true 使用事务 false 不使用事务
    */
   private Boolean isUseTrans = false;
   private String poolName="";
   
   /**
    * 是否采用事务
    * @param ts
    */
   public void setTransaction(boolean ts)
   {
	   this.isUseTrans=ts;
   }
   
//   private int TLevel=1; //1 datarow级  2 DataTable级 3 DataSet级
   /**
   @param poolName - 数据连接池名称，如果没有则取默认的连接池
   @roseuid 4B56BE3002D4
    */
   public DataAdapter(String poolName) 
   {  
	   this.poolName=poolName;
	 
	 //  iDatabase=GetDataBase.createDataBase(poolName);
   }
   
   /**
   @roseuid 4B56CB8800CF
    */
   public DataAdapter() 
   {
	  // iDatabase=GetDataBase.createDataBase();
   }
   
   /**
   根据DataSet数据进行处理，操作类型由DataRow中的操作类型(optype)来决定(增加、修改
   、删除),可以使用事务
   @param p_dataset
   @return java.lang.Boolean
   @roseuid 4B56AB44037D
   @see 
    */
   public int execute(DataSet p_dataset) 
   {
	    this.getConn();
	    int result=0;
		int count=0;
		if(p_dataset!=null)
			count=p_dataset.length();
		int effect;
		try{
			this.beginTrans();//开始事务，如果没有设置事务则不起作用
			//循环执行表
			for(int i=0;i<count;i++)
			{
				effect=this.operationDataTable(p_dataset.getTable(i),0);
				if(effect>-1)
				{
					result+=effect;
				}
				else
				{
					//如果采用事务则退出
					if(this.isUseTrans)
					{
						result=-1;
						break;
					}
				}
			}
		}catch(Exception ex)
		{
			if(this.isUseTrans)
			{
				result=-1;
			}
			ex.printStackTrace();
			//throw new BaseException("插入dataset数据出错："+ex.getMessage());
		}
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}		
	    return result;

   }
   
   /**
   根据DataTable数据进行处理，操作类型由DataRow中的操作类型(optype)来决定(增加、修
   改、删除),可以使用事务
   @param p_dataset
   @return java.lang.Boolean
   @roseuid 4B56B505019C
    */
   public int execute(DataTable p_datatable) 
   {
	   this.getConn();
	   this.beginTrans();
	   int result=-1;
	   try{
		   result=operationDataTable(p_datatable,0);
	   }catch(Exception ex)
	   {
		   if(this.isUseTrans)
		   {
			   result=-1;
		   }
		   ex.printStackTrace();
	   }
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}   
	   return result;
   }
   
   /**
    * 操作一条数据
    * @param tblName
    * @param p_dtrow
    * @param conditions
    * @param values
    * @return
    */
   public int putDataRow(String tblName,DataRow p_dtrow, String conditions, Object[] values)
   {
	   int result=-1;
	   this.getConn();
	   try{
		   this.beginTrans();
		   result=exeDataRow( tblName, p_dtrow,conditions, values);
	   }
	   catch(Exception ex)
	   {
		   if(this.isUseTrans)
		   {
			   result=-1;
		   }
		   ex.printStackTrace();
	   }
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}  	   
	   return result;
	   
   }
   /**
    * 将DataTable数据保存到数据库
    * @param p_datatable
    * @return
    */
   private int operationDataTable(DataTable p_datatable,int optType) throws BaseException
   {
		int result=0;
		int rowCount=0;
		if(p_datatable!=null)
			rowCount=p_datatable.length();
		int effect;
		DataRow dtRow;
		StringBuffer strWhere;
		Object[] values;
		try
		{
			for (int row = 0; row < rowCount; row++) 
			{
				effect = 0;
				dtRow = p_datatable.getDataRow(row);
				if (optType==1 || dtRow.getOptype() == 1)// 添加
				{
					effect = exeDataRow(p_datatable.getTableName(), dtRow,null, null);
				} else if (optType==2|| dtRow.getOptype() == 2)// 修改
				{
					String[] aryKey = p_datatable.getPrimaryKey();
					int lens = 0;
					if (aryKey != null) {
						lens = aryKey.length;
					}
					strWhere = new StringBuffer();
					values = new Object[lens];
					for (int len = 0; len < lens; len++) {
						if (len > 0) {
							strWhere.append(" and ");
						}
						strWhere.append(aryKey[len] + "=? ");
						values[len] = dtRow.getString(aryKey[len]);
					}

					effect = exeDataRow(p_datatable.getTableName(), dtRow,
							strWhere.toString(), values);
				}
				if (effect > -1)
					result += effect;
				else 
				{
					//如果采用事务则退出
					if(this.isUseTrans)
					{
						result=-1;
						break;
					}
				}
			}//for (int row = 0; row < rowCount; row++)
		}catch(Exception ex)
		{
			if(this.isUseTrans)
			{
				result=-1;
			}
			ex.printStackTrace();
			throw new BaseException(ex.getMessage());
		}
	    return result;
   }
   /**
   根据DataSet数据进行处理，将DataSet中的数据都插入到对应的表中,DataRow中操作类型不
   在起作用,可以使用事务
   @param p_dataset
   @return int
   @roseuid 4B56CE6E0194
    */
   public int executeAdd(DataSet p_dataset) 
   {
	    this.getConn();
	    int result=0;
		int count=0;
		if(p_dataset!=null)
			count=p_dataset.length();
		int effect;
		try{
			this.beginTrans();//开始事务，如果没有设置事务则不起作用
			//循环执行表
			for(int i=0;i<count;i++)
			{
				effect=this.operationDataTable(p_dataset.getTable(i),1);
				if(effect>-1)
				{
					result+=effect;
				}
				else
				{
					if(this.isUseTrans)
					{
						result=-1;
						break;
					}
				}
			}
		}catch(Exception ex)
		{
			if(this.isUseTrans)
			{
				result=-1;
			}
			ex.printStackTrace();
			//throw new BaseException("插入dataset数据出错："+ex.getMessage());
		}
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}		
	    return result;

   }
   
   /**
   根据DataTable数据进行处理，将DataSet中的数据都插入到对应的表中,DataRow中操作类型
   不在起作用,可以使用事务
   @param p_datatable
   @return int
   @roseuid 4B56C36E00CF
    */
   public int executeAdd(DataTable p_datatable) 
   {
	   this.getConn();
		this.beginTrans();
		int result =0;
		try {
			result = operationDataTable(p_datatable, 1);
		} 
		catch (Exception ex) 
		{
			if(this.isUseTrans)
			{
				result = -1;
			}
			ex.printStackTrace();
		}
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}
		return result;
   }
   
   /**
	 * 根据DataSet数据进行处理，将DataSet中的数据进行修改,修改的条件是DataTable中的prim
	 * aryKey作为条件,(DataRow中操作类型不在起作用),可以使用事务
	 * 
	 * @param p_dataset
	 * @return int
	 * @roseuid 4B56C37C008E
	 */
   public int executeUpdate(DataSet p_dataset) 
   {
	    this.getConn();
	    int result=0;
		int count=0;
		if(p_dataset!=null)
			count=p_dataset.length();
		int effect;
		try{
			this.beginTrans();//开始事务，如果没有设置事务则不起作用
			//循环执行表
			for(int i=0;i<count;i++)
			{
				effect=this.operationDataTable(p_dataset.getTable(i),2);
				if(effect>-1)
				{
					result+=effect;
				}
				else
				{
					if(this.isUseTrans)
					{
						result=-1;
						break;
					}
				}
			}
			//this.commitTrans();
		}catch(Exception ex)
		{
			if(this.isUseTrans)
			{
				result=-1;
			}
//			this.rollbackTrans();
			ex.printStackTrace();
			//throw new BaseException("插入dataset数据出错："+ex.getMessage());
		}
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}
	    return result;

   }
   
   /**
   根据DataTable数据进行处理，操作类型由DataRow中的操作类型(optype)来决定(增加、修
   改、删除),可以使用事务
   @param p_dataset
   @return int
   @roseuid 4B56CD540302
    */
   public int executeUpdate(DataTable p_datatable) 
   {
	   this.getConn();
	   this.beginTrans();
	   int result=0;
	   try{
		   result=operationDataTable(p_datatable,2);
	   }catch(Exception ex)
	   {
		   //this.rollbackTrans();
		   if(this.isUseTrans)
		   {
			   result=-1;
		   }
		   ex.printStackTrace();
	   }
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}
	   return result;
   }
   
   /**
   根据SQL更新数据,不能使用事务，即使设置了也无效
   @param sql - sql语句
   @param values - 值数组，用于变量绑定
   @return int
   @roseuid 4B56D66401D0
    */
   public int execute(String sql, Object[] values) 
   {
	   int result=-1;
		PreparedStatement pstmt=null;
		//Connection conn;
		try {
			
			pstmt= this.getConn().prepareStatement(sql);
			int len=0;
			if(values!=null)
				len=values.length;
			int j = 0; // 查询参数计数器
			for (int i = 0; i <len; i++) {
				if (values[i] != null) { // 如果不是空则解析查询参数
					pstmt.setObject(++j, values[i]); // 解析查询参数
				}
			}
			result = pstmt.executeUpdate();
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		
		} 
		finally 
		{
			if (result > -1)
			{
				this.commitTrans();
			}
			else if (result < 0)
			{
				this.rollbackTrans();
			}
		}
		return result;	   
   }
   
   /**
   根据DataSet数据进行处理，将根据DataSet中的数据进行删除,删除的条件是DataRow中的数
   据,(DataRow中操作类型不在起作用),可以使用事务
   @param p_dataset
   @return int
   @roseuid 4B56D00E0200
    */
/*   public int executeDelete(DataSet p_dataset) 
   {
	    this.getConn();
	    int result=0;
		int count=0;
		if(p_dataset!=null)
			count=p_dataset.length();
		int effect;
		try{
			this.beginTrans();//开始事务，如果没有设置事务则不起作用
			//循环执行表
			for(int i=0;i<count;i++)
			{
				effect=this.operationDataTable(p_dataset.getTable(i),3);
				if(effect>-1)
				{
					result+=effect;
				}
				else
				{
					if(this.isUseTrans)
					{
						this.rollbackTrans();
						return -1;
					}
				}
			}
			this.commitTrans();
		}catch(Exception ex)
		{
			this.rollbackTrans();
			ex.printStackTrace();
			//throw new BaseException("插入dataset数据出错："+ex.getMessage());
		}
	    return result;

   }*/
   
   /**
   根据DataTable数据进行处理，将根据DataTable中的数据进行删除,删除的条件是DataRow中
   的数据,(DataRow中操作类型不在起作用),可以使用事务
   @param p_dataset
   @return int
   @roseuid 4B56D00E0232
    */
/*   public int executeDelete(DataTable p_dataset) 
   {
    return 0;
   }*/
   
   /**
   根据SQL删除数据
   @param sql - sql语句
   @param values - 值数组，用于变量绑定
   @return int
   @roseuid 4B56D11F03E1
    */
/*   public int executeDelete(String sql, Object[] values) 
   {
    return 0;
   }*/
   
   /**
   获得数据连接
   @return java.sql.Connection
   @roseuid 4B56D2FA008D
    */
   public Connection getConn() 
   {
	   Object obj=connLocal.get();
	   Connection conn=null;
	   if(obj!=null)
	   {
		   conn=(Connection)obj;
	   }
	   try {
		    
			if (conn == null||conn.isClosed())
			{
				conn = ConnectionPool.getConn(this.poolName);
				connLocal.set(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
   }
   
   /**
   把sql中的Form名替换成对应的表名格式{form名}
   @param p_sql
   @roseuid 4B57B666034E
    */
   public void analyseFormName(String p_sql) 
   {
    
   }
   
   /**
   根据SQL查询数据,条件为DataRow中的数据
   @param p_sql
   @param p_dataRow
   @param p_pageNum
   @param p_pageSize
   @param p_isCount
   @return com.ultrapower.system.data.DataTable
   @roseuid 4B57C27F00FA
    */
/*   public DataTable executeQuery(String p_sql, DataRow p_dataRow, int p_pageNum, int p_pageSize, int p_isCount) 
   {
	   
	   return null;
   }*/
   
   /**
   根据SQL查询数据,返回第一行数据,条件为DataRow中的数据
   @param p_sql
   @param p_datarow
   @param p_pageNum
   @param p_pageSize
   @param p_isCount
   @return com.ultrapower.system.data.DataRow
   @roseuid 4B57C27F0139
    */
/*   public DataRow executeQueryDataRow(String p_sql, DataRow p_datarow, int p_pageNum, int p_pageSize, int p_isCount) 
   {
    return null;
   }*/
   
   /**
   开始事务，如果没有设置事务则不起作用
   @throws SQLException 
   @roseuid 4B57EE79009E
    */
   private void beginTrans() 
   {
	 //是否采用事务，如果是则申请一个整体的数据连接
     //isUseTrans=true;
	 Connection conn=this.getConn();
     try {
    	 if(isUseTrans)
    	 {
    		 if(conn!=null)
    			 conn.setAutoCommit(false);
    	 }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   /**
   如果使用了事务则提交事务，否则不起作用
   @throws SQLException 
   @roseuid 4B57EE8600B8
    */
   private void commitTrans()
   {
	   Connection conn=this.getConn();
	     try {
		    	 if(conn!=null && isUseTrans)
		    	 {
					conn.commit();
					conn.setAutoCommit(true);
		    	 }
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				ConnectionPool.free(conn);
				conn=null;
			}
   }
   
   /**
   回滚事务
   @roseuid 4B57EE910104
    */
   private void rollbackTrans() 
   {
	   Connection conn=this.getConn();
	     try {
	    	  if(conn!=null&& isUseTrans)
	    	  {
	    			conn.rollback();
	    			conn.setAutoCommit(true);
	    			System.out.println("事务回滚");
	    	  }
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				//this.iDatabase.closeConn();
				ConnectionPool.free(conn);
			}
			//isUseTrans=false;	
	   
	       
   }
   /**
    * 操作数据库数据，增加或修改数据库表数据
    * @param tblName
    * @param p_dtrow
    * @param conditions 条件，如果该值为null 则是添加，否则是修改
    * @param values  条件中的值
    * @return
    * @throws Exception
    */
   private int exeDataRow(String tblName,DataRow p_dtrow, String conditions, Object[] values) 
   
   {
	   
		String sql = "";
		String sqlplt="";
		int affectableRow = 0; // 执行SQL后影响的行数
		conditions=StringUtils.checkNullString(conditions).trim();
		if (conditions.equals("")) {
			sql = "INSERT INTO " + tblName + "(";
			sqlplt=sql;
			for (int i = 0; i < p_dtrow.length(); ++i) {
				String k = p_dtrow.getKey(i);
				sql += k;
				sqlplt+=k;
				if (i != p_dtrow.length() - 1) {
					sql += ", ";
					sqlplt+= ", ";
				}
			}
			sql += ") VALUES (";
			sqlplt+= ") VALUES (";
			for (int j = 0; j < p_dtrow.length(); ++j) 
			{
				sqlplt+= "'"+p_dtrow.getString(j)+"'";
				sql += (p_dtrow.getObject(j) == null) ? "null" : "?"; // 如果row中有空值则设置为null，否则设置为查询参数
				if (j != p_dtrow.length() - 1) {
					sql += ", ";
					sqlplt += ", ";
				}
			}
			sql += ")";
			sqlplt+= ")";
			System.out.println("insert sql: "+sqlplt);
			
			
		} else {
			sql = "UPDATE " + tblName + " SET ";
			for (int i = 0; i < p_dtrow.length(); ++i) {
				String k = p_dtrow.getKey(i);
				sql += k + "=" + ((p_dtrow.getObject(i) == null) ? "null" : "?"); // 设置查询参数
				if (i != p_dtrow.length() - 1) {
					sql += ", ";
				}
			}
			sql += " WHERE ";
			
			int end=conditions.indexOf(" ");
			if(end<0) end=0;
			String pixChar=conditions.substring(0,end);
			if(pixChar.equals(""))
			{
				sql +=conditions;
			}
			else
			{
				pixChar=pixChar.toUpperCase();
				if( pixChar.equals("AND") || pixChar.equals("OR") ||pixChar.equals("EXISTS")||pixChar.equals("NOT"))
					sql +=" 1=1 "+conditions;
				else
					sql +=conditions;
			}
			
		}//if (conditions == null) {
		
		PreparedStatement pstmt=null;
		try {
			// conn = database.getConnection();
			
			pstmt= this.getConn().prepareStatement(sql);
/*			if (this.isUseTrans)
				pstmt= this.conn.prepareStatement(sql);
			else
			{
				pstmt=ConnectionPool.getConn().prepareStatement(sql);
			}*/
			int j = 0; // 查询参数计数器
			for (int i = 0; i < p_dtrow.length(); i++) {
				if (p_dtrow.getObject(i) != null) { // 如果不是空则解析查询参数
					pstmt.setObject(++j, p_dtrow.getObject(i)); // 解析查询参数
				}
			}
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					pstmt.setObject(++j, values[i]);// 预定的规则，null不能放到查询参数中要以name=null的静态形式存放
				}
			}
			affectableRow = pstmt.executeUpdate();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			// throw new DBAccessException(InforGeter.getErrorInfor(this,
			// "putRow", ex,"更新表" + name + "中的数据时出错！"));
			return -1;
		} finally {
			// database.disConnect(conn);
			try
			{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{}
		}
		return affectableRow;
	}   
   
}
