
package com.ultrapower.eoms.common.core.component.data;

import java.util.Vector;
import java.util.HashMap;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-1-21
 */
public class DataSet 
{
//   private Vector ordering;
//   /**
//   存放DataTable对象
//    */
//   private HashMap map;
	//存放DataTable对象
	DataRow tables=new DataRow();
   /**
   @roseuid 4B5572920189
    */
   public DataSet() 
   {
    
   }
   
   /**
   @return int
   @roseuid 4B5677670288
    */
   public int length() 
   {
    return tables.length();
   }
   
   /**
   @param p_DataTable
   @roseuid 4B5679B50398
    */
   public void put(DataTable p_DataTable) 
   {
	   if(p_DataTable==null)
		   return ;
	    String name=p_DataTable.getTableName();
		if(name==null ||"".equals(name))
			return ;
		name=name.toUpperCase();
		tables.put(name, p_DataTable);
   }
   
   /**
   根据表名获取DataTable对象
   @param tblName
   @return com.ultrapower.system.data.DataTable
   @roseuid 4B5565360185
    */
   public DataTable getTable(String tblName) 
   {
		 Object obj=tables.getObject(tblName);
		 if(obj!=null)
			 return (DataTable)obj;
	    return null;
	    
   }   
   /**
   根据index取DataTable对象
   @param p_index
   @return com.ultrapower.system.data.DataTable
   @roseuid 4B57E1890395
    */
   public DataTable getTable(int p_index) 
   {
	 Object obj=tables.getObject(p_index);
	 if(obj!=null)
		 return (DataTable)obj;
    return null;
   }
}
