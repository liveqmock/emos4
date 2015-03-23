
package com.ultrapower.eoms.common.core.component.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-1-21
 */
public class DataTable 
{

	private String tblName="";
   /*
    * 存放DataRow对象
    */
   private List lstDataRow=new ArrayList();
   /**
   关键字段，修改数据时会根据以该关键字为条件
    */
   private  String[] primaryKey;
   /**
   @param p_tblName - 表名
   @roseuid 4B557CB000FB
    */
   public DataTable(String p_tblName) 
   {
	   tblName=p_tblName;
   }
   private int[] columnType;
   
   public int[] getColumnType() {
	return columnType;
   }

	public void setColumnType(int[] columnType) {
		this.columnType = columnType;
	}

/*
    * 返回数据的行数
   @return int
   @roseuid 4B5565010331
    */
   public int length() 
   {
    return lstDataRow.size();
   }
   
   /**
   向表中加一行数据
   @param p_objRow
   @roseuid 4B5573E002AA
    */
   public void putDataRow(DataRow p_objRow) 
   {
	   if(p_objRow!=null)
		   lstDataRow.add(p_objRow);
   }
   
   /**
   @param p_rowIndex
   @return com.ultrapower.system.data.DataRow
   @roseuid 4B55772D0013
    */
   public DataRow getDataRow(int p_rowIndex) 
   {
	if(p_rowIndex>=0)
	{
		int lstCount=this.length();
		if(p_rowIndex<lstCount)
		{
			return (DataRow)lstDataRow.get(p_rowIndex);
		}
	}
    return null;
   }
   
   /**
   返回表名
   @return java.lang.String
   @roseuid 4B56704C0170
    */
   public String getTableName() 
   {
    return tblName;
   }

   public void setTableName(String tableName) 
   {
	   tblName=tableName;
   }

    /**
     * 设置表的主键，修改数据时会根据主键的数据进行update操作
     * @return
     */
	public String[] getPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * 设置表的主键，修改数据时会根据主键的数据进行update操作
	 * @param primaryKey
	 */
	public void setPrimaryKey(String[] primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public List getRowList()
	{
		return lstDataRow;
	}
	public void setRowList(List rowList)
	{
		
		lstDataRow=rowList;
	}	
	/**
	 * 获得数据表的xml格式的字符串
	 * @return
	 */
	public String getXmlString()
	{
		int rowCount=this.length();
		if(rowCount==0)
			return "";
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<");
		stringBuffer.append(this.tblName);
		stringBuffer.append(">");
		int colCount;
		int col;
		DataRow dtRow;
		String colName;
		for(int row=0;row<rowCount;row++)
		{
			dtRow=this.getDataRow(row);
			if(dtRow==null)
				continue;
			colCount=dtRow.length();
			stringBuffer.append("<row>");
			for(col=0;col<colCount;col++)
			{
				colName=dtRow.getKey(col);
				stringBuffer.append("<");
				stringBuffer.append(colName);
				stringBuffer.append(">");
				stringBuffer.append(dtRow.getString(colName));
				stringBuffer.append("</");
				stringBuffer.append(colName);
				stringBuffer.append(">");
			}
			stringBuffer.append("</row>");
		}
		stringBuffer.append("</");
		stringBuffer.append(this.tblName);
		stringBuffer.append(">");		
		return stringBuffer.toString();
		
	}
	
	/*
	 * 清空DataTable中的所有数据
	 */
	
	public void removeAllDataRow(){
		lstDataRow.clear();
	}
   
}
