package com.ultrapower.eoms.ultrasm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;


/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： 孙海龙
 * @date： 2010-5-27 
 * 当前版本：
 * 摘要: 定义公用方法的类
 */
public class UltraSmUtil
{
	public static String MANAGER = "Demo";//管理员登陆名
	
	/**
	 * 将List转换成以","分割的字符串。
	 * 返回结果如：1,2,3
	 */
	public static String listThanString(List list)
	{
		String strs = "";
		if(list != null)
		{
			strs = list.toString();
			strs = strs.replaceAll(" ", "");
			strs = strs.replace("[", "");
			strs = strs.replace("]", "");
		}
		return strs;
	}
	
	/**
	 * 将数组转换成List
	 * @param strs
	 * @return
	 */
	public static List arrayToList(Object[] strs)
	{
		List list = new ArrayList();
		if(strs != null && strs.length > 0)
		{
			for(int i=0;i<strs.length;i++)
			{
				list.add(strs[i]);
			}
		}
		return list;
	}
	
	public static String getSimpleStr(String str1,String str2) throws Exception
	{
		List list1 = "".equals(str1)?null:arrayToList(str1.split(","));
		List list2 = "".equals(str2)?null:arrayToList(str2.split(","));
		List list = ArrayTransferUtils.copyListNoteTheSame(list1, list2);
		return listThanString(list);
	}
	/**
	 * 获取SQL参数集
	 * 传入一个以","分割的字符串id返回一个map
	 * map中存放参数个数和对象数组
	 */
	public static Map getSqlParameter(String ids)
	{
		ids = ids.replaceAll(" ", "");
		return getSqlParameter(ids.split(","));
	}
	
	/**
	 * 获取SQL参数集
	 * 传入一个字符串数组返回一个map
	 * map中存放参数个数和对象数组
	 */
	public static Map getSqlParameter(String[] str)
	{
		List list = new ArrayList();
		for(int i=0;i<str.length;i++)
		{
			list.add(str[i]);
		}
		return getSqlParameter(list);
	}
	
	/**
	 * 获取SQL参数集
	 * 传入一个list返回一个map
	 * map中存放参数个数和对象数组
	 */
	public static Map getSqlParameter(List list)
	{
		Map map = new HashMap();
		int n = 0;
		if(list != null)
			n = list.size();
		Object[] obj = new Object[n];
		String wh = "";
		for(int i=0;i<n;i++)
		{
			if(i!=0)
			{
				wh = wh + ",";
			}
			wh = wh + "?";
			obj[i] = list.get(i);
		}
		map.put("?", wh);
		map.put("obj", obj);
		return map;
	}
	
	/**
	 * 获取Map的键值
	 */
	public static List getMapKeys(Map map)
	{
		List keyList = new ArrayList();
		Set keys = map.keySet();
		Object[] keyObj = keys.toArray();
		for(int i=0;i<keyObj.length;i++)
		{
			keyList.add(keyObj[i]);
		}
		return keyList;
	}
	
	/**
	 * 专门处理整体保存中的为列表信息添加XML头信息
	 * @param content	XML实体内容（JTable.js生成的）
	 * @return
	 */
	public static String thansToXml(String content)
	{
		content = StringUtils.checkNullString(content);
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<sql-group>");
		sb.append(content);
		sb.append("</sql-group>");
		return sb.toString();
	}
	
	/**
	 * 移除list中空数据
	 * @param list	存放String类型的list
	 * @return
	 */
	public static List removeNullData(List list)
	{
		if(list != null && list.size() > 0)
		{
			for(int i=0;i<list.size();i++)
			{
				if("".equals(StringUtils.checkNullString(list.get(i))))
				{
					list.remove(i);
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据ID查询名称
	 * 主要使用在操作审计模块
	 * @param tableName 数据库表名
	 * @param fieldName 名称对应字段名
	 * @param idList 查询的ID集合
	 * @return
	 */
	public static String getNameById(String tableName, String fieldName, List idList)
	{
		String strName = "";
		QueryAdapter queryAdapter = new QueryAdapter();
		Map map = getSqlParameter(idList);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(fieldName);
		sql.append(" from ");
		sql.append(tableName);
		sql.append(" where pid in (");
		sql.append(map.get("?"));
		sql.append(")");
		DataTable table = queryAdapter.executeQuery(sql.toString(), (Object[]) map.get("obj"));
		if(table != null && table.length() > 0)
		{
			for(int i=0;i<table.length();i++)
			{
				String temp = table.getDataRow(i).getString(0);
				if(!"".equals(StringUtils.checkNullString(temp)))
				{
					if(!"".equals(strName))
						strName += ",";
					strName += temp;
				}
			}
		}
		return strName;
	}
	
	/**
	 * 主要测试用
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}
}
