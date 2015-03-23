
package com.ultrapower.eoms.common.core.component.data;

import java.util.HashMap;
import java.util.Vector;

import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;


/**
 * 数据行对象
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-1-21
 */

public class DataRow 
{
	/**
	 * 排序,由于Hashtable不提供通过索引取得值的方法，并且其中的键值对也不是按照put上去时的顺序排列的。
	 * 注意：Vector中加入的对象是有序的，即按加入的顺序排列并且能够根据索引访问，可以看成是可变大小的数组 List可以取代Vector
	 */
	private Vector ordering = new Vector();
	
	/**
	 * 存放键值对（表中字段名称与字段值）
	 */
	private HashMap map = new HashMap();
   
   /**
   行数据操作类型，0 初始的值  1：新增 2 修改 3 删除  4是查询
    */
   private int optype=0;
   
   /**
   @roseuid 4B5583B70009
    */
   public DataRow() 
   {
    
   }
   
   public HashMap getRowHashMap()
   {
	   return map;
   }
   /**
   根据列名获取列数据
   @param col
   @return java.lang.String
   @roseuid 4B55800303A1
    */
   public String getString(String colName) 
   {
		if(colName==null)
			colName="";
		if(colName.equals(""))
			return "";
		colName=colName.toUpperCase();
		return StringUtils.checkNullString(map.get(colName));
   }
   
   /**
    *根据列索引获取列数据
   @param col
   @return java.lang.String
   @roseuid 4B57EFB70066
    */
   public String getString(int col) 
   {
	   String colName=getKey(col);
	   
	   return getString(colName);
   }   
   
   /**
   @param col
   @return double
   @roseuid 4B55808A0318
    */
   public double getDouble(int col) 
   {
    return 0;
   }
   
   /**
   根据列名取列数据
   @param col
   @return Object
   @roseuid 4B558267025B
    */
   public Object getObject(String colName) 
   {
		if(colName==null)
			colName="";
		if(colName.equals(""))
			return null;
		colName=colName.toUpperCase();
		return  map.get(colName);
   }
   
   /*
    * 根据列取列值
   @param col
   @return Object
   @roseuid 4B558267027A
    */
   public Object getObject(int col) 
   {
	   String colName=getKey(col);
	   return getObject(colName);
   }
   
   /**
   获取行的列数
   @return int
   @roseuid 4B565CC5027B
    */
   public int length() 
   {
    return map.size();
   }
   
   /**
   向行中追添加数据，即字段名称与字段值
   @param name
   @param value
   @roseuid 4B565D070185
    */
   public void put(String name, Object value) 
   {
		if(name==null ||"".equals(name))
			return ;
		name=name.toUpperCase();
		if (!map.containsKey(name)) {
			ordering.addElement(name); // 将键保存起来
		}
		map.put(name, value);    
   }
   
   /**
   根据列索引获得列名
   @param which
   @return java.lang.String
   @roseuid 4B565DB303BE
    */
   public String getKey(int which) 
   {
		if(!checkIndex(which))
		{
			return "";
		}
		if(which<ordering.size())
		{
			String key =StringUtils.checkNullString(ordering.elementAt(which));
			return key;
		}
		else
			return "";
   }
   
   /**
   新建一个和本身一样字段个数的DataRow对象，值都为空的
   @return com.ultrapower.system.data.DataRow
   @roseuid 4B565F050116
    */
   public DataRow newRow() 
   {
    return null;
   }
   
   /**
   根据列名取列数据
   @param col
   @return double
   @roseuid 4B57EFB603E0
    */
   public double getDouble(String col) 
   {
    return 0;
   }

   
   public long getLong(String colName) 
   {
		if(colName==null)
			colName="";
		if(colName.equals(""))
			return 0;
		colName=colName.toUpperCase();
		return NumberUtils.formatToLong(map.get(colName));//  .checkNullString(map.get(colName));
   }
   
   /**
    *根据列索引获取列数据
   @param col
   @return java.lang.String
   @roseuid 4B57EFB70066
    */
   public long getLong(int col) 
   {
	   String colName=getKey(col);
	   
	   return getLong(colName);
   }   
   
   public int getInt(String colName) 
   {
		if(colName==null)
			colName="";
		if(colName.equals(""))
			return 0;
		colName=colName.toUpperCase();
		return NumberUtils.formatToInt(map.get(colName));//  .checkNullString(map.get(colName));
   }
   
   /**
    *根据列索引获取列数据
   @param col
   @return java.lang.String
   @roseuid 4B57EFB70066
    */
   public int getInt(int col) 
   {
	   String colName=getKey(col);
	   
	   return getInt(colName);
   }   
   
   /**
   @param col
   @return float
   @roseuid 4B57F03202FB
    */
   public float getFloat(int col) 
   {
    return 0;
   }
   
   /**
   根据列名取列数据
   @param col
   @return Float
   @roseuid 4B57F0320397
    */
   public Float getFloat(String col) 
   {
    return null;
   }
   
   /**
    * 检查索引
    * @param which
    * @return
    */
   private boolean checkIndex(int which)
   {
		if(which<0)
			return false;
		if(which>=map.size())
			return false;
		return true;
   }

	/**
	 * 行数据操作类型，1：新增 2 修改 3 删除  4是查询
	 * @return
	 */   
	public int getOptype() 
	{
		return optype;
	}
	
	/**
	 * 行数据操作类型，1：新增 2 修改 3 删除  4是查询
	 * @param optype
	 */
	public void setOptype(int optype) 
	{
		this.optype = optype;
	}
}
