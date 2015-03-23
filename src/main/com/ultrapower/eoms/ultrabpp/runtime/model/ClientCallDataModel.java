package com.ultrapower.eoms.ultrabpp.runtime.model;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataTable;

public class ClientCallDataModel<T>
{
	private DataType returnType;
	public enum DataType
	{
		String, Map, List, Table, Bean
	}
	
	private String re_StringData;
	private Map<String, T> re_MapData;
	private List<T> re_ListData;
	private DataTable re_TableData;
	private T re_BeanData;
	
	public void setData(String returnData)
	{
		returnType = DataType.String;
		re_StringData = returnData;
	}
	public void setData(Map<String, T> returnData)
	{
		returnType = DataType.Map;
		re_MapData = returnData;
	}
	public void setData(List<T> returnData)
	{
		returnType = DataType.List;
		re_ListData = returnData;
	}
	public void setData(DataTable returnData)
	{
		returnType = DataType.Table;
		re_TableData = returnData;
	}
	public void setData(T returnData)
	{
		returnType = DataType.Bean;
		re_BeanData = returnData;
	}
	/**
	 * @return the returnType
	 */
	public DataType getReturnType()
	{
		return returnType;
	}
	/**
	 * @return the re_StringData
	 */
	public String getRe_StringData()
	{
		return re_StringData;
	}
	/**
	 * @return the re_MapData
	 */
	public Map<String, T> getRe_MapData()
	{
		return re_MapData;
	}
	/**
	 * @return the re_ListData
	 */
	public List<T> getRe_ListData()
	{
		return re_ListData;
	}
	/**
	 * @return the re_TableData
	 */
	public DataTable getRe_TableData()
	{
		return re_TableData;
	}
	/**
	 * @return the re_BeanData
	 */
	public T getre_BeanData()
	{
		return re_BeanData;
	}
}
